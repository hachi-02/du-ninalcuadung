package NguoiMuon;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DAO.PhieuMuonDAO;
import DAO.SanPhamNMDAO;
import Model.PhieuMuon;
import Model.SanPham;

public class SanPhamNM extends Fragment {
    RecyclerView rcv;
    SanPhamNMDAO spnmd;
    ArrayList<SanPham> ds;
    Button bt_ngaymuon, bt_ngaytra;
    TextView tv_ngaymuon, tv_ngaytra;
    EditText et_tentp, et_tenngm, et_sdt, et_masp;
    PhieuMuonDAO pmd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sanphampm, container, false);
        rcv = v.findViewById(R.id.rcv);
        spnmd = new SanPhamNMDAO(getContext());
        dulieu();
        return v;
    }

    public void dulieu() {
        ds = spnmd.xemSP();

        SanPhamPMAdapterFragment adapter = new SanPhamPMAdapterFragment(getContext(), ds, this);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);


    }

    public void muonSach(final SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.them_phieumuonpm, null);

        // Khởi tạo các view
        bt_ngaymuon = view.findViewById(R.id.bt_ngaymuon);
        bt_ngaytra = view.findViewById(R.id.bt_ngaytra);
        tv_ngaymuon = view.findViewById(R.id.tv_ngaymuon);
        tv_ngaytra = view.findViewById(R.id.tv_ngaytra);
        et_masp = view.findViewById(R.id.id_tacpham);
        et_tentp = view.findViewById(R.id.ten_tp);
        et_tenngm = view.findViewById(R.id.ten_ngm);
        et_sdt = view.findViewById(R.id.sdt);

        // Đặt mã sản phẩm và tên sản phẩm tự động
        et_masp.setText(String.valueOf(sanPham.masp));
        et_tentp.setText(sanPham.tentp);

        builder.setView(view);

        // Chọn ngày mượn và ngày trả
        bt_ngaymuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaymuon);
            }
        });
        bt_ngaytra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaytra);
            }
        });

        // Xử lý khi nhấn nút "Mượn"
        builder.setPositiveButton("Mượn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int masp = Integer.parseInt(et_masp.getText().toString());
                String tentp = et_tentp.getText().toString();
                String tenngm = et_tenngm.getText().toString();
                String sdt = et_sdt.getText().toString();
                String ngaymuonStr = tv_ngaymuon.getText().toString();
                String ngaytraStr = tv_ngaytra.getText().toString();

                // Định dạng ngày tháng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date ngaymuon = null;
                Date ngaytra = null;
                try {
                    ngaymuon = dateFormat.parse(ngaymuonStr);
                    ngaytra = dateFormat.parse(ngaytraStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                PhieuMuon phieuMuon = new PhieuMuon(tenngm, sanPham.masp, sanPham.tentp, sdt, ngaymuon, ngaytra);
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
                phieuMuonDAO.themYeuCauPhieuMuon(phieuMuon);
                Toast.makeText(getContext(), "Yêu cầu đã được gửi", Toast.LENGTH_SHORT).show();
                dulieu();
            }
        });

        // Xử lý khi nhấn nút "Hủy"
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDatePickerDialog(final TextView dateTextView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                        dateTextView.setText(selectedDate);
                        Toast.makeText(getContext(), "Ngày đã chọn: " + selectedDate, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
        // Ngày tối thiểu là hiện tại
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
