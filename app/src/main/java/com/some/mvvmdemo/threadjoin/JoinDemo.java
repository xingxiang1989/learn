package com.some.mvvmdemo.threadjoin;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author xiangxing
 */
public class JoinDemo extends Thread{

    String name;
    public JoinDemo(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            LogUtils.d(" name =" + name + " i = " + i);
        }
    }
}
