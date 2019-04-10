package com.easygo.jerry.map;

import android.content.IntentFilter;

import com.amap.api.maps.model.Marker;
import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/6
 */
public interface MapContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void collectLocation(Marker marker);
    }
}
