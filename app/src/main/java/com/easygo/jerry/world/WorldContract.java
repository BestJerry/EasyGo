package com.easygo.jerry.world;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

public interface WorldContract {

    interface View extends BaseView<Presenter>{

    }


    interface Presenter extends BasePresenter{

        void fetchDynamic();
    }
}
