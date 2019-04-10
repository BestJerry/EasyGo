package com.easygo.jerry.map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AmapPageType;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.overlay.PoiOverlay;
import com.easygo.jerry.search.InputTipsActivity;
import com.easygo.jerry.search.SearchPoiActivity;
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/6
 */
public class MapFragment extends BaseFragment implements MapContract.View, AMapLocationListener, AMap.OnMyLocationChangeListener,
        LocationSource, AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter, PoiSearch.OnPoiSearchListener {

    private static final String TAG = "MapFragment";

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;
    public static final int RESULT_CODE_KEYWORDS = 102;
    @BindView(R.id.mapview_map)
    MapView mapviewMap;
    @BindView(R.id.clean_keywords)
    ImageView cleanKeywords;
    @BindView(R.id.main_keywords)
    TextView mainKeywords;
    @BindView(R.id.bt_locate)
    Button btLocate;
    Unbinder unbinder;

    private AMap mAMap;
    private String mKeyWords = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private Marker mPoiMarker;

    private String mCity;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private MyLocationStyle mMyLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private MapContract.Presenter mPresenter;

    //    //声明定位回调监听器
    //    public AMapLocationListener mLocationListener = new AMapLocationListener() {
    //        @Override
    //        public void onLocationChanged(AMapLocation aMapLocation) {
    //
    //        }
    //    };
    private OnLocationChangedListener onLocationChangedListener;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mapviewMap.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mapviewMap.getMap();
        }
        mKeyWords = "";
        setUpMap();
        initLocation();
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
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.showIndoorMap(true);
        mAMap.showBuildings(true);
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
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

    //设置定位回调监听器
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    /**
     * 声明定位回调监听器
     *
     * @param aMapLocation 回调返回的定位结果
     */
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
            } else {
                String errText = "定位失败：" + aMapLocation.getErrorInfo();
                ToastUtils.show(getActivity(), errText);
            }
        }
    }

    /**
     * 点击Mark标记回调，弹出自定义窗口
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(final Marker marker) {
        //实例化弹窗View视图
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        //显示地点名称
        title.setText(marker.getTitle());
        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        //显示地点详细位置
        snippet.setText(marker.getSnippet());
        ImageButton collect_button = (ImageButton) view
                .findViewById(R.id.ib_collect);
        ImageButton navigate_button = (ImageButton) view
                .findViewById(R.id.ib_navigate);
        navigate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调起导航
                startAMapNavi(marker);
            }
        });
        collect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收藏地点
                mPresenter.collectLocation(marker);
            }
        });
        return view;
    }

    private void startAMapNavi(Marker marker) {

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        // 启动定位
        Log.i(TAG, "startLocation");
        mLocationClient.startLocation();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(getActivity());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + mKeyWords);
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
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keywords) {
        showProgressDialog();// 显示进度框
        currentPage = 1;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, "", Constants.DEFAULT_CITY);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查第一页
        query.setPageNum(currentPage);

        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        // 返回 true 则表示接口已响应事件，否则返回false
        return true;
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtils.show(getActivity(), infomation);
    }

    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        mAMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtils.show(getActivity(),
                                R.string.no_result);
                    }
                }
            } else {
                ToastUtils.show(getActivity(),
                        R.string.no_result);
            }
        } else {
            ToastUtils.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 输入提示activity选择结果后的处理逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_INPUTTIPS && data
                != null) {
            mAMap.clear();
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            if (tip.getPoiID() == null || tip.getPoiID().equals("")) {
                doSearchQuery(tip.getName());
            } else {
                addTipMarker(tip);
            }
            mainKeywords.setText(tip.getName());
            if (!tip.getName().equals("")) {
                cleanKeywords.setVisibility(View.VISIBLE);
            }
        } else if (resultCode == RESULT_CODE_KEYWORDS && data != null) {
            mAMap.clear();
            String keywords = data.getStringExtra(Constants.KEY_WORDS_NAME);
            if (keywords != null && !keywords.equals("")) {
                doSearchQuery(keywords);
            }
            mainKeywords.setText(keywords);
            if (!keywords.equals("")) {
                cleanKeywords.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 用marker展示输入提示list选中数据
     *
     * @param tip
     */
    private void addTipMarker(Tip tip) {
        if (tip == null) {
            return;
        }
        mPoiMarker = mAMap.addMarker(new MarkerOptions());
        LatLonPoint point = tip.getPoint();
        if (point != null) {
            LatLng markerPosition = new LatLng(point.getLatitude(), point.getLongitude());
            mPoiMarker.setPosition(markerPosition);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));
        }
        mPoiMarker.setTitle(tip.getName());
        mPoiMarker.setSnippet(tip.getAddress());
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    ToastUtils.show(getActivity(), "收藏成功");
                } else {
                    ToastUtils.show(getActivity(), "该兴趣点已收藏～");
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        mapviewMap.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapviewMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapviewMap.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapviewMap.onSaveInstanceState(outState);
    }

    @OnClick({R.id.clean_keywords, R.id.main_keywords, R.id.bt_locate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clean_keywords:
                mainKeywords.setText("");
                mAMap.clear();
                cleanKeywords.setVisibility(View.GONE);
                break;
            case R.id.main_keywords:
                Intent intent = new Intent(getActivity(), InputTipsActivity.class);
                intent.putExtra("city", mCity);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_locate:
                startLocation();
                break;
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）

    }
}
