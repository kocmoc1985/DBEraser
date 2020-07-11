/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MCCompound.PROD_PLAN;
import MyObjectTable.CommonControllsPanel;
import MyObjectTable.OutPut;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import forall.Sql_B;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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
public class Run_Invert_Example_C extends Basic implements MouseListener {

    private Border PREV_BORDER;
    private final Sql_B sql = new Sql_B(false, false);
    private CommonControllsPanel ccp;
    private final JButton button = new JButton("TEST");
    private final JPanel container = new JPanel(new BorderLayout());
    private final JFrame table_container_frame = new JFrame("test");
    private TableBuilderInvert tableBuilder;

    public Run_Invert_Example_C() {
        initOther();
    }

    private void initOther() {
        //
        table_container_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table_container_frame.setLayout(new BorderLayout());
        table_container_frame.setSize(new Dimension(600, 400));
        //
        final ShowMessage sm = new OutPut();
        //

        //
        try {
            sql.connect_mdb_java_8("", "", "example.mdb");
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert_Example_C.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        tableBuilder = new TableBuilderInvert(sm, sql, getConfigTableInvert(), false, "");
        //
        ccp = new CommonControllsPanel((TableInvert) TABLE_INVERT);
        //
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableInvertToCSV(TABLE_INVERT, 1, getConfigTableInvert(), true); // OBS! using 2 if units enabled 1 otherwise [2020-07-10]
            }
        });
    }

    public void go() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                TABLE_INVERT = tableBuilder.buildTable_B();
                //
                container.add(TABLE_INVERT.getTable());
                //
                table_container_frame.add(ccp, BorderLayout.PAGE_START);
                //
                table_container_frame.add(container);
                //
                table_container_frame.add(button, BorderLayout.PAGE_END);
                //
                table_container_frame.setVisible(true);
                //
                initializeSaveIndicators();
                //
            }
        });

        //
        //
//                addMouseListenerToAllComponentsOfComponent((JComponent) table.getTable(), this);
//        addMouseListenerToAllComponentsOfComponent(container, this);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
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

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>
        //
        Run_Invert_Example_C invert = new Run_Invert_Example_C();
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

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(ccp.getSaveButton(), this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        //
        if (nr == 1) {
            if (TABLE_INVERT == null) {
                return false;
            } else if (TABLE_INVERT.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
        }
        //
        return false;
    }
}
