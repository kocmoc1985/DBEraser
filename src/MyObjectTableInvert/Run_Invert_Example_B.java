/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MCCompound.PROD_PLAN;
import MyObjectTable.CommonControllsPanel;
import MyObjectTable.OutPut;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import forall.HelpA_;
import static forall.HelpA_.run_application_with_associated_application;
import forall.Sql_B;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author mcab
 */
public class Run_Invert_Example_B implements MouseListener {

    private static Table table;
    private Border PREV_BORDER;
    private final Sql_B sql = new Sql_B(false, false);

    public void go() {
        //
        final JFrame table_container_frame = new JFrame("test");
        table_container_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table_container_frame.setLayout(new BorderLayout());
        table_container_frame.setSize(new Dimension(600, 400));
        //
        final ShowMessage sm = new OutPut();
        //
        final JPanel container = new JPanel(new BorderLayout());
        //

        //
        try {
            sql.connect_mdb_java_8("", "", "example.mdb");
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert_Example_B.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        final TableBuilderInvert tableBuilder = new TableBuilderInvert(sm, sql, getConfigTableInvert(), false, "");
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                    //
                    //
                    table = tableBuilder.buildTable_B(null);
                    CommonControllsPanel ccp = new CommonControllsPanel((TableInvert) table);
                    //
                    JButton button = new JButton("TEST");
                    //
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tableInvertToCSV(table, 1, getConfigTableInvert(), true); // OBS! using 2 if units enabled 1 otherwise [2020-07-10]
                        }
                    });
                    //
                    container.add(button,BorderLayout.BEFORE_FIRST_LINE);
//                    container.add(ccp, BorderLayout.BEFORE_FIRST_LINE); // BEFORE_FIRST_LINE
                    container.add(table.getTable());
                    //
                    //
                    table_container_frame.add(container);
                    table_container_frame.setVisible(true);
                    //
            }
        });

        //
        //
        //
//                addMouseListenerToAllComponentsOfComponent((JComponent) table.getTable(), this);
//        addMouseListenerToAllComponentsOfComponent(container, this);
        //
    }

    private RowDataInvert[] getConfigTableInvert() {
        //
        //Show 1 parameter in a "JTextField"
        RowDataInvert order = new RowDataInvert("", "", false, "", "ORDER", "KM/H", true, true, false);
        //
        RowDataInvert batch = new RowDataInvert("main_table", "batch_id", false, "batch_nr", "BATCH", "", true, true, false);
        //
        String fixedComboValues = "admin,user,useradvanced,developer";
        RowDataInvert fixdValTest = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues, null, "", "", "", false, "test", "TEST", "", true, true, false);
        fixdValTest.enableFixedValues();
        //
        //
        String q_5 = MCRecipe.SQL_B.basic_combobox_query_double_param("recipe_id", "batch_id", "main_table");
        RowDataInvert line = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql, "", "", "", false, "line_nr", "LINE", "", true, true, false);
        line.enableComboBoxMultipleValue();
        //
        String q_6 = MCRecipe.SQL_B.basic_combobox_query("duration", "main_table");
        RowDataInvert duration = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_6, sql, "", "main_table", "batch_id", false, "duration", "DURATION", "", true, true, false);
        //
        RowDataInvert[] rows = {
            fixdValTest,
            order,
            batch,
            //            recipe,
            line,
            duration};
        //
        return rows;
    }

    public String tableInvertToCSV(Table table_invert, int startColumn, RowDataInvert[] rdi, boolean writeToFile) {
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        String csv = "";
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            if (dataInvert.getVisible() == false) {
                continue;
            }
            //
            csv += dataInvert.getFieldNickName() + ";";
            //
            if (dataInvert.getUnit() instanceof String) {
                String unit = (String) dataInvert.getUnit();
                //
                if (unit.isEmpty() == false) {
                    csv += unit + ";";
                } else {
                    csv += "unit" + ";";
                }
                //
            }
            //
            for (int x = startColumn; x < getColumnCount(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                csv += columnValue.getValue() + ";";
                // 
            }
            //
            csv += "\n";
            //
        }
        //
//        System.out.println("CSV: \n" + csv);
        //
        //
        String path = HelpA_.get_desktop_path() + "\\"
                + HelpA_.get_proper_date_time_same_format_on_all_computers_err_output() + ".csv";
        //
        if (writeToFile) {
            try {
                HelpA_.writeToFile(path, csv);
//                JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
                run_application_with_associated_application(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        return csv;
    }

    public int getColumnCount(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        return tableInvert.getColumnCount();
    }

    public static void main(String[] args) {
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //
        Run_Invert_Example_B invert = new Run_Invert_Example_B();
        invert.go();
    }

    private void addMouseListenerToAllComponentsOfComponent(JComponent c, MouseListener ml) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            component.addMouseListener(this);
            if (component instanceof JComponent) {
                addMouseListenerToAllComponentsOfComponent((JComponent) component, ml);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        String str = "SOURCE ELEM: " + me.getSource();
        System.out.println(str);
        System.out.println(str);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() instanceof JComponent) {
            JComponent jc = (JComponent) me.getSource();
            PREV_BORDER = jc.getBorder();
            jc.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (me.getSource() instanceof JComponent) {
            JComponent jc = (JComponent) me.getSource();
            jc.setBorder(PREV_BORDER);
        }
    }
}
