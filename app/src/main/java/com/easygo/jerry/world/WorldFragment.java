package com.easygo.jerry.world;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easygo.jerry.R;
import com.easygo.jerry.adapter.DynamicAdapter;
import com.easygo.jerry.adapter.PoiAdapter;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.Dynamic;
import com.easygo.jerry.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/6
 */
public class WorldFragment extends BaseFragment implements WorldContract.View {

    private static final String TAG = "WorldFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private WorldContract.Presenter mPresenter;
    private DynamicAdapter mDynamicAdapter;


    public static WorldFragment newInstance() {
        return new WorldFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        initAdapter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview;
    }

    @Override
    public void setPresenter(WorldContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                mDynamicAdapter.clear();
                mDynamicAdapter.addItems((List<Dynamic>) object);
                break;
        }
    }


    private void initAdapter() {
        mDynamicAdapter = new DynamicAdapter();
        // 设置每个项目的点击事件
        mDynamicAdapter.setOnItemClickListener(new OnItemClickListener<Dynamic>() {
            @Override
            public void onClick(Dynamic item) {
                if (item != null) {
                }
            }
        });
        // 设置Adapter
        recyclerView.setAdapter(mDynamicAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchDynamic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
