package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import Database.myhelper;

public class TheLoaiDAO {
    private myhelper helper;
    private SQLiteDatabase db;

    public TheLoaiDAO(Context context) {
        helper = new myhelper(context);
        db = helper.getWritableDatabase();
    }

}

