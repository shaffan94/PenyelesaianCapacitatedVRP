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
public class ModelCWSA {
    private double nilai_saving;
    private ModelDataPermintaan[] lokasi_terhubung;
            
    public ModelCWSA() {
        
    }
    
    public void setModelCWSA(double nilai_saving, ModelDataPermintaan[] lokasi_terhubung){
        this.nilai_saving = nilai_saving;
        this.lokasi_terhubung = lokasi_terhubung;
    }
    
    public void setS_CWSA(double nilai_saving){
        this.nilai_saving = nilai_saving;
    }
    
    public void setLokasiTerhubung(ModelDataPermintaan[] lokasi_terhubung){
        this.lokasi_terhubung = new ModelDataPermintaan[2];
        
        for(int i=0; i<this.lokasi_terhubung.length; i++){
            this.lokasi_terhubung[i] = lokasi_terhubung[i];
        }
    }
    
    public double getSCWSA(){
        return nilai_saving;
    }
    
    public ModelDataPermintaan[] getLokasiTerhubung(){
        return lokasi_terhubung;
    }
    
}
