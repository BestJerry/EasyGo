package com.easygo.jerry.my.diary.editdiary;

import android.content.ContentValues;

import com.easygo.jerry.base.BasePresenter;
import com.easygo.jerry.base.BaseView;

/**
 * Create by wujiewei on 2019/4/7
 */
public interface EditDiaryContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        void updateDiary(ContentValues contentValues);

        //删除计划
        void deleteDiary(int id);
    }
}
