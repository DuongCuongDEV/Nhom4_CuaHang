package com.fpoly.quanly.Model;
public class Uploadinfo {
    private String id;
    private String name;
    private String image;
    private int Gia;
    private String khuyenmai;
    private String moTa;
    private String loai;

    public Uploadinfo() {
    }

    public Uploadinfo(String id, String name, String image, int gia, String khuyenmai, String moTa, String loai) {
        this.id = id;
        this.name = name;
        this.image = image;
        Gia = gia;
        this.khuyenmai = khuyenmai;
        this.moTa = moTa;
        this.loai = loai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setGia(int gia) {
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
