public class oop {
    static Mahasiswa mhs = new Mahasiswa();

    static void cetakJudul() {
        System.out.println("----------------------");
        System.out.println("PROGRAM DATA MAHASISWA");
        System.out.println("----------------------");
    }

    public static void main(String[] args) {
        cetakJudul();
        mhs.isiData("Achmadillah Yusuf Faqih Febrianto", "25082010193", 3.7);
        mhs.cetakData();
    }
}

class Mahasiswa {
    static String name;
    static String npm;
    static double ipk;

    static void isiData(String nm, String np, double ip) {
        name = nm;
        npm = np;
        ipk = ip;
    }

    static void cetakData() {
        System.out.println("---- Data Mahasiswa ----");
        System.out.println("Nama : " + name);
        System.out.println("Npm : " + npm);
        System.out.println("Ipk : " + ipk);
    }
}