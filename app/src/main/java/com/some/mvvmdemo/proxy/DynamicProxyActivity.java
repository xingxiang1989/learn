package com.some.mvvmdemo.proxy;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiangxing
 */
public class DynamicProxyActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        testInnovationHandler();
    }

    /**
     * 动态代理
     */
    private void testInnovationHandler(){
        final IHello hello = new RealSubject();

        InvocationHandler invocationHandler = (proxy, method, args) -> {
            if(method.getName().equals("sayHello")){
                LogUtils.d("sayHello before--> ");
                int argsLength = args.length;
                LogUtils.d("sayHello before args =" + args[argsLength - 1]);

            }else if(method.getName().equals("sayBye")){
                LogUtils.d("sayBye before--> ");
            }
            method.invoke(hello,args);
            return null;
        };

        IHello proxySubject =
                (IHello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
                        new Class<?>[]{IHello.class},invocationHandler);

        proxySubject.sayHello("hello, xiaoming");
        proxySubject.sayBye("Bye bye", 5);

        LogUtils.d("proxySubject = " + proxySubject.getClass().getName());
        LogUtils.d("proxySubject getSuperclass = " + proxySubject.getClass().getSuperclass().getName());

    }
}
