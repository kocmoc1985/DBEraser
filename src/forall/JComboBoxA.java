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

    public JComboBoxA(String PARAMETER, boolean isNumber) {
        this.PARAMETER = PARAMETER;
        this.NUMBER = isNumber;
    }

    public JComboBoxA() {
        
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
   
}
