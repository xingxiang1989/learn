package com.some.mvvmdemo.observer;


public abstract class PersonBean<T> implements Observer<T>{

    @Override
    public void receiverEvent(T t) {
         dealWithEvent(t);
    }

    /**
     * 交给用户去处理事件
     */
    public abstract void dealWithEvent(T t);
}
