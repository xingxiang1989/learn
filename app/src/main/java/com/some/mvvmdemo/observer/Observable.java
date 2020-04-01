package com.some.mvvmdemo.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

    List<Observer<T>> mObservers = new ArrayList<>();

    public void registerObserver(Observer<T> observer){
        mObservers.add(observer);
    }

    public void clearObservers(){
        mObservers.clear();
    }

    public abstract void publishData(T t);
}
