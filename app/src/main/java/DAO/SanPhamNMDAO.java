package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Database.myhelper;
import Model.SanPham;

public class SanPhamNMDAO {
    public myhelper helper;
    public SanPhamNMDAO(Context c){helper=new myhelper(c);}
    public ArrayList<SanPham> xemSP(){
        ArrayList<SanPham>ds= new ArrayList<SanPham>();
        SQLiteDatabase db=helper.getReadableDatabase();
        String query = "SELECT sp.masp, sp.tentp, sp.theloai_id, sp.soluong, sp.dongia, tl.ten_theloai " +
                "FROM sanpham sp " +
                "JOIN theloai tl ON sp.theloai_id = tl.id";
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            do {
                int masp = c.getInt(0);
                String tensp  = c.getString(1);
                int theloai =c.getInt(2);
                int soluong = c.getInt(3);
                int dongia = c.getInt(4);

                SanPham sp = new SanPham(masp,tensp,theloai, soluong,dongia);
                ds.add(sp);
            }while(c.moveToNext());

        }

        return ds;
    }
}
