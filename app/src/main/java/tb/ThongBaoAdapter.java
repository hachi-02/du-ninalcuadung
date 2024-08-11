package tb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.duancuadung.R;
import java.util.ArrayList;

import DAO.PhieuMuonDAO;

public class ThongBaoAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> thongBaoList;
    private PhieuMuonDAO phieuMuonDAO;

    public ThongBaoAdapter(Context context, ArrayList<String> thongBaoList) {
        super(context, 0, thongBaoList);
        this.context = context;
        this.thongBaoList = thongBaoList;
        this.phieuMuonDAO = new PhieuMuonDAO(context); // Khởi tạo DAO ở đây hoặc có thể truyền từ bên ngoài
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thongbao, parent, false);
        }

        String thongBao = thongBaoList.get(position);
        TextView textViewThongBao = convertView.findViewById(R.id.textview_thongbao);
        Button buttonXoa = convertView.findViewById(R.id.button_xoa);

        textViewThongBao.setText(thongBao);

        buttonXoa.setOnClickListener(v -> {
            thongBaoList.remove(position);
            notifyDataSetChanged();
            phieuMuonDAO.xoaThongBao(thongBao);
        });

        return convertView;
    }
}
