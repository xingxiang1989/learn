package com.some.im

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.text.TextUtils
import android.util.Log

/**
 * @author xiangxing
 * RongService crash的捕获以及日志的打印并上报
 */
class RongService : Service(), Thread.UncaughtExceptionHandler {

    private val tag = "RongService"
    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "onCreate, pid == ${Process.myPid()}")
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(tag, "onBind, pid == ${Process.myPid()}")
        return null
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy, pid == ${Process.myPid()}")
        super.onDestroy()
        Process.killProcess(Process.myPid())
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(tag, "onUnbind, pid == ${Process.myPid()}")
        return super.onUnbind(intent)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        var reason = throwable.toString()
        if (!TextUtils.isEmpty(reason) && reason.contains(":")) {
            reason = reason.substring(0, reason.indexOf(":"))
        }
        Log.e(tag, "uncaughtException reason = $reason")
        defaultExceptionHandler?.uncaughtException(thread, throwable)
    }

}