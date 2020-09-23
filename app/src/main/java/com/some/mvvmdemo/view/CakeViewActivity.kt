package com.some.mvvmdemo.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityCakeviewBinding
import com.some.mvvmdemo.entity.CakeBean
import com.some.mvvmdemo.service.TestServiceOne

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

        val intent = Intent(this, TestServiceOne::class.java)
        this.bindService(intent,serviceConn, Context.BIND_AUTO_CREATE)
    }

    private val serviceConn = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.d("TestServiceOne onServiceDisconnected")

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtils.d("TestServiceOne onServiceConnected")
            val testServiceOne = (service as TestServiceOne.MyBinder).getService()
            testServiceOne.print()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        this.unbindService(serviceConn)
    }
}