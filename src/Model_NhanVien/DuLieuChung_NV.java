/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_NhanVien;

import Model_NhanVien.LichSuNhanVien;
import Model_NhanVien.NhanVien;

/**
 *
 * @author ASUS
 */
public class DuLieuChung_NV {

    private NhanVien nv;
    private LichSuNhanVien lsnv;

    public DuLieuChung_NV() {
    }

    public DuLieuChung_NV(NhanVien nv, LichSuNhanVien lsnv) {
        this.nv = nv;
        this.lsnv = lsnv;
    }

    public DuLieuChung_NV(NhanVien nv) {
        this.nv = nv;
    }

    public NhanVien getNv() {
        return nv;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    public LichSuNhanVien getLsnv() {
        return lsnv;
    }

    public void setLsnv(LichSuNhanVien lsnv) {
        this.lsnv = lsnv;
    }

    public Object[] toDataRow() {
        return new Object[]{
            nv.getMaNhanVien(), nv.getHoTen(), nv.getDiaChi(), nv.getSoDienThoai(), nv.getEmail(), nv.getChucVu(), nv.getBoPhan(), nv.getTrangThai(), (nv.isGioiTinh()) ? "Nam" : "Nữ", nv.getTenDangNhap(), nv.getMatKhau(), (nv.isVaiTro()) ? "Chủ quán" : "Nhân viên",};
    }

    public Object[] toDataRowLichSuNV() {
        return new Object[]{
            lsnv.getMaHoatDong(), lsnv.getHanhDong(), lsnv.getThoiGian(), lsnv.getMaNhanVien()
        };
    }

}
