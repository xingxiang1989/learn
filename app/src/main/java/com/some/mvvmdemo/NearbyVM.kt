package com.some.mvvmdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.some.mvvmdemo.entity.Account

import java.nio.channels.MulticastChannel
import java.util.ArrayList
import java.util.Random

class NearbyVM : ViewModel() {

    var liveData = MutableLiveData<List<Account>>()
        internal set

    fun initData() {

        var mList = ArrayList<Account>()
        mList.add(Account("xiangxing1", 1))
        mList.add(Account("xiangxing2", 2))
        mList.add(Account("xiangxing3", 3))
        mList.add(Account("xiangxing4", 4))
        mList.add(Account("xiangxing5", 5))
        mList.add(Account("xiangxing6", 6))

        liveData.postValue(mList)
    }

    fun add() {

        val random = Random()
        val level = random.nextInt(300)

        var list : List<Account>?= liveData.value
        var mutableList = list?.toMutableList()
        mutableList?.add(0, Account("add $level", level))
        liveData.postValue(mutableList)
    }
}
