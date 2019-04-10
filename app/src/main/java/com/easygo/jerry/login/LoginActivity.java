package com.easygo.jerry.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.util.ActivityUtils;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private LoginFragment mLoginFragment;
    @Override
    protected void initView(Bundle savedInstanceState) {

        if (mLoginFragment == null){
            mLoginFragment = LoginFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mLoginFragment
                ,R.id.fragment_container);
        new LoginPresenter(mLoginFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container_without_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
