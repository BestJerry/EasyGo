package com.easygo.jerry.mainpage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.easygo.jerry.R;
import com.easygo.jerry.util.ActivityUtils;
import com.easygo.jerry.util.PermissionUtil;
import com.easygo.jerry.util.schedulers.SchedulerProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainPageActivity extends AppCompatActivity {

    private static final String TAG = "EasyGo";

    private MainPagePresenter mMainPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        MainPageFragment mainPageFragment = (MainPageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (mainPageFragment == null) {
            // Create the fragment
            mainPageFragment = MainPageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainPageFragment, R.id.fragment_container);
        }

        // Create the presenter
        mMainPagePresenter = new MainPagePresenter(mainPageFragment, SchedulerProvider.getInstance());

        // Load previously saved state, if available.
        //        if (savedInstanceState != null) {
        //
        //        }

        //Log.i(TAG, "onCreate: " + sHA1(this));
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
