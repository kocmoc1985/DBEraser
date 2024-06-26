/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.Ingredients;
import MCRecipe.MC_RECIPE;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class AdministrateIngredGroups extends AdministrateRecipeGroups_ {

    public AdministrateIngredGroups(String title,MC_RECIPE mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        super(title,mc_recipe, sql, sql_additional);
    }
    
    @Override
    public JTable getJTable() {
        return jTable1;
    }
    
    @Override
    public void setTableTitle() {
        jLabel1.setText("INGRED GROUPS");
    }

    @Override
    public void actionsAfterShowTable() {
    }
    
    @Override
    public String addEntryQuery(){
        return "insert into " + TABLE_NAME + " values('NEW','NEW')";
    }
    
    @Override
     public void initBasicTab() {
        //
        TABLE_NAME = "Ingred_Group";
        TABLE_ID = "Id";
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                //
                RowDataInvert id = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Id", "ID", "", true, true, false);
                //
                RowDataInvert group = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Grupp", "GROUP", "", true, true, false);
                //
                RowDataInvert descr = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Descr", "DESCRIPTION", "", true, true, false);
                //
                id.setUneditable();
                //
                RowDataInvert[] rows = {id, group, descr};
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                //
                TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "vendors_2");
                //
                TABLE_INVERT = null;
                //
                String id = HelpA.getValueSelectedRow(jTable1, TABLE_ID);
                //
                try {
                    String q = "select * from " + TABLE_NAME
                            + " where " + TABLE_ID + "=" + id;
                    //
                    OUT.showMessage(q);
                    TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q,this);
                } catch (SQLException ex) {
                    Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
                    TABLE_BUILDER_INVERT.showMessage(ex.toString());
                }
                //
                showTableInvert(jPanel1, TABLE_INVERT);
            }

            @Override
            public void initializeSaveIndicators() {
                SaveIndicator saveIndicator1 = new SaveIndicator(jButton_Save, this, 1);
            }

            @Override
            public void fillNotes() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean getUnsaved(int nr) {
                if (nr == 1) {
                    //
                    if (TABLE_INVERT == null) {
                        return false;
                    } else if (unsavedEntriesExist(TABLE_INVERT)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                        return true;
                    }
                    //
                }
                return false;
            }

            @Override
            public boolean fieldsValidated(boolean insert) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
}
