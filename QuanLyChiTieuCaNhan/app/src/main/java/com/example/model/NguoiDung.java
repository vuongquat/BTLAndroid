package com.example.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private int id_nguoidung;
    private String ten;
    private String taikhoan;
    private String matkhau;
    private String sdt;

    public int getId_nguoidung() {
        return id_nguoidung;
    }

    public void setId_nguoidung(int id_nguoidung) {
        this.id_nguoidung = id_nguoidung;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public NguoiDung(int id_nguoidung, String ten, String taikhoan, String matkhau, String sdt) {
        this.id_nguoidung = id_nguoidung;
        this.ten = ten;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.sdt = sdt;
    }

    public NguoiDung() {
    }
}
