package com.some.mvvmdemo.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.some.common.BuildConfig;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    /**
     * 初始化路由
     */
    private void initRouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }
}
