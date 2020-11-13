package com.some.mvvmdemo.inflate;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityInflaterBinding;

/**
 * @author xiangxing
 */
public class LayoutInflaterActivity extends BaseActiviy {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                //注意理解，xml中的每个控件都会走这个方法，都会显示出当前parent，以及自己控件
                LogUtils.d( "parent:" + parent + ",name = " + name);
//                int n = attrs.getAttributeCount();
//                for (int i = 0; i < n; i++) {
//                    LogUtils.e( attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
//                }

                //这种方式不好，还是应该沿用系统的创建方式，让系统来创建
//                if(TextUtils.equals(name,"TextView")){
//                    Button button = new Button(context);
//                    button.setText("我是被替换的btn");
//                    return button;
//                }

                if(name.equals("TextView") || name.equals("ImageView")){
                    name = "Button";
                }

//                //'我们只是更换了参数，但最终实例化View的逻辑还是交给了AppCompatDelegateImpl'
//                AppCompatDelegate delegate = AppCompatDelegateImpl();
//                View view = delegate.createView(parent, name, context, attrs);
//                return view;

                return null;

            }

            @Nullable
            @Override
            public View onCreateView(@NonNull String name, @NonNull Context context,
                                     @NonNull AttributeSet attrs) {
                return null;
            }
        });

        super.onCreate(savedInstanceState);
        ActivityInflaterBinding mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_inflater);

    }
}
