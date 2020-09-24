package com.some.mvvmdemo.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 */
class ChildView: FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> LogUtils.d("dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtils.d( "dispatchTouchEvent ACTION_MOVE")
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> LogUtils.d("onInterceptTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> LogUtils.d( "onInterceptTouchEvent ACTION_MOVE")
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.d("onTouchEvent ACTION_DOWN")
        if(event?.action ==  MotionEvent.ACTION_DOWN){
            return true
        }
        return super.onTouchEvent(event)
    }
}