
package Model_NhanVien;


public class LichSuNhanVien {
    private int maHoatDong ;
    private String hanhDong ;
    private String thoiGian ;
    private String maNhanVien ;

    public LichSuNhanVien() {
    }

    public LichSuNhanVien(int maHoatDong, String hanhDong, String thoiGian, String maNhanVien) {
        this.maHoatDong = maHoatDong;
        this.hanhDong = hanhDong;
        this.thoiGian = thoiGian;
        this.maNhanVien = maNhanVien;
    }

    public int getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(int maHoatDong) {
        this.maHoatDong = maHoatDong;
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

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    
    
}
