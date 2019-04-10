package com.easygo.jerry.my.diary.diarylist;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/7
 */
public class DiaryListContract {

    interface View extends BaseView<Presenter>{

    }


    interface Presenter extends BasePresenter{

        void fetchDiary();
    }
}
