/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author KOCMOC
 */
public class JComboBoxM extends JComboBox<Object> {

    private ListSelectionListener listener;
    private ArrayList<JComboBoxValueChangedListener> list = new ArrayList<JComboBoxValueChangedListener>();
    
    public JComboBoxM() {
        uninstall();
        install();
    }
    
    @Override
    public void updateUI() {
        uninstall();
        super.updateUI();
        install();
    }
    
    private void uninstall() {
        if (listener == null) {
            return;
        }
        getPopupList().removeListSelectionListener(listener);
        listener = null;
    }
    
    private void install() {
        listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                
                JList list = getPopupList();
//                System.out.println("--> " + String.valueOf(list.getSelectedValue()));
                callListeners(String.valueOf(list.getSelectedValue()));
            }
        };
        getPopupList().addListSelectionListener(listener);
    }
    
    private void callListeners(String value) {
        for (JComboBoxValueChangedListener listnr : list) {
            listnr.valueChanged(value,this);
        }
    }
    
    public void addValueChangedListener(JComboBoxValueChangedListener jcbvcl){
        list.add(jcbvcl);
    }
    
    private JList getPopupList() {
        ComboPopup popup = (ComboPopup) getUI().getAccessibleChild(this, 0);
        return popup.getList();
        
    }
}
