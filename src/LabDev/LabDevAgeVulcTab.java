/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__AGEMENT;
import static LabDev.LabDevelopment_.TABLE__MAT_INFO;
import static LabDev.LabDevelopment_.TABLE__VULC;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_dev_aging");
        //
        TABLE_INVERT = null;
        //
        String agecode = "";
        //
        try {
            String q = "";
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
//        addTableInvertRowListener(TABLE_INVERT, this);
        //
        showTableInvert(null);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert material = new RowDataInvert(TABLE__AGEMENT, "ID", false, "AGEINGCODE", T_INV.LANG("AGEING CODE"), "", true, true, false);
        //
        return null;
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert material = new RowDataInvert(TABLE__VULC, "ID", false, "VULCCODE", T_INV.LANG("VULCANISATION CODE"), "", true, true, false);
        //
        return null;
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
