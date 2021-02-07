package com.some.mvvmdemo.http;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.some.http.Constants;
import com.some.http.RetrofitUtil;
import com.some.mvvmdemo.entity.Translation;
import com.some.mvvmdemo.http.entity.EncryptInfo;
import com.some.mvvmdemo.http.request.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitTestVM extends AndroidViewModel {

    public RetrofitTestVM(@NonNull Application application) {
        super(application);
    }

    public void request() {

        final ApiService request = RetrofitUtil.Companion.getService(Constants.INSTANCE.getREQUEST_BASE_URL(),
                ApiService.class);
        Disposable disposable =
                request.getConfig().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EncryptInfo>() {
            @Override
            public void accept(EncryptInfo encryptInfo) throws Exception {
                LogUtils.d("getEncrypt = " + encryptInfo.getEncrypt());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.d("getEncrypt err= " + throwable.getMessage());

            }
        });
    }
}
