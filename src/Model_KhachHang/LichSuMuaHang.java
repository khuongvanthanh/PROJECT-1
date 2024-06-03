/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_KhachHang;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class LichSuMuaHang {
    private String maHoaDon;
    private String maKH;
    private String maSP;
    private String danhSachMua;
    private Integer soLuongMua;
    private BigDecimal donGia;
    private BigDecimal tongGia;
    private String trangThai;
    private String thoiGian;        
           

    public LichSuMuaHang() {
    }

    public LichSuMuaHang(String maHoaDon, String maKH, String maSP, String danhSachMua, Integer soLuongMua, BigDecimal donGia, BigDecimal tongGia, String trangThai, String thoiGian) {
        this.maHoaDon = maHoaDon;
        this.maKH = maKH;
        this.maSP = maSP;
        this.danhSachMua = danhSachMua;
        this.soLuongMua = soLuongMua;
        this.donGia = donGia;
        this.tongGia = tongGia;
        this.trangThai = trangThai;
        this.thoiGian = thoiGian;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getDanhSachMua() {
        return danhSachMua;
    }

    public void setDanhSachMua(String danhSachMua) {
        this.danhSachMua = danhSachMua;
    }

    public Integer getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(Integer soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getTongGia() {
        return tongGia;
    }

    public void setTongGia(BigDecimal tongGia) {
        this.tongGia = tongGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    @Override
    public String toString() {
        return "LichSuMuaHang{" + "maHoaDon=" + maHoaDon + ", maKH=" + maKH + ", maSP=" + maSP + ", danhSachMua=" + danhSachMua + ", soLuongMua=" + soLuongMua + ", donGia=" + donGia + ", tongGia=" + tongGia + ", trangThai=" + trangThai + ", thoiGian=" + thoiGian + '}';
    }
      
}
