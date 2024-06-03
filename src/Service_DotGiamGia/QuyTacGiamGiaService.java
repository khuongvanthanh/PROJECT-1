/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_DotGiamGia;

import Model_DotGiamGia.QuyTacGiamGia;
import KetNoi.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author hung0
 */
public class QuyTacGiamGiaService {
    public List<QuyTacGiamGia> getAllqtgg(){
        String sql = """
                 SELECT [MaQTGG]
                       ,[TenQTGG]
                       ,[SoLanAD]
                       ,[MaDGG]
                       ,[GiaTriMin]
                       ,[GiaTriMax]
                       ,[Mota]
                   FROM [dbo].[QuyTacGiamGia]
                 """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<QuyTacGiamGia> listqtgg = new ArrayList<>();
            while(rs.next()){
                QuyTacGiamGia qtgg = new QuyTacGiamGia();
                qtgg.setMaQTGG(rs.getString(1));
                qtgg.setTenQtgg(rs.getString(2));
                qtgg.setSolanAD(rs.getInt(3));
                qtgg.setMaDGG(rs.getString(4));
                qtgg.setGiatrimin(rs.getInt(5));
                qtgg.setGiatriMax(rs.getInt(6));
                qtgg.setMoTa(rs.getString(7));
                listqtgg.add(qtgg);
            }
            return listqtgg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean add(QuyTacGiamGia qt){
         String sql = """
                           INSERT INTO [dbo].[QuyTacGiamGia]
                                      ([MaQTGG]
                                      ,[TenQTGG]
                                      ,[SoLanAD]
                                      ,[MaDGG]
                                      ,[GiaTriMin]
                                      ,[GiaTriMax]
                                      ,[Mota])
                             VALUES (?,?,?,?,?,?,?)
                 """;
        
        int check = 0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setObject(1, qt.getMaQTGG());
            ps.setObject(2, qt.getTenQtgg());
            ps.setObject(3, qt.getSolanAD());
            ps.setObject(4, qt.getMaDGG());
            ps.setObject(5, qt.getGiatrimin());
            ps.setObject(6, qt.getGiatriMax());
            ps.setObject(7, qt.getMoTa());
            
            check = ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
    public boolean removeqtgg(String ma){
        String sql = """
                     DELETE FROM [dbo].[QuyTacGiamGia]
                           WHERE MaQTGG = ?
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
    
    public List<QuyTacGiamGia> search(String ma){
        String sql = """
                 SELECT [MaQTGG]
                       ,[TenQTGG]
                       ,[SoLanAD]
                       ,[MaDGG]
                       ,[GiaTriMin]
                       ,[GiaTriMax]
                       ,[Mota]
                   FROM [dbo].[QuyTacGiamGia]
                     Where MaQTGG = ? or TenQTGG = ?
                 """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setObject(1, ma);
                        ps.setObject(2, ma);

            ResultSet rs = ps.executeQuery();
            List<QuyTacGiamGia> list = new ArrayList<>();
            while(rs.next()){
                QuyTacGiamGia qtgg = new QuyTacGiamGia();
                qtgg.setMaQTGG(rs.getString(1));
                qtgg.setTenQtgg(rs.getString(2));
                qtgg.setSolanAD(rs.getInt(3));
                qtgg.setMaDGG(rs.getString(4));
                qtgg.setGiatrimin(rs.getInt(5));
                qtgg.setGiatriMax(rs.getInt(6));
                qtgg.setMoTa(rs.getString(7));
                list.add(qtgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    public static void main(String[] args) {
        // Replace "your_input_here" with the actual value you want to search for
        String searchTerm = "QT0001";

        // Create an instance of your class containing the search method
        QuyTacGiamGiaService yourClassInstance = new QuyTacGiamGiaService();

        // Call the search method and store the result
        List<QuyTacGiamGia> result = yourClassInstance.search(searchTerm);

        // Check if the result is not null and contains elements
        if (result != null && !result.isEmpty()) {
            // Print the results
            System.out.println("Search results for '" + searchTerm + "':");
            for (QuyTacGiamGia qtgg : result) {
                System.out.println("MaQTGG: " + qtgg.getMaQTGG());
                System.out.println("TenQTGG: " + qtgg.getTenQtgg());
                System.out.println("SoLanAD: " + qtgg.getSolanAD());
                System.out.println("MaDGG: " + qtgg.getMaDGG());
                System.out.println("GiaTriMin: " + qtgg.getGiatrimin());
                System.out.println("GiaTriMax: " + qtgg.getGiatriMax());
                System.out.println("MoTa: " + qtgg.getMoTa());
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No results found for '" + searchTerm + "'.");
        }
    }


}
