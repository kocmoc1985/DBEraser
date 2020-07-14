/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.RowData;
import MyObjectTable.Table;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * For the case when SQL is NOT used [2020-07-14]
 *
 * @author MCREMOTE
 */
public class TableRowInvertB extends TableRowInvert {

    public TableRowInvertB(RowData rowColumnObjects, String database_id, int row_nr, int layout, Table table) {
        super(rowColumnObjects, database_id, row_nr, layout, table);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println("TableRowInvertB.class keyReleased(...)");
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        System.out.println("TableRowInvertB.class JComboBox itemStateChanged()");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //
        Object source = me.getSource();
        //
        if (isHeaderComponent(source)) {
            return;
        }
        //
        current_row_highlight(me.getSource());
        //
        TableInvert ti = (TableInvert) getTable();
        set_current_row__and__database_id(source);
        System.out.println("\n==============================");
        System.out.println("Value: " + getValueAt(ti.getCurrentColumn(source)));
        System.out.println("Row_nr: " + ti.getCurrentRow());
        System.out.println("Column_nr: " + ti.getCurrentColumn(source));
        System.out.println("Column name: " + ti.getCurrentColumnName(source));
        System.out.println("Column NickName: " + ti.getCurrentColumnNickName(source));
        System.out.println("InvertTableName: " + ti.getTABLE_NAME());
        //
        RowDataInvertB dataInvertB = (RowDataInvertB) ti.getRowConfig(ti.getCurrentRow()-1); 
        //
        System.out.println("");
//        System.out.println("attr. important: " + dataInvertB.fieldNickName);
        System.out.println("attr. important: " + dataInvertB.getImportant());
        System.out.println("attr. visible: " + dataInvertB.getVisible());
        System.out.println("attr. additionalInfo: " + dataInvertB.additionalInfo);
        System.out.println("attr. initialValue: " + dataInvertB.getInitialValue());
        System.out.println("attr. editable: " + dataInvertB.isEditable());

        System.out.println("==============================");
        //
        //

    }

}
