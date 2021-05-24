package com.some.mvvmdemo.widget.timeline

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.Utils

/**
 * @author xiangxing
 * 时间线练习
 */
class TimeLineView: View {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val ITEM_WIDTH = SizeUtils.dp2px(25f)
    private var mArrays = ArrayList<DateBean>()

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)




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
    }

    fun bindData(datas: ArrayList<DateBean>){
        mArrays = datas
        invalidate()
    }
}