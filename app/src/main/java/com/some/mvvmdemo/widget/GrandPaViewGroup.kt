package com.some.mvvmdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * @author xiangxing
 */
class GrandPaViewGroup : ViewGroup {

    var view: View?= null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        LogUtils.d("l=$l , t = $t , r = $r , b = $b")


    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        view = getChildAt(0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(SizeUtils.dp2px(350f),SizeUtils.dp2px(350f))

    }
}
