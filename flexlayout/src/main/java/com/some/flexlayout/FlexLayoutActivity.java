package com.some.flexlayout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.some.flexlayout.databinding.ActivityFlexLayoutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xiangxing
 */
public class FlexLayoutActivity extends Activity {

    private List<String> nameList = new ArrayList<>();
    private ActivityFlexLayoutBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_flex_layout);
        for(int i = 0 ; i < 20 ; i++){
            nameList.add("name index" + i);
        }

        LogUtils.d("nameList size =" + nameList.size());

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this,
                FlexDirection.ROW, FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        mBinding.recyclerview.setLayoutManager(flexboxLayoutManager);

        TagAdapter adapter = new TagAdapter(nameList);
        mBinding.recyclerview.setAdapter(adapter);

    }
}
