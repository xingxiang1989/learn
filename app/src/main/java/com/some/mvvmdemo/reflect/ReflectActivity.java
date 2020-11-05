package com.some.mvvmdemo.reflect;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.entity.Account;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author xiangxing
 */
public class ReflectActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

        try {
            Class classObj = Class.forName("com.some.mvvmdemo.entity.Account");
            Constructor constructor = classObj.getConstructor(String.class,int.class);
            //强制性转换用于演示
            String log = ((Account)constructor.newInstance("111",222)).toString();
            LogUtils.d("log = " + log);

            Object object = constructor.newInstance("111",222);

            classObj.getMethod("setLevel",new Class[]{int.class}).invoke(object,102);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
