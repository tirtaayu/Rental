/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rental.tampil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import rental.koneksi.koneksi;

/**
 *
 * @author greenTech
 */
public class Bayar extends javax.swing.JInternalFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    private boolean kondisiCari= false;
    private double total;
    
    
    int[] max = {31,28,31,30,31,30,31,31,30,31,30,31};
    
     protected void aktif(){
        cbD.setEnabled(true);
        cbM.setEnabled(true);
        cbY.setEnabled(true);
        txtId.setEnabled(true);
        txtNamaPetugas.setEnabled(true);
        txtNoSewa.setEnabled(true);
        txtTgl.setEnabled(true);
        txtKTP.setEnabled(true);
        txtNamaDriver.setEnabled(true);
        txtMerk.setEnabled(true);
        txtNopol.setEnabled(true);
        txtBayar.setEnabled(true);
        txtLama.setEnabled(true);
        txtTotal.setEnabled(true);
        txtBayar.setEnabled(true);
        txtNo.requestFocus();
    }
     
      protected void kosong(){
        txtNo.setText("");
        sekarang();
        txtId.setText("");
        txtNamaPetugas.setText("");
        txtNo.setText("");
        txtTgl.setText("");
        txtNamaDriver.setText("");
        txtKTP.setText("");
        txtMerk.setText("");
        txtNopol.setText("");
        txtBayar.setText("");
        txtLama.setText("");
        txtTotal.setText("");
    }
      
      protected void datatable(){
        Object[] Baris ={"No. Pembayaran", "Tgl Pembayaran", "ID Petugas", "Nama Petugas", "No. Sewa", "Tgl Sewa", "No. KTP", "Nama Pelanggan", "Merk", "No. Polisi", "Harga Sewa", "Lama Sewa","Total"};
        tabmode = new DefaultTableModel(null, Baris);
        tblBayar.setModel(tabmode);
        String sql = "select * from bayar";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String no = hasil.getString("no");
                String tgl = hasil.getString("tgl");
                String id_petugas = hasil.getString("id_petugas");
                String nama_petugas = hasil.getString("nama_petugas");
                String no_sewa = hasil.getString("no_sewa");
                String tgl_sewa = hasil.getString("tgl_sewa");
                String ktp = hasil.getString("ktp");
                String nama_driver = hasil.getString("nama_driver");
                String merk = hasil.getString("merk");
                String nopol = hasil.getString("nopol");
                String bayar = hasil.getString("bayar");
                String uang = hasil.getString("lama");
                String keluar = hasil.getString("total");
                String[] data={no,tgl,id_petugas,nama_petugas,no_sewa,tgl_sewa,ktp,nama_driver,merk,nopol,bayar,uang,keluar};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
      
      private String gettgl(){
            int d = Integer.parseInt (cbD.getSelectedItem().toString());
            int m = cbM.getSelectedIndex();
            int y = Integer.parseInt (cbY.getSelectedItem().toString());
            String tgl = +d+"/"+m+"/"+y;
            return tgl;
        }
        
        //string untuk menampilkan tanggal maksimum pada tiap bulan
        private void validasi(){
            int d = Integer.parseInt(cbD.getSelectedItem().toString());
            int m = cbM.getSelectedIndex();
            int y = Integer.parseInt(cbY.getSelectedItem().toString());
            if (y%4 ==0){
                max[1]=29;
            } else {
                max[1]=28;
            }
            if(d>max[m]){
                cbD.setSelectedItem(max[m]);
            }
        }
        
        //string untuk menampilkan tanggal saat ini
        private void sekarang(){
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int d = cal.get(Calendar.DAY_OF_MONTH);
            int m = cal.get(Calendar.MONTH);
            int y = cal.get(Calendar.YEAR);
            cbD.setSelectedItem(d);
            cbM.setSelectedIndex(m);
            cbY.setSelectedItem(y);
        }
        
        //string untuk menampilkan tahun saat ini sampai 60 tahun yang lalu
        private void isicmbthn(){
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int awal = cal.get(Calendar.YEAR);
            Integer[] isi = new Integer[60];
            for(int i = 0; i<60; i++){
                isi[i]= awal - i;
            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel (isi);
            cbY.setModel(dcm);
        }
        
        //string untuk menampilkan tanggal mulai 1 sampai 31
        private void isicmbtgl(){
            int mulai = 31;
            Integer[] isi2 = new Integer[31];
            for(int a = 0; a<31; a++){
                isi2[a] = mulai - a;
            }
            DefaultComboBoxModel dcm2 = new DefaultComboBoxModel (isi2);
            cbD.setModel (dcm2);
        }
        
    protected void dataSewa(String dtCari){
        Object[] Baris ={"No. Sewa", "Tgl Sewa", "ID Pelanggan", "No. KTP","Nama Pelanggan","Telepon", "KD Mobil", "Merk", "Tipe", "Tahun", "No. Polisi", "Warna", "Harga Sewa"};
        tabmode = new DefaultTableModel(null, Baris);
        tblSewa.setModel(tabmode);
        String kondisi = "";
        if (!dtCari.isEmpty()){
            kondisiCari = true;
            kondisi = " where merk='"+dtCari+"'";
        }
        String sql = "select * from sewa" +kondisi;
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String no = hasil.getString("no");
                String tgl = hasil.getString("tgl");
                String id_driver = hasil.getString("id_driver");
                String ktp = hasil.getString("ktp");
                String nama_driver = hasil.getString("nama_driver");
                String tlpn = hasil.getString("tlpn");
                String kd_mobil = hasil.getString("kd_mobil");
                String merk = hasil.getString("merk");
                String tipe = hasil.getString("tipe");
                String thn = hasil.getString("thn");
                String nopol = hasil.getString("nopol");
                String warna = hasil.getString("warna");
                String bayar = hasil.getString("bayar");
                String[] data={no,tgl,id_driver,ktp,nama_driver,tlpn,kd_mobil,merk,tipe,thn,nopol,warna,bayar};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    protected void dataPetugas(String dtCari){
        Object[] Baris ={"ID Petugas","Nama","Telepon","Alamat"};
        tabmode = new DefaultTableModel(null, Baris);
        tblPetugas.setModel(tabmode);
        String kondisi = "";
        if (!dtCari.isEmpty()){
            kondisiCari = true;
            kondisi = " where nama='"+dtCari+"'";
        }
        String sql = "select * from petugas" +kondisi;
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String nip = hasil.getString("id");
                String nama = hasil.getString("nama");
                String status = hasil.getString("tlpn");
                String almt = hasil.getString("almt");
                String[] data={nip,nama,status,almt};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    /**
     * Creates new form Bayar
     */
    public Bayar() {
        initComponents();
        isicmbthn();
        isicmbtgl();
        sekarang();
        datatable();
        dataPetugas("");
        dataSewa("");
        D_Petugas.setLocationRelativeTo(this);
        D_Sewa.setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        D_Petugas = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPetugas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        D_Sewa = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSewa = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCari1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTgl = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtLama = new javax.swing.JTextField();
        txtNoSewa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbD = new javax.swing.JComboBox();
        cbM = new javax.swing.JComboBox();
        cbY = new javax.swing.JComboBox();
        txtKTP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNamaDriver = new javax.swing.JTextField();
        txtNamaPetugas = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMerk = new javax.swing.JTextField();
        txtNopol = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txtcetak = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBayar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        cmdSimpan = new javax.swing.JButton();
        cmdUbah = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        D_Petugas.setTitle("Data Petugas");
        D_Petugas.setMinimumSize(new java.awt.Dimension(672, 479));

        jPanel2.setBackground(new java.awt.Color(37, 23, 110));

        tblPetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPetugasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblPetugasMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tblPetugas);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cari Berdasarkan Nama Petugas :");

        jButton3.setText("Cari");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout D_PetugasLayout = new javax.swing.GroupLayout(D_Petugas.getContentPane());
        D_Petugas.getContentPane().setLayout(D_PetugasLayout);
        D_PetugasLayout.setHorizontalGroup(
            D_PetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        D_PetugasLayout.setVerticalGroup(
            D_PetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        D_Sewa.setTitle("Proses Sewa");
        D_Sewa.setMinimumSize(new java.awt.Dimension(672, 479));

        jPanel5.setBackground(new java.awt.Color(37, 23, 110));

        tblSewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSewaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblSewaMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tblSewa);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cari Berdasarkan Merk :");

        jButton5.setText("Cari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout D_SewaLayout = new javax.swing.GroupLayout(D_Sewa.getContentPane());
        D_Sewa.getContentPane().setLayout(D_SewaLayout);
        D_SewaLayout.setHorizontalGroup(
            D_SewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        D_SewaLayout.setVerticalGroup(
            D_SewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setTitle("CV TRISTIADI");

        jPanel6.setBackground(new java.awt.Color(51, 102, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proses Bayar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(204, 204, 204));

        jPanel7.setBackground(new java.awt.Color(51, 102, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setForeground(new java.awt.Color(204, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No. Pembayaran");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 15, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID Petugas");
        jPanel7.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 94, -1, -1));
        jPanel7.add(txtNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 15, 192, -1));
        jPanel7.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 94, 135, -1));

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 93, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tanggal Sewa");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 15, -1, -1));
        jPanel7.add(txtTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 15, 214, -1));

        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 179, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("No. KTP");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 55, -1, -1));
        jPanel7.add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(965, 15, 154, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Harga Sewa");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(848, 15, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Lama Sewa");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(848, 55, -1, -1));

        txtLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamaActionPerformed(evt);
            }
        });
        jPanel7.add(txtLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(966, 55, 153, -1));
        jPanel7.add(txtNoSewa, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 180, 134, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No. Sewa");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 179, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Total");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(848, 112, 77, -1));

        txtTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTotalMouseExited(evt);
            }
        });
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
        });
        jPanel7.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(966, 112, 153, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tanggal Pembayaran");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 55, -1, -1));

        cbD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cbD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDItemStateChanged(evt);
            }
        });
        jPanel7.add(cbD, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 55, -1, -1));

        cbM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februa", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMItemStateChanged(evt);
            }
        });
        jPanel7.add(cbM, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 55, -1, -1));

        cbY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbYItemStateChanged(evt);
            }
        });
        jPanel7.add(cbY, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 55, 66, -1));
        jPanel7.add(txtKTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 55, 214, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Pelanggan");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 94, -1, -1));

        txtNamaDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaDriverActionPerformed(evt);
            }
        });
        jPanel7.add(txtNamaDriver, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 94, 214, -1));
        jPanel7.add(txtNamaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 138, 192, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama Petugas");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 138, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Merk");
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 138, -1, -1));
        jPanel7.add(txtMerk, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 138, 184, -1));
        jPanel7.add(txtNopol, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 180, 214, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("No. Polisi");
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 180, -1, -1));

        jButton6.setText("Cetak");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 160, -1, -1));
        jPanel7.add(txtcetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 160, 180, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("*Tekan Enter");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(966, 86, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("*Ketik No Pembayaran");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, -1, -1));

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

        jScrollPane6.setForeground(new java.awt.Color(153, 0, 0));

        tblBayar.setForeground(new java.awt.Color(153, 0, 0));
        tblBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBayarMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblBayar);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(51, 102, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setForeground(new java.awt.Color(204, 204, 204));

        cmdSimpan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmdSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save Close_16px.png"))); // NOI18N
        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdUbah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmdUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit_15px_1.png"))); // NOI18N
        cmdUbah.setText("Ubah");
        cmdUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUbahActionPerformed(evt);
            }
        });

        cmdHapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmdHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Erase_15px_1.png"))); // NOI18N
        cmdHapus.setText("Hapus");
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Exit_15px.png"))); // NOI18N
        jButton4.setText("Keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(cmdUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(cmdHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdUbah)
                    .addComponent(cmdHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1271, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        D_Petugas.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        D_Sewa.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDItemStateChanged
        // TODO add your handling code here:
        validasi();
    }//GEN-LAST:event_cbDItemStateChanged

    private void cbMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMItemStateChanged
        // TODO add your handling code here:
        validasi();
    }//GEN-LAST:event_cbMItemStateChanged

    private void cbYItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbYItemStateChanged
        // TODO add your handling code here:
        validasi();
    }//GEN-LAST:event_cbYItemStateChanged

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        String tgl = cbD.getSelectedItem().toString();
        String bln = cbM.getSelectedItem().toString();
        String thn = cbY.getSelectedItem().toString();

        String tgl_gabung = tgl+"/"+bln+"/"+thn;

        String sql = "insert into bayar values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNo.getText());
            stat.setString(2, tgl_gabung);
            stat.setString(3, txtId.getText());
            stat.setString(4, txtNamaPetugas.getText());
            stat.setString(5, txtNoSewa.getText());
            stat.setString(6, txtTgl.getText());
            stat.setString(7, txtKTP.getText());
            stat.setString(8, txtNamaDriver.getText());
            stat.setString(9, txtMerk.getText());
            stat.setString(10, txtNopol.getText());
            stat.setString(11, txtBayar.getText());
            stat.setString(12, txtLama.getText());
            stat.setString(13, txtTotal.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            kosong();
            txtNo.requestFocus();
            datatable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
        }
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUbahActionPerformed
        // TODO add your handling code here:
        String tgl = cbD.getSelectedItem().toString();
        String bln = cbM.getSelectedItem().toString();
        String thn = cbY.getSelectedItem().toString();

        String tgl_gabung = tgl+"/"+bln+"/"+thn;

        String sql = "update bayar set tgl=?, id_petugas=?, nama_petugas=?, no_sewa=?, tgl_sewa=?, ktp=?, nama_driver=?, merk=?, nopol=?, bayar=?, lama=?, total=? where no='"+txtNo.getText()+"'";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tgl_gabung);
            stat.setString(2, txtId.getText());
            stat.setString(3, txtNamaPetugas.getText());
            stat.setString(4, txtNoSewa.getText());
            stat.setString(5, txtTgl.getText());
            stat.setString(6, txtKTP.getText());
            stat.setString(7, txtNamaDriver.getText());
            stat.setString(8, txtMerk.getText());
            stat.setString(9, txtNopol.getText());
            stat.setString(10, txtBayar.getText());
            stat.setString(11, txtLama.getText());
            stat.setString(12, txtTotal.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            kosong();
            txtNo.requestFocus();
            datatable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
        }
    }//GEN-LAST:event_cmdUbahActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String sql = "delete from bayar where no='"+txtNo.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                txtNo.requestFocus();
                datatable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus "+e);
            }
        }
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBayarMouseClicked
        int bar = tblBayar.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        String h = tabmode.getValueAt(bar, 7).toString();
        String i = tabmode.getValueAt(bar, 8).toString();
        String j = tabmode.getValueAt(bar, 9).toString();
        String k = tabmode.getValueAt(bar, 10).toString();
        String l = tabmode.getValueAt(bar, 11).toString();
        String m = tabmode.getValueAt(bar, 12).toString();
        String n = tabmode.getValueAt(bar, 13).toString();

        txtNo.setText(a);
        txtId.setText(c);
        txtId.setText(d);
        txtNamaPetugas.setText(e);
        txtNoSewa.setText(f);
        txtTgl.setText(g);
        txtKTP.setText(h);
        txtNamaDriver.setText(i);
        txtMerk.setText(j);
        txtNopol.setText(k);
        txtBayar.setText(l);
        txtLama.setText(m);
        txtTotal.setText(n);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBayarMouseClicked

    private void txtNamaDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaDriverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaDriverActionPerformed

    private void tblPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPetugasMouseClicked
        // TODO add your handling code here:
        try { 
            int row =tblPetugas.getSelectedRow(); 
            String tabel_klik=(tblPetugas.getModel().getValueAt(row, 0).toString()); 
            java.sql.Statement stm = conn.createStatement(); 
            java.sql.ResultSet sql = stm.executeQuery("select * from petugas where id='"+tabel_klik+"'");
            if(sql.next()){ 
                String no = sql.getString("id");
                    txtId.setText(no); 
                String nama = sql.getString("nama");
                    txtNamaPetugas.setText(nama); 
                String tlpn = sql.getString("tlpn"); 
                
                String almt = sql.getString("almt"); 
                    
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang anda pilih.");
            
        }

        D_Petugas.setVisible(false);
    }//GEN-LAST:event_tblPetugasMouseClicked

    private void tblPetugasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPetugasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPetugasMouseEntered

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dataPetugas(txtCari.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSewaMouseClicked
        // TODO add your handling code here:
        try { 
            int row =tblSewa.getSelectedRow(); 
            String tabel_klik=(tblSewa.getModel().getValueAt(row, 0).toString()); 
            java.sql.Statement stm = conn.createStatement(); 
            java.sql.ResultSet sql = stm.executeQuery("select * from sewa where no='"+tabel_klik+"'");
            if(sql.next()){ 
                String no = sql.getString("no");
                    txtNoSewa.setText(no); 
                String nama = sql.getString("tgl");
                    txtTgl.setText(nama); 
                String id_driver = sql.getString("id_driver"); 
                    
                String almt = sql.getString("ktp"); 
                    txtKTP.setText(almt);
                String nama_driver = sql.getString("nama_driver"); 
                    txtNamaDriver.setText(nama_driver);
                String tlpn = sql.getString("tlpn"); 
                    
                String kd_mobil = sql.getString("kd_mobil"); 
                    
                String merk = sql.getString("merk"); 
                    txtMerk.setText(merk);
                String tipe = sql.getString("tipe"); 
                    
                String thn = sql.getString("thn"); 
                    
                String nopol = sql.getString("nopol"); 
                    txtNopol.setText(nopol);
                String warna = sql.getString("warna"); 
                    
                String bayar = sql.getString("bayar"); 
                    txtBayar.setText(bayar);
                String lama = sql.getString("lama"); 
                    txtLama.setText(lama);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang anda pilih.");
            
        }

        D_Sewa.setVisible(false);
    }//GEN-LAST:event_tblSewaMouseClicked

    private void tblSewaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSewaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSewaMouseEntered

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dataSewa(txtCari.getText());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtTotalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalMouseExited
        // TODO add your handling code here:
         
    }//GEN-LAST:event_txtTotalMouseExited

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10){
            txtTotal.requestFocus();}
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
        hitung_jual();
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamaActionPerformed
        // TODO add your handling code here:
        double xtot,xhrg,xjml;
        xhrg=Double.valueOf(txtBayar.getText());
        xjml=Double.valueOf(txtLama.getText());
        xtot=xjml*xhrg;
        String xsubtotal=Double.toString(xtot);
        txtTotal.setText(xsubtotal);
    }//GEN-LAST:event_txtLamaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         java.sql.Connection con = null;
        try {
            String jdbcDriver = "com.mysql.jdbc.Driver";
            Class.forName(jdbcDriver);

            String url = "jdbc:mysql://localhost:3306/rental";
            String user = "root";
            String pass = "";

            con = DriverManager.getConnection(url, user, pass);
            java.sql.Statement stm = con.createStatement();

            try {

                String report = ("C:\\Users\\User\\Documents\\rental\\rental\\src\\rental\\laporan\\transaksi.jrxml");
                 HashMap hash = new HashMap();
                //Mengambil parameter dari ireport
                hash.put("kode", txtcetak.getText());
                JasperReport JR = JasperCompileManager.compileReport(report);
                JasperPrint JP = JasperFillManager.fillReport(JR, hash, con);
                JasperViewer.viewReport(JP, false);
            } catch (Exception rptexcpt) {
                System.out.println("Report Can't view because : " + rptexcpt);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>s
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
                new Bayar().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog D_Petugas;
    private javax.swing.JDialog D_Sewa;
    private javax.swing.JComboBox cbD;
    private javax.swing.JComboBox cbM;
    private javax.swing.JComboBox cbY;
    private javax.swing.JButton cmdHapus;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdUbah;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblBayar;
    private javax.swing.JTable tblPetugas;
    private javax.swing.JTable tblSewa;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCari1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKTP;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtMerk;
    private javax.swing.JTextField txtNamaDriver;
    private javax.swing.JTextField txtNamaPetugas;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtNoSewa;
    private javax.swing.JTextField txtNopol;
    private javax.swing.JTextField txtTgl;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtcetak;
    // End of variables declaration//GEN-END:variables

  private void hitung_jual() {
        double xtot,xhrg;
        int xjml;
        xhrg=Double.parseDouble(txtLama.getText());
        xjml=Integer.parseInt(txtLama.getText());
        xtot=xhrg*xjml;
        String xtotal=Double.toString(xtot);
        txtTotal.setText(xtotal);
        total=total+xtot;
        txtTotal.setText(Double.toString(total));
          
    }
}
