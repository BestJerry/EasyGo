package com.easygo.jerry.my.editpassword;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.tripplan.edittirpplan.EditTripPlanContract;
import com.easygo.jerry.util.Constants;

import java.time.LocalDateTime;

/**
 * Create by wujiewei on 2019/4/6
 */
public class EditPasswordPresenter implements EditPasswordContract.Presenter {

    private static final String TAG = "EditPasswordPresenter";

    private EditPasswordContract.View mView;

    public EditPasswordPresenter(EditPasswordContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    // 更新密码和登录配置
    @Override
    public void updatePassword(final String new_password) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            @Override
            protected Boolean doInBackground() {

                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("password", new_password);
                    database.update(DatabaseHelper.TABLE_USER_INFO,
                            contentValues, "id = ?", new String[]{String.valueOf(Constants.USER_ID)});
                    database.update(DatabaseHelper.TABLE_LOGIN_CONFIG,
                            contentValues, "u_id = ?", new String[]{String.valueOf(Constants.USER_ID)});
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 更新密码失败 " + e.getMessage());
                    return false;
                }
                Log.d(TAG, "doInBackground: 更新密码成功");

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }

    @Override
    public void checkPassword(final String old_password) {
        // 获取数据库实例
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;
            @Override
            protected Boolean doInBackground() {
                Log.d(TAG, "doInBackground: 查询用户表");
                cursor = database.query(DatabaseHelper.TABLE_USER_INFO, null,
                        "id = ?", new String[]{String.valueOf(Constants.USER_ID)}, null, null, null);

                while (cursor.moveToNext()) {
                    String pa = cursor.getString(2);
                    if (pa.equals(old_password)) {
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
                mView.setData(0x002, result);
            }
        }.execute();
    }
}
