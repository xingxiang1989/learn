package com.some.mvvmdemo

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityViewgroupBinding
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/**
 * @author xiangxing
 */
class FiveRingsActivity: BaseActiviy() {

    lateinit  var binding: ActivityViewgroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout
                .activity_viewgroup)

//        var degree = 0
//        Observable.interval(100,TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { Consumer<Long> {
//                        LogUtils.d("1111")
//                        degree += 10
//                        binding.roateRing.setDegreeAngele(degree)
//                    }
//                    io.reactivex.functions.Consumer<Throwable> { t -> LogUtils.d("t=${t.toString()}") }
//                }
    }
}