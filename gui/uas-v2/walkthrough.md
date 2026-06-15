# Walkthrough: Alur Kerja Aplikasi TrashTrackApp

Aplikasi **TrashTrack** adalah simulasi "Sistem Antrean Tempat Sampah Pintar" berbasis desktop dengan antarmuka modern yang mendukung visi _Sustainable Development Goals (SDG) 9_. Aplikasi ini menggunakan struktur data *Linked List* khusus (`LinkedListCustom`) yang berfungsi sebagai antrean *First-In, First-Out* (FIFO).

Berikut adalah alur kerja lengkap dari aplikasi ini, baik dari interaksi pengguna maupun proses yang terjadi di belakang layar.

---

## 1. Menjalankan Aplikasi & Inisialisasi
- Saat program dijalankan, `TrashTrackApp` akan mengatur tampilan visual (UI) dan membagi antarmuka menjadi dua panel utama:
  - **Sisi Kiri**: Panel formulir untuk mendaftarkan tempat sampah yang penuh.
  - **Sisi Kanan**: Panel visualisasi daftar antrean (Queue).
- Di latar belakang, sistem menginisiasi `LinkedListCustom` yang awalnya masih kosong (`head` = null, `tail` = null).

## 2. Pendaftaran Tempat Sampah (Enqueue)
Ketika ada sebuah tempat sampah pintar yang mendeteksi kondisinya sudah penuh, data perlu dimasukkan ke sistem antrean.

### Interaksi Pengguna:
1. Pengguna mengisi **Lokasi Pemasangan** dan **Kapasitas** tempat sampah.
2. Pengguna memilih **Tipe Klasifikasi Sampah**:
   - Jika **Organik**, pengguna bisa mencentang apakah sampah tersebut *Dapat Membusuk Alami* (untuk dijadikan kompos).
   - Jika **Anorganik**, pengguna akan disajikan pilihan tambahan untuk menentukan jenis **Material Utama** (Plastik, Kertas, Logam, dsb).
3. Pengguna mengklik tombol `Masukkan Ke Antrean Penuh`.

### Proses Sistem (Behind the Scenes):
- Sistem akan mengekstrak semua data formulir.
- Sistem meng-generate **ID unik** secara otomatis (contoh: `BIN-001`).
- Menggunakan konsep *Polymorphism*, sistem menciptakan objek (Node) baru, entah itu `SampahOrganik` atau `SampahAnorganik`, mewarisi *super-class* `TempatSampah`.
- Objek tersebut dimasukkan ke struktur `LinkedListCustom` dengan operasi **Enqueue**. Elemen baru ini akan diikat sebagai **Tail (Ekor)** dalam Linked List.
- Setelah berhasil, formulir isian akan di-reset untuk menginput data baru selanjutnya.

## 3. Visualisasi Antrean (Dynamic Rendering)
Setiap kali ada penambahan atau pengurangan pada antrean, antarmuka daftar antrean (di sisi kanan) akan otomatis diperbarui secara dinamis.

- Sistem memanggil method `updateVisualAntrean()`.
- Method ini menelusuri (melakukan iterasi/traversal) `LinkedListCustom` secara sekuensial, dimulai dari `Head` hingga mencapai ujung belakang.
- Pada setiap iterasi *node*, sistem membuat komponen *Card* visual (kotak informasi berdesain bersih yang menunjukkan *ID*, tipe, status membusuk/jenis material, dan urutan antrean).
- Sampah urutan #1 (yang berada di `Head`) akan mendapat _tag_ khusus berwarna merah bertuliskan **"TERDEPAN"**. Sampah sisanya mendapat _tag_ **"MENUNGGU"**.

## 4. Pengangkutan Sampah Terdepan (Dequeue)
Fase ketika petugas kebersihan sudah tiba di lokasi untuk mengangkut sampah.

### Interaksi Pengguna:
1. Pengguna mengklik tombol `Angkut & Kosongkan Sampah Terdepan (FIFO)` pada panel sebelah kanan bawah.

### Proses Sistem (Behind the Scenes):
- Sistem melakukan verifikasi apakah Linked List kosong atau tidak. Jika kosong, pengguna akan mendapat peringatan.
- Jika ada antrean, sistem menjalankan operasi **Dequeue** pada `LinkedListCustom`.
- Node yang berada di **Head (Kepala)** dihapus dari antrean dan `Head` yang baru digantikan oleh elemen tepat di belakangnya.
- Data dari node yang dihapus tersebut ditampilkan dalam _Popup Message Dialog_ berbunyi **"Petugas telah membersihkan & mengosongkan..."** beserta rincian informasi.
- Sistem memanggil kembali visualisasi antrean. Elemen terdepan menghilang, dan sisa antrean akan bergeser maju mengisi posisi #1.

---

> [!NOTE]
> Seluruh alur di atas mencerminkan pola antrean FIFO yang efisien, menjamin tempat sampah yang pertama kali penuh akan diproses paling pertama oleh petugas. Pola ini mencegah penumpukan sampah yang terlalu lama di satu lokasi dan menjaga higienitas lingkungan.
