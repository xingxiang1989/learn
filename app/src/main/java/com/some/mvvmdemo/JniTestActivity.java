package com.some.mvvmdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

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

                tv.setText(builder.toString());
            }
        });
    }
}
