package com.easygo.jerry.searchlocation;

import com.easygo.jerry.data.LocationsDataSource;

public class SearchLocationPresenter implements SearchLocationContract.Presenter {

    private final LocationsDataSource mLocationsRepository;

    private final SearchLocationContract.View mSearchLocationView;


    private String mLocationId;

    private boolean mIsDataMissing;

    public SearchLocationPresenter(LocationsDataSource mLocationsRepository, SearchLocationContract.View mSearchLocationView, String mLocationId, boolean mIsDataMissing) {
        this.mLocationsRepository = mLocationsRepository;
        this.mSearchLocationView = mSearchLocationView;
        this.mLocationId = mLocationId;
        this.mIsDataMissing = mIsDataMissing;

        this.mSearchLocationView.setPresenter(this);
    }

    @Override
    public void saveTask(String title, String description) {

    }

    @Override
    public void populateTask() {

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    //    @Override
//    public void onTaskLoaded(Task task) {
//
//    }


}
