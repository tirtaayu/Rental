/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rental.tampil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import rental.koneksi.koneksi;

/**
 *
 * @author greenTech
 */
public class Sewa extends javax.swing.JInternalFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    private boolean kondisiCari= false;
    
    
    int[] max = {31,28,31,30,31,30,31,31,30,31,30,31};
    
     protected void aktif(){
        cbD.setEnabled(true);
        cbM.setEnabled(true);
        cbY.setEnabled(true);
        txtId.setEnabled(true);
        txtKTP.setEnabled(true);
        txtNama.setEnabled(true);
        txtTlpn.setEnabled(true);
        txtKd.setEnabled(true);
        txtMerk.setEnabled(true);
        txtTipe.setEnabled(true);
        txtThn.setEnabled(true);
        txtNopol.setEnabled(true);
        txtWarna.setEnabled(true);
        txtBayar.setEnabled(true);
        txtLama.setEnabled(true);
        txtNo.requestFocus();
    }
     
      protected void kosong(){
        txtNo.setText("");
        sekarang();
        txtId.setText("");
        txtKTP.setText("");
        txtNama.setText("");
        txtTlpn.setText("");
        txtKd.setText("");
        txtMerk.setText("");
        txtTipe.setText("");
        txtThn.setText("");
        txtNopol.setText("");
        txtWarna.setText("");
        txtBayar.setText("");
        txtLama.setText("");
    }
      
      protected void datatable(){
        Object[] Baris ={"No. Sewa", "Tgl Sewa", "ID Driver", "No. KTP","Nama Pelanggan","Telepon", "KD Mobil", "Merk", "Tipe", "Tahun", "No. Polisi", "Warna", "Harga Sewa", "Lama Sewa"};
        tabmode = new DefaultTableModel(null, Baris);
        tblSewa.setModel(tabmode);
        String sql = "select * from sewa";
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
                String lama = hasil.getString("lama");
                String[] data={no,tgl,id_driver,ktp,nama_driver,tlpn,kd_mobil,merk,tipe,thn,nopol,warna,bayar,lama};
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
        
    protected void dataDriver(String dtCari){
        Object[] Baris ={"ID Driver", "No. KTP","Nama","Telepon", "Tanggal Daftar", "Alamat"};
        tabmode = new DefaultTableModel(null, Baris);
        tblDriver.setModel(tabmode);
        String kondisi = "";
        if (!dtCari.isEmpty()){
            kondisiCari = true;
            kondisi = " where nama='"+dtCari+"'";
        }
        String sql = "select * from driver" +kondisi;
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String nip = hasil.getString("id");
                String ktp = hasil.getString("ktp");
                String nama = hasil.getString("nama");
                String status = hasil.getString("tlpn");
                String tgl = hasil.getString("tgl");
                String almt = hasil.getString("almt");
                String[] data={nip,ktp,nama,status,tgl,almt};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    protected void dataMobil(String dtCari){
        Object[] Baris ={"KD Mobil", "Merk", "Tipe", "Tahhun", "No. Polisi", "Warna","Harga Sewa"};
        tabmode = new DefaultTableModel(null, Baris);
        tblMobil.setModel(tabmode);
        String kondisi = "";
        if (!dtCari.isEmpty()){
            kondisiCari = true;
            kondisi = " where merk='"+dtCari+"'";
        }
        String sql = "select * from mobil" +kondisi;
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String nip = hasil.getString("kd");
                String ktp = hasil.getString("merk");
                String nama = hasil.getString("tipe");
                String status = hasil.getString("thn");
                String tgl = hasil.getString("nopol");
                String almt = hasil.getString("warna");
                String bayar = hasil.getString("harga");
                String[] data={nip,ktp,nama,status,tgl,almt,bayar};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    /**
     * Creates new form Sewa
     */
    public Sewa() {
        initComponents();
        datatable();
        dataDriver("");
        dataMobil("");
        isicmbthn();
        isicmbtgl();
        sekarang();
        D_Driver.setLocationRelativeTo(this);
        D_Mobil.setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        D_Driver = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDriver = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        D_Mobil = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMobil = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCari1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtTlpn = new javax.swing.JTextField();
        txtKd = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMerk = new javax.swing.JTextField();
        txtTipe = new javax.swing.JTextField();
        txtThn = new javax.swing.JTextField();
        txtNopol = new javax.swing.JTextField();
        txtWarna = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbD = new javax.swing.JComboBox();
        cbM = new javax.swing.JComboBox();
        cbY = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtKTP = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtLama = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        cmdSimpan = new javax.swing.JButton();
        cmdUbah = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSewa = new javax.swing.JTable();

        D_Driver.setTitle("Data Pelanggan");
        D_Driver.setMinimumSize(new java.awt.Dimension(672, 479));

        jPanel2.setBackground(new java.awt.Color(37, 23, 110));

        tblDriver.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDriver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDriverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblDriverMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tblDriver);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cari Berdasarkan Nama Pelanggan :");

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

        javax.swing.GroupLayout D_DriverLayout = new javax.swing.GroupLayout(D_Driver.getContentPane());
        D_Driver.getContentPane().setLayout(D_DriverLayout);
        D_DriverLayout.setHorizontalGroup(
            D_DriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        D_DriverLayout.setVerticalGroup(
            D_DriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        D_Mobil.setTitle("Data Mobil");
        D_Mobil.setMinimumSize(new java.awt.Dimension(672, 479));

        jPanel5.setBackground(new java.awt.Color(37, 23, 110));

        tblMobil.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMobilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblMobilMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tblMobil);

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

        javax.swing.GroupLayout D_MobilLayout = new javax.swing.GroupLayout(D_Mobil.getContentPane());
        D_Mobil.getContentPane().setLayout(D_MobilLayout);
        D_MobilLayout.setHorizontalGroup(
            D_MobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        D_MobilLayout.setVerticalGroup(
            D_MobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(51, 102, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proses Sewa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(204, 204, 204));

        jPanel7.setBackground(new java.awt.Color(51, 102, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setForeground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No. Sewa");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tanggal Sewa");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID Pelanggan");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nama Pelanggan");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("No. KTP");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Telepon");

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("KD Mobil");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Merk");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tipe");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tahun");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("No. Polisi");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Warna");

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cbD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDItemStateChanged(evt);
            }
        });

        cbM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februa", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMItemStateChanged(evt);
            }
        });

        cbY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbYItemStateChanged(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Harga Sewa");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Lama Sewa");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(txtId)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(cbD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbY, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNama)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtWarna)
                            .addComponent(txtNopol)
                            .addComponent(txtThn)
                            .addComponent(txtTipe)
                            .addComponent(txtMerk)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(txtBayar)
                            .addComponent(txtTlpn, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtKTP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNo, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(77, 77, 77)
                        .addComponent(txtLama)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbD)
                        .addComponent(cbM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtKTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNama))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTlpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtThn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtWarna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

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
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(cmdSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(cmdUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(cmdHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmdSimpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(cmdUbah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane6.setForeground(new java.awt.Color(153, 0, 0));

        tblSewa.setForeground(new java.awt.Color(153, 0, 0));
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
        });
        jScrollPane6.setViewportView(tblSewa);

        jScrollPane1.setViewportView(jScrollPane6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void tblSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSewaMouseClicked
        try { 
            int row =tblSewa.getSelectedRow(); 
            String tabel_klik=(tblSewa.getModel().getValueAt(row, 0).toString()); 
            java.sql.Statement stm = conn.createStatement(); 
            java.sql.ResultSet sql = stm.executeQuery("select * from sewa where no='"+tabel_klik+"'");
            if(sql.next()){ 
                String no = sql.getString("no");
                    txtNo.setText(no); 
                String nama = sql.getString("tgl");
                    
                String id_driver = sql.getString("id_driver"); 
                    txtId.setText(id_driver); 
                String almt = sql.getString("ktp"); 
                    txtKTP.setText(almt);
                String nama_driver = sql.getString("nama_driver"); 
                    txtNama.setText(nama_driver);
                String tlpn = sql.getString("tlpn"); 
                    txtNama.setText(tlpn);
                String kd_mobil = sql.getString("kd_mobil"); 
                    txtKd.setText(kd_mobil);
                String merk = sql.getString("merk"); 
                    txtMerk.setText(merk);
                String tipe = sql.getString("tipe"); 
                    txtTipe.setText(tipe);
                String thn = sql.getString("thn"); 
                    txtTipe.setText(tipe);
                String nopol = sql.getString("nopol"); 
                    txtNopol.setText(nopol);
                String warna = sql.getString("warna"); 
                    txtWarna.setText(warna);
                String bayar = sql.getString("bayar"); 
                    txtBayar.setText(bayar);
                String lama = sql.getString("lama"); 
                    txtLama.setText(lama);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang anda pilih.");
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSewaMouseClicked

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        String tgl = cbD.getSelectedItem().toString();
        String bln = cbM.getSelectedItem().toString();
        String thn = cbY.getSelectedItem().toString();

        String tgl_gabung = tgl+"/"+bln+"/"+thn;
        
        String sql = "insert into sewa values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNo.getText());
            stat.setString(2, tgl_gabung);
            stat.setString(3, txtId.getText());
            stat.setString(4, txtKTP.getText());
            stat.setString(5, txtNama.getText());
            stat.setString(6, txtTlpn.getText());
            stat.setString(7, txtKd.getText());
            stat.setString(8, txtMerk.getText());
            stat.setString(9, txtTipe.getText());
            stat.setString(10, txtThn.getText());
            stat.setString(11, txtNopol.getText());
            stat.setString(12, txtWarna.getText());
            stat.setString(13, txtBayar.getText());
            stat.setString(14, txtLama.getText());
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
        
        
        String sql = "update sewa set no=?, tgl=?, id_driver=?, ktp=?, nama_driver=?, tlpn=?, kd_mobil=?, merk=?, tipe=?, thn=?, nopol=?, warna=?, bayar=?, lama=? where no='"+txtNo.getText()+"'";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tgl_gabung);
            stat.setString(2, txtId.getText());
            stat.setString(3, txtKTP.getText());
            stat.setString(4, txtNama.getText());
            stat.setString(5, txtTlpn.getText());
            stat.setString(6, txtKd.getText());
            stat.setString(7, txtMerk.getText());
            stat.setString(8, txtTipe.getText());
            stat.setString(9, txtThn.getText());
            stat.setString(10, txtNopol.getText());
            stat.setString(11, txtWarna.getText());
            stat.setString(12, txtBayar.getText());
            stat.setString(13, txtLama.getText());
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
            String sql = "delete from sewa where no='"+txtNo.getText()+"'";
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

    private void tblDriverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDriverMouseClicked
        // TODO add your handling code here:
        try { 
            int row =tblDriver.getSelectedRow(); 
            String tabel_klik=(tblDriver.getModel().getValueAt(row, 0).toString()); 
            java.sql.Statement stm = conn.createStatement(); 
            java.sql.ResultSet sql = stm.executeQuery("select * from driver where id='"+tabel_klik+"'");
            if(sql.next()){ 
                String no = sql.getString("id");
                    txtId.setText(no); 
                String ktp = sql.getString("ktp"); 
                     txtKTP.setText(ktp); 
                String nama = sql.getString("nama");
                    txtNama.setText(nama); 
                String tlpn = sql.getString("tlpn"); 
                    txtTlpn.setText(tlpn);
                String almt = sql.getString("almt"); 
                    
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang anda pilih.");
            
        }

        D_Driver.setVisible(false);
    }//GEN-LAST:event_tblDriverMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dataDriver(txtCari.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblDriverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDriverMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDriverMouseEntered

    private void tblMobilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMobilMouseClicked
        // TODO add your handling code here:
        int bar = tblMobil.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();

        txtKd.setText(a);
        txtMerk.setText(b);
        txtTipe.setText(c);
        txtThn.setText(d);
        txtNopol.setText(e);
        txtWarna.setText(f);
        txtBayar.setText(g);
        
        D_Mobil.setVisible(false);
    }//GEN-LAST:event_tblMobilMouseClicked

    private void tblMobilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMobilMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMobilMouseEntered

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dataMobil(txtCari.getText());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        D_Driver.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        D_Mobil.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

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
                new Sewa().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog D_Driver;
    private javax.swing.JDialog D_Mobil;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblDriver;
    private javax.swing.JTable tblMobil;
    private javax.swing.JTable tblSewa;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCari1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKTP;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtMerk;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtNopol;
    private javax.swing.JTextField txtThn;
    private javax.swing.JTextField txtTipe;
    private javax.swing.JTextField txtTlpn;
    private javax.swing.JTextField txtWarna;
    // End of variables declaration//GEN-END:variables
}
