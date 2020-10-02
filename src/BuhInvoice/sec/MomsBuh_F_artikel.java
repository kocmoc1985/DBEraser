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
public class MomsBuh_F_artikel  {

    private final double momsSats;
    private final double sum;

    public MomsBuh_F_artikel(double momsSats, double sum) {
        this.momsSats = momsSats;
        this.sum = sum;
    }

    public double getMomsSats() {
        return momsSats;
    }

    public double getSum() {
        return sum;
    }
    
    
}
