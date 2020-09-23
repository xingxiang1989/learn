package com.some.mvvmdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 * 学习startService ，bindService
 */
class TestServiceOne: Service() {

    inner class MyBinder: Binder() {
        fun getService(): TestServiceOne{
            return this@TestServiceOne
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        LogUtils.d("onBind")
        return MyBinder()
    }

    override fun onRebind(intent: Intent?) {
        LogUtils.d("onRebind")

        super.onRebind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("onStartCommand startId = $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("onCreate")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtils.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("onDestroy")

    }

    fun print(){
        LogUtils.d("被打印了print ---->")
    }

}