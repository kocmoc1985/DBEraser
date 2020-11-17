/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public abstract class ChkBoxItemListComponent extends JFrame {

    public static final int HEIGHT = 35;
    
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
     * Increase width "physically" in drawing tool IF NOT SHOWING
     * As i understand the width shall be (dim.width + 70)
     */
    public void addRows(String[] items, JPanel panel, Dimension dim) {
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
    
    /**
     * Increase width "physically" in drawing tool IF NOT SHOWING
     * As i understand the width shall be (dim.width + 70)
     */
    public void addRows_B(String[] items, JPanel panel, Dimension dim){
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
    
   private void addRowsTable_B(String[] items, JPanel panel){
       for (String item : items) {
           addRowTable_B(item, panel);
       }
   }

    private void addRowsTable(String[] items, JPanel panel) {
        //
        for (int i = 0; i < items.length; i++) {
            if (i == 0) {
                addRowTable("     ", items[i], panel);
            } else if (i < 10) {
                addRowTable("  " + i + ":", items[i], panel);
            } else if (i > 10) {
                addRowTable("" + i + ":", items[i], panel);
            }
        }
        //
    }

    private void addRowTable(String sequence, String item, JPanel panel) {
        //
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT),true); //new FlowLayout(FlowLayout.LEFT)
        container.setPreferredSize(getRowSize(panel));
        //
        JLabel lbl = new JLabel(sequence);
        //
        JCheckBox chk = new JCheckBox();
        //
        JTextField txtfield = new JTextField(item);
        txtfield.setPreferredSize(getTxtAreaSize(panel));
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
    
     private void addRowTable_B(String item, JPanel panel) {
        //
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT),false); //new FlowLayout(FlowLayout.LEFT)
        container.setPreferredSize(getRowSize(panel));
        //
        JCheckBox chk = new JCheckBox();
        //
        JLabel jlbl = new JLabel(item);
//        jlbl.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jlbl.setPreferredSize(getTxtAreaSize(panel));
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

    private Dimension getTxtAreaSize(JPanel panel) {
        int width_total = panel.getWidth();
        int width = (int) (width_total * 0.85);
        return new Dimension(width, 27);
    }

}
