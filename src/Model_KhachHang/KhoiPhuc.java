/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_KhachHang;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class KhoiPhuc {

    private int maKhoiPhuc;
    private String maKhachHang;
    private String hanhDong;
    private String thoiGian;

    public KhoiPhuc() {
    }

    public KhoiPhuc(int maKhoiPhuc, String maKhachHang, String hanhDong, String thoiGian) {
        this.maKhoiPhuc = maKhoiPhuc;
        this.maKhachHang = maKhachHang;
        this.hanhDong = hanhDong;
        this.thoiGian = thoiGian;
    }

    public int getMaKhoiPhuc() {
        return maKhoiPhuc;
    }

    public void setMaKhoiPhuc(int maKhoiPhuc) {
        this.maKhoiPhuc = maKhoiPhuc;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    @Override
    public String toString() {
        return "KhoiPhuc{" + "maKhoiPhuc=" + maKhoiPhuc + ", maKhachHang=" + maKhachHang + ", hanhDong=" + hanhDong + ", thoiGian=" + thoiGian + '}';
    }

   
}
