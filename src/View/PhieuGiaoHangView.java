/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model_PGH.DichVu;
import Model_PGH.PhieuGiaoHang;
import Service_PGH.DichVuService;
import Service_PGH.PhieuGiaoHangService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hothi
 */
public class PhieuGiaoHangView extends javax.swing.JFrame {

    private final DichVuService dichVuService = new DichVuService();
    private final PhieuGiaoHangService phieuGiaoHangService = new PhieuGiaoHangService();
    private DefaultTableModel tableDichVu = new DefaultTableModel();
    private DefaultTableModel tablePhieuGiaoHang = new DefaultTableModel();
    private List<DichVu> listDichVu = new ArrayList<>();
    private List<PhieuGiaoHang> listPhieuGiaoHang = new ArrayList<>();

    /**
     * Creates new form PhieuGiaoHangView
     */
    public PhieuGiaoHangView() {
        initComponents();
        setSize(1500, 800);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        loadData();
        fillTableDichVu();
        fillTableDPhieuGiaoHang();
        setLocationRelativeTo(null);
    }

    private void deleteDichVu(String maDv) {
        dichVuService.deleteDichVu(maDv);
        loadData();
        fillTableDichVu();
        clearDataDichVu();
    }

    private void editDichVu(String maDv) {
        DichVu dv = readDichVu();
        dichVuService.updateDichVu(dv, maDv);
        loadData();
        fillTableDichVu();
        clearDataDichVu();
    }

    private void deletePhieuGiaoHang(String maPGH) {
        phieuGiaoHangService.deleteData(maPGH);
        loadData();
        fillTableDPhieuGiaoHang();
        clearDataPhieuGiaoHang();
    }

    private void editPhieuGiaoHang(String maPGH) {
        PhieuGiaoHang pgh = readPhieuGiaoHang();
        phieuGiaoHangService.updateData(pgh, maPGH);
        loadData();
        fillTableDPhieuGiaoHang();
        clearDataPhieuGiaoHang();
    }

    private void fillTableDichVu() {
        tableDichVu = (DefaultTableModel) jTableDichVu.getModel();
        tableDichVu.setRowCount(0);
        for (DichVu dv : listDichVu) {
            tableDichVu.addRow(dv.toDataRow());
        }
    }

    private void clearDataDichVu() {
        maDichVu.setText("");
        tenDichVu.setText("");
        motaDichVu.setText("");
        giaDichVu.setText("");
        maDichVu.setEnabled(true);
    }

    private void clearDataPhieuGiaoHang() {
        maPGH.setText("");
        maHoaDonPGH.setText("");
        ngaytaoPGH.setText("");
        nguoitaoPGH.setText("");
        jComboBox1.setSelectedIndex(0);
        diachiPGH.setText("");
        ngayGiaoDuKienPGH.setText("");
        thoiDiemGiaoPGH.setText("");
        nguoiNhanPGH.setText("");
        ghiChuPGH.setText("");
        maPGH.setEnabled(true);
        maHoaDonPGH.setEnabled(true);
    }

    private void fillTableDPhieuGiaoHang() {
        tablePhieuGiaoHang = (DefaultTableModel) jTablePhieuGiaoHang.getModel();
        tablePhieuGiaoHang.setRowCount(0);
        for (PhieuGiaoHang pgh : listPhieuGiaoHang) {
            tablePhieuGiaoHang.addRow(pgh.toDataRow());
        }
    }

    private void fillTableDTimKiemPhieuGiaoHang(List<PhieuGiaoHang> listPhieuGiaoHang) {
        tablePhieuGiaoHang = (DefaultTableModel) jTablePhieuGiaoHang.getModel();
        tablePhieuGiaoHang.setRowCount(0);
        for (PhieuGiaoHang pgh : listPhieuGiaoHang) {
            tablePhieuGiaoHang.addRow(pgh.toDataRow());
        }
    }

    private void loadData() {
        listDichVu = dichVuService.getData();
        listPhieuGiaoHang = phieuGiaoHangService.getData();
    }

    private DichVu readDichVu() {
        DichVu dv = new DichVu();
        dv.setMaDv(maDichVu.getText());
        dv.setTenDv(tenDichVu.getText());
        dv.setMota(motaDichVu.getText());
        dv.setGia(new BigDecimal(giaDichVu.getText()));
        return dv;
    }

    private PhieuGiaoHang readPhieuGiaoHang() {
        PhieuGiaoHang pgh = new PhieuGiaoHang();
        pgh.setMaPGH(maPGH.getText());
        pgh.setMaHD(maHoaDonPGH.getText());
        pgh.setNgayTao(ngaytaoPGH.getText());
        pgh.setNguoiTao(nguoitaoPGH.getText());
        pgh.setTrangThai(jComboBox1.getSelectedItem().toString());
        pgh.setDiaChi(diachiPGH.getText());
        pgh.setNgayGiaoDuKien(ngayGiaoDuKienPGH.getText());
        pgh.setThoiDiemGiao(thoiDiemGiaoPGH.getText());
        pgh.setNguoiNhan(nguoiNhanPGH.getText());
        pgh.setGhiChu(ghiChuPGH.getText());
        return pgh;
    }

    private boolean validateDichVu() {
        String maDv = maDichVu.getText();
        String tenDv = tenDichVu.getText();
        String mota = motaDichVu.getText();
        String gia = giaDichVu.getText();
        if (maDv.isEmpty() || tenDv.isEmpty() || mota.isEmpty() || gia.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validatePhieuGiaoHang() {
        String ma = this.maPGH.getText();
        String maHoaDon = this.maHoaDonPGH.getText();
        String ngayTao = this.ngaytaoPGH.getText();
        String nguoiTao = this.nguoitaoPGH.getText();
        String trangThai = this.jComboBox1.getSelectedItem().toString();
        String diaChi = this.diachiPGH.getText();
        String ngayGiaoDuKien = this.ngayGiaoDuKienPGH.getText();
        String thoiDiemGiao = this.thoiDiemGiaoPGH.getText();
        String nguoiNhan = this.nguoiNhanPGH.getText();
        String ghiChu = this.ghiChuPGH.getText();
        if (ma.isEmpty() || maHoaDon.isEmpty() || ngayTao.isEmpty() || nguoiTao.isEmpty() || trangThai.isEmpty() || diaChi.isEmpty() || ngayGiaoDuKien.isEmpty() || thoiDiemGiao.isEmpty() || nguoiNhan.isEmpty() || ghiChu.isEmpty()) {
            return false;
        }
        return true;
    }

    private void fillDataDichVu(int row) {
        DichVu dv = listDichVu.get(row);
        maDichVu.setText(dv.getMaDv());
        tenDichVu.setText(dv.getTenDv());
        motaDichVu.setText(dv.getMota());
        giaDichVu.setText(dv.getGia() + "");
        maDichVu.setEnabled(false);
    }

    private void fillDataPhieuGiaoHang(int row) {
        PhieuGiaoHang pgh = listPhieuGiaoHang.get(row);
        maPGH.setText(pgh.getMaPGH());
        maHoaDonPGH.setText(pgh.getMaHD());
        ngaytaoPGH.setText(pgh.getNgayTao());
        nguoitaoPGH.setText(pgh.getNguoiTao());
        jComboBox1.setSelectedItem(pgh.getTrangThai());
        diachiPGH.setText(pgh.getDiaChi());
        ngayGiaoDuKienPGH.setText(pgh.getNgayGiaoDuKien());
        thoiDiemGiaoPGH.setText(pgh.getThoiDiemGiao());
        nguoiNhanPGH.setText(pgh.getNguoiNhan());
        ghiChuPGH.setText(pgh.getGhiChu());
//        maPGH.setEnabled(false);
        maHoaDonPGH.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDichVu = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        addBtnDichvu = new javax.swing.JButton();
        editBtnDichvu = new javax.swing.JButton();
        deleteBtnDichvu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        maDichVu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tenDichVu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        giaDichVu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        motaDichVu = new javax.swing.JTextArea();
        newBtnDichvu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePhieuGiaoHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        addBtnPGH = new javax.swing.JButton();
        editBtnPGH = new javax.swing.JButton();
        deleteBtnPGH = new javax.swing.JButton();
        newBtnPGH = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        maPGH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        diachiPGH = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        ngayGiaoDuKienPGH = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        thoiDiemGiaoPGH = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        nguoiNhanPGH = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        ghiChuPGH = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        ngaytaoPGH = new javax.swing.JTextField();
        nguoitaoPGH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        maHoaDonPGH = new javax.swing.JTextField();
        locPGH = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txt_tim = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTableDichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Mô tả dịch vụ", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDichVu.getTableHeader().setReorderingAllowed(false);
        jTableDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDichVuMouseClicked(evt);
            }
        });
        jTableDichVu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableDichVuKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDichVu);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addBtnDichvu.setText("Thêm");
        addBtnDichvu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnDichvuActionPerformed(evt);
            }
        });

        editBtnDichvu.setText("Sửa");
        editBtnDichvu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnDichvuActionPerformed(evt);
            }
        });

        deleteBtnDichvu.setText("Xoá");
        deleteBtnDichvu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnDichvuActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã dịch vụ:");

        jLabel2.setText("Tên dịch vụ:");

        jLabel3.setText("Giá:");

        jLabel4.setText("Mô tả dịch vụ:");

        motaDichVu.setColumns(20);
        motaDichVu.setRows(5);
        jScrollPane3.setViewportView(motaDichVu);

        newBtnDichvu.setText("Mới");
        newBtnDichvu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnDichvuActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/dvuvanchuyen1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maDichVu)
                    .addComponent(tenDichVu)
                    .addComponent(giaDichVu)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(413, 413, 413))
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBtnDichvu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editBtnDichvu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtnDichvu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newBtnDichvu)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtnDichvu)
                    .addComponent(editBtnDichvu)
                    .addComponent(deleteBtnDichvu)
                    .addComponent(newBtnDichvu))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(giaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dịch vụ", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTablePhieuGiaoHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã giao hàng", "Mã hoá đơn", "Ngày tạo", "Người tạo", "Trạng thái", "Địa chỉ", "Ngày giao dự kiến", "Thời điểm giao", "Người nhận", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePhieuGiaoHang.getTableHeader().setReorderingAllowed(false);
        jTablePhieuGiaoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePhieuGiaoHangMouseClicked(evt);
            }
        });
        jTablePhieuGiaoHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePhieuGiaoHangKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTablePhieuGiaoHang);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addBtnPGH.setText("Thêm");
        addBtnPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnPGHActionPerformed(evt);
            }
        });

        editBtnPGH.setText("Sửa");
        editBtnPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnPGHActionPerformed(evt);
            }
        });

        deleteBtnPGH.setText("Xoá");
        deleteBtnPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnPGHActionPerformed(evt);
            }
        });

        newBtnPGH.setText("Mới");
        newBtnPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnPGHActionPerformed(evt);
            }
        });

        jLabel6.setText("Mã giao hàng:");

        jLabel7.setText("Trạng thái:");

        jLabel8.setText("Địa chỉ:");

        diachiPGH.setColumns(20);
        diachiPGH.setRows(5);
        jScrollPane4.setViewportView(diachiPGH);

        jLabel9.setText("Ngày giao dự kiến:");

        jLabel10.setText("Thời điểm giao:");

        jLabel11.setText("Người nhận:");

        ghiChuPGH.setColumns(20);
        ghiChuPGH.setRows(5);
        jScrollPane5.setViewportView(ghiChuPGH);

        jLabel12.setText("Ghi chú:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang giao hàng", "Đã nhận hàng", "Đã huỷ đơn" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Ngày tạo:");

        jLabel14.setText("Người tạo:");

        jLabel15.setText("Mã hoá đơn:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ngayGiaoDuKienPGH)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(thoiDiemGiaoPGH)
                    .addComponent(nguoiNhanPGH)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(nguoitaoPGH)
                            .addComponent(ngaytaoPGH)
                            .addComponent(maPGH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(maHoaDonPGH))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(addBtnPGH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(editBtnPGH)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(deleteBtnPGH)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(newBtnPGH)))
                                .addGap(0, 6, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtnPGH)
                    .addComponent(editBtnPGH)
                    .addComponent(deleteBtnPGH)
                    .addComponent(newBtnPGH))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ngaytaoPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nguoitaoPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maHoaDonPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ngayGiaoDuKienPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thoiDiemGiaoPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nguoiNhanPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        locPGH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang giao hàng", "Đã nhận hàng", "Đã huỷ đơn" }));
        locPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locPGHActionPerformed(evt);
            }
        });

        jLabel16.setText("Trạng thái:");

        jButton1.setText("Lọc");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Tìm Kiếm :");

        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(locPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addGap(216, 216, 216)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_tim, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_search)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(locPGH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(txt_tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu giao hàng", jPanel2);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
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
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDichVuMouseClicked
        // TODO add your handling code here:
        int row = jTableDichVu.getSelectedRow();
        fillDataDichVu(row);
    }//GEN-LAST:event_jTableDichVuMouseClicked

    private void jTableDichVuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDichVuKeyReleased
        // TODO add your handling code here:
        int row = jTableDichVu.getSelectedRow();
        fillDataDichVu(row);
    }//GEN-LAST:event_jTableDichVuKeyReleased

    private void editBtnDichvuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnDichvuActionPerformed
        // TODO add your handling code here:
        String maDv = maDichVu.getText();
        if (!maDv.isEmpty()) {
            editDichVu(maDv);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng điền mã dịch vụ", "Không thể sửa", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_editBtnDichvuActionPerformed

    private void deleteBtnDichvuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnDichvuActionPerformed
        String maDv = maDichVu.getText();
        if (!maDv.isEmpty()) {
            int choose = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắc muốn xoá?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
            if (choose == 0) {
                deleteDichVu(maDv);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng điền mã dịch vụ", "Không thể xoá", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_deleteBtnDichvuActionPerformed

    private void newBtnDichvuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnDichvuActionPerformed
        // TODO add your handling code here:
        clearDataDichVu();
    }//GEN-LAST:event_newBtnDichvuActionPerformed

    private void editBtnPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnPGHActionPerformed
        // TODO add your handling code here:        String ma = this.maPGH.getText();
        String ma = this.maPGH.getText();
        if (!ma.isEmpty()) {
            editPhieuGiaoHang(ma);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng điền mã phiếu giao hàng", "Không thể xoá", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_editBtnPGHActionPerformed

    private void deleteBtnPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnPGHActionPerformed
        String ma = this.maPGH.getText();
        if (!ma.isEmpty()) {
            int choose = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắc muốn xoá?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
            if (choose == 0) {
                deletePhieuGiaoHang(ma);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng điền mã phiếu giao hàng", "Không thể xoá", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_deleteBtnPGHActionPerformed

    private void newBtnPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnPGHActionPerformed
        // TODO add your handling code here:
        clearDataPhieuGiaoHang();
    }//GEN-LAST:event_newBtnPGHActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void addBtnDichvuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnDichvuActionPerformed
        // TODO add your handling code here:
        if (validateDichVu()) {
            DichVu dv = readDichVu();
            dichVuService.addDichVu(dv);
            loadData();
            fillTableDichVu();
        } else {
            JOptionPane.showMessageDialog(null, "Không thể thêm mới dịch vụ", "Không thể thêm mới", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_addBtnDichvuActionPerformed

    private void jTablePhieuGiaoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhieuGiaoHangMouseClicked
        // TODO add your handling code here:
        int row = jTablePhieuGiaoHang.getSelectedRow();
        fillDataPhieuGiaoHang(row);
    }//GEN-LAST:event_jTablePhieuGiaoHangMouseClicked

    private void jTablePhieuGiaoHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePhieuGiaoHangKeyReleased
        // TODO add your handling code here:
        int row = jTablePhieuGiaoHang.getSelectedRow();
        fillDataPhieuGiaoHang(row);
    }//GEN-LAST:event_jTablePhieuGiaoHangKeyReleased

    private void addBtnPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnPGHActionPerformed
        // TODO add your handling code here:
        if (validatePhieuGiaoHang()) {
            PhieuGiaoHang pgh = readPhieuGiaoHang();
            phieuGiaoHangService.addData(pgh);
            loadData();
            fillTableDPhieuGiaoHang();
        } else {
            JOptionPane.showMessageDialog(null, "Không thể thêm mới dịch vụ", "Không thể thêm mới", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_addBtnPGHActionPerformed

    private void locPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locPGHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_locPGHActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("Index: " + locPGH.getSelectedIndex());
        if (locPGH.getSelectedIndex() == 0) {
            listPhieuGiaoHang = phieuGiaoHangService.getData();
        } else {
            String filter = locPGH.getSelectedItem().toString();
            listPhieuGiaoHang = phieuGiaoHangService.getData(filter);
        }
        fillTableDPhieuGiaoHang();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        String tim = txt_tim.getText();
        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "chưa nhập đủ thông tin");
        }
        tim = "%" + tim + "%";
        if (phieuGiaoHangService.Search(tim).size() > 0) {
            this.fillTableDTimKiemPhieuGiaoHang(phieuGiaoHangService.Search(tim));
            JOptionPane.showMessageDialog(this, "tìm thành công");
        } else {
            JOptionPane.showMessageDialog(this, "tìm thất bại");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_searchActionPerformed

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

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        // TODO add your handling code here:
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_jLabel37MouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(PhieuGiaoHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuGiaoHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuGiaoHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuGiaoHangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhieuGiaoHangView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtnDichvu;
    private javax.swing.JButton addBtnPGH;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton deleteBtnDichvu;
    private javax.swing.JButton deleteBtnPGH;
    private javax.swing.JTextArea diachiPGH;
    private javax.swing.JButton editBtnDichvu;
    private javax.swing.JButton editBtnPGH;
    private javax.swing.JTextArea ghiChuPGH;
    private javax.swing.JTextField giaDichVu;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDichVu;
    private javax.swing.JTable jTablePhieuGiaoHang;
    private javax.swing.JLabel jblDotGiamGia1;
    private javax.swing.JLabel jblHome1;
    private javax.swing.JLabel jblKhachHang1;
    private javax.swing.JLabel jblNhanVien1;
    private javax.swing.JLabel jblPhieuGiaoHang1;
    private javax.swing.JLabel jlbPhieuGiamGia1;
    private javax.swing.JLabel jlbSanPham1;
    private javax.swing.JLabel jlbTaiKhoan1;
    private javax.swing.JComboBox<String> locPGH;
    private javax.swing.JTextField maDichVu;
    private javax.swing.JTextField maHoaDonPGH;
    private javax.swing.JTextField maPGH;
    private javax.swing.JTextArea motaDichVu;
    private javax.swing.JButton newBtnDichvu;
    private javax.swing.JButton newBtnPGH;
    private javax.swing.JTextField ngayGiaoDuKienPGH;
    private javax.swing.JTextField ngaytaoPGH;
    private javax.swing.JTextField nguoiNhanPGH;
    private javax.swing.JTextField nguoitaoPGH;
    private javax.swing.JTextField tenDichVu;
    private javax.swing.JTextField thoiDiemGiaoPGH;
    private javax.swing.JTextField txt_tim;
    // End of variables declaration//GEN-END:variables
}
