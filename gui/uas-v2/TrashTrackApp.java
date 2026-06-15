// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: TrashTrackApp.java (Main Application & GUI)
// ============================================================

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Class Aplikasi TrashTrack.
 * Menghadirkan antarmuka desktop modern bertema eco-minimalis.
 */
public class TrashTrackApp extends JFrame {

    // Kumpulan Palet Warna Modern (Soft Palette)
    private static final Color COLOR_BG         = new Color(248, 250, 252); // Abu-abu sangat muda (Slate 50)
    private static final Color COLOR_CARD_BG    = new Color(255, 255, 255); // Putih bersih
    private static final Color COLOR_PRIMARY    = new Color(47, 133, 90);   // Hijau Eco (Emerald 700)
    private static final Color COLOR_PRIMARY_LITE= new Color(240, 253, 244); // Hijau Sangat Muda (Emerald 50)
    private static final Color COLOR_ACCENT     = new Color(56, 161, 105);  // Hijau Terang (Emerald 600)
    private static final Color COLOR_ACCENT_HOVER= new Color(39, 110, 75);  // Hijau Gelap untuk hover
    private static final Color COLOR_TEXT_MAIN  = new Color(45, 55, 72);    // Abu-abu Gelap (Slate 800)
    private static final Color COLOR_TEXT_MUTED = new Color(113, 128, 150); // Abu-abu Pudar (Slate 500)
    private static final Color COLOR_BORDER     = new Color(226, 232, 240); // Pembatas Lembut (Slate 200)
    private static final Color COLOR_DANGER     = new Color(229, 62, 62);   // Merah Aksen (Red 600)
    private static final Color COLOR_DANGER_HOVER= new Color(155, 44, 44);  // Merah Gelap untuk hover

    // Font Standard Modern
    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_SECTION  = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_BODY_BOLD= new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_BODY     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL    = new Font("Segoe UI", Font.PLAIN, 11);

    // Variabel Penunjang Aplikasi & Struktur Data
    private final LinkedListCustom antreanSampah = new LinkedListCustom();
    private int counterID = 1; // Auto increment ID Tempat Sampah

    // Komponen Input Swing
    private JTextField tfLokasi;
    private JSpinner spinKapasitas;
    private JComboBox<String> cbTipe;
    private JPanel panelDynamicInput;
    private CardLayout cardLayout;

    // Komponen Pendukung Input Spesifik
    private JCheckBox chkMembusuk;
    private JComboBox<String> cbMaterial;

    // Komponen Output Swing
    private JPanel panelQueueList;
    private JLabel lblTotalAntrean;
    private JLabel lblEmptyState;

    public TrashTrackApp() {
        super("TrashTrack - Sistem Antrean Tempat Sampah Pintar");
        initUI();
    }

    /**
     * Membangun seluruh antarmuka grafis (GUI) aplikasi.
     */
    private void initUI() {
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 0));

        // 1. HEADER BANNER (Informasi Utama & SDG 9)
        JPanel panelHeader = createHeaderPanel();
        add(panelHeader, BorderLayout.NORTH);

        // 2. MAIN LAYOUT (Split Form & List)
        JPanel panelMain = new JPanel(new GridLayout(1, 2, 20, 0));
        panelMain.setBackground(COLOR_BG);
        panelMain.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Sisi Kiri: Panel Form Input
        JPanel panelLeft = createFormPanel();
        panelMain.add(panelLeft);

        // Sisi Kanan: Panel Daftar Antrean (FIFO)
        JPanel panelRight = createQueuePanel();
        panelMain.add(panelRight);

        add(panelMain, BorderLayout.CENTER);

        // Update visual awal untuk mendeteksi kondisi kosong
        updateVisualAntrean();
    }

    /**
     * Membuat Panel Header atas bertema modern dengan info SDG 9.
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(COLOR_PRIMARY);
        panel.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Info Judul Aplikasi & SDG
        JPanel panelText = new JPanel(new GridLayout(2, 1, 4, 0));
        panelText.setOpaque(false);

        JLabel lblTitle = new JLabel("TrashTrack \u267B");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSdgInfo = new JLabel("Sistem Antrean Tempat Sampah Pintar | Mendukung SDG 9 (Industri, Inovasi & Infrastruktur Mikro)");
        lblSdgInfo.setFont(FONT_SUBTITLE);
        lblSdgInfo.setForeground(new Color(220, 252, 231)); // Soft light green

        panelText.add(lblTitle);
        panelText.add(lblSdgInfo);

        panel.add(panelText, BorderLayout.WEST);
        return panel;
    }

    /**
     * Membuat Form Input Data Tempat Sampah (Sisi Kiri).
     */
    private JPanel createFormPanel() {
        JPanel panelForm = new JPanel(new BorderLayout());
        panelForm.setBackground(COLOR_CARD_BG);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(24, 24, 24, 24)
        ));

        // Sub-judul Form
        JLabel lblHeader = new JLabel("Tambah Detektor Tempat Sampah Penuh");
        lblHeader.setFont(FONT_SECTION);
        lblHeader.setForeground(COLOR_TEXT_MAIN);
        lblHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelForm.add(lblHeader, BorderLayout.NORTH);

        // Isian Form (GridBagLayout agar rapi dan dinamis)
        JPanel panelFields = new JPanel(new GridBagLayout());
        panelFields.setBackground(COLOR_CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;

        // Baris 1: Lokasi
        gbc.gridy = 0;
        JLabel lblLokasi = new JLabel("Lokasi Pemasangan");
        lblLokasi.setFont(FONT_BODY_BOLD);
        lblLokasi.setForeground(COLOR_TEXT_MAIN);
        panelFields.add(lblLokasi, gbc);

        gbc.gridy = 1;
        tfLokasi = new JTextField();
        tfLokasi.setFont(FONT_BODY);
        tfLokasi.setPreferredSize(new Dimension(0, 38));
        // Membuat custom padding & border modern pada input text field
        tfLokasi.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        panelFields.add(tfLokasi, gbc);

        // Baris 2: Kapasitas (Liter)
        gbc.gridy = 2;
        JLabel lblKapasitas = new JLabel("Kapasitas Tempat Sampah (Liter)");
        lblKapasitas.setFont(FONT_BODY_BOLD);
        lblKapasitas.setForeground(COLOR_TEXT_MAIN);
        panelFields.add(lblKapasitas, gbc);

        gbc.gridy = 3;
        spinKapasitas = new JSpinner(new SpinnerNumberModel(50, 10, 500, 5));
        spinKapasitas.setFont(FONT_BODY);
        spinKapasitas.setPreferredSize(new Dimension(0, 38));
        // Styling JSpinner
        JComponent editor = spinKapasitas.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinKapasitas.setBorder(new LineBorder(COLOR_BORDER, 1, true));
        panelFields.add(spinKapasitas, gbc);

        // Baris 3: Tipe Sampah (Organik / Anorganik)
        gbc.gridy = 4;
        JLabel lblTipe = new JLabel("Tipe Klasifikasi Sampah");
        lblTipe.setFont(FONT_BODY_BOLD);
        lblTipe.setForeground(COLOR_TEXT_MAIN);
        panelFields.add(lblTipe, gbc);

        gbc.gridy = 5;
        cbTipe = new JComboBox<>(new String[]{"Organik", "Anorganik"});
        cbTipe.setFont(FONT_BODY);
        cbTipe.setPreferredSize(new Dimension(0, 38));
        cbTipe.setBackground(Color.WHITE);
        cbTipe.setBorder(new LineBorder(COLOR_BORDER, 1, true));
        panelFields.add(cbTipe, gbc);

        // Baris 4: Panel Input Dinamis (CardLayout)
        gbc.gridy = 6;
        cardLayout = new CardLayout();
        panelDynamicInput = new JPanel(cardLayout);
        panelDynamicInput.setBackground(COLOR_CARD_BG);
        panelDynamicInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, COLOR_BORDER),
                new EmptyBorder(12, 0, 12, 0)
        ));

        // Sub-Card 1: Organik Input
        JPanel cardOrganik = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cardOrganik.setBackground(COLOR_CARD_BG);
        chkMembusuk = new JCheckBox("Dapat Membusuk Alami (Bisa dijadikan kompos)");
        chkMembusuk.setFont(FONT_BODY);
        chkMembusuk.setForeground(COLOR_TEXT_MAIN);
        chkMembusuk.setOpaque(false);
        cardOrganik.add(chkMembusuk);

        // Sub-Card 2: Anorganik Input
        JPanel cardAnorganik = new JPanel(new BorderLayout(5, 5));
        cardAnorganik.setBackground(COLOR_CARD_BG);
        JLabel lblMaterial = new JLabel("Jenis Material Utama");
        lblMaterial.setFont(FONT_BODY_BOLD);
        lblMaterial.setForeground(COLOR_TEXT_MAIN);
        cbMaterial = new JComboBox<>(new String[]{"Plastik", "Kertas", "Logam", "Kaca", "Karet"});
        cbMaterial.setFont(FONT_BODY);
        cbMaterial.setPreferredSize(new Dimension(0, 35));
        cbMaterial.setBackground(Color.WHITE);
        cbMaterial.setBorder(new LineBorder(COLOR_BORDER, 1, true));
        cardAnorganik.add(lblMaterial, BorderLayout.NORTH);
        cardAnorganik.add(cbMaterial, BorderLayout.CENTER);

        panelDynamicInput.add(cardOrganik, "Organik");
        panelDynamicInput.add(cardAnorganik, "Anorganik");
        panelFields.add(panelDynamicInput, gbc);

        panelForm.add(panelFields, BorderLayout.CENTER);

        // Event Handling Combobox untuk berganti card/jenis input
        cbTipe.addActionListener(e -> {
            String selected = (String) cbTipe.getSelectedItem();
            cardLayout.show(panelDynamicInput, selected);
        });

        // Baris Akhir: Tombol Tambah Antrean
        JPanel panelBtn = new JPanel(new BorderLayout());
        panelBtn.setBackground(COLOR_CARD_BG);
        panelBtn.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Membuat Custom Rounded Button
        RoundedButton btnAdd = new RoundedButton("Masukkan Ke Antrean Penuh", COLOR_PRIMARY, COLOR_ACCENT, COLOR_ACCENT_HOVER);
        btnAdd.setPreferredSize(new Dimension(0, 45));
        
        // Event Handling: Proses Klik Tambah data ke Linked List Custom
        btnAdd.addActionListener(e -> actionTambahAntrean());

        panelBtn.add(btnAdd, BorderLayout.CENTER);
        panelForm.add(panelBtn, BorderLayout.SOUTH);

        return panelForm;
    }

    /**
     * Membuat Panel Visualisasi Daftar Antrean Tempat Sampah (Sisi Kanan).
     */
    private JPanel createQueuePanel() {
        JPanel panelQueue = new JPanel(new BorderLayout());
        panelQueue.setBackground(COLOR_CARD_BG);
        panelQueue.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(24, 24, 24, 24)
        ));

        // Sub-Header List: Judul dan Jumlah Antrean Aktif
        JPanel panelListHeader = new JPanel(new BorderLayout());
        panelListHeader.setBackground(COLOR_CARD_BG);
        panelListHeader.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblTitle = new JLabel("Daftar Antrean Angkutan");
        lblTitle.setFont(FONT_SECTION);
        lblTitle.setForeground(COLOR_TEXT_MAIN);

        lblTotalAntrean = new JLabel("0 Tempat Sampah");
        lblTotalAntrean.setFont(FONT_BODY_BOLD);
        lblTotalAntrean.setForeground(COLOR_PRIMARY);

        panelListHeader.add(lblTitle, BorderLayout.WEST);
        panelListHeader.add(lblTotalAntrean, BorderLayout.EAST);
        panelQueue.add(panelListHeader, BorderLayout.NORTH);

        // ScrollPane pembungkus list
        panelQueueList = new JPanel();
        panelQueueList.setLayout(new BoxLayout(panelQueueList, BoxLayout.Y_AXIS));
        panelQueueList.setBackground(COLOR_CARD_BG);

        JScrollPane scrollPane = new JScrollPane(panelQueueList);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(COLOR_CARD_BG);
        panelQueue.add(scrollPane, BorderLayout.CENTER);

        // Label Empty State (Ditampilkan saat tidak ada antrean)
        lblEmptyState = new JLabel("Tidak ada antrean. Semua tempat sampah dalam kondisi aman.", SwingConstants.CENTER);
        lblEmptyState.setFont(FONT_BODY);
        lblEmptyState.setForeground(COLOR_TEXT_MUTED);
        lblEmptyState.setBorder(new EmptyBorder(40, 20, 40, 20));

        // Tombol Angkut Sampah Terdepan (FIFO)
        JPanel panelAction = new JPanel(new BorderLayout());
        panelAction.setBackground(COLOR_CARD_BG);
        panelAction.setBorder(new EmptyBorder(15, 0, 0, 0));

        RoundedButton btnRemove = new RoundedButton("Angkut & Kosongkan Sampah Terdepan (FIFO)", COLOR_DANGER, COLOR_DANGER.brighter(), COLOR_DANGER_HOVER);
        btnRemove.setPreferredSize(new Dimension(0, 45));
        
        // Event Handling: Hapus elemen terdepan dari Linked List Custom (FIFO)
        btnRemove.addActionListener(e -> actionAngkutSampah());

        panelAction.add(btnRemove, BorderLayout.CENTER);
        panelQueue.add(panelAction, BorderLayout.SOUTH);

        return panelQueue;
    }

    /**
     * Logika Bisnis: Menambah data Tempat Sampah ke Antrean (LinkedList Tail).
     */
    private void actionTambahAntrean() {
        String lokasi = tfLokasi.getText().trim();
        if (lokasi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lokasi tidak boleh kosong!", "Input Salah", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int kapasitas = (int) spinKapasitas.getValue();
        String tipe = (String) cbTipe.getSelectedItem();
        TempatSampah dataBaru;

        // ID Generator otomatis (Contoh: BIN-001, BIN-002, dst)
        String generatedID = String.format("BIN-%03d", counterID++);

        // Menerapkan konsep Polymorphism untuk pembuatan subclass objek
        if ("Organik".equals(tipe)) {
            boolean bisaMembusuk = chkMembusuk.isSelected();
            dataBaru = new SampahOrganik(generatedID, lokasi, kapasitas, bisaMembusuk);
        } else {
            String material = (String) cbMaterial.getSelectedItem();
            dataBaru = new SampahAnorganik(generatedID, lokasi, kapasitas, material);
        }

        // Panggil method enqueue milik Linked List kustom (Memasukkan ke Ekor/Tail)
        antreanSampah.enqueue(dataBaru);

        // Bersihkan Form
        tfLokasi.setText("");
        chkMembusuk.setSelected(false);
        cbMaterial.setSelectedIndex(0);

        // Perbarui visualisasi tampilan list queue
        updateVisualAntrean();
    }

    /**
     * Logika Bisnis: Mengambil dan membersihkan sampah terdepan (LinkedList Head / FIFO).
     */
    private void actionAngkutSampah() {
        if (antreanSampah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada antrean tempat sampah saat ini.", "Antrean Kosong", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Panggil method dequeue dari Linked List kustom (Menghapus dari Kepala/Head)
        TempatSampah terangkut = antreanSampah.dequeue();

        if (terangkut != null) {
            JOptionPane.showMessageDialog(this,
                    "Petugas telah membersihkan & mengosongkan:\n" +
                    "ID: " + terangkut.getId() + "\n" +
                    "Lokasi: " + terangkut.getLokasi() + "\n" +
                    "Kategori: " + terangkut.getTipe() + "\n\n" +
                    "Status: Sukses Dikosongkan!",
                    "Sampah Diangkut",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // Perbarui visualisasi tampilan list queue
        updateVisualAntrean();
    }

    /**
     * Memperbarui visualisasi daftar antrean tempat sampah di layar.
     * Melakukan iterasi manual menyusuri Linked List dari Head hingga Tail.
     */
    private void updateVisualAntrean() {
        // Reset kontainer list
        panelQueueList.removeAll();

        int size = antreanSampah.getSize();
        lblTotalAntrean.setText(size + " Tempat Sampah");

        if (antreanSampah.isEmpty()) {
            panelQueueList.setLayout(new BorderLayout());
            panelQueueList.add(lblEmptyState, BorderLayout.CENTER);
        } else {
            panelQueueList.setLayout(new BoxLayout(panelQueueList, BoxLayout.Y_AXIS));

            // Iterasi manual menyusuri Linked List mulai dari Head
            Node current = antreanSampah.getHead();
            int index = 1;
            while (current != null) {
                TempatSampah bin = current.data;
                JPanel card = createVisualCard(bin, index);
                panelQueueList.add(card);
                
                // Tambahkan jarak spasi antar card agar legang & bernafas
                panelQueueList.add(Box.createRigidArea(new Dimension(0, 10)));
                
                current = current.next; // Bergeser ke node berikutnya
                index++;
            }
        }

        // Refresh rendering GUI Swing
        panelQueueList.revalidate();
        panelQueueList.repaint();
    }

    /**
     * Membuat card GUI individu yang mewakili satu node antrean tempat sampah.
     */
    private JPanel createVisualCard(TempatSampah bin, int urutan) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(COLOR_CARD_BG);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));
        card.setPreferredSize(new Dimension(350, 85));
        
        // Border card tipis dengan padding lega
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));

        // 1. Ikon & Urutan (Kiri)
        JPanel panelBadge = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 8));
        panelBadge.setOpaque(false);
        
        JLabel lblUrutan = new JLabel("#" + urutan);
        lblUrutan.setFont(FONT_BODY_BOLD);
        lblUrutan.setForeground(COLOR_PRIMARY);

        JLabel lblIcon = new JLabel("Organik".equals(bin.getTipe()) ? "\uD83C\uDF42" : "\u267B");
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 22));

        panelBadge.add(lblUrutan);
        panelBadge.add(lblIcon);
        card.add(panelBadge, BorderLayout.WEST);

        // 2. Info Detail (Tengah)
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Lokasi
        gbc.gridy = 0;
        JLabel lblLoc = new JLabel(bin.getLokasi() + " (" + bin.getKapasitas() + " Liter)");
        lblLoc.setFont(FONT_BODY_BOLD);
        lblLoc.setForeground(COLOR_TEXT_MAIN);
        panelInfo.add(lblLoc, gbc);

        // ID dan Tipe Sampah
        gbc.gridy = 1;
        JLabel lblDetails = new JLabel("ID: " + bin.getId() + "  |  Tipe: " + bin.getTipe());
        lblDetails.setFont(FONT_SMALL);
        lblDetails.setForeground(COLOR_TEXT_MUTED);
        panelInfo.add(lblDetails, gbc);

        // Detail Spesifik Subclass (Polimorfisme)
        gbc.gridy = 2;
        JLabel lblSpecific = new JLabel(bin.getDetailSpesifik());
        lblSpecific.setFont(FONT_SMALL);
        lblSpecific.setForeground(COLOR_PRIMARY);
        panelInfo.add(lblSpecific, gbc);

        card.add(panelInfo, BorderLayout.CENTER);

        // 3. Status Tag (Kanan)
        JPanel panelStatus = new JPanel(new GridBagLayout());
        panelStatus.setOpaque(false);
        
        JLabel lblStatusTag = new JLabel(urutan == 1 ? "TERDEPAN" : "MENUNGGU");
        lblStatusTag.setFont(FONT_SMALL);
        lblStatusTag.setOpaque(true);
        
        // Pilihan warna tag berdasarkan urutan antrean
        if (urutan == 1) {
            lblStatusTag.setBackground(COLOR_DANGER);
            lblStatusTag.setForeground(Color.WHITE);
            lblStatusTag.setBorder(new CompoundBorder(new LineBorder(COLOR_DANGER), new EmptyBorder(3, 8, 3, 8)));
        } else {
            lblStatusTag.setBackground(COLOR_PRIMARY_LITE);
            lblStatusTag.setForeground(COLOR_PRIMARY);
            lblStatusTag.setBorder(new CompoundBorder(new LineBorder(COLOR_PRIMARY_LITE), new EmptyBorder(3, 8, 3, 8)));
        }
        
        panelStatus.add(lblStatusTag);
        card.add(panelStatus, BorderLayout.EAST);

        return card;
    }

    /**
     * Main method untuk menjalankan aplikasi desktop TrashTrack.
     */
    public static void main(String[] args) {
        // Atur look and feel sistem agar tidak kaku
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Jalankan aplikasi di Thread Event Dispatching Swing
        SwingUtilities.invokeLater(() -> {
            new TrashTrackApp().setVisible(true);
        });
    }

    /**
     * Custom Helper Class: RoundedButton.
     * Tombol modern dengan sudut melingkar (rounded corner) dan warna interaktif.
     */
    private static class RoundedButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private final Color clickColor;

        public RoundedButton(String text, Color baseColor, Color hoverColor, Color clickColor) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.clickColor = clickColor;

            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(FONT_BODY_BOLD);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Tambahkan event hover & press untuk menirukan interaksi modern
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(baseColor);
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(clickColor);
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }
            });
            setBackground(baseColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Aktifkan antialiasing untuk rendering yang halus
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.dispose();

            super.paintComponent(g);
        }
    }
}
