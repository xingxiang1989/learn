package com.some.common.arouter.service;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.blankj.utilcode.util.Utils;
import com.some.common.arouter.RouterUrl;

@Route(path = RouterUrl.SERVICE_DEGRADE)
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        Toast.makeText(Utils.getApp(),"页面不存在，敬请期待！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
    }
}