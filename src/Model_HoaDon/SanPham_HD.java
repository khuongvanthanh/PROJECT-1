package Model_HoaDon;

import Model_SanPham.BienTheSanPham;
import Model_SanPham.SanPham;
import java.text.DecimalFormat;

public class SanPham_HD {

    private SanPham sp;
    private BienTheSanPham btsp;

    public SanPham_HD() {
    }

    public SanPham_HD(SanPham sp, BienTheSanPham btsp) {
        this.sp = sp;
        this.btsp = btsp;
    }

    public SanPham getSp() {
        return sp;
    }

    public void setSp(SanPham sp) {
        this.sp = sp;
    }

    public BienTheSanPham getBtsp() {
        return btsp;
    }

    public void setBtsp(BienTheSanPham btsp) {
        this.btsp = btsp;
    }

    @Override
    public String toString() {
        return "SanPham_HD{" + "sp=" + sp + ", btsp=" + btsp + '}';
    }

    public Object[] toDataRowSP() {
        // Định dạng giá bán về kiểu tiền VND mà không có ký tự VND
        DecimalFormat decimalFormat = new DecimalFormat("####");
        String giaBanVND = decimalFormat.format(btsp.getGiaB());

        return new Object[]{
            sp.getMaSp(),btsp.getbTheSP(), sp.getTonKho(), giaBanVND
        };
    }

}
