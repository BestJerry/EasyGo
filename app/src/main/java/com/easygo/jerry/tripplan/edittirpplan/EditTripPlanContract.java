package com.easygo.jerry.tripplan.edittirpplan;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface EditTripPlanContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void updateTripPlan(ContentValues contentValues);

        void deleteTripPlan(int id);

        void completeTripPlan(int id);
    }
}
