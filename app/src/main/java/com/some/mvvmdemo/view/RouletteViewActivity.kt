package com.some.mvvmdemo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ToastUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityRouletteBinding

/**
 * @author xiangxing
 */
class RouletteViewActivity: BaseActiviy() {

    private lateinit var binding: ActivityRouletteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_roulette)

        binding.activityRouletteRv.setOnPartClickListener {
            when (it) {
                -1 -> {
                    ToastUtils.showShort("未点击扇形区")
                }
                else -> {
                    ToastUtils.showShort("扇形区索引=$it")
                }
            }
        }

        binding.activityRouletteRv.startRotate()

    }
}