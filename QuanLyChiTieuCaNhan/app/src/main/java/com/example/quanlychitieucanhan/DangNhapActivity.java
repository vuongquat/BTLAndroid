package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DangNhapActivity extends AppCompatActivity {

    EditText txtTenDangNhap,txtMatKhau;
    Button btnDangNhap,btnDangKy,btnQuenMatKhau;
    CheckBox chkNhoTaiKhoan;


    String DATABASE_NAME="quanlychitieu.sqlite"; //Tên file sql trong thư mục assets
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null; //Cho phép truy vấn dữ liệu để thêm sửa xóa

    String tenThongTinDangNhap = "login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        xuLySaoChepCSDLTuAssetsVaoHeThongMobile();
        addControls();
        addEvents();
    }

    //=============== Hàm để xử lý sao chép CSDL từ assets vào data của moblie==============
    private void xuLySaoChepCSDLTuAssetsVaoHeThongMobile() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists())
        {
            try {

                CopyDataBaseFromAssets();

            }
            catch (Exception ex)
            {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();

            }
        }

    }

    //=============== Hàm copy database từ assets =====================
    private void CopyDataBaseFromAssets() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
            {
                f.mkdir();
            }
            OutputStream myOutPut = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer) )>0)
            {
                myOutPut.write(buffer,0,length);
            }
            myOutPut.flush();
            myOutPut.close();
            myInput.close();

        }
        catch (Exception ex)
        {
            Log.e("LOI_SAOCHEP",ex.toString());
        }
    }

    //=============== Hàm trả về đường dẫn để lưu CSDL  ==========================
    public String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME; //Trả về đường dẫn để lưu trữ
    }


//===============================  gán sự kiện xử lý cho các control ======================================//
    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });
        btnQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quenMatKhau();
            }
        });
    }
//===============================  gán sự kiện xử lý cho các control ======================================//


//=================== Mở trang quên mật khẩu ===========================//
    private void quenMatKhau() {
        Intent intent = new Intent(this,QuenMatKhauAcitivity.class);
        this.startActivity(intent);
    }


 //======================== Mở trang đăng ký ======================================//
    private void dangKy() {
        Intent intent = new Intent(this,DangKyActivity.class);
        this.startActivity(intent);
    }


//============================  kiểm tra tài khoản và mật khẩu ===============================
    public boolean kiemTraTKMK(String taiKhoan,String matKhau)
    {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        String[] columns ={"taikhoan"};
        String selection = "taikhoan=? and matkhau=?";
        String[] selectionArgs = {taiKhoan,matKhau};
        Cursor cursor =database.query("nguoidung",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

 //=============== Hàm kiểm tra tài khoản người dùng có tồn tại không =============================//
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

//=================== Hàm xử lý đăng nhập =========================//
    private void xuLyDangNhap() {

        boolean isExist1 = kiemTraTaiKhoan(txtTenDangNhap.getText().toString());
        boolean isExist2 = kiemTraTKMK(txtTenDangNhap.getText().toString(),txtMatKhau.getText().toString());



        if(txtTenDangNhap.getText().toString().equals("") || txtMatKhau.getText().toString().equals(""))
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else if(isExist1 && isExist2 ) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("taiKhoan",txtTenDangNhap.getText().toString());
            this.startActivity(intent);
            Toast.makeText(this,"Đăng nhập thành công!",Toast.LENGTH_LONG).show();
        }
        else if (isExist1 == false)
        {
            Toast.makeText(this,"Tài khoản không tồn tại!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Mật khẩu không chính xác, vui lòng thử lại!",Toast.LENGTH_LONG).show();
        }


    }

    //============================= Đăng ký id cho các Controls ======================================
    private void addControls() {
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
        chkNhoTaiKhoan = findViewById(R.id.chkNhoTaiKhoan);

    }


    //============================== sử dụng share preference đê lưu tài khoản đăng nhập ================
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(tenThongTinDangNhap,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("taiKhoan",txtTenDangNhap.getText().toString());
        editor.putString("matKhau",txtMatKhau.getText().toString());
        editor.putBoolean("Save",chkNhoTaiKhoan.isChecked());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(tenThongTinDangNhap,MODE_PRIVATE);
        String taiKhoan = sharedPreferences.getString("taiKhoan","");
        String matKhau = sharedPreferences.getString("matKhau","");
        boolean save = sharedPreferences.getBoolean("Save",false);
        if(save)
        {
            txtTenDangNhap.setText(taiKhoan);
            txtMatKhau.setText(matKhau);
        }

    }
}
