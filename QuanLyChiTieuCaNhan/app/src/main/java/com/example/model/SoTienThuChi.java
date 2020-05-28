package com.example.model;

import java.io.Serializable;

public class SoTienThuChi implements Serializable {
    private float tongTienThu;
    private float tongTienChi;
    private float soDu;

    public float getTongTienThu() {
        return tongTienThu;
    }

    public void setTongTienThu(float tongTienThu) {
        this.tongTienThu = tongTienThu;
    }

    public float getTongTienChi() {
        return tongTienChi;
    }

    public void setTongTienChi(float tongTienChi) {
        this.tongTienChi = tongTienChi;
    }

    public float getSoDu() {
        return soDu;
    }

    public void setSoDu(float soDu) {
        this.soDu = soDu;
    }

    public SoTienThuChi(float tongTienThu, float tongTienChi, float soDu) {
        this.tongTienThu = tongTienThu;
        this.tongTienChi = tongTienChi;
        this.soDu = soDu;
    }

    public SoTienThuChi() {
    }
}
