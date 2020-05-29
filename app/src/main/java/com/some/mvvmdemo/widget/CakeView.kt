package com.some.mvvmdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.entity.CakeBean
import com.some.mvvmdemo.testkotlin.b

/**
 * @author xiangxing
 * 参考文章： https://www.jianshu.com/p/dff84787d3a5
 */
class CakeView: View {

    var mPaint: Paint
    var mList: MutableList<CakeBean>?= null
    var minParamer: Int =0
    var sum = 0f

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        mPaint = Paint()
        mPaint.isAntiAlias = true

    }

    fun setCakeList(list: MutableList<CakeBean>){


        for(bean in list){
            sum += bean.amount
        }
        LogUtils.d("sum = $sum")
        for(bean in list){
            bean.degree = bean.amount/sum
        }
        mList = list
        invalidate()
    }

    /**
     * 为什么要重写，这是因为要考虑到父布局的宽，高。我们依赖父布局的宽高绘制，
     * 这样才能确定绘制在哪里
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        LogUtils.d("onMeasure")
        var mWMode = MeasureSpec.getMode(widthMeasureSpec)
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        var mHMode = MeasureSpec.getMode(heightMeasureSpec)
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0

        when(mWMode){
            MeasureSpec.EXACTLY -> {
                //match_parent或者具体的数值
                width = mWidth
            }
            MeasureSpec.AT_MOST -> {
                //最多，wrap_content
                width = SizeUtils.dp2px(400f)
            }
            MeasureSpec.UNSPECIFIED -> {

            }

        }


        when(mHMode){
            MeasureSpec.EXACTLY -> {
                //match_parent或者具体的数值
                height = mHeight
            }
            MeasureSpec.AT_MOST -> {
                //最多，wrap_content
                height = SizeUtils.dp2px(600f)
            }
            MeasureSpec.UNSPECIFIED -> {

            }

        }

        LogUtils.d("onMeasure width = $width , height= $height")
        setMeasuredDimension(width,height)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtils.d("onSizeChanged w=$w,h=$h,oldw=$oldh,oldH=$oldh")
        LogUtils.d("onSizeChanged measuredHeight=$measuredHeight,measuredWidth=$measuredWidth")
        minParamer = Math.min(measuredHeight,measuredWidth)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(mList == null || sum == 0f){

        }
    }
}