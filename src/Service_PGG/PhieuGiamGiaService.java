package Service_PGG;

import KetNoi.DBConnect;
import Model_PGG.DieuKienGiamGia;
import Model_PGG.DuLieuChung;
import Model_PGG.LichSu;
import Model_NhanVien.NhanVien;
import Model_PGG.PhieuGiamGia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utils.Auth;

public class PhieuGiamGiaService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<DuLieuChung> getList() {
        sql = "SELECT * FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.MaGiamGia = DKG.MaGiamGia "
                + "LEFT JOIN NhanVien NV ON PGG.MaNhanVien = NV.MaNhanVien "
                + "WHERE PGG.isDelete = 0 OR PGG.isDelete IS NULL ";
//    sql = "SELECT * FROM PhieuGiamGia PGG "
//            + "INNER JOIN LoaiGiamGia LGG ON PGG.PGG_ID = LGG.PGG_ID "
//            + "INNER JOIN DieuKienGiamGia DKG ON PGG.PGG_ID = DKG.PGG_ID "
//            + "LEFT JOIN NhanVien NV ON PGG.MaNhanVien = NV.MaNhanVien "
//            + "WHERE (PGG.isDelete = 0 OR PGG.isDelete IS NULL) AND NV.MaNhanVien = ?";

        List<DuLieuChung> listDLC = new ArrayList<>();

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            // Gán giá trị mã nhân viên từ Auth vào câu truy vấn
            //ps.setString(1, Auth.user.getMaNV());
            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung dlc = new DuLieuChung();
                dlc.setPgg(new PhieuGiamGia(rs.getString("MaGiamGia"), rs.getString("TenPhieu"), rs.getString("MaNhanVien"), rs.getString("MoTa")));
                dlc.setDkgg(new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("LoaiGiamGia"), rs.getInt("GiaTriGiam"), rs.getString("TrangThai"), rs.getString("NgayBD"), rs.getString("NgayKT"), rs.getInt("SoLuongTao"), rs.getInt("SoLuongCon"), rs.getString("MaGiamGia")));
                dlc.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));

                listDLC.add(dlc);
            }

            return listDLC;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public int insert(DuLieuChung dlc) {
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu giao dịch     
            String randomMaGiamGia = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
            sql = "INSERT INTO PhieuGiamGia (MaGiamGia, TenPhieu, MaNhanVien, MoTa) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, randomMaGiamGia); // Assign the generated random string to MaGiamGia
            ps.setString(2, dlc.getPgg().getTenPhieu());
            ps.setString(3, dlc.getPgg().getMaNV());
            ps.setString(4, dlc.getPgg().getMoTa());
            ps.executeUpdate();
            sql = "INSERT INTO DieuKienGiamGia (LoaiGiamGia, GiaTriGiam, TrangThai, NgayBD, NgayKT, SoLuongTao, SoLuongCon, MaGiamGia) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, dlc.getDkgg().getLoaiGiamGia());
            ps.setInt(2, dlc.getDkgg().getGiaTriGiam());
            ps.setString(3, dlc.getDkgg().getTrangThai());
            ps.setString(4, dlc.getDkgg().getNgayBD());
            ps.setString(5, dlc.getDkgg().getNgayKT());
            ps.setInt(6, dlc.getDkgg().getSoLuongTao());
            ps.setInt(7, dlc.getDkgg().getSoLuongCon());
            ps.setString(8, randomMaGiamGia);
            ps.executeUpdate();
            con.commit();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace(System.out);
            }
            return 0;
        }
    }

    public boolean update(DuLieuChung dlc) {
        String updatePGG = "UPDATE PhieuGiamGia SET TenPhieu = ?, MaNhanVien = ?, MoTa = ? WHERE MaGiamGia = ?";
        String updateDKGG = "UPDATE DieuKienGiamGia SET LoaiGiamGia = ?, GiaTriGiam = ?, TrangThai = ?, NgayBD = ?, NgayKT = ?, SoLuongTao = ?, SoLuongCon = ? WHERE MaGiamGia = ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psPGG = con.prepareStatement(updatePGG); PreparedStatement psDKGG = con.prepareStatement(updateDKGG)) {

            con.setAutoCommit(false);

            // Update PhieuGiamGia
            psPGG.setString(1, dlc.getPgg().getTenPhieu());
            psPGG.setString(2, dlc.getPgg().getMaNV());
            psPGG.setString(3, dlc.getPgg().getMoTa());
            psPGG.setString(4, dlc.getPgg().getMaGiamGia());
            psPGG.executeUpdate();

            // Update DieuKienGiamGia
            psDKGG.setString(1, dlc.getDkgg().getLoaiGiamGia());
            psDKGG.setInt(2, dlc.getDkgg().getGiaTriGiam());
            psDKGG.setString(3, dlc.getDkgg().getTrangThai());
            psDKGG.setString(4, dlc.getDkgg().getNgayBD());
            psDKGG.setString(5, dlc.getDkgg().getNgayKT());
            psDKGG.setInt(6, dlc.getDkgg().getSoLuongTao());
            psDKGG.setInt(7, dlc.getDkgg().getSoLuongCon());
            psDKGG.setString(8, dlc.getPgg().getMaGiamGia());
            psDKGG.executeUpdate();

            con.commit(); // Commit the transaction if all updates are successful
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public int xoa(DuLieuChung dlc) {
        String xoaPGG = "UPDATE PhieuGiamGia SET IsDelete = 1 WHERE MaGiamGia = ?";
        String insertLichSu = "INSERT INTO LichSuPhieuGiamGia (MaGiamGia, MaDieuKienGiamGia, HanhDong, ThoiGian) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psXoaPGG = con.prepareStatement(xoaPGG); PreparedStatement psInsertLichSu = con.prepareStatement(insertLichSu)) {

            con.setAutoCommit(false);

            psXoaPGG.setString(1, dlc.getPgg().getMaGiamGia());
            int deletedRows = psXoaPGG.executeUpdate();

            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentTime);

            psInsertLichSu.setString(1, dlc.getPgg().getMaGiamGia());
            psInsertLichSu.setInt(2, dlc.getDkgg().getDieuKienGiamGiaID());
            psInsertLichSu.setString(3, "Xóa");
            psInsertLichSu.setTimestamp(4, currentTimestamp);
            psInsertLichSu.executeUpdate();

            con.commit(); // Commit giao dịch nếu xóa thành công
            return deletedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<DuLieuChung> getLichSu() {
        sql = "SELECT * FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.MaGiamGia = DKG.MaGiamGia "
                + "INNER JOIN LICHSUPHIEUGIAMGIA ON PGG.MaGiamGia = LICHSUPHIEUGIAMGIA.MaGiamGia "
                + "WHERE PGG.isDelete = 1 OR LICHSUPHIEUGIAMGIA.HANHDONG LIKE N'Xoá'";
        List<DuLieuChung> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung dlc = new DuLieuChung();
                dlc.setLs(new LichSu(rs.getInt("LichSuID"), rs.getString("MaGiamGia"), rs.getString("HanhDong"), rs.getString("ThoiGian")));
                listDLC.add(dlc);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   public List<DuLieuChung> filterRecords(boolean giamPhanTram, boolean giamTien, boolean chuaden, boolean dangdienra, boolean hethan) {
    List<DuLieuChung> resultList = new ArrayList<>();
    StringBuilder queryBuilder = new StringBuilder("SELECT * FROM PhieuGiamGia PGG "
            + "INNER JOIN DieuKienGiamGia DKG ON PGG.MaGiamGia = DKG.MaGiamGia "
            + "INNER JOIN NhanVien ON PGG.MaNhanVien = NhanVien.MaNhanVien "
            + "WHERE 1 = 1 AND (PGG.IsDelete = 0 OR PGG.IsDelete IS NULL) ");

    // Xây dựng các điều kiện trong câu truy vấn SQL dựa trên trạng thái của các checkbox đã chọn
    if (giamPhanTram || giamTien || chuaden || dangdienra || hethan) {
        queryBuilder.append(" AND (");
        boolean addedCondition = false;

        if (giamPhanTram) {
            queryBuilder.append(" DKG.LoaiGiamGia = N'Giảm phần trăm' OR");
            addedCondition = true;
        }

        if (giamTien) {
            queryBuilder.append(" DKG.LoaiGiamGia = N'Giảm tiền' OR");
            addedCondition = true;
        }

        if (chuaden) {
            queryBuilder.append(" DKG.TrangThai = N'Chưa đến' OR");
            addedCondition = true;
        }

        if (dangdienra) {
            queryBuilder.append(" DKG.TrangThai = N'Đang chạy' OR");
            addedCondition = true;
        }

        if (hethan) {
            queryBuilder.append(" DKG.TrangThai = N'Hết hạn' OR");
            addedCondition = true;
        }

        if (addedCondition) {
            // Xóa đi phần "OR" cuối cùng không cần thiết
            queryBuilder.delete(queryBuilder.length() - 3, queryBuilder.length());
            queryBuilder.append(")");
        }
    }

    try {
        con = DBConnect.getConnection();
        ps = con.prepareStatement(queryBuilder.toString());
        rs = ps.executeQuery();
        while (rs.next()) {
            DuLieuChung dlc = new DuLieuChung();
            dlc.setPgg(new PhieuGiamGia(rs.getString("MaGiamGia"), rs.getString("TenPhieu"), rs.getString("MaNhanVien"), rs.getString("MoTa")));
            dlc.setDkgg(new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("LoaiGiamGia"), rs.getInt("GiaTriGiam"), rs.getString("TrangThai"), rs.getString("NgayBD"), rs.getString("NgayKT"), rs.getInt("SoLuongTao"), rs.getInt("SoLuongCon"), rs.getString("MaGiamGia")));
            dlc.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));
            resultList.add(dlc);
        }
        return resultList;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    public int softDelete(String magiamgia) {
        String khoiphucPGG = "UPDATE PhieuGiamGia SET IsDelete = 0 WHERE MaGiamGia = ?";
        String xoaLSGG = "DELETE FROM LichSuPhieuGiamGia WHERE MaGiamGia = ?";
        String xoaDKGG = "DELETE FROM DieuKienGiamGia WHERE MaGiamGia = ?";
        String xoaPGG = "DELETE FROM PhieuGiamGia WHERE MaGiamGia = ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement psKhoiphucPGG = con.prepareStatement(khoiphucPGG); PreparedStatement psXoaLSGG = con.prepareStatement(xoaLSGG); PreparedStatement psXoaDKGG = con.prepareStatement(xoaDKGG); PreparedStatement psXoaPGG = con.prepareStatement(xoaPGG)) {

            con.setAutoCommit(false);

            psKhoiphucPGG.setString(1, magiamgia);
            psKhoiphucPGG.executeUpdate();

            psXoaLSGG.setString(1, magiamgia);
            psXoaLSGG.executeUpdate();

            psXoaDKGG.setString(1, magiamgia);
            psXoaDKGG.executeUpdate();

            psXoaPGG.setString(1, magiamgia);
            psXoaPGG.executeUpdate();

            con.commit(); // Commit giao dịch nếu xóa thành công
            return 1; // Trả về 1 để chỉ ra xóa thành công
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public int restorePhieuGiamGia(String ma) {
        String restorePGG = "UPDATE PhieuGiamGia SET isDelete = 0 WHERE MaGiamGia = ?";
        String xoaLichSu = "DELETE FROM LichSuPhieuGiamGia WHERE MaGiamGia = ?";
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

    public boolean checkTrungMa(String maGiamGia) {
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS count FROM PhieuGiamGia WHERE MaGiamGia = ?")) {

            ps.setString(1, maGiamGia);
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

    public List<DuLieuChung> hienThiDaXoa(int ma) {
        sql = "SELECT * FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.PGG_ID = DKG.PGG_ID "
                + "INNER JOIN LICHSUPHIEUGIAMGIA LSPGG ON PGG.PGG_ID = LSPGG.PGG_ID " // Thêm khoảng trắng ở đây
                + "WHERE PGG.isDelete = 1 "; // Thêm khoảng trắng ở đây

        List<DuLieuChung> listDLC = new ArrayList<>();
        listDLC.clear();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ma); // Giả sử lichSuID là biến bạn truyền vào

            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung dlc = new DuLieuChung();
                dlc.setPgg(new PhieuGiamGia(rs.getString("MaGiamGia"), rs.getString("TenPhieu"), rs.getString("MaNhanVien"), rs.getString("MoTa")));
                dlc.setDkgg(new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("LoaiGiamGia"), rs.getInt("GiaTriGiam"), rs.getString("TrangThai"), rs.getString("NgayBD"), rs.getString("NgayKT"), rs.getInt("SoLuongTao"), rs.getInt("SoLuongCon"), rs.getString("MaGiamGia")));
                listDLC.add(dlc);
            }

            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DuLieuChung getTimKiem(String ma) {
        String maNV = Auth.user.getMaNhanVien(); // Lấy mã nhân viên từ Auth

        sql = "SELECT PGG.MaGiamGia, PGG.TenPhieu, PGG.MaNhanVien, PGG.MoTa, "
                + "DKG.DKGG_ID,DKG.LoaiGiamGia, DKG.GiaTriGiam, DKG.TrangThai, DKG.NgayBD, DKG.NgayKT, DKG.SoLuongTao, DKG.SoLuongCon "
                + "FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.MaGiamGia = DKG.MaGiamGia "
                + "INNER JOIN LichSuPhieuGiamGia LSPGG ON PGG.MaGiamGia = LSPGG.MaGiamGia "
                + "WHERE PGG.MaGiamGia LIKE ?"; // Thêm điều kiện mã nhân viên

        DuLieuChung dlc = null; // Khởi tạo biến dlc

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + ma + "%"); // Gán giá trị mã giảm giá vào truy vấn           // Gán giá trị mã nhân viên vào truy vấn

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Sử dụng if để lấy thông tin nếu có kết quả
                    dlc = new DuLieuChung();
                    dlc.setPgg(new PhieuGiamGia(rs.getString("MaGiamGia"), rs.getString("TenPhieu"), rs.getString("MaNhanVien"), rs.getString("MoTa")));
                    dlc.setDkgg(new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("LoaiGiamGia"), rs.getInt("GiaTriGiam"), rs.getString("TrangThai"), rs.getString("NgayBD"), rs.getString("NgayKT"), rs.getInt("SoLuongTao"), rs.getInt("SoLuongCon"), rs.getString("MaGiamGia")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dlc;
    }

    public List<DuLieuChung> getSearch(String maGiamGia) {
        sql = "SELECT * FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.MaGiamGia = DKG.MaGiamGia "
                + "LEFT JOIN NhanVien NV ON PGG.MaNhanVien = NV.MaNhanVien "
                + "WHERE (PGG.isDelete = 0 OR PGG.isDelete IS NULL) AND PGG.MaGiamGia LIKE ?";

        List<DuLieuChung> listDLC = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + maGiamGia + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                DuLieuChung dlc = new DuLieuChung();
                dlc.setPgg(new PhieuGiamGia(rs.getString("MaGiamGia"), rs.getString("TenPhieu"), rs.getString("MaNhanVien"), rs.getString("MoTa")));
                dlc.setDkgg(new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("LoaiGiamGia"), rs.getInt("GiaTriGiam"), rs.getString("TrangThai"), rs.getString("NgayBD"), rs.getString("NgayKT"), rs.getInt("SoLuongTao"), rs.getInt("SoLuongCon"), rs.getString("MaGiamGia")));
                dlc.setNv(new NhanVien(rs.getString("MaNhanVien"), rs.getString("HoTen"), rs.getString("DiaChi"), rs.getString("SoDienThoai"), rs.getString("Email"), rs.getString("ChucVu"), rs.getString("BoPhan"), rs.getString("TrangThai"), rs.getBoolean("GioiTinh"), rs.getString("TenDangNhap"), rs.getString("MatKhau"), rs.getBoolean("VaiTro")));
                listDLC.add(dlc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các resource
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
        return listDLC;
    }

    public List<DuLieuChung> getSearchLichSu(String ma) {
        sql = "SELECT * FROM PhieuGiamGia PGG "
                + "INNER JOIN DieuKienGiamGia DKG ON PGG.PGG_ID = DKG.PGG_ID "
                + "INNER JOIN LICHSUPHIEUGIAMGIA ON PGG.PGG_ID = LICHSUPHIEUGIAMGIA.PGG_ID "
                + "WHERE PGG.isDelete = 1 AND LichSuPhieuGiamGia.MaGiamGia LIKE ? ";
        List<DuLieuChung> listDLC = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + ma + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                DuLieuChung dlc = new DuLieuChung();
                dlc.setLs(new LichSu(rs.getInt("LichSuID"), rs.getString("MaGiamGia"), rs.getString("HanhDong"), rs.getString("ThoiGian")));
                listDLC.add(dlc);
            }
            return listDLC;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> thongke() {
        List<Object[]> resultList = new ArrayList<>();
        String sql = "SELECT "
                + "DATEPART(YEAR, DKGG.NgayBD) AS Nam, "
                + "DATEPART(MONTH, DKGG.NgayBD) AS Thang, "
                + "DATEPART(DAY, DKGG.NgayBD) AS Ngay, "
                + "COUNT(DISTINCT DKGG.MaGiamGia) AS SoPhieuTao, "
                + "COUNT(DISTINCT LSGG.MaGiamGia) AS SoPhieuDung, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia = N'Giảm tiền' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongTienGiam, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia = N'Giảm phần trăm' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongPhanTramGiam "
                + "FROM DIEUKIENGIAMGIA DKGG "
                + "LEFT JOIN LICHSUPHIEUGIAMGIA LSGG ON DKGG.MaGiamGia = LSGG.MaGiamGia "
                + "GROUP BY DATEPART(YEAR, DKGG.NgayBD), DATEPART(MONTH, DKGG.NgayBD), DATEPART(DAY, DKGG.NgayBD) "
                + "ORDER BY Nam, Thang, Ngay";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("Nam");
                row[1] = rs.getInt("Thang");
                row[2] = rs.getInt("Ngay");
                row[3] = rs.getInt("SoPhieuTao");
                row[4] = rs.getInt("SoPhieuDung");
                row[5] = rs.getInt("TongTienGiam");
                row[6] = rs.getInt("TongPhanTramGiam");
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Object[]> getDataForSelectedDay(int selectedDay) {
        List<Object[]> resultList = new ArrayList<>();
        String sql = "SELECT "
                + "DATEPART(YEAR, DKGG.NgayBD) AS Nam, "
                + "DATEPART(MONTH, DKGG.NgayBD) AS Thang, "
                + "DATEPART(DAY, DKGG.NgayBD) AS Ngay, "
                + "COUNT(DISTINCT DKGG.MaGiamGia) AS SoPhieuTao, "
                + "COUNT(DISTINCT CASE WHEN LSGG.MaGiamGia IS NOT NULL THEN DKGG.MaGiamGia END) AS SoPhieuDung, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm tiền' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongTienGiam, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm phần trăm' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongPhanTramGiam "
                + "FROM DIEUKIENGIAMGIA DKGG "
                + "LEFT JOIN LICHSUPHIEUGIAMGIA LSGG ON DKGG.MaGiamGia = LSGG.MaGiamGia "
                + "WHERE DATEPART(DAY, DKGG.NgayBD) = ? " // Lọc theo ngày được chọn
                + "GROUP BY DATEPART(YEAR, DKGG.NgayBD), DATEPART(MONTH, DKGG.NgayBD), DATEPART(DAY, DKGG.NgayBD) "
                + "ORDER BY Nam, Thang, Ngay";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, selectedDay); // Gán giá trị ngày được chọn vào câu truy vấn
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7]; // Thêm 3 cột mới để lưu trữ ngày, tháng, năm
                row[0] = rs.getInt("Nam");
                row[1] = rs.getInt("Thang");
                row[2] = rs.getInt("Ngay");
                row[3] = rs.getInt("SoPhieuTao");
                row[4] = rs.getInt("SoPhieuDung");
                row[5] = rs.getInt("TongTienGiam");
                row[6] = rs.getInt("TongPhanTramGiam");
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Object[]> getDataForSelectedMonth(int selectedMonth) {
        List<Object[]> resultList = new ArrayList<>();

        String sql = "SELECT "
                + "DATEPART(YEAR, DKGG.NgayBD) AS Nam, "
                + "DATEPART(MONTH, DKGG.NgayBD) AS Thang, "
                + "DATEPART(DAY, DKGG.NgayBD) AS Ngay, "
                + "COUNT(DISTINCT DKGG.MaGiamGia) AS SoPhieuTao, "
                + "COUNT(DISTINCT CASE WHEN LSGG.MaGiamGia IS NOT NULL THEN DKGG.MaGiamGia END) AS SoPhieuDung, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm tiền' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongTienGiam, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm phần trăm' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongPhanTramGiam "
                + "FROM DIEUKIENGIAMGIA DKGG "
                + "LEFT JOIN LICHSUPHIEUGIAMGIA LSGG ON DKGG.MaGiamGia = LSGG.MaGiamGia "
                + "WHERE DATEPART(MONTH, DKGG.NgayBD) = ? "
                + "GROUP BY DATEPART(YEAR, DKGG.NgayBD), DATEPART(MONTH, DKGG.NgayBD), DATEPART(DAY, DKGG.NgayBD)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, selectedMonth);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("Nam");
                row[1] = rs.getInt("Thang");
                row[2] = rs.getInt("Ngay");
                row[3] = rs.getInt("SoPhieuTao");
                row[4] = rs.getInt("SoPhieuDung");
                row[5] = rs.getInt("TongTienGiam");
                row[6] = rs.getInt("TongPhanTramGiam");
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Object[]> getDataForSelectedYear(int selectedYear) {
        List<Object[]> resultList = new ArrayList<>();

        String sql = "SELECT "
                + "DATEPART(YEAR, DKGG.NgayBD) AS Nam, "
                + "DATEPART(MONTH, DKGG.NgayBD) AS Thang, "
                + "DATEPART(DAY, DKGG.NgayBD) AS Ngay, "
                + "COUNT(DISTINCT DKGG.MaGiamGia) AS SoPhieuTao, "
                + "COUNT(DISTINCT CASE WHEN LSGG.MaGiamGia IS NOT NULL THEN DKGG.MaGiamGia END) AS SoPhieuDung, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm tiền' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongTienGiam, "
                + "SUM(CASE WHEN DKGG.LoaiGiamGia LIKE N'Giảm phần trăm' THEN DKGG.GiaTriGiam ELSE 0 END) AS TongPhanTramGiam "
                + "FROM DIEUKIENGIAMGIA DKGG "
                + "LEFT JOIN LICHSUPHIEUGIAMGIA LSGG ON DKGG.MaGiamGia = LSGG.MaGiamGia "
                + "WHERE DATEPART(YEAR, DKGG.NgayBD) = ? "
                + "GROUP BY DATEPART(YEAR, DKGG.NgayBD), DATEPART(MONTH, DKGG.NgayBD), DATEPART(DAY, DKGG.NgayBD)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, selectedYear);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("Nam");
                row[1] = rs.getInt("Thang");
                row[2] = rs.getInt("Ngay");
                row[3] = rs.getInt("SoPhieuTao");
                row[4] = rs.getInt("SoPhieuDung");
                row[5] = rs.getInt("TongTienGiam");
                row[6] = rs.getInt("TongPhanTramGiam");
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
