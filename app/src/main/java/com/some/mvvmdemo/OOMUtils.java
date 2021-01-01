package com.some.mvvmdemo;

import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author xiangxing
 */
public class OOMUtils {

    public static final float UNIT_M = 1024 * 1024;

    /**
     * 获取当前内存情况
     * @return
     */
    public String getCurrentMemory(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Java Heap Max : ").append(Runtime.getRuntime().maxMemory()/UNIT_M).append(" MB\r\n");
        stringBuilder.append("Current 已经申请的内存 : ").append(Runtime.getRuntime().totalMemory()/UNIT_M).append(" MB\r\n");
        stringBuilder.append("Current used  : ").append((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/UNIT_M).append(" MB\r\n");
        return stringBuilder.toString();
    }

    /**
     * 获取文件句柄
     * @return
     */
    public int getCurrentFDCount(){
        File fdFile=new File("/proc/" + android.os.Process.myPid() + "/fd");
        File[] files = fdFile.listFiles();
        if(files != null){
            return files.length;
        }
        return -1;
    }

    public String getAppLimits(){
        return showFileContent("/proc/"+ android.os.Process.myPid()+"/limits");
    }

    /**
     * 查看到该进程打开的线程数
     * 其中有Threads字段
     * @return
     */
    public String getAppStatus(){
        return showFileContent("/proc/"+ android.os.Process.myPid()+"/status");
    }


    private String showFileContent(String path){
        if (TextUtils.isEmpty(path)){
            return "";
        }
        try{
            RandomAccessFile randomAccessFile=new RandomAccessFile(path,"r");
            StringBuilder stringBuilder=new StringBuilder();
            String s;
            while ((s=randomAccessFile.readLine())!=null){
                stringBuilder.append(s).append("\r\n");
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
