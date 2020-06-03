package com.some.mvvmdemo.slideconflict

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.*
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.base.BaseFragment
import com.some.mvvmdemo.databinding.ActivitySlideConflictBinding
import java.util.ArrayList

/**
 * @author xiangxing
 */
class SlideConfictActivity:BaseActiviy() {

    var binding: ActivitySlideConflictBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_slide_conflict)

        val fragments = ArrayList<BaseFragment>()
        fragments.add(MessageFragment())
        fragments.add(NearbyFragment())
        fragments.add(SettingFragment())

        val adapter = TabFragmentPagerAdapter(supportFragmentManager, fragments)
        binding?.viewpager?.adapter = adapter

    }
}