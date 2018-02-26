/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penyelesaiancapacitatedvrp.Entity;

import java.util.ArrayList;

/**
 *
 * @author shaff
 */
public class ModelRuteKendaraan {
    ArrayList<ModelDataPermintaan> ruteMobile1Searched;
    ArrayList<ModelDataPermintaan> ruteMobile2Searched;
    ArrayList<ModelDataPermintaan> ruteAbandoned;
    ArrayList<ModelDataPermintaan> ruteSaved;

    public ModelRuteKendaraan() {
        ruteMobile1Searched = new ArrayList<>();
        ruteMobile2Searched = new ArrayList<>();
        ruteAbandoned = new ArrayList<>();
        ruteSaved = new ArrayList<>();
    }

    public ArrayList<ModelDataPermintaan> getRuteMobile1Searched() {
        return ruteMobile1Searched;
    }

    public void setRuteMobile1Searched(ArrayList<ModelDataPermintaan> ruteMobile1Searched) {
        this.ruteMobile1Searched = ruteMobile1Searched;
    }

    public ArrayList<ModelDataPermintaan> getRuteMobile2Searched() {
        return ruteMobile2Searched;
    }

    public void setRuteMobile2Searched(ArrayList<ModelDataPermintaan> ruteMobile2Searched) {
        this.ruteMobile2Searched = ruteMobile2Searched;
    }
    
    public ArrayList<ModelDataPermintaan> getRuteAbandoned() {
        return ruteAbandoned;
    }

    public void setRuteAbandoned(ArrayList<ModelDataPermintaan> ruteAbandoned) {
        this.ruteAbandoned = ruteAbandoned;
    }
    
    public void setRuteAbandonedEmpty(){
        this.ruteAbandoned = new ArrayList<>();
    }
}
