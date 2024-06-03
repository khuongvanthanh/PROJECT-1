/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model_SanPham;

/**
 *
 * @author Acer
 */
public class NCC {
    private int MANCC ;
    private String MASP ;
	private String TENNCC ;
    private String SDTNCC ;
    private String THOATHUANMUABAN ;

    public NCC(int MANCC, String MASP, String TENNCC, String SDTNCC, String THOATHUANMUABAN) {
        this.MANCC = MANCC;
        this.MASP = MASP;
        this.TENNCC = TENNCC;
        this.SDTNCC = SDTNCC;
        this.THOATHUANMUABAN = THOATHUANMUABAN;
    }

    public NCC() {
    }

    public int getMANCC() {
        return MANCC;
    }

    public void setMANCC(int MANCC) {
        this.MANCC = MANCC;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENNCC() {
        return TENNCC;
    }

    public void setTENNCC(String TENNCC) {
        this.TENNCC = TENNCC;
    }

    public String getSDTNCC() {
        return SDTNCC;
    }

    public void setSDTNCC(String SDTNCC) {
        this.SDTNCC = SDTNCC;
    }

    public String getTHOATHUANMUABAN() {
        return THOATHUANMUABAN;
    }

    public void setTHOATHUANMUABAN(String THOATHUANMUABAN) {
        this.THOATHUANMUABAN = THOATHUANMUABAN;
    }

    public Object[] todataRow(){
        return new Object[]{ MANCC, MASP, TENNCC, SDTNCC, THOATHUANMUABAN};
    }
}
