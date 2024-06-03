package Service_SanPham;

import Model_SanPham.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import KetNoi.DBConnect;

public class SanPhamService {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<SanPham> getList() {
        sql = "select MASP,LOAI,TONKHO from SANPHAM";
        List<SanPham> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                SanPham bt = new SanPham(rs.getString(1), rs.getString(2), rs.getInt(3));
                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
