package com.easygo.jerry.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.idst.nls.nlsclientsdk.requests.Constant;
import com.easygo.jerry.R;
import com.easygo.jerry.adapter.MenuAdapter;
import com.easygo.jerry.base.BaseFragment;
import com.easygo.jerry.data.bean.MenuItem;
import com.easygo.jerry.listener.OnItemClickListener;
import com.easygo.jerry.my.diary.diarylist.DiaryListActivity;
import com.easygo.jerry.my.editpassword.EditPasswordActivity;
import com.easygo.jerry.my.interestposition.PoiListActivity;
import com.easygo.jerry.my.myinfo.MyInfoActivity;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by wujiewei on 2019/4/6
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.img_head_picture)
    ImageView imgHeadPicture;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // 初始化菜单Adapter
        MenuAdapter menuAdapter = new MenuAdapter();
        menuAdapter.addItems(prepareMenuItems());
        menuAdapter.setOnItemClickListener(new OnItemClickListener<MenuItem>() {
            @Override
            public void onClick(MenuItem item) {
                clickMenuItem(item);
            }
        });
        recyclerView.setAdapter(menuAdapter);
    }

    private void clickMenuItem(MenuItem item) {

        switch (item.getIconResId()) {
            case R.drawable.ic_my_info:
                startActivity(MyInfoActivity.newIntent(getActivity()));
                break;

            case R.drawable.ic_my_poi:
                startActivity(PoiListActivity.newIntent(getActivity()));
                break;

            case R.drawable.ic_my_diary:
                startActivity(DiaryListActivity.newIntent(getActivity()));
                break;

            case R.drawable.ic_edit_password:
                startActivity(EditPasswordActivity.newIntent(getActivity()));
                break;

            case R.drawable.ic_logout:
                //全局用户ID置为-1
                Constants.USER_ID = -1;
                //用户昵称是否填写标志置为0
                Constants.IS_DETAIL_COMPLETE = 0;
                //用户昵称置为“”
                Constants.USER_NAME = "";
                //销毁当前Activity
                getActivity().finish();
                break;
        }
    }

    private List<MenuItem> prepareMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(getString(R.string.my_info), R.drawable.ic_my_info));
        menuItems.add(new MenuItem(getString(R.string.my_poi), R.drawable.ic_my_poi));
        menuItems.add(new MenuItem(getString(R.string.my_diary), R.drawable.ic_my_diary));
        menuItems.add(new MenuItem(getString(R.string.edit_password), R.drawable.ic_edit_password));
        menuItems.add(new MenuItem(getString(R.string.logout), R.drawable.ic_logout));

        return menuItems;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvUserName.setText(Constants.USER_NAME);
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
}
