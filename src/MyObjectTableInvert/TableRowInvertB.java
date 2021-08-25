/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import BuhInvoice.GP_BUH;
import MyObjectTable.RowData;
import MyObjectTable.Table;
import forall.HelpA;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;

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
    protected void setTrackingToolTip(HeaderInvert hi, JLabel label) {
        if (GP_BUH.TRACKING_TOOL_TIP_ENABLED) {
            if (hi.getTableName() != null) {
                HelpA.setTrackingToolTip_b(label, hi.getRealColName() + " / " + hi.getTableName());
            } else {
                HelpA.setTrackingToolTip_b(label, hi.getRealColName());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //
        TableInvert t = (TableInvert) getTable();
        //
        Basic consumer = t.getTableInvertConsumer();
        consumer.mouseClickedForward(me, t.getCurrentColumn(me.getSource()), t.getCurrentRow(), t.getTABLE_NAME(), t);
        //
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        TableInvert ti = (TableInvert) getTable();
        Basic consumer = ti.getTableInvertConsumer();
        //
        if (consumer != null) {
            consumer.mouseWheelForward(ti, e);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //
//        System.out.println("TableRowInvertB.class keyReleased(...)");
        //
        TableInvert ti = (TableInvert) getTable();
        Basic consumer = ti.getTableInvertConsumer();
        //
        if (consumer != null) {
            consumer.keyReleasedForward(ti, ke);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        //
//        System.out.println("TableRowInvertB.class JComboBox itemStateChanged()");
        //
        TableInvert ti = (TableInvert) getTable();
        Basic consumer = ti.getTableInvertConsumer();
        //
        if (consumer != null) {
            consumer.jComboBoxItemStateChangedForward(ti, ie);
        }
    }
    
     @Override
    public void insertUpdate(DocumentEvent e) {
        //This one is trigere uppon data is "paste" to the JTextField
        TableInvert ti = (TableInvert) getTable();
        Basic consumer = ti.getTableInvertConsumer();
        //
        if (consumer != null) {
            JLinkInvert jli = (JLinkInvert) e.getDocument().getProperty("owner");
            String col_name = ti.getCurrentColumnName(jli);
            consumer.jTextFieldPasteEventForward(ti, e, jli,col_name);
        }
        //
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Might be useful for the future 
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // Might be useful for the future
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
        RowDataInvertB dataInvertB = (RowDataInvertB) ti.getRowConfig(ti.getCurrentRow() - 1);
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
