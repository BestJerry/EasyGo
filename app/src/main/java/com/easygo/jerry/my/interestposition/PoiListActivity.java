package com.easygo.jerry.my.interestposition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.myinfo.MyInfoFragment;
import com.easygo.jerry.my.myinfo.MyInfoPresenter;
import com.easygo.jerry.util.ActivityUtils;

import java.security.PolicySpi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoiListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private PoiListFragment mPoiListFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, PoiListActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的兴趣点");

        if (mPoiListFragment == null) {
            mPoiListFragment = PoiListFragment.newInstance();
            new PoiListPresenter(mPoiListFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mPoiListFragment, R.id.fragment_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }
}
