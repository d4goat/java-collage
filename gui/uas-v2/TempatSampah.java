// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: TempatSampah.java (Abstract Superclass)
// ============================================================

/**
 * Superclass abstrak TempatSampah.
 * Menerapkan konsep Inheritance (Pewarisan) dan Encapsulation (Enkapsulasi).
 */
public abstract class TempatSampah {
    // Enkapsulasi: Atribut diset private agar terlindungi, diakses lewat getter/setter
    private String id;
    private String lokasi;
    private int kapasitas; // Kapasitas dalam satuan Liter

    // Constructor untuk inisialisasi data tempat sampah
    public TempatSampah(String id, String lokasi, int kapasitas) {
        this.id = id;
        this.lokasi = lokasi;
        this.kapasitas = kapasitas;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }

    /**
     * Method abstrak yang Wajib di-override oleh subclass (Polimorfisme).
     */
    public abstract String getTipe();
    public abstract String getDetailSpesifik();

    @Override
    public String toString() {
        return "[" + id + "] Lokasi: " + lokasi + " (" + kapasitas + "L)";
    }
}
