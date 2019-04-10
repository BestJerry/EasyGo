package com.easygo.jerry.map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.amap.api.maps.model.Marker;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

/**
 * Create by wujiewei on 2019/4/6
 */
public class MapPresenter implements MapContract.Presenter {
    private static final String TAG = "MapPresenter";

    private MapContract.View mView;

    public MapPresenter(MapContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void collectLocation(final Marker marker) {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<Boolean>() {
            Cursor cursor;
            ContentValues mContentValues = new ContentValues();

            @Override
            protected Boolean doInBackground() {
                try {
                    mContentValues.put("u_id", Constants.USER_ID);
                    mContentValues.put("title", marker.getTitle());
                    mContentValues.put("snippet", marker.getSnippet());
                    mContentValues.put("longitude", marker.getPosition().longitude);
                    mContentValues.put("latitude", marker.getPosition().latitude);

                    database.insertWithOnConflict(DatabaseHelper.TABLE_INTEREST_LOCATION,
                            null, mContentValues, SQLiteDatabase.CONFLICT_ROLLBACK);

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
