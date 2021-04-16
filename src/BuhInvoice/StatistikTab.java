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
import javax.swing.JPanel;

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
        Thread x = new Thread(new Thread_A());
        x.start();
        //
        Thread x2 = new Thread(new Thread_B());
        x2.start();
        //
    }

    private void refresh_() {
        // OBS! OBS! Superimportant - see "MyGraphXY.class
        // -> private synchronized void waitForPanelHeightIsInitialized()" -> So if the component is not
        // visible from the beginning it will NOT WORK as it will wait untill the height>50
        //
        drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
        //
        drawGraph_basic( bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
        //
    }

    private void drawGraph_basic(JPanel container, String name, String phpScript) {
        //
        container.removeAll();
        //
        String dateNow = GP_BUH.getDate_yyyy_MM_dd();
        String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
        //
        XyGraph_BuhInvoice xghm = new XyGraph_BuhInvoice(name, new MyGraphXY_BuhInvoice(bim), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, dateNow, dateFormat);
        //
        container.add(xghm.getGraph());
        //
        String json = bim.getSELECT_kundId();
        //
        String json_str_return = "";
        //
        try {
            // DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR ---> GET ALL
            // DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH --> ACT MONTHF
            json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpScript, json);
        } catch (Exception ex) {
            Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
        //
        xghm.addData(invoices, new String[]{"fakturadatum", "forfallodatum"});
        //
    }

    
    class Thread_A implements Runnable{

        @Override
        public void run() {
            drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
        }
        
    }
    
     class Thread_B implements Runnable{

        @Override
        public void run() {
            drawGraph_basic( bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
        }
        
    }

}
