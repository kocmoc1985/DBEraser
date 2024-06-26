/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import java.util.ArrayList;

/**
 *
 * @author mcab
 */
public class PROC {

    public static final ArrayList<String> procList = new ArrayList<String>();

    public static boolean exists(String procName) {
        for (String proc : procList) {
            if (proc.contains(procName)) {
                return true;
            }
        }
        return false;
    }

    public static final String PROC_01 = "Create_Recipe_Tempory_USER_Compare ";//#RECIPE INITIAL, HelpA.runProcedureIntegerReturn_A_2
    public static final String PROC_02 = "fn_Lab_REsults "; //#RECIPE ADDITIONAL, ResultSet rs = sql.execute(q,mCRecipe)
    public static final String PROC_03 = "fn_Lab_SelectOrders "; //#RECIPE ADDITIONAL, ResultSet rs = sql.execute(query)
    public static final String PROC_04 = "fn_Lab_Procedures ";//#RECIPE ADDITIONAL, ResultSet rs = sql.execute(q,mCRecipe)
    public static final String PROC_05 = "prc_ITF_Vendor_Insert ";//#VENDORS, sql.execute(q)
    public static final String PROC_06 = "fn_Recipe_Ingredient_Insert ";//#RECIPE DETAILED, ResultSet rs = sql.execute(q, mCRecipe2)
    public static final String PROC_07 = "prc_ITF_RECIPE_FreeInfo_Insert ";//#RECIPE DETAILED, sql.execute(q, mCRecipe2)
    public static final String PROC_08 = "prc_ITF_RECIPE_FreeTEXT_Insert ";//#RECIPE DETAILED, sql.execute(q, mCRecipe2)
    public static final String PROC_09 = "prc_ITF_RECIPE_FreeInfo_Update ";//#RECIPE DETAILED, sql.execute(q, mCRecipe2)
    public static final String PROC_10 = "prc_ITF_Ingredient_NEW_fromScratch ";//#INGREDIENTS, sql.execute(q, mCRecipe)
    public static final String PROC_11 = "prc_ITF_Ingredient_NEW_Insert ";//#INGREDIENTS
    public static final String PROC_12 = "prc_ITF_RECIPE_Scratch_1 ";//#RECIPE DETAILED, HelpA.runProcedureIntegerReturn_A_2
    public static final String PROC_13 = "prc_ITF_RECIPE_main_Insert ";//#RECIPE DETAILED, HelpA.runProcedureIntegerReturn_A_2
    public static final String PROC_14 = "prc_ITF_Igredients_DisplVendor_Delete ";//#VENDORS
    public static final String PROC_15 = "prc_ITF_Igredients_DisplPurchase_delete ";//#VENDORS
    public static final String PROC_16 = "prc_ITF_Igredients_DisplVendor_INSERT ";//#VENDORS
    public static final String PROC_17 = "prc_ITF_Igredients_DisplPurchase_INSERT ";//#VENDORS
    public static final String PROC_18 = "fn_ITF_Vendor_Init_Load ";//#VENDORS
    public static final String PROC_19 = "fn_ITF_Igredients_Display_Purchase ";//#VENDORS
    public static final String PROC_20 = "fn_ITF_Igredients_Display_Warehouse ";//#VENDORS
    public static final String PROC_21 = "fn_ITF_Sequence_Main_Get ";    //#SEQUENCE
    public static final String PROC_22 = "prc_ITF_Sequence_insert_from_Other ";    //#SEQUENCE
    public static final String PROC_23 = "prc_ITF_Sequence_Insert_Main_from_Other ";    //#SEQUENCE
    public static final String PROC_24 = "fn_ITF_Igredients_BasSearch ";//#INGREDIENTS //#VENDORS
    public static final String PROC_25 = "fn_ITF_Recipes_PropFree_Info_fullList ";
    public static final String PROC_26 = "fn_ITF_Recipes_Z ";//#RECIPE INITIAL
    public static final String PROC_27 = "fn_ITF_Recipes_Z_X "; //#RECIPE INITIAL
    public static final String PROC_28 = "fn_ITF_Recipes_Z_X_IngredName "; //#RECIPE INITIAL
    public static final String PROC_29 = "fn_ITF_Recipes_Z_X_OR "; //#RECIPE INITIAL
    public static final String PROC_30 = "prc_ITF_Recipes_Z_Z "; //#RECIPE INITIAL, HelpA.runProcedureIntegerReturn_A
    public static final String PROC_31 = "prc_ITF_Recipes_Z_X "; //#RECIPE INITIAL
    public static final String PROC_32 = "prc_ITF_Recipes_Z_X_OR "; //#RECIPE INITIAL
    public static final String PROC_33 = "prc_ITF_Recipes_Z_A "; //#RECIPE INITIAL, HelpA.runProcedureIntegerReturn_A
    public static final String PROC_34 = "prc_ITF_Sequence_deleteMain "; //#SEQUENCE
    public static final String PROC_35 = "prc_ITF_Sequence_Edit_Main "; //#SEQUENCE
    public static final String PROC_36 = "prc_ITF_Sequence_Insert_Main "; //#SEQUENCE
    public static final String PROC_37 = "prc_ITF_Sequence_deleteSingle "; //#SEQUENCE
    public static final String PROC_38 = "prc_ITF_Sequence_Edit "; //#SEQUENCE
    public static final String PROC_39 = "prc_ITF_Sequence_insert "; //#SEQUENCE
    public static final String PROC_40 = "fn_ITF_Sequence_Init_SelectUnic "; //#SEQUENCE
    public static final String PROC_41 = "fn_ITF_Sequence_Init_SelectSet_1 "; //#SEQUENCE
    public static final String PROC_42 = "fn_ITF_Sequence_Init_SelectSet_2 "; //#SEQUENCE
    public static final String PROC_43 = "prc_ITF_Ingredients_FreeInfo_Insert "; //#INGREDIENTS
    public static final String PROC_44 = "prc_ITF_Ingredients_FreeInfo_Delete "; //#INGREDIENTS
    public static final String PROC_45 = "prc_ITF_Ingredients_FreeInfo_Update "; //#INGREDIENTS
    public static final String PROC_46 = "prc_ITF_Ingredients_Coments_Select "; //#INGREDIENTS
    public static final String PROC_47 = "prc_ITF_Ingredients_FreeInfo_Select "; //#INGREDIENTS
    public static final String PROC_48 = "fn_ITF_Igredients_Init_Load_Warehause "; //#INGREDIENTS
    public static final String PROC_49 = "prc_ITF_Igredients_main_Select "; //#INGREDIENTS //#VENDORS
    public static final String PROC_50 = "fn_ITF_Igredients_BasSearch "; //#INGREDIENTS
    public static final String PROC_51 = "Recipe_Tempory_Recalc_newTotalWeight_USER "; //#RECIPE DETAILED
    public static final String PROC_52 = "Recipe_Tempory_Recalc_FillFactor_USER "; //#RECIPE DETAILED
    public static final String PROC_53 = "Recipe_Tempory_Recalc_newTotalVolume_USER "; //#RECIPE DETAILED
    public static final String PROC_54 = "Recipe_Tempory_Recalc_givenPHRS_USER "; //#RECIPE DETAILED
    public static final String PROC_55 = "Recipe_Tempory_Recalc_GivenWeights_USER "; //#RECIPE DETAILED
    public static final String PROC_56 = "Recipe_Recipe_INSERT_New_material "; //#RECIPE DETAILED
    public static final String PROC_57 = "Recipe_Tempory_Delete_User "; //#RECIPE DETAILED
    public static final String PROC_58 = "Recipe_Tempory_UPDATE_USER "; //#RECIPE DETAILED
    public static final String PROC_59 = "Recipe_Tempory_INSERT_INTO_USER "; //#RECIPE DETAILED
    public static final String PROC_60 = "Create_Recipe_Tempory1_USER "; //#RECIPE DETAILED
    public static final String PROC_61 = "Recipe_Full_RZPT_1_O_W_User ";  //#RECIPE DETAILED, HelpA.runProcedureIntegerReturn_A_2(sql_additional, q_0_1)
    public static final String PROC_62 = "User_Int_RecipeInvert "; //#RECIPE DETAILED
    public static final String PROC_63 = "fn_ITF_Recipes_Info "; //#RECIPE INITIAL
    public static final String PROC_64 = "fn_ITF_Recipes_TEXT "; //#RECIPE INITIAL
    public static final String PROC_65 = "prc_ITF_Igredients_main_Delete "; //#INGREDIENTS
    public static final String PROC_66 = "fn_ITF_Test_Related "; //#TEST PARAMETERS
    public static final String PROC_67 = "fn_ITF_Test_Related_ID_4 "; //#TEST PARAMETERS
    public static final String PROC_68 = "fn_ITF_REcipesInTestOrder "; //#LAB DEVELOPMENT -> MATERIALINFORMATION
    public static final String PROC_69 = "fn_Proc_Tags_Values_Pruff "; //#LAB DEVELOPMENT -> TEST CONFIG
    public static final String PROC_70 = "fn_Proc_Tags_Values_Test "; //#LAB DEVELOPMENT -> TEST CONFIG
    public static final String PROC_71 = "fn_TestOrder_VULMET "; //#LAB DEVELOPMENT -> TEST CONFIG -> Preparationmethods
    public static final String PROC_72 = "fn_TestOrder_Aegement "; //#LAB DEVELOPMENT -> TEST CONFIG -> Agingmethods
    public static final String PROC_73 = "fn_TestOrder_VULMET "; //#LAB DEVELOPMENT -> TEST CONFIG -> Preparationmethods -> get chosen
    public static final String PROC_74 = "fn_TestOrder_Aegement "; //#LAB DEVELOPMENT -> TEST CONFIG -> Agingmethods -> get chosen
    public static final String PROC_75 = "fn_ITF_CPWORDER_Info "; //#LAB DEVELOPMENT -> TEST VARIABLES 
    public static final String PROC_76 = "fn_Proc_Tags_Values "; //#LAB DEVELOPMENT -> TEST VARIABLES  -> Show all testCodes
    public static final String PROC_77 = "prc_ITF_CPWORDER_INSERT "; //#LAB DEVELOPMENT -> TEST VARIABLES -> create new
    public static final String PROC_78 = "fn_ITF_CPWORDER_Info "; //#LAB DEVELOPMENT -> TEST VARIABLES -> check exist
    public static final String PROC_79 = "prc_ITF_CPWORDER_INSERT_Table "; //#LAB DEVELOPMENT -> MATERIALINFORMATION -> ADD MATERIAL
    public static final String PROC_80 = "prc_ITF_CPWORDER_Delete "; //#LAB DEVELOPMENT -> FIND ORDER -> DELETE ORDER
    public static final String PROC_81 = "prc_ITF_CPWORDER_Materials_Delete "; //#LAB DEVELOPMENT -> MATERIALINFORMATION -> DELETE MATERIAL, HelpA.runProcedureIntegerReturn_A_2(sql, q)
    public static final String PROC_82 = "prc_ITF_MCCPTproc_INSERT_Procedure "; //#LAB DEVELOPMENT -> TEST PROCEDURE -> CREATE NEW
    public static final String PROC_83 = "prc_ITF_MCCPTproc_Copy_From "; //#LAB DEVELOPMENT -> TEST PROCEDURE -> COPY
    public static final String PROC_84 = "prc_ITF_MCCPTproc_Delete_Procedure "; //#LAB DEVELOPMENT -> TEST PROCEDURE -> DELETE ENTRY
    public static final String PROC_85 = "prc_ITF_CPWORDER_CopyFrom "; //#LAB DEVELOPMENT -> FIND ORDER -> COPY
    public static final String PROC_86 = "fn_ITF_OrdersWithMaterials "; //#LAB DEVELOPMENT -> FIND ORDER -> FILL TABLE BY MATERIAL
    public static final String PROC_87 = "prc_ITF_ MCcpwotest_Copy "; //#LAB DEVELOPMENT -> MATERIAL INFO
    public static final String PROC_88 = "CreateRecipesCompareTestMOD1 "; //[#COMPARE-RECIPES-2021#]
    //
    // LAB_DEVELOPMENT  
    //
    //PROD_PLAN
    public static final String PROC_P_01 = "fn_Sequence_Get ";
    public static final String PROC_P_02 = "DELETE_CREATE_ALL_RECIPENew ";// HelpA.runProcedureIntegerReturn_A
    public static final String PROC_P_03 = "generate_CSVColumn_1 ";
    public static final String PROC_P_04 = "generate_Empty_CSVColumn ";

    static {
        procList.add(PROC_01);
        procList.add(PROC_02);
        procList.add(PROC_03);
        procList.add(PROC_04);
        procList.add(PROC_05);
        procList.add(PROC_06);
        procList.add(PROC_06);
        procList.add(PROC_08);
        procList.add(PROC_09);
        procList.add(PROC_10);
        procList.add(PROC_11);
        procList.add(PROC_12);
        procList.add(PROC_13);
        procList.add(PROC_14);
        procList.add(PROC_15);
        procList.add(PROC_16);
        procList.add(PROC_17);
        procList.add(PROC_18);
        procList.add(PROC_19);
        procList.add(PROC_20);
        procList.add(PROC_21);
        procList.add(PROC_22);
        procList.add(PROC_23);
        procList.add(PROC_24);
        procList.add(PROC_25);
        procList.add(PROC_26);
        procList.add(PROC_27);
        procList.add(PROC_28);
        procList.add(PROC_29);
        procList.add(PROC_30);
        procList.add(PROC_31);
        procList.add(PROC_32);
        procList.add(PROC_33);
        procList.add(PROC_34);
        procList.add(PROC_35);
        procList.add(PROC_36);
        procList.add(PROC_37);
        procList.add(PROC_38);
        procList.add(PROC_39);
        procList.add(PROC_40);
        procList.add(PROC_41);
        procList.add(PROC_42);
        procList.add(PROC_43);
        procList.add(PROC_44);
        procList.add(PROC_45);
        procList.add(PROC_46);
        procList.add(PROC_47);
        procList.add(PROC_48);
        procList.add(PROC_49);
        procList.add(PROC_50);
        procList.add(PROC_51);
        procList.add(PROC_52);
        procList.add(PROC_53);
        procList.add(PROC_54);
        procList.add(PROC_55);
        procList.add(PROC_56);
        procList.add(PROC_57);
        procList.add(PROC_58);
        procList.add(PROC_59);
        procList.add(PROC_60);
        procList.add(PROC_61);
        procList.add(PROC_62);
        procList.add(PROC_63);
        procList.add(PROC_64);
        procList.add(PROC_65);
        procList.add(PROC_66);
        procList.add(PROC_67);
        procList.add(PROC_68);
        procList.add(PROC_69);
        procList.add(PROC_70);
        procList.add(PROC_71);
        procList.add(PROC_72);
        procList.add(PROC_73);
        procList.add(PROC_74);
        procList.add(PROC_75);
        procList.add(PROC_76);
        procList.add(PROC_77);
        procList.add(PROC_78);
        procList.add(PROC_79);
        procList.add(PROC_80);
        procList.add(PROC_81);
        procList.add(PROC_82);
        procList.add(PROC_83);
        procList.add(PROC_84);
        procList.add(PROC_85);
        procList.add(PROC_86);
        procList.add(PROC_87);
        procList.add(PROC_88);
        //
        procList.add(PROC_P_01);
        procList.add(PROC_P_02);
        procList.add(PROC_P_03);
        procList.add(PROC_P_04);
    }

}
