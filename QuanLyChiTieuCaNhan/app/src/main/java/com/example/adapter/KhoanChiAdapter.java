package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.model.KhoanChi;
import com.example.quanlychitieucanhan.R;
import com.example.quanlychitieucanhan.SuaKhoanChiActivity;
import com.example.quanlychitieucanhan.XoaKhoanChiActivity;

import java.text.DecimalFormat;
import java.util.List;

public class KhoanChiAdapter extends ArrayAdapter<KhoanChi> {

    Activity context;
    int resource;
    List<KhoanChi> objects;

    public KhoanChiAdapter(Activity context, int resource, List<KhoanChi> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;

    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row =  layoutInflater.inflate(this.resource,null);

        TextView txtTenND = row.findViewById(R.id.txtTenND);
        TextView txtNguoiTao = row.findViewById(R.id.txtNguoiTao);
        TextView txtNgayTao = row.findViewById(R.id.txtNgayTao);
        TextView txtSoTien = row.findViewById(R.id.txtSoTien);
        TextView txtGhiChu = row.findViewById(R.id.txtGhiChu);

        ImageButton btnSua = row.findViewById(R.id.btnSua);
        ImageButton btnXoa = row.findViewById(R.id.btnXoa);

        final KhoanChi khoanChi = this.objects.get(position); //Trả về khoản chi muốn hiển thị;
        txtTenND.setText(khoanChi.getTenKhoanChi());
        txtNguoiTao.setText(khoanChi.getTenNguoiTao());
        txtNgayTao.setText(khoanChi.getNgayTao());
        txtSoTien.setText(dinhDangTien(khoanChi.getSoTien()) + " VNĐ");
        txtGhiChu.setText(khoanChi.getGhiChu());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaKhoanChi(khoanChi);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XoaKhoanChi(khoanChi);
            }
        });

        return row ;
    }

    private void XoaKhoanChi(KhoanChi khoanChi) {
        Intent intent = new Intent(context, XoaKhoanChiActivity.class);
        intent.putExtra("id_khoanchi",khoanChi.getId_khoanchi());
        context.startActivity(intent);
    }

    private void SuaKhoanChi(KhoanChi khoanChi) {
        Intent intent = new Intent(context, SuaKhoanChiActivity.class);
        intent.putExtra("id_khoanchi",khoanChi.getId_khoanchi());
        intent.putExtra("tenkhoanchi",khoanChi.getTenKhoanChi());
        intent.putExtra("nguoitao",khoanChi.getTenNguoiTao());
        intent.putExtra("ngaytao",khoanChi.getNgayTao());
        intent.putExtra("sotien",khoanChi.getSoTien());
        intent.putExtra("ghichu",khoanChi.getGhiChu());
        context.startActivity(intent);
    }

    private static String dinhDangTien(String number)
    {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Double.parseDouble(number));
    }
}
