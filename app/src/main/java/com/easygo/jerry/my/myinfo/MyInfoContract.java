package com.easygo.jerry.my.myinfo;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/7
 */
public interface MyInfoContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void fetchUserInfo();

        void updateUserInfo(ContentValues contentValues);
    }
}
