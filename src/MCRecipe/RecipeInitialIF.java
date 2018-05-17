/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.CompareRecipes;
import forall.JComboBoxM;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public interface RecipeInitialIF {

    public boolean checkIfRecipeDisabled();

    public void delete_record_table_2_or_3(JTable table, int row_index, String tableName, String idColumnName);

    public void clearBoxes();

    public void clearBoxesB();

    public void fill_table_1(String recipe_code, String release, String mixer_code, ActionEvent event);

    public void table1Repport();

    public void setCompareRecipes(CompareRecipes compareRecipes);
    
    public void dropCompareTable();
    
    public void addToCompare();
    
    public void showComparedRecipes();
    
    public void remoweAllFromCompare();
    
    public void delete_record_table_1(JTable table);
    
    public void fillNotes();
    
    public void fill_table_2_and_3();
    
    public void fillComboBoxMultiple(final JComboBox box, final String colName, final String colName2);
    
    public void fillComboBox(final JComboBox box, final String colName);
    
    public void fillComboBoxIngredients_with_wait(final JComboBoxM box, final String colName);
    
    public void fillComboBoxB(final JComboBox box, final String colName);
    
    public void showIngredInfoOnValueChange(String ingredName);
    

}
