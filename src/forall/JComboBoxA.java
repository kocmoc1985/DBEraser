/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import MCRecipe.Sec.CBoxParam;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import static forall.HelpA.getValueResultSet;
import static forall.HelpA.initialComboBoxBorder;
import static forall.HelpA.setCursorWait;
import static forall.HelpA.setTrackingToolTip;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class JComboBoxA extends JComboBox {

    private long FLAG_WAIT;
    private String PARAMETER; // Column name in DB
    private String PARAMETER_2; // For MultiParams -> fillComboBoxMultiple(.....)
    private boolean NUMBER;
    //
    //glazedlists AutoComplete
    private final EventList<Object> LIST = new BasicEventList<Object>();
    private AutoCompleteSupport support;

    public JComboBoxA() {
    }

    /**
     * For multiparams
     *
     * @param param
     */
    public JComboBoxA(CBoxParam param) {
        if (param.isMultipleParam()) {
            this.PARAMETER = param.getParam();
            this.PARAMETER_2 = param.getParam2();
            this.NUMBER = param.isNumber();
        } else {
            this.PARAMETER = param.getParam();
            this.NUMBER = param.isNumber();
        }
    }

    /**
     * @deprecated 
     */
    public JComboBoxA(String PARAMETER, String PARAMETER_2, boolean isNumber) {
        this.PARAMETER = PARAMETER;
        this.NUMBER = isNumber;
        this.PARAMETER_2 = PARAMETER_2;
    }

    public JComboBoxA(String PARAMETER, boolean isNumber) {
        this.PARAMETER = PARAMETER;
        this.NUMBER = isNumber;
    }

    public void AUTOFILL_ADD(List list) {
        if (support == null || support.isInstalled() == false) {
            LIST.addAll(list);
            support = AutoCompleteSupport.install(this, this.LIST);
        } else {
            LIST.clear();
            LIST.addAll(list);
            setEditable(true);
        }
    }

    public boolean isNUMBER() {
        return NUMBER;
    }

    public boolean isMULTI_PARAM() {
        if (PARAMETER != null && PARAMETER_2 != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getPARAMETER_2() {
        return PARAMETER_2;
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
