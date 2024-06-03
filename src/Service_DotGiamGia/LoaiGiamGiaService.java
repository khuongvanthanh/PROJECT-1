/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_DotGiamGia;

import KetNoi.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model_DotGiamGia.LoaiGiamGia;

/**
 *
 * @author hung0
 */
public class LoaiGiamGiaService {

    public List<LoaiGiamGia> getAlllgg() {
        String sql = """
                 SELECT [MaLGG]
                       ,[TenLGG]
                       ,[GiaTriGG]
                       ,[TrangThai]
                       ,[Mota]
                   FROM [dbo].[Loaigiamgia]
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<LoaiGiamGia> lits1 = new ArrayList<>();
            while (rs.next()) {
                LoaiGiamGia lgg = new LoaiGiamGia();
                lgg.setMaLGG(rs.getString(1));
                lgg.setTenLGG(rs.getString(2));
                lgg.setGiaTriGG(rs.getInt(3));
                lgg.setTrangThai(rs.getBoolean(4));
                lgg.setMoTa(rs.getString(5));
                lits1.add(lgg);

            }
            return lits1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(LoaiGiamGia lgg) {
        String sql = """
            INSERT INTO [dbo].[Loaigiamgia]
                       ([MaLGG]
                       ,[TenLGG]
                       ,[GiaTriGG]
                       ,[TrangThai]
                       ,[Mota])
                 VALUES (?,?,?,?,?)
                 """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, lgg.getMaLGG());
            ps.setObject(2, lgg.getTenLGG());
            ps.setObject(3, lgg.getGiaTriGG());
            ps.setObject(4, lgg.getTrangThai());
            ps.setObject(5, lgg.getMoTa());
            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updata(LoaiGiamGia lgg, String ma) {
        String sql = """
                   UPDATE [dbo].[Loaigiamgia]
                        SET [MaLGG] = ?
                           ,[TenLGG] = ?
                           ,[GiaTriGG] = ?
                           ,[TrangThai] = ?
                           ,[Mota] = ?
                      WHERE MaLGG = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, lgg.getMaLGG());
            ps.setObject(2, lgg.getTenLGG());
            ps.setObject(3, lgg.getGiaTriGG());
            ps.setObject(4, lgg.getTrangThai());
            ps.setObject(5, lgg.getMoTa());
            ps.setObject(6, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removelgg(String ma) {
        String sql = """
                       DELETE FROM [dbo].[Loaigiamgia]
                                            WHERE MaLGG = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<LoaiGiamGia> search(String MaLGG) {
        String sql = """
                 SELECT [MaLGG]
                         ,[TenLGG]
                         ,[GiaTriGG]
                         ,[TrangThai]
                         ,[Mota]
                     FROM [dbo].[Loaigiamgia]
                     where MaLGG = ? or TenLGG = ?
                     
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, MaLGG);
            ps.setObject(2, MaLGG);

            ResultSet rs = ps.executeQuery();
            List<LoaiGiamGia> list = new ArrayList<>();
            while (rs.next()) {
                LoaiGiamGia lgg = new LoaiGiamGia();
                lgg.setMaLGG(rs.getString(1));
                lgg.setTenLGG(rs.getString(2));
                lgg.setGiaTriGG(rs.getInt(3));
                lgg.setTrangThai(rs.getBoolean(4));
                lgg.setMoTa(rs.getString(5));
                list.add(lgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // Tạo một đối tượng của lớp chứa hàm search
        LoaiGiamGiaService yourClassInstance = new LoaiGiamGiaService(); // Thay "YourClassName" bằng tên lớp chứa hàm search của bạn

        // Gọi hàm search với một số giá trị MaLGG để kiểm tra
        String maLGGToSearch = "LG0004"; // Thay "ABC" bằng giá trị MaLGG bạn muốn tìm kiếm
        List<LoaiGiamGia> result = yourClassInstance.search(maLGGToSearch);

        // Kiểm tra kết quả trả về từ hàm search
        if (result != null) {
            if (!result.isEmpty()) {
                System.out.println("Kết quả tìm kiếm:");
                for (LoaiGiamGia lgg : result) {
                    System.out.println("MaLGG: " + lgg.getMaLGG());
                    System.out.println("TenLGG: " + lgg.getTenLGG());
                    System.out.println("GiaTriGG: " + lgg.getGiaTriGG());
                    System.out.println("TrangThai: " + lgg.getTrangThai());
                    System.out.println("MoTa: " + lgg.getMoTa());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("Không tìm thấy kết quả.");
            }
        } else {
            System.out.println("Có lỗi xảy ra trong quá trình tìm kiếm.");
        }
    }
}
