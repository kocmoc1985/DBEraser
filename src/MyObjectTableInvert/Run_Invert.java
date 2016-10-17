/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MCCompound.SQL_B;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTable.CommonControllsPanel;
import MyObjectTable.CommonControllsPanel;
import MyObjectTableInvert.Invert_Tables_Config;
import MyObjectTable.Table;
import MyObjectTableInvert.TableInvert;
import MyObjectTable.OutPut;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
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
public class Run_Invert implements MouseListener {

    public static Table table;
    private Border PREV_BORDER;

    public void go() {
        //
        JFrame table_container_frame = new JFrame("test");
        table_container_frame.setLayout(new BorderLayout());
        table_container_frame.setSize(new Dimension(600, 400));
        //
        ShowMessage sm = new OutPut();
        //
        JPanel container = new JPanel(new BorderLayout());
        //
        final TableBuilderInvert tableBuilder = new TableBuilderInvert(sm,null, Invert_Tables_Config.getConfigTest(),true,"");
        //
        try {
            table = tableBuilder.buildTable(SQL_B.select_query_1());
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert.class.getName()).log(Level.SEVERE, null, ex);
            sm.showMessage(ex.toString());
        }
        //
        //
        CommonControllsPanel ccp = new CommonControllsPanel((TableInvert) table);
        container.add(ccp, BorderLayout.BEFORE_FIRST_LINE);//BEFORE_FIRST_LINE
        //
        //
        container.add(table.getTable());
        //
        //
        table_container_frame.add(container);
        table_container_frame.setVisible(true);
        //
        //
//                addMouseListenerToAllComponentsOfComponent((JComponent) table.getTable(), this);
//        addMouseListenerToAllComponentsOfComponent(container, this);
        //
        
    }

    public static void main(String[] args) {
        Run_Invert invert = new Run_Invert();
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
