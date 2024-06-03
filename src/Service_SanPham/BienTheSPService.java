package Service_SanPham;

import Model_SanPham.BienTheSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import KetNoi.DBConnect;

public class BienTheSPService {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<BienTheSanPham> getList() {
        sql = "select MABT,MASP,MAULONG,TUOI,CANNANG,CHIEUCAO,NGAYSINH,GIOITINH,Tiem,GIABAN,Hinh from BIENTHESP";

        List<BienTheSanPham> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                BienTheSanPham bt = new BienTheSanPham(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("Hinh"));

                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addSP(BienTheSanPham tt) {
        sql = "INSERT INTO BIENTHESP(MABT,MASP,CANNANG,TUOI,MAULONG,CHIEUCAO,NGAYSINH,GIABAN,GIOITINH,Tiem,Hinh) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, tt.getbTheSP());
            pr.setObject(2, tt.getMaSP());
            pr.setObject(3, tt.getCn());
            pr.setObject(4, tt.getTuoi());
            pr.setObject(5, tt.getMauLong());
            pr.setObject(6, tt.getCc());
            pr.setObject(7, tt.getNgaySinh());
            pr.setObject(8, tt.getGiaB());
            pr.setObject(9, tt.getgTinh());
            pr.setObject(10, tt.getTiem());

            if (tt.getHinhAnh() == null || tt.getHinhAnh().isEmpty()) {
                pr.setNull(11, java.sql.Types.VARCHAR);
            } else {
                pr.setObject(11, tt.getHinhAnh());
            }

            return pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteSanPham(String MABT) { //ma can xoa
        sql = "delete from BIENTHESP where MABT = ? ";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, MABT);

            return pr.executeUpdate();

            //add , delete , update deu phai dung : executeUpdate(); 
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateSanPham(BienTheSanPham tt, String MABT) {
        //đối số chuyền vào sẽ là 1 đối tượng sinh viên mới
        //id - của đối tượng cũ
        sql = "update  BIENTHESP set MASP = ? ,CANNANG = ? ,TUOI = ?,MAULONG = ?,CHIEUCAO = ?,NGAYSINH = ?,GIABAN = ?,GIOITINH=?, tiem = ?, Hinh = ? where MABT = ?";
        try {// update được
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);

            pr.setObject(1, tt.getMaSP());
            pr.setObject(2, tt.getCn());
            pr.setObject(3, tt.getTuoi());
            pr.setObject(4, tt.getMauLong());
            pr.setObject(5, tt.getCc());
            pr.setObject(6, tt.getNgaySinh());
            pr.setObject(7, tt.getGiaB());
            pr.setObject(8, tt.getgTinh());
            pr.setObject(9, tt.getTiem());
            pr.setObject(10, tt.getHinhAnh());
            pr.setObject(11, MABT);

            return pr.executeUpdate();
        } catch (Exception e) {//ko dc
            e.printStackTrace();
            return 0;
        }
    }

    public List<BienTheSanPham> searchBienTheSPByMaSP(String maSP) {
        List<BienTheSanPham> resultList = new ArrayList<>();

        String sql = "SELECT * FROM BIENTHESP WHERE MASP = ? AND ISDELETE = 0";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maSP);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BienTheSanPham bienTheSP = new BienTheSanPham();
                    bienTheSP.setbTheSP(resultSet.getString("MABT"));
                    bienTheSP.setMaSP(resultSet.getString("MASP"));
                    bienTheSP.setCn(resultSet.getFloat("CANNANG"));
                    bienTheSP.setTuoi(resultSet.getInt("TUOI"));
                    bienTheSP.setMauLong(resultSet.getString("MAULONG"));
                    bienTheSP.setCc(resultSet.getFloat("CHIEUCAO"));
                    bienTheSP.setNgaySinh(resultSet.getString("NGAYSINH"));
                    bienTheSP.setGiaB(resultSet.getBigDecimal("GIABAN"));
                    bienTheSP.setgTinh(resultSet.getBoolean("GIOITINH"));
                    bienTheSP.setTiem(resultSet.getString("TIEM"));

                    resultList.add(bienTheSP);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
