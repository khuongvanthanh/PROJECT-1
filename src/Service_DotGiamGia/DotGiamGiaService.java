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
import Model_DotGiamGia.DotGiamGia;

/**
 *
 * @author hung0
 */
public class DotGiamGiaService {
     public List<DotGiamGia> getAll(){
        String sql = """
                 SELECT [MaDGG]
                         ,[TenDGG]
                         ,[MaLGG]
                         ,[MaNhanVien]
                         ,[PhanTramGG]
                         ,[NgayBatDau]
                         ,[NgayKetThuc]
                        
                     FROM [dbo].[DotGiamGia]
                 """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<DotGiamGia> list = new ArrayList<>();
            while(rs.next()){
                DotGiamGia dgg = new DotGiamGia();
                dgg.setMaDGG(rs.getString(1));
                dgg.setTenDGG(rs.getString(2));
                dgg.setMaLGG(rs.getString(3));
                dgg.setMaNV(rs.getString(4));
                dgg.setPhanTramGG(rs.getString(5));
                dgg.setNgayBatDau(rs.getDate(6));
                dgg.setNgayKetThuc(rs.getDate(7));
                list.add(dgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean add(DotGiamGia s){
         String sql = """
            INSERT INTO [dbo].[DotGiamGia]
                                ([MaDGG]
                                ,[TenDGG]
                                ,[MaLGG]
                                ,[MaNhanVien]
                                ,[PhanTramGG]
                                ,[NgayBatDau]
                                ,[NgayKetThuc]
                                
                 VALUES (?,?,?,?,?,?,?)
                 """;
        
        int check = 0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setObject(1, s.getMaDGG());
            ps.setObject(2, s.getTenDGG());
            ps.setObject(3, s.getMaLGG());
            ps.setObject(4, s.getMaNV());
            ps.setObject(5, s.getPhanTramGG());
            ps.setObject(6, s.getNgayBatDau());
            ps.setObject(7, s.getNgayKetThuc());
            
            check = ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
    public boolean remove(String ma){
        String sql = """
                     DELETE FROM [dbo].[DotGiamGia]
                           WHERE MaDGG = ?
                     """;
        int check = 0 ;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
    
    
      public boolean updata(DotGiamGia s, String ma){
        String sql = """
                     UPDATE [dbo].[DotGiamGia]
                              SET [MaDGG] = ?
                                 ,[TenDGG] = ?
                                 ,[MaLGG] = ?
                                 ,[MaNhanVien] = ?
                                 ,[PhanTramGG] = ?
                                 ,[NgayBatDau] = ?
                                 ,[NgayKetThuc] = ?
                      WHERE MaDGG = ?
                     """;
        int check = 0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setObject(1, s.getMaDGG());
            ps.setObject(2, s.getTenDGG());
            ps.setObject(3, s.getMaLGG());
            ps.setObject(4, s.getMaNV());
            ps.setObject(5, s.getPhanTramGG());
            ps.setObject(6, s.getNgayBatDau());
            ps.setObject(7, s.getNgayKetThuc());
            ps.setObject(8, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check >0;
    }
      
      public List<DotGiamGia> search(String MaDGG){
        String sql = """
                 SELECT [MaDGG]
                                                ,[TenDGG]
                                                ,[MaLGG]
                                                ,[MaNhanVien]
                                                ,[PhanTramGG]
                                                ,[NgayBatDau]
                                                ,[NgayKetThuc]
                   FROM [dbo].[DotGiamGia] 
                     where MaDGG = ? or TenDGG = ?
                     
                     
                 """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setObject(1, MaDGG);
                        ps.setObject(2, MaDGG);

            ResultSet rs = ps.executeQuery();
            List<DotGiamGia> list = new ArrayList<>();
            while(rs.next()){
                DotGiamGia dgg = new DotGiamGia();
                dgg.setMaDGG(rs.getString(1));
                dgg.setTenDGG(rs.getString(2));
                dgg.setMaLGG(rs.getString(3));
                dgg.setMaNV(rs.getString(4));
                dgg.setPhanTramGG(rs.getString(5));
                dgg.setNgayBatDau(rs.getDate(6));
                dgg.setNgayKetThuc(rs.getDate(7));
                list.add(dgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      
      
}
