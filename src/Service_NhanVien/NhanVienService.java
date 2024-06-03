package Service_NhanVien;

import Model_NhanVien.DuLieuChung_NV;
import Model_NhanVien.LichSuNhanVien;
import Model_NhanVien.NhanVien;
import KetNoi.DBConnect;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NhanVienService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<DuLieuChung_NV> getList() {
        sql = "SELECT * FROM NHANVIEN WHERE IsDelete <> 1";
        List<DuLieuChung_NV> listDLC_NV = new ArrayList<>();

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung_NV dlcnv = new DuLieuChung_NV();
                dlcnv.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));
                listDLC_NV.add(dlcnv);
            }

            return listDLC_NV;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<DuLieuChung_NV> getLichSuNV() {
        sql = "SELECT HD.MaHoatDong, HD.HanhDong, HD.ThoiGianThucHien, NV.MaNhanVien, NV.HoTen, NV.DiaChi, NV.SoDienThoai, NV.Email, NV.ChucVu, NV.BoPhan, NV.TrangThai, NV.GioiTinh, NV.TenDangNhap, NV.MatKhau, NV.VaiTro "
                + "FROM HoatDong HD "
                + "LEFT JOIN NhanVien NV ON HD.MaNhanVien = NV.MaNhanVien "
                + "WHERE NV.IsDelete = 1 OR HD.HanhDong LIKE N'Xóa'";
        List<DuLieuChung_NV> listDLC_LS = new ArrayList<>();

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung_NV dlcnv = new DuLieuChung_NV();
                dlcnv.setLsnv(new LichSuNhanVien(rs.getInt("MaHoatDong"), rs.getString("HanhDong"), rs.getString("ThoiGianThucHien"), rs.getString("MaNhanVien")));
                listDLC_LS.add(dlcnv);
            }

            return listDLC_LS;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public NhanVien selectById(String ma) {
        sql = "SELECT * FROM NhanVien WHERE TenDangNhap LIKE ?";
        List<NhanVien> list = new ArrayList<>();

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            System.out.println(ma);
            rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro"));
                list.add(nv);
            }

            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng các resource
        }
    }

    public boolean insert(DuLieuChung_NV dlcnv) {
        sql = "INSERT INTO NHANVIEN (MaNhanVien, HoTen, DiaChi, SoDienThoai, Email, ChucVu, BoPhan, TrangThai, GioiTinh, TenDangNhap, MatKhau, VaiTro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            NhanVien nv = dlcnv.getNv();

            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getChucVu());
            ps.setString(7, nv.getBoPhan());
            ps.setString(8, nv.getTrangThai());
            ps.setBoolean(9, nv.isGioiTinh());
            ps.setString(10, nv.getTenDangNhap());
            ps.setString(11, nv.getMatKhau());
            ps.setBoolean(12, nv.isVaiTro());

            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public Boolean update(DuLieuChung_NV dlcnv) {
        sql = "UPDATE NHANVIEN SET HoTen = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, ChucVu = ?, "
                + "BoPhan = ?, TrangThai = ?, GioiTinh = ?, TenDangNhap = ?, MatKhau = ?, VaiTro = ? WHERE MaNhanVien = ?";

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            NhanVien nv = dlcnv.getNv();

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getDiaChi());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getBoPhan());
            ps.setString(7, nv.getTrangThai());
            ps.setBoolean(8, nv.isGioiTinh());
            ps.setString(9, nv.getTenDangNhap());
            ps.setString(10, nv.getMatKhau());
            ps.setBoolean(11, nv.isVaiTro());
            ps.setString(12, nv.getMaNhanVien());

            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public Boolean updateMK(String maNhanVien, String matKhauMoi) {
        String sql = "UPDATE NhanVien SET MatKhau = ? WHERE MaNhanVien = ?";

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Sét giá trị mới cho trường 'MatKhau' và mã nhân viên 'MaNhanVien'
            ps.setString(1, matKhauMoi);
            ps.setString(2, maNhanVien);

            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean delete(String maNhanVien) {
        String deleteNhanVien = "UPDATE NHANVIEN SET IsDelete = 1 WHERE MaNhanVien = ?";
        String insertLichSu = "INSERT INTO HoatDong (HanhDong, ThoiGianThucHien, MaNhanVien) VALUES (?, ?, ?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psDeleteNhanVien = con.prepareStatement(deleteNhanVien); PreparedStatement psInsertLichSu = con.prepareStatement(insertLichSu)) {

            con.setAutoCommit(false);

            psDeleteNhanVien.setString(1, maNhanVien);
            int deletedRows = psDeleteNhanVien.executeUpdate();

            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentTime);

            psInsertLichSu.setString(1, "Xóa");
            psInsertLichSu.setTimestamp(2, currentTimestamp);
            psInsertLichSu.setString(3, maNhanVien);
            psInsertLichSu.executeUpdate();

            con.commit(); // Commit giao dịch nếu xóa thành công
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DuLieuChung_NV getTimKiem(String ma) {
        sql = "SELECT HD.MaHoatDong, HD.HanhDong, HD.ThoiGianThucHien, NV.MaNhanVien, NV.HoTen, NV.DiaChi,"
                + "NV.SoDienThoai, NV.Email,NV.ChucVu,NV.BoPhan,NV.TrangThai,NV.GioiTinh,NV.TenDangNhap,NV.MatKhau,NV.VaiTro,NV.isDelete "
                + "FROM HoatDong HD "
                + "INNER JOIN NhanVien NV ON HD.MaNhanVien = NV.MaNhanVien "
                + "WHERE NV.MaNhanVien LIKE ?";
        DuLieuChung_NV dlcnv = null;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + ma + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dlcnv = new DuLieuChung_NV();
                    dlcnv.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));
                    dlcnv.setLsnv(new LichSuNhanVien(rs.getInt("MaHoatDong"), rs.getString("HanhDong"), rs.getString("ThoiGianThucHien"), rs.getString("MaNhanVien")));
                }
            }
            System.out.println("1");
        } catch (Exception e) {
            e.printStackTrace(System.out);

        }
        return dlcnv;
    }

    public int softDelete(String nvId) {
        String khoiphucNV = "UPDATE NhanVien SET IsDelete = 1 WHERE MaNhanVien = ?";
        String xoaHD = "DELETE HoatDong WHERE MaNhanVien = ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psKhoiphucNV = con.prepareStatement(khoiphucNV); PreparedStatement psXoaHD = con.prepareStatement(xoaHD)) {

            con.setAutoCommit(false);

            psKhoiphucNV.setString(1, nvId);
            int updatedRowsNV = psKhoiphucNV.executeUpdate();

            psXoaHD.setString(1, nvId);
            int updatedRowsHD = psXoaHD.executeUpdate();

            con.commit(); // Commit giao dịch nếu xóa thành công
            return (updatedRowsNV > 0 && updatedRowsHD > 0) ? 1 : 0; // Trả về 1 nếu cả hai bảng đều được cập nhật thành công, ngược lại trả về 0
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            // Rollback giao dịch nếu có lỗi
        }
        return 0;
    }

    public int restorePhieuGiamGia(String ma) {
        String restorePGG = "UPDATE NhanVien SET isDelete = 0 WHERE MaNhanVien = ?";
        String xoaLichSu = "DELETE FROM HoatDong WHERE MaNhanVien = ?";
        try {
            con = DBConnect.getConnection();
            // Truy vấn khôi phục dữ liệu cho Phiếu Giảm Giá
            ps = con.prepareStatement(restorePGG);
            ps.setString(1, ma);
            int updatedRowsPGG = ps.executeUpdate();

            // Truy vấn xóa thông tin liên quan trong Lịch Sử Phiếu Giảm Giá
            ps = con.prepareStatement(xoaLichSu);
            ps.setString(1, ma);
            int updatedRowsLichSu = ps.executeUpdate();

            // Trả về tổng số hàng đã được cập nhật hoặc xóa           
            return updatedRowsPGG + updatedRowsLichSu;

        } catch (SQLException e) {

            return 0; // Trả về 0 nếu có lỗi xảy ra

        }
    }

    public List<DuLieuChung_NV> timKiem(String keyword) {
        String sql = "SELECT NV.MaNhanVien, NV.HoTen, NV.DiaChi, "
                + "NV.SoDienThoai, NV.Email, NV.ChucVu, NV.BoPhan, NV.TrangThai, NV.GioiTinh, NV.TenDangNhap, NV.MatKhau, NV.VaiTro, NV.isDelete "
                + "FROM NhanVien NV "
                + "WHERE NV.isDelete <> 1 AND (NV.MaNhanVien LIKE ? OR NV.HoTen LIKE ? OR NV.SoDienThoai LIKE ?)";

        List<DuLieuChung_NV> resultList = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String keywordPattern = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, keywordPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("MaNhanVien"),
                            rs.getString("HoTen"),
                            rs.getString("DiaChi"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("ChucVu"),
                            rs.getString("BoPhan"),
                            rs.getString("TrangThai"),
                            rs.getBoolean("GioiTinh"),
                            rs.getString("TenDangNhap"),
                            rs.getString("MatKhau"),
                            rs.getBoolean("VaiTro")
                    );

                    DuLieuChung_NV dlcnv = new DuLieuChung_NV(nv);
                    resultList.add(dlcnv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<DuLieuChung_NV> getSearch(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien LIKE ? OR HoTen LIKE ? OR SoDienThoai LIKE ?";

        List<DuLieuChung_NV> resultList = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String keywordPattern = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, keywordPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("MaNhanVien"),
                            rs.getString("HoTen"),
                            rs.getString("DiaChi"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("ChucVu"),
                            rs.getString("BoPhan"),
                            rs.getString("TrangThai"),
                            rs.getBoolean("GioiTinh"),
                            rs.getString("TenDangNhap"),
                            rs.getString("MatKhau"),
                            rs.getBoolean("VaiTro")
                    );

                    DuLieuChung_NV dlcnv = new DuLieuChung_NV(nv);
                    resultList.add(dlcnv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public String getEmailByMaNhanVien(String maNhanVien) {
        String sql = "SELECT Email FROM NhanVien WHERE MaNhanVien = ?";
        String email = null;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                email = rs.getString("Email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public List<DuLieuChung_NV> timKiemLichSu(String keyword) {
        String sql = "SELECT HD.MaHoatDong, HD.HanhDong, HD.ThoiGianThucHien, NV.MaNhanVien, NV.HoTen, NV.DiaChi, NV.SoDienThoai, NV.Email, NV.ChucVu, NV.BoPhan, NV.TrangThai, NV.GioiTinh, NV.TenDangNhap, NV.MatKhau, NV.VaiTro "
                + "FROM HoatDong HD "
                + "LEFT JOIN NhanVien NV ON HD.MaNhanVien = NV.MaNhanVien "
                + "WHERE (NV.IsDelete = 1 OR HD.HanhDong LIKE N'Xóa') AND (NV.MaNhanVien LIKE ? OR NV.HoTen LIKE ? OR NV.SoDienThoai LIKE ?)";

        List<DuLieuChung_NV> resultList = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String keywordPattern = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, keywordPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DuLieuChung_NV dlcnv = new DuLieuChung_NV();
                    dlcnv.setLsnv(new LichSuNhanVien(rs.getInt("MaHoatDong"), rs.getString("HanhDong"), rs.getString("ThoiGianThucHien"), rs.getString("MaNhanVien")));
                    resultList.add(dlcnv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<DuLieuChung_NV> filterRecords(boolean hoatDong, boolean nghiLam) {
        List<DuLieuChung_NV> resultList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM NHANVIEN WHERE IsDelete <> 1");

        // Xây dựng các điều kiện trong câu truy vấn SQL dựa trên trạng thái của các checkbox đã chọn
        if (hoatDong || nghiLam) {
            queryBuilder.append(" AND (");
            if (hoatDong) {
                queryBuilder.append(" TrangThai = N'Hoạt động' AND");
            }
            if (nghiLam) {
                queryBuilder.append(" TrangThai = N'Nghỉ làm' AND");
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
                DuLieuChung_NV dlcnv = new DuLieuChung_NV();
                dlcnv.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));
                resultList.add(dlcnv);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public NhanVien getNhanVienByMa(String maNhanVien) {
        sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ? OR TenDangNhap LIKE ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ps.setString(2, "%" + maNhanVien + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                return new NhanVien(
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("ChucVu"),
                        rs.getString("BoPhan"),
                        rs.getString("TrangThai"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getBoolean("VaiTro")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
