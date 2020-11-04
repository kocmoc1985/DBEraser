/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import forall.SqlBasicLocal;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevHeaderComponent {

    private final JPanel header;
    private final SqlBasicLocal sql;

    public LabDevHeaderComponent(JPanel header, SqlBasicLocal sql) {
        this.header = header;
        this.sql = sql;
        addStandardComponent();
    }
    
    private JPanel getUpper(){
        return (JPanel)header.getComponent(0);
    }
    
    private JPanel getLower(){
        return (JPanel)header.getComponent(1);
    }

    public void addStandardComponent(){
        //
        buildJLabelJTextFieldComonent("Order", "ENTW002106");
        //
        buildJLabelJTextFieldComonent("Status", "Ausführen");
        //
        buildJLabelJTextFieldComonent("Aktualisiert", "11.03.2020");
        //
        getUpper().add(new JPanel()); // Adding empty for compacting
        //
        getUpper().add(new JPanel()); // Adding empty for compacting
    }
    
    private void buildJLabelJTextFieldComonent(String jLabelVal, String jTextFieldVal){
        JLabel label_order = new JLabel(jLabelVal);
        label_order.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JTextField order_val = new JTextField(jTextFieldVal);
        JPanel order_container = new JPanel(new GridLayout(1, 2));
        order_container.add(label_order);
        order_container.add(order_val);
        getUpper().add(order_container);
    }
    
}
