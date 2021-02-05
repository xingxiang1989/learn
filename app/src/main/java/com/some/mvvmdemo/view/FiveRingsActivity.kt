package com.some.mvvmdemo.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityFiveRingBinding
import com.some.mvvmdemo.service.TestServiceOne
import com.some.room.db.AppDataBase
import com.some.room.entity.Car

/**
 * @author xiangxing
 */
class FiveRingsActivity: BaseActiviy() {

    lateinit  var binding: ActivityFiveRingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtils.d("xiangxingtest taskid = $taskId")


        binding = DataBindingUtil.setContentView(this, R.layout.activity_five_ring)

        binding.btn.setOnClickListener{
            this.startActivity(Intent(this, CakeViewActivity::class.java))
        }

        val intent = Intent(this, TestServiceOne::class.java)
        this.startService(intent)

        Handler().postDelayed({
            this@FiveRingsActivity.bindService(intent, serviceConn, Context.BIND_AUTO_CREATE)
        },
                2000)

        val dao = AppDataBase.getInstance(Utils.getApp()).getCarDao()
        val car = Car(1,"SUV","url","url","url","#ffffff")
        val car2 = Car(2,"跑车","url","url","url","#ffffff")
        dao.insert(car)
        dao.insert(car2)

    }

    private val serviceConn = object: ServiceConnection{
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
        this.stopService(Intent(this, TestServiceOne::class.java))
        this.unbindService(serviceConn)
    }
}