package com.easygo.jerry.world;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easygo.jerry.data.bean.Dynamic;
import com.easygo.jerry.data.bean.Poi;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/4/6
 */
public class WorldPresenter implements WorldContract.Presenter {
    private static final String TAG = "WorldPresenter";

    private WorldContract.View mView;

    public WorldPresenter(WorldContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //获取动态
    @Override
    public void fetchDynamic() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<List<Dynamic>>() {
            Cursor cursor;
            List<Dynamic> list = new ArrayList<>();

            @Override
            protected List<Dynamic> doInBackground() {
                Log.d(TAG, "doInBackground: 查询动态");
                try {
                    cursor = database.query(DatabaseHelper.TABLE_USER_DYNAMIC,
                            null, null,
                            null,
                            null, null, null);

                    while (cursor.moveToNext()) {
                        Dynamic dynamic = new Dynamic();
                        dynamic.setId(cursor.getInt(0));
                        dynamic.setU_id(cursor.getInt(1));
                        dynamic.setContent(cursor.getString(2));
                        dynamic.setDate(cursor.getString(3));
                        dynamic.setLocation(cursor.getString(4));
                        dynamic.setRecommend_level(cursor.getString(5));

                        list.add(dynamic);
                    }
                } catch (
                        Exception e)

                {

                    Log.d(TAG, "doInBackground: 查询失败 " + e.getMessage());
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Dynamic> result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
