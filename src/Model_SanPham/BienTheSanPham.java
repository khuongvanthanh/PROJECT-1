package Model_SanPham;

import java.math.BigDecimal;

public class BienTheSanPham {

    private String bTheSP;
    private String maSP;
    private float cn;
    private int tuoi;
    private String mauLong;
    private float cc;
    private String NgaySinh;
    private BigDecimal giaB;
    private Boolean gTinh;
    private String tiem;
    private String hinhAnh ;

    public BienTheSanPham() {
    }

    public BienTheSanPham(String bTheSP, String maSP, float cn, int tuoi, String mauLong, float cc, String NgaySinh, BigDecimal giaB, Boolean gTinh, String tiem, String hinhAnh) {
        this.bTheSP = bTheSP;
        this.maSP = maSP;
        this.cn = cn;
        this.tuoi = tuoi;
        this.mauLong = mauLong;
        this.cc = cc;
        this.NgaySinh = NgaySinh;
        this.giaB = giaB;
        this.gTinh = gTinh;
        this.tiem = tiem;
        this.hinhAnh = hinhAnh;
    }

    public String getbTheSP() {
        return bTheSP;
    }

    public void setbTheSP(String bTheSP) {
        this.bTheSP = bTheSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public float getCn() {
        return cn;
    }

    public void setCn(float cn) {
        this.cn = cn;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getMauLong() {
        return mauLong;
    }

    public void setMauLong(String mauLong) {
        this.mauLong = mauLong;
    }

    public float getCc() {
        return cc;
    }

    public void setCc(float cc) {
        this.cc = cc;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public BigDecimal getGiaB() {
        return giaB;
    }

    public void setGiaB(BigDecimal giaB) {
        this.giaB = giaB;
    }

    public Boolean getgTinh() {
        return gTinh;
    }

    public void setgTinh(Boolean gTinh) {
        this.gTinh = gTinh;
    }

    public String getTiem() {
        return tiem;
    }

    public void setTiem(String tiem) {
        this.tiem = tiem;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    
   

    @Override
    public String toString() {
        return "BienTheSanPham{" + "bTheSP=" + bTheSP + ", maSP=" + maSP + ", mauLong=" + mauLong + ", tuoi=" + tuoi + ", cn=" + cn + ", cc=" + cc + ", gTinh=" + gTinh + ", giaB=" + giaB + '}';
    }

    public Object[] toDataRow() {
        return new Object[]{bTheSP, maSP, cn, tuoi, mauLong, cc, NgaySinh, giaB, gTinh == true ? "Đực" : "Cái", tiem,hinhAnh};
    }
}
