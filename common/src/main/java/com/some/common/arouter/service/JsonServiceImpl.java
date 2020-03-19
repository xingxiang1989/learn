package com.some.common.arouter.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.blankj.utilcode.util.GsonUtils;
import com.some.common.arouter.RouterUrl;

import java.lang.reflect.Type;

@Route(path = RouterUrl.SERVICE_JSON)
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
//        return new Gson().fromJson(input, clazz);
        return GsonUtils.fromJson(input,clazz);
    }

    @Override
    public String object2Json(Object instance) {
//        return new Gson().toJson(instance);
        return GsonUtils.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
//        return new Gson().fromJson(input, clazz);
        return GsonUtils.fromJson(input,clazz);
    }

    @Override
    public void init(Context context) {

    }
}