package com.easygo.jerry.tripplan.addtripplan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;

/**
 * Create by wujiewei on 2019/4/7
 */
public class AddTripPlanPresenter implements AddTripPlanContract.Presenter {
    private static final String TAG = "AddTripPlanPresenter";

    private AddTripPlanContract.View mView;

    public AddTripPlanPresenter(AddTripPlanContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void addTripPlan(final ContentValues contentValues) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {

            @Override
            protected Boolean doInBackground() {
                try {
                    database.insertWithOnConflict(DatabaseHelper.TABLE_TRAVEL_PLAN, null,
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
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
