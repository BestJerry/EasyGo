package com.easygo.jerry.login;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface LoginContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void fetchLoginConfig();

        void login(String phone, String password);

        void updateLoginConfig(ContentValues contentValues);
    }
}
