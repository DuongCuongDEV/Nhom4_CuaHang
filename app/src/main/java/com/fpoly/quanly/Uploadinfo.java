package com.fpoly.quanly;
public class Uploadinfo {
    private String oderNo;
    private String name;
    private String image;
    private int Gia;
    private String khuyenmai;
    private String moTa;
    private String loai;

    public Uploadinfo() {
    }

    public Uploadinfo(String oderNo, String name, String image, int gia, String khuyenmai, String moTa, String loai) {
        this.oderNo = oderNo;
        this.name = name;
        this.image = image;
        Gia = gia;
        this.khuyenmai = khuyenmai;
        this.moTa = moTa;
        this.loai = loai;
    }

    public String getOderNo() {
        return oderNo;
    }

    public void setOderNo(String oderNo) {
        this.oderNo = oderNo;
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
