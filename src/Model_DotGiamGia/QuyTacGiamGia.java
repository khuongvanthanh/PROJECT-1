/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_DotGiamGia;

/**
 *
 * @author hung0
 */
public class QuyTacGiamGia {
    private String maQTGG;
    private String tenQtgg;
    private int solanAD;
    private String maDGG;
    private int giatrimin;
    private int giatriMax;
    private String moTa;

    public QuyTacGiamGia() {
    }

    public QuyTacGiamGia(String maQTGG, String tenQtgg, int solanAD, String maDGG, int giatrimin, int giatriMax, String moTa) {
        this.maQTGG = maQTGG;
        this.tenQtgg = tenQtgg;
        this.solanAD = solanAD;
        this.maDGG = maDGG;
        this.giatrimin = giatrimin;
        this.giatriMax = giatriMax;
        this.moTa = moTa;
    }

    public String getMaQTGG() {
        return maQTGG;
    }

    public void setMaQTGG(String maQTGG) {
        this.maQTGG = maQTGG;
    }

    public String getTenQtgg() {
        return tenQtgg;
    }

    public void setTenQtgg(String tenQtgg) {
        this.tenQtgg = tenQtgg;
    }

    public int getSolanAD() {
        return solanAD;
    }

    public void setSolanAD(int solanAD) {
        this.solanAD = solanAD;
    }

    public String getMaDGG() {
        return maDGG;
    }

    public void setMaDGG(String maDGG) {
        this.maDGG = maDGG;
    }

    public int getGiatrimin() {
        return giatrimin;
    }

    public void setGiatrimin(int giatrimin) {
        this.giatrimin = giatrimin;
    }

    public int getGiatriMax() {
        return giatriMax;
    }

    public void setGiatriMax(int giatriMax) {
        this.giatriMax = giatriMax;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    
}
