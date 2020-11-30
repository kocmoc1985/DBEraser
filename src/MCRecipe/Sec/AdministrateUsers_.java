/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.Ingredients;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_B;
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

/**
 *
 * @author KOCMOC
 */
public class AdministrateUsers_ extends AdministrateRecipeGroups_ {

    public static final String USER_ADM_TBL_NAME = "MCRecipeUsers";

    public AdministrateUsers_(String title, MC_RECIPE_ mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        super(title, mc_recipe, sql, sql_additional);
    }

    

    @Override
    public void setTableTitle() {
        jLabel1.setText("USERS");
    }

    @Override
    public String addEntryQuery() {
        return "insert into " + TABLE_NAME
                + " values('new','new','','" + HelpA.updatedOn() + "','" + HelpA.updatedOn() + "')";
    }

    @Override
    public void initBasicTab() {
        //
        TABLE_NAME = USER_ADM_TBL_NAME;
        TABLE_ID = "id";
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                RowDataInvert id = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "id", "ID", "", true, true, false);
                //
                RowDataInvert user = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "userName", "USER", "", true, true, false);
                //
                RowDataInvert pass = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "pass", "PASS", "", true, true, false);
                //
                String fixedComboValues = "admin,user,useradvanced,developer";
                RowDataInvert role = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues, null, "", TABLE_NAME, TABLE_ID, false, "role", "ROLE", "", true, true, false);
                role.enableFixedValues();
//              RowDataInvert role = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "role", "ROLE", "", true, true, false);
                //
                RowDataInvert dateCreated = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "dateCreated", "CREATED ON", "", true, true, false);
                //
                RowDataInvert dateChanged = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "dateChanged", "UPDATED ON", "", true, true, false);
                //
                id.setUneditable();
                dateCreated.setUneditable();
                dateChanged.setUneditable();
                //
                RowDataInvert[] rows = {id, user, pass, role, dateCreated, dateChanged};
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "vendors_2");
                //
                TABLE_INVERT = null;
                //
                String id = HelpA.getValueSelectedRow(jTable1, TABLE_ID);
                //
                try {
                    String q = "select * from " + TABLE_NAME
                            + " where " + TABLE_ID + "= " + id;
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
        };
    }
}
