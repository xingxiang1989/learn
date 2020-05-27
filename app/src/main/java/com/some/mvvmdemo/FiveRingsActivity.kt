package com.some.mvvmdemo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityViewgroupBinding

/**
 * @author xiangxing
 */
class FiveRingsActivity: BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityViewgroupBinding>(this,R.layout.activity_viewgroup)
    }
}