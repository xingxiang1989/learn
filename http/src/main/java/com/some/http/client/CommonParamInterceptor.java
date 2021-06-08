package com.some.http.client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by xiangxing5 on 2021/6/8.
 * Describe: 通过设置拦截器进行公共参数的添加
 * https://blog.csdn.net/Xuexx_520/article/details/84765489
 */
public class CommonParamInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
