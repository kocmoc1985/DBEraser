/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.TableInvert;
import forall.SqlBasicLocal;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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

    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        //OBS! IS ENABLED ON DOUBLE CLICK
        enableEditingLongValues(me, ti);
        //
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
        } 
        //Manual input length validation below. YES IS USED [2020-11-24]
        else if (jli.getInputLengthValidationManual() > 0) {
            //
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
}
