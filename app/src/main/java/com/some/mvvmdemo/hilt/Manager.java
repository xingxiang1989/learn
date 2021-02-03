package com.some.mvvmdemo.hilt;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author xiangxing
 */
public class Manager implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateX(){
        LogUtils.d("onCreateX");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeX(){
        LogUtils.d("onResumeX");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPauseX(){
        LogUtils.d("onPauseX");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyX(){
        LogUtils.d("onDestroyX");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAnyX(){
        LogUtils.d("onAnyX");
    }
}
