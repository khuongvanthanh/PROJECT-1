/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_PGH;

import Model_PGH.DichVu;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import KetNoi.DBConnect;
import javax.swing.JOptionPane;
/**
 *
 * @author hoang
 */
public class DichVuService {

    List<DichVu> list;
    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<DichVu> getData() {
        sql = "select MaDichVu,TenDichVu,MoTa,Gia from DichVu";
        List<DichVu> service = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                DichVu DV = new DichVu(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4));
                service.add(DV);
            }
            return service;
        } catch (SQLException e) {
            return null;
        }
    }

    public int addDichVu(DichVu Dv) {
        sql = "INSERT INTO DichVu(MaDichVu,TenDichVu,MoTa,Gia ) values (?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, Dv.getMaDv());
            pr.setObject(2, Dv.getTenDv());
            pr.setObject(3, Dv.getMota());
            pr.setObject(4, Dv.getGia());

            return pr.executeUpdate();

            //add , delete , update deu phai dung : executeUpdate(); 
        } catch (SQLException e) {
            return 0;
        }
    }

    public int deleteDichVu(String maDv) { //ma can xoa
        sql = "delete from DichVu where MaDichVu = ?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setObject(1, maDv);

            return pr.executeUpdate();

            //add , delete , update deu phai dung : executeUpdate(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể xoá dịch vụ đang được xử dụng!", "Dịch vụ đang được xử dụng.", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
    }

    public int updateDichVu(DichVu Dv, String maDv) {
        //đối số chuyền vào sẽ là 1 đối tượng sinh viên mới
        //id - của đối tượng cũ
        sql = "update DichVu set TenDichVu = ?,MoTa = ?,Gia = ? where MaDichVu = ?";
        try {// update được
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);

            pr.setObject(1, Dv.getTenDv());
            pr.setObject(2, Dv.getMota());
            pr.setObject(3, Dv.getGia());
            pr.setObject(4, Dv.getMaDv());
            return pr.executeUpdate();
        } catch (Exception e) {//ko dc
            e.printStackTrace();
            return 0;
        }
    }

    public List<DichVu> Search(String ma) {
        list = new ArrayList<>();
        sql = "select MaDichVu,TenDichVu,MoTa,Gia from DichVu where MaDichVu like ? or TenDichVu like ?  ";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, "%" + ma + "%");
            pr.setString(2, "%" + ma + "%");

            rs = pr.executeQuery();
            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDv(rs.getString(1));
                dv.setTenDv(rs.getString(2));
                dv.setMota(rs.getString(3));
                dv.setGia(rs.getBigDecimal(4));
                list.add(dv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                pr.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
