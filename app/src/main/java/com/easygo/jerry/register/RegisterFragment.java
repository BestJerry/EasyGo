package com.easygo.jerry.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.idst.nls.nlsclientsdk.requests.Constant;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Create by wujiewei on 2019/4/6
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.tv_register_title)
    TextView tvRegisterTitle;
    @BindView(R.id.et_input_account)
    EditText etInputAccount;
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.bt_register)
    Button btRegister;
    Unbinder unbinder;

    private RegisterContract.Presenter mPresenter;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    Intent intent = new Intent();
                    intent.putExtra("phone", etInputAccount.getText().toString());
                    intent.putExtra("password", etInputPassword.getText().toString());
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                } else {

                    ToastUtils.show(getActivity(), "该手机号已经被注册");
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.image_back, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
                break;
            case R.id.bt_register:
                if (etInputAccount.getText().toString().length() != 11) {
                    ToastUtils.show(getActivity(), "请输入11位手机号码");
                    return;
                } else if (etInputPassword.getText().toString().equals("")) {
                    ToastUtils.show(getActivity(), "密码不可为空");
                    return;
                }
                mPresenter.register(etInputAccount.getText().toString(),
                        etInputPassword.getText().toString());
                break;
        }
    }
}
