package com.some.mvvmdemo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.some.mvvmdemo.R
import com.some.mvvmdemo.base.BaseActiviy
import com.some.mvvmdemo.databinding.ActivityViewgroupBinding

/**
 * @author xiangxing
 */
class FiveRingsActivity: BaseActiviy() {

    lateinit  var binding: ActivityViewgroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewgroup)

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