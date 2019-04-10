package com.easygo.jerry.mainpage;


public class MainPagePresenter implements MainPageContract.Presenter {

    private MainPageContract.View mView;

    public MainPagePresenter(MainPageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
}
