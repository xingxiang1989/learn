package com.some.mvvmdemo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ToastUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityTimelineBinding
import com.some.mvvmdemo.widget.timeline.DateBean

/**
 * @author xiangxing
 */
class TimeLineActivity:BaseActiviy() {

    private lateinit var binding: ActivityTimelineBinding
    private var mList = ArrayList<DateBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline)

        for(index in 1..20){
            val bean = DateBean(index)
            mList.add(bean)
        }
        binding.timeLine.bindData(mList)

    }
}