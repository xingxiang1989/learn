package com.some.mvvmdemo.testkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityTestKotlinBinding

/**
 * @author xiangxing
 */
class TestKotlinActivity : BaseActiviy(){

    var entity : Entity?= null

    lateinit var mBinding: ActivityTestKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_kotlin)

        mBinding.tv.setOnClickListener { v -> Log.d("11","onclick") }
        mBinding.tv.setOnClickListener { v: View? ->  }


    }
}