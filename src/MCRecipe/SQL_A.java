/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

/**
 * vendor_insert_new_table_3_2
 *
 * @author KOCMOC
 */
public class SQL_A {

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
     * @param param1 - recipeCode
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String compareRecipesAddToCompare(String param1, String param2, String param3) {
        return "Create_Recipe_Tempory_USER_Compare " + ""
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param param1 - recipeCode
     * @param param2 - order
     * @return
     */
    public static String recipe_additional_build_table_2(String param1, String param2) {
        return "SELECT * FROM fn_Lab_REsults" + " ("
                + quotes(param1, false) + ","
                + quotes(param2, false)
                + ")"
                + " order by BatchNo, TestCode, TestNo desc";
    }

    /**
     *
     * @param param1 - recipeCode
     * @return
     */
    public static String recipe_additional_fill_combo_orders(String param1) {
        return "SELECT * FROM fn_Lab_SelectOrders" + " ("
                + quotes(param1, false) + ")"
                + " order by [order] desc";
    }

    /**
     *
     * @param param1 = recipeCode
     * @return
     */
    public static String recipe_additional_build_table_1(String param1) {
        return "SELECT Quality,TestCode,Description,LSL,USL,Name,Target,Device"
                + " FROM fn_Lab_Procedures" + " ("
                + quotes(param1, false) + ")"
                + " order by TestCode";
    }

    /**
     *
     * @param param1 - new vendor name
     * @return
     */
    public static String vendors_add_new_vendor(String param1) {
        return "prc_ITF_Vendor_Insert" + " "
                + quotes(param1, false) + "";
    }

    /**
     *
     * @param param1 - IngredName
     * @return
     */
    public static String recipe_detailed_find_density_ingred(String param1) {
        return "select density from fn_Recipe_Ingredient_Insert (" + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 - Recipe_Id, bigint
     * @param param2 - NoteName
     * @param param3 - NoteValue
     * @param param3 - UpdateOn
     * @param param4 - UpdateBy
     * @return
     */
    public static String recipe_detailed_insert_note_table_2(String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[prc_ITF_RECIPE_FreeInfo_Insert] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false);
    }

    /**
     *
     * @param param1 - Recipe_Id, bigint
     * @param param2 - NoteName
     * @param param3 - NoteValue
     * @param param3 - UpdateOn
     * @param param4 - UpdateBy
     * @return
     */
    public static String recipe_detailed_insert_note_table_3(String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[prc_ITF_RECIPE_FreeTEXT_Insert] " + ""
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false);
    }

    /**
     *
     * @param param1 - Recipe_Id, bigint
     * @param param2 - NoteName varchar
     * @param param3 - NoteValue varchar
     * @param param4 - UpdateOn
     * @param param5 - UpdateBy
     * @return
     */
    public static String recipe_detailed_update_table_2(String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[prc_ITF_RECIPE_FreeInfo_Update] " + ""
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
     * @param param1 - ingredientCOde_id
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String ingredients_add_new_ingredient_scratch(String param1, String param2, String param3) {
        return "prc_ITF_Ingredient_NEW_fromScratch" + " "
                + quotes(param1, true) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param param1 - ingredientCOde_id
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String ingredients_add_new_ingredient(String param1, String param2, String param3) {
        return "prc_ITF_Ingredient_NEW_Insert" + " "
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

    public static String recipe_detailed_add_new_recipe_scratch(String param1, String param2, String param3) {
        return "prc_ITF_RECIPE_Scratch" + " "
                + quotes(param1, true) + "," //prc_ITF_RECIPE_Scratch
                + quotes(param2, false) + "," //prc_ITF_RECIPE_main_Insert
                + quotes(param3, false) + "";
    }

    /**
     *
     * @param param1 - RecipeID
     * @param param2 - CreatedOn
     * @param param3 - CreatedBy
     * @return
     */
    public static String recipe_detailed_add_new_recipe(String param1, String param2, String param3) {
        return "prc_ITF_RECIPE_main_Insert" + " "
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
    public static String vendor_delete_from_table_4(String param1) {
        return "[dbo].[prc_ITF_Igredients_DisplVendor_Delete] "
                + quotes(param1, true) + "";
    }

    /**
     *
     * @param param1 id from "_INTRF_IngredientCode_ID__Vendor_ID" table
     * @return
     */
    public static String vendor_delete_from_table_3B(String param1) {
        return "[prc_ITF_Igredients_DisplVendor_Delete] "
                + quotes(param1, true);
    }

    /**
     *
     * @param param1 = Tradename_Main_Id
     * @param param2 = IngredientCodeId
     * @return
     */
    public static String vendor_delete_from_table_3(String param1, String param2) {
        return "[prc_ITF_Igredients_DisplPurchase_delete] "
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
    public static String vendor_insert_table_4(String param1, String param2, String param3, String param4) {
        return "[dbo].[prc_ITF_Igredients_DisplVendor_INSERT] " + ""
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

    public static void main(String[] args) {
        System.out.println("" + vendor_check_if_contact_is_present("1236", "aa", "aa", "aa", "aa"));
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
    public static String vendor_insert_table_3B(String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[prc_ITF_Igredients_DisplPurchase_INSERT] " + ""
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
    public static String vendor_insert_table_3(String param1, String param2, String param3, String param4) {
        return "[dbo].[prc_ITF_Igredients_DisplPurchase_INSERT] " + ""
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
    public static String vendor_build_table_invert_4(String param1) {
        return "SELECT * FROM fn_ITF_Vendor_Init_Load" + " ("
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
    public static String vendor_build_table_invert_3(String param1) {
        return "SELECT * FROM fn_ITF_Igredients_Display_Purchase" + " ("
                + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 = ingred name
     * @return
     */
    public static String vendor_build_table_invert_warehouse(String param1) {
        return "SELECT * FROM fn_ITF_Igredients_Display_Warehouse" + " ("
                + quotes(param1, false) + ")";
    }

    /**
     *
     * @param param1 - recipe
     * @param param2 - release
     * @param param3 - mixerCode
     * @return
     */
    public static String sequence_get_sequence_main(String param1, String param2, String param3) {
        return "SELECT * FROM [fn_ITF_Sequence_Main_Get]" + " ("
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
    public static String sequenceInsertFromOther(String param1, String param2, String param3, String param4, String param5, String param6) {
        return "[dbo].[prc_ITF_Sequence_insert_from_Other] " + ""
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
     * @return
     */
    public static String copy_sequence(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8) {
        return "[dbo].[prc_ITF_Sequence_Insert_Main_from_Other] " + ""
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + ","
                + quotes(param5, false) + ","
                + quotes(param6, false) + ","
                + quotes(param7, false) + ","
                + quotes(param8, false);
    }

    public static String fill_comboboxes_ingred(String param, String[] params) {
        return "SELECT DISTINCT " + param + " FROM [fn_ITF_Igredients_BasSearch]" + " ("
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

    /**
     *
     * @param param
     * @param params
     * @deprecated
     * @return
     */
    public static String fill_comboboxes_recipe_initial_old(String param, String[] params) {
        return "SELECT DISTINCT " + param + " FROM [fn_ITF_Recipes_Init]" + " ("
                + "" + checkIfNull(params[0]) + ","
                + "" + checkIfNull(params[1]) + ","
                + "" + checkIfNull(params[2]) + ","
                + "" + checkIfNull(params[3]) + ","
                + "" + checkIfNull(params[4]) + ","
                + "" + checkIfNull(params[5]) + ","
                + "" + checkIfNull(params[6]) + ","
                + "" + checkIfNull(params[7]) + ")"
                + " order by " + param + " asc";
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

    public static String fill_comboboxes_recipe_initial_B(String param) {
        return "SELECT NoteValue "
                + "FROM dbo.fn_ITF_Recipes_PropFree_Info_fullList('" + param + "') "
                + "ORDER BY NoteValue";
    }

    public static String fill_comboboxes_recipe_initial_multiple(String param, String param2, String[] params) {
        //
        String procedure = "";
        //
        if (params.length == 21) {
            procedure = "fn_ITF_Recipes_Z";
        } else if (params.length == 23) { // In case if Ingredients search function is activated
            procedure = "fn_ITF_Recipes_Z_X";
        }
        //
        return "select distinct "
                + param + "," + param2
                + " from " + procedure + " ("
                + buildParametersForProcedure(params)
                + ") order by " + param + " asc";
    }

    public static String fill_comboboxes_recipe_initial_b(String param, String[] params) {
        //
        String procedure;
        //
        if (params.length == 23) {
            procedure = "fn_ITF_Recipes_Z_X";
            //
            return "select distinct " + param + " from " + procedure + " ("
                    + buildParametersForProcedure(params)
                    + ") order by " + param + " asc";
            //
        } else {
            procedure = "fn_ITF_Recipes_Z_X_IngredName";
            //
            return "select distinct IngredName from " + procedure + "() "
                    + "order by IngredName asc";
            //
        }
        //
    }

    public static String fill_comboboxes_recipe_initial(String param, String[] params, MC_RECIPE mc_recipe) {
        //
        String procedure;
        //
        boolean cond_1 = MC_RECIPE.jCheckBoxRecipeInitialOR.isSelected();
        boolean cond_2 = mc_recipe.jComboBox_Ingred_1.getSelectedItem() == null
                && mc_recipe.jComboBox_Ingred_2.getSelectedItem() == null;
        //
        if (params.length == 21) {//Ingredients box disabled
            procedure = "fn_ITF_Recipes_Z";
        } else if (params.length == 23 && cond_1 == false && cond_2 == false) { // In case if Ingredients search function is activated
            procedure = "fn_ITF_Recipes_Z_X";
        } else if (params.length == 23 && cond_1 && cond_2 == false) {
            procedure = "fn_ITF_Recipes_Z_X_OR";
        } else {
            procedure = "fn_ITF_Recipes_Z";
        }
        //
        return "select distinct " + param + " from " + procedure + " ("
                + buildParametersForProcedure(params)
                + ") order by " + param + " asc";
    }

    public static String recipeInitialBuildTable1(String[] params) {
        //
        String procedure;
        //
        boolean cond_1 = MC_RECIPE.jCheckBoxRecipeInitialOR.isSelected();
        //
        if (params.length == 21) { //Ingred check box is of
            procedure = "prc_ITF_Recipes_Z_Z";
        } else if (params.length == 23 && cond_1 == false) { // In case if Ingredients search function is activated
            procedure = "prc_ITF_Recipes_Z_X";
        } else {
            procedure = "prc_ITF_Recipes_Z_X_OR";
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
    public static String recipeInitialBuildTable1_B(String[] params) {
        //
        String procedure = "prc_ITF_Recipes_Z_A";
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
    public static String deleteOtherSequence(String param1, String param2) {
        return "[dbo].[prc_ITF_Sequence_deleteMain] " + ""
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
    public static String updateOtherSequence(String param1, String param2, String param3, String param4, String param5) {
        return "[dbo].[prc_ITF_Sequence_Edit_Main] " + ""
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
    public static String insertOtherSequence(String param1, String param2, String param3) {
        return "[dbo].[prc_ITF_Sequence_Insert_Main] " + ""
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
    public static String deleteStepSequence(String param1) {
        return "[dbo].[prc_ITF_Sequence_deleteSingle] " + ""
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
    public static String updateStepSequence(String param1, String param2, String param3, String param4) {
        return "[dbo].[prc_ITF_Sequence_Edit] " + ""
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
    public static String insertStepSequence(String param1, String param2, String param3, String param4) {
        return "[dbo].[prc_ITF_Sequence_insert] " + ""
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

    public static String build_table1_sequence(String recipe, String release, String mixerCode) {
        return "SELECT Code AS [Recipe Code], Release, Step_NB AS [Step Nb], Command_Name AS [Command Name], Command_Param AS [Cmd Parameter], "
                + "Mixer_Code AS [Mixer Code], Recipe_Sequence_Main_ID, Recipe_Sequence_Steps_ID, "
                + "Info,"
                + "LEFT(CONVERT(VARCHAR(19),UpdatedOn,126),10) as UpdatedOn,"
                + "UpdatedBy "
                + "FROM dbo.fn_ITF_Sequence_Init_SelectUnic(" + quotes(recipe, false) + "," + quotes(release, false) + "," + quotes(mixerCode, false) + ") "
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
     * @param param1 = IngredientCode_ID bigint
     * @param param2 = NoteName varchar(50)
     * @param param3 = NoteValue varchar(50)
     * @param param4 = UpdatedOn varchar(50)
     * @param param5 = UpdatedBy varchar(50)
     * @return
     */
    public static String add_to_ingred_table_3(String param1, String param2, String param3, String param4, String param5) {
        return "prc_ITF_Ingredients_FreeInfo_Insert " + ""
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
    public static String delete_from_ingred_table_3(String param1) {
        return "prc_ITF_Ingredients_FreeInfo_Delete" + " "
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
    public static String update_ingred_table_3(String param1, String param2, String param3, String param4, String param5, String param6) {
        return "prc_ITF_Ingredients_FreeInfo_Update " + ""
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
    public static String build_ingred_textarea(String param1) {
        return "prc_ITF_Ingredients_Coments_Select" + " '"
                + param1 + "'";
    }

    /**
     *
     * @param param1 = IngredCodeId (Name)
     * @return
     */
    public static String build_ingred_table_3(String param1) {
        return "prc_ITF_Ingredients_FreeInfo_Select" + " '"
                + param1 + "'";
    }

    /**
     *
     * @param param1 = IngredCodeId (Name)
     * @return
     */
    public static String build_ingred_table_2(String param1) {
        return "SELECT * FROM [fn_ITF_Igredients_Init_Load_Warehause]" + " ('"
                + param1 + "')";
    }

    /**
     *
     * @param param1 = name
     * @return
     */
    public static String prc_ITF_Igredients_main_Select(String param1) {
        return "prc_ITF_Igredients_main_Select " + quotes(param1, false);
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
    public static String ingredientListFunction(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10) {
        return "SELECT * FROM [fn_ITF_Igredients_BasSearch]" + " ("
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
    public static String changeTotalWeight(String param1, String param2, String param3, String param4) {
        return "Recipe_Tempory_Recalc_newTotalWeight_USER " + "'"
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
    public static String changeLoadFactor(String param1, String param2, String param3, String param4, String param5) {
        return "Recipe_Tempory_Recalc_FillFactor_USER " + "'"
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
    public static String changeTotalVolume(String param1, String param2, String param3, String param4) {
        return "Recipe_Tempory_Recalc_newTotalVolume_USER " + "'"
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
    public static String changeIngredPHR(String param1, String param2, String param3) {
        return "Recipe_Tempory_Recalc_givenPHRS_USER " + "'"
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
    public static String changeIngredWeights(String param1, String param2, String param3) {
        return "Recipe_Tempory_Recalc_GivenWeights_USER " + "'"
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
    public static String recipeRecipeInsertNewMaterial(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8) {
        return "Recipe_Recipe_INSERT_New_material " + ""
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
    public static String recipyTemporaryDelete(String param1, String param2) {
        return "Recipe_Tempory_Delete_User " + "'"
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
    public static String recipyTemporaryUpdate(String param1, String param2, String param3, String param4, String param5, String param6, String param7) {
        return "Recipe_Tempory_UPDATE_USER " + "'"
                + param1 + "','"
                + param2 + "','"
                + param3 + "','"
                + param4 + "','"
                + param5 + "','"
                + param6 + "','"
                + param7 + "'";
    }

    public static String recipyTemporaryInsert(String param1, String param2, String param3, String param4) {
        return "Recipe_Tempory_INSERT_INTO_USER "
                + quotes(param1, false) + ","
                + quotes(param2, false) + ","
                + quotes(param3, false) + ","
                + quotes(param4, false) + "";
    }

    public static String for_adding_to_table_4(String param1) {
        return "SELECT * FROM [fn_Recipe_Ingredient_Insert]" + " ('"
                + param1 + "')";
    }

    /**
     *
     * @param param1 - recipe code
     * @param param2 - release
     * @param param3 - user
     * @return
     */
    public static String createRecipeTempTable(String param1, String param2, String param3) {
        return "Create_Recipe_Tempory1_USER " + "'"
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
    public static String RecipeTemporaryPrepareSelect(String param1, String param2, String param3) {
        //
        if (param2.isEmpty()) {
            param2 = "0";
        }
        //
        return "Recipe_Full_RZPT_1_O_W_User " + "'"
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
     * @param param1 - mixerCode
     * @param param2 - LoadFactor
     * @deprecated
     * @return
     */
    public static String RecipeTemporarySelect(String param1, String param2) {
        return "SELECT * FROM [Recipe_Full_RZPT_1_O_W]" + " ('"
                + param1 + "','"
                + param2 + "') "
                + "where id != 99";
    }

    /**
     *
     * @param param1
     * @param param2
     * @deprecated
     * @return
     */
    public static String RecipeTemporarySelectForTable4SummTable(String param1, String param2) {
        return "SELECT * FROM [Recipe_Full_RZPT_1_O_W]" + " ('"
                + param1 + "','"
                + param2 + "') "
                + "where id = 99";
    }

    /**
     *
     * @param param1 = recipeVersion (Recipe code)
     * @param param2 = recipeAdditional (Release)
     * @param param3 = mixerCode
     * @return
     */
    public static String RecipeInvertFunction(String param1, String param2, String param3) {
        return "SELECT * FROM [User_Int_RecipeInvert]" + " ('"
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
    public static String fillTable2RecipeInitial(String param1, String param2, String param3) {
        return "SELECT * FROM [fn_ITF_Recipes_Info]" + " ('"
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
    public static String fillTable3RecipeInitial(String param1, String param2, String param3) {
        return "SELECT * FROM [fn_ITF_Recipes_TEXT]" + " ('"
                + param1 + "','"
                + param2 + "','"
                + param3 + "')";
    }
}
