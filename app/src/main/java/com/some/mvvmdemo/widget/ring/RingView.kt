package com.some.mvvmdemo.widget.ring

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
    var strokeWidth = 0f

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        strokeWidth = SizeUtils.dp2px(5f).toFloat()
        mPaint = Paint()
        mPaint.color = resources.getColor(R.color.colorAccent)
        mPaint.strokeWidth = strokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        LogUtils.d("init ")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(SizeUtils.dp2px(90f),SizeUtils.dp2px(90f))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var rWidth = measuredWidth
        var centerX = rWidth/2f

        LogUtils.d("centerX=$centerX , rWidth = $rWidth")
        canvas!!.drawCircle(centerX,centerX,rWidth/2f - strokeWidth,mPaint)
    }
}