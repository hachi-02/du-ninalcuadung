package NguoiMuon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import java.util.ArrayList;

import Database.myhelper;
import Model.SanPham;
import Model.TheLoai;
import ds_sanpham.SanPhamViewholder;


public class SanPhamPMAdapterFragment extends RecyclerView.Adapter<SanPhamPMViewholder> {
    ArrayList<SanPham> ds ;
    Context context;
    SanPhamNM spnm;
    ArrayList<TheLoai> dstl;

    public SanPhamPMAdapterFragment(Context context, ArrayList<SanPham> ds, SanPhamNM spnm) {
        this.context = context;
        this.ds = ds;
        this.spnm=spnm;
        myhelper helper = new myhelper(context);
        this.dstl = new ArrayList<>(helper.getTheLoaiID());
    }
    public SanPhamPMViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_san_pham_pm_viewholder, parent, false);

        return new SanPhamPMViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamPMViewholder holder,@SuppressLint("RecyclerView") int position) {
        SanPham sp = ds.get(position);
        //gắn vị  trí index
        holder.tv_id.setText(sp.masp+"");
        holder.tv_tentp.setText(sp.tentp+"");
        String categoryName = tenTLQuaID(sp.theloai);
        holder.tv_theloai.setText(categoryName);
        holder.tv_soluong.setText(sp.soluong+"");
        holder.tv_dongia.setText(sp.dongia+"");

        holder.bt_muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnm.muonSach(sp);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ds.size();
    }

    private String tenTLQuaID(int theloaiId) {
        for (TheLoai theloai : dstl) {
            if (theloai.getId() == theloaiId) {
                return theloai.getName();
            }
        }
        return "Unknown";
    }


}