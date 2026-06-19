# 🎤 Script Presentasi: TrashTrack — Sistem Antrean Tempat Sampah Pintar
> **Durasi Total:** ± 3 Menit | **Bahasa:** Indonesia
> **Pembagian:** 4 Bagian | **Rata-rata per Bagian:** ± 45 Detik

---

## 📌 Bagian 1 — Penjelasan Project
> ⏱ *Estimasi baca: ± 45 detik*

---

Halo semua, perkenalkan project kami yang bernama **TrashTrack** — sebuah aplikasi desktop berbasis Java yang kami rancang sebagai **Sistem Antrean Tempat Sampah Pintar**.

Bayangkan sebuah kota yang memiliki ratusan tempat sampah tersebar di berbagai titik. Ketika tempat-tempat sampah itu penuh, petugas kebersihan perlu tahu: **mana yang harus diprioritaskan?** Itulah masalah yang TrashTrack selesaikan.

TrashTrack memungkinkan operator untuk **mendaftarkan tempat sampah yang sudah penuh** ke dalam sebuah sistem antrean digital. Setiap tempat sampah dapat diklasifikasikan sebagai **Organik** — misalnya sisa makanan yang bisa membusuk dan dijadikan kompos — atau **Anorganik** — seperti plastik, kertas, logam, dan kaca.

Sistem kemudian memastikan petugas mengangkut sampah **berdasarkan urutan kedatangan** — yang lebih dulu penuh, lebih dulu diangkut. Konsep inilah yang disebut **FIFO: First-In, First-Out**.

---

## 🌍 Bagian 2 — Keterkaitan dengan SDG yang Dipilih
> ⏱ *Estimasi baca: ± 45 detik*

---

Project TrashTrack secara langsung mendukung **SDG 9: Industri, Inovasi, dan Infrastruktur** — khususnya pada poin pembangunan **infrastruktur yang berkelanjutan dan inovatif** di tingkat komunitas.

Mengapa SDG 9? Ada tiga alasan utama:

**Pertama, Inovasi Teknologi untuk Layanan Publik.**
TrashTrack memperkenalkan pendekatan berbasis perangkat lunak untuk mengelola sampah, sesuatu yang sebelumnya dilakukan secara manual. Ini adalah contoh nyata bagaimana teknologi sederhana bisa meningkatkan kualitas layanan publik di tingkat lokal.

**Kedua, Infrastruktur yang Efisien.**
Dengan sistem antrean FIFO, petugas tidak perlu lagi menduga-duga atau membuang waktu memeriksa setiap titik secara acak. Rute pengangkutan menjadi terurut dan terstruktur, sehingga **menghemat waktu, bahan bakar, dan biaya operasional**.

**Ketiga, Mendukung Lingkungan Bersih Secara Sistematis.**
Dengan manajemen yang tertata, tumpukan sampah di titik tertentu bisa dicegah. Ini berdampak langsung pada **kebersihan dan kesehatan lingkungan masyarakat** — fondasi dari infrastruktur kota yang berkelanjutan.

---

## 💻 Bagian 3 — Penjelasan Code yang Digunakan
> ⏱ *Estimasi baca: ± 45 detik*

---

Project ini dibangun menggunakan **5 file Java** yang masing-masing punya peran berbeda, dan menerapkan konsep OOP secara lengkap.

**Pertama, `TempatSampah.java` — Abstract Superclass.**
Ini adalah cetak biru (blueprint) dari semua tempat sampah. Menggunakan `abstract class`, kelas ini menerapkan **Enkapsulasi** dengan atribut `private`, serta memaksa subclass untuk mengimplementasikan method `getTipe()` dan `getDetailSpesifik()`.

```java
// Enkapsulasi: Atribut diset private, diakses lewat getter/setter
public abstract class TempatSampah {
    private String id;
    private String lokasi;
    private int kapasitas;

    // Method abstrak → wajib di-override oleh subclass (Polimorfisme)
    public abstract String getTipe();
    public abstract String getDetailSpesifik();
}
```

**Kedua, `SampahOrganik.java` & `SampahAnorganik.java` — Subclass (Inheritance + Polymorphism).**
Keduanya mewarisi `TempatSampah` menggunakan keyword `extends`. Masing-masing menambahkan atribut unik: Organik memiliki `bisaMembusuk`, Anorganik memiliki `jenisMaterial`.

```java
// Inheritance: SampahOrganik mewarisi TempatSampah
public class SampahOrganik extends TempatSampah {
    private boolean bisaMembusuk;

    @Override  // Polimorfisme: Override method abstract
    public String getTipe() { return "Organik"; }

    @Override
    public String getDetailSpesifik() {
        return "Bisa Membusuk: " + (bisaMembusuk ? "Ya" : "Tidak");
    }
}
```

**Ketiga, `Node.java` & `LinkedListCustom.java` — Struktur Data Linked List Manual.**
Ini adalah inti dari sistem antrean. `Node` menyimpan satu objek `TempatSampah` dan referensi ke `Node` berikutnya. `LinkedListCustom` mengimplementasikan operasi queue:
- **`enqueue()`** → menambahkan node ke ekor (tail)
- **`dequeue()`** → menghapus node dari kepala (head) sesuai prinsip FIFO

```java
// Enqueue: Masukkan ke ekor Linked List
public void enqueue(TempatSampah data) {
    Node newNode = new Node(data);
    if (isEmpty()) { head = newNode; tail = newNode; }
    else { tail.next = newNode; tail = newNode; }
    size++;
}

// Dequeue: Hapus dari kepala (FIFO)
public TempatSampah dequeue() {
    TempatSampah removedData = head.data;
    head = head.next; // Geser head ke node berikutnya
    if (head == null) tail = null;
    size--;
    return removedData;
}
```

**Keempat, `TrashTrackApp.java` — Main GUI Application.**
Kelas utama yang membangun antarmuka grafis menggunakan **Java Swing**. Ketika tombol "Masukkan ke Antrean" diklik, sistem memanggil `enqueue()`. Ketika tombol "Angkut" diklik, sistem memanggil `dequeue()` dan menampilkan popup konfirmasi. Daftar antrean dirender ulang secara dinamis dengan menelusuri Linked List dari head ke tail.

---

## 🖥️ Bagian 4 — Menampilkan Output Aplikasi
> ⏱ *Estimasi baca: ± 45 detik*

---

Mari kita lihat bagaimana aplikasi ini bekerja secara nyata.

**Tampilan Utama Aplikasi:**
Saat dijalankan, aplikasi menampilkan dua panel berdampingan:
- **Panel Kiri:** Form input untuk mendaftarkan tempat sampah baru yang sudah penuh. Berisi field lokasi, spinner kapasitas (10–500 Liter), dropdown tipe, dan input dinamis yang berubah sesuai pilihan tipe sampah.
- **Panel Kanan:** Daftar antrean visual yang menampilkan setiap tempat sampah dalam bentuk kartu (card), dilengkapi ikon, ID unik, lokasi, kapasitas, dan tag status.

**Skenario Demo:**

**Langkah 1 — Tambah Data Pertama:**
Kita isi lokasi `"Taman Merdeka"`, kapasitas `100 Liter`, tipe `Organik`, centang *Dapat Membusuk*, lalu klik `Masukkan ke Antrean`. Sistem membuat `BIN-001` dengan ID otomatis dan muncul sebagai kartu pertama bertag **TERDEPAN** berwarna merah.

**Langkah 2 — Tambah Data Kedua:**
Kita isi `"Pasar Minggu"`, `150 Liter`, tipe `Anorganik`, material `Plastik`, klik tambah. `BIN-002` muncul di bawah dengan tag **MENUNGGU** berwarna hijau.

**Langkah 3 — Proses Pengangkutan (FIFO):**
Kita klik tombol merah `Angkut & Kosongkan Sampah Terdepan (FIFO)`. Sistem menjalankan `dequeue()`, menghapus `BIN-001`, dan menampilkan popup:

```
Petugas telah membersihkan & mengosongkan:
ID: BIN-001
Lokasi: Taman Merdeka
Kategori: Organik

Status: Sukses Dikosongkan!
```

`BIN-002` otomatis bergeser maju, naik ke posisi #1 dengan tag **TERDEPAN** — membuktikan bahwa prinsip FIFO berjalan dengan sempurna.

---

> ✅ **Demikian presentasi project TrashTrack.** Sistem ini membuktikan bahwa konsep struktur data Linked List dan prinsip OOP Java tidak hanya relevan secara akademis, tetapi juga dapat diimplementasikan untuk memecahkan masalah nyata di masyarakat yang selaras dengan tujuan pembangunan berkelanjutan.
>
> **Terima kasih!** 🎉
