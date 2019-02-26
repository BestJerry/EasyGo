package com.easygo.jerry.searchlocation;

import android.support.v4.app.Fragment;

public class SearchLocationFragment extends Fragment implements SearchLocationContract.View{

    private SearchLocationContract.Presenter mPresenter;


    public static SearchLocationFragment newInstance() {
        return new SearchLocationFragment();
    }

    public SearchLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void showEmptyTaskError() {

    }

    @Override
    public void showTasksList() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SearchLocationContract.Presenter presenter) {

        mPresenter = presenter;
    }
}
