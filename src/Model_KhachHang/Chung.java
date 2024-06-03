/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_KhachHang;

import Model_HoaDon.HoaDon;
import Model_HoaDon.HoaDonChiTiet;

/**
 *
 * @author ADMIN
 */
public class Chung {

    private DiaChiGiaoHang dcgh;
    private LichSuMuaHang lsmh;
    private KhachHang kh;
    private KhoiPhuc kp;
    private HoaDon hd;
    private HoaDonChiTiet hdct;
    
    

    public Chung() {
    }

    public Chung(DiaChiGiaoHang dcgh, LichSuMuaHang lsmh, KhachHang kh, KhoiPhuc kp, HoaDon hd, HoaDonChiTiet hdct) {
        this.dcgh = dcgh;
        this.lsmh = lsmh;
        this.kh = kh;
        this.kp = kp;
        this.hd = hd;
        this.hdct = hdct;
    }

    public DiaChiGiaoHang getDcgh() {
        return dcgh;
    }

    public void setDcgh(DiaChiGiaoHang dcgh) {
        this.dcgh = dcgh;
    }

    public LichSuMuaHang getLsmh() {
        return lsmh;
    }

    public void setLsmh(LichSuMuaHang lsmh) {
        this.lsmh = lsmh;
    }

    public KhachHang getKh() {
        return kh;
    }

    public void setKh(KhachHang kh) {
        this.kh = kh;
    }

    public KhoiPhuc getKp() {
        return kp;
    }

    public void setKp(KhoiPhuc kp) {
        this.kp = kp;
    }

    public HoaDon getHd() {
        return hd;
    }

    public void setHd(HoaDon hd) {
        this.hd = hd;
    }

    public HoaDonChiTiet getHdct() {
        return hdct;
    }

    public void setHdct(HoaDonChiTiet hdct) {
        this.hdct = hdct;
    }

   
    

    public Object[] toDataRow() {
        return new Object[]{getKh().getMaKH(), getKh().getHoTen(), getKh().getGioiTinh() == true ? "Nam" : "Nữ", getKh().getLoaiKH()
        };
    }

//    public Object[] toDataRowLs() {
//        return new Object[]{getHd().getMaHoaDon(), getHd().getMaKhachHang(), getHd().getTongTien(), getHd().getTrangThai()};
//    }


    public Object[] toDataRowKp() {
        return new Object[]{getKp().getMaKhachHang(), getKp().getHanhDong(), getKp().getThoiGian()};
    }

    public Object[] toDataRowLsSP() {
        return new Object[]{getLsmh().getDanhSachMua(), getLsmh().getSoLuongMua(), getLsmh().getTongGia(),};
    }

}
