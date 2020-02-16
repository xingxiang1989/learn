package com.some.mvvmdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.some.mvvmdemo.databinding.ActivityHomeTabBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeTabActivity extends FragmentActivity implements View.OnClickListener {

    ActivityHomeTabBinding mBinding;

    List<TextView> mTabViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_tab);
        mBinding.setClick(this);

        mTabViews.add(mBinding.tabMsg);
        mTabViews.add(mBinding.tabNearby);
        mTabViews.add(mBinding.tabSetting);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_msg:
                switchBottomTab(0);
                break;
            case R.id.tab_nearby:
                switchBottomTab(1);
                break;
            case R.id.tab_setting:
                switchBottomTab(2);
                break;
        }
    }

    private void switchBottomTab(int selected){
        for(int i = 0 ; i <mTabViews.size() ; i++){
            if(i == selected){
                mTabViews.get(i).setTextColor(getResources().getColor(R.color.colorAccent));
            }else {
                mTabViews.get(i).setTextColor(getResources().getColor(R.color.black));
            }
        }
    }
}
