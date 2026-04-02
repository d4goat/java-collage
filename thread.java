import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class thread {
    // Variabel bersama untuk menghitung jumlah thread yang telah selesai
    private static int completedThreads = 0;
    // Object monitor untuk sinkronisasi
    private static final Object lock = new Object();

    // Thread 1: Menghitung faktorial
    static class FactorialTask implements Runnable {
        private int n;

        public FactorialTask(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            System.out.println("Faktorial dari " + n + " = " + result);

            // Sinkronisasi: update counter bersama
            synchronized (lock) {
                completedThreads++;
                System.out.println("Thread faktorial selesai. Total selesai: " + completedThreads);
            }
        }
    }

    // Thread 2: Menampilkan deret fibonacci
    static class FibonacciTask implements Runnable {
        private int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            int a = 0, b = 1;
            System.out.print("Deret Fibonacci hingga " + n + ": ");
            for (int i = 0; i < n; i++) {
                System.out.print(a + " ");
                int next = a + b;
                a = b;
                b = next;
            }
            System.out.println();

            synchronized (lock) {
                completedThreads++;
                System.out.println("Thread fibonacci selesai. Total selesai: " + completedThreads);
            }
        }
    }

    // Thread 3: Membaca file teks
    static class FileReaderTask implements Runnable {
        private String filename;

        public FileReaderTask(String filename) {
            this.filename = filename;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                List<String> line = Files.readAllLines(Path.of(filename));
                int index = 1;
                System.out.println("Isi file " + filename + ":");

                for (String b : line) {
                    System.out.println(index + ". " + b);
                    index++;
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error membaca file: " + e.getMessage());
            }

            synchronized (lock) {
                completedThreads++;
                System.out.println("Thread file reader selesai. Total selesai: " + completedThreads);
            }
        }
    }

    public static void main(String[] args) {
        // Buat dan jalankan ketiga thread
        Thread t1 = new Thread(new FactorialTask(5));
        Thread t2 = new Thread(new FibonacciTask(10));
        Thread t3 = new Thread(new FileReaderTask("data.txt"));
        t1.start();
        t2.start();
        t3.start();

        // Tunggu semua thread selesai
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Semua thread selesai. Total threads selesai: " + completedThreads);
    }
}