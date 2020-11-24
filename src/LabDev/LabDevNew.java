/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__MCCPWOTEST;
import static LabDev.LabDevelopment_.TABLE__TEST_PROCEDURE;
import MCRecipe.Lang.T_INV;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author KOCMOC
 */
public class LabDevNew extends LabDevTab_ implements ActionListener{

    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevNew(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        initializeSaveIndicators();
        getSaveButton().addActionListener(this);
    }
    
    public void refresh(){
        
    }
    
    private JButton getSaveButton(){
        return mcRecipe.jButton__lab_dev__new;
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
//       [ORDERNO] ---> ??? Needed ?
//      ,[SCOPE]
//      ,[CODE]
//      ,[PREFVULC]
//      ,[PREFAGE]
//      ,[TESTCODE]
//      ,[TESTCOND]
//      ,[TESTREM1]
//      ,[TESTREM2]
//      ,[UpdatedOn]
//      ,[Test_Condition_NUM]
//      ,[UpdatedBy]
//      ,[ID_Wotest]
//      ,[TagId]
        //
        RowDataInvert scope = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "SCOPE", T_INV.LANG("SCOPE"), "", true, true, false);
        //
        RowDataInvert[] rows = {};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "lab_dev__new");
        //
        TABLE_INVERT = null;
        //
        String id = "";
        //
        try {
            String q = "";
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);

        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class
                    .getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mcRecipe.jPanel77);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(getSaveButton(), this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        //
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                return true;
            }
            //
        }
        //
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if(e.getSource().equals(getSaveButton())){
            
        }
        //
    }
}
