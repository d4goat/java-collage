import java.util.Scanner;

public class appSiamikWeek5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        cMhs mhs = null;
        cMatkul mk = null;
        cKRS krs = null;
        cProdi prodi = null;
        int pilih, pilih2;
        String npm, kd, nm, ak, smt;

        do {
            System.out.println("\nMENU SIAMIK");
            System.out.println("1. Mahasiswa");
            System.out.println("2. Mata Kuliah");
            System.out.println("3. Kartu Rencana Studi");
            System.out.println("4. Program Studi");
            System.out.println("5. Exit");
            System.out.print("Pilih = ");
            pilih = sc.nextInt();
            System.out.println("");

            switch (pilih) {
                // --- SUB MENU MAHASISWA (CASE 1) ---
                case 1:
                    System.out.println("Sub Menu Mahasiswa");
                    System.out.println("1. Tambah Mhs");
                    System.out.println("2. Hapus Mhs");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah Mhs
                            System.out.println("== Tambah Mhs ==");
                            System.out.print("Masukkan Nama = ");
                            nm = sc.next();
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            mhs = new cMhs(nm, npm);
                            System.out.println("Data ditambahkan...");
                            break;
                        case 2: // Hapus Mhs
                            System.out.println("== Hapus Mhs ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            if (mhs != null && mhs.getNPM().equalsIgnoreCase(npm)) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mhs = null;
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 3: // Update Mhs
                            System.out.println("== Update Data ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            if (mhs != null && mhs.getNPM().equalsIgnoreCase(npm)) {
                                System.out.println("Data ditemukan");
                                System.out.print("Nama baru = ");
                                nm = sc.next();
                                System.out.print("IPK baru = ");
                                double ip = sc.nextDouble();
                                System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mhs.setNama(nm);
                                    mhs.setIPK(ip);
                                    System.out.println("Data diperbarui...");
                                } else {
                                    System.out.println("Batal update...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 4: // Cetak Mhs
                            System.out.println("== Lihat Data ==");
                            if (mhs == null) {
                                System.out.println("Data kosong...");
                            } else {
                                System.out.println("NPM  : " + mhs.getNPM());
                                System.out.println("Nama : " + mhs.getNama());
                                System.out.println("IPK  : " + mhs.getIPK() + "\n");
                            }
                            break;
                        case 5: // Query Mhs
                            System.out.println("== Query Data ==");
                            if (mhs == null) {
                                System.out.println("Data Kosong..");
                            } else {
                                System.out.println(mhs.ToString());
                            }
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                // --- SUB MENU MATA KULIAH (CASE 2) ---
                case 2:
                    System.out.println("Sub Menu Mata Kuliah");
                    System.out.println("1. Tambah Mata Kuliah");
                    System.out.println("2. Hapus Mata Kuliah");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah MK
                            System.out.println("== Tambah Matkul ==");
                            System.out.print("Masukkan Nama MK = ");
                            sc.nextLine(); // clear buffer
                            nm = sc.nextLine();
                            System.out.print("Masukkan Kode MK = ");
                            kd = sc.next();
                            System.out.print("Masukkan SKS = ");
                            int sks = sc.nextInt();
                            mk = new cMatkul(kd, nm, sks);
                            System.out.println("Data ditambahkan...");
                            break;
                        case 2: // Hapus MK
                            System.out.println("== Hapus Matkul ==");
                            System.out.print("Masukkan Kode MK = ");
                            kd = sc.next();
                            if (mk != null && mk.getKodeMK().equalsIgnoreCase(kd)) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mk = null;
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 3: // Update MK
                            System.out.println("== Update Data ==");
                            System.out.print("Masukkan Kode MK = ");
                            kd = sc.next();
                            if (mk != null && mk.getKodeMK().equalsIgnoreCase(kd)) {
                                System.out.println("Data ditemukan");
                                System.out.print("Nama baru = ");
                                sc.nextLine(); // clear buffer
                                nm = sc.nextLine();
                                System.out.print("SKS baru = ");
                                sks = sc.nextInt();
                                System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mk.setNamaMK(nm);
                                    mk.setSKS(sks);
                                    System.out.println("Data diperbarui...");
                                } else {
                                    System.out.println("Batal update...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 4: // Cetak MK
                            System.out.println("== Lihat Data ==");
                            if (mk == null) {
                                System.out.println("Data kosong...");
                            } else {
                                System.out.println("Kode MK : " + mk.getKodeMK());
                                System.out.println("Nama MK : " + mk.getNamaMK());
                                System.out.println("SKS     : " + mk.getSKS() + "\n");
                            }
                            break;
                        case 5: // Query MK
                            System.out.println("== Query Data ==");
                            if (mk == null) {
                                System.out.println("Data kosong...");
                            } else {
                                System.out.println(mk.ToString());
                            }
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                // --- SUB MENU KRS (CASE 3) ---
                case 3:
                    System.out.println("Sub Menu KRS Mahasiswa");
                    System.out.println("1. Tambah Data");
                    System.out.println("2. Hapus Data");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah KRS
                            System.out.println("== Tambah Data ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            System.out.print("Masukkan Kode MK = ");
                            kd = sc.next();
                            System.out.print("Masukkan Semester = ");
                            smt = sc.next();

                            if (mhs != null && mhs.getNPM().equalsIgnoreCase(npm)) {
                                if (mk != null && mk.getKodeMK().equalsIgnoreCase(kd)) {
                                    krs = new cKRS();
                                    krs.setMhs(mhs);
                                    krs.setMatkul(mk);
                                    krs.setSemester(smt);
                                    System.out.println("Penambahan sukses...");
                                } else
                                    System.out.println("Kode MK tidak ada!");
                            } else
                                System.out.println("NPM tidak ada!");
                            break;

                        case 2: // Hapus KRS
                            System.out.println("== Hapus Data ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            System.out.print("Masukkan Kode MK = ");
                            kd = sc.next();
                            System.out.print("Masukkan Semester = ");
                            smt = sc.next();

                            if (krs != null && krs.getMhs().getNPM().equalsIgnoreCase(npm)) {
                                if (krs.getMatkul().getKodeMK().equalsIgnoreCase(kd)) {
                                    if (krs.getSemester().equalsIgnoreCase(smt)) {
                                        krs = null;
                                        System.out.println("Penghapusan sukses...");
                                    } else
                                        System.out.println("Semester tidak ada!");
                                } else
                                    System.out.println("Kode MK tidak ada!");
                            } else
                                System.out.println("NPM tidak ada!");
                            break;
                    }
                    break;

                // --- SUB MENU PRODI (CASE 4) ---
                case 4:
                    System.out.println("Sub Menu Prodi");
                    System.out.println("1. Buat Prodi");
                    System.out.println("2. Hapus Prodi");
                    System.out.println("3. Update Data Prodi");
                    System.out.println("4. Tambah Mahasiswa");
                    System.out.println("5. Hapus Mahasiswa");
                    System.out.println("6. Cetak Mahasiswa");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Buat Prodi
                            System.out.println("== Buat Prodi ==");
                            System.out.print("Masukkan Kode = ");
                            kd = sc.next();
                            System.out.print("Masukkan Nama = ");
                            sc = new Scanner(System.in);
                            nm = sc.nextLine();
                            System.out.print("Masukkan Akreditasi = ");
                            ak = sc.next();
                            prodi = new cProdi(kd, nm, ak);
                            System.out.println("Prodi Berhasil Dibuat!");
                            break;

                        case 4: // Tambah Mhs ke Prodi (Relasi)
                            System.out.println("== Tambah Mahasiswa ==");
                            System.out.print("Masukkan Kode Prodi = ");
                            kd = sc.next();
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();

                            if (mhs != null && mhs.getNPM().equalsIgnoreCase(npm)) {
                                if (prodi != null && prodi.getKodeProdi().equalsIgnoreCase(kd)) {
                                    prodi.setMhs(mhs);
                                    System.out.println("Tambah sukses...");
                                } else
                                    System.out.println("Prodi Tidak Ada!");
                            } else
                                System.out.println("NPM tidak ada!");
                            break;
                    }
                    break;
            }
        } while (pilih != 5);
        System.out.println("Terima kasih...");
    }
}

class cMhs {
    private String nama;
    private String NPM;
    private double IPK;

    // Constructor 1
    cMhs() {
        System.out.println("Constructor 1 mhs...");
    }

    // Constructor 2 — FIX: parameter names clarified (nama first, npm second)
    cMhs(String nm, String np) {
        nama = nm;
        NPM = np;
        IPK = 0.0;
        System.out.println("Constructor 2 mhs...");
    }

    // Setter
    public void setNPM(String n) {
        NPM = n;
    }

    public void setNama(String n) {
        nama = n;
    }

    public void setIPK(double i) {
        IPK = i;
    }

    // Getter
    public String getNPM() {
        return NPM;
    }

    public String getNama() {
        return nama;
    }

    public double getIPK() {
        return IPK;
    }

    // Query
    public String ToString() {
        return NPM + " " + nama + " " + IPK;
    }
}

class cProdi {
    private String kodeProdi;
    private String namaProdi;
    private String akreditasi;
    private cMhs mhs; // Implementasi Has A

    cProdi() {
        kodeProdi = "XYZ";
        namaProdi = "ABC";
        akreditasi = "N/A";
        mhs = null;
        System.out.println("Object Prodi dibuat...");
    }

    cProdi(String kd, String nm, String ak) {
        kodeProdi = kd;
        namaProdi = nm;
        akreditasi = ak;
        mhs = null;
        System.out.println("Object Prodi dibuat...");
    }

    public void setMhs(cMhs m) {
        mhs = m;
    }

    public cMhs getMhs() {
        return mhs;
    }

    public void deleteMhs() {
        mhs = null;
    }

    public void setKodeProdi(String k) {
        kodeProdi = k;
    }

    public void setNamaProdi(String k) {
        namaProdi = k;
    }

    public void setAkreditasi(String k) {
        akreditasi = k;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public String getAkreditasi() {
        return akreditasi;
    }

    public String getKodeProdi() {
        return kodeProdi;
    }

    public String ToString() {
        return kodeProdi + " " + namaProdi + " " + akreditasi;
    }
}

class cKRS {
    private cMhs mhs;
    private cMatkul mk;
    private String semester;

    cKRS() {
        mhs = null;
        mk = null;
        System.out.println("Object KRS dibuat...");
    }

    public void setMhs(cMhs m) {
        mhs = m;
    }

    public void setMatkul(cMatkul m) {
        mk = m;
    }

    public void setSemester(String s) {
        semester = s;
    }

    public cMhs getMhs() {
        return mhs;
    }

    public cMatkul getMatkul() {
        return mk;
    }

    public String getSemester() {
        return semester;
    }

    public String ToString() {
        String temp = "Semester " + semester + " : ";
        temp = temp + mhs.getNama();
        temp = temp + " ambil MK " + mk.getNamaMK();
        return temp;
    }
}