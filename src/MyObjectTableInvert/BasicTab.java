/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import LabDev.Validator_MCR;
import MCRecipe.MC_RECIPE;
import static MCRecipe.MC_RECIPE.USER_ROLE;
import static MCRecipe.MC_RECIPE.USER_ROLES_ADMIN_DEVELOPER_ACCESS;
import MCRecipe.SQL_B;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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

    public BasicTab() {
        this.OUT = null;
        this.sql = null;
        this.sql_additional = null;
    }
    
    public BasicTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT) {
        this.OUT = OUT;
        this.sql = sql;
        this.sql_additional = sql_additional;
    }
    
    public void saveChangesTableInvert__no_check(Table tableInvert){
        super.saveChangesTableInvert(tableInvert);
    }
    
    @Override
    public void saveChangesTableInvert() {
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return;
        }
        //
        TableInvert ti = (TableInvert) TABLE_INVERT;
        //
        automaticFieldUpdate(TABLE_INVERT);
        //
        ti.applyChanges();
        //
    }
    
    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        //OBS! IS ENABLED ON DOUBLE CLICK
        enableEditingLongValues(me, ti);
        //
        trimValueTableInvert(col_name, ti);
        //
    }

    
    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        //
        //AUTOMATIC **SQL** INPUT LENGTH VALIDATION [2020-11-22]
        Validator_MCR.validateMaxInputLengthAutomatic(sql, jli);
        //
        //
        //
        if (jli.getValidateDate()) {
            //
            Validator_MCR.validateDate(jli);
            //
        } //Manual input length validation below. YES IS USED [2020-11-24]
        else if (jli.getInputLengthValidationManual() > 0) {
            //[$TEST-VAR-SAVE$] -> this TAG here, does not mean that the functionality below only belongs to this TAG
            JTextFieldInvert jtf = (JTextFieldInvert) jli;
            //
            int inputLength = jtf.getInputLengthValidationManual();
            //
            if (inputLength > 0) {
                Validator_MCR.validateMaxInputLength(jli, inputLength);
            }
            //
        }
        //
    }
    
    @Override
    public void saveChangesTableInvert(Table tableInvert) {
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return;
        }
        //
        TableInvert ti = (TableInvert) tableInvert;
        //
        automaticFieldUpdate(tableInvert);
        //
        ti.applyChanges();
        //
    }
    
    public abstract void initializeSaveIndicators();
    
    public void save_changes_notes(
            JScrollPane jScrollPane,
            JEditorPane editorPane,
            JTable table,
            JTextArea jTextArea,
            String idColumnName) throws BadLocationException {
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
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
