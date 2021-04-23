package com.some.changeskin

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import java.util.*

/**
 * 这个管理类被定义成一个被观察者，如果皮肤有更改，会通知给其他的观察者
 *
 * 任何一个类继承Observable ，都可以作为被观察者
 *
 * @author xiangxing
 */
class SkinManager private constructor(application: Application): Observable() {

    private val tag = "SkinManager"
    private var mContext: Application
    private var skinActivityLifecycle: ApplicationActivityLifecycle

    /**
     * 应用每次启动后，就会加载原来已经保存的apk地址信息
     */
    init {
        Log.d(tag, "init constructor----》")
        mContext = application
        //初始化sp
        SkinPreference.init(application)
        //初始化resoureces
        SkinResources.init(application)
        //注册activity的生命周期
        skinActivityLifecycle = ApplicationActivityLifecycle(this)
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle)

        //每次应用启动，初始化都会自动 加载上次使用保存的皮肤
        loadSkin(SkinPreference.getInstance().skin)

        Log.d(tag, "init constructor end----》")


    }

    companion object{
        //默认是否set，get方法，现在对set方法前添加私有
        @Volatile var instance: SkinManager? = null
                        private set

        /**
         * 初始化 必须在Application中先进行初始化
         */
        fun init(application: Application): SkinManager{
            Log.d("SkinManager", "init app--->")
            return instance?: synchronized(this){
                instance?: SkinManager(application).also {
                    instance = it
                    Log.d("SkinManager", "init app instance--->")
                }
            }
        }

    }

    /**
     * 从apk中加载皮肤并应用
     *
     * @param skinPath 皮肤路径 如果为空则使用默认皮肤
     *
     * 存储的是类似这种格式："/data/data/com.enjoy.skin/skin/skin-debug.apk"，apk的路径
     */
    fun loadSkin(skinPath: String){
        Log.d(tag, " loadSkin skinPath = $skinPath")
        if(TextUtils.isEmpty(skinPath)){
            SkinPreference.getInstance().reset()
            SkinResources.getInstance().reset()
        }else{
            try {
                val appResource: Resources = mContext.resources
                //反射创建AssetManager
                val assetManager = AssetManager::class.java.newInstance()
                //资源路径设置 目录或压缩包
                val method = AssetManager::class.java.getMethod("addAssetPath",
                        String::class.java)
                method.invoke(assetManager, skinPath)

                //根据当前的设备显示器信息 与 配置(横竖屏、语言等) 创建Resources
                val skinResource = Resources(assetManager, appResource.displayMetrics, appResource.configuration)

                //获取外部apk的包名,pms 相关还没看过
                val mPm = mContext.packageManager
                val mPackageInfo = mPm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                val packageName = mPackageInfo?.packageName?:""
                Log.d(tag, " loadSkin packageName = $packageName")

                //将已经构造好的Resources和包名存到管理类中
                SkinResources.getInstance().applySkin(skinResource, packageName)

                //记录
                SkinPreference.getInstance().skin = skinPath

            }catch (e: Exception){

            }
            //通知采集的View 更新皮肤
            //被观察者改变 通知所有观察者
            setChanged()
            notifyObservers()
            Log.d(tag, "loadSkin  setChanged")

        }
    }
}