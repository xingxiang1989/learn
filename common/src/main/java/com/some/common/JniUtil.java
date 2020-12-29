package com.some.common;

import java.util.TreeMap;

/**
 * @author xiangxing
 */
public class JniUtil {

    static{
        System.loadLibrary("jni-util");
    }

    public native String getFlSign();

    public native String getFlSignId();

    public native String getTimeStamp();

    public native String getKey1();

    public native String getVaule1();


}
