package com.some.mvvmdemo.http;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.some.http.BaseEntity;
import com.some.http.Constants;
import com.some.http.RetrofitUtil;
import com.some.mvvmdemo.http.entity.EncryptInfo;
import com.some.mvvmdemo.http.request.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RetrofitTestVM extends AndroidViewModel {

    public RetrofitTestVM(@NonNull Application application) {
        super(application);
    }

    public void request() {

        final ApiService request = RetrofitUtil.Companion.getService(Constants.REQUEST_BASE_URL,
                ApiService.class);
        Disposable disposable =
                request.getConfig().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseEntity<EncryptInfo>>() {
                    @Override
                    public void accept(BaseEntity<EncryptInfo> encryptInfoBaseEntity) throws Exception {
                        int encrypt = encryptInfoBaseEntity.getInfo().getEncrypt();
                        LogUtils.d("getEncrypt = " + encrypt);

                    }
                }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.d("getEncrypt err= " + throwable.getMessage());

            }
        });
    }
}
