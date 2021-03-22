package com.some.im

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

/**
 * @author xiangxing
 * 仿照融云im编写，里面有很多值得学习
 */
class ImClient private constructor() {

    private var topForeGroundActivity: Activity? = null
    private val tag = "ImClient"
    private var mContext: Context? = null
    private var mAppKey: String?= null

    companion object {

        private var isEnablePush: Boolean = false
        private var isInForeground = false


        private val instance: ImClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ImClient()
        }

        fun init(context: Context) {
            init(context, null)
        }

        fun init(context: Context, appKey: String?) {
            init(context, appKey, true)
        }

        fun init(context: Context, appKey: String?, isEnablePush: Boolean) {
            this.isEnablePush = isEnablePush
            instance.initSdk(context, appKey)
        }
    }

    private fun initSdk(context: Context, appKey: String?) {

        //1. packagename的比较
        //2. 监听前后台变化,这里的监听写的很有意思，完全依赖于生命周期的切换，与blankj写的不同

        mContext = context

        (context as? Application)?.registerActivityLifecycleCallbacks(object :
                Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {

            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                if (topForeGroundActivity == null) {
                    Log.d(tag, "i am foreground")
                    onAppBackgroundChanged(true)
                }
                topForeGroundActivity = activity
            }

            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                if (topForeGroundActivity == activity) {
                    Log.d(tag, "i am background")
                    onAppBackgroundChanged(false)
                    topForeGroundActivity = null
                }
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityDestroyed(p0: Activity) {

            }
        })

    }

    private fun onAppBackgroundChanged(isForeground: Boolean) {
        if(mContext != null){
            isInForeground = isForeground
            //这里有判断，判断serviceConn是否已经连接，如果有连接则进行检测
            //如果没有，则进行bindService
            if(isForeground){
                initBindService()
            }
        }
    }

    private fun initBindService(){
        try {
            val intent = Intent(mContext, ImService::class.java)
            intent.putExtra("appKey", this.mAppKey)
            intent.putExtra("deviceId", "1111")
//            mContext!!.bindService(intent, this.mAidlConnection, 1)
        } catch (var2: SecurityException) {
            Log.e("RongIMClient", "initBindService SecurityException")
            Log.e("RongIMClient", "initBindService", var2)
        }
    }
}