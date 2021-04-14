/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import XYG_BASIC.MyGraphContainer;
import XY_RUN.MyGraphXY_BuhInvoice;
import XY_RUN.XyGraph_BuhInvoice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP-A
 */
public class StatistikTab {

    private final BUH_INVOICE_MAIN bim;

    public StatistikTab(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        init();
    }

    private void init() {
        refresh();
    }

    public void refresh() {
        //
        XyGraph_BuhInvoice xghm = new XyGraph_BuhInvoice("test", new MyGraphXY_BuhInvoice(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        bim.jPanel_graph_panel_a.add(xghm.getGraph());
        //
        String json = bim.getSELECT_kundId();
        //
        String json_str_return = "";
        //
        try {
            json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH, json);
        } catch (Exception ex) {
            Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
        //
        xghm.addData(invoices, new String[]{"fakturadatum", "forfallodatum"});
        //
    }

}
