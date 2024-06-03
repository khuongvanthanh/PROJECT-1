/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model_SanPham.BienTheSanPham;
import Model_SanPham.HoTroQuanLy;
import Model_SanPham.NCC;
import Model_SanPham.SanPham;
import Service_SanPham.BienTheSPService;
import Service_SanPham.HTQLservice;
import Service_SanPham.NCCservice;
import Service_SanPham.SanPhamService;
import View.formcHinhsua;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class Chinh extends javax.swing.JFrame {

    private HTQLservice serviceht = new HTQLservice();
    private SanPhamService sp = new SanPhamService();
    private NCCservice serviceNCC = new NCCservice();
    private final BienTheSPService service = new BienTheSPService();
    private int index = -1;
    private DefaultTableModel tblModel = new DefaultTableModel();

    public Chinh() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setSize(1500, 800);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        fillDataSP(sp.getList());
        fillData02(service.getList(), serviceNCC.getList(), serviceht.getList());
        fillDataNC(serviceNCC.getList());
        fillDataHTQL(serviceht.getList());
        fillData(service.getList());
    }

    public void fillDataHTQL(List<HoTroQuanLy> cl) {
        tblModel = (DefaultTableModel) tblHTQL.getModel();
        tblModel.setRowCount(0);
        for (HoTroQuanLy htql : cl) {
            tblModel.addRow(htql.toDataRow());
        }
    }

    public void fillData(List<BienTheSanPham> cl) {
        tblModel = (DefaultTableModel) tblBienThe.getModel();
        tblModel.setRowCount(0);
        for (BienTheSanPham bt : cl) {
            tblModel.addRow(bt.toDataRow());
        }
    }

    public void fillData02(List<BienTheSanPham> cl, List<NCC> ncc, List<HoTroQuanLy> htql) {
        DefaultTableModel tblModelBienThe1 = (DefaultTableModel) tblBienThe.getModel();
        DefaultTableModel tblModelNCC = (DefaultTableModel) tblNCC.getModel();
        DefaultTableModel tblModelHTQL = (DefaultTableModel) tblHTQL.getModel();

        tblModelBienThe1.setRowCount(0);
        tblModelNCC.setRowCount(0);
        tblModelHTQL.setRowCount(0);

        tblBienThe.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblBienThe.getSelectedRow();

                if (selectedRow != -1) {
                    BienTheSanPham selectedBienTheSP = cl.get(selectedRow);
                    tblModelBienThe1.setRowCount(0);  // Clear the existing rows
                    tblModelBienThe1.addRow(selectedBienTheSP.toDataRow());

                }
            }
        });

        tblNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblNCC.getSelectedRow();

                if (selectedRow != -1) {
                    NCC selectedBienTheNCC = ncc.get(selectedRow);
                    tblModelNCC.setRowCount(0);  // Clear the existing rows
                    tblModelNCC.addRow(selectedBienTheNCC.todataRow());// Add the selected row

                }
            }
        });

        tblHTQL.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblHTQL.getSelectedRow();

                if (selectedRow != -1) {
                    HoTroQuanLy selectedBienThehtql = htql.get(selectedRow);
                    tblModelHTQL.setRowCount(0);  // Clear the existing rows
                    tblModelHTQL.addRow(selectedBienThehtql.toDataRow());// Add the selected row

                }
            }
        });

        for (BienTheSanPham bt : cl) {
            tblModelBienThe1.addRow(bt.toDataRow());
        }

        for (NCC ncc1 : ncc) {
            tblModelNCC.addRow(ncc1.todataRow());
        }

        for (HoTroQuanLy HT : htql) {
            tblModelHTQL.addRow(HT.toDataRow());
        }
    }

    public void fillDataNC(List<NCC> ncc) {
        tblModel = (DefaultTableModel) tblNCC.getModel();
        tblModel.setRowCount(0);
        for (NCC n : ncc) {
            tblModel.addRow(n.todataRow());
        }
    }

    public void fillDataSP(List<SanPham> sp) {
        tblModel = (DefaultTableModel) tblSanPham2.getModel();
        tblModel.setRowCount(0);
        for (SanPham SP : sp) {
            tblModel.addRow(SP.toDatarow());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSanPham2 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBienThe = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNCC = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHTQL = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        xem1 = new javax.swing.JButton();
        xem2 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jblHome = new javax.swing.JLabel();
        jlbSanPham = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jlbPhieuGiamGia = new javax.swing.JLabel();
        jblDotGiamGia = new javax.swing.JLabel();
        jblNhanVien = new javax.swing.JLabel();
        jblKhachHang = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jblPhieuGiaoHang = new javax.swing.JLabel();
        jlbTaiKhoan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Những giống chó có trong shop", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        tblSanPham2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã sản phẩm ", "Giống", "Tồn kho"
            }
        ));
        tblSanPham2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPham2tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblSanPham2);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        tblBienThe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Biến thể sản phẩm", "Mã sản phẩm ", "Màu lông", "Tuổi", "Cân nặng", "Chiều cao", "Ngày sinh", "Giới tính", "Giá bán", "Tiêm phòng"
            }
        ));
        tblBienThe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBienTheMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBienThe);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhà cung cấp", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        tblNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên NCC", "SDT", "Hợp đồng"
            }
        ));
        tblNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNCCMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNCC);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hỗ trợ quản lý", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        tblHTQL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã biến thể sản phẩm", "Mã sản phẩm", "Thức ăn yêu thích", "Thức ăn dị ứng ", "Hoạt đông sở thích", "Hotline "
            }
        ));
        jScrollPane5.setViewportView(tblHTQL);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        xem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/document.png"))); // NOI18N
        xem1.setText("Xem danh sách sản phẩm");
        xem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xem1ActionPerformed(evt);
            }
        });

        xem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/user-add.png"))); // NOI18N
        xem2.setText("Chỉnh sửa sản phẩm");
        xem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xem2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xem1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(xem2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xem1)
                    .addComponent(xem2))
                .addGap(25, 25, 25))
        );

        jPanel15.setBackground(new java.awt.Color(102, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thoat.png"))); // NOI18N
        jLabel23.setText("Đăng xuất");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo (1).jpg"))); // NOI18N

        jblPhieuGiaoHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Phieugiaohang.png"))); // NOI18N
        jblPhieuGiaoHang.setText("Phiếu giao hàng");
        jblPhieuGiaoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblPhieuGiaoHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jblPhieuGiaoHangMouseEntered(evt);
            }
        });

        jlbTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taikhoan.png"))); // NOI18N
        jlbTaiKhoan.setText("Tài khoản");
        jlbTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbTaiKhoanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 64, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jblHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPhieuGiamGia)
                    .addComponent(jblDotGiamGia)
                    .addComponent(jblNhanVien)
                    .addComponent(jblKhachHang)
                    .addComponent(jblPhieuGiaoHang))
                .addGap(34, 34, 34))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel25)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel23)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 165, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPham2tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPham2tblSanPhamMouseClicked
        int selectedRow = tblSanPham2.getSelectedRow();

        if (selectedRow != -1) {
            String maSP = tblSanPham2.getValueAt(selectedRow, 0).toString();
            List<BienTheSanPham> bienTheSPList = service.searchBienTheSPByMaSP(maSP);
            List<NCC> Bienncc = serviceNCC.searchBienTheSPByMaSP(maSP);
            List<HoTroQuanLy> ht = serviceht.searchBienTheSPByMaSP(maSP);

            if (bienTheSPList != null && !bienTheSPList.isEmpty() && Bienncc != null && !Bienncc.isEmpty() && ht != null && !ht.isEmpty()) {
                BienTheSanPham firstBienTheSP = bienTheSPList.get(0);
                NCC nc = Bienncc.get(0);
                HoTroQuanLy htt = ht.get(0);

                fillData02(bienTheSPList, Bienncc, ht);

            }
        }
    }//GEN-LAST:event_tblSanPham2tblSanPhamMouseClicked

    private void tblBienTheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBienTheMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBienTheMouseClicked

    private void tblNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNCCMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblNCCMouseClicked

    private void xem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xem1ActionPerformed
        BienTheSP pggv = new BienTheSP();
        pggv.setVisible(true);

// Ẩn cửa sổ hiện tại hoặc giải phóng tài nguyên
        setVisible(false);
    }//GEN-LAST:event_xem1ActionPerformed

    private void xem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xem2ActionPerformed
        formcHinhsua pggv = new formcHinhsua();
        pggv.setVisible(true);

// Ẩn cửa sổ hiện tại hoặc giải phóng tài nguyên
        setVisible(false);    }//GEN-LAST:event_xem2ActionPerformed

    private void jblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblHomeMouseClicked
        // TODO add your handling code here:
        TrangChuChinhView tccv = new TrangChuChinhView();
        tccv.setVisible(true);
    }//GEN-LAST:event_jblHomeMouseClicked

    private void jlbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSanPhamMouseClicked
        Chinh c = new Chinh();
        c.setVisible(true);
    }//GEN-LAST:event_jlbSanPhamMouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        HoaDonView donView = new HoaDonView();
        donView.setVisible(true);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jlbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPhieuGiamGiaMouseClicked
        PhieuGiamGiaView pggv = new PhieuGiamGiaView();
        pggv.setVisible(true);
    }//GEN-LAST:event_jlbPhieuGiamGiaMouseClicked

    private void jblDotGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblDotGiamGiaMouseClicked
        DotGiamGiaView dggv = new DotGiamGiaView();
        dggv.setVisible(true);
    }//GEN-LAST:event_jblDotGiamGiaMouseClicked

    private void jblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblNhanVienMouseClicked

    }//GEN-LAST:event_jblNhanVienMouseClicked

    private void jblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblKhachHangMouseClicked
        KhachHangView khv = new KhachHangView();
        khv.setVisible(true);
    }//GEN-LAST:event_jblKhachHangMouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jblPhieuGiaoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblPhieuGiaoHangMouseClicked
        // TODO add your handling code here:
        PhieuGiaoHangView giaoHangView = new PhieuGiaoHangView();
        giaoHangView.setVisible(true);
    }//GEN-LAST:event_jblPhieuGiaoHangMouseClicked

    private void jblPhieuGiaoHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblPhieuGiaoHangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jblPhieuGiaoHangMouseEntered

    private void jlbTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbTaiKhoanMouseClicked
        ThongTinTaiKhoanView tttv = new ThongTinTaiKhoanView();
        tttv.setVisible(true);
    }//GEN-LAST:event_jlbTaiKhoanMouseClicked

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
            java.util.logging.Logger.getLogger(Chinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jblDotGiamGia;
    private javax.swing.JLabel jblHome;
    private javax.swing.JLabel jblKhachHang;
    private javax.swing.JLabel jblNhanVien;
    private javax.swing.JLabel jblPhieuGiaoHang;
    private javax.swing.JLabel jlbPhieuGiamGia;
    private javax.swing.JLabel jlbSanPham;
    private javax.swing.JLabel jlbTaiKhoan;
    private javax.swing.JTable tblBienThe;
    private javax.swing.JTable tblHTQL;
    private javax.swing.JTable tblNCC;
    private javax.swing.JTable tblSanPham2;
    private javax.swing.JButton xem1;
    private javax.swing.JButton xem2;
    // End of variables declaration//GEN-END:variables
}
