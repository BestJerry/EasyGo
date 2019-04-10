package com.easygo.jerry.tripplan.triplist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.bean.TripPlan;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.tripplan.addtripplan.AddTripPlanContract;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/4/7
 */
public class TripListPresenter implements TripListContract.Presenter {
    private static final String TAG = "TripListPresenter";

    private TripListContract.View mView;

    public TripListPresenter(TripListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //获取出行计划数据
    //查询数据库
    @Override
    public void fetchTripPlan() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<List<TripPlan>>() {
            Cursor cursor;
            List<TripPlan> list = new ArrayList<>();

            @Override
            protected List<TripPlan> doInBackground() {
                Log.d(TAG, "doInBackground: 查询计划");
                try {
                    cursor = database.query(DatabaseHelper.TABLE_TRAVEL_PLAN, null, "u_id = ?",
                            new String[]{String.valueOf(Constants.USER_ID)}, null, null, null);

                    while (cursor.moveToNext()) {
                        TripPlan tripPlan = new TripPlan();
                        tripPlan.setId(cursor.getInt(0));
                        tripPlan.setU_id(cursor.getInt(1));
                        tripPlan.setTitle(cursor.getString(2));
                        tripPlan.setLocation(cursor.getString(3));
                        tripPlan.setDate(cursor.getString(4));
                        tripPlan.setStart_time(cursor.getString(5));
                        tripPlan.setEnd_time(cursor.getString(6));
                        tripPlan.setAdd_time(cursor.getString(7));
                        tripPlan.setIs_complete(cursor.getInt(8));
                        list.add(tripPlan);
                    }
                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: 查询失败 " + e.getMessage());
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<TripPlan> result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }

}
