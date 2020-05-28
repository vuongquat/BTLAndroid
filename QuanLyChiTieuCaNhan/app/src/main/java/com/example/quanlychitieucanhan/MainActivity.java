package com.example.quanlychitieucanhan;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.example.adapter.KhoanChiAdapter;
import com.example.adapter.KhoanThuAdapter;
import com.example.model.KhoanChi;
import com.example.model.KhoanThu;
import com.example.model.NguoiDung;
import com.example.model.SoTienThuChi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txtNguoiDung,txtSoDu,txtLich,txtTongChi,txtTongThu;
    EditText txtTenND,txtNguoiTao,txtSoTien,txtGhiChu;
    Button btnThu,btnChi;
    ImageButton btnLich;


    float soDu = 0;

    Calendar calendar = Calendar.getInstance();//Trả về ngày giờ tháng năm hiện tại

    SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("dd-MM-yyyy");//Hiển thị theo định dạng ngày tháng năm

    ListView lvThu;
    ArrayList<KhoanThu>dsKhoanThu;
    KhoanThuAdapter khoanThuAdapter;

    ListView lvChi;
    ArrayList<KhoanChi> dsKhoanChi;
    KhoanChiAdapter khoanChiAdapter;

    String DATABASE_NAME="quanlychitieu.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addControls();
        addEvents();

        hienThiDanhSachKhoanThu();
        hienThiDanhSachKhoanChi();
    }




 //======================= Lấy dữ liệu khoản chi từ database thêm vào model ==============================================//
    private void hienThiDanhSachKhoanChi() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor =database.query("khoanchi",null,"id_nguoidung=?",new String[]{layIDNguoiDung()+""},null,null,"ngaytao");
        dsKhoanChi.clear();
        while (cursor.moveToNext())
        {
            int id_khoanchi = cursor.getInt(0);
            String tenKhoanChi = cursor.getString(2);
            String nguoiTao = cursor.getString(3);
            String ngayTao = cursor.getString(4);
            String soTien = cursor.getString(5);
            String ghiChu = cursor.getString(6);
            dsKhoanChi.add(new KhoanChi(id_khoanchi,tenKhoanChi,nguoiTao,ngayTao,soTien,ghiChu));
        }
        cursor.close();
        khoanChiAdapter.notifyDataSetChanged();
    }
    //======================= Lấy dữ liệu khoản chi từ database thêm vào model ==============================================//



    //======================= Lấy dữ liệu khoản thu từ database thêm vào model ==============================================//
    private void hienThiDanhSachKhoanThu() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor =database.query("khoanthu",null,"id_nguoidung=?",new String[]{layIDNguoiDung()+""},null,null,"ngaytao");
        dsKhoanThu.clear();
        while (cursor.moveToNext())
        {

            int id_khoanthu = cursor.getInt(0);
            String tenKhoanThu = cursor.getString(2);
            String nguoiTao = cursor.getString(3);
            String ngayTao = cursor.getString(4);
            String soTien = cursor.getString(5);
            String ghiChu = cursor.getString(6);
            dsKhoanThu.add(new KhoanThu(id_khoanthu,tenKhoanThu,nguoiTao,ngayTao,soTien,ghiChu));
        }
        cursor.close();
        khoanThuAdapter.notifyDataSetChanged();
    }
    //======================= Lấy dữ liệu khoản thu từ database thêm vào model ==============================================//

    @Override
    protected void onResume() {
        super.onResume();
        hienThiDanhSachKhoanThu();
        hienThiDanhSachKhoanChi();
        txtTongChi.setText(dinhDangTien(tongChi()+"")+" VNĐ");
        txtTongThu.setText(dinhDangTien(tongThu()+"")+" VNĐ");
        soDu = tongThu() - tongChi();
        txtSoDu.setText(dinhDangTien(soDu+"")+" VNĐ");
    }




    //========================= Tạo Option Menu==========================//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


   //Khai báo id và gắn sự kiện cho option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menudangxuat)
        {
            finish();
        }
        else if(item.getItemId()==R.id.menudoimatkhau)
        {
            doiMatKhau();
        }
        else if(item.getItemId()==R.id.menudoisdt)
        {
            doiSdt();
        }
        return super.onOptionsItemSelected(item);
    }

    private void doiSdt() {
        Intent intent = new Intent(this,DoiSoDienThoaiActivity.class);
        intent.putExtra("id_nguoidung",layIDNguoiDung());
        intent.putExtra("sdt",laySDT());
        this.startActivity(intent);
    }

    private void doiMatKhau() {
        Intent intent = new Intent(this,DoiMatKhauActivity.class);
        intent.putExtra("id_nguoidung",layIDNguoiDung());
        intent.putExtra("matkhau",layMatKhau());
        this.startActivity(intent);
    }
    //Khai báo id và gắn sự kiện cho option menu

    //========================= Tạo Option Menu==========================//

    private void addEvents() {
        btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiNgayThang();
            }
        });

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemKhoanThu();
            }
        });
        
        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemKhoanChi();
            }
        });


    }

    //========================== Xử lý thêm khoản chi========================//
    private void xuLyThemKhoanChi() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();

        if(txtTenND.getText().toString().equals("") || txtNguoiTao.getText().toString().equals("") || txtSoTien.getText().toString().equals("") )
        {
            Toast.makeText(this,"Bạn chưa nhập đủ thông tin, vui lòng thử lại!",Toast.LENGTH_LONG).show();
        }
        else
        {
            row.put("id_nguoidung",layIDNguoiDung());
            row.put("tenkhoanchi",txtTenND.getText().toString());
            row.put("nguoitao",txtNguoiTao.getText().toString());
            row.put("ngaytao",txtLich.getText().toString());
            row.put("sotienchi",txtSoTien.getText().toString());
            row.put("ghichu",txtGhiChu.getText().toString());
            database.insert("khoanchi",null,row);
            Toast.makeText(this,"Thêm khoản chi thành cônng",Toast.LENGTH_LONG).show();
            hienThiDanhSachKhoanChi();
            txtTenND.getText().clear();
            txtNguoiTao.getText().clear();
            txtSoTien.getText().clear();
            txtGhiChu.getText().clear();

            txtTongChi.setText(dinhDangTien(tongChi()+"")+" VNĐ");
            soDu = tongThu() - tongChi();
            txtSoDu.setText(dinhDangTien(soDu+"")+" VNĐ");
        }
    }
    //========================== Xử lý thêm khoản chi========================//


    //========================== Xử lý thêm khoản thu========================//
    private void xuLyThemKhoanThu() {

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues row = new ContentValues();

        if(txtTenND.getText().toString().equals("") || txtNguoiTao.getText().toString().equals("") || txtSoTien.getText().toString().equals("") )
        {
            Toast.makeText(this,"Bạn chưa nhập đủ thông tin, vui lòng thử lại!",Toast.LENGTH_LONG).show();
        }
        else
        {
            row.put("id_nguoidung",layIDNguoiDung());
            row.put("tenkhoanthu",txtTenND.getText().toString());
            row.put("nguoitao",txtNguoiTao.getText().toString());
            row.put("ngaytao",txtLich.getText().toString());
            row.put("sotienthu",txtSoTien.getText().toString());
            row.put("ghichu",txtGhiChu.getText().toString());
            database.insert("khoanthu",null,row);
            Toast.makeText(this,"Thêm khoản thu thành cônng",Toast.LENGTH_LONG).show();
            hienThiDanhSachKhoanThu();
            txtTenND.getText().clear();
            txtNguoiTao.getText().clear();
            txtSoTien.getText().clear();
            txtGhiChu.getText().clear();
            txtTongThu.setText(dinhDangTien(tongThu()+"")+" VNĐ");
            soDu = tongThu() - tongChi();
            txtSoDu.setText(dinhDangTien(soDu+"")+" VNĐ");
        }
    }
    //========================== Xử lý thêm khoản chi========================//


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
                MainActivity.this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();//Hiển thị cửa sổ chọn ngày tháng năm lên
    }

    //======================= Hiển thị ngày tháng khi xảy ra sự kiện=========================================//



    private void addControls() {

     //======================== Tạo Tabost ==============================//
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();//Cấu hình để tạo setup

        TabHost.TabSpec tab1 =tabHost.newTabSpec("t1");
        tab1.setIndicator("",getResources().getDrawable(R.drawable.add));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Thu");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Chi");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);
     //========================= Tạo TabHost ===============================//

     //======================= Khai báo id cho các controls =======================//
        txtTenND = findViewById(R.id.txtTenND);
        txtNguoiTao = findViewById(R.id.txtNguoiTao);
        txtSoTien = findViewById(R.id.txtSoTien);
        txtGhiChu = findViewById(R.id.txtGhiChu);
        txtSoDu = findViewById(R.id.txtSoDu);
        txtNguoiDung = findViewById(R.id.txtNguoiDung);
        txtTongChi = findViewById(R.id.txtTongChi);
        txtTongThu = findViewById(R.id.txtTongThu);

        txtLich = findViewById(R.id.txtLich);
        btnLich = findViewById(R.id.btnLich);
        calendar = Calendar.getInstance(); //Hiển thị lên ngày tháng
        txtLich.setText(simpleDateFormat.format(calendar.getTime()));

        btnThu = findViewById(R.id.btnThu);
        btnChi = findViewById(R.id.btnChi);
        //======================= Khai báo id cho các controls =======================//


//======================== Tạo listview ==============================//
        lvThu = findViewById(R.id.lvThu);
        dsKhoanThu = new ArrayList<>();
        khoanThuAdapter = new KhoanThuAdapter(
                MainActivity.this,
                R.layout.item,
                dsKhoanThu
        );
        lvThu.setAdapter(khoanThuAdapter);



        lvChi = findViewById(R.id.lvChi);
        dsKhoanChi = new ArrayList<>();
        khoanChiAdapter = new KhoanChiAdapter(
                MainActivity.this,
                R.layout.item,
                dsKhoanChi
        );
        lvChi.setAdapter(khoanChiAdapter);
        //======================== Tạo listview ==============================//


        txtNguoiDung.setText(layTenNguoiDung());
        txtTongChi.setText(dinhDangTien(tongChi()+"")+" VNĐ");
        txtTongThu.setText(dinhDangTien(tongThu()+"")+" VNĐ");
        soDu = tongThu() - tongChi();
        txtSoDu.setText(dinhDangTien(soDu+"")+" VNĐ");



    }


 //======================== Tính tổng số tiền của khoản chi ===================================//
    public float tongChi(){
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        int id_nguoidung = layIDNguoiDung();
        Cursor cursor = database.query("khoanchi",null,"id_nguoidung=?",new String[]{id_nguoidung+""},null,null,null);
        SoTienThuChi soTienThuChi = new SoTienThuChi();
        float tongChi = 0;
        while (cursor.moveToNext())
        {
            Float sotienchi = cursor.getFloat(5);
            tongChi += sotienchi;
            soTienThuChi.setTongTienChi(tongChi);
        }
        cursor.close();

        return soTienThuChi.getTongTienChi();
    }

    //======================== Tính tổng số tiền của khoản chi ===================================//


    //==========================Tính tổng số tiền của khoản thu =================================//
    public float tongThu(){
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        int id_nguoidung = layIDNguoiDung();
        Cursor cursor = database.query("khoanthu",null,"id_nguoidung=?",new String[]{id_nguoidung+""},null,null,null);
        SoTienThuChi soTienThuChi = new SoTienThuChi();
        float tongThu = 0;
        while (cursor.moveToNext())
        {
            Float sotienthu = cursor.getFloat(5);
            tongThu += sotienthu;
            soTienThuChi.setTongTienThu(tongThu);
        }
        cursor.close();

        return soTienThuChi.getTongTienThu();
    }

    //==========================Tính tổng số tiền của khoản thu =================================//



    //==========================Lây id người dùng của tài khoản đã đăng nhập=================================//
    public int layIDNguoiDung(){
        Intent intent = getIntent();
        String taiKhoan = intent.getStringExtra("taiKhoan");

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("nguoidung",null,"taikhoan=?",new String[]{taiKhoan},null,null,null);
        NguoiDung nguoiDung = new NguoiDung();
        while (cursor.moveToNext())
        {
            int id_nguoidung = cursor.getInt(0);

            nguoiDung.setId_nguoidung(id_nguoidung);
        }
        cursor.close();

        return nguoiDung.getId_nguoidung();

    }
    //==========================Lây id người dùng của tài khoản đã đăng nhập=================================//


    //==========================Lấy tên người dùng của tài khoản đã đăng nhập ==============================//
    public String layTenNguoiDung(){
        Intent intent = getIntent();
        String taiKhoan = intent.getStringExtra("taiKhoan");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("nguoidung",null,"taikhoan=?",new String[]{taiKhoan},null,null,null);
        NguoiDung nguoiDung = new NguoiDung();
        while (cursor.moveToNext())
        {
            String ten = cursor.getString(1);

            nguoiDung.setTen(ten);
        }
        cursor.close();

        return nguoiDung.getTen();
    }
    //==========================Lấy tên người dùng của tài khoản đã đăng nhập ==============================//



    //========================== Lấy mật khẩu người dùng từ trang đăng nhập ===============================//
    public String layMatKhau(){
        Intent intent = getIntent();
        String taiKhoan = intent.getStringExtra("taiKhoan");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("nguoidung",null,"taikhoan=?",new String[]{taiKhoan},null,null,null);
        NguoiDung nguoiDung = new NguoiDung();
        while (cursor.moveToNext())
        {
            String matkhau = cursor.getString(3);

            nguoiDung.setMatkhau(matkhau);
        }
        cursor.close();

        return nguoiDung.getMatkhau();

    }

    //========================== Lấy mật khẩu người dùng từ trang đăng nhập ===============================//


    //========================== Lấy SDT của người dùng từ trang đăng nhập ===============================//
    public String laySDT(){
        Intent intent = getIntent();
        String taiKhoan = intent.getStringExtra("taiKhoan");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("nguoidung",null,"taikhoan=?",new String[]{taiKhoan},null,null,null);
        NguoiDung nguoiDung = new NguoiDung();
        while (cursor.moveToNext())
        {
            String sdt = cursor.getString(4);

            nguoiDung.setSdt(sdt);
        }
        cursor.close();

        return nguoiDung.getSdt();
    }
    //========================== Lấy SDT của người dùng từ trang đăng nhập ===============================//


    //========================== Hàm chỉnh định dạng tiền ===============================//
    private static String dinhDangTien(String number)
    {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Double.parseDouble(number));
    }
    //========================== Hàm chỉnh định dạng tiền ===============================//


}
