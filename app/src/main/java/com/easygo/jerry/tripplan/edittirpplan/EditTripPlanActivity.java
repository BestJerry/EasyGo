package com.easygo.jerry.tripplan.edittirpplan;

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
import com.easygo.jerry.my.diary.editdiary.EditDiaryFragment;
import com.easygo.jerry.my.diary.editdiary.EditDiaryPresenter;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTripPlanActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private EditTripPlanFragment mEditTripPlanFragment;

    private EditTripPlanPresenter mEditTripPlanPresenter;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, EditTripPlanActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("当前计划");

        if (mEditTripPlanFragment == null) {
            mEditTripPlanFragment = EditTripPlanFragment.newInstance();
            mEditTripPlanPresenter = new EditTripPlanPresenter(mEditTripPlanFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mEditTripPlanFragment, R.id.fragment_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_toolbar;
    }

    //创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_trip_plan_menu, menu);
        return true;
    }

    //设置每个菜单项的触发事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_diary:
                mEditTripPlanPresenter.deleteTripPlan(mEditTripPlanFragment.getTripPlan().getId());
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
