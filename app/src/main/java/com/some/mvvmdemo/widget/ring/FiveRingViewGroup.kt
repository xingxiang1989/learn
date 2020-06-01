package com.some.mvvmdemo.widget.ring

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * @author xiangxing
 * 奥运五环
 */
class FiveRingViewGroup: ViewGroup {

    var mWidth = SizeUtils.dp2px(50f)

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super
    (context, attrs, defStyleAttr){

        var params = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        for (i in 1..5){
            addView(RingView(context),params)
        }
        setWillNotDraw(false)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtils.d("onMeasure")
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var count = childCount
        var tWidth = 0
        var tHeight = 0
        var bWidth = 0
        var bHeight = 0
        for(i in 0 until count){
            var childView = getChildAt(i)
            childView.measure(widthMeasureSpec,heightMeasureSpec)
            if(i < 3){
                tWidth += childView.measuredWidth + childView.marginStart + childView.marginEnd
                tHeight = childView.measuredHeight
            }else{
                bWidth += childView.measuredWidth + childView.marginStart + childView.marginEnd
                bHeight = tHeight/2 + childView.measuredHeight
            }

        }

        var totalWidth = Math.max(tWidth,bWidth)
        var totalHeight = Math.max(tHeight,bHeight)

        LogUtils.d("onMeasure totalWidth=$totalWidth , totalHeight=$totalHeight")

        var finalWidth = 0
        var finalHeight = 0

        if(widthMode == MeasureSpec.AT_MOST){
            finalWidth = totalWidth
        }else{
            finalWidth = widthSize
        }

        if(heightMode == MeasureSpec.AT_MOST){
            finalHeight = totalHeight
        }else{
            finalHeight = heightSize
        }

        LogUtils.d("onMeasure finalWidth=$finalWidth , finalHeight=$finalHeight")


        setMeasuredDimension(finalWidth,finalHeight)


    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        LogUtils.d("onLayout")
        var count = childCount
        var top = 0
        var left = 0
        var bottom = 0
        for(i in 0 until count){
            var childView = getChildAt(i)
            if(i < 3){
                top = 0
                bottom = top + childView.measuredHeight
            }else{
                top = childView.measuredHeight/2
                bottom = top + childView.measuredHeight
            }
            if(i == 3){
                left = childView.measuredWidth/2
            }

            childView.layout(left,top,left + childView.measuredWidth,bottom)

            left += childView.measuredWidth + SizeUtils.dp2px(10f)
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        LogUtils.d("onDraw")
        canvas?.drawColor(Color.YELLOW)
    }
}