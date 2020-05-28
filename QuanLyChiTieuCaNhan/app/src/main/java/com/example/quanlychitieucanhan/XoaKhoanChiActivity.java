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

public class XoaKhoanChiActivity extends AppCompatActivity {

    Button btnCo;
    Button btnKhong;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoa_khoan_chi);
        addControls();
        addEvents();
    }


    private void addEvents() {
        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoaKhoanChi();
            }
        });
    }

   //======================== Xử lý xóa khoản chi==========================//
    private void xuLyXoaKhoanChi() {
        Intent intent = getIntent();
        int id_khoanchi = intent.getIntExtra("id_khoanchi",0);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        database.delete("khoanchi","id_khoanchi=?",new String[]{id_khoanchi+""});
        finish();
    }
    //======================== Xử lý xóa khoản chi==========================//

    private void addControls() {
        btnCo = findViewById(R.id.btnCo);
        btnKhong = findViewById(R.id.btnKhong);
    }
}
