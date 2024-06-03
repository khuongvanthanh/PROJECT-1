/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_KhachHang;

/**
 *
 * @author ADMIN
 */
public class DiaChiGiaoHang {
    private String maDiaChi;
    private String maKhachHang;
    private String Hoten;
    private String diaChi;
    private String SDT;

    public DiaChiGiaoHang() {
    }

    public DiaChiGiaoHang(String maDiaChi, String maKhachHang, String Hoten, String diaChi, String SDT) {
        this.maDiaChi = maDiaChi;
        this.maKhachHang = maKhachHang;
        this.Hoten = Hoten;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    public String getMaDiaChi() {
        return maDiaChi;
    }

    public void setMaDiaChi(String maDiaChi) {
        this.maDiaChi = maDiaChi;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    @Override
    public String toString() {
        return "DiaChiGiaoHang{" + "maDiaChi=" + maDiaChi + ", maKhachHang=" + maKhachHang + ", Hoten=" + Hoten + ", diaChi=" + diaChi + ", SDT=" + SDT + '}';
    }
    
    
}
