/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_SanPham;

import Model_SanPham.Tong;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import KetNoi.DBConnect;

/**
 *
 * @author Acer
 */
public class TongService {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<Tong> getList() {
        sql = "SELECT BIENTHESP.MABT, BIENTHESP.MASP, MAULONG, TUOI, CANNANG, CHIEUCAO, NGAYSINH, GIOITINH, Tiem,Hinh, GIABAN, THUCANHOPLY, THUCANDIUNG, THOIQUEN "
                + "FROM BIENTHESP "
                + "JOIN HOTROQUANLY ON BIENTHESP.MABT = HOTROQUANLY.MABT and HOTROQUANLY.ISDELETE = 0  "
                + "JOIN SANPHAM ON BIENTHESP.MASP = SANPHAM.MASP  "
                + "WHERE BIENTHESP.ISDELETE = 0";
        List<Tong> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                Tong bt = new Tong(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("HINH"), rs.getString("THUCANHOPLY"), rs.getString("THUCANDIUNG"), rs.getString("THOIQUEN"));
                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tong> searchBienTheSPByMaSP(String maSP) {
        List<Tong> resultList = new ArrayList<>();

        String sql = "SELECT BIENTHESP.MABT, BIENTHESP.MASP, MAULONG, TUOI, CANNANG, CHIEUCAO, NGAYSINH, GIOITINH, Tiem, GIABAN, THUCANHOPLY, THUCANDIUNG, THOIQUEN "
                + "FROM BIENTHESP "
                + "JOIN HOTROQUANLY ON BIENTHESP.MABT = HOTROQUANLY.MABT AND HOTROQUANLY.ISDELETE = 0  "
                + "JOIN SANPHAM ON BIENTHESP.MASP = SANPHAM.MASP  "
                + "WHERE BIENTHESP.ISDELETE = 0 AND BIENTHESP.MASP = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maSP);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Tong bienTheSP = new Tong();
                    bienTheSP.setbTheSP(resultSet.getString("MABT"));
                    bienTheSP.setMaSP(resultSet.getString("MASP"));
                    bienTheSP.setMauLong(resultSet.getString("MAULONG"));
                    bienTheSP.setTuoi(resultSet.getInt("TUOI"));
                    bienTheSP.setCn(resultSet.getFloat("CANNANG"));
                    bienTheSP.setCc(resultSet.getFloat("CHIEUCAO"));
                    bienTheSP.setNgaySinh(resultSet.getString("NGAYSINH"));
                    bienTheSP.setgTinh(resultSet.getBoolean("GIOITINH"));
                    bienTheSP.setTiem(resultSet.getString("TIEM"));
                    bienTheSP.setGiaB(resultSet.getBigDecimal("GIABAN"));
                    bienTheSP.setTHUCANHOPLY(resultSet.getString("THUCANHOPLY"));
                    bienTheSP.setTHUCANDIUNG(resultSet.getString("THUCANDIUNG"));
                    bienTheSP.setTHOIQUEN(resultSet.getString("THOIQUEN"));

                    resultList.add(bienTheSP);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public boolean checkTrungMa(String maBTT) {
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS count FROM BIENTHESP WHERE MABT = ?")) {

            ps.setString(1, maBTT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Trả về true nếu mã giảm giá đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý các ngoại lệ hoặc trả về false nếu có lỗi xảy ra
        }
        return false; // Trả về false nếu không có hoặc có lỗi xảy ra
    }

    public List<Tong> getList01() {
        sql = "select MASP,LOAI,TONKHO from SANPHAM";
        List<Tong> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                Tong bt = new Tong(rs.getString(1), rs.getString(2), rs.getInt(3));
                listDLC.add(bt);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertData(Tong t) {
        try (Connection connection = DBConnect.getConnection()) {

            // Insert into BIENTHESP table
            String insertBienTheSQL = "INSERT INTO BIENTHESP(MABT,MASP,CANNANG,TUOI, MAULONG, CHIEUCAO, NGAYSINH, GIABAN, GIOITINH, Tiem, Hinh) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatementBienThe = connection.prepareStatement(insertBienTheSQL)) {
                preparedStatementBienThe.setString(1, t.getbTheSP());
                preparedStatementBienThe.setString(2, t.getMaSP());
                preparedStatementBienThe.setFloat(3, t.getCn());
                preparedStatementBienThe.setInt(4, t.getTuoi());
                preparedStatementBienThe.setString(5, t.getMauLong());
                preparedStatementBienThe.setFloat(6, t.getCc());
                preparedStatementBienThe.setString(7, t.getNgaySinh());
                preparedStatementBienThe.setBigDecimal(8, t.getGiaB());
                preparedStatementBienThe.setBoolean(9, t.getgTinh());
                preparedStatementBienThe.setString(10, t.getTiem());
                if (t.getHinhAnh() == null || t.getHinhAnh().isEmpty()) {
                    preparedStatementBienThe.setNull(11, java.sql.Types.VARCHAR);
                } else {
                    preparedStatementBienThe.setString(11, t.getHinhAnh());
                }

                preparedStatementBienThe.executeUpdate();
            }

            // Insert into HOTROQUANLY table
            String insertHoTroSQL = "INSERT INTO HOTROQUANLY(MABT, MASP, THUCANHOPLY, THUCANDIUNG, THOIQUEN, SDTHOTRO) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatementHoTro = connection.prepareStatement(insertHoTroSQL)) {
                preparedStatementHoTro.setString(1, t.getbTheSP());
                preparedStatementHoTro.setString(2, t.getMaSP());
                preparedStatementHoTro.setString(3, t.getTHUCANHOPLY());
                preparedStatementHoTro.setString(4, t.getTHUCANDIUNG());
                preparedStatementHoTro.setString(5, t.getTHOIQUEN());
                preparedStatementHoTro.setString(6, "0333496699");

                preparedStatementHoTro.executeUpdate();
            }

            // Update TONKHO in SANPHAM table
            String updateTonKhoSQL = "UPDATE SANPHAM SET TONKHO = TONKHO + 1 WHERE MASP = ?";
            try (PreparedStatement preparedStatementUpdateTonKho = connection.prepareStatement(updateTonKhoSQL)) {
                preparedStatementUpdateTonKho.setString(1, t.getMaSP());
                preparedStatementUpdateTonKho.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed.
        }
    }

    public void Update(Tong tt, String mabt) {
        String sql;
        Connection con = null;
        PreparedStatement pr = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); // Set auto-commit to false to manage transactions

            // Insert into SANPHAM table
            sql = "UPDATE BIENTHESP SET MASP = ? , CANNANG = ?, TUOI = ?, MAULONG = ?, CHIEUCAO = ?, NGAYSINH = ?, "
                    + "GIABAN = ?, GIOITINH = ?, Tiem = ?, Hinh = ? WHERE MABT = ?";
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
            pr.setObject(11, mabt);
            pr.executeUpdate();
            pr.close();

            // Insert into SOLUONGTONKHO table
            sql = "UPDATE HOTROQUANLY SET MASP = ? , THUCANHOPLY = ?, THUCANDIUNG = ?, THOIQUEN = ?, SDTHOTRO = ? WHERE MABT = ?";
            pr = con.prepareStatement(sql);
            pr.setObject(1, tt.getMaSP());
            pr.setObject(2, tt.getTHUCANHOPLY());
            pr.setObject(3, tt.getTHUCANDIUNG());
            pr.setObject(4, tt.getTHOIQUEN());
            pr.setObject(5, "0333496699");
            pr.setObject(6, mabt);

            pr.executeUpdate();
            pr.close();

            // Insert into TINHTRANGTIEMPHONG table
            con.commit(); // Commit the transaction if everything goes well
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback if any error occurs
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Close resources in a finally block
            try {
                if (pr != null) {
                    pr.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Set auto-commit back to true
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void Update01(Tong tt, String mabt) {
        String sql;
        Connection con = null;
        PreparedStatement pr = null;

        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); // Set auto-commit to false to manage transactions

            // Get the current MASP associated with MABT
            String currentMaspQuery = "SELECT MASP FROM BIENTHESP WHERE MABT = ?";
            String currentMasp;
            try (PreparedStatement preparedStatementCurrentMasp = con.prepareStatement(currentMaspQuery)) {
                preparedStatementCurrentMasp.setString(1, mabt);
                try (ResultSet resultSet = preparedStatementCurrentMasp.executeQuery()) {
                    if (resultSet.next()) {
                        currentMasp = resultSet.getString("MASP");
                    } else {
                        // Handle the case where MASP is not found
                        return;
                    }
                }
            }

            // Decrease inventory for the current MASP in SANPHAM table
            String decreaseInventoryCurrentMaspSQL = "UPDATE SANPHAM SET TONKHO = TONKHO - 1 WHERE MASP = ?";
            try (PreparedStatement preparedStatementDecreaseInventoryCurrentMasp = con.prepareStatement(decreaseInventoryCurrentMaspSQL)) {
                preparedStatementDecreaseInventoryCurrentMasp.setString(1, currentMasp);
                preparedStatementDecreaseInventoryCurrentMasp.executeUpdate();
            }

            // Update BIENTHESP table
            sql = "UPDATE BIENTHESP SET MASP = ? , CANNANG = ?, TUOI = ?, MAULONG = ?, CHIEUCAO = ?, NGAYSINH = ?, "
                    + "GIABAN = ?, GIOITINH = ?, Tiem = ?, Hinh = ? WHERE MABT = ?";
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
            pr.setObject(11, mabt);
            pr.executeUpdate();
            pr.close();

            // Increase inventory for the new MASP in SANPHAM table
            String increaseInventoryNewMaspSQL = "UPDATE SANPHAM SET TONKHO = TONKHO + 1 WHERE MASP = ?";
            try (PreparedStatement preparedStatementIncreaseInventoryNewMasp = con.prepareStatement(increaseInventoryNewMaspSQL)) {
                preparedStatementIncreaseInventoryNewMasp.setString(1, tt.getMaSP());
                preparedStatementIncreaseInventoryNewMasp.executeUpdate();
            }

            // Update HOTROQUANLY table
            sql = "UPDATE HOTROQUANLY SET MASP = ? , THUCANHOPLY = ?, THUCANDIUNG = ?, THOIQUEN = ?, SDTHOTRO = ? WHERE MABT = ?";
            pr = con.prepareStatement(sql);
            pr.setObject(1, tt.getMaSP());
            pr.setObject(2, tt.getTHUCANHOPLY());
            pr.setObject(3, tt.getTHUCANDIUNG());
            pr.setObject(4, tt.getTHOIQUEN());
            pr.setObject(5, "0333496699");
            pr.setObject(6, mabt);
            pr.executeUpdate();

            // Commit the changes
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback if any error occurs
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Close resources in a finally block
            try {
                if (pr != null) {
                    pr.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Set auto-commit back to true
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void softDeleteAndDecreaseInventory(String maBT) {
        try (Connection connection = DBConnect.getConnection()) {
            con.setAutoCommit(false);

            // Get MASP associated with MABT
            String maspQuery = "SELECT MASP FROM BIENTHESP WHERE MABT = ?";
            String masp;
            try (PreparedStatement preparedStatementMasp = connection.prepareStatement(maspQuery)) {
                preparedStatementMasp.setString(1, maBT);
                try (ResultSet resultSet = preparedStatementMasp.executeQuery()) {
                    if (resultSet.next()) {
                        masp = resultSet.getString("MASP");
                    } else {
                        // Handle the case where MASP is not found
                        return;
                    }
                }
            }

            // Decrease inventory in SANPHAM table
            String decreaseInventorySQL = "UPDATE SANPHAM SET TONKHO = TONKHO - 1 WHERE MASP = ?";
            try (PreparedStatement preparedStatementDecreaseInventory = connection.prepareStatement(decreaseInventorySQL)) {
                preparedStatementDecreaseInventory.setString(1, masp);
                preparedStatementDecreaseInventory.executeUpdate();
            }

            // Update BIENTHESP table
            String updateBienTheSQL = "UPDATE BIENTHESP SET isdelete = 1 WHERE MABT = ?";
            try (PreparedStatement preparedStatementBienThe = connection.prepareStatement(updateBienTheSQL)) {
                preparedStatementBienThe.setString(1, maBT);
                preparedStatementBienThe.executeUpdate();
            }

            // Update HOTROQUANLY table
            String updateHTQl = "UPDATE HOTROQUANLY SET isdelete = 1 WHERE MABT = ?";
            try (PreparedStatement preparedStatementHTQl = connection.prepareStatement(updateHTQl)) {
                preparedStatementHTQl.setString(1, maBT);
                preparedStatementHTQl.executeUpdate();
            }

            // Commit the changes
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, consider rolling back changes in case of an error.
        }
    }

//    public int xoa(Tong t) {
//        String xoaPGG = "UPDATE BIENTHESP SET isdelete = 1 WHERE MABT = ?";
//        String insertLichSu = "INSERT INTO HOTROQUANLY(MABT, MASP, THUCANHOPLY, THUCANDIUNG, THOIQUEN, SDTHOTRO) "
//                + "VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection con = DBConnect.getConnection(); PreparedStatement psXoaPGG = con.prepareStatement(xoaPGG); PreparedStatement preparedStatementHoTro = con.prepareStatement(insertLichSu)) {
//
//            con.setAutoCommit(false);
//
//            psXoaPGG.setString(1, t.getbTheSP());
//            int deletedRows = psXoaPGG.executeUpdate();
//
//            LocalDateTime currentTime = LocalDateTime.now();
//            Timestamp currentTimestamp = Timestamp.valueOf(currentTime);
//
//            preparedStatementHoTro.setString(1, t.getbTheSP());
//
//            preparedStatementHoTro.setString(2, t.getTHUCANHOPLY());
//            preparedStatementHoTro.setString(3, t.getTHUCANDIUNG());
//            preparedStatementHoTro.setString(4, t.getTHOIQUEN());
//            preparedStatementHoTro.setString(5, "0333496699"); // Example value, replace with your actual value
//
//            con.commit(); // Commit giao dịch nếu xóa thành công
//
//            return deletedRows;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    public int xoa(Tong t) {
        String xoaPGG = "UPDATE BIENTHESP SET isdelete = 1 WHERE MABT = ?";
        String insertLichSu = "INSERT INTO HOTROQUANLY(MABT, MASP, THUCANHOPLY, THUCANDIUNG, THOIQUEN, SDTHOTRO) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psXoaPGG = con.prepareStatement(xoaPGG); PreparedStatement preparedStatementHoTro = con.prepareStatement(insertLichSu)) {

            con.setAutoCommit(false);

            psXoaPGG.setString(1, t.getbTheSP());
            int deletedRows = psXoaPGG.executeUpdate();

            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentTime);

            preparedStatementHoTro.setString(1, t.getbTheSP());
            preparedStatementHoTro.setString(2, t.getTHUCANHOPLY());
            preparedStatementHoTro.setString(3, t.getTHUCANDIUNG());
            preparedStatementHoTro.setString(4, t.getTHOIQUEN());
            preparedStatementHoTro.setString(5, "0333496699"); // Example value, replace with your actual value

            // Decrease inventory in SANPHAM table
            String decreaseInventorySQL = "UPDATE SANPHAM SET TONKHO = TONKHO - 1 WHERE MASP = ?";
            try (PreparedStatement preparedStatementDecreaseInventory = con.prepareStatement(decreaseInventorySQL)) {
                preparedStatementDecreaseInventory.setString(1, t.getMaSP());
                preparedStatementDecreaseInventory.executeUpdate();
            }
            con.commit(); // Commit giao dịch nếu xóa thành công

            return deletedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Tong> filterRecords(boolean duc, boolean cai) {
        List<Tong> resultList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("select BIENTHESP.MABT,SANPHAM.MASP,MAULONG,TUOI,CANNANG,CHIEUCAO,NGAYSINH,GIOITINH,Tiem,HINH,GIABAN,THUCANHOPLY,THUCANDIUNG,THOIQUEN,TONKHO FROM BIENTHESP JOIN SANPHAM on BIENTHESP.MASP = SANPHAM.MASP join HOTROQUANLY on BIENTHESP.MABT = HOTROQUANLY.MABT");
        // Xây dựng các điều kiện trong câu truy vấn SQL dựa trên trạng thái của các checkbox đã chọn
        if (duc || cai) {
            queryBuilder.append(" Where (");
            if (duc) {
                queryBuilder.append("BIENTHESP.GIOITINH = 1 AND");
            }
            if (cai) {
                queryBuilder.append("BIENTHESP.GIOITINH = 0 AND");
            }

            queryBuilder.delete(queryBuilder.length() - 3, queryBuilder.length());
            queryBuilder.append(")");
        }

        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(queryBuilder.toString());
            rs = pr.executeQuery();
            while (rs.next()) {
                Tong bt = new Tong(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("HINH"), rs.getString("THUCANHOPLY"), rs.getString("THUCANDIUNG"), rs.getString("THOIQUEN"));
                resultList.add(bt);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tong> search(String maBt) {
        sql = "SELECT BIENTHESP.MABT, SANPHAM.MASP, MAULONG, TUOI, CANNANG, CHIEUCAO, NGAYSINH, GIOITINH, Tiem,Hinh, GIABAN, THUCANHOPLY, THUCANDIUNG, THOIQUEN, TONKHO "
                + "FROM BIENTHESP "
                + "JOIN SANPHAM ON BIENTHESP.MASP = SANPHAM.MASP "
                + "JOIN HOTROQUANLY ON BIENTHESP.MABT = HOTROQUANLY.MABT "
                + "WHERE BIENTHESP.MABT LIKE ?";

        List<Tong> resultList = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, "%" + maBt);

            rs = pr.executeQuery();
            while (rs.next()) {
                Tong bt = new Tong(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("HINH"), rs.getString("THUCANHOPLY"), rs.getString("THUCANDIUNG"), rs.getString("THOIQUEN"));
                resultList.add(bt);
            }

            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
