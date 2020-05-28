package com.example.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class KhoanThu implements Serializable {
    private int id_khoanthu;
    private String tenKhoanThu;
    private String tenNguoiTao;
    private String ngayTao;
    private String soTien;
    private String ghiChu;


    public int getId_khoanthu() {
        return id_khoanthu;
    }

    public void setId_khoanthu(int id_khoanthu) {
        this.id_khoanthu = id_khoanthu;
    }

    public String getTenKhoanThu() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }

    public String getTenNguoiTao() {
        return tenNguoiTao;
    }

    public void setTenNguoiTao(String tenNguoiTao) {
        this.tenNguoiTao = tenNguoiTao;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhoanThu(int id_khoanthu, String tenKhoanThu, String tenNguoiTao, String ngayTao, String soTien, String ghiChu) {
        this.id_khoanthu = id_khoanthu;
        this.tenKhoanThu = tenKhoanThu;
        this.tenNguoiTao = tenNguoiTao;
        this.ngayTao = ngayTao;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
    }

    public KhoanThu() {
    }
}
