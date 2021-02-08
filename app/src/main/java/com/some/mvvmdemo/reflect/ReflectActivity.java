package com.some.mvvmdemo.reflect;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.entity.Account;
import com.some.mvvmdemo.proxy.IHello;
import com.some.mvvmdemo.proxy.RealSubject;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * @author xiangxing
 */
public class ReflectActivity extends BaseActiviy implements View.OnClickListener {

    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";
    public static final String ROUTE_ROOT_PAKCAGE = "com.alibaba.android.arouter.routes";

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

        imageView = findViewById(R.id.imageView);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

//        getARouterClass();

//        addPluginApkToDex("");

//        testInnovationHandler();

//        testClassLoaderFromApk();

        createInstance();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn1){
            switchSkin(1);
        }else if(v.getId() == R.id.btn2){
            switchSkin(2);
        }
    }

    /**
     * 切换皮肤
     * @param type
     */
    public void switchSkin(int type) {
        String skinType;
        if(type == 1){
            skinType= "app-test.apk";
        }else {
            skinType= "children.apk";
        }
        final String path = Environment.getExternalStorageDirectory() + File.separator + skinType;
        final String pkgName = getUninstallApkPkgName(this, path);

        LogUtils.d("switchSkin pkgName =" + pkgName);
        dynamicLoadApk(path,pkgName);
    }


    /**
     * 获取未安装apk的信息
     * @param context
     * @param pApkFilePath apk文件的path
     * @return
     */
    private String getUninstallApkPkgName(Context context, String pApkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(pApkFilePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            return appInfo.packageName;
        }
        return "";
    }

    /**
     *
     * @param pApkFilePath
     * @param pApkPacketName
     */
    private  void dynamicLoadApk(String pApkFilePath,String pApkPacketName){

        LogUtils.d("dynamicLoadApk start pApkPacketName =" + pApkPacketName);

        File file=getDir("dex", Context.MODE_PRIVATE);
        //第一个参数：是dex压缩文件的路径
        //第二个参数：是dex解压缩后存放的目录
        //第三个参数：是C/C++依赖的本地库文件目录,可以为null
        //第四个参数：是上一级的类加载器
        DexClassLoader  classLoader=new DexClassLoader(pApkFilePath,file.getAbsolutePath(),null,getClassLoader());
        try {
            final Class<?> loadClazz = classLoader.loadClass(pApkPacketName +
                    ".R$mipmap");
            //插件中皮肤的名称是skin_one
            final Field skinOneField = loadClazz.getDeclaredField("skin_one");
            skinOneField.setAccessible(true);
            //反射获取skin_one的resousreId
            final int resousreId = (int) skinOneField.get(R.id.class);
            //可以加载插件资源的Resources
            final Resources resources = createResources(pApkFilePath);
            if (resources != null) {
                final Drawable drawable = resources.getDrawable(resousreId);
                imageView.setBackground(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("dynamicLoadApk e =" + e.toString());
        }

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

    /**
     *
     */
    private void getARouterClass(){

        final Set<String> classNames = new HashSet<>();
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
            LogUtils.d("getARouterClass sourceDir =" + applicationInfo.sourceDir);
            LogUtils.d("getARouterClass sourceApk =" + sourceApk.getName());

            List<String> sourcePaths = new ArrayList<>();
            //add the default apk path
            sourcePaths.add(applicationInfo.sourceDir);

            final CountDownLatch countDownLatch = new CountDownLatch(sourcePaths.size());
            for(final String path : sourcePaths){
                DefaultPoolExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {

                        DexFile dexfile = null;

                        try {
                            if (path.endsWith(EXTRACTED_SUFFIX)) {
                                //NOT use new DexFile(path), because it will throw "permission error in /data/dalvik-cache"
                                dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                            } else {
                                dexfile = new DexFile(path);
                            }

                            Enumeration<String> dexEntries = dexfile.entries();
                            while (dexEntries.hasMoreElements()) {
                                String className = dexEntries.nextElement();
                                if (className.startsWith(ROUTE_ROOT_PAKCAGE)) {
                                    LogUtils.d("getARouterClass className=" + className);
                                    classNames.add(className);
                                }
                            }
                        } catch (Throwable ignore) {
                            LogUtils.e("ARouter", "getARouterClass Scan map file in dex" +
                                    " files made error.", ignore);
                        } finally {
                            if (null != dexfile) {
                                try {
                                    dexfile.close();
                                } catch (Throwable ignore) {
                                }
                            }
                        }

                        countDownLatch.countDown();
                    }
                });
            }

            countDownLatch.await();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 插件开发
     * 插件化动态加载的文件是不是必须是apk文件？
     *
     * dex也可以加载，不过作为完整的插件文件需要是apk或者是aar，因为还包含资源文件
     */
    private void testClassLoaderFromApk(){

        String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "app-test.apk";
        File sourceFile = new File(dexPath);
        if(sourceFile.exists()){
            LogUtils.d("testClassLoaderFromApk 目标apk存在 " );

        }

        File outPutFile = getDir("dex11", MODE_PRIVATE);

        LogUtils.d("testClassLoaderFromApk outPutFile =" + outPutFile.toString());

        //这里要注意，最开始没加读取sd卡的权限，提示did not findClass，添加后，还需要手动去打开权限。
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,
                outPutFile.toString(),null,getClassLoader());

        try {
            //就前面两步与Class.forName()有区别，通过newInstance构建对象
            Class<?> classz = dexClassLoader.loadClass("com.some.testdemo.LogUtils");

            //方式1 构造对象
            Object object = classz.newInstance();
            Method method = classz.getDeclaredMethod("printLog");
            method.setAccessible(true);
            method.invoke(object);


            Method method2 = classz.getDeclaredMethod("printMessage");
            method2.setAccessible(true);
            method2.invoke(object);

            LogUtils.d("testClassLoaderFromApk invoke 完成 " );


        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("testClassLoaderFromApk Exception e= " + e.toString());

        }
    }

    /**
     * 把插件文件设置进去，生成AssetManager
     * @param pFilePath
     */
    private AssetManager createAssetManager(String pFilePath){

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Class classzz = Class.forName("android.content.res.AssetManager");
            Method method = classzz.getDeclaredMethod("addAssetPath",String.class);
            method.setAccessible(true);
            method.invoke(assetManager, pFilePath);
            return assetManager;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个Resources就可以加载非宿主apk中的资源
     * @param pFilePath
     * @return
     */
    private Resources createResources(String pFilePath){
        final AssetManager assetManager = createAssetManager(pFilePath);
        Resources superRes = this.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    /**
     * 两种构造器的方法
     * 已经理解
     */
    private void createInstance(){
        try {
            Account object = Account.class.newInstance();
            LogUtils.d("createInstance 11 name = " + object.getName() +
                    " level = " + object.getLevel());

            Constructor<?> constructor = Account.class.getConstructor(String.class,
                    int.class);
            Account account = (Account) constructor.newInstance("LI Lei",11);
            LogUtils.d("createInstance 22  name = " + account.getName() +
                    " level = " + account.getLevel());

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("createInstance 33  e = " + e.toString());
        }

    }
}
