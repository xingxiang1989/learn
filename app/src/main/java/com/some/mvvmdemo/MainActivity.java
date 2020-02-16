package com.some.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.some.mvvmdemo.databinding.ActivityMainBinding;
import com.some.mvvmdemo.entity.Account;
import com.some.mvvmdemo.mvvm.MvvmActivity;

public class MainActivity extends AppCompatActivity {

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        account = new Account("XiaoLei",100);
        binding.setAccount(account);
        binding.setActivity(this);



    }

    public void onclick(View view){
//        Toast.makeText(this,"点击",Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this, MvvmActivity.class));
//        startActivity(new Intent(MainActivity.this, NearbyActivity.class));
        startActivity(new Intent(MainActivity.this, HomeTabActivity.class));
    }
}
