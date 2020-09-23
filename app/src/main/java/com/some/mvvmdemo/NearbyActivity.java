package com.some.mvvmdemo;

import android.app.Activity;
import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.service.TestServiceOne;

public class NearbyActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(android.R.id.content,new NearbyFragment());

    }

}
