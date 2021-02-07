package com.some.mvvmdemo.touch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityTouchBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author xiangxing
 * 验证的场景如下：
 * 1.全部都使用默认值，且不对控件做任何点击事件
 *
 * 1.1 activity,parentView 只包含这两者的事件传递
 * activity --> dispatchTouchEvent Action_down
 * parentView ---> dispatchTouchEvent Action_down
 * parentView ---> onInterceptTouchEvent Action_down
 * parentView ---> onTouchEvent Action_down
 * activity --> onTouchEvent Action_down
 * (事件最后传到activity，后面的比较重要，将不再往下传递)
 * activity --> dispatchTouchEvent Action_move
 * activity --> onTouchEvent Action_move
 * activity --> dispatchTouchEvent Action_up
 * activity --> onTouchEvent Action_up
 *
 *
 * 1.2 activity,parentView，childView 只包含这两者的事件传递
 * activity --> dispatchTouchEvent Action_down
 * parentView ---> dispatchTouchEvent Action_down
 * parentView ---> onInterceptTouchEvent Action_down
 * childView ---> dispatchTouchEvent Action_down
 * childView ---> onInterceptTouchEvent Action_down （层级有多深就传递有多深）
 * childView ---> onTouchEvent Action_down
 * parentView ---> onTouchEvent Action_down
 * activity --> onTouchEvent Action_down
 * (事件最后传到activity，后面的比较重要，将不再往下传递)
 * activity --> dispatchTouchEvent Action_move
 * activity --> onTouchEvent Action_move
 * activity --> dispatchTouchEvent Action_up
 * activity --> onTouchEvent Action_up
 *
 * 2.对textview或者btn做点击事件(setOnClickListener事件，setOnTouchListener事件)
 *
 * 3.父viewGroup 进行了拦截
 *
 * 4.
 *
 */
class TouchActivity: BaseActiviy() {

    private val TAG = "TouchTest_Activity"
    val scheduledExecutorService = Executors.newScheduledThreadPool(4)

    lateinit var mBinding: ActivityTouchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
                DataBindingUtil.setContentView<ActivityTouchBinding>(this, R.layout
                .activity_touch)
        Log.d(TAG,"init")

//        mBinding.tv.setOnClickListener {
//            ToastUtils.showShort("textView onClick")
//        }





//        scheduledExecutorService.schedule(object: Runnable{
//            override fun run() {
//               Log.d("延迟执行")
//            }
//        },2, TimeUnit.SECONDS)
//
//        /**
//         * 结束后再执行
//         */
//        scheduledExecutorService.scheduleAtFixedRate({
//            Log.d("scheduleAtFixedRate ")
//            Thread.sleep(3000L)
//
//        },0,1,TimeUnit.SECONDS)
//
//        /**
//         * 任务结束后，在加上间隔周期
//         */
//        scheduledExecutorService.scheduleWithFixedDelay({
//
//            Log.d("scheduleWithFixedDelay ")
//            Thread.sleep(3000L)
//        },
//                0,1,TimeUnit.SECONDS)

//        val thread = MyThread()
//        thread.start()

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
            Log.d("MyHandler","msg =" + msg.what)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "dispatchTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "dispatchTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "dispatchTouchEvent ACTION_UP")
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"onTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "onTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "onTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "onTouchEvent ACTION_UP")
        }
        return super.onTouchEvent(event)
    }
}