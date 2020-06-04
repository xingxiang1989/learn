package com.some.mvvmdemo.viewpager2

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.some.mvvmdemo.*
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.base.BaseFragment
import com.some.mvvmdemo.databinding.ActivityViewpager2Binding
import java.util.ArrayList

/**
 * @author xiangxing
 */
class ViewPager2Activity: BaseActiviy() {

    lateinit var binding: ActivityViewpager2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewpager2)

        val fragments = ArrayList<BaseFragment>()
        fragments.add(MessageFragment())
        fragments.add(NearbyFragment())
        fragments.add(SettingFragment())
//        fragments.add(MessageFragment())
//        fragments.add(NearbyFragment())
//        fragments.add(SettingFragment())

        binding.viewager.orientation = ViewPager2.ORIENTATION_VERTICAL

        var adapter = ViewPagerFragmentAdapter(this, fragments.toMutableList())
        binding.viewager.adapter = adapter

    }

}