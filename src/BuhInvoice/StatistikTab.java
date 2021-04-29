/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import XYG_BARGRAPH.BARGraph;
import XYG_BARGRAPH.MyGraphXY_BG;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import XYG_STATS.BarGraphListener;
import XYG_STATS.BasicGraphListener;
import XYG_STATS.XyGraph_M;
import XY_BUH_INVOICE.MyGraphXY_BuhInvoice;
import XY_BUH_INVOICE.XyGraph_BuhInvoice;
import forall.HelpA;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import other.StringDouble;

/**
 *
 * @author HP-A
 */
public class StatistikTab implements BarGraphListener {

    private final BUH_INVOICE_MAIN bim;
    private ArrayList<HashMap<String, String>> fakturor_one_year_map;
    private final Object lock_a = new Object();

    public StatistikTab(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        init();
    }

    private void init() {
        refresh();
    }
    
    public void go(){
        //
         java.awt.EventQueue.invokeLater(() -> {
            drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
        });
        //
    }

    public void refresh() {
        //
//        java.awt.EventQueue.invokeLater(() -> {
//            drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
//        });
        //
//        java.awt.EventQueue.invokeLater(() -> {
//            drawGraph_basic(bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
//        });
//        //
//        java.awt.EventQueue.invokeLater(() -> {
//            drawGraph_bargraph(bim.jPanel_graph_panel_c, "bar_graph_one_year_back");
//        });
        //
//        Thread x = new Thread(new Thread_A(lock_a));
//        x.start();
//        //
//        Thread x2 = new Thread(new Thread_B());
//        x2.start();
//        //
//        Thread x3 = new Thread(new Thread_C(lock_a));
//        x3.start();
        //
    }

    private void drawGraph_bargraph(JPanel container, String name) {
        //
        container.removeAll();
        //
        BasicGraphListener gg;
        MyGraphXY_BG mgxyhm;
        //
        final XyGraph_M xygraph = new XyGraph_M(name, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm = new MyGraphXY_BG();
        mgxyhm.addBarGraphListener(this);
        gg = new BARGraph(name, mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph.setGraph(gg);
        container.add(gg.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        final LinkedHashMap<String, Double> mont_sum_map = new LinkedHashMap<>();
        //
        for (HashMap<String, String> map : fakturor_one_year_map) {
            //
            String fakturadatum = map.get("fakturadatum");
            String total = map.get("total_ink_moms");
            int fakturatyp = Integer.parseInt(map.get("fakturatyp")); 
            //
            String[] arr = fakturadatum.split("-");
            String faktura_datum_short = arr[0] + "-" + arr[1];
            //
            if(fakturatyp == 0 || fakturatyp == 2){
                HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(total), mont_sum_map);
            }
            //
        }
        //
        //
        Set set = mont_sum_map.keySet();
        Iterator it = set.iterator();
        //
        final ArrayList<StringDouble> barGraphValuesList = new ArrayList<>();
        //
        while (it.hasNext()) {
            String key = (String) it.next();
            Double value = mont_sum_map.get(key);
            System.out.println("key = " + key + "  value = " + value);
            barGraphValuesList.add(new StringDouble(key, value));
        }
        //
        if(barGraphValuesList.size() < 12){
            //
            while(barGraphValuesList.size() < 12){
                barGraphValuesList.add(new StringDouble("", 0));
            }
            //
        }
        //
        Collections.reverse(barGraphValuesList);
        //
        BARGraph barg = (BARGraph) gg;
        barg.addData(barGraphValuesList);
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
        if (phpScript.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK)) {
            fakturor_one_year_map = invoices;
        }
        //
        xghm.addData(invoices, new String[]{"fakturadatum", "forfallodatum"});
        //
        System.out.println("Data added ****************************");
        //
    }

    class Thread_A implements Runnable {

        private final Object lock;

        public Thread_A(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            // OBS! OBS! Superimportant - see "MyGraphXY.class
            // -> private synchronized void waitForPanelHeightIsInitialized()" -> So if the component is not
            // visible from the beginning it will NOT WORK as it will wait untill the height>50
            drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
            //
            synchronized (lock) {
                lock.notify();
                System.out.println("Notified A");
            }
            //
        }

    }

    class Thread_B implements Runnable {

        @Override
        public void run() {
            drawGraph_basic(bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
        }

    }

    class Thread_C implements Runnable {

        private final Object lock;

        public Thread_C(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    //
                    System.out.println("Waiting");
                    lock.wait();
                    System.out.println("Notified B");
                    //
//                    java.awt.EventQueue.invokeLater(() -> {
                        drawGraph_bargraph(bim.jPanel_graph_panel_c, "bar_graph_one_year_back");
//                    });
                    //
                    System.out.println("Drawn");
                    //
                } catch (InterruptedException ex) {
                    Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Override
    public void barGraphHoverEvent(MouseEvent me, MyPoint mp) {
        // See MCLabStats 
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent me) {
        // See MCLabStats 
    }

}
