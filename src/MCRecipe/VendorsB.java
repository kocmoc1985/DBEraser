/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.MSG;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;

/**
 *
 * @author KOCMOC
 */
public class VendorsB extends BasicTab {

    private Vendors vendors;
    private final MC_RECIPE mCRecipe;

    public VendorsB(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mc_recipe, Vendors vendors) {
        super(sql, sql_additional, mc_recipe);
        this.vendors = vendors;
        this.mCRecipe = mc_recipe;
    }

    

    private void refreshTable4_B() {
        showTableInvert(mCRecipe.jPanelInvertTable4_B, vendors.TABLE_INVERT_4);
    }

    private void refreshTable3_2_B() {
        showTableInvert(mCRecipe.jPanelInvertTable3_2_B, vendors.TABLE_INVERT_3_2);
    }

    public void clearFieldsBeforeAddingTable3_2() {
        vendors.clearFieldsBeforeAddingTable3_2();
//        refreshTable3_2_B();
    }

    public void addToTable3_2() {
        //
        String tradeName = getValueTableInvert("TradeName", 1, vendors.TABLE_INVERT_3_2);
        //
        //
        if (tradeName.isEmpty()) {
            HelpA.showNotification(MSG.LANG("TADE NAME must be filled in"));
            return;
        }
        //
        vendors.addToTable3_2();
        mCRecipe.jButtonVendorsSaveTable3_2.setEnabled(true);
        refreshTable3_2_B();
    }

    public void deleteFromTable3_2() {
        vendors.deleteFromTable3_2();
        refreshTable3_2_B();
    }

    public void addToTable4() {
        //
        String vendor = getValueTableInvert("VendorName", 1, vendors.TABLE_INVERT_4);
        //
        if (vendor.isEmpty()) {
            HelpA.showNotification(MSG.LANG("Vendor must be filled in"));
            return;
        }
        //
        if (vendors.addToTable4()) {
            refreshTable4_B();
            mCRecipe.jButtonVendorsSaveTable4.setEnabled(true);
        }
    }

    public void clearFieldsBeforeAddingTable4() {
        vendors.clearFieldsBeforeAddingTable4();
        refreshTable4_B();
    }

    public void deleteFromTable4() {
        vendors.deleteFromTable4();
        refreshTable4_B();
    }

    @Override
    public void initializeSaveIndicators() {
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
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
