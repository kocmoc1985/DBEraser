/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MyObjectTable.ShowMessage;
import forall.SqlBasicLocal;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public abstract class ChkBoxItemListComponent extends LabDevTab {

    public static final int HEIGHT = 35;

//    public ChkBoxItemListComponent() {
//    }
    public ChkBoxItemListComponent(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment labDev) {
        super(sql, sql_additional, OUT, labDev);
    }

    public ArrayList<JPanelPrepM> getSelectedFromTable(JPanel tablePanel) {
        //
        ArrayList<JPanelPrepM> list = new ArrayList<>();
        //
        Component[] arr = tablePanel.getComponents();
        //
        for (Component component : arr) {
            //
            if (component instanceof JPanelPrepM) {
                //
                JPanelPrepM jp = (JPanelPrepM) component;
                //
                if (jp.isSelected()) {
                    list.add(jp);
                }
                //
            }
        }
        //
        return list;
        //
    }

    public Object[] getSelectedValuesFromTable(JPanel tablePanel) {
        //
        ArrayList<String> list = new ArrayList<>();
        //
        Component[] arr = tablePanel.getComponents();
        //
        for (Component component : arr) {
            //
            if (component instanceof JPanelPrepM) {
                //
                JPanelPrepM jp = (JPanelPrepM) component;
                //
                if (jp.isSelected()) {
                    list.add(jp.getStatusEng());
                }
                //
            }
        }
        //
        return list.toArray();
        //
    }

    public Dimension defaultDimension() {
        //OBS! Very important to set width, otherwise the FlowLayout
        // will add components to the "left" but not "down" one after another
        return new Dimension(430, HEIGHT);
    }

    
    /**
     * Increase width "physically" in drawing tool IF NOT SHOWING As i
     * understand the width shall be (dim.width + 70)
     */
    public void addRows(Object[] items, JPanel panel, Dimension dim) {
        //
        panel.removeAll();
        //
        if (dim == null) {
            panel.setPreferredSize(defaultDimension());
        } else {
            panel.setPreferredSize(dim);
        }
        //
        addRowsTable(items, panel);
        //
    }
    
    private void addRowsTable(Object[] items, JPanel panel) {
        //
        if (items[0] instanceof String) {
            for (int i = 0; i < items.length; i++) {
                if (i == 0) {
                    addRowTable("     ", (String) items[i], panel);
                } else if (i < 10) {
                    addRowTable("  " + i + ":", (String) items[i], panel);
                } else if (i > 10) {
                    addRowTable("" + i + ":", (String) items[i], panel);
                }
            }
        } else if (items[0] instanceof PrepOrAgingEntry) {
            //
            for (int i = 0; i < items.length; i++) {
                //
                PrepOrAgingEntry poae = (PrepOrAgingEntry) items[i];
                //
                if (i < 10) {
                    addRowTable_C("  " + (i+1) + ":", poae, panel);
                } else if (i > 10) {
                    addRowTable_C("" + (i+1) + ":", poae, panel);
                }
            }
            //
        }
    }
    
    /**
     * Used by "LabDevTestConfig.class"
     * @param sequence
     * @param item
     * @param panel 
     */
     private void addRowTable_C(String sequence, PrepOrAgingEntry item, JPanel panel) {
        //
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT,0,5), true); //new FlowLayout(FlowLayout.LEFT)
        container.setPreferredSize(getRowSize(panel));
        //
        JLabel lbl = new JLabel(sequence);
        //
        JCheckBox chk = new JCheckBox();
        //
        JTextField txtfield = new JTextField(item.getDescription());
        txtfield.setEditable(false);
        txtfield.setPreferredSize(getTxtAreaSize(panel,0.73));
        //
        JTextField txtfield_b = new JTextField(item.getCode());
        txtfield_b.setEditable(false);
        txtfield_b.setPreferredSize(getTxtAreaSize(panel,0.15));
        //
        container.add(lbl);
        container.add(chk);
        container.add(txtfield);
        container.add(txtfield_b);
        //
        panel.add(container);
        //
        panel.setPreferredSize(new Dimension(panel.getWidth(), panel.getPreferredSize().height + 31));
        //
        panel.validate(); // MUST BE CALLED adfter setting the prefferedSize
        //
    }

    private void addRowTable(String sequence, String item, JPanel panel) {
        //
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT), true); //new FlowLayout(FlowLayout.LEFT)
        container.setPreferredSize(getRowSize(panel));
        //
        JLabel lbl = new JLabel(sequence);
        //
        JCheckBox chk = new JCheckBox();
        //
        JTextField txtfield = new JTextField(item);
        txtfield.setEditable(false);
        txtfield.setPreferredSize(getTxtAreaSize(panel,0.85));
        //
        container.add(lbl);
        container.add(chk);
        container.add(txtfield);
        //
        panel.add(container);
        //
        panel.setPreferredSize(new Dimension(panel.getWidth(), panel.getPreferredSize().height + 31));
        //
        panel.validate(); // MUST BE CALLED adfter setting the prefferedSize
        //
    }
    
    
    /**
     * OBS! Used by "LabDevFindOrder.class" -> "FIND ORDER" TAB
     * Increase width "physically" in drawing tool IF NOT SHOWING As i
     * understand the width shall be (dim.width + 70)
     */
    public void addRows_B(String[] items, JPanel panel, Dimension dim) {
        //
        panel.removeAll();
        //
        if (dim == null) {
            panel.setPreferredSize(defaultDimension());
        } else {
            panel.setPreferredSize(dim);
        }
        //
        addRowsTable_B(items, panel);
        //
        panel.repaint();
        //
    }

    private void addRowsTable_B(String[] items, JPanel panel) {
        for (String item : items) {
            addRowTable_B(item, panel);
        }
    }

    private void addRowTable_B(String item, JPanel panel) {
        //
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT), false); //new FlowLayout(FlowLayout.LEFT)
        container.setPreferredSize(getRowSize(panel));
        //
        JCheckBox chk = new JCheckBox();
        //
        JLabel jlbl = new JLabel(item);
//        jlbl.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jlbl.setPreferredSize(getTxtAreaSize(panel,0.85));
        //
        container.add(chk);
        container.add(jlbl);
        //
        panel.add(container);
        //
        panel.setPreferredSize(new Dimension(panel.getWidth(), panel.getPreferredSize().height + 31));
        //
        panel.validate(); // MUST BE CALLED adfter setting the prefferedSize
        //
    }

    private Dimension getRowSize(JPanel panel) {
        return new Dimension(panel.getWidth() - 10, 31);
    }

    private Dimension getTxtAreaSize(JPanel panel, double widthPercent) {
        int width_total = panel.getWidth();
        int width = (int) (width_total * widthPercent);
        return new Dimension(width, 27);
    }

}
