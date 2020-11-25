/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_A;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public abstract class LabDevTab_ extends BasicTab {

    protected final MC_RECIPE_ mcRecipe;
    protected final LabDevelopment_ labDev;

    public LabDevTab_(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT);
        this.mcRecipe = (MC_RECIPE_) OUT;
        this.labDev = labDev;
    }

    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        //OBS! IS ENABLED ON DOUBLE CLICK
        enableEditingLongValues(me, ti);
        //
        trimValueTableInvert(col_name, ti);
        //
    }

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        //
        //AUTOMATIC **SQL** INPUT LENGTH VALIDATION [2020-11-22]
        Validator_MCR.validateMaxInputLengthAutomatic(sql, jli);
        //
        //
        //
        if (jli.getValidateDate()) {
            //
            Validator_MCR.validateDate(jli);
            //
        } //Manual input length validation below. YES IS USED [2020-11-24]
        else if (jli.getInputLengthValidationManual() > 0) {
            //[$TEST-VAR-SAVE$] -> this TAG here, does not mean that the functionality below only belongs to this TAG
            JTextFieldInvert jtf = (JTextFieldInvert) jli;
            //
            int inputLength = jtf.getInputLengthValidationManual();
            //
            if (inputLength > 0) {
                Validator_MCR.validateMaxInputLength(jli, inputLength);
            }
            //
        }
        //
    }

    //=========================================================================
    //=========================================================================
    //=========================================================================
    //BELOW IS FOR MULTIPLE COMBOBOX SEARCH/FILTER
    private static long prevCall;

    public boolean delay() {
        if (Math.abs(System.currentTimeMillis() - prevCall) < 1000) {
            prevCall = System.currentTimeMillis();
            return false;
        } else {
            prevCall = System.currentTimeMillis();
            return true;
        }
    }

    public void fillComboBox(final JComboBox box, final String colName, SqlBasicLocal sql, String query) {
        // 
        if (delay() == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                Object selection = box.getSelectedItem();
                //
//                JComboBoxA boxA = (JComboBoxA) box;
                //
                HelpA_.fillComboBox(sql, box, query, null, false, false);
                //
                box.setSelectedItem(selection);
                //
            }
        });

    }

    public abstract String getQuery(String procedure, String colName, String[] comboParameters);

    public abstract String[] getComboParams();

    public void addMouseListenerJComboBox(JComponent c, MouseListener ml) {
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
