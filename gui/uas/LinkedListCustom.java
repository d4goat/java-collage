package uas;

import java.util.ArrayList;

/**
 * Class LinkedListCustom - Modul 22 (Struktur Data Linked List Manual)
 * Implementasi Linked List tunggal (Singly Linked List) secara manual
 * tanpa menggunakan java.util.LinkedList bawaan Java.
 *
 * @param <T> Tipe data yang disimpan dalam linked list
 */
public class LinkedListCustom<T> {

    // ============================================================
    // ATRIBUT - pointer head dan ukuran list
    // ============================================================
    private cNode<T> head;  // Pointer ke node pertama
    private int      size;  // Jumlah elemen dalam list

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public LinkedListCustom() {
        this.head = null;
        this.size = 0;
    }

    // ============================================================
    // OPERASI TAMBAH NODE - tambahNode()
    // ============================================================

    /**
     * Menambahkan node baru di akhir linked list (tail insertion).
     * @param data Objek data yang akan ditambahkan
     */
    public void tambahNode(T data) {
        cNode<T> nodeBaru = new cNode<>(data);

        if (head == null) {
            // List kosong - node baru menjadi head
            head = nodeBaru;
        } else {
            // Traverse ke node terakhir
            cNode<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            // Hubungkan node terakhir ke node baru
            current.setNext(nodeBaru);
        }
        size++;
    }

    /**
     * Menghapus node berdasarkan indeks (0-based).
     * @param index Indeks node yang akan dihapus
     * @return true jika berhasil dihapus, false jika indeks tidak valid
     */
    public boolean hapusNode(int index) {
        if (index < 0 || index >= size || head == null) return false;

        if (index == 0) {
            head = head.getNext();
        } else {
            cNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            current.setNext(current.getNext().getNext());
        }
        size--;
        return true;
    }

    /**
     * Mengambil data pada indeks tertentu.
     * @param index Indeks node (0-based)
     * @return Data di node tersebut, atau null jika tidak valid
     */
    public T getData(int index) {
        if (index < 0 || index >= size) return null;
        cNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Mencetak seluruh isi linked list ke konsol.
     */
    public void cetak() {
        if (head == null) {
            System.out.println("(List kosong)");
            return;
        }
        cNode<T> current = head;
        int idx = 1;
        while (current != null) {
            System.out.println(idx + ". " + current.getData().toString());
            current = current.getNext();
            idx++;
        }
    }

    /**
     * Mengembalikan seluruh elemen linked list sebagai ArrayList.
     * Berguna untuk ditampilkan di komponen GUI.
     * @return ArrayList berisi semua data dalam linked list
     */
    public ArrayList<T> getDaftar() {
        ArrayList<T> daftar = new ArrayList<>();
        cNode<T> current = head;
        while (current != null) {
            daftar.add(current.getData());
            current = current.getNext();
        }
        return daftar;
    }

    /**
     * Mengecek apakah list kosong.
     */
    public boolean isEmpty() { return head == null; }

    /**
     * Mengembalikan jumlah elemen dalam list.
     */
    public int getSize() { return size; }
}
