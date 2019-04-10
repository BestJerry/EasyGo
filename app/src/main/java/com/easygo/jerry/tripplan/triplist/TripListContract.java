package com.easygo.jerry.tripplan.triplist;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface TripListContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        //获取出行计划数据
        void fetchTripPlan();
    }
}
