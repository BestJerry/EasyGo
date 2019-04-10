package com.easygo.jerry.my.diary.editdiary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.tripplan.edittirpplan.EditTripPlanContract;

/**
 * Create by wujiewei on 2019/4/7
 */
public class EditDiaryPresenter implements EditDiaryContract.Presenter {

    private static final String TAG = "EditDiaryPresenter";

    private EditDiaryContract.View mView;

    public EditDiaryPresenter(EditDiaryContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void updateDiary(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor mCursor;

            @Override
            protected Boolean doInBackground() {

                try {
                    Log.d(TAG, "doInBackground: 更新日记");
                    database.updateWithOnConflict(DatabaseHelper.TABLE_USER_DIARY, contentValues,
                            "id = ?",
                            new String[]{String.valueOf(contentValues.get("id"))}, SQLiteDatabase.CONFLICT_ROLLBACK);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 更新日记失败 " + e.getMessage());
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



    //删除计划
    @Override
    public void deleteDiary(final int id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    database.delete(DatabaseHelper.TABLE_USER_DIARY, "id = ?",
                            new String[]{String.valueOf(id)});
                    Log.d(TAG, "doInBackground: 删除日记成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 删除日记失败 " + e.getMessage());
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x002, result);
            }
        }.execute();
    }
}
