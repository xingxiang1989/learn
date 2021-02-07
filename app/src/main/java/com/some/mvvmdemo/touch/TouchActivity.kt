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
 * 1.3 activity,parentView，childView，customView
 * 传递层级与1.2 一致，只是传递路径更深
 *
 * 2.对textview或者btn做点击事件(setOnClickListener事件，setOnTouchListener事件)
 * 2.1 对textview做setOnClickListener事件
 * 设置点击事件，会自动设置为setClickable(true)，这样在OnTouchEvent（）中有进行是否可点击的判断，如果有，则会返回true，事件
 * Action_down被消耗掉。
 * activity --> dispatchTouchEvent Action_down
 * parentView ---> dispatchTouchEvent Action_down
 * parentView ---> onInterceptTouchEvent Action_down
 * childView ---> dispatchTouchEvent Action_down
 * childView ---> onInterceptTouchEvent Action_down （层级有多深就传递有多深）
 * customView ---> dispatchTouchEvent Action_down
 * customView ---> onTouchEvent Action_down (此处事件被消耗，就不再向上传递onTouchEvent事件)
 * activity --> dispatchTouchEvent Action_move
 * parentView ---> dispatchTouchEvent Action_move
 * parentView ---> onInterceptTouchEvent Action_move
 * childView ---> dispatchTouchEvent Action_move
 * childView ---> onInterceptTouchEvent Action_move
 * customView ---> dispatchTouchEvent Action_move
 * customView ---> onTouchEvent Action_move
 * 后面省略...
 * customView ---> onTouchEvent Action_up
 * textView onClick （onclick 事件是在OnTouchEvent中 action_up中进行判断触发）
 *
 * 2.2 对childView设置setOnClickListener事件
 * 点击区域与customView不重合，事件触发顺序与点击textview类似，只是层级会少
 * 点击区域与customView重合，childView点击事件不会触发，因为onTouchEvent action_down事件会优先会customView消耗掉
 *
 * 3.父viewGroup 进行了拦截
 * customView，childView都设置点击事件，且点击重叠区域，childView进行事件拦截
 * 事件只会传递到childView
 *
 * 4.同层级事件分发
 * 4.1 childView，childViewTwo 均不做事件消费
 * activity --> dispatchTouchEvent Action_down
 * parentView ---> dispatchTouchEvent Action_down
 * parentView ---> onInterceptTouchEvent Action_down
 * （接下来很重要，根据层级关系chidTwoView，childView会依次执行事件，各自走各自的事件分发）
 * childTwoView ---> dispatchTouchEvent Action_down
 * childTwoView ---> onInterceptTouchEvent Action_down （层级有多深就传递有多深）
 * childTwoView ---> onTouchEvent Action_down
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
 * 4.2 childView，childTwoView
 * 同层级，两个都做点击事件（这个就类似直播间做的滑动事件，上层是recyvlerview的滑动，下面是contentview）
 * 只会响应childTwoView，并且事件根本不会分发到childView中，可以跟4.1情况做比较
 * activity --> dispatchTouchEvent Action_down
 * parentView ---> dispatchTouchEvent Action_down
 * parentView ---> onInterceptTouchEvent Action_down
 * childTwoView ---> dispatchTouchEvent Action_down
 * childTwoView ---> onInterceptTouchEvent Action_down （层级有多深就传递有多深）
 * childTwoView ---> onTouchEvent Action_down（事件被消耗）
 * activity --> dispatchTouchEvent Action_move
 * parentView ---> dispatchTouchEvent Action_move
 * parentView ---> onInterceptTouchEvent Action_move
 * childTwoView ---> dispatchTouchEvent Action_move
 * childTwoView ---> onInterceptTouchEvent Action_move （层级有多深就传递有多深）
 * childTwoView ---> onTouchEvent Action_move（事件被消耗）
 *
 * 4.3 如何让事件透传，保证上下两个view都能接收到事件
 * 直接在parentView中dispatchTouchEvent方法中进行手动的分发，这样就可以（直播间就是这样做的）
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

        mBinding.tv.setOnClickListener {
            Log.d(TAG,"textView onClick")
        }

        mBinding.childView.setOnClickListener {
            Log.d(TAG,"childView onClick")

        }

        mBinding.childTwoView.setOnClickListener {
            Log.d(TAG,"childTwoView onClick")
        }



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
//        mBinding.childView.dispatchTouchEvent(ev!!)
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