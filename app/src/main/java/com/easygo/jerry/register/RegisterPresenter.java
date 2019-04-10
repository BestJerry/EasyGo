package com.easygo.jerry.register;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.idst.nls.nlsclientsdk.requests.Constant;
import com.autonavi.ae.pos.LocGSVData;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.login.LoginContract;

/**
 * Create by wujiewei on 2019/4/6
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private static final String TAG = "RegisterPresenter";

    private RegisterContract.View mView;

    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //用户注册
    //数据库插入操作
    @Override
    public void register(final String phone, final String password) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;
            ContentValues mContentValues = new ContentValues();

            @Override
            protected Boolean doInBackground() {
                try {

                    mContentValues.put("phone", phone);
                    mContentValues.put("password", password);
                    database.insertWithOnConflict(DatabaseHelper.TABLE_USER_INFO, null,
                            mContentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

                    Log.d(TAG, "doInBackground: 插入成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 插入失败" + e.getMessage());

                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (cursor != null) {
                    cursor.close();
                }
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
