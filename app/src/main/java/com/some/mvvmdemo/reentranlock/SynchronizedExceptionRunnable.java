package com.some.mvvmdemo.reentranlock;

import android.util.Log;

public class SynchronizedExceptionRunnable implements Runnable{

    private static final String TAG = SynchronizedExceptionRunnable.class.getSimpleName();
    private volatile boolean flag = true;
    @Override
    public void run() {

        synchronized (this){
            if(flag){
                methodB();
            }else{
                methodA();
            }
        }
    }

    private synchronized void methodB(){
        Log.d(TAG, "methodB will throw a exception " + Thread.currentThread().getName());

        flag = false;

//        int a = 1/0;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "methodB end  " + Thread.currentThread().getName());


    }

    private synchronized void methodA(){
        Log.d(TAG, "methodA " + Thread.currentThread().getName());
    }
}
