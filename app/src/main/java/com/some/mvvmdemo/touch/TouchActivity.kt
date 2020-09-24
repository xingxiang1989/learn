package com.some.mvvmdemo.touch

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityTouchBinding

/**
 * @author xiangxing
 */
class TouchActivity: BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         DataBindingUtil.setContentView<ActivityTouchBinding>(this, R.layout.activity_touch)
    }
}