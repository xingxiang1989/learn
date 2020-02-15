package com.some.mvvmdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.some.mvvmdemo.entity.Account;

import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NearbyVM extends ViewModel {

    MutableLiveData<List<Account>> mLiveData = new MutableLiveData<>();

    public void initData(){

        List<Account> mList = new ArrayList<>();
        mList.add(new Account("xiangxing1",1));
        mList.add(new Account("xiangxing2",2));
        mList.add(new Account("xiangxing3",3));
        mList.add(new Account("xiangxing4",4));
        mList.add(new Account("xiangxing5",5));
        mList.add(new Account("xiangxing6",6));

        mLiveData.postValue(mList);
    }

    public void add(){

        Random random = new Random();
        int level = random.nextInt(300);
        List<Account> mList = mLiveData.getValue();
        mList.add(0,new Account("add " + level , level));
        mLiveData.postValue(mList);
    }

    public MutableLiveData<List<Account>> getLiveData() {
        return mLiveData;
    }
}
