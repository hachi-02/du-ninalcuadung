package YC_PhieuMuon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import DAO.PhieuMuonDAO;
import Model.PhieuMuon;
import NguoiMuon.SanPhamPMAdapterFragment;
import phieumuon.PhieuMuonAdapter;

import java.util.ArrayList;

public class YeuCauFragment extends Fragment {
    private RecyclerView recyclerView;
    private YC_PM_Adapter adapter;
    private ArrayList<PhieuMuon> yeuCauList;
    private PhieuMuonDAO phieuMuonDAO;
    ImageView img;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeu_cau, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_yeu_cau);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        img=view.findViewById(R.id.img_yc);

        phieuMuonDAO = new PhieuMuonDAO(getContext());
        yeuCauList = phieuMuonDAO.xemPM();
        adapter = new YC_PM_Adapter(getContext(), yeuCauList);
        recyclerView.setAdapter(adapter);
        dulieu();
        return view;
    }
    public void dulieu() {
        yeuCauList = phieuMuonDAO.xemYeuCauPhieuMuon();

        if (yeuCauList.isEmpty()) {
            img.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            YC_PM_Adapter adapter = new YC_PM_Adapter(getContext(), yeuCauList);
            LinearLayoutManager linear = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linear);
            recyclerView.setAdapter(adapter);
        }



    }
}

