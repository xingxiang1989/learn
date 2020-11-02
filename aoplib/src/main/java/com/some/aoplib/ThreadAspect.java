package com.some.aoplib;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xiangxing
 */
@Aspect
public class ThreadAspect {


    @Pointcut("execution(@com.some.aoplib.Async void *(..))")
    public void method() {}

    @Around("method()")
    public void doAsync(final ProceedingJoinPoint joinPoint){
        Log.d("ThreadAspect","doAsync");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    //执行原始的方法
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }


    @Around("execution(@com.some.aoplib.Main void *(..))")
    public void doMain(final ProceedingJoinPoint joinPoint){
        Log.d("ThreadAspect","doMain");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    //执行原始的方法
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
