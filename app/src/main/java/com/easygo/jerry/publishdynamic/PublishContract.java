package com.easygo.jerry.publishdynamic;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/7
 */
public class PublishContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void publishDynamic(ContentValues contentValues);
    }
}
