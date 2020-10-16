package com.some.mvvmdemo;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityHomeTabBinding;
import com.some.mvvmdemo.reentranlock.SynchronizedExceptionRunnable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HomeTabActivity extends BaseActiviy implements View.OnClickListener {

    ActivityHomeTabBinding mBinding;

    List<TextView> mTabViews = new ArrayList<>();

    MessageFragment mMessageFragment;

    NearbyFragment mNearbyFragment;

    SettingFragment mSettingFragment;

    int currentTab = 1;

    FragmentManager fm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_tab);
        mBinding.setClick(this);

        fm = getSupportFragmentManager();

        mTabViews.add(mBinding.tabMsg);
        mTabViews.add(mBinding.tabNearby);
        mTabViews.add(mBinding.tabSetting);

        if(savedInstanceState != null){
            currentTab = savedInstanceState.getInt("currentTab");
        }

        switchTab(currentTab);

        SynchronizedExceptionRunnable runnable = new SynchronizedExceptionRunnable();

        Thread thread1 = new Thread(runnable,"杯子");
        Thread thread2 = new Thread(runnable,"人");
        thread1.start();
        thread2.start();


        final float scale = Resources.getSystem().getDisplayMetrics().density;
        Log.d("MainActivity","scale=" + scale);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

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

    /**
     * 1.首先先进行隐藏
     * @param selected
     */
    private void switchTab(int selected){

        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction, selected, mMessageFragment,mNearbyFragment,mSettingFragment);

        switch (selected){
            case 0:
                if(mMessageFragment == null){
                    mMessageFragment = new MessageFragment();
                }
                showFragment(transaction,mMessageFragment);
                break;
            case 1:
                if(mNearbyFragment == null){
                    mNearbyFragment = new NearbyFragment();
                }
                showFragment(transaction,mNearbyFragment);
                break;
            case 2:
                if(mSettingFragment == null){
                    mSettingFragment = new SettingFragment();
                }
                showFragment(transaction,mSettingFragment);
                break;
        }

        switchBottomTab(selected);
    }

    /**
     * 除选中fragment，隐藏其他fragment
     * @param transaction
     * @param tab
     * @param fragments
     */
    private void hideFragment(FragmentTransaction transaction, int tab,
                              Fragment... fragments){

        if(transaction == null || fragments == null){
            return;
        }

        for(int i = 0; i < fragments.length ; i++){
            if(i != tab && fragments[i] != null){
                transaction.hide(fragments[i]);
            }
        }

    }

    /**
     * 1.首先判断fragment是否被添加，或者能否找到，如果没有则添加进去
     * 2.如果已经添加，如果被隐藏，则进行显示。
     * @param transaction
     * @param fragment
     */
    private void showFragment(FragmentTransaction transaction, Fragment fragment){

        if(transaction == null || fragment == null){
            return;
        }

        Fragment tempFragment = fm.findFragmentByTag(fragment.getClass().getSimpleName());

        if(!fragment.isAdded() && tempFragment == null){
            transaction.add(R.id.container,fragment);
            transaction.commitAllowingStateLoss();
        }else{
            if(fragment.isHidden()){
                transaction.show(fragment);
            }
            transaction.commitAllowingStateLoss();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentTab",currentTab);
    }
}
