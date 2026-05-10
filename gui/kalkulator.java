package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class kalkulator extends JFrame {

    // ─── Warna tema ───────────────────────────────────────────────────────────
    private static final Color BG_DARK = new Color(18, 18, 24);
    private static final Color BG_DISPLAY = new Color(12, 12, 18);
    private static final Color BTN_DIGIT = new Color(38, 38, 52);
    private static final Color BTN_DIGIT_HOV = new Color(52, 52, 70);
    private static final Color BTN_OP = new Color(90, 60, 180);
    private static final Color BTN_OP_HOV = new Color(110, 80, 210);
    private static final Color BTN_FUNC = new Color(30, 30, 44);
    private static final Color BTN_FUNC_HOV = new Color(44, 44, 62);
    private static final Color BTN_CLEAR = new Color(180, 50, 70);
    private static final Color BTN_CLEAR_HOV = new Color(210, 65, 85);
    private static final Color BTN_EQ = new Color(120, 80, 230);
    private static final Color BTN_EQ_HOV = new Color(150, 110, 255);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 255);
    private static final Color TEXT_DIM = new Color(140, 135, 170);

    // ─── State ────────────────────────────────────────────────────────────────
    private double nilaiPertama = 0;
    private double nilaiKedua = 0;
    private String operatorAktif = "";
    private boolean inputBaru = true;
    private boolean hasilDitampilkan = false;

    // ─── Komponen UI ──────────────────────────────────────────────────────────
    private JLabel lblEkspresi;
    private JLabel lblDisplay;
    private JTextArea taRiwayat;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new kalkulator().setVisible(true));
    }

    // ─── Konstruktor ──────────────────────────────────────────────────────────
    public kalkulator() {
        setTitle("Kalkulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        buatKomponen();
    }

    // ─── Bangun UI ────────────────────────────────────────────────────────────
    private void buatKomponen() {
        JPanel root = new JPanel(new BorderLayout(0, 8));
        root.setBackground(BG_DARK);
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        // ══════════════════════════════════════════════════════════════════════
        // DISPLAY
        // ══════════════════════════════════════════════════════════════════════
        JPanel panelDisplay = new JPanel(new BorderLayout(0, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_DISPLAY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(60, 50, 100));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        panelDisplay.setOpaque(false);
        panelDisplay.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        panelDisplay.setPreferredSize(new Dimension(0, 110));

        lblEkspresi = new JLabel(" ", SwingConstants.RIGHT);
        lblEkspresi.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEkspresi.setForeground(TEXT_DIM);

        lblDisplay = new JLabel("0", SwingConstants.RIGHT);
        lblDisplay.setFont(new Font("SansSerif", Font.BOLD, 44));
        lblDisplay.setForeground(TEXT_PRIMARY);

        panelDisplay.add(lblEkspresi, BorderLayout.NORTH);
        panelDisplay.add(lblDisplay, BorderLayout.SOUTH);

        // ══════════════════════════════════════════════════════════════════════
        // RIWAYAT — tinggi tetap, tidak ada overflow
        // ══════════════════════════════════════════════════════════════════════
        taRiwayat = new JTextArea(3, 1);
        taRiwayat.setEditable(false);
        taRiwayat.setFont(new Font("Monospaced", Font.PLAIN, 11));
        taRiwayat.setBackground(new Color(22, 22, 32));
        taRiwayat.setForeground(TEXT_DIM);
        taRiwayat.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        taRiwayat.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(taRiwayat,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBackground(new Color(22, 22, 32));
        scroll.getViewport().setBackground(new Color(22, 22, 32));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 70), 1));

        // Panel riwayat dengan label eksplisit
        JLabel lblRiwayat = new JLabel("Riwayat");
        lblRiwayat.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblRiwayat.setForeground(TEXT_DIM);
        lblRiwayat.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 0));

        JPanel panelRiwayat = new JPanel(new BorderLayout(0, 2));
        panelRiwayat.setOpaque(false);
        panelRiwayat.setPreferredSize(new Dimension(0, 88));
        panelRiwayat.add(lblRiwayat, BorderLayout.NORTH);
        panelRiwayat.add(scroll, BorderLayout.CENTER);

        // ══════════════════════════════════════════════════════════════════════
        // TOMBOL — urutan standar kalkulator:
        //
        // Baris 0: sqrt x^2 1/x Hist
        // Baris 1: C Del % /
        // Baris 2: 7 8 9 *
        // Baris 3: 4 5 6 -
        // Baris 4: 1 2 3 +
        // Baris 5: +/- 0 . =
        // ══════════════════════════════════════════════════════════════════════
        Object[][] defs = {
                { "sqrt", "F" }, { "x^2", "F" }, { "1/x", "F" }, { "Hist", "F" },
                { "C", "C" }, { "Del", "F" }, { "%", "F" }, { "/", "O" },
                { "7", "D" }, { "8", "D" }, { "9", "D" }, { "*", "O" },
                { "4", "D" }, { "5", "D" }, { "6", "D" }, { "-", "O" },
                { "1", "D" }, { "2", "D" }, { "3", "D" }, { "+", "O" },
                { "+/-", "F" }, { "0", "D" }, { ".", "D" }, { "=", "E" },
        };

        JPanel panelTombol = new JPanel(new GridLayout(6, 4, 8, 8));
        panelTombol.setOpaque(false);
        for (Object[] def : defs) {
            panelTombol.add(buatTombol((String) def[0], (String) def[1]));
        }

        // ══════════════════════════════════════════════════════════════════════
        // SUSUN LAYOUT
        // ══════════════════════════════════════════════════════════════════════
        JPanel topArea = new JPanel(new BorderLayout(0, 8));
        topArea.setOpaque(false);
        topArea.add(panelDisplay, BorderLayout.NORTH);
        topArea.add(panelRiwayat, BorderLayout.SOUTH);

        root.add(topArea, BorderLayout.NORTH);
        root.add(panelTombol, BorderLayout.CENTER);

        setContentPane(root);
        getContentPane().setBackground(BG_DARK);
    }

    // ─── Buat tombol ──────────────────────────────────────────────────────────
    private JButton buatTombol(String label, String tipe) {
        Color normal, hover;
        switch (tipe) {
            case "O":
                normal = BTN_OP;
                hover = BTN_OP_HOV;
                break;
            case "C":
                normal = BTN_CLEAR;
                hover = BTN_CLEAR_HOV;
                break;
            case "E":
                normal = BTN_EQ;
                hover = BTN_EQ_HOV;
                break;
            case "F":
                normal = BTN_FUNC;
                hover = BTN_FUNC_HOV;
                break;
            default:
                normal = BTN_DIGIT;
                hover = BTN_DIGIT_HOV;
                break;
        }

        JButton btn = new JButton(label) {
            private boolean hovered = false;
            private boolean pressed = false;

            {
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setOpaque(false);
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = true;
                        repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        pressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                float s = pressed ? 0.92f : 1f;
                int sw = (int) (w * s), sh = (int) (h * s);
                int ox = (w - sw) / 2, oy = (h - sh) / 2;

                g2.setColor(hovered ? hover : normal);
                g2.fillRoundRect(ox, oy, sw, sh, 12, 12);

                // Highlight tipis di bagian atas
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRoundRect(ox, oy, sw, sh / 2, 12, 12);

                g2.setFont(getFont());
                g2.setColor(TEXT_PRIMARY);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (w - fm.stringWidth(getText())) / 2;
                int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };

        int fs = label.length() > 2 ? 13 : 18;
        btn.setFont(new Font("SansSerif", Font.BOLD, fs));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this::tombolDitekan);
        return btn;
    }

    // ─── Handler utama ────────────────────────────────────────────────────────
    private void tombolDitekan(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();

        if (cmd.matches("[0-9]")) {
            inputAngka(cmd);
        } else if (cmd.equals(".")) {
            inputDesimal();
        } else if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
            setOperator(cmd);
        } else if (cmd.equals("=")) {
            hitungHasil();
        } else {
            switch (cmd) {
                case "C":
                    reset();
                    break;
                case "Del":
                    hapusKarakter();
                    break;
                case "+/-":
                    ubahTanda();
                    break;
                case "%":
                    persen();
                    break;
                case "sqrt":
                    akarKuadrat();
                    break;
                case "x^2":
                    pangkatDua();
                    break;
                case "1/x":
                    balikan();
                    break;
                case "Hist":
                    tampilkanRiwayat();
                    break;
            }
        }
    }

    // ─── Logika input ─────────────────────────────────────────────────────────
    private void inputAngka(String angka) {
        if (inputBaru || hasilDitampilkan) {
            setDisplay(angka);
            inputBaru = false;
            hasilDitampilkan = false;
        } else {
            String s = lblDisplay.getText();
            setDisplay(s.equals("0") ? angka : s + angka);
        }
    }

    private void inputDesimal() {
        if (inputBaru) {
            setDisplay("0.");
            inputBaru = false;
        } else if (!lblDisplay.getText().contains(".")) {
            setDisplay(lblDisplay.getText() + ".");
        }
    }

    private void setOperator(String op) {
        nilaiPertama = getNilaiDisplay();
        operatorAktif = op;
        inputBaru = true;
        lblEkspresi.setText(formatAngka(nilaiPertama) + "  " + opLabel(op));
    }

    private void hitungHasil() {
        if (operatorAktif.isEmpty())
            return;

        nilaiKedua = getNilaiDisplay();
        double hasil;
        String ekspresi = formatAngka(nilaiPertama) + " " + opLabel(operatorAktif)
                + " " + formatAngka(nilaiKedua) + " =";

        switch (operatorAktif) {
            case "+":
                hasil = nilaiPertama + nilaiKedua;
                break;
            case "-":
                hasil = nilaiPertama - nilaiKedua;
                break;
            case "*":
                hasil = nilaiPertama * nilaiKedua;
                break;
            case "/":
                if (nilaiKedua == 0) {
                    tampilError("Tidak bisa dibagi nol!");
                    return;
                }
                hasil = nilaiPertama / nilaiKedua;
                break;
            default:
                return;
        }

        ekspresi += " " + formatAngka(hasil);
        tambahRiwayat(ekspresi);
        tampilHasil(hasil);
        lblEkspresi.setText(ekspresi);
        operatorAktif = "";
        hasilDitampilkan = true;
    }

    // ─── Fungsi tambahan ──────────────────────────────────────────────────────
    private void reset() {
        nilaiPertama = nilaiKedua = 0;
        operatorAktif = "";
        inputBaru = true;
        hasilDitampilkan = false;
        setDisplay("0");
        lblEkspresi.setText(" ");
    }

    private void hapusKarakter() {
        String s = lblDisplay.getText();
        setDisplay(s.length() > 1 ? s.substring(0, s.length() - 1) : "0");
        inputBaru = false;
    }

    private void ubahTanda() {
        tampilHasil(-getNilaiDisplay());
    }

    private void persen() {
        tampilHasil(getNilaiDisplay() / 100.0);
    }

    private void akarKuadrat() {
        double v = getNilaiDisplay();
        if (v < 0) {
            tampilError("Tidak bisa akar dari bilangan negatif!");
            return;
        }
        double h = Math.sqrt(v);
        tambahRiwayat("sqrt(" + formatAngka(v) + ") = " + formatAngka(h));
        tampilHasil(h);
    }

    private void pangkatDua() {
        double v = getNilaiDisplay(), h = v * v;
        tambahRiwayat(formatAngka(v) + "^2 = " + formatAngka(h));
        tampilHasil(h);
    }

    private void balikan() {
        double v = getNilaiDisplay();
        if (v == 0) {
            tampilError("Tidak bisa 1/0!");
            return;
        }
        double h = 1.0 / v;
        tambahRiwayat("1/" + formatAngka(v) + " = " + formatAngka(h));
        tampilHasil(h);
    }

    private void tampilkanRiwayat() {
        JTextArea ta = new JTextArea(taRiwayat.getText(), 12, 28);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(ta),
                "Riwayat Perhitungan", JOptionPane.INFORMATION_MESSAGE);
    }

    // ─── Utilitas ─────────────────────────────────────────────────────────────
    private double getNilaiDisplay() {
        try {
            return Double.parseDouble(lblDisplay.getText());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void setDisplay(String teks) {
        int fs = teks.length() > 14 ? 22 : teks.length() > 9 ? 32 : 44;
        lblDisplay.setFont(new Font("SansSerif", Font.BOLD, fs));
        lblDisplay.setText(teks);
    }

    private void tampilHasil(double nilai) {
        setDisplay(nilai == (long) nilai
                ? String.valueOf((long) nilai)
                : new DecimalFormat("#.##########").format(nilai));
        inputBaru = true;
    }

    private String formatAngka(double nilai) {
        return nilai == (long) nilai
                ? String.valueOf((long) nilai)
                : new DecimalFormat("#.##########").format(nilai);
    }

    private String opLabel(String op) {
        switch (op) {
            case "*":
                return "*";
            case "/":
                return "/";
            case "-":
                return "-";
            default:
                return op;
        }
    }

    private void tambahRiwayat(String teks) {
        taRiwayat.append(teks + "\n");
        taRiwayat.setCaretPosition(taRiwayat.getDocument().getLength());
    }

    private void tampilError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Error", JOptionPane.ERROR_MESSAGE);
    }

}