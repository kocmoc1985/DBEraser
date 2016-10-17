/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableRowInvertListener;
import MyObjectTable.SaveIndicator;
import MyObjectTable.Table;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class Vendors extends BasicTab implements TableRowInvertListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    public Table TABLE_INVERT_2;
    private TableBuilderInvert TABLE_BUILDER_INVERT_2;
    public Table TABLE_INVERT_3;
    private TableBuilderInvert TABLE_BUILDER_INVERT_3;
    public Table TABLE_INVERT_3_2;
    private TableBuilderInvert TABLE_BUILDER_INVERT_3_2;
    public Table TABLE_INVERT_4;
    private TableBuilderInvert TABLE_BUILDER_INVERT_4;
    public Table TABLE_INVERT_4_2;
    private TableBuilderInvert TABLE_BUILDER_INVERT_4_2;
    private final MC_RECIPE mCRecipe;
    public int currentVendorId = -1;
    private String currentTradeNameId = "";
    private String currentTradeName = "";
    private String currentVendorContactId = "";
    private String currentVendorContactName = "";
    private String currentId = "";

    public Vendors(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mc_recipe) {
        super(sql, sql_additional, mc_recipe);
        this.mCRecipe = mc_recipe;
        //
        hideTableNames();
        //
        go();
        //
    }

    private void go() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fillComboBox(mCRecipe.jComboBoxVendorChooseIngred, "Name");
                mCRecipe.jComboBoxVendorChooseIngred.setSelectedIndex(0);
                mCRecipe.vendorsShowTablesOnItemStateChanged();
                //
                mCRecipe.addListenersVendor();
                initializeSaveIndicators();
                fillComboTradeName();
                fillComboVendors();
            }
        });
    }

    private void hideTableNames() {
        mCRecipe.jLabel3.setVisible(false);
        mCRecipe.jLabel80.setVisible(false);
        mCRecipe.jLabel81.setVisible(false);
        mCRecipe.jLabel82.setVisible(false);
        mCRecipe.jLabel83.setVisible(false);
        mCRecipe.jLabel84.setVisible(false);
    }

    public void table1Repport() {
        tableInvertExportOrRepport(TABLE_INVERT, 2, getConfigTableInvert());
    }

    public void table3Repport() {
        tableInvertExportOrRepport(TABLE_INVERT_3, 1, getConfigTableInvert3());
    }

    public void table4Repport() {
        tableInvertExportOrRepport(TABLE_INVERT_4, 1, getConfigTableInvert4());
    }

    public void table4_2Repport() {
        tableInvertExportOrRepport(TABLE_INVERT_4_2, 1, getConfigTableInvert4_2());
    }

    public void clear_tables_3_2__4__4_2() {
        clearFieldsBeforeAddingTable3_2();
        clearFieldsBeforeAddingTable4_2();
        clearAllRowsTableInvert(TABLE_INVERT_4);
    }

    public void setSelectedItem(Object item) {
        mCRecipe.jComboBoxVendorChooseIngred.setSelectedItem(item);
    }

    public void nextIngredient() {
        int curr = mCRecipe.jComboBoxVendorChooseIngred.getSelectedIndex();
        //
        try {
            mCRecipe.jComboBoxVendorChooseIngred.setSelectedIndex(curr + 1);
        } catch (Exception ex) {
        }
    }

    public void prevIngredient() {
        int curr = mCRecipe.jComboBoxVendorChooseIngred.getSelectedIndex();
        //
        try {
            mCRecipe.jComboBoxVendorChooseIngred.setSelectedIndex(curr - 1);
        } catch (Exception ex) {
        }

    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable1, this, 1);
        SaveIndicator saveIndicator2 = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable2, this, 2);
        SaveIndicator saveIndicator3 = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable3, this, 3);
        SaveIndicator saveIndicator4 = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable4, this, 4);
        SaveIndicator saveIndicator5 = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable3_2, this, 5);
        SaveIndicator saveIndicator6 = new SaveIndicator(mCRecipe.jButtonVendorsSaveTable4_2, this, 6);
    }

    private void fillComboTradeName() {
        String q = SQL_A.vendor_fill_combo_tradenames();
        //
        HelpA.fillComboBox(sql, mCRecipe.jComboBoxVenorsTradnames, q, null, false, false);
    }

    public void fillComboVendors() {
        String q = SQL_A.vendor_fill_combo_vendors();
        //
        HelpA.fillComboBox(sql, mCRecipe.jComboBoxVenorsVendors, q, null, false, false);
    }

    public void comboTradeNamesItemStateChanged() {
//        HelpA.ComboBoxObject boxObject = (HelpA.ComboBoxObject) mCRecipe.jComboBoxVenorsTradnames.getSelectedItem();
        HelpA.ComboBoxObject boxObject = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsTradnames);
        currentTradeName = boxObject.getParam_1();
        currentTradeNameId = boxObject.getParam_2();
        //
        showTableInvert3_2(currentTradeNameId);
    }

    public void comboVendorsItemStateChanged() {
//        HelpA.ComboBoxObject boxObject = (HelpA.ComboBoxObject) mCRecipe.jComboBoxVenorsVendors.getSelectedItem();
        HelpA.ComboBoxObject boxObject = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsVendors);
        currentVendorId = Integer.parseInt(boxObject.getParam_2());
        //
        showTableInvert4("" + currentVendorId);
        showTableInvert4_2("" + currentVendorId);
    }

    public void vendorsColumnClickedSimulation() {
        mouseClicked(1, "vendors_3", (TableInvert) TABLE_INVERT_3);// simulating click on TableInvert
    }

    @Override
    public void mouseClicked(int column, String tableName, TableInvert ti) {
        //
        if (tableEmpty(ti)) {
            return;
        }
        //
        if (tableName.equals("vendors_3")) {
            String vendor_id = getValueTableInvert("VENDOR_ID", column, TABLE_INVERT_3);
            currentTradeNameId = getValueTableInvert("Tradename_Main_ID", column, TABLE_INVERT_3);
            currentVendorId = Integer.parseInt(vendor_id);
            currentTradeName = getValueTableInvert("TradeName", column, TABLE_INVERT_3);
            currentId = getValueTableInvert("id", column, TABLE_INVERT_3);
            showTableInvert4(vendor_id);
            showTableInvert4_2(vendor_id);
            showTableInvert3_2(currentTradeNameId);
        } else if (tableName.equals("vendors_4_2")) {
            currentVendorContactId = getValueTableInvert("Vendor_Contact_ID", column, TABLE_INVERT_4_2);
            currentVendorContactName = getValueTableInvert("ContactName", column, TABLE_INVERT_4_2);
        }
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        RowDataInvert name = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Name", "NAME", "", true, true, false);
        RowDataInvert cross_reference = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Info_01", "CROSS REFERENCE", "", true, true, false);
        //
        String q_1 = SQL_B.basic_combobox_query("Descr", "Ingredient_Code");
        RowDataInvert description = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_1, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Descr", "DESCRIPTION", "", true, true, false);
        //
        RowDataInvert priceKg = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "ActualPreisePerKg", "PRICE", "Euro/kg", true, true, false);
        //
        RowDataInvert casno = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Cas_Number", "CASNO", "", true, true, false);
        //
        //
        RowDataInvert chem_number = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "ChemName", "CHEMICAL NAME", "", true, true, false);
        chem_number.enableToolTipTextJTextField();
        //
        //
        String q_2 = SQL_B.basic_combobox_query("Class", "Ingredient_Code");
        RowDataInvert clasS = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_2, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Class", "CLASS", "", true, true, false);
        //
        String q_3 = SQL_B.basic_combobox_query("Status", "Ingredient_Code");
        RowDataInvert status = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_3, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Status", "STATUS", "", true, true, false);
        //
        String q_4 = SQL_B.basic_combobox_query("[Group]", "Ingredient_Code");
        RowDataInvert group = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Group", "GROUP", "", true, true, false);
        //
        RowDataInvert group_name = new RowDataInvert("Ingred_Group", "Id", false, "Descr", "GROUP NAME", "", true, true, false);
        //
        //
        String q_5 = SQL_B.basic_combobox_query("Form", "Ingredient_Code");
        RowDataInvert appereance = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Form", "APPEARANCE", "", true, true, false);
        //
        //
        RowDataInvert perc_rubber = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "percRubber", "PERCENTAGE RUBBER", "%", true, true, false);
        RowDataInvert rubber_tolerances = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "PercRubtOl", "RUBBER TOLLERANCES", "%", true, true, false);
        RowDataInvert perc_act_mat = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "PercActMat", "ACTIVITY", "%", true, true, false);
        RowDataInvert density = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "density", "DENSITY", "mg/ml", true, true, false);
        RowDataInvert density_tol = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "DensityTOl", "DENSITY TOLLERANCE", "mg/ml", true, true, false);
        RowDataInvert visc_temp = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscTemp", "MOONEY TEMPEARTURE", "C", true, true, false);
        RowDataInvert visc_time = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscTime", "MOONEY TIME", "min", true, true, false);
        RowDataInvert visc_ml = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscML", "MOONEY VISCOSITY", "MU", true, true, false);
        RowDataInvert visc_ml_tol = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscMLTOl", "MOONEY TOLERANCES", "MU", true, true, false);
        //
        //
        RowDataInvert updated_on = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedOn", "UPDATED ON", "", true, true, false);
        RowDataInvert updated_by = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedBy", "UPDATED BY", "", true, true, false);
        //
        RowDataInvert created_on = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "CreatedOn", "CREATED ON", "", true, true, false);
        RowDataInvert created_by = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "CreatedBy", "CREATED BY", "", true, true, false);
        //
        String q_6 = SQL_B.basic_combobox_query("Info_02", "Ingredient_Code");
        RowDataInvert technical_datasheet = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_6, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Info_02", "TECHNICAL DATASHEET", "", true, true, false);
        //
        //
        RowDataInvert[] rows = {name, cross_reference, description, priceKg,
            casno, chem_number, clasS, status, group, group_name,
            appereance, perc_rubber, rubber_tolerances, perc_act_mat, density,
            density_tol, visc_temp, visc_time, visc_ml, visc_ml_tol, updated_on,
            updated_by, created_on, created_by, technical_datasheet};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert2() {
        //
        RowDataInvert location = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "Location", "LOCATION", "", true, true, false);
//        RowDataInvert shelflife = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "percRubber", "PERCENTAGE RUBBER", "%", true, true, false);
        RowDataInvert silo_id = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "SiloId", "SILO ID", "", true, true, false);
        RowDataInvert balance_id = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "BalanceID", "BALANCE ID", "", true, true, false);
//        RowDataInvert info = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "FreeInfo", "INFO", "", true, true, false);
//        RowDataInvert ingred_warehouse_id = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "Ingredient_Warehouse_ID", "WAREHOUSE ID", "", true, true, false);
//        RowDataInvert ingredient_code_id = new RowDataInvert("Ingredient_Warehouse", "Ingredient_Warehouse_ID", false, "IngredientCode_ID", "INGRED ID", "", true, true, false);
        //
        RowDataInvert[] rows = {location, silo_id, balance_id};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert3() {
        //
        RowDataInvert vendorName = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VendorName", "VENDOR", "", true, true, false);
//        RowDataInvert price = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "ActualPreisePerKg", "PRICE", "Euro/kg", true, true, true);
        RowDataInvert tradeName = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "TradeName", "TRADENAME", "", true, true, false);
//        RowDataInvert casno = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "Cas_Number", "CASNO ID", "", true, true, false);
        RowDataInvert supplierOrManufacturer = new RowDataInvert("_INTRF_IngredientCode_ID__Vendor_ID", "id", false, "VM", "SUPP. / MAN.", "", true, true, false);
        //
        vendorName.setUneditable();
        tradeName.setUneditable();
//        casno.setUneditable();
        //hidden
        RowDataInvert vendorId = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VENDOR_ID", "VENDOR ID", "", true, false, false);
        RowDataInvert tradeNameId = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "Tradename_Main_ID", "TID", "", true, false, false);
        RowDataInvert id = new RowDataInvert("_INTRF_IngredientCode_ID__Vendor_ID", "id", false, "id", "id", "", true, false, false);
        //
        RowDataInvert[] rows = {vendorName, tradeName, supplierOrManufacturer, vendorId, tradeNameId, id};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert3_2() {
        //
        RowDataInvert tradeName = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "TradeName", "TRADE NAME", "", true, true, false);
        RowDataInvert cas_nr = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "Cas_Number", "CASNO ID", "", true, true, false);
        RowDataInvert msds = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "MSDS", "MSDS", "", true, true, false);
        RowDataInvert updatedOn = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "UpdatedOn", "UPDATED ON", "", true, true, false);
        updatedOn.setUneditable();
        RowDataInvert updatedBy = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "UpdatedBy", "UPDATED BY", "", true, true, false);
        updatedBy.setUneditable();
        //hidden
        RowDataInvert tradeNameId = new RowDataInvert("TRADENAME_MAIN", "Tradename_Main_ID", false, "Tradename_Main_ID", "TID", "", true, false, false);
        //
        RowDataInvert[] rows = {tradeName, cas_nr, msds, updatedOn, updatedBy, tradeNameId};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert4() {
        //
        RowDataInvert vendorName = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VendorName", "VENDOR", "", true, true, false);
        RowDataInvert vendorId = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VENDOR_ID", "VENDOR ID", "", true, true, false);
        vendorId.setUneditable();
        RowDataInvert vendorNo = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VendorNo", "VENDOR NO", "", true, true, false);
        RowDataInvert address = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Adress", "ADRESS", "", true, true, false);
        RowDataInvert zipCode = new RowDataInvert("VENDOR", "VENDOR_ID", false, "ZipCode", "POSTAL CODE", "", true, true, false);
        RowDataInvert city = new RowDataInvert("VENDOR", "VENDOR_ID", false, "City", "CITY", "", true, true, false);
        RowDataInvert country = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Country", "LAND", "", true, true, false);
        RowDataInvert phone = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Phone", "TELEPHONE", "", true, true, false);
        RowDataInvert fax = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Fax", "FAX", "", true, true, false);
        RowDataInvert email = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Email", "EMAIL", "", true, true, false);
        RowDataInvert website = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Website", "WEBSITE", "", true, true, false);
        RowDataInvert freeinfo = new RowDataInvert("VENDOR", "VENDOR_ID", false, "FreeInfo", "FREEINFO", "", true, true, false);
        RowDataInvert status = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Status", "STATUS", "", true, true, false);
        RowDataInvert updatedOn = new RowDataInvert("VENDOR", "VENDOR_ID", false, "UpdatedOn", "UPDATED ON", "", true, true, false);
        updatedOn.setUneditable();
        RowDataInvert updatedBy = new RowDataInvert("VENDOR", "VENDOR_ID", false, "UpdatedBy", "UPDATED BY", "", true, true, false);
        updatedBy.setUneditable();
        //
        RowDataInvert[] rows = {vendorName, vendorId, vendorNo, address, zipCode, city, country, phone, fax, email, website, freeinfo, status, updatedOn, updatedBy};
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert4_2() {
        //
        RowDataInvert vendor_id = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "VENDOR_ID", "VENDOR ID", "", true, true, false);
        vendor_id.setUneditable();
        RowDataInvert contactName = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "ContactName", "CONTACT NAME", "", true, true, false);
        RowDataInvert position = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "position", "POSITION", "", true, true, false);
        RowDataInvert phone = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "Phone", "PHONE", "", true, true, false);
        RowDataInvert email = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "Email", "EMAIL", "", true, true, false);
        RowDataInvert updatedOn = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "UpdatedOn", "UPDATED ON", "", true, true, false);
        updatedOn.setUneditable();
        RowDataInvert updatedBy = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "UpdatedBy", "UPDATED BY", "", true, true, false);
        updatedBy.setUneditable();
        //hidden
        RowDataInvert vendor_contact_id = new RowDataInvert("Vendor_Contact", "Vendor_Contact_ID", false, "Vendor_Contact_ID", "VCID", "", true, false, false);
        //
        RowDataInvert[] rows = {vendor_id, contactName, position, phone, email, updatedOn, updatedBy, vendor_contact_id};
        //
        return rows;
    }

    public void showTableInvert4_2(String vendor_id) {
        //
        //
        if (vendor_id == null) {
            vendor_id = getValueTableInvert("VENDOR_ID", 1, TABLE_INVERT_3);
        }
        //
        if (vendor_id.isEmpty()) {
            return;
        }
        //
        TABLE_BUILDER_INVERT_4_2 = new TableBuilderInvert(OUT, sql, getConfigTableInvert4_2(), false, "vendors_4_2");
        //
        TABLE_INVERT_4_2 = null;
        //
        try {
            String q = SQL_A.vendor_build_table_invert_4_2(vendor_id);
            OUT.showMessage(q);
            TABLE_INVERT_4_2 = TABLE_BUILDER_INVERT_4_2.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_4_2.showMessage(ex.toString());
        }
        //
        addTableInvertRowListener(TABLE_INVERT_4_2, this);
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_4_2);
        //
        showTableInvert(mCRecipe.jPanelInvertTable4_2, TABLE_INVERT_4_2);
        //
        setValueTableInvert("VENDOR_ID", TABLE_INVERT_4_2, vendor_id);
    }

    public void showTableInvert4(String vendorId) {
        //
        if (vendorId == null) {
            vendorId = getValueTableInvert("VENDOR_ID", 1, TABLE_INVERT_3);
        }
        //
        //
        TABLE_BUILDER_INVERT_4 = new TableBuilderInvert(OUT, sql, getConfigTableInvert4(), false, "vendors_4");
        //
        TABLE_INVERT_4 = null;
        //
        try {
            String q = SQL_A.vendor_build_table_invert_4(vendorId);
            OUT.showMessage(q);
            TABLE_INVERT_4 = TABLE_BUILDER_INVERT_4.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_4.showMessage(ex.toString());
        }
        //
        //
        showTableInvert(mCRecipe.jPanelInvertTable4, TABLE_INVERT_4);
        //
    }

    public void deleteTradeNameFromTable3B() {
        //
        if (currentId.isEmpty()) {
            HelpA.showNotification("Nothing chosen, click on a row in the table below");
            return;
        }
        //
        if (HelpA.confirm("Dou you really want to delete tradename: " + currentTradeName) == false) {
            return;
        }
        //
        String q = SQL_A.vendor_delete_from_table_3B("" + currentId);
        //
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert3();
    }

    public void deleteFromTable3_2() {
        //
        if (currentTradeName.isEmpty()) {
            HelpA.showNotification("Nothing chosen plese click on any of corresponding rows");
            return;
        }
        //
        if (HelpA.confirm("Delete tradename: " + currentTradeName + "?") == false) {
            return;
        }
        //
        String q = SQL_A.vendor_delete_from_table_3_2(currentTradeNameId);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            HelpA.showNotification("Operation failed, because of sql error");
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert3_2("0");
        //
        //remove item from combobox
//        fillComboTradeName();
        //
    }

    public void clearFieldsBeforeAddingTable3_2() {
        clearAllRowsTableInvert(TABLE_INVERT_3_2);
    }

    public void addToTable3_2() {
        //
        String trade_name = getValueTableInvert("TradeName", 1, TABLE_INVERT_3_2);
        String cas_nr = getValueTableInvert("Cas_Number", 1, TABLE_INVERT_3_2);
        String msds = getValueTableInvert("MSDS", 1, TABLE_INVERT_3_2);
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        if (cas_nr == null || trade_name == null || msds == null) {
            HelpA.showNotification("Error, please try aggain");
            return;
        }
        //
        String q = SQL_A.vendor_insert_new_table_3_2(cas_nr, trade_name, msds, HelpA.updatedOn(), HelpA.updatedBy());
        //
        try {
            //
            sql.execute(q, mCRecipe);
            //
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            HelpA.showNotification("Operation failed, because of sql error");
        }
        //
        currentTradeNameId = HelpA.getLastIncrementedId(sql, "tradename_main");
        currentTradeName = trade_name;
        //
        showTableInvert3_2(currentTradeNameId);
        //
        fillComboTradeName();
    }

    public void addToTable3C() {
        //
        fillComboTradeName();
        fillComboVendors();
        //
        if (HelpA.chooseFrom2ComboBoxDialogs(mCRecipe.jComboBoxVenorsTradnames, mCRecipe.jComboBoxVenorsVendors, "Choose trade name & vendor") == false) {
            return;
        }
        //
        HelpA.ComboBoxObject boxObject = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsTradnames);
        //
        String ingred_name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        //
        String ingred_code_id = getIngredCodeId(ingred_name);
        String tradname_id = boxObject.getParam_2();
        //
        HelpA.ComboBoxObject boxObject2 = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsVendors);
        String vendor_id = boxObject2.getParam_2();
        //
        //
        String q = SQL_A.vendor_insert_table_3B(ingred_code_id, tradname_id, HelpA.updatedBy(), HelpA.updatedOn(), vendor_id);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);

        }
        //
        showTableInvert3();
    }

    public void clearFieldsBeforeAddingTable4() {
        showTableInvert4("" + 0);
        //
    }

    public boolean addToTable4() {
        //
        if (HelpA.confirm() == false) {
            return false;
        }
        //
        String vendor_no = getValueTableInvert("VendorNo", 1, TABLE_INVERT_4);
        String vendor_name = getValueTableInvert("VendorName", 1, TABLE_INVERT_4);
        String adress = getValueTableInvert("Adress", 1, TABLE_INVERT_4);
        String zip = getValueTableInvert("ZipCode", 1, TABLE_INVERT_4);
        String city = getValueTableInvert("City", 1, TABLE_INVERT_4);
        String country = getValueTableInvert("Country", 1, TABLE_INVERT_4);
        String phone = getValueTableInvert("Phone", 1, TABLE_INVERT_4);
        String fax = getValueTableInvert("Fax", 1, TABLE_INVERT_4);
        String email = getValueTableInvert("Email", 1, TABLE_INVERT_4);
        String website = getValueTableInvert("Website", 1, TABLE_INVERT_4);
        String free_info = getValueTableInvert("FreeInfo", 1, TABLE_INVERT_4);
        String status = getValueTableInvert("Status", 1, TABLE_INVERT_4);
        String updatedOn = HelpA.updatedOn();
        String updatedBy = HelpA.updatedBy();
        //
        String q = SQL_A.vendor_insert_new_table_4(vendor_no, vendor_name, adress, zip, city,
                country, phone, fax, email, website, free_info, status, updatedOn, updatedBy);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        currentVendorId = Integer.parseInt(HelpA.getLastIncrementedId(sql, "VENDOR"));
        //
        showTableInvert4("" + currentVendorId);
        showTableInvert4_2("" + currentVendorId);
        //
        fillComboVendors();
        //
        return true;
    }

    public void deleteFromTable4() {
        //
        if (HelpA.confirm("Delete Vendor: " + currentVendorId + " ?") == false) {
            return;
        }
        //
        String q = "delete from VENDOR where VENDOR_ID = " + currentVendorId;
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fillComboVendors();
        //
        showTableInvert4("" + currentVendorId);
        //
        //
    }

    public void clearFieldsBeforeAddingTable4_2() {
//        clearAllRowsTableInvert(TABLE_INVERT_4_2);
        showTableInvert4_2("" + 0);
        //
        setValueTableInvert("VENDOR_ID", TABLE_INVERT_4_2, currentVendorId);
    }

    public void addToTable4_2() {
        //
        String vendor_id = getValueTableInvert("VENDOR_ID", 1, TABLE_INVERT_4_2);
        String contactName = getValueTableInvert("ContactName", 1, TABLE_INVERT_4_2);
        String position = getValueTableInvert("position", 1, TABLE_INVERT_4_2);
        String phone = getValueTableInvert("Phone", 1, TABLE_INVERT_4_2);
        String email = getValueTableInvert("Email", 1, TABLE_INVERT_4_2);
        String date_export = "";
        String date_processed = "";
        String status = "";
        String updatedOn = HelpA.updatedOn();
        String updatedBy = HelpA.updatedBy();
        //
        if (contactName.isEmpty() && contactName.isEmpty() && position.isEmpty() && phone.isEmpty() && email.isEmpty()) {
            HelpA.showNotification("Please fill in the fields before adding");
            return;
        }
        //
        if (checkIfContactIsPresent(vendor_id, contactName, position, phone, email)) {
            //
            clearFieldsBeforeAddingTable4_2(); // !!!
            //
            mCRecipe.jButtonVendorsSaveTable4_2.setEnabled(false);
            //
            HelpA.setButtonIconCompleteAdd(mCRecipe.jButtonVendorsAddToTable4_2);
            //
            return;
        }
        //
        String q = SQL_A.vendor_insert_new_table_4_2(vendor_id, contactName, position, phone, email, date_export,
                date_processed, status, updatedOn, updatedBy);
        //
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert4_2(vendor_id);
        //
        mCRecipe.jButtonVendorsSaveTable4_2.setEnabled(true);
        HelpA.setButtonIconPreAdd(mCRecipe.jButtonVendorsAddToTable4_2);
    }

    public void reset() {
        mCRecipe.jButtonVendorsSaveTable4_2.setEnabled(true);
        HelpA.setButtonIconPreAdd(mCRecipe.jButtonVendorsAddToTable4_2);
    }

    private boolean checkIfContactIsPresent(String vendorId, String cName, String pos, String phone, String email) {
        //
        String q = SQL_A.vendor_check_if_contact_is_present(vendorId, cName, pos, phone, email);
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            if (rs.next()) {
                int x = rs.getInt("ammount");
                if (x == 1) {
                    return true;
                }
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        return false;
    }

    /**
     *
     */
    public void deleteFromTable4_2() {
        //
        if (currentVendorContactId.isEmpty()) {
            HelpA.showNotification("Contact not chosen, click on any field corresponding to the contact");
            return;
        }
        //
        if (HelpA.confirm("Delete contact: " + currentVendorContactName) == false) {
            return;
        }
        //
        String q = SQL_A.vendor_delete_from_table_4_2(currentVendorContactId);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            HelpA.showNotification("Operation failed, because of sql error");
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert4_2("" + currentVendorId);
        //
        currentVendorContactId = "";
        currentVendorContactName = "";
    }

    private String getIngredCodeId(String ingredName) {
        String q = SQL_A.get_ingredient_code_id_by_name(ingredName);
        try {
            ResultSet rs = sql.execute(q, mCRecipe);

            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void showTableInvert3_2(String tradename_main_id) {
        //
        TABLE_BUILDER_INVERT_3_2 = new TableBuilderInvert(OUT, sql, getConfigTableInvert3_2(), false, "vendors_3_2");
        //
        TABLE_INVERT_3_2 = null;
        //
        try {
            String q = SQL_A.vendor_build_table_invert_3_2(tradename_main_id);
            OUT.showMessage(q);
            TABLE_INVERT_3_2 = TABLE_BUILDER_INVERT_3_2.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_3_2.showMessage(ex.toString());
        }
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_3_2);
        //
//        showTableInvert(mCRecipe.jPanelInvertTable3_2, TABLE_INVERT_3_2);
    }

    public void showTableInvert3() {
        //
        TABLE_BUILDER_INVERT_3 = new TableBuilderInvert(OUT, sql, getConfigTableInvert3(), false, "vendors_3");
        //
        TABLE_INVERT_3 = null;
        //
        String name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        //
        try {
            String q = SQL_A.vendor_build_table_invert_3(name);
            OUT.showMessage(q);
            TABLE_INVERT_3 = TABLE_BUILDER_INVERT_3.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_3.showMessage(ex.toString());
        }
        //
        addTableInvertRowListener(TABLE_INVERT_3, this);
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_3);
        //
        showTableInvert(mCRecipe.jPanelInvertTable3, TABLE_INVERT_3);
    }

    public void showTableInvert2() {
        //
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert(OUT, sql, getConfigTableInvert2(), false, "vendors_2");
        //
        TABLE_INVERT_2 = null;
        //
        String name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        //
        try {
            String q = SQL_A.vendor_build_table_invert_warehouse(name);
            OUT.showMessage(q);
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanelVendorsInvertTableWarehouse, TABLE_INVERT_2);
    }

    @Override
    public void showTableInvert() {
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), true, "vendors_1");
        //
        TABLE_INVERT = null;
        //
        String name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        //
        try {
            String q = SQL_A.prc_ITF_Igredients_main_Select(name);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanelVendorsInvertTableIngreds);
    }

    private String[] getComboParams() {
        String ingredName = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Name);
        return new String[]{ingredName, null, null, null, null, null, null, null, null, null};
    }

    public void fillComboBox(JComboBox box, String colName) {
        Object selection = box.getSelectedItem();
        //
        //
        String q = SQL_A.fill_comboboxes_ingred(colName, getComboParams());
        OUT.showMessage(q);
        HelpA.fillComboBox(sql, box, q, null, false, false);
        //
        box.setSelectedItem(selection);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (TABLE_INVERT.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        } else if (nr == 2) {
            //
            if (TABLE_INVERT_2 == null) {
                return false;
            } else if (TABLE_INVERT_2.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        } else if (nr == 3) {
            //
            if (TABLE_INVERT_3 == null) {
                return false;
            } else if (TABLE_INVERT_3.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        } else if (nr == 4) {
            //
            if (TABLE_INVERT_4 == null) {
                return false;
            } else if (TABLE_INVERT_4.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        } else if (nr == 5) {
            //
            if (TABLE_INVERT_3_2 == null) {
                return false;
            } else if (TABLE_INVERT_3_2.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        } else if (nr == 6) {
            //
            if (TABLE_INVERT_4_2 == null) {
                return false;
            } else if (TABLE_INVERT_4_2.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        }
        return false;
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @deprecated
     */
    public void deleteTradeNameFromTable3() {
        //
        if (currentTradeNameId.isEmpty()) {
            HelpA.showNotification("Nothing chosen, click on a row in the table below");
            return;
        }
        //
        if (HelpA.confirm("Dou you really want to delete tradename: " + currentTradeName) == false) {
            return;
        }
        //
        String ingred_name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        String ingred_code_id = getIngredCodeId(ingred_name);
        //
        String q = SQL_A.vendor_delete_from_table_3("" + currentTradeNameId, "" + ingred_code_id);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert3();
    }

    /**
     * @deprecated
     */
    public void deleteVendorFromTable3() {
        //
        if (currentVendorId == -1) {
            return;
        }
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        String q = SQL_A.vendor_delete_from_table_4("" + currentVendorId);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert3();
        showTableInvert4("" + currentVendorId);
        //
        fillComboVendors();
    }

    /**
     * @deprecated
     */
    public void addToTable3() {
        //
        if (HelpA.chooseFromComboBoxDialog(mCRecipe.jComboBoxVenorsTradnames, "Choose trade name") == false) {
            return;
        }
        //
//        HelpA.ComboBoxObject boxObject = (HelpA.ComboBoxObject) mCRecipe.jComboBoxVenorsTradnames.getSelectedItem();
        HelpA.ComboBoxObject boxObject = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsTradnames);
        //
        String ingred_name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
        String tradname_id = boxObject.getParam_2();
        String ingred_code_id = getIngredCodeId(ingred_name);
        //
        if (ingred_code_id == null) {
            HelpA.showNotification("Cannot proceed,Please choose ingredient");
            return;
        }
        //
        String q = SQL_A.vendor_insert_table_3(ingred_code_id, tradname_id, HelpA.updatedBy(), HelpA.updatedOn());
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);

        }
        //
        showTableInvert3();
    }

    /**
     * @deprecated
     */
    public void addToTable3_B() {
        //
        fillComboVendors();
        //
        JTextField textField = new JTextField();
        //
        if (HelpA.chooseFromComboBoxDialogBoxAndTextfield(mCRecipe.jComboBoxVenorsVendors, textField, "Choose or type vendor") == false) {
            return;
        }
        //
        String ingred_name;
        String vendor_id;
        String ingred_code_id;
        //
        //
        if (textField.getText().isEmpty()) { // This means chosen from checkBox
//            HelpA.ComboBoxObject boxObject = (HelpA.ComboBoxObject) mCRecipe.jComboBoxVenorsVendors.getSelectedItem();
            HelpA.ComboBoxObject boxObject = HelpA.getSelectedComboBoxObject(mCRecipe.jComboBoxVenorsVendors);
            ingred_name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
            vendor_id = boxObject.getParam_2();
            ingred_code_id = getIngredCodeId(ingred_name);
        } else {
            ingred_name = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxVendorChooseIngred);
            String vendorName = textField.getText();
            vendor_id = insertNewVendor(vendorName);
            //
            if (vendor_id == null) {
                return;
            }
            //
            ingred_code_id = getIngredCodeId(ingred_name);
        }
        //
        if (ingred_code_id == null) {
            HelpA.showNotification("Cannot proceed,Please choose ingredient");
            return;
        }
        //
        String q = SQL_A.vendor_insert_table_4(ingred_code_id, vendor_id, HelpA.updatedBy(), HelpA.updatedOn());
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTableInvert3();
        showTableInvert4("" + vendor_id);
    }

    private String insertNewVendor(String newVendorName) {
        String q = SQL_A.vendors_add_new_vendor(newVendorName);
        OUT.showMessage(q);
        String idOfInsertedVendor = null;
        try {
            sql.execute(q);
            return HelpA.getLastIncrementedId(sql, "VENDOR");
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return idOfInsertedVendor;
    }
}
