package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class bangun_datar extends JFrame {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new bangun_datar().setVisible(true));
    }

    // Daftar nama bangun (termasuk separator untuk grouping)
    private static final String[] NAMA_BANGUN = {
            "--- BANGUN DATAR ---",
            "Persegi", "Persegi Panjang", "Segitiga", "Lingkaran",
            "Jajar Genjang", "Trapesium", "Layang-Layang", "Belah Ketupat",
            "Segi Lima", "Segi Enam",
            "--- BANGUN RUANG ---",
            "Kubus", "Balok", "Bola", "Tabung", "Kerucut",
            "Prisma Segitiga", "Limas Segiempat"
    };

    // Data untuk setiap bangun: {parameter, satuan, rumus, info}
    private final Object[][] DATA_BANGUN = {
            // 0: separator (tidak digunakan)
            null,
            // 1: Persegi
            new Object[] {
                    new String[] { "s = Sisi" },
                    new String[] { "satuan panjang" },
                    "Luas = s^2\nKeliling = 4 * s",
                    "Semua sisi sama panjang"
            },
            // 2: Persegi Panjang
            new Object[] {
                    new String[] { "p = Panjang", "l = Lebar" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Luas = p * l\nKeliling = 2 * (p + l)",
                    ""
            },
            // 3: Segitiga
            new Object[] {
                    new String[] { "a = Alas", "t = Tinggi", "s = Sisi Miring" },
                    new String[] { "satuan panjang", "satuan panjang", "satuan panjang" },
                    "Luas = 1/2 * a * t\nKeliling = a + t + s",
                    "Tinggi tegak lurus terhadap alas; sisi miring = sisi ketiga"
            },
            // 4: Lingkaran
            new Object[] {
                    new String[] { "r = Jari-jari" },
                    new String[] { "satuan panjang" },
                    "Luas = pi * r^2\nKeliling = 2 * pi * r",
                    "pi ~ 3.14159"
            },
            // 5: Jajar Genjang
            new Object[] {
                    new String[] { "a = Alas", "t = Tinggi" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Luas = a * t\nKeliling = 2 * (a + t)",
                    "Tinggi = jarak tegak lurus antara dua sisi sejajar"
            },
            // 6: Trapesium
            new Object[] {
                    new String[] { "a = Sisi Sejajar Atas", "b = Sisi Sejajar Bawah", "t = Tinggi" },
                    new String[] { "satuan panjang", "satuan panjang", "satuan panjang" },
                    "Luas = 1/2 * (a + b) * t\nKeliling = a + b + 2 * kaki",
                    "* Keliling dihitung dengan asumsi trapesium sama kaki"
            },
            // 7: Layang-Layang
            new Object[] {
                    new String[] { "d1 = Diagonal 1 (mendatar)", "d2 = Diagonal 2 (tegak)" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Luas = 1/2 * d1 * d2\nKeliling = 4 * sisi",
                    "* Asumsi layang-layang simetris (belah ketupat)"
            },
            // 8: Belah Ketupat
            new Object[] {
                    new String[] { "d1 = Diagonal 1", "d2 = Diagonal 2" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Luas = 1/2 * d1 * d2\nKeliling = 4 * s, s = 1/2*sqrt(d1^2+d2^2)",
                    "Semua sisi sama; diagonal saling tegak lurus"
            },
            // 9: Segi Lima
            new Object[] {
                    new String[] { "s = Sisi" },
                    new String[] { "satuan panjang" },
                    "Luas ~ 1.7205 * s^2\nKeliling = 5 * s",
                    "Rumus luas untuk segi lima beraturan"
            },
            // 10: Segi Enam
            new Object[] {
                    new String[] { "s = Sisi" },
                    new String[] { "satuan panjang" },
                    "Luas = (3*sqrt(3)/2) * s^2\nKeliling = 6 * s",
                    "Segi enam beraturan"
            },
            // 11: Kubus
            new Object[] {
                    new String[] { "s = Sisi" },
                    new String[] { "satuan panjang" },
                    "Volume = s^3\nLuas Permukaan = 6 * s^2",
                    ""
            },
            // 12: Balok
            new Object[] {
                    new String[] { "p = Panjang", "l = Lebar", "t = Tinggi" },
                    new String[] { "satuan panjang", "satuan panjang", "satuan panjang" },
                    "Volume = p * l * t\nLuas Permukaan = 2*(p*l + p*t + l*t)",
                    ""
            },
            // 13: Bola
            new Object[] {
                    new String[] { "r = Jari-jari" },
                    new String[] { "satuan panjang" },
                    "Volume = (4/3)*pi*r^3\nLuas Permukaan = 4*pi*r^2",
                    ""
            },
            // 14: Tabung
            new Object[] {
                    new String[] { "r = Jari-jari", "t = Tinggi" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Volume = pi*r^2*t\nLuas Permukaan = 2*pi*r(r+t)",
                    ""
            },
            // 15: Kerucut
            new Object[] {
                    new String[] { "r = Jari-jari", "t = Tinggi" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Volume = (1/3)*pi*r^2*t\nLuas Permukaan = pi*r(r+s), s = sqrt(r^2+t^2)",
                    "s = garis pelukis"
            },
            // 16: Prisma Segitiga
            new Object[] {
                    new String[] { "a = Alas Segitiga", "t = Tinggi Segitiga", "tp = Tinggi Prisma" },
                    new String[] { "satuan panjang", "satuan panjang", "satuan panjang" },
                    "Volume = Luas Alas * tp\nLuas Permukaan = 2*LA + Keliling Alas * tp",
                    "Alas segitiga siku-siku dengan tinggi t"
            },
            // 17: Limas Segiempat
            new Object[] {
                    new String[] { "a = Sisi Alas", "t = Tinggi Limas" },
                    new String[] { "satuan panjang", "satuan panjang" },
                    "Volume = (1/3) * a^2 * t\nLuas Permukaan = a^2 + 2*a * s, s = sqrt(t^2+(a/2)^2)",
                    "Alas persegi"
            }
    };

    // Komponen UI
    private JComboBox<String> pilihan;
    private JLabel lblGambarInfo;
    private JLabel[] lblInput = new JLabel[3];
    private JTextField[] tfInput = new JTextField[3];
    private JLabel[] lblSatuan = new JLabel[3];
    private JButton btnHitung, btnReset;
    private JTextArea taHasil;

    public bangun_datar() {
        setTitle("Bangun Datar & Ruang SUPER Lengkap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 540);
        setLocationRelativeTo(null);
        setResizable(false);
        buatKomponen();
        pilihan.setSelectedIndex(1); // Persegi
        updateTampilan(1);
    }

    private void buatKomponen() {
        JPanel panelUtama = new JPanel(new BorderLayout(8, 8));
        panelUtama.setBorder(new EmptyBorder(10, 12, 10, 12));
        panelUtama.setBackground(new Color(240, 245, 255));

        // Header
        JPanel panelHeader = new JPanel(new BorderLayout(4, 2));
        panelHeader.setOpaque(false);
        JLabel lblJudul = new JLabel("KALKULATOR BANGUN DATAR & RUANG", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 15));
        lblJudul.setForeground(new Color(25, 70, 160));
        lblGambarInfo = new JLabel("", SwingConstants.CENTER);
        lblGambarInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblGambarInfo.setForeground(new Color(100, 100, 150));
        panelHeader.add(lblJudul, BorderLayout.NORTH);
        panelHeader.add(lblGambarInfo, BorderLayout.SOUTH);

        // ComboBox pilihan
        pilihan = new JComboBox<>(NAMA_BANGUN);
        pilihan.setFont(new Font("Arial", Font.BOLD, 13));
        pilihan.setPreferredSize(new Dimension(280, 30));
        pilihan.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null && value.toString().startsWith("---")) {
                    setBackground(new Color(200, 200, 220));
                    setForeground(Color.DARK_GRAY);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setBackground(isSelected ? new Color(173, 214, 255) : Color.WHITE);
                    setForeground(Color.BLACK);
                }
                return this;
            }
        });

        // Panel input
        JPanel panelInput = new JPanel(new GridLayout(3, 3, 5, 5));
        panelInput.setBorder(new TitledBorder("Parameter Input"));
        panelInput.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            lblInput[i] = new JLabel("", SwingConstants.RIGHT);
            lblInput[i].setFont(new Font("Arial", Font.BOLD, 12));
            tfInput[i] = new JTextField();
            tfInput[i].setFont(new Font("Arial", Font.PLAIN, 12));
            lblSatuan[i] = new JLabel("", SwingConstants.LEFT);
            lblSatuan[i].setFont(new Font("Arial", Font.ITALIC, 11));
            lblSatuan[i].setForeground(new Color(120, 120, 120));
            panelInput.add(lblInput[i]);
            panelInput.add(tfInput[i]);
            panelInput.add(lblSatuan[i]);
        }

        // Tombol
        btnHitung = new JButton("HITUNG");
        btnHitung.setFont(new Font("Arial", Font.BOLD, 13));
        btnHitung.setBackground(new Color(30, 120, 200));
        btnHitung.setForeground(Color.BLACK);
        btnHitung.setFocusPainted(false);
        btnHitung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHitung.setPreferredSize(new Dimension(130, 34));

        btnReset = new JButton("RESET");
        btnReset.setFont(new Font("Arial", Font.BOLD, 13));
        btnReset.setBackground(new Color(200, 60, 60));
        btnReset.setForeground(Color.BLACK);
        btnReset.setFocusPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new Dimension(130, 34));

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        panelTombol.setOpaque(false);
        panelTombol.add(btnHitung);
        panelTombol.add(btnReset);

        // Area hasil
        taHasil = new JTextArea(8, 1);
        taHasil.setEditable(false);
        taHasil.setFont(new Font("Monospaced", Font.BOLD, 13));
        taHasil.setBackground(new Color(250, 252, 255));
        taHasil.setForeground(new Color(20, 20, 100));
        taHasil.setBorder(new EmptyBorder(6, 10, 6, 10));
        JScrollPane scrollHasil = new JScrollPane(taHasil);
        scrollHasil.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 230)),
                "Hasil Perhitungan", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 11), new Color(25, 70, 160)));

        // Susun layout
        JPanel panelAtas = new JPanel(new BorderLayout(5, 5));
        panelAtas.setOpaque(false);
        panelAtas.add(panelHeader, BorderLayout.NORTH);
        panelAtas.add(pilihan, BorderLayout.CENTER);

        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setOpaque(false);
        panelTengah.add(panelInput, BorderLayout.CENTER);
        panelTengah.add(panelTombol, BorderLayout.SOUTH);

        panelUtama.add(panelAtas, BorderLayout.NORTH);
        panelUtama.add(panelTengah, BorderLayout.CENTER);
        panelUtama.add(scrollHasil, BorderLayout.SOUTH);

        add(panelUtama);

        // Event handling
        pilihan.addActionListener(e -> {
            int idx = pilihan.getSelectedIndex();
            if (!NAMA_BANGUN[idx].startsWith("---")) {
                updateTampilan(idx);
            } else {
                // Jika separator dipilih, pindah ke item berikutnya
                SwingUtilities.invokeLater(() -> pilihan.setSelectedIndex(idx + 1));
            }
        });

        btnHitung.addActionListener(e -> hitung());
        btnReset.addActionListener(e -> resetForm());
    }

    private void updateTampilan(int idx) {
        Object[] data = DATA_BANGUN[idx];
        if (data == null)
            return;

        String[] param = (String[]) data[0];
        String[] satuan = (String[]) data[1];
        String rumus = (String) data[2];
        String info = (String) data[3];

        // Update info
        lblGambarInfo.setText(info.isEmpty() ? " " : info);

        // Update input fields
        for (int i = 0; i < 3; i++) {
            if (i < param.length) {
                lblInput[i].setText(param[i] + " :");
                tfInput[i].setEnabled(true);
                lblSatuan[i].setText(satuan[i]);
            } else {
                lblInput[i].setText("");
                tfInput[i].setEnabled(false);
                tfInput[i].setText("");
                lblSatuan[i].setText("");
            }
        }

        // Tampilkan rumus di area hasil sebagai hint
        taHasil.setText("RUMUS:\n" + rumus + "\n\nMasukkan nilai dan tekan HITUNG.");
    }

    private void resetForm() {
        for (JTextField tf : tfInput) {
            if (tf.isEnabled())
                tf.setText("");
        }
        taHasil.setText("");
    }

    private void hitung() {
        int idx = pilihan.getSelectedIndex();
        if (NAMA_BANGUN[idx].startsWith("---"))
            return;

        try {
            // Ambil nilai dari field yang aktif
            double a = 0, b = 0, c = 0;
            int count = 0;
            for (JTextField tf : tfInput) {
                if (tf.isEnabled())
                    count++;
            }
            if (count >= 1)
                a = Double.parseDouble(tfInput[0].getText().trim());
            if (count >= 2)
                b = Double.parseDouble(tfInput[1].getText().trim());
            if (count >= 3)
                c = Double.parseDouble(tfInput[2].getText().trim());

            StringBuilder sb = new StringBuilder();
            sb.append("BANGUN: ").append(NAMA_BANGUN[idx]).append("\n");
            sb.append("-------------------------------\n");

            switch (idx) {
                case 1: { // Persegi
                    validasiPositif(a, "Sisi");
                    double luas = a * a;
                    double keliling = 4 * a;
                    sb.append(String.format(" s = %.4f\n", a));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = s^2 = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 4*s = %.4f\n", keliling));
                    break;
                }
                case 2: { // Persegi Panjang
                    validasiPositif(a, "Panjang");
                    validasiPositif(b, "Lebar");
                    double luas = a * b;
                    double keliling = 2 * (a + b);
                    sb.append(String.format(" p = %.4f\n", a));
                    sb.append(String.format(" l = %.4f\n", b));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = p * l = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 2*(p+l) = %.4f\n", keliling));
                    break;
                }
                case 3: { // Segitiga
                    validasiPositif(a, "Alas");
                    validasiPositif(b, "Tinggi");
                    validasiPositif(c, "Sisi Miring");
                    double luas = 0.5 * a * b;
                    double keliling = a + b + c;
                    sb.append(String.format(" a (alas) = %.4f\n", a));
                    sb.append(String.format(" t (tinggi) = %.4f\n", b));
                    sb.append(String.format(" s (sisi miring) = %.4f\n", c));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = 1/2*a*t = %.4f\n", luas));
                    sb.append(String.format(" Keliling = a+t+s = %.4f\n", keliling));
                    break;
                }
                case 4: { // Lingkaran
                    validasiPositif(a, "Jari-jari");
                    double luas = Math.PI * a * a;
                    double keliling = 2 * Math.PI * a;
                    sb.append(String.format(" r = %.4f\n", a));
                    sb.append(String.format(" pi = %.5f\n", Math.PI));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = pi*r^2 = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 2*pi*r = %.4f\n", keliling));
                    break;
                }
                case 5: { // Jajar Genjang
                    validasiPositif(a, "Alas");
                    validasiPositif(b, "Tinggi");
                    double luas = a * b;
                    double keliling = 2 * (a + b);
                    sb.append(String.format(" a (alas) = %.4f\n", a));
                    sb.append(String.format(" t (tinggi) = %.4f\n", b));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = a * t = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 2*(a+t) = %.4f\n", keliling));
                    break;
                }
                case 6: { // Trapesium
                    validasiPositif(a, "Sisi Atas");
                    validasiPositif(b, "Sisi Bawah");
                    validasiPositif(c, "Tinggi");
                    double luas = 0.5 * (a + b) * c;
                    double kaki = Math.sqrt(c * c + Math.pow((b - a) / 2.0, 2));
                    double keliling = a + b + 2 * kaki;
                    sb.append(String.format(" a (sisi atas) = %.4f\n", a));
                    sb.append(String.format(" b (sisi bawah) = %.4f\n", b));
                    sb.append(String.format(" t (tinggi) = %.4f\n", c));
                    sb.append(String.format(" kaki (sama kaki) ≈ %.4f\n", kaki));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = 1/2*(a+b)*t = %.4f\n", luas));
                    sb.append(String.format(" Keliling = a+b+2*kaki ~ %.4f\n", keliling));
                    sb.append("* Asumsi trapesium sama kaki\n");
                    break;
                }
                case 7: { // Layang-Layang
                    validasiPositif(a, "Diagonal 1");
                    validasiPositif(b, "Diagonal 2");
                    double luas = 0.5 * a * b;
                    double sisi = Math.sqrt(Math.pow(a / 2, 2) + Math.pow(b / 2, 2));
                    double keliling = 4 * sisi;
                    sb.append(String.format(" d1 = %.4f\n", a));
                    sb.append(String.format(" d2 = %.4f\n", b));
                    sb.append(String.format(" sisi = sqrt((d1/2)^2+(d2/2)^2) = %.4f\n", sisi));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = 1/2*d1*d2 = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 4*sisi = %.4f\n", keliling));
                    sb.append("* Asumsi layang-layang simetris (belah ketupat)\n");
                    break;
                }
                case 8: { // Belah Ketupat
                    validasiPositif(a, "Diagonal 1");
                    validasiPositif(b, "Diagonal 2");
                    double luas = 0.5 * a * b;
                    double sisi = 0.5 * Math.sqrt(a * a + b * b);
                    double keliling = 4 * sisi;
                    sb.append(String.format(" d1 = %.4f\n", a));
                    sb.append(String.format(" d2 = %.4f\n", b));
                    sb.append(String.format(" s = 1/2*sqrt(d1^2+d2^2) = %.4f\n", sisi));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = 1/2*d1*d2 = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 4*s = %.4f\n", keliling));
                    break;
                }
                case 9: { // Segi Lima
                    validasiPositif(a, "Sisi");
                    double luas = 1.7205 * a * a;
                    double keliling = 5 * a;
                    sb.append(String.format(" s = %.4f\n", a));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas ~ 1.7205*s^2 = %.4f\n", luas));
                    sb.append(String.format(" Keliling = 5*s = %.4f\n", keliling));
                    break;
                }
                case 10: { // Segi Enam
                    validasiPositif(a, "Sisi");
                    double luas = (3 * Math.sqrt(3) / 2) * a * a;
                    double keliling = 6 * a;
                    sb.append(String.format(" s = %.4f\n", a));
                    sb.append(String.format(" 3*sqrt(3)/2 ~ %.5f\n", 3 * Math.sqrt(3) / 2));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Luas = (3*sqrt(3)/2)*s^2 ~ %.4f\n", luas));
                    sb.append(String.format(" Keliling = 6*s = %.4f\n", keliling));
                    break;
                }
                case 11: { // Kubus
                    validasiPositif(a, "Sisi");
                    double vol = a * a * a;
                    double lp = 6 * a * a;
                    sb.append(String.format(" s = %.4f\n", a));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = s^3 = %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = 6*s^2 = %.4f\n", lp));
                    break;
                }
                case 12: { // Balok
                    validasiPositif(a, "Panjang");
                    validasiPositif(b, "Lebar");
                    validasiPositif(c, "Tinggi");
                    double vol = a * b * c;
                    double lp = 2 * (a * b + a * c + b * c);
                    sb.append(String.format(" p = %.4f\n", a));
                    sb.append(String.format(" l = %.4f\n", b));
                    sb.append(String.format(" t = %.4f\n", c));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = p*l*t = %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = 2*(p*l+p*t+l*t) = %.4f\n", lp));
                    break;
                }
                case 13: { // Bola
                    validasiPositif(a, "Jari-jari");
                    double vol = (4.0 / 3) * Math.PI * Math.pow(a, 3);
                    double lp = 4 * Math.PI * a * a;
                    sb.append(String.format(" r = %.4f\n", a));
                    sb.append(String.format(" pi ~ %.5f\n", Math.PI));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = (4/3)*pi*r^3 ~ %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = 4*pi*r^2 ~ %.4f\n", lp));
                    break;
                }
                case 14: { // Tabung
                    validasiPositif(a, "Jari-jari");
                    validasiPositif(b, "Tinggi");
                    double vol = Math.PI * a * a * b;
                    double lp = 2 * Math.PI * a * (a + b);
                    sb.append(String.format(" r = %.4f\n", a));
                    sb.append(String.format(" t = %.4f\n", b));
                    sb.append(String.format(" pi ~ %.5f\n", Math.PI));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = pi*r^2*t ~ %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = 2*pi*r*(r+t) ~ %.4f\n", lp));
                    break;
                }
                case 15: { // Kerucut
                    validasiPositif(a, "Jari-jari");
                    validasiPositif(b, "Tinggi");
                    double s = Math.sqrt(a * a + b * b);
                    double vol = (1.0 / 3) * Math.PI * a * a * b;
                    double lp = Math.PI * a * (a + s);
                    sb.append(String.format(" r = %.4f\n", a));
                    sb.append(String.format(" t = %.4f\n", b));
                    sb.append(String.format(" s = sqrt(r^2+t^2) = %.4f (garis pelukis)\n", s));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = (1/3)*pi*r^2*t ~ %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = pi*r*(r+s) ~ %.4f\n", lp));
                    break;
                }
                case 16: { // Prisma Segitiga
                    validasiPositif(a, "Alas Segitiga");
                    validasiPositif(b, "Tinggi Segitiga");
                    validasiPositif(c, "Tinggi Prisma");
                    double luasAlas = 0.5 * a * b;
                    double sMiring = Math.sqrt(b * b + Math.pow(a / 2, 2));
                    double kelilingAlas = a + b + sMiring;
                    double vol = luasAlas * c;
                    double lp = 2 * luasAlas + kelilingAlas * c;
                    sb.append(String.format(" a (alas segitiga) = %.4f\n", a));
                    sb.append(String.format(" t (tinggi segitiga) = %.4f\n", b));
                    sb.append(String.format(" tp (tinggi prisma) = %.4f\n", c));
                    sb.append(String.format(" Luas Alas = %.4f\n", luasAlas));
                    sb.append(String.format(" Keliling Alas ~ %.4f\n", kelilingAlas));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = LuasAlas * tp = %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = 2*LA + K*tp ~ %.4f\n", lp));
                    break;
                }
                case 17: { // Limas Segiempat
                    validasiPositif(a, "Sisi Alas");
                    validasiPositif(b, "Tinggi Limas");
                    double s = Math.sqrt(b * b + Math.pow(a / 2, 2));
                    double vol = (1.0 / 3) * a * a * b;
                    double lp = a * a + 2 * a * s;
                    sb.append(String.format(" a (sisi alas) = %.4f\n", a));
                    sb.append(String.format(" t (tinggi) = %.4f\n", b));
                    sb.append(String.format(" s = sqrt(t^2+(a/2)^2) = %.4f (apotema)\n", s));
                    sb.append("-------------------------------\n");
                    sb.append(String.format(" Volume = (1/3)*a^2*t = %.4f\n", vol));
                    sb.append(String.format(" Luas Permukaan = a^2 + 2*a*s = %.4f\n", lp));
                    break;
                }
                default:
                    return;
            }
            taHasil.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Semua field yang aktif harus diisi dengan ANGKA!\nGunakan titik (.) untuk desimal.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Nilai Tidak Valid", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void validasiPositif(double nilai, String nama) {
        if (nilai <= 0) {
            throw new IllegalArgumentException(nama + " harus > 0!");
        }
    }
}