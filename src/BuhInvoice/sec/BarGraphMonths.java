/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author KOCMOC
 */
public class BarGraphMonths {

    private final ArrayList<HashMap<String, String>> list_general;
    //
    private final LinkedHashMap<String, Double> mont_sum_map = new LinkedHashMap<>();
    //

    public BarGraphMonths(ArrayList<HashMap<String, String>> list) {
        this.list_general = list;
        go();
    }

    private void go() {
        //
        for (HashMap<String, String> map : list_general) {
            //
            String fakturadatum = map.get("fakturadatum");
            String total = map.get("total_ink_moms");
            //
            sort(fakturadatum, total);
            //
        }
        //
        System.out.println("" + mont_sum_map);
        //
    }

    private void sort(String fakturadatum, String faktura_total) {
        //
        String[] arr = fakturadatum.split("-");
        String faktura_datum_short = arr[0] + "-" + arr[1];
        //
        HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(faktura_total), mont_sum_map);
        //
    }

}
