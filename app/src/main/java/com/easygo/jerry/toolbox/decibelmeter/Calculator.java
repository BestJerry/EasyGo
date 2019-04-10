package com.easygo.jerry.toolbox.decibelmeter;

/**
 * Create by wujiewei on 2019/4/7
 */
public class Calculator {
    public static float dbstart = 0; //初始值记录

    public static float dblast = dbstart; //最新值

    public static void setDbCount(float dbValue) {
        dbstart = dblast + (dbValue - dblast) * 0.2f; //最新值赋予以及保留两位小数
        dblast = dbstart;
    }
}
