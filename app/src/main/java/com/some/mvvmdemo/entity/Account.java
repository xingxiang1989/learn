package com.some.mvvmdemo.entity;

import com.blankj.utilcode.util.LogUtils;

public class Account {

    private String name;

    private int level;

    public Account(){

    }

    public Account(String name, int level){
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;

        LogUtils.d("name =" + name + ",  level = " + level);
    }



    @Override
    public String toString() {
        return "Account{" + "name='" + name + '\'' + ", level=" + level + '}';
    }
}
