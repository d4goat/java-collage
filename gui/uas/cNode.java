package uas;

/**
 * Class cNode - Modul 22 (Linked List Custom)
 * Node generik yang menyimpan objek data dan referensi ke node berikutnya.
 * Menggunakan Java Generics agar dapat dipakai untuk semua tipe data.
 *
 * @param <T> Tipe data yang disimpan dalam node
 */
public class cNode<T> {

    // ============================================================
    // ATRIBUT - ENKAPSULASI (Modul 16)
    // ============================================================
    private T       data;   // Objek data yang disimpan
    private cNode<T> next;  // Pointer ke node berikutnya

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public cNode() {
        this.data = null;
        this.next = null;
    }

    public cNode(T data) {
        this.data = data;
        this.next = null;
    }

    // ============================================================
    // SETTER
    // ============================================================
    public void setData(T data)        { this.data = data; }
    public void setNext(cNode<T> next) { this.next = next; }

    // ============================================================
    // GETTER
    // ============================================================
    public T        getData() { return data; }
    public cNode<T> getNext() { return next; }
}
