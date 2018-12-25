package com.easygo.jerry.basemvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by jerry on 2018/3/15.
 */

public abstract class BaseToolBarActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    public Context mContext;
    public String mToolBarTitle;
    protected Intent mIntent;

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
    }


}
