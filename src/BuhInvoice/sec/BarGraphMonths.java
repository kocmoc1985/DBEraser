/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class BarGraphMonths {

    private final ArrayList<HashMap<String, String>> list_general;
    //
    private final HashMap<String, Double> mont_sum_map = new HashMap<>();
    //
    private static final String JAN = "2021-01";
    private static final String FEB = "2021-02";
    private static final String MAR = "2021-03";
    private static final String APR = "2021-04";

    private static final ArrayList<String> months_list = new ArrayList<>();

    static {
        months_list.add(JAN);
        months_list.add(FEB);
        months_list.add(MAR);
        months_list.add(APR);
    }

    public BarGraphMonths(ArrayList<HashMap<String, String>> list) {
        this.list_general = list;
        go();
    }

    private void go() {
        //
        for (HashMap<String, String> map : list_general) {
            //
            for (String month : months_list) {
                String fakturadatum = map.get("fakturadatum");
                String total = map.get("total_ink_moms");
                System.out.println("total:" + total);
                isSameMonth(month, fakturadatum, total);
            }
            //
        }
        //
        System.out.println("" + mont_sum_map);
        //
    }

    private boolean isSameMonth(String date_yyyy_MM, String fakturadatum, String faktura_total) {
        String[] arr = fakturadatum.split("-");
        String faktura_datum_short = arr[0] + "-" + arr[1];
        System.out.println("faktura_datum_short: " + faktura_datum_short);
        System.out.println("date:" + date_yyyy_MM + " / " + "fakturadatum: " + fakturadatum);
        //
        if (faktura_datum_short.equals(date_yyyy_MM)) {
            HelpA.increase_map_value_with_x(date_yyyy_MM, Double.parseDouble(faktura_total), mont_sum_map);
        }
        //
        return true;
    }

}
