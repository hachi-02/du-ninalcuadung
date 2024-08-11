package NguoiMuon;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

public class SanPhamPMViewholder extends RecyclerView.ViewHolder {
    TextView tv_id,tv_tentp,tv_theloai,tv_soluong,tv_dongia;
    LinearLayout bt_muon;

    public SanPhamPMViewholder(@NonNull View itemView) {
        super(itemView);
        this.tv_id=itemView.findViewById(R.id.tv_masp);
        this.tv_tentp=itemView.findViewById(R.id.tv_tentp);
        this.tv_theloai=itemView.findViewById(R.id.tv_theloai);
        this.tv_soluong=itemView.findViewById(R.id.tv_soluong);
        this.tv_dongia=itemView.findViewById(R.id.tv_dongia);


        this.bt_muon=itemView.findViewById(R.id.bt_muon);
    }

}