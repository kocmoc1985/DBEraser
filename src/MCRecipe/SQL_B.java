/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MyObjectTable.ShowMessage;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class SQL_B {
    
    public static void prepare_statement_save_changes_update(ShowMessage OUT, SqlBasicLocal sql, String query, String comments, String updatedOn, String updatedBy) {
        //
        String q1 = query;
        //
        OUT.showMessage(q1);
        //
        byte[] bytes = comments.getBytes();
        //
        //
        try {
            sql.prepareStatement(q1);
            //
            sql.getPreparedStatement().setBytes(1, bytes);
            sql.getPreparedStatement().setString(2, updatedOn);
            sql.getPreparedStatement().setString(3, updatedBy);
            //
            //
            sql.executeUpdatePreparedStatement();
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void prepare_statement_save_changes_insert(ShowMessage OUT, SqlBasicLocal sql, String query, String comments, String id, String updatedOn, String updatedBy) {
        //
        String q1 = query;
        //
        OUT.showMessage(q1);
        //
        byte[] bytes = comments.getBytes();
        //
        //
        if (q1.contains("Ingred_Comments")) {
            h_01(sql, query, id, bytes, updatedOn, updatedBy);
        } 
        //
    }
    
    
    
    private static void h_01(SqlBasicLocal sql, String query, String id, byte[] bytes, String updatedOn, String updatedBy) {
        try {
            sql.prepareStatement(query);
            //
            sql.getPreparedStatement().setInt(1, Integer.parseInt(id));
            sql.getPreparedStatement().setBytes(2, bytes);
            sql.getPreparedStatement().setString(3, updatedOn);
            sql.getPreparedStatement().setString(4, updatedBy);
            //
            //
            sql.executeUpdatePreparedStatement();
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String IngredientsNotesPreparedStatementStringInsert(String idColumnName, String id) {
        //
        String q1 = "";
        //
        if (idColumnName.equals("IngredientCode_ID")) {
            q1 = "insert into Ingred_Comments values (?,?,?,?)";
        } 
//        else if (idColumnName.equals("Recipe_Prop_Free_Text_ID")) {
//            q1 = "insert into Recipe_Prop_Free_Text values (?,?,?,?,?)";
//        }
        
        return q1;
    }
    
    public static String IngredientsNotesPreparedStatementStringUpdate(String idColumnName, String id) {
        //
        String q1 = "";
        //
        if (idColumnName.equals("IngredientCode_ID")) {
            q1 = "update Ingred_Comments set "
                    + "Comments = ?" + ","
                    + "UpdatedOn = ?" + ","
                    + "UpdatedBy = ?" + ""
                    + " where IngredientCode_ID = " + id;
        } else if (idColumnName.equals("Recipe_Prop_Free_Text_ID")) {
            q1 = "update Recipe_Prop_Free_Text set "
                    + "NoteValue = ?" + ","
                    + "UpdatedOn = ?" + ","
                    + "UpdatedBy = ?" + ""
                    + " where Recipe_Prop_Free_Text_ID = " + id;
        }
        //
        return q1;
    }
    
    public static String getIngredCodeIdByName(String name) {
        return "select * from Ingredient_Code where Name =" + quotes(name, false);
    }
    
    private static String quotes(String str, boolean number) {
        if (number) {
            return str;
        } else {
            return "'" + str + "'";
        }
    }

    /**
     * phr, weight,container_nb, phase, mat_index, recipe_recipe_id
     *
     * @return
     */
    public static String saveChangesTable4(String param1, String param2, String param3, String param4, String param5, String param6) {
        String q = String.format("UPDATE Recipe_Recipe SET "
                + "PHR = '%s',"
                + "Weight = '%s',"
                + "SiloID = '%s',"
                + "Phase = '%s',"
                + "MatIndex = '%s' "
                + "WHERE Recipe_Recipe_ID = %s",
                param1, param2, param3, param4, param5, param6);
        return q;
    }
    
    public static String fill_containerNB_combo_box_table_4() {
        return "select distinct siloID from Recipe_Recipe\n"
                + "where siloID is not NULL\n"
                + "AND siloID <> ''\n"
                + "order by siloID asc";
    }
    
    public static String fill_ingredients_combo_box_for_table_4() {
        return "SELECT distinct [Name] from Ingredient_Code\n"
                + "UNION\n"
                + "SELECT distinct Code FROM Recipe_Prop_Main\n"
                + "order by [Name] asc";
    }
    
    public static String get_recipe_sequence_main_id(String code, String release, String mixer_code) {
        return "select Recipe_Sequence_Main_ID from Recipe_Sequence_Main "
                + "where Code ='" + code + "'"
                + " and Release ='" + release + "'"
                + " and Mixer_Code ='" + mixer_code + "'";
    }

    /**
     * @deprecated @param x
     * @return
     */
    public static String RecipeInitial_RecipeOrigin_RecipeStage_Comboboxes(int x) {
        return "SELECT DISTINCT TOP (100) PERCENT RIGHT(RTRIM(Code)," + x + ")" + "AS Recipe_origin\n"
                + "FROM dbo.Recipe_Prop_Main\n"
                + "ORDER BY RIGHT(RTRIM(Code)," + x + ")";
    }
    
    public static String basic_combobox_query(String parameter, String tableName) {
        return "SELECT DISTINCT [" + parameter  + "] FROM " + tableName
                + " ORDER BY [" + parameter + "] ASC";
    }
    
    public static String basic_combobox_query_double_param(String parameter1, String parameter2, String tableName) {
        return "SELECT DISTINCT " + parameter1 + "," + parameter2
                + " FROM " + tableName
                + " ORDER BY " + parameter1 + " ASC";
    }
    
    public static void main(String[] args) {
        System.out.println("" + basic_combobox_query_double_param("Code", "Name", "Mixer_InfoBasic"));
    }
    
    public static String delete_record_table_4(String recipe_recipe_id) {
        return "DELETE FROM Recipe_Recipe "
                + "WHERE Recipe_Recipe_ID = " + recipe_recipe_id;
    }
    
    public static String delete_record_table_1(String recipeId, int recipeSequenceMainId) {
        return "Delete from Recipe_Recipe where Recipe_Recipe_ID = " + recipeId + ";\n"
                + " Delete from Recipe_Sequence_Steps where Recipe_Sequence_Main_ID = " + recipeSequenceMainId + ";\n"
                + " Delete from Recipe_Sequence_Main where Recipe_Sequence_Main_ID = " + recipeSequenceMainId + ";\n"
                + " Delete from Recipe_Prop_Free_Text where Recipe_ID = " + recipeId + ";\n"
                + " Delete from Recipe_Prop_Free_Info where Recipe_ID = " + recipeId + ";\n"
                + " Delete from Recipe_Prop_Main where Recipe_ID = " + recipeId;
    }
    
    public static String delete_record_table_2_or_3(String id, String tableName, String idColumnName) {
        return "delete from " + tableName
                + " where " + idColumnName + "=" + id;
    }
}
