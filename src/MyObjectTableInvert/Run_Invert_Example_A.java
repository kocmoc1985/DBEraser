/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.CommonControllsPanel;
import MyObjectTable.OutPut;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import forall.Sql_B;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author mcab
 */
public class Run_Invert_Example_A implements MouseListener {

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
            Logger.getLogger(Run_Invert_Example_A.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        final TableBuilderInvert tableBuilder = new TableBuilderInvert(sm, sql, getConfigTableInvert(), true, "");
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                try {
                    //
                    //
                    table = tableBuilder.buildTable("select * from main_table where batch_id = 155");
                    CommonControllsPanel ccp = new CommonControllsPanel((TableInvert) table);
                    container.add(ccp, BorderLayout.BEFORE_FIRST_LINE);//BEFORE_FIRST_LINE
                    container.add(table.getTable());
                    //
                    //
                    table_container_frame.add(container);
                    table_container_frame.setVisible(true);
                    //
                } catch (SQLException ex) {
                    Logger.getLogger(Run_Invert_Example_A.class.getName()).log(Level.SEVERE, null, ex);
                    sm.showMessage(ex.toString());
                }
                //
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
        //Show 1 parameter in a "JTextFiel"
        RowDataInvert order = new RowDataInvert("main_table", "batch_id", false, "order_id", "ORDER", "KM/H", true, true, false);
        //
        RowDataInvert batch = new RowDataInvert("main_table", "batch_id", false, "batch_nr", "BATCH", "", true, true, false);
        //
        String fixedComboValues = "admin,user,useradvanced,developer";
        RowDataInvert fixdValTest = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues, null, "", "main_table", "batch_id", false, "line_nr", "LINE", "", true, true, false);
        fixdValTest.enableFixedValues();
        //
//        String q_4 = MCRecipe.SQL_B.basic_combobox_query("recipe_id", "main_table");
//        RowDataInvert recipe = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql, "", "main_table", "batch_id", false, "recipe_id", "RECIPE", "", true, true, false);
        //
        String q_5 = MCRecipe.SQL_B.basic_combobox_query_double_param("recipe_id", "batch_id", "main_table");
        RowDataInvert line = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql, "", "main_table", "batch_id", false, "line_nr", "LINE", "", true, true, false);
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
        Run_Invert_Example_A invert = new Run_Invert_Example_A();
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
