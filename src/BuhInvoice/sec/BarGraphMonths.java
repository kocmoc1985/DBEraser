/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class BarGraphMonths {
    
    private final ArrayList<HashMap<String, String>>list_general;
    //
    private static final String JAN = "2021-01";
    private static final String FEB = "2021-02";
    private static final String MAR = "2021-03";
    private static final String APR = "2021-04";
    
    private static final ArrayList<String>months_list = new ArrayList<>();
    static{
        months_list.add(JAN);
        months_list.add(FEB);
        months_list.add(MAR);
        months_list.add(APR);
    }

    public BarGraphMonths(ArrayList<HashMap<String, String>>list) {
        this.list_general = list;
        go();
    }
    
    private void go(){
        //
        for (HashMap<String, String> map : list_general) {
            //
            for (String month : months_list) {
                String fakturadatum = map.get("fakturadatum");
                isSameMonth(month, fakturadatum);
            }
            //
        }
        //
    }
    
    
    private boolean isSameMonth(String date_yyyy_MM, String fakturadatum){
        String[]arr = fakturadatum.split("-");
        String faktura_datum_short = arr[0] + "-" + arr[1];
        System.out.println("faktura_datum_short: " + faktura_datum_short);
        System.out.println("date:" + date_yyyy_MM + " / " + "fakturadatum: " + fakturadatum);
        return true;
    }
    
    
}
