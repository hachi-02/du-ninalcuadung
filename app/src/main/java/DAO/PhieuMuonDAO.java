package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duancuadung.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Database.myhelper;
import Model.PhieuMuon;

public class PhieuMuonDAO {
    public myhelper helper;
    SQLiteDatabase db;
    private MainActivity activity;
    public PhieuMuonDAO(Context c){
        helper=new myhelper(c);
        db = helper.getWritableDatabase();}
    public void themPhieuMuon(PhieuMuon pm){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put("masp",pm.masp);
        value.put("tentp",pm.tentp);
        value.put("tennguoimuon",pm.tenngm);
        value.put("sdt",pm.sdt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        value.put("ngaymuon", dateFormat.format(pm.ngaymuon));
        value.put("ngaytra", dateFormat.format(pm.ngaytra));
        value.put("trangthai",pm.trangthai);


        db.insert("phieumuon",null,value);
    }

    public ArrayList<PhieuMuon> xemYeuCauPhieuMuon() {
        ArrayList<PhieuMuon> dsYeuCau = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM yeucauphieumuon", null);

        if (c != null) {
            int mapmIndex = c.getColumnIndex("id_phieumuon");
            int maspIndex = c.getColumnIndex("masp");
            int tenngmIndex = c.getColumnIndex("tennguoimuon");
            int tentpIndex = c.getColumnIndex("tentp");
            int sdtIndex = c.getColumnIndex("sdt");
            int ngaymuonIndex = c.getColumnIndex("ngaymuon");
            int ngaytraIndex = c.getColumnIndex("ngaytra");

            if (mapmIndex != -1 && maspIndex != -1 && tenngmIndex != -1 &&
                    tentpIndex != -1 && sdtIndex != -1 && ngaymuonIndex != -1 &&
                    ngaytraIndex != -1) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                if (c.moveToFirst()) {
                    do {
                        int mapm = c.getInt(mapmIndex);
                        int masp = c.getInt(maspIndex);
                        String tenngm = c.getString(tenngmIndex);
                        String tentp = c.getString(tentpIndex);
                        String sdt = c.getString(sdtIndex);
                        Date ngaymuon = null;
                        Date ngaytra = null;
                        try {
                            ngaymuon = dateFormat.parse(c.getString(ngaymuonIndex));
                            ngaytra = dateFormat.parse(c.getString(ngaytraIndex));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        PhieuMuon pm = new PhieuMuon(mapm, tenngm, masp, tentp, sdt, ngaymuon, ngaytra);
                        dsYeuCau.add(pm);
                    } while (c.moveToNext());
                }
            } else {
                Log.e("Database", "Một hoặc nhiều cột không tồn tại trong kết quả truy vấn.");
            }
            c.close();
        } else {
            Log.e("Database", "Không có dữ liệu hoặc con trỏ null.");
        }
        return dsYeuCau;
    }


    public ArrayList<PhieuMuon> xemPM(){
        ArrayList<PhieuMuon> dspm= new ArrayList<PhieuMuon>();
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor c=db.rawQuery("select* from phieumuon",null);
        if(c.moveToFirst()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            do {
                int mapm= c.getInt(0);
                int masp=c.getInt(1);
                String tenngm=c.getString(2);
                String tentp=c.getString(3);
                String sdt=c.getString(4);
                Date ngaymuon = null;
                Date ngaytra = null;
                try {
                    ngaymuon = dateFormat.parse(c.getString(5));
                    ngaytra = dateFormat.parse(c.getString(6));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Boolean trangthai=c.getInt(7)==1;

                PhieuMuon pm = new PhieuMuon(mapm, tenngm, masp, tentp, sdt, ngaymuon, ngaytra, trangthai);
                dspm.add(pm);
            }while(c.moveToNext());

        }
        return dspm;
    }
    public void themYeuCauPhieuMuon(PhieuMuon pm) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masp", pm.masp);
        values.put("tentp", pm.tentp);
        values.put("tennguoimuon", pm.tenngm);
        values.put("sdt", pm.sdt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        values.put("ngaymuon", dateFormat.format(pm.ngaymuon));
        values.put("ngaytra", dateFormat.format(pm.ngaytra));

        db.insert("yeucauphieumuon", null, values);
        db.close();
    }

    public void xuLyYeuCau(PhieuMuon pm, boolean chapNhan) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (chapNhan) {
            themPhieuMuon(pm);
            // Thêm thông báo khi yêu cầu được chấp nhận
            String thongBao = "Yêu cầu mượn sách " + pm.tentp + " đã được chấp nhận.";
            themThongBao(thongBao);
        }
        xoaYeuCauPhieuMuon(pm.mapm);
        db.close();
    }


    public void xoaYeuCauPhieuMuon(int mapm) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("yeucauphieumuon", "id_phieumuon=?", new String[]{String.valueOf(mapm)});
        db.close();
    }

    public void xoaPhieuMuon(int mapm){
        SQLiteDatabase db=helper.getReadableDatabase();
        db.delete("phieumuon","id_phieumuon=?",new String[]{mapm+""});
    }
    public void suaPhieuMuon(PhieuMuon pm){
        SQLiteDatabase db=helper.getReadableDatabase();
        ContentValues value=new ContentValues();
        value.put("masp", pm.masp);
        value.put("tentp", pm.tentp);
        value.put("tennguoimuon", pm.tenngm);
        value.put("sdt", pm.sdt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        value.put("ngaymuon", pm.ngaymuon != null ? dateFormat.format(pm.ngaymuon) : null);
        value.put("ngaytra", pm.ngaytra != null ? dateFormat.format(pm.ngaytra) : null);
        value.put("trangthai", pm.trangthai != null && pm.trangthai);

        db.update("phieumuon", value, "id_phieumuon = ?", new String[]{String.valueOf(pm.mapm)});
    }

    public String getTenSanPham(int id) {
        SQLiteDatabase db=helper.getReadableDatabase();
        String tentp = null;
        String query = "SELECT tentp FROM sanpham WHERE masp = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("tentp");
            if (columnIndex != -1) {
                tentp = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return tentp;
    }
    public int tinhTongDoanhThu(String startDate, String endDate) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT SUM(s.dongia) FROM phieumuon pm " +
                "JOIN sanpham s ON pm.masp = s.masp " +
                "WHERE pm.trangthai = 1 " +
                "AND pm.ngaytra BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        int kq = 0;
        if (cursor.moveToFirst()) {
            kq = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return kq;
    }
    public void xoaPhieuMuonBySanPham(int masp) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "DELETE FROM phieumuon WHERE masp = ?";
        db.execSQL(query, new String[]{String.valueOf(masp)});
    }
    public void themThongBao(String message) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("message", message);
        db.insert("thongbao", null, values);
        db.close();
    }

    public ArrayList<String> layDanhSachThongBao() {
        ArrayList<String> thongBaoList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT message FROM thongbao", null);

        if (cursor.moveToFirst()) {
            do {
                thongBaoList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return thongBaoList;
    }
    public void xoaThongBao(String thongBao) {
        SQLiteDatabase db=helper.getReadableDatabase();
        db.delete("thongbao","message=?", new String[]{thongBao});
    }







}
