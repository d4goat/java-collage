class prosedural {
    static String name;
    static String npm;
    static double ipk;

    static void cetakJudul() {
        System.out.println("----------------------");
        System.out.println("PROGRAM DATA MAHASISWA");
        System.out.println("----------------------");
    }

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

    public static void main(String[] args) {
        cetakJudul();
        isiData("Achmadillah Yusuf Faqih Febrianto", "25082010193", 3.7);
        cetakData();
    }
}