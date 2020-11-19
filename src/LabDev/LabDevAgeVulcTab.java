/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA_;
import forall.SqlBasicLocal;
import javax.swing.JComboBox;

/**
 *
 * @author KOCMOC
 */
public class LabDevAgeVulcTab extends LabDevTab {

    private Table TABLE_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;
    
    public LabDevAgeVulcTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }
    
    private void init(){
        refresh();
    }
    
    public void refresh(){
        fillAgeComboBox();
    }
    
    private String getAgeCode() {
        return "00011";
    }

    private String getVulcCode() {
        return "00014";
    }
    
    private JComboBox getAgeComboBox(){
        return mcRecipe.jComboBox_lab_dev__age;
    }
    
    private void fillAgeComboBox(){
        //
        String q = "select DISTINCT AGEINGCODE from MC_CPAGEMET";
        //
        HelpA_.fillComboBox(sql, getAgeComboBox(), q, null, false, false);
        //
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
