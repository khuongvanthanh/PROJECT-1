/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_DotGiamGia;

import java.util.Date;

/**
 *
 * @author hung0
 */
public class DotGiamGia {
    private String maDGG;
    private String tenDGG;
    private String maLGG;
    private String maNV;
    private String phanTramGG;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String ghiChu;

    public DotGiamGia() {
    }

    public DotGiamGia(String maDGG, String tenDGG, String maLGG, String maNV, String phanTramGG, Date ngayBatDau, Date ngayKetThuc, String ghiChu) {
        this.maDGG = maDGG;
        this.tenDGG = tenDGG;
        this.maLGG = maLGG;
        this.maNV = maNV;
        this.phanTramGG = phanTramGG;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ghiChu = ghiChu;
    }

    public String getMaDGG() {
        return maDGG;
    }

    public void setMaDGG(String maDGG) {
        this.maDGG = maDGG;
    }

    public String getTenDGG() {
        return tenDGG;
    }

    public void setTenDGG(String tenDGG) {
        this.tenDGG = tenDGG;
    }

    public String getMaLGG() {
        return maLGG;
    }

    public void setMaLGG(String maLGG) {
        this.maLGG = maLGG;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getPhanTramGG() {
        return phanTramGG;
    }

    public void setPhanTramGG(String phanTramGG) {
        this.phanTramGG = phanTramGG;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
