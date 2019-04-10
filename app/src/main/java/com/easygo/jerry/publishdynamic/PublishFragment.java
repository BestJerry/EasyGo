package com.easygo.jerry.publishdynamic;

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
import com.easygo.jerry.util.DateUtil;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class PublishFragment extends BaseFragment implements PublishContract.View {
    private static final String TAG = "PublishFragment";
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.tv_recommend_level)
    TextView tvRecommendLevel;
    @BindView(R.id.et_recommend_level)
    EditText etRecommendLevel;
    @BindView(R.id.bt_publish)
    Button btPublish;
    Unbinder unbinder;

    private PublishContract.Presenter mPresenter;

    private ContentValues mContentValues;

    public static PublishFragment newInstance() {
        return new PublishFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etContent.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish;
    }

    @Override
    public void setPresenter(PublishContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((boolean) object) {
                    Toast.makeText(getActivity(), "动态已发布～", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.bt_publish)
    public void onViewClicked() {
        if (etContent.getText().toString().equals("")||etLocation.getText().toString().equals("")||
                etRecommendLevel.getText().toString().equals("")) {
            ToastUtils.show(getActivity(), "请先完善所有信息～");
            return;
        }
        putData();
        mPresenter.publishDynamic(mContentValues);
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("u_id", Constants.USER_ID);
        mContentValues.put("content", etContent.getText().toString());
        mContentValues.put("date", DateUtil.getDate());
        mContentValues.put("location", etLocation.getText().toString());
        mContentValues.put("recommend_level", etRecommendLevel.getText().toString());
    }
}
