package uas;

/**
 * Class cPelangan - Modul 18 & 19 (Asosiasi & Container)
 * Menyimpan data pelanggan toko.
 */
public class cPelangan {

    // ============================================================
    // ATRIBUT - ENKAPSULASI (Modul 16)
    // ============================================================
    private String idPelangan;
    private String nama;
    private String noTelp;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cPelangan() {}

    public cPelangan(String idPelangan, String nama, String noTelp) {
        this.idPelangan = idPelangan;
        this.nama       = nama;
        this.noTelp     = noTelp;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setIdPelangan(String idPelangan) { this.idPelangan = idPelangan; }
    public void setNama(String nama)             { this.nama       = nama;       }
    public void setNoTelp(String noTelp)         { this.noTelp     = noTelp;     }

    // ============================================================
    // GETTER
    // ============================================================
    public String getIdPelangan() { return idPelangan; }
    public String getNama()       { return nama;       }
    public String getNoTelp()     { return noTelp;     }

    @Override
    public String toString() {
        return String.format("[%s] %s | Telp: %s", idPelangan, nama, noTelp);
    }
}
