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
 * 用来接管系统的view的生产过程,
 * factory 是观察者，该类override onCreateView来获取view的相关属性并将页面中所有的view记录在SkinAttribute对象中。
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
    private var mActivity: Activity

    //记录对应VIEW的构造函数
    private val mConstructorSignature = arrayOf(
            Context::class.java, AttributeSet::class.java)

    /**
     * 提高效率，记录app中所有已经解析过的view的Constructor
     */
    private val mConstructorMap = HashMap<String, Constructor<out View?>>()
    init {
        skinAttribute = SkinAttribute()
        mActivity = activity
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {

        Log.d(TAG,"1.0 onCreateView start ---> activity = ${mActivity.javaClass.simpleName}")
        //换肤就是在需要时候替换 View的属性(src、background等)
        //所以这里创建 View,从而修改View属性
        var view = createSDKView(name, context, attrs)
        if (null == view) {
            view = createView(name, context, attrs)
        }
        //根据name获取到所有的view，（系统提供的类和自定义的类），找到控件后，再进行解析属性
        if (null != view) {
            //加载属性
            skinAttribute?.look(view, attrs)
        }
        Log.d(TAG,"onCreateView end --->")

        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    private fun createSDKView(name: String, context: Context, attrs: AttributeSet):View?{
        Log.e(TAG, "2.0 createSDKView name=$name")
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
        Log.e(TAG, "3.0 createView name=$name")
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
        Log.e(TAG, "4.0 findConstructor name=$name")
        var constructor = mConstructorMap[name]
        if(constructor == null){
            try {
                //这里也可以使用Class.ForName()
                val view = context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = view.getConstructor(*mConstructorSignature)
                mConstructorMap[name] = constructor
            }catch (e: Exception){
                //存在反射拿不到的情况
                Log.e(TAG, "findConstructor Exception $e")
            }
        }
        return constructor
    }

    override fun update(o: Observable?, arg: Any?) {
        skinAttribute?.applySkin()
    }

}