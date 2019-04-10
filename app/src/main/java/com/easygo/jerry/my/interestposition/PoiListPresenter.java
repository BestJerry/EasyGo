package com.easygo.jerry.my.interestposition;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.bean.Poi;
import com.easygo.jerry.data.bean.TripPlan;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/4/7
 */
public class PoiListPresenter implements PoiListContract.Presenter {

    private static final String TAG = "PoiListPresenter";

    private PoiListContract.View mView;

    public PoiListPresenter(PoiListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //获取兴趣点
    @Override
    public void fetchPoi() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<List<Poi>>() {
            Cursor cursor;
            List<Poi> list = new ArrayList<>();

            @Override
            protected List<Poi> doInBackground() {
                Log.d(TAG, "doInBackground: 查询兴趣点");
                try {
                    cursor = database.query(DatabaseHelper.TABLE_INTEREST_LOCATION,
                            null, "u_id = ?",
                            new String[]{String.valueOf(Constants.USER_ID)},
                            null, null, null);

                    while (cursor.moveToNext()) {
                        Poi poi = new Poi();
                        poi.setId(cursor.getInt(0));
                        poi.setU_id(cursor.getInt(1));
                        poi.setTitle(cursor.getString(2));
                        poi.setSnippet(cursor.getString(3));
                        poi.setLongitude(cursor.getString(4));
                        poi.setLatitude(cursor.getString(5));
                        list.add(poi);
                    }
                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: 查询失败 " + e.getMessage());
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Poi> result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
