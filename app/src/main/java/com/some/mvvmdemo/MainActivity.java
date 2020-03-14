package com.some.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityMainBinding;
import com.some.mvvmdemo.entity.Account;
import com.some.mvvmdemo.mvvm.MvvmActivity;

public class MainActivity extends BaseActiviy {

    Account account;
    ActivityMainBinding binding;
    Animation animDown,animUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        account = new Account("XiaoLei",100);
        binding.setAccount(account);
        binding.setActivity(this);


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


    }

    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn:
                startActivity(new Intent(MainActivity.this, HomeTabActivity.class));
                break;
            case R.id.btn_down:
                binding.topLayout.startAnimation(animDown);
                break;
            case R.id.btn_up:
                binding.topLayout.startAnimation(animUp);
                break;
        }
    }
}
