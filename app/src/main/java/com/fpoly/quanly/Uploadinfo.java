package com.fpoly.quanly;
public class Uploadinfo {
    private String name;
    private String image;
    private long Gia;
    private String khuyenmai;
    private String moTa;
    private String loai;

    public Uploadinfo() {
    }

    public Uploadinfo(String name, String image, long gia, String khuyenmai, String moTa, String loai) {
        this.name = name;
        this.image = image;
        Gia = gia;
        this.khuyenmai = khuyenmai;
        this.moTa = moTa;
        this.loai = loai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getGia() {
        return Gia;
    }

    public void setGia(long gia) {
        Gia = gia;
    }

    public String getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(String khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
