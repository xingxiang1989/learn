package com.some.mvvmdemo.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 */
class CustomTextView: AppCompatTextView {
    private val TAG = "TouchTest_custom"

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG, "dispatchTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "dispatchTouchEvent ACTION_CANCEL")
            MotionEvent.ACTION_UP -> Log.d(TAG, "dispatchTouchEvent ACTION_UP")
        }
        return super.dispatchTouchEvent(ev)
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