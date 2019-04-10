package com.easygo.jerry.publishdynamic;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.myinfo.MyInfoActivity;
import com.easygo.jerry.my.myinfo.MyInfoFragment;
import com.easygo.jerry.my.myinfo.MyInfoPresenter;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;

public class PublishActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private PublishFragment mPublishFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, PublishActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发布动态");

        if (mPublishFragment == null) {
            mPublishFragment = PublishFragment.newInstance();
            new PublishPresenter(mPublishFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mPublishFragment, R.id.fragment_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_toolbar;
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }
}
