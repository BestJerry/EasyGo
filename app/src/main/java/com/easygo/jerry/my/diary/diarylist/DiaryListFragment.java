package com.easygo.jerry.my.diary.diarylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.easygo.jerry.R;
import com.easygo.jerry.adapter.DiaryAdapter;
import com.easygo.jerry.adapter.PoiAdapter;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.Diary;
import com.easygo.jerry.listener.OnItemClickListener;
import com.easygo.jerry.my.diary.editdiary.EditDiaryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class DiaryListFragment extends BaseFragment implements DiaryListContract.View {

    private static final String TAG = "DiaryListFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private DiaryListContract.Presenter mPresenter;

    private DiaryAdapter mDiaryAdapter;

    public static DiaryListFragment newInstance() {
        return new DiaryListFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        initAdapter();
    }

    private void initAdapter() {
        mDiaryAdapter = new DiaryAdapter();
        // 设置每个项目的点击事件
        mDiaryAdapter.setOnItemClickListener(new OnItemClickListener<Diary>() {
            @Override
            public void onClick(Diary item) {
                if (item != null) {
                    showDiary(item);
                }
            }
        });
        // 设置Adapter
        recyclerView.setAdapter(mDiaryAdapter);
    }

    private void showDiary(Diary diary) {

        Intent intent = EditDiaryActivity.newIntent(getActivity());
        intent.putExtra("diary", diary);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview;
    }

    @Override
    public void setPresenter(DiaryListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                mDiaryAdapter.clear();
                mDiaryAdapter.addItems((List<Diary>) object);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchDiary();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
