package com.some.demo_annotation;
/**
 * @author xiangxing
 */
public class DeepLinkEntry {

    private String path;
    private Class<?> classz;
    public DeepLinkEntry(String path,Class<?> classz){
        this.path = path;
        this.classz = classz;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class<?> getActivityClass() {
        return classz;
    }

    public void setClassz(Class<?> classz) {
        this.classz = classz;
    }


}
