// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: SampahAnorganik.java (Subclass)
// ============================================================

/**
 * Subclass SampahAnorganik mewarisi TempatSampah.
 * Menambahkan atribut spesifik jenisMaterial.
 */
public class SampahAnorganik extends TempatSampah {
    private String jenisMaterial; // Atribut tambahan (Contoh: Plastik, Kertas, Logam, Kaca)

    // Constructor memanggil constructor superclass menggunakan keyword 'super'
    public SampahAnorganik(String id, String lokasi, int kapasitas, String jenisMaterial) {
        super(id, lokasi, kapasitas);
        this.jenisMaterial = jenisMaterial;
    }

    public String getJenisMaterial() {
        return jenisMaterial;
    }

    public void setJenisMaterial(String jenisMaterial) {
        this.jenisMaterial = jenisMaterial;
    }

    // Polimorfisme: Meng-override method dari superclass
    @Override
    public String getTipe() {
        return "Anorganik";
    }

    @Override
    public String getDetailSpesifik() {
        return "Material: " + jenisMaterial;
    }
}
