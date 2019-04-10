package com.easygo.jerry.toolbox.decibelmeter;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.permission.CheckPermissionsActivity;
import com.easygo.jerry.toolbox.ToolBoxActivity;
import com.easygo.jerry.util.FileUtil;
import com.easygo.jerry.util.ToastUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DecibelMeterActivity extends CheckPermissionsActivity {

    private static final String TAG = "DecibelMeterActivity";

    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_stop)
    Button btStop;
    @BindView(R.id.tv_decibel)
    TextView tvDecibel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static final int MAX_LENGTH = 600000;

    private File mFileRec;
    MediaRecorder mMediaRecorder;
    private static final int RECORD_PERMISSION_CODE = 100;
    private MyHandler mHandler;

    private boolean isRecording = false;
    private boolean isListener = false;
    private boolean isThreading = false;
    private Thread mThread;
    private float volume;

    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DecibelMeterActivity> mActivityWeakReference;

        public MyHandler(DecibelMeterActivity activity) {
            mActivityWeakReference = new WeakReference<DecibelMeterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DecibelMeterActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0x001:
                        String s = activity.mDecimalFormat.format(msg.obj);
                        activity.tvDecibel.setText(s + " dB");
                        break;
                }
            }
        }
    }

    public static Intent newIntent(ToolBoxActivity toolBoxActivity) {
        Intent intent = new Intent(toolBoxActivity, DecibelMeterActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 设置后退箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("分贝计");
        mHandler = new MyHandler(this);
    }

    // 需要覆盖系统的这个方法才能执行返回
    @Override
    public boolean onSupportNavigateUp() {
        // 必须加finish()方法，否则不会关闭当前Activity
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_decibel_meter;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRecord(); // 在onStop方法中停止录音
    }

    //开始录音
    private void startRecord() {
        try {
            ToastUtils.show(this, "开始录音");
            mFileRec = FileUtil.createFile("test.amr");
            if (mFileRec == null) {
                //ToastUtils.show(this, "创建文件失败");
                return;
            }
            // 创建MediaRecorder并初始化参数
            if (mMediaRecorder == null) {
                mMediaRecorder = new MediaRecorder();
            }
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.setOutputFile(mFileRec.getAbsolutePath());

            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (Exception e) {
            Log.d(TAG, "startRecord: " + e.getMessage());
            ToastUtils.show(this, "录音失败");
            isThreading = false;
            isListener = false;
            isRecording = false;
        }

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isThreading) {
                    try {
                        if (isListener) {
                            volume = mMediaRecorder.getMaxAmplitude();
                            if (volume > 0 && volume < 1000000) {
                                Calculator.setDbCount(20 * (float) (Math.log10(volume)));
                            }
                        }
                        Log.d(TAG, "run: " + Calculator.dbstart);
                        Message message = new Message();
                        message.what = 0x001;
                        message.obj = Calculator.dbstart;
                        mHandler.sendMessage(message);
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isThreading = false;
                        isListener = false;
                        isRecording = false;
                    }
                }
            }
        });
        mThread.start();
    }

    //停止录音
    private void stopRecord() {
        ToastUtils.show(this, "停止录音");
        isListener = false;
        isThreading = false;
        isRecording = false;
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
        }
        if (mFileRec != null) {
            mFileRec.delete();
        }
        Calculator.dbstart = 0.00f;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //检查录音权限
    private void checkRecordPermission() {
        // 检查权限的方法: ContextCompat.checkSelfPermission()两个参数分别是Context和权限名.
        // 返回PERMISSION_GRANTED是有权限，PERMISSION_DENIED没有权限
        if (ContextCompat.checkSelfPermission(DecibelMeterActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //没有权限，向系统申请该权限。
            //ToastUtils.show(this, "没有权限");
            Log.i("MY", "没有权限");
            requestPermission(RECORD_PERMISSION_CODE);
        } else {
            //同组的权限，只要有一个已经授权，则系统会自动授权同一组的所有权限，比如WRITE_EXTERNAL_STORAGE和READ_EXTERNAL_STORAGE
            startRecord();
        }
    }

    private void requestPermission(int permissioncode) {
        String permission = getPermissionString(permissioncode);
        if (!IsEmptyOrNullString(permission)) {
            ActivityCompat.requestPermissions(DecibelMeterActivity.this,
                    new String[]{permission}, permissioncode);
        }
    }

    private String getPermissionString(int requestCode) {
        String permission = "";
        switch (requestCode) {
            case RECORD_PERMISSION_CODE:
                permission = Manifest.permission.RECORD_AUDIO;
                break;
        }
        return permission;
    }

    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(DecibelMeterActivity.this, "录音权限已获取", Toast.LENGTH_SHORT).show();
                    Log.i("MY", "录音权限已获取");
                    startRecord();
                } else {
                    ToastUtils.show(this, "缺少权限，无法开启录音");
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick({R.id.bt_start, R.id.bt_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (isRecording || isListener || isThreading) {
                    return;
                } else {
                    isListener = true;
                    isRecording = true;
                    isThreading = true;
                    checkRecordPermission();
                }

                break;
            case R.id.bt_stop:
                if (isListener || isRecording || isThreading) {
                    stopRecord();
                } else {
                    return;
                }
                break;
        }
    }
}
