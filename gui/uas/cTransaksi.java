package uas;

/**
 * Class cTransaksi - Modul 18 & 19 (Asosiasi & Container)
 * Menerapkan relasi asosiasi antara Transaksi, Pelanggan, dan Pakaian.
 */
public class cTransaksi {

    // ============================================================
    // ATRIBUT - ENKAPSULASI (Modul 16)
    // ============================================================
    private String     noTransaksi;
    private cPelangan  pelangan;      // Asosiasi -> cPelangan
    private cPakaian   pakaian;       // Asosiasi -> cPakaian
    private int        jumlahBeli;
    private double     totalBayar;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cTransaksi() {}

    public cTransaksi(String noTransaksi, cPelangan pelangan, cPakaian pakaian,
                      int jumlahBeli) {
        this.noTransaksi = noTransaksi;
        this.pelangan    = pelangan;
        this.pakaian     = pakaian;
        this.jumlahBeli  = jumlahBeli;
        this.totalBayar  = pakaian.getHarga() * jumlahBeli;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setNoTransaksi(String noTransaksi) { this.noTransaksi = noTransaksi; }
    public void setPelangan(cPelangan pelangan)     { this.pelangan    = pelangan;    }
    public void setPakaian(cPakaian pakaian)        { this.pakaian     = pakaian;     }
    public void setJumlahBeli(int jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
        this.totalBayar = pakaian != null ? pakaian.getHarga() * jumlahBeli : 0;
    }

    // ============================================================
    // GETTER
    // ============================================================
    public String     getNoTransaksi() { return noTransaksi; }
    public cPelangan  getPelangan()    { return pelangan;    }
    public cPakaian   getPakaian()     { return pakaian;     }
    public int        getJumlahBeli()  { return jumlahBeli;  }
    public double     getTotalBayar()  { return totalBayar;  }

    @Override
    public String toString() {
        return String.format(
            "No: %s | Pelanggan: %s | Barang: %s | Qty: %d | Total: Rp%,.0f",
            noTransaksi,
            pelangan != null ? pelangan.getNama() : "-",
            pakaian  != null ? pakaian.getNamaPakaian() : "-",
            jumlahBeli,
            totalBayar
        );
    }
}
