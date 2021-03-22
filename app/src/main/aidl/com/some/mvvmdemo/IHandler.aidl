// IHandler.aidl
package com.some.mvvmdemo;
import com.some.mvvmdemo.OnReceiveMessageListener;
// Declare any non-default types here with import statements

interface IHandler {

   void setOnReceiveMessageListener(in OnReceiveMessageListener listener);
}