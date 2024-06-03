package Model_PGG;

import Model_NhanVien.NhanVien;

public class DuLieuChung {

    private PhieuGiamGia pgg;
    private DieuKienGiamGia dkgg;
    private LichSu ls;
    private NhanVien nv;

    public DuLieuChung() {
    }

    public DuLieuChung(PhieuGiamGia pgg, DieuKienGiamGia dkgg, LichSu ls, NhanVien nv) {
        this.pgg = pgg;
        this.dkgg = dkgg;
        this.ls = ls;
        this.nv = nv;
    }

    public DuLieuChung(PhieuGiamGia pgg, DieuKienGiamGia dkgg, NhanVien nv) {
        this.pgg = pgg;
        this.dkgg = dkgg;
        this.nv = nv;
    }

    public DuLieuChung(PhieuGiamGia pgg, NhanVien nv) {
        this.pgg = pgg;
        this.nv = nv;
    }

    public PhieuGiamGia getPgg() {
        return pgg;
    }

    public void setPgg(PhieuGiamGia pgg) {
        this.pgg = pgg;
    }

    public DieuKienGiamGia getDkgg() {
        return dkgg;
    }

    public void setDkgg(DieuKienGiamGia dkgg) {
        this.dkgg = dkgg;
    }

    public LichSu getLs() {
        return ls;
    }

    public void setLs(LichSu ls) {
        this.ls = ls;
    }

    public NhanVien getNv() {
        return nv;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    public Object[] toDataRow() {
        return new Object[]{
            getPgg().getMaGiamGia(), getPgg().getTenPhieu(), getPgg().getMaNV(), getPgg().getMoTa(),
            getDkgg().getLoaiGiamGia(), getDkgg().getGiaTriGiam(), getDkgg().getTrangThai(), getDkgg().getNgayBD(), getDkgg().getNgayKT(), getDkgg().getSoLuongTao(), getDkgg().getSoLuongCon(),
            getNv().getMaNhanVien()
        };
    }

    public Object[] toDataRowLS() {
        return new Object[]{getLs().getLichSuID(), getLs().getMaGiamGia(), getLs().getHanhDong(), getLs().getThoiGian()};
    }

}
