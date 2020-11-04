/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.LAB_DEV;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevHeaderComponent {

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

    private void clear() {
        getUpper().removeAll();
        getLower().removeAll();
        header.repaint();
    }

    public void tab_main_data() {
        //
        clear();
        //
        showStandard();
        //
    }

    public void tab_status() {
        //
        clear();
        //
        showStatusTab();
        //
    }

    private void showStatusTab() {
        //
        showStandard();
        //
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_4(), labDev.getRequester());
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_3(), "");
        //
        for (int i = 0; i < 3; i++) {
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
        buildJLabelJTextFieldComonent(upper, "Status", "Ausführen");
        //
        buildJLabelJTextFieldComonent(upper, "Aktualisiert", "11.03.2020");
        //
        for (int i = 0; i < 2; i++) {
            upper.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void buildJLabelJTextFieldComonent(JPanel upperOrLower, String jLabelVal, String jTextFieldVal) {
        JLabel label = new JLabel(jLabelVal);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        HelpA_.increase_font(label, 14);
        JTextField val = new JTextField(jTextFieldVal);
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        container.add(val);
        upperOrLower.add(container);
    }

}
