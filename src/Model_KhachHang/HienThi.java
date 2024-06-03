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
public class HienThi {
    private String loai;
    private Integer soLuong;
    private BigDecimal tien;

    public HienThi() {
    }

    public HienThi(String loai, Integer soLuong, BigDecimal tien) {
        this.loai = loai;
        this.soLuong = soLuong;
        this.tien = tien;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getTien() {
        return tien;
    }

    public void setTien(BigDecimal tien) {
        this.tien = tien;
    }
    
    
    
}
