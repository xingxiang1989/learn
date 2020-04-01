package com.some.mvvmdemo.observer;


public abstract class Person implements Observer<String>{

    @Override
    public void receiverEvent(String s) {
         dealWithEvent(s);
    }

    /**
     * 交给用户去处理事件
     */
    public abstract void dealWithEvent(String s);
}
