package YC_PhieuMuon;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import DAO.PhieuMuonDAO;
import Model.PhieuMuon;

public class Qli_YC_PM_Adapter extends RecyclerView.Adapter<QLi_YeuCauViewHolder> {
    private ArrayList<PhieuMuon> yeuCauList;
    private Context context;

    public Qli_YC_PM_Adapter(Context context, ArrayList<PhieuMuon> yeuCauList) {
        this.context = context;
        this.yeuCauList = yeuCauList;
    }

    @NonNull
    @Override
    public QLi_YeuCauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qli_yc_pm_viewholder, parent, false);
        return new QLi_YeuCauViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLi_YeuCauViewHolder holder, int position) {
        PhieuMuon yeuCau = yeuCauList.get(position);
        holder.tvNguoiMuon.setText(yeuCau.tenngm);
        holder.tvTenSach.setText(yeuCau.tentp);
        holder.btnTuChoi.setOnClickListener(v -> {
            PhieuMuonDAO pmd = new PhieuMuonDAO(context);
            pmd.xuLyYeuCau(yeuCau, false);  // false: từ chối yêu cầu
            Toast.makeText(context, "Hủy yêu cầu thành công", Toast.LENGTH_SHORT).show();
            yeuCauList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, yeuCauList.size());
        });

        holder.btnChiTiet.setOnClickListener(v -> {
            showChiTietDialog(yeuCau);
        });
    }

    @Override
    public int getItemCount() {
        return yeuCauList.size();
    }
    private void showChiTietDialog(PhieuMuon yeuCau) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chi tiết phiếu mượn");

        View view = LayoutInflater.from(context).inflate(R.layout.sua_phieumuon_pm, null);
        builder.setView(view);
        TextView tvTenngm = view.findViewById(R.id.ten_ngm);
        TextView tvTentp = view.findViewById(R.id.ten_tp);
        TextView tvIdSach = view.findViewById(R.id.id_tacpham);
        TextView tvSdt = view.findViewById(R.id.sdt);
        TextView tvNgayMuon = view.findViewById(R.id.tv_ngaymuon);
        TextView tvNgayTra = view.findViewById(R.id.tv_ngaytra);

        // Cập nhật dữ liệu
        tvIdSach.setText(String.valueOf(yeuCau.masp));
        tvSdt.setText(yeuCau.sdt);
        tvTentp.setText(yeuCau.tentp);
        tvTenngm.setText(yeuCau.tenngm);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        tvNgayMuon.setText(sdf.format(yeuCau.ngaymuon));
        tvNgayTra.setText(sdf.format(yeuCau.ngaytra));

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


