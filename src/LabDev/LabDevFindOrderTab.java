/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import LabDev.sec.ChkBoxItemListComponent;
import MCRecipe.Lang.LAB_DEV;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.MSG;
import MCRecipe.MC_RECIPE;
import MCRecipe.SQL_A_;
import MCRecipe.Sec.PROC;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import forall.TextFieldCheck;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class LabDevFindOrderTab extends ChkBoxItemListComponent implements KeyListener, MouseListener, ActionListener {
    
    private boolean oneTimeFlag = false;
    private String materialPrev = "";
    
    public LabDevFindOrderTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }
    
    private void init() {
        //
        mcRecipe.jTable_lab_dev__find_order.addMouseListener(this);
        mcRecipe.jTextField__lab_dev__find_order.addKeyListener(this);
        getFilterBtn().addActionListener(this);
        getSetOrderBtn().addActionListener(this);
        getPrintBtn().addActionListener(this);
        getNewBtn().addActionListener(this);
        getCopyBtn().addActionListener(this);
        getDeleteBtn().addActionListener(this);
        //
        fillTable_filter(); // show all at startup
        //
    }
    
    public void refresh() {
        //
        getTexField().setText("");
        showCheckBoxComponent();
        //
        // Refresh after adding new
        if (getTable().getRowCount() == 1) {
            fillTable_order(labDev.getOrderNo());
        } else {
            //
            fillTable_filter();
            //
            //=================================================================
            //
            //[#SET MATERIAL - FROM OUTSIDE LABDEV#]-> Search for this tag ****************
            //
//            String material = labDev.getMaterial();
//            if (material != null && material.isEmpty() == false) {
//                //
//                if (material.equals(materialPrev) == false) {
//                    fillTable_material(material);
//                }else{
//                    fillTable_filter();
//                }
//                //
//            }
            //==================================================================
        }
        //
    }
    
    private void refresh__insert_and_copy(String order) {
        labDev.setOrderNo(order);
        fillTable_order(order);
        HelpA.openTabByName(labDev.getTabbedPane(), LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA());
        labDev.lab_dev_tab__tab_main_data__clicked();
        labDev.setPrevTabName(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA()); // Important!
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource() == getSetOrderBtn()) {
            table_clicked();
        } else if (e.getSource() == getFilterBtn()) {
            fillTable_filter();
        } else if (e.getSource() == getPrintBtn()) {
            tableCommonExportOrRepport(getTable(), true);
        } else if (e.getSource() == getNewBtn()) {
            createOrder(LabDevelopment_.TABLE__MC_CPWORDER, "WORDERNO", null);
        } else if (e.getSource() == getCopyBtn()) {
            copyOrder(LabDevelopment_.TABLE__MC_CPWORDER, "WORDERNO", null);
        } else if (e.getSource() == getDeleteBtn()) {
            deleteOrder();
        }
        //
    }
    
    private boolean copyOrder(String tableName, String colName, String regex) {
        //
        JTable table = getTable();
        String order = HelpA.getValueSelectedRow(table, "WORDERNO");
        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Table row not chosen"));
            return false;
        }
        //
        if (HelpA.confirm(MSG.LANG("Copy order ") + order + "?") == false) {
            return false;
        }
        //
        String q = "SELECT DISTINCT " + colName + " from " + tableName + " WHERE " + colName + " = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, regex, 15, 22);
        //
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, MSG.LANG("Create new order"), order);
        String order_new = tfc.getText();
        //
        if (order == null || yesNo == false) {
            return false;
        }
        //
        //
        String q_copy = SQL_A_.lab_dev_find_order__copy(PROC.PROC_85, order_new, order, null, null);
        //
        try {
            sql.execute(q_copy, OUT);
            refresh__insert_and_copy(order_new);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevFindOrderTab.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }
    
    private boolean deleteOrder() {
        //
        JTable table = getTable();
        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Table row not chosen"));
            return false;
        }
        //
        String id = HelpA.getValueSelectedRow(table, "ID");
        String order = HelpA.getValueSelectedRow(table, "WORDERNO");
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return false;
        }
        //
        if (HelpA.confirm(MSG.LANG("Confirm deletion of: ") + order + "?") == false) {
            return false;
        }
        //
        if (order == null || order.isEmpty()) {
            return false;
        }
        //
//        String q = SQL_A_.find_order_lab_dev__delete_order(id);
        String q = SQL_A_.find_order_lab_dev__delete_order(PROC.PROC_80, order);
        //
        try {
            sql.execute(q, OUT);
            fillTable_filter();
            labDev.setOrderNo("");
            labDev.refreshHeader();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevFindOrderTab.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }
    
    private boolean createOrder(String tableName, String colName, String regex) {
        //
        String q = "SELECT DISTINCT " + colName + " from " + tableName + " WHERE " + colName + " = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, regex, 15, 22);
        //
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, MSG.LANG("Create new order"));
        String order = tfc.getText();
        //
        if (order == null || yesNo == false) {
            return false;
        }
        //
        String q_insert = SQL_A_.lab_dev__find_order_tab__create__new(tableName, order);
        //
        try {
            //
            sql.execute(q_insert, OUT);
            //
            refresh__insert_and_copy(order);
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return false;
        //
    }
    
    private void fillTable_filter() {
        //
        Object[] selectedValues = getSelectedValuesFromTable(getPanel());
        //
        String q = SQL_A_.find_order_lab_dev__dynamic(selectedValues);
        //
        fillTable(q);
    }
    
    private void fillTable_material(String material) {
        //
        String q = SQL_A_.find_order_lab_dev__by_material(PROC.PROC_86, null, material, null, null, null, null, null, null);
        //
        fillTable(q);
        //
    }
    
    private void fillTable_order(String orderno) {
        //
        String q = SQL_A_.find_order_lab_dev__by_order(orderno);
        //
        fillTable(q);
        //
    }
    
    private void fillTable(String q) {
        //
        HelpA.build_table_common(sql, mcRecipe, getTable(), q, new String[]{"ID"});
        //
        // Translating status - as status is always saved in ENG in the database
        LAB_DEV.find_order_tab_translate_status(getTable(), "WOSTATUS");
        //
        LAB_DEV.find_order_tab_change_jtable__header(getTable());
        //
        HelpA.setEnabled(getSetOrderBtn(), true);
        //
    }
    
    private JPanel getPanel() {
        return mcRecipe.jPanel_lab_dev__find_order;
    }
    
    private JTable getTable() {
        return mcRecipe.jTable_lab_dev__find_order;
    }
    
    private JTextField getTexField() {
        return mcRecipe.jTextField__lab_dev__find_order;
    }
    
    private JButton getDeleteBtn() {
        return mcRecipe.jButton_lab_dev__delete_order_btn;
    }
    
    private JButton getNewBtn() {
        return mcRecipe.jButton__lab_dev__new_order_btn;
    }
    
    private JButton getCopyBtn() {
        return mcRecipe.jButton_lab_dev__copy_order_btn;
    }
    
    private JButton getSetOrderBtn() {
        return mcRecipe.jButton__lab_dev_find_order_tab__set_order;
    }
    
    private JButton getFilterBtn() {
        return mcRecipe.jButton__lab_dev_find_order_tab__filter;
    }
    
    private JButton getPrintBtn() {
        return mcRecipe.jButton_lab_dev__findorder_tab__print;
    }
    
    private void showCheckBoxComponent() {
        //
        if (oneTimeFlag == false) {
            oneTimeFlag = true;
            String[] status_list = LAB_DEV__STATUS.getLabDevStatusesAuto(LNG.LANG_ENG);
            addRows_B(status_list, getPanel(), new Dimension(200, LabDevFindOrderTab.HEIGHT));
        }
        //
    }
    
    private void table_clicked() {
        //
        JTable table = getTable();
        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Please choose a row from table"));
            return;
        }
        //
        String order = HelpA.getValueSelectedRow(table, "WORDERNO"); // Auftrag
        labDev.setOrderNo(order);
        //
        HelpA.openTabByName(labDev.getTabbedPane(), LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA());
        //
        labDev.lab_dev_tab__tab_main_data__clicked();
        //
    }
    
    private void filterButtonClicked() {
        //
        fillTable_filter();
        //
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
//        HelpA.markRowByValue_contains(getTable(), "Title 1", mcRecipe.jTextField__lab_dev__find_order.getText());
        HelpA.markRowByValue_contains(getTable(), "WORDERNO", getTexField().getText());
    }
    
    private static String random() {
        int x = (int) ((Math.random() * 5000) + 100);//((Math.random() * 100) + 1)
        return "" + x;
    }
    
    private void addFakeValuesToTable() {
        //
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        //
        for (int i = 0; i < 100; i++) {
            Object[] jtableRow = new Object[]{random(), random(), random(), random()};
            model.addRow(jtableRow);
        }
        //
    }
    
    @Override
    public void fillNotes() {
    }
    
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        return null;
    }
    
    @Override
    public void showTableInvert() {
    }
    
    @Override
    public void initializeSaveIndicators() {
    }
    
    @Override
    public boolean getUnsaved(int nr) {
        return false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        //
        JTabbedPane jtb = labDev.getTabbedPane();
        //
        if (e.getSource() == mcRecipe.jTable_lab_dev__find_order && (e.getClickCount() == 2)) {
            table_clicked();
        }
        //
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public String[] getComboParams__mcs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getQuery__mcs(String procedure, String colName, String[] comboParameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
