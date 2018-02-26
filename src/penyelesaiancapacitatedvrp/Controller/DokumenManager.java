/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penyelesaiancapacitatedvrp.Controller;

import java.util.ArrayList;
import penyelesaiancapacitatedvrp.Entity.ModelDokumen;

/**
 *
 * @author shaff
 */
public class DokumenManager {
    public DokumenManager(){
    
    }
    
    public ArrayList<ArrayList> get_data(String Path) {
        ModelDokumen excelmanager = new ModelDokumen();
        ArrayList<ArrayList> data = excelmanager.load_excel(Path);
        return data;
    }
}
