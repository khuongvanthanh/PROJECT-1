/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_PGH;

import Model_PGH.PhieuGiaoHang;
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
public class PhieuGiaoHangService {

    List<PhieuGiaoHang> list;
    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<PhieuGiaoHang> getData() {
        sql = "select MaPhieuGiaoHang,MaHoaDon,NgayTao,NguoiTao,TrangThai,DiaChi,NgayGiaoDuKien,ThoiDiemGiao,NguoiNhan,GhiChu from PhieuGiaoHang";
        List<PhieuGiaoHang> service = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                PhieuGiaoHang PGH = new PhieuGiaoHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                service.add(PGH);
            }
            return service;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuGiaoHang> getData(String TrangThai) {
        sql = "select MaPhieuGiaoHang,MaHoaDon,NgayTao,NguoiTao,TrangThai,DiaChi,NgayGiaoDuKien,ThoiDiemGiao,NguoiNhan,GhiChu from PhieuGiaoHang where TrangThai like ?";
        List<PhieuGiaoHang> service = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, TrangThai);
            rs = pr.executeQuery();
            while (rs.next()) {
                PhieuGiaoHang PGH = new PhieuGiaoHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                service.add(PGH);
            }
            return service;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addData(PhieuGiaoHang PGH) {
        sql = "insert into PhieuGiaoHang(MaPhieuGiaoHang,MaHoaDon,NgayTao,NguoiTao,TrangThai,DiaChi,NgayGiaoDuKien,ThoiDiemGiao,NguoiNhan,GhiChu) values(?,?,?,?,?,?,?,?,?,?)";

        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, PGH.getMaPGH());
            pr.setString(2, PGH.getMaHD());
            pr.setString(3, PGH.getNgayTao());
            pr.setString(4, PGH.getNguoiTao());
            pr.setString(5, PGH.getTrangThai());
            pr.setString(6, PGH.getDiaChi());
            pr.setString(7, PGH.getNgayGiaoDuKien());
            pr.setString(8, PGH.getThoiDiemGiao());
            pr.setString(9, PGH.getNguoiNhan());
            pr.setString(10, PGH.getGhiChu());
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData(PhieuGiaoHang PGH, String MaPhieuGiaoHang) {
        sql = "update PhieuGiaoHang set NgayTao=?,NguoiTao=?,TrangThai=?,DiaChi=?,NgayGiaoDuKien=?,ThoiDiemGiao=?,NguoiNhan=?,GhiChu=? where MaPhieuGiaoHang=?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, PGH.getNgayTao());
            pr.setString(2, PGH.getNguoiTao());
            pr.setString(3, PGH.getTrangThai());
            pr.setString(4, PGH.getDiaChi());
            pr.setString(5, PGH.getNgayGiaoDuKien());
            pr.setString(6, PGH.getThoiDiemGiao());
            pr.setString(7, PGH.getNguoiNhan());
            pr.setString(8, PGH.getGhiChu());
            pr.setString(9, MaPhieuGiaoHang);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteData(String MaPhieuGiaoHang) {
        sql = "delete from PhieuGiaoHang where MaPhieuGiaoHang=?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, MaPhieuGiaoHang);
            pr.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể xoá phiếu giao hàng đang được xử dụng!", "Phiếu giao hàng đang được xử dụng.", JOptionPane.WARNING_MESSAGE);
        }
    }
    public List<PhieuGiaoHang> Search(String ma) {
        list =new ArrayList<>();
        sql="select MaPhieuGiaoHang,MaHoaDon,NgayTao,NguoiTao,TrangThai,DiaChi,NgayGiaoDuKien,ThoiDiemGiao,NguoiNhan,GhiChu from PhieuGiaoHang where MaPhieuGiaoHang like ? or MaHoaDon like ? ";
        try {
            con=DBConnect.getConnection();
            pr=con.prepareStatement(sql);
            pr.setString(1, "%" + ma + "%");
            pr.setString(2, "%" + ma + "%");
            
            rs=pr.executeQuery();
            while (rs.next()) {                
                PhieuGiaoHang gh = new PhieuGiaoHang();
                gh.setMaPGH(rs.getString(1));
                gh.setMaHD(rs.getString(2));
                gh.setNgayTao(rs.getString(3));
                gh.setNguoiTao(rs.getString(4));
                gh.setTrangThai(rs.getString(5));
                gh.setDiaChi(rs.getString(6));
                gh.setNgayGiaoDuKien(rs.getString(7));
                gh.setThoiDiemGiao(rs.getString(8));
                gh.setNguoiNhan(rs.getString(9));
                gh.setGhiChu(rs.getString(10));
                list.add(gh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            try {
                pr.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
