package com.some.changeskin

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat

/**
 * 这些对象都放在内存中
 * @author xiangxing
 */
class SkinResources private constructor(context: Context){

    var mContext: Context = context
    private val TAG = "SkinResources"
    private var mSkinPkgName: String? = null
    private var isDefaultSkin = true
    // app原始的resource
    private var mAppResources: Resources? = null
    // 皮肤包的resource
    private var mSkinResources: Resources? = null

    init {
        mAppResources = context.resources
    }

    companion object{
        @Volatile private var instance: SkinResources?= null
        fun init(context: Context): SkinResources{
            return instance?: synchronized(this){
                instance?: SkinResources(context).also {
                    instance = it
                }
            }
        }
        //这种写法一般不是很通用，要么按照skinManger来写，要么按照腾讯文档来写，在getInstance中传递参数，然后
        //再进行对象的单例实例化
        fun getInstance(): SkinResources {
            return instance!!
        }
    }



    fun reset() {
        mSkinResources = null
        mSkinPkgName = ""
        isDefaultSkin = true
    }

    fun applySkin(resources: Resources, pkgName: String) {
        mSkinResources = resources
        mSkinPkgName = pkgName
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null
    }

    /**
     * 输入主APP的ID，到皮肤APK文件中去找到对应ID的颜色值
     */
    fun getColor(resId: Int): Int{
        if(isDefaultSkin){
            return mAppResources!!.getColor(resId)
        }
        val skinResId = getIdentifier(resId)

        return if (skinResId == 0) {
            mAppResources!!.getColor(resId)
        } else mSkinResources!!.getColor(skinResId)
    }

    fun getDrawable(resId: Int): Any {
        if(isDefaultSkin){
            return mAppResources!!.getDrawable(resId) as Any
        }
        val skinResId = getIdentifier(resId)

        return if (skinResId == 0) {
            mAppResources!!.getDrawable(resId) as Any
        } else mSkinResources!!.getDrawable(skinResId) as Any
    }

    /**
     * 可能是Color 也可能是drawable
     */
    fun getBackGround(resId: Int): Any {
        val type = mAppResources!!.getResourceTypeName(resId)
        return when(type){
            "color" ->{
                getColor(resId)
            }
            else ->{
                getDrawable(resId)
            }
        }
    }

    fun getColorStateList(resId: Int): ColorStateList? {
        if(isDefaultSkin){
            return ContextCompat.getColorStateList(mContext!!,resId)
        }
        val skinResId = getIdentifier(resId)

        return if (skinResId == 0) {
            mAppResources!!.getColorStateList(resId)
        } else mSkinResources!!.getColorStateList(skinResId)
    }

    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    private fun getIdentifier(resId: Int): Int{
        if(isDefaultSkin){
            return resId
        }
        val resName = mAppResources!!.getResourceEntryName(resId)
        val resType = mAppResources!!.getResourceTypeName(resId)

        Log.d(TAG, "getIdentifier app resId = $resId, resName= $resName, resType = $resType")

        val skinId = mSkinResources!!.getIdentifier(resName, resType, mSkinPkgName)

        Log.d(TAG, "getIdentifier skinId = $skinId")

        return  skinId
    }
}