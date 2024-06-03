/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_PGG;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class DieuKienGiamGia {

    private int dieuKienGiamGiaID;
    private String loaiGiamGia;
    private int giaTriGiam;
    private String trangThai;
    private String ngayBD;
    private String ngayKT;
    private int soLuongTao;
    private int soLuongCon;
    private String maGiamGia;

    public DieuKienGiamGia() {
    }

    public DieuKienGiamGia(int dieuKienGiamGiaID, String loaiGiamGia, int giaTriGiam, String trangThai, String ngayBD, String ngayKT, int soLuongTao, int soLuongCon, String maGiamGia) {
        this.dieuKienGiamGiaID = dieuKienGiamGiaID;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTriGiam = giaTriGiam;
        this.trangThai = trangThai;
        setNgayBD(ngayBD);
        setNgayKT(ngayKT);
        this.soLuongTao = soLuongTao;
        this.soLuongCon = soLuongCon;
        this.maGiamGia = maGiamGia;
    }

    public DieuKienGiamGia(String loaiGiamGia, int giaTriGiam) {
        this.loaiGiamGia = loaiGiamGia;
        this.giaTriGiam = giaTriGiam;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public int getDieuKienGiamGiaID() {
        return dieuKienGiamGiaID;
    }

    public void setDieuKienGiamGiaID(int dieuKienGiamGiaID) {
        this.dieuKienGiamGiaID = dieuKienGiamGiaID;
    }

    public String getLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(String loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public int getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(int giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayBD() {
        return ngayBD;
    }


    public int getSoLuongTao() {
        return soLuongTao;
    }

    public void setSoLuongTao(int soLuongTao) {
        this.soLuongTao = soLuongTao;
    }

    public int getSoLuongCon() {
        return soLuongCon;
    }

    public void setSoLuongCon(int soLuongCon) {
        this.soLuongCon = soLuongCon;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

   public void setNgayBD(String ngayBD) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
        Date startDate = sdf.parse(ngayBD);
        Date currentDate = new Date();

        if (currentDate.before(startDate)) {
            this.trangThai = "Chưa chạy";
        } else {
            this.trangThai = "Đang chạy";
        }
        this.ngayBD = ngayBD;
    } catch (ParseException e) {
        e.printStackTrace();
        // Xử lý ngoại lệ ở đây nếu cần
    }
}

public void setNgayKT(String ngayKT) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
        Date endDate = sdf.parse(ngayKT);
        Date currentDate = new Date();

        if (currentDate.after(endDate) || currentDate.equals(endDate)) {
            this.trangThai = "Hết hạn";
        }
        this.ngayKT = ngayKT;
    } catch (ParseException e) {
        e.printStackTrace();
        // Xử lý ngoại lệ ở đây nếu cần
    }
}

    
    

    public Object[] toDataRow() {
        return new Object[]{this.dieuKienGiamGiaID, this.loaiGiamGia, this.giaTriGiam, this.trangThai, this.ngayBD, this.ngayKT, this.soLuongTao, this.soLuongCon};
    }
}
