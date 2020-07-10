package MyObjectTableInvert;

import MCRecipe.SQL_B;
import forall.HelpA;
import forall.SqlBasicLocal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KOCMOC
 */
public class Invert_Tables_Config {
    
     

    public static RowDataInvert[] getConfigTest() {
        //
        RowDataInvert vendor_name = new RowDataInvert("VENDOR", "VENDOR_ID", false, "VendorName", "ContactName", "km/h", true, true,false);
        RowDataInvert adress = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Adress", "Adress", "kg", true, true,false);
        RowDataInvert zipcode = new RowDataInvert("VENDOR", "VENDOR_ID", false, "ZipCode", "Zip", "cm", true, true,false);
        RowDataInvert city = new RowDataInvert("VENDOR", "VENDOR_ID", false, "City", "City", null, true, true,false);
        RowDataInvert country = new RowDataInvert("VENDOR", "VENDOR_ID", false, "Country", "Country", null, true, true,false);
        //
        RowDataInvert person_phone = new RowDataInvert("Vendor_Contact", "VENDOR_ID", false, "Phone", "PersonPhone", null, true, true,false);
        //
        RowDataInvert[] rows = {vendor_name, adress, zipcode, city, country, person_phone};
        //
        return rows;
    }

    private static RowDataInvert[] getConfigOneOrderTable_ex_1() {
        //
        RowDataInvert plandate = new RowDataInvert("Production_Plan", "PLANID", true, "PLANDATE", "PLANDATE", "", true, true,false);
        RowDataInvert plan_id = new RowDataInvert("Production_Plan", "PLANID", true, "PLANID", "PLAN", "", true, true,false);
        RowDataInvert order_no = new RowDataInvert("Production_Plan", "PLANID", true, "ORDERNO", "ORDER", "", true, true,true);
        RowDataInvert rcode = new RowDataInvert("Production_Plan", "PLANID", true, "RCODE", "RCODE", "", true, true,true);
        RowDataInvert mtype = new RowDataInvert("Production_Plan", "PLANID", true, "MTYPE", "MTYPE", "", true, true,false);
        RowDataInvert status = new RowDataInvert("Production_Plan", "PLANID", true, "STATUS", "STATUS", "", false, true,false);
        RowDataInvert batch_qty = new RowDataInvert("Production_Plan", "PLANID", true, "BATCHQTY", "BATCHQTY", "", false, true,true);
        RowDataInvert prodqty = new RowDataInvert("Production_Plan", "PLANID", true, "PRODQTY", "PRODQTY", "", false, true,false);
        RowDataInvert first_batch = new RowDataInvert("Production_Plan", "PLANID", true, "FIRSTBATCH", "FIRSTBATCH", "", true, true,false);
        RowDataInvert priority = new RowDataInvert("Production_Plan", "PLANID", true, "PRIORITY", "PRIORITY", "", false, true,false);
        RowDataInvert cancelqty = new RowDataInvert("Production_Plan", "PLANID", true, "CANCELQTY", "CANCELQTY", "", false, true,false);
        RowDataInvert bookedqty = new RowDataInvert("Production_Plan", "PLANID", true, "BOOKEDQTY", "BOOKEDQTY", "", false, true,false);
        RowDataInvert proddate = new RowDataInvert("Production_Plan", "PLANID", true, "PRODDATE", "PRODDATE", "", true, true,false);
        RowDataInvert origin = new RowDataInvert("Production_Plan", "PLANID", true, "ORIGIN", "ORIGIN", "", true, true,false);
        RowDataInvert modified = new RowDataInvert("Production_Plan", "PLANID", true, "MODIFIED", "MODIFIED", "", false, true,false);
        RowDataInvert remark = new RowDataInvert("Production_Plan", "PLANID", true, "REMARK", "REMARK", "", true, true,false);
        RowDataInvert lastupdate = new RowDataInvert("Production_Plan", "PLANID", true, "LASTUPDATE", "LASTUPDATE", "", true, true,false);
        RowDataInvert updatedby = new RowDataInvert("Production_Plan", "PLANID", true, "UPDATEDBY", "UPDATEDBY", "", true, true,false);
        //
        RowDataInvert[] rows = {plandate, plan_id, order_no, rcode, mtype,
            status, batch_qty, prodqty, first_batch, priority, cancelqty, bookedqty, proddate, origin, modified, remark, lastupdate, updatedby};
        //
        return rows;
    }
    
    private RowDataInvert[] getConfigTableInvert_ex2() {
        SqlBasicLocal sql_additional = null;
        //
        RowDataInvert name = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Name", "Name", "", true, true, false);
        RowDataInvert cross_reference = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Info_01", "Cross Reference", "", true, true, false);
        //
        String q_1 = SQL_B.basic_combobox_query("Descr", "Ingredient_Code");
        RowDataInvert description = new RowDataInvert(1, q_1, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Descr", "Description", "", true, true, false);
        //
        RowDataInvert casno = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Cas_Number", "CASNO", "", true, true, false);
        //
        //
        RowDataInvert chem_number = new RowDataInvert("CASNO_ID", "CASNO_ID", false, "Chem_Name", "CHEMICAL NUMBER", "", true, true, false);
        //
        //
        String q_2 = SQL_B.basic_combobox_query("Class", "Ingredient_Code");
        RowDataInvert clasS = new RowDataInvert(1, q_2, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Class", "CLASS", "", true, true, false);
        //
        String q_3 = SQL_B.basic_combobox_query("Status", "Ingredient_Code");
        RowDataInvert status = new RowDataInvert(1, q_3, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Status", "STATUS", "", true, true, false);
        //
        String q_4 = SQL_B.basic_combobox_query("[Group]", "Ingredient_Code");
        RowDataInvert group = new RowDataInvert(1, q_4, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Group", "GROUP", "", true, true, false);
        //
        RowDataInvert group_name = new RowDataInvert("Ingred_Group", "Id", false, "Descr", "GROUP NAME", "", true, true, false);
        //
        //
        String q_5 = SQL_B.basic_combobox_query("Form", "Ingredient_Code");
        RowDataInvert appereance = new RowDataInvert(1, q_5, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Form", "APPEARANCE", "", true, true, false);
        //
        //
        RowDataInvert perc_rubber = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "percRubber", "PERCENTAGE RUBBER", "", true, true, false);
        RowDataInvert rubber_tolerances = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "PercRubtOl", "RUBBER TOLLERANCES", "", true, true, false);
        RowDataInvert perc_act_mat = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "PercActMat", "ACTIVITY", "", true, true, false);
        RowDataInvert density = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "density", "DENSITY", "", true, true, false);
        RowDataInvert density_tol = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "DensityTOl", "DENSITY TOLLERANCE", "", true, true, false);
        RowDataInvert visc_temp = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "ViscTemp", "MOONEY TEMPEARTURE", "", true, true, false);
        RowDataInvert visc_time = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "ViscTime", "MOONEY TIME", "", true, true, false);
        RowDataInvert visc_ml = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "ViscML", "MOONEY VISCOSITY", "", true, true, false);
        RowDataInvert visc_ml_tol = new RowDataInvert("Ingredient_phys_Properties_1", "Ingredient_phys_Properties_ID", false, "ViscMLTOl", "MOONEY TOLERANCES", "", true, true, false);
        //
        //
        RowDataInvert updated_on = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedOn", "UpdatedOn", "", true, true, false);
        RowDataInvert updated_by = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedBy", "UpdatedBy", "", true, true, false);
        //
        String q_6 = SQL_B.basic_combobox_query("Info_02", "Ingredient_Code");
        RowDataInvert technical_datasheet = new RowDataInvert(1, q_6, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Info_02", "TECHNICAL DATASHEET", "", true, true, false);
        //
        //
        RowDataInvert[] rows = {name, cross_reference, description,
            casno, chem_number, clasS, status, group, group_name,
            appereance, perc_rubber, rubber_tolerances, perc_act_mat, density,
            density_tol, visc_temp, visc_time, visc_ml, visc_ml_tol, updated_on,
            updated_by, technical_datasheet};
        //
        return rows;
    }
}
