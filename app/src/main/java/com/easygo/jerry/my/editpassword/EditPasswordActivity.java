package com.easygo.jerry.my.editpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.mainpage.MainPageFragment;
import com.easygo.jerry.util.ActivityUtils;

import butterknife.BindView;

public class EditPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private EditPasswordFragment mEditPasswordFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, EditPasswordActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("修改密码");

        if (mEditPasswordFragment == null) {
            mEditPasswordFragment = EditPasswordFragment.newInstance();
            new EditPasswordPresenter(mEditPasswordFragment);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mEditPasswordFragment, R.id.fragment_container);
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
