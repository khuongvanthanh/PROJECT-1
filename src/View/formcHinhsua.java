/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model_SanPham.SanPham;
import Model_SanPham.Tong;
import Service_SanPham.SanPhamService;
import Service_SanPham.TongService;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class formcHinhsua extends javax.swing.JFrame {

    private SanPhamService serviceSp = new SanPhamService();
    private List<Tong> list = new ArrayList<>();
    private TongService service = new TongService();
    private int index = -1;
    private DefaultTableModel tblModel = new DefaultTableModel();

    public formcHinhsua() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setSize(1500, 800);
        setLocationRelativeTo(null);
        fillDataHTQL(service.getList());
        fillDataSP(serviceSp.getList());

    }

    public void fillDataSP(List<SanPham> sp) {
        tblModel = (DefaultTableModel) tblSanPham.getModel();
        tblModel.setRowCount(0);
        for (SanPham SP : sp) {
            tblModel.addRow(SP.toDatarow());
        }
    }

    public void fillDataHTQL(List<Tong> cl) {
        tblModel = (DefaultTableModel) tblBangCHinh.getModel();
        tblModel.setRowCount(0);
        for (Tong t : cl) {
            tblModel.addRow(t.toDataRow());
        }
    }

    public void showData(int index) {
        Tong bt = service.getList().get(index);
        txtBT.setText(bt.getbTheSP());
        cboMaSp.setSelectedItem(bt.getMaSP());
        txtML.setText(bt.getMauLong());
        txtTuoi.setText(bt.getTuoi() + "");
        txtCn.setText(bt.getCn() + "");
        txtCC.setText(bt.getCc() + "");
        txtNs.setText(bt.getNgaySinh());
        Boolean gt = bt.getgTinh();
        if (gt == true) {
            rdoDuc.setSelected(true);
        } else {
            rdoCai.setSelected(true);
        }

        txtGb.setText(bt.getGiaB() + "");
        cboTP.setSelectedItem(bt.getTiem());
        txtYeuThich.setText(bt.getTHUCANHOPLY());
        txtDiUng.setText(bt.getTHUCANDIUNG());
        txtSoThich.setText(bt.getTHOIQUEN());
        String hinhAnh = bt.getHinhAnh();
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            lblHinhAnh.setText(hinhAnh);
            String imagePath = "C:\\Users\\ASUS\\Desktop\\hinh\\" + hinhAnh;
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            lblHinhAnh.setIcon(scaledIcon);
        } else {
            // Hình ảnh mặc định "no image" (ví dụ: hình trắng)
            ImageIcon noImageIcon = new ImageIcon("path_to_no_image.jpg"); // Thay thế "path_to_no_image.jpg" bằng đường dẫn đến hình ảnh "no image"
            Image noImage = noImageIcon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledNoImage = new ImageIcon(noImage);
            lblHinhAnh.setIcon(scaledNoImage);
            lblHinhAnh.setText("No Image");
        }
    }

    public Tong readFormChinh() {
        String ma = cboMaSp.getSelectedItem().toString();
        String bt = txtBT.getText().trim();
        String mauLong = txtML.getText().trim();
        int tuoi = Integer.parseInt(txtTuoi.getText().trim());
        float cn = Float.parseFloat(txtCn.getText().trim());
        float cc = Float.parseFloat(txtCC.getText().trim());
        BigDecimal giaBan = new BigDecimal(txtGb.getText().trim());
        Boolean gtinh = rdoDuc.isSelected();
        String ns = txtNs.getText().trim();
        String tiem = (String) cboTP.getSelectedItem().toString();
        String yt = txtYeuThich.getText().trim();
        String dy = txtDiUng.getText().trim();
        String Ht = txtSoThich.getText().trim();
        String duongDanHinhAnh = lblHinhAnh.getText().trim(); // Đường dẫn hình ảnh
        return new Tong(bt, ma, cn, tuoi, mauLong, cc, ns, giaBan, gtinh, tiem, duongDanHinhAnh, yt, dy, Ht);
    }

    public void fillLoc() {
        Boolean duc = chKDuc.isSelected();
        Boolean cai = CHKCai.isSelected();

        tblModel = (DefaultTableModel) tblBangCHinh.getModel();
        tblModel.setRowCount(0);
        for (Tong cl : service.filterRecords(duc, cai)) {
            tblModel.addRow(cl.toDataRow());
        }
    }

    public boolean checkDuLieu() {
        // Kiểm tra dữ liệu trong PhieuGiamGia
        if (txtBT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã BT.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtYeuThich.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Thức ăn yêu thích.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtDiUng.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập thức ăn dị ứng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtGb.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập gia bán.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra Gioi tinh đã được chọn hay chưa
        if (!rdoDuc.isSelected() && !rdoCai.isSelected()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Gioi tinh.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra dữ liệu trong DieuKienGiamGia
        if (txtML.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Màu lông", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtNs.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập Ngày sinh.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtSoThich.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập sở thích.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra LoaiGiamGia đã được chọn hay chưa
        return true; // Nếu thông tin đều hợp lệ, trả về true
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtBT = new javax.swing.JTextField();
        txtNs = new javax.swing.JTextField();
        txtML = new javax.swing.JTextField();
        txtTuoi = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txtCn = new javax.swing.JTextField();
        txtCC = new javax.swing.JTextField();
        rdoDuc = new javax.swing.JRadioButton();
        rdoCai = new javax.swing.JRadioButton();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtGb = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        cboTP = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtYeuThich = new javax.swing.JTextField();
        txtDiUng = new javax.swing.JTextField();
        txtSoThich = new javax.swing.JTextField();
        cboMaSp = new javax.swing.JComboBox<>();
        lblHinhAnh = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangCHinh = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chKDuc = new javax.swing.JCheckBox();
        CHKCai = new javax.swing.JCheckBox();
        btnFill = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
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
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabel43.setText("Biến thể sản phẩm");

        jLabel44.setText("Mã sản phẩm");

        jLabel45.setText("Ngày sinh");

        jLabel46.setText("Màu lông");

        jLabel47.setText("Tuổi");

        jLabel48.setText("Cân nặng");

        jLabel49.setText("Chiều cao");

        jLabel50.setText("Giới tính");

        buttonGroup1.add(rdoDuc);
        rdoDuc.setText("Đực");

        buttonGroup1.add(rdoCai);
        rdoCai.setText("Cái");

        jLabel51.setText("Giá bán");

        jLabel52.setText("Tiêm phòng");

        jLabel55.setText("Thức ăn yêu thích");

        cboTP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã tiêm phòng", "Chưa tiêm phòng", " " }));

        jLabel56.setText("Thức ăn dị ứng");

        jLabel57.setText("Hoạt động sở thích");

        cboMaSp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SP0001", "SP0002", "SP0003", "SP0004", "SP0005" }));

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBT, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNs, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtML, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel47)
                            .addComponent(jLabel49))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCC, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel18Layout.createSequentialGroup()
                                    .addComponent(rdoDuc, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rdoCai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jLabel50))
                .addGap(52, 52, 52)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56))
                .addGap(26, 26, 26)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiUng, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoThich, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel57)
                                .addComponent(txtSoThich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel43)
                                .addComponent(txtBT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel47)
                                .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel51)
                                .addComponent(txtGb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel48)
                                    .addComponent(txtCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52)
                                    .addComponent(cboTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel55)
                                        .addComponent(txtYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel45)
                                        .addComponent(txtNs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel49)
                                        .addComponent(txtCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel56)
                                .addComponent(txtDiUng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel50)
                                .addComponent(rdoDuc)
                                .addComponent(rdoCai)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblBangCHinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Biến Thể SP", "Mã SP", "Cân nặng", "Tuổi", "Màu lông", "Chiều cao", "Ngày sinh", "Giá bán", "Giới tính", "Tiêm phòng", "Hình ảnh", "Món yêu thích", "Món dị ứng", "Thói quen"
            }
        ));
        tblBangCHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangCHinhMouseClicked(evt);
            }
        });
        tblBangCHinh.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tblBangCHinhComponentHidden(evt);
            }
        });
        jScrollPane1.setViewportView(tblBangCHinh);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/home.png"))); // NOI18N
        jButton1.setText("Trang chủ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/document.png"))); // NOI18N
        jButton3.setText("Danh sách sản phẩm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Lọc");

        buttonGroup2.add(chKDuc);
        chKDuc.setSelected(true);
        chKDuc.setText("Đực");

        buttonGroup2.add(CHKCai);
        CHKCai.setText("Cái");

        btnFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/filter.png"))); // NOI18N
        btnFill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chKDuc, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CHKCai, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnFill)
                .addGap(37, 37, 37))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CHKCai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chKDuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        jLabel2.setText("Tìm kiếm");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnTimKiem)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/cleaning.png"))); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Giống", "Tồn kho"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Chỉnh sửa dữ liệu");

        jPanel20.setBackground(new java.awt.Color(102, 204, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator6)
            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jblHome1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbSanPham1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPhieuGiamGia1)
                    .addComponent(jblDotGiamGia1)
                    .addComponent(jblNhanVien1)
                    .addComponent(jblKhachHang1)
                    .addComponent(jblPhieuGiaoHang1))
                .addGap(34, 34, 34))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jlbTaiKhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator4))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTaiKhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel37)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(468, 468, 468)
                        .addComponent(jLabel3)))
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1662, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlbTaiKhoan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbTaiKhoan1MouseClicked
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_jlbTaiKhoan1MouseClicked

    private void jblPhieuGiaoHang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblPhieuGiaoHang1MouseClicked
        // TODO add your handling code here:
        PhieuGiaoHangView pgh = new PhieuGiaoHangView();
        pgh.setVisible(true);
    }//GEN-LAST:event_jblPhieuGiaoHang1MouseClicked

    private void jblKhachHang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblKhachHang1MouseClicked
        KhachHangView khv = new KhachHangView();
        khv.setVisible(true);
    }//GEN-LAST:event_jblKhachHang1MouseClicked

    private void jblNhanVien1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblNhanVien1MouseClicked
        NhanVienView nvv = new NhanVienView();
        nvv.setVisible(true);
    }//GEN-LAST:event_jblNhanVien1MouseClicked

    private void jblDotGiamGia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblDotGiamGia1MouseClicked
        DotGiamGiaView dggv = new DotGiamGiaView();
        dggv.setVisible(true);
    }//GEN-LAST:event_jblDotGiamGia1MouseClicked

    private void jlbPhieuGiamGia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPhieuGiamGia1MouseClicked
        PhieuGiamGiaView pggv = new PhieuGiamGiaView();
        pggv.setVisible(true);
    }//GEN-LAST:event_jlbPhieuGiamGia1MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        HoaDonView hdv = new HoaDonView();
        hdv.setVisible(true);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jlbSanPham1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSanPham1MouseClicked
        Chinh c = new Chinh();
        c.setVisible(true);
    }//GEN-LAST:event_jlbSanPham1MouseClicked

    private void jblHome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblHome1MouseClicked
        // TODO add your handling code here:
        TrangChuChinhView tccv = new TrangChuChinhView();
        tccv.setVisible(true);
    }//GEN-LAST:event_jblHome1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtBT.setText("");
        cboMaSp.setSelectedItem("SP0001");
        txtML.setText("");
        txtTuoi.setText("");
        txtCn.setText("");
        txtCC.setText("");
        txtNs.setText("");

        rdoDuc.setSelected(true);

        txtGb.setText("");
        cboTP.setSelectedItem("Đã tiêm phòng");
        txtYeuThich.setText("");
        txtDiUng.setText("");
        txtSoThich.setText("");
        fillDataHTQL(service.getList());
        fillDataSP(serviceSp.getList());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String maBT = txtSearch.getText();

        list = service.search(maBT);
        fillDataHTQL(list);

        tblBangCHinh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblBangCHinh.getSelectedRow(); // Lấy chỉ số hàng được chọn

                if (row >= 0) {
                    // Lấy dữ liệu từ hàng được chọn
                    Object[] rowData = list.get(row).toDataRow();

                    // Hiển thị dữ liệu lên các thành phần giao diện
                    txtBT.setText(rowData[0].toString());
                    cboMaSp.setSelectedItem(rowData[1].toString());
                    txtML.setText(rowData[2].toString());
                    txtTuoi.setText(rowData[3].toString());
                    txtCn.setText(rowData[4].toString());
                    txtCC.setText(rowData[5].toString());
                    txtNs.setText(rowData[6].toString());

                    Boolean gt = (rowData[7].toString().equals("Đực"));
                    cboTP.setSelectedItem(rowData[8].toString());
                    txtGb.setText(rowData[9].toString());
                    txtYeuThich.setText(rowData[10].toString());
                    txtDiUng.setText(rowData[11].toString());
                    txtSoThich.setText(rowData[12].toString());

                    if (gt) {
                        rdoCai.setSelected(true);
                    } else {
                        rdoDuc.setSelected(true);
                    }

                    // Hiển thị thêm thông tin cần thiết lên các thành phần khác
                }
            }
        });
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnFillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFillActionPerformed
        Boolean duc = chKDuc.isSelected();
        Boolean cai = CHKCai.isSelected();
        List<Tong> dlc = service.filterRecords(duc, cai); // Kiểm tra các trường hợp lọc và hiển thị thông tin tương ứng

        if (duc || cai) {
            fillLoc();

            if (duc) {
                fillLoc();
            }
            if (cai) {
                fillLoc();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không có mục nào được chọn");
        }
    }//GEN-LAST:event_btnFillActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int selectedRow = tblBangCHinh.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần xóa.");
            return;
        }
        Tong r = this.service.getList().get(selectedRow);
        int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dữ liệu này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (service.xoa(r) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillDataHTQL(service.getList());

            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (checkDuLieu()) {

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật dữ liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Tong cl = readFormChinh();
                int index = tblBangCHinh.getSelectedRow();
                String maBt = tblBangCHinh.getValueAt(index, 0).toString();

                cl.setbTheSP(maBt);

                service.Update01(cl, maBt);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                fillDataHTQL(service.getList());
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String ma = txtBT.getText();
        if (service.checkTrungMa(ma)) {
            JOptionPane.showMessageDialog(this, "Trung ma");
        } else if (checkDuLieu()) {

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm phiếu giảm giá?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Tong cl = readFormChinh();
                service.insertData(cl);
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                fillDataHTQL(service.getList());
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        BienTheSP pggv = new BienTheSP();
        pggv.setVisible(true);

        // Ẩn cửa sổ hiện tại hoặc giải phóng tài nguyên
        setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Chinh pggv = new Chinh();
        pggv.setVisible(true);

        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblBangCHinhComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblBangCHinhComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBangCHinhComponentHidden

    private void tblBangCHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangCHinhMouseClicked
        int index = tblBangCHinh.getSelectedRow();
        showData(index);
    }//GEN-LAST:event_tblBangCHinhMouseClicked

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String duongDan = chooser.getSelectedFile().getAbsolutePath();

            // Lưu tên file hình ảnh
            String tenFileAnh = chooser.getSelectedFile().getName();

            // Hiển thị tên file ảnh lên JLabel
            lblHinhAnh.setText(tenFileAnh);

            // Đọc và chỉnh kích thước ảnh
            ImageIcon imageIcon = new ImageIcon(duongDan);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(130, 100, Image.SCALE_SMOOTH); // Chỉnh kích thước ảnh
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Hiển thị ảnh đã được chỉnh kích thước lên JLabel
            lblHinhAnh.setIcon(scaledIcon);
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked
    public void fillData02(List<Tong> cl) {
        DefaultTableModel tblModelBienThe1 = (DefaultTableModel) tblBangCHinh.getModel();

        tblModelBienThe1.setRowCount(0);

        tblBangCHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblBangCHinh.getSelectedRow();

                if (selectedRow != -1) {
                    Tong selectedBienTheSP = cl.get(selectedRow);
                    tblModelBienThe1.setRowCount(0);  // Clear the existing rows
                    tblModelBienThe1.addRow(selectedBienTheSP.toDataRow());
                }
            }
        });

        for (Tong t : cl) {
            tblModelBienThe1.addRow(t.toDataRow());  // Use tblModelBienThe1 instead of tblModel
        }
    }
    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int selectedRow = tblSanPham.getSelectedRow();

        if (selectedRow != -1) {
            String maSP = tblSanPham.getValueAt(selectedRow, 0).toString();
            List<Tong> bienTheSPList = service.searchBienTheSPByMaSP(maSP);

            if (bienTheSPList != null && !bienTheSPList.isEmpty()) {
                // Assuming fillData02 is a method of the same class
                fillData02(bienTheSPList);
            } else {
                // Clear tblBangCHinh if no data is found
                DefaultTableModel tblModelBienThe1 = (DefaultTableModel) tblBangCHinh.getModel();
                tblModelBienThe1.setRowCount(0);
            }
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

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
            java.util.logging.Logger.getLogger(formcHinhsua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formcHinhsua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formcHinhsua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formcHinhsua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formcHinhsua().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CHKCai;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFill;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboMaSp;
    private javax.swing.JComboBox<String> cboTP;
    private javax.swing.JCheckBox chKDuc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JRadioButton rdoCai;
    private javax.swing.JRadioButton rdoDuc;
    private javax.swing.JTable tblBangCHinh;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtBT;
    private javax.swing.JTextField txtCC;
    private javax.swing.JTextField txtCn;
    private javax.swing.JTextField txtDiUng;
    private javax.swing.JTextField txtGb;
    private javax.swing.JTextField txtML;
    private javax.swing.JTextField txtNs;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoThich;
    private javax.swing.JTextField txtTuoi;
    private javax.swing.JTextField txtYeuThich;
    // End of variables declaration//GEN-END:variables
}
