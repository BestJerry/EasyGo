package com.easygo.jerry.tripplan.addtripplan;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface AddTripPlanContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void addTripPlan(ContentValues contentValues);
    }
}
