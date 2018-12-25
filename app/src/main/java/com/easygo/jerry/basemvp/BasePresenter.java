package com.easygo.jerry.basemvp;

import android.content.Context;

/**
 * Created by jerry on 2018/3/14.
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T mView;

    protected Context mContext;

    public BasePresenter(Context context, T View) {
        mContext = context;
        mView = View;
    }

    public T getView() {
        return mView;
    }

/*    public void attach(T view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }*/


}

