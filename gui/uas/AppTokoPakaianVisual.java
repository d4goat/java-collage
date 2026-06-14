package uas;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * ============================================================
 * AppTokoPakaianVisual - Main Class (Modul 21 - GUI Swing)
 * Sistem Manajemen Toko Pakaian
 *
 * Mengintegrasikan:
 *  - Enkapsulasi          (Modul 16)
 *  - Asosiasi & Container (Modul 18 & 19)
 *  - Inheritance          (Modul 20)
 *  - GUI Swing            (Modul 21)
 *  - Linked List Custom   (Modul 22)
 * ============================================================
 */
public class AppTokoPakaianVisual extends JFrame {

    // ============================================================
    // WARNA & FONT DESAIN
    // ============================================================
    private static final Color CLR_BG         = new Color(13,  17,  23);
    private static final Color CLR_SURFACE     = new Color(22,  27,  34);
    private static final Color CLR_CARD        = new Color(30,  37,  48);
    private static final Color CLR_ACCENT      = new Color(88, 166, 255);
    private static final Color CLR_ACCENT2     = new Color(63, 185, 130);
    private static final Color CLR_ACCENT3     = new Color(247,183, 49);
    private static final Color CLR_TEXT        = new Color(230,237,243);
    private static final Color CLR_TEXT_DIM    = new Color(139,148,158);
    private static final Color CLR_BORDER      = new Color(48,  54,  61);
    private static final Color CLR_INPUT_BG    = new Color(13,  17,  23);
    private static final Color CLR_DANGER      = new Color(248, 81,  73);
    private static final Color CLR_ROW_ALT     = new Color(22,  27,  34);
    private static final Color CLR_TABLE_HEADER= new Color(33,  38,  45);
    private static final Color CLR_SELECTED    = new Color(36,  41,  67);

    private static final Font  FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font  FONT_HEADING = new Font("Segoe UI", Font.BOLD,  15);
    private static final Font  FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_FIELD   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_BTN     = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font  FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);

    private static final NumberFormat FMT_RUPIAH =
            NumberFormat.getNumberInstance(new Locale("id", "ID"));

    // ============================================================
    // DATA STORE - Linked List Custom (Modul 22)
    // ============================================================
    private final LinkedListCustom<cPakaian>  listBarang    = new LinkedListCustom<>();
    private final LinkedListCustom<cPelangan> listPelangan  = new LinkedListCustom<>();
    private final LinkedListCustom<cTransaksi> listTransaksi = new LinkedListCustom<>();

    // Counter auto ID
    private int ctrBarang    = 1;
    private int ctrPelangan  = 1;
    private int ctrTransaksi = 1;

    // ============================================================
    // KOMPONEN NAVIGASI UTAMA
    // ============================================================
    private JPanel         panelContent;
    private CardLayout     cardLayout;
    private JButton        btnNavBarang, btnNavPelangan, btnNavTransaksi;
    private JButton        btnNavActive; // button nav yang sedang aktif

    // ============================================================
    // KOMPONEN PANEL BARANG
    // ============================================================
    private JTextField     tfKodeBarang, tfNamaBarang, tfHarga, tfStok;
    private JTextField     tfJenisBahan, tfUkuranPinggang;
    private JRadioButton   rbAtasan, rbBawahan;
    private JPanel         panelAtasanField, panelBawahanField;
    private DefaultTableModel modelTblBarang;
    private JTable         tblBarang;
    private JLabel         lblJumlahBarang;

    // ============================================================
    // KOMPONEN PANEL PELANGGAN
    // ============================================================
    private JTextField     tfIdPelangan, tfNamaPelangan, tfNoTelp;
    private DefaultTableModel modelTblPelangan;
    private JTable         tblPelangan;
    private JLabel         lblJumlahPelangan;

    // ============================================================
    // KOMPONEN PANEL TRANSAKSI
    // ============================================================
    private JComboBox<String> cbPelangan, cbBarang;
    private JTextField     tfJumlahBeli, tfTotalBayar;
    private DefaultTableModel modelTblTransaksi;
    private JTable         tblTransaksi;
    private JLabel         lblJumlahTransaksi;

    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public AppTokoPakaianVisual() {
        setTitle("Sistem Manajemen Toko Pakaian ✦ Fashion Store Pro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1200, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CLR_BG);

        initUI();
        tambahDataContoh();
        setVisible(true);
    }

    // ============================================================
    // INISIALISASI UI UTAMA
    // ============================================================
    private void initUI() {
        setLayout(new BorderLayout(0, 0));

        // ----- Sidebar Kiri -----
        JPanel sidebar = buildSidebar();
        add(sidebar, BorderLayout.WEST);

        // ----- Header Atas -----
        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        // ----- Konten Utama (Card Layout) -----
        cardLayout    = new CardLayout();
        panelContent  = new JPanel(cardLayout);
        panelContent.setBackground(CLR_BG);
        panelContent.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelContent.add(buildPanelBarang(),     "BARANG");
        panelContent.add(buildPanelPelangan(),   "PELANGAN");
        panelContent.add(buildPanelTransaksi(),  "TRANSAKSI");

        add(panelContent, BorderLayout.CENTER);

        // ----- Status Bar Bawah -----
        JPanel statusBar = buildStatusBar();
        add(statusBar, BorderLayout.SOUTH);

        // Tampilkan panel pertama
        navigateTo("BARANG", btnNavBarang);
    }

    // ============================================================
    // SIDEBAR
    // ============================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(CLR_SURFACE);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, CLR_BORDER));

        // Logo Area
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(CLR_SURFACE);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(24, 16, 24, 16));

        JLabel lblLogo = new JLabel("👗");
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAppName = new JLabel("Fashion Store");
        lblAppName.setFont(FONT_HEADING);
        lblAppName.setForeground(CLR_TEXT);
        lblAppName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAppSub = new JLabel("Management Pro");
        lblAppSub.setFont(FONT_SMALL);
        lblAppSub.setForeground(CLR_ACCENT);
        lblAppSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(lblLogo);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(lblAppName);
        logoPanel.add(Box.createVerticalStrut(2));
        logoPanel.add(lblAppSub);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_BORDER);
        sep.setBackground(CLR_BORDER);

        // Nav Buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(CLR_SURFACE);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        btnNavBarang    = createNavButton("📦  Daftar Barang",    CLR_ACCENT);
        btnNavPelangan  = createNavButton("👤  Daftar Pelanggan",  CLR_ACCENT2);
        btnNavTransaksi = createNavButton("🧾  Daftar Transaksi", CLR_ACCENT3);

        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(btnNavBarang);
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(btnNavPelangan);
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(btnNavTransaksi);

        btnNavBarang.addActionListener(e    -> navigateTo("BARANG",    btnNavBarang));
        btnNavPelangan.addActionListener(e  -> navigateTo("PELANGAN",  btnNavPelangan));
        btnNavTransaksi.addActionListener(e -> navigateTo("TRANSAKSI", btnNavTransaksi));

        // Info Box Bawah
        JPanel infoBox = new JPanel();
        infoBox.setBackground(new Color(33, 38, 45));
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBorder(new CompoundBorder(
            new EmptyBorder(12, 12, 16, 12),
            new CompoundBorder(
                BorderFactory.createLineBorder(CLR_BORDER, 1, true),
                new EmptyBorder(10, 10, 10, 10)
            )
        ));

        JLabel lblVer = new JLabel("v1.0.0 - UAS 2025");
        lblVer.setFont(FONT_SMALL);
        lblVer.setForeground(CLR_TEXT_DIM);
        lblVer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBy = new JLabel("Pemrograman Berorientasi Objek");
        lblBy.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblBy.setForeground(CLR_TEXT_DIM);
        lblBy.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoBox.add(lblVer);
        infoBox.add(Box.createVerticalStrut(3));
        infoBox.add(lblBy);

        sidebar.add(logoPanel, BorderLayout.NORTH);
        sidebar.add(sep, BorderLayout.CENTER);

        JPanel bottomArea = new JPanel(new BorderLayout());
        bottomArea.setBackground(CLR_SURFACE);
        bottomArea.add(navPanel, BorderLayout.NORTH);
        bottomArea.add(infoBox, BorderLayout.SOUTH);
        sidebar.add(bottomArea, BorderLayout.SOUTH);

        return sidebar;
    }

    // ============================================================
    // HEADER
    // ============================================================
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CLR_SURFACE);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CLR_BORDER));

        JLabel lblTitle = new JLabel("  ✦  Sistem Manajemen Toko Pakaian");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(CLR_TEXT);

        JLabel lblTime = new JLabel("  Fashion Store Pro  ");
        lblTime.setFont(FONT_SMALL);
        lblTime.setForeground(CLR_TEXT_DIM);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(lblTime,  BorderLayout.EAST);
        return header;
    }

    // ============================================================
    // STATUS BAR
    // ============================================================
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 5));
        bar.setBackground(CLR_SURFACE);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, CLR_BORDER));

        JLabel lbl = new JLabel("● Sistem aktif  |  Linked List Custom  |  OOP Penuh  |  Java Swing GUI");
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(CLR_ACCENT2);
        bar.add(lbl);
        return bar;
    }

    // ============================================================
    // NAVIGASI
    // ============================================================
    private void navigateTo(String card, JButton btn) {
        cardLayout.show(panelContent, card);
        if (btnNavActive != null) {
            btnNavActive.setBackground(CLR_SURFACE);
            btnNavActive.setForeground(CLR_TEXT_DIM);
        }
        btn.setBackground(CLR_CARD);
        btn.setForeground(CLR_TEXT);
        btnNavActive = btn;

        if ("TRANSAKSI".equals(card)) refreshComboBoxes();
    }

    // ============================================================
    // HELPER - NAV BUTTON
    // ============================================================
    private JButton createNavButton(String text, Color accent) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setForeground(CLR_TEXT_DIM);
        btn.setBackground(CLR_SURFACE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (btn != btnNavActive) {
                    btn.setBackground(new Color(30, 37, 48));
                    btn.setForeground(CLR_TEXT);
                }
            }
            @Override public void mouseExited(MouseEvent e) {
                if (btn != btnNavActive) {
                    btn.setBackground(CLR_SURFACE);
                    btn.setForeground(CLR_TEXT_DIM);
                }
            }
        });
        return btn;
    }

    // ============================================================
    // HELPER - BUAT LABEL
    // ============================================================
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(CLR_TEXT_DIM);
        return lbl;
    }

    // ============================================================
    // HELPER - BUAT TEXT FIELD
    // ============================================================
    private JTextField makeTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_FIELD);
        tf.setBackground(CLR_INPUT_BG);
        tf.setForeground(CLR_TEXT);
        tf.setCaretColor(CLR_ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    // ============================================================
    // HELPER - BUAT AKSI TOMBOL
    // ============================================================
    private JButton makeButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));

        btn.addMouseListener(new MouseAdapter() {
            Color orig = bgColor;
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(orig.brighter());
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(orig);
            }
        });
        return btn;
    }

    // ============================================================
    // HELPER - BUAT CARD PANEL
    // ============================================================
    private JPanel makeCard(String title, Color accentColor) {
        JPanel card = new JPanel();
        card.setBackground(CLR_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_HEADING);
        lblTitle.setForeground(accentColor);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setBorder(new EmptyBorder(0, 0, 12, 0));

        card.add(lblTitle);
        return card;
    }

    // ============================================================
    // HELPER - BUAT TABEL DARK
    // ============================================================
    private JTable makeDarkTable(DefaultTableModel model) {
        JTable tbl = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tbl.setFont(FONT_LABEL);
        tbl.setForeground(CLR_TEXT);
        tbl.setBackground(CLR_SURFACE);
        tbl.setRowHeight(32);
        tbl.setGridColor(CLR_BORDER);
        tbl.setSelectionBackground(CLR_SELECTED);
        tbl.setSelectionForeground(CLR_TEXT);
        tbl.setShowHorizontalLines(true);
        tbl.setShowVerticalLines(false);
        tbl.setIntercellSpacing(new Dimension(0, 0));
        tbl.setFillsViewportHeight(true);

        // Header
        JTableHeader th = tbl.getTableHeader();
        th.setFont(FONT_BTN);
        th.setBackground(CLR_TABLE_HEADER);
        th.setForeground(CLR_TEXT_DIM);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CLR_BORDER));
        th.setReorderingAllowed(false);

        // Row renderer alternating color
        tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (isSelected) {
                    setBackground(CLR_SELECTED);
                    setForeground(CLR_TEXT);
                } else {
                    setBackground(row % 2 == 0 ? CLR_SURFACE : CLR_ROW_ALT);
                    setForeground(CLR_TEXT);
                }
                return this;
            }
        });
        return tbl;
    }

    // ============================================================
    // PANEL BARANG (Tab 1)
    // ============================================================
    private JPanel buildPanelBarang() {
        JPanel root = new JPanel(new BorderLayout(16, 0));
        root.setBackground(CLR_BG);

        // ---- Kiri: Form Input ----
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(CLR_BG);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(340, 0));

        JPanel cardForm = makeCard("📦  Tambah Data Barang", CLR_ACCENT);
        cardForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardForm.setMaximumSize(new Dimension(340, Integer.MAX_VALUE));

        // Jenis Pakaian Radio
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelRadio.setOpaque(false);
        rbAtasan  = new JRadioButton("Atasan");
        rbBawahan = new JRadioButton("Bawahan");
        styleRadio(rbAtasan,  CLR_ACCENT);
        styleRadio(rbBawahan, CLR_ACCENT2);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAtasan); bg.add(rbBawahan);
        rbAtasan.setSelected(true);
        panelRadio.add(rbAtasan);
        panelRadio.add(Box.createHorizontalStrut(16));
        panelRadio.add(rbBawahan);

        // Fields
        tfKodeBarang     = makeTextField();
        tfNamaBarang     = makeTextField();
        tfHarga          = makeTextField();
        tfStok           = makeTextField();
        tfJenisBahan     = makeTextField();
        tfUkuranPinggang = makeTextField();

        // Panel khusus Atasan
        panelAtasanField = new JPanel(new BorderLayout());
        panelAtasanField.setOpaque(false);
        panelAtasanField.add(makeLabel("Jenis Bahan (cth: Katun, Sifon):"), BorderLayout.NORTH);
        panelAtasanField.add(tfJenisBahan, BorderLayout.CENTER);

        // Panel khusus Bawahan
        panelBawahanField = new JPanel(new BorderLayout());
        panelBawahanField.setOpaque(false);
        panelBawahanField.add(makeLabel("Ukuran Pinggang (cm):"), BorderLayout.NORTH);
        panelBawahanField.add(tfUkuranPinggang, BorderLayout.CENTER);
        panelBawahanField.setVisible(false);

        rbAtasan.addActionListener(e -> {
            panelAtasanField.setVisible(true);
            panelBawahanField.setVisible(false);
            cardForm.revalidate();
        });
        rbBawahan.addActionListener(e -> {
            panelAtasanField.setVisible(false);
            panelBawahanField.setVisible(true);
            cardForm.revalidate();
        });

        // Tombol
        JButton btnTambah = makeButton("+ Tambah Barang", CLR_ACCENT);
        JButton btnHapus  = makeButton("✕ Hapus Terpilih", CLR_DANGER);

        btnTambah.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnHapus.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnTambah.addActionListener(e -> actionTambahBarang());
        btnHapus.addActionListener(e  -> actionHapusBarang());

        addFormRow(cardForm, "Kode Barang (auto):", tfKodeBarang);
        addFormRow(cardForm, "Nama Pakaian:", tfNamaBarang);
        addFormRow(cardForm, "Harga (Rp):", tfHarga);
        addFormRow(cardForm, "Stok:", tfStok);
        addFormRow(cardForm, "Jenis Pakaian:", panelRadio);
        cardForm.add(Box.createVerticalStrut(6));
        cardForm.add(panelAtasanField);
        cardForm.add(panelBawahanField);
        cardForm.add(Box.createVerticalStrut(14));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnTambah);
        btnPanel.add(Box.createHorizontalStrut(8));
        btnPanel.add(btnHapus);
        cardForm.add(btnPanel);

        leftPanel.add(cardForm);

        // ---- Kanan: Tabel Barang ----
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(CLR_BG);

        // Header tabel
        JPanel tblHeader = new JPanel(new BorderLayout());
        tblHeader.setBackground(CLR_BG);
        JLabel lblTblTitle = new JLabel("Daftar Barang");
        lblTblTitle.setFont(FONT_HEADING);
        lblTblTitle.setForeground(CLR_TEXT);
        lblJumlahBarang = new JLabel("Total: 0 item");
        lblJumlahBarang.setFont(FONT_SMALL);
        lblJumlahBarang.setForeground(CLR_TEXT_DIM);
        tblHeader.add(lblTblTitle, BorderLayout.WEST);
        tblHeader.add(lblJumlahBarang, BorderLayout.EAST);

        String[] colBarang = {"Kode", "Nama Pakaian", "Jenis", "Detail Spesifik",
                              "Harga (Rp)", "Stok"};
        modelTblBarang = new DefaultTableModel(colBarang, 0);
        tblBarang = makeDarkTable(modelTblBarang);
        tblBarang.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblBarang.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblBarang.getColumnModel().getColumn(2).setPreferredWidth(70);
        tblBarang.getColumnModel().getColumn(3).setPreferredWidth(130);
        tblBarang.getColumnModel().getColumn(4).setPreferredWidth(110);
        tblBarang.getColumnModel().getColumn(5).setPreferredWidth(50);

        JScrollPane scrollBarang = new JScrollPane(tblBarang);
        styleScrollPane(scrollBarang);

        rightPanel.add(tblHeader,    BorderLayout.NORTH);
        rightPanel.add(scrollBarang, BorderLayout.CENTER);

        root.add(leftPanel,  BorderLayout.WEST);
        root.add(rightPanel, BorderLayout.CENTER);
        return root;
    }

    // ============================================================
    // PANEL PELANGGAN (Tab 2)
    // ============================================================
    private JPanel buildPanelPelangan() {
        JPanel root = new JPanel(new BorderLayout(16, 0));
        root.setBackground(CLR_BG);

        // ---- Kiri: Form Input ----
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(CLR_BG);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(320, 0));

        JPanel cardForm = makeCard("👤  Tambah Data Pelanggan", CLR_ACCENT2);
        cardForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardForm.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));

        tfIdPelangan   = makeTextField();
        tfNamaPelangan = makeTextField();
        tfNoTelp       = makeTextField();

        JButton btnTambah = makeButton("+ Tambah Pelanggan", CLR_ACCENT2);
        JButton btnHapus  = makeButton("✕ Hapus Terpilih",   CLR_DANGER);

        btnTambah.addActionListener(e -> actionTambahPelangan());
        btnHapus.addActionListener(e  -> actionHapusPelangan());

        addFormRow(cardForm, "ID Pelanggan (auto):", tfIdPelangan);
        addFormRow(cardForm, "Nama Pelanggan:", tfNamaPelangan);
        addFormRow(cardForm, "Nomor Telepon:", tfNoTelp);
        cardForm.add(Box.createVerticalStrut(14));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnTambah);
        btnPanel.add(Box.createHorizontalStrut(8));
        btnPanel.add(btnHapus);
        cardForm.add(btnPanel);

        leftPanel.add(cardForm);

        // ---- Kanan: Tabel Pelanggan ----
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(CLR_BG);

        JPanel tblHeader = new JPanel(new BorderLayout());
        tblHeader.setBackground(CLR_BG);
        JLabel lblTblTitle = new JLabel("Daftar Pelanggan");
        lblTblTitle.setFont(FONT_HEADING);
        lblTblTitle.setForeground(CLR_TEXT);
        lblJumlahPelangan = new JLabel("Total: 0 pelanggan");
        lblJumlahPelangan.setFont(FONT_SMALL);
        lblJumlahPelangan.setForeground(CLR_TEXT_DIM);
        tblHeader.add(lblTblTitle, BorderLayout.WEST);
        tblHeader.add(lblJumlahPelangan, BorderLayout.EAST);

        String[] colPelangan = {"ID Pelanggan", "Nama", "No. Telepon"};
        modelTblPelangan = new DefaultTableModel(colPelangan, 0);
        tblPelangan = makeDarkTable(modelTblPelangan);
        tblPelangan.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblPelangan.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblPelangan.getColumnModel().getColumn(2).setPreferredWidth(140);

        JScrollPane scrollPelangan = new JScrollPane(tblPelangan);
        styleScrollPane(scrollPelangan);

        rightPanel.add(tblHeader,      BorderLayout.NORTH);
        rightPanel.add(scrollPelangan, BorderLayout.CENTER);

        root.add(leftPanel,  BorderLayout.WEST);
        root.add(rightPanel, BorderLayout.CENTER);
        return root;
    }

    // ============================================================
    // PANEL TRANSAKSI (Tab 3)
    // ============================================================
    private JPanel buildPanelTransaksi() {
        JPanel root = new JPanel(new BorderLayout(16, 0));
        root.setBackground(CLR_BG);

        // ---- Kiri: Form Input ----
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(CLR_BG);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(340, 0));

        JPanel cardForm = makeCard("🧾  Buat Transaksi", CLR_ACCENT3);
        cardForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardForm.setMaximumSize(new Dimension(340, Integer.MAX_VALUE));

        cbPelangan   = new JComboBox<>();
        cbBarang     = new JComboBox<>();
        tfJumlahBeli = makeTextField();
        tfTotalBayar = makeTextField();

        styleComboBox(cbPelangan);
        styleComboBox(cbBarang);

        tfTotalBayar.setEditable(false);
        tfTotalBayar.setForeground(CLR_ACCENT3);
        tfTotalBayar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnHitung = makeButton("⟳ Hitung Total", new Color(56, 139, 253));
        JButton btnSimpan = makeButton("✔ Simpan Transaksi", CLR_ACCENT3);
        JButton btnHapus  = makeButton("✕ Hapus Terpilih",   CLR_DANGER);

        btnHitung.addActionListener(e -> actionHitungTotal());
        btnSimpan.addActionListener(e -> actionSimpanTransaksi());
        btnHapus.addActionListener(e  -> actionHapusTransaksi());

        addFormRow(cardForm, "Pilih Pelanggan:", cbPelangan);
        addFormRow(cardForm, "Pilih Barang:", cbBarang);
        addFormRow(cardForm, "Jumlah Beli:", tfJumlahBeli);

        cardForm.add(Box.createVerticalStrut(6));
        cardForm.add(btnHitung);
        cardForm.add(Box.createVerticalStrut(8));
        addFormRow(cardForm, "Total Bayar:", tfTotalBayar);
        cardForm.add(Box.createVerticalStrut(14));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSimpan);
        btnPanel.add(Box.createHorizontalStrut(8));
        btnPanel.add(btnHapus);
        cardForm.add(btnPanel);

        leftPanel.add(cardForm);

        // ---- Kanan: Tabel Transaksi ----
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(CLR_BG);

        JPanel tblHeader = new JPanel(new BorderLayout());
        tblHeader.setBackground(CLR_BG);
        JLabel lblTblTitle = new JLabel("Histori Transaksi");
        lblTblTitle.setFont(FONT_HEADING);
        lblTblTitle.setForeground(CLR_TEXT);
        lblJumlahTransaksi = new JLabel("Total: 0 transaksi");
        lblJumlahTransaksi.setFont(FONT_SMALL);
        lblJumlahTransaksi.setForeground(CLR_TEXT_DIM);
        tblHeader.add(lblTblTitle,      BorderLayout.WEST);
        tblHeader.add(lblJumlahTransaksi, BorderLayout.EAST);

        String[] colTrx = {"No. Transaksi", "Pelanggan", "Barang", "Qty", "Total Bayar (Rp)"};
        modelTblTransaksi = new DefaultTableModel(colTrx, 0);
        tblTransaksi = makeDarkTable(modelTblTransaksi);
        tblTransaksi.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblTransaksi.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblTransaksi.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblTransaksi.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblTransaksi.getColumnModel().getColumn(4).setPreferredWidth(130);

        JScrollPane scrollTrx = new JScrollPane(tblTransaksi);
        styleScrollPane(scrollTrx);

        rightPanel.add(tblHeader,  BorderLayout.NORTH);
        rightPanel.add(scrollTrx, BorderLayout.CENTER);

        root.add(leftPanel,  BorderLayout.WEST);
        root.add(rightPanel, BorderLayout.CENTER);
        return root;
    }

    // ============================================================
    // ACTION: TAMBAH BARANG (Modul 21 - ActionListener)
    // ============================================================
    private void actionTambahBarang() {
        String nama  = tfNamaBarang.getText().trim();
        String hargaStr = tfHarga.getText().trim().replaceAll("[^\\d]", "");
        String stokStr  = tfStok.getText().trim().replaceAll("[^\\d]", "");

        if (nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
            showError("Nama, Harga, dan Stok tidak boleh kosong!");
            return;
        }

        double harga;
        int stok;
        try {
            harga = Double.parseDouble(hargaStr);
            stok  = Integer.parseInt(stokStr);
        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka valid!");
            return;
        }

        String kode = String.format("BRG%03d", ctrBarang++);

        cPakaian barang;
        if (rbAtasan.isSelected()) {
            String bahan = tfJenisBahan.getText().trim();
            if (bahan.isEmpty()) { showError("Jenis Bahan tidak boleh kosong!"); return; }
            barang = new cAtasan(kode, nama, harga, stok, bahan);
        } else {
            String pingStr = tfUkuranPinggang.getText().trim().replaceAll("[^\\d]", "");
            if (pingStr.isEmpty()) { showError("Ukuran Pinggang tidak boleh kosong!"); return; }
            int ping;
            try { ping = Integer.parseInt(pingStr); }
            catch (NumberFormatException ex) { showError("Ukuran Pinggang harus angka!"); return; }
            barang = new cBawahan(kode, nama, harga, stok, ping);
        }

        // Tambahkan ke Linked List (Modul 22)
        listBarang.tambahNode(barang);

        // Update Tabel
        modelTblBarang.addRow(new Object[]{
            barang.getKodeBarang(),
            barang.getNamaPakaian(),
            barang.getJenisPakaian(),
            barang.getDetailSpesifik(),
            "Rp " + FMT_RUPIAH.format(barang.getHarga()),
            barang.getStok()
        });
        lblJumlahBarang.setText("Total: " + listBarang.getSize() + " item");

        // Cetak ke konsol
        System.out.println("[TAMBAH BARANG] " + barang);
        listBarang.cetak();

        clearFieldsBarang();
        showSuccess("Barang " + kode + " berhasil ditambahkan!");
    }

    // ============================================================
    // ACTION: HAPUS BARANG
    // ============================================================
    private void actionHapusBarang() {
        int row = tblBarang.getSelectedRow();
        if (row < 0) { showError("Pilih baris barang yang ingin dihapus!"); return; }

        String kode = (String) modelTblBarang.getValueAt(row, 0);
        listBarang.hapusNode(row);
        modelTblBarang.removeRow(row);
        lblJumlahBarang.setText("Total: " + listBarang.getSize() + " item");

        System.out.println("[HAPUS BARANG] Kode: " + kode);
        showSuccess("Barang berhasil dihapus!");
    }

    // ============================================================
    // ACTION: TAMBAH PELANGGAN
    // ============================================================
    private void actionTambahPelangan() {
        String nama  = tfNamaPelangan.getText().trim();
        String telp  = tfNoTelp.getText().trim();

        if (nama.isEmpty() || telp.isEmpty()) {
            showError("Nama dan No. Telepon tidak boleh kosong!");
            return;
        }

        String id = String.format("PLG%03d", ctrPelangan++);
        cPelangan pelangan = new cPelangan(id, nama, telp);

        // Tambahkan ke Linked List (Modul 22)
        listPelangan.tambahNode(pelangan);

        modelTblPelangan.addRow(new Object[]{
            pelangan.getIdPelangan(),
            pelangan.getNama(),
            pelangan.getNoTelp()
        });
        lblJumlahPelangan.setText("Total: " + listPelangan.getSize() + " pelanggan");

        System.out.println("[TAMBAH PELANGGAN] " + pelangan);
        listPelangan.cetak();

        clearFieldsPelangan();
        showSuccess("Pelanggan " + id + " berhasil didaftarkan!");
    }

    // ============================================================
    // ACTION: HAPUS PELANGGAN
    // ============================================================
    private void actionHapusPelangan() {
        int row = tblPelangan.getSelectedRow();
        if (row < 0) { showError("Pilih baris pelanggan yang ingin dihapus!"); return; }

        String id = (String) modelTblPelangan.getValueAt(row, 0);
        listPelangan.hapusNode(row);
        modelTblPelangan.removeRow(row);
        lblJumlahPelangan.setText("Total: " + listPelangan.getSize() + " pelanggan");

        System.out.println("[HAPUS PELANGGAN] ID: " + id);
        showSuccess("Pelanggan berhasil dihapus!");
    }

    // ============================================================
    // ACTION: HITUNG TOTAL TRANSAKSI
    // ============================================================
    private void actionHitungTotal() {
        int idxBarang = cbBarang.getSelectedIndex();
        String qtyStr = tfJumlahBeli.getText().trim().replaceAll("[^\\d]", "");

        if (idxBarang < 0 || listBarang.isEmpty()) {
            showError("Pilih barang terlebih dahulu!"); return;
        }
        if (qtyStr.isEmpty()) {
            showError("Masukkan jumlah beli!"); return;
        }

        int qty;
        try { qty = Integer.parseInt(qtyStr); }
        catch (NumberFormatException ex) { showError("Jumlah beli harus angka!"); return; }

        cPakaian barang = listBarang.getData(idxBarang);
        if (barang == null) { showError("Barang tidak ditemukan!"); return; }

        if (qty > barang.getStok()) {
            showError("Stok tidak mencukupi! Stok tersedia: " + barang.getStok()); return;
        }

        double total = barang.getHarga() * qty;
        tfTotalBayar.setText("Rp " + FMT_RUPIAH.format(total));
    }

    // ============================================================
    // ACTION: SIMPAN TRANSAKSI
    // ============================================================
    private void actionSimpanTransaksi() {
        int idxPelangan = cbPelangan.getSelectedIndex();
        int idxBarang   = cbBarang.getSelectedIndex();
        String qtyStr   = tfJumlahBeli.getText().trim().replaceAll("[^\\d]", "");

        if (listPelangan.isEmpty()) { showError("Belum ada pelanggan terdaftar!"); return; }
        if (listBarang.isEmpty())   { showError("Belum ada barang terdaftar!"); return; }
        if (idxPelangan < 0)        { showError("Pilih pelanggan!"); return; }
        if (idxBarang < 0)          { showError("Pilih barang!"); return; }
        if (qtyStr.isEmpty())       { showError("Masukkan jumlah beli!"); return; }

        int qty;
        try { qty = Integer.parseInt(qtyStr); }
        catch (NumberFormatException ex) { showError("Jumlah beli harus angka!"); return; }

        cPelangan pelangan = listPelangan.getData(idxPelangan);
        cPakaian  barang   = listBarang.getData(idxBarang);

        if (pelangan == null || barang == null) {
            showError("Data pelanggan atau barang tidak ditemukan!"); return;
        }
        if (qty <= 0) { showError("Jumlah beli harus lebih dari 0!"); return; }
        if (qty > barang.getStok()) {
            showError("Stok tidak mencukupi! Stok tersedia: " + barang.getStok()); return;
        }

        String noTrx = String.format("TRX%04d", ctrTransaksi++);

        // Asosiasi Transaksi -> Pelanggan & Barang (Modul 18 & 19)
        cTransaksi trx = new cTransaksi(noTrx, pelangan, barang, qty);

        // Update stok
        barang.setStok(barang.getStok() - qty);

        // Tambahkan ke Linked List Transaksi (Modul 22)
        listTransaksi.tambahNode(trx);

        modelTblTransaksi.addRow(new Object[]{
            trx.getNoTransaksi(),
            pelangan.getNama() + " (" + pelangan.getIdPelangan() + ")",
            barang.getNamaPakaian(),
            qty,
            "Rp " + FMT_RUPIAH.format(trx.getTotalBayar())
        });
        lblJumlahTransaksi.setText("Total: " + listTransaksi.getSize() + " transaksi");

        // Refresh tabel barang (stok berkurang)
        refreshTabelBarang();

        System.out.println("[TRANSAKSI BARU] " + trx);
        listTransaksi.cetak();

        clearFieldsTransaksi();
        showSuccess(String.format("Transaksi %s berhasil! Total: Rp %s",
            noTrx, FMT_RUPIAH.format(trx.getTotalBayar())));
    }

    // ============================================================
    // ACTION: HAPUS TRANSAKSI
    // ============================================================
    private void actionHapusTransaksi() {
        int row = tblTransaksi.getSelectedRow();
        if (row < 0) { showError("Pilih baris transaksi yang ingin dihapus!"); return; }

        String no = (String) modelTblTransaksi.getValueAt(row, 0);
        listTransaksi.hapusNode(row);
        modelTblTransaksi.removeRow(row);
        lblJumlahTransaksi.setText("Total: " + listTransaksi.getSize() + " transaksi");

        System.out.println("[HAPUS TRANSAKSI] No: " + no);
        showSuccess("Transaksi berhasil dihapus!");
    }

    // ============================================================
    // REFRESH HELPERS
    // ============================================================
    private void refreshTabelBarang() {
        modelTblBarang.setRowCount(0);
        ArrayList<cPakaian> list = listBarang.getDaftar();
        for (cPakaian b : list) {
            modelTblBarang.addRow(new Object[]{
                b.getKodeBarang(),
                b.getNamaPakaian(),
                b.getJenisPakaian(),
                b.getDetailSpesifik(),
                "Rp " + FMT_RUPIAH.format(b.getHarga()),
                b.getStok()
            });
        }
        lblJumlahBarang.setText("Total: " + listBarang.getSize() + " item");
    }

    private void refreshComboBoxes() {
        cbPelangan.removeAllItems();
        ArrayList<cPelangan> lp = listPelangan.getDaftar();
        for (cPelangan p : lp) {
            cbPelangan.addItem(p.getNama() + " (" + p.getIdPelangan() + ")");
        }

        cbBarang.removeAllItems();
        ArrayList<cPakaian> lb = listBarang.getDaftar();
        for (cPakaian b : lb) {
            cbBarang.addItem(b.getNamaPakaian() + " [" + b.getKodeBarang() + "] Stok:" + b.getStok());
        }
    }

    // ============================================================
    // CLEAR FIELDS
    // ============================================================
    private void clearFieldsBarang() {
        tfNamaBarang.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        tfJenisBahan.setText("");
        tfUkuranPinggang.setText("");
        rbAtasan.setSelected(true);
        panelAtasanField.setVisible(true);
        panelBawahanField.setVisible(false);
    }

    private void clearFieldsPelangan() {
        tfNamaPelangan.setText("");
        tfNoTelp.setText("");
    }

    private void clearFieldsTransaksi() {
        tfJumlahBeli.setText("");
        tfTotalBayar.setText("");
    }

    // ============================================================
    // DIALOG HELPER
    // ============================================================
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============================================================
    // HELPER - TAMBAH ROW FORM
    // ============================================================
    private void addFormRow(JPanel card, String labelText, JComponent field) {
        JLabel lbl = makeLabel(labelText);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(6, 0, 2, 0));

        if (field instanceof JPanel) {
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        } else if (field instanceof JTextField) {
            ((JTextField) field).setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        } else if (field instanceof JComboBox) {
            ((JComboBox<?>) field).setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        card.add(lbl);
        card.add(field);
    }

    // ============================================================
    // HELPER - STYLE RADIO
    // ============================================================
    private void styleRadio(JRadioButton rb, Color color) {
        rb.setFont(FONT_LABEL);
        rb.setForeground(CLR_TEXT);
        rb.setBackground(Color.DARK_GRAY.darker());
        rb.setOpaque(false);
        rb.setFocusPainted(false);
    }

    // ============================================================
    // HELPER - STYLE COMBO BOX
    // ============================================================
    private void styleComboBox(JComboBox<?> cb) {
        cb.setFont(FONT_FIELD);
        cb.setBackground(CLR_INPUT_BG);
        cb.setForeground(CLR_TEXT);
        cb.setBorder(BorderFactory.createLineBorder(CLR_BORDER, 1, true));
    }

    // ============================================================
    // HELPER - STYLE SCROLL PANE
    // ============================================================
    private void styleScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createLineBorder(CLR_BORDER, 1, true));
        sp.getViewport().setBackground(CLR_SURFACE);
        sp.getVerticalScrollBar().setBackground(CLR_SURFACE);
        sp.getHorizontalScrollBar().setBackground(CLR_SURFACE);
    }

    // ============================================================
    // DATA CONTOH (Sample Data)
    // ============================================================
    private void tambahDataContoh() {
        // Barang contoh
        cAtasan a1 = new cAtasan("BRG001", "Kaos Polos Premium", 85000,  50, "Katun Combed");
        cAtasan a2 = new cAtasan("BRG002", "Kemeja Flanel",       175000, 30, "Flanel");
        cAtasan a3 = new cAtasan("BRG003", "Blouse Sifon",        220000, 25, "Sifon");
        cBawahan b1 = new cBawahan("BRG004", "Celana Jeans Slim",  350000, 20, 30);
        cBawahan b2 = new cBawahan("BRG005", "Rok Midi Plisket",   195000, 15, 28);
        ctrBarang = 6;

        for (cPakaian p : new cPakaian[]{a1, a2, a3, b1, b2}) {
            listBarang.tambahNode(p);
            modelTblBarang.addRow(new Object[]{
                p.getKodeBarang(), p.getNamaPakaian(), p.getJenisPakaian(),
                p.getDetailSpesifik(),
                "Rp " + FMT_RUPIAH.format(p.getHarga()), p.getStok()
            });
        }
        lblJumlahBarang.setText("Total: " + listBarang.getSize() + " item");

        // Pelanggan contoh
        cPelangan p1 = new cPelangan("PLG001", "Budi Santoso",     "08123456789");
        cPelangan p2 = new cPelangan("PLG002", "Siti Rahayu",      "08234567890");
        cPelangan p3 = new cPelangan("PLG003", "Ahmad Fauzi",      "08345678901");
        ctrPelangan = 4;

        for (cPelangan p : new cPelangan[]{p1, p2, p3}) {
            listPelangan.tambahNode(p);
            modelTblPelangan.addRow(new Object[]{
                p.getIdPelangan(), p.getNama(), p.getNoTelp()
            });
        }
        lblJumlahPelangan.setText("Total: " + listPelangan.getSize() + " pelanggan");

        System.out.println("=== DATA CONTOH DIMUAT ===");
        System.out.println("Barang:"); listBarang.cetak();
        System.out.println("Pelanggan:"); listPelangan.cetak();
    }

    // ============================================================
    // MAIN METHOD - Entry Point
    // ============================================================
    public static void main(String[] args) {
        // Gunakan tema modern FlatLaf jika tersedia, fallback ke Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // Override Nimbus dengan warna kustom
        UIManager.put("nimbusBase",                new Color(13,  17,  23));
        UIManager.put("nimbusBlueGrey",            new Color(22,  27,  34));
        UIManager.put("control",                   new Color(30,  37,  48));
        UIManager.put("text",                      new Color(230,237,243));
        UIManager.put("nimbusFocus",               new Color(88, 166,255));
        UIManager.put("nimbusLightBackground",     new Color(13,  17,  23));
        UIManager.put("OptionPane.background",     new Color(22,  27,  34));
        UIManager.put("Panel.background",          new Color(22,  27,  34));

        SwingUtilities.invokeLater(() -> new AppTokoPakaianVisual());
    }
}
