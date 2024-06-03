package Service_SanPham;

import Model_SanPham.NCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import KetNoi.DBConnect;

public class NCCservice {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<NCC> getList() {
        sql = "select MANCC,MASP,TENNCC,SDTNCC,THOATHUANMUABAN  from NCC";

        List<NCC> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                NCC bt = new NCC(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<NCC> searchBienTheSPByMaSP(String maSP) {
        List<NCC> resultList = new ArrayList<>();

        String sql = "select MANCC,MASP,TENNCC,SDTNCC,THOATHUANMUABAN from NCC WHERE MASP = ? AND ISDELETE = 0";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maSP);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    NCC NCC = new NCC();
                    NCC.setMANCC(resultSet.getInt("MANCC"));
                    NCC.setMASP(resultSet.getString("MASP"));
                    NCC.setTENNCC(resultSet.getString("TENNCC"));
                    NCC.setSDTNCC(resultSet.getString("SDTNCC"));
                    NCC.setTHOATHUANMUABAN(resultSet.getString("THOATHUANMUABAN"));
                    

                    resultList.add(NCC);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
