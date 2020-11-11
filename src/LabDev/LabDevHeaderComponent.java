/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.Lang.LAB_DEV;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.MSG;
import MCRecipe.SQL_A;
import forall.GP;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevHeaderComponent implements ItemListener {

    private final JPanel header;
    private final SqlBasicLocal sql;
    private final LabDevelopment labDev;

    public LabDevHeaderComponent(JPanel header, SqlBasicLocal sql, LabDevelopment labDev) {
        this.header = header;
        this.sql = sql;
        this.labDev = labDev;
    }

    private JPanel getUpper() {
        return (JPanel) header.getComponent(0);
    }

    private JPanel getLower() {
        return (JPanel) header.getComponent(1);
    }

    private void updateGraphics() {
        header.repaint();
        header.updateUI();
    }

    private void clear() {
        getUpper().removeAll();
        getLower().removeAll();
        updateGraphics();
    }

    public void tab_notes() {
        tab_main_data();
    }

    public void tab_material_info() {
        tab_main_data();
    }

    public void tab_main_data() {
        clear();
        showStandard();
        updateGraphics();
    }

    public void tab_status() {
        clear();
        showStandard();
        showStatusTab();
        updateGraphics();
    }

    private void showStatusTab() {
        //
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_4(), labDev.getRequester());
        //
        for (int i = 0; i < 4; i++) {
            lower.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    /**
     * For "Kopfdaten / Main Data" also probably suits for other
     */
    private void showStandard() {
        //
        JPanel upper = getUpper();
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_1(), labDev.getOrderNo());
        //
        //
        buildJLabelJComboComponent(upper, LAB_DEV.LBL_2(), LAB_DEV__STATUS.getLabDevStatusesAuto(LNG.LANG_ENG));
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_5(), labDev.getUpdatedOn());
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_6(), labDev.getUpdatedBy());
        //
        for (int i = 0; i < 1; i++) {
            upper.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void buildJLabelJTextFieldComonent(JPanel upperOrLower, String jLabelVal, String jTextFieldVal) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JTextField val = new JTextField(jTextFieldVal);
        val.setEditable(false);
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        container.add(val);
        upperOrLower.add(container);
    }

    private void buildJLabelJComboComponent(JPanel upperOrLower, String jLabelVal, String[] values) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JComboBox box = new JComboBox();
        box = HelpA_.fillComboBox_simple(box, values, null);
        box.setEditable(true);
        //
        String actual_status = getStatusInActLang();
        box.setSelectedItem(actual_status);
        //
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        box.addItemListener(this);
        container.add(box);
        upperOrLower.add(container);
    }

    private JLabel buildStandardLabel(String jLabelVal) {
        JLabel label = new JLabel(jLabelVal);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        HelpA_.increase_font(label, 14);
        return label;
    }
    
    private String getStatusInActLang(){
        String status_eng = HelpA_.getSingleParamSql(sql, LabDevelopment.TABLE__MC_CPWORDER, "WORDERNO", labDev.getOrderNo(), "WOSTATUS", false);
        String status_act_lang = LAB_DEV__STATUS.getStatusActLang(LNG.LANG_ENG, status_eng);
        return status_act_lang;
    }

    private void saveStatus(Object item) {
        //
        String status = item.toString();
        //
        //OBS! VERY IMPORATN -> WHEN SAVING STATUS IT SHOULD BE ALWAYS "ENG"
        String status_eng = LAB_DEV__STATUS.getStatusEng(LNG.LANG_ENG, status);
        //
        if (HelpA_.confirm(MSG.MSG_4(status)) == false) {
            return;
        }
        //
        String q = SQL_A.save_status_lab_dev(status_eng,labDev.getOrderNo());
        //
        System.out.println("q: " + q);
        //
        try {
            sql.execute(q);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevHeaderComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //
        System.out.println("BOX ITEM STATE CHANGED: " + e.getItem() + " / " + e.getStateChange());
        //
        if (e.getStateChange() == 1) {
            saveStatus(e.getItem());
        }
        //
    }

}
