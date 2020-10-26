package com.some.mvvmdemo.hotfix;

import android.app.Application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangxing
 */
public class HotFix {


    /**
     * 1.获取当前应用的 PathClassLoader
     * 2.获取DexPathList的属性对象 pathList
     * 3.修改pathList中的Element对象
     *  3.1 把补丁包path.dex 转换成Element path
     *  3.2 获取pathList的dexElements属性（old）
     *  3.3 patch + old合并，并反射赋值给pathList的dexElements
     */
    public static void installPatch(Application application, File patchFile){

        if(!patchFile.exists()){
            return;
        }

        //1.获取当前应用的 PathClassLoader
        ClassLoader classLoader = application.getClassLoader();

        //2.获取DexPathList的属性对象 pathList
        Field pathListField = ShareReflectUtil.findField(classLoader,"pathList");
        try{
            Object pathList = pathListField.get(classLoader);

            //3.1 把补丁包path.dex 转换成Element[] path
            List<File> dexFiles = new ArrayList<>();
            dexFiles.add(patchFile);

            File dexOutPutDir = application.getCodeCacheDir();

            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();

            Method method = ShareReflectUtil.getMethod(pathList,"makeDexElements",
                    new Class[]{List.class, File.class, List.class});

            Object[] patchElements = (Object[]) method.invoke(pathList,dexFiles,dexOutPutDir,
                    suppressedExceptions);


            //3.2 获取pathList的dexElements属性（old）
            Field dexElementsField = ShareReflectUtil.findField(pathList,"dexElements");
            Object[] dexElements = (Object[]) dexElementsField.get(pathList);

            //3.3 patch + old合并，并反射赋值给pathList的dexElements
            //创建一个新的数组来装载所有的长度
            Object[] newArray = (Object[]) Array.newInstance(patchElements[0].getClass(),
                    patchElements.length + dexElements.length);

            System.arraycopy(patchElements,0,newArray,0,patchElements.length);
            System.arraycopy(dexElements,0,newArray,patchElements.length,dexElements.length);

            dexElementsField.set(pathList,newArray);


        }catch (Exception e){

        }

    }
}
