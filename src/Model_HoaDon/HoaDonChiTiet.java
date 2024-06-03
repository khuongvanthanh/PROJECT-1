package Model_HoaDon;

import java.math.BigDecimal;

public class HoaDonChiTiet {

    private int MaHDCT;
    private String MaHoaDon;
    private String MaBT;
    private int SoLuong;
    private BigDecimal TongTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int MaHDCT, String MaHoaDon, String MaBT, int SoLuong, BigDecimal TongTien) {
        this.MaHDCT = MaHDCT;
        this.MaHoaDon = MaHoaDon;
        this.MaBT = MaBT;
        this.SoLuong = SoLuong;
        this.TongTien = TongTien;
    }

    public HoaDonChiTiet(String MaHoaDon, String MaBT, int SoLuong, BigDecimal TongTien) {
        this.MaHoaDon = MaHoaDon;
        this.MaBT = MaBT;
        this.SoLuong = SoLuong;
        this.TongTien = TongTien;
    }

    public int getMaHDCT() {
        return MaHDCT;
    }

    public void setMaHDCT(int MaHDCT) {
        this.MaHDCT = MaHDCT;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaBT() {
        return MaBT;
    }

    public void setMaBT(String MaBT) {
        this.MaBT = MaBT;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    public void setTongTien(BigDecimal TongTien) {
        this.TongTien = TongTien;
    }

   

   

    
    public Object[] todataRowHDCT() {
        return new Object[]{
            this.MaHoaDon, this.MaBT, this.SoLuong, this.TongTien
        };
    }

    public Object[] toDataRowLS() {
        return new Object[]{
            this.MaBT, this.SoLuong, this.TongTien
        };
    }
}
