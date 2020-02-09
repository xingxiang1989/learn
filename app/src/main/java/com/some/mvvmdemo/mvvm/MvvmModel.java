package com.some.mvvmdemo.mvvm;

import com.some.mvvmdemo.MvvmCallBack;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.entity.Account;

import java.util.Random;

public class MvvmModel {

    public MvvmModel(){

    }

    public void getAccountData(String input, MvvmCallBack callBack){

        Random random = new Random();

        Account account = new Account(input, random.nextInt(3000));

        callBack.onSuccess(account);

    }
}
