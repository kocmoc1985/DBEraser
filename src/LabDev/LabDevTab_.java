/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.BasicTab;
import forall.HelpA_;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import java.awt.Component;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public abstract class LabDevTab_ extends BasicTab {

    protected final MC_RECIPE mcRecipe;
    protected final LabDevelopment_ labDev;

    public LabDevTab_(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT);
        this.mcRecipe = (MC_RECIPE) OUT;
        this.labDev = labDev;
    }


    //=========================================================================
    //=========================================================================
    //=========================================================================
    //BELOW IS FOR MULTIPLE COMBOBOX SEARCH/FILTER
    private static long prevCall__mcs;

    public abstract String getQuery__mcs(String procedure, String colName, String[] comboParameters);

    public abstract String[] getComboParams__mcs();
    
    /**
     * "_msc" = MULTIPLE COMBOBOX SEARCH
     * @return 
     */
    public boolean delay__mcs() {
        if (Math.abs(System.currentTimeMillis() - prevCall__mcs) < 1000) {
            prevCall__mcs = System.currentTimeMillis();
            return false;
        } else {
            prevCall__mcs = System.currentTimeMillis();
            return true;
        }
    }
    
    public void removeFilter__mcs(JComboBox box){
        box.setSelectedItem(null);
        box.setEditable(false);
    }

    public void fillComboBox__mcs(final JComboBox box, final String colName, SqlBasicLocal sql, String query) {
        // 
        if (delay__mcs() == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                Object selection = box.getSelectedItem();
                //
                JComboBoxA boxA = (JComboBoxA) box;
                //
                HelpA_.fillComboBox(sql, box, query, null, false, false);
                //
                box.setSelectedItem(selection);
                //
            }
        });

    }

    public void addMouseListenerJComboBox__mcs(JComponent c, MouseListener ml) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            try {
                component.addMouseListener(ml);
            } catch (Exception ex) {
            }
        }
    }
    //=========================================================================
}
