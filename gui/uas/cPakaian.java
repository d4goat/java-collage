package uas;

/**
 * Superclass cPakaian - Modul 20 (Inheritance)
 * Menyimpan atribut umum untuk semua jenis pakaian.
 */
public class cPakaian {

    // ============================================================
    // ATRIBUT - ENKAPSULASI (Modul 16)
    // ============================================================
    private String kodeBarang;
    private String namaPakaian;
    private double harga;
    private int    stok;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cPakaian() {}

    public cPakaian(String kodeBarang, String namaPakaian, double harga, int stok) {
        this.kodeBarang   = kodeBarang;
        this.namaPakaian  = namaPakaian;
        this.harga        = harga;
        this.stok         = stok;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setKodeBarang(String kodeBarang)   { this.kodeBarang  = kodeBarang;  }
    public void setNamaPakaian(String namaPakaian) { this.namaPakaian = namaPakaian; }
    public void setHarga(double harga)             { this.harga       = harga;       }
    public void setStok(int stok)                  { this.stok        = stok;        }

    // ============================================================
    // GETTER
    // ============================================================
    public String getKodeBarang()  { return kodeBarang;  }
    public String getNamaPakaian() { return namaPakaian; }
    public double getHarga()       { return harga;       }
    public int    getStok()        { return stok;        }

    /**
     * Method untuk mendapatkan jenis pakaian (di-override subclass).
     */
    public String getJenisPakaian() { return "Pakaian"; }

    /**
     * Mengembalikan detail spesifik subclass (di-override subclass).
     */
    public String getDetailSpesifik() { return "-"; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Harga: Rp%,.0f | Stok: %d",
                kodeBarang, namaPakaian, harga, stok);
    }
}
