package com.some.mvvmdemo.widget.timeline

import android.annotation.SuppressLint
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
 * https://my.oschina.net/ososchina/blog/600281 关于Scroller写的很透彻
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

    /**
     * Scroller只是个计算器，提供插值计算，让滚动过程具有动画属性，但它并不是UI，也不是辅助UI滑动，反而是单纯地为滑动提供计算。
     * 无论从构造方法还是其他方法，以及Scroller的属性可知，其并不会持有View，辅助ViewGroup滑动
     */
    private var mScroller: Scroller
    private var mVelocityTracker: VelocityTracker?= null

    /**
     * 有数据的画布宽
     */
    private var mCanvasWidth = 0f
    /**
     * 最大速度，默认为24000
     */
    private var mMinimumVelocity: Int

    /**
     * 最小速度，默认150
     */
    private var mMaximumVelocity: Int

    /**
     * 最小滑动距离24
     */
    private var mTouchSlop: Int = 0

    /**
     * 上一个位置
     */
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
        LogUtils.d("mTouchSlop = $mTouchSlop ，mMaximumVelocity = $mMaximumVelocity, mMinimumVelocity = $mMinimumVelocity")
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

        LogUtils.d("onDraw start --->")
        var mLeftX = 0
        mArrays.forEachIndexed { index, dateBean ->
            val x = mLeftX + itemWidth/2 - mTextPaint.measureText(dateBean.getText())/2
            val y = mHeight - SizeUtils.dp2px(5f)
            if(isInVisibleArea(mLeftX.toFloat())){
                canvas?.drawText(dateBean.getText(), x, y.toFloat(), mTextPaint)

                val circleX = mLeftX + itemWidth/2
                val circleY = mHeight - SizeUtils.dp2px(25f)
                canvas?.drawCircle(circleX.toFloat(), circleY.toFloat(), RADIUS.toFloat(), mCirclePaint)

                val startX = mLeftX + itemWidth/2
                canvas?.drawLine(startX.toFloat(), 0f, startX.toFloat(), (mHeight - SizeUtils.dp2px(50f)).toFloat(), mTextPaint)

            }

            mLeftX += itemWidth

        }
    }


    /**
     * 是否在可视的范围内
     *
     * @param x
     * @return true：在可视的范围内；false：不在可视的范围内
     */
    private fun isInVisibleArea(x: Float): Boolean {
//        val dx = x - scrollX
//        return -itemWidth <= dx && dx <= mWidth + itemWidth
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //当数据长度不足，不做滑动处理
        if(mCanvasWidth <= mWidth){
            LogUtils.d("onTouchEvent 长度不足，不做滑动")
            return true
        }

        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(event)

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                LogUtils.d("onTouchEvent ACTION_DOWN")
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

                LogUtils.d("ACTION_MOVE scrollX = $scrollX , scrollLengthX = $scrollLengthX, endX = $endX， event.x = " +
                        "${event.x} , mLastX = $mLastX")
                if (scrollLengthX > 0) {    // 画布往右移动 -->
                    // 画布往左移动  <--
                    LogUtils.d("ACTION_MOVE 右滑 ")

                    // 注意：这里的等号不能去除，否则会有闪动
                    if (endX <= 0) {
                        scrollTo(0, 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }
                } else if (scrollLengthX < 0) {                    // 画布往左移动  <--
                    // 画布往左移动  <--
                    LogUtils.d("ACTION_MOVE 左滑 ")
                    if (endX >= mCanvasWidth - mWidth) {     // 需要考虑是否右越界
                        scrollTo((mCanvasWidth - mWidth).toInt(), 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }
                }
                mLastX = event.x
                LogUtils.d("ACTION_MOVE  -----> end")

            }
            MotionEvent.ACTION_CANCEL -> {
                LogUtils.d("onTouchEvent ACTION_CANCEL")

            }
            MotionEvent.ACTION_UP -> {
                LogUtils.d("onTouchEvent ACTION_UP")

                //计算当前速度，1000表示每秒像素数等
                mVelocityTracker?.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                //获取横向速度
                val initialVelocity = mVelocityTracker?.xVelocity ?: 0f
                LogUtils.d("ACTION_UP initialVelocity = $initialVelocity")
                //速度要大于最小的速度值，才开始滑动
                if (abs(initialVelocity) > mMinimumVelocity) {
                    fling(-initialVelocity.toInt())
                }

                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return true
    }

    /**
     * 1、computeScroll也不是来让ViewGroup滑动的，真正让ViewGroup滑动的是scrollTo,scrollBy。
     * 2、computeScroll的作用是计算ViewGroup如何滑动。computeScroll是通过draw来调用的。
     * 3、Scroller不会调用computeScroll，反而是computeScroll调用Scroller。
     */
    override fun computeScroll() {
        super.computeScroll()
        if(mScroller.computeScrollOffset()){
            LogUtils.d("computeScroll scrollx = $scrollX, mScroller.currX = ${mScroller.currX}")
            val difx = scrollX - mScroller.currX
            LogUtils.d("computeScroll true and difx = $difx ")
            if(difx != 0){
                scrollTo(mScroller.currX,0)
            }
        }else{
            LogUtils.d("computeScroll false")

        }
    }

    /**
     * 滑行，但是在滑行前需要判断当前位置是否已经触达边界
     */
    private fun fling(velocityX: Int){
        val startX = scrollX
        val maxX = (mCanvasWidth - mWidth)
        LogUtils.d("fling start velocityX = $velocityX, startx= $startX, maxX = $maxX")
        mScroller.fling(startX, 0, velocityX, 0, 0, maxX.toInt(), 0, 0)
        postInvalidateOnAnimation()
    }

    fun bindData(datas: ArrayList<DateBean>){
        LogUtils.d("bindData -- >")
        mArrays = datas
        mCanvasWidth = (datas.size * itemWidth).toFloat()
        LogUtils.d("bindData -- >mCanvasWidth =$mCanvasWidth")

        invalidate()
    }
}