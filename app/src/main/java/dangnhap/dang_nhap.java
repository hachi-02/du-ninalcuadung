package dangnhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duancuadung.MainActivity;
import com.example.duancuadung.MainActivity2;
import com.example.duancuadung.R;

import DAO.NguoiDungDAO;

public class dang_nhap extends AppCompatActivity {
    private Button bt_login;
    private Button bt_signup;
    private EditText edt_username_signin, edt_pass_signin;
    private NguoiDungDAO ndDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        bt_signup = findViewById(R.id.bt_signup);
        bt_login = findViewById(R.id.bt_login);
        edt_pass_signin = findViewById(R.id.edt_pass_signin);
        edt_username_signin = findViewById(R.id.edt_user_signin);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dang_nhap.this, dang_ky.class);
                startActivity(i);
            }
        });

        ndDAO = new NguoiDungDAO(this);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username_signin.getText().toString().trim();
                String password = edt_pass_signin.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(dang_nhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isLoginSuccessful = ndDAO.login(username, password);

                if (isLoginSuccessful) {
                    String role = ndDAO.getUserRole(); // Lấy vai trò từ SharedPreferences
                    Intent intent;
                    if ("admin".equalsIgnoreCase(role)) {
                        // Chuyển hướng đến MainActivity
                        intent = new Intent(dang_nhap.this, MainActivity.class);
                    } else {
                        // Chuyển hướng đến MainActivity
                        intent = new Intent(dang_nhap.this, MainActivity2.class);
                    }
                    startActivity(intent);
                    finish(); // Đóng activity đăng nhập để không quay lại được
                } else {
                    Toast.makeText(dang_nhap.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
