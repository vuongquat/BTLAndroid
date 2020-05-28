package com.example.quanlychitieucanhan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DangKyActivity extends AppCompatActivity {

    EditText txtHoTen,txtTenDangNhap,txtMatKhau,txtXacNhanMK,txtSDT;
    Button btnDangKy,btnQuayVe;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        addControls();
        addEvents();
    }


    private void addEvents() {
        btnQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangKy();
            }
        });
    }

    public boolean kiemTraTaiKhoan(String taiKhoan)
    {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        String[] columns ={"taikhoan"};
        String selection = "taikhoan=?";
        String[] selectionArgs = {taiKhoan};
        Cursor cursor =database.query("nguoidung",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }
    public boolean kiemTraSoDienThoai(String sdt)
    {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        String[] columns ={"sdt"};
        String selection = "sdt=?";
        String[] selectionArgs = {sdt};
        Cursor cursor =database.query("nguoidung",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    private void xuLyDangKy() {

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();

        boolean isExist1 = kiemTraTaiKhoan(txtTenDangNhap.getText().toString());
        boolean isExist2 = kiemTraSoDienThoai(txtSDT.getText().toString());

        if(txtHoTen.getText().toString().equals("") || txtTenDangNhap.getText().toString().equals("") || txtMatKhau.getText().toString().equals("") || txtXacNhanMK.getText().toString().equals("") || txtSDT.getText().toString().equals(""))
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin để đăng ký", Toast.LENGTH_LONG).show();
        }
        else if(isExist1 )
        {
            Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_LONG).show();
        }
        else if(isExist2)
        {
            Toast.makeText(this, "Số điện thoại đã tồn tại", Toast.LENGTH_LONG).show();
        }
        else if(txtMatKhau.getText().toString().equals(txtXacNhanMK.getText().toString()) == true)
        {

                row.put("ten",txtHoTen.getText().toString());
                row.put("taikhoan",txtTenDangNhap.getText().toString());
                row.put("matkhau",txtMatKhau.getText().toString());
                row.put("sdt",txtSDT.getText().toString());
                database.insert("nguoidung",null,row);
                Toast.makeText(this,"Bạn đã đăng ký  tài khoản thành công ",Toast.LENGTH_LONG).show();
                finish();



        }
        else if(txtMatKhau.getText().toString().equals(txtXacNhanMK.getText().toString()) == false) {
            Toast.makeText(this, "Mật khẩu không trùng khớp vui lòng kiểm tra lại ", Toast.LENGTH_LONG).show();
        }




    }

    private void addControls() {

        txtHoTen = findViewById(R.id.txtHoTen);
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        txtXacNhanMK = findViewById(R.id.txtXacNhanMK);
        txtSDT = findViewById(R.id.txtSDT);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuayVe = findViewById(R.id.btnQuayVe);
    }
}
