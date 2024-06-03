
package Model_SanPham;

public class SanPham {
    private String maSp;
    private String loai;
    private int tonKho;

    public SanPham() {
    }

    public SanPham(String maSp, String loai, int tonKho) {
        this.maSp = maSp;
        this.loai = loai;
        this.tonKho = tonKho;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }
    
    public Object[] toDatarow(){
        return new Object[]{maSp,loai,tonKho};
    }
}
