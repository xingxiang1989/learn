// OnReceiveMessageListener.aidl
package com.some.mvvmdemo;
import com.some.mvvmdemo.entity.Message;

// Declare any non-default types here with import statements

interface OnReceiveMessageListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean onReceived(in Message var1,int left, boolean offline, boolean hasMsg, int cmdLeft);
}