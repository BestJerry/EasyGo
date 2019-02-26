package com.easygo.jerry.searchlocation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.easygo.jerry.R;

public class SearchLocationActivity extends AppCompatActivity {


    private SearchLocationPresenter mSearchLocationPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        SearchLocationFragment searchLocationFragment = (SearchLocationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);


        //mSearchLocationPresenter = new SearchLocationPresenter();
    }
}
