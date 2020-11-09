package com.some.mvvmdemo.reflect;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.entity.Account;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * @author xiangxing
 */
public class ReflectActivity extends BaseActiviy {

    private static final String EXTRACTED_NAME_EXT = ".classes";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

        try {
            Class classObj = Class.forName("com.some.mvvmdemo.entity.Account");
            Constructor constructor = classObj.getConstructor(String.class,int.class);
            //强制性转换用于演示
            String log = ((Account)constructor.newInstance("111",222)).toString();
            LogUtils.d("log = " + log);

            Field levelField = classObj.getDeclaredField("level");
            levelField.setAccessible(true);
            levelField.set(classObj, 55);

            Object object = constructor.newInstance("111",222);

            classObj.getMethod("setLevel",new Class[]{int.class}).invoke(object,102);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 目的，ARouter是在编译期对注解进行编译，生成编译文件
         * 需要理解点：
         * 这些编译文件被打进dex文件中，是如何找到，并在运行期如何加载到内存中的，是如何找到相关的路由信息的
         */

        /**
         * step1：编译的文件都是放在com.alibaba.android.arouter.routes 这个包名下，于是查找包
         * 名下的文件
         * 因为是多dex，要先找到所有的dex文件信息
         */
        try {
            ApplicationInfo applicationInfo =
                    Utils.getApp().getPackageManager().getApplicationInfo(getPackageName(),0);

            File sourceApk = new File(applicationInfo.sourceDir);
            LogUtils.d("sourceDir =" + applicationInfo.sourceDir);
            LogUtils.d("sourceApk =" + sourceApk.getName());

            List<String> sourcePaths = new ArrayList<>();
            //add the default apk path
            sourcePaths.add(applicationInfo.sourceDir);

            final CountDownLatch countDownLatch = new CountDownLatch(sourcePaths.size());
            for(final String path : sourcePaths){
                DefaultPoolExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        File dexOutputDir = getDir("dex1", 0);
                        DexClassLoader dexClassLoader = new DexClassLoader(path,
                                dexOutputDir.getAbsolutePath(),null,getClassLoader());
                        String library = dexClassLoader.findLibrary("com.alibaba" +
                                ".android.arouter.routes");

                        LogUtils.d("library = " + library);
                        countDownLatch.countDown();
                    }
                });
            }

            countDownLatch.await();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
