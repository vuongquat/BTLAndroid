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

public class DoiMatKhauActivity extends AppCompatActivity {

    Button btnDoiMatKhau,btnQuayVe;
    EditText txtMatKhau,txtMatKhauMoi,txtNhapLaiMK;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        addControls();
        addEvents();
    }

    //===============================  gán sự kiện xử lý cho các control ======================================//
    private void addEvents() {
        btnQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDoiMatKhau();
            }
        });
    }
    //===============================  gán sự kiện xử lý cho các control ======================================//


 //================= Xử lý đổi mật khẩu ==========================================//
    private void xuLyDoiMatKhau() {
        Intent intent = getIntent();
        int id_nguoidung = intent.getIntExtra("id_nguoidung",0);
        String matkhau = intent.getStringExtra("matkhau");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();


        if(txtMatKhau.getText().toString().equals("") || txtNhapLaiMK.getText().toString().equals("") || txtMatKhauMoi.getText().toString().equals(""))
        {
            Toast.makeText(this,"Bạn chưa nhập đủ thông tin, vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
        }
        else if(txtMatKhau.getText().toString().equals(matkhau) == false)
        {
            Toast.makeText(this, "Mật khẩu cũ không đúng, vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
        }
        else if (txtMatKhau.getText().toString().equals(txtMatKhauMoi.getText().toString()) && txtMatKhau.getText().toString() != "")
        {
            Toast.makeText(this, "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
        }
        else if(txtMatKhauMoi.getText().toString().equals(txtNhapLaiMK.getText().toString()) == false)
        {
            Toast.makeText(this, "Mật khẩu không trùng khớp, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        }

        else if(txtMatKhauMoi.getText().toString().equals(txtNhapLaiMK.getText().toString()))
        {
            row.put("matkhau",txtMatKhauMoi.getText().toString());
            database.update("nguoidung",row,"id_nguoidung=?",new String[]{id_nguoidung+""});
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    //================= Xử lý đổi mật khẩu ==========================================//


    private void addControls() {
        txtMatKhau = findViewById(R.id.txtMatKhau);
        txtMatKhauMoi = findViewById(R.id.txtMatKhauMoi);
        txtNhapLaiMK = findViewById(R.id.txtNhapLaiMK);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnQuayVe = findViewById(R.id.btnQuayVe);
    }
}
