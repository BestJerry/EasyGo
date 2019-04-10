package com.easygo.jerry.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerry on 2018/3/5.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    // 数据库的名称
    public static final String DB_NAME = "EasyGo.db";

    // 数据库的版本号
    public static final int DB_VERSION = 1;

    // 数据库的单一实例
    private SQLiteDatabase mDatabase;

    private static DatabaseHelper sDatabaseHelper;

    public static final String TABLE_USER_INFO = "UserInfo";

    public static final String TABLE_LOGIN_CONFIG = "LoginConfig";

    public static final String TABLE_TRAVEL_PLAN = "TravelPlan";

    public static final String TABLE_USER_DYNAMIC = "UserDynamic";

    public static final String TABLE_USER_DIARY = "UserDiary";

    public static final String TABLE_INTEREST_LOCATION = "InterestLocation";

    // 用户信息表
    private static final String USER_INFO_CREATE_TABLE_SQL = "create table "
            + TABLE_USER_INFO + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "phone text not null unique,"//1
            + "password text not null,"//2
            + "name text, "//3
            + "sex text, "//4
            + "age text, "//5
            + "email text, "//6
            + "introduction text, "//7
            + "hobby text, "//8
            + "is_complete text"//9
            + ");";

    // 登录配置表
    private static final String LOGIN_CONFIG_CREATE_TABLE_SQL = "create table "
            + TABLE_LOGIN_CONFIG + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "u_id integer not null,"//1
            + "phone varchar(20) not null,"//2
            + "password text not null, "//3
            + "is_remember_pwd integer default 0, "//4
            + "is_auto_login integer default 0, "//5
            + "foreign key (u_id) references " + TABLE_USER_INFO + " (id) "
            + ");";

    // 出行计划表
    private static final String TRAVEL_PLAN_CREATE_TABLE_SQL = "create table "
            + TABLE_TRAVEL_PLAN + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "u_id integer not null,"//1
            + "title text not null,"//2
            + "location text, "//3
            + "date text, "//4
            + "start_time text, "//5
            + "end_time text, "//6
            + "add_date text, "//7
            + "is_complete integer default 0, "//8
            + "foreign key (u_id) references " + TABLE_USER_INFO + " (id) "
            + ");";

    // 用户日记表
    private static final String USER_DIARY_CREATE_TABLE_SQL = "create table "
            + TABLE_USER_DIARY + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "u_id integer not null,"//1
            + "title varchar(20) not null,"//2
            + "content text, "//3
            + "date text not null,"//4
            + "foreign key (u_id) references " + TABLE_USER_INFO + " (id) "
            + ");";

    // 用户动态表
    private static final String USER_DYNAMIC_CREATE_TABLE_SQL = "create table "
            + TABLE_USER_DYNAMIC + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "u_id integer not null,"//1
            + "content varchar(20) not null,"//2
            + "date text not null, "//3
            + "location text,"//4
            + "recommend_level text,"//5
            + "foreign key (u_id) references " + TABLE_USER_INFO + " (id) "
            + ");";

    // 兴趣地点表
    private static final String INTEREST_LOCATION_CREATE_TABLE_SQL = "create table "
            + TABLE_INTEREST_LOCATION + " ( "
            + "id integer primary key autoincrement unique not null,"//0
            + "u_id integer not null,"//1
            + "title text unique not null,"//2
            + "snippet text not null, "//3
            + "longitude text not null,"//4
            + "latitude text not null,"//5
            + "foreign key (u_id) references " + TABLE_USER_INFO + " (id) "
            + ");";



    private DatabaseHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
        mDatabase = getWritableDatabase();
    }

    // 初始化DatabaseHelper
    public static void init(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }
    }

    // 获取DatabaseHelper的单一实例
    public static DatabaseHelper getInstance() {
        if (sDatabaseHelper == null) {
            throw new NullPointerException("sDatabaseHelper is null, please call init " +
                    "method first.");
        }
        return sDatabaseHelper;
    }

    // 获取SQLiteDatabase实例
    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        try {
            db.execSQL(USER_INFO_CREATE_TABLE_SQL);
            db.execSQL(LOGIN_CONFIG_CREATE_TABLE_SQL);
            db.execSQL(TRAVEL_PLAN_CREATE_TABLE_SQL);
            db.execSQL(USER_DYNAMIC_CREATE_TABLE_SQL);
            db.execSQL(USER_DIARY_CREATE_TABLE_SQL);
            db.execSQL(INTEREST_LOCATION_CREATE_TABLE_SQL);
            Log.d(TAG, "onCreate: 创建数据库表成功");
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            default:
                break;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");
        }
    }
}