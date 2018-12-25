package com.easygo.jerry.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jerry on 2018/3/20.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T mPresenter;
    public Context mContext;


    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mPresenter = createPresenter();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter = null;
        mContext = null;
    }
}
