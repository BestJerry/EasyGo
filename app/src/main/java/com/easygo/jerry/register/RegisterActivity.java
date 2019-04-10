package com.easygo.jerry.register;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.login.LoginFragment;
import com.easygo.jerry.login.LoginPresenter;
import com.easygo.jerry.util.ActivityUtils;

public class RegisterActivity extends BaseActivity {

    private RegisterFragment mRegisterFragment;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity,RegisterActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (mRegisterFragment == null){
            mRegisterFragment = RegisterFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mRegisterFragment
                ,R.id.fragment_container);
        new RegisterPresenter(mRegisterFragment);
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
