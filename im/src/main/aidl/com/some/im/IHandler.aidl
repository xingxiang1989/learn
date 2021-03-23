// IHandler.aidl
package com.some.im;
import com.some.im.OnReceiveMessageListener;
// Declare any non-default types here with import statements

interface IHandler {

   void setOnReceiveMessageListener(in OnReceiveMessageListener listener);
}