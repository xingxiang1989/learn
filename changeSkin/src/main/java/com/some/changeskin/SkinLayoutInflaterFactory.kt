package com.some.changeskin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import java.lang.reflect.Constructor
import java.util.*

/**
 * 用来接管系统的view的生产过程
 * @author xiangxing
 */
class SkinLayoutInflaterFactory(activity: Activity): LayoutInflater.Factory2, Observer {


    private val TAG = "SkinLayoutInflaterFactory"
    private val mClassPrefixList = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    )
    private var skinAttribute: SkinAttribute?= null

    //记录对应VIEW的构造函数
    private val mConstructorSignature = arrayOf(
            Context::class.java, AttributeSet::class.java)

    /**
     * 提高效率，记录app中所有已经解析过的view的Constructor
     */
    private val mConstructorMap = HashMap<String, Constructor<out View?>>()
    init {
        skinAttribute = SkinAttribute()
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        //换肤就是在需要时候替换 View的属性(src、background等)
        //所以这里创建 View,从而修改View属性
        var view = createSDKView(name, context, attrs)
        if (null == view) {
            view = createView(name, context, attrs)
        }
        //这就是我们加入的逻辑
        if (null != view) {
            //加载属性
            skinAttribute?.look(view, attrs)
        }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    private fun createSDKView(name: String, context: Context, attrs: AttributeSet):View?{
        Log.e(TAG, " createSDKView name=$name")
        /**
         * 如果是系统提供的view，view的name直接是名称，比如TextView，ImageView这些都是系统提供的view
         * 而自定义的view，则会有报名路径
         *
         */
        if (-1 != name.indexOf('.')) {
            return null
        }
        mClassPrefixList.forEach {
            val view = createView(it + name, context, attrs)
            if(view != null){
                return view
            }
        }
        return null
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet):View?{
        Log.e(TAG, "createView name=$name")
        val constructor = findConstructor(context, name)
        if(constructor != null){
            return constructor.newInstance(context, attrs)
        }
        return null
    }

    /**
     * 通过name反射拿到view，再通过view获取constructor
     */
    private fun findConstructor(context: Context, name: String): Constructor<out View?>? {
        var constructor = mConstructorMap[name]
        if(constructor == null){
            //这里也可以使用Class.ForName()
            val view = context.classLoader.loadClass(name).asSubclass(View::class.java)
            constructor = view.getConstructor(*mConstructorSignature)
            mConstructorMap[name] = constructor
        }
        return constructor
    }

    override fun update(o: Observable?, arg: Any?) {
        skinAttribute?.applySkin()
    }

}