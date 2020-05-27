package com.some.mvvmdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R

/**
 * @author xiangxing
 * 绘制圆环 https://blog.csdn.net/mp624183768/article/details/78870551
 */
class RingView: View {

    var mPaint:Paint

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        mPaint = Paint()
        mPaint.color = resources.getColor(R.color.colorAccent)
        mPaint.strokeWidth = SizeUtils.dp2px(40f).toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        LogUtils.d("init ")

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var centerX = width/2f
        var radius = SizeUtils.dp2px(80f).toFloat()

        LogUtils.d("centerX=$centerX , width = $width")

        canvas!!.drawCircle(centerX,centerX,radius,mPaint)
    }
}