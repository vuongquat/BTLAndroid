package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class XoaKhoanThuActivity extends AppCompatActivity {

    Button btnCo;
    Button btnKhong;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoa_khoan_thu);
        addControls();
        addEvent();
    }


    private void addEvent() {
        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoaDuLieu();
            }
        });
    }


    //==================== Xử lý xóa khoản thu ==================================//
    private void xuLyXoaDuLieu() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        Intent intent = getIntent();
        int id_khoanthu = intent.getIntExtra("id_khoanthu",0);
        database.delete("khoanthu","id_khoanthu=?",new String[]{id_khoanthu+""});
        finish();
    }
    //==================== Xử lý xóa khoản thu ==================================//

    private void addControls() {
        btnCo = findViewById(R.id.btnCo);
        btnKhong = findViewById(R.id.btnKhong);
    }
}
