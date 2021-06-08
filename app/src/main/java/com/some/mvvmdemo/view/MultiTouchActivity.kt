package com.some.mvvmdemo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityMultitouchBinding

/**
 * Created by xiangxing5 on 2021/6/7.
 * Describe:
 */
class MultiTouchActivity: BaseActiviy() {

    private lateinit var binding: ActivityMultitouchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_multitouch)

//        val drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
//        binding.bigView.setImage(FormatTools.getInstance().Drawable2InputStream(drawable))

        binding.bigView.setImage(null)
    }
}