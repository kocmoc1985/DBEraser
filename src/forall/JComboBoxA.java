/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author KOCMOC
 */
public class JComboBoxA extends JComboBox {

    private long FLAG_WAIT;
    private String PARAMETER; // Column name in DB
    private boolean NUMBER;
    //
    //glazedlists AutoComplete
    private final EventList<Object> LIST = new BasicEventList<Object>();
    private AutoCompleteSupport support;
    //
    //Value changed listener
    private ListSelectionListener listener;
    private ArrayList<JComboBoxValueChangedListener> list = new ArrayList<JComboBoxValueChangedListener>();

    public JComboBoxA(String PARAMETER, boolean isNumber) {
        this.PARAMETER = PARAMETER;
        this.NUMBER = isNumber;
        //
        //
        valueChangedListener_uninstall();
        valueChangedListener_install();
    }

    public JComboBoxA() {
        valueChangedListener_uninstall();
        valueChangedListener_install();
    }

    public void AUTOFILL_ADD(List list) {
        if (support == null || support.isInstalled() == false) {
            LIST.addAll(list);
            support = AutoCompleteSupport.install(this, this.LIST);
        } else {
            LIST.clear();
            LIST.addAll(list);
        }
    }

    public boolean isNUMBER() {
        return NUMBER;
    }

    public String getPARAMETER() {
        return PARAMETER;
    }

    public void setPARAMETER(String PARAMETER) {
        this.PARAMETER = PARAMETER;
    }

    public long getFLAG_WAIT() {
        return FLAG_WAIT;
    }

    public void setFLAG_WAIT(long FLAG_WAIT) {
        this.FLAG_WAIT = FLAG_WAIT;
    }
    //=========================================================================

    public void addValueChangedListener(JComboBoxValueChangedListener jcbvcl) {
        list.add(jcbvcl);
    }
    
    @Override
    public void updateUI() {
        valueChangedListener_uninstall();
        super.updateUI();
        valueChangedListener_install();
    }

    private void valueChangedListener_uninstall() {
        if (listener == null) {
            return;
        }
        getPopupList().removeListSelectionListener(listener);
        listener = null;
    }

    private void valueChangedListener_install() {
        listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("ListSelectionEvent: " + e);
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
            listnr.comboBoxvalueChanged(value, this);
        }
    }

    

    private JList getPopupList() {
        ComboPopup popup = (ComboPopup) getUI().getAccessibleChild(this, 0);
        return popup.getList();
    }
}
