package com.some.common.thread;

import android.util.Log;

/**
 * @author xiangxing
 */
public class FourThreadUtils {

    public static void startLog(){
        new Thread(new FourThreadRunnable(0, new FourThreadRunnable.CallBack() {
            @Override
            public void log(int value) {
                Log.d("xiangxingtest","C");
            }
        })).start();

        new Thread(new FourThreadRunnable(1, new FourThreadRunnable.CallBack() {
            @Override
            public void log(int value) {
                Log.d("xiangxingtest","B");
            }
        })).start();

        new Thread(new FourThreadRunnable(2, new FourThreadRunnable.CallBack() {
            @Override
            public void log(int value) {
                Log.d("xiangxingtest","A");
            }
        })).start();

        new Thread(new FourThreadRunnable(3, new FourThreadRunnable.CallBack() {
            @Override
            public void log(int value) {
                Log.d("xiangxingtest",value + "");
            }
        })).start();
    }
}
