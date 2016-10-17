/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MyObjectTable.ShowMessage;
import forall.SqlBasicLocal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class ChangeSaver {

    private final SqlBasicLocal sql;
    private final ShowMessage OUT;

    public ChangeSaver(SqlBasicLocal sql, ShowMessage OUT) {
        this.sql = sql;
        this.OUT = OUT;
    }

    public void saveChange(UpdateEntry entry) {
        String q = entry.getQuery();
        try {
            sql.execute(q,OUT);
        } catch (Exception ex) {
            Logger.getLogger(ChangeSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
