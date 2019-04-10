package com.easygo.jerry.my.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.my.MyFragment;
import com.easygo.jerry.my.editpassword.EditPasswordFragment;
import com.easygo.jerry.my.editpassword.EditPasswordPresenter;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private MyInfoFragment mMyInfoFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, MyInfoActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的信息");

        if (mMyInfoFragment == null) {
            mMyInfoFragment = MyInfoFragment.newInstance();
            new MyInfoPresenter(mMyInfoFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mMyInfoFragment, R.id.fragment_container);
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
