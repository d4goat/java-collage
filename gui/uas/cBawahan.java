package uas;

/**
 * Subclass cBawahan - Modul 20 (Inheritance)
 * Mewarisi cPakaian, menambahkan atribut: ukuranPinggang.
 */
public class cBawahan extends cPakaian {

    // ============================================================
    // ATRIBUT SPESIFIK - ENKAPSULASI (Modul 16)
    // ============================================================
    private int ukuranPinggang; // contoh: 28, 30, 32, 34

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cBawahan() { super(); }

    public cBawahan(String kodeBarang, String namaPakaian, double harga, int stok,
                    int ukuranPinggang) {
        super(kodeBarang, namaPakaian, harga, stok);
        this.ukuranPinggang = ukuranPinggang;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setUkuranPinggang(int ukuranPinggang) { this.ukuranPinggang = ukuranPinggang; }

    // ============================================================
    // GETTER
    // ============================================================
    public int getUkuranPinggang() { return ukuranPinggang; }

    @Override
    public String getJenisPakaian() { return "Bawahan"; }

    @Override
    public String getDetailSpesifik() { return "Pinggang: " + ukuranPinggang + " cm"; }

    @Override
    public String toString() {
        return super.toString() + " | Jenis: Bawahan | Pinggang: " + ukuranPinggang + " cm";
    }
}
