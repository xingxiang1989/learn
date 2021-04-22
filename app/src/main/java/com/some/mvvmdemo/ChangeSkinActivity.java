package com.some.mvvmdemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.some.changeskin.SkinManager;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityChangeSkinBinding;

/**
 * @author xiangxing
 */
public class ChangeSkinActivity extends BaseActiviy {

    ActivityChangeSkinBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("ApplicationActivityLifecycle","xiangxingtest ChangeSkinActivity onCreate");

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_change_skin);

        binding.changeSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.Companion.getInstance().loadSkin("/storage/emulated/0/ASkin" +
                        "/skin.apk");
            }
        });

        binding.revertSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.Companion.getInstance().loadSkin("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("ApplicationActivityLifecycle","xiangxingtest ChangeSkinActivity onDestroy");

    }
}
