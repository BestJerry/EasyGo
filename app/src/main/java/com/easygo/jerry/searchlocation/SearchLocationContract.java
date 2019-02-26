package com.easygo.jerry.searchlocation;

import com.easygo.jerry.BasePresenter;
import com.easygo.jerry.BaseView;

public interface SearchLocationContract  {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }

}
