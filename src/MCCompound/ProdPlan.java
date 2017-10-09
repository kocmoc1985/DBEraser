/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import MCRecipe.Sec.PROC;
import MyObjectTableInvert.Run_Invert;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTable.SaveIndicator;
import MyObjectTable.Table;
import forall.GP;
import forall.HelpA;
import forall.ProgressBarB;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class ProdPlan extends BasicTab {

    private PROD_PLAN P_P;
    private TableBuilderInvert TABLE_BUILDER_INVERT;
    public Table TABLE_INVERT_2;
    private TableBuilderInvert TABLE_BUILDER_INVERT_2;
    private LinkedList<Integer> rowsToHighlight = new LinkedList<Integer>();
    public final static String ID = "id";
    public final static String PROD_ORDER = "Prod#Order";
    public final static String ARTIKEL = "Artikel";
    public final static String DESCRIPTION = "Omschrijving";
    public final static String BATCHES = "Batches";
    public final static String SORTEREN = "Sorteren";
    public final static String SEQUENCE = "Volgorde";
    public final static String NOTES = "Opmerkingen";

    public ProdPlan(SqlBasicLocal sql, SqlBasicLocal sql_additional, PROD_PLAN P_P) {
        super(sql, sql_additional, P_P);
        this.P_P = P_P;
        go();
    }

    private void go() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                buildProdPlanTable();
                buildProdPlanTableTemp();
                initializeSaveIndicators();
                //
                HelpA.markGivenRow(P_P.jTable1, 0);
                showTableInvert();
                //
                HelpA.markGivenRow(P_P.jTable1_2, 0);
                showTableInvert2();
            }
        });
    }

    public void table1Repport() {
        tableCommonExportOrRepport(P_P.jTable1, true);
    }

    public void table2Repport() {
        tableCommonExportOrRepport(P_P.jTable1_2, true);
    }

    public void table3Repport() {
        tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
    }

    public void table4Repport() {
        tableInvertExportOrRepport(TABLE_INVERT_2, 1,  getConfigTableInvert2());
   }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(P_P.jButton2Save, this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            if (TABLE_INVERT_2 == null) {
                return false;
            } else if (TABLE_INVERT_2.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
        }
        return false;
    }

    public void add_to_csv_table() {
        //
        JTable table = P_P.jTable1_2;
        //
        if (getUnsaved(1)) {
            saveChangesTableInvert(TABLE_INVERT_2);
        }
        //
        try {
            //
            ProgressBarB progressBarB = new ProgressBarB("Importing to CSV Table", table.getRowCount(), GP.IMAGE_ICON_URL_PROD_PLAN);
            //
            for (int i = 0; i < table.getRowCount(); i++) {
                String prodOrder = HelpA.getValueGivenRow(table, i, PROD_ORDER);
                addToRecipeCsvTable((i + 5), prodOrder);
                progressBarB.go(i + 1);
            }
            //
//            P_P.jTabbedPane1.setSelectedIndex(1);//show a specifig tab
            HelpA.openTabByName(P_P.jTabbedPane1, "CSV Table");
            //
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Add to CSV table failed: " + ex.toString());
        }

    }

    private void highLightRows(JTable table, String idColumnName) {
        String rowID = "" + table.getValueAt(table.getSelectedRow(), HelpA.getColByName(table, idColumnName));
        int rowid = Integer.parseInt(rowID);
        //
        rowsToHighlight.add(rowid);
        //
        paint_selected_rows_b(rowsToHighlight, table, idColumnName, Color.lightGray);
    }

    private void addToRecipeCsvTable(int column, String prodOrder) throws SQLException {
        //
        ResultSet rs = sql.execute(SQL_B.getOneOrderByPlanId(prodOrder), OUT);
        rs.beforeFirst();
        //
//        int column = Integer.parseInt((String) P_P.jComboBox1SelectColumn.getSelectedItem());
//        column += 4;// OBS!
        String release = "0";
        //
//        if (P_P.jTextField1.getText().isEmpty() == false) {
//            release = P_P.jTextField1.getText();
//        }
        //
        if (rs.next()) {
            String recipeName = rs.getString(ARTIKEL);
            String order = rs.getString(PROD_ORDER);
            int ammount = rs.getInt(BATCHES);
            //
            String procedure = SQL_A.generate_CSVColumn(PROC.PROC_P_03,recipeName, release, column, order, ammount, "null", "null");
            //
            OUT.showMessage("procedure: " + procedure);
            //
            ResultSet rs2 = sql.execute_2(procedure);
            //
            if (additionalOpsNeeded(rs2)) {
                additionalOps(rs2, recipeName, release, column, order, ammount);
            }
            //
        }
        //
        //
        P_P.buildCSVTable_2();
    }

    private boolean additionalOpsNeeded(ResultSet rs) {
        if (rs == null) {
            return false;
        } else {
            return true;
        }
    }

    private void additionalOps(ResultSet rs, String recipeName, String release, int column, String order, int ammount) {
        //
        JTable table = new JTable();
        table = HelpA.build_table_common_return(rs, table);
        //
        JScrollPane container = new JScrollPane(table);
        //
        JOptionPane.showMessageDialog(null, container, "Additional info is needed for recipe: " + recipeName, JOptionPane.PLAIN_MESSAGE);
        //
        //
        //
//        String recipeName2 = (String) table.getValueAt(table.getSelectedRow(), HelpA.getColByName(table, "Code"));
//        String release2 = (String) table.getValueAt(table.getSelectedRow(), HelpA.getColByName(table, "Release"));
        String recipeName2 = HelpA.getValueSelectedRow(table, "Code");
        String release2 = HelpA.getValueSelectedRow(table, "Release");
        //
        //
        String procedure = SQL_A.generate_CSVColumn(PROC.PROC_P_03,recipeName, release, column, order, ammount, recipeName2, release2);
        //
        System.out.println("procedure: " + procedure);
        //
        try {
            sql.execute(procedure, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildProdPlanTable() {
        try {
            String q = SQL_B.getProductionPlan();
            ResultSet rs = sql.execute(q, OUT);
            HelpA.build_table_common(rs, P_P.jTable1, q);
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildProdPlanTableTemp() {
        //
        try {
            String q = SQL_B.getProductionPlanTemp();
            ResultSet rs = sql.execute(q, OUT);
            HelpA.build_table_common(rs, P_P.jTable1_2,q);
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.hideColumnByName(P_P.jTable1_2, ID);
    }

    private JComboBox buildChoseSeqNrComboBox(JComboBox box) {
        //
        int chosenSeqNr = Integer.parseInt(HelpA.getValueSelectedRow(P_P.jTable1_2, SEQUENCE));
        //
//        int highestSeqNr = getHighestSeqNumber();
        //
        String[] items = new String[chosenSeqNr - 1];
        //
        for (int i = 0; i < chosenSeqNr - 1; i++) {
            items[i] = "" + (i + 1);
        }
        //
        HelpA.fillComboBox(box, items, null);
        //
        if (HelpA.chooseFromComboBoxDialog(box, "Move to sequence position:")) {
            return box;
        } else {
            return null;
        }
    }

    public void changeSequencePosition() {
        //
        JComboBox box = buildChoseSeqNrComboBox(new JComboBox());
        //
        if (box == null) {
            return;
        }
        //
        String moveToPosition = HelpA.getComboBoxSelectedValue(box);
        //
        String actSeqPos = HelpA.getValueSelectedRow(P_P.jTable1_2, SEQUENCE);
        //
        String q = SQL_B.updatePositions(moveToPosition, actSeqPos);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        String id = HelpA.getValueSelectedRow(P_P.jTable1_2, ID);
        //
        String q2 = SQL_B.updatePositionsB(moveToPosition, id);
        //
        try {
            sql.execute(q2, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        buildProdPlanTableTemp();
        //
    }

    private int getHighestSeqNumber() {
        String q = SQL_B.prodPlanGetHighestSeqNr();
        try {
            ResultSet rs = sql.execute(q, OUT);
            //
            if (rs.next()) {
                String nr = rs.getString(SEQUENCE);
                return Integer.parseInt(nr);
            } else {
                return 0;
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void insertIntoTempTable() {
        //
        if (HelpA.getRowCount(sql, "Production_Plan_Csv_Temp") >= 16) {
            HelpA.showNotification("Maximum ammount reached (16)");
            return;
        }
        //
        int seqNr = getHighestSeqNumber();
        //
        String prodOrder = HelpA.getValueSelectedRow(P_P.jTable1, PROD_ORDER);
        //
        String q = SQL_B.prodPlanAddToTempTable(prodOrder);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        String id = HelpA.getLastIncrementedId(sql, "Production_Plan_Csv_Temp");
        String q2 = SQL_B.updateSequenceProdPlanTemp(id, seqNr + 1);
        //
        try {
            sql.execute(q2, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        buildProdPlanTableTemp();
        //
        highLightRows(P_P.jTable1, PROD_ORDER);
    }

    public void deleteEntryTempTable() {
        String prodOrder = HelpA.getValueSelectedRow(P_P.jTable1_2, PROD_ORDER);
        String id = HelpA.getValueSelectedRow(P_P.jTable1_2, ID);
        //
        String q = SQL_B.deleteEntryTempTable(id);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        String seqPos = HelpA.getValueSelectedRow(P_P.jTable1_2, SEQUENCE);
        String q2 = SQL_B.updatePositionsErase(seqPos);
        //
        try {
            sql.execute(q2, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        buildProdPlanTableTemp();
        //
        unpaintRow_b(P_P.jTable1, prodOrder, PROD_ORDER, rowsToHighlight);
        //
    }

    public void deleteAllRecordsTempTable() {
        if (HelpA.confirm("Delete all rows?") == false) {
            return;
        }
        //
        String q = SQL_B.deleteAllFromTempTable();
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(ProdPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        buildProdPlanTableTemp();
        //
        //
        rowsToHighlight = new LinkedList<Integer>();
        unpaintAllRows_a(P_P.jTable1);
        //
    }

    public void showAdditional() throws SQLException {
        //
        JTable table = P_P.jTable1;
        //
//        String recipeCode = (String) table.getValueAt(table.getSelectedRow(), HelpA.getColByName(table, "RCODE"));
        String recipeCode = HelpA.getValueSelectedRow(table, ARTIKEL);
        String release = "0";
        //
        ResultSet rs_sequence = sql.execute(SQL_A.fnSequenceGet(PROC.PROC_P_01,recipeCode, release), OUT);
        ResultSet rs_recipe_basic = sql.execute_2(SQL_A.recipeBasicRZPT(recipeCode, release));
        //
        JTable table_sequence = new JTable();
        table_sequence = HelpA.build_table_common_return(rs_sequence, table_sequence);
        JScrollPane jsp_1 = new JScrollPane(table_sequence);
        jsp_1.setPreferredSize(new Dimension(920, 300));
        //
        HelpA.hideColumnByName(table_sequence, "Code");
        HelpA.hideColumnByName(table_sequence, "Release");
        jsp_1.setBorder(BorderFactory.createTitledBorder("Sequence"));
        //
        //
        JTable table_recipe_basic = new JTable();
        table_recipe_basic = HelpA.build_table_common_return(rs_recipe_basic, table_recipe_basic);
        JScrollPane jsp_2 = new JScrollPane(table_recipe_basic);
        jsp_2.setPreferredSize(new Dimension(920, 300));
        //
        jsp_2.setBorder(BorderFactory.createTitledBorder("Ingredients"));
        //
        //
        JPanel container = new JPanel(new GridLayout(2, 1, 10, 10));
        //
        container.add(jsp_1);
        container.add(jsp_2);
        //
        JOptionPane.showMessageDialog(null, container, "Recipe Data", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        RowDataInvert prodorder = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, PROD_ORDER, "PROD ORDER", "", false, true, false);
        RowDataInvert artikel = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, ARTIKEL, "ARTIKEL", "", true, true, false);
        RowDataInvert descr = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, DESCRIPTION, "DESCRIPTION", "", true, true, false);
        RowDataInvert batches = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, BATCHES, "BATCHES", "", false, true, false);
        RowDataInvert sorteren = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, SORTEREN, "SORT", "", true, true, false);
        RowDataInvert sequence = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, SEQUENCE, "SEQUENCE", "", false, true, false);
        RowDataInvert notes = new RowDataInvert("Production_Plan_Csv", PROD_ORDER, false, NOTES, "NOTES", "", true, true, false);
        //
        prodorder.setUneditable();
        artikel.setUneditable();
        descr.setUneditable();
        batches.setUneditable();
        sorteren.setUneditable();
        sequence.setUneditable();
        notes.setUneditable();
        //
        RowDataInvert[] rows = {prodorder, artikel, descr, batches, sorteren, sequence, notes};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert2() {
        RowDataInvert prodorder = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, PROD_ORDER, "PROD ORDER", "", false, true, false);
        RowDataInvert artikel = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, ARTIKEL, "ARTIKEL", "", true, true, false);
        RowDataInvert descr = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, DESCRIPTION, "DESCRIPTION", "", true, true, false);
        RowDataInvert batches = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, BATCHES, "BATCHES", "", false, true, false);
        RowDataInvert sorteren = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, SORTEREN, "SORT", "", true, true, false);
        RowDataInvert sequence = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, SEQUENCE, "SEQUENCE", "", false, true, false);
        RowDataInvert notes = new RowDataInvert("Production_Plan_Csv_Temp", ID, false, NOTES, "NOTES", "", true, true, false);
        //
        prodorder.setUneditable();
        artikel.setUneditable();
        sequence.setUneditable();
        //
        RowDataInvert[] rows = {prodorder, artikel, descr, batches, sorteren, sequence, notes};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
//        if (TABLE_INVERT == null) {
//            return;
//        }
        //
        String SELECTED_ORDER = HelpA.getValueSelectedRow(P_P.jTable1, PROD_ORDER);//P_P.getSelectedId(P_P.jTable1)
        //
        OUT.showMessage("Selected id: " + SELECTED_ORDER);
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "prod_plan_1");
        //
        TABLE_INVERT = null;
        //
        if (SELECTED_ORDER == null) {
            return;
        }
        //
        try {
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(SQL_B.getOneOrderByPlanId(SELECTED_ORDER));
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(P_P.jPanel_Table_Invert_1, TABLE_INVERT);
    }

    public void showTableInvert2() {
        //
        String id = HelpA.getValueSelectedRow(P_P.jTable1_2, ID);//P_P.getSelectedId(P_P.jTable1_2)
        //
        OUT.showMessage("Selected id: " + id);
        //
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert(OUT, sql, getConfigTableInvert2(), false, "prod_plan_2");
        //
        TABLE_INVERT_2 = null;
        //
        if (id == null) {
            return;
        }
        //
        try {
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(SQL_B.getOneOrderByPlanIdTemp(id));
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
        showTableInvert(P_P.jPanel_Table_Invert_1_2, TABLE_INVERT_2);
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
