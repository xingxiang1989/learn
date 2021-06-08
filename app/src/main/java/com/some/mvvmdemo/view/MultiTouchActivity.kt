package com.some.mvvmdemo.view

import android.os.Bundle
import android.os.Environment
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityMultitouchBinding
import java.io.File
import java.io.FileInputStream

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
        val path = Environment.getExternalStorageDirectory().toString() + "/Pictures/earth.JPEG"
        LogUtils.d("path = $path")
        val inputStream = FileInputStream(File(path))
        binding.bigView.setImage(inputStream)
    }
}