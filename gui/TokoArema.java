package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// ==================== KELAS UTAMA ====================
public class TokoArema extends JFrame {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new TokoArema());
    }

    // ------------------------ DATA ARRAYS ------------------------
    private static final int MAX_PLG = 50;
    private static final int MAX_BRG = 100;
    private static final int MAX_TRX = 200;

    private Pelanggan[] daftarPelanggan = new Pelanggan[MAX_PLG];
    private Barang[] daftarBarang = new Barang[MAX_BRG];
    private Transaksi[] daftarTransaksi = new Transaksi[MAX_TRX];

    private int jmlPelanggan = 0;
    private int jmlBarang = 0;
    private int jmlTransaksi = 0;

    // ------------------------ KOMPONEN UI ------------------------
    private JTabbedPane tabbedPane;

    // ----- Tab Pelanggan -----
    private JTextField tfIdPlg, tfNamaPlg, tfAlamatPlg, tfTelpPlg;
    private JTable tabelPelanggan;
    private DefaultTableModel modelPelanggan;

    // ----- Tab Barang -----
    private JTextField tfKodeBrg, tfNamaBrg, tfHargaBrg, tfStokBrg, tfKategoriBrg;
    private JTable tabelBarang;
    private DefaultTableModel modelBarang;

    // ----- Tab Transaksi -----
    private JTextField tfNoTrx, tfIdPlgTrx, tfKodeBrgTrx, tfQtyTrx;
    private JTable tabelTransaksi;
    private DefaultTableModel modelTransaksi;

    // ----- Tab Dashboard -----
    private JTextArea taDashboard;

    // ------------------------ KONSTRUKTOR ------------------------
    public TokoArema() {
        setTitle("Toko Arema - Sistem Penjualan");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel lblHeader = new JLabel("TOKO AREMA", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setForeground(new Color(30, 80, 160));
        lblHeader.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("Dashboard", buatTabDashboard());
        tabbedPane.addTab("Pelanggan", buatTabPelanggan());
        tabbedPane.addTab("Barang", buatTabBarang());
        tabbedPane.addTab("Transaksi", buatTabTransaksi());
        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JLabel lblFooter = new JLabel("Achmadillah Yusuf Faqih Febrianto | 25082010193", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFooter.setBorder(new EmptyBorder(5, 0, 5, 0));
        add(lblFooter, BorderLayout.SOUTH);

        refreshDashboard();
        setVisible(true);
    }

    // ==================== TAB DASHBOARD ====================
    private JPanel buatTabDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        taDashboard = new JTextArea();
        taDashboard.setEditable(false);
        taDashboard.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(taDashboard);
        scroll.setBorder(new TitledBorder("Informasi Toko"));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void refreshDashboard() {
        double totalPendapatan = 0;
        for (int i = 0; i < jmlTransaksi; i++) {
            totalPendapatan += daftarTransaksi[i].getTotalHarga();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("===== DASHBOARD TOKO AREMA =====\n\n");
        sb.append("Jumlah Pelanggan : ").append(jmlPelanggan).append("\n");
        sb.append("Jumlah Barang    : ").append(jmlBarang).append("\n");
        sb.append("Jumlah Transaksi : ").append(jmlTransaksi).append("\n");
        sb.append("Total Pendapatan : Rp ").append(String.format("%,.0f", totalPendapatan)).append("\n\n");
        sb.append("===== TRANSAKSI TERAKHIR =====\n");
        int start = Math.max(0, jmlTransaksi - 5);
        for (int i = start; i < jmlTransaksi; i++) {
            sb.append(daftarTransaksi[i].toString()).append("\n");
        }
        taDashboard.setText(sb.toString());
    }

    // ==================== TAB PELANGGAN ====================
    private JPanel buatTabPelanggan() {
        JPanel tab = new JPanel(new BorderLayout(8, 8));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form input
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 6, 6));
        panelForm.setBorder(new TitledBorder("Data Pelanggan"));
        tfIdPlg = buatTextField();
        tfNamaPlg = buatTextField();
        tfAlamatPlg = buatTextField();
        tfTelpPlg = buatTextField();

        panelForm.add(buatLabel("ID Pelanggan :"));
        panelForm.add(tfIdPlg);
        panelForm.add(buatLabel("Nama Lengkap :"));
        panelForm.add(tfNamaPlg);
        panelForm.add(buatLabel("Alamat       :"));
        panelForm.add(tfAlamatPlg);
        panelForm.add(buatLabel("No. Telepon  :"));
        panelForm.add(tfTelpPlg);
        panelForm.add(new JLabel());

        // Tombol
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnTambah = buatTombol("+ Tambah", new Color(40, 167, 69));
        JButton btnUbah = buatTombol("Ubah", new Color(255, 193, 7));
        JButton btnHapus = buatTombol("Hapus", new Color(220, 53, 69));
        JButton btnBersih = buatTombol("Bersihkan", new Color(108, 117, 125));
        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersih);

        JPanel panelAtas = new JPanel(new BorderLayout(6, 6));
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        // Tabel
        String[] kolom = { "ID", "Nama", "Alamat", "Telepon" };
        modelPelanggan = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabelPelanggan = buatTabel(modelPelanggan);
        JScrollPane scroll = new JScrollPane(tabelPelanggan);
        scroll.setBorder(new TitledBorder("Daftar Pelanggan"));

        tab.add(panelAtas, BorderLayout.NORTH);
        tab.add(scroll, BorderLayout.CENTER);

        // Event Handling
        btnTambah.addActionListener(e -> tambahPelanggan());
        btnUbah.addActionListener(e -> ubahPelanggan());
        btnHapus.addActionListener(e -> hapusPelanggan());
        btnBersih.addActionListener(e -> bersihkanFormPlg());

        tabelPelanggan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelPelanggan.getSelectedRow();
                if (baris >= 0) {
                    tfIdPlg.setText(modelPelanggan.getValueAt(baris, 0).toString());
                    tfIdPlg.setEditable(false);
                    tfNamaPlg.setText(modelPelanggan.getValueAt(baris, 1).toString());
                    tfAlamatPlg.setText(modelPelanggan.getValueAt(baris, 2).toString());
                    tfTelpPlg.setText(modelPelanggan.getValueAt(baris, 3).toString());
                }
            }
        });

        return tab;
    }

    private void tambahPelanggan() {
        String id = tfIdPlg.getText().trim();
        String nama = tfNamaPlg.getText().trim();
        String alamat = tfAlamatPlg.getText().trim();
        String telp = tfTelpPlg.getText().trim();

        if (id.isEmpty() || nama.isEmpty()) {
            showWarn("ID dan Nama wajib diisi!");
            return;
        }
        if (jmlPelanggan >= MAX_PLG) {
            showWarn("Kapasitas pelanggan penuh!");
            return;
        }
        if (cariPelanggan(id) >= 0) {
            showWarn("ID Pelanggan sudah ada!");
            return;
        }

        daftarPelanggan[jmlPelanggan] = new Pelanggan(id, nama, alamat, telp);
        modelPelanggan.addRow(new Object[] { id, nama, alamat, telp });
        jmlPelanggan++;
        bersihkanFormPlg();
        showInfo("Pelanggan berhasil ditambahkan!");
        refreshDashboard();
    }

    private void ubahPelanggan() {
        int idx = cariPelanggan(tfIdPlg.getText().trim());
        if (idx < 0) {
            showWarn("Pilih pelanggan dari tabel!");
            return;
        }
        String nama = tfNamaPlg.getText().trim();
        String alamat = tfAlamatPlg.getText().trim();
        String telp = tfTelpPlg.getText().trim();

        int konfirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin mengubah data pelanggan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm != JOptionPane.YES_OPTION)
            return;

        daftarPelanggan[idx].setNama(nama);
        daftarPelanggan[idx].setAlamat(alamat);
        daftarPelanggan[idx].setTelp(telp);

        int baris = tabelPelanggan.getSelectedRow();
        modelPelanggan.setValueAt(nama, baris, 1);
        modelPelanggan.setValueAt(alamat, baris, 2);
        modelPelanggan.setValueAt(telp, baris, 3);
        bersihkanFormPlg();
        showInfo("Data pelanggan berhasil diubah!");
    }

    private void hapusPelanggan() {
        int idx = cariPelanggan(tfIdPlg.getText().trim());
        if (idx < 0) {
            showWarn("Pilih pelanggan dari tabel!");
            return;
        }
        int konfirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus pelanggan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm != JOptionPane.YES_OPTION)
            return;

        // Geser array
        for (int i = idx; i < jmlPelanggan - 1; i++) {
            daftarPelanggan[i] = daftarPelanggan[i + 1];
        }
        daftarPelanggan[--jmlPelanggan] = null;
        modelPelanggan.removeRow(tabelPelanggan.getSelectedRow());
        bersihkanFormPlg();
        showInfo("Pelanggan berhasil dihapus!");
        refreshDashboard();
    }

    private void bersihkanFormPlg() {
        tfIdPlg.setText("");
        tfIdPlg.setEditable(true);
        tfNamaPlg.setText("");
        tfAlamatPlg.setText("");
        tfTelpPlg.setText("");
        tabelPelanggan.clearSelection();
    }

    private int cariPelanggan(String id) {
        for (int i = 0; i < jmlPelanggan; i++) {
            if (daftarPelanggan[i].getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    // ==================== TAB BARANG ====================
    private JPanel buatTabBarang() {
        JPanel tab = new JPanel(new BorderLayout(8, 8));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 6, 6));
        panelForm.setBorder(new TitledBorder("Data Barang"));

        tfKodeBrg = buatTextField();
        tfNamaBrg = buatTextField();
        tfHargaBrg = buatTextField();
        tfStokBrg = buatTextField();
        tfKategoriBrg = buatTextField();

        panelForm.add(buatLabel("Kode Barang :"));
        panelForm.add(tfKodeBrg);
        panelForm.add(buatLabel("Nama Barang :"));
        panelForm.add(tfNamaBrg);
        panelForm.add(buatLabel("Harga (Rp)  :"));
        panelForm.add(tfHargaBrg);
        panelForm.add(buatLabel("Stok        :"));
        panelForm.add(tfStokBrg);
        panelForm.add(buatLabel("Kategori    :"));
        panelForm.add(tfKategoriBrg);
        panelForm.add(new JLabel());

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnTambah = buatTombol("+ Tambah", new Color(40, 167, 69));
        JButton btnUbah = buatTombol("Ubah", new Color(255, 193, 7));
        JButton btnHapus = buatTombol("Hapus", new Color(220, 53, 69));
        JButton btnBersih = buatTombol("Bersihkan", new Color(108, 117, 125));
        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersih);

        JPanel panelAtas = new JPanel(new BorderLayout(6, 6));
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        String[] kolom = { "Kode", "Nama", "Harga", "Stok", "Kategori" };
        modelBarang = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabelBarang = buatTabel(modelBarang);
        JScrollPane scroll = new JScrollPane(tabelBarang);
        scroll.setBorder(new TitledBorder("Daftar Barang"));

        tab.add(panelAtas, BorderLayout.NORTH);
        tab.add(scroll, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> tambahBarang());
        btnUbah.addActionListener(e -> ubahBarang());
        btnHapus.addActionListener(e -> hapusBarang());
        btnBersih.addActionListener(e -> bersihkanFormBrg());

        tabelBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelBarang.getSelectedRow();
                if (baris >= 0) {
                    tfKodeBrg.setText(modelBarang.getValueAt(baris, 0).toString());
                    tfKodeBrg.setEditable(false);
                    tfNamaBrg.setText(modelBarang.getValueAt(baris, 1).toString());
                    // Hapus "Rp" dan format jika ada
                    String hargaStr = modelBarang.getValueAt(baris, 2).toString().replaceAll("[^0-9]", "");
                    tfHargaBrg.setText(hargaStr);
                    tfStokBrg.setText(modelBarang.getValueAt(baris, 3).toString());
                    tfKategoriBrg.setText(modelBarang.getValueAt(baris, 4).toString());
                }
            }
        });

        return tab;
    }

    private void tambahBarang() {
        try {
            String kode = tfKodeBrg.getText().trim();
            String nama = tfNamaBrg.getText().trim();
            double harga = Double.parseDouble(tfHargaBrg.getText().trim());
            int stok = Integer.parseInt(tfStokBrg.getText().trim());
            String kat = tfKategoriBrg.getText().trim();

            if (kode.isEmpty() || nama.isEmpty()) {
                showWarn("Kode dan Nama wajib diisi!");
                return;
            }
            if (jmlBarang >= MAX_BRG) {
                showWarn("Kapasitas barang penuh!");
                return;
            }
            if (cariBarang(kode) >= 0) {
                showWarn("Kode barang sudah ada!");
                return;
            }

            daftarBarang[jmlBarang] = new Barang(kode, nama, harga, stok, kat);
            modelBarang.addRow(new Object[] { kode, nama, "Rp" + String.format("%,.0f", harga), stok, kat });
            jmlBarang++;
            bersihkanFormBrg();
            showInfo("Barang berhasil ditambahkan!");
            refreshDashboard();
        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka!");
        }
    }

    private void ubahBarang() {
        int idx = cariBarang(tfKodeBrg.getText().trim());
        if (idx < 0) {
            showWarn("Pilih barang dari tabel!");
            return;
        }
        try {
            String nama = tfNamaBrg.getText().trim();
            double harga = Double.parseDouble(tfHargaBrg.getText().trim());
            int stok = Integer.parseInt(tfStokBrg.getText().trim());
            String kat = tfKategoriBrg.getText().trim();

            int konfirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin mengubah data barang ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirm != JOptionPane.YES_OPTION)
                return;

            daftarBarang[idx].setNama(nama);
            daftarBarang[idx].setHarga(harga);
            daftarBarang[idx].setStok(stok);
            daftarBarang[idx].setKategori(kat);

            int baris = tabelBarang.getSelectedRow();
            modelBarang.setValueAt(nama, baris, 1);
            modelBarang.setValueAt("Rp" + String.format("%,.0f", harga), baris, 2);
            modelBarang.setValueAt(stok, baris, 3);
            modelBarang.setValueAt(kat, baris, 4);
            bersihkanFormBrg();
            showInfo("Data barang berhasil diubah!");
        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka!");
        }
    }

    private void hapusBarang() {
        int idx = cariBarang(tfKodeBrg.getText().trim());
        if (idx < 0) {
            showWarn("Pilih barang dari tabel!");
            return;
        }
        int konfirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus barang ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm != JOptionPane.YES_OPTION)
            return;

        for (int i = idx; i < jmlBarang - 1; i++) {
            daftarBarang[i] = daftarBarang[i + 1];
        }
        daftarBarang[--jmlBarang] = null;
        modelBarang.removeRow(tabelBarang.getSelectedRow());
        bersihkanFormBrg();
        showInfo("Barang berhasil dihapus!");
        refreshDashboard();
    }

    private void bersihkanFormBrg() {
        tfKodeBrg.setText("");
        tfKodeBrg.setEditable(true);
        tfNamaBrg.setText("");
        tfHargaBrg.setText("");
        tfStokBrg.setText("");
        tfKategoriBrg.setText("");
        tabelBarang.clearSelection();
    }

    private int cariBarang(String kode) {
        for (int i = 0; i < jmlBarang; i++) {
            if (daftarBarang[i].getKode().equalsIgnoreCase(kode))
                return i;
        }
        return -1;
    }

    // ==================== TAB TRANSAKSI ====================
    private JPanel buatTabTransaksi() {
        JPanel tab = new JPanel(new BorderLayout(8, 8));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 6, 6));
        panelForm.setBorder(new TitledBorder("Input Transaksi"));

        tfNoTrx = buatTextField();
        tfIdPlgTrx = buatTextField();
        tfKodeBrgTrx = buatTextField();
        tfQtyTrx = buatTextField();

        panelForm.add(buatLabel("No. Transaksi :"));
        panelForm.add(tfNoTrx);
        panelForm.add(buatLabel("ID Pelanggan  :"));
        panelForm.add(tfIdPlgTrx);
        panelForm.add(buatLabel("Kode Barang   :"));
        panelForm.add(tfKodeBrgTrx);
        panelForm.add(buatLabel("Jumlah (Qty)  :"));
        panelForm.add(tfQtyTrx);
        panelForm.add(new JLabel());

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnTambah = buatTombol("+ Tambah", new Color(40, 167, 69));
        JButton btnHapus = buatTombol("Hapus", new Color(220, 53, 69));
        JButton btnCari = buatTombol("Cari", new Color(23, 162, 184));
        JButton btnStruk = buatTombol("Struk", new Color(102, 16, 242));
        JButton btnReset = buatTombol("Reset", new Color(108, 117, 125));
        panelTombol.add(btnTambah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnCari);
        panelTombol.add(btnStruk);
        panelTombol.add(btnReset);

        JPanel panelAtas = new JPanel(new BorderLayout(6, 6));
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        String[] kolom = { "No. Transaksi", "Pelanggan", "Barang", "Qty", "Total Harga", "Tanggal" };
        modelTransaksi = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabelTransaksi = buatTabel(modelTransaksi);
        JScrollPane scroll = new JScrollPane(tabelTransaksi);
        scroll.setBorder(new TitledBorder("Daftar Transaksi"));

        tab.add(panelAtas, BorderLayout.NORTH);
        tab.add(scroll, BorderLayout.CENTER);

        // Event handling
        btnTambah.addActionListener(e -> tambahTransaksi());
        btnHapus.addActionListener(e -> hapusTransaksi());
        btnCari.addActionListener(e -> cariTransaksi());
        btnStruk.addActionListener(e -> cetakStruk());
        btnReset.addActionListener(e -> bersihkanFormTrx());

        tabelTransaksi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelTransaksi.getSelectedRow();
                if (baris >= 0) {
                    tfNoTrx.setText(modelTransaksi.getValueAt(baris, 0).toString());
                    tfNoTrx.setEditable(false);
                    // Cari ID Pelanggan berdasarkan nama di tabel (bisa tidak akurat jika nama
                    // sama)
                    // Tidak diisi ke form karena tidak disimpan secara langsung
                    tfIdPlgTrx.setText("");
                    tfKodeBrgTrx.setText("");
                    tfQtyTrx.setText("");
                }
            }
        });

        return tab;
    }

    private void tambahTransaksi() {
        try {
            String noTrx = tfNoTrx.getText().trim();
            String idPlg = tfIdPlgTrx.getText().trim();
            String kodeBrg = tfKodeBrgTrx.getText().trim();
            int qty = Integer.parseInt(tfQtyTrx.getText().trim());

            if (noTrx.isEmpty() || idPlg.isEmpty() || kodeBrg.isEmpty()) {
                showWarn("Semua field harus diisi!");
                return;
            }
            if (jmlTransaksi >= MAX_TRX) {
                showWarn("Kapasitas transaksi penuh!");
                return;
            }

            int idxPlg = cariPelanggan(idPlg);
            if (idxPlg < 0) {
                showWarn("ID Pelanggan tidak ditemukan!");
                return;
            }
            int idxBrg = cariBarang(kodeBrg);
            if (idxBrg < 0) {
                showWarn("Kode Barang tidak ditemukan!");
                return;
            }

            Barang barang = daftarBarang[idxBrg];
            if (barang.getStok() < qty) {
                showWarn("Stok tidak mencukupi! Stok tersedia: " + barang.getStok());
                return;
            }

            // Kurangi stok
            barang.setStok(barang.getStok() - qty);
            // Update tampilan tabel barang
            int rowBrg = cariRowTabel(modelBarang, 0, kodeBrg);
            if (rowBrg >= 0) {
                modelBarang.setValueAt(barang.getStok(), rowBrg, 3);
            }

            Pelanggan plg = daftarPelanggan[idxPlg];
            Transaksi trx = new Transaksi(noTrx, plg, barang, qty);
            daftarTransaksi[jmlTransaksi] = trx;

            modelTransaksi.addRow(new Object[] {
                    noTrx,
                    plg.getNama(),
                    barang.getNama(),
                    qty,
                    "Rp" + String.format("%,.0f", trx.getTotalHarga()),
                    trx.getTanggal()
            });
            jmlTransaksi++;
            bersihkanFormTrx();
            showInfo("Transaksi berhasil disimpan!");
            refreshDashboard();
        } catch (NumberFormatException ex) {
            showError("Qty harus berupa angka bulat!");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void hapusTransaksi() {
        int baris = tabelTransaksi.getSelectedRow();
        if (baris < 0) {
            showWarn("Pilih transaksi yang akan dihapus!");
            return;
        }
        int konfirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus transaksi ini?\n(Stok barang tidak akan dikembalikan)",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm != JOptionPane.YES_OPTION)
            return;

        // Geser array
        for (int i = baris; i < jmlTransaksi - 1; i++) {
            daftarTransaksi[i] = daftarTransaksi[i + 1];
        }
        daftarTransaksi[--jmlTransaksi] = null;
        modelTransaksi.removeRow(baris);
        bersihkanFormTrx();
        showInfo("Transaksi berhasil dihapus!");
        refreshDashboard();
    }

    private void cariTransaksi() {
        String noTrx = JOptionPane.showInputDialog(this, "Masukkan No. Transaksi yang dicari:");
        if (noTrx == null || noTrx.trim().isEmpty())
            return;

        StringBuilder sb = new StringBuilder();
        boolean ketemu = false;
        for (int i = 0; i < jmlTransaksi; i++) {
            if (daftarTransaksi[i].getNoTransaksi().equalsIgnoreCase(noTrx.trim())) {
                sb.append(daftarTransaksi[i].toString()).append("\n");
                ketemu = true;
            }
        }
        if (ketemu) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Hasil Pencarian", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showWarn("Transaksi dengan nomor tersebut tidak ditemukan.");
        }
    }

    private void cetakStruk() {
        int baris = tabelTransaksi.getSelectedRow();
        if (baris < 0) {
            showWarn("Pilih transaksi untuk mencetak struk!");
            return;
        }
        Transaksi trx = daftarTransaksi[baris];
        StringBuilder sb = new StringBuilder();
        sb.append("========== STRUK PEMBELIAN ==========\n");
        sb.append("Toko Arema\n");
        sb.append("=====================================\n");
        sb.append("No. Transaksi : ").append(trx.getNoTransaksi()).append("\n");
        sb.append("Tanggal      : ").append(trx.getTanggal()).append("\n");
        sb.append("Pelanggan    : ").append(trx.getPelanggan().getNama()).append("\n");
        sb.append("-------------------------------------\n");
        sb.append("Barang       : ").append(trx.getBarang().getNama()).append("\n");
        sb.append("Harga Satuan : Rp").append(String.format("%,.0f", trx.getBarang().getHarga())).append("\n");
        sb.append("Jumlah       : ").append(trx.getQty()).append("\n");
        sb.append("-------------------------------------\n");
        sb.append("Total Harga  : Rp").append(String.format("%,.0f", trx.getTotalHarga())).append("\n");
        sb.append("=====================================\n");
        sb.append("Terima kasih telah berbelanja!\n");

        JTextArea ta = new JTextArea(sb.toString());
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ta.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Struk Transaksi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void bersihkanFormTrx() {
        tfNoTrx.setText("");
        tfNoTrx.setEditable(true);
        tfIdPlgTrx.setText("");
        tfKodeBrgTrx.setText("");
        tfQtyTrx.setText("");
        tabelTransaksi.clearSelection();
    }

    // ==================== UTILITY UI ====================
    private JTextField buatTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        return tf;
    }

    private JLabel buatLabel(String teks) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }

    private JButton buatTombol(String label, Color warna) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(warna);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 30));
        return btn;
    }

    private JTable buatTabel(DefaultTableModel model) {
        JTable tabel = new JTable(model);
        tabel.setFont(new Font("Arial", Font.PLAIN, 12));
        tabel.setRowHeight(22);
        tabel.setSelectionBackground(new Color(173, 214, 255));
        tabel.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(30, 80, 160));
        tabel.getTableHeader().setForeground(Color.black);
        tabel.setGridColor(new Color(200, 210, 230));
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return tabel;
    }

    private int cariRowTabel(DefaultTableModel model, int kolom, String nilai) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, kolom).toString().equalsIgnoreCase(nilai))
                return i;
        }
        return -1;
    }

    // ==================== POPUP MESSAGES ====================
    private void showInfo(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarn(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== MAIN ====================

}

// ==================== KELAS ENTITAS ====================
class Pelanggan {
    private String id;
    private String nama;
    private String alamat;
    private String telp;

    public Pelanggan(String id, String nama, String alamat, String telp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }
}

class Barang {
    private String kode;
    private String nama;
    private double harga;
    private int stok;
    private String kategori;

    public Barang(String kode, String nama, double harga, int stok, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public String getKategori() {
        return kategori;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}

class Transaksi {
    private String noTransaksi;
    private Pelanggan pelanggan;
    private Barang barang;
    private int qty;
    private double totalHarga;
    private String tanggal;

    public Transaksi(String noTransaksi, Pelanggan pelanggan, Barang barang, int qty) {
        this.noTransaksi = noTransaksi;
        this.pelanggan = pelanggan;
        this.barang = barang;
        this.qty = qty;
        this.totalHarga = barang.getHarga() * qty;
        this.tanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
    }

    public String getNoTransaksi() {
        return noTransaksi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Barang getBarang() {
        return barang;
    }

    public int getQty() {
        return qty;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public String getTanggal() {
        return tanggal;
    }

    @Override
    public String toString() {
        return String.format("No: %s | Pelanggan: %s | Barang: %s | Qty: %d | Total: Rp%.0f | Tanggal: %s",
                noTransaksi, pelanggan.getNama(), barang.getNama(), qty, totalHarga, tanggal);
    }
}
