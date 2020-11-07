package com.some.mvvmdemo.reflect;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.entity.Account;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangxing
 */
public class ReflectActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

//        try {
//            Class classObj = Class.forName("com.some.mvvmdemo.entity.Account");
//            Constructor constructor = classObj.getConstructor(String.class,int.class);
//            //强制性转换用于演示
//            String log = ((Account)constructor.newInstance("111",222)).toString();
//            LogUtils.d("log = " + log);
//
//            Object object = constructor.newInstance("111",222);
//
//            classObj.getMethod("setLevel",new Class[]{int.class}).invoke(object,102);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        byte[] cacheData = new byte[100 * 1024 * 1024];
        //软引用，1.没有强引用，2.内存不足才会回收
        SoftReference<byte[]> softReference = new SoftReference<>(cacheData);
        cacheData = null;

        LogUtils.d("gc前 cacheData = " + cacheData);
        LogUtils.d("gc前 softReference.get() = " + softReference.get());

        System.gc();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.d("gc后 cacheData = " + cacheData);
        LogUtils.d("gc后 softReference.get() = " + softReference.get());

        byte[] allcote = new byte[400 * 1024 * 1024];

        LogUtils.d("分配内存后， cacheData = " + cacheData);
        LogUtils.d("分配内存后， softReference.get() = " + softReference.get());

    }
}
