import java.util.ArrayList;
import java.util.Scanner;

public class GoDriveRentalSystem {
    private ArrayList<Kendaraan> daftarKendaraan;

    public GoDriveRentalSystem() {
        daftarKendaraan = new ArrayList<>();
    }

    public void tambahKendaraan(Kendaraan k) {
        daftarKendaraan.add(k);
        System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + k.getNamaKendaraan() + " (" + k.getKodeKendaraan() + ")");
    }

    public void tampilkanDaftarKendaraan() {
        System.out.println("\n=== DAFTAR ARMADA GODRIVE ===");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan dalam sistem.");
            return;
        }
        int nomor = 1;
        for (Kendaraan k : daftarKendaraan) {
            String tipe = (k instanceof Mobil) ? "[MOBIL]" : "[MOTOR]";
            System.out.print(nomor + ". " + tipe + " ");
            k.tampilInfo();
            nomor++;
        }
    }

    public void sewaKendaraan(String kode, int lamaSewa, boolean isVIP) throws KendaraanTidakTersediaException {
        Kendaraan target = null;
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kode)) {
                target = k;
                break;
            }
        }

        // Validasi ketersediaan
        if (target == null || !target.isTersedia()) {
            throw new KendaraanTidakTersediaException("Kendaraan dengan kode " + kode + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan!");
        }

        // Mengubah status menjadi tidak tersedia
        target.setTersedia(false);

        // Perhitungan Biaya
        double biayaDasar = target.hitungBiayaDasar(lamaSewa);
        double biayaTambahan = 0;
        
        if (target instanceof Mobil) {
            Mobil m = (Mobil) target;
            if (m.getJumlahKursi() > 5) {
                biayaTambahan = 50000; // Biaya perawatan flat
            }
        } else if (target instanceof Motor) {
            Motor mot = (Motor) target;
            if (mot.getJenisTransmisi().equalsIgnoreCase("Matik") || mot.getJenisTransmisi().equalsIgnoreCase("Matic")) {
                biayaTambahan = 10000 * lamaSewa; // Asuransi per hari
            }
        }

        double subTotal = biayaDasar + biayaTambahan;
        
        // Logika Fitur Tambahan (Diskon)
        double diskonVIP = 0;
        if (isVIP) {
            diskonVIP = subTotal * 0.10; // Diskon 10% member VIP
        }
        
        double diskonDurasi = 0;
        if (lamaSewa > 7) {
            diskonDurasi = subTotal * 0.05; // Tambahan diskon durasi lama > 7 hari (5%)
        }
        
        double totalDiskon = diskonVIP + diskonDurasi;
        double totalBiayaAkhir = subTotal - totalDiskon;

        // Cetak Detail Transaksi
        System.out.println("\n=== TRANSAKSI SEWA GODRIVE ===");
        System.out.println("Kendaraan Berhasil Disewa!");
        System.out.println("Unit               : " + target.getNamaKendaraan() + " (" + target.getKodeKendaraan() + ")");
        System.out.println("Lama Sewa          : " + lamaSewa + " hari");
        System.out.printf("Biaya Dasar Harian : Rp %,.0f\n", biayaDasar);
        
        if (target instanceof Mobil) {
            System.out.printf("Tambahan Kursi (>5): Rp %,.0f\n", biayaTambahan);
        } else {
            System.out.printf("Tambahan Asuransi  : Rp %,.0f\n", biayaTambahan);
        }
        
        if (diskonVIP > 0) {
            System.out.printf("Diskon Member VIP (10%%): -Rp %,.0f\n", diskonVIP);
        }
        if (diskonDurasi > 0) {
            System.out.printf("Diskon Sewa > 7 Hari (5%%): -Rp %,.0f\n", diskonDurasi);
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL BIAYA AKHIR: Rp %,.0f\n", totalBiayaAkhir);
    }

    public void kembalikanKendaraan(String kode) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kode)) {
                if (!k.isTersedia()) {
                    k.setTersedia(true);
                    System.out.println("[INFO] Kendaraan " + k.getNamaKendaraan() + " (" + k.getKodeKendaraan() + ") berhasil dikembalikan. Status: Tersedia.");
                    return;
                } else {
                    System.out.println("[INFO] Kendaraan sudah berstatus tersedia di garasi.");
                    return;
                }
            }
        }
        System.out.println("[ERROR] Kode kendaraan tidak ditemukan.");
    }

    // MAIN METHOD (Console Interface)
    public static void main(String[] args) {
        GoDriveRentalSystem sistem = new GoDriveRentalSystem();
        Scanner input = new Scanner(System.in);
        
        // Data Awal Sesuai Gambar Contoh Output
        sistem.daftarKendaraan.add(new Mobil("MBL01", "Toyota Avanza", 350000, 7));
        sistem.daftarKendaraan.add(new Mobil("MBL02", "Daihatsu Sigra", 300000, 7));
        sistem.daftarKendaraan.add(new Mobil("MBL03", "Honda Brio", 280000, 5));
        sistem.daftarKendaraan.add(new Motor("MTR01", "Honda Vario", 80000, "Matik"));
        sistem.daftarKendaraan.add(new Motor("MTR02", "Yamaha NMAX", 100000, "Matik"));
        sistem.daftarKendaraan.add(new Motor("MTR03", "Kawasaki KLX", 90000, "Manual"));

        int pilihan = 0;
        do {
            System.out.println("\n======= MENU GO DRIVE RENTAL SYSTEM =======");
            System.out.println("1. Tambah Kendaraan");
            System.out.println("2. Tampilkan Daftar Armada");
            System.out.println("3. Sewa Kendaraan");
            System.out.println("4. Kembalikan Kendaraan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            
            try {
                pilihan = Integer.parseInt(input.nextLine());
                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan jenis kendaraan (mobil/motor): ");
                        String jenis = input.nextLine();
                        System.out.print("Masukkan kode kendaraan: ");
                        String kode = input.nextLine();
                        System.out.print("Masukkan nama kendaraan: ");
                        String nama = input.nextLine();
                        System.out.print("Masukkan harga sewa per hari: ");
                        double harga = Double.parseDouble(input.nextLine());

                        if (jenis.equalsIgnoreCase("mobil")) {
                            System.out.print("Masukkan kapasitas kursi: ");
                            int kursi = Integer.parseInt(input.nextLine());
                            sistem.tambahKendaraan(new Mobil(kode, nama, harga, kursi));
                        } else if (jenis.equalsIgnoreCase("motor")) {
                            System.out.print("Masukkan jenis transmisi (Matik/Manual): ");
                            String transmisi = input.nextLine();
                            sistem.tambahKendaraan(new Motor(kode, nama, harga, transmisi));
                        } else {
                            System.out.println("[ERROR] Jenis kendaraan tidak valid.");
                        }
                        break;

                    case 2:
                        sistem.tampilkanDaftarKendaraan();
                        break;

                    case 3:
                        System.out.print("Masukkan kode kendaraan yang ingin disewa: ");
                        String kodeSewa = input.nextLine();
                        System.out.print("Masukkan durasi sewa (dalam hari): ");
                        int durasi = Integer.parseInt(input.nextLine());
                        System.out.print("Apakah Anda Member VIP? (y/n): ");
                        String vipInput = input.nextLine();
                        boolean isVIP = vipInput.equalsIgnoreCase("y");

                        try {
                            sistem.sewaKendaraan(kodeSewa, durasi, isVIP);
                        } catch (KendaraanTidakTersediaException e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace(); // Menampilkan stack trace persis seperti pada gambar instruksi
                        }
                        break;

                    case 4:
                        System.out.print("Masukkan kode kendaraan yang ingin dikembalikan: ");
                        String kodeKembali = input.nextLine();
                        sistem.kembalikanKendaraan(kodeKembali);
                        break;

                    case 5:
                        System.out.println("Terima kasih telah menggunakan sistem Go Drive!");
                        break;

                    default:
                        System.out.println("Pilihan tidak valid, silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Harap masukkan input angka yang valid.");
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan sistem: " + e.getMessage());
            }
        } while (pilihan != 5);

        input.close();
    }
}