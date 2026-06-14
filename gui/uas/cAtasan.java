package uas;

/**
 * Subclass cAtasan - Modul 20 (Inheritance)
 * Mewarisi cPakaian, menambahkan atribut: jenisBahan.
 */
public class cAtasan extends cPakaian {

    // ============================================================
    // ATRIBUT SPESIFIK - ENKAPSULASI (Modul 16)
    // ============================================================
    private String jenisBahan; // contoh: Katun, Sifon, Polyester

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cAtasan() { super(); }

    public cAtasan(String kodeBarang, String namaPakaian, double harga, int stok,
                   String jenisBahan) {
        super(kodeBarang, namaPakaian, harga, stok);
        this.jenisBahan = jenisBahan;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setJenisBahan(String jenisBahan) { this.jenisBahan = jenisBahan; }

    // ============================================================
    // GETTER
    // ============================================================
    public String getJenisBahan() { return jenisBahan; }

    @Override
    public String getJenisPakaian() { return "Atasan"; }

    @Override
    public String getDetailSpesifik() { return "Bahan: " + jenisBahan; }

    @Override
    public String toString() {
        return super.toString() + " | Jenis: Atasan | Bahan: " + jenisBahan;
    }
}
