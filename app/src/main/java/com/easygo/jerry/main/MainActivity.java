package com.easygo.jerry.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.easygo.jerry.R;
import com.easygo.jerry.base.BaseActivity;
import com.easygo.jerry.mainpage.MainPageFragment;
import com.easygo.jerry.mainpage.MainPagePresenter;
import com.easygo.jerry.my.MyFragment;
import com.easygo.jerry.permission.CheckPermissionsActivity;
import com.easygo.jerry.util.ActivityUtils;
import com.easygo.jerry.world.WorldFragment;
import com.easygo.jerry.world.WorldPresenter;

import java.net.Authenticator;

import butterknife.BindView;

public class MainActivity extends CheckPermissionsActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    RelativeLayout container;

    private MainPageFragment mMainPageFragment;

    private WorldFragment mWorldFragment;

    private MyFragment mMyFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main_page:
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("   首页");
                    if (mMainPageFragment == null) {
                        mMainPageFragment = MainPageFragment.newInstance();
                    }
                    ActivityUtils.ReplaceFragmentToActivity(getSupportFragmentManager(),
                            mMainPageFragment, R.id.fragment_container);
                    return true;
                case R.id.navigation_world:
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("   世界");
                    if (mWorldFragment == null) {
                        mWorldFragment = WorldFragment.newInstance();
                        new WorldPresenter(mWorldFragment);
                    }
                    ActivityUtils.ReplaceFragmentToActivity(getSupportFragmentManager(),
                            mWorldFragment, R.id.fragment_container);
                    return true;
                case R.id.navigation_my:
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("   我的主页");
                    if (mMyFragment == null) {
                        mMyFragment = MyFragment.newInstance();
                    }
                    ActivityUtils.ReplaceFragmentToActivity(getSupportFragmentManager(),
                            mMyFragment, R.id.fragment_container);
                    return true;
            }
            return false;
        }
    };

    public static Intent newIntent(FragmentActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        return intent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   首页");
        if (mMainPageFragment == null) {
            mMainPageFragment = MainPageFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mMainPageFragment, R.id.fragment_container);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    public static String sHA1(Context context) {
    //        try {
    //            PackageInfo info = context.getPackageManager().getPackageInfo(
    //                    context.getPackageName(), PackageManager.GET_SIGNATURES);
    //            byte[] cert = info.signatures[0].toByteArray();
    //            MessageDigest md = MessageDigest.getInstance("SHA1");
    //            byte[] publicKey = md.digest(cert);
    //            StringBuffer hexString = new StringBuffer();
    //            for (int i = 0; i < publicKey.length; i++) {
    //                String appendString = Integer.toHexString(0xFF & publicKey[i])
    //                        .toUpperCase(Locale.US);
    //                if (appendString.length() == 1)
    //                    hexString.append("0");
    //                hexString.append(appendString);
    //                hexString.append(":");
    //            }
    //            String result = hexString.toString();
    //            return result.substring(0, result.length()-1);
    //        } catch (PackageManager.NameNotFoundException e) {
    //            e.printStackTrace();
    //        } catch (NoSuchAlgorithmException e) {
    //            e.printStackTrace();
    //        }
    //        return null;
    //    }
}
