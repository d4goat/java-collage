import java.util.ArrayList;
import java.util.Scanner;

public class appSiamik {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<cMhs> mhs = new ArrayList<>();
        ArrayList<cMatkul> mk = new ArrayList<>();
        ArrayList<cKRS> krs = new ArrayList<>();
        ArrayList<cProdi> prodi = new ArrayList<>();
        int pilih, pilih2;
        int idx = -1;

        do {
            System.out.println("\n=== MENU SIAMIK ===");
            System.out.println("1. Mahasiswa");
            System.out.println("2. Mata Kuliah");
            System.out.println("3. Kartu Rencana Studi");
            System.out.println("4. Program Studi");
            System.out.println("5. Exit");
            System.out.print("Pilih = ");
            pilih = sc.nextInt();
            System.out.println();

            switch (pilih) {
                case 1: // Menu Mahasiswa
                    System.out.println("Sub Menu Mahasiswa");
                    System.out.println("1. Tambah Mhs");
                    System.out.println("2. Hapus Mhs");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah
                            System.out.println("== Tambah Mhs ==");
                            System.out.print("Masukkan Nama = ");
                            String nama = sc.next();
                            System.out.print("Masukkan NPM = ");
                            String npm = sc.next();
                            // FIX: constructor parameter order was swapped (nama, npm)
                            mhs.add(new cMhs(nama, npm));
                            System.out.println("Data ditambahkan...");
                            break;
                        case 2: // Hapus
                            System.out.println("== Hapus Mhs ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            // FIX: reset idx before each search
                            idx = -1;
                            for (int i = 0; i < mhs.size(); i++) {
                                if (mhs.get(i).getNPM().equalsIgnoreCase(npm)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    // FIX: remove specific element, not nullify the whole list
                                    mhs.remove(idx);
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 3: // Update
                            System.out.println("== Update Data ==");
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();
                            // FIX: reset idx before each search
                            idx = -1;
                            for (int i = 0; i < mhs.size(); i++) {
                                if (mhs.get(i).getNPM().equalsIgnoreCase(npm)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Nama baru = ");
                                String nm = sc.next();
                                System.out.print("IPK baru = ");
                                double ip = sc.nextDouble();
                                System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mhs.get(idx).setNama(nm);
                                    mhs.get(idx).setIPK(ip);
                                    System.out.println("Data diperbarui...");
                                } else {
                                    System.out.println("Batal update...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 4: // Cetak
                            System.out.println("== Lihat Data ==");
                            if (mhs.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cMhs m : mhs) {
                                    System.out.println("NPM  : " + m.getNPM());
                                    System.out.println("Nama : " + m.getNama());
                                    System.out.println("IPK  : " + m.getIPK() + "\n");
                                }
                            }
                            break;
                        case 5: // Query
                            System.out.println("== Query Data ==");
                            // FIX: check isEmpty() before the loop, not inside it
                            if (mhs.isEmpty()) {
                                System.out.println("Data Kosong..");
                            } else {
                                for (int i = 0; i < mhs.size(); i++) {
                                    System.out.println(mhs.get(i).ToString());
                                }
                            }
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                case 2: // Menu Mata Kuliah
                    System.out.println("Sub Menu Mata Kuliah");
                    System.out.println("1. Tambah Mata Kuliah");
                    System.out.println("2. Hapus Mata Kuliah");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah
                            System.out.println("== Tambah Matkul ==");
                            System.out.print("Masukkan Nama MK = ");
                            // supaya bisa baca spasi, kita pakai nextLine() tapi perlu handling
                            sc.nextLine(); // clear buffer
                            String namaMK = sc.nextLine();
                            System.out.print("Masukkan Kode MK = ");
                            String kode = sc.next();
                            System.out.print("Masukkan SKS = ");
                            int sks = sc.nextInt();
                            // FIX: add to the ArrayList, not assign a single object
                            mk.add(new cMatkul(kode, namaMK, sks));
                            System.out.println("Data ditambahkan...");
                            break;
                        case 2: // Hapus
                            System.out.println("== Hapus Matkul ==");
                            System.out.print("Masukkan Kode MK = ");
                            kode = sc.next();
                            // FIX: search in the ArrayList
                            idx = -1;
                            for (int i = 0; i < mk.size(); i++) {
                                if (mk.get(i).getKodeMK().equalsIgnoreCase(kode)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mk.remove(idx);
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 3: // Update
                            System.out.println("== Update Data ==");
                            System.out.print("Masukkan Kode MK = ");
                            kode = sc.next();
                            // FIX: search in the ArrayList
                            idx = -1;
                            for (int i = 0; i < mk.size(); i++) {
                                if (mk.get(i).getKodeMK().equalsIgnoreCase(kode)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Nama baru = ");
                                sc.nextLine(); // clear buffer
                                String nmBaru = sc.nextLine();
                                System.out.print("SKS baru = ");
                                int sksBaru = sc.nextInt();
                                System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    mk.get(idx).setNamaMK(nmBaru);
                                    mk.get(idx).setSKS(sksBaru);
                                    System.out.println("Data diperbarui...");
                                } else {
                                    System.out.println("Batal update...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 4: // Cetak
                            System.out.println("== Lihat Data ==");
                            if (mk.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cMatkul m : mk) {
                                    System.out.println("Kode MK : " + m.getKodeMK());
                                    System.out.println("Nama MK : " + m.getNamaMK());
                                    System.out.println("SKS     : " + m.getSKS() + "\n");
                                }
                            }
                            break;
                        case 5: // Query
                            System.out.println("== Query Data ==");
                            if (mk.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cMatkul m : mk) {
                                    System.out.println(m.ToString());
                                }
                            }
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                case 3: // Menu KRS
                    System.out.println("Sub Menu KRS");
                    System.out.println("1. Tambah KRS");
                    System.out.println("2. Hapus KRS");
                    System.out.println("3. Update Data");
                    System.out.println("4. Cetak Data");
                    System.out.println("5. Query");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Tambah
                            System.out.println("== Tambah KRS ==");
                            if (mhs.isEmpty()) {
                                System.out.println("Belum ada data mahasiswa!");
                                break;
                            }
                            if (mk.isEmpty()) {
                                System.out.println("Belum ada data mata kuliah!");
                                break;
                            }
                            System.out.print("Masukkan NPM = ");
                            String npmKrs = sc.next();
                            cMhs mhsFound = null;
                            for (cMhs m : mhs) {
                                if (m.getNPM().equalsIgnoreCase(npmKrs)) {
                                    mhsFound = m;
                                    break;
                                }
                            }
                            if (mhsFound == null) {
                                System.out.println("Mahasiswa tidak ditemukan!");
                                break;
                            }
                            System.out.println("Mahasiswa ditemukan: " + mhsFound.getNama());

                            System.out.print("Masukkan Semester = ");
                            int smt = sc.nextInt();
                            System.out.print("Masukkan Tahun Angkatan = ");
                            int ta = sc.nextInt();
                            cKRS newKrs = new cKRS(smt, ta);
                            newKrs.setMhs(mhsFound);

                            boolean tambahLagi = true;
                            while (tambahLagi) {
                                ArrayList<Integer> mkTersedia = new ArrayList<>();
                                System.out.println("\nDaftar Mata Kuliah yang tersedia:");
                                int nomor = 1;
                                for (int i = 0; i < mk.size(); i++) {
                                    boolean sudahDipilih = false;
                                    for (cMatkul mkDipilih : newKrs.getMatkul()) {
                                        if (mkDipilih.getKodeMK().equalsIgnoreCase(mk.get(i).getKodeMK())) {
                                            sudahDipilih = true;
                                            break;
                                        }
                                    }
                                    if (!sudahDipilih) {
                                        System.out.println(nomor + ". " + mk.get(i).getNamaMK() +
                                                " [" + mk.get(i).getKodeMK() + "]" +
                                                " (" + mk.get(i).getSKS() + " SKS)");
                                        mkTersedia.add(i); // simpan index asli
                                        nomor++;
                                    }
                                }

                                if (mkTersedia.isEmpty()) {
                                    System.out.println("Semua mata kuliah sudah dipilih!");
                                    break;
                                }

                                System.out.println("0. Selesai memilih MK");
                                System.out.print("Pilih nomor MK = ");
                                int pilihanMK = sc.nextInt();

                                if (pilihanMK == 0) {
                                    tambahLagi = false;
                                } else if (pilihanMK >= 1 && pilihanMK <= mkTersedia.size()) {
                                    int realIdx = mkTersedia.get(pilihanMK - 1);
                                    newKrs.addMatkul(mk.get(realIdx));
                                    System.out.println("MK \"" + mk.get(realIdx).getNamaMK() + "\" ditambahkan.");
                                } else {
                                    System.out.println("Nomor tidak valid, coba lagi.");
                                }
                            }

                            // Step 4: Simpan KRS jika ada MK yang dipilih
                            if (!newKrs.getMatkul().isEmpty()) {
                                krs.add(newKrs);
                                System.out.println("KRS berhasil disimpan! Total " +
                                        newKrs.getMatkul().size() + " MK, " +
                                        newKrs.getTotalSKS() + " SKS.");
                            } else {
                                System.out.println("Tidak ada MK yang dipilih, KRS tidak disimpan.");
                            }
                            break;
                        case 2: // Hapus
                            System.out.println("== Hapus KRS ==");
                            if (krs.isEmpty()) {
                                System.out.println("Data KRS kosong...");
                                break;
                            }
                            System.out.print("Masukkan NPM = ");
                            npmKrs = sc.next();
                            idx = -1;
                            for (int i = 0; i < krs.size(); i++) {
                                if (krs.get(i).getMhs().getNPM().equalsIgnoreCase(npmKrs)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    krs.remove(idx);
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;
                        case 3: // Update
                            System.out.println("== Update KRS ==");
                            if (krs.isEmpty()) {
                                System.out.println("Data KRS kosong...");
                                break;
                            }
                            System.out.print("Masukkan NPM = ");
                            npmKrs = sc.next();
                            idx = -1;
                            for (int i = 0; i < krs.size(); i++) {
                                if (krs.get(i).getMhs().getNPM().equalsIgnoreCase(npmKrs)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx == -1) {
                                System.out.println("Data KRS tidak ditemukan!");
                                break;
                            }
                            cKRS krsEdit = krs.get(idx);
                            System.out.println("KRS ditemukan: " + krsEdit.getMhs().getNama() +
                                    " | Semester " + krsEdit.getSemester() +
                                    " | TA " + krsEdit.getTA());

                            // Sub-menu update KRS
                            boolean updateLagi = true;
                            while (updateLagi) {
                                System.out.println("\n-- Edit KRS --");
                                System.out.println("1. Ubah Semester & Tahun Angkatan");
                                System.out.println("2. Tambah Mata Kuliah");
                                System.out.println("3. Hapus Mata Kuliah");
                                System.out.println("4. Selesai");
                                System.out.print("Pilih = ");
                                int pilihanEdit = sc.nextInt();

                                switch (pilihanEdit) {
                                    case 1: // Ubah semester & TA
                                        System.out.print("Semester baru = ");
                                        int smtBaru = sc.nextInt();
                                        System.out.print("Tahun Angkatan baru = ");
                                        int taBaru = sc.nextInt();
                                        System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                        int konfirmEdit = sc.nextInt();
                                        if (konfirmEdit == 1) {
                                            krsEdit.setSemester(smtBaru);
                                            krsEdit.setTA(taBaru);
                                            System.out.println("Semester & TA diperbarui.");
                                        } else {
                                            System.out.println("Batal.");
                                        }
                                        break;

                                    case 2: // Tambah MK ke KRS
                                        if (mk.isEmpty()) {
                                            System.out.println("Belum ada data mata kuliah!");
                                            break;
                                        }
                                        ArrayList<Integer> mkBisaDitambah = new ArrayList<>();
                                        System.out.println("\nMata Kuliah yang bisa ditambahkan:");
                                        int nomTambah = 1;
                                        for (int i = 0; i < mk.size(); i++) {
                                            boolean sudahAda = false;
                                            for (cMatkul mkCek : krsEdit.getMatkul()) {
                                                if (mkCek.getKodeMK().equalsIgnoreCase(mk.get(i).getKodeMK())) {
                                                    sudahAda = true;
                                                    break;
                                                }
                                            }
                                            if (!sudahAda) {
                                                System.out.println(nomTambah + ". " + mk.get(i).getNamaMK() +
                                                        " [" + mk.get(i).getKodeMK() + "]" +
                                                        " (" + mk.get(i).getSKS() + " SKS)");
                                                mkBisaDitambah.add(i);
                                                nomTambah++;
                                            }
                                        }
                                        if (mkBisaDitambah.isEmpty()) {
                                            System.out.println("Semua MK sudah ada di KRS ini.");
                                            break;
                                        }
                                        System.out.print("Pilih nomor MK = ");
                                        int pilihanTambah = sc.nextInt();
                                        if (pilihanTambah >= 1 && pilihanTambah <= mkBisaDitambah.size()) {
                                            int realIdxTambah = mkBisaDitambah.get(pilihanTambah - 1);
                                            krsEdit.addMatkul(mk.get(realIdxTambah));
                                            System.out.println("\"" + mk.get(realIdxTambah).getNamaMK()
                                                    + "\" ditambahkan ke KRS.");
                                        } else {
                                            System.out.println("Nomor tidak valid.");
                                        }
                                        break;

                                    case 3: // Hapus MK dari KRS
                                        if (krsEdit.getMatkul().isEmpty()) {
                                            System.out.println("Tidak ada MK dalam KRS ini.");
                                            break;
                                        }
                                        System.out.println("\nMata Kuliah dalam KRS:");
                                        for (int i = 0; i < krsEdit.getMatkul().size(); i++) {
                                            System.out.println((i + 1) + ". " + krsEdit.getMatkul().get(i).getNamaMK() +
                                                    " [" + krsEdit.getMatkul().get(i).getKodeMK() + "]" +
                                                    " (" + krsEdit.getMatkul().get(i).getSKS() + " SKS)");
                                        }
                                        System.out.print("Pilih nomor MK yang dihapus = ");
                                        int pilihanHapusMK = sc.nextInt();
                                        if (pilihanHapusMK >= 1 && pilihanHapusMK <= krsEdit.getMatkul().size()) {
                                            cMatkul mkDihapus = krsEdit.getMatkul().get(pilihanHapusMK - 1);
                                            krsEdit.removeMatkul(mkDihapus);
                                            System.out.println("\"" + mkDihapus.getNamaMK() + "\" dihapus dari KRS.");
                                        } else {
                                            System.out.println("Nomor tidak valid.");
                                        }
                                        break;

                                    case 4:
                                        updateLagi = false;
                                        System.out.println(
                                                "Selesai update KRS. Total SKS sekarang: " + krsEdit.getTotalSKS());
                                        break;

                                    default:
                                        System.out.println("Pilihan tidak valid!");
                                }
                            }
                            break;
                        case 4: // Cetak
                            System.out.println("== Lihat Data KRS ==");
                            if (krs.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cKRS k : krs) {
                                    System.out.println("Mahasiswa : " + k.getMhs().getNama() +
                                            " (" + k.getMhs().getNPM() + ")");
                                    System.out.println("Semester  : " + k.getSemester());
                                    System.out.println("Tahun     : " + k.getTA());
                                    System.out.println("Total SKS : " + k.getTotalSKS());
                                    System.out.println("Mata Kuliah:");
                                    for (cMatkul m : k.getMatkul()) {
                                        System.out.println("  - " + m.getNamaMK() +
                                                " (" + m.getSKS() + " SKS)");
                                    }
                                    System.out.println();
                                }
                            }
                            break;
                        case 5: // Query
                            System.out.println("== Query KRS ==");
                            if (krs.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cKRS k : krs) {
                                    System.out.println(k.ToString());
                                }
                            }
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                case 4: // Menu Program Studi
                    System.out.println("Sub Menu Program Studi");
                    System.out.println("1. Buat Prodi");
                    System.out.println("2. Hapus Prodi");
                    System.out.println("3. Update Data Prodi");
                    System.out.println("4. Tambah Mahasiswa ke Prodi");
                    System.out.println("5. Hapus Mahasiswa dari Prodi");
                    System.out.println("6. Cetak Data Prodi");
                    System.out.print("Pilih = ");
                    pilih2 = sc.nextInt();

                    switch (pilih2) {
                        case 1: // Buat Prodi
                            System.out.println("== Buat Prodi ==");
                            System.out.print("Masukkan Kode = ");
                            String kode = sc.next();
                            sc.nextLine(); // clear buffer
                            System.out.print("Masukkan Nama = ");
                            String nama = sc.nextLine();
                            System.out.print("Masukkan Akreditasi = ");
                            String ak = sc.next();
                            prodi.add(new cProdi(kode, nama, ak));
                            System.out.println("Prodi Berhasil Dibuat!");
                            break;

                        case 2: // Hapus Prodi
                            System.out.println("== Hapus Prodi ==");
                            System.out.print("Masukkan Kode Prodi = ");
                            kode = sc.next();
                            idx = -1;
                            for (int i = 0; i < prodi.size(); i++) {
                                if (prodi.get(i).getKodeProdi().equalsIgnoreCase(kode)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Dihapus? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    prodi.remove(idx);
                                    System.out.println("Data dihapus...");
                                } else {
                                    System.out.println("Batal hapus...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;

                        case 3: // Update Prodi
                            System.out.println("== Update Data Prodi ==");
                            System.out.print("Masukkan Kode Prodi = ");
                            kode = sc.next();
                            idx = -1;
                            for (int i = 0; i < prodi.size(); i++) {
                                if (prodi.get(i).getKodeProdi().equalsIgnoreCase(kode)) {
                                    idx = i;
                                    break;
                                }
                            }
                            if (idx != -1) {
                                System.out.println("Data ditemukan");
                                System.out.print("Nama baru = ");
                                sc.nextLine(); // clear buffer
                                String nmBaru = sc.nextLine();
                                System.out.print("Akreditasi baru = ");
                                String akBaru = sc.next();
                                System.out.print("Simpan? 1.Ya, 2.Tidak : ");
                                pilih2 = sc.nextInt();
                                if (pilih2 == 1) {
                                    prodi.get(idx).setNamaProdi(nmBaru);
                                    prodi.get(idx).setAkreditasi(akBaru);
                                    System.out.println("Data diperbarui...");
                                } else {
                                    System.out.println("Batal update...");
                                }
                            } else {
                                System.out.println("Data tidak ada...");
                            }
                            break;

                        case 4: // Tambah Mahasiswa ke Prodi
                            System.out.println("== Tambah Mahasiswa ke Prodi ==");
                            System.out.print("Masukkan Kode Prodi = ");
                            kode = sc.next();
                            System.out.print("Masukkan NPM = ");
                            String npm = sc.next();

                            cProdi prodiFound = null;
                            for (cProdi p : prodi) {
                                if (p.getKodeProdi().equalsIgnoreCase(kode)) {
                                    prodiFound = p;
                                    break;
                                }
                            }
                            if (prodiFound == null) {
                                System.out.println("Prodi tidak ditemukan!");
                                break;
                            }

                            cMhs mhsFound = null;
                            for (cMhs m : mhs) {
                                if (m.getNPM().equalsIgnoreCase(npm)) {
                                    mhsFound = m;
                                    break;
                                }
                            }
                            if (mhsFound == null) {
                                System.out.println("Mahasiswa tidak ditemukan!");
                                break;
                            }

                            prodiFound.addMhs(mhsFound);
                            System.out.println("Mahasiswa berhasil ditambahkan ke Prodi!");
                            break;

                        case 5: // Hapus Mahasiswa dari Prodi
                            System.out.println("== Hapus Mahasiswa dari Prodi ==");
                            System.out.print("Masukkan Kode Prodi = ");
                            kode = sc.next();
                            System.out.print("Masukkan NPM = ");
                            npm = sc.next();

                            prodiFound = null;
                            for (cProdi p : prodi) {
                                if (p.getKodeProdi().equalsIgnoreCase(kode)) {
                                    prodiFound = p;
                                    break;
                                }
                            }
                            if (prodiFound == null) {
                                System.out.println("Prodi tidak ditemukan!");
                                break;
                            }

                            mhsFound = null;
                            for (cMhs m : mhs) {
                                if (m.getNPM().equalsIgnoreCase(npm)) {
                                    mhsFound = m;
                                    break;
                                }
                            }
                            if (mhsFound == null) {
                                System.out.println("Mahasiswa tidak ditemukan!");
                                break;
                            }

                            prodiFound.removeMhs(mhsFound);
                            System.out.println("Mahasiswa berhasil dihapus dari Prodi!");
                            break;

                        case 6: // Cetak Data Prodi
                            System.out.println("== Lihat Data Prodi ==");
                            if (prodi.isEmpty()) {
                                System.out.println("Data kosong...");
                            } else {
                                for (cProdi p : prodi) {
                                    System.out.println("Kode Prodi : " + p.getKodeProdi());
                                    System.out.println("Nama Prodi : " + p.getNamaProdi());
                                    System.out.println("Akreditasi : " + p.getAkreditasi());
                                    System.out.println("Mahasiswa dalam Prodi:");
                                    if (p.getMhs().isEmpty()) {
                                        System.out.println("  (Tidak ada mahasiswa)");
                                    } else {
                                        for (cMhs m : p.getMhs()) {
                                            System.out.println("  - " + m.getNama() + " (" + m.getNPM() + ")");
                                        }
                                    }
                                    System.out.println();
                                }
                            }
                            break;

                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                    break;

                case 5:
                    System.out.println("Terima kasih...");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);

        sc.close();
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

// ========== CLASS MATA KULIAH ==========
class cMatkul {
    private String kodeMK;
    private String namaMK;
    private int SKS;

    cMatkul() {
        System.out.println("Constructor 1 Matkul...");
    }

    cMatkul(String kd, String nm, int s) {
        kodeMK = kd;
        namaMK = nm;
        SKS = s;
        System.out.println("Constructor 2 Matkul...");
    }

    public void setKodeMK(String k) {
        kodeMK = k;
    }

    public void setNamaMK(String n) {
        namaMK = n;
    }

    public void setSKS(int s) {
        SKS = s;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public String getNamaMK() {
        return namaMK;
    }

    public int getSKS() {
        return SKS;
    }

    public String ToString() {
        return kodeMK + " " + namaMK + " " + SKS + " SKS";
    }
}

// ========== CLASS KRS ==========
class cKRS {
    private cMhs mhs;
    // FIX: was incorrectly declared as ArrayList<cMatkul>[] (array of ArrayLists)
    private ArrayList<cMatkul> mk;
    private int semester;
    private int TA;
    private int totalSKS;

    cKRS() {
        mk = new ArrayList<>();
        totalSKS = 0;
        System.out.println("Constructor 1 KRS...");
    }

    cKRS(int smt, int t) {
        mhs = null;
        mk = new ArrayList<>();
        semester = smt;
        TA = t;
        totalSKS = 0;
        System.out.println("Constructor 2 KRS...");
    }

    public void setMhs(cMhs m) {
        mhs = m;
    }

    // FIX: renamed to addMatkul to properly add to the list
    public void addMatkul(cMatkul m) {
        mk.add(m);
        totalSKS += m.getSKS();
    }

    public void removeMatkul(cMatkul m) {
        mk.remove(m);
        recalcTotalSKS();
    }

    private void recalcTotalSKS() {
        totalSKS = 0;
        for (cMatkul m : mk) {
            totalSKS += m.getSKS();
        }
    }

    public void setSemester(int s) {
        semester = s;
    }

    public void setTA(int t) {
        TA = t;
    }

    public void setTotalSKS(int ts) {
        totalSKS = ts;
    }

    public cMhs getMhs() {
        return mhs;
    }

    public ArrayList<cMatkul> getMatkul() {
        return mk;
    }

    public int getSemester() {
        return semester;
    }

    public int getTA() {
        return TA;
    }

    public int getTotalSKS() {
        return totalSKS;
    }

    public String ToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mhs.getNama()).append(" | Semester ").append(semester)
                .append(" | TA ").append(TA).append(" | SKS ").append(totalSKS).append("\n");
        for (cMatkul m : mk) {
            sb.append("  -> ").append(m.getNamaMK()).append("\n");
        }
        return sb.toString();
    }
}

// ========== CLASS PROGRAM STUDI ==========
class cProdi {
    private String kodeProdi;
    private String namaProdi;
    private String akreditasi;
    private ArrayList<cMhs> mhs; // Implementation of Has A (multiple students)

    cProdi() {
        kodeProdi = "XYZ";
        namaProdi = "ABC";
        akreditasi = "N/A";
        mhs = new ArrayList<>();
        System.out.println("Constructor 1 Prodi...");
    }

    cProdi(String kd, String nm, String ak) {
        kodeProdi = kd;
        namaProdi = nm;
        akreditasi = ak;
        mhs = new ArrayList<>();
        System.out.println("Object Prodi dibuat...");
    }

    public void addMhs(cMhs m) {
        mhs.add(m);
    }

    public void removeMhs(cMhs m) {
        mhs.remove(m);
    }

    public ArrayList<cMhs> getMhs() {
        return mhs;
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