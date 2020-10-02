/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 * This class is for saving actual "moms sats" & "inkl exkl moms" when the
 * jcombobox state is changed
 * 
 * OBS! Not in use since [2020-10-02]
 * @author KOCMOC
 */
public class Moms {

    private String momsSats;
    private String inklExklMoms;

    public void reset(){
        momsSats = null;
        inklExklMoms = null;
    }
    
    public void setInklExklMoms(String inklExklMoms) {
        this.inklExklMoms = inklExklMoms;
    }

    public void setMomsSats(String momsSats) {
        this.momsSats = momsSats;
    }

    public String getMomsSats() {
        return momsSats;
    }

    public String getInklExklMoms() {
        return inklExklMoms;
    }

}
