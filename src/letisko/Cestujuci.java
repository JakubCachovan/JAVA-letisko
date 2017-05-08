/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letisko;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class Cestujuci extends Osoba implements Serializable{

    private ArrayList<String> destinacie;
            
    public Cestujuci(String meno, String priezvisko, String RC) {
        super(meno, priezvisko, RC);
        
        destinacie = new ArrayList<>();
    }

    public ArrayList<String> getDestinacie() {
        return destinacie;
    }
    
    public boolean addDestinacia(String dest){
        destinacie.add(dest);
        return true;
    }

    @Override
    public String getPriezvisko() {
        return super.getPriezvisko(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMeno() {
        return super.getMeno(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRC() {
        return super.getRC(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDestinacie(ArrayList<String> destinacie) {
        this.destinacie = destinacie;
    }
    
    public String exportCSV(){
        return getMeno() 
                + ";" + getPriezvisko() 
                + ";" + getRC();
    } 

    @Override
    public String toString() {
        return "Cestujuci{" + "destinacie=" + destinacie + '}';
    }
    
    
    
    
    
}
