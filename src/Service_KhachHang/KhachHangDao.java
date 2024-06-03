/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service_KhachHang;

import Model_KhachHang.KhachHang;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Model_KhachHang.Chung;
import Model_KhachHang.KhoiPhuc;
//import model.Chung;
import Model_KhachHang.LichSuMuaHang;
import KetNoi.DBConnect;
import Model_HoaDon.HoaDon;
import Model_HoaDon.HoaDonChiTiet;
import Model_KhachHang.HienThi;

/**
 *
 * @author ADMIN
 */
public class KhachHangDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<KhachHang> getAll() {
        String sql = """
             SELECT [MaKhachHang]
                   ,[HoTen]
                   ,[GioiTinh]
                   ,[DiaChi]
                   ,[SoDienThoai]
                   ,[Email]                      
                   ,[LoaiKhachHang]
               FROM [dbo].[KHACHHANG]
               WHERE isDelete <> 1;
             """;
        List<KhachHang> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setGioiTinh(rs.getBoolean(3));
                kh.setDiaChi(rs.getString(4));
                kh.setSDT(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setLoaiKH(rs.getString(7));
                lists.add(kh);
            }
            return lists;
        } catch (Exception e) {
        }
        return null;
    }

    public List<HienThi> getHienThi(String ma) {
        String sql = """
             SELECT sp.LOAI,hdct.SoLuong,hdct.TongTien FROM HoaDon hd  JOIN 
                     HOADONCHITIET hdct ON hd.MaHoaDon = hdct.MaHoaDon JOIN 
                     BIENTHESP btsp ON btsp.MABT = hdct.MaBT JOIN 
                     SANPHAM SP ON sp.MASP = btsp.MASP WHERE hd.MaHoaDon = ?
             """;
        List<HienThi> listsHT = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HienThi ht = new HienThi();
                ht.setLoai(rs.getString(1));
                ht.setSoLuong(rs.getInt(2));
                ht.setTien(rs.getBigDecimal(3));
                listsHT.add(ht);
            }
            return listsHT;
        } catch (Exception e) {
        }
        return null;
    }

    public List<Chung> getList() {
        String sql = """
                SELECT DISTINCT * 
                                FROM KHACHHANG
                                LEFT JOIN DIACHIGIAOHANG ON KHACHHANG.MaKhachHang = DIACHIGIAOHANG.MaKhachHang
                                LEFT JOIN KhoiPhuc ON KHACHHANG.MaKhachHang = KhoiPhuc.MaKhachHang 
                                LEFT JOIN LICHSUMUAHANG ON KHACHHANG.MaKhachHang = LICHSUMUAHANG.MaKhachHang                                
                                LEFT JOIN HOADON ON KHACHHANG.MaKhachHang = HOADON.MaKhachHang
                                LEFT JOIN HOADONCHITIET ON HOADON.MaHoaDon = HOADONCHITIET.MaHoaDon
                                WHERE KHACHHANG.isDelete = 0 OR KHACHHANG.isDelete IS NULL;
                """;
        List<Chung> listsDLC = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chung dlc = new Chung();
                dlc.setKh(new KhachHang(rs.getString("MaKhachHang"), rs.getString("HoTen"), rs.getBoolean("GioiTinh"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("LoaiKhachHang")));
                dlc.setKp(new KhoiPhuc(rs.getInt("MaKhoiPhuc"), rs.getString("MaKhachHang"), rs.getString("HanhDong"), rs.getString("ThoiGian")));
                dlc.setLsmh(new LichSuMuaHang(rs.getString("MaHoaDon"), rs.getString("MaKhachHang"), rs.getString("MaSP"), rs.getString("DanhSachDaMua"), rs.getInt("SoLuongMua"), rs.getBigDecimal("DonGia"), rs.getBigDecimal("TongGiaTriDonHang"), rs.getString("TrangThaiDonHang"), rs.getString("NgayTaoDonHang")));
                dlc.setHd(new HoaDon(
                        rs.getString("MaHoaDon"),
                        rs.getString("TrangThai"),
                        rs.getString("MaKhachHang"),
                        rs.getString("MaNhanVien"),
                        rs.getString("NgayTao"),
                        rs.getString("MaDGG"),
                        rs.getString("MaGiamGia"),
                        rs.getInt("GiaTriGiamDGG"),
                        rs.getInt("GiaTriGiamPGG"),
                        rs.getBigDecimal("SoTienGiam"),
                        rs.getBigDecimal("TongTien"),
                        rs.getBoolean("VanChuyen"),
                        rs.getBigDecimal("SoTienThanhToan")));
                dlc.setHdct(new HoaDonChiTiet(
                        rs.getInt("MaHDCT"),
                        rs.getString("MaHoaDon"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("TongTien")));
                listsDLC.add(dlc);
            }
            return listsDLC;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các resource như ResultSet, PreparedStatement và Connection ở đây (trong khối finally)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Xử lý lỗi khi đóng resource
            }
        }
        return null;
    }

    public List<KhachHang> getByMa(String ma) {
        String sql = """
                 SELECT [MaKhachHang]
                       ,[HoTen]
                       ,[GioiTinh]
                       ,[DiaChi]
                       ,[SoDienThoai]
                       ,[Email]                      
                       ,[LoaiKhachHang]
                   FROM [dbo].[KHACHHANG]
                   WHERE MaKhachHang LIKE ?
                 """;
        List<KhachHang> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + ma + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setGioiTinh(rs.getBoolean(3));
                kh.setDiaChi(rs.getString(4));
                kh.setSDT(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setLoaiKH(rs.getString(7));
                lists.add(kh);
            }
            return lists;
        } catch (Exception e) {
        }
        return null;
    }

    public void Add(KhachHang kh) {
        String sql = """
                 INSERT INTO [dbo].[KHACHHANG]
                                 ([MaKhachHang]
                                 ,[HoTen]
                                 ,[GioiTinh]
                                 ,[DiaChi]
                                 ,[SoDienThoai]
                                 ,[Email]
                                 ,[LoaiKhachHang])                               
                           VALUES(?,?,?,?,?,?,?)
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, kh.getMaKH());
            ps.setObject(2, kh.getHoTen());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getDiaChi());
            ps.setObject(5, kh.getSDT());
            ps.setObject(6, kh.getEmail());
            ps.setObject(7, kh.getLoaiKH());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Update(KhachHang kh, String ma) {
        String sql = """
                UPDATE [dbo].[KHACHHANG]
                   SET [MaKhachHang] = ?                     
                      ,[HoTen] = ?
                      ,[GioiTinh] = ?
                      ,[DiaChi] = ?                      
                      ,[SoDienThoai] = ?
                      ,[Email] = ?                      
                      ,[LoaiKhachHang] = ?                                       
                 WHERE MaKhachHang = ?                          
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(7, kh.getMaKH());
            ps.setObject(1, kh.getHoTen());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getDiaChi());
            ps.setObject(4, kh.getSDT());
            ps.setObject(5, kh.getEmail());
            ps.setObject(6, kh.getLoaiKH());
            //ps.setObject(8, ma);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

  public void KhoiPhucMaKhachHang(String ma) {
    String khoiphucma = "UPDATE KHACHHANG SET isDelete = 0 WHERE MaKhachHang = ?";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psKhoiPhucMa = con.prepareStatement(khoiphucma)) {

        psKhoiPhucMa.setString(1, ma);
        psKhoiPhucMa.executeUpdate();
        System.out.println("1");
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void XoaKhachHang(String ma) {
    String sqlKhoiPhuc = "DELETE FROM KHOIPHUC WHERE MaKhachHang = ?";
    String sqlDiaChi = "DELETE FROM DIACHIGIAOHANG WHERE MaKhachHang  = ?";
    String sqlLichsu = "DELETE FROM LICHSUMUAHANG WHERE MaKhachHang  = ?";
    String sqlKhachHang = "DELETE FROM KHACHHANG WHERE MaKhachHang  = ?";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psDeleteKhoiPhuc = con.prepareStatement(sqlKhoiPhuc);
         PreparedStatement psDeleteDiaChi = con.prepareStatement(sqlDiaChi);
         PreparedStatement psDeleteLichSu = con.prepareStatement(sqlLichsu);
         PreparedStatement psDeleteKhachHang = con.prepareStatement(sqlKhachHang)) {

        con.setAutoCommit(false); // Tắt tự động commit

        // Xóa dữ liệu từ bảng KHOIPHUC trước
        psDeleteKhoiPhuc.setString(1, ma);
        psDeleteKhoiPhuc.executeUpdate();

        // Xóa dữ liệu từ bảng LIChSuMUAHANG sau đó
        psDeleteLichSu.setString(1, ma);
        psDeleteLichSu.executeUpdate();

        // Xóa dữ liệu từ bảng DIA_CHI_GIAO_HANG sau đó
        psDeleteDiaChi.setString(1, ma);
        psDeleteDiaChi.executeUpdate();

        // Xóa dữ liệu từ bảng KHACH_HANG cuối cùng
        psDeleteKhachHang.setString(1, ma);
        psDeleteKhachHang.executeUpdate();

        con.commit(); // Thực hiện commit khi tất cả các truy vấn thành công

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}





    public int Delete(String ma) {
        String xoaKH = "UPDATE KHACHHANG SET IsDelete = 1 WHERE MaKhachHang =? ";
        String insertKhoiPhuc = "INSERT INTO KhoiPhuc(MaKhachHang,HanhDong,ThoiGian) VALUES(?,?,?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement psXoaKH = con.prepareStatement(xoaKH); PreparedStatement psInsertKhoiPhuc = con.prepareStatement(insertKhoiPhuc)) {
            con.setAutoCommit(false);

            psXoaKH.setString(1, ma);
            int deleteRow = psXoaKH.executeUpdate();

            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentTime);

            psInsertKhoiPhuc.setString(1, ma);
            psInsertKhoiPhuc.setString(2, "Xóa");
            psInsertKhoiPhuc.setTimestamp(3, currentTimestamp);
            psInsertKhoiPhuc.executeUpdate();

            con.commit();
            return deleteRow;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Tìm kiếm khách hàng
    public List<KhachHang> Search(String ma, String ten, String SDT) {
        String sql = """
                 SELECT [MaKhachHang]
                       ,[HoTen]
                       ,[GioiTinh]
                       ,[DiaChi]
                       ,[SoDienThoai]
                       ,[Email]                      
                       ,[LoaiKhachHang]
                   FROM [dbo].[KHACHHANG]
                   WHERE MaKhachHang LIKE ?
                        OR HoTen LIKE ?
                        OR SoDienThoai LIKE ?
                        
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + ma + "%");
            ps.setObject(2, "%" + ten + "%");
            ps.setObject(3, "%" + SDT + "%");
            ResultSet rs = ps.executeQuery();
            List<KhachHang> lists = new ArrayList<>();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setGioiTinh(rs.getBoolean(3));
                kh.setDiaChi(rs.getString(4));
                kh.setSDT(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setLoaiKH(rs.getString(7));
                lists.add(kh);
            }
            return lists;
        } catch (Exception e) {
        }
        return null;
    }

    public List<Chung> getLichSu(String ma) {
        String sql = """
                      SELECT hd.MaHoaDon,kh.MaKhachHang,hd.TongTien,hd.TrangThai FROM KHACHHANG kh JOIN
                     HOADON hd ON kh.MaKhachHang = hd.MaKhachHang WHERE kh.MaKhachHang = ?
                                         
                    """;
        List<Chung> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            rs = ps.executeQuery();
            while (rs.next()) {
                Chung dlc = new Chung();
                dlc.setKh(new KhachHang(rs.getString("MaKhachHang"), rs.getString("HoTen"), rs.getBoolean("GioiTinh"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("LoaiKhachHang")));
                dlc.setHd(new HoaDon(rs.getString("MaHoaDon"), rs.getString("TrangThai"), rs.getString("MaKhachHang"), rs.getString("MaNhanVien"), rs.getString("NgayTao"), rs.getString("MaDGG"), rs.getString("MaGiamGia"), rs.getInt("GiaTriGiamDGG"), rs.getInt("GiaTriGiamPGG"), rs.getBigDecimal("SoTienGiam"), rs.getBigDecimal("TongTien"), rs.getBoolean("VanChuyen"), rs.getBigDecimal("SoTienThanhToan")));
                listDLC.add(dlc);
            }
            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDon> getDataHoaDonLichSu() {
        String sql = """
            SELECT MaHoaDon,MaKhachHang,TongTien,TrangThai,MaNhanVien FROM HOADON 
                                    
             """;
        List<HoaDon> listsHD = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setMaKhachHang(rs.getString(2));
                hd.setTongTien(rs.getBigDecimal(3));
                hd.setTrangThai(rs.getString(4));
                hd.setMaNhanVien(rs.getString(5));
                listsHD.add(hd);
            }
            return listsHD;
        } catch (Exception e) {
        }
        return null;
    }

    public List<HoaDon> getHoaDonLichSu(String maKH) {
        String sql = """
            SELECT hd.MaHoaDon,kh.MaKhachHang,hd.TongTien,hd.TrangThai FROM KHACHHANG kh JOIN
                                    HOADON hd ON kh.MaKhachHang = hd.MaKhachHang WHERE kh.MaKhachHang = ?
             """;
        List<HoaDon> listsHD = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maKH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString(1));
                hd.setMaKhachHang(rs.getString(2));
                hd.setTongTien(rs.getBigDecimal(3));
                hd.setTrangThai(rs.getString(4));
                listsHD.add(hd);
            }
            return listsHD;
        } catch (Exception e) {
        }
        return null;
    }

    public HoaDon getHoaDonByMa(String maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        try {
            con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HoaDon hoaDon = new HoaDon(
                        rs.getString("MaHoaDon"),
                        rs.getString("TrangThai"),
                        rs.getString("MaKhachHang"),
                        rs.getString("MaNhanVien"),
                        rs.getString("NgayTao"),
                        rs.getString("MaDGG"),
                        rs.getString("MaGiamGia"),
                        rs.getInt("GiaTriGiamDGG"),
                        rs.getInt("GiaTriGiamPGG"),
                        rs.getBigDecimal("SoTienGiam"),
                        rs.getBigDecimal("TongTien"),
                        rs.getBoolean("VanChuyen"),
                        rs.getBigDecimal("SoTienThanhToan")
                );
                return hoaDon;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Chung> getMaLichSu(String ma) {
        String sql = """
                       SELECT * FROM KHACHHANG kh JOIN LICHSUMUAHANG lsmh 
                                         ON kh.MaKhachHang = lsmh.MaKhachHang JOIN HOADON hd
                                         ON lsmh.MaHoaDon = hd.MaHoaDon
                                         WHERE kh.MaKhachHang = ?
                    """;
        List<Chung> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            rs = ps.executeQuery();
            while (rs.next()) {
                Chung dlc = new Chung();
                dlc.setLsmh(new LichSuMuaHang(rs.getString("MaHoaDon"), rs.getString("MaKhachHang"), rs.getString("MaSP"), rs.getString("DanhSachDaMua"), rs.getInt("SoLuongMua"), rs.getBigDecimal("DonGia"), rs.getBigDecimal("TongGiaTriDonHang"), rs.getString("TrangThaiDonHang"), rs.getString("NgayTaoDonHang")));
                listDLC.add(dlc);
            }
            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Chung> getKhoiPhuc() {
        String sql = """
          SELECT * FROM KHACHHANG kh JOIN KhoiPhuc kp ON kh.MaKhachHang = kp.MaKhachHang 
          WHERE kh.isDelete = 1 OR kp.HanhDong LIKE N'Xóa';
          """;
        List<Chung> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Chung dlc = new Chung();
                dlc.setKp(new KhoiPhuc(rs.getInt("MaKhoiPhuc"), rs.getString("MaKhachHang"), rs.getString("HanhDong"), rs.getString("ThoiGian")));
                listDLC.add(dlc);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Chung getTimKiem(String ma) {
        String sql = """
                SELECT sp.MASP,dgsp.MaDanhGia,dgsp.DanhGiaSao,dgsp.NhanXet,dgsp.NhanXet,dgsp.NgayTaoDanhGia,
                 kh.MaKhachHang,kh.HoTen,kh.Email,kh.DiaChi,kh.SoDienThoai,kh.GioiTinh,kh.LoaiKhachHang,
                 lsmh.DanhSachDaMua,lsmh.SoLuongMua,lsmh.TongGiaTriDonHang,lsmh.TrangThaiDonHang,lsmh.DonGia,lsmh.NgayTaoDonHang,
                 hd.MaHoaDon
                 FROM SANPHAM sp                     
                JOIN KHACHHANG kh ON kh.MaKhachHang = dgsp.MaKhachHang
                JOIN LICHSUMUAHANG lsmh ON kh.MaKhachHang = lsmh.MaKhachHang 
                JOIN HOADON hd ON lsmh.MaHoaDon = hd.MaHoaDon 
                WHERE kh.MaKhachHang LIKE ?;
                """;
        Chung dlc = null;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + ma + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dlc = new Chung();
                dlc.setKh(new KhachHang(rs.getString("MaKhachHang"), rs.getString("HoTen"), rs.getBoolean("GioiTinh"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("LoaiKhachHang")));
                dlc.setLsmh(new LichSuMuaHang(rs.getString("MaHoaDon"), rs.getString("MaKhachHang"), rs.getString("MaSP"), rs.getString("DanhSachDaMua"), rs.getInt("SoLuongMua"), rs.getBigDecimal("DonGia"), rs.getBigDecimal("TongGiaTriDonHang"), rs.getString("TrangThaiDonHang"), rs.getString("NgayTaoDonHang")));
            }
        } catch (Exception e) {
        }
        return dlc;
    }

    // lọc bên bảng khách hàng
    public List<Chung> filterRecords(boolean nam, boolean nu, boolean vip, boolean doanhNghiep, boolean caNhan) {
        List<Chung> resultList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM KHACHHANG");

        // Xây dựng các điều kiện trong câu truy vấn SQL dựa trên trạng thái của các checkbox đã chọn
        if (nam || nu || vip || doanhNghiep || caNhan) {
            queryBuilder.append(" WHERE (");
            if (nam) {
                queryBuilder.append(" GioiTinh= 1 AND");
            }
            if (nu) {
                queryBuilder.append(" GioiTinh= 0 AND");
            }
            if (vip) {
                queryBuilder.append(" LoaiKhachHang= N'Vip' AND");
            }
            if (doanhNghiep) {
                queryBuilder.append(" LoaiKhachHang = N'Doanh nghiệp' AND");
            }
            if (caNhan) {
                queryBuilder.append(" LoaiKhachHang = N'Cá nhân' AND");
            }
            // Xóa đi phần "OR" cuối cùng không cần thiết
            queryBuilder.delete(queryBuilder.length() - 3, queryBuilder.length());
            queryBuilder.append(")");
        }

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(queryBuilder.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                Chung dlc = new Chung();
                dlc.setKh(new KhachHang(rs.getString("MaKhachHang"), rs.getString("HoTen"), rs.getBoolean("GioiTinh"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("LoaiKhachHang")));
                resultList.add(dlc);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int khoiPhuc(Chung dlc) {
        String khoiPhucKH = "UPDATE KHACHHANG SET IsDelete = 0 WHERE MaKhachHang = ? ";
        String xoaDuLieuKhoiPhuc = "DELETE FROM KhoiPhuc WHERE MaKhachHang = ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psKhoiPhucKH = con.prepareStatement(khoiPhucKH); PreparedStatement psXoaDuLieuKhoiPhuc = con.prepareStatement(xoaDuLieuKhoiPhuc)) {

            con.setAutoCommit(false);

            psKhoiPhucKH.setString(1, dlc.getKh().getMaKH());
            int restoreRow = psKhoiPhucKH.executeUpdate();

            psXoaDuLieuKhoiPhuc.setString(1, dlc.getKh().getMaKH());
            psXoaDuLieuKhoiPhuc.executeUpdate();

            con.commit();
            return restoreRow;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}
