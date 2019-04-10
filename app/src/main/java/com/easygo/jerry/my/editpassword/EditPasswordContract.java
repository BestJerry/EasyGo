package com.easygo.jerry.my.editpassword;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public class EditPasswordContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void updatePassword(String new_password);

        void checkPassword(String old_password);
    }
}
