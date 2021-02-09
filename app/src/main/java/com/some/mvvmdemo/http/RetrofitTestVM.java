package com.some.mvvmdemo.http;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.blankj.utilcode.util.LogUtils;
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

    /**
     * 这种写法添加了map，进行了转换与处理，如果不加的话，解析的数据是BaseEntity
     * rxjava 与 lifecycle 结合使用 TODO:
     */
    public void request() {

        final ApiService request = RetrofitUtil.Companion.getService(Constants.REQUEST_BASE_URL,
                ApiService.class);
        Disposable disposable =
                request.getConfig()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponseMapper.create())
                        .subscribe(new Consumer<EncryptInfo>() {
                            @Override
                            public void accept(EncryptInfo encryptInfo) throws Exception {
                                LogUtils.d(" onSuccess getEncrypt = " + encryptInfo.getEncrypt());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtils.d(" onError = " + throwable.getMessage());
                            }
                        });


//        request.getConfig2().enqueue(new Callback<BaseEntity<EncryptInfo>>() {
//            @Override
//            public void onResponse(Call<BaseEntity<EncryptInfo>> call,
//                                   Response<BaseEntity<EncryptInfo>> response) {
//                LogUtils.d("getConfig2 onResponse  ");
//                BaseEntity<EncryptInfo> entity = response.body();
//                int encrypt = entity.getInfo().getEncrypt();
//
//                LogUtils.d("getConfig2 onResponse  encrypt = " + encrypt);
//
//            }
//
//            @Override
//            public void onFailure(Call<BaseEntity<EncryptInfo>> call, Throwable t) {
//                LogUtils.d("getConfig2 onFailure err= " + t.getMessage());
//            }
//        });
    }
}
