package com.some.mvvmdemo.widget.ring

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R

/**
 * @author xiangxing
 */
class Ring2View: View {

    var mPaint: Paint?= null
    var mOtherPaint: Paint?= null
    var degreeAngle: Int = 0

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        mPaint = Paint()
        mPaint?.color = resources.getColor(R.color.colorAccent)
        mPaint?.strokeWidth = SizeUtils.dp2px(20f).toFloat()
        mPaint?.style = Paint.Style.STROKE
        mPaint?.isAntiAlias = true

        mOtherPaint = Paint()
        mOtherPaint?.color = resources.getColor(R.color.colorAccent_30)
        mOtherPaint?.strokeWidth = SizeUtils.dp2px(20f).toFloat()
        mOtherPaint?.style = Paint.Style.STROKE
        mOtherPaint?.isAntiAlias = true

    }

    fun setDegreeAngele(degree:Int){
        degreeAngle = degree
        LogUtils.d("degreeAngle=$degreeAngle")
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.GRAY)

        var point = SizeUtils.dp2px(20f).toFloat()
        var width = width - SizeUtils.dp2px(20f)
        var rectF = RectF(point,point,width.toFloat(),width.toFloat())

        canvas?.drawArc(rectF,(0f + degreeAngle)%360,90f,false,mPaint!!)
        canvas?.drawArc(rectF,(90f + degreeAngle)%360,90f,false,mOtherPaint!!)
        canvas?.drawArc(rectF,(180f + degreeAngle)%360,90f,false,mPaint!!)
        canvas?.drawArc(rectF,(270f + degreeAngle)%360,90f,false,mOtherPaint!!)

    }
}