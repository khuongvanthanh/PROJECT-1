package View;

import Model_PGG.DieuKienGiamGia;
import Model_PGG.DuLieuChung;
import Model_NhanVien.NhanVien;
import Model_PGG.PhieuGiamGia;
import Service_PGG.PhieuGiamGiaService;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utils.Auth;

public final class PhieuGiamGiaView extends javax.swing.JFrame {

    private final PhieuGiamGiaService service;
    private int index = -1;
    private DefaultTableModel mol = new DefaultTableModel();
    private TrangChuChinhView trangChuChinhView;

    public PhieuGiamGiaView() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.service = new PhieuGiamGiaService();
        setSize(1500, 800);
        setLocationRelativeTo(null);

        Color disabledTextColor = Color.BLACK;
        txtMaNguoiTao.setDisabledTextColor(disabledTextColor);
        txtMaPhieu.setDisabledTextColor(disabledTextColor);
        txtMaNguoiTao.setEnabled(false);
        txtMaPhieu.setEnabled(false);
        txtMaNguoiTao.setText(Auth.user.getMaNhanVien());

        if (!Auth.isManager()) {
            if (tabLichSu != null && tabThongKe != null) {
                int lichSuIndex = indexOfTab(tabs, "Lịch sử Phiếu Giảm Giá");
                int thongKeIndex = indexOfTab(tabs, "Thống kê");

                if (lichSuIndex != -1) {
                    tabs.setEnabledAt(lichSuIndex, false);
                    tabs.setToolTipTextAt(lichSuIndex, "Bạn không có quyền truy cập");
                }
                if (thongKeIndex != -1) {
                    tabs.setEnabledAt(thongKeIndex, false);
                    tabs.setToolTipTextAt(thongKeIndex, "Bạn không có quyền truy cập");
                }
            }
        }
        fillTable();
        fillTableLichSu();
        fillTableThongKe();
        timKiemMa();
        ngaythangnam();
        loc();
    }

    public void loc() {
        // Thiết lập sự kiện lắng nghe cho các checkbox
        chkGiamPhanTram.addItemListener(e -> fillLoc());
        chkGiamTien.addItemListener(e -> fillLoc());
        chkChuaDen.addItemListener(e -> fillLoc());
        chkDangDienRa.addItemListener(e -> fillLoc());
        chkHetHan.addItemListener(e -> fillLoc());
    }

    public void fillLoc() {
        boolean giamPhanTram = chkGiamPhanTram.isSelected();
        boolean giamTien = chkGiamTien.isSelected();
        boolean chuaden = chkChuaDen.isSelected();
        boolean dangdienra = chkDangDienRa.isSelected();
        boolean hetHan = chkHetHan.isSelected();

        // Xóa dữ liệu hiện có trong bảng và điền lại bằng dữ liệu đã lọc
        DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
        model.setRowCount(0);

        for (DuLieuChung dlc : this.service.filterRecords(giamPhanTram, giamTien, chuaden, dangdienra, hetHan)) {
            model.addRow(dlc.toDataRow());
        }
    }

    public List<DuLieuChung> filterRecords(boolean giamPhanTram, boolean giamTien, boolean chuachay, boolean dangdienra, boolean hethan, DefaultTableModel modelSanPham, DefaultTableModel modelPhieuGiamGia) {
        List<DuLieuChung> danhSachDaLoc = new ArrayList<>();

        for (int i = 0; i < modelSanPham.getRowCount(); i++) {
            boolean shouldAdd = true;
            Object loaiGiamGiaValue = modelSanPham.getValueAt(i, 4); // Giả sử cột loại giảm giá ở cột thứ 4 trong tblSanPham

            if (giamPhanTram && !loaiGiamGiaValue.equals("Giảm phần trăm")) {
                shouldAdd = false;
            }

            if (giamTien && !loaiGiamGiaValue.equals("Giảm tiền")) {
                shouldAdd = false;
            }

            if (shouldAdd) {
                String maGiamGia = (String) modelSanPham.getValueAt(i, 0); // Giả sử mã giảm giá lấy từ cột thứ nhất trong tblSanPham

                for (int j = 0; j < modelPhieuGiamGia.getRowCount(); j++) {
                    Object trangThaiValue = modelPhieuGiamGia.getValueAt(j, 6); // Giả sử cột trạng thái ở cột thứ 6 trong tblPhieuGiamGia
                    if (maGiamGia.equals(modelPhieuGiamGia.getValueAt(j, 0))) {
                        if (chuachay && !trangThaiValue.equals("Chưa chạy")) {
                            shouldAdd = false;
                            break;
                        }

                        if (dangdienra && !trangThaiValue.equals("Đang chạy")) {
                            shouldAdd = false;
                            break;
                        }

                        if (hethan && !trangThaiValue.equals("Hết hạn")) {
                            shouldAdd = false;
                            break;
                        }
                    }
                }

                if (shouldAdd) {
                    DuLieuChung data = new DuLieuChung(); // Tạo đối tượng dữ liệu mới
                    fillTable();
                    danhSachDaLoc.add(data);
                }
            }
        }

        return danhSachDaLoc;
    }

    private int indexOfTab(JTabbedPane tabbedPane, String title) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(title)) {
                return i;
            }
        }
        return -1;
    }

    public void ngaythangnam() {
        // Đổ dữ liệu vào combo box ngày (từ 1 đến 31)
        cboNgay.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            cboNgay.addItem(String.valueOf(i));
        }

        // Đổ dữ liệu vào combo box tháng (từ 1 đến 12)
        cboThang.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(String.valueOf(i));
        }

        // Đổ dữ liệu vào combo box năm (từ năm 2000 trở đi)
        cboNam.removeAllItems();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= currentYear; i++) {
            cboNam.addItem(String.valueOf(i));
        }
    }

    public void fillTableThongKe() {
        List<Object[]> resultList = service.thongke();
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng trong bảng

        if (resultList != null) {
            for (Object[] row : resultList) {
                model.addRow(row);
            }
        }
    }

    public void fillTable() {
        mol = (DefaultTableModel) tblPhieuGiamGia.getModel();
        mol.setRowCount(0);
        for (DuLieuChung dlc : this.service.getList()) {
            mol.addRow(dlc.toDataRow());
        }

    }

    public void fillTableTimKiem(List<DuLieuChung> dlcList) {
        DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
        model.setRowCount(0);
        for (DuLieuChung dlc : dlcList) {
            model.addRow(dlc.toDataRow());
        }
    }

    public void fillTableTimKiemLichSu(List<DuLieuChung> dlcList) {
        DefaultTableModel model = (DefaultTableModel) tblLichSu.getModel();
        model.setRowCount(0);
        for (DuLieuChung dlc : dlcList) {
            model.addRow(dlc.toDataRowLS());
        }
    }

    public void fillTableLichSu() {
        mol = (DefaultTableModel) tblLichSu.getModel();
        mol.setRowCount(0);
        for (DuLieuChung dlc : this.service.getLichSu()) {
            mol.addRow(dlc.toDataRowLS());
        }
    }

    public DuLieuChung readForm() {
        return new DuLieuChung(getPhieuGiamGia(), getDieuKienGiamGia(), getNhanVien());
    }

    public PhieuGiamGia getPhieuGiamGia() {
        PhieuGiamGia pgg = new PhieuGiamGia();
        pgg.setMaGiamGia(txtMaPhieu.getText());
        pgg.setTenPhieu(txtTenPhieu.getText());
        pgg.setMaNV(Auth.user.getMaNhanVien()); // Assuming Auth class has a method to get the user ID
        pgg.setMoTa(txtMoTa.getText());
        return pgg;
    }

    public DieuKienGiamGia getDieuKienGiamGia() {
        DieuKienGiamGia dkgg = new DieuKienGiamGia();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);
        dkgg.setNgayBD(formattedCurrentDateTime);
        LocalDateTime endDateTime = currentDateTime.plusDays(7);
        String formattedEndDateTime = endDateTime.format(formatter);
        dkgg.setNgayKT(formattedEndDateTime);
        dkgg.setLoaiGiamGia(rdoGiamPhanTram.isSelected() ? "Giảm phần trăm" : "Giảm tiền");
        String giaTriGiamStr = txtGiaTriGiam.getText().trim();
        String soLuongTaoStr = txtSoLuongTao.getText().trim();
        int giaTriGiam = giaTriGiamStr.isEmpty() ? 0 : Integer.parseInt(giaTriGiamStr);
        int soLuongTao = soLuongTaoStr.isEmpty() ? 0 : Integer.parseInt(soLuongTaoStr);
        dkgg.setGiaTriGiam(giaTriGiam);
        dkgg.setSoLuongTao(soLuongTao);
        dkgg.setSoLuongCon(soLuongTao);
        return dkgg;
    }

    public NhanVien getNhanVien() {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNguoiTao.getText());
        return nv;
    }

    public void showData(List<DuLieuChung> dlcList) {

        if (dlcList != null && !dlcList.isEmpty()) {
            DuLieuChung dlc = dlcList.get(0);
            PhieuGiamGia pgg = dlc.getPgg();
            DieuKienGiamGia dkgg = dlc.getDkgg();
            txtMaPhieu.setText(pgg.getMaGiamGia());
            txtTenPhieu.setText(pgg.getTenPhieu());
            txtMaNguoiTao.setText(pgg.getMaNV());
            txtMoTa.setText(pgg.getMoTa());
            if (dkgg.getLoaiGiamGia().equals("Giảm phần trăm")) {
                rdoGiamPhanTram.setSelected(true);
            } else {
                rdoGiamTien.setSelected(true);
            }
            txtGiaTriGiam.setText(String.valueOf(dkgg.getGiaTriGiam()));
            txtNgayBatDau.setText(dkgg.getNgayBD());
            txtNgayKetThuc.setText(dkgg.getNgayKT());
            txtSoLuongTao.setText(String.valueOf(dkgg.getSoLuongTao()));
        }

    }

    public void showDataLichSu(int index) {
        if (index >= 0) {
            int selectedRow = tblLichSu.getSelectedRow(); // Lấy dòng được chọn từ bảng           
            DuLieuChung selectedData = this.service.getTimKiem(tblLichSu.getValueAt(selectedRow, 1).toString());
            PhieuGiamGia pgg = selectedData.getPgg();
            DieuKienGiamGia dkgg = selectedData.getDkgg();
            lblMaGiamGia.setText(pgg.getMaGiamGia());
            lblTenPhieu.setText(pgg.getTenPhieu());
            lblMaNV.setText(pgg.getMaNV());
            lblMoTa.setText(pgg.getMoTa());
            lblLoaiGiamGia.setText(dkgg.getLoaiGiamGia());
            lblGiaTriGiam.setText(String.valueOf(dkgg.getGiaTriGiam()));
            lblTrangThai.setText(dkgg.getTrangThai());
            lblNgayBatDau.setText(dkgg.getNgayBD());
            lblNgayKetThuc.setText(dkgg.getNgayKT());
            lblSoLuongTao.setText(String.valueOf(dkgg.getSoLuongTao()));
            lblSoLuongCon.setText(String.valueOf(dkgg.getSoLuongCon()));
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu");
        }

    }

    public DuLieuChung getUpdate() {
        DuLieuChung r = this.service.getList().get(tblPhieuGiamGia.getSelectedRow());

        // Cập nhật thông tin của phiếu giảm giá
        r.getPgg().setMaGiamGia(txtMaPhieu.getText().trim());
        r.getPgg().setTenPhieu(txtTenPhieu.getText().trim());
        r.getPgg().setMoTa(txtMoTa.getText().trim());
        r.getPgg().setMaNV(Auth.user.getMaNhanVien()); // Lấy mã nhân viên từ Auth

        // Cập nhật thông tin của điều kiện giảm giá
        r.getDkgg().setLoaiGiamGia(rdoGiamPhanTram.isSelected() ? "Giảm phần trăm" : "Giảm tiền");
        r.getDkgg().setGiaTriGiam(Integer.parseInt(txtGiaTriGiam.getText()));
        r.getDkgg().setNgayBD(txtNgayBatDau.getText());
        r.getDkgg().setNgayKT(txtNgayKetThuc.getText()); // Truyền cả ngày bắt đầu và kết thúc

        r.getDkgg().setSoLuongTao(Integer.parseInt(txtSoLuongTao.getText()));
        r.getDkgg().setSoLuongCon(Integer.parseInt(txtSoLuongTao.getText()));

        return r;
    }

    public boolean checkDuLieu() {
        // Kiểm tra dữ liệu trong PhieuGiamGia
        if (txtMaPhieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã Phiếu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtTenPhieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Tên Phiếu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtTenPhieu.getText().trim().length() > 255) {
            JOptionPane.showMessageDialog(null, "Tên Phiếu không được vượt quá 255 ký tự.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtMaNguoiTao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã Người Tạo.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtMoTa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Mô Tả.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra LoaiGiamGia đã được chọn hay chưa
        if (!rdoGiamPhanTram.isSelected() && !rdoGiamTien.isSelected()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Loại Giảm Giá.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra dữ liệu trong DieuKienGiamGia
        if (txtNgayBatDau.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Ngày Bắt Đầu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtNgayKetThuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Ngày Kết Thúc.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtSoLuongTao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Số Lượng Tạo.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra LoaiGiamGia đã được chọn hay chưa
        if (!rdoGiamPhanTram.isSelected() && !rdoGiamTien.isSelected()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn LoaiGiamGia.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            // Kiểm tra định dạng ngày trong DieuKienGiamGia
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.parse(txtNgayBatDau.getText().trim());
            dateFormat.parse(txtNgayKetThuc.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false; // Nếu không thể chuyển đổi thành ngày, hiển thị thông báo và trả về false
        }

        try {
            // Kiểm tra kiểu dữ liệu của số lượng tạo
            int soLuongTao = Integer.parseInt(txtSoLuongTao.getText().trim());
            // Nếu dữ liệu không hợp lệ (ví dụ: số âm), hiển thị thông báo và trả về false
            if (soLuongTao < 0) {
                JOptionPane.showMessageDialog(null, "Số lượng không được nhỏ hơn 0.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false; // Nếu không thể chuyển đổi thành số, hiển thị thông báo và trả về false
        }

        return true; // Nếu thông tin đều hợp lệ, trả về true
    }

    public void clear() {
        txtMaPhieu.setText("");
        txtTenPhieu.setText("");
        txtMaNguoiTao.setText("");
        txtMoTa.setText("");
        txtGiaTriGiam.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtSoLuongTao.setText("");
        rdoGiamPhanTram.setSelected(true);
        rdoGiamTien.setSelected(false);
    }

    private void timKiemMa() {
        txtTimKiemTheoMa.getDocument().addDocumentListener(new DocumentListener() {
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
                String maTimKiem = txtTimKiemTheoMa.getText();
                List<DuLieuChung> dlcList = service.getSearch(maTimKiem);
                if (dlcList != null && !dlcList.isEmpty()) {
                    fillTableTimKiem(dlcList);
                } else {
                    clear();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        txtMaNguoiTao = new javax.swing.JTextField();
        txtTenPhieu = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNgayBatDau = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNgayKetThuc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdoGiamTien = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txtGiaTriGiam = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoGiamPhanTram = new javax.swing.JRadioButton();
        txtSoLuongTao = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        chkGiamPhanTram = new javax.swing.JCheckBox();
        chkGiamTien = new javax.swing.JCheckBox();
        chkDangDienRa = new javax.swing.JCheckBox();
        chkHetHan = new javax.swing.JCheckBox();
        chkChuaDen = new javax.swing.JCheckBox();
        txtTimKiemTheoMa = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuGiamGia = new javax.swing.JTable();
        tabLichSu = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblMaGiamGia = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTenPhieu = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblLoaiGiamGia = new javax.swing.JLabel();
        lblGiaTriGiam = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblNgayBatDau = new javax.swing.JLabel();
        lblNgayKetThuc = new javax.swing.JLabel();
        lblSoLuongTao = new javax.swing.JLabel();
        lblSoLuongCon = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtTimTheoMa = new javax.swing.JTextField();
        btnTimKiemLichSu = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        btnXoaLichSu = new javax.swing.JButton();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLichSu = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        tabThongKe = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        cboNam = new javax.swing.JComboBox<>();
        cboThang = new javax.swing.JComboBox<>();
        cboNgay = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jblHome1 = new javax.swing.JLabel();
        jlbSanPham1 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jlbPhieuGiamGia1 = new javax.swing.JLabel();
        jblDotGiamGia1 = new javax.swing.JLabel();
        jblNhanVien1 = new javax.swing.JLabel();
        jblKhachHang1 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jblPhieuGiaoHang1 = new javax.swing.JLabel();
        jlbTaiKhoan1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabs.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tabs.setForeground(new java.awt.Color(0, 0, 102));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Bảng phiếu giảm giá"));
        jPanel2.setPreferredSize(new java.awt.Dimension(1344, 756));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("THÔNG TIN PHIẾU GIẢM GIÁ");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Mã phiếu :");

        jLabel2.setText("Tên phiếu :");

        jLabel9.setText("Mã người tạo :");

        jLabel3.setText("Mô tả :");

        txtMaNguoiTao.setEditable(false);
        txtMaNguoiTao.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaNguoiTao))
                    .addComponent(txtMoTa)
                    .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Điều kiện ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel7.setText("Ngày bắt đầu :");

        jLabel8.setText("Ngày kết thúc :");

        jLabel5.setText("Loại giảm giá :");

        buttonGroup1.add(rdoGiamTien);
        rdoGiamTien.setSelected(true);
        rdoGiamTien.setText("Giảm tiền");

        jLabel6.setText("Giá trị giảm :");

        jLabel4.setText("Số lượng :");

        buttonGroup1.add(rdoGiamPhanTram);
        rdoGiamPhanTram.setText("Giảm %");

        txtSoLuongTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongTaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuongTao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoGiamPhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(rdoGiamTien)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoGiamTien)
                    .addComponent(jLabel5)
                    .addComponent(rdoGiamPhanTram))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuongTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        chkGiamPhanTram.setText("Giảm %");
        chkGiamPhanTram.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkGiamPhanTramItemStateChanged(evt);
            }
        });

        chkGiamTien.setText("Giảm tiền");
        chkGiamTien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkGiamTienItemStateChanged(evt);
            }
        });

        chkDangDienRa.setText("Đang chạy");
        chkDangDienRa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkDangDienRaItemStateChanged(evt);
            }
        });
        chkDangDienRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDangDienRaActionPerformed(evt);
            }
        });

        chkHetHan.setText("Hết hạn");
        chkHetHan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkHetHanItemStateChanged(evt);
            }
        });
        chkHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHetHanActionPerformed(evt);
            }
        });

        chkChuaDen.setText("Chưa chạy");
        chkChuaDen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkChuaDenItemStateChanged(evt);
            }
        });
        chkChuaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChuaDenActionPerformed(evt);
            }
        });

        txtTimKiemTheoMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTheoMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkGiamPhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGiamTien, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkChuaDen)
                .addGap(22, 22, 22)
                .addComponent(chkDangDienRa)
                .addGap(18, 18, 18)
                .addComponent(chkHetHan)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemTheoMa, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkGiamPhanTram)
                    .addComponent(txtTimKiemTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkGiamTien)
                    .addComponent(chkChuaDen)
                    .addComponent(chkDangDienRa)
                    .addComponent(chkHetHan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd.setBackground(new java.awt.Color(102, 204, 255));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 204, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 204, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(102, 204, 255));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnAdd)
                .addGap(30, 30, 30)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Bảng phiếu giảm giá"));

        tblPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu", "Tên phiếu", "Mã nhân viên", "Mô tả", "Loại giảm giá", "Giá trị giảm", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc", "Số lượng tạo", "Số lượng còn"
            }
        ));
        tblPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuGiamGia);
        if (tblPhieuGiamGia.getColumnModel().getColumnCount() > 0) {
            tblPhieuGiamGia.getColumnModel().getColumn(1).setHeaderValue("Tên phiếu");
            tblPhieuGiamGia.getColumnModel().getColumn(2).setHeaderValue("Mã nhân viên");
            tblPhieuGiamGia.getColumnModel().getColumn(3).setHeaderValue("Mô tả");
            tblPhieuGiamGia.getColumnModel().getColumn(4).setHeaderValue("Loại giảm giá");
            tblPhieuGiamGia.getColumnModel().getColumn(5).setHeaderValue("Giá trị giảm");
            tblPhieuGiamGia.getColumnModel().getColumn(6).setHeaderValue("Trạng thái");
            tblPhieuGiamGia.getColumnModel().getColumn(7).setHeaderValue("Ngày bắt đầu");
            tblPhieuGiamGia.getColumnModel().getColumn(8).setHeaderValue("Ngày kết thúc");
            tblPhieuGiamGia.getColumnModel().getColumn(9).setResizable(false);
            tblPhieuGiamGia.getColumnModel().getColumn(9).setHeaderValue("Số lượng tạo");
            tblPhieuGiamGia.getColumnModel().getColumn(10).setHeaderValue("Số lượng còn");
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(501, 501, 501)
                        .addComponent(jLabel11)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        tabs.addTab("Thông tin Phiếu Giảm Giá", jPanel2);

        tabLichSu.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Mã giảm giá :");

        lblMaGiamGia.setBackground(new java.awt.Color(255, 255, 255));
        lblMaGiamGia.setText("null");

        jLabel12.setText("Tên phiếu :");

        lblTenPhieu.setText("null");

        jLabel14.setText("Mã nhân viên :");

        lblMaNV.setText("null");

        jLabel16.setText("Mô tả :");

        jLabel18.setText("Loại giảm giá :");

        jLabel19.setText("Giá trị giảm :");

        lblLoaiGiamGia.setText("null");

        lblGiaTriGiam.setText("null");

        jLabel22.setText("Trạng thái :");

        lblTrangThai.setText("null");

        jLabel24.setText("Ngày bắt đầu :");

        jLabel26.setText("Ngày kết thúc :");

        jLabel28.setText("Số lượng tạo :");

        jLabel30.setText("Số lượng còn :");

        lblNgayBatDau.setText("null");

        lblNgayKetThuc.setText("null");

        lblSoLuongTao.setText("null");

        lblSoLuongCon.setText("null");

        lblMoTa.setText("null");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblMaGiamGia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                    .addComponent(lblTenPhieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblGiaTriGiam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTrangThai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayBatDau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayKetThuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSoLuongTao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSoLuongCon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLoaiGiamGia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblMaGiamGia))
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTenPhieu))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblMaNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblLoaiGiamGia))
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lblGiaTriGiam))
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lblTrangThai))
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblNgayBatDau))
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblNgayKetThuc))
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lblSoLuongTao))
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(lblSoLuongCon))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        btnTimKiemLichSu.setText("Tìm kiếm ");
        btnTimKiemLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemLichSuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnTimKiemLichSu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemLichSu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        btnXoaLichSu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoaLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLichSuActionPerformed(evt);
            }
        });

        btnKhoiPhuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/restore.png"))); // NOI18N
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnXoaLichSu, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnKhoiPhuc)
                .addGap(16, 16, 16))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaLichSu)
                    .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Bảng Lịch sử"));

        tblLichSu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblLichSu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã giảm giá", "Thao tác", "Thời gian"
            }
        ));
        tblLichSu.setGridColor(new java.awt.Color(255, 255, 255));
        tblLichSu.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblLichSu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLichSu);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("LỊCH SỬ XÓA");

        javax.swing.GroupLayout tabLichSuLayout = new javax.swing.GroupLayout(tabLichSu);
        tabLichSu.setLayout(tabLichSuLayout);
        tabLichSuLayout.setHorizontalGroup(
            tabLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLichSuLayout.createSequentialGroup()
                .addGap(514, 514, 514)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabLichSuLayout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        tabLichSuLayout.setVerticalGroup(
            tabLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLichSuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("Lịch sử Phiếu Giảm Giá", tabLichSu);

        tabThongKe.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Năm", "Tháng", "Ngày", "Số lượng tạo", "Số lượng dùng", "Số tiền giảm", "Số phần trăm giảm"
            }
        ));
        jScrollPane2.setViewportView(tblThongKe);

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThangItemStateChanged(evt);
            }
        });

        cboNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNgay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNgayItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout tabThongKeLayout = new javax.swing.GroupLayout(tabThongKe);
        tabThongKe.setLayout(tabThongKeLayout);
        tabThongKeLayout.setHorizontalGroup(
            tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboNgay, 0, 597, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tabThongKeLayout.setVerticalGroup(
            tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        tabs.addTab("Thống kê", tabThongKe);

        jPanel18.setBackground(new java.awt.Color(102, 204, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jblHome1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        jblHome1.setText("Home");
        jblHome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblHome1MouseClicked(evt);
            }
        });

        jlbSanPham1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dog.png"))); // NOI18N
        jlbSanPham1.setText("Sản phẩm");
        jlbSanPham1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbSanPham1MouseClicked(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoadon.png"))); // NOI18N
        jLabel33.setText("Hóa đơn");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jlbPhieuGiamGia1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phieugiamgia.png"))); // NOI18N
        jlbPhieuGiamGia1.setText("Phiếu giảm giá");
        jlbPhieuGiamGia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPhieuGiamGia1MouseClicked(evt);
            }
        });

        jblDotGiamGia1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dotgiamgia.png"))); // NOI18N
        jblDotGiamGia1.setText("Đợt giảm giá");
        jblDotGiamGia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblDotGiamGia1MouseClicked(evt);
            }
        });

        jblNhanVien1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nhanvien.png"))); // NOI18N
        jblNhanVien1.setText("Nhân viên");
        jblNhanVien1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblNhanVien1MouseClicked(evt);
            }
        });

        jblKhachHang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/khachhang.png"))); // NOI18N
        jblKhachHang1.setText("Khách hàng");
        jblKhachHang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblKhachHang1MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thoat.png"))); // NOI18N
        jLabel37.setText("Đăng xuất");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo (1).jpg"))); // NOI18N

        jblPhieuGiaoHang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Phieugiaohang.png"))); // NOI18N
        jblPhieuGiaoHang1.setText("Phiếu giao hàng");
        jblPhieuGiaoHang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblPhieuGiaoHang1MouseClicked(evt);
            }
        });

        jlbTaiKhoan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taikhoan.png"))); // NOI18N
        jlbTaiKhoan1.setText("Tài khoản");
        jlbTaiKhoan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbTaiKhoan1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator6)
            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jblHome1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbSanPham1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPhieuGiamGia1)
                    .addComponent(jblDotGiamGia1)
                    .addComponent(jblNhanVien1)
                    .addComponent(jblKhachHang1)
                    .addComponent(jblPhieuGiaoHang1))
                .addGap(34, 34, 34))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jlbTaiKhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator4))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel38)
                .addGap(1, 1, 1)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jblHome1)
                .addGap(33, 33, 33)
                .addComponent(jlbSanPham1)
                .addGap(33, 33, 33)
                .addComponent(jLabel33)
                .addGap(33, 33, 33)
                .addComponent(jblPhieuGiaoHang1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbPhieuGiamGia1)
                .addGap(33, 33, 33)
                .addComponent(jblDotGiamGia1)
                .addGap(33, 33, 33)
                .addComponent(jblNhanVien1)
                .addGap(33, 33, 33)
                .addComponent(jblKhachHang1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTaiKhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel37)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1317, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLichSuActionPerformed
        int selectedRow = tblLichSu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần xóa");
            return;
        }
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dữ liệu này?", "Xác nhận xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            String maGiamGia = tblLichSu.getValueAt(selectedRow, 1).toString();
            DuLieuChung dlc = service.getTimKiem(maGiamGia); // Sử dụng dịch vụ để tìm thông tin từ mã giảm giá

            if (dlc != null && service.softDelete(dlc.getPgg().getMaGiamGia()) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa dữ liệu thành công");
                fillTableLichSu();
                fillTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dữ liệu thất bại");
            }
        }

    }//GEN-LAST:event_btnXoaLichSuActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        int selectedRow = tblLichSu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần khôi phục.");
            return;
        }
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn khôi phục dữ liệu này?", "Xác nhận khôi phục dữ liệu", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            String ma = tblLichSu.getValueAt(selectedRow, 1).toString();

            if (service.restorePhieuGiamGia(ma) != 0) {
                JOptionPane.showMessageDialog(this, "Khôi phục dữ liệu thành công");
                fillTableLichSu();
                fillTable();
            } else {
                JOptionPane.showMessageDialog(this, "Khôi phục dữ liệu thất bại");
            }
        }

    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void tblLichSuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuMouseClicked
        showDataLichSu(tblLichSu.getSelectedRow());
    }//GEN-LAST:event_tblLichSuMouseClicked

    private void btnTimKiemLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemLichSuActionPerformed
        String maTimKiem = txtTimTheoMa.getText();
        List<DuLieuChung> dlcList = service.getSearchLichSu(maTimKiem);
        if (dlcList != null && !dlcList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tìm kiếm thành công");
            fillTableTimKiemLichSu(dlcList);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào");
            clear();
        }
    }//GEN-LAST:event_btnTimKiemLichSuActionPerformed
    private void updateDataForSelectedDay(int selectedDay) {
        List<Object[]> resultList = service.getDataForSelectedDay(selectedDay);

        mol = (DefaultTableModel) tblThongKe.getModel();
        mol.setRowCount(0);
        mol.setColumnIdentifiers(new Object[]{"Ngày", "Tháng", "Năm", "Số Lượng Phiếu Tạo", "Số Lượng Phiếu Dùng", "Tổng Tiền Giảm", "Tổng Phần Trăm Giảm"});
        if (resultList != null) {
            for (Object[] row : resultList) {
                // Đổi tên cột và chỉnh lại dữ liệu để hiển thị ngày, tháng, năm riêng biệt
                int nam = (int) row[0];
                int thang = (int) row[1];
                int ngay = (int) row[2];
                row[0] = ngay;
                row[1] = thang;
                row[2] = nam;
                mol.addRow(row);
            }
        }
    }

    private void updateDataForSelectedMonth(int selectedMonth) {
        List<Object[]> resultList = service.getDataForSelectedMonth(selectedMonth);

        mol = (DefaultTableModel) tblThongKe.getModel();
        mol.setRowCount(0);
        mol.setColumnIdentifiers(new Object[]{"Ngày", "Tháng", "Năm", "Số Lượng Phiếu Tạo", "Số Lượng Phiếu Dùng", "Tổng Tiền Giảm", "Tổng Phần Trăm Giảm"});
        if (resultList != null) {
            for (Object[] row : resultList) {
                // Đổi tên cột và chỉnh lại dữ liệu để hiển thị ngày, tháng, năm riêng biệt
                int nam = (int) row[0];
                int thang = (int) row[1];
                int ngay = (int) row[2];
                row[0] = ngay;
                row[1] = thang;
                row[2] = nam;
                mol.addRow(row);
            }
        }
    }

    private void updateDataForSelectedYear(int selectedYear) {
        List<Object[]> resultList = service.getDataForSelectedYear(selectedYear);

        mol = (DefaultTableModel) tblThongKe.getModel();
        mol.setRowCount(0);
        mol.setColumnIdentifiers(new Object[]{"Ngày", "Tháng", "Năm", "Số Lượng Phiếu Tạo", "Số Lượng Phiếu Dùng", "Tổng Tiền Giảm", "Tổng Phần Trăm Giảm"});
        if (resultList != null) {
            for (Object[] row : resultList) {
                // Đổi tên cột và chỉnh lại dữ liệu để hiển thị ngày, tháng, năm riêng biệt
                int nam = (int) row[0];
                int thang = (int) row[1];
                int ngay = (int) row[2];
                row[0] = ngay;
                row[1] = thang;
                row[2] = nam;
                mol.addRow(row);
            }
        }
    }

    private void cboNgayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNgayItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedDay = (String) cboNgay.getSelectedItem();
            System.out.println("Ngày được chọn: " + selectedDay);
            updateDataForSelectedDay(Integer.parseInt(selectedDay));
        }
    }//GEN-LAST:event_cboNgayItemStateChanged

    private void cboThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThangItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedMonth = (String) cboThang.getSelectedItem();
            System.out.println("Ngày được chọn: " + selectedMonth);
            updateDataForSelectedMonth(Integer.parseInt(selectedMonth));
        }
    }//GEN-LAST:event_cboThangItemStateChanged

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedYear = (String) cboNam.getSelectedItem();
            System.out.println("Ngày được chọn: " + selectedYear);
            updateDataForSelectedYear(Integer.parseInt(selectedYear));
        }
    }//GEN-LAST:event_cboNamItemStateChanged

    private void txtTimKiemTheoMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTheoMaActionPerformed

    }//GEN-LAST:event_txtTimKiemTheoMaActionPerformed

    private void chkHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHetHanActionPerformed

    }//GEN-LAST:event_chkHetHanActionPerformed

    private void chkDangDienRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDangDienRaActionPerformed

    }//GEN-LAST:event_chkDangDienRaActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblPhieuGiamGia.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần xóa.");
            return;
        }
        DuLieuChung r = this.service.getList().get(selectedRow);
        int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dữ liệu này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (service.xoa(r) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillTable();
                fillTableLichSu();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            if (checkDuLieu()) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật dữ liệu không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (service.update(getUpdate())) {
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        fillTable();
                        fillTableLichSu();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Đã hủy cập nhật");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu để cập nhật", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String ma = txtMaPhieu.getText();
        if (service.checkTrungMa(ma)) {
            JOptionPane.showMessageDialog(this, "Trung ma");
        } //else if (checkDuLieu()) {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm phiếu giảm giá?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.insert(readForm()) != 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                fillTable();
                fillTableLichSu();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm không thành công");
            }
            //}
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void chkChuaDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkChuaDenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkChuaDenActionPerformed

    private void tblPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuGiamGiaMouseClicked
        if (!Auth.isLogin()) {
            JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập để xem dữ liệu!");
            return;
        }

        index = tblPhieuGiamGia.getSelectedRow();
        if (index >= 0 && index < tblPhieuGiamGia.getRowCount()) {
            String maGiamGia = String.valueOf(tblPhieuGiamGia.getValueAt(index, 0));
            List<DuLieuChung> resultList = service.getSearch(maGiamGia);
            showData(resultList);
        } else {
            clear();
        }
    }//GEN-LAST:event_tblPhieuGiamGiaMouseClicked

    private void jblHome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblHome1MouseClicked
        // TODO add your handling code here:
        TrangChuChinhView tccv = new TrangChuChinhView();
        tccv.setVisible(true);
    }//GEN-LAST:event_jblHome1MouseClicked

    private void jlbSanPham1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSanPham1MouseClicked
        Chinh c = new Chinh();
        c.setVisible(true);
    }//GEN-LAST:event_jlbSanPham1MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        HoaDonView hdv = new HoaDonView();
        hdv.setVisible(true);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jlbPhieuGiamGia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPhieuGiamGia1MouseClicked
        PhieuGiamGiaView pggv = new PhieuGiamGiaView();
        pggv.setVisible(true);
    }//GEN-LAST:event_jlbPhieuGiamGia1MouseClicked

    private void jblDotGiamGia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblDotGiamGia1MouseClicked
        DotGiamGiaView dggv = new DotGiamGiaView();
        dggv.setVisible(true);
    }//GEN-LAST:event_jblDotGiamGia1MouseClicked

    private void jblNhanVien1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblNhanVien1MouseClicked
        NhanVienView nvv = new NhanVienView();
        nvv.setVisible(true);
    }//GEN-LAST:event_jblNhanVien1MouseClicked

    private void jblKhachHang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblKhachHang1MouseClicked
        KhachHangView khv = new KhachHangView();
        khv.setVisible(true);
    }//GEN-LAST:event_jblKhachHang1MouseClicked

    private void jblPhieuGiaoHang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblPhieuGiaoHang1MouseClicked
        // TODO add your handling code here:
        PhieuGiaoHangView pgh = new PhieuGiaoHangView();
        pgh.setVisible(true);
    }//GEN-LAST:event_jblPhieuGiaoHang1MouseClicked

    private void jlbTaiKhoan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbTaiKhoan1MouseClicked
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_jlbTaiKhoan1MouseClicked


    private void chkGiamPhanTramItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkGiamPhanTramItemStateChanged

    }//GEN-LAST:event_chkGiamPhanTramItemStateChanged

    private void chkGiamTienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkGiamTienItemStateChanged

    }//GEN-LAST:event_chkGiamTienItemStateChanged

    private void chkChuaDenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkChuaDenItemStateChanged

    }//GEN-LAST:event_chkChuaDenItemStateChanged

    private void chkDangDienRaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkDangDienRaItemStateChanged

    }//GEN-LAST:event_chkDangDienRaItemStateChanged

    private void chkHetHanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkHetHanItemStateChanged

    }//GEN-LAST:event_chkHetHanItemStateChanged

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel37MouseClicked

    private void txtSoLuongTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongTaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhieuGiamGiaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnTimKiemLichSu;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoaLichSu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNgay;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JCheckBox chkChuaDen;
    private javax.swing.JCheckBox chkDangDienRa;
    private javax.swing.JCheckBox chkGiamPhanTram;
    private javax.swing.JCheckBox chkGiamTien;
    private javax.swing.JCheckBox chkHetHan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel jblDotGiamGia1;
    private javax.swing.JLabel jblHome1;
    private javax.swing.JLabel jblKhachHang1;
    private javax.swing.JLabel jblNhanVien1;
    private javax.swing.JLabel jblPhieuGiaoHang1;
    private javax.swing.JLabel jlbPhieuGiamGia1;
    private javax.swing.JLabel jlbSanPham1;
    private javax.swing.JLabel jlbTaiKhoan1;
    private javax.swing.JLabel lblGiaTriGiam;
    private javax.swing.JLabel lblLoaiGiamGia;
    private javax.swing.JLabel lblMaGiamGia;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblSoLuongCon;
    private javax.swing.JLabel lblSoLuongTao;
    private javax.swing.JLabel lblTenPhieu;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JRadioButton rdoGiamPhanTram;
    private javax.swing.JRadioButton rdoGiamTien;
    private javax.swing.JPanel tabLichSu;
    private javax.swing.JPanel tabThongKe;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLichSu;
    private javax.swing.JTable tblPhieuGiamGia;
    private javax.swing.JTable tblThongKe;
    private javax.swing.JTextField txtGiaTriGiam;
    private javax.swing.JTextField txtMaNguoiTao;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    private javax.swing.JTextField txtSoLuongTao;
    private javax.swing.JTextField txtTenPhieu;
    private javax.swing.JTextField txtTimKiemTheoMa;
    private javax.swing.JTextField txtTimTheoMa;
    // End of variables declaration//GEN-END:variables
}
