package com.easygo.jerry.my.myinfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.bean.TripPlan;
import com.easygo.jerry.data.bean.UserInfo;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/4/7
 */
public class MyInfoPresenter implements MyInfoContract.Presenter {

    private static final String TAG = "MyInfoPresenter";

    private MyInfoContract.View mView;

    public MyInfoPresenter(MyInfoContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //获取用户信息
    @Override
    public void fetchUserInfo() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<UserInfo>() {
            Cursor cursor;
            UserInfo mUserInfo = new UserInfo();

            @Override
            protected UserInfo doInBackground() {
                Log.d(TAG, "doInBackground: 查询用户信息");
                try {
                    cursor = database.query(DatabaseHelper.TABLE_USER_INFO, null,
                            "id = ?", new String[]{String.valueOf(Constants.USER_ID)},
                            null, null, null);

                    while (cursor.moveToNext()) {
                        mUserInfo.setId(cursor.getInt(0));
                        mUserInfo.setName(cursor.getString(3));
                        mUserInfo.setSex(cursor.getString(4));
                        mUserInfo.setAge(cursor.getString(5));
                        mUserInfo.setEmail(cursor.getString(6));
                        mUserInfo.setIntroduction(cursor.getString(7));
                        mUserInfo.setHobby(cursor.getString(8));

                        return mUserInfo;
                    }
                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: 查询失败 " + e.getMessage());
                }
                return mUserInfo;
            }

            @Override
            protected void onPostExecute(UserInfo result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x002, result);
            }
        }.execute();
    }

    //更新用户信息
    @Override
    public void updateUserInfo(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {
                    Log.d(TAG, "doInBackground: 更新用户信息");
                    database.updateWithOnConflict(DatabaseHelper.TABLE_USER_INFO, contentValues,
                            "id = ?",
                            new String[]{String.valueOf(contentValues.get("id"))}, SQLiteDatabase.CONFLICT_ROLLBACK);
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 更新用户信息失败 " + e.getMessage());
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mCursor != null) {
                    mCursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
