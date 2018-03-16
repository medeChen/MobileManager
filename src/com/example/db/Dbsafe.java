package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbsafe extends SQLiteOpenHelper{

    final static int version = 1;
    final static String dbName = "safe";

    public Dbsafe(Context context){
        super(context,dbName,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE [isset] ("
        		+ "[Sid] varchar(500),"
        		+ "[Set] varchar(500),"
                + "[Spwd] varchar(500))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
