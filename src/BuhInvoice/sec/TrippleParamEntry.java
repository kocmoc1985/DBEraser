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
public class TrippleParamEntry {

    private final String param_a;
    private final String param_b;
    private final String param_c;

    public TrippleParamEntry(String param_a, String param_b,String param_c) {
        this.param_a = param_a;
        this.param_b = param_b;
        this.param_c = param_c;
    }

    public String getParam_a() {
        return param_a;
    }

    public String getParam_b() {
        return param_b;
    }
    
    public String getParam_c(){
        return param_c;
    }

    public Double getParam_a__double() {
        try {
            return Double.parseDouble(param_a);
        } catch (Exception ex) {
            return null;
        }
    }
    
     public Double getParam_c__double() {
        try {
            return Double.parseDouble(param_c);
        } catch (Exception ex) {
            return null;
        }
    }

    public Double getParam_b__double() {
        try {
            return Double.parseDouble(param_b);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public double getParam_b__percent() {
        try {
            return (Double.parseDouble(param_b)) / 100;
        } catch (Exception ex) {
            return -1;
        }
    }

}
