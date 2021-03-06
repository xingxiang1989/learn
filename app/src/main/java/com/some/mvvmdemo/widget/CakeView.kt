package com.some.mvvmdemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.entity.CakeBean

/**
 * @author xiangxing
 * 参考文章： https://www.jianshu.com/p/dff84787d3a5
 */
class CakeView: View {

    var mPaint: Paint
    var mList: MutableList<CakeBean>?= null
    var minParamer: Int =0
    var sum = 0f
    var mTextPaint: Paint

    /**
     * 这个是右边的小方块的w,h
     */
    var rectW = SizeUtils.dp2px(40f)
    var rectH = SizeUtils.dp2px(25f)

    //存储的是路径
    var mPathList: MutableList<Path> = mutableListOf()
    var mCenterX: Float = 0f
    var mCenterY: Float = 0f
    private var mOnPartClickListener: ((CakeBean) -> Unit)?= null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.color = Color.BLACK
        mTextPaint.textSize = 20f

    }

    fun setOnPartyClickListener(block:(CakeBean) -> Unit){
        mOnPartClickListener = block
    }

    fun setCakeList(list: MutableList<CakeBean>){

        mPathList.clear()
        for(bean in list){
            sum += bean.amount
            mPathList.add(Path())
        }
        for(bean in list){
            bean.degree = bean.amount/sum * 360
        }
        mList = list
        invalidate()
    }

    /**
     * 为什么要重写，这是因为要考虑到父布局的宽，高。我们依赖父布局的宽高绘制，
     * 这样才能确定绘制在哪里
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var mWMode = MeasureSpec.getMode(widthMeasureSpec)
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        var mHMode = MeasureSpec.getMode(heightMeasureSpec)
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)

        LogUtils.d("onMeasure parent mwMode= $mWMode , mWidth=$mWidth, mHMode=$mHMode, " +
                " mHeight=$mHeight")

        var width = 0
        var height = 0

        when(mWMode){
            MeasureSpec.EXACTLY -> {
                //match_parent或者具体的数值
                width = mWidth

                LogUtils.d("onMeasure parent wMode MeasureSpec.EXACTLY ")
            }
            MeasureSpec.AT_MOST -> {
                //最多，wrap_content
                width = SizeUtils.dp2px(400f)

                LogUtils.d("onMeasure parent wMode  MeasureSpec.AT_MOST ")

            }
            MeasureSpec.UNSPECIFIED -> {
                LogUtils.d("onMeasure parent wMode  MeasureSpec.UNSPECIFIED ")

            }

        }


        when(mHMode){
            MeasureSpec.EXACTLY -> {
                //match_parent或者具体的数值
                height = mHeight
                LogUtils.d("onMeasure parent hMode MeasureSpec.EXACTLY ")

            }
            MeasureSpec.AT_MOST -> {
                //最多，wrap_content
                height = SizeUtils.dp2px(400f)
                LogUtils.d("onMeasure parent hMode MeasureSpec.AT_MOST ")

            }
            MeasureSpec.UNSPECIFIED -> {
                LogUtils.d("onMeasure parent hMode MeasureSpec.UNSPECIFIED ")

            }

        }

        LogUtils.d("onMeasure width = $width , height= $height")
        setMeasuredDimension(width, height)

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
//                stopRotate()
//                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP -> {
                val partIndex = getPointPart(event.x, event.y)
                mList?.let {
                    mOnPartClickListener?.invoke(it[partIndex])
                }

            }
        }
        return true
    }

    private fun getPointPart(x: Float, y: Float): Int {
        var resIndex: Int = -1
        mPathList.forEachIndexed { index, path ->
            if (pointIsInPath(x, y, path)) {
                resIndex = index
            }
        }
        return resIndex
    }

    private fun pointIsInPath(x: Float, y: Float, path: Path): Boolean {
        val bounds = RectF()
        path.computeBounds(bounds, true)
        val region = Region()
        region.setPath(
                path,
                Region(
                        Rect(
                                bounds.left.toInt(),
                                bounds.top.toInt(),
                                bounds.right.toInt(),
                                bounds.bottom.toInt()
                        )
                )
        )

        return region.contains(x.toInt(), y.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtils.d("onSizeChanged w=$w,h=$h,oldw=$oldh,oldH=$oldh")
        LogUtils.d("onSizeChanged measuredHeight=$measuredHeight,measuredWidth=$measuredWidth")
        minParamer = Math.min(measuredHeight, measuredWidth)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(mList == null || sum == 0f){
            LogUtils.d("mList == null or sum == 0")
            return
        }
        canvas?.drawColor(Color.YELLOW)
        var rectF = RectF(0f, 0f, minParamer.toFloat(), minParamer.toFloat())
        var startDegree = 0f

        mCenterX = minParamer/2f
        mCenterY = mCenterX

        var left = (minParamer + SizeUtils.dp2px(10f)).toFloat()
        var right = left + rectW
        var top = 0f

        LogUtils.d("minParamer = $minParamer, left = $left, right = $right")


        mList?.forEachIndexed { index, bean ->
            mPaint.color = bean.color
            //方法1：绘制扇形区域，这种无法进行区域点击
            canvas?.drawArc(rectF, startDegree, bean.degree, true, mPaint)

            //方法2：利用绘制扇形区域
            mPathList[index].apply {
                reset()
                addArc(rectF,startDegree,bean.degree)
                lineTo(mCenterX,mCenterY)
                close()
            }
            canvas?.drawPath(mPathList[index],mPaint)


            //绘制文本
            drawText2(startDegree,bean,canvas)

            startDegree += bean.degree

            canvas?.drawRect(left, top, right, top + rectH, mPaint)
            //设置文字
            mPaint.color = Color.BLACK
            mPaint.textSize = 30f
            canvas?.drawText(bean.name + bean.amount + "%", right + SizeUtils.dp2px(10f), top + SizeUtils.dp2px(15f),
                    mPaint)

            top+= rectH
        }
    }

    /**
     * 方法1：绘制扇形上文字,这种方式不合适，出现的问题是文字方向永远是横着的
     * 效果不太好，可以弃用
     */
    private fun drawText(startDegree: Float, bean: CakeBean, canvas: Canvas?) {
        //计算文字位置角度
        val textAngle: Float = startDegree + bean.degree / 2
        val x = minParamer / 2 + (minParamer / 4 * Math.cos(textAngle * Math.PI / 180))
                .toFloat()
        //计算文字位置坐标
        val y = minParamer / 2 + (minParamer / 4 * Math.sin(textAngle * Math.PI / 180))
                .toFloat()
        canvas?.drawText(bean.name ?: "", x, y, mTextPaint)

        LogUtils.d("name = ${bean.name}, textAngle = $textAngle, x= $x, y= $y")
    }

    /**
     * 旋转画布，
     */
    private fun drawText2(startDegree: Float, bean: CakeBean, canvas: Canvas?) {
        //计算文字位置角度
        val textAngle: Float = startDegree + bean.degree / 2
        val x = (minParamer/2.0).toFloat() + SizeUtils.dp2px(50f)
        val y = (minParamer/2.0).toFloat()

        val centerX = (minParamer/2.0).toFloat()
        val centerY = (minParamer/2.0).toFloat()

        canvas?.rotate(textAngle,centerX,centerY)

        canvas?.drawText(bean.name ?: "",x , y, mTextPaint)
        canvas?.rotate(-textAngle,centerX,centerY)

        LogUtils.d("name = ${bean.name}, textAngle = $textAngle, x= $x, y= $y")
    }
}