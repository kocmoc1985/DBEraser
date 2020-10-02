/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.util.Comparator;

/**
 *
 * @author KOCMOC
 */
public class MomsComporator implements Comparator<MomsBuh_F_artikel> {

    @Override
    public int compare(MomsBuh_F_artikel o1, MomsBuh_F_artikel o2) {
//        return (int) (o1.getSum() - o2.getSum()); // ASC
            return (int) (o2.getSum() - o1.getSum()); // DESC
    }
    
}
