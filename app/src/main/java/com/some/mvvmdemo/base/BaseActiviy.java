package com.some.mvvmdemo.base;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BaseActiviy extends FragmentActivity {

    public void addFragment(int resid , Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(resid,fragment,fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }
}
