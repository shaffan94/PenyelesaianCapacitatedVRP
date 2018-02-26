/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penyelesaiancapacitatedvrp.Entity;

/**
 *
 * @author digitalcreative
 */
public class ModelDataPermintaan {
    private String nama_customer;
    private String nama_jalan;
    private int kode_jalan;
    private double longitude;
    private double latitude;
    private int jumlah_permintaan;
    
    public ModelDataPermintaan() {
        //constructor for model data permintaan
    }

    public ModelDataPermintaan(String nama_customer, String nama_jalan, int kode_jalan, double longitude, double latitude, int jumlah_permintaan) {
        this.nama_customer = nama_customer;
        this.nama_jalan = nama_jalan;
        this.kode_jalan = kode_jalan;
        this.longitude = longitude;
        this.latitude = latitude;
        this.jumlah_permintaan = jumlah_permintaan;
    }
    
    public void setNamaCustomer(String nama_customer){
        this.nama_customer = nama_customer;
    }
    
    public void setNamaJalan(String nama_jalan){
        this.nama_jalan = nama_jalan;
    }
    
    public void setKodeJalan(int kode_jalan){
        this.kode_jalan = kode_jalan;
    }
    
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    
    public void setJumlahPermintaan(int jumlah_permintaan){
        this.jumlah_permintaan = jumlah_permintaan;
    }
    
    public String getNamaCustomer(){
        return nama_customer;
    }
    
    public String getNamaJalan(){
        return nama_jalan;
    }
    
    public int getKodeJalan(){
        return kode_jalan;
    }
    
    public double getLongitude(){
        return longitude;
    }
    
    public double getLatitude(){
        return latitude;
    }
    
    public int getJumlahPermintaan(){
        return jumlah_permintaan;
    }
}
