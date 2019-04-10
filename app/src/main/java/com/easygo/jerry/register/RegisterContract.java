package com.easygo.jerry.register;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface RegisterContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void register(String phone, String password);
    }
}
