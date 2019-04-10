package com.easygo.jerry.route;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.overlay.DrivingRouteOverlay;
import com.easygo.jerry.overlay.WalkRouteOverlay;
import com.easygo.jerry.route.drive.DriveRouteDetailActivity;
import com.easygo.jerry.route.walk.WalkRouteDetailActivity;
import com.easygo.jerry.search.InputTipsActivity;
import com.easygo.jerry.util.AMapUtil;
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.easygo.jerry.map.MapFragment.RESULT_CODE_KEYWORDS;

public class RouteActivity extends BaseActivity implements AMapLocationListener, LocationSource, AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener {

    private static final int REQUEST_GET_START_CODE = 200;
    private static final int REQUEST_GET_END_CODE = 201;
    @BindView(R.id.firstline)
    TextView firstline;//mRotueTimeDes
    @BindView(R.id.secondline)
    TextView secondline;//mRouteDetailDes
    @BindView(R.id.detail)
    LinearLayout detail;
    @BindView(R.id.bottom_layout)
    RelativeLayout bottomLayout;//mBottomLayout
    @BindView(R.id.route_map)
    MapView routeMap;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.tv_start_point)
    TextView tvStartPoint;
    @BindView(R.id.linearlayout_1)
    LinearLayout linearlayout1;
    @BindView(R.id.tv_end_point)
    TextView tvEndPoint;
    @BindView(R.id.linearlayout_2)
    LinearLayout linearlayout2;
    @BindView(R.id.top_layout)
    RelativeLayout topLayout;
    @BindView(R.id.drive_linearlayout)
    LinearLayout carLinearlayout;
    @BindView(R.id.walk_linearlayout)
    LinearLayout walkLinearlayout;
    private AMap mAMap;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private String mCity;

    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private final int ROUTE_TYPE_CROSSTOWN = 4;

    private ProgressDialog progDialog = null;// 搜索时进度条

    private WalkRouteOverlay walkRouteOverlay;
    private WalkRouteResult mWalkRouteResult;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private MyLocationStyle mMyLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private OnLocationChangedListener onLocationChangedListener;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, RouteActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mContext = this.getApplicationContext();
        routeMap.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 设置页面监听
     */
    private void setUpMap() {
        mMyLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        // （1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        mMyLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);

        mMyLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        mMyLocationStyle.showMyLocation(true);

        mAMap.setMyLocationStyle(mMyLocationStyle);//设置定位蓝点的Style
        //mAMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.showIndoorMap(true);
        mAMap.showBuildings(true);
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        //mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(false);
        mUiSettings.setScaleControlsEnabled(false);
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);

        mAMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        mAMap.setMyLocationEnabled(true);// 触发定位并显示当前位置
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象

        mLocationOption = new AMapLocationClientOption();
        //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //设置定位间隔
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息
        mLocationOption.setNeedAddress(true);
        //设置网络请求超时毫秒数
        mLocationOption.setHttpTimeOut(20000);
        //设置为单次定位
        mLocationOption.setOnceLocation(true);
        //开启缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //设置为高精度定位模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        if (null != mLocationClient) {
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            //开始定位
            mLocationClient.startLocation();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_route;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = routeMap.getMap();
        }
        registerListener();
        setUpMap();
        initLocation();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        mAMap.setOnMapClickListener(this);
        mAMap.setOnMarkerClickListener(this);
    }

    public void onDriveClick(View view) {
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
        //routeMap.setVisibility(View.VISIBLE);
    }

    private void setfromandtoMarker(int mode) {
        if (mStartPoint == null) {
            ToastUtils.show(this, "未设置起点");
            return;
        }
        if (mEndPoint == null) {
            ToastUtils.show(this, "未设置终点");
            return;
        }
        if (mStartPoint.equals(mEndPoint)) {
            ToastUtils.show(mContext, "起点和终点不能相同");
            return;
        }

        mAMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mAMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
        if (mode == ROUTE_TYPE_DRIVE) {
            //开始搜索驾车路径规划方案
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
        } else if (mode == ROUTE_TYPE_WALK) {
            //开始搜索步行路径规划方案
            searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WALK_MULTI_PATH);
        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ToastUtils.show(mContext, "起点未设置");
            return;
        }
        if (mEndPoint == null) {
            ToastUtils.show(mContext, "终点未设置");
        }

        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
        dissmissProgressDialog();
        mAMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            mContext, mAMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    bottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    firstline.setText(des);
                    secondline.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    secondline.setText("打车约" + taxiCost + "元");
                    bottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    ToastUtils.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtils.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int errorCode) {
        dissmissProgressDialog();
        mAMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (walkRouteResult != null && walkRouteResult.getPaths() != null) {
                if (walkRouteResult.getPaths().size() > 0) {
                    mWalkRouteResult = walkRouteResult;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    if (walkRouteOverlay != null) {
                        walkRouteOverlay.removeFromMap();
                    }
                    walkRouteOverlay = new WalkRouteOverlay(
                            this, mAMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    bottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    firstline.setText(des);
                    secondline.setVisibility(View.GONE);
                    bottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    WalkRouteDetailActivity.class);
                            intent.putExtra("walk_path", walkPath);
                            intent.putExtra("walk_result",
                                    mWalkRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (walkRouteResult != null && walkRouteResult.getPaths() == null) {
                    ToastUtils.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtils.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * SearchPoiActivity返回后调用的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_START_CODE && data != null) {
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            //设置起点
            mStartPoint = tip.getPoint();
            tvStartPoint.setText(tip.getName());
        } else if (requestCode == REQUEST_GET_END_CODE && data != null) {
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            //设置终点
            mEndPoint = tip.getPoint();
            tvEndPoint.setText(tip.getName());
        } else if (resultCode == RESULT_CODE_KEYWORDS && data != null) {

        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        routeMap.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        routeMap.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        routeMap.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        routeMap.onDestroy();
    }

    @OnClick({R.id.tv_start_point, R.id.tv_end_point, R.id.drive_linearlayout, R.id.walk_linearlayout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_start_point:
                intent = new Intent(this, InputTipsActivity.class);
                intent.putExtra("city", mCity);
                startActivityForResult(intent, REQUEST_GET_START_CODE);
                break;
            case R.id.tv_end_point:
                intent = new Intent(this, InputTipsActivity.class);
                intent.putExtra("city", mCity);
                startActivityForResult(intent, REQUEST_GET_END_CODE);
                break;
            case R.id.drive_linearlayout:
                setfromandtoMarker(ROUTE_TYPE_DRIVE);
                break;
            case R.id.walk_linearlayout:
                setfromandtoMarker(ROUTE_TYPE_WALK);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && onLocationChangedListener != null) {
            onLocationChangedListener.onLocationChanged(aMapLocation);
            if (aMapLocation.getErrorCode() == 0) {
                //                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //                amapLocation.getLatitude();//获取纬度
                //                amapLocation.getLongitude();//获取经度
                //                amapLocation.getAccuracy();//获取精度信息
                //                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                //                amapLocation.getCountry();//国家信息
                //                amapLocation.getProvince();//省信息
                //                amapLocation.getCity();//城市信息
                //                amapLocation.getDistrict();//城区信息
                //                amapLocation.getStreet();//街道信息
                //                amapLocation.getStreetNum();//街道门牌号信息
                //                amapLocation.getCityCode();//城市编码
                //                amapLocation.getAdCode();//地区编码
                //                amapLocation.getAoiName();//获取当前定位点的AOI信息
                //                amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                //                amapLocation.getFloor();//获取当前室内定位的楼层
                //                amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                //                //获取定位时间
                //                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //                Date date = new Date(amapLocation.getTime());
                //                df.format(date);

                //获取当前位置的经度
                double longitude = aMapLocation.getLongitude();
                //获取当前位置的纬度
                double latitude = aMapLocation.getLatitude();
                //获取当前所在城市
                mCity = aMapLocation.getCity();
                //构建起点的LatLonPoint对象
                if (mStartPoint == null) {
                    mStartPoint = new LatLonPoint(latitude, longitude);
                } else {
                    mStartPoint.setLatitude(latitude);
                    mStartPoint.setLongitude(longitude);
                }

                tvStartPoint.setText("我的位置");
            } else {
                //                String errText = "定位失败：" + aMapLocation.getErrorInfo();
                //                ToastUtils.show(this, errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }
}
