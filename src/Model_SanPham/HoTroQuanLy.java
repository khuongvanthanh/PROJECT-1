
package Model_SanPham;


public class HoTroQuanLy {
     private int MAHOTROQUANLY ;
	private String MABT  ;
    private String MASP ;
    private String THUCANHOPLY ;
    private String THUCANDIUNG ;
    private String THOIQUEN ;
    private String SDTHOTRO ;

    public HoTroQuanLy() {
    }

    public HoTroQuanLy(int MAHOTROQUANLY, String MABT, String MASP, String THUCANHOPLY, String THUCANDIUNG, String THOIQUEN, String SDTHOTRO) {
        this.MAHOTROQUANLY = MAHOTROQUANLY;
        this.MABT = MABT;
        this.MASP = MASP;
        this.THUCANHOPLY = THUCANHOPLY;
        this.THUCANDIUNG = THUCANDIUNG;
        this.THOIQUEN = THOIQUEN;
        this.SDTHOTRO = SDTHOTRO;
    }

    public int getMAHOTROQUANLY() {
        return MAHOTROQUANLY;
    }

    public void setMAHOTROQUANLY(int MAHOTROQUANLY) {
        this.MAHOTROQUANLY = MAHOTROQUANLY;
    }

    public String getMABT() {
        return MABT;
    }

    public void setMABT(String MABT) {
        this.MABT = MABT;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
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

    public String getSDTHOTRO() {
        return SDTHOTRO;
    }

    public void setSDTHOTRO(String SDTHOTRO) {
        this.SDTHOTRO = SDTHOTRO;
    }

    @Override
    public String toString() {
        return "HoTroQuanLy{" + "MAHOTROQUANLY=" + MAHOTROQUANLY + ", MABT=" + MABT + ", MASP=" + MASP + ", THUCANHOPLY=" + THUCANHOPLY + ", THUCANDIUNG=" + THUCANDIUNG + ", THOIQUEN=" + THOIQUEN + ", SDTHOTRO=" + SDTHOTRO + '}';
    }
    
    public Object[] toDataRow(){
        return new Object[]{MAHOTROQUANLY, MABT, MASP, THUCANHOPLY, THUCANDIUNG, THOIQUEN, SDTHOTRO };
    }
}
