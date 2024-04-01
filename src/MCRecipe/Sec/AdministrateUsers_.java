/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import BuhInvoice.DB;
import BuhInvoice.JSon;
import BuhInvoice.sec.LANG;
import LabDev.Validator_MCR;
import MCRecipe.Ingredients;
import MCRecipeLang.T_INV;
import MCRecipe.MC_RECIPE;
import static MCRecipe.MC_RECIPE.ROLE_ADMIN;
import static MCRecipe.MC_RECIPE.ROLE_ADVANCED_USER;
import static MCRecipe.MC_RECIPE.ROLE_COMMON_USER;
import static MCRecipe.MC_RECIPE.ROLE_DEVELOPER;
import MCRecipe.SQL_B;
import MCRecipeLang.MSG;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class AdministrateUsers_ extends AdministrateRecipeGroups_ {

    public static final String USER_ADM_TBL_NAME = "MCRecipeUsers";
    public static final String USER_NAME_FIELD = "userName";
    public static final String PASS_FIELD = "pass";

    public AdministrateUsers_(String title, MC_RECIPE mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        super(title, mc_recipe, sql, sql_additional);
//        HelpA.setUneditableJTable(jTable1);

    }

    @Override
    public void setAddButtonToolTipText() {
        this.jButtonRecipeDetailedAddNewRecipe.setToolTipText("Add new user");
    }

    @Override
    public JTable getJTable() {
        return jTable1;
    }

    @Override
    public void setTableTitle() {
        jLabel1.setText("USERS");
    }

    @Override
    public String addEntryQuery() {
        return "insert into " + TABLE_NAME
                + " values('new','new','" + ROLE_COMMON_USER + "','" + HelpA.updatedOn() + "','" + HelpA.updatedOn() + "')";
    }

    @Override
    public void saveButtonClicked() {
        //#MCRECIPE-INPUT-VALIDATION#
        if (basicTab.fieldsValidated(false)) {
            super.saveButtonClicked(); //To change body of generated methods, choose Tools | Templates.
        } 
    }

    @Override
    public void initBasicTab() {
        //
        TABLE_NAME = USER_ADM_TBL_NAME;
        TABLE_ID = "id";
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {

            @Override
            public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
                //#MCRECIPE-INPUT-VALIDATION#
                //
                JLinkInvert jli = (JLinkInvert) ke.getSource();
                //
                String col_name = ti.getCurrentColumnName(ke.getSource());
                //
                // You call this one here to unmark the row marked in RED when the row is not empty
                containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert());
                //
                if (col_name.equals(USER_NAME_FIELD)) {
                    //
                    if (Validator_MCR.validateMaxInputLengthAutomatic(sql, jli)) {
                        Validator_MCR.checkForSqlReservedWords(jli);//#MCRECIPE-CHECK-SQL-RESERVED-WORDS#
                    }
                    //
                } else {
                    // Perform basic checking for other fields
                    super.keyReleasedForward(ti, ke);
                }
                //
            }

            @Override
            public boolean fieldsValidated(boolean insert) {
                // This one is called when the saved button is pressed
                //#MCRECIPE-INPUT-VALIDATION#
                if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                    HelpA.showNotification(MSG.cannotSaveObligatoryRowEmpty());
                    return false;
                }
                //
                if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                    HelpA.showNotification(MSG.cannotSaveCheckMarkedRowsMsg());
                    return false;
                }
                //
                return true;
            }

            @Override
            public RowDataInvert[] getConfigTableInvert() {
                //
                RowDataInvert id = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "id", "ID", "", true, true, false);
                //
                RowDataInvert user = new RowDataInvert(TABLE_NAME, TABLE_ID, false, USER_NAME_FIELD, "USER", "", true, true, true);
                //
                RowDataInvert pass = new RowDataInvert(TABLE_NAME, TABLE_ID, false, PASS_FIELD, "PASS", "", true, true, true);
                pass.setInputLenthValidation(4);
                //
//                String fixedComboValues_b = JSon._get_simple(HelpA.getValueSelectedRow(jTable1, "role"), "user,poweruser,admin,developer");
                //
                String fixedComboValues_b = JSon._get_simple(HelpA.getValueSelectedRow(jTable1, "role"), ROLE_COMMON_USER + "," + ROLE_ADVANCED_USER + "," + ROLE_ADMIN + "," + ROLE_DEVELOPER);
                //
                RowDataInvert role = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, null, "", TABLE_NAME, TABLE_ID, false, "role", "ROLE", "", true, true, false);
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
                    TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
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
