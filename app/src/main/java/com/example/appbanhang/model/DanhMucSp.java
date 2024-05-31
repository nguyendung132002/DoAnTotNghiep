package com.example.appbanhang.model;

public class DanhMucSp {
    int iddanhmuc;
    String tendanhmuccon;
    String hinhanhdmcon;

    public DanhMucSp(String tendanhmuccon, String hinhanhdmcon) {
        this.tendanhmuccon = tendanhmuccon;
        this.hinhanhdmcon = hinhanhdmcon;
    }

    public int getIddanhmuc() {
        return iddanhmuc;
    }

    public void setIddanhmuc(int iddanhmuc) {
        this.iddanhmuc = iddanhmuc;
    }

    public String getTendanhmuccon() {
        return tendanhmuccon;
    }

    public void setTendanhmuccon(String tendanhmuccon) {
        this.tendanhmuccon = tendanhmuccon;
    }

    public String getHinhanhdmcon() {
        return hinhanhdmcon;
    }

    public void setHinhanhdmcon(String hinhanhdmcon) {
        this.hinhanhdmcon = hinhanhdmcon;
    }
}
