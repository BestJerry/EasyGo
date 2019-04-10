package com.easygo.jerry.tripplan.addtripplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.diary.editdiary.EditDiaryFragment;
import com.easygo.jerry.my.diary.editdiary.EditDiaryPresenter;
import com.easygo.jerry.tripplan.triplist.TripListActivity;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;

public class AddTripPlanActivity extends BaseActivity {

    private static final String TAG = "AddTripPlanActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private AddTripPlanFragment mAddTripPlanFragment;

    public static Intent newIntent(TripListActivity tripListActivity) {
        Intent intent = new Intent(tripListActivity, AddTripPlanActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新增计划");

        if (mAddTripPlanFragment == null) {
            mAddTripPlanFragment = AddTripPlanFragment.newInstance();
            new AddTripPlanPresenter(mAddTripPlanFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mAddTripPlanFragment, R.id.fragment_container);
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
