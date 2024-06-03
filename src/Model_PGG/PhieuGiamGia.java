package Model_PGG;

public class PhieuGiamGia {

    private String maGiamGia;
    private String tenPhieu;
    private String maNV;
    private String moTa;

    public PhieuGiamGia() {
    }

    public PhieuGiamGia(String maGiamGia, String tenPhieu, String maNV, String moTa) {
        this.maGiamGia = maGiamGia;
        this.tenPhieu = tenPhieu;
        this.maNV = maNV;
        this.moTa = moTa;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenPhieu() {
        return tenPhieu;
    }

    public void setTenPhieu(String tenPhieu) {
        this.tenPhieu = tenPhieu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "PhieuGiamGia{" + "maGiamGia=" + maGiamGia + ", tenPhieu=" + tenPhieu + ", maNV=" + maNV + ", moTa=" + moTa + '}';
    }

}
