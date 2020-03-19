package com.some.common.arouter;

public class RouterUrl {

    public static final String SCHEME = "/oversea/";
    public static final String SYSTEM = "/arouter/";//ARouter系统内置path

    //自定义全局降级策略
    public static final String SERVICE_DEGRADE = "/service/degrade";
    //重写跳转URL
    public static final String SERVICE_PATH_REPLACE = "/service/pathReplace";
    //传递自定义对象
    public static final String SERVICE_JSON = "/service/json";

    //赚取页面
    public static final String Main = SCHEME + "main";



    //----moduleA 模块---------//
    //视频聊/坐等
    private static final String SCHEME_VCHAT = "/moduleA/";
    public static final String A = SCHEME_VCHAT + "A";

}
