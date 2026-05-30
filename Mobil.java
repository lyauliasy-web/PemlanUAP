public class Mobil extends Kendaraan {
    private int jumlahKursi;

    public Mobil(String kode, String nama, double hargaSewa, int jumlahKursi) {
        super(kode, nama, hargaSewa);
        this.jumlahKursi = jumlahKursi;
    }

    public int getJumlahKursi() {
        return jumlahKursi;
    }

    public void setJumlahKursi(int jumlahKursi) {
        this.jumlahKursi = jumlahKursi;
    }

    @Override
    public void tampilInfo() {
        String statusStr = isTersedia() ? "Tersedia" : "Tidak Tersedia";
        System.out.printf("Kode: %-6s | Nama: %-20s | Kursi: %-2d | Tarif: Rp%,-12.0f | Status: %s\n",
                getKodeKendaraan(), getNamaKendaraan(), jumlahKursi, getHargaSewaPerHari(), statusStr);
    }

    @Override
    public double hitungBiayaDasar(int lamaSewa) {
        return lamaSewa * getHargaSewaPerHari();
    }
}