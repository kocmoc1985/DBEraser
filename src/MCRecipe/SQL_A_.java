/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import LabDev.LabDevelopment_;
import static LabDev.LabDevelopment_.TABLE__TEST_PROCEDURE;
import forall.HelpA;

/**
 * vendor_insert_new_table_3_2
 *
 * @author KOCMOC
 */
public class SQL_A_ {

    /**
     *
     * @param param1 - user
     * @return
     */
    public static String compareRecipeDropTable(String param1) {
//        return "drop table " + param1 + "_C";
        return "IF EXISTS (SELECT * FROM sys.tables"
                + " WHERE name = N'" + param1 + "_C'" + " AND type = 'U')"
                + " begin"
                + " drop table " + param1 + "_C"
                + " end";
    }

    /**
     *
     * @param param1 - user
     * @param orderByCriteria - leave empty if to have it by default
     * @return
     */
    public static String compareRecipesShow(String param1, String orderByCriteria) {
        return "SELECT RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc,"
                + " volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID"
                + " FROM " + param1 + "_C"
                + " " + orderByCriteria;
    }

    /**
     *
     * @param PROCEDURE
     * @param param1 - recipeCode
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String compareRecipesAddToCompare(String PROCEDURE, String param1, String param2, String param3) {
        return PROCEDURE + ""
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param PROC
     * @param param1 - recipeCode
     * @param param2 - order
     * @return
     */
    public static String recipe_additional_build_table_2(String PROC, String param1, String param2) {
        return "SELECT * FROM " + PROC + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false)
                + ")"
                + " order by BatchNo, TestCode, TestNo desc";
    }

    /**
     *
     * @param PROC
     * @param param1 - recipeCode
     * @return
     */
    public static String recipe_additional_fill_combo_orders(String PROC, String param1) {
        return "SELECT * FROM " + PROC + " ("
                + quotes(param1, false) + ")"
                + " order by [order] desc";
    }

    /**
     *
     * @param PROC
     * @param param1 = recipeCode
     * @return
     */
    public static String recipe_additional_build_table_1(String PROC, String param1) {
        return "SELECT Quality,TestCode,Description,LSL,USL,Name,Target,Device"
                + " FROM " + PROC + " ("
                + quotes(param1, false) + ")"
                + " order by TestCode";
    }

    /**
     *
     * @param param1 - new vendor name
     * @return
     */
    public static String vendors_add_new_vendor(String PROC, String param1) {
        return PROC + " " + quotes(param1, false) + "";
    }

    /**
     *
     * @param PROC
     * @param param1 - IngredName
     * @return
     */
    public static String recipe_detailed_find_density_ingred(String PROC, String param1) {
        return "select density from " + PROC + " (" + quotes(param1, false) + ")";
    }

    public static String recipe_detailed_insert_note_table_2(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false);
    }

    public static String recipe_detailed_insert_note_table_3(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false);
    }

    /**
     *
     * @param PROC
     * @param param1 - Recipe_Id, bigint
     * @param param2 - NoteName varchar
     * @param param3 - NoteValue varchar
     * @param param4 - UpdateOn
     * @param param5 - UpdateBy
     * @return
     */
    public static String recipe_detailed_update_table_2(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false);
    }

    /**
     *
     * @param param1 - IngredientCode
     * @return
     */
    public static String ingredient_update_ingred_name(String param1) {
        return "UPDATE Ingredient_Code SET Name ="
                + quotes(param1, false) + " WHERE (Name = 'NEW')";
    }

    /**
     *
     * @param PROC
     * @param param1 - ingredientCOde_id
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String ingredients_add_new_ingredient_scratch(String PROC, String param1, String param2, String param3) {
        return PROC + " "
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param PROC
     * @param param1 - ingredientCOde_id
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String ingredients_add_new_ingredient(String PROC, String param1, String param2, String param3) {
        return PROC + " "
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param param1 - user
     * @return
     */
    public static String recipeDetailedDeleteIfEmpty(String param1) {
        return "IF EXISTS (SELECT * FROM sys.tables"
                + " WHERE name = N'" + param1 + "1'" + " AND type = 'U')"
                + " begin"
                + " delete from " + param1 + "1"
                + " end";
    }

    /**
     *
     * @param param1 - recipeCode
     * @return
     */
    public static String recipe_detailed_update_recipe_name(String param1) {
        return "UPDATE Recipe_Prop_Main SET Code ="
                + quotes(param1, false) + " WHERE (Code = 'NEW')";
    }

    public static String recipe_detailed_add_new_recipe_scratch(String PROC, String param1, String param2, String param3) {
        return PROC + " "
                + quotes(param1, true) + "," //prc_ITF_RECIPE_Scratch
                + quotes(param2, false) + "," //prc_ITF_RECIPE_main_Insert
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param PROC
     * @param param1 - RecipeID
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String recipe_detailed_add_new_recipe(String PROC, String param1, String param2, String param3) {
        return PROC + " "
                + quotes(param1, true) + "," //prc_ITF_RECIPE_Scratch
                + quotes(param2, false) + "," //prc_ITF_RECIPE_main_Insert
                + quotes(param3, false) + "";
    }

    public static String vendor_delete_from_table_4_2(String param1) {
        return "DELETE FROM Vendor_Contact "
                + "WHERE (Vendor_Contact_ID = " + param1 + ")";
    }

    public static String vendor_insert_new_table_4_2(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10) {
        String q = String.format("INSERT INTO Vendor_Contact "
                + "(VENDOR_ID, ContactName, position, Phone, Email,DateExport,Dateprocessed,Status,UpdatedOn,UpdatedBy)"
                + "VALUES (%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
        return q;
    }

    public static String vendor_delete_from_table_3_2(String param1) {
        return "DELETE FROM TRADENAME_MAIN "
                + "WHERE (Tradename_Main_ID = " + param1 + ")";
    }

    public static String vendor_insert_new_table_3_2(String param1, String param2, String param3, String param4, String param5) {
        String q = String.format("INSERT INTO TRADENAME_MAIN "
                + "(Cas_Number, TradeName, MSDS, UpdatedOn, UpdatedBy)"
                + "VALUES ('%s','%s','%s','%s','%s')",
                param1, param2, param3, param4, param5);
        return q;
    }

    public static String vendor_insert_new_table_4(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10, String param11, String param12, String param13, String param14) {
        String q = String.format("INSERT INTO VENDOR "
                + "(VendorNo, VendorName, Adress,ZipCode,City,Country,Phone,Fax,Email,Website,FreeInfo,Status, UpdatedOn, UpdatedBy)"
                + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14);
        return q;
    }

    public static String vendor_get_vendor_id_by_name(String vendorName) {
        return "select distinct VENDOR_ID from Vendor where VendorName =" + quotes(vendorName, false);
    }

    /**
     *
     * @param vendor_id
     * @return
     */
    public static String vendor_delete_from_table_4(String PROC, String param1) {
        return "[dbo].[" + PROC + "] "
                + quotes(param1, true) + "";
    }

    /**
     *
     * @param param1 id from "_INTRF_IngredientCode_ID__Vendor_ID" table
     * @return
     */
    public static String vendor_delete_from_table_3B(String PROC, String param1) {
        return "[" + PROC + "] "
                + quotes(param1, true);
    }

    /**
     *
     * @param param1 = Tradename_Main_Id
     * @param param2 = IngredientCodeId
     * @return
     */
    public static String vendor_delete_from_table_3(String PROC, String param1, String param2) {
        return "[" + PROC + "] "
                + quotes(param1, true) + ","
                + quotes(param2, true);
    }

    /**
     *
     * @param param1 IngredientCode_ID
     * @param param2 vendor_ID
     * @param param3 UpdatedBy
     * @param param4 UpdatedOn
     * @deprecated
     * @return
     */
    public static String vendor_insert_table_4(String PROC, String param1, String param2, String param3, String param4) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, true) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false);
    }

    public static String vendor_fill_combo_vendors() {
        return "SELECT distinct [VendorName], "
                + "[VENDOR_ID]  FROM [NCPD].[dbo].[VENDOR]"
                + " order by VendorName asc";
    }

    public static String vendor_check_if_contact_is_present(String param1, String param2, String param3, String param4, String param5) {
        return "select count(VENDOR_ID) as ammount"
                + " from Vendor_Contact"
                + " where"
                + " VENDOR_ID = " + quotes(param1, true)
                + " and"
                + " ContactName = " + quotes(param2, false)
                + " and"
                + " position = " + quotes(param3, false)
                + " and"
                + " Phone = " + quotes(param4, false)
                + " and"
                + " Email = " + quotes(param5, false);
    }

    /**
     *
     * @param ingredName 17460
     * @return
     */
    public static String get_ingredient_code_id_by_name(String ingredName) {
        return "select IngredientCode_Id from Ingredient_Code where [name] =" + quotes(ingredName, false);
    }

    /**
     *
     * @param param1 IngredientCode_ID bigint
     * @param param2 Tradename_Main_ID bigint
     * @param param3 UpdatedBy varchar(50)
     * @param param4 UpdatedOn datetime
     * @param param5 VENDOR_ID bigint
     * @return
     */
    public static String vendor_insert_table_3B(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, true) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, true);
    }

    /**
     *
     * @param param1 IngredientCode_ID
     * @param param2 tradename_Main_ID
     * @param param3 UpdatedBy
     * @param param4 UpdatedOn
     * @deprecated
     * @return
     */
    public static String vendor_insert_table_3(String PROC, String param1, String param2, String param3, String param4) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, true) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false);
    }

    public static String vendor_fill_combo_tradenames() {
        return "SELECT distinct [TradeName],[Tradename_Main_ID] "
                + "FROM [NCPD].[dbo].[TRADENAME_MAIN]"
                + " order by TradeName asc";
    }

    /**
     *
     * @param param1 = tradename_main_id
     * @return
     */
    public static String vendor_build_table_invert_3_2(String param1) {
        return "SELECT TradeName, Cas_Number, MSDS, UpdatedOn, UpdatedBy, Tradename_Main_ID\n"
                + "FROM TRADENAME_MAIN\n"
                + " WHERE (Tradename_Main_ID =" + quotes(param1, true) + ")";
    }

    /**
     *
     * @param param1 = ingred name
     * @return
     */
    public static String vendor_build_table_invert_4(String PROC, String param1) {
        return "SELECT * FROM " + PROC + " ("
                + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 vendor_id
     * @return
     */
    public static String vendor_build_table_invert_4_2(String param1) {
        return "SELECT VENDOR_ID, ContactName, position, Phone, Email, DateExport, Dateprocessed, Status,"
                + " UpdatedOn, UpdatedBy, Vendor_Contact_ID FROM Vendor_Contact"
                + " WHERE ( VENDOR_ID =" + quotes(param1, true) + ")";
    }

    /**
     *
     * @param param1 = ingred name
     * @return
     */
    public static String vendor_build_table_invert_3(String PROC, String param1) {
        return "SELECT * FROM " + PROC + " ("
                + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 = ingred name
     * @return
     */
    public static String vendor_build_table_invert_warehouse(String PROC, String param1) {
        return "SELECT * FROM " + PROC + " ("
                + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - mixerCode
     * @return
     */
    public static String sequence_get_sequence_main(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ""
                + ")";
    }

    /**
     *
     * @param param1 Recipe_Sequence_Main_ID_Target bigint
     * @param param2 Code varchar(50) source (right)
     * @param param3 Release varchar(50) source (right)
     * @param param4 MixerCode varchar(50) source (right)
     * @param param5 UpdatedOn datetime
     * @param param6 UpdatedBy varchar(50)
     * @return
     */
    public static String sequenceInsertFromOther(String PROC, String param1, String param2, String param3, String param4, String param5, String param6) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false);
    }

    /**
     *
     * @param code Code varchar(50) target (left)
     * @param release Release varchar(50)target (left)
     * @param mixerCode Mixer_Code varchar(50)target (left)
     * @return
     */
    public static String getRecipeSequenceMainId(String code, String release, String mixerCode) {
        return "select Recipe_Sequence_Main_ID from Recipe_Sequence_Main"
                + " where code = " + code + " "
                + " and release = " + release + ""
                + " and mixer_code = " + mixerCode + "";
    }

    /**
     *
     * @param param1 Code varchar(50) target (left)
     * @param param2 Release varchar(50)target (left)
     * @param param3 Mixer_Code varchar(50)target (left)
     * @param param4 Code2 varchar(50) source (right)
     * @param param5 Release2 varchar(50)source (right)
     * @param param6 Mixer_Code2 varchar(50)source (right)
     * @param param7 UpdatedOn datetime
     * @param param8 UpdatedBy varchar(50)
     * @param param9 info varchar(50)
     * @return
     */
    public static String copy_sequence(String PROC, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false) + ","
                + quotes(param7, false) + ","
                + quotes(param8, false) + ","
                + quotes(param9, false);
    }

    public static String fill_comboboxes_ingred(String PROC, String param, String[] params) {
        return "SELECT DISTINCT " + param + " FROM [" + PROC + "]" + " ("
                + "" + checkIfNull(params[0]) + ","
                + "" + checkIfNull(params[1]) + ","
                + "" + checkIfNull(params[2]) + ","
                + "" + checkIfNull(params[3]) + ","
                + "" + checkIfNull(params[4]) + ","
                + "" + checkIfNull(params[5]) + ","
                + "" + checkIfNull(params[6]) + ","
                + "" + checkIfNull(params[7]) + ","
                + "" + checkIfNull(params[8]) + ","
                + "" + checkIfNull(params[9]) + ")"
                + " order by " + param + " asc";
    }

    /**
     *
     * @param param IngredName
     * @return
     */
    public static String recipe_initial_build_info_table(String param) {
        return "SELECT distinct dbo.TRADENAME_MAIN.TradeName, dbo.Ingredient_Code.Descr, dbo.Ingredient_Code.[Group]\n"
                + "FROM dbo._INTRF_IngredientCode_ID__Tradename_Main_ID INNER JOIN\n"
                + "dbo.Ingredient_Code ON \n"
                + "dbo._INTRF_IngredientCode_ID__Tradename_Main_ID.IngredientCode_ID = dbo.Ingredient_Code.IngredientCode_ID INNER JOIN\n"
                + "dbo.TRADENAME_MAIN ON \n"
                + "dbo._INTRF_IngredientCode_ID__Tradename_Main_ID.Tradename_Main_ID = dbo.TRADENAME_MAIN.Tradename_Main_ID\n"
                + "WHERE (dbo.Ingredient_Code.Name='" + param + "')";
    }

    /**
     *
     * @param param = RecipeId
     * @return
     */
    public static String recipe_initial_unblock_recipe(String param) {
        return "UPDATE Recipe_Prop_Main SET "
                + "[Status]='I' "
                + "WHERE Recipe_ID=" + quotes(param, true);
    }

//    1. Recipe version, 2. Recipe_Additional, 3. Descr, 4. Customer, 
//            5. Detailed_Group, 6. Status, 7. Class, 8. Mixer_Code, 
//                    9. Created on, 10. Created  by, 11. UpdatedOn, 12. UpdatedBy
    public static String recipe_initial_main() {
        return "select ["
                + RecipeInitial.T1_RECIPE_VERSION + "],["
                + RecipeInitial.T1_RECIPE_ADDITIONAL + "],"
                + RecipeInitial.T1_DESCRIPTION + ",["
                + RecipeInitial.T1_DETAILED_GROUP + "],"
                + RecipeInitial.T1_STATUS + ","
                + RecipeInitial.T1_CLASS + ","
                + RecipeInitial.T1_MIXER_CODE + ","
                + "LEFT(CONVERT(VARCHAR(19),[" + RecipeInitial.T1_UPDATED_ON + "],126),10) "
                + "as [" + RecipeInitial.T1_UPDATED_ON + "],["
                + RecipeInitial.T1_UPDATED_BY + "],"
                //
                //These are not to be shown, but they might be needed 
                + RecipeInitial.T1_RECIPE_ID + ","
                + RecipeInitial.T1_RECIPE_ORIGIN + ","
                + RecipeInitial.T1_LOAD_FACTOR + ","
                + RecipeInitial.T1_MIX_TIME + " "
                //
                + "from ZZZ";
//                + " where [" + RecipeInitial.T1_RECIPE_VERSION + "] ='01-8-1608'";
    }

    public static String fill_combobox_recipe_initial_customer(String param) {
        return "SELECT distinct NoteValue "
                + "FROM Recipe_Prop_Free_Text "
                + "where NoteName = " + quotes(param, false) + " "
                + "ORDER BY NoteValue asc";
    }

    public static String fill_comboboxes_recipe_initial_B(String PROC, String param) {
        return "SELECT NoteValue "
                + "FROM " + PROC + "('" + param + "') "
                + "ORDER BY NoteValue";
    }

    public static String fill_comboboxes_recipe_initial_multiple(String PROC_1, String PROC_2, String param, String param2, String[] params) {
        //
        String procedure = "";
        //
        if (params.length == 21) {
            procedure = PROC_1;
        } else if (params.length == 23) { // In case if Ingredients search function is activated
            procedure = PROC_2;
        }
        //
        return "select distinct "
                + param + "," + param2
                + " from " + procedure + " ("
                + buildParametersForProcedure(params)
                + ") order by " + param + " asc";
    }

    public static String fill_comboboxes_recipe_initial_b(String PROC_1, String PROC_2, String param, String[] params) {
        //
        String procedure;
        //
        if (params.length == 23) {
            procedure = PROC_1;
            //
            return "select distinct " + param + " from " + procedure + " ("
                    + buildParametersForProcedure(params)
                    + ") order by " + param + " asc";
            //
        } else {
            procedure = PROC_2;
            //
            return "select distinct IngredName from " + procedure + "() "
                    + "order by IngredName asc";
            //
        }
        //
    }

    public static String fill_comboboxes_recipe_initial(String PROC_1, String PROC_2, String PROC_3, String PROC_4, String param, String[] params, MC_RECIPE_ mc_recipe) {
        //
        String procedure;
        //
        boolean cond_1 = MC_RECIPE_.jCheckBoxRecipeInitialOR.isSelected();
        boolean cond_2 = mc_recipe.jComboBox_Ingred_1.getSelectedItem() == null
                && mc_recipe.jComboBox_Ingred_2.getSelectedItem() == null;
        //
        if (params.length == 21) {//Ingredients box disabled
            procedure = PROC_1;
        } else if (params.length == 23 && cond_1 == false && cond_2 == false) { // In case if Ingredients search function is activated
            procedure = PROC_2;
        } else if (params.length == 23 && cond_1 && cond_2 == false) {
            procedure = PROC_3;
        } else {
            params = cutArr(params, 21);
            procedure = PROC_4;
        }
        //
        return "select distinct " + param + " from " + procedure + " ("
                + buildParametersForProcedure(params)
                + ") order by " + param + " asc";
    }

    private static String[] cutArr(String[] arrToCut, int positions) {
        //
        String[] arrToReturn = new String[positions];
        //
        for (int i = 0; i < positions; i++) {
            arrToReturn[i] = arrToCut[i];
        }
        //
        return arrToReturn;
    }

    public static String recipeInitialBuildTable1(String PROC_1, String PROC_2, String PROC_3, String[] params) {
        //
        String procedure;
        //
        boolean cond_1 = MC_RECIPE_.jCheckBoxRecipeInitialOR.isSelected();
        //
        if (params.length == 21) { //Ingred check box is of
            procedure = PROC_1;
        } else if (params.length == 23 && cond_1 == false) { // In case if Ingredients search function is activated
            procedure = PROC_2;
        } else {
            procedure = PROC_3;
        }
        //
        return "dbo.[" + procedure + "]" + " "
                + buildParametersForProcedure(params);
        //
    }

    /**
     * This one is called when searching by "note values"
     *
     * @param params
     * @return
     */
    public static String recipeInitialBuildTable1_B(String PROC, String[] params) {
        //
        String procedure = PROC;
        //
        return "dbo.[" + procedure + "]" + " "
                + buildParametersForProcedure(params);
    }

    private static String buildParametersForProcedure(String[] params) {
        String toReturn = "";
        //
        for (int i = 0; i < params.length; i++) {
            if (i < params.length - 1) {
                toReturn += checkIfNull(params[i]) + ",";
            } else {
                toReturn += checkIfNull(params[i]);
            }
        }
        //
        return toReturn;
    }

    /**
     *
     * @param param1 = Code varchar(50)
     * @param param2 = Release varchar(50)
     * @return
     */
    public static String deleteOtherSequence(String PROC, String param1, String param2) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, false) + ","
                + quotes(param2, false);
    }

    /**
     *
     * @param param1 = Recipe_Sequence_Main_ID
     * @param param2 = Mixer_Code varchar(50)
     * @param param3 = Info varchar(50)
     * @param param4 = UpdatedOn
     * @param param5 = UpdatedBy
     *
     * @return
     */
    public static String updateOtherSequence(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + "";
    }

    /**
     *
     * @param param1 = Code varchar(50)
     * @param param2 = Release varchar(50)
     * @param param3 = Mixer_Code
     * @return
     */
    public static String insertOtherSequence(String PROC, String param1, String param2, String param3) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    public static String fillMixerCodeComboSequence() {
        return "SELECT Code FROM Mixer_InfoBasic GROUP BY Code "
                + "ORDER BY Code";
    }

    /**
     *
     * @param param1 = Recipe_Sequence_Steps_ID bigint
     * @return
     */
    public static String deleteStepSequence(String PROC, String param1) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + "";
    }

    public static String sequenceGetStepsWithSeqId(String sequenceId) {
        return "select * from Recipe_Sequence_Steps"
                + " where Recipe_Sequence_Main_ID = " + quotes(sequenceId, true)
                + " Order by cast(Step_NB AS float)";
    }

    public static String sequenceRecalcStepNumbers(String id, String stepNr) {
        return "UPDATE Recipe_Sequence_Steps SET Step_NB ="
                + quotes(stepNr, false)
                + " WHERE (Recipe_Sequence_Steps_ID = " + quotes(id, true) + ")";
    }

    /**
     *
     * @param param1 = Recipe_Sequence_Steps_ID
     * @param param2 = Command_Name varchar(50)
     * @param param3 = Step_NB varchar(50)
     * @param param4 = Command_Param varchar(50)
     * @return
     */
    public static String updateStepSequence(String PROC, String param1, String param2, String param3, String param4) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + "";
    }

    /**
     *
     * @param param1 = Recipe_Sequence_Main_ID bigint,
     * @param param2 = Command_Name varchar(50)
     * @param param3 = Step_NB varchar(50)
     * @param param4 = Command_Param varchar(50)
     * @return
     */
    public static String insertStepSequence(String PROC, String param1, String param2, String param3, String param4) {
        return "[dbo].[" + PROC + "] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                //                + "'" + param3 + ".5',"
                + quotes(param4, false) + "";
    }

    public static String fillCommandNameComboBoxSequence() {
        return "SELECT Command_Name FROM Recipe_Sequence_Commands GROUP BY Command_Name "
                + "ORDER BY Command_Name";
    }

    public static String build_table2_sequence(String recipe, String release, String mixerCode) {
        return "SELECT RecipeID, Release, material, PHR, Weight, ContainerNB, Phase, percRubber, density, weighingID, BalanceID, SiloId, MatIndex, PriceKG, PriceData,   Descr, GRP "
                + "FROM dbo.Recipe_Full_RZPT_Seq(" + quotes(recipe, false) + "," + quotes(release, false) + "," + quotes(mixerCode, false) + ") "
                + "ORDER BY Phase, ContainerNB";
    }

    public static String build_table1_sequence(String PROC, String recipe, String release, String mixerCode) {
        return "SELECT Code AS [Recipe Code], Release, Step_NB AS [Step Nb], Command_Name AS [Command Name], Command_Param AS [Cmd Parameter], "
                + "Mixer_Code AS [Mixer Code], Recipe_Sequence_Main_ID, Recipe_Sequence_Steps_ID, "
                + "Info,"
                + "LEFT(CONVERT(VARCHAR(19),UpdatedOn,126),10) as UpdatedOn,"
                + "UpdatedBy "
                + "FROM dbo." + PROC + "(" + quotes(recipe, false) + "," + quotes(release, false) + "," + quotes(mixerCode, false) + ") "
                + "Order by CAST(Step_NB AS integer)";
    }

    public static String fill_combobox_sequence_2(String param, String procedureName, String[] params) {
        return "SELECT DISTINCT " + param + " FROM " + procedureName + " ("
                + "" + checkIfNull(params[0]) + ","
                + "" + checkIfNull(params[1]) + ","
                + "" + checkIfNull(params[2]) + ")";
    }

    public static String fill_combobox_sequence_2_multiple(String param, String param2, String procedureName, String[] params) {
        return "SELECT DISTINCT "
                + param + ","
                + param2
                + " FROM " + procedureName + " ("
                + "" + checkIfNull(params[0]) + ","
                + "" + checkIfNull(params[1]) + ","
                + "" + checkIfNull(params[2]) + ")";
    }

    private static String checkIfNull(String param) {
        if (param == null) {
            return null;
        } else if (param.equals("NULL")) {
            return null;
        } else {
            if (param.contains("'")) {
                return param;
            } else {
                return "'" + param + "'";
            }
        }
    }

    /**
     *
     * @param PROC
     * @param param1 - ingredName
     * @return
     */
    public static String ingredients_delete_ingred(String PROC, String param1) {
        return PROC + " " + quotes(param1, false) + "";
    }

    /**
     *
     * @param param1 = IngredientCode_ID bigint
     * @param param2 = NoteName varchar(50)
     * @param param3 = NoteValue varchar(50)
     * @param param4 = UpdatedOn varchar(50)
     * @param param5 = UpdatedBy varchar(50)
     * @return
     */
    public static String add_to_ingred_table_3(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return PROC + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + "";
    }

    /**
     *
     * @param param1 = Ingred_Free_Info_ID bigint
     * @return
     */
    public static String delete_from_ingred_table_3(String PROC, String param1) {
        return PROC + " "
                + quotes(param1, true) + "";
    }

    /**
     *
     * @param param1 = Ingred_Free_Info_ID bigint
     * @param param2 = IngredientCode_ID bigint
     * @param param3 = NoteName varchar(50)
     * @param param4 = NoteValue varchar(50)
     * @param param5 = UpdatedOn varchar(50)
     * @param param6 = UpdatedBy varchar(50)
     * @return
     */
    public static String update_ingred_table_3(String PROC, String param1, String param2, String param3, String param4, String param5, String param6) {
        return PROC + ""
                + quotes(param1, true) + ","
                + quotes(param2, true) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false) + "";
    }

    public static String quotes(String str, boolean number) {
        //
        if (str == null || str.equals("NULL")) {
            return "NULL";
        }
        //
        if (number) {
            return str.replaceAll("'", "");
        } else {
            if (str.contains("'")) {
                return str;
            } else {
                return "'" + str + "'";
            }
        }
    }

    /**
     *
     * @param param1 = IngredientCode_ID
     * @return
     */
    public static String build_ingred_textarea(String PROC, String param1) {
        return PROC + " '"
                + param1 + "'";
    }

    /**
     *
     * @param param1 = IngredCodeId (Name)
     * @return
     */
    public static String build_ingred_table_3(String PROC, String param1) {
        return PROC + " '"
                + param1 + "'";
    }

    /**
     *
     * @param param1 = IngredCodeId (Name)
     * @return
     */
    public static String build_ingred_table_2(String PROC, String param1) {
        return "SELECT * FROM [" + PROC + "]" + " ('"
                + param1 + "')";
    }

    /**
     *
     * @param param1 = name
     * @return
     */
    public static String prc_ITF_Igredients_main_Select(String PROC, String param1) {
        return PROC + quotes(param1, false);
    }

    /**
     *
     * @param param1 = Name
     * @param param2 = Class
     * @param param3 = Status
     * @param param4 = Group
     * @param param5 = Description
     * @param param6 = Form
     * @param param7 = PercRubber (CasNr??)
     * @param param8 = TradeName
     * @param param9 = Vendor
     * @param param10 = CasNr
     * @return
     */
    public static String ingredientListFunction(String PROC, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false) + ","
                + quotes(param7, false) + ","
                + quotes(param8, false) + ","
                + quotes(param9, false) + ","
                + quotes(param10, false) + ")"
                + " order by Name asc";
//                + " order by CAST(Name AS decimal) asc ";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - totalWeight
     * @param param4 - user
     * @return
     */
    public static String changeTotalWeight(String PROC, String param1, String param2, String param3, String param4) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "','"
                + param4 + "'";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - NewFillFactor
     * @param param4 - MixerId/MixerCode
     * @param param5 - user
     * @return
     */
    public static String changeLoadFactor(String PROC, String param1, String param2, String param3, String param4, String param5) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "','"
                + param4 + "','"
                + param5 + "'";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - totalVolume
     * @param param4 - user
     * @return
     */
    public static String changeTotalVolume(String PROC, String param1, String param2, String param3, String param4) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "','"
                + param4 + "'";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String changeIngredPHR(String PROC, String param1, String param2, String param3) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "'";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String changeIngredWeights(String PROC, String param1, String param2, String param3) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "'";
    }

    /**
     *
     * @param param1 = RecipeID
     * @param param2 = IngredName
     * @param param3 = PHR
     * @param param4 = Weight
     * @param param5 = ContainerNb
     * @param param6 = Phase
     * @param param7 = MatIndex
     * @param param8 = UpdatedBy
     * @return
     */
    public static String recipeRecipeInsertNewMaterial(String PROC, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8) {
        return PROC + ""
                + param1 + ",'"
                + param2 + "','"
                + param3 + "','"
                + param4 + "','"
                + param5 + "','"
                + param6 + "','"
                + param7 + "','"
                + param8 + "'";
    }

    /**
     *
     * @param param1 - Recipe_Recipe_ID
     * @param param2 - user
     * @return
     */
    public static String recipyTemporaryDelete(String PROC, String param1, String param2) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "'";
    }

    /**
     *
     * @param param1 - Recipe_Recipe_ID
     * @param param2 - PHR
     * @param param3 - weight
     * @param param4 - ContainerNb
     * @param param5 - phase
     * @param param6 - matindex
     * @param param7 - user
     * @return
     */
    public static String recipyTemporaryUpdate(String PROC, String param1, String param2, String param3, String param4, String param5, String param6, String param7) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "','"
                + param4 + "','"
                + param5 + "','"
                + param6 + "','"
                + param7 + "'";
    }

    public static String recipyTemporaryInsert(String PROC, String param1, String param2, String param3, String param4) {
        return PROC
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + "";
    }

    /**
     *
     * @param param1 - recipe code
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String createRecipeTempTable(String PROC, String param1, String param2, String param3) {
        return PROC + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "'";
    }

    /**
     *
     * @param param1 - mixerCode
     * @param param2 - LoadFactor
     * @param param3 - user
     * @return
     */
    public static String RecipeTemporaryPrepareSelect(String PROC, String param1, String param2, String param3) {
        //
        if (param2.isEmpty()) {
            param2 = "0";
        }
        //
        return PROC + "'"
                + param1 + "',"
                + param2 + ",'"
                + param3 + "'";
    }

    /**
     *
     * @param param1 - user
     * @return
     */
    public static String RecipeTemporarySelect(String param1) {
        return "SELECT "
                + " Id,"
                + " RecipeName,"
                + " Release,"
                + " material,"
                + " Descr,"
                + " density,"
                + " PriceKG,"
                + " PHR,"
                + " weight,"
                + " Volume,"
                + " Phase AS [Loading S.],"
                + " ContainerNB AS [Weighing S.],"
                //                + " density_Recalc,"
                + " PHR_recalc,"
                + " weight_Recalc,"
                + " MatIndex,"
                + " GRP,"
                + " percRubber,"
                + " volume_Recalc,"
                + " weighingID,"
                + " SiloId,"
                + " BalanceID,"
                + " PriceData,"
                + " Recipe_Recipe_ID,"
                + " RecipeID "
                + " FROM " + param1
                + " Order by cast(Id AS float)";
    }

    /**
     *
     * @param param1 - user
     * @return
     */
    public static String RecipeTemporarySelectForTable4SummTable(String param1) {
        return "SELECT TOP (100) "
                + "Id,"
                + " RecipeName,"
                + " Release,"
                + " material,"
                + " Descr,"
                + " density,"
                + " PriceKG,"
                + " PHR,"
                + " weight,"
                + " Volume,"
                + " Phase AS [Loading S.],"
                + " ContainerNB AS [Weighing S.],"
                //                + " density_Recalc,"
                + " PHR_recalc,"
                + " weight_Recalc,"
                + " MatIndex,"
                + " GRP,"
                + " percRubber,"
                + " volume_Recalc,"
                + " weighingID,"
                + " SiloId,"
                + " BalanceID,"
                + " PriceData,"
                + " Recipe_Recipe_ID,"
                + " RecipeID "
                + "FROM " + param1 + "1";
    }

    /**
     *
     * @param param1 = recipeVersion (Recipe code)
     * @param param2 = recipeAdditional (Release)
     * @param param3 = mixerCode
     * @return
     */
    public static String RecipeInvertFunction(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ('"
                + param1 + "','"
                + param2 + "','"
                + param3 + "')";
    }

    /**
     *
     * @param param1 = recipeVersion
     * @param param2 = recipeAdditional
     * @param param3 = mixerCode
     * @return
     */
    public static String fillTable2RecipeInitial(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ('"
                + param1 + "','"
                + param2 + "','"
                + param3 + "')";
    }

    /**
     *
     * @param param1 = recipeVersion
     * @param param2 = recipeAdditional
     * @param param3 = mixerCode
     * @return
     */
    public static String fillTable3RecipeInitial(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ('"
                + param1 + "','"
                + param2 + "','"
                + param3 + "')";
    }

    /**
     *
     * @param PROC
     * @param param1 - order
     * @param param2 - recipe
     * @return
     */
    public static String fn_ITF_Test_Related(String PROC, String param1, String param2) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ")";
    }

    /**
     *
     * @param PROC
     * @param param1 - order
     * @param param2 - recipe
     * @param param3 - ID_Wotest
     * @param param4 - ID_Proc
     * @param param5 - Ingredient_Vulco_Code_ID
     * @param param6 - Ingredient_Aeging_Code_ID
     * @return
     */
    public static String fn_ITF_Test_Related_ID(String PROC, String param1, String param2, String param3, String param4, String param5, String param6) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false) + ")";
    }

    /**
     *
     * @param PROC
     * @param param1 - parameter to choose - order or recipe
     * @param param2 - order
     * @param param3 - recipe
     * @return
     */
    public static String fill_comboboxes_test_parameters_a(String PROC, String param1, String param2, String param3) {
        return "SELECT distinct (" + param1 + ") FROM [" + PROC + "]" + " ("
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ")";
    }

    /**
     *
     * @param order
     * @return
     */
    public static String select_all_from_MC_Cpworder(String order) {
        return "SELECT * from MC_Cpworder WHERE WORDERNO='" + order + "'";
    }

    public static String get_lab_dev_table_1(String order, String tableName) {
        return "SELECT * from " + tableName + " WHERE WORDERNO='" + order + "'" + " ORDER BY ID ASC";
    }

    public static String get_lab_dev_table_2(String order, String tableName) {
        return "SELECT * from " + tableName + " WHERE WORDERNO='" + order + "'" + " ORDER BY ID ASC";
    }

//    public static String insert_into_lab_dev_table_1_and_2(String order){
//        return "INSERT INTO MC_Cpworder_SendTo VALUES ()";
//    }
    public static String insert_into_lab_dev_table_1_and_2(String table, String param1, String param2, String param3, String param4, String param5) {
        //
        String q = String.format("INSERT INTO " + table
                + "(WORDERNO, Abteilung, Name, UpdatedOn, UpdatedBy)"
                + "VALUES ('%s','%s','%s','%s','%s')", param1, param2, param3, param4, param5);
        //
        return q;
        //
    }

    public static String delete_lab_dev_jtable(String tableName, String id) {
        return "DELETE FROM " + tableName + " WHERE ID=" + id;
    }

    public static String save_notes_jtextarea_lab_dev(String tableName, String note, String order) {
        String q = "UPDATE " + tableName
                + " SET NOTE=" + quotes(note, false) + ","
                + "UpdatedOn=" + quotes(HelpA.updatedOn(), false) + ","
                + "UpdatedBy=" + quotes(HelpA.updatedBy(), false)
                + " WHERE WORDERNO=" + quotes(order, false) + "";
//        System.out.println(q);
        return q;
    }

    public static String get_lab_dev_tinvert_material_info(String id) {
        return "SELECT * from MC_Cpworder_OrderMaterials WHERE ID=" + id;
    }

    public static String lab_dev__material_info__add_material_combo() {
        return "SELECT distinct Code,Descr, Detailed_Group, Mixer_Code, Release, Status, Class FROM  Recipe_Prop_Main";
    }

    /**
     *
     * @param PROC - 79
     * @param param1 - order
     * @param param2 - material
     * @return
     */
    public static String lab_dev__material_info__add_material(String PROC, String param1, String param2) {
        return PROC + " "
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + "1" + "," // batch menge
                + "1" + "," // misch
                + "1" + "," // first batchno
                + "NULL" + "," // planId
                + "NULL" + "," // updatedOn
                + "NULL" + ""; // updatedby
    }

    /**
     *
     * @param PROC
     * @param param1 - orderno
     * @param param2 - ID
     * @return
     */
    public static String get_lab_dev_jtable_material_info(String PROC, String param1, String param2) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ")";
    }

    public static String get_lab_dev__test_definition_material_add_info(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ")"
                + " WHERE Material=" + quotes(param3, false);
    }

    public static String save_status_lab_dev(String status, String order) {
        return "UPDATE " + LabDevelopment_.TABLE__MC_CPWORDER + " SET WOSTATUS="
                + quotes(status, false) + " WHERE WORDERNO=" + quotes(order, false);
    }

    /**
     * SELECT * from MC_Cpworder where WOSTATUS='Execute' OR WOSTATUS='Ready'
     *
     * @param filter
     * @return
     */
    public static String find_order_lab_dev__dynamic(Object[] filter) {
        //
        String q = "SELECT ID,WORDERNO,WOSTATUS,REQUESTER,CUSTOMER,EXPREADY,UpdatedBy,UpdatedOn from MC_Cpworder";
        //
        for (int i = 0; i < filter.length; i++) {
            //
            String status = (String) filter[i];
            //
            if (i == 0) {
                q += " WHERE WOSTATUS=" + quotes(status, false);
            } else {
                q += " OR WOSTATUS=" + quotes(status, false);
            }
        }
        //
        q += " ORDER BY WORDERNO ASC";
        //
        return q;
    }

    public static String find_order_lab_dev__by_order(String orderno) {
        //
        return "SELECT ID,WORDERNO,WOSTATUS,REQUESTER,CUSTOMER,EXPREADY,UpdatedBy,UpdatedOn from MC_Cpworder WHERE WORDERNO=" + quotes(orderno, false);
        //
    }

    public static String find_order_lab_dev__delete_order__deprecated(String id) {
        return "DELETE FROM MC_Cpworder WHERE ID=" + quotes(id, true);
    }

    public static String find_order_lab_dev__delete_order(String PROC, String param1) {
        return PROC + " " + quotes(param1, false) + "";
    }

    /**
     * Used by: TEST DEFINITION & TEST CONFIG tabs
     *
     * @param PROC
     * @param param1 - material
     * @param param2 - order
     * @param param3 - code
     * @return
     */
    public static String lab_dev_proc69_proc70(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ")";
    }

    public static String lab_dev_test_definition__get_lastupdate(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ") ORDER BY UpdatedOn DESC";
    }

    public static String lab_dev_test_definitions_tab__getCodes(String PROC, String param1, String param2, String param3) {
        return "SELECT DISTINCT CODE FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ")";
    }

    /**
     *
     * @param PROC - 71, 72
     * @param param1 - material
     * @param param2 - order
     * @param param3 - NULL
     * @return
     */
    public static String lab_dev_test_config__get_preparation_aging_methods(String PROC, String param1, String param2, String param3) {
        return "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ") ORDER BY DESCR";
    }

    public static String lab_dev__insert_into_MC_CPAGEMET__MC_CPVULMET(String tableName, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10, String param11) {
        String q = String.format("INSERT INTO " + tableName + " "
                + "VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
                quotes(param1, false), quotes(param2, false), quotes(param3, false),
                quotes(param4, false), quotes(param5, false), quotes(param6, false),
                quotes(param7, false), quotes(param8, false), quotes(param9, false),
                quotes(param10, false), quotes(param11, false));
        return q;
    }

    public static String lab_dev__test_proc(String UpdatedOnORBy, String tableName, String id) {
        return "SELECT " + UpdatedOnORBy + " FROM " + tableName + " WHERE ID_Proc=" + id;
    }

    public static String lab_dev__test__proc__build__testcode_combo() {
        return "select DISTINCT CODE from " + TABLE__TEST_PROCEDURE + " ORDER BY CODE ASC";
    }

    /**
     *
     * @param PROC - 75
     * @param param1 Order
     * @param param2 Material
     * @param param3 TestCode - VUG01
     * @param param4 ID_Wotest
     * @return
     */
    public static String lab_dev__test_variable(String PROC, String param1, String param2, String param3, String param4) {
        //
        String where = " WHERE ID_Wotest=";
        String orderBy = " ORDER BY CODE, TestCode";
        //
        String prefix = "SELECT * FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ")";
        //
        if (param4 == null || param4.isEmpty()) { // ID_Wotest not present
            return prefix + orderBy;
        } else {
            return prefix + where + param4 + orderBy;
        }
        //
    }

    public static String lab_dev__test_variable__fill__combos(String PROC, String param, String[] params) {
        return "SELECT DISTINCT " + param + " FROM [" + PROC + "]" + " ("
                + "" + checkIfNull(params[0]) + ","
                + "" + checkIfNull(params[1]) + ","
                + "" + checkIfNull(params[2]) + ")"
                + " order by " + param + " asc";
    }

    /**
     *
     * @param PROC 76
     * @param param1 - TestCode (VUG01)
     * @return
     */
    public static String lab_dev_test_variable__get_list_for_creating_new(String PROC, String param1) {
        return "SELECT CODE, TESTVAR, DESCRIPT, NORM, Condition, ID_Proc FROM [" + PROC + "]" + " ("
                + quotes(param1, false)
                + ") ORDER BY ID_Proc";
    }

    /**
     *
     * @param PROC - 77
     * @param param1 - order
     * @param param2 - material
     * @param param3 - testCode
     * @param param4 - id
     * @return
     */
    public static String lab_dev_test_variable__insert_new(String PROC, String param1, String param2, String param3, String param4) {
        return PROC + " "
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, true) + "";
    }

    public static String lab_dev_test__test_variable_check_exist(String PROC, String param1, String param2, String param3, String param4) {
        return "SELECT TagId FROM [" + PROC + "]" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false)
                + ") WHERE TagId=" + quotes(param4, true);
    }

    public static String lab_dev__test_variable__delete_button(String id, String order, String material) {
        return "DELETE FROM MCcpwotest "
                + "WHERE TagId=" + quotes(id, true) + " AND ORDERNO=" + quotes(order, false) + "AND CODE=" + quotes(material, false);
    }

    public static String lab_dev__find_order_tab__create__new(String tableName, String param1) {
        //
        String q = String.format("INSERT INTO %s (WORDERNO,WOSTATUS) VALUES (%s,'Input')", tableName, quotes(param1, false));
        //
        return q;
    }
    
    /**
     * 
     * @param PROC - 81
     * @param param1 - order
     * @param param2 - material
     * @return 
     */
    public static String lab_dev_material_info__delete(String PROC, String param1, String param2) {
        return PROC + " "
                + quotes(param1, false) + ","
                + quotes(param2, false) + "";
    }

    public static void main(String[] args) {
        System.out.println("" + lab_dev__find_order_tab__create__new("MC_Cpworder", "AAAAA"));
    }

//    SELECT     TagId
//FROM         dbo.fn_ITF_CPWORDER_Info('ENTW001921', 'WE8422', 'ZUG02') AS fn_ITF_CPWORDER_Info_1
//WHERE     (TagId = 17)
//    public static void main(String[] args) {
//        //
//        String[] arr = new String[]{"Archiv", "Ready"};
//        //
//        System.out.println("" + find_order_lab_dev__dynamic(arr));
//    }
}
