package com.some.http;

import android.content.Context;
import android.util.SparseArray;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiangxing5 on 2021/6/17.
 * Describe:
 */
public class RetrofitFactory {

    private static Retrofit.Builder mDefaultBuilder;
    private static SparseArray<Retrofit> mRetrofits;

    static{
        mRetrofits = new SparseArray<>();
        mDefaultBuilder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 按自定义方式获取Retrofit
     *
     * @param httpClientType HttpClient类型（可以组合）
     * @param baseUrlType 基本Url类型
     * @param customUrl 自定义Url（baseUrlType需要是CUSTOM才生效）
     */
    public static Retrofit create(Context context, int httpClientType, BaseUrlType baseUrlType, String customUrl) {

        Retrofit retrofit = mRetrofits.get(httpClientType);
        if(retrofit == null){
            synchronized (RetrofitFactory.class){
                if(retrofit == null){
                    retrofit = mDefaultBuilder.baseUrl(customUrl)
                            .client(HttpClientManager.Companion.getClient(context,httpClientType))
                            .build();
                }
            }
        }
        mRetrofits.put(httpClientType,retrofit);
        return retrofit;
    }

    public static void clearCache(){
        mRetrofits.clear();
        HttpClientManager.Companion.clearCache();
    }


    public enum BaseUrlType{
        DEFAULT,DOMAIN,CUSTOM,ROUTER
    }


}
