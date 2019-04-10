package com.easygo.jerry.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.diary.diarylist.DiaryListFragment;
import com.easygo.jerry.my.diary.diarylist.DiaryListPresenter;
import com.easygo.jerry.util.ActivityUtils;

import java.nio.MappedByteBuffer;

import butterknife.BindView;

public class MapActivity extends BaseActivity {

    private static final String TAG = "MapActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private MapFragment mMapFragment;

    public static Intent newIntent(FragmentActivity activity) {

        Intent intent = new Intent(activity, MapActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("地图定位");

        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
            new MapPresenter(mMapFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mMapFragment, R.id.fragment_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }
}
