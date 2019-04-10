package com.easygo.jerry.my.diary.diarylist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.easygo.jerry.data.bean.Diary;
import com.easygo.jerry.data.bean.Poi;
import com.easygo.jerry.data.db.DatabaseHelper;
import com.easygo.jerry.data.db.DbCommand;
import com.easygo.jerry.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/4/7
 */
public class DiaryListPresenter implements DiaryListContract.Presenter {

    private static final String TAG = "DiaryListPresenter";

    private DiaryListContract.View mView;

    public DiaryListPresenter(DiaryListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    //获取日记
    @Override
    public void fetchDiary() {
        final SQLiteDatabase database = DatabaseHelper.getInstance().getDatabase();

        new DbCommand<List<Diary>>() {
            Cursor cursor;
            List<Diary> list = new ArrayList<>();

            @Override
            protected List<Diary> doInBackground() {
                Log.d(TAG, "doInBackground: 查询日记");
                try {
                    cursor = database.query(DatabaseHelper.TABLE_USER_DIARY,
                            null, "u_id = ?",
                            new String[]{String.valueOf(Constants.USER_ID)},
                            null, null, null);

                    while (cursor.moveToNext()) {
                        Diary diary = new Diary();
                        diary.setId(cursor.getInt(0));
                        diary.setU_id(cursor.getInt(1));
                        diary.setTitle(cursor.getString(2));
                        diary.setContent(cursor.getString(3));
                        diary.setDate(cursor.getString(4));
                        list.add(diary);
                    }
                } catch (Exception e) {

                    Log.d(TAG, "doInBackground: 查询失败 " + e.getMessage());
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Diary> result) {
                if (cursor != null) {
                    cursor.close();
                }
                Log.d(TAG, "onPostExecute: 返回数据");
                mView.setData(0x001, result);
            }
        }.execute();
    }
}
