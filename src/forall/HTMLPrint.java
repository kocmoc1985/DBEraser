/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import MCRecipeLang.MSG;
import MCRecipe.RecipeDetailed_;
import MyObjectTable.Table;
import MyObjectTableInvert.ColumnValue;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableInvert;
import Reporting.InvertTableRow;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public class HTMLPrint extends javax.swing.JFrame {

    public HTMLPrint(String recipe, JTable jtable, JTable jtableSumm, Table table, int startColumn, RowDataInvert[] cfg,
            String[] CSSRules, String[] jtableColsToInclude) {
        //
        initComponents();
        //
        recipeDetailedBuildTable4WithTableInvert(recipe, jtable, jtableSumm, table, startColumn, cfg, CSSRules, jtableColsToInclude);
        //
        this.setTitle("Print");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_RECIPE).getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void recipeDetailedBuildTable4WithTableInvert(String recipe, JTable jtable, JTable jtableSumm, Table table, int startColumn,
            RowDataInvert[] cfg, String[] CSSRules, String[] jtableColsToInclude) {
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane1.setEditorKit(kit);
        //
        StyleSheet styleSheet = kit.getStyleSheet();
//        styleSheet.addRule("table, th, td {border: 1px solid black}");
        //
        for (int i = 0; i < CSSRules.length; i++) {
            styleSheet.addRule(CSSRules[i]);
        }
        //
        String html = "";
        //
        html += "<h2'>" + recipe + "</h2>";
        //
        html += "<div style='color:grey;font-size:6pt'>MixCont MCRecipe Report: " + HelpA.get_proper_date_adjusted_format(3) + "</div>";
        //
        html += "<br>";
        //
        //
        html += tableInvertToHTML(table, startColumn, cfg);
        //
        html += "<br><br>";
        //
//        html += jTableToHTML(jtable, jtableColsToInclude);
        //
        html += jTableToHTML_adjusted(jtable, jtableSumm, jtableColsToInclude);
        //
        html += "<br><br>";
        //
        //This one is needed, otherwise the last element used to be cut
        html += "<div style='height:20px;color:grey;font-size:6pt'>.</div>";
        //
        Document doc = kit.createDefaultDocument();
        jEditorPane1.setDocument(doc);
        //
        jEditorPane1.setText(html);
        
    }

    private String jTableToHTML_adjusted(JTable table, JTable tableSum, String[] jtableColsToInclude) {
        //
        ArrayList<String> colNames;
        //
        if (jtableColsToInclude != null) {
            colNames = HelpA.getVisibleColumnsNames_B(table, jtableColsToInclude);
        } else {
            colNames = HelpA.getVisibleColumnsNames(table);
        }
        //
        //
        String html = "";
        //
        //
        html += "<table class='jtable'>";
        //
        //<TABLE HEADER>
        html += "<tr>";
        //
        for (int i = 0; i < colNames.size(); i++) {
            html += "<th>" + colNames.get(i) + "</th>";
        }
        //
        html += "</tr>";
        //</TABLE HEADER>
        //
        //<TABLE BODY>
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            ArrayList rowValues;
            //
            if (jtableColsToInclude != null) {
                rowValues = HelpA.getLineValuesVisibleColsOnly_B(table, x, jtableColsToInclude);
            } else {
                rowValues = HelpA.getLineValuesVisibleColsOnly(table, x);
            }
            //
            //
            html += "<tr>";
            //
            for (int i = 0; i < rowValues.size(); i++) {
                html += "<td>" + rowValues.get(i) + "</td>";
            }
            //
            html += "</tr>";
            //
        }
        //
        //
        html += "<tr>";
        //
        for (int i = 0; i < colNames.size(); i++) {
            //
            String colName = colNames.get(i);
            //
            //
            int col = HelpA.getColByName_hashmap(table, colName); // I need to make a map for real and nicknames
            //
            if (HelpA.columnIsVisible(table, col)) {
                //
                String value = (String) tableSum.getValueAt(0, col);
                //
                if (value != null && value.isEmpty() == false) {
                    //
                    if (colName.equals(RecipeDetailed_.t4_Descr_nick)) {
                        html += "<td><strong>*" + value + "</strong></td>";
                    } else if (colName.equals(RecipeDetailed_.t4_material_nick)) {
                        html += "<td><strong>sum</strong></td>";
                    } else {
                        html += "<td><strong>" + value + "</strong></td>";
                    }
                    //
                } else {
                    if (colName.equals(RecipeDetailed_.t4_material_nick)) {
                        html += "<td><strong>sum</strong></td>";
                    } else {
                        html += "<td>-</td>";
                    }
                }
                //
            }
            //
        }
        //
        html += "</tr>";
        //
        //</TABLE BODY>
        //
        html += "</table>";
        //
        //
        html += "<div style='color:grey;font-size:6pt'>*" + RecipeDetailed_.t4_Fillfactor + "</div>";
        //
        return html;
    }

    private String jTableToHTML(JTable table, String[] jtableColsToInclude) {
        //
        ArrayList<String> colNames;
        //
        if (jtableColsToInclude != null) {
            colNames = HelpA.getVisibleColumnsNames_B(table, jtableColsToInclude);
        } else {
            colNames = HelpA.getVisibleColumnsNames(table);
        }
        //
        //
        String html = "";
        //
        //
        html += "<table class='jtable'>";
        //
        //<TABLE HEADER>
        html += "<tr>";
        //
        for (int i = 0; i < colNames.size(); i++) {
            html += "<th>" + colNames.get(i) + "</th>";
        }
        //
        html += "</tr>";
        //</TABLE HEADER>
        //
        //<TABLE BODY>
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            ArrayList rowValues;
            //
            if (jtableColsToInclude != null) {
                rowValues = HelpA.getLineValuesVisibleColsOnly_B(table, x, jtableColsToInclude);
            } else {
                rowValues = HelpA.getLineValuesVisibleColsOnly(table, x);
            }
            //
            //
            html += "<tr>";
            //
            for (int i = 0; i < rowValues.size(); i++) {
                html += "<td>" + rowValues.get(i) + "</td>";
            }
            //
            html += "</tr>";
            //
        }
        //</TABLE BODY>
        //
        html += "</table>";
        //
        //
        return html;
    }

    private String tableInvertToHTML(Table table, int startColumn, RowDataInvert[] cfg) {
        String csv = tableInvertToCSV(table, startColumn, cfg);
        ArrayList<InvertTableRow> tableRowsList = buildTableRowList(csv);
        //
        String html = "<table class='table-invert'>";
        //
        //
        for (InvertTableRow invertTableRow : tableRowsList) {
            html += "<tr>";
            html += "<td style='background-color:light-grey'>" + invertTableRow.getColumnName() + "</td>";
            //
            try{
               html += "<td>" + invertTableRow.getValue(0) + "</td>"; 
            }catch(Exception ex){
                html += "<td>" + MSG.VAL_MISSING_REPORT + "</td>";
            }
            //
            html += "</tr>";
        }
        //
        //
        html += "</table>";
        //
        return html;
    }

    private String tableInvertToCSV(Table table_invert, int startColumn, RowDataInvert[] rdi) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        String csv = "";
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            if (dataInvert.getVisible() == false) {
                continue;
            }
            //
            csv += dataInvert.getFieldNickName() + ";";
            //
            if (dataInvert.getUnit() instanceof String) {
                String unit = (String) dataInvert.getUnit();
                //
                if (unit.isEmpty() == false) {
                    csv += unit + ";";
                } else {
                    csv += "unit" + ";";
                }
                //
            }
            //
            for (int x = startColumn; x < getColumnCountTableInvert(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                csv += columnValue.getValue() + ";";
                // 
            }
            //
            csv += "\n";
            //
        }
        //
//        System.out.println("CSV: \n" + csv);
        //
        //
        return csv;
    }

    private ArrayList<InvertTableRow> buildTableRowList(String csv) {
        //
        String[] lines = csv.split("\n");
        //
        ArrayList<InvertTableRow> tableRowsList = new ArrayList<InvertTableRow>();
        //
        for (String line : lines) {
            String arr[] = line.split(";");

            String columnName = arr[0];
            String unit = arr[1];

            InvertTableRow row = new InvertTableRow(columnName, unit);

            for (int i = 2; i < arr.length; i++) {
                row.addValue(arr[i]);
            }
            //
            tableRowsList.add(row);
            //
        }
        return tableRowsList;
    }

    private int getColumnCountTableInvert(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        return tableInvert.getColumnCount();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jEditorPane1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Print Preview");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            //
            jEditorPane1.print();
            //
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setJobName("Recipe");
            //
        } catch (PrinterException ex) {
            Logger.getLogger(HTMLPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(HTMLPrint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(HTMLPrint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(HTMLPrint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(HTMLPrint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HTMLPrint().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
