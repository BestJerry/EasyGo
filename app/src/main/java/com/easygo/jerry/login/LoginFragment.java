package com.easygo.jerry.login;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.idst.nls.nlsclientsdk.requests.Constant;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.LoginConfig;
import com.easygo.jerry.main.MainActivity;
import com.easygo.jerry.register.RegisterActivity;
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.ToastUtils;
import com.easygo.jerry.widget.CustomProgressDialog;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Create by wujiewei on 2019/4/6
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    private static final String TAG = "LoginFragment";
    @BindView(R.id.bt_close)
    Button btClose;
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_goto_register)
    TextView tvGotoRegister;
    @BindView(R.id.bt_immediately_login)
    Button btImmediatelyLogin;
    @BindView(R.id.ck_remember_password)
    CheckBox ckRememberPassword;
    @BindView(R.id.ck_auto_login)
    CheckBox ckAutoLogin;
    Unbinder unbinder;

    private LoginContract.Presenter mPresenter;

    private CustomProgressDialog mProgressDialog;

    private static Thread sThread;

    private MyHandler mHandler;

    private ContentValues mContentValues;

    private static class MyHandler extends Handler {
        private final WeakReference<LoginFragment> mFragmentWeakReference;

        public MyHandler(LoginFragment fragment) {
            mFragmentWeakReference = new WeakReference<LoginFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginFragment fragment = mFragmentWeakReference.get();
            if (fragment != null) {

                switch (msg.what) {
                    case 0x001:
                        fragment.mProgressDialog.dismiss();
                        fragment.mProgressDialog = null;
                        fragment.startActivity(MainActivity.newIntent(fragment.getActivity()));
                        break;
                }
            }
        }
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHandler = new MyHandler(this);
        mContentValues = new ContentValues();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragement_login;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    mContentValues.clear();
                    mContentValues.put("u_id", Constants.USER_ID);
                    mContentValues.put("phone", etLoginAccount.getText().toString());
                    mContentValues.put("password", etLoginPassword.getText().toString());
                    mContentValues.put("is_remember_pwd", ckRememberPassword.isChecked() ? 1 : 0);
                    mContentValues.put("is_auto_login", ckAutoLogin.isChecked() ? 1 : 0);
                    mPresenter.updateLoginConfig(mContentValues);
                    Message message = new Message();
                    message.what = 0x001;
                    mHandler.sendMessage(message);
                } else {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                    Toast.makeText(getActivity(), "手机号或密码不正确", Toast.LENGTH_SHORT).show();
                }

                break;
            case 0x002:
                if (object!=null){
                    LoginConfig loginConfig = (LoginConfig) object;
                    etLoginAccount.setText(loginConfig.getPhone());
                    if (loginConfig.getIs_remember_pwd() == 1) {
                        ckRememberPassword.setChecked(true);
                        etLoginPassword.setText(loginConfig.getPassword());
                    }

                    if (loginConfig.getIs_auto_login() == 1) {
                        ckAutoLogin.setChecked(true);
                        login();
                    }
                }else{
                    ToastUtils.show(getActivity(),"出了点小问题～");
                }

                break;
            case 0x003:
                if ((Boolean) object) {

                } else {

                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchLoginConfig();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_close, R.id.tv_goto_register, R.id.bt_immediately_login, R.id.ck_remember_password, R.id.ck_auto_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                getActivity().finish();
                break;
            case R.id.tv_goto_register:
                startActivityForResult(RegisterActivity.newIntent(getActivity()),
                        0x001);
                break;
            case R.id.bt_immediately_login:
                login();
                break;
            case R.id.ck_remember_password:
                break;
            case R.id.ck_auto_login:
                if (!ckRememberPassword.isChecked()) {
                    ckRememberPassword.setChecked(true);
                }
                break;
        }
    }

    /**
     * 点击立即登录按钮执行此方法
     */
    private void login() {
        if (etLoginAccount.getText().toString().length() != 11) {
            ToastUtils.show(getActivity(), "请输入11位手机号码");
            return;
        } else if (etLoginPassword.getText().toString().equals("")) {
            ToastUtils.show(getActivity(), "密码不可为空");
            return;
        }
        mProgressDialog = new CustomProgressDialog(getActivity());
        mProgressDialog.show();
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sThread.start();
        mPresenter.login(etLoginAccount.getText().toString(),
                etLoginPassword.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0x001:
                if (resultCode == RESULT_OK) {
                    etLoginAccount.setText(data.getStringExtra("phone"));
                    etLoginPassword.setText(data.getStringExtra("password"));
                    login();
                }
                break;
        }
    }
}
