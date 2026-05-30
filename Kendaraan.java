public abstract class Kendaraan {
    private String kodeKendaraan;
    private String namaKendaraan;
    private double hargaSewaPerHari;
    private boolean isTersedia;

    public Kendaraan(String kode, String nama, double hargaSewa) {
        this.kodeKendaraan = kode;
        this.namaKendaraan = nama;
        this.hargaSewaPerHari = hargaSewa;
        this.isTersedia = true; // Default selalu tersedia saat ditambahkan
    }

    public String getKodeKendaraan() {
        return kodeKendaraan;
    }

    public void setKodeKendaraan(String kodeKendaraan) {
        this.kodeKendaraan = kodeKendaraan;
    }

    public String getNamaKendaraan() {
        return namaKendaraan;
    }

    public void setNamaKendaraan(String namaKendaraan) {
        this.namaKendaraan = namaKendaraan;
    }

    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public void setHargaSewaPerHari(double hargaSewaPerHari) {
        this.hargaSewaPerHari = hargaSewaPerHari;
    }

    public boolean isTersedia() {
        return isTersedia;
    }

    public void setTersedia(boolean status) {
        this.isTersedia = status;
    }

    public abstract void tampilInfo();
    
    public abstract double hitungBiayaDasar(int lamaSewa);
}