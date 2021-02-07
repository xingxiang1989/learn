package com.some.mvvmdemo.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * @author xiangxing
 */
class ChildTwoView: FrameLayout {

    private val TAG = "TouchTest_childtwo"

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "dispatchTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "dispatchTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "dispatchTouchEvent ACTION_UP")
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"onInterceptTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "onInterceptTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "onInterceptTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "onInterceptTouchEvent ACTION_UP")
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"onTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "onTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "onTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "onTouchEvent ACTION_UP")
        }
        return super.onTouchEvent(ev)
    }
}