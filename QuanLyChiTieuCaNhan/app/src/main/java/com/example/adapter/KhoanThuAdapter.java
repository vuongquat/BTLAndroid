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
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.model.KhoanThu;
import com.example.quanlychitieucanhan.R;
import com.example.quanlychitieucanhan.SuaKhoanThuActivity;
import com.example.quanlychitieucanhan.XoaKhoanThuActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KhoanThuAdapter extends ArrayAdapter<KhoanThu> implements Filterable {


    Activity context; //Màn hình sử dụng layout
    int resource;//layout cho từng dòng muốn hiển thị
    List<KhoanThu> objects;//Nguồn dữ liệu hiển thị lên giao diện


    public KhoanThuAdapter(Activity context, int resource,List<KhoanThu> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row =  layoutInflater.inflate(this.resource,null);

        TextView txtTenND = row.findViewById(R.id.txtTenND);
        TextView txtNguoiTao = row.findViewById(R.id.txtNguoiTao);
        TextView txtNgayTao = row.findViewById(R.id.txtNgayTao);
        TextView txtSoTien = row.findViewById(R.id.txtSoTien);
        TextView txtGhiChu = row.findViewById(R.id.txtGhiChu);

        ImageButton btnSua = row.findViewById(R.id.btnSua);
        ImageButton btnXoa = row.findViewById(R.id.btnXoa);

        final KhoanThu khoanThu = this.objects.get(position);//Trả về khoản thu muốn hiển thị
        txtTenND.setText(khoanThu.getTenKhoanThu());
        txtNguoiTao.setText(khoanThu.getTenNguoiTao());
        txtNgayTao.setText(khoanThu.getNgayTao());
        txtSoTien.setText(dinhDangTien(khoanThu.getSoTien())+" VNĐ");
        txtGhiChu.setText(khoanThu.getGhiChu());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaKhoanThu(khoanThu);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoaKhoanThu(khoanThu);
            }
        });

        return row;

    }

    private void xuLyXoaKhoanThu(KhoanThu khoanThu) {
        Intent intent = new Intent(context, XoaKhoanThuActivity.class);
        intent.putExtra("id_khoanthu",khoanThu.getId_khoanthu());
        context.startActivity(intent);
    }


    private void SuaKhoanThu(KhoanThu khoanThu) {
        Intent intent = new Intent(context,SuaKhoanThuActivity.class);
        intent.putExtra("id_khoanthu",khoanThu.getId_khoanthu());
        intent.putExtra("tenkhoanthu",khoanThu.getTenKhoanThu());
        intent.putExtra("nguoitao",khoanThu.getTenNguoiTao());
        intent.putExtra("ngaytao",khoanThu.getNgayTao());
        intent.putExtra("sotien",khoanThu.getSoTien());
        intent.putExtra("ghichu",khoanThu.getGhiChu());
        context.startActivity(intent);
    }

    private static String dinhDangTien(String number)
    {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Double.parseDouble(number));
    }

}
