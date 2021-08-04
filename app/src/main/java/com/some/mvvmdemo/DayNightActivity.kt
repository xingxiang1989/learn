package com.some.mvvmdemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityDaynightBinding
import com.some.mvvmdemo.util.DarkModeUtils

/**
 * @author xiangxing
 */
class DayNightActivity: AppCompatActivity() {

    private var TAG = "DayNightActivity"
    lateinit var mBinding: ActivityDaynightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtils.d(TAG,"onCreate")
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_daynight)

        mBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtils.d(TAG,"ischecked = $isChecked")
            if(isChecked){
                DarkModeUtils.applyNightMode(this)
            }else{
                DarkModeUtils.applyDayMode(this)
            }
            //加上这个会频繁的闪烁
//            recreate()
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG,"onResume")

    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG,"onPause")

    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG,"onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG,"onDestroy")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when(currentNightMode){
            Configuration.UI_MODE_NIGHT_YES ->{
                LogUtils.d(TAG,"暗黑")

            }
            Configuration.UI_MODE_NIGHT_NO ->{
                LogUtils.d(TAG,"白天")

            }
        }
    }
}