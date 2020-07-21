/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import static MCRecipe.MC_RECIPE.USER_ROLE;
import static MCRecipe.MC_RECIPE.USER_ROLES_ADMIN_DEVELOPER_ACCESS;
import MCRecipe.SQL_B;
import MyObjectTable.ShowMessage;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.util.LinkedList;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author KOCMOC
 */
public abstract class BasicTab extends Basic  {
    
    public final ShowMessage OUT;
    public final SqlBasicLocal sql;
    public final SqlBasicLocal sql_additional;
    public boolean notesUnsaved = false;
    
    public BasicTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT) {
        this.OUT = OUT;
        this.sql = sql;
        this.sql_additional = sql_additional;
    }
    
    public void save_changes_notes(
            JScrollPane jScrollPane,
            JEditorPane editorPane,
            JTable table,
            JTextArea jTextArea,
            String idColumnName) throws BadLocationException {
        //
        if (USER_ROLES_ADMIN_DEVELOPER_ACCESS.contains(USER_ROLE) == false) {
             HelpA.showActionDeniedUserRole(USER_ROLE);
             return;
        }
        //
        String comments = "";
        //
        String id = null;
        //
        //
        if (idColumnName.equals("IngredientCode_ID")) {
            id = getValueTableInvert(idColumnName, 2, TABLE_INVERT);
        } else if (idColumnName.equals("Recipe_Prop_Free_Text_ID")) {
            id = HelpA.getValueSelectedRow(table, idColumnName);
        }
        //
        //
        if (id == null || id.isEmpty()) {
            return;
        }
        //
        if (jScrollPane.getViewport().getComponent(0) instanceof JEditorPane) {
            javax.swing.text.Document document = editorPane.getDocument();
            comments = document.getText(0, document.getLength());
        } else if (jScrollPane.getViewport().getComponent(0) instanceof JTextArea) {
            comments = jTextArea.getText();
        }
        //
        //
        if (idColumnName.equals("IngredientCode_ID")) {
            //
            h01_ingred_comments(idColumnName, id, comments);
            //
        } else if (idColumnName.equals("Recipe_Prop_Free_Text_ID")) {
            //
            String query = SQL_B.IngredientsNotesPreparedStatementStringUpdate(idColumnName, id);
            //
            SQL_B.prepare_statement_save_changes_update(OUT, sql, query, comments, HelpA.updatedOn(), HelpA.updatedBy());
        }
        //
        notesUnsaved = false;
        //
        fillNotes();
        //
    }
    
    private void h01_ingred_comments(String idColumnName, String id, String comments) {
        String whereCondtion = idColumnName + " = " + id;
        int count = HelpA.getRowCount(sql, "Ingred_Comments", whereCondtion);
        boolean exists = count >= 1;
        //
        String query;
        //
        if (exists == false) {
            query = SQL_B.IngredientsNotesPreparedStatementStringInsert(idColumnName, id);
            //
            SQL_B.prepare_statement_save_changes_insert(OUT, sql, query, comments, id, HelpA.updatedOn(), HelpA.updatedBy());
            //
        } else {
            query = SQL_B.IngredientsNotesPreparedStatementStringUpdate(idColumnName, id);
            //
            SQL_B.prepare_statement_save_changes_update(OUT, sql, query, comments, HelpA.updatedOn(), HelpA.updatedBy());
        }
        
    }

    /**
     * This one is called when adding a new row to a table
     *
     * @param table
     * @param list
     */
    @Override
    public void addRow(JTable table, LinkedList<Integer> list) {
        HelpA.addRowJTable(table);
        //
        int added_row_nr = table.getRowCount();
        //
        list.add(added_row_nr - 1);
        //
        OUT.showMessage("Added new row: " + added_row_nr + " / Table: " + table.getName());
        //
    }

    /**
     * This is called when a editing of a table is done
     *
     * @param row_id
     * @param listChanged
     * @param listAdded
     * @param table
     */
    @Override
    public void addPotentiallyUnsavedEntries(int row_id, LinkedList<Integer> listChanged, LinkedList<Integer> listAdded, JTable table) {
        if (listChanged.contains(row_id) == false && listAdded.contains(row_id) == false) {
            listChanged.add(row_id);
            OUT.showMessage("Potentially unsaved row_id added: " + row_id + " / Table: " + table.getName());
        }
    }
    
    public abstract void fillNotes();

}
