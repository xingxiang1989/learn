package com.some.flexlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.some.common.arouter.RouterUrl;
import com.some.flexlayout.databinding.ActivityFlexLayoutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xiangxing
 */
@Route(path = RouterUrl.flexOut)
public class FlexLayoutActivity extends Activity {

    private List<String> nameList = new ArrayList<>();
    private ActivityFlexLayoutBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_flex_layout);
        nameList.add("1" );
        nameList.add("中国" );
        nameList.add("点点滴滴点点滴滴" );
        nameList.add("给哥哥哥哥" );
        nameList.add("多多岛" );
        nameList.add("澳大利亚" );
        nameList.add("得到多多岛" );
        nameList.add("俄罗斯" );
        nameList.add("美国" );
        nameList.add("日本" );
        nameList.add("巴基斯坦" );
        nameList.add("匈牙利" );
        nameList.add("跑套的点点滴滴" );
        nameList.add("的点点滴滴 得到党的点点滴滴" );
        nameList.add("sfdksdfj" );

//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );
//        nameList.add("中国" );


        LogUtils.d("nameList size =" + nameList.size());

        mBinding.recyclerview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int left = mBinding.recyclerview.getLeft();
                int paddingLeft = mBinding.recyclerview.getPaddingLeft();

                int right = mBinding.recyclerview.getRight();
                int paddingRight = mBinding.recyclerview.getPaddingRight();

                LogUtils.d("left = " + left);
                LogUtils.d("paddingLeft = " + paddingLeft);
                LogUtils.d("right = " + right);
                LogUtils.d("paddingRight = " + paddingRight);

                mBinding.recyclerview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });



        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this,
                FlexDirection.ROW, FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        mBinding.recyclerview.setLayoutManager(flexboxLayoutManager);

        //默认的为分割线
        FlexboxItemDecoration itemDecoration = new FlexboxItemDecoration(this);
        mBinding.recyclerview.addItemDecoration(itemDecoration);

        TagAdapter adapter = new TagAdapter(nameList);
        mBinding.recyclerview.setAdapter(adapter);




    }
}
