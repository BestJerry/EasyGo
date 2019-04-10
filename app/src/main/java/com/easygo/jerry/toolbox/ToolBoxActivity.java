package com.easygo.jerry.toolbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.toolbox.decibelmeter.DecibelMeterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ToolBoxActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_decibel_meter)
    Button btDecibelMeter;

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, ToolBoxActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("工具箱");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_box;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.bt_decibel_meter)
    public void onViewClicked() {
        startActivity(DecibelMeterActivity.newIntent(this));
    }
}
