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

    private String momsSats;
    private String inklExklMoms;

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
