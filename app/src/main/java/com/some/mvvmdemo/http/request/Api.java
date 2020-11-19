package com.some.mvvmdemo.http.request;

import com.some.mvvmdemo.entity.Translation;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getConfig();
}
