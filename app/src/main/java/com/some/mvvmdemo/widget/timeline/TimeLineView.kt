package com.some.mvvmdemo.widget.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R

/**
 * @author xiangxing
 * 时间线练习
 */
class TimeLineView: View {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val ITEM_WIDTH = SizeUtils.dp2px(50f)
    private val RADIUS = SizeUtils.dp2px(6f)
    private var mArrays = ArrayList<DateBean>()
    private var mTextPaint = Paint()
    private var mCirclePaint = Paint()
    private var mLinePaint = Paint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        mTextPaint.isAntiAlias = true
        mTextPaint.color = ContextCompat.getColor(getContext(), R.color.white)
        mTextPaint.textSize = 30f

        mCirclePaint.isAntiAlias = true
        mCirclePaint.color = ContextCompat.getColor(getContext(), R.color.red)
        mLinePaint.style = Paint.Style.FILL

        mLinePaint.isAntiAlias = true
        mLinePaint.color = ContextCompat.getColor(getContext(), R.color.red)
        mLinePaint.style = Paint.Style.FILL
        mLinePaint.strokeWidth = SizeUtils.dp2px(1f).toFloat()
    }




    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtils.d("onSizeChanged w = $w, h =$h, oldw = $oldw, oldH =$oldh")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        LogUtils.d("onMeasure mWidth = $mWidth, mHeight = $mHeight")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var mLeftX = 0
        mArrays.forEachIndexed { index, dateBean ->
            val x = mLeftX + ITEM_WIDTH/2 - mTextPaint.measureText(dateBean.getText())/2
            val y = mHeight - SizeUtils.dp2px(5f)
            LogUtils.d("mLeftX = $mLeftX, x = $x , y = $y, itemwidth = $ITEM_WIDTH, textWidth = ${mTextPaint.measureText(dateBean.getText())}")
            canvas?.drawText(dateBean.getText(), x, y.toFloat(), mTextPaint)

            val circleX = mLeftX + ITEM_WIDTH/2
            val circleY = mHeight - SizeUtils.dp2px(25f)
            canvas?.drawCircle(circleX.toFloat(), circleY.toFloat(), RADIUS.toFloat(), mCirclePaint)


            val startX = mLeftX + ITEM_WIDTH/2
            canvas?.drawLine(startX.toFloat(),0f,startX.toFloat(), (mHeight - SizeUtils.dp2px(50f)).toFloat(),mTextPaint)

            mLeftX += ITEM_WIDTH

        }
    }

    fun bindData(datas: ArrayList<DateBean>){
        mArrays = datas
        invalidate()
    }
}