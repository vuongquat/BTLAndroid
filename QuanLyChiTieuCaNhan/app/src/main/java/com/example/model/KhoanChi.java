package com.example.model;

import java.io.Serializable;

public class KhoanChi implements Serializable {
    private int id_khoanchi;
    private String tenKhoanChi;
    private String tenNguoiTao;
    private String ngayTao;
    private String soTien;
    private String ghiChu;

    public int getId_khoanchi() {
        return id_khoanchi;
    }

    public void setId_khoanchi(int id_khoanchi) {
        this.id_khoanchi = id_khoanchi;
    }

    public String getTenKhoanChi() {
        return tenKhoanChi;
    }

    public void setTenKhoanChi(String tenKhoanChi) {
        this.tenKhoanChi = tenKhoanChi;
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

    public KhoanChi(int id_khoanchi, String tenKhoanChi, String tenNguoiTao, String ngayTao, String soTien, String ghiChu) {
        this.id_khoanchi = id_khoanchi;
        this.tenKhoanChi = tenKhoanChi;
        this.tenNguoiTao = tenNguoiTao;
        this.ngayTao = ngayTao;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
    }

    public KhoanChi() {
    }
}
