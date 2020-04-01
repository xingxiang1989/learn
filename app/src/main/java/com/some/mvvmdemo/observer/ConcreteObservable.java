package com.some.mvvmdemo.observer;

public class ConcreteObservable<T> extends Observable<T>{

    /**
     * 这里抽象出来，因为发送的时候可以根据observer不同的类型来发
     * @param t
     */
    @Override
    public void publishData(T t) {
        for(Observer<T> observer: mObservers){
            if(observer instanceof Person){
                observer.receiverEvent(t);
            }
        }
    }
}
