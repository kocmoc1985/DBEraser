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

    public Dimension defaultDimension() {
        //OBS! Very important to set width, otherwise the FlowLayout
        // will add components to the "left" but not "down" one after another
        return new Dimension(430, 35);
    }

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

    private void addRowTable(String sequence, String prepmethod, JPanel panel) {
        JPanelPrepM container = new JPanelPrepM(new FlowLayout(FlowLayout.LEFT)); //new FlowLayout(FlowLayout.LEFT)
//        container.setBackground(Color.yellow);
        container.setPreferredSize(getRowSize(panel));
        //
        JLabel lbl = new JLabel(sequence);
        //
        JCheckBox chk = new JCheckBox();
        //
        JTextField txtfield = new JTextField(prepmethod);
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

    private Dimension getRowSize(JPanel panel) {
        return new Dimension(panel.getWidth() - 10, 31);
    }

    private Dimension getTxtAreaSize(JPanel panel) {
        int width_total = panel.getWidth();
        int width = (int) (width_total * 0.85);
        return new Dimension(width, 27);
    }

}
