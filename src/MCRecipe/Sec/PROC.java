/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

/**
 *
 * @author mcab
 */
public class PROC {

    public static final String PROC_01 = "Create_Recipe_Tempory_USER_Compare ";//#RECIPE INITIAL
    public static final String PROC_02 = "fn_Lab_REsults "; //#RECIPE ADDITIONAL
    public static final String PROC_03 = "fn_Lab_SelectOrders "; //#RECIPE ADDITIONAL
    public static final String PROC_04 = "fn_Lab_Procedures ";//#RECIPE ADDITIONAL
    public static final String PROC_05 = "prc_ITF_Vendor_Insert ";//#VENDORS
    public static final String PROC_06 = "fn_Recipe_Ingredient_Insert ";//#RECIPE DETAILED
    public static final String PROC_07 = "prc_ITF_RECIPE_FreeInfo_Insert ";//#RECIPE DETAILED
    public static final String PROC_08 = "prc_ITF_RECIPE_FreeTEXT_Insert ";//#RECIPE DETAILED
    public static final String PROC_09 = "prc_ITF_RECIPE_FreeInfo_Update ";//#RECIPE DETAILED
    public static final String PROC_10 = "prc_ITF_Ingredient_NEW_fromScratch ";//#INGREDIENTS
    public static final String PROC_11 = "prc_ITF_Ingredient_NEW_Insert ";//#INGREDIENTS
    public static final String PROC_12 = "prc_ITF_RECIPE_Scratch ";//#RECIPE DETAILED
    public static final String PROC_13 = "prc_ITF_RECIPE_main_Insert ";//#RECIPE DETAILED
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
    public static final String PROC_30 = "prc_ITF_Recipes_Z_Z "; //#RECIPE INITIAL
    public static final String PROC_31 = "prc_ITF_Recipes_Z_X "; //#RECIPE INITIAL
    public static final String PROC_32 = "prc_ITF_Recipes_Z_X_OR "; //#RECIPE INITIAL
    public static final String PROC_33 = "prc_ITF_Recipes_Z_A "; //#RECIPE INITIAL
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
    public static final String PROC_61 = "Recipe_Full_RZPT_1_O_W_User ";  //#RECIPE DETAILED
    public static final String PROC_62 = "User_Int_RecipeInvert "; //#RECIPE DETAILED
    public static final String PROC_63 = "fn_ITF_Recipes_Info "; //#RECIPE INITIAL
    public static final String PROC_64 = "fn_ITF_Recipes_TEXT "; //#RECIPE INITIAL

}
