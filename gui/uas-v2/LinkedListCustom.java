// ============================================================
// TrashTrack - Sistem Antrean Tempat Sampah Pintar (SDG 9)
// file: LinkedListCustom.java (Queue FIFO Implementation)
// ============================================================

/**
 * Struktur data Linked List manual yang bertindak sebagai Queue (FIFO).
 * Objek baru masuk di akhir (tail) dan dibersihkan dari awal (head).
 */
public class LinkedListCustom {
    private Node head; // Elemen terdepan antrean
    private Node tail; // Elemen terakhir antrean
    private int size;  // Menyimpan jumlah antrean aktif

    public LinkedListCustom() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Memasukkan tempat sampah baru ke dalam antrean (Enqueue ke Tail).
     */
    public void enqueue(TempatSampah data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode; // Sambungkan node lama paling belakang ke node baru
            tail = newNode;      // Perbarui penunjuk ekor (tail) ke node baru
        }
        size++;
    }

    /**
     * Mengeluarkan/mengangkut sampah terdepan dari antrean (Dequeue dari Head).
     * Menerapkan prinsip First-In, First-Out (FIFO).
     */
    public TempatSampah dequeue() {
        if (isEmpty()) {
            return null; // Return null jika antrean kosong
        }
        TempatSampah removedData = head.data; // Simpan data terdepan
        head = head.next;                     // Pindahkan head ke node setelahnya
        
        if (head == null) {
            tail = null;                      // Jika antrean menjadi kosong, kosongkan tail juga
        }
        size--;
        return removedData;
    }

    /**
     * Mengintip data terdepan antrean tanpa menghapusnya.
     */
    public TempatSampah peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    // Mengecek apakah antrean kosong
    public boolean isEmpty() {
        return head == null;
    }

    // Mendapatkan ukuran antrean saat ini
    public int getSize() {
        return size;
    }

    // Mendapatkan Node terdepan (untuk visualisasi looping/perulangan)
    public Node getHead() {
        return head;
    }

    // Mengosongkan seluruh antrean
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
