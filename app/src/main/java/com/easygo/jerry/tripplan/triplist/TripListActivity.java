package com.easygo.jerry.tripplan.triplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.diary.diarylist.DiaryListFragment;
import com.easygo.jerry.my.diary.diarylist.DiaryListPresenter;
import com.easygo.jerry.tripplan.addtripplan.AddTripPlanActivity;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripListActivity extends BaseActivity {
    private static final String TAG = "TripListActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private TripListFragment mTripListFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, TripListActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("出行计划");

        if (mTripListFragment == null) {
            mTripListFragment = TripListFragment.newInstance();
            new TripListPresenter(mTripListFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mTripListFragment, R.id.fragment_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_trip_plan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_trip_plan:
                startActivity(AddTripPlanActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
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
