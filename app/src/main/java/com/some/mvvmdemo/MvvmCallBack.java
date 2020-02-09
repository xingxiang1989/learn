package com.some.mvvmdemo;

import com.some.mvvmdemo.entity.Account;

public interface MvvmCallBack {

    void onSuccess(Account account);

    void onFailed();
}
