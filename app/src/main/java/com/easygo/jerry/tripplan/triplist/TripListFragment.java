package com.easygo.jerry.tripplan.triplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easygo.jerry.R;
import com.easygo.jerry.adapter.TripPlanAdapter;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.TripPlan;
import com.easygo.jerry.listener.OnItemClickListener;
import com.easygo.jerry.my.diary.editdiary.EditDiaryActivity;
import com.easygo.jerry.tripplan.edittirpplan.EditTripPlanActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class TripListFragment extends BaseFragment implements TripListContract.View {

    private static final String TAG = "TripListFragment";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private TripListContract.Presenter mPresenter;

    private TripPlanAdapter mTripPlanAdapter;

    public static TripListFragment newInstance() {
        return new TripListFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        initAdapter();
    }

    private void initAdapter() {
        mTripPlanAdapter = new TripPlanAdapter();
        // 设置每个项目的点击事件
        mTripPlanAdapter.setOnItemClickListener(new OnItemClickListener<TripPlan>() {
            @Override
            public void onClick(TripPlan item) {
                if (item != null) {
                    showTripPlan(item);
                }
            }
        });
        // 设置Adapter
        recyclerView.setAdapter(mTripPlanAdapter);
    }


    private void showTripPlan(TripPlan tripPlan) {
        Intent intent = EditTripPlanActivity.newIntent(getActivity());
        intent.putExtra("trip_plan", tripPlan);
        startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview;
    }

    @Override
    public void setPresenter(TripListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                mTripPlanAdapter.clear();
                mTripPlanAdapter.addItems((List<TripPlan>) object);
                break;
        }
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
        if (mPresenter!=null){
            mPresenter.fetchTripPlan();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
