package com.some.mvvmdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.some.demo_annotation.DeepLink;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.base.BaseFragment;
import com.some.mvvmdemo.databinding.ActivityHomeViewpagerBinding;

import java.util.ArrayList;
import java.util.List;
@DeepLink("acdef://wenkiwu.com/a/")
public class HomeViewPagerActivity extends BaseActiviy implements View.OnClickListener{

    ActivityHomeViewpagerBinding binding;
    List<TextView> mTabViews = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_viewpager);
        binding.setClick(this);

        mTabViews.add(binding.tabMsg);
        mTabViews.add(binding.tabNearby);
        mTabViews.add(binding.tabSetting);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MessageFragment());
        fragments.add(new NearbyFragment());
        fragments.add(new SettingFragment());

        TabFragmentPagerAdapter adapter =
                new TabFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        binding.viewpager.setAdapter(adapter);

        switchTab(1);
        binding.viewpager.setOffscreenPageLimit(3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_msg:
                switchTab(0);
                break;
            case R.id.tab_nearby:
                switchTab(1);
                break;
            case R.id.tab_setting:
                switchTab(2);
                break;
        }
    }

    private void switchTab(int index){
        binding.viewpager.setCurrentItem(index);
        switchBottomTab(index);
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
