package com.easygo.jerry.tripplan.edittirpplan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.tripplan.addtripplan.AddTripPlanContract;

/**
 * Create by wujiewei on 2019/4/7
 */
public class EditTripPlanPresenter implements EditTripPlanContract.Presenter {

    private static final String TAG = "EditTripPlanPresenter";

    private EditTripPlanContract.View mView;

    public EditTripPlanPresenter(EditTripPlanContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    //更新出行计划
    //更新数据库
    @Override
    public void updateTripPlan(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {

            @Override
            protected Boolean doInBackground() {

                try {
                    Log.d(TAG, "doInBackground: 更新计划");
                    database.updateWithOnConflict(DatabaseHelper.TABLE_TRAVEL_PLAN, contentValues,
                            "id = ?",
                            new String[]{String.valueOf(contentValues.get("id"))}, SQLiteDatabase.CONFLICT_ROLLBACK);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 更新计划失败 " + e.getMessage());
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }


    //删除计划
    @Override
    public void deleteTripPlan(final int id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    database.delete(DatabaseHelper.TABLE_TRAVEL_PLAN, "id = ?",
                            new String[]{String.valueOf(id)});
                    Log.d(TAG, "doInBackground: 删除计划成功");
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 删除计划失败 " + e.getMessage());
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


    @Override
    public void completeTripPlan(final int id) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor mCursor;
            ContentValues mContentValues = new ContentValues();
            @Override
            protected Boolean doInBackground() {

                try {
                    Log.d(TAG, "doInBackground: 完成计划");
                    mContentValues.put("is_complete",1);
                    database.updateWithOnConflict(DatabaseHelper.TABLE_TRAVEL_PLAN, mContentValues,
                            "id = ?",
                            new String[]{String.valueOf(id)}, SQLiteDatabase.CONFLICT_ROLLBACK);

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: 完成计划失败 " + e.getMessage());
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
                mView.setData(0x003, result);
            }
        }.execute();
    }
}
