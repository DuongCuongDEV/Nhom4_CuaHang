package com.fpoly.quanly;

public class Uploadinfo {
    String giaTienCu;
     String Name;
     String Image;
     String moTa;
     String maSanPham;
     String giaMoi;
     String loai;

    public Uploadinfo() {
    }

    public Uploadinfo(String giaTienCu, String name, String image, String moTa, String maSanPham, String giaMoi, String loai) {
        this.giaTienCu = giaTienCu;
        Name = name;
        Image = image;
        this.moTa = moTa;
        this.maSanPham = maSanPham;
        this.giaMoi = giaMoi;
        this.loai = loai;
    }

    public String getGiaTienCu() {
        return giaTienCu;
    }

    public void setGiaTienCu(String giaTienCu) {
        this.giaTienCu = giaTienCu;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(String giaMoi) {
        this.giaMoi = giaMoi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
