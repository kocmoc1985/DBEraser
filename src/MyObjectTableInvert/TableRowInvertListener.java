/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public interface TableRowInvertListener {

    public void mouseClicked(MouseEvent me,int column,int row,String tableName,TableInvert tableInvert);
}
