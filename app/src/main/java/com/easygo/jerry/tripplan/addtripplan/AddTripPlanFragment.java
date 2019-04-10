package com.easygo.jerry.tripplan.addtripplan;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.easygo.jerry.data.db.DatabaseHelper.TABLE_TRAVEL_PLAN;

/**
 * Create by wujiewei on 2019/4/7
 */
public class AddTripPlanFragment extends BaseFragment implements AddTripPlanContract.View {
    private static final String TAG = "AddTripPlanFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.et_start_time)
    EditText etStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_end_time)
    EditText etEndTime;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    Unbinder unbinder;

    private AddTripPlanContract.Presenter mPresenter;

    private ContentValues mContentValues;

    public static AddTripPlanFragment newInstance() {
        return new AddTripPlanFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etTitle.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_trip_plan;
    }

    @Override
    public void setPresenter(AddTripPlanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((boolean) object) {
                    Toast.makeText(getActivity(), "计划已经新建成功～", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        if (etTitle.getText().toString().equals("")||etLocation.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "请先完善必填信息～", Toast.LENGTH_SHORT).show();
            return;
        }
        putData();
        mPresenter.addTripPlan(mContentValues);
    }


    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("u_id", Constants.USER_ID);
        mContentValues.put("title", etTitle.getText().toString());
        mContentValues.put("location", etLocation.getText().toString());
        mContentValues.put("date", etDate.getText().toString());
        mContentValues.put("start_time", etStartTime.getText().toString());
        mContentValues.put("end_time", etEndTime.getText().toString());
        mContentValues.put("add_date",DateUtil.getDate());
        mContentValues.put("is_complete", 0);
    }

}
