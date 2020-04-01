package com.some.mvvmdemo.observer;

public interface Observer<T> {
    void receiverEvent(T t);
}
