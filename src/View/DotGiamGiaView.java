package View;



import Service_DotGiamGia.DotGiamGiaService;
import Service_DotGiamGia.DotGiamGiaService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import Model_DotGiamGia.DotGiamGia;
import Model_DotGiamGia.LoaiGiamGia;
import Model_DotGiamGia.QuyTacGiamGia;
import Service_DotGiamGia.LoaiGiamGiaService;
import Service_DotGiamGia.QuyTacGiamGiaService;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author hung0
 */
public class DotGiamGiaView extends javax.swing.JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableModel tableModel1 = new DefaultTableModel();
    private DefaultTableModel model = new DefaultTableModel();
    private List<DotGiamGia> listdgg = new ArrayList<>();
    private List<LoaiGiamGia> listlgg = new ArrayList<>();
    private DotGiamGiaService giamGiaService = new DotGiamGiaService();
    private LoaiGiamGiaService lggService = new LoaiGiamGiaService();
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private QuyTacGiamGiaService qtggService = new QuyTacGiamGiaService();
    private List<QuyTacGiamGia> listqtgg = new ArrayList<>();
    private DefaultTableModel modelqtgg = new DefaultTableModel();

    public DotGiamGiaView() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        int screenWidth = 1400;
        int screenHeight = 756;
        // Cài đặt kích thước của cửa sổ
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        tableModel = (DefaultTableModel) tblDotGiamGia.getModel();
        tableModel1 = (DefaultTableModel) tblLoaiGiamGia.getModel();
        model = (DefaultTableModel) tblloaigiamgia.getModel();
        listdgg = giamGiaService.getAll();
        showdata(listdgg);
        listlgg = lggService.getAlllgg();
        showdatalgg(listlgg);
        showdatatklgg(listlgg);
        modelqtgg = (DefaultTableModel) tblQuytacgiamgia.getModel();
        listqtgg = qtggService.getAllqtgg();
        fillqtgg(listqtgg);
        timKiemBangChinh();
        timKiemBangQuyTacGiamGia();
    }

    public void showdata(List<DotGiamGia> giamgia) {
        tableModel.setRowCount(0);
        for (DotGiamGia gg : giamgia) {
            tableModel.addRow(new Object[]{
                gg.getMaDGG(), gg.getTenDGG(), gg.getMaLGG(), gg.getMaNV(), gg.getPhanTramGG(), gg.getNgayBatDau(), gg.getNgayKetThuc()
            });
        }
    }

    public void showdatalgg(List<LoaiGiamGia> loaigiamgia) {
        tableModel1.setRowCount(0);
        for (LoaiGiamGia lgg : loaigiamgia) {
            tableModel1.addRow(new Object[]{
                lgg.getMaLGG(), lgg.getTenLGG(), lgg.getGiaTriGG(), lgg.getTrangThai() == true ? "Áp Dụng" : "Không còn áp dụng", lgg.getMoTa()
            });
        }
    }

    public void showdatatklgg(List<LoaiGiamGia> loaigiamgia1) {
        model.setRowCount(0);
        for (LoaiGiamGia lgg : loaigiamgia1) {
            model.addRow(new Object[]{
                lgg.getMaLGG(), lgg.getTenLGG(), lgg.getGiaTriGG(), lgg.getTrangThai() == true ? "Áp Dụng" : "Không còn áp dụng", lgg.getMoTa()
            });
        }
    }

    public void fillqtgg(List<QuyTacGiamGia> quytacgg) {
        modelqtgg.setRowCount(0);
        for (QuyTacGiamGia qtgg1 : quytacgg) {
            modelqtgg.addRow(new Object[]{
                qtgg1.getMaQTGG(), qtgg1.getTenQtgg(), qtgg1.getSolanAD(), qtgg1.getMaDGG(), qtgg1.getGiatrimin(), qtgg1.getGiatriMax(), qtgg1.getMoTa()
            });
        }
    }

    private void locDuLieu(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblDotGiamGia.setRowSorter(sorter);
        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();
        filters.add(RowFilter.regexFilter("(?i)" + searchText, 0));
        filters.add(RowFilter.regexFilter("(?i)" + searchText, 1));

        RowFilter<Object, Object> combinedFilter = RowFilter.orFilter(filters);
        sorter.setRowFilter(combinedFilter);
    }

    private void timKiemBangChinh() {
        txtTK.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                locDuLieu(txtTK.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                locDuLieu(txtTK.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                locDuLieu(txtTK.getText());
            }
        });
    }

    private void timKiemBangQuyTacGiamGia() {
        txttkqtgg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                locDuLieu(txttkqtgg.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                locDuLieu(txttkqtgg.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                locDuLieu(txttkqtgg.getText());
            }
        });
    }

    public void clear() {
        txtMaDGG.setText("");
        txtTenDGG.setText("");
        txtGiaTriKhuyenMai.setText("");
        txtLGG.setText("");
        txtMaNV.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");

    }

    public void clearlgg() {
        txtMaLGG.setText("");
        txtTenLGG.setText("");
        txtMoTa.setText("");
        txtGiaTriGG.setText("");
        rdAdung.setSelected(true);
    }

    public void clearqtgg() {
        txtMaqtgg.setText("");
        txtTenqtgg.setText("");
        txtMadgg.setText("");
        txtsolanapdung.setText("");
        txtgiatrimin.setText("");
        txtgiatrimax.setText("");
        txtmotaqtgg.setText("");
    }

    private Boolean vaLiDate() {
        if (txtMaDGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Đợt Giảm Giá");
            return false;
        }
        if (txtTenDGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên Đợt Giảm Giá");
            return false;
        }
        if (txtLGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Loại Giảm Giá");
            return false;
        }
        if (txtMaNV.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Nhân Viên");
            return false;
        }
        if (txtGiaTriKhuyenMai.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Giá Trị Khuyến Mãi");
            return false;
        }
        if (txtNgayBatDau.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày Bắt Đầu Đợt Giảm Giá");
            return false;
        }
        if (txtNgayKetThuc.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày Kết Thúc Đợt Giảm Giá");
            return false;
        }

        return true;
    }

    private Boolean valiDatesua() {
        if (txtMaDGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Đợt Giảm Giá");
            return false;
        }
        if (txtTenDGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên Đợt Giảm Giá");
            return false;
        }
        if (txtLGG.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Loại Giảm Giá");
            return false;
        }
        if (txtMaNV.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Nhân Viên");
            return false;
        }
        if (txtGiaTriKhuyenMai.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Giá Trị Khuyến Mãi");
            return false;
        }
        if (txtNgayBatDau.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày Bắt Đầu Đợt Giảm Giá");
            return false;
        }
        if (txtNgayKetThuc.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày Kết Thúc Đợt Giảm Giá");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblQuytacgiamgia = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtMaqtgg = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMadgg = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTenqtgg = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtsolanapdung = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtgiatrimin = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtgiatrimax = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtmotaqtgg = new javax.swing.JTextField();
        btadd = new javax.swing.JButton();
        btundate = new javax.swing.JButton();
        btremove = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txttkqtgg = new javax.swing.JTextField();
        bttkqtgg = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txttkdgg = new javax.swing.JTextField();
        bttkdgg = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDotGiamGia1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDotGiamGia = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaDGG = new javax.swing.JTextField();
        txtTenDGG = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLGG = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGiaTriKhuyenMai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayBatDau = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNgayKetThuc = new javax.swing.JTextField();
        btThem = new javax.swing.JButton();
        btSua = new javax.swing.JButton();
        btXoa = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTK = new javax.swing.JTextField();
        btTim = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtTKLGG = new javax.swing.JTextField();
        btTKLGG = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblloaigiamgia = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoaiGiamGia = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rdAdung = new javax.swing.JRadioButton();
        rdKapdung = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        txtMaLGG = new javax.swing.JTextField();
        txtTenLGG = new javax.swing.JTextField();
        txtGiaTriGG = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        btaddLGG = new javax.swing.JButton();
        btsuaLGG = new javax.swing.JButton();
        btxoaLGG = new javax.swing.JButton();
        brclearLGG = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtTKlgg = new javax.swing.JTextField();
        btTKlgg = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblQuytacgiamgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Ma QTGG", "Ten QTGG", "So Lan Áp Dụng", "Mã DGG", "Giá Trị Min", "Giá Trị Max", "Mô Tả"
            }
        ));
        tblQuytacgiamgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuytacgiamgiaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblQuytacgiamgia);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Quy Tắc Giảm Giá");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin "));

        jLabel19.setText("Mã QTGG:");

        jLabel20.setText("Mã DGG:");

        jLabel21.setText("Tên QTGG:");

        jLabel22.setText("Số Lần Áp Dụng:");

        jLabel23.setText("Giá Trị Min:");

        jLabel24.setText("Giá Trị Max:");

        jLabel25.setText("Mô Tả:");

        btadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/add.png"))); // NOI18N
        btadd.setText("Thêm");
        btadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaddActionPerformed(evt);
            }
        });

        btundate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/update.png"))); // NOI18N
        btundate.setText("Sửa");
        btundate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btundateActionPerformed(evt);
            }
        });

        btremove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/delete.png"))); // NOI18N
        btremove.setText("Xóa");
        btremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btremoveActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/cleaning.png"))); // NOI18N
        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtsolanapdung))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(txtMaqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMadgg, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenqtgg)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtgiatrimin))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtgiatrimax)
                                    .addComponent(txtmotaqtgg))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btadd, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btundate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btremove)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtMaqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtMadgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtTenqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(txtsolanapdung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtgiatrimin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtgiatrimax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(txtmotaqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btadd)
                    .addComponent(btundate)
                    .addComponent(btremove)
                    .addComponent(jButton5))
                .addGap(38, 38, 38))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Quy Tắc Giảm Giá"));

        jLabel26.setText("Nhập Thông Tin:");

        bttkqtgg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        bttkqtgg.setText("OK");
        bttkqtgg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttkqtggActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttkqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bttkqtgg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txttkqtgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bttkqtgg))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Đợt Giảm Giá"));

        jLabel27.setText("Nhập Mã DGG:");

        bttkdgg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        bttkdgg.setText("OK");

        tblDotGiamGia1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã DGG", "Tên DGG", "Mã LGG", "Mã Nhân Viên", "Phần Trăm KM", "Ngày Bắt Đầu", "Ngày Kết Thúc"
            }
        ));
        tblDotGiamGia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDotGiamGia1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDotGiamGia1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(txttkdgg, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bttkdgg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txttkdgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bttkdgg, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(392, 392, 392)
                        .addComponent(jLabel18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quy tắc Giảm Giá", jPanel3);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setText("Thông Tin Đợt Giảm Giá");

        tblDotGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã DGG", "Tên DGG", "Mã LGG", "Mã Nhân Viên", "Phần Trăm KM", "Ngày Bắt Đầu", "Ngày Kết Thúc"
            }
        ));
        tblDotGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDotGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDotGiamGia);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin"));

        jLabel2.setText("Mã DGG: ");

        jLabel3.setText("Tên DGG:");

        txtTenDGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDGGActionPerformed(evt);
            }
        });

        jLabel4.setText("Mã LGG:");

        jLabel5.setText("Mã Nhân Viên: ");

        jLabel6.setText("Phần Trăm KM:");

        jLabel7.setText("Ngày Bắt Đầu:");

        jLabel8.setText("Ngày Kết Thúc: ");

        btThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/add.png"))); // NOI18N
        btThem.setText("Tạo");
        btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemActionPerformed(evt);
            }
        });

        btSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/update.png"))); // NOI18N
        btSua.setText("Sửa");
        btSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaActionPerformed(evt);
            }
        });

        btXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/delete.png"))); // NOI18N
        btXoa.setText("Xóa");
        btXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/cleaning.png"))); // NOI18N
        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaTriKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtMaDGG, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenDGG, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btThem)
                        .addGap(13, 13, 13)
                        .addComponent(btSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btXoa)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(94, 94, 94))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaDGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtLGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenDGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGiaTriKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThem)
                    .addComponent(btXoa)
                    .addComponent(btSua)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm Đợt Giảm Giá", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel9.setText("Nhập Thông Tin:");

        btTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        btTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btTim)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btTim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Loại Giảm Giá"));

        jLabel17.setText("Nhập Thông Tin:");

        btTKLGG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        btTKLGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTKLGGActionPerformed(evt);
            }
        });

        tblloaigiamgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã LGG", "Tên LGG", "Giá Trị GG", "Trạng Thái", "Mô Tả"
            }
        ));
        jScrollPane3.setViewportView(tblloaigiamgia);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel17)
                .addGap(26, 26, 26)
                .addComponent(txtTKLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btTKLGG)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTKLGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btTKLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(348, 348, 348)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đợt Giảm Giá", jPanel1);

        tblLoaiGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Loại Giảm Giá", "Tên Loại Giảm Giá", "Giá Trị Giảm Giá", "Trạng Thái", "Mô Tả"
            }
        ));
        tblLoaiGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoaiGiamGia);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Thông Tin Loại Giảm Giá");

        jLabel10.setText("Mã Loại Giảm Giá: ");

        jLabel12.setText("Tên Loại Giảm Giá:");

        jLabel13.setText("Giá Trị Giảm Giá:");

        jLabel14.setText("Trạng Thái:");

        buttonGroup1.add(rdAdung);
        rdAdung.setText("Áp dụng");

        buttonGroup1.add(rdKapdung);
        rdKapdung.setText("Không còn áp dụng");

        jLabel15.setText("Mô Tả:");

        btaddLGG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/add.png"))); // NOI18N
        btaddLGG.setText("Tạo");
        btaddLGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaddLGGActionPerformed(evt);
            }
        });

        btsuaLGG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/update.png"))); // NOI18N
        btsuaLGG.setText("Sửa");
        btsuaLGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsuaLGGActionPerformed(evt);
            }
        });

        btxoaLGG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/delete.png"))); // NOI18N
        btxoaLGG.setText("Xóa");
        btxoaLGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btxoaLGGActionPerformed(evt);
            }
        });

        brclearLGG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/cleaning.png"))); // NOI18N
        brclearLGG.setText("Clear");
        brclearLGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brclearLGGActionPerformed(evt);
            }
        });

        jLabel16.setText("Tìm Kiếm:");

        btTKlgg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/search.png"))); // NOI18N
        btTKlgg.setText("OK");
        btTKlgg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTKlggActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thoat.png"))); // NOI18N
        jButton8.setText("Exit");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btaddLGG))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdAdung)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdKapdung))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(btsuaLGG)
                                        .addGap(28, 28, 28)
                                        .addComponent(btxoaLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(brclearLGG))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(88, 88, 88)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMaLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenLGG, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaTriGG, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtMoTa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 407, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTKlgg, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jButton8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btTKlgg)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(480, 480, 480)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaLGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtTKlgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btTKlgg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtTenLGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtGiaTriGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rdAdung)
                    .addComponent(rdKapdung))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brclearLGG)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btaddLGG)
                        .addComponent(btsuaLGG)
                        .addComponent(btxoaLGG)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Loại Giảm Giá", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btTKLGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTKLGGActionPerformed
        String i = txtTKLGG.getText();
        List<LoaiGiamGia> listtklgg = lggService.search(i);
        showdatatklgg(listtklgg);
    }//GEN-LAST:event_btTKLGGActionPerformed

    private void btTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTimActionPerformed
        String i = txtTK.getText();
        List<DotGiamGia> listtk = new ArrayList<>();
        listtk = giamGiaService.search(i);
        showdata(listtk);
    }//GEN-LAST:event_btTimActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaActionPerformed
        int index = tblDotGiamGia.getSelectedRow();
        giamGiaService.remove(listdgg.get(index).getMaDGG());
        showdata(giamGiaService.getAll());
    }//GEN-LAST:event_btXoaActionPerformed

    private void btSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaActionPerformed
        if (valiDatesua()) {
            int index = tblDotGiamGia.getSelectedRow();
            giamGiaService.updata(getFromData(), listdgg.get(index).getMaDGG());
            showdata(giamGiaService.getAll());
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Sửa không thành công");
        }
    }//GEN-LAST:event_btSuaActionPerformed

    private void btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemActionPerformed
        if (vaLiDate()) {
            listdgg.add(getFromData());
            showdata(listdgg);
            JOptionPane.showMessageDialog(this, "Tạo thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Tạo không thành công");

        }

    }//GEN-LAST:event_btThemActionPerformed

    private void txtTenDGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDGGActionPerformed

    private void tblDotGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDotGiamGiaMouseClicked
        int index = tblDotGiamGia.getSelectedRow();
        if (index >= 0 && index < tblDotGiamGia.getRowCount()) {
            String ma = String.valueOf(tblDotGiamGia.getValueAt(index, 0));
            List<DotGiamGia> listsss = giamGiaService.search(ma);
            if (!listsss.isEmpty()) {
                detail(listsss.get(0));
            }
        }
    }//GEN-LAST:event_tblDotGiamGiaMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        TrangChuChinhView tcc = new TrangChuChinhView();
        tcc.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btTKlggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTKlggActionPerformed
        String i = txtTKlgg.getText();
        List<LoaiGiamGia> listtklgg = new ArrayList<>();
        listtklgg = lggService.search(i);
        showdatalgg(listtklgg);
    }//GEN-LAST:event_btTKlggActionPerformed

    private void btxoaLGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btxoaLGGActionPerformed
        int index = tblloaigiamgia.getSelectedRow();
        lggService.removelgg(listlgg.get(index).getMaLGG());
        showdatalgg(lggService.getAlllgg());
    }//GEN-LAST:event_btxoaLGGActionPerformed

    private void btsuaLGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsuaLGGActionPerformed
        int index = tblLoaiGiamGia.getSelectedRow();
        lggService.updata(getFromDatalgg(), listlgg.get(index).getMaLGG());
        showdatalgg(lggService.getAlllgg());
    }//GEN-LAST:event_btsuaLGGActionPerformed

    private void btaddLGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaddLGGActionPerformed
        listlgg.add(getFromDatalgg());
        showdatalgg(listlgg);
    }//GEN-LAST:event_btaddLGGActionPerformed

    private void tblLoaiGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiGiamGiaMouseClicked
        int index = tblLoaiGiamGia.getSelectedRow();
        if (index >= 0 && index < tblLoaiGiamGia.getRowCount()) {
            String ma = String.valueOf(tblLoaiGiamGia.getValueAt(index, 0));
            List<LoaiGiamGia> listlgg = lggService.search(ma);
            if (!listlgg.isEmpty()) {
                detaillgg(listlgg.get(0));
            }
        }
    }//GEN-LAST:event_tblLoaiGiamGiaMouseClicked

    private void brclearLGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brclearLGGActionPerformed
        clearlgg();
    }//GEN-LAST:event_brclearLGGActionPerformed

    private void tblDotGiamGia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDotGiamGia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDotGiamGia1MouseClicked

    private void btaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaddActionPerformed
        listqtgg.add(getfromqtgg());
        fillqtgg(listqtgg);
    }//GEN-LAST:event_btaddActionPerformed

    private void tblQuytacgiamgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuytacgiamgiaMouseClicked
        int index = tblQuytacgiamgia.getSelectedRow();
        if (index >= 0 && index < tblQuytacgiamgia.getRowCount()) {
            String ma = String.valueOf(tblQuytacgiamgia.getValueAt(index, 0));
            List<QuyTacGiamGia> listqtgg = qtggService.search(ma);
            if (!listqtgg.isEmpty()) {
                detailqtgg(listqtgg.get(0));
            }
        }
    }//GEN-LAST:event_tblQuytacgiamgiaMouseClicked

    private void bttkqtggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttkqtggActionPerformed
        String ma = txttkqtgg.getText();
        List<QuyTacGiamGia> listtqtgg = new ArrayList<>();
        listtqtgg = qtggService.search(ma);
        fillqtgg(listqtgg);
    }//GEN-LAST:event_bttkqtggActionPerformed

    private void btremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btremoveActionPerformed
        int index = tblQuytacgiamgia.getSelectedRow();
        qtggService.removeqtgg(listqtgg.get(index).getMaQTGG());
        fillqtgg(qtggService.getAllqtgg());
    }//GEN-LAST:event_btremoveActionPerformed

    private void btundateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btundateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btundateActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clearqtgg();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void detail(DotGiamGia dgg) {

        txtMaDGG.setText(dgg.getMaDGG());
        txtTenDGG.setText(dgg.getTenDGG());
        txtLGG.setText(dgg.getMaLGG());
        txtMaNV.setText(dgg.getMaNV());
        txtGiaTriKhuyenMai.setText(String.valueOf(dgg.getPhanTramGG()));
        txtNgayBatDau.setText(String.valueOf(dgg.getNgayBatDau()));
        txtNgayKetThuc.setText(String.valueOf(dgg.getNgayKetThuc()));
    }

    private void detailqtgg(QuyTacGiamGia qtgg) {
        txtMaqtgg.setText(qtgg.getMaQTGG());
        txtTenqtgg.setText(qtgg.getTenQtgg());
        txtsolanapdung.setText(String.valueOf(qtgg.getSolanAD()));
        txtMadgg.setText(qtgg.getMaDGG());
        txtgiatrimin.setText(String.valueOf(qtgg.getGiatrimin()));
        txtgiatrimax.setText(String.valueOf(qtgg.getGiatriMax()));
        txtmotaqtgg.setText(qtgg.getMoTa());
    }

    private void detaillgg(LoaiGiamGia lgg) {
        txtMaLGG.setText(lgg.getMaLGG());
        txtTenLGG.setText(lgg.getTenLGG());
        txtGiaTriGG.setText(String.valueOf(lgg.getGiaTriGG()));
        txtMoTa.setText(lgg.getMoTa());
        Boolean tT = lgg.getTrangThai();
        if (tT == true) {
            rdAdung.setSelected(true);
        } else {
            rdKapdung.setSelected(true);
        }
    }

//    private DotGiamGia getfromdata(){
//        DotGiamGia gg = new DotGiamGia();
//        gg.setMaDGG(txtMaDGG.getText());
//        gg.setTenDGG(txtTenDGG.getText());
//        gg.setMaLGG(txtLGG.getText());
//        gg.setMaNV(txtMaNV.getText());
//        gg.setPhanTramGG(txtGiaTriKhuyenMai.getText());
//        
//        
//        try {
//            gg.setNgayBatDau(date.parse(txtNgayBatDau.getText()));
//        } catch (ParseException ex) {
//            Logger.getLogger(ViewDotGiamGia.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            gg.setNgayKetThuc(date.parse(txtNgayKetThuc.getText()));
//        } catch (ParseException ex) {
//            Logger.getLogger(ViewDotGiamGia.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return gg;
    // }
    private DotGiamGia getFromData() {
        DotGiamGia gg = new DotGiamGia();
        gg.setMaDGG(txtMaDGG.getText());
        gg.setTenDGG(txtTenDGG.getText());
        gg.setMaLGG(txtLGG.getText());
        gg.setMaNV(txtMaNV.getText());
        gg.setPhanTramGG(txtGiaTriKhuyenMai.getText());
        try {
            gg.setNgayBatDau(new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayBatDau.getText()));
        } catch (ParseException ex) {
            // Handle the exception appropriately, e.g., show an error message
            Logger.getLogger(DotGiamGiaView.class.getName()).log(Level.SEVERE, "Invalid start date format", ex);
        }
        try {
            gg.setNgayKetThuc(new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayKetThuc.getText()));
        } catch (ParseException ex) {
            // Handle the exception appropriately, e.g., show an error message
            Logger.getLogger(DotGiamGiaView.class.getName()).log(Level.SEVERE, "Invalid end date format", ex);
        }

        return gg;
    }

    private LoaiGiamGia getFromDatalgg() {
        LoaiGiamGia lgg1 = new LoaiGiamGia();
        lgg1.setMaLGG(txtMaLGG.getText());
        lgg1.setTenLGG(txtTenLGG.getText());
        lgg1.setGiaTriGG(Integer.valueOf(txtGiaTriGG.getText()));
        lgg1.setTrangThai(rdAdung.isSelected());
        lgg1.setMoTa(txtMoTa.getText());
        return lgg1;
    }

    private QuyTacGiamGia getfromqtgg() {
        QuyTacGiamGia qtgggf = new QuyTacGiamGia();
        qtgggf.setMaQTGG(txtMaqtgg.getText());
        qtgggf.setTenQtgg(txtTenqtgg.getText());
        qtgggf.setSolanAD(Integer.valueOf(txtsolanapdung.getText()));
        qtgggf.setMaDGG(txtMadgg.getText());
        qtgggf.setGiatrimin(Integer.valueOf(txtgiatrimin.getText()));
        qtgggf.setGiatriMax(Integer.valueOf(txtgiatrimax.getText()));
        qtgggf.setMoTa(txtMoTa.getText());
        return qtgggf;
    }

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
            java.util.logging.Logger.getLogger(DotGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DotGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DotGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DotGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DotGiamGiaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brclearLGG;
    private javax.swing.JButton btSua;
    private javax.swing.JButton btTKLGG;
    private javax.swing.JButton btTKlgg;
    private javax.swing.JButton btThem;
    private javax.swing.JButton btTim;
    private javax.swing.JButton btXoa;
    private javax.swing.JButton btadd;
    private javax.swing.JButton btaddLGG;
    private javax.swing.JButton btremove;
    private javax.swing.JButton btsuaLGG;
    private javax.swing.JButton bttkdgg;
    private javax.swing.JButton bttkqtgg;
    private javax.swing.JButton btundate;
    private javax.swing.JButton btxoaLGG;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdAdung;
    private javax.swing.JRadioButton rdKapdung;
    private javax.swing.JTable tblDotGiamGia;
    private javax.swing.JTable tblDotGiamGia1;
    private javax.swing.JTable tblLoaiGiamGia;
    private javax.swing.JTable tblQuytacgiamgia;
    private javax.swing.JTable tblloaigiamgia;
    private javax.swing.JTextField txtGiaTriGG;
    private javax.swing.JTextField txtGiaTriKhuyenMai;
    private javax.swing.JTextField txtLGG;
    private javax.swing.JTextField txtMaDGG;
    private javax.swing.JTextField txtMaLGG;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMadgg;
    private javax.swing.JTextField txtMaqtgg;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    private javax.swing.JTextField txtTK;
    private javax.swing.JTextField txtTKLGG;
    private javax.swing.JTextField txtTKlgg;
    private javax.swing.JTextField txtTenDGG;
    private javax.swing.JTextField txtTenLGG;
    private javax.swing.JTextField txtTenqtgg;
    private javax.swing.JTextField txtgiatrimax;
    private javax.swing.JTextField txtgiatrimin;
    private javax.swing.JTextField txtmotaqtgg;
    private javax.swing.JTextField txtsolanapdung;
    private javax.swing.JTextField txttkdgg;
    private javax.swing.JTextField txttkqtgg;
    // End of variables declaration//GEN-END:variables
}
