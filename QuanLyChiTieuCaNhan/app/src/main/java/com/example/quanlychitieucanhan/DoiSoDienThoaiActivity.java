package com.example.quanlychitieucanhan;

import android.content.ContentValues;
import android.content.Intent;
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

public class DoiSoDienThoaiActivity extends AppCompatActivity {

    Button btnDoiSDT,btnQuayVe;
    EditText txtSDT,txtSDTMoi;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_so_dien_thoai);
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
        btnDoiSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDoiSDT();
            }
        });
    }

 //================================ Xử lý đổi số điện thoại =================================================//
    private void xuLyDoiSDT() {
        Intent intent =getIntent();
        int id_nguoidung = intent.getIntExtra("id_nguoidung",0);
        String sdt = intent.getStringExtra("sdt");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();
        if (txtSDT.getText().toString().equals("") || txtSDTMoi.getText().toString().equals(""))
        {
            Toast.makeText(this,"Bạn chưa nhập đủ thông tin, vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
        }
        else if(txtSDT.getText().toString().equals(txtSDTMoi.getText().toString()))
        {
            Toast.makeText(this,"Số điện thoại mới không được trùng số điện thoại cũ!",Toast.LENGTH_SHORT).show();
        }
        else if(txtSDT.getText().toString().equals(sdt) == false)
        {
            Toast.makeText(this,"Số điện thoại cũ không đúng, vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            row.put("sdt",txtSDTMoi.getText().toString());
            database.update("nguoidung",row,"id_nguoidung=?",new String[]{id_nguoidung+""});
            Toast.makeText(this, "Bạn đã đổi số điện thoại thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    //================================ Xử lý đổi số điện thoại =================================================//

    private void addControls() {
        txtSDT = findViewById(R.id.txtSDT);
        txtSDTMoi = findViewById(R.id.txtSDTMoi);
        btnDoiSDT = findViewById(R.id.btnDoiSDT);
        btnQuayVe = findViewById(R.id.btnQuayVe);
    }
}
