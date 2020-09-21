/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 *
 * @author KOCMOC
 */
public class Moms {

    private double momsSats;
    private int inklExklMoms;

    public Moms() {
      
    }

    public void setInklExklMoms(int inklExklMoms) {
        this.inklExklMoms = inklExklMoms;
    }

    public void setMomsSats(double momsSats) {
        this.momsSats = momsSats;
    }
    
    public double getMomsSats() {
        return momsSats;
    }

    public int getInklExklMoms() {
        return inklExklMoms;
    }

}
