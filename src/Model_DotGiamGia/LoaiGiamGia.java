/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_DotGiamGia;

/**
 *
 * @author hung0
 */
public class LoaiGiamGia {
    private String maLGG;
    private String tenLGG;
    private int giaTriGG;
    private Boolean trangThai;
    private String moTa;

    public LoaiGiamGia() {
    }

    public LoaiGiamGia(String maLGG, String tenLGG, int giaTriGG, Boolean trangThai, String moTa) {
        this.maLGG = maLGG;
        this.tenLGG = tenLGG;
        this.giaTriGG = giaTriGG;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    public String getMaLGG() {
        return maLGG;
    }

    public void setMaLGG(String maLGG) {
        this.maLGG = maLGG;
    }

    public String getTenLGG() {
        return tenLGG;
    }

    public void setTenLGG(String tenLGG) {
        this.tenLGG = tenLGG;
    }

    public int getGiaTriGG() {
        return giaTriGG;
    }

    public void setGiaTriGG(int giaTriGG) {
        this.giaTriGG = giaTriGG;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

   
}
