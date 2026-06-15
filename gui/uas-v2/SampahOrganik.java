// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: SampahOrganik.java (Subclass)
// ============================================================

/**
 * Subclass SampahOrganik mewarisi TempatSampah.
 * Menambahkan atribut spesifik untuk sampah organik.
 */
public class SampahOrganik extends TempatSampah {
    private boolean bisaMembusuk; // Atribut tambahan penanda organik

    // Constructor memanggil constructor superclass menggunakan keyword 'super'
    public SampahOrganik(String id, String lokasi, int kapasitas, boolean bisaMembusuk) {
        super(id, lokasi, kapasitas);
        this.bisaMembusuk = bisaMembusuk;
    }

    public boolean isBisaMembusuk() {
        return bisaMembusuk;
    }

    public void setBisaMembusuk(boolean bisaMembusuk) {
        this.bisaMembusuk = bisaMembusuk;
    }

    // Polimorfisme: Meng-override method dari superclass
    @Override
    public String getTipe() {
        return "Organik";
    }

    @Override
    public String getDetailSpesifik() {
        return "Bisa Membusuk: " + (bisaMembusuk ? "Ya" : "Tidak");
    }
}
