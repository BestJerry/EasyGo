package com.easygo.jerry.tripplan.edittirpplan;

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
import com.easygo.jerry.data.bean.TripPlan;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class EditTripPlanFragment extends BaseFragment implements EditTripPlanContract.View {

    private static final String TAG = "EditTripPlanFragment";
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
    @BindView(R.id.bt_complete)
    Button btComplete;
    @BindView(R.id.bt_update)
    Button btUpdate;
    Unbinder unbinder;

    private EditTripPlanContract.Presenter mPresenter;

    private TripPlan mTripPlan;

    private ContentValues mContentValues;

    public TripPlan getTripPlan() {
        return mTripPlan;
    }

    public static EditTripPlanFragment newInstance() {
        return new EditTripPlanFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTripPlan = (TripPlan) getActivity().getIntent().getSerializableExtra("trip_plan");
        setContent(mTripPlan);
    }

    private void setContent(TripPlan tripPlan) {
        etTitle.setText(tripPlan.getTitle());
        etLocation.setText(tripPlan.getLocation());
        etDate.setText(tripPlan.getDate());
        etStartTime.setText(tripPlan.getStart_time());
        etEndTime.setText(tripPlan.getEnd_time());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_trip_plan;
    }

    @Override
    public void setPresenter(EditTripPlanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    Toast.makeText(getActivity(), "计划更新成功～", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "出了点小问题～", Toast.LENGTH_SHORT).show();
                }
                break;
            case 0x002:
                if ((Boolean) object) {
                    ToastUtils.show(getActivity(), "计划删除成功～");
                    getActivity().finish();
                } else {
                    ToastUtils.show(getActivity(), "出了点小问题～");
                }
                break;
            case 0x003:
                if ((Boolean) object) {
                    ToastUtils.show(getActivity(), "恭喜，计划已完成～");
                } else {
                    ToastUtils.show(getActivity(), "出了点小问题～");
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

    @OnClick({R.id.bt_complete, R.id.bt_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_complete:
                mPresenter.completeTripPlan(mTripPlan.getId());
                break;
            case R.id.bt_update:
                if (etTitle.getText().toString().equals("")||etLocation.getText().toString().equals("")) {
                    ToastUtils.show(getActivity(), "请先完善必填信息～");
                } else {
                    putData();
                    mPresenter.updateTripPlan(mContentValues);
                }
                break;
        }
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("id", mTripPlan.getId());
        mContentValues.put("title", etTitle.getText().toString());
        mContentValues.put("location", etLocation.getText().toString());
        mContentValues.put("date", etDate.getText().toString());
        mContentValues.put("start_time", etStartTime.getText().toString());
        mContentValues.put("end_time", etEndTime.getText().toString());
    }
}
