/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.IO;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.TableInvert;
import java.awt.event.KeyEvent;

/**
 *
 * @author KOCMOC
 */
public class Home extends Basic_Buh {

    private final String VALIDATION__V_ERR_0 = "V_ERR_0"; // user or pass parameters not set
    private final String VALIDATION__V_ERR_01 = "V_ERR_01"; // user does not exist
    private final String VALIDATION__V_ERR_02 = "V_ERR_02"; // password wrong

    public static final String CHECK_BOX__SAVE_LOGIN_STATE = "chkbox";

    public Home(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        loadCheckBoxSaveLoginState();
        refresh();
    }

    private void loadCheckBoxSaveLoginState() {
        //
        String state = IO.loadLastEntered(CHECK_BOX__SAVE_LOGIN_STATE, "0");
        //
        if (state.equals("1")) {
            bim.jCheckBox_spara_inloggning.setSelected(true);
        }
    }

    private boolean saveInloggning() {
        return bim.jCheckBox_spara_inloggning.isSelected();
    }

    protected void refresh() {
        showTableInvert();
    }

    protected void loggaIn() {
        //
        if (fieldsValidated(false) == false) {
            return;
        }
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        GP_BUH.USER = _get(map, DB.BUH_LICENS__USER);
        GP_BUH.PASS = _get(map, DB.BUH_LICENS__PASS);
        //
        if (validateAndefineKundId()) {
            //
            bim.enableTabs(true);
            refresh();
            bim.openTabByName(BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
            bim.allInvoicesTabClicked();
            //
        } else {
            HelpA.showNotification(LANG.VALIDATION_MSG_1);
//            System.exit(0);
        }
        //
    }

    private boolean validateAndefineKundId() {
        //[#SEQURITY#]
        HashMap<String, String> map = new HashMap();
        //
        String responce;
        //
        try {
            //
            responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DEFINE_KUNDID, JSon.hashMapToJSON(map));
            //
            return validateResponce(responce);
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean validateResponce(String responce) {
        //[#SEQURITY#]
        boolean isNumber = HelpA.isNumber(responce);
        //
        if (isNumber) {
            //
            int kundid = Integer.parseInt(responce);
            //
            if (kundid > 0) {
                GP_BUH.KUND_ID = "" + kundid; // *********IMPORTANT
                return true;
            }
        } else {
            //
            if (responce.equals(VALIDATION__V_ERR_0)) {
                // This on can infact never happen [2020-10-09]
                HelpA.showNotification(LANG.VALIDATION_MSG__V_ERR_0);
            } else if (responce.equals(VALIDATION__V_ERR_01)) {
                HelpA.showNotification(LANG.VALIDATION_MSG__V_ERR_01);
            } else if (responce.equals(VALIDATION__V_ERR_02)) {
                HelpA.showNotification(LANG.VALIDATION_MSG__V_ERR_02);
            }
            //
        }
        //
        return false;
    }

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_inloggning);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        String user_ = IO.loadLastEntered(DB.BUH_LICENS__USER, "");
        RowDataInvert user = new RowDataInvertB(user_, DB.BUH_LICENS__USER, "ANVÄNDARNAMN", "", true, true, true);
        //
        String pass_ = IO.loadLastEntered(DB.BUH_LICENS__PASS, "");
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, pass_, DB.BUH_LICENS__PASS, "LÖSENORD", "", true, true, true);
        //
        RowDataInvert[] rows = {
            user,
            pass
        };
        //
        return rows;
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (col_name.equals(DB.BUH_LICENS__USER) && ti.equals(TABLE_INVERT)) {
            if (Validator.validateMaxInputLength(jli, 100)) { // The length 100 has also nothing with db to do
                save(DB.BUH_LICENS__USER, jli.getValue());
            }
        } else if (col_name.equals(DB.BUH_LICENS__PASS) && ti.equals(TABLE_INVERT)) {
            if (Validator.validateMaxInputLength(jli, 50)) { // The length 50 has nothing to do with db storage as the password sent is only compared on PHP side
                save(DB.BUH_LICENS__PASS, jli.getValue());
            }
        }
        //
    }
    
    /**
     * Saving when the checkBox state changed to "1/selected"
     */
    protected void saveUserAndPass(){
         save(DB.BUH_LICENS__USER, getValueTableInvert(DB.BUH_LICENS__USER));
         save(DB.BUH_LICENS__PASS, getValueTableInvert(DB.BUH_LICENS__PASS));
    }
    
    protected void deleteUserAndPass(){
        IO.delete(DB.BUH_LICENS__USER);
        IO.delete(DB.BUH_LICENS__PASS);
    }
    
    private void save(String param, String value) {
        if (saveInloggning()) {
            IO.writeToFile(param, value);
        }
    }

}
