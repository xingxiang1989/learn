package com.some.mvvmdemo.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.some.mvvmdemo.R;
import com.some.mvvmdemo.databinding.ActivityMvvmBinding;

public class MvvmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_mvvm);

        MvvmViewModel viewModel = new MvvmViewModel(getApplication(),binding);
        binding.setViewModel(viewModel);
    }
}
