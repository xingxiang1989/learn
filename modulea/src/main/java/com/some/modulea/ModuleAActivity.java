package com.some.modulea;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.some.common.arouter.RouterUrl;

@Route(path = "/moduleA/A")
public class ModuleAActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulea);
    }
}
