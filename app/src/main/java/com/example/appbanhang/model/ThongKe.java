package com.example.appbanhang.model;

public class ThongKe {
    private String tensp;
    private int tong;//soluong
    private String tongtienthang;
    private String thang;
    int loai;
    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public String getTongtienthang() {
        return tongtienthang;
    }

    public void setTongtienthang(String tongtienthang) {
        this.tongtienthang = tongtienthang;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }
}
