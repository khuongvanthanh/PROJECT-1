/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_PGH;

import java.math.BigDecimal;

/**
 *
 * @author hoang
 */
public class DichVu {
    private String maDv;
    private String tenDv;
    private String Mota;
    private BigDecimal gia;

    public DichVu() {
    }

    public DichVu(String maDv, String tenDv, String Mota, BigDecimal gia) {
        this.maDv = maDv;
        this.tenDv = tenDv;
        this.Mota = Mota;
        this.gia = gia;
    }

    public String getMaDv() {
        return maDv;
    }

    public void setMaDv(String maDv) {
        this.maDv = maDv;
    }

    public String getTenDv() {
        return tenDv;
    }

    public void setTenDv(String tenDv) {
        this.tenDv = tenDv;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "DichVu{" + "maDv=" + maDv + ", tenDv=" + tenDv + ", Mota=" + Mota + ", gia=" + gia + '}';
    }
    public Object[] toDataRow() {
        return new Object[]{maDv,tenDv,Mota,gia};
    }
}
