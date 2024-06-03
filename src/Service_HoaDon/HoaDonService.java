package Service_HoaDon;

import KetNoi.DBConnect;
import Model_HoaDon.HoaDon;
import Model_HoaDon.HoaDonChiTiet;
import Model_HoaDon.SanPham_HD;
import Model_PGG.DieuKienGiamGia;
import Model_PGG.DuLieuChung;
import Model_PGG.PhieuGiamGia;
import Model_SanPham.BienTheSanPham;
import Model_SanPham.SanPham;
import View.HoaDonView;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDonService {

    Connection con = null;
    PreparedStatement pr = null;
    ResultSet rs = null;
    String sql = "";

    public List<SanPham_HD> getSP() {
        sql = "SELECT S.MASP, S.LOAI, S.TONKHO, B.MABT, B.TUOI, B.MAULONG, B.CHIEUCAO, B.NGAYSINH, B.GIOITINH, B.CANNANG, B.GIABAN, B.TIEM ,B.Hinh "
                + "FROM SANPHAM S "
                + "JOIN BIENTHESP B ON S.MASP = B.MASP WHERE B.IsDelete = 0";

        List<SanPham_HD> listHD = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                SanPham_HD hd = new SanPham_HD();
                SanPham sp = new SanPham(rs.getString("MASP"), rs.getString("LOAI"), rs.getInt("TONKHO"));
                BienTheSanPham bt = new BienTheSanPham(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("Hinh"));
                hd.setBtsp(bt);
                hd.setSp(sp);
                listHD.add(hd);
            }
            return listHD;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public List<HoaDon> getHoaDon() {
        sql = "SELECT * FROM HoaDon WHERE TrangThai LIKE N'Chưa thanh toán'";
        List<HoaDon> listHoaDon = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon(rs.getString("MaHoaDon"), rs.getString("TrangThai"), rs.getString("MaKhachHang"), rs.getString("MaNhanVien"), rs.getString("NgayTao"), rs.getString("MaDGG"), rs.getString("MaGiamGia"), rs.getInt("GiaTriGiamDGG"), rs.getInt("GiaTriGiamPGG"), rs.getBigDecimal("SoTienGiam"), rs.getBigDecimal("TongTien"), rs.getBoolean("VanChuyen"), rs.getBigDecimal("SoTienThanhToan"));
                listHoaDon.add(hoaDon);
            }
            return listHoaDon;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public List<HoaDonChiTiet> getHoaDonChiTiet() {
        sql = "SELECT hdct.* "
                + "FROM HoaDonChiTiet hdct "
                + "INNER JOIN HoaDon hd ON hdct.MaHoaDon = hd.MaHoaDon "
                + "WHERE hd.TrangThai = N'Chưa thanh toán'";
        List<HoaDonChiTiet> listHoaDon = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getInt("MaHDCT"), rs.getString("MaHoaDon"), rs.getString("MaSP"), rs.getInt("SoLuong"), rs.getBigDecimal("TongTien"));
                listHoaDon.add(hdct);
            }
            return listHoaDon;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public List<DuLieuChung> findHoaDonByMaGiamGia(String maGiamGia) {
        sql = "SELECT pgg.MaGiamGia AS PGG_MaGiamGia, pgg.TenPhieu AS PGG_TenPhieu, pgg.MaNhanVien AS PGG_MaNhanVien, pgg.MoTa AS PGG_MoTa, "
                + "dkgg.DKGG_ID, dkgg.LoaiGiamGia AS DKGG_LoaiGiamGia, dkgg.GiaTriGiam AS DKGG_GiaTriGiam, dkgg.TrangThai AS DKGG_TrangThai, "
                + "dkgg.NgayBD AS DKGG_NgayBD, dkgg.NgayKT AS DKGG_NgayKT, dkgg.SoLuongTao AS DKGG_SoLuongTao, dkgg.SoLuongCon AS DKGG_SoLuongCon "
                + "FROM PhieuGiamGia pgg "
                + "INNER JOIN DieuKienGiamGia dkgg ON pgg.MaGiamGia = dkgg.MaGiamGia "
                + "WHERE dkgg.MaGiamGia = ? AND (dkgg.LoaiGiamGia = 'Giảm phần trăm' OR dkgg.LoaiGiamGia = 'Giảm tiền')";
        List<DuLieuChung> listPGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, maGiamGia);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    DuLieuChung dlc = new DuLieuChung();
                    PhieuGiamGia phieuGiamGia = new PhieuGiamGia(rs.getString("PGG_MaGiamGia"), rs.getString("PGG_TenPhieu"), rs.getString("PGG_MaNhanVien"), rs.getString("PGG_MoTa"));
                    DieuKienGiamGia dieuKienGiamGia = new DieuKienGiamGia(rs.getInt("DKGG_ID"), rs.getString("DKGG_LoaiGiamGia"), rs.getInt("DKGG_GiaTriGiam"), rs.getString("DKGG_TrangThai"), rs.getString("DKGG_NgayBD"), rs.getString("DKGG_NgayKT"), rs.getInt("DKGG_SoLuongTao"), rs.getInt("DKGG_SoLuongCon"), rs.getString("PGG_MaGiamGia"));
                    dlc.setPgg(phieuGiamGia);
                    dlc.setDkgg(dieuKienGiamGia);
                    listPGG.add(dlc);
                }
            }
            return listPGG;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return new ArrayList<>(); // Trả về danh sách rỗng để xử lý tiếp
        }
    }

    public HoaDon getHoaDonByMa(String maHoaDon) {
        sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, maHoaDon);
            rs = pr.executeQuery();

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

    public List<Map<String, Object>> getDiscountInfo(String pggId) {
        List<Map<String, Object>> discountInfo = new ArrayList<>();
        try {
            // Viết truy vấn SQL để lấy thông tin giảm giá từ bảng DieuKienGiamGia
            String query = "SELECT * FROM DIEUKIENGIAMGIA WHERE MaGiamGia = ? ";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, pggId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Lấy thông tin giảm giá và thêm vào danh sách
                Map<String, Object> discount = new HashMap<>();
                discount.put("LoaiGiamGia", resultSet.getString("LoaiGiamGia"));
                discount.put("GiaTriGiam", resultSet.getInt("GiaTriGiam"));
                discountInfo.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return discountInfo;
    }

    public List<Map<String, Object>> getDiscountDetails(String maGiamGia) {
        List<Map<String, Object>> discountDetailsList = new ArrayList<>();
        try {
            con = DBConnect.getConnection(); // Kết nối đến cơ sở dữ liệu

            String query = "SELECT dkgg.LoaiGiamGia, dkgg.GiaTriGiam "
                    + "FROM PhieuGiamGia pgg "
                    + "INNER JOIN DieuKienGiamGia dkgg ON pgg.MaGiamGia = dkgg.MaGiamGia "
                    + "WHERE pgg.MaGiamGia = ? AND TrangThai LIKE N'Đang chạy'";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, maGiamGia);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> discountDetails = new HashMap<>();

                // Lấy thông tin loại giảm giá và giá trị giảm từ cơ sở dữ liệu
                String loaiGiamGia = resultSet.getString("LoaiGiamGia");
                int giaTriGiam = resultSet.getInt("GiaTriGiam");

                discountDetails.put("LoaiGiamGia", loaiGiamGia);
                discountDetails.put("GiaTriGiam", giaTriGiam);

                discountDetailsList.add(discountDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discountDetailsList;
    }

    public int updateHoaDon(HoaDon hoaDon) {
        sql = "UPDATE HoaDon SET TrangThai = ?, MaKhachHang = ?, MaNhanVien = ?, NgayTao = ?, MaDGG = ?, MaGiamGia = ?, GiaTriGiamDGG = ?, GiaTriGiamPGG = ?, SoTienGiam = ?, TongTien = ?, VanChuyen = ?, SoTienThanhToan = ? WHERE MaHoaDon = ?";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);

            pr.setString(1, "Đã thanh toán"); // Cập nhật trạng thái thành "Đã thanh toán"
            pr.setString(2, hoaDon.getMaKhachHang());
            pr.setString(3, hoaDon.getMaNhanVien());

            // Chuyển đổi chuỗi NgayTao thành java.sql.Timestamp
            Timestamp ngayTaoTimestamp = Timestamp.valueOf(hoaDon.getNgayTao());
            pr.setTimestamp(4, ngayTaoTimestamp);

            if (hoaDon.getMaDGG() != null && !hoaDon.getMaDGG().isEmpty()) {
                pr.setString(5, hoaDon.getMaDGG());
            } else {
                pr.setNull(5, Types.VARCHAR); // Nếu maDGG là null hoặc rỗng, đặt giá trị null cho trường này
            }

            if (hoaDon.getMaGiamGia() != null && !hoaDon.getMaGiamGia().isEmpty()) {
                pr.setString(6, hoaDon.getMaGiamGia());
            } else {
                pr.setNull(6, Types.VARCHAR); // Nếu maGiamGia là null hoặc rỗng, đặt giá trị null cho trường này
            }

            if (hoaDon.getGiaTriGiamDGG() != 0) {
                pr.setInt(7, hoaDon.getGiaTriGiamDGG());
            } else {
                pr.setInt(7, 0); // Nếu giaTriGiam là null hoặc 0, đặt giá trị mặc định là 0 cho trường này
            }

            if (hoaDon.getGiaTriGiamPGG() != 0) {
                pr.setInt(8, hoaDon.getGiaTriGiamPGG());
            } else {
                pr.setInt(8, 0); // Nếu giaTriGiam là null hoặc 0, đặt giá trị mặc định là 0 cho trường này
            }

            if (hoaDon.getSoTienGiam() != null) {
                pr.setBigDecimal(9, hoaDon.getSoTienGiam());
            } else {
                pr.setBigDecimal(9, BigDecimal.ZERO); // Nếu soTienGiam là null, đặt giá trị mặc định là 0 cho trường này
            }

            if (hoaDon.getTongTien() != null) {
                pr.setBigDecimal(10, hoaDon.getTongTien());
            } else {
                pr.setNull(10, Types.DECIMAL); // Nếu tongTien là null, đặt giá trị null cho trường này
            }

            pr.setBoolean(11, hoaDon.isVanChuyen()); // Gán giá trị vào truy vấn SQL          
            if (hoaDon.getSoTienThanhToan() != null) {
                pr.setBigDecimal(12, hoaDon.getSoTienThanhToan());
            } else {
                pr.setNull(12, Types.DECIMAL); // Nếu soTienThanhToan là null, đặt giá trị null cho trường này
            }

            pr.setString(13, hoaDon.getMaHoaDon());
            return pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int insertHoaDon(HoaDon hoaDon) {
        sql = "INSERT INTO HoaDon (MaHoaDon, TrangThai) VALUES (?, ?)";
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);

            pr.setString(1, hoaDon.getMaHoaDon());
            pr.setString(2, hoaDon.getTrangThai());
            return pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return 0;
        }

    }

    public int insertAllHoaDonChiTiet(List<HoaDonChiTiet> hoaDonChiTietList) {
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu giao dịch

            String sql = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaBT, SoLuong, TongTien) VALUES (?, ?, ?, ?)";
            pr = con.prepareStatement(sql);

            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                String maHoaDon = hoaDonChiTiet.getMaHoaDon();
                String maSP = hoaDonChiTiet.getMaBT();
                int soLuong = hoaDonChiTiet.getSoLuong();
                BigDecimal tongTien = hoaDonChiTiet.getTongTien();

                if (soLuong > 0) { // Chỉ chèn dữ liệu nếu số lượng lớn hơn 0
                    pr.setString(1, maHoaDon);
                    pr.setString(2, maSP);
                    pr.setInt(3, soLuong);
                    pr.setBigDecimal(4, tongTien);
                    pr.addBatch();
                }
            }

            int[] updateCounts = pr.executeBatch();

            // Kiểm tra và commit giao dịch nếu không có lỗi xảy ra
            boolean allSuccess = true;
            for (int updateCount : updateCounts) {
                if (updateCount == PreparedStatement.EXECUTE_FAILED) {
                    allSuccess = false;
                    break;
                }
            }

            if (allSuccess) {
                con.commit(); // Kết thúc giao dịch (commit tất cả thay đổi)
                con.setAutoCommit(true); // Quay về chế độ mặc định tự động commit
                return updateCounts.length; // Trả về số lượng dòng đã chèn thành công
            } else {
                con.rollback(); // Rollback nếu có lỗi xảy ra trong batch insert
                con.setAutoCommit(true); // Quay về chế độ tự động commit
                return 0; // Trả về 0 để xử lý lỗi
            }
        } catch (SQLException e) {
            // Xử lý lỗi và rollback giao dịch nếu có
            try {
                if (con != null) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return 0; // Trả về 0 để xử lý lỗi
        } finally {
            // Đóng PreparedStatement sau khi sử dụng xong
            if (pr != null) {
                try {
                    pr.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<HoaDon> getHoaDonDaThanhToan() {
        sql = "SELECT * FROM HoaDon WHERE TrangThai LIKE N'Đã thanh toán'";
        List<HoaDon> listHoaDon = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
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
                listHoaDon.add(hoaDon);
            }
            return listHoaDon;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public List<HoaDonChiTiet> getHoaDonChiTietByMaHoaDon(String maHoaDon) {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maHoaDon);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int maHDCT = resultSet.getInt("MaHDCT");
                    String maHD = resultSet.getString("MaHoaDon");
                    String maSP = resultSet.getString("MaBT");
                    int soLuong = resultSet.getInt("SoLuong");
                    BigDecimal tongTien = resultSet.getBigDecimal("TongTien");

                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(maHDCT, maHD, maSP, soLuong, tongTien);
                    hoaDonChiTietList.add(hoaDonChiTiet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDonChiTietList;
    }

    public DieuKienGiamGia findDiscountStatusAndQuantity(String maGiamGia) {
        String sql = "SELECT * FROM DIEUKIENGIAMGIA WHERE MaGiamGia = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maGiamGia);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int dkId = resultSet.getInt("DKGG_ID");
                    String loaiGiamGia = resultSet.getString("LoaiGiamGia");
                    int giaTriGiam = resultSet.getInt("GiaTriGiam");
                    String trangThai = resultSet.getString("TrangThai");
                    Timestamp ngayBD = resultSet.getTimestamp("NgayBD");
                    Timestamp ngayKT = resultSet.getTimestamp("NgayKT");
                    int soLuongTao = resultSet.getInt("SoLuongTao");
                    int soLuongCon = resultSet.getInt("SoLuongCon");

                    DieuKienGiamGia dieuKienGiamGia = new DieuKienGiamGia(dkId, loaiGiamGia, giaTriGiam, trangThai, ngayBD.toString(), ngayKT.toString(), soLuongTao, soLuongCon, maGiamGia);
                    return dieuKienGiamGia;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public SanPham findDiscountStatusAndQuantityTK(String maTK) {
        String sql = "SELECT * FROM SanPham WHERE MaSP = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maTK);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SanPham sp = new SanPham();
                    sp.setMaSp(resultSet.getString(1));
                    sp.setLoai(resultSet.getString(2));
                    sp.setTonKho(resultSet.getInt(3));
                    return sp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void decreaseQuantityByOne(String maGiamGia) {
        DieuKienGiamGia discount = findDiscountStatusAndQuantity(maGiamGia);
        if (discount != null && discount.getSoLuongCon() > 0) {
            int newQuantity = discount.getSoLuongCon() - 1;
            try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DIEUKIENGIAMGIA SET SoLuongCon = ? WHERE MaGiamGia = ?")) {
                String dkggId = String.valueOf(maGiamGia);
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setString(2, dkggId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void decreaseQuantityByOneSP(String maSP) {
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(TonKho) AS TotalStock FROM SanPham WHERE MaSP = ?")) {

            preparedStatement.setString(1, maSP);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int totalStock = resultSet.getInt("TotalStock");

                if (totalStock > 0) {
                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE SanPham SET TonKho = ? WHERE MaSP = ?")) {
                        updateStatement.setInt(1, totalStock - 1);
                        updateStatement.setString(2, maSP);

                        int rowsUpdated = updateStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Quantity decremented successfully for product: " + maSP);
                        } else {
                            System.out.println("No quantity updated for product: " + maSP);
                        }
                    }
                } else {
                    System.out.println("The quantity of product: " + maSP + " is already 0.");
                }
            } else {
                System.out.println("Product: " + maSP + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void deleteVariantsSP(List<String> maBienTheList) {
        if (maBienTheList == null || maBienTheList.isEmpty()) {
            return; // Return if the list is null or empty
        }

        String deleteHDCTQuery = "UPDATE HoaDonChiTiet SET IsDeleted = 1 WHERE MABT = ?";
        String deleteHotroQuery = "UPDATE HOTROQUANLY SET IsDelete = 1 WHERE MABT = ?";
        String deleteVariantQuery = "UPDATE BIENTHESP SET IsDelete = 1 WHERE MABT = ?";

        try (Connection connection = DBConnect.getConnection()) {
            try (PreparedStatement deleteHDCTStatement = connection.prepareStatement(deleteHDCTQuery); PreparedStatement deleteHotroStatement = connection.prepareStatement(deleteHotroQuery); PreparedStatement deleteVariantStatement = connection.prepareStatement(deleteVariantQuery)) {

                connection.setAutoCommit(false); // Start transaction

                for (String maBienThe : maBienTheList) {
                    deleteHDCTStatement.setString(1, maBienThe);
                    deleteHDCTStatement.addBatch();

                    deleteHotroStatement.setString(1, maBienThe);
                    deleteHotroStatement.addBatch();

                    deleteVariantStatement.setString(1, maBienThe);
                    deleteVariantStatement.addBatch();
                }

                deleteHDCTStatement.executeBatch();
                deleteHotroStatement.executeBatch();
                deleteVariantStatement.executeBatch();

                connection.commit(); // Commit transaction

            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
                connection.rollback(); // Rollback if an exception occurs
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            // Handle database connection exception
        }
    }

    public List<Map<String, Object>> getDiscountDetailsForLichSu(String maGiamGia) {
        List<Map<String, Object>> discountInfo = new ArrayList<>();

        // Thực hiện truy vấn cơ sở dữ liệu để lấy thông tin giảm giá từ mã giảm giá
        String sql = "SELECT * FROM DieuKienGiamGia WHERE MaGiamGia = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maGiamGia);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Lấy thông tin từ kết quả truy vấn và thêm vào danh sách discountInfo
                Map<String, Object> discount = new HashMap<>();
                discount.put("LoaiGiamGia", resultSet.getString("LoaiGiamGia"));
                discount.put("GiaTriGiam", resultSet.getInt("GiaTriGiam"));
                // Thêm các thông tin khác nếu cần

                discountInfo.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discountInfo;
    }

    public String getGiaTriGiamFromDatabase(String maGiamGia) {
        String giaTriGiam = ""; // Giá trị mặc định hoặc thông báo nếu không tìm thấy

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT GiaTriGiam FROM DIEUKIENGIAMGIA WHERE MaGiamGia = ?")) {

            preparedStatement.setString(1, maGiamGia);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    giaTriGiam = resultSet.getString("GiaTriGiam");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý hoặc báo lỗi khi có lỗi truy vấn cơ sở dữ liệu
        }

        return giaTriGiam;
    }

    public String getLoaiGiamGiaByMa(String maGiamGia) {
        String loaiGiamGia = "";
        sql = "SELECT LoaiGiamGia FROM DieuKienGiamGia WHERE MaGiamGia = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maGiamGia);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loaiGiamGia = resultSet.getString("LoaiGiamGia");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out); // Xử lý hoặc báo lỗi khi có lỗi truy vấn cơ sở dữ liệu
        }
        return loaiGiamGia;
    }

    public String getDiscountTypeByMaGiamGia(String maGiamGia) {
        String sql = "SELECT LoaiGiamGia FROM DieuKienGiamGia WHERE MaGiamGia = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, maGiamGia);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("LoaiGiamGia");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return ""; // Trả về rỗng nếu không tìm thấy loại giảm giá
    }

    public boolean deleteHoaDonAndChiTietByMaHoaDon(String maHoaDon) {
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false);

            // Xóa các chi tiết hóa đơn
            String deleteLichSuSQL = "DELETE FROM LichSuMuaHang WHERE MaHoaDon = ?";
            pr = con.prepareStatement(deleteLichSuSQL);
            pr.setString(1, maHoaDon);
            pr.executeUpdate();

            // Xóa các chi tiết hóa đơn dựa trên mã hóa đơn và mã biến thể
            String deleteChiTietSQL = "DELETE FROM HoaDonChiTiet WHERE MaHoaDon = ? ";
            pr = con.prepareStatement(deleteChiTietSQL);
            pr.setString(1, maHoaDon);
            pr.executeUpdate();

            // Xóa hóa đơn
            String deleteHoaDonSQL = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
            pr = con.prepareStatement(deleteHoaDonSQL);
            pr.setString(1, maHoaDon);
            pr.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
                if (pr != null) {
                    pr.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Integer> getDiscountPercentageFromDatabase(String maDGG) {
        List<Integer> discountPercentages = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection()) {
            String query = "SELECT PhanTramGG FROM DotGiamGia WHERE MaDGG = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maDGG);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int discountPercentage = resultSet.getInt("PhanTramGG");
                discountPercentages.add(discountPercentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý exception
        }
        return discountPercentages;
    }

    public String getDiscountValueForPGG(String maPGG) {
        String discountValue = "";
        try {
            con = DBConnect.getConnection();

            // Viết truy vấn SQL để lấy giá trị giảm giá cho maPGG
            sql = "SELECT GiaTriGiam FROM DieuKienGiamGia WHERE MaGiamGia = ?";
            pr = con.prepareStatement(sql);
            pr.setString(1, maPGG);
            rs = pr.executeQuery();

            if (rs.next()) {
                discountValue = rs.getString("GiaTriGiam");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ hoặc thông báo lỗi
        } finally {
            // Đóng kết nối, PreparedStatement và ResultSet ở đây (nếu cần)
            // Bạn cần đảm bảo rằng các resource này sẽ được đóng để tránh memory leak
        }
        return discountValue;
    }

    // Lấy giá trị giảm giá từ cơ sở dữ liệu cho dgg
    public String getDiscountValueForDGG(String maDGG) {
        String discountValue = "";
        try {
            con = DBConnect.getConnection();

            // Viết truy vấn SQL để lấy giá trị giảm giá cho maDGG
            sql = "SELECT PhanTramGG FROM DotGiamGia WHERE  MaDGG = ?";
            pr = con.prepareStatement(sql);
            pr.setString(1, maDGG);
            rs = pr.executeQuery();

            if (rs.next()) {
                discountValue = rs.getString("PhanTramGG");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ hoặc thông báo lỗi
        } finally {
            // Đóng kết nối, PreparedStatement và ResultSet ở đây (nếu cần)
            // Bạn cần đảm bảo rằng các resource này sẽ được đóng để tránh memory leak
        }
        return discountValue;
    }

    public List<DieuKienGiamGia> timloaigiamgia(String maGiamGia) {
        List<DieuKienGiamGia> discountList = new ArrayList<>();
        sql = "SELECT LoaiGiamGia, GiaTriGiam FROM DieuKienGiamGia WHERE MaGiamGia = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maGiamGia);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String loaiGiamGia = resultSet.getString("LoaiGiamGia");
                    int giaTriGiam = resultSet.getInt("GiaTriGiam");

                    DieuKienGiamGia dieuKienGiamGia = new DieuKienGiamGia();
                    dieuKienGiamGia.setLoaiGiamGia(loaiGiamGia);
                    dieuKienGiamGia.setGiaTriGiam(giaTriGiam);

                    discountList.add(dieuKienGiamGia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return discountList;
    }

    public List<HoaDon> getSearch(String maHoaDon) {
        sql = "SELECT * FROM HoaDon WHERE MaHoaDon LIKE ? AND TrangThai LIKE N'Chưa thanh toán'";

        List<HoaDon> listHoaDon = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            pr = con.prepareStatement(sql);
            pr.setString(1, "%" + maHoaDon + "%");

            rs = pr.executeQuery();

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon(rs.getString("MaHoaDon"), rs.getString("TrangThai"), rs.getString("MaKhachHang"), rs.getString("MaNhanVien"), rs.getString("NgayTao"), rs.getString("MaDGG"), rs.getString("MaGiamGia"), rs.getInt("GiaTriGiamDGG"), rs.getInt("GiaTriGiamPGG"), rs.getBigDecimal("SoTienGiam"), rs.getBigDecimal("TongTien"), rs.getBoolean("VanChuyen"), rs.getBigDecimal("SoTienThanhToan"));
                listHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return listHoaDon;
    }

    //-------------------------------------------------------
    public List<HoaDon> getHoaDonTheoNgay(java.sql.Date ngay) {
        List<HoaDon> listHoaDon = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT * FROM HoaDon WHERE CONVERT(date, NgayTao) = ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngay);
            rs = ps.executeQuery();

            while (rs.next()) {
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
                listHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listHoaDon;
    }

    public BigDecimal getTongTienHoaDonGiamGiaTheoNgay(java.sql.Date ngay) {
        BigDecimal tongTienGiamGia = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(SoTienGiam) AS TongTienGiamGia FROM HoaDon WHERE CONVERT(date, NgayTao) = ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngay);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienGiamGia = rs.getBigDecimal("TongTienGiamGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongTienGiamGia;
    }

    public BigDecimal tongSoTienThanhToanTheoNgay(java.sql.Date ngay) {
        BigDecimal tongTienThuDuoc = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(SoTienThanhToan) AS TongTienThanhToan FROM HoaDon WHERE CONVERT(date, NgayTao) = ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngay);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienThuDuoc = rs.getBigDecimal("TongTienThanhToan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongTienThuDuoc;
    }

    public BigDecimal tongTienHoaDonTrongKhoangThoiGian(java.sql.Date ngayBatDau, java.sql.Date ngayKetThuc) {
        BigDecimal tongTien = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(TongTien) AS TongTien FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTien = rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongTien;
    }

    public BigDecimal tongTienGiamGiaTrongThang(java.sql.Date ngayDauThang, java.sql.Date ngayCuoiThang) {
        BigDecimal tongTienGiamGia = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(SoTienGiam) AS TongTienGiamGia FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngayDauThang);
            ps.setDate(2, ngayCuoiThang);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienGiamGia = rs.getBigDecimal("TongTienGiamGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongTienGiamGia;
    }

    public BigDecimal tongTienThanhToanTrongThang(java.sql.Date ngayDauThang, java.sql.Date ngayCuoiThang) {
        BigDecimal tongTienThanhToan = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(SoTienThanhToan) AS TongTienThanhToan FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngayDauThang);
            ps.setDate(2, ngayCuoiThang);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienThanhToan = rs.getBigDecimal("TongTienThanhToan");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return tongTienThanhToan;
    }

    public BigDecimal tongTienGiamGiaTrongKhoangThoiGian(Date ngayDau, Date ngayCuoi) {
        BigDecimal tongTienGiamGia = BigDecimal.ZERO;

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String query = "SELECT SUM(SoTienGiam) AS TongTienGiam FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngayDau);
            ps.setDate(2, ngayCuoi);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienGiamGia = rs.getBigDecimal("TongTienGiam");
            }

            // Đóng kết nối và các tài nguyên
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tongTienGiamGia;
    }

    public BigDecimal tongTienThanhToanTrongKhoangThoiGian(Date ngayDauNam, Date ngayCuoiNam) {
        BigDecimal tongTien = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT SUM(SoTienThanhToan) AS TongTien FROM HoaDon WHERE NgayTao >= ? AND NgayTao <= ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngayDauNam);
            ps.setDate(2, ngayCuoiNam);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTien = rs.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongTien;
    }

    public BigDecimal getTongTienHoaDonThuDuocTheoNgay(java.sql.Date ngay) {
        BigDecimal tongTienThuDuoc = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection(); // Hàm này để thiết lập kết nối đến cơ sở dữ liệu
            String query = "SELECT SUM(SoTienThanhToan) AS TongTienThuDuoc FROM HoaDon WHERE CAST(NgayTao AS DATE) = ?";
            ps = conn.prepareStatement(query);
            ps.setDate(1, ngay);
            rs = ps.executeQuery();

            if (rs.next()) {
                tongTienThuDuoc = rs.getBigDecimal("TongTienThuDuoc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongTienThuDuoc;
    }

    public BigDecimal getTongTienHoaDonTrongKhoangThoiGian(Date startDate, Date endDate) {
        BigDecimal tongTien = BigDecimal.ZERO;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBConnect.getConnection();

            // Chuẩn bị truy vấn SQL
            String query = "SELECT SUM(TongTien) AS TongTien FROM HoaDon WHERE CAST(NgayTao AS DATE) BETWEEN ? AND ?";
            preparedStatement = connection.prepareStatement(query);

            // Thiết lập tham số cho truy vấn SQL
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            // Thực hiện truy vấn và lấy kết quả
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tongTien = resultSet.getBigDecimal("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý exception nếu có
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tongTien;
    }

    public int getSoLuongHoaDonTheoNhanVien(String maNhanVien) {
        int soLuong = 0;
        String query = "SELECT COUNT(*) AS SoLuong FROM HoaDon WHERE MaNhanVien = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, maNhanVien);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    soLuong = resultSet.getInt("SoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ
        }
        return soLuong;
    }

    // Phương thức lấy tổng tiền hóa đơn theo mã nhân viên
    public BigDecimal getTongTienHoaDonTheoNhanVien(String maNhanVien) {
        BigDecimal tongTien = BigDecimal.ZERO;
        String query = "SELECT SUM(SoTienThanhToan) AS TongTien FROM HoaDon WHERE MaNhanVien = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, maNhanVien);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tongTien = resultSet.getBigDecimal("SoTienThanhToan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ
        }
        return tongTien;
    }
    
    public Map<String, Object[]> getThongTinNhanVien() {
        Map<String, Object[]> thongTinNhanVien = new HashMap<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.getConnection();
            if (connection != null) {
                String query = "SELECT MaNhanVien, COUNT(*) AS SoLuongTao, SUM(SoTienThanhToan) AS TongTienThu " +
                               "FROM HoaDon " +
                               "WHERE MaNhanVien IS NOT NULL " +
                               "GROUP BY MaNhanVien";

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String maNhanVien = resultSet.getString("MaNhanVien");
                    int soLuongTao = resultSet.getInt("SoLuongTao");
                    BigDecimal tongTienThu = resultSet.getBigDecimal("TongTienThu");

                    thongTinNhanVien.put(maNhanVien, new Object[]{soLuongTao, tongTienThu});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return thongTinNhanVien;
    }
    
    public List<SanPham_HD> getSPByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
    sql = "SELECT S.MASP, S.LOAI, S.TONKHO, B.MABT, B.TUOI, B.MAULONG, B.CHIEUCAO, B.NGAYSINH, B.GIOITINH, B.CANNANG, B.GIABAN, B.TIEM, B.Hinh "
            + "FROM SANPHAM S "
            + "JOIN BIENTHESP B ON S.MASP = B.MASP "
            + "WHERE B.GIABAN BETWEEN ? AND ? AND B.IsDelete = 0";

    List<SanPham_HD> listHD = new ArrayList<>();
    try {
        con = DBConnect.getConnection();
        pr = con.prepareStatement(sql);
        pr.setBigDecimal(1, minPrice);
        pr.setBigDecimal(2, maxPrice);
        rs = pr.executeQuery();

        while (rs.next()) {
            SanPham_HD hd = new SanPham_HD();
            SanPham sp = new SanPham(rs.getString("MASP"), rs.getString("LOAI"), rs.getInt("TONKHO"));
            BienTheSanPham bt = new BienTheSanPham(rs.getString("MABT"), rs.getString("MASP"), rs.getFloat("CANNANG"), rs.getInt("TUOI"), rs.getString("MAULONG"), rs.getFloat("CHIEUCAO"), rs.getString("NGAYSINH"), rs.getBigDecimal("GIABAN"), rs.getBoolean("GIOITINH"), rs.getString("TIEM"), rs.getString("Hinh"));
            hd.setBtsp(bt);
            hd.setSp(sp);
            listHD.add(hd);
        }
        return listHD;
    } catch (SQLException e) {
        e.printStackTrace(System.out);
        return null;
    }
}


}

