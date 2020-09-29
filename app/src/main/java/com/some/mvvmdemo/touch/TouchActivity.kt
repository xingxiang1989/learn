package com.some.mvvmdemo.touch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
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

    lateinit var mBinding: ActivityTouchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
                DataBindingUtil.setContentView<ActivityTouchBinding>(this, R.layout
                .activity_touch)
         LogUtils.d("init")
//        scheduledExecutorService.schedule(object: Runnable{
//            override fun run() {
//               LogUtils.d("延迟执行")
//            }
//        },2, TimeUnit.SECONDS)
//
//        /**
//         * 结束后再执行
//         */
//        scheduledExecutorService.scheduleAtFixedRate({
//            LogUtils.d("scheduleAtFixedRate ")
//            Thread.sleep(3000L)
//
//        },0,1,TimeUnit.SECONDS)
//
//        /**
//         * 任务结束后，在加上间隔周期
//         */
//        scheduledExecutorService.scheduleWithFixedDelay({
//
//            LogUtils.d("scheduleWithFixedDelay ")
//            Thread.sleep(3000L)
//        },
//                0,1,TimeUnit.SECONDS)

        val thread = MyThread()
        thread.start()

    }

    inner class MyThread: Thread(){

        override fun run() {
            super.run()

            //case 1：handler 需要配合Looper的使用
//            Looper.prepare()
//            val handler = MyHandler()
//            handler.sendEmptyMessage(10)
//            Looper.loop()

            //case 2: 子线程更新UI，toast，showdialog
//            Looper.prepare()
//            Toast.makeText(this@TouchActivity, "toast", Toast.LENGTH_SHORT).show()
//            Looper.loop()

            Thread.sleep(3000L)

            //case 3:
            mBinding.tv.text = " hello world"

        }
    }

    class MyHandler: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtils.d("msg =" + msg.what)
        }
    }
}