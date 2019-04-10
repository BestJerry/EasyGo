package com.easygo.jerry.my.diary.adddiary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;

/**
 * Create by wujiewei on 2019/4/7
 */
public class AddDiaryPresenter implements AddDiaryContract.Presenter {
    private static final String TAG = "AddDiaryPresenter";

    private AddDiaryContract.View mView;

    public AddDiaryPresenter(AddDiaryContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void addDiary(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;

            @Override
            protected Boolean doInBackground() {
                try {
                    database.insertWithOnConflict(DatabaseHelper.TABLE_USER_DIARY, null,
                            contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

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
