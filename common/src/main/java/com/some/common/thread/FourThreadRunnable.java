package com.some.common.thread;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kotlin.jvm.Volatile;

/**
 * @author xiangxing
 */
public class FourThreadRunnable implements Runnable{

    private static int[] mArray= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    /**
     * 4个线程共用了同一个lock锁，这个很好理解，四个线程都是互斥的，也必须是static，因为在
     * Runnable中初始化的。
     */
    private static ReentrantLock mLock = new ReentrantLock();
    private static Condition mCondition = mLock.newCondition();
    private int mFlag;
    private CallBack mCallBack;
    private static volatile int mCurrentIndex = 0;

    public FourThreadRunnable(int flag , CallBack callBack){
        mFlag = flag;
        mCallBack = callBack;
    }

    private int checkFlag(int value){
        if(value % 3 == 0 && value % 5 == 0){
            return 0;
        }else if(value % 5 == 0){
            return 1;
        }else if(value % 3 == 0){
            return 2;
        }
        return 3;
    }

    @Override
    public void run() {
        while(true){
            mLock.lock();
            try {
                //这里必须要加循环。用于check flag，标记哪个需要阻塞
                while(mCurrentIndex < mArray.length && checkFlag(mArray[mCurrentIndex]) % 4 != mFlag){
                    Log.e("xiangxingtest",
                            "当前flag =" + mFlag + " 需要await" + " value =" + mArray[mCurrentIndex]);
                    mCondition.await();
                    Log.e("xiangxingtest","当前flag =" + mFlag + " 等待完毕");
                }
                if(mCurrentIndex < mArray.length){
                    if(mCallBack != null){
                        mCallBack.log(mArray[mCurrentIndex]);
                    }
                    mCurrentIndex++;
                    mCondition.signalAll();
                    Log.e("xiangxingtest","唤醒全部等待队列");
                }else {
                    Log.e("xiangxingtest","线程结束-- end");
                    return;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mLock.unlock();
            }
        }
    }


    interface  CallBack{
        public void log(int value);
    }

}
