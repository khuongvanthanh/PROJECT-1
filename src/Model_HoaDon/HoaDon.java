package Model_HoaDon;

import java.math.BigDecimal;

public class HoaDon {

    private String maHoaDon;
    private String trangThai;
    private String maKhachHang;
    private String maNhanVien;
    private String ngayTao;
    private String maDGG;
    private String maGiamGia;
    private int giaTriGiamDGG;
    private int giaTriGiamPGG;
    private BigDecimal soTienGiam;
    private BigDecimal tongTien;
    private boolean vanChuyen;
    private BigDecimal soTienThanhToan;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String trangThai, String maKhachHang, String maNhanVien, String ngayTao, String maDGG, String maGiamGia, int giaTriGiamDGG, int giaTriGiamPGG, BigDecimal soTienGiam, BigDecimal tongTien, boolean vanChuyen, BigDecimal soTienThanhToan) {
        this.maHoaDon = maHoaDon;
        this.trangThai = trangThai;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayTao = ngayTao;
        this.maDGG = maDGG;
        this.maGiamGia = maGiamGia;
        this.giaTriGiamDGG = giaTriGiamDGG;
        this.giaTriGiamPGG = giaTriGiamPGG;
        this.soTienGiam = soTienGiam;
        this.tongTien = tongTien;
        this.vanChuyen = vanChuyen;
        this.soTienThanhToan = soTienThanhToan;
    }

    public BigDecimal getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public void setSoTienThanhToan(BigDecimal soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }

    
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaDGG() {
        return maDGG;
    }

    public void setMaDGG(String maDGG) {
        this.maDGG = maDGG;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public int getGiaTriGiamDGG() {
        return giaTriGiamDGG;
    }

    public void setGiaTriGiamDGG(int giaTriGiamDGG) {
        this.giaTriGiamDGG = giaTriGiamDGG;
    }

    public int getGiaTriGiamPGG() {
        return giaTriGiamPGG;
    }

    public void setGiaTriGiamPGG(int giaTriGiamPGG) {
        this.giaTriGiamPGG = giaTriGiamPGG;
    }

    public BigDecimal getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(BigDecimal soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isVanChuyen() {
        return vanChuyen;
    }

    public void setVanChuyen(boolean vanChuyen) {
        this.vanChuyen = vanChuyen;
    }

    public Object[] toDataRowHD() {
        return new Object[]{
            this.maHoaDon, this.trangThai
        };
    }
}
