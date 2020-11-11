package com.some.mvvmdemo.reflect;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dalvik.system.DexClassLoader;

/**
 * @author xiangxing
 */
public class ReflectActivity extends BaseActiviy {

    private static final String EXTRACTED_NAME_EXT = ".classes";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

        addPluginApkToDex("");



    }

    /**
     * 插件化将apk中的dex文件加入进来
     */
    private void addPluginApkToDex(String apkPath){
        try {

            Class baseDexLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            LogUtils.d(" addPluginApkToDex pathListField = " + pathListField);

            Class dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field elementsField = dexPathListClass.getDeclaredField("dexElements");
            elementsField.setAccessible(true);

            LogUtils.d(" addPluginApkToDex elementsField = " + elementsField);

            //1. 获取插件的dex
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath,
                    getApplicationContext().getCacheDir().toString(),null,
                    getBaseContext().getClassLoader());

            Object pathListObj = pathListField.get(dexClassLoader);
            Object[] elementObj = (Object[]) elementsField.get(pathListObj);

            LogUtils.d(" addPluginApkToDex elementObj = " + elementObj.length);

            //2.获取宿主的dex
            ClassLoader classLoader = getClassLoader();
            Object hostPathListObj = pathListField.get(classLoader);
            Object[] hostElementsObj = (Object[]) elementsField.get(hostPathListObj);

            LogUtils.d(" addPluginApkToDex hostElementObj = " + hostElementsObj.length);


            //3.将插件dex，宿主dex组装起来
            Object[] dexElements =
                    (Object[])Array.newInstance(hostElementsObj.getClass().getComponentType(),
                    elementObj.length + hostElementsObj.length);

            LogUtils.d(" addPluginApkToDex getComponentType = " + hostElementsObj.getClass().getComponentType());

            System.arraycopy(hostElementsObj,0,dexElements,0,hostElementsObj.length);
            System.arraycopy(elementObj,0,dexElements,
                    hostElementsObj.length,elementObj.length);

            //4.设置进去
            elementsField.set(hostPathListObj,dexElements);

            LogUtils.d(" addPluginApkToDex complete ");


        } catch (Exception e) {
            LogUtils.d( e.toString());
        }
    }


    private void getClassFromDex(){
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
