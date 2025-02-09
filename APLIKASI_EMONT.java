package project_emont;

import java.util.ArrayList;
import java.util.Scanner;

public class APLIKASI_EMONT {

    static ArrayList<String> tanggal = new ArrayList<>();
    static ArrayList<String> kategori = new ArrayList<>();
    static ArrayList<Double> jumlah = new ArrayList<>();
    static double saldo = 0;
    private static Scanner input; // Scanner input

    public static void main(String[] args) {
        input = new Scanner(System.in); // Scanner diinisialisasi di sini

        System.out.print("Masukkan saldo awal atau pemasukan bulanan: ");
        saldo = input.nextDouble(); // Menyimpan saldo awal dari pemasukan bulanan
        
        System.out.print("Masukkan jumlah kebutuhan: ");
        int jumlahKebutuhan = input.nextInt();
        String[] kebutuhan = new String[jumlahKebutuhan];
        double[] persentase = new double[jumlahKebutuhan];

        double totalPersentase = 0;
        for (int i = 0; i < jumlahKebutuhan; i++) {
            System.out.print("Masukkan nama kebutuhan " + (i + 1) + ": ");
            kebutuhan[i] = input.next();
            System.out.print("Masukkan persentase kebutuhan " + kebutuhan[i] + ": ");
            persentase[i] = input.nextDouble();
            totalPersentase += persentase[i];
        }

        if (totalPersentase < 100) {
            double sisaPersentase = 100 - totalPersentase;
            System.out.printf("Sisa persentase sebesar %.2f%% akan dialokasikan ke dana darurat.%n", sisaPersentase);
            // Tambahkan dana darurat ke daftar kebutuhan
            jumlahKebutuhan++;
            String[] kebutuhanBaru = new String[jumlahKebutuhan];
            double[] persentaseBaru = new double[jumlahKebutuhan];

            // Salin data lama
            System.arraycopy(kebutuhan, 0, kebutuhanBaru, 0, kebutuhan.length);
            System.arraycopy(persentase, 0, persentaseBaru, 0, persentase.length);

            // Tambahkan dana darurat
            kebutuhanBaru[jumlahKebutuhan - 1] = "dana darurat";
            persentaseBaru[jumlahKebutuhan - 1] = sisaPersentase;

            // Update array
            kebutuhan = kebutuhanBaru;
            persentase = persentaseBaru;
        }

        System.out.println("\nRincian Pengeluaran Maksimal:");
        for (int i = 0; i < jumlahKebutuhan; i++) {
            double maksimalPengeluaran = (persentase[i] / 100) * saldo;
            System.out.printf("%s: Rp %.2f%n", kebutuhan[i], maksimalPengeluaran);
        }

        boolean selesai = false;

        while (!selesai) {
            System.out.println("\nMenu:");
            System.out.println("1. Input Transaksi Harian");
            System.out.println("2. Lihat Rekap Bulanan");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    inputTransaksi(input);
                    break;
                case 2:
                    lihatRekapBulanan();
                    break;
                case 3:
                    selesai = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void inputTransaksi(Scanner input) {
        System.out.print("Masukkan tanggal (dd/mm/yyyy): ");
        String tgl = input.next();
        tanggal.add(tgl);

        System.out.print("Masukkan kategori (Pemasukan/Pengeluaran): ");
        String kat = input.next();
        kategori.add(kat);

        System.out.print("Masukkan jumlah: ");
        double jumlahTransaksi = input.nextDouble();
        jumlah.add(jumlahTransaksi);

        // Tambahkan atau kurangkan dari saldo
        if (kat.equalsIgnoreCase("Pemasukan")) {
            saldo += jumlahTransaksi;
        } else if (kat.equalsIgnoreCase("Pengeluaran")) {
            saldo -= jumlahTransaksi;
        }

        System.out.println("Transaksi berhasil dicatat. Sisa saldo: Rp " + saldo);
    }

    private static void lihatRekapBulanan() {
        System.out.println("\nRekap Transaksi Bulanan:");
        for (int i = 0; i < tanggal.size(); i++) {
            System.out.println(tanggal.get(i) + "\t|\t" + kategori.get(i) + "\t|\t Rp " + jumlah.get(i));
        }
        System.out.println("Saldo akhir\t:\t Rp " + saldo);
    }
}
