package com.some.mvvmdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.some.common.JniUtil;
import com.some.mvvmdemo.base.BaseActiviy;

import java.util.TreeMap;

/**
 * @author xiangxing
 */
public class JniTestActivity extends BaseActiviy {


    //本地私钥 需要加固保密
    private static TreeMap<String, String> localKeyPairs = new TreeMap<>();

    private static JniUtil jniUtil;

    private int[] array = new int[]{1,2,3,4,5};

    static {
        jniUtil = new JniUtil();
        localKeyPairs.put(jniUtil.getKey1(), jniUtil.getVaule1());
        localKeyPairs.put("1002", "3896AE7748DBB2C55A27B3C4D1560E1E");
        localKeyPairs.put("1003", "4745CE309142087D451F5DF57D5EDF67");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        final TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder builder = new StringBuilder();
                builder.append("key =" + localKeyPairs.firstKey());
                builder.append("\n");

                builder.append("value =" + localKeyPairs.get(localKeyPairs.firstKey()));
                builder.append("\n");

                builder.append(jniUtil.getFlSign());
                builder.append("\n");

                builder.append(jniUtil.getFlSignId());
                builder.append("\n");

                builder.append(jniUtil.getTimeStamp());
                builder.append("\n");

//                builder.append("sum = " + jniUtil.sumArray(array));

                tv.setText(builder.toString());
            }
        });

        long M = 1024 * 1024;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //虚拟机java堆大小的上限，分配对象时突破这个大小就会OOM
        int memory = activityManager.getMemoryClass();
        //manifest中设置largeheap=true时虚拟机java堆的上限
        int largeMemory = activityManager.getLargeMemoryClass();
        LogUtils.d("java heap memory = " + memory + " M");
        LogUtils.d("java heap max largeMemory = " + largeMemory+ " M");

        //当前虚拟机实例的内存使用上限，为上述两者之一(getMemoryClass, getLargeMemoryClass())
        long maxMemory = Runtime.getRuntime().maxMemory() / M;
        //当前已经申请的内存，包括已经使用的和还没有使用的
        long totalMemory = Runtime.getRuntime().totalMemory() / M;
        // 上一条中已经申请但是尚未使用的那部分。那么已经申请并且正在使用的部分used=totalMemory() - freeMemory()
        long freeMemory = Runtime.getRuntime().freeMemory() / M ;
        LogUtils.d("maxMemory = " + maxMemory + " M" + " totalMemory = " + totalMemory+ " M"
         + " freeMemory = "+ freeMemory + " M");

         ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
         //设备总内存
         long totalMem = memoryInfo.totalMem;
         //设备当前可用内存
         long availMem = memoryInfo.availMem;
        LogUtils.d("totalMem = " + totalMem + " availMem = " + availMem);
    }
}
