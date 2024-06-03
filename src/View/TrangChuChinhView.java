package View;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public final class TrangChuChinhView extends javax.swing.JFrame {

    public TrangChuChinhView() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        URL imageUrl = getClass().getResource("/icon/dog.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image image = icon.getImage();

        setIconImage(image);
        setLocationRelativeTo(null);
        DateTimeDisplay();

    }

    public void hideTrangChuChinhView() {
        setVisible(false);
    }

    // Phương thức để hiển thị lại cửa sổ TrangChuChinhView
    public void showTrangChuChinhView() {
        setVisible(true);
    }

    public void DateTimeDisplay() {
        setTitle("Shop chó Alaska");
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss | dd/MM/yyyy");
            String dateTime = dateFormat.format(now);

            jlbThoiGian.setText(dateTime); // Hiển thị thời gian và ngày lên jlbThoiGian
        });
        timer.start();
    }

    public void setTenDangNhap(String tenDangNhap) {
        jlbTaiKhoan.setText(tenDangNhap); // Cập nhật tên đăng nhập lên giao diện
    }

    public void opendangnhap() {
        this.setVisible(false); // Đóng giao diện TrangChuChinhView
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true); // Mở giao diện DangNhapView
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jblHome = new javax.swing.JLabel();
        jlbSanPham = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        jlbPhieuGiamGia = new javax.swing.JLabel();
        jblDotGiamGia = new javax.swing.JLabel();
        jblNhanVien = new javax.swing.JLabel();
        jblKhachHang = new javax.swing.JLabel();
        lblDangXuat = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        lblPhieuGiaoHang = new javax.swing.JLabel();
        jlbTaiKhoan = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jlbThoiGian = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1344, 756));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(102, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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

        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoadon.png"))); // NOI18N
        lblHoaDon.setText("Hóa đơn");
        lblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseClicked(evt);
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

        lblDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thoat.png"))); // NOI18N
        lblDangXuat.setText("Đăng xuất");
        lblDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo (1).jpg"))); // NOI18N

        lblPhieuGiaoHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Phieugiaohang.png"))); // NOI18N
        lblPhieuGiaoHang.setText("Phiếu giao hàng");
        lblPhieuGiaoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPhieuGiaoHangMouseClicked(evt);
            }
        });

        jlbTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taikhoan.png"))); // NOI18N
        jlbTaiKhoan.setText("Tài khoản");
        jlbTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbTaiKhoanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jblHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPhieuGiamGia)
                    .addComponent(jblDotGiamGia)
                    .addComponent(jblNhanVien)
                    .addComponent(jblKhachHang)
                    .addComponent(lblPhieuGiaoHang))
                .addGap(34, 34, 34))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblDangXuat)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jblHome)
                .addGap(33, 33, 33)
                .addComponent(jlbSanPham)
                .addGap(33, 33, 33)
                .addComponent(lblHoaDon)
                .addGap(33, 33, 33)
                .addComponent(lblPhieuGiaoHang)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(lblDangXuat)
                .addGap(17, 17, 17))
        );

        jLabel11.setFont(new java.awt.Font("Zilla Slab Medium", 1, 48)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("SHOP BÁN CHÓ ALASKA");

        jLabel13.setText("Địa chỉ : Đường Lê Đức Thọ , Quận Nam Từ Liêm , Hà Nội");

        jLabel14.setText("Hotiline : 01234567890");

        jLabel15.setText("Email : Nhom5Alaska@gmail.com");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/banner.jpg"))); // NOI18N

        jlbThoiGian.setText("HH:mm:ss | dd/MM/yyyy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(82, 82, 82))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jlbThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jlbThoiGian)))
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(87, 87, 87)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPhieuGiamGiaMouseClicked
        PhieuGiamGiaView pggv = new PhieuGiamGiaView();
        pggv.setVisible(true);
    }//GEN-LAST:event_jlbPhieuGiamGiaMouseClicked

    private void jlbTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbTaiKhoanMouseClicked
        ThongTinTaiKhoanView tttv = new ThongTinTaiKhoanView();
        tttv.setVisible(true);
    }//GEN-LAST:event_jlbTaiKhoanMouseClicked

    private void jlbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSanPhamMouseClicked
        Chinh c = new Chinh();
        c.setVisible(true);
    }//GEN-LAST:event_jlbSanPhamMouseClicked

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

    private void jblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblHomeMouseClicked
        // TODO add your handling code here:
        TrangChuChinhView tccv = new TrangChuChinhView();
        tccv.setVisible(true);
    }//GEN-LAST:event_jblHomeMouseClicked

    private void lblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseClicked
        HoaDonView hdv = new HoaDonView();
        hdv.setVisible(true);
    }//GEN-LAST:event_lblHoaDonMouseClicked

    private void lblPhieuGiaoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPhieuGiaoHangMouseClicked
        // TODO add your handling code here:
        PhieuGiaoHangView pghv = new PhieuGiaoHangView();
        pghv.setVisible(true);
    }//GEN-LAST:event_lblPhieuGiaoHangMouseClicked

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        // TODO add your handling code here:
        DangNhapView dnv = new DangNhapView();
        dnv.setVisible(true);
    }//GEN-LAST:event_lblDangXuatMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new DangNhapView().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jblDotGiamGia;
    private javax.swing.JLabel jblHome;
    private javax.swing.JLabel jblKhachHang;
    private javax.swing.JLabel jblNhanVien;
    private javax.swing.JLabel jlbPhieuGiamGia;
    private javax.swing.JLabel jlbSanPham;
    private javax.swing.JLabel jlbTaiKhoan;
    private javax.swing.JLabel jlbThoiGian;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblPhieuGiaoHang;
    // End of variables declaration//GEN-END:variables
}
