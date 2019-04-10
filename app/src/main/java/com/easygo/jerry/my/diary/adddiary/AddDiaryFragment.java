package com.easygo.jerry.my.diary.adddiary;

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
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.DateUtil;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class AddDiaryFragment extends BaseFragment implements AddDiaryContract.View {
    private static final String TAG = "AddDiaryFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_complete)
    Button btComplete;
    Unbinder unbinder;

    private AddDiaryContract.Presenter mPresenter;

    private ContentValues mContentValues;

    public static AddDiaryFragment newInstance() {
        return new AddDiaryFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etTitle.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_diary;
    }

    @Override
    public void setPresenter(AddDiaryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((boolean) object) {
                    Toast.makeText(getActivity(), "已帮您保存日记～", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "出了点小问题～", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_complete)
    public void onViewClicked() {
        if (etTitle.getText().toString().equals("")||etContent.getText().toString().equals("")) {
            ToastUtils.show(getActivity(), "日记还没写好哦～");
            return;
        }
        putData();
        mPresenter.addDiary(mContentValues);
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("u_id", Constants.USER_ID);
        mContentValues.put("title", etTitle.getText().toString());
        mContentValues.put("content", etContent.getText().toString());
        mContentValues.put("date",DateUtil.getDate());
    }
}
