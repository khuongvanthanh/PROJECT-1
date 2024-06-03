package Model_PGG;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class LichSu {

    private int lichSuID;
    private String maGiamGia;  
    private int lggID;
    private int dkggID;
    private String hanhDong;
    private String thoiGian;

    public LichSu(int lichSuID, String maGiamGia, int lggID, int dkggID, String hanhDong, String thoiGian) {
        this.lichSuID = lichSuID;
        this.maGiamGia = maGiamGia;
        this.lggID = lggID;
        this.dkggID = dkggID;
        this.hanhDong = hanhDong;
        this.thoiGian = thoiGian;
    }

    

    public LichSu(int lichSuID, String maGiamGia, String hanhDong, String thoiGian) {
        this.lichSuID = lichSuID;
        this.maGiamGia = maGiamGia;
        this.hanhDong = hanhDong;
        this.thoiGian = getCurrentDateTime();
    }

    public int getLichSuID() {
        return lichSuID;
    }

    public void setLichSuID(int lichSuID) {
        this.lichSuID = lichSuID;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public int getLggID() {
        return lggID;
    }

    public void setLggID(int lggID) {
        this.lggID = lggID;
    }

    public int getDkggID() {
        return dkggID;
    }

    public void setDkggID(int dkggID) {
        this.dkggID = dkggID;
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
        return "LichSu{" + "lichSuID=" + lichSuID + ", maGiamGia=" + maGiamGia + ", lggID=" + lggID + ", dkggID=" + dkggID + ", hanhDong=" + hanhDong + ", thoiGian=" + thoiGian + '}';
    }

    
}
