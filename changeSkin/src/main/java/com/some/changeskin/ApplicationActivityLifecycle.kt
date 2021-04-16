package com.some.changeskin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.collection.ArrayMap
import androidx.core.view.LayoutInflaterCompat
import java.util.*

/**
 * @author xiangxing
 * 监听activity的生命周期，用于为每个activity设置新的factory2，以及管理activity对应的factory
 */
class ApplicationActivityLifecycle(observable: Observable): Application.ActivityLifecycleCallbacks {

    var mObservable : Observable?= null
    /**
     * 存储每个activity对应的LayoutInflater.Factory2
     */
    private val mLayoutInflaterFactories: ArrayMap<Activity, SkinLayoutInflaterFactory> = ArrayMap()

    init {
        mObservable = observable
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        /**
         * TODO：需要查看onActivityCreated执行时机，factory2是在何时进行设置的
         * 更新状态栏
         */
        SkinThemeUtils.updateStatusBarColor(activity)
        val layoutInflater = activity.layoutInflater

        val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
        field.isAccessible = true
        field.setBoolean(layoutInflater,false)

        //使用factory2 设置布局加载工程
        val skinLayoutInflaterFactory = SkinLayoutInflaterFactory(activity)
        LayoutInflaterCompat.setFactory2(layoutInflater,skinLayoutInflaterFactory)
        mLayoutInflaterFactories[activity] = skinLayoutInflaterFactory

        //每一个页面会创建，那么就会作为观察者被加入进去
        mObservable?.addObserver(skinLayoutInflaterFactory)

    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        val factory = mLayoutInflaterFactories.remove(activity)
        mObservable?.deleteObserver(factory)

    }
}