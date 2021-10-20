/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;
import javax.swing.JTable;

/**
 * [#DEVIDE-RUT-ROT-BETWEEN-PERSONS#]
 * @author KOCMOC
 */
public class RUT_Pers {
    
    private final JTable jtable;
    private final int jtablerow;
    private final String fornamn;
    private final String efternamn;
    private double avdrag;
    protected final double avdragstak;
    private final String pnr;
    private boolean felberaknat;

    public RUT_Pers(JTable jtable,int jtablerow, String fornamn, String efternamn, double avdrag, double avdragstak, String pnr) {
        this.jtable = jtable;
        this.jtablerow = jtablerow;
        this.fornamn = fornamn;
        this.efternamn = efternamn;
        this.avdrag = avdrag;
        this.avdragstak = avdragstak;
        this.pnr = pnr;
        validateAvdrag();
    }

    
    protected boolean isFelberaknat(){
        return felberaknat;
    }
    
    protected double setAvdrag(double avdragTotalLeft){
        //
        if(avdragTotalLeft > avdragstak){
            avdrag = avdragstak;
        }else{
            avdrag = avdragTotalLeft;
        }
        //
        HelpA.setValueGivenRow(jtable, jtablerow, RutRot.COL__AVDRAG, avdrag);
        //
        return avdragTotalLeft - avdrag;
    }

    private void validateAvdrag(){
        if(avdrag > avdragstak){
            felberaknat = true;
        }
    }

   

    
    
    
}
