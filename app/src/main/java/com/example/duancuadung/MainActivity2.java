package com.example.duancuadung;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import NguoiMuon.SanPhamNM;
import YC_PhieuMuon.Qli_YeuCauFragment;
import dangnhap.dang_nhap;
import ds_sanpham.SanPhamFragment;
import tb.ThongBaoFragment;

public class MainActivity2 extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        navigation=(NavigationView)findViewById(R.id.nvView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");

        ActionBar ab=getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        SanPhamNM f= new SanPhamNM();
        FragmentManager fagment=getSupportFragmentManager();
        fagment.beginTransaction()
                .replace(R.id.flContent,f)
                .commit();


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                Fragment f;

                if(id== R.id.et_sach){
                    f=new SanPhamNM();
                    getSupportActionBar().setTitle("Trang chủ");
                }
                else{
                    f=new SanPhamNM();
                }
                if(id== R.id.et_dandgxuat){
                    Intent i =new Intent(MainActivity2.this, dang_nhap.class);
                    startActivity(i);
                }
                if(id==R.id.et_sachmuon){
                    f= new Qli_YeuCauFragment();
                    getSupportActionBar().setTitle("Sách mượn");
                }
                if(id==R.id.et_thongbao){
                    f= new ThongBaoFragment();
                    getSupportActionBar().setTitle("Thông báo");
                }
                FragmentManager fagment=getSupportFragmentManager();
                fagment.beginTransaction()
                        .replace(R.id.flContent,f)
                        .commit();

                setTitle(item.getTitle());
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id=item.getItemId();
        if(id == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}