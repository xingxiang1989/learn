package com.some.mvvmdemo.view

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityCakeviewBinding
import com.some.mvvmdemo.entity.CakeBean

/**
 * @author xiangxing
 */
class CakeViewActivity: BaseActiviy() {

    lateinit  var binding: ActivityCakeviewBinding
    var list: MutableList<CakeBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cakeview)

        list.add(CakeBean("C", Color.RED,10.34f))
        list.add(CakeBean("Java", Color.BLUE,17.24f))
        list.add(CakeBean("Python", Color.YELLOW,6.3f))
        list.add(CakeBean("Android", getColor(R.color.color_9B44FD),20.69f))
        list.add(CakeBean("Linux", getColor(R.color.light_green),24.1f))
        list.add(CakeBean("c++", getColor(R.color.blue),13.79f))

        binding.cakeview.setCakeList(list)
    }
}