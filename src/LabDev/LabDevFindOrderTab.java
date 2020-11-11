/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.Lang.LNG;
import MCRecipe.MC_RECIPE;
import MCRecipe.SQL_A;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class LabDevFindOrderTab extends ChkBoxItemListComponent implements KeyListener {

    private final LabDevelopment labDev;
    private final SqlBasicLocal sql;
    private final MC_RECIPE mcRecipe;

    public LabDevFindOrderTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE mcRecipe) {
        this.labDev = labDev;
        this.sql = sql;
        this.mcRecipe = mcRecipe;
        mcRecipe.jTextField__lab_dev__find_order.addKeyListener(this);
    }

    public void go() {
        showCheckBoxComponent();
//        addFakeValuesToTable();

    }

    private void fillTable() {
        //
        Object[] selectedValues = getSelectedValuesFromTable(getPanel());
        //
        String q = SQL_A.find_order_lab_dev(selectedValues);
        //
        HelpA_.build_table_common(sql, mcRecipe, getTable(), q, new String[]{});
        //
        HelpA_.changeTableHeaderTitleOfOneColumn(getTable(), "WORDERNO", "AUFTRAG");
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

    private JPanel getPanel() {
        return mcRecipe.jPanel_lab_dev__find_order;
    }

    private JTable getTable() {
        return mcRecipe.jTable_lab_dev__find_order;
    }

    private void showCheckBoxComponent() {
        String[] status_list = LAB_DEV__STATUS.getLabDevStatusesAuto(LNG.LANG_ENG);
        addRows_B(status_list, getPanel(), new Dimension(200, LabDevFindOrderTab.HEIGHT));
    }

    public void testBtnClicked() {
        //
//        ArrayList<JPanelPrepM> list = getSelectedFromTable(getPanel());
//        //
//        for (JPanelPrepM jp : list) {
//            System.out.println("" + jp);
//        }
        //
//        HelpA_.markLastRowJtable(getTable());
//        HelpA_.markGivenRow(getTable(), 57);

        fillTable();
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
        HelpA_.markRowByValue_contains(getTable(), "WORDERNO", mcRecipe.jTextField__lab_dev__find_order.getText());
    }

    public static String random() {
        int x = (int) ((Math.random() * 5000) + 100);//((Math.random() * 100) + 1)
        return "" + x;
    }

}
