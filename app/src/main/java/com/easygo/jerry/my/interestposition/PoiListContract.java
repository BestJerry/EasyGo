package com.easygo.jerry.my.interestposition;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/7
 */
public interface PoiListContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void fetchPoi();
    }
}
