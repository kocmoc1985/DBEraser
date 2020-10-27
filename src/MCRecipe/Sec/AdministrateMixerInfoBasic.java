/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.Ingredients;
import MCRecipe.MC_RECIPE_;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
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
public class AdministrateMixerInfoBasic extends AdministrateRecipeGroups_ {

    public AdministrateMixerInfoBasic(String title,MC_RECIPE_ mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        super(title,mc_recipe, sql, sql_additional);
    }

    @Override
    public void setTableTitle() {
        jLabel1.setText("MIXER INFO BASIC");
    }
    
    @Override
    public String addEntryQuery(){
        return "insert into " + TABLE_NAME
                + " values('NEW','','','','','','','','','','" + HelpA.updatedOn() + "','" + HelpA.updatedBy()+ "')";
    }
    

    @Override
    public void initBasicTab() {
        //
        TABLE_NAME = "Mixer_InfoBasic";
        TABLE_ID = "Mixer_InfoBasic_ID";
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                //
                RowDataInvert id = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Mixer_InfoBasic_ID", "ID", "", true, true, false);
                //
                RowDataInvert code = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Code", "CODE", "", true, true, false);
                //
                RowDataInvert name = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Name", "NAME", "", true, true, false);
                //
                RowDataInvert volume = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Volume", "VOLUME", "", true, true, false);
                //
                RowDataInvert cost = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Costs_perKilo", "PRICE/KG", "", true, true, false);
                //
                RowDataInvert waste = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Waste", "WASTE", "", true, true, false);
                //
                RowDataInvert dust = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Dust", "DUST", "", true, true, false);
                //
                RowDataInvert starttime = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "StartTime", "START TIME", "", true, true, false);
                //
                RowDataInvert cycle_ovh = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "CycleOVH", "CYCLE OVH", "", true, true, false);
                //
                RowDataInvert decimals = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Decimals", "DECIMALS", "", true, true, false);
                //
                RowDataInvert noteMain = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Note_Main", "NOTE MAIN", "", true, true, false);
                //
                RowDataInvert updatedOn = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "UpdatedOn", "UPDATED ON", "", true, true, false);
                //
                RowDataInvert updatedBy = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "UpdatedBy", "UPDATED BY", "", true, true, false);
                //
                id.setUneditable();
                updatedOn.setUneditable();
                updatedBy.setUneditable();
                //
                RowDataInvert[] rows = {id, code, name, volume, cost, waste, dust, starttime, cycle_ovh, decimals, noteMain, updatedOn, updatedBy};
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                //
                TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "vendors_2");
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
                    TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
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
