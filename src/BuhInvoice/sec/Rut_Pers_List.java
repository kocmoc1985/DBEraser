/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * [#DEVIDE-RUT-ROT-BETWEEN-PERSONS#]
 * @author KOCMOC
 */
public class Rut_Pers_List {

    private final double AVDRAG_TOTAL;
    private final ArrayList<RUT_Pers> perslist = new ArrayList<>();

    public Rut_Pers_List(double AVDRAG_TOTAL) {
        this.AVDRAG_TOTAL = AVDRAG_TOTAL;
    }

    protected void addRutPers(RUT_Pers pers) {
        this.perslist.add(pers);
    }

    protected boolean containsPersonsWithAvdragExceed() {
        //
        Collections.sort(perslist, new RutPersComporator()); // Sorterar med den lägsta FÖRST
        //
        for (RUT_Pers rUT_Pers : perslist) {
            if (rUT_Pers.isFelberaknat()) {
                return true;
            }
        }
        return false;
    }

    
    protected void devideProperly() {
        // Deviding taking into account the "avdragstak"
        double rutAvdragTotalLeft = AVDRAG_TOTAL;
        //
        for (RUT_Pers rUT_Pers : perslist) {
            rutAvdragTotalLeft = rUT_Pers.setAvdrag(rutAvdragTotalLeft);
        }
        //
    }

    class RutPersComporator implements Comparator<RUT_Pers> {

        @Override
        public int compare(RUT_Pers o1, RUT_Pers o2) {
            return (int) (o1.avdragstak - o2.avdragstak); // Sorterar med den lägsta FÖRST
        }
    }
}
