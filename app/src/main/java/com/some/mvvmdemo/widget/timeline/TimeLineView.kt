package com.some.mvvmdemo.widget.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
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
    private var mVelocityTracker: VelocityTracker?= null

    /**
     * 有数据的画布宽
     */
    private var mCanvasWidth = 0f
    /**
     * 最大速度，最小速度
     */
    private var mMinimumVelocity: Int
    private var mMaximumVelocity: Int
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

        mMaximumVelocity = viewConfiguration.scaledMaximumFlingVelocity
        mMinimumVelocity = viewConfiguration.scaledMinimumFlingVelocity
        LogUtils.d("mTouchSlop = $mTouchSlop ")
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

        //当数据长度不足，不做滑动处理
        if(mCanvasWidth <= mWidth){
            return true
        }

        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(event)

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
                mLastX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                // 滑动的距离
                val scrollLengthX: Float = event.x - mLastX
                // getScrollX() 小于0，说明画布右移了
                // getScrollX() 大于0，说明画布左移了
                val endX = scrollX - scrollLengthX

                LogUtils.d("ACTION_MOVE scrollX = $scrollX , scrollLengthX = $scrollLengthX, endX = $endX ---->")
                if (scrollLengthX > 0) {    // 画布往右移动 -->

                    // 注意：这里的等号不能去除，否则会有闪动
                    if (endX <= 0) {
                        scrollTo(0, 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }
                } else if (scrollLengthX < 0) {                    // 画布往左移动  <--
                    if (endX >= mCanvasWidth - mWidth) {     // 需要考虑是否右越界
                        scrollTo((mCanvasWidth - mWidth) as Int, 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }
                }
                mLastX = event.x
                LogUtils.d("ACTION_MOVE  111111")

            }
            MotionEvent.ACTION_CANCEL -> {

            }
            MotionEvent.ACTION_UP -> {
                //计算当前速度，1000表示每秒像素数等
                mVelocityTracker?.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                //获取横向速度
                val initialVelocity = mVelocityTracker?.xVelocity ?: 0f
                LogUtils.d("ACTION_UP initialVelocity = $initialVelocity")
                //速度要大于最小的速度值，才开始滑动
                if (abs(initialVelocity) > mMinimumVelocity) {
                    fling(initialVelocity.toInt())
                }

                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()
        if(mScroller.computeScrollOffset()){
            LogUtils.d("computeScroll true")
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }else{
            LogUtils.d("computeScroll false")

        }
    }

    /**
     * 滑行，但是在滑行前需要判断当前位置是否已经触达边界
     */
    private fun fling(velocityX: Int){
        val startX = scrollX
        LogUtils.d("fling start velocityX = $velocityX, startx= $startX")

        mScroller.fling(startX, 0, velocityX, 0, 0, 0, 0, 0)
        postInvalidateOnAnimation()
    }

    fun bindData(datas: ArrayList<DateBean>){
        LogUtils.d("bindData -- >")
        mArrays = datas
        mCanvasWidth = (datas.size * itemWidth).toFloat()
        invalidate()
    }
}