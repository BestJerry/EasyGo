package com.easygo.jerry.publishdynamic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;

/**
 * Create by wujiewei on 2019/4/7
 */
public class PublishPresenter implements PublishContract.Presenter {

    private static final String TAG = "PoiListPresenter";

    private PublishContract.View mView;

    public PublishPresenter(PublishContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    //发布动态
    //插入数据库
    @Override
    public void publishDynamic(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();
        new DbCommand<Boolean>() {
            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {
                try {
                    Log.d(TAG, "doInBackground: 发布动态 ");
                    database.insertWithOnConflict(DatabaseHelper.TABLE_USER_DYNAMIC, null,
                            contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 发布动态失败 " + e.getMessage());
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
