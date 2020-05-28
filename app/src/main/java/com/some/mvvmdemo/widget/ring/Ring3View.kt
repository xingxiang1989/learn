package com.some.mvvmdemo.widget.ring

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R

/**
 * @author xiangxing
 */
class Ring3View: View {

    var mPaint: Paint?= null

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        mPaint = Paint()
        mPaint?.color = resources.getColor(R.color.colorAccent)
        mPaint?.isAntiAlias = true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.GRAY)

        var centerX = width/2f
        var radius = SizeUtils.dp2px(40f).toFloat()

        canvas?.drawCircle(centerX,centerX,radius,mPaint!!)

        mPaint?.color = resources.getColor(R.color.colorPrimary)
        mPaint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        canvas?.drawCircle(centerX,centerX,radius-SizeUtils.dp2px(20f),mPaint!!)

    }
}