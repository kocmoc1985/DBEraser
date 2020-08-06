/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statics.HelpMy;

/**
 *
 * @author KOCMOC
 */
public class JPDialogs {

    public static void main(String[] args) {
        showErrorMessage("AAA");
    }
    
    
    
    public static void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showNotificationCustomFontSize(String msg,int fontSize) {
        JLabel label = new JLabel("<html><font color='blue'>"+msg+"</font></html>");
        
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        JOptionPane.showMessageDialog(null,label,"NOTE",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showNotification(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public static boolean confirm() {
        return JOptionPane.showConfirmDialog(null, "Confirm action?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(Object message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    

    public static boolean chooseFromComboBoxDialog(JComboBox box, String msg) {
        return JOptionPane.showConfirmDialog(null, box, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFrom2ComboBoxDialogs(JComboBox box, JComboBox box2, String msg) {
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(box);
        container.add(box2);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFromComboBoxDialogBoxAndTextfield(JComboBox box, JTextField field, String msg) {
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(box);
        container.add(field);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFrom2Textfields(JTextField field1, JTextField field2, String label1, String label2, String msg) {
        JPanel container = new JPanel(new GridLayout(4, 1));
        container.setPreferredSize(new Dimension(200, 100));
        JLabel lbl1 = new JLabel(label1);
        JLabel lbl2 = new JLabel(label2);
        container.add(lbl1);
        container.add(field1);
        container.add(lbl2);
        container.add(field2);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    
}
