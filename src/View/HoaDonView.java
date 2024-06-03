package View;

import Model_HoaDon.HoaDon;
import Model_HoaDon.HoaDonChiTiet;
import Model_HoaDon.SanPham_HD;
import Model_PGG.DieuKienGiamGia;
import Model_PGG.DuLieuChung;
import Model_PGG.PhieuGiamGia;
import Service_HoaDon.HoaDonService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import utils.Auth;

public final class HoaDonView extends javax.swing.JFrame {

    private final HoaDonService service = new HoaDonService();
    private DefaultTableModel mol = new DefaultTableModel();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public HoaDonView() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Hóa đơn");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        if (!Auth.isManager()) {
            if (tabLichSuHD != null && tabThongKe != null) {
                int lichSuIndex = indexOfTab(tabs, "Lịch sử");
                int thongkeIndex = indexOfTab(tabs, "Thống kê");
                if (lichSuIndex != -1) {
                    tabs.setEnabledAt(lichSuIndex, false);
                    tabs.setEnabledAt(thongkeIndex, false);
                    tabs.setToolTipTextAt(lichSuIndex, "Bạn không có quyền truy cập");
                    tabs.setToolTipTextAt(thongkeIndex, "Bạn không có quyền truy cập");

                }
            }

        }
        fillTableThanhTich();
        init();
        thongke();
    }

    public void thongke() {
        tongtienngay();
        tonggiamngay();
        tongtienthuduocngay();
        tongtienthang();
        tongiamthang();
        tongthuthang();
        tongTatCaNam();
        tongGiamGiaNam();
        tongthunam();
    }

    private int indexOfTab(JTabbedPane tabbedPane, String title) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(title)) {
                return i;
            }
        }
        return -1;
    }

    public void init() {
        URL imageUrl = getClass().getResource("/icon/dog.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image image = icon.getImage();
        setIconImage(image);
        getContentPane().setBackground(Color.WHITE);
        Color disabledTextColor = Color.BLACK;
        Thread updateDateTimeThread = new Thread(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            while (true) {
                Date currentDate = new Date();
                String formattedDate = dateFormat.format(currentDate);
                txtNgayTao.setText(formattedDate);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateDateTimeThread.start();
        txtGiaTriGiamDGG.setDisabledTextColor(disabledTextColor);
        txtGiaTriGiamPGG.setDisabledTextColor(disabledTextColor);
        txtSoTienGiam.setDisabledTextColor(disabledTextColor);
        txtTongTienCuoi.setDisabledTextColor(disabledTextColor);
        txtSoTienNhap.setDisabledTextColor(disabledTextColor);
        Icon disabledIcon = lblNgayTao.getIcon();
        txtNgayTao.setDisabledIcon(disabledIcon);
        txtMaNhanVien.setDisabledTextColor(disabledTextColor);
        txtGiaTriGiamDGG.setEnabled(false);
        txtGiaTriGiamPGG.setEnabled(false);
        txtSoTienGiam.setEnabled(false);
        txtTongTienCuoi.setEnabled(false);
        txtNgayTao.setEnabled(false);
        txtSoTienNhap.setEnabled(false);
        txtMaNhanVien.setEnabled(false);
        txtMaNhanVien.setText(Auth.user.getMaNhanVien());
        fillHoaDon();
        fillSanPham();
        fillHDCT();
        fillLichSuHD();
        timkiem();
        timkiemhd();
        ngay();
        thang();
        nam();
    }

    public void timkiem() {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            public void search() {
                String giaBatDauStr = txtGiaBD.getText().trim();
                String giaKetThucStr = txtGiaKetThuc.getText().trim();
                if (!giaBatDauStr.isEmpty() && !giaKetThucStr.isEmpty()) {
                    try {
                        BigDecimal giaBatDau = new BigDecimal(giaBatDauStr);
                        BigDecimal giaKetThuc = new BigDecimal(giaKetThucStr);
                        List<SanPham_HD> productList = service.getSPByPriceRange(giaBatDau, giaKetThuc);
                        if (productList != null && !productList.isEmpty()) {
                            fillTableSanPham(productList);
                        } else {
                            clearSanPhamTable();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số.");
                    }
                } else {
                    fillSanPham();
                }
            }
        };

        txtGiaBD.getDocument().addDocumentListener(documentListener);
        txtGiaKetThuc.getDocument().addDocumentListener(documentListener);
    }

    public void fillTableSanPham(List<SanPham_HD> list) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        for (SanPham_HD sp : list) {
            model.addRow(sp.toDataRowSP());
        }
    }

    public void clearSanPhamTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
    }

    private void timkiemhd() {
        txtTimKiemMaHD.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            public void search() {
                String maTimKiem = txtTimKiemMaHD.getText();
                List<HoaDon> hd = service.getSearch(maTimKiem);
                if (hd != null && !hd.isEmpty()) {
                    fillTableTimKiem(hd);
                } else {
                    clear();
                }
            }
        });
    }

    private String formatCurrency(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
        return decimalFormat.format(amount);
    }

    private BigDecimal convertStringToBigDecimal(String moneyString) {
        try {
            // Loại bỏ các ký tự không phải số, ngoại trừ dấu chấm (nếu có)
            String numberOnly = moneyString.replaceAll("[^\\d.]", "");

            // Kiểm tra nếu có nhiều hơn một dấu chấm thì loại bỏ dấu chấm thứ hai trở đi
            if (numberOnly.indexOf('.') != numberOnly.lastIndexOf('.')) {
                numberOnly = numberOnly.replaceFirst("\\.", "");
            }

            return new BigDecimal(numberOnly);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    private String danhGiaTheoTienThu(BigDecimal tongTienThu, BigDecimal sotiendanhgia) {
        int compareResult = tongTienThu.compareTo(sotiendanhgia);
        if (compareResult < 0) {
            return "Kém";
        } else if (compareResult == 0) {
            return "Giỏi";
        } else {
            return "Xuất sắc";
        }
    }

    public void fillTableThanhTich() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
        // Xóa dữ liệu cũ trong bảng
        model.setRowCount(0);

        // Lấy thông tin từ service
        Map<String, Object[]> thongTinNhanVien = service.getThongTinNhanVien(); // Lấy thông tin nhân viên từ service

        String soTienDanhGiaText = txtSoTienDanhGia.getText().trim();
        BigDecimal sotiendanhgia = BigDecimal.ZERO; // Mặc định là 0 nếu không thể chuyển đổi

        // Kiểm tra và chuyển đổi chuỗi số thành BigDecimal
        if (!soTienDanhGiaText.isEmpty()) {
            try {
                sotiendanhgia = new BigDecimal(soTienDanhGiaText);
            } catch (NumberFormatException e) {
                // Xử lý ngoại lệ nếu chuỗi không thể chuyển đổi thành số
                // Ví dụ: Hiển thị thông báo lỗi hoặc xử lý khác tùy vào yêu cầu của bạn
                e.printStackTrace(); // In ra lỗi để kiểm tra thông tin lỗi trong quá trình phát triển
                // Có thể thêm thông báo lỗi để người dùng biết rằng đầu vào không hợp lệ
            }
        }

        int row = 0; // Dòng cần chèn dữ liệu vào bảng
        for (Map.Entry<String, Object[]> entry : thongTinNhanVien.entrySet()) {
            String maNhanVien = entry.getKey();
            Object[] info = entry.getValue();

            int soLuongTao = (int) info[0];
            BigDecimal tongTienThu = (BigDecimal) info[1];
            String danhGia = danhGiaTheoTienThu(tongTienThu, sotiendanhgia); // Đánh giá dựa vào tổng tiền thu

            // Thêm dữ liệu vào bảng tblThongKeHD
            model.addRow(new Object[]{maNhanVien, soLuongTao, formatCurrency(tongTienThu), danhGia});

            // Nếu chưa nhập số tiền đánh giá thì trạng thái là "Chờ đánh giá"
            if (sotiendanhgia.equals(BigDecimal.ZERO)) {
                model.setValueAt("Chờ đánh giá", row, 3);
            }

            row++;
        }
    }

    public void fillTableTimKiem(List<HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HoaDon hd : list) {
            model.addRow(hd.toDataRowHD());
        }
    }

    public void fillSanPham() {
        mol = (DefaultTableModel) tblSanPham.getModel();
        mol.setRowCount(0);
        for (SanPham_HD sp : this.service.getSP()) {
            mol.addRow(sp.toDataRowSP());
        }
    }

    public void fillHoaDon() {
        mol = (DefaultTableModel) tblHoaDon.getModel();
        mol.setRowCount(0); // Xóa hết dữ liệu trong bảng

        for (HoaDon hd : this.service.getHoaDon()) {
            mol.addRow(hd.toDataRowHD());
        }
    }

    public void fillHDCT() {
        mol = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        mol.setRowCount(0);
        for (HoaDonChiTiet hdct : this.service.getHoaDonChiTiet()) {
            mol.addRow(hdct.todataRowHDCT());
        }
    }

    public void fillLichSuHD() {
        mol = (DefaultTableModel) tblLichSuHoaDon.getModel();
        mol.setRowCount(0);
        for (HoaDon hd : this.service.getHoaDonDaThanhToan()) {
            mol.addRow(hd.toDataRowHD());
        }
    }

    public void guiMail(List<HoaDon> danhSachHoaDon, List<HoaDonChiTiet> danhSachHoaDonChiTiet) {
        String senderEmail = "thanhkvph34331@fpt.edu.vn"; // Địa chỉ email người gửi
        String senderPassword = "zxqonxacgdthvjej"; // Mật khẩu email người gửi

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            HoaDon hoaDon = readFormUpdate(); // Lấy thông tin hóa đơn từ form

            String maHoaDon = hoaDon.getMaHoaDon();

            String recipientEmail = "chienngoclong2k4@gmail.com"; // Địa chỉ email người nhận
            String subject = "Thông tin hóa đơn - Mã: " + maHoaDon;
            DecimalFormat decimalFormat = new DecimalFormat("#,### VND");

            String formattedTongTienCuoi = decimalFormat.format(hoaDon.getTongTien());
            String formattedSoTienNhap = decimalFormat.format(hoaDon.getSoTienThanhToan());
            String formattedSoTienGiam = decimalFormat.format(hoaDon.getSoTienGiam());
            StringBuilder messageContent = new StringBuilder();
            messageContent.append("Thông tin hóa đơn\n")
                    .append("Mã hóa đơn: ").append(hoaDon.getMaHoaDon()).append("\n")
                    .append("Trạng thái: ").append(hoaDon.getTrangThai()).append("\n")
                    .append("Mã khách hàng: ").append(hoaDon.getMaKhachHang()).append("\n")
                    .append("Mã nhân viên: ").append(hoaDon.getMaNhanVien()).append("\n")
                    .append("Mã đợt giảm giá: ").append(hoaDon.getMaDGG()).append("\n")
                    .append("Mã giảm giá: ").append(hoaDon.getMaGiamGia()).append("\n")
                    .append("Giá trị giảm giá đợt giảm giá: ").append(hoaDon.getGiaTriGiamDGG()).append(" %").append("\n")
                    .append("Giá trị giảm của phiếu giảm giá: ").append(txtGiaTriGiamPGG.getText()).append("\n")
                    .append("Số tiền giảm: ").append(formattedSoTienGiam).append("\n")
                    .append("Tổng tiền cuối: ").append(formattedTongTienCuoi).append("\n")
                    .append("Vận chuyển: ").append(hoaDon.isVanChuyen() ? "Có" : "Không").append("\n")
                    .append("Số tiền nhập: ").append(formattedSoTienNhap).append("\n")
                    .append("\nChi tiết sản phẩm:\n");

            List<HoaDonChiTiet> chiTietHoaDon = new ArrayList<>();
            for (HoaDonChiTiet chiTiet : danhSachHoaDonChiTiet) {
                if (chiTiet.getMaHoaDon().equals(maHoaDon)) {
                    chiTietHoaDon.add(chiTiet);
                }
            }

            for (HoaDonChiTiet chiTiet : chiTietHoaDon) {
//                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTien = decimalFormat.format(chiTiet.getTongTien());

                messageContent.append("Tên sản phẩm: ").append(chiTiet.getMaBT())
                        .append(", Số lượng: ").append(chiTiet.getSoLuong())
                        .append(", Số tiền: ").append(formattedTongTien).append("\n");
            }

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail)); // Địa chỉ người gửi
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Địa chỉ người nhận
                message.setSubject(subject); // Tiêu đề email
                message.setText(messageContent.toString()); // Nội dung email

                Transport.send(message);
                System.out.println("Email sent successfully to " + recipientEmail);
            } catch (MessagingException e) {
                System.err.println("Failed to send email to " + recipientEmail + ": " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public HoaDon readForm() {
        String maHoaDon = generateRandomMaHoaDon(10);
        String trangthai = "Chưa thanh toán";
        String makh = txtMaKH.getText();
        String manv = txtMaNhanVien.getText();

        String madgg = txtMaDotGiam.getText();
        String pgg = txtMaPGG.getText();
        String giatrigiamdgg = txtGiaTriGiamDGG.getText();
        String giatrigiampgg = txtGiaTriGiamPGG.getText();
        String soTienGiam = txtSoTienGiam.getText();
        String tongTienCuoi = txtTongTienCuoi.getText();
        Boolean vanchuyen = false; // Giả định mặc định là không
        if (rdoCo.isSelected()) {
            vanchuyen = true; // Nếu rdoCo được chọn thì cập nhật thành 1
        }
        String sotienhap = txtSoTienNhap.getText();
        //System.out.println(vanchuyen);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);

        // Parse các giá trị String về các kiểu dữ liệu tương ứng nếu cần
        BigDecimal parsedGiaTriGiamDGG = parseStringToBigDecimal(giatrigiamdgg);
        BigDecimal parsedGiaTriGiamPGG = parseStringToBigDecimal(giatrigiampgg);
        BigDecimal parsedSoTienGiam = parseStringToBigDecimal(soTienGiam);
        BigDecimal parsedTongTien = parseStringToBigDecimal(tongTienCuoi);
        BigDecimal parsedSoTienNhap = parseStringToBigDecimal(sotienhap);

        return new HoaDon(maHoaDon, trangthai, makh, manv, formattedCurrentDateTime, madgg, pgg, parsedGiaTriGiamDGG.intValue(), parsedGiaTriGiamPGG.intValue(), parsedSoTienGiam, parsedTongTien, vanchuyen, parsedSoTienNhap);
    }

    public HoaDon readFormUpdate() {
        String maHoaDon = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
        String trangThai = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString();
        String maKH = txtMaKH.getText();
        String maNV = txtMaNhanVien.getText();
        String maDGG = txtMaDotGiam.getText();
        String maGG = txtMaPGG.getText();
        String giaTriGiamDGG = txtGiaTriGiamDGG.getText().trim();
        String giaTriGiamPGG = txtGiaTriGiamPGG.getText().trim();
        String soTienGiam = txtSoTienGiam.getText().trim();
        String tongTienCuoi = txtTongTienCuoi.getText().trim();
        Boolean vanchuyen = false; // Giả định mặc định là không
        if (rdoCo.isSelected()) {
            vanchuyen = true; // Nếu rdoCo được chọn thì cập nhật thành 1
        }
        String sotienhap = txtSoTienNhap.getText();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);

        BigDecimal parsedGiaTriGiamDGG = parseStringToBigDecimal(giaTriGiamDGG);
        BigDecimal parsedGiaTriGiamPGG = parseStringToBigDecimal(giaTriGiamPGG);
        BigDecimal parsedSoTienGiam = parseStringToBigDecimal(soTienGiam);
        BigDecimal parsedTongTien = parseStringToBigDecimal(tongTienCuoi);
        BigDecimal parsedSoTienNhap = parseStringToBigDecimal(sotienhap);

        return new HoaDon(maHoaDon, trangThai, maKH, maNV, formattedCurrentDateTime, maDGG, maGG,
                parsedGiaTriGiamDGG.intValue(), parsedGiaTriGiamPGG.intValue(), parsedSoTienGiam, parsedTongTien, vanchuyen, parsedSoTienNhap);
    }

    private BigDecimal parseStringToBigDecimal(String value) {
        value = cleanString(value);

        if (value.isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            // Handle comma-separated numbers
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            DecimalFormat format = new DecimalFormat("#,##0.0#", symbols);
            format.setParseBigDecimal(true);
            return (BigDecimal) format.parse(value);
        } catch (ParseException e) {
            // Handle parsing exception
            return BigDecimal.ZERO; // Return default value if there's an error
        }
    }

    private String cleanString(String value) {
        return value.replaceAll("[^\\d.]", ""); // Retain only digits and decimal points
    }

    public HoaDonChiTiet HDCTUpdate() {
        int selectedRow = tblHoaDonChiTiet.getSelectedRow();

        if (selectedRow >= 0) {
            String mahoadon = tblHoaDonChiTiet.getValueAt(selectedRow, 0).toString();
            String masp = tblHoaDonChiTiet.getValueAt(selectedRow, 1).toString();
            int soluong = Integer.parseInt(tblHoaDonChiTiet.getValueAt(selectedRow, 2).toString());
            BigDecimal tongtien = new BigDecimal(tblHoaDonChiTiet.getValueAt(selectedRow, 3).toString());

            return new HoaDonChiTiet(mahoadon, masp, soluong, tongtien);
        }

        return null; // Trả về null nếu không có hàng nào được chọn
    }

    private String generateRandomMaHoaDon(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public double applyDiscountToBill(String pggId, double totalAmount) {
        List<Map<String, Object>> discounts = service.getDiscountInfo(pggId);
        double finalAmount = totalAmount;

        for (Map<String, Object> discount : discounts) {
            String loaiGiamGia = (String) discount.get("LoaiGiamGia");
            int giaTriGiam = (int) discount.get("GiaTriGiam");

            if (loaiGiamGia.equals("Giảm phần trăm")) {
                // Nếu giảm giá theo %
                finalAmount -= (finalAmount * giaTriGiam) / 100;
            } else if (loaiGiamGia.equals("Giảm tiền")) {
                // Nếu giảm giá theo số tiền cố định
                finalAmount -= giaTriGiam;
            }
        }
        return finalAmount;
    }

    public boolean isDiscountExpired(String maGiamGia) {
        DieuKienGiamGia discount = service.findDiscountStatusAndQuantity(maGiamGia);
        if (discount != null) {
            if (discount.getTrangThai().equalsIgnoreCase("Hết hạn")) {
                System.out.println("Phiếu giảm giá đã hết hạn.");
                txtMaPGG.setText("");
                return true;
            }
            return false;
        } else {
            System.out.println("Không tìm thấy mã giảm giá hoặc có lỗi xảy ra.");
            return true;
        }
    }

    public boolean isDiscountUsedUp(String maGiamGia) {
        DieuKienGiamGia discount = service.findDiscountStatusAndQuantity(maGiamGia);

        if (discount != null) {
            int remainingQuantity = discount.getSoLuongCon();
            if (remainingQuantity <= 0) {
                System.out.println("Mã giảm giá đã hết lượt sử dụng.");
                txtMaPGG.setText("");
                return true;
            }
            return false;
        } else {
            System.out.println("Không tìm thấy mã giảm giá hoặc có lỗi xảy ra.");
            return true;
        }
    }

    private void applyDiscountToBill() {
        String maGiamGia = txtMaPGG.getText();
        BigDecimal totalBillAmount = calculateTotalBillAmount();

        boolean isExpired = isDiscountExpired(maGiamGia);
        boolean isUsedUp = isDiscountUsedUp(maGiamGia);

        if (isExpired) {
            JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết hạn");
        } else if (isUsedUp) {
            JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết số lượng");
        } else {
            DieuKienGiamGia discount = service.findDiscountStatusAndQuantity(maGiamGia);

            if (discount != null) {
                List<Map<String, Object>> discountInfo = service.getDiscountDetails(maGiamGia);

                if (!discountInfo.isEmpty()) {
                    BigDecimal discountAmount = calculateDiscountAmount(discountInfo, totalBillAmount);

                    String discountDisplay = "";
                    BigDecimal displayedDiscountAmount = BigDecimal.ZERO;

                    for (Map<String, Object> discountDetail : discountInfo) {
                        String loaiGiamGia = (String) discountDetail.get("LoaiGiamGia");
                        int giaTriGiam = (int) discountDetail.get("GiaTriGiam");

                        if ("Giảm phần trăm".equals(loaiGiamGia)) {
                            displayedDiscountAmount = BigDecimal.valueOf(giaTriGiam)
                                    .divide(BigDecimal.valueOf(100))
                                    .multiply(totalBillAmount);
                            discountDisplay = giaTriGiam + "%";
                        } else if ("Giảm tiền".equals(loaiGiamGia)) {
                            displayedDiscountAmount = BigDecimal.valueOf(giaTriGiam);
                            discountDisplay = giaTriGiam + " VND";
                        }

                        if (!discountDisplay.isEmpty()) {
                            break;
                        }
                    }

                    BigDecimal discountedTotalBill = totalBillAmount.subtract(discountAmount);

// Nếu tổng tiền sau khi giảm giá nhỏ hơn 0, thì đặt giá trị là 0
                    if (discountedTotalBill.compareTo(BigDecimal.ZERO) < 0) {
                        discountedTotalBill = BigDecimal.ZERO;
                    }

// Format số tiền giảm giá và tổng tiền cuối cùng
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                    String formattedDiscountedTotalBill = decimalFormat.format(discountedTotalBill.doubleValue());
                    String formattedDisplayedDiscountAmount = decimalFormat.format(displayedDiscountAmount.doubleValue());

// Hiển thị giá trị đã được format
                    txtGiaTriGiamDGG.setText("");
                    txtTongTienCuoi.setText(formattedDiscountedTotalBill);
                    txtSoTienNhap.setText(formattedDiscountedTotalBill);
                    txtSoTienGiam.setText(formattedDisplayedDiscountAmount);
                    txtGiaTriGiamPGG.setText(discountDisplay);

                    // service.decreaseQuantityByOne(maGiamGia); // Trừ số lượng còn đi 1 ở điều kiện giảm giá
                } else {
                    System.out.println("Không tìm thấy thông tin giảm giá cho mã này.");
                    txtMaPGG.setText("");
                }
            } else {
                System.out.println("Không tìm thấy mã giảm giá hoặc có lỗi xảy ra.");
                txtMaPGG.setText("");
            }
        }
    }

    public void clear() {
        txtMaDotGiam.setText("");
        txtMaPGG.setText("");
        txtGiaTriGiamDGG.setText("");
        txtGiaTriGiamPGG.setText("");
        txtSoTienGiam.setText("");
        txtTongTienCuoi.setText("");
        tblHoaDon.clearSelection();
        tblHoaDonChiTiet.clearSelection();
    }

    public Boolean checkDuLieu() {
        String makh = txtMaKH.getText();
        String manv = txtMaNhanVien.getText();
        String madgg = txtMaDotGiam.getText();
        String pgg = txtMaPGG.getText();

        if (tblHoaDon.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thêm sản phẩm.");
            return false;
        }

        if (tblHoaDonChiTiet.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào hóa đơn.");
            return false;
        }

        if (makh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng.");
            return false;
        }

        if (makh.length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được nhập quá 10 kí tự.");
            return false;
        }

        if (manv.length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được nhập quá 10 kí tự.");
            return false;
        }

        if (madgg.length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã đợt giảm giá không được nhập quá 10 kí tự.");
            return false;
        }
        if (pgg.length() > 15) {
            JOptionPane.showMessageDialog(this, "Mã phiếu giảm giá không được nhập quá 15 kí tự.");
            return false;
        }
        return true;
    }
    private boolean tblSanPhamClicked = false;
    private boolean tblHoaDonClicked = false;
    private boolean soLuongTonDaHet = false;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        txtTimKiemMaHD = new javax.swing.JTextField();
        btnTaoHoaDon = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTongTienCuoi = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        rdoCo = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        rdoKhong = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtSoTienNhap = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtGiaTriGiamDGG = new javax.swing.JTextField();
        txtMaDotGiam = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSoTienGiam = new javax.swing.JTextField();
        txtMaPGG = new javax.swing.JTextField();
        btnNhap = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtGiaTriGiamPGG = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        txtGiaBD = new javax.swing.JTextField();
        txtGiaKetThuc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tabLichSuHD = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLichSuHoaDon = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblMaDGG = new javax.swing.JLabel();
        lblMaPGG = new javax.swing.JLabel();
        lblGiaTriGiamDGG = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lblThongTinSP = new javax.swing.JTextArea();
        lblSoTienGiam = new javax.swing.JLabel();
        lblTongTienGiam = new javax.swing.JLabel();
        lblGiaTriGiamPGG = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblVanChuyen = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblDaThanhToan = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnXoaLichSu = new javax.swing.JButton();
        txtTimKiemLichSu = new javax.swing.JTextField();
        tabThongKe = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtTongTatCaNgay = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtTongGiamGiaNgay = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtTongThuDuocNgay = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        txtTongTatCaThang = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtTongGiamGiaThang = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtTongThuThang = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        txtTongTatCaNam = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtTongGiamGiaNam = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtTongThuNam = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblThongKeHD = new javax.swing.JTable();
        txtTimKiemThongKe = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtSoTienDanhGia = new javax.swing.JTextField();
        btnAZ = new javax.swing.JButton();
        btnZA = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        cboLuaChonSapXep = new javax.swing.JComboBox<>();
        cboNgay = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jblHome = new javax.swing.JLabel();
        jlbSanPham = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jlbPhieuGiamGia = new javax.swing.JLabel();
        jblDotGiamGia = new javax.swing.JLabel();
        jblNhanVien = new javax.swing.JLabel();
        jblKhachHang = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jblPhieuGiaoHang = new javax.swing.JLabel();
        jlbTaiKhoan = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabs.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã hóa đơn", "Trạng thái"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaoHoaDonMouseClicked(evt);
            }
        });

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnTaoHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiemMaHD)
                        .addGap(18, 18, 18)
                        .addComponent(btnThanhToan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoi))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDon)
                    .addComponent(btnThanhToan)
                    .addComponent(btnMoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết hóa đơn"));

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã sản phẩm", "Mã biến thể", "Số lượng", "Tổng tiền SP"
            }
        ));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khác"));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setText("Mã khách hàng :");

        jLabel3.setText("Mã nhân viên :");

        jLabel6.setText("Tổng tiền cuối:");

        txtMaKH.setText("KH001");

        buttonGroup1.add(rdoCo);
        rdoCo.setText("Có");

        jLabel23.setText("Vận chuyển :");

        buttonGroup1.add(rdoKhong);
        rdoKhong.setSelected(true);
        rdoKhong.setText("Không");

        jLabel5.setText("Số tiền nhập :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongTienCuoi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaNhanVien)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(rdoCo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoKhong))
                    .addComponent(txtSoTienNhap))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTienCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSoTienNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoCo)
                    .addComponent(jLabel23)
                    .addComponent(rdoKhong))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel7.setText("Mã đợt giảm :");

        txtGiaTriGiamDGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriGiamDGGActionPerformed(evt);
            }
        });

        txtMaDotGiam.setText("DG0001");
        txtMaDotGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaDotGiamActionPerformed(evt);
            }
        });

        jLabel9.setText("GTG_DGG :");

        jLabel8.setText("Mã PGG :");

        jLabel11.setText("Số tiền giảm :");

        txtMaPGG.setText("MG001");
        txtMaPGG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaPGGFocusLost(evt);
            }
        });

        btnNhap.setText("Nhập mã");
        btnNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNhapMouseClicked(evt);
            }
        });

        jLabel19.setText("GTG_PGG :");

        txtGiaTriGiamPGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriGiamPGGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(35, 35, 35)
                        .addComponent(txtSoTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaTriGiamPGG, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriGiamDGG, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMaPGG, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaDotGiam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnNhap))))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtMaDotGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtMaPGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGiaTriGiamDGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtGiaTriGiamPGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtSoTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Sản phẩm"));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Mã biến thế", "Số lượng", "Giá tiền"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        jLabel25.setText("Giá BD :");

        jLabel26.setText("Giá KT :");

        jLabel27.setText("Tìm kiếm :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaBD, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtGiaKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 180, Short.MAX_VALUE))
        );

        tabs.addTab("Hóa đơn", jPanel2);

        tabLichSuHD.setBackground(new java.awt.Color(255, 255, 255));
        tabLichSuHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã hóa đơn", "Trạng thái"
            }
        ));
        tblLichSuHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblLichSuHoaDon);

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Mã khách hàng :");

        jLabel10.setText("Mã nhân viên :");

        jLabel12.setText("Ngày tạo :");

        jLabel13.setText("Thông tin sản phẩm :");

        jLabel14.setText("Mã DGG :");

        jLabel15.setText("Mã PGG :");

        jLabel16.setText("GTG_DGG :");

        jLabel17.setText("Số tiền giảm :");

        jLabel18.setText("Tổng tiền :");

        lblMaKH.setText("null");

        lblMaNV.setText("null");

        lblNgayTao.setText("null");

        lblMaDGG.setText("null");

        lblMaPGG.setText("null");

        lblGiaTriGiamDGG.setText("null");

        lblThongTinSP.setColumns(20);
        lblThongTinSP.setRows(5);
        jScrollPane5.setViewportView(lblThongTinSP);

        lblSoTienGiam.setText("null");

        lblTongTienGiam.setText("null");

        lblGiaTriGiamPGG.setText("null");

        jLabel20.setText("GTG_PGG :");

        jLabel24.setText("Vận chuyển :");

        lblVanChuyen.setText("null");

        jLabel29.setText("Đã thanh toán :");

        lblDaThanhToan.setText("null");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblSoTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTongTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel10)
                                .addComponent(jLabel12)
                                .addComponent(jLabel24))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblVanChuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(36, 36, 36)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblMaDGG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblMaPGG, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel20))
                                    .addGap(30, 30, 30)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblGiaTriGiamPGG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblGiaTriGiamDGG, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel14)
                    .addComponent(lblMaKH)
                    .addComponent(lblMaDGG))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15)
                    .addComponent(lblMaNV)
                    .addComponent(lblMaPGG))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(lblNgayTao)
                    .addComponent(lblGiaTriGiamDGG))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGiaTriGiamPGG)
                    .addComponent(jLabel20)
                    .addComponent(jLabel24)
                    .addComponent(lblVanChuyen))
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblSoTienGiam))
                .addGap(34, 34, 34)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblTongTienGiam))
                .addGap(34, 34, 34)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(lblDaThanhToan))
                .addGap(26, 26, 26))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoaLichSu.setText("Xóa");
        btnXoaLichSu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaLichSuMouseClicked(evt);
            }
        });

        txtTimKiemLichSu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiemLichSuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoaLichSu)
                .addGap(44, 44, 44))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaLichSu)
                    .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabLichSuHDLayout = new javax.swing.GroupLayout(tabLichSuHD);
        tabLichSuHD.setLayout(tabLichSuHDLayout);
        tabLichSuHDLayout.setHorizontalGroup(
            tabLichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLichSuHDLayout.createSequentialGroup()
                .addGroup(tabLichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabLichSuHDLayout.setVerticalGroup(
            tabLichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLichSuHDLayout.createSequentialGroup()
                .addGroup(tabLichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(tabLichSuHDLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 146, Short.MAX_VALUE))
        );

        tabs.addTab("Lịch sử", tabLichSuHD);

        tabThongKe.setBackground(new java.awt.Color(255, 255, 255));
        tabThongKe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tổng các khoản tiền trong ngày"));

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel45.setText("Tổng tiền tất cả hóa đơn :");

        txtTongTatCaNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTatCaNgayActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel46.setText("Tổng tiền hóa đơn đã giảm giá :");

        txtTongGiamGiaNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongGiamGiaNgayActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel47.setText("Tổng tiền thu được :");

        txtTongThuDuocNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongThuDuocNgayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongThuDuocNgay)
                    .addComponent(txtTongTatCaNgay, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongGiamGiaNgay, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47))
                        .addGap(0, 136, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel45)
                .addGap(18, 18, 18)
                .addComponent(txtTongTatCaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(txtTongGiamGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongThuDuocNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tổng các khoản tiền trong tháng"));

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel48.setText("Tổng tiền tất cả hóa đơn :");

        txtTongTatCaThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTatCaThangActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel49.setText("Tổng tiền hóa đơn đã giảm giá :");

        txtTongGiamGiaThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongGiamGiaThangActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel50.setText("Tổng tiền thu được :");

        txtTongThuThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongThuThangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongThuThang)
                    .addComponent(txtTongTatCaThang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongGiamGiaThang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50))
                        .addGap(0, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel48)
                .addGap(18, 18, 18)
                .addComponent(txtTongTatCaThang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addComponent(txtTongGiamGiaThang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongThuThang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tổng các khoản tiền trong năm"));

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel51.setText("Tổng tiền tất cả hóa đơn :");

        txtTongTatCaNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTatCaNamActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setText("Tổng tiền hóa đơn đã giảm giá :");

        txtTongGiamGiaNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongGiamGiaNamActionPerformed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel53.setText("Tổng tiền thu được :");

        txtTongThuNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongThuNamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongThuNam)
                    .addComponent(txtTongTatCaNam, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongGiamGiaNam, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53))
                        .addGap(0, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addComponent(txtTongTatCaNam, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel52)
                .addGap(18, 18, 18)
                .addComponent(txtTongGiamGiaNam, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongThuNam, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thành tích nhân viên ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        tblThongKeHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Số lượng tạo ", "Tổng tiền ", "Đánh giá"
            }
        ));
        jScrollPane7.setViewportView(tblThongKeHD);

        txtTimKiemThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemThongKeActionPerformed(evt);
            }
        });

        jLabel30.setText("Tìm kiếm :");

        jLabel31.setText("Số tiền đánh giá :");

        txtSoTienDanhGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTienDanhGiaActionPerformed(evt);
            }
        });

        btnAZ.setText("A-Z");
        btnAZ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAZMouseClicked(evt);
            }
        });

        btnZA.setText("Z-A");
        btnZA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnZAMouseClicked(evt);
            }
        });

        jLabel32.setText("Sắp xếp :");

        cboLuaChonSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã nhân viên", "Số lượng tạo", "Tổng tiền", "Đánh giá" }));
        cboLuaChonSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLuaChonSapXepItemStateChanged(evt);
            }
        });
        cboLuaChonSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLuaChonSapXepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(txtSoTienDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jLabel32)
                .addGap(29, 29, 29)
                .addComponent(cboLuaChonSapXep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(btnAZ)
                .addGap(27, 27, 27)
                .addComponent(btnZA)
                .addGap(28, 28, 28)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(txtSoTienDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAZ)
                    .addComponent(btnZA)
                    .addComponent(jLabel32)
                    .addComponent(cboLuaChonSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cboNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNgay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNgayItemStateChanged(evt);
            }
        });

        jLabel33.setText("Ngày :");

        jLabel34.setText("Tháng :");

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThangItemStateChanged(evt);
            }
        });

        jLabel35.setText("Năm :");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout tabThongKeLayout = new javax.swing.GroupLayout(tabThongKe);
        tabThongKe.setLayout(tabThongKeLayout);
        tabThongKeLayout.setHorizontalGroup(
            tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabThongKeLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabThongKeLayout.createSequentialGroup()
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tabThongKeLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabThongKeLayout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboThang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabThongKeLayout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
        );
        tabThongKeLayout.setVerticalGroup(
            tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabThongKeLayout.createSequentialGroup()
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)
                            .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(15, 15, 15)
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(tabThongKeLayout.createSequentialGroup()
                        .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(15, 15, 15)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        tabs.addTab("Thống kê", tabThongKe);

        jPanel10.setBackground(new java.awt.Color(102, 204, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jblHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        jblHome.setText("Home");
        jblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblHomeMouseClicked(evt);
            }
        });

        jlbSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dog.png"))); // NOI18N
        jlbSanPham.setText("Sản phẩm");
        jlbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbSanPhamMouseClicked(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoadon.png"))); // NOI18N
        jLabel21.setText("Hóa đơn");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jlbPhieuGiamGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phieugiamgia.png"))); // NOI18N
        jlbPhieuGiamGia.setText("Phiếu giảm giá");
        jlbPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPhieuGiamGiaMouseClicked(evt);
            }
        });

        jblDotGiamGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dotgiamgia.png"))); // NOI18N
        jblDotGiamGia.setText("Đợt giảm giá");
        jblDotGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblDotGiamGiaMouseClicked(evt);
            }
        });

        jblNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nhanvien.png"))); // NOI18N
        jblNhanVien.setText("Nhân viên");
        jblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblNhanVienMouseClicked(evt);
            }
        });

        jblKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/khachhang.png"))); // NOI18N
        jblKhachHang.setText("Khách hàng");
        jblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblKhachHangMouseClicked(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thoat.png"))); // NOI18N
        jLabel22.setText("Đăng xuất");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo (1).jpg"))); // NOI18N

        jblPhieuGiaoHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Phieugiaohang.png"))); // NOI18N
        jblPhieuGiaoHang.setText("Phiếu giao hàng");
        jblPhieuGiaoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblPhieuGiaoHangMouseClicked(evt);
            }
        });

        jlbTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taikhoan.png"))); // NOI18N
        jlbTaiKhoan.setText("Tài khoản");
        jlbTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbTaiKhoanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jblHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPhieuGiamGia)
                    .addComponent(jblDotGiamGia)
                    .addComponent(jblNhanVien)
                    .addComponent(jblKhachHang)
                    .addComponent(jblPhieuGiaoHang))
                .addGap(34, 34, 34))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jblHome)
                .addGap(33, 33, 33)
                .addComponent(jlbSanPham)
                .addGap(33, 33, 33)
                .addComponent(jLabel21)
                .addGap(33, 33, 33)
                .addComponent(jblPhieuGiaoHang)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbPhieuGiamGia)
                .addGap(33, 33, 33)
                .addComponent(jblDotGiamGia)
                .addGap(33, 33, 33)
                .addComponent(jblNhanVien)
                .addGap(33, 33, 33)
                .addComponent(jblKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel22)
                .addGap(17, 17, 17))
        );

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel28.setText("HÓA ĐƠN");

        txtNgayTao.setText("dd/MM/yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(491, 491, 491)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabs))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNgayTao)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        tblSanPhamClicked = true;
        if (tblHoaDon.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn từ bảng 'Hóa đơn' hoặc tạo hóa đơn mới.");
            return;
        }

        int selectedSanPhamRow = tblSanPham.getSelectedRow();
        int columnSoLuongTonIndex = 2;
        int soLuongTon = Integer.parseInt(tblSanPham.getValueAt(selectedSanPhamRow, columnSoLuongTonIndex).toString());

        if (soLuongTon == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm đã hết.");
            return;
        }

        if (selectedSanPhamRow >= 0) {
            String maSanPham = tblSanPham.getValueAt(selectedSanPhamRow, 0).toString();
            double giaTien = Double.parseDouble(tblSanPham.getValueAt(selectedSanPhamRow, 3).toString());
            DefaultTableModel modelHoaDonChiTiet = (DefaultTableModel) tblHoaDonChiTiet.getModel();
            int selectedHoaDonRow = tblHoaDon.getSelectedRow();
            String currentMaHoaDon = "";

            if (selectedHoaDonRow >= 0) {
                currentMaHoaDon = tblHoaDon.getValueAt(selectedHoaDonRow, 0).toString();
            }

            for (int i = 0; i < tblHoaDonChiTiet.getRowCount(); i++) {
                String existingMaHD = tblHoaDonChiTiet.getValueAt(i, 0).toString();
                if (!existingMaHD.equals(currentMaHoaDon)) {
                    modelHoaDonChiTiet.removeRow(i);
                    i--;
                }
            }

            String maHoaDon = "";
            if (selectedHoaDonRow >= 0) {
                maHoaDon = tblHoaDon.getValueAt(selectedHoaDonRow, 0).toString();
            }

            if (!maHoaDon.isEmpty()) {
                boolean existed = false;
                int existingRowIndex = -1;

                for (int i = 0; i < tblHoaDonChiTiet.getRowCount(); i++) {
                    String existingMaHD = tblHoaDonChiTiet.getValueAt(i, 0).toString();
                    String existingMaBT = tblHoaDonChiTiet.getValueAt(i, 2).toString();
                    if (existingMaHD.equals(maHoaDon) && existingMaBT.equals(tblSanPham.getValueAt(selectedSanPhamRow, 1).toString())) {
                        existed = true;
                        existingRowIndex = i;
                        break;
                    }
                }

                if (!existed) {
                    String maBienThe = tblSanPham.getValueAt(selectedSanPhamRow, 1).toString();
                    int soLuong = 1;
                    double thanhTien = soLuong * giaTien;
                    String formattedThanhTien = new DecimalFormat("######").format(thanhTien);
                    modelHoaDonChiTiet.addRow(new Object[]{maHoaDon, maSanPham, maBienThe, soLuong, formattedThanhTien});

                    // Giảm số lượng sản phẩm bên tblSanPham
                    int soLuongTonMoi = Math.max(0, soLuongTon - soLuong);
                    tblSanPham.setValueAt(soLuongTonMoi, selectedSanPhamRow, columnSoLuongTonIndex);
                } else {
                    int currentQuantity = Integer.parseInt(tblHoaDonChiTiet.getValueAt(existingRowIndex, 3).toString());
                    int soLuongTonMoi = Math.max(0, soLuongTon - (currentQuantity + 1));

                    tblHoaDonChiTiet.setValueAt(currentQuantity + 1, existingRowIndex, 3);
                    double thanhTien = (currentQuantity + 1) * giaTien;
                    String formattedThanhTien = new DecimalFormat("######").format(thanhTien);
                    tblHoaDonChiTiet.setValueAt(formattedThanhTien, existingRowIndex, 4);

                    // Giảm số lượng sản phẩm bên tblSanPham
                    tblSanPham.setValueAt(soLuongTonMoi, selectedSanPhamRow, columnSoLuongTonIndex);
                }

                int rowCount = tblHoaDonChiTiet.getRowCount();
                if (rowCount > 0) {
                    tblHoaDonChiTiet.setRowSelectionInterval(0, rowCount - 1);
                    tblHoaDonChiTiet.requestFocus();
                }

                double tongTienSanPham = 0.0;
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                for (int i = 0; i < modelHoaDonChiTiet.getRowCount(); i++) {
                    String thanhTienStr = modelHoaDonChiTiet.getValueAt(i, 4).toString().replace(",", "");
                    double thanhTien = Double.parseDouble(thanhTienStr);
                    tongTienSanPham += thanhTien;
                }

                String formattedTongTienSanPham = decimalFormat.format(tongTienSanPham);
                txtTongTienCuoi.setText(formattedTongTienSanPham);
                txtSoTienNhap.setText(formattedTongTienSanPham);
            }
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void txtMaDotGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaDotGiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaDotGiamActionPerformed

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked

    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void btnTaoHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoHoaDonMouseClicked
        // TODO add your handling code here:
        if (service.insertHoaDon(readForm()) != 0) {
            JOptionPane.showMessageDialog(this, "thanh cong");
            fillHoaDon();
        }
    }//GEN-LAST:event_btnTaoHoaDonMouseClicked
    private final boolean daThanhToan = false;

    private void xoaDuLieuCuTrenTblHoaDonChiTiet() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0); // Đặt số lượng dòng về 0 để xóa hết dữ liệu cũ
    }

    private void resetSoLuongSanPhamTrongTblSanPham() {
        fillSanPham();
    }
    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        tblHoaDonClicked = true;

        // Kiểm tra nếu chưa thanh toán thì làm mới và đưa cột thứ 3 của bảng tblSanPham về như cũ
        if (!daThanhToan) {
            xoaDuLieuCuTrenTblHoaDonChiTiet();
            resetSoLuongSanPhamTrongTblSanPham();
        }

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void displayDiscountType(String maGiamGia) {
        HoaDonService hoaDonService = new HoaDonService();
        List<DuLieuChung> discountInfo = hoaDonService.findHoaDonByMaGiamGia(maGiamGia);

        if (!discountInfo.isEmpty()) {
            DuLieuChung duLieuChung = discountInfo.get(0);
            PhieuGiamGia phieuGiamGia = duLieuChung.getPgg();
            DieuKienGiamGia dieuKienGiamGia = duLieuChung.getDkgg();

            BigDecimal discountAmount = calculateDiscountAmount(phieuGiamGia, dieuKienGiamGia, BigDecimal.ZERO);

            String loaiGiamGia = dieuKienGiamGia.getLoaiGiamGia();
            DecimalFormat formatter = new DecimalFormat("#,###");

            // Hiển thị giá trị giảm giá tùy thuộc vào loại giảm giá từ cơ sở dữ liệu
            if ("Giảm phần trăm".equals(loaiGiamGia)) {
                lblGiaTriGiamPGG.setText(formatter.format(discountAmount) + "%"); // Hiển thị giá trị phần trăm
            } else if ("Giảm tiền".equals(loaiGiamGia)) {
                lblGiaTriGiamPGG.setText(formatter.format(discountAmount) + " VND"); // Hiển thị giá trị tiền
            }
        }
    }

    private BigDecimal calculateDiscountAmount(PhieuGiamGia phieuGiamGia, DieuKienGiamGia dieuKienGiamGia, BigDecimal totalBillAmount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        int giaTriGiam = dieuKienGiamGia.getGiaTriGiam();

        if ("Giảm phần trăm".equals(dieuKienGiamGia.getLoaiGiamGia())) {
            BigDecimal percentage = BigDecimal.valueOf(giaTriGiam).divide(BigDecimal.valueOf(100));
            BigDecimal percentageDiscount = totalBillAmount.multiply(percentage);
            discountAmount = discountAmount.add(percentageDiscount);
        } else if ("Giảm tiền".equals(dieuKienGiamGia.getLoaiGiamGia())) {
            discountAmount = discountAmount.add(BigDecimal.valueOf(giaTriGiam));
        }

        return discountAmount;
    }

    private void tblLichSuHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuHoaDonMouseClicked

        int selectedRow = tblLichSuHoaDon.getSelectedRow();
        if (selectedRow >= 0 && tblLichSuHoaDon.getModel().getValueAt(selectedRow, 0) != null) {
            String maHoaDon = tblLichSuHoaDon.getModel().getValueAt(selectedRow, 0).toString();
            HoaDonService hoaDonService = new HoaDonService();

            // Lấy thông tin hóa đơn dựa trên mã hóa đơn
            HoaDon hoaDon = hoaDonService.getHoaDonByMa(maHoaDon);

            // Hiển thị thông tin hóa đơn lên các JLabel
            lblMaKH.setText(hoaDon.getMaKhachHang());
            lblMaNV.setText(hoaDon.getMaNhanVien());
            lblNgayTao.setText(hoaDon.getNgayTao());
            lblMaDGG.setText(hoaDon.getMaDGG());
            lblMaPGG.setText(hoaDon.getMaGiamGia());
            lblVanChuyen.setText(hoaDon.isVanChuyen() ? "Có" : "Không");
            int giaTriGiamDGG = hoaDon.getGiaTriGiamDGG();
            int giaTriGiamPGG = hoaDon.getGiaTriGiamPGG();
            String loaiGiamGia = hoaDonService.getLoaiGiamGiaByMa(hoaDon.getMaGiamGia());

            if (loaiGiamGia != null) {
                String giaTriGiamTextDGG = "";
                if ("Giảm phần trăm".equalsIgnoreCase(loaiGiamGia)) {
                    giaTriGiamTextDGG = giaTriGiamDGG + "%";
                } else if ("Giảm tiền".equalsIgnoreCase(loaiGiamGia)) {
                    giaTriGiamTextDGG = giaTriGiamDGG + " VND";
                }
                lblGiaTriGiamDGG.setText(giaTriGiamTextDGG);
            }
            if (loaiGiamGia != null) {
                String giaTriGiamTextPGG = "";
                if ("Giảm phần trăm".equalsIgnoreCase(loaiGiamGia)) {
                    giaTriGiamTextPGG = giaTriGiamPGG + "%";
                } else if ("Giảm tiền".equalsIgnoreCase(loaiGiamGia)) {
                    giaTriGiamTextPGG = giaTriGiamPGG + " VND";
                }
                lblGiaTriGiamPGG.setText(giaTriGiamTextPGG);
            }

            DecimalFormat decimalFormat = new DecimalFormat("#,### VND"); // Định dạng số tiền theo VND

            lblSoTienGiam.setText(decimalFormat.format(hoaDon.getSoTienGiam()));
            lblTongTienGiam.setText(decimalFormat.format(hoaDon.getTongTien()));
            lblDaThanhToan.setText(decimalFormat.format(hoaDon.getSoTienThanhToan()));
            // Lấy thông tin chi tiết hóa đơn và sản phẩm
            List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonService.getHoaDonChiTietByMaHoaDon(maHoaDon);

            if (!listHoaDonChiTiet.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (HoaDonChiTiet hoaDonChiTiet : listHoaDonChiTiet) {
                    sb.append("Mã sản phẩm: ").append(hoaDonChiTiet.getMaBT()).append("\n");
                    sb.append("Số lượng: ").append(hoaDonChiTiet.getSoLuong()).append("\n");
                    sb.append("Tổng tiền: ").append(decimalFormat.format(hoaDonChiTiet.getTongTien())).append("\n");
                    sb.append("\n");
                }
                // Hiển thị thông tin sản phẩm lên JLabel
                lblThongTinSP.setText(sb.toString());
            }

            // Hiển thị giá trị giảm theo loại giảm giá từ điều kiện giảm giá
            displayDiscountType(hoaDon.getMaGiamGia());
        }
    }//GEN-LAST:event_tblLichSuHoaDonMouseClicked

    private void txtMaPGGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaPGGFocusLost

    }//GEN-LAST:event_txtMaPGGFocusLost

    private void txtGiaTriGiamDGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriGiamDGGActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtGiaTriGiamDGGActionPerformed

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        if (!tblSanPhamClicked) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ bảng 'Sản phẩm'.");
            return;
        }

        if (!tblHoaDonClicked && tblHoaDon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn từ bảng 'Hóa đơn' hoặc tạo hóa đơn mới.");
            return;
        }
        if (!checkDuLieu()) {
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thanh toán?", "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            HoaDon hoaDonToUpdate = readFormUpdate();
            int[] selectedRows = tblHoaDonChiTiet.getSelectedRows();
            List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
            for (int selectedRow : selectedRows) {
                String maHoaDon = tblHoaDonChiTiet.getValueAt(selectedRow, 0).toString();
                String maBT = tblHoaDonChiTiet.getValueAt(selectedRow, 2).toString();
                int soLuong = Integer.parseInt(tblHoaDonChiTiet.getValueAt(selectedRow, 3).toString());
                BigDecimal tongTien = new BigDecimal(tblHoaDonChiTiet.getValueAt(selectedRow, 4).toString());

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(maHoaDon, maBT, soLuong, tongTien);
                hoaDonChiTietList.add(hoaDonChiTiet);
            }
            if (service.updateHoaDon(hoaDonToUpdate) != 0) {
                int rowsInserted = service.insertAllHoaDonChiTiet(hoaDonChiTietList);
                if (rowsInserted == selectedRows.length) {
                    // Thực hiện thanh toán thành công
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                    guiMail(service.getHoaDon(), hoaDonChiTietList);

                    txtGiaTriGiamDGG.setText("");
                    txtGiaTriGiamPGG.setText("");
                    txtSoTienGiam.setText("");
                    txtTongTienCuoi.setText("");

                    String maGiamGia = txtMaPGG.getText();
                    String maSanPham = tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 0).toString();

                    // Giảm số lượng sản phẩm
                    service.decreaseQuantityByOne(maGiamGia);

                    List<String> maBienTheList = new ArrayList<>();
                    for (int selectedRow : selectedRows) {
                        int columnIndexMaBienThe = 2; // Assuming the column index containing 'maBienThe' is 1
                        if (columnIndexMaBienThe >= 0 && columnIndexMaBienThe < tblHoaDonChiTiet.getColumnCount()) {
                            Object value = tblHoaDonChiTiet.getValueAt(selectedRow, columnIndexMaBienThe);
                            if (value != null) {
                                String maBienThe = value.toString();
                                maBienTheList.add(maBienThe);
                            }
                        }
                    }

                    if (!maBienTheList.isEmpty()) {
                        // Giảm số lượng biến thể sản phẩm
                        service.decreaseQuantityByOneSP(maSanPham);
                        service.deleteVariantsSP(maBienTheList);
                    }

                    // Làm mới dữ liệu
                    fillSanPham();
                    fillHoaDon();
                    fillLichSuHD();
                    thongke();
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm dữ liệu vào HoaDonChiTiet.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin HoaDon không thành công. Vui lòng thử lại.");
            }
        }
    }//GEN-LAST:event_btnThanhToanMouseClicked
    private String cleanNumericString(String input) {
        return input.replaceAll("[^\\d.]", ""); // Keep digits and decimal points
    }

// Method to convert percentage string to numeric value
    private int convertPercentageStringToNumber(String input) {
        String cleaned = cleanNumericString(input);
        try {
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            // Handle invalid or non-numeric inputs
            return 0; // Return default value or handle accordingly
        }
    }

    private void btnNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhapMouseClicked
        String maDGG = txtMaDotGiam.getText();
        String maPGG = txtMaPGG.getText();
        String discountTextPGG = getDiscountText(maPGG);
        String pgg = "";
        String dgg = "";

        if (maDGG.isEmpty() && maPGG.isEmpty()) {
            JOptionPane.showMessageDialog(this, "MaDGG và MaPGG trống");
        } else if (!maDGG.isEmpty() && maPGG.isEmpty()) {
            applyPercentageDiscountToBill(maDGG);
            txtGiaTriGiamPGG.setText("");
        } else if (maDGG.isEmpty() && !maPGG.isEmpty()) {
            applyDiscountToBill();
            txtGiaTriGiamDGG.setText("");
        } else if (!maDGG.isEmpty() && !maPGG.isEmpty()) {
            // Your logic for non-empty maDGG and maPGG goes here...
            if (discountTextPGG != null && discountTextPGG.contains("VND")) {
                if (isDiscountExpired(maPGG)) {
                    txtMaPGG.setText("");
                    JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết hạn.");
                    return;
                } else if (isDiscountUsedUp(maPGG)) {
                    txtMaPGG.setText("");
                    JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết số lượng.");
                    return;
                }
                if (!maDGG.isEmpty()) {
                    pgg = service.getDiscountValueForPGG(maPGG);
                }
                if (!maPGG.isEmpty()) {
                    dgg = service.getDiscountValueForDGG(maDGG);
                }
                String totalAfterDiscountStr = cleanNumericString(txtTongTienCuoi.getText());
                int totalAfterDiscount = Integer.parseInt(totalAfterDiscountStr);

                int discountPercent = convertPercentageStringToNumber(dgg);
                int giamlan1 = totalAfterDiscount * discountPercent / 100;
                System.out.println(giamlan1);
                int giamlan2 = totalAfterDiscount - giamlan1;
                System.out.println(giamlan2);
                int giamlan3 = giamlan2 - convertPercentageStringToNumber(pgg);
                System.out.println(giamlan3);
                int tongtien = giamlan1 + convertPercentageStringToNumber(pgg);
                int finalAmount = giamlan3;

                // Handle final amount less than 0
                if (finalAmount < 0) {
                    finalAmount = 0;
                }

                // Format numbers back to display
                String tongtiencuoi = String.format("%,d VND", finalAmount);
                String sotiengiam = String.format("%,d VND", tongtien);

                // Set text to respective fields
                txtTongTienCuoi.setText(tongtiencuoi);
                txtSoTienNhap.setText(tongtiencuoi);
                txtGiaTriGiamDGG.setText(cleanNumericString(dgg) + "%");
                txtGiaTriGiamPGG.setText(cleanNumericString(pgg) + "%");
                txtSoTienGiam.setText(sotiengiam);

            } else {
                if (isDiscountExpired(maPGG)) {
                    JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết hạn.");
                    txtMaPGG.setText("");
                    return;
                } else if (isDiscountUsedUp(maPGG)) {
                    JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã hết số lượng.");
                    txtMaPGG.setText("");
                    return;
                }
                if (!maDGG.isEmpty()) {
                    pgg = service.getDiscountValueForPGG(maPGG);
                }
                if (!maPGG.isEmpty()) {
                    dgg = service.getDiscountValueForDGG(maDGG);
                }
                int tongPhanTramGiam = Integer.parseInt(dgg) + Integer.parseInt(pgg);
                System.out.println(tongPhanTramGiam);
                BigDecimal totalBillAmount = calculateTotalBillAmount();
                BigDecimal percentageValue = BigDecimal.valueOf(tongPhanTramGiam).divide(BigDecimal.valueOf(100));
                BigDecimal discountAmount = totalBillAmount.multiply(percentageValue);
                BigDecimal discountedTotalBill = totalBillAmount.subtract(discountAmount);
                if (discountedTotalBill.compareTo(BigDecimal.ZERO) < 0) {
                    discountedTotalBill = BigDecimal.ZERO;
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
                String tongtiencuoi = decimalFormat.format(discountedTotalBill.doubleValue()) + " VND";
                String sotiengiam = decimalFormat.format(discountAmount.doubleValue()) + " VND";
                txtTongTienCuoi.setText(tongtiencuoi);
                txtSoTienNhap.setText(tongtiencuoi);
                txtGiaTriGiamDGG.setText(dgg + "%");
                txtGiaTriGiamPGG.setText(pgg + "%");
                txtSoTienGiam.setText(sotiengiam);
            }
        }
    }

    private void updateFieldsBasedOnMaHoaDon(String maHoaDonMoi) {
        if (maHoaDonMoi != null && !maHoaDonMoi.isEmpty()) {
            int rowCount = tblHoaDon.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String maHoaDonCot1 = tblHoaDon.getValueAt(i, 1).toString();
                if (maHoaDonCot1.equals(maHoaDonMoi)) {
                    btnNhap.setEnabled(true); // Nếu mã hóa đơn thỏa mãn điều kiện, mở btnNhap
                    clear();
                    return; // Thoát khỏi vòng lặp nếu tìm thấy mã hóa đơn và đã thiết lập btnNhap
                }
            }
            btnNhap.setEnabled(false); // Nếu không tìm thấy mã hóa đơn, vô hiệu hóa btnNhap
            clear();
        }
    }

    public void thaydoihoadon() {
        tblHoaDon.getModel().addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {
                // Lấy mã hóa đơn mới từ cột số 0 của tblHoaDon
                int row = tblHoaDon.getSelectedRow();
                String maHoaDonHienTai = tblHoaDon.getValueAt(row, 0).toString();
                System.out.println(maHoaDonHienTai);

                // Thực hiện các hành động khi mã hóa đơn thay đổi
                updateFieldsBasedOnMaHoaDon(maHoaDonHienTai);
            }
        });
    }

    private String getDiscountText(String pgg) {
        HoaDonService hoaDonService = new HoaDonService();
        List<DieuKienGiamGia> discountInfo = hoaDonService.timloaigiamgia(pgg);

        if (!discountInfo.isEmpty()) {
            DieuKienGiamGia dieuKienGiamGia = discountInfo.get(0);

            BigDecimal discountAmount = BigDecimal.valueOf(dieuKienGiamGia.getGiaTriGiam());
            String loaiGiamGia = dieuKienGiamGia.getLoaiGiamGia();

            if ("Giảm phần trăm".equals(loaiGiamGia)) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                return formatter.format(discountAmount) + "%";
            } else if ("Giảm tiền".equals(loaiGiamGia)) {
                DecimalFormat formatter = new DecimalFormat("#,###");
                return formatter.format(discountAmount) + "VND";
            }
        }
        return null;
    }

    private int calculateTotalDiscountPercentage(List<Integer> discountPercentages, List<Map<String, Object>> discountDetails) {
        int totalDiscountPercentage = 0;

        for (Integer percentage : discountPercentages) {
            totalDiscountPercentage += percentage;
        }

        for (Map<String, Object> detail : discountDetails) {
            Object discountPercentageObj = detail.get("DiscountPercentage");
            if (discountPercentageObj != null && discountPercentageObj instanceof Integer) {
                int discountPercentage = ((Integer) discountPercentageObj);
                totalDiscountPercentage += discountPercentage;
            }
        }

        return totalDiscountPercentage;

    }//GEN-LAST:event_btnNhapMouseClicked

    private void txtGiaTriGiamPGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriGiamPGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriGiamPGGActionPerformed

    private void jblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblHomeMouseClicked
        // TODO add your handling code here:
        TrangChuChinhView tccv = new TrangChuChinhView();
        tccv.setVisible(true);
    }//GEN-LAST:event_jblHomeMouseClicked

    private void jlbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSanPhamMouseClicked
        Chinh c = new Chinh();
        c.setVisible(true);
    }//GEN-LAST:event_jlbSanPhamMouseClicked

    private void jlbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPhieuGiamGiaMouseClicked
        PhieuGiamGiaView pggv = new PhieuGiamGiaView();
        pggv.setVisible(true);
    }//GEN-LAST:event_jlbPhieuGiamGiaMouseClicked

    private void jblDotGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblDotGiamGiaMouseClicked
        DotGiamGiaView dggv = new DotGiamGiaView();
        dggv.setVisible(true);
    }//GEN-LAST:event_jblDotGiamGiaMouseClicked

    private void jblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblNhanVienMouseClicked
        NhanVienView nvv = new NhanVienView();
        nvv.setVisible(true);
    }//GEN-LAST:event_jblNhanVienMouseClicked

    private void jblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblKhachHangMouseClicked
        KhachHangView khv = new KhachHangView();
        khv.setVisible(true);
    }//GEN-LAST:event_jblKhachHangMouseClicked

    private void jlbTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbTaiKhoanMouseClicked
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_jlbTaiKhoanMouseClicked

    private void btnXoaLichSuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaLichSuMouseClicked
        String maHoaDon = tblLichSuHoaDon.getValueAt(tblLichSuHoaDon.getSelectedRow(), 0).toString();
        System.out.println("Mã Hóa Đơn: " + maHoaDon);

        if (service.deleteHoaDonAndChiTietByMaHoaDon(maHoaDon)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            fillLichSuHD();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa không thành công");
        }
    }//GEN-LAST:event_btnXoaLichSuMouseClicked
    private BigDecimal getTongTienHoaDonTheoNgay(java.sql.Date ngay) {
        BigDecimal tongTienNgay = BigDecimal.ZERO;
        List<HoaDon> hoaDonList = service.getHoaDonTheoNgay(ngay);
        for (HoaDon hoaDon : hoaDonList) {
            tongTienNgay = tongTienNgay.add(hoaDon.getTongTien());
        }
        return tongTienNgay;
    }

    private void tongtienngay() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ngay = txtNgayTao.getText();

        try {
            Date parsedDate = inputDateFormat.parse(ngay);
            String ngayChiTiet = outputDateFormat.format(parsedDate);
            java.sql.Date ngaySQL = java.sql.Date.valueOf(ngayChiTiet);
            BigDecimal tongTienNgay = getTongTienHoaDonTheoNgay(ngaySQL);

            // Định dạng số tiền thành tiền tệ VND
            DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
            String formattedTongTien = decimalFormat.format(tongTienNgay);

            txtTongTatCaNgay.setText(formattedTongTien);
            System.out.println(formattedTongTien);

        } catch (ParseException ex) {
            // Xử lý ngoại lệ ParseException
        }
    }

    private void txtTongTatCaNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTatCaNgayActionPerformed

    }//GEN-LAST:event_txtTongTatCaNgayActionPerformed

    public void tongtienthuduocngay() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ngay = txtNgayTao.getText();

        try {
            Date parsedDate = inputDateFormat.parse(ngay);
            String ngayChiTiet = outputDateFormat.format(parsedDate);
            java.sql.Date ngaySQL = java.sql.Date.valueOf(ngayChiTiet);

            BigDecimal tongTienThuDuocNgay = service.tongSoTienThanhToanTheoNgay(ngaySQL);

            if (tongTienThuDuocNgay != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienThuDuocNgay = decimalFormat.format(tongTienThuDuocNgay);

                txtTongThuDuocNgay.setText(formattedTongTienThuDuocNgay);
            } else {
                txtTongThuDuocNgay.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace(); // Xử lý ngoại lệ ParseException
        }
    }

    private void txtTongThuDuocNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongThuDuocNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongThuDuocNgayActionPerformed
    public void tongtienthang() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputMonthFormat = new SimpleDateFormat("yyyy-MM");

        String ngayTao = txtNgayTao.getText(); // Định dạng ngày tạo: "yyyy/MM/dd HH:mm:ss"

        try {
            Date parsedDate = inputDateFormat.parse(ngayTao);
            String thangNam = outputMonthFormat.format(parsedDate);

            // Chuyển đổi tháng và năm thành chuỗi "yyyy-MM" để lấy ngày đầu và cuối tháng
            java.util.Date parsedMonth = outputMonthFormat.parse(thangNam);
            java.sql.Date ngayDauThang = new java.sql.Date(parsedMonth.getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            java.util.Date firstDayOfMonth = calendar.getTime();
            java.sql.Date ngayDauThangSQL = new java.sql.Date(firstDayOfMonth.getTime());

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            java.util.Date lastDayOfMonth = calendar.getTime();
            java.sql.Date ngayCuoiThang = new java.sql.Date(lastDayOfMonth.getTime());

            BigDecimal tongTienThang = service.tongTienHoaDonTrongKhoangThoiGian(ngayDauThangSQL, ngayCuoiThang);

            if (tongTienThang != null) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienThang = decimalFormat.format(tongTienThang);

                txtTongTatCaThang.setText(formattedTongTienThang);
            } else {
                // Xử lý khi giá trị là null
                txtTongTatCaThang.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongTatCaThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTatCaThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTatCaThangActionPerformed
    public void tongthuthang() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputMonthFormat = new SimpleDateFormat("yyyy-MM");

        String ngayTao = txtNgayTao.getText(); // Ví dụ: "2023-12-10"

        try {
            java.util.Date parsedDate = inputDateFormat.parse(ngayTao);

            // Lấy tháng và năm từ ngày được nhập vào
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0

            // Tạo chuỗi tháng và năm theo định dạng "yyyy-MM"
            String thangNam = String.format("%04d-%02d", year, month);

            // Chuyển đổi chuỗi tháng-năm về định dạng Date
            java.util.Date parsedMonthDate = outputMonthFormat.parse(thangNam);
            java.sql.Date ngayDauThang = new java.sql.Date(parsedMonthDate.getTime());

            // Lấy ngày cuối tháng
            calendar.setTime(parsedMonthDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            java.util.Date lastDayOfMonth = calendar.getTime();
            java.sql.Date ngayCuoiThang = new java.sql.Date(lastDayOfMonth.getTime());

            BigDecimal tongTienThanhToanThang = service.tongTienThanhToanTrongThang(ngayDauThang, ngayCuoiThang);

            // Kiểm tra nếu giá trị không phải là null và là số
            if (tongTienThanhToanThang != null && !BigDecimal.ZERO.equals(tongTienThanhToanThang)) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienThanhToanThang = decimalFormat.format(tongTienThanhToanThang);

                txtTongThuThang.setText(formattedTongTienThanhToanThang);
            } else {
                // Xử lý khi giá trị là null hoặc không phải là số
                txtTongThuThang.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongThuThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongThuThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongThuThangActionPerformed

    public void tongTatCaNam() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputYearFormat = new SimpleDateFormat("yyyy");

        String nam = txtNgayTao.getText(); // Ví dụ: "2023-12-10"

        try {
            java.util.Date parsedDate = inputDateFormat.parse(nam);

            // Lấy năm từ ngày được nhập vào
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int year = calendar.get(Calendar.YEAR);

            // Tạo chuỗi năm theo định dạng "yyyy"
            String namFormatted = outputYearFormat.format(parsedDate);

            // Chuyển đổi chuỗi năm về định dạng Date
            java.util.Date parsedYearDate = outputYearFormat.parse(namFormatted);
            java.sql.Date ngayDauNam = new java.sql.Date(parsedYearDate.getTime());

            // Lấy ngày cuối năm
            calendar.setTime(parsedYearDate);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            java.util.Date lastDayOfYear = calendar.getTime();
            java.sql.Date ngayCuoiNam = new java.sql.Date(lastDayOfYear.getTime());

            BigDecimal tongTienNam = service.tongTienHoaDonTrongKhoangThoiGian(ngayDauNam, ngayCuoiNam);

            if (tongTienNam != null && !BigDecimal.ZERO.equals(tongTienNam)) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienNam = decimalFormat.format(tongTienNam);

                txtTongTatCaNam.setText(formattedTongTienNam);
            } else {
                // Xử lý khi giá trị là null hoặc không phải là số
                txtTongTatCaNam.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongTatCaNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTatCaNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTatCaNamActionPerformed

    public void tongthunam() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputYearFormat = new SimpleDateFormat("yyyy");

        String nam = txtNgayTao.getText(); // Ví dụ: "2023-12-10"

        try {
            java.util.Date parsedDate = inputDateFormat.parse(nam);

            // Lấy năm từ ngày được nhập vào
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int year = calendar.get(Calendar.YEAR);

            // Tạo chuỗi năm theo định dạng "yyyy"
            String namFormatted = outputYearFormat.format(parsedDate);

            // Chuyển đổi chuỗi năm về định dạng Date
            java.util.Date parsedYearDate = outputYearFormat.parse(namFormatted);
            java.sql.Date ngayDauNam = new java.sql.Date(parsedYearDate.getTime());

            // Lấy ngày cuối năm
            calendar.setTime(parsedYearDate);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER); // Đặt tháng là tháng 12 của năm trước
            calendar.set(Calendar.DAY_OF_MONTH, 31); // Đặt ngày là ngày cuối cùng của tháng 12
            java.util.Date lastDayOfPrevYear = calendar.getTime();
            java.sql.Date ngayCuoiNam = new java.sql.Date(lastDayOfPrevYear.getTime());

            BigDecimal tongTienNam = service.tongTienThanhToanTrongKhoangThoiGian(ngayDauNam, ngayCuoiNam);

            if (tongTienNam != null && !BigDecimal.ZERO.equals(tongTienNam)) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienNam = decimalFormat.format(tongTienNam);

                txtTongThuNam.setText(formattedTongTienNam);
            } else {
                // Xử lý khi giá trị là null hoặc không phải là số
                txtTongThuNam.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongThuNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongThuNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongThuNamActionPerformed
    public void tonggiamngay() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ngay = txtNgayTao.getText();

        try {
            Date parsedDate = inputDateFormat.parse(ngay);
            String ngayChiTiet = outputDateFormat.format(parsedDate);
            java.sql.Date ngaySQL = java.sql.Date.valueOf(ngayChiTiet);

            BigDecimal tongTienGiamGiaNgay = service.getTongTienHoaDonGiamGiaTheoNgay(ngaySQL);

            if (tongTienGiamGiaNgay != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###VND");
                String formattedTongTienGiamGiaNgay = decimalFormat.format(tongTienGiamGiaNgay);

                txtTongGiamGiaNgay.setText(formattedTongTienGiamGiaNgay);
            } else {
                txtTongGiamGiaNgay.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace(); // Xử lý ngoại lệ ParseException
        }
    }
    private void txtTongGiamGiaNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongGiamGiaNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongGiamGiaNgayActionPerformed
    public void tongiamthang() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputMonthFormat = new SimpleDateFormat("yyyy-MM");

        String ngayTao = txtNgayTao.getText(); // Định dạng ngày tạo: "yyyy/MM/dd HH:mm:ss"

        try {
            Date parsedDate = inputDateFormat.parse(ngayTao);
            String thangNam = outputMonthFormat.format(parsedDate);

            // Chuyển đổi tháng và năm thành chuỗi "yyyy-MM" để lấy ngày đầu và cuối tháng
            java.util.Date parsedMonth = outputMonthFormat.parse(thangNam);
            java.sql.Date ngayDauThang = new java.sql.Date(parsedMonth.getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            java.util.Date lastDayOfMonth = calendar.getTime();
            java.sql.Date ngayCuoiThang = new java.sql.Date(lastDayOfMonth.getTime());

            BigDecimal tongTienGiamGiaThang = service.tongTienGiamGiaTrongThang(ngayDauThang, ngayCuoiThang);

            if (tongTienGiamGiaThang != null) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienGiamGiaThang = decimalFormat.format(tongTienGiamGiaThang);

                txtTongGiamGiaThang.setText(formattedTongTienGiamGiaThang);
            } else {
                // Xử lý khi giá trị là null
                txtTongGiamGiaThang.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongGiamGiaThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongGiamGiaThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongGiamGiaThangActionPerformed
    public void tongGiamGiaNam() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputYearFormat = new SimpleDateFormat("yyyy");

        String nam = txtNgayTao.getText(); // Ví dụ: "2023-12-10"

        try {
            java.util.Date parsedDate = inputDateFormat.parse(nam);

            // Lấy năm từ ngày được nhập vào
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int year = calendar.get(Calendar.YEAR);

            // Tạo chuỗi năm theo định dạng "yyyy"
            String namFormatted = outputYearFormat.format(parsedDate);

            // Chuyển đổi chuỗi năm về định dạng Date
            java.util.Date parsedYearDate = outputYearFormat.parse(namFormatted);
            java.sql.Date ngayDauNam = new java.sql.Date(parsedYearDate.getTime());

            // Lấy ngày cuối năm
            calendar.setTime(parsedYearDate);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            java.util.Date lastDayOfYear = calendar.getTime();
            java.sql.Date ngayCuoiNam = new java.sql.Date(lastDayOfYear.getTime());

            BigDecimal tongTienGiamGiaNam = service.tongTienGiamGiaTrongKhoangThoiGian(ngayDauNam, ngayCuoiNam);

            if (tongTienGiamGiaNam != null && !BigDecimal.ZERO.equals(tongTienGiamGiaNam)) {
                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTienGiamGiaNam = decimalFormat.format(tongTienGiamGiaNam);

                txtTongGiamGiaNam.setText(formattedTongTienGiamGiaNam);
            } else {
                // Xử lý khi giá trị là null hoặc không phải là số
                txtTongGiamGiaNam.setText("0 VND");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void txtTongGiamGiaNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongGiamGiaNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongGiamGiaNamActionPerformed
    private void ngay() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 31; i++) {
            model.addElement(String.valueOf(i));
        }
        cboNgay.setModel(model);
    }

    private void thang() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 12; i++) {
            model.addElement(String.valueOf(i));
        }
        cboThang.setModel(model);
    }

    private void nam() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        int startYear = 2023; // Năm bắt đầu

        for (int i = startYear; i <= currentYear; i++) {
            model.addElement(String.valueOf(i));
        }

        cboNam.setModel(model);
    }
    private void cboNgayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNgayItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            Object selectedDate = evt.getItem();

            if (selectedDate instanceof String) {
                String selectedDay = (String) selectedDate;
                int day = Integer.parseInt(selectedDay);

                // Tạo một ngày mới từ ngày được chọn và hiển thị dữ liệu tương ứng
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.set(java.util.Calendar.DAY_OF_MONTH, day);

                java.util.Date selectedUtilDate = calendar.getTime();
                java.sql.Date selectedSqlDate = new java.sql.Date(selectedUtilDate.getTime());

                // Lấy dữ liệu tương ứng với ngày đã chọn
                BigDecimal tongTatCaNgay = getTongTienHoaDonTheoNgay(selectedSqlDate);
                BigDecimal tongGiamGiaNgay = service.getTongTienHoaDonGiamGiaTheoNgay(selectedSqlDate);
                BigDecimal tongThuDuocNgay = service.getTongTienHoaDonThuDuocTheoNgay(selectedSqlDate);

                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTatCaNgay = (tongTatCaNgay != null) ? decimalFormat.format(tongTatCaNgay) : "0 VND";
                String formattedTongGiamGiaNgay = (tongGiamGiaNgay != null) ? decimalFormat.format(tongGiamGiaNgay) : "0 VND";
                String formattedTongThuDuocNgay = (tongThuDuocNgay != null) ? decimalFormat.format(tongThuDuocNgay) : "0 VND";

                // Hiển thị dữ liệu tương ứng trong các textfield hoặc thông báo nếu không tìm thấy
                txtTongTatCaNgay.setText(formattedTongTatCaNgay);
                txtTongGiamGiaNgay.setText(formattedTongGiamGiaNgay);
                txtTongThuDuocNgay.setText(formattedTongThuDuocNgay);
            }
        }
    }//GEN-LAST:event_cboNgayItemStateChanged
    private java.util.Date createDate(int year, int month, int day) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month - 1, day); // Tháng trong Calendar bắt đầu từ 0
        return calendar.getTime();
    }

    private int getDaysInMonth(int month, int year) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Thiết lập ngày đầu tiên của tháng
        return calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }
    private void cboThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThangItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            Object selectedMonth = evt.getItem();

            if (selectedMonth instanceof String) {
                String selectedMonthStr = (String) selectedMonth;
                int month = Integer.parseInt(selectedMonthStr);

                // Lấy năm hiện tại
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int currentYear = calendar.get(java.util.Calendar.YEAR);

                // Tạo ngày đầu và ngày cuối của tháng đã chọn
                java.util.Date startDate = createDate(currentYear, month, 1);
                java.util.Date endDate = createDate(currentYear, month, getDaysInMonth(month, currentYear));

                // Chuyển đổi thành kiểu java.sql.Date
                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

                // Lấy dữ liệu tương ứng với tháng đã chọn
                BigDecimal tongTatCaThang = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);
                BigDecimal tongGiamGiaThang = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);
                BigDecimal tongThuThang = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);

                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTatCaThang = (tongTatCaThang != null) ? decimalFormat.format(tongTatCaThang) : "";
                String formattedTongGiamGiaThang = (tongGiamGiaThang != null) ? decimalFormat.format(tongGiamGiaThang) : "";
                String formattedTongThuThang = (tongThuThang != null) ? decimalFormat.format(tongThuThang) : "";

                // Hiển thị dữ liệu tương ứng trong các textfield
                txtTongTatCaThang.setText(formattedTongTatCaThang);
                txtTongGiamGiaThang.setText(formattedTongGiamGiaThang);
                txtTongThuThang.setText(formattedTongThuThang);
            }
        }
    }//GEN-LAST:event_cboThangItemStateChanged

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            Object selectedYear = evt.getItem();

            if (selectedYear instanceof String) {
                String selectedYearStr = (String) selectedYear;
                int year = Integer.parseInt(selectedYearStr);

                // Tạo ngày đầu và ngày cuối của năm đã chọn
                java.util.Date startDate = createDate(year, 1, 1); // Ngày đầu tiên của năm
                java.util.Date endDate = createDate(year, 12, 31); // Ngày cuối cùng của năm

                // Chuyển đổi thành kiểu java.sql.Date
                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

                // Lấy dữ liệu tương ứng với năm đã chọn
                BigDecimal tongTatCaNam = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);
                BigDecimal tongGiamGiaNam = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);
                BigDecimal tongThuNam = service.getTongTienHoaDonTrongKhoangThoiGian(sqlStartDate, sqlEndDate);

                // Định dạng số tiền thành tiền tệ VND
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                String formattedTongTatCaNam = (tongTatCaNam != null) ? decimalFormat.format(tongTatCaNam) : "0 VND";
                String formattedTongGiamGiaNam = (tongGiamGiaNam != null) ? decimalFormat.format(tongGiamGiaNam) : "0 VND";
                String formattedTongThuNam = (tongThuNam != null) ? decimalFormat.format(tongThuNam) : "0 VND";

                // Hiển thị dữ liệu tương ứng trong các textfield
                txtTongTatCaNam.setText(formattedTongTatCaNam);
                txtTongGiamGiaNam.setText(formattedTongGiamGiaNam);
                txtTongThuNam.setText(formattedTongThuNam);
            }
        }
    }//GEN-LAST:event_cboNamItemStateChanged

    private void txtSoTienDanhGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTienDanhGiaActionPerformed
        fillTableThanhTich();
    }//GEN-LAST:event_txtSoTienDanhGiaActionPerformed

    private void cboLuaChonSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLuaChonSapXepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLuaChonSapXepActionPerformed

    private void cboLuaChonSapXepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLuaChonSapXepItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedType = cboLuaChonSapXep.getSelectedItem().toString();

            switch (selectedType) {
                case "Mã nhân viên":
                    // Sắp xếp Mã nhân viên theo thứ tự tăng dần (A-Z)
                    sortTableAscending(0);
                    break;
                case "Số lượng tạo":
                    // Sắp xếp Số lượng tạo theo thứ tự tăng dần (A-Z)
                    sortTableAscending(1);
                    break;
                case "Tổng tiền":
                    // Sắp xếp Tổng tiền theo thứ tự tăng dần (A-Z)
                    sortTableAscending(2);
                    break;
                case "Đánh giá":
                    // Sắp xếp Đánh giá theo thứ tự tăng dần (A-Z)
                    sortTableAscending(3);
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_cboLuaChonSapXepItemStateChanged

    private void btnAZMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAZMouseClicked
        String selectedType = cboLuaChonSapXep.getSelectedItem().toString();

        switch (selectedType) {
            case "Mã nhân viên":
                // Sắp xếp Mã nhân viên theo thứ tự tăng dần (A-Z)
                sortTableAscending(0);
                break;
            case "Số lượng tạo":
                // Sắp xếp Số lượng tạo theo thứ tự tăng dần (A-Z)
                sortTableAscending(1);
                break;
            case "Tổng tiền":
                // Sắp xếp Tổng tiền theo thứ tự tăng dần (A-Z)
                sortTableAscending(2);
                break;
            case "Đánh giá":
                // Sắp xếp Đánh giá theo thứ tự tăng dần (A-Z)
                sortTableAscending(3);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnAZMouseClicked

    private void btnZAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnZAMouseClicked
        String selectedType = cboLuaChonSapXep.getSelectedItem().toString();

        switch (selectedType) {
            case "Mã nhân viên":
                // Sắp xếp Mã nhân viên theo thứ tự giảm dần (Z-A)
                sortTableDescending(0);
                break;
            case "Số lượng tạo":
                // Sắp xếp Số lượng tạo theo thứ tự giảm dần (Z-A)
                sortTableDescending(1);
                break;
            case "Tổng tiền":
                // Sắp xếp Tổng tiền theo thứ tự giảm dần (Z-A)
                sortTableDescending(2);
                break;
            case "Đánh giá":
                // Sắp xếp Đánh giá theo thứ tự giảm dần (Z-A)
                sortTableDescending(3);
                break;
            default:
                break;
        }

    }//GEN-LAST:event_btnZAMouseClicked
    private void searchMaNhanVien(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblThongKeHD.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText, 0); // 0 là chỉ số của cột Mã nhân viên

        sorter.setRowFilter(rowFilter);
        tblThongKeHD.setRowSorter(sorter);
    }
    private void txtTimKiemThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemThongKeActionPerformed
        String searchText = txtTimKiemThongKe.getText();
        // Gọi hàm thực hiện tìm kiếm
        searchMaNhanVien(searchText);
    }//GEN-LAST:event_txtTimKiemThongKeActionPerformed
    private void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa tất cả các dòng dữ liệu trong bảng
    }
    private void btnMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseClicked
        clear();
        clearTable(tblHoaDonChiTiet);
    }//GEN-LAST:event_btnMoiMouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        HoaDonView hdv = new HoaDonView();
        hdv.setVisible(true);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jblPhieuGiaoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblPhieuGiaoHangMouseClicked
        // TODO add your handling code here:
        PhieuGiaoHangView pgh = new PhieuGiaoHangView();
        pgh.setVisible(true);
    }//GEN-LAST:event_jblPhieuGiaoHangMouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void txtTimKiemLichSuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemLichSuMouseClicked
        String tuKhoaTimKiem = txtTimKiemLichSu.getText().trim();

// Kiểm tra nếu từ khóa tìm kiếm không rỗng
        if (!tuKhoaTimKiem.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) tblLichSuHoaDon.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tblLichSuHoaDon.setRowSorter(sorter);

            // Thiết lập tiêu chí tìm kiếm để tìm trong cột số 0
            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(tuKhoaTimKiem, 0);
            sorter.setRowFilter(rowFilter);

            // Tô màu vàng cho các hàng tìm thấy
            tblLichSuHoaDon.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Kiểm tra xem hàng có khớp với từ khóa tìm kiếm không và tô màu vàng nếu khớp
                    if (sorter.getViewRowCount() > 0 && row < sorter.getViewRowCount()) {
                        int modelRow = tblLichSuHoaDon.convertRowIndexToModel(row);
                        boolean matched = false;
                        for (int col = 0; col < tblLichSuHoaDon.getColumnCount(); col++) {
                            Object val = tblLichSuHoaDon.getValueAt(modelRow, col);
                            if (val != null && val.toString().toLowerCase().contains(tuKhoaTimKiem.toLowerCase())) {
                                matched = true;
                                break;
                            }
                        }

                        if (matched) {
                            c.setBackground(Color.YELLOW);
                        } else {
                            c.setBackground(table.getBackground());
                        }
                    } else {
                        c.setBackground(table.getBackground());
                    }

                    return c;
                }
            });
        } else {
            // Nếu từ khóa tìm kiếm rỗng, xóa bộ lọc và hiển thị toàn bộ dữ liệu
            tblLichSuHoaDon.setRowSorter(null);
            fillLichSuHD();
        }

    
    }//GEN-LAST:event_txtTimKiemLichSuMouseClicked
private void sortTableAscending(int columnIndex) {
        DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblThongKeHD.setRowSorter(sorter);
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING)));
        sorter.sort(); // Sắp xếp bảng
    }

    private void sortTableDescending(int columnIndex) {
        DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblThongKeHD.setRowSorter(sorter);
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(columnIndex, SortOrder.DESCENDING)));
        sorter.sort(); // Sắp xếp bảng
    }

    public double calculateFinalAmountFromDatabase(String maDGG, String maGiamGiaKhac, double totalAmount) {
        List<Integer> discountPercentages = service.getDiscountPercentageFromDatabase(maDGG);
        discountPercentages.addAll(service.getDiscountPercentageFromDatabase(maGiamGiaKhac));

        double finalAmount = totalAmount;

        for (int discountPercentage : discountPercentages) {
            // Áp dụng giảm giá theo phần trăm
            finalAmount -= (finalAmount * discountPercentage) / 100;
        }
        return finalAmount;
    }

    public void handleDiscountCalculation(String pggId, double totalAmount) {
        List<Map<String, Object>> discounts = service.getDiscountInfo(pggId);
        BigDecimal totalBillAmount = calculateTotalBillAmount();

        if (!discounts.isEmpty()) {
            BigDecimal discountAmount = calculateDiscountAmount(discounts, totalBillAmount);
            BigDecimal discountedTotalBill = totalBillAmount.subtract(discountAmount);

            // Cập nhật tổng tiền sau khi giảm giá và số tiền giảm lên giao diện
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String formattedDiscountedTotalBill = decimalFormat.format(discountedTotalBill.doubleValue());
            txtTongTienCuoi.setText(formattedDiscountedTotalBill);

            // Hiển thị giá trị giảm lên giao diện theo loại giảm giá
            String discountDisplay = "";
            BigDecimal displayedDiscountAmount = BigDecimal.ZERO;

            for (Map<String, Object> discount : discounts) {
                String loaiGiamGia = (String) discount.get("LoaiGiamGia");
                int giaTriGiam = (int) discount.get("GiaTriGiam");

                if ("Giảm phần trăm".equals(loaiGiamGia)) {
                    displayedDiscountAmount = BigDecimal.valueOf(giaTriGiam).divide(BigDecimal.valueOf(100))
                            .multiply(totalBillAmount);
                    discountDisplay = giaTriGiam + "%";
                } else if ("Giảm tiền".equals(loaiGiamGia)) {
                    displayedDiscountAmount = BigDecimal.valueOf(giaTriGiam);
                    discountDisplay = giaTriGiam + " VND";
                }

                // Đã xác định được loại giảm giá, thoát khỏi vòng lặp
                if (!discountDisplay.isEmpty()) {
                    break;
                }
            }

            String formattedDisplayedDiscountAmount = decimalFormat.format(displayedDiscountAmount.doubleValue());

            txtSoTienGiam.setText(formattedDisplayedDiscountAmount); // Hiển thị số tiền giảm
            txtGiaTriGiamDGG.setText(discountDisplay); // Hiển thị giá trị giảm theo loại giảm giá

            // Trừ số lượng còn đi 1 ở điều kiện giảm giá
            service.decreaseQuantityByOne(pggId);
        } else {
            System.out.println("ko hop le");
        }

        // Tính toán giảm giá theo phần trăm từ maDGG
        applyPercentageDiscountToBill(pggId);
    }

    private BigDecimal calculateTotalBillAmount() {
        BigDecimal totalBillAmount = BigDecimal.ZERO;

        for (int i = 0; i < tblHoaDonChiTiet.getRowCount(); i++) {
            BigDecimal totalPrice = new BigDecimal(tblHoaDonChiTiet.getValueAt(i, 4).toString());
            totalBillAmount = totalBillAmount.add(totalPrice);
        }

        return totalBillAmount;
    }

    private BigDecimal calculateDiscountAmount(List<Map<String, Object>> discountInfo, BigDecimal totalBillAmount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        for (Map<String, Object> discount : discountInfo) {
            String loaiGiamGia = (String) discount.get("LoaiGiamGia");
            int giaTriGiam = (int) discount.get("GiaTriGiam");

            if ("Giảm phần trăm".equals(loaiGiamGia)) {
                BigDecimal percentage = BigDecimal.valueOf(giaTriGiam).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                BigDecimal percentageDiscount = totalBillAmount.multiply(percentage);
                discountAmount = discountAmount.add(percentageDiscount);
            } else if ("Giảm tiền".equals(loaiGiamGia)) {
                discountAmount = discountAmount.add(BigDecimal.valueOf(giaTriGiam));
            }
        }

        return discountAmount;
    }

    private void applyPercentageDiscountToBill(String maDGG) {
        BigDecimal totalBillAmount = calculateTotalBillAmount();

        List<Integer> discountPercentages = service.getDiscountPercentageFromDatabase(maDGG);

        if (!discountPercentages.isEmpty()) {
            BigDecimal totalDiscountAmount = BigDecimal.ZERO;

            StringBuilder discountDisplay = new StringBuilder(); // Sử dụng StringBuilder để hiển thị thông tin giảm giá

            for (Integer discountPercentage : discountPercentages) {
                BigDecimal percentageValue = BigDecimal.valueOf(discountPercentage)
                        .divide(BigDecimal.valueOf(100)); // Chuyển phần trăm thành giá trị dec

                BigDecimal discountAmount = totalBillAmount.multiply(percentageValue);
                totalDiscountAmount = totalDiscountAmount.add(discountAmount);

                // Hiển thị giá trị phần trăm giảm giá
                if (discountDisplay.length() > 0) {
                    discountDisplay.append(", "); // Thêm dấu phẩy nếu cần thiết
                }
                discountDisplay.append(discountPercentage).append("%");
            }

            // Cập nhật giá trị giảm giá phần trăm vào TextField
            txtGiaTriGiamDGG.setText(discountDisplay.toString());

            // Tính và hiển thị tổng tiền sau khi giảm giá
            BigDecimal discountedTotalBill = totalBillAmount.subtract(totalDiscountAmount);
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String formattedDiscountedTotalBill = decimalFormat.format(discountedTotalBill.doubleValue());
            txtTongTienCuoi.setText(formattedDiscountedTotalBill);
            txtSoTienNhap.setText(formattedDiscountedTotalBill);

            // Tính và hiển thị tổng số tiền giảm
            String formattedTotalDiscountAmount = decimalFormat.format(totalDiscountAmount.doubleValue());
            txtSoTienGiam.setText(formattedTotalDiscountAmount);

            // Trừ số lượng còn đi 1 ở điều kiện giảm giá
            service.decreaseQuantityByOne(maDGG);
        } else {
            System.out.println("Đợt giảm giá không hợp lệ hoặc không có giảm giá phần trăm.");
        }
    }
    private int lastDay = -1;
    private int lastMonth = -1;
    private int lastYear = -1;

    private boolean isNewDay(int currentDay) {
        if (lastDay == -1) {
            lastDay = currentDay;
            return false;
        }

        if (currentDay != lastDay) {
            lastDay = currentDay;
            return true;
        }

        return false;
    }

    private boolean isNewMonth(int currentMonth) {
        if (lastMonth == -1) {
            lastMonth = currentMonth;
            return false;
        }

        if (currentMonth != lastMonth) {
            lastMonth = currentMonth;
            return true;
        }

        return false;
    }

    private boolean isNewYear(int currentYear) {
        if (lastYear == -1) {
            lastYear = currentYear;
            return false;
        }

        if (currentYear != lastYear) {
            lastYear = currentYear;
            return true;
        }

        return false;
    }

    public void resetDataOnNewDayMonthYear() {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ngay = txtNgayTao.getText();

        try {
            Date parsedDate = inputDateFormat.parse(ngay);
            java.sql.Date ngaySQL = new java.sql.Date(parsedDate.getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);

            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            int currentYear = calendar.get(Calendar.YEAR);

            if (isNewDay(currentDay)) {
                txtTongTatCaNgay.setText("");
                txtTongGiamGiaNgay.setText("");
                txtTongThuDuocNgay.setText("");
            }

            if (isNewMonth(currentMonth)) {
                txtTongTatCaThang.setText("");
                txtTongGiamGiaThang.setText("");
                txtTongThuThang.setText("");
            }

            if (isNewYear(currentYear)) {
                txtTongTatCaNam.setText("");
                txtTongGiamGiaNam.setText("");
                txtTongThuNam.setText("");
            }

            // Tiếp tục xử lý và hiển thị dữ liệu
        } catch (ParseException ex) {
            // Xử lý ngoại lệ ParseException
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HoaDonView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoaDonView().setVisible(true);
//                DangNhapView dnv = new DangNhapView();
//                dnv.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAZ;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNhap;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaLichSu;
    private javax.swing.JButton btnZA;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboLuaChonSapXep;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNgay;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jblDotGiamGia;
    private javax.swing.JLabel jblHome;
    private javax.swing.JLabel jblKhachHang;
    private javax.swing.JLabel jblNhanVien;
    private javax.swing.JLabel jblPhieuGiaoHang;
    private javax.swing.JLabel jlbPhieuGiamGia;
    private javax.swing.JLabel jlbSanPham;
    private javax.swing.JLabel jlbTaiKhoan;
    private javax.swing.JLabel lblDaThanhToan;
    private javax.swing.JLabel lblGiaTriGiamDGG;
    private javax.swing.JLabel lblGiaTriGiamPGG;
    private javax.swing.JLabel lblMaDGG;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaPGG;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblSoTienGiam;
    private javax.swing.JTextArea lblThongTinSP;
    private javax.swing.JLabel lblTongTienGiam;
    private javax.swing.JLabel lblVanChuyen;
    private javax.swing.JRadioButton rdoCo;
    private javax.swing.JRadioButton rdoKhong;
    private javax.swing.JPanel tabLichSuHD;
    private javax.swing.JPanel tabThongKe;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblLichSuHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThongKeHD;
    private javax.swing.JTextField txtGiaBD;
    private javax.swing.JTextField txtGiaKetThuc;
    private javax.swing.JTextField txtGiaTriGiamDGG;
    private javax.swing.JTextField txtGiaTriGiamPGG;
    private javax.swing.JTextField txtMaDotGiam;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaPGG;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JTextField txtSoTienDanhGia;
    private javax.swing.JTextField txtSoTienGiam;
    private javax.swing.JTextField txtSoTienNhap;
    private javax.swing.JTextField txtTimKiemLichSu;
    private javax.swing.JTextField txtTimKiemMaHD;
    private javax.swing.JTextField txtTimKiemThongKe;
    private javax.swing.JTextField txtTongGiamGiaNam;
    private javax.swing.JTextField txtTongGiamGiaNgay;
    private javax.swing.JTextField txtTongGiamGiaThang;
    private javax.swing.JTextField txtTongTatCaNam;
    private javax.swing.JTextField txtTongTatCaNgay;
    private javax.swing.JTextField txtTongTatCaThang;
    private javax.swing.JTextField txtTongThuDuocNgay;
    private javax.swing.JTextField txtTongThuNam;
    private javax.swing.JTextField txtTongThuThang;
    private javax.swing.JTextField txtTongTienCuoi;
    // End of variables declaration//GEN-END:variables
}
