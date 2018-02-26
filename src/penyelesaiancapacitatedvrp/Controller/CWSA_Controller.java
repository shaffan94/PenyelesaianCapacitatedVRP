/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penyelesaiancapacitatedvrp.Controller;

import java.util.ArrayList;
import penyelesaiancapacitatedvrp.Entity.ModelCWSA;
import penyelesaiancapacitatedvrp.Entity.ModelDataPermintaan;
import penyelesaiancapacitatedvrp.Entity.ModelRuteKendaraan;

/**
 *
 * @author shaff
 */
public class CWSA_Controller {
    private double[] m_lokasi_permintaan_depot;
    
    public CWSA_Controller(){}
    
    public double[][] bentuk_matriks_lokasi_permintaan(ArrayList<ModelDataPermintaan> dataPermintaanList, ModelDataPermintaan depot){        
        int panjang_matriks = dataPermintaanList.size();
        
        double[][] m_lokasi_permintaan = new double[panjang_matriks][panjang_matriks];
        m_lokasi_permintaan_depot = new double[panjang_matriks];
        
        for(int i=0; i<panjang_matriks; i++){
            for(int j=0; j<panjang_matriks; j++){
                if(i == j){
                    m_lokasi_permintaan[i][j] = 0;
                }
                else{
                    m_lokasi_permintaan[i][j] = cari_jarak_euclidean(dataPermintaanList.get(i), dataPermintaanList.get(j));
                }
            }
        }
        
        for(int i=0; i<panjang_matriks; i++){
            m_lokasi_permintaan_depot[i] = cari_jarak_euclidean(depot, dataPermintaanList.get(i));
        }
        
        return m_lokasi_permintaan;
    }
    
    public double cari_jarak_euclidean(ModelDataPermintaan lokasiPermintaan1, ModelDataPermintaan lokasiPermintaan2){
        double d_euc;

        double rlat1 = Math.PI*lokasiPermintaan1.getLatitude()/180;
        double rlat2 = Math.PI*lokasiPermintaan2.getLatitude()/180;
        double theta = lokasiPermintaan1.getLongitude() - lokasiPermintaan2.getLongitude();
        double rtheta = Math.PI*theta/180;
        double dist = Math.sin(rlat1)*Math.sin(rlat2) + Math.cos(rlat1)*Math.cos(rlat2)*Math.cos(rtheta);
        dist = Math.acos(dist);
        dist = dist*180/Math.PI;
        dist = dist*60*1.1515;

        d_euc = dist*1.609344;
        
        return d_euc;
    }
    
    public ArrayList<ModelCWSA> bentuk_matriks_cwsa(double[][] m_lokasi_permintaan, ArrayList<ModelDataPermintaan> dataPermintaanList){
        ArrayList<ModelCWSA> m_cwsa;
        ModelCWSA cwsa;
        double s_cwsa;
        
        ModelDataPermintaan[] model_data_terhubung = new ModelDataPermintaan[2];
        int panjang_matriks = m_lokasi_permintaan.length;
        m_cwsa = new ArrayList<>();
        
        for(int i=0; i<panjang_matriks; i++){
            for(int j=i+1; j<panjang_matriks; j++){
                cwsa = new ModelCWSA();
                
                model_data_terhubung[0] = dataPermintaanList.get(i);
                model_data_terhubung[1] = dataPermintaanList.get(j);
                
                s_cwsa = m_lokasi_permintaan_depot[i] + m_lokasi_permintaan_depot[j] - m_lokasi_permintaan[i][j];
                
                cwsa.setS_CWSA(s_cwsa);
                cwsa.setLokasiTerhubung(model_data_terhubung);
                
                m_cwsa.add(cwsa);
            }
        }
        
        return m_cwsa;
    }  
    
    public ArrayList<ModelDataPermintaan> pengurutan_nilai_penghematan(ArrayList<ModelCWSA> m_cwsa, int nData){
        ArrayList<ModelDataPermintaan> rute;
        ArrayList<ModelCWSA> m_cwsa_temp;
        ModelCWSA cwsa;
        ModelDataPermintaan lokasi_pertama, lokasi_kedua, temp1, temp2;
        int i, data_ke;
        double max;
        
        m_cwsa_temp = new ArrayList<>();
        
        for(i=0; i<m_cwsa.size(); i++){
           cwsa = new ModelCWSA();
           cwsa.setModelCWSA(m_cwsa.get(i).getSCWSA(), m_cwsa.get(i).getLokasiTerhubung());
           
           m_cwsa_temp.add(cwsa);
        }
        
        rute = new ArrayList<>();
        while(!m_cwsa_temp.isEmpty()){
            max = -1000;
            data_ke = -1;
            temp1 = new ModelDataPermintaan();
            temp2 = new ModelDataPermintaan();
            
            if(rute.isEmpty()){
                for(i=0; i<m_cwsa_temp.size(); i++){
                    if(max <= m_cwsa_temp.get(i).getSCWSA()){
                        max = m_cwsa_temp.get(i).getSCWSA();
                        temp1 = m_cwsa_temp.get(i).getLokasiTerhubung()[0];
                        temp2 = m_cwsa_temp.get(i).getLokasiTerhubung()[1];
                        data_ke = i;
                    }
                }
                
                if(data_ke != -1){
                    lokasi_pertama = new ModelDataPermintaan();
                    lokasi_kedua = new ModelDataPermintaan();
                    
                    lokasi_pertama.setNamaCustomer(temp1.getNamaCustomer());
                    lokasi_pertama.setNamaJalan(temp1.getNamaJalan());
                    lokasi_pertama.setKodeJalan(temp1.getKodeJalan());
                    lokasi_pertama.setLongitude(temp1.getLongitude());
                    lokasi_pertama.setLatitude(temp1.getLatitude());
                    lokasi_pertama.setJumlahPermintaan(temp1.getJumlahPermintaan());
                    
                    lokasi_kedua.setNamaCustomer(temp2.getNamaCustomer());
                    lokasi_kedua.setNamaJalan(temp2.getNamaJalan());
                    lokasi_kedua.setKodeJalan(temp2.getKodeJalan());
                    lokasi_kedua.setLongitude(temp2.getLongitude());
                    lokasi_kedua.setLatitude(temp2.getLatitude());
                    lokasi_kedua.setJumlahPermintaan(temp2.getJumlahPermintaan());
                    
                    rute.add(lokasi_pertama);
                    rute.add(lokasi_kedua);

                    m_cwsa_temp.remove(data_ke);
                }
            }
            else{
                int flag_1 = 1;
                int flag_2 = 1;
                
                for(i=0; i<m_cwsa_temp.size(); i++){
                    if(max < m_cwsa_temp.get(i).getSCWSA()){
                        max = m_cwsa_temp.get(i).getSCWSA();
                        temp1 = m_cwsa_temp.get(i).getLokasiTerhubung()[0];
                        temp2 = m_cwsa_temp.get(i).getLokasiTerhubung()[1];
                        data_ke = i;
                    }
                }
                
                if(data_ke != -1){
                    for(i=0; i<rute.size(); i++){
                        if(rute.get(i).getKodeJalan() == temp1.getKodeJalan()){
                            flag_1 = 0;
                        }
                        
                        if(rute.get(i).getKodeJalan() == temp2.getKodeJalan()){
                            flag_2 = 0;
                        }
                    }
                    
                    if(flag_1 == 1){
                        lokasi_pertama = new ModelDataPermintaan();
                       
                        lokasi_pertama.setNamaCustomer(temp1.getNamaCustomer());
                        lokasi_pertama.setNamaJalan(temp1.getNamaJalan());
                        lokasi_pertama.setKodeJalan(temp1.getKodeJalan());
                        lokasi_pertama.setLongitude(temp1.getLongitude());
                        lokasi_pertama.setLatitude(temp1.getLatitude());
                        lokasi_pertama.setJumlahPermintaan(temp1.getJumlahPermintaan());
                        
                        rute.add(lokasi_pertama);
                    }
                    
                    if(flag_2 == 1){
                        lokasi_kedua = new ModelDataPermintaan();
                        
                        lokasi_kedua.setNamaCustomer(temp2.getNamaCustomer());
                        lokasi_kedua.setNamaJalan(temp2.getNamaJalan());
                        lokasi_kedua.setKodeJalan(temp2.getKodeJalan());
                        lokasi_kedua.setLongitude(temp2.getLongitude());
                        lokasi_kedua.setLatitude(temp2.getLatitude());
                        lokasi_kedua.setJumlahPermintaan(temp2.getJumlahPermintaan());
                        
                        rute.add(lokasi_kedua);
                    }

                    m_cwsa_temp.remove(data_ke);
                }
            }
        }
        
        return rute;
    }

    public ModelRuteKendaraan pencarian_rute(ArrayList<ModelDataPermintaan> rute, ArrayList<ModelDataPermintaan> ruteSaved, int limitKarton) {
        int limit = limitKarton;
        int i, j, flag, currentLimit = 0;
        ModelRuteKendaraan ruteKendaraan;
        
        ArrayList<ModelDataPermintaan> ruteSearched;
        ArrayList<ModelDataPermintaan> ruteMobile1Searched;
        ArrayList<ModelDataPermintaan> ruteMobile2Searched;
        ArrayList<ModelDataPermintaan> ruteAbandoned;
        
        ruteSearched = new ArrayList<>();
        ruteMobile1Searched = new ArrayList<>();
        ruteMobile2Searched = new ArrayList<>();
        ruteAbandoned = new ArrayList<>();
        ruteKendaraan = new ModelRuteKendaraan();
        
        if(!ruteSaved.isEmpty()){
            for(i=0; i<ruteSaved.size(); i++){
                currentLimit += ruteSaved.get(i).getJumlahPermintaan();

                ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                dataPermintaan.setNamaCustomer(ruteSaved.get(i).getNamaCustomer());
                dataPermintaan.setNamaJalan(ruteSaved.get(i).getNamaJalan());
                dataPermintaan.setKodeJalan(ruteSaved.get(i).getKodeJalan());
                dataPermintaan.setLongitude(ruteSaved.get(i).getLongitude());
                dataPermintaan.setLatitude(ruteSaved.get(i).getLatitude());
                dataPermintaan.setJumlahPermintaan(ruteSaved.get(i).getJumlahPermintaan());

                ruteSearched.add(dataPermintaan);
            }
        }
        
        flag = 0;
        
        for(i=0; i<rute.size(); i++){
            if((rute.get(i).getJumlahPermintaan()+currentLimit) <= 150){
                currentLimit += rute.get(i).getJumlahPermintaan();
                
                ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                dataPermintaan.setNamaCustomer(rute.get(i).getNamaCustomer());
                dataPermintaan.setNamaJalan(rute.get(i).getNamaJalan());
                dataPermintaan.setKodeJalan(rute.get(i).getKodeJalan());
                dataPermintaan.setLongitude(rute.get(i).getLongitude());
                dataPermintaan.setLatitude(rute.get(i).getLatitude());
                dataPermintaan.setJumlahPermintaan(rute.get(i).getJumlahPermintaan());
                
                ruteSearched.add(dataPermintaan);
                
                if(i == rute.size()-1 && flag == 0){
                    ruteKendaraan.setRuteMobile1Searched(ruteSearched);
                }
                else if(i == rute.size()-1 && flag == 1){
                    ruteKendaraan.setRuteMobile2Searched(ruteSearched);
                }
            }
            else{
                if(flag == 0){
                    ruteKendaraan.setRuteMobile1Searched(ruteSearched);
                    
                    flag = 1;
                    currentLimit = 0;
                    currentLimit += rute.get(i).getJumlahPermintaan();
                    ruteSearched = new ArrayList<>();
                    
                    ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                    dataPermintaan.setNamaCustomer(rute.get(i).getNamaCustomer());
                    dataPermintaan.setNamaJalan(rute.get(i).getNamaJalan());
                    dataPermintaan.setKodeJalan(rute.get(i).getKodeJalan());
                    dataPermintaan.setLongitude(rute.get(i).getLongitude());
                    dataPermintaan.setLatitude(rute.get(i).getLatitude());
                    dataPermintaan.setJumlahPermintaan(rute.get(i).getJumlahPermintaan());

                    ruteSearched.add(dataPermintaan);
                    
                }
                else{
                    if(flag == 1){
                        ruteKendaraan.setRuteMobile2Searched(ruteSearched);
                        flag = 2;
                    }
                    
                    ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                    dataPermintaan.setNamaCustomer(rute.get(i).getNamaCustomer());
                    dataPermintaan.setNamaJalan(rute.get(i).getNamaJalan());
                    dataPermintaan.setKodeJalan(rute.get(i).getKodeJalan());
                    dataPermintaan.setLongitude(rute.get(i).getLongitude());
                    dataPermintaan.setLatitude(rute.get(i).getLatitude());
                    dataPermintaan.setJumlahPermintaan(rute.get(i).getJumlahPermintaan());

                    ruteAbandoned.add(dataPermintaan);
                }
            }
        }
        
        ruteKendaraan.setRuteAbandoned(ruteAbandoned);
        
        return ruteKendaraan;
    }
    
    public ModelRuteKendaraan pencarian_rute(ArrayList<ModelDataPermintaan> ruteSaved, int limitKarton) {
        int limit = limitKarton;
        int i, j, flag, currentLimit = 0;
        ModelRuteKendaraan ruteKendaraan;
        
        ArrayList<ModelDataPermintaan> ruteSearched;
        ArrayList<ModelDataPermintaan> ruteMobile1Searched;
        ArrayList<ModelDataPermintaan> ruteMobile2Searched;
        
        ruteSearched = new ArrayList<>();
        ruteMobile1Searched = new ArrayList<>();
        ruteMobile2Searched = new ArrayList<>();
        ruteKendaraan = new ModelRuteKendaraan();
        
        flag = 0;
        
        for(i=0; i<ruteSaved.size(); i++){
            if((ruteSaved.get(i).getJumlahPermintaan()+currentLimit) <= 150){
                currentLimit += ruteSaved.get(i).getJumlahPermintaan();
                
                ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                dataPermintaan.setNamaCustomer(ruteSaved.get(i).getNamaCustomer());
                dataPermintaan.setNamaJalan(ruteSaved.get(i).getNamaJalan());
                dataPermintaan.setKodeJalan(ruteSaved.get(i).getKodeJalan());
                dataPermintaan.setLongitude(ruteSaved.get(i).getLongitude());
                dataPermintaan.setLatitude(ruteSaved.get(i).getLatitude());
                dataPermintaan.setJumlahPermintaan(ruteSaved.get(i).getJumlahPermintaan());
                
                ruteSearched.add(dataPermintaan);
                
                if(i == ruteSaved.size()-1 && flag == 0){
                    ruteKendaraan.setRuteMobile1Searched(ruteSearched);
                }
                else if(i == ruteSaved.size()-1 && flag == 1){
                    ruteKendaraan.setRuteMobile2Searched(ruteSearched);
                }
            }
            else{
                if(flag == 0){
                    ruteKendaraan.setRuteMobile1Searched(ruteSearched);
                    
                    flag = 1;
                    currentLimit = 0;
                    currentLimit += ruteSaved.get(i).getJumlahPermintaan();
                    ruteSearched = new ArrayList<>();
                    
                    ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                    dataPermintaan.setNamaCustomer(ruteSaved.get(i).getNamaCustomer());
                    dataPermintaan.setNamaJalan(ruteSaved.get(i).getNamaJalan());
                    dataPermintaan.setKodeJalan(ruteSaved.get(i).getKodeJalan());
                    dataPermintaan.setLongitude(ruteSaved.get(i).getLongitude());
                    dataPermintaan.setLatitude(ruteSaved.get(i).getLatitude());
                    dataPermintaan.setJumlahPermintaan(ruteSaved.get(i).getJumlahPermintaan());

                    ruteSearched.add(dataPermintaan);
                    
                }
                else{
                    if(flag == 1){
                        ruteKendaraan.setRuteMobile2Searched(ruteSearched);
                    }
                    
                    ModelDataPermintaan dataPermintaan = new ModelDataPermintaan();
                    dataPermintaan.setNamaCustomer(ruteSaved.get(i).getNamaCustomer());
                    dataPermintaan.setNamaJalan(ruteSaved.get(i).getNamaJalan());
                    dataPermintaan.setKodeJalan(ruteSaved.get(i).getKodeJalan());
                    dataPermintaan.setLongitude(ruteSaved.get(i).getLongitude());
                    dataPermintaan.setLatitude(ruteSaved.get(i).getLatitude());
                    dataPermintaan.setJumlahPermintaan(ruteSaved.get(i).getJumlahPermintaan());
                }
            }
        }
        
        return ruteKendaraan;
    } 
}
