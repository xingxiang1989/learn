package com.some.mvvmdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityAlgorithmBinding;

import java.util.Base64;

/**
 * @author xiangxing
 */
public class AlgorithmActivity extends BaseActiviy {

    private String text = "qianyang123";
    private ActivityAlgorithmBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_algorithm);

        Base64

    }
}
