package tb;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.duancuadung.R;
import DAO.PhieuMuonDAO;
import java.util.ArrayList;

public class ThongBaoFragment extends Fragment {

    private ListView listViewThongBao;
    private ThongBaoAdapter thongBaoAdapter;
    private ArrayList<String> thongBaoList;
    private PhieuMuonDAO phieuMuonDAO;

    public ThongBaoFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);

        listViewThongBao = view.findViewById(R.id.listview_thongbao);
        thongBaoList = new ArrayList<>();

        // Khởi tạo adapter
        thongBaoAdapter = new ThongBaoAdapter(getContext(), thongBaoList);
        listViewThongBao.setAdapter(thongBaoAdapter);

        phieuMuonDAO = new PhieuMuonDAO(getContext());
        // Tải danh sách thông báo từ cơ sở dữ liệu
        thongBaoList.addAll(phieuMuonDAO.layDanhSachThongBao());
        thongBaoAdapter.notifyDataSetChanged();

        return view;
    }
}
