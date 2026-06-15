// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: Node.java (Linked List Component)
// ============================================================

/**
 * Kelas Node untuk menampung objek TempatSampah.
 * Digunakan sebagai komponen pembentuk struktur data Linked List manual.
 */
public class Node {
    TempatSampah data; // Menyimpan data objek (TempatSampah)
    Node next;         // Pointer/Referensi ke Node berikutnya di antrean

    // Constructor inisialisasi node baru
    public Node(TempatSampah data) {
        this.data = data;
        this.next = null; // Awalnya menunjuk ke null
    }
}
