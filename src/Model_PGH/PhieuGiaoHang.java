/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_PGH;

/**
 *
 * @author hoang
 */
public class PhieuGiaoHang {
    private String MaPGH;
    private String MaHD;
    private String NgayTao;
    private String NguoiTao;
    private String TrangThai;
    private String DiaChi;
    private String NgayGiaoDuKien;
    private String ThoiDiemGiao;
    private String NguoiNhan;
    private String GhiChu;

    @Override
    public String toString() {
        return "PhieuGiaoHang{" + "MaPGH=" + MaPGH + ", MaHD=" + MaHD + ", NgayTao=" + NgayTao + ", NguoiTao=" + NguoiTao + ", TrangThai=" + TrangThai + ", DiaChiGiaoHang=" + DiaChi + ", NgayGiaoDuKien=" + NgayGiaoDuKien + ", ThoiDiemGiaoThucTe=" + ThoiDiemGiao + ", TenNguoiNhan=" + NguoiNhan + ", GhiChu=" + GhiChu + '}';
    }

    public String getMaPGH() {
        return MaPGH;
    }

    public void setMaPGH(String MaPGH) {
        this.MaPGH = MaPGH;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(String NguoiTao) {
        this.NguoiTao = NguoiTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getNgayGiaoDuKien() {
        return NgayGiaoDuKien;
    }

    public void setNgayGiaoDuKien(String NgayGiaoDuKien) {
        this.NgayGiaoDuKien = NgayGiaoDuKien;
    }

    public String getThoiDiemGiao() {
        return ThoiDiemGiao;
    }

    public void setThoiDiemGiao(String ThoiDiemGiao) {
        this.ThoiDiemGiao = ThoiDiemGiao;
    }

    public String getNguoiNhan() {
        return NguoiNhan;
    }

    public void setNguoiNhan(String NguoiNhan) {
        this.NguoiNhan = NguoiNhan;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public Object[] toDataRow() {
        return new Object[]{MaPGH, MaHD, NgayTao, NguoiTao, TrangThai, DiaChi, NgayGiaoDuKien, ThoiDiemGiao, NguoiNhan, GhiChu};
    }

    public PhieuGiaoHang(String maPGH, String maHD, String ngayTao, String nguoiTao, String trangThai, String diaChi, String ngayGiaoDuKien, String thoiDiemGiao, String nguoiNhan, String ghiChu) {
        MaPGH = maPGH;
        MaHD = maHD;
        NgayTao = ngayTao;
        NguoiTao = nguoiTao;
        TrangThai = trangThai;
        DiaChi = diaChi;
        NgayGiaoDuKien = ngayGiaoDuKien;
        ThoiDiemGiao = thoiDiemGiao;
        NguoiNhan = nguoiNhan;
        GhiChu = ghiChu;
    }

    public PhieuGiaoHang() {
    }
}
