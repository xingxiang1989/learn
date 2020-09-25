package com.some.mvvmdemo.touch

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityTouchBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author xiangxing
 */
class TouchActivity: BaseActiviy() {

    val scheduledExecutorService = Executors.newScheduledThreadPool(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         DataBindingUtil.setContentView<ActivityTouchBinding>(this, R.layout.activity_touch)
         LogUtils.d("init")
        scheduledExecutorService.schedule(object: Runnable{
            override fun run() {
               LogUtils.d("延迟执行")
            }
        },2, TimeUnit.SECONDS)

        /**
         * 结束后再执行
         */
        scheduledExecutorService.scheduleAtFixedRate({
            LogUtils.d("scheduleAtFixedRate ")
            Thread.sleep(3000L)

        },0,1,TimeUnit.SECONDS)

        /**
         * 任务结束后，在加上间隔周期
         */
        scheduledExecutorService.scheduleWithFixedDelay({

            LogUtils.d("scheduleWithFixedDelay ")
            Thread.sleep(3000L)
        },
                0,1,TimeUnit.SECONDS)

    }
}