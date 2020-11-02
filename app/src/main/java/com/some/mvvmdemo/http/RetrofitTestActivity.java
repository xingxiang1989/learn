package com.some.mvvmdemo.http;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.some.aoplib.Async;
import com.some.aoplib.Main;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityRetrofitTestBinding;
import com.some.mvvmdemo.threadjoin.JoinDemo;

public class RetrofitTestActivity extends BaseActiviy {

    ActivityRetrofitTestBinding mBinding;
    RetrofitTestVM vm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.d("RetrofitTestActivity");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit_test);
        vm = ViewModelProviders.of(this).get(RetrofitTestVM.class);

//        vm.request();

        JoinDemo joinDemo1 = new JoinDemo("小明");
        JoinDemo joinDemo2 = new JoinDemo("小星");
        joinDemo1.start();
        try{
            /**join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
             程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
             所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
             */
            joinDemo1.join();
        }catch (InterruptedException e){

        }

        joinDemo2.start();
    }

    @Async
    public void readFile() {
        try{
            Thread.sleep(1000L);
        }catch (Exception e){

        }
    }

    @Main
    public void showResult(){
        ToastUtils.showShort("hello Aspectj");
    }
}
