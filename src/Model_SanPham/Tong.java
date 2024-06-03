/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_SanPham;

import java.math.BigDecimal;

/**
 *
 * @author Acer
 */
public class Tong {

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
    private String hinhAnh;
    private String THUCANHOPLY;
    private String THUCANDIUNG;
    private String THOIQUEN;
    private String LOAI;
    private int tk;
    private Boolean IsDelete;

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public Tong() {
    }

    public Tong(String bTheSP, String maSP, float cn, int tuoi, String mauLong, float cc, String NgaySinh, BigDecimal giaB, Boolean gTinh, String tiem, String hinhAnh, String THUCANHOPLY, String THUCANDIUNG, String THOIQUEN, String LOAI, int tk) {
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
        this.THUCANHOPLY = THUCANHOPLY;
        this.THUCANDIUNG = THUCANDIUNG;
        this.THOIQUEN = THOIQUEN;
        this.LOAI = LOAI;
        this.tk = tk;
    }

    public Tong(String maSP, String LOAI, int tk) {
        this.maSP = maSP;
        this.LOAI = LOAI;
        this.tk = tk;
    }

    public String getLOAI() {
        return LOAI;
    }

    public void setLOAI(String LOAI) {
        this.LOAI = LOAI;
    }

    public int getTk() {
        return tk;
    }

    public void setTk(int tk) {
        this.tk = tk;
    }

    public Tong(String bTheSP, String maSP, float cn, int tuoi, String mauLong, float cc, String NgaySinh, BigDecimal giaB, Boolean gTinh, String tiem, String hinhAnh, String THUCANHOPLY, String THUCANDIUNG, String THOIQUEN) {
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
        this.THUCANHOPLY = THUCANHOPLY;
        this.THUCANDIUNG = THUCANDIUNG;
        this.THOIQUEN = THOIQUEN;
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

    public String getMauLong() {
        return mauLong;
    }

    public void setMauLong(String mauLong) {
        this.mauLong = mauLong;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public float getCn() {
        return cn;
    }

    public void setCn(float cn) {
        this.cn = cn;
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

    public BigDecimal getGiaB() {
        return giaB;
    }

    public void setGiaB(BigDecimal giaB) {
        this.giaB = giaB;
    }

    public String getTHUCANHOPLY() {
        return THUCANHOPLY;
    }

    public void setTHUCANHOPLY(String THUCANHOPLY) {
        this.THUCANHOPLY = THUCANHOPLY;
    }

    public String getTHUCANDIUNG() {
        return THUCANDIUNG;
    }

    public void setTHUCANDIUNG(String THUCANDIUNG) {
        this.THUCANDIUNG = THUCANDIUNG;
    }

    public String getTHOIQUEN() {
        return THOIQUEN;
    }

    public void setTHOIQUEN(String THOIQUEN) {
        this.THOIQUEN = THOIQUEN;
    }

    public Object[] toDataRow() {
        return new Object[]{bTheSP, maSP, cn, tuoi, mauLong, cc, NgaySinh, giaB, gTinh == true ? "Đực" : "Cái", tiem, hinhAnh, THUCANHOPLY, THUCANDIUNG, THOIQUEN};
    }

}
