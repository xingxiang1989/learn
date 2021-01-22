package com.some.common.thread;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangxing
 */
public class Consumer {

    private static final String TAG = "Consumer";
    private static ReentrantLock mLock = new ReentrantLock();
    private static Condition mCondition = mLock.newCondition();
    private static volatile LinkedList<String> mList = new LinkedList<>();
    private static volatile int index = 0;
    public static Consumer getInstance(){
        return new Consumer();
    }
    public void start(){
        ProduceThread produceThread = new ProduceThread();
        produceThread.start();
        ConsumerThread consumerThread = new ConsumerThread();
        consumerThread.start();
    }

    static class ConsumerThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                try{
                    mLock.lock();
                    if(mList.size() == 0){
                        Log.e(TAG,"消费者，list.size == 0, 等待中 ");
                        mCondition.await();
                    }
                    Thread.sleep(200);
                    String s = mList.removeFirst();
                    Log.e(TAG,"消费者 消费" + s + " 每次消耗后就通知生产者");
                    mCondition.signal();
                }catch (InterruptedException e){

                }catch (Exception e){
                }finally {
                    Log.e(TAG,"消费者 释放锁 ");
                    mLock.unlock();
                }
            }
        }
    }

    static class ProduceThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                try{
                    mLock.lock();
                    mList.add(" 产品id = " + index);
                    Log.e(TAG,"生产者，生产数据" + " 产品id = " + index);
                    index++;
                    while(mList.size() >= 2){
                        Log.e(TAG,"生产者，list.size >= 10, 休息中 ");
                        mCondition.await();
                    }
                    Thread.sleep(1000);
                    mCondition.signal();
                    Log.e(TAG,"生产者 通知消费者 ");
                }catch (Exception e){

                }finally {
                    Log.e(TAG,"生产者，释放锁 ");
                    mLock.unlock();
                }
            }
        }
    }
}
