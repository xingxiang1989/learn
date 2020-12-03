package com.some.hookactivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiangxing
 */
public class HookUtils {

    private static final String EXTRA_TARGET_INTENT = "EXTRA_TARGET_INTENT";


    /**
     * 其实难点是在Android系统版本的兼容
     */
    public static void hookAMSAidl(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            hookIActivityTaskManager();
        }else{
            hookIActivityManager();
        }
    }

    /**
     * 前期修改，主要是通过proxy代理类，将传递的activity信息替换掉
     */
    private static void hookIActivityTaskManager(){

        LogUtils.d("hookIActivityTaskManager");

        try {
            //1. 拿到Singleton<IActivityTaskManager>对象
            Class<?> mActivityTaskManagerClass =  Class.forName("android.app.ActivityTaskManager");
            Field singletonField = mActivityTaskManagerClass.getDeclaredField(
                    "IActivityTaskManagerSingleton");
            singletonField.setAccessible(true);
            final Object singleton = singletonField.get(null);
            LogUtils.d("hook singleton = " + singleton.toString());

            //2. getService（） 返回的是IActivityTaskManager，通过singleton 获取，
            //查看singleton代码，返回的是instance 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object IActivityTaskManager = mInstanceField.get(singleton);
            LogUtils.d("hook IActivityTaskManager = " + IActivityTaskManager);

            //3. 动态代理一下，拦截startActivity方法


            Object proxy =
                    Proxy.newProxyInstance(singleton.getClass().getClassLoader(),
                            new Class<?>[]{Class.forName("android.app.IActivityTaskManager")},
                    new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if("startActivity".equals(method.getName())){
                        LogUtils.d("startActivity start ");
                        //args 里面存储的是方法里的入参集合,通过循环遍历取出，不要简单的认为根据下标
                        Intent rawIntent = null;
                        int index = -1;
                        for(int i = 0 ; i < args.length ; i++ ){
                            if(args[i] instanceof Intent ){
                                rawIntent = (Intent)args[i];
                                index = i;
                            }
                        }
                        LogUtils.d("startActivity raw = " + rawIntent + " index =" + index);
                        LogUtils.d("startActivity raw getPackageName = " + rawIntent.getComponent().getPackageName());
                        LogUtils.d("startActivity raw getClassName = " + rawIntent.getComponent().getClassName());


                        Intent newIntent = new Intent();
                        newIntent.putExtra(EXTRA_TARGET_INTENT,rawIntent);
                        newIntent.setComponent(new ComponentName("com.some.mvvmdemo",
                                ProxyActivity.class.getCanonicalName()));

                        LogUtils.d("startActivity new getPackageName = " + newIntent.getComponent().getPackageName());
                        LogUtils.d("startActivity new getClassName = " + newIntent.getComponent().getClassName());


                        args[index]= newIntent;

                        LogUtils.d("startActivity end ");
                    }
                    //因为代理的是IActivityTaskManager接口，因此object 必须是实现IActivityTaskManager接口的对象
                    return method.invoke(IActivityTaskManager, args);
                }
            });

            //给某个类的变量设置代理，然后代理的是另外一个接口
            mInstanceField.set(singleton,proxy);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("e =" + e.toString());
        }
    }

    private static void hookIActivityManager() {

        LogUtils.d("hookIActivityManager");

        try{
            Field singletonField = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> actvityManager = Class.forName("android.app.ActivityManager");
                singletonField = actvityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> actvityManager = Class.forName("android.app.ActivityManagerNative");
                singletonField = actvityManager.getDeclaredField("gDefault");
            }
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);
            //拿IActivityManager对象
            Class<?> actvityManager = Class.forName("android.util.Singleton");
            Field mInstanceField = actvityManager.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //原始的IActivityManager
            final Object rawIActivityManager = mInstanceField.get(singleton);
            //创建一个IActivityManager代理对象
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , new Class[]{Class.forName("android.app.IActivityManager")}
                    , new InvocationHandler() {
                        @Override
                        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
//                            Log.i(TAG, "invoke: " + method.getName());

                            //偷梁换柱
                            //真正要启动的activity目标
                            Intent raw = null;
                            int index = -1;
                            if ("startActivity".equals(method.getName())) {
                                LogUtils.d("invoke: startActivity 启动准备");
                                for (int i = 0; i < args.length; i++) {
                                    if(args[i] instanceof  Intent){
                                        raw = (Intent)args[i];
                                        index = i;
                                    }
                                }
                                LogUtils.d( "invoke: raw: " + raw);
                                //代替的Intent
                                Intent newIntent = new Intent();
                                newIntent.setComponent(new ComponentName("com.some" +
                                        ".hookactivity",ProxyActivity.class.getName()));                                newIntent.putExtra(EXTRA_TARGET_INTENT,raw);

                                args[index] = newIntent;

                            }

                            return method.invoke(rawIActivityManager, args);
                        }
                    });

            //            7. IActivityManagerProxy 融入到framework
            mInstanceField.set(singleton, proxy);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
