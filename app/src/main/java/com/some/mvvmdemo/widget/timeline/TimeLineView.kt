package com.some.mvvmdemo.widget.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R
import kotlin.math.abs

/**
 * @author xiangxing
 * 时间线练习
 */
class TimeLineView: View {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val itemWidth = SizeUtils.dp2px(50f)
    private val RADIUS = SizeUtils.dp2px(6f)
    private var mArrays = ArrayList<DateBean>()
    private var mTextPaint = Paint()
    private var mCirclePaint = Paint()
    private var mLinePaint = Paint()
    private var mScroller: Scroller
    private var mTouchSlop: Int = 0
    private var mLastX: Float = 0f
    private var mScrollX: Float = 0f

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

        mScroller = Scroller(context)
        val viewConfiguration = ViewConfiguration.get(context)
        mTouchSlop = viewConfiguration.scaledTouchSlop

        LogUtils.d("mTouchSlop = $mTouchSlop")
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
            val x = mLeftX + itemWidth/2 - mTextPaint.measureText(dateBean.getText())/2
            val y = mHeight - SizeUtils.dp2px(5f)
            LogUtils.d("mLeftX = $mLeftX, x = $x , y = $y, itemwidth = $itemWidth, textWidth = ${mTextPaint.measureText(dateBean.getText())}")
            canvas?.drawText(dateBean.getText(), x, y.toFloat(), mTextPaint)

            val circleX = mLeftX + itemWidth/2
            val circleY = mHeight - SizeUtils.dp2px(25f)
            canvas?.drawCircle(circleX.toFloat(), circleY.toFloat(), RADIUS.toFloat(), mCirclePaint)


            val startX = mLeftX + itemWidth/2
            canvas?.drawLine(startX.toFloat(), 0f, startX.toFloat(), (mHeight - SizeUtils.dp2px(50f)).toFloat(), mTextPaint)

            mLeftX += itemWidth

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
                mLastX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                var deltaX = mLastX - x
                if (abs(deltaX) > mTouchSlop) {
                    LogUtils.d("滑动距离大于mTouchSlop")
                    if (deltaX > 0) {
                        deltaX -= mTouchSlop
                    } else {
                        deltaX += mTouchSlop
                    }
                    mLastX = x
                    mScrollX += deltaX
                    invalidate()
                } else {
                    LogUtils.d("滑动距离过小")
                }
            }
            MotionEvent.ACTION_CANCEL -> {

            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()
        if(mScroller.computeScrollOffset()){
            LogUtils.d("computeScroll true")
            scrollTo(mScroller.currX,mScroller.currY)
            invalidate()
        }else{
            LogUtils.d("computeScroll false")

        }
    }

    fun bindData(datas: ArrayList<DateBean>){
        LogUtils.d("bindData -- >")
        mArrays = datas
        invalidate()
    }
}