package com.easygo.jerry.my.interestposition;

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
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.easygo.jerry.R;
import com.easygo.jerry.adapter.PoiAdapter;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.Poi;
import com.easygo.jerry.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class PoiListFragment extends BaseFragment implements PoiListContract.View, INaviInfoCallback {

    private static final String TAG = "PoiListFragment";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private PoiListContract.Presenter mPresenter;
    private PoiAdapter mPoiAdapter;

    public static PoiListFragment newInstance() {
        return new PoiListFragment();
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
    public void setPresenter(PoiListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                mPoiAdapter.clear();
                mPoiAdapter.addItems((List<Poi>) object);
                break;
        }
    }

    private void initAdapter() {
        mPoiAdapter = new PoiAdapter();
        // 设置每个项目的点击事件
        mPoiAdapter.setOnItemClickListener(new OnItemClickListener<Poi>() {
            @Override
            public void onClick(Poi item) {
                if (item != null) {
                    navigation(item);
                }
            }
        });
        // 设置Adapter
        recyclerView.setAdapter(mPoiAdapter);
    }

    private void navigation(Poi item) {
        AmapNaviPage.getInstance().showRouteActivity(getActivity().getApplicationContext(),
                new AmapNaviParams(null, null, new com.amap.api.maps.model.Poi(item.getTitle(),
                        new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())), ""), AmapNaviType.DRIVER), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchPoi();
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

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
