/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.RowData;
import MyObjectTable.Table;
import MyObjectTable.TableRow;
import forall.HelpA_;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mcab
 */
public class TableRowInvert extends TableRow implements KeyListener, MouseWheelListener, ItemListener {

    private ArrayList<TableRowInvertListener> tableRowListenerList = new ArrayList<TableRowInvertListener>();

    public TableRowInvert(RowData rowColumnObjects, String database_id, int row_nr, int layout, Table table) {
        super(rowColumnObjects, database_id, row_nr, layout, table);
        gridLayoutFix();
    }

    public void addTableRowInvertListener(TableRowInvertListener tril) {
        tableRowListenerList.add(tril);
    }

    /**
     * OBS! This one fixes the empty space on the both sides of a row
     */
    private void gridLayoutFix() {
        if (this.getLayout() instanceof GridLayout) {
            GridLayout gridLayout = (GridLayout) this.getLayout();
            gridLayout.setHgap(5);
        }
    }

    @Override
    public RowDataInvert getRowConfig() {
        return (RowDataInvert) ROW_COLUMN_DATA;
    }

    protected void setTrackingToolTip(HeaderInvert hi, JLabel label) {
        if (hi.getTableName() != null) {
            HelpA_.setTrackingToolTip(label, hi.getRealColName() + " / " + hi.getTableName());
        } else {
            HelpA_.setTrackingToolTip(label, hi.getRealColName());
        }

    }

    @Override
    protected void addColumn(Object obj) {
        Component add_component = null;
        //
        TableInvert table_invert = (TableInvert) getTable();
        //
        if (obj instanceof HeaderInvert) {
            HeaderInvert hi = (HeaderInvert) obj;
            //
            if (hi.isUnitHeader() == false) {
                JLabel label;
                //
                if (getRowConfig().getImportant()) {
                    label = new JLabel("<html><p style='margin-left:5px;font-weight:bold'>" + hi.getHeader() + " *" + "</p></html>");
                } else {
                    label = new JLabel("<html><p style='margin-left:5px;font-weight:bold'>" + hi.getHeader() + "</p></html>");
                }
                //
//                HelpA_.setTrackingToolTip(label, hi.getRealColName() + " / " + hi.getTableName());
                setTrackingToolTip(hi, label);
                //
                add_component = label;
                addComponent(add_component);
            } else {
                if (hi.getHeader() instanceof String) {
                    JLabel label = new JLabel("<html><p style='margin-left:5px;font-weight:bold;color:gray'>" + hi.getHeader() + "</p></html>");
                    add_component = label;
                    addComponent(add_component);
                } else if (hi.getHeader() instanceof JLabel) {
                    JLabel label = (JLabel) hi.getHeader();
                    add_component = label;
                    addComponent(add_component);
                } else if (hi.getHeader() instanceof JPanel) {
                    JPanel panel = (JPanel) hi.getHeader();
                    add_component = panel;
                    addComponent(add_component);
                }
            }
            //

        }

        if (obj instanceof ColumnDataEntryInvert) {
            //
            ColumnDataEntryInvert cde = (ColumnDataEntryInvert) obj;
            //
            if (cde.getObject() instanceof JLinkInvert) {
                JLinkInvert jpi = (JLinkInvert) cde.getObject();
                jpi.setParentObj(this);
            }
            //
            if (cde.getObject() instanceof String) {
                //
                JTextFieldInvert jtf = new JTextFieldInvert((String) cde.getObject());
                //
                jtf.setChildObject(cde);
                jtf.setParentObj(this); // *************** setting separately here!
                //
                cde.setParent(jtf);// [2020-08-03]
                //
                jtf.setMargin(new Insets(5, 5, 5, 5));
                //
                if (getRowConfig().getImportant()) {
                    jtf.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                }
                //
                if (getRowConfig().isEditable() == false) {
                    jtf.setEditable(false);
                }
                //
                if (getRowConfig().isEnabled() == false) {
                    jtf.setEnabled(false);
                }
                //
                if (getRowConfig().toolTipTextEnabled()) {
                    jtf.setToolTipText((String) cde.getObject());
                }
                //
                if(getRowConfig().toolTipFixedTextPresent()){
                    jtf.setToolTipText(getRowConfig().getToolTipFixedText());
                }
                //
                add_component = jtf;
                //
                addComponent(add_component);
                add_component.addKeyListener(this);
                //
            } else if (cde.getObject() instanceof JButtonInvert) {
                add_component = (Component) cde.getObject();
                addComponent(add_component);
            } else if (cde.getObject() instanceof JComboBoxInvert) {
                add_component = (Component) cde.getObject();
                addComponent(add_component);
                JComboBox box = (JComboBox) add_component;
                box.addItemListener(this);
                //
                if (getRowConfig().isEditable() == false) {
                    box.setEditable(false);
                }
                //
                if (getRowConfig().isEnabled() == false) {
                    box.setEnabled(false);
                }
                //
                if (getRowConfig().getImportant()) {
                    Font f = box.getFont();
                    box.setFont(new Font(f.getFamily(), Font.BOLD, f.getSize() + 1));
//                    box.setFont(new Font("SansSerif", Font.BOLD, 20));
                }
                //
            }else if (cde.getObject() instanceof JPassWordFieldInvert) {
                add_component = (Component) cde.getObject();
                addComponent(add_component);
                cde.setParent(add_component);
                add_component.addKeyListener(this);
            }
            //
            table_invert.row_col_object__column_count__map.put(add_component, COLUMN_COUNT);
            table_invert.row_col_object__db_id__map.put(add_component, cde.getDatabase_id());
            table_invert.row_col_object__column_name__map.put(add_component, cde.getOriginalColumn_name());
            table_invert.row__col_name__tablerowinvert_map.put(cde.getOriginalColumn_name(), this);
            table_invert.row_col_object__column_nick_name__map.put(add_component, cde.getColumn_nick_name());
            table_invert.row___col_object__map.put(add_component, this);
            table_invert.col_name__row_nr__map.put(cde.getOriginalColumn_name(), ROW_NR);
        }

        if (add_component != null) {
            add_component.addMouseListener(this);
            //
            add_component.addMouseWheelListener(this);
            //
            JComponent component = (JComponent) add_component;
            component.addAncestorListener(this);
        }
        //

        //        
        COLUMN_COUNT++;
//        System.out.println("COLUMN_COUNT:" + COLUMN_COUNT);
    }

    public String getTableNameDataBase() {
        return getRowConfig().getTableName();
    }

    protected void set_current_row__and__database_id(Object source) {
        Table t = getTable();
        t.setCurrentRow(ROW_NR);
        t.setCurrentDatabaseId("" + t.row_col_object__db_id__map.get((Component) source));
//        System.out.println("selected_row: " + t.getCurrentRow() + " / database_id: " + t.getCurrentDatabaseId());
    }

    protected boolean isHeaderComponent(Object source) {
        if (getTable().row_col_object__db_id__map.containsKey((Component) source)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getComponentAt(int column_index) {
        return this.getComponent(column_index);
    }

    @Override
    public String getValueAt(int column_index) {
        Component c = this.getComponent(column_index);
        //
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            return label.getText();
        } else if (c instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) c;
            return HelpA_.getComboBoxSelectedValue(comboBox);
        } else if (c instanceof JTextField) {
            JTextField jtf = (JTextField) c;
            return (String) jtf.getText();
        } else if (c instanceof JButton) {
            JButton jb = (JButton) c;
            return (String) jb.getText();
        } else {
            return null;
        }
    }
    
    public String getValueAtJComboBox(int column_index,int paramToReturn) {
        Component c = this.getComponent(column_index);
        //
         if (c instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) c;
            return HelpA_.getComboBoxSelectedValue(comboBox,paramToReturn);
        } else {
            return null;
        }
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
        System.out.println("DB_ID: " + ti.getCurrentDatabaseId());
        System.out.println("Column name: " + ti.getCurrentColumnName(source));
        System.out.println("Column NickName: " + ti.getCurrentColumnNickName(source));
        System.out.println("TABLE_NAME: " + getTableNameDataBase());// OBS!
        System.out.println("==============================");
        //
        //
        Object col_nr = ti.getCurrentColumn(source);
        //
        if (col_nr == null) {
            return;
        }
        //
        ArrayList table_fields_list = ti.getComponentsWithColNr((Integer) col_nr);
        //
        for (Object field : table_fields_list) {
            //
            if (field instanceof JTextField == false) {
                break;
            }
            //
            JTextField jtf = (JTextField) field;
            //
//            System.out.println("value:" + jtf.getText());
            //
            //
//            TableRowInvert tri = (TableRowInvert) ti.row___col_object__map.get(field);
            TableRowInvert tri = ti.getTableRowInvertByComponent(field);
            RowDataInvert rcdi = tri.getRowConfig();
//            RowDataInvert rcdi_2 = ti.getRowConfig(1);
            //
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        TableInvert t = (TableInvert) getTable();
        for (TableRowInvertListener tril : tableRowListenerList) {
            tril.mouseClicked(me, t.getCurrentColumn(me.getSource()), t.getCurrentRow(), t.getTABLE_NAME(), t);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        Object source = ke.getSource();
        //
        add_to_unsaved(source);
        //
    }

    public void add_to_unsaved(Object source) {
        TableInvert table = (TableInvert) getTable();
//        String db_id = table.getCurrentDatabaseId();
        String db_id = table.row_col_object__db_id__map.get((Component) source);
        set_current_row__and__database_id(source);
        //
        // OBS! OBS!
        HashMap unsaved_entries_map = getTable().unsaved_entries_map;
        //
        String col_name = table.getCurrentColumnName(source);
        //
        String tableName = getRowConfig().getTableName();
        String primaryOrForeignKeyName = getRowConfig().getPrimaryOrForeignKeyName();
        boolean isString = getRowConfig().isString();
        boolean keyIsString = getRowConfig().getKeyIsString();
        String updateOtherTablesBefore = getRowConfig().getUpdateOtherTablesBefore();
        int colNr = table.getCurrentColumn(source);
        //
        if (unsaved_entries_map.containsKey(source)) {
            unsaved_entries_map.remove(source);
            //
            UnsavedEntryInvert entryInvert_update = new UnsavedEntryInvert(source, tableName, primaryOrForeignKeyName, db_id, col_name, colNr, isString, keyIsString, updateOtherTablesBefore);
            unsaved_entries_map.put(source, entryInvert_update);
        } else {
            unsaved_entries_map.put(source, new UnsavedEntryInvert(source, tableName, primaryOrForeignKeyName, db_id, col_name, colNr, isString, keyIsString, updateOtherTablesBefore));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        Object source = ie.getSource();
        //
        add_to_unsaved(source);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
