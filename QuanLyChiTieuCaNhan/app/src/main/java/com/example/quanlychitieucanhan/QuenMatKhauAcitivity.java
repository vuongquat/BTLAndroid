package com.example.quanlychitieucanhan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class QuenMatKhauAcitivity extends AppCompatActivity {

    EditText txtTaiKhoan,txtMatKhauMoi,txtSDT,txtNhapLaiMK;
    Button btnLayMatKhau,btnQuayVe;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);


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
        btnLayMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayMatKhau();
            }
        });

    }



//========================= Hàm kiểm tra tài khoản và mật khẩu người dùng có tồn tại không ========================//
    public boolean kiemTraTaiKhoan(String taiKhoan, String sdt)
    {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        String[] columns ={"taikhoan"};
        String selection = "taikhoan=? and sdt=? ";
        String[] selectionArgs = {taiKhoan,sdt};
        Cursor cursor =database.query("nguoidung",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }
    //========================= Hàm kiểm tra tài khoản và mật khẩu người dùng ========================//



    //======================================== Xử lý lấy lại mật khẩu ============================================//
    private void xuLyLayMatKhau() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();

        boolean isExist = kiemTraTaiKhoan(txtTaiKhoan.getText().toString(),txtSDT.getText().toString());

        if(txtTaiKhoan.getText().toString().equals("") || txtSDT.getText().toString().equals("") || txtMatKhauMoi.getText().toString().equals("") || txtNhapLaiMK.getText().toString().equals(""))
        {
            Toast.makeText(this, "Bạn chưa nhập đủ thông tin, vui lòng kiểm tra lại!", Toast.LENGTH_LONG).show();
        }
        else  if (txtMatKhauMoi.getText().toString().equals(txtNhapLaiMK.getText().toString()) == false)
        {
            Toast.makeText(this, "Mật khẩu không trùng khớp, vui lòng kiểm tra lại!", Toast.LENGTH_LONG).show();
        }
        else if (isExist && txtMatKhauMoi.getText().toString().equals(txtNhapLaiMK.getText().toString()) == true)
        {
            row.put("matkhau",txtMatKhauMoi.getText().toString());
            database.update("nguoidung",row,"taikhoan=?",new String[]{txtTaiKhoan.getText().toString()});
            Toast.makeText(this,"Bạn đã lấy lại mật khẩu thành công!",Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "Tài khoản hoặc số điện thoại không đúng, vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
        }

    }
    //===================================== Xử lý lấy lại mật khẩu ============================================//

    private void addControls() {
        txtTaiKhoan = findViewById(R.id.txtTaiKhoan);
        txtMatKhauMoi = findViewById(R.id.txtMatKhauMoi);
        txtNhapLaiMK = findViewById(R.id.txtNhapLaiMK);
        txtSDT = findViewById(R.id.txtSDT);
        btnLayMatKhau = findViewById(R.id.btnLayMatKhau);
        btnQuayVe = findViewById(R.id.btnQuayVe);
    }
}
