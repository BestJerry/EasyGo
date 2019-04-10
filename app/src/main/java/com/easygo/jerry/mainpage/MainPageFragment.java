package com.easygo.jerry.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amap.api.maps.model.Poi;
import com.amap.api.maps.offlinemap.OfflineMapActivity;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.autonavi.ae.route.route.Route;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.map.MapActivity;
import com.easygo.jerry.publishdynamic.PublishActivity;
import com.easygo.jerry.route.RouteActivity;
import com.easygo.jerry.toolbox.ToolBoxActivity;
import com.easygo.jerry.tripplan.triplist.TripListActivity;
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainPageFragment extends BaseFragment implements MainPageContract.View, INaviInfoCallback {
    private static final String TAG = "MainPageFragment";
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.linearlayout_2)
    LinearLayout linearlayout2;
    @BindView(R.id.linearlayout_1)
    LinearLayout linearlayout1;
    @BindView(R.id.linearlayout_3)
    LinearLayout linearlayout3;
    @BindView(R.id.linearlayout_5)
    LinearLayout linearlayout5;
    @BindView(R.id.linearlayout_4)
    LinearLayout linearlayout4;
    @BindView(R.id.linearlayout_6)
    LinearLayout linearlayout6;
    @BindView(R.id.linearlayout_7)
    LinearLayout linearlayout7;
    Unbinder unbinder;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void setPresenter(MainPageContract.Presenter presenter) {

    }

    @Override
    public void setData(int state, Object object) {

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

    @OnClick({R.id.linearlayout_2, R.id.linearlayout_1, R.id.linearlayout_3, R.id.linearlayout_5, R.id.linearlayout_4, R.id.linearlayout_6, R.id.linearlayout_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearlayout_2:
                startActivity(RouteActivity.newIntent(getActivity()));
                break;
            case R.id.linearlayout_1:
                startActivity(MapActivity.newIntent(getActivity()));
                break;
            case R.id.linearlayout_3:
                AmapNaviPage.getInstance().showRouteActivity(getActivity().getApplicationContext(),
                        new AmapNaviParams(null, null, new Poi("", null, ""),
                                AmapNaviType.DRIVER), this);
                break;
            case R.id.linearlayout_5:
                startActivity(TripListActivity.newIntent(getActivity()));
                break;
            case R.id.linearlayout_4:
                startActivity(new Intent(getActivity(),
                        OfflineMapActivity.class));
                break;
            case R.id.linearlayout_6:
                if (Constants.IS_DETAIL_COMPLETE==1){
                    startActivity(PublishActivity.newIntent(getActivity()));
                }else{
                    ToastUtils.show(getActivity(),"请先完善个人信息～");
                }
                break;
            case R.id.linearlayout_7:
                startActivity(ToolBoxActivity.newIntent(getActivity()));
                break;
        }
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
}
