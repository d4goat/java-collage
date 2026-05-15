import java.util.Scanner;

public class linkedList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        cNode node = null;
        cLinkedList LL = new cLinkedList(null);
        int pilih = 0;
        do {
            System.out.println("\nMENU LINKED LIST");
            System.out.println("1. Tambah Node");
            System.out.println("2. Hapus Node");
            System.out.println("3. Cari Data");
            System.out.println("4. Cetak Linked List");
            System.out.println("5. Exit");
            System.out.print("Pilih = ");
            pilih = sc.nextInt();
            switch (pilih) {
                case 1:
                    System.out.print("Masukkan Data = ");
                    int d = sc.nextInt();
                    node = new cNode(d);
                    LL.addNode(node);
                    break;
                case 2:
                    LL.delNode();
                    break;
                case 3:
                    System.out.print("Masukkan Data yang dicari = ");
                    d = sc.nextInt();
                    LL.find(d);
                    break;
                case 4:
                    LL.print();
                    break;
                case 5:
                    System.out.println("Terima kasih...");
                    break;
            }
        } while (pilih != 5);
        sc.close();
    }
}

class cNode {
    private int data;
    private cNode next;

    cNode(int d) {
        data = d;
        next = null;
    }

    public void setData(int d) {
        data = d;
    }

    public void setNext(cNode n) {
        next = n;
    }

    public int getData() {
        return data;
    }

    public cNode getNext() {
        return next;
    }
}

class cLinkedList {
    private cNode header;
    private int jNode;

    cLinkedList(cNode n) {
        header = n;
        jNode = 0;
    }

    // Tambah simpul di depan
    public void addNode(cNode baru) {
        if (header == null) {
            header = baru;
        } else {
            baru.setNext(header);
            header = baru;
        }
        jNode++;
        System.out.println("Penambahan simpul...");
    }

    // Hapus simpul di depan
    public int delNode() {
        int temp = 0;
        if (header != null) {
            cNode t = header.getNext(); // simpan simpul kedua
            temp = header.getData();
            header.setNext(null); // putuskan header lama
            header = t; // update header
            jNode--;
            System.out.println("Penghapusan simpul...");
        } else {
            System.out.println("Linked list kosong!");
        }
        return temp;
    }

    // Cetak isi linked list
    public void print() {
        System.out.print("Isi Linked List: ");
        for (cNode t = header; t != null; t = t.getNext()) {
            System.out.print(t.getData() + " ");
        }
        System.out.println("");
    }

    // Cari data dalam linked list
    public void find(int f) {
        boolean ada = false;
        System.out.print("Hasil pencarian: ");
        for (cNode t = header; t != null; t = t.getNext()) {
            if (t.getData() == f) {
                ada = true;
                break;
            }
        }
        if (!ada) {
            System.out.println("Tidak ditemukan!");
        } else {
            System.out.println("Ditemukan...");
        }
        System.out.println("");
    }
}