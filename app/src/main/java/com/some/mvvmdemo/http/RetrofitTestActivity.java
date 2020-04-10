package com.some.mvvmdemo.http;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityRetrofitTestBinding;

public class RetrofitTestActivity extends BaseActiviy {

    ActivityRetrofitTestBinding mBinding;
    RetrofitTestVM vm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit_test);
        vm = ViewModelProviders.of(this).get(RetrofitTestVM.class);
    }
}
