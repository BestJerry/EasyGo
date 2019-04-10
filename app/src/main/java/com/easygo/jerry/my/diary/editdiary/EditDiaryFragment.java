package com.easygo.jerry.my.diary.editdiary;

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
import com.easygo.jerry.data.bean.Diary;
import com.easygo.jerry.util.Constants;
import com.easygo.jerry.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/7
 */
public class EditDiaryFragment extends BaseFragment implements EditDiaryContract.View {
    private static final String TAG = "EditDiaryFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_update)
    Button btUpdate;
    Unbinder unbinder;

    private EditDiaryContract.Presenter mPresenter;

    private Diary mDiary;

    private ContentValues mContentValues;

    public Diary getDiary() {
        return mDiary;
    }

    public static EditDiaryFragment newInstance() {
        return new EditDiaryFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDiary = (Diary) getActivity().getIntent().getSerializableExtra("diary");
        setContent(mDiary);
    }

    private void setContent(Diary diary) {
        etTitle.setText(diary.getTitle());
        etContent.setText(diary.getContent());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_diary;
    }

    @Override
    public void setPresenter(EditDiaryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(int state, Object object) {
        switch (state) {
            case 0x001:
                if ((Boolean) object) {
                    Toast.makeText(getActivity(), "更新日记成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "出了点小问题～", Toast.LENGTH_SHORT).show();
                }
                break;
            case 0x002:
                if ((Boolean)object){
                    ToastUtils.show(getActivity(),"删除日记成功");
                    getActivity().finish();
                }else{
                    ToastUtils.show(getActivity(),"出了点小问题～");
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

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        if (etTitle.getText().toString().equals("")) {
            ToastUtils.show(getActivity(), "标题不可为空");
        } else {
            putData();
            mPresenter.updateDiary(mContentValues);
        }
    }

    private void putData() {
        if (mContentValues == null) {
            mContentValues = new ContentValues();
        }
        mContentValues.clear();
        mContentValues.put("id", mDiary.getId());
        mContentValues.put("title", etTitle.getText().toString());
        mContentValues.put("content", etContent.getText().toString());
    }
}
