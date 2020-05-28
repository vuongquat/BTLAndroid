package com.example.quanlychitieucanhan;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.KhoanThu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SuaKhoanThuActivity extends AppCompatActivity {

    TextView txtLich;
    EditText txtTenND,txtNguoiTao,txtSoTien,txtGhiChu;
    Button btnSua,btnQuayVe;
    ImageButton btnLich;

    Calendar calendar = Calendar.getInstance();//Trả về ngày giờ tháng năm hiện tại

    SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("dd-MM-yyyy");//Hiển thị theo định dạng ngày tháng năm

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_khoan_thu);
        addControls();
        addEvents();
    }


    private void addEvents() {
        btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiNgayThang();
            }
        });

        btnQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySuaDuLieu();
            }
        });

    }


    //========================= Xử lý sửa dữ liệu ============================================//
    private void xuLySuaDuLieu() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        Intent intent = getIntent();
        int id_khoanthu = intent.getIntExtra("id_khoanthu",0);
        ContentValues row = new ContentValues();
        if(txtTenND.getText().toString().equals("") || txtNguoiTao.getText().toString().equals("") || txtSoTien.getText().toString().equals(""))
        {
            Toast.makeText(this, "Mời bạn vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            row.put("tenkhoanthu",txtTenND.getText().toString());
            row.put("nguoitao",txtNguoiTao.getText().toString());
            row.put("ngaytao",txtLich.getText().toString());
            row.put("sotienthu",txtSoTien.getText().toString());
            row.put("ghichu",txtGhiChu.getText().toString());
            database.update("khoanthu",row,"id_khoanthu=?",new String[]{id_khoanthu+""});
            Toast.makeText(this, "Sửa khoản thu thành công", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    //========================= Xử lý sửa dữ liệu ============================================//




    //======================= Hiển thị ngày tháng khi xảy ra sự kiện=========================================//
    private void xuLyHienThiNgayThang() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                txtLich.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };//Lắng nghe sự thay đổi của người dùng

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();//Hiển thị cửa sổ chọn ngày tháng năm lên
    }
    //======================= Hiển thị ngày tháng khi xảy ra sự kiện=========================================//

    private void addControls() {
        txtLich = findViewById(R.id.txtLich);
        txtTenND = findViewById(R.id.txtTenND);
        txtNguoiTao = findViewById(R.id.txtNguoiTao);
        txtSoTien = findViewById(R.id.txtSoTien);
        txtGhiChu = findViewById(R.id.txtGhiChu);
        btnLich = findViewById(R.id.btnLich);
        btnSua = findViewById(R.id.btnSua);
        btnQuayVe = findViewById(R.id.btnQuayVe);

        calendar = Calendar.getInstance(); //Hiển thị lên ngày tháng
        txtLich.setText(simpleDateFormat.format(calendar.getTime()));

        Intent intent = getIntent();
        String tenkhoanthu = intent.getStringExtra("tenkhoanthu");
        String nguoitao = intent.getStringExtra("nguoitao");
        String ngaytao = intent.getStringExtra("ngaytao");
        String ghichu = intent.getStringExtra("ghichu");

        txtTenND.setText(tenkhoanthu);
        txtNguoiTao.setText(nguoitao);
        txtLich.setText(ngaytao);
        txtGhiChu.setText(ghichu);


    }


}
