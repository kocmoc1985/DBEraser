/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import XYG_BARGRAPH.BARGraph;
import XYG_BARGRAPH.MyGraphXY_BG;
import XYG_BARGRAPH.MyPoint_BG;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_STATS.BarGraphListener;
import XYG_STATS.BasicGraphListener;
import XYG_STATS.XyGraph_M;
import XY_BUH_INVOICE.MyGraphXY_BuhInvoice;
import XY_BUH_INVOICE.XyGraph_BuhInvoice;
import forall.HelpA;
import java.awt.Color;
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
 * OBS! OBS! Showing Exkl Moms for both bargraph and common graphs
 *
 * @author HP-A
 */
public class StatistikTab implements BarGraphListener {

    private final BUH_INVOICE_MAIN bim;
    private ArrayList<HashMap<String, String>> fakturor_one_year_map;
    private final Object lock_a = new Object();
    private XyGraph_BuhInvoice xygraph;

    private final static String SERIE_NAME__BARGTAPH__TOTAL_PER_MONTH = "bar_graph_total_per_month";
    private final static String SERIE_NAME__BARGTAPH__AMMOUNT_PER_MONTH = "bar_graph_ammount_per_month";

    public StatistikTab(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        init();
    }

    private void init() {
        refresh();
    }

    public void go() {
        //
        reset();
        //
        drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
        drawGraph_basic(bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
        drawGraph_bargraph(bim.jPanel_graph_panel_c, bim.jPanel_graph_panel_d, SERIE_NAME__BARGTAPH__TOTAL_PER_MONTH, SERIE_NAME__BARGTAPH__AMMOUNT_PER_MONTH);
        //
    }

    public void refresh() {
        //
        reset();
        //
        drawGraph_basic(bim.jPanel_graph_panel_a, "all_invoices", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK);
        drawGraph_basic(bim.jPanel_graph_panel_b, "act_month", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH);
        drawGraph_bargraph(bim.jPanel_graph_panel_c, bim.jPanel_graph_panel_d, SERIE_NAME__BARGTAPH__TOTAL_PER_MONTH, SERIE_NAME__BARGTAPH__AMMOUNT_PER_MONTH);
        //
    }

    private void reset() {
        //
        fakturor_one_year_map = null;
        //
    }

    private void drawGraph_basic(JPanel container, String name, String phpScript) {
        //
        container.removeAll();
        //
        String dateNow = GP_BUH.getDate_yyyy_MM_dd();
        String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
        //
        XyGraph_BuhInvoice xygm = new XyGraph_BuhInvoice(name, DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, new MyGraphXY_BuhInvoice(bim), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, dateNow, dateFormat);
        //
        if (phpScript.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK)) {
            xygraph = xygm;
        }
        //
        System.out.println("Thread:" + Thread.currentThread().getName());
        //
        container.add(xygm.getGraph());
        //
        Thread x = new Thread(new Thread_A_A(phpScript, xygm));
        x.setName("Thread_A_A");
        x.start();
        //
    }

    private void drawGraph_bargraph(JPanel containerTotalPerMonth, JPanel containerAmmountPerMonth, String name_a, String name_b) {
        //
        containerTotalPerMonth.removeAll();
        containerAmmountPerMonth.removeAll();
        //
        //====================================================
        BasicGraphListener gg__total_per_month;
        MyGraphXY_BG mgxyhm;
        //
        final XyGraph_M xygraph = new XyGraph_M(name_a, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm = new MyGraphXY_BG("Total", ":-");
        mgxyhm.addBarGraphListener(this);
        gg__total_per_month = new BARGraph(name_a, mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph.setGraph(gg__total_per_month);
        containerTotalPerMonth.add(gg__total_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        BasicGraphListener gg__ammount_per_month;
        MyGraphXY_BG mgxyhm_b;
        //
        final XyGraph_M xygraph_b = new XyGraph_M(name_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm_b = new MyGraphXY_BG("Antal", " st");
        mgxyhm_b.addBarGraphListener(this);
        gg__ammount_per_month = new BARGraph(name_b, mgxyhm_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph_b.setGraph(gg__ammount_per_month);
        containerAmmountPerMonth.add(gg__ammount_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        Thread x = new Thread(new Thread_B_B(gg__total_per_month, gg__ammount_per_month));
        x.setName("Thread_B_B");
        x.start();
        //
    }

    class Thread_A_A implements Runnable {

        private final String phpScript;
        private final XyGraph_BuhInvoice xghm;

        public Thread_A_A(String phpScript, XyGraph_BuhInvoice xghm) {
            this.phpScript = phpScript;
            System.out.println("CCC:" + this.phpScript);
            this.xghm = xghm;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            String json = bim.getSELECT_kundId();
            //
            String json_str_return = "";
            //
            try {
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
            if (this.phpScript.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK)) {
                //
                synchronized (lock_a) {
                    lock_a.notify();
                    System.out.println("NOTIFY......................................." + phpScript);
                }
                //
            }
            //
            // OBS! HERE Below it's done with AWT-Thread
            java.awt.EventQueue.invokeLater(() -> {
                System.out.println("Thread addData: " + Thread.currentThread());
                this.xghm.addData(invoices, new String[]{DB.BUH_FAKTURA__FAKTURA_DATUM, DB.BUH_FAKTURA__FORFALLO_DATUM});
            });
            //
        }

    }

    class Thread_B_B implements Runnable {

        private final BasicGraphListener gg__total_per_month;
        private final BasicGraphListener gg__ammount_per_month;

        public Thread_B_B(BasicGraphListener gg__total_per_month, BasicGraphListener gg__ammount_per_month) {
            this.gg__total_per_month = gg__total_per_month;
            this.gg__ammount_per_month = gg__ammount_per_month;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            final LinkedHashMap<String, Double> mont_sum_map = new LinkedHashMap<>();
            final LinkedHashMap<String, Double> mont_ammount_map = new LinkedHashMap<>();
            //
            if (fakturor_one_year_map == null) {
                synchronized (lock_a) {
                    try {
                        System.out.println("WAIT.......................................");
                        lock_a.wait();
                        System.out.println("NOTIFIED.......................................");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //
            for (HashMap<String, String> map : fakturor_one_year_map) {
                //
                String fakturadatum = map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
                String total = map.get(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__);
                int fakturatyp = Integer.parseInt(map.get(DB.BUH_FAKTURA__FAKTURATYP));
                //
                String[] arr = fakturadatum.split("-");
                String faktura_datum_short = arr[0] + "-" + arr[1];
                //
                if (fakturatyp == 0 || fakturatyp == 2) {
                    //
                    HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(total), mont_sum_map);
                    //
                    HelpA.increase_map_value_with_x(faktura_datum_short, 1.0, mont_ammount_map);
                    //
                }
                //
            }
            //
            //====================================================
            //
            Set set = mont_sum_map.keySet();
            Iterator it = set.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_total = new ArrayList<>();
            //
            while (it.hasNext()) {
                String key = (String) it.next();
                Double value = mont_sum_map.get(key);
//                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_total.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_total.size() < 12) {
                //
                while (barGraphValuesList_total.size() < 12) {
                    barGraphValuesList_total.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_total);
            //
            BARGraph barg_a = (BARGraph) gg__total_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_a.addData(barGraphValuesList_total);
            });
            //
            //====================================================
            //
            Set set_b = mont_ammount_map.keySet();
            Iterator it_b = set_b.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_ammount = new ArrayList<>();
            //
            while (it_b.hasNext()) {
                String key = (String) it_b.next();
                Double value = mont_ammount_map.get(key);
                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_ammount.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_ammount.size() < 12) {
                //
                while (barGraphValuesList_ammount.size() < 12) {
                    barGraphValuesList_ammount.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_ammount);
            //
            BARGraph barg_b = (BARGraph) gg__ammount_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_b.addData(barGraphValuesList_ammount);
            });
            //
            //====================================================
            //
        }

    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint mp) {
        if (e.getSource() instanceof MyPoint_BG) {
            //
            MyPoint_BG mpbg = (MyPoint_BG) e.getSource();
            //
//            if (mpbg.getSerieName().equals(SERIE_NAME__BARGTAPH__TOTAL_PER_MONTH)) {
            highlight_a(mpbg, xygraph);
//            }
            //
        }
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent me) {
        xygraph.getSerie().resetPointsColorAndForm();
    }

    private void highlight_a(MyPoint_BG mpbg, XyGraph_BuhInvoice xygraph_) {
        //
        MySerie serie = xygraph_.getSerie();
        //
        serie.resetPointsColorAndForm();
        //
        String monthYear = mpbg.getBarName(); // "2021-02"
        //
        for (MyPoint point : serie.getPoints()) {
            //
            HashMap<String, String> map = point.getPointInfo();
            String fakturaDatum = map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
            if (fakturaDatum.contains(monthYear)) {
                point.setPointColor(Color.MAGENTA);
                point.setPointBorder(null, false);
                point.setPointRectBorder(null, false);
                point.setPointDrawRect(true);
            }
            //
        }
        //
        xygraph_.getGraph().revalidate();
        xygraph_.getGraph().repaint();
        //
    }

}
