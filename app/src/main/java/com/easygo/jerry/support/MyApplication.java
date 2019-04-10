package com.easygo.jerry.support;

import android.app.Application;

import com.easygo.jerry.data.db.DatabaseHelper;

/**
 * Created by jerry on 2018/3/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       DatabaseHelper.init(this);
    }
}
