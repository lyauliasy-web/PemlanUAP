public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String kode, String nama, double hargaSewa, String jenisTransmisi) {
        super(kode, nama, hargaSewa);
        this.jenisTransmisi = jenisTransmisi;
    }

    public String getJenisTransmisi() {
        return jenisTransmisi;
    }

    public void setJenisTransmisi(String jenisTransmisi) {
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public void tampilInfo() {
        String statusStr = isTersedia() ? "Tersedia" : "Tidak Tersedia";
        System.out.printf("Kode: %-6s | Nama: %-20s | Transmisi: %-6s | Tarif: Rp%,-12.0f | Status: %s\n",
                getKodeKendaraan(), getNamaKendaraan(), jenisTransmisi, getHargaSewaPerHari(), statusStr);
    }

    @Override
    public double hitungBiayaDasar(int lamaSewa) {
        return lamaSewa * getHargaSewaPerHari();
    }
}