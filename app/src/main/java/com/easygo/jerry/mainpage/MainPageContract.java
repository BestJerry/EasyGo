package com.easygo.jerry.mainpage;

import android.content.Context;

import com.easygo.jerry.BasePresenter;
import com.easygo.jerry.BaseView;
import com.easygo.jerry.searchlocation.SearchLocationContract;

public interface MainPageContract {

    interface View extends BaseView<Presenter>{

        boolean isActive();

    }


    interface Presenter extends BasePresenter{

        void result(int requestCode, int resultCode);
    }
}
