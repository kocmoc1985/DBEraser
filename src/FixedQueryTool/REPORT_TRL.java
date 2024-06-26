/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FixedQueryTool;

import Reporting.JTableBasicRepport;
import forall.HelpA;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class REPORT_TRL {
    
    
    /**
     *
     * @param table
     * @param landscape - for report
     * @param writeToFile - for export
     */
    public void tableCommonExportOrRepport(JTable table, boolean landscape) {
        //
        JComboBox box = new JComboBox(new String[]{"REPORT", "CSV"});
        //
        boolean cond_0 = HelpA.chooseFromComboBoxDialog(box, "Report or CSV");
        //
        boolean cond_1 = HelpA.getComboBoxSelectedValue(box).equals("REPORT");
        //
        boolean cond_2 = HelpA.getComboBoxSelectedValue(box).equals("CSV");
        //
        if (cond_0 && cond_1) {
            tableCommonRepport(table, landscape);
        } else if (cond_0 && cond_2) {
            jTableToCSV(table, true);
        }
        //
    }
    
     public void tableCommonRepport(JTable table, boolean landscape) {
        new JTableBasicRepport(table, landscape);
    }
     
     public String jTableToCSV(JTable table, boolean writeToFile) {
        return HelpA.jTableToCSV(table, writeToFile);
    }
}
