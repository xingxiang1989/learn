package com.some.changeskin

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * @author xiangxing
 *
 * 记录所有要换肤的控件
 *
 * 1.静态代码块的实现
 * 2.静态内部类
 */
class SkinAttribute {

    private val TAG = "SkinAttribute"
    private val mSkinViews = arrayListOf<SkinView>()

    companion object{
        val mAttributes = arrayListOf<String>()

        init {
            mAttributes.add("background")
            mAttributes.add("src")
            mAttributes.add("textColor")
            mAttributes.add("drawableStart")
            mAttributes.add("drawableTop")
            mAttributes.add("drawableEnd")
            mAttributes.add("drawableBottom")
        }


    }

    /**
     * 默认为静态内部类，与java不一样.java默认为非静态内部类
     * 记录当前这个view以及它所有的属性集合
     */
    class SkinView(val view: View, val skinPairs: List<SkinPair>){

        /**
         * 对一个View中的所有的属性进行修改
         */
        fun applySkin(){
            var drawableStart: Drawable?= null
            var drawableEnd: Drawable?= null
            var drawableTop: Drawable?= null
            var drawableBottom: Drawable?= null

            skinPairs.forEach {
                when(it.attributeName){
                    "background" -> {
                        val background = SkinResources.getInstance().getBackGround(it
                                .resId)
                        if (background is Int) {
                            view.setBackgroundColor(background)
                        } else {
                            view.background = background as Drawable
                        }
                    }
                    "src" -> {
                        val background = SkinResources.getInstance().getBackGround(it
                                .resId)
                        if (background is Int) {
                            (view as ImageView).setImageDrawable(ColorDrawable(background))
                        } else {
                            (view as ImageView).setImageDrawable(background as Drawable)
                        }
                    }
                    "textColor" -> {
                        (view as TextView).setTextColor(SkinResources.getInstance()
                                .getColorStateList(it.resId))
                    }
                    "drawableStart" -> {
                        drawableStart = SkinResources.getInstance().getDrawable(it
                                .resId) as Drawable
                    }
                    "drawableEnd" -> {
                        drawableEnd = SkinResources.getInstance().getDrawable(it
                                .resId) as Drawable
                    }
                    "drawableTop" -> {
                        drawableTop = SkinResources.getInstance().getDrawable(it
                                .resId) as Drawable
                    }
                    "drawableBottom" -> {
                        drawableBottom = SkinResources.getInstance().getDrawable(it
                                .resId) as Drawable
                    }
                }
            }

            if (null != drawableStart || null != drawableEnd || null != drawableTop || null !=
                    drawableBottom) {
                (view as TextView).setCompoundDrawablesWithIntrinsicBounds(drawableStart, drawableTop,
                        drawableEnd, drawableBottom)
            }
        }

        private fun applySkinViewSupport(){
            (view as? SkinViewSupport)?.applySkin()
        }

    }

    /**
     * 属性名
     * 对应的资源id。不同皮肤包中相同资源名称的ID是不一样的
     */
    data class SkinPair(val attributeName: String, val resId: Int)

    /**
     * 遍历view的属性，符合的话构成SkinView，并加入到mSKinViews中
     */
    fun look(view: View?, attrs: AttributeSet){
        val mSkinPairs = arrayListOf<SkinPair>()
        for(i in 0 until attrs.attributeCount){
            val attributeName = attrs.getAttributeName(i)
            if(mAttributes.contains(attributeName)){
                val attributeValue = attrs.getAttributeValue(i)
                Log.d(TAG, "attributeName = $attributeName, attributeValue=$attributeValue")
                //需要过滤部分情况
                // #
                // ?722727272
                // @722727272
                if(attributeValue.startsWith("#")){
                    continue
                }
                val resId = if (attributeValue.startsWith("?")) {
                    val attrId = attributeValue.substring(1).toInt()
                    SkinThemeUtils.getResId(view!!.context, intArrayOf(attrId))[0]
                } else {
                    attributeValue.substring(1).toInt()
                }
                val skinPair = SkinPair(attributeName,resId)
                mSkinPairs.add(skinPair)
            }
        }
        if(mSkinPairs.isNotEmpty() || view is SkinViewSupport){
            val skinView = SkinView(view!!,mSkinPairs)
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    fun applySkin(){
        mSkinViews.forEach {
            it.applySkin()
        }
    }

}