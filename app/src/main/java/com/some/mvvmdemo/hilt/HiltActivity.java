package com.some.mvvmdemo.hilt;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * @author xiangxing
 */
public class HiltActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilt);

        getLifecycle().addObserver(new Manager());
    }
}
