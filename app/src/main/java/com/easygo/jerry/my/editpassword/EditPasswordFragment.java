package com.easygo.jerry.my.editpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.services.share.ShareSearch;
import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.tripplan.edittirpplan.EditTripPlanContract;
import com.easygo.jerry.util.ToastUtils;

import java.sql.SQLClientInfoException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/6
 */
public class EditPasswordFragment extends BaseFragment implements EditPasswordContract.View {

    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password_again)
    EditText etNewPasswordAgain;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    Unbinder unbinder;

    private EditPasswordContract.Presenter mPresenter;

    private String old_password;
    private String new_password;
    private String confirm_password;

    public static EditPasswordFragment newInstance() {
        return new EditPasswordFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etOldPassword.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_password;
    }

    @Override
    public void setPresenter(EditPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    ToastUtils.show(getActivity(), "修改密码成功～");
                } else {
                    ToastUtils.show(getActivity(), "出了点小问题～");
                }
                break;
            case 0x002:
                if ((Boolean) object) {
                    mPresenter.updatePassword(new_password);
                } else {
                    ToastUtils.show(getActivity(), "原密码输入错误");
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

    @OnClick({R.id.bt_cancel, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                getActivity().finish();
                break;
            case R.id.bt_confirm:
                old_password = etOldPassword.getText().toString();
                new_password = etNewPassword.getText().toString();
                confirm_password = etNewPasswordAgain.getText().toString();
                if (old_password.equals("") || new_password.equals("") || confirm_password.equals("")) {
                    ToastUtils.show(getActivity(), "密码不可为空");
                } else if (new_password.equals(old_password)) {
                    ToastUtils.show(getActivity(), "原密码与新密码不能相同");
                } else {
                    // Presenter执行更新数据库操作
                    mPresenter.checkPassword(old_password);
                }

                break;
        }
    }
}
