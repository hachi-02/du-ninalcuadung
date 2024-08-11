package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import Database.myhelper;
import Model.NguoiDung;

public class NguoiDungDAO {
    private myhelper helper;
    private Context context; // Thêm trường context

    // Constructor
    public NguoiDungDAO(Context c) {
        helper = new myhelper(c);
        context = c; // Lưu context để sử dụng SharedPreferences
    }

    public void themNguoiDung(NguoiDung nd) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("username", nd.username);
        value.put("password", nd.password);
        value.put("role", "borrower"); // Vai trò người mượn
        db.insert("user", null, value);
    }


    // Kiểm tra sự tồn tại của người dùng
    public boolean kiemTraNguoiDung(String username) {
        SQLiteDatabase db = helper.getReadableDatabase(); // Sửa từ writable thành readable
        Cursor cursor = db.query("user", null, "username=?", new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Đăng nhập và lưu vai trò vào SharedPreferences
    public boolean login(String username, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            int roleColumnIndex = cursor.getColumnIndex("role");
            if (roleColumnIndex != -1) {
                String role = cursor.getString(roleColumnIndex);
                // Lưu vai trò vào SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_role", role);
                editor.apply();
                cursor.close();
                return true;
            } else {
                // Xử lý khi cột "role" không tồn tại
                Log.e("NguoiDungDAO", "Cột 'role' không tồn tại trong kết quả truy vấn.");
            }
        }
        cursor.close();
        return false;
    }
    public String getUserRole() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_role", "unknown"); // Trả về vai trò của người dùng, hoặc "unknown" nếu không tìm thấy
    }



}
