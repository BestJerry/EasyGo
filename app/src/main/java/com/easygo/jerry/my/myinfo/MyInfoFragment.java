package com.easygo.jerry.my.myinfo;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.UserInfo;
import com.easygo.jerry.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class MyInfoFragment extends BaseFragment implements MyInfoContract.View {

    private static final String TAG = "MyInfoFragment";
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tv_hobby)
    TextView tvHobby;
    @BindView(R.id.et_hobby)
    EditText etHobby;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.et_introduction)
    EditText etIntroduction;
    @BindView(R.id.bt_update)
    Button btUpdate;
    Unbinder unbinder;

    private ContentValues mContentValues;

    private MyInfoContract.Presenter mPresenter;

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etName.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_info;
    }

    @Override
    public void setPresenter(MyInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((boolean) object) {
                    Toast.makeText(getActivity(), "更新信息成功", Toast.LENGTH_SHORT).show();
                    Constants.USER_NAME = etName.getText().toString();
                } else {
                    Toast.makeText(getActivity(), "出了点小问题～", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0x002:
                if (object != null) {
                    setContent((UserInfo) object);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchUserInfo();
    }

    private void setContent(UserInfo userInfo) {
        etName.setText(userInfo.getName());
        etSex.setText(userInfo.getSex());
        etAge.setText(userInfo.getAge());
        etEmail.setText(userInfo.getEmail());
        etHobby.setText(userInfo.getHobby());
        etIntroduction.setText(userInfo.getIntroduction());
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

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        if (etName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "昵称不得为空", Toast.LENGTH_SHORT).show();
            return;
        }
        putData();
        mPresenter.updateUserInfo(mContentValues);
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("id", Constants.USER_ID);
        mContentValues.put("name", etName.getText().toString());
        mContentValues.put("sex", etSex.getText().toString());
        mContentValues.put("age", etAge.getText().toString());
        mContentValues.put("email", etEmail.getText().toString());
        mContentValues.put("introduction", etIntroduction.getText().toString());
        mContentValues.put("hobby", etHobby.getText().toString());
        mContentValues.put("is_complete", 1);
    }
}
