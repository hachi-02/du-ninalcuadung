package YC_PhieuMuon;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

public class QLi_YeuCauViewHolder extends RecyclerView.ViewHolder {
    TextView tvNguoiMuon, tvTenSach;
    Button  btnTuChoi, btnChiTiet,btnDaMuon;

    public QLi_YeuCauViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNguoiMuon = itemView.findViewById(R.id.tv_ten_ngm);
        tvTenSach = itemView.findViewById(R.id.tv_tentp);

        btnTuChoi = itemView.findViewById(R.id.btnTuChoi);
        btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
    }
}
