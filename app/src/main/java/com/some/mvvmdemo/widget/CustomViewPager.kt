package com.some.mvvmdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 */
class CustomViewPager: ViewPager {

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        LogUtils.d("keyevent dispatchTouchEvent action = " + ev?.action)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        super.onInterceptTouchEvent(ev)
        LogUtils.d("keyevent onInterceptTouchEvent action = " + ev?.action)
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        LogUtils.d("keyevent onTouchEvent action = " + ev?.action)
        return super.onTouchEvent(ev)
    }
}