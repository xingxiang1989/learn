package com.some.mvvmdemo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityFiveRingBinding

/**
 * @author xiangxing
 */
class FiveRingsActivity: BaseActiviy() {

    lateinit  var binding: ActivityFiveRingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_five_ring)
    }
}