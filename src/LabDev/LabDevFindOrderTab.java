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
import MCRecipe.SQL_A;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    
    public LabDevFindOrderTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }
    
    private void init(){
        mcRecipe.jTable_lab_dev__find_order.addMouseListener(this);
        mcRecipe.jTextField__lab_dev__find_order.addKeyListener(this);
        getFilterBtn().addActionListener(this);
        getSetOrderBtn().addActionListener(this);
    }
    
    public void go() {
        showCheckBoxComponent();
    }
    
    private void fillTable() {
        //
        Object[] selectedValues = getSelectedValuesFromTable(getPanel());
        //
        String q = SQL_A.find_order_lab_dev__dynamic(selectedValues);
        //
        HelpA_.build_table_common(sql, mcRecipe, getTable(), q, new String[]{"ID"});
        //
        // Translating status - as status is always saved in ENG in the database
        LAB_DEV.find_order_tab_translate_status(getTable(), "WOSTATUS");
        //
        LAB_DEV.find_order_tab_change_jtable__header(getTable());
        //
        HelpA_.setEnabled(getSetOrderBtn(), true);
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
    
    private JButton getSetOrderBtn(){
        return mcRecipe.jButton__lab_dev_find_order_tab__set_order;
    }
    
    private JButton getFilterBtn(){
        return mcRecipe.jButton__lab_dev_find_order_tab__filter;
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
    
    private void setOrderBtnClicked() {
        //
        JTable table = getTable();
        //
        if (HelpA_.rowSelected(table) == false) {
            HelpA_.showNotification(MSG.MSG_5());
            return;
        }
        //
        String selectedOrder = HelpA_.getValueSelectedRow(table, "WORDERNO");
        labDev.setOrderNo(selectedOrder);
        //
    }
    
    private void filterButtonClicked() {
        //
        fillTable();
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
//        HelpA_.markRowByValue_contains(getTable(), "Title 1", mcRecipe.jTextField__lab_dev__find_order.getText());
        HelpA_.markRowByValue_contains(getTable(), "WORDERNO", getTexField().getText());
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
    public void actionPerformed(ActionEvent e) {
        //
        if(e.getSource() == getSetOrderBtn()){
            table_clicked();
        }else if(e.getSource() == getFilterBtn()){
            filterButtonClicked();
        }
        //
    }
    
    private void table_clicked() {
        //
        JTable table = mcRecipe.jTable_lab_dev__find_order;
        //
        String order = HelpA_.getValueSelectedRow(table, "WORDERNO"); // Auftrag
        labDev.setOrderNo(order);
        //
        HelpA_.openTabByName(labDev.getTabbedPane(), LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA());
        //
        labDev.lab_dev_tab__tab_main_data__clicked();
        //
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
    public String[] getComboParams() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
