package com.some.mvvmdemo;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.some.common.arouter.RouterUrl;
import com.some.demo_annotation.BindView;
import com.some.demo_annotation.DeepLink;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityAnimBinding;
import com.some.mvvmdemo.entity.Account;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * @author xiangxing
 */
@DeepLink("acdef://wenkiwu.com/c/")
@Route(path = RouterUrl.Main)
public class AnimActivity extends BaseActiviy implements View.OnClickListener {

    Account account;
    ActivityAnimBinding binding;
    Animation animDown,animUp, animLeftIn, animLeftOut,animRightIn, animRightOut;

    @BindView(R.id.topLayout)
    LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_anim);

        account = new Account("XiaoLei",100);
        binding.setAccount(account);
        binding.setClick(this);

        final float scale = Resources.getSystem().getDisplayMetrics().density;
        Log.d("MainActivity","scale=" + scale);


        animDown = AnimationUtils.loadAnimation(this,R.anim.anim_down);
        animDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.topLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animUp = AnimationUtils.loadAnimation(this,R.anim.anim_up);
        animUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.topLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        animLeftIn = AnimationUtils.loadAnimation(this,R.anim.anim_left_in);
        animLeftIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.LeftLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animLeftOut = AnimationUtils.loadAnimation(this,R.anim.anim_left_out);
        animLeftOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.LeftLayout.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animRightIn = AnimationUtils.loadAnimation(this,R.anim.anim_right_in);
        animRightIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.RightLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animRightOut = AnimationUtils.loadAnimation(this,R.anim.anim_right_out);
        animRightOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.RightLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                LogUtils.d("xiangxingtest 点击btn");
                startActivity(new Intent(AnimActivity.this, HomeTabActivity.class));
                overridePendingTransition(R.anim.anim_left_in,0);
                break;
            case R.id.btnviewpager:
                startActivity(new Intent(AnimActivity.this, HomeViewPagerActivity.class));
                break;
            case R.id.btn_down:
//                binding.topLayout.startAnimation(animDown);
                ARouter.getInstance().build("/moduleA/moduleA").navigation();
                break;
            case R.id.btn_up:
                binding.topLayout.startAnimation(animUp);
                break;

            case R.id.btn_left_in:
                binding.LeftLayout.startAnimation(animLeftIn);
                break;
            case R.id.btn_left_out:
                binding.LeftLayout.startAnimation(animLeftOut);
                break;

            case R.id.btn_right_in:
                binding.RightLayout.startAnimation(animRightIn);
                break;
            case R.id.btn_right_out:
                binding.RightLayout.startAnimation(animRightOut);
                break;
            case R.id.btnLoadPatch:
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");

                break;

        }
    }
}
