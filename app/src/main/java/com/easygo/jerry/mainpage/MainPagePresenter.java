package com.easygo.jerry.mainpage;

import com.easygo.jerry.util.schedulers.BaseSchedulerProvider;



import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPagePresenter implements MainPageContract.Presenter {

    private final MainPageContract.View mMainPageView;

    private final BaseSchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    public MainPagePresenter(MainPageContract.View mainPageView, BaseSchedulerProvider schedulerProvider) {
        mMainPageView = checkNotNull(mainPageView);
        mSchedulerProvider = checkNotNull(schedulerProvider);

        mCompositeDisposable = new CompositeDisposable();
        mMainPageView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }
}
