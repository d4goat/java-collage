public class appMahasiswa {
    public static void main(String[] args) {
        cMahasiswa mhs1 = new cMahasiswa();
        cMataKuliah BP2 = new cMataKuliah("SI");
        cRuangUjian R202 = new cRuangUjian(30);
        mhs1.setNPM("25082010193");
        mhs1.setNama("Achmadillah Yusuf Faqih Febrianto");
        mhs1.setIPK(3.7);
        mhs1.setSKS(19);
        mhs1.setAlamat("Surabaya");
        System.out.println("Nama Mhs : " + mhs1.getNama());
        System.out.println("NPM : " + mhs1.getNPM());
        System.out.println("Alamat Asal : " + mhs1.getAlamat());
        System.out.println(mhs1.toString());
        BP2.setNamaMK("Bahasa Pemrograman 2");
        BP2.setKodeMK("2025");
        BP2.setSKS(3);
        R202.setNamaRuang("202");
        R202.setKapasitas(50);
        R202.setMK(BP2);
        R202.tambahPeserta(mhs1);
        System.out.print("Peserta Ujian Ruang ");
        System.out.print(R202.getNamaRuang());
        System.out.println(" : " + R202.getMahasiswa());
        System.out.println(R202.ToString());
    }
}

class cMahasiswa {
    private String nama, npm, alamat;
    private double ipk;
    private int SKS;

    cMahasiswa() {
        SKS = 18;
        System.out.println("Object mhs dibuat...");
    }

    cMahasiswa(double m, int n) {
        ipk = m;
        SKS = n;
        System.out.println("Object mhs dibuat...");
    }

    public void setNama(String nm) {
        nama = nm;
    }

    public void setNPM(String np) {
        npm = np;
    }

    public void setIPK(double ip) {
        ipk = ip;
    }

    public void setSKS(int sks) {
        SKS = sks;
    }

    public void setAlamat(String al) {
        alamat = al;
    }

    public String getNama() {
        return nama;
    }

    public String getNPM() {
        return npm;
    }

    public double getIPK() {
        return ipk;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getSKS() {
        return (SKS);
    }

    public String toString() {
        return "IPK Mahasiswa NPM " + npm + " : " + ipk;
    }
}

class cMataKuliah {
    public String namaMK, kodeMK;
    public int sks;

    cMataKuliah(String k) {
        kodeMK = "SI";
        System.out.println("Object mata kuliah dibuat...");
    }

    public void setNamaMK(String nm) {
        namaMK = nm;
    }

    public void setKodeMK(String kd) {
        kodeMK = kodeMK.concat(kd);
    }

    public void setSKS(int s) {
        sks = s;
    }

    public String getNamaMK() {
        return namaMK;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public int getSKS() {
        return sks;
    }

    public String ToString() {
        return "Bobot MK " + namaMK + " : " + sks;
    }
}

class cRuangUjian {
    private cMahasiswa mhs;
    private cMataKuliah mk;
    private String namaRuang;
    private int kapasitas;
    private int totalpeserta;

    cRuangUjian(int k) {
        kapasitas = k;
        System.out.println("Object ruang ujian dibuat...");
    }

    public void tambahPeserta(cMahasiswa m) {
        mhs = m;
        totalpeserta++;
    }

    public void hapusPeserta() {
        totalpeserta--;
    }

    public String getMahasiswa() {
        return mhs.getNama();
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public int getTotalPeserta() {
        return totalpeserta;
    }

    public void setMK(cMataKuliah m) {
        mk = m;
    }

    public void setKapasitas(int k) {
        kapasitas = k;
    }

    public void setTotalPeserta(int t) {
        totalpeserta = t;
    }

    public void setNamaRuang(String nm) {
        namaRuang = nm;
    }

    public String ToString() {
        return "Kapasitas Ruang " + namaRuang + " : " + kapasitas;
    }
}