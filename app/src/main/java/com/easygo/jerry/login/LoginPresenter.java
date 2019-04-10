package com.easygo.jerry.login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.bean.LoginConfig;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

/**
 * Create by wujiewei on 2019/4/6
 */
public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";

    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 访问用户数据表，进行账号和密码对比
     * 查询数据
     *
     * @param phone
     * @param password
     */
    @Override
    public void login(final String phone, final String password) {
        // 获取数据库实例
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;
            @Override
            protected Boolean doInBackground() {
                Log.d(TAG, "doInBackground: 查询用户表");
                cursor = database.query(DatabaseHelper.TABLE_USER_INFO, null,
                        null, null, null, null, null);

                while (cursor.moveToNext()) {
                    String ph = cursor.getString(1);
                    String pa = cursor.getString(2);
                    if (ph.equals(phone) && pa.equals(password)) {
                        Constants.USER_ID = cursor.getInt(0);
                        Constants.USER_NAME = cursor.getString(3);
                        Constants.IS_DETAIL_COMPLETE = cursor.getInt(9);
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回结果");
                mView.setData(0x001, result);
            }
        }.execute();
    }



    @Override
    public void updateLoginConfig(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            @Override
            protected Boolean doInBackground() {

                try {

                    database.delete(DatabaseHelper.TABLE_LOGIN_CONFIG, null, null);

                    database.insertWithOnConflict(DatabaseHelper.TABLE_LOGIN_CONFIG, null,
                            contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 更新登录失败 " + e.getMessage());
                    return false;
                }
                Log.d(TAG, "doInBackground: 更新登录配置成功");

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x003, result);
            }
        }.execute();
    }

    /**
     * 获取登录配置信息
     * 查询数据
     */
    @Override
    public void fetchLoginConfig() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<LoginConfig>() {
            Cursor cursor;
            LoginConfig mLoginConfig = new LoginConfig();

            @Override
            protected LoginConfig doInBackground() {
                Log.d(TAG, "doInBackground: 查询登录配置表");

                try {
                    cursor = database.query(DatabaseHelper.TABLE_LOGIN_CONFIG, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        mLoginConfig.setId(cursor.getInt(0));
                        mLoginConfig.setU_id(cursor.getInt(1));
                        mLoginConfig.setPhone(cursor.getString(2));
                        mLoginConfig.setPassword(cursor.getString(3));
                        mLoginConfig.setIs_remember_pwd(cursor.getInt(4));
                        mLoginConfig.setIs_auto_login(cursor.getInt(5));
                    }
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: " + e.getMessage());
                }
                return mLoginConfig;
            }

            @Override
            protected void onPostExecute(LoginConfig result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x002, result);
            }
        }.execute();
    }
}
