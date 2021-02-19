package com.some.mvvmdemo.file;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.some.common.file.SaveUtils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityFileBinding;

/**
 * @author xiangxing
 */
public class FileActivity extends BaseActiviy {

    private ActivityFileBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_file);

        mBinding.btnSaveBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ImageUtils.getBitmap(R.mipmap.race_but_history);
                boolean flag = SaveUtils.saveBitmapToFile(bitmap,"history.jpeg");
                ToastUtils.showShort("图片-1保存成功 " + flag);
            }
        });

        mBinding.btnSaveBitmap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ImageUtils.getBitmap(R.mipmap.race_but_history);
                boolean flag = SaveUtils.saveBitmapToFile(bitmap,"history2.png");
                ToastUtils.showShort("图片-2保存成功 " + flag);
            }
        });
    }
}
