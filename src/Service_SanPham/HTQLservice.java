package Service_SanPham;

import Model_SanPham.HoTroQuanLy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import KetNoi.DBConnect;

public class HTQLservice {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<HoTroQuanLy> getList() {
        sql = "select MAHOTROQUANLY,MABT,MASP,THUCANHOPLY,THUCANDIUNG,THOIQUEN,SDTHOTRO from HOTROQUANLY";

        List<HoTroQuanLy> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                HoTroQuanLy bt = new HoTroQuanLy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoTroQuanLy> searchBienTheSPByMaSP(String maSP) {
        List<HoTroQuanLy> resultList = new ArrayList<>();

        String sql = "select MAHOTROQUANLY,MABT,MASP,THUCANHOPLY,THUCANDIUNG,THOIQUEN,SDTHOTRO from HOTROQUANLY WHERE MASP = ? ";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maSP);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HoTroQuanLy ht = new HoTroQuanLy();
                    ht.setMAHOTROQUANLY(resultSet.getInt("MAHOTROQUANLY"));
                    ht.setMABT(resultSet.getString("MABT"));
                    ht.setMASP(resultSet.getString("MASP"));
                    ht.setTHUCANHOPLY(resultSet.getString("THUCANHOPLY"));
                    ht.setTHUCANDIUNG(resultSet.getString("THUCANDIUNG"));
                    ht.setTHOIQUEN(resultSet.getString("THOIQUEN"));
                    ht.setSDTHOTRO(resultSet.getString("SDTHOTRO"));

                    resultList.add(ht);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
