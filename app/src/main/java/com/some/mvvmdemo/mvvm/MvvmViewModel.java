package com.some.mvvmdemo.mvvm;

import android.app.Application;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.some.mvvmdemo.BR;
import com.some.mvvmdemo.MvvmCallBack;
import com.some.mvvmdemo.databinding.ActivityMvvmBinding;
import com.some.mvvmdemo.entity.Account;

public class MvvmViewModel extends BaseObservable {

    private String result;
    private String input;
    private MvvmModel mvvmModel;
    private ActivityMvvmBinding binding;

    public MvvmViewModel(Application application){
        mvvmModel = new MvvmModel();
    }

    public MvvmViewModel(Application application, ActivityMvvmBinding binding){
        mvvmModel = new MvvmModel();
        this.binding = binding;
    }

    public void getData(View view){

        mvvmModel.getAccountData(input, new MvvmCallBack() {
            @Override
            public void onSuccess(Account account) {
                String info = account.getName() + "|" + account.getLevel();
                setResult(info);
            }

            @Override
            public void onFailed() {
                setResult("获取失败");
            }
        });
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(com.some.mvvmdemo.BR.result);
    }

    @Bindable
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
        notifyPropertyChanged(com.some.mvvmdemo.BR.input);
    }
}
