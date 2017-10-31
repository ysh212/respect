package com.example.user.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2017-09-21.
 */

public class RootDao extends SQLiteOpenHelper {
    public RootDao(Context context) {
        super(context, "officeDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE officeTB(token CHAR(60) PRIMARY KEY, officeName CHAR(20), curLatitude DOUBLE, curLongitude DOUBLE;");
        db.execSQL("CREATE TABLE officeTB(officeName CHAR(20) PRIMARY KEY, curLatitude DOUBLE, curLongitude DOUBLE;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS officeTB;");
        onCreate(db);
    }
}
