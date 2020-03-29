package com.some.mvvmdemo.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ShareDataVM extends AndroidViewModel {

    private MutableLiveData<String> mLiveData = new MutableLiveData<>();

    public ShareDataVM(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getLiveData() {
        return mLiveData;
    }
}
