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
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.HttpResponce;
import BuhInvoice.sec.IO;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.TableInvert;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class Home extends Basic_Buh {
    
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Table TABLE_INVERT_4;
    
    public static final String CHECK_BOX__SAVE_LOGIN_STATE = "chkbox";
    
    private static final String LOGIN = "E-POST - ANVÄNDARNAMN";
    //
    private static final String TABLE_SHARED_USERS__USER = "ANVÄNDARE";
    private static final String TABLE_SHARED_USERS__DATE_CREATED = "SKAPAD";
    
    public Home(LAFakturering bim) {
        super(bim);
    }
    
    private void notifyAboutTestEnvironment() {
        //
        if (HelpBuh.USE_TEST_DB && HelpBuh.USE_TEST_SCRIPTS) {
            HelpA.showNotification("NOTE, TEST DATABASE AND TEST SCRIPTS IN USE");
            return;
        }
        //
        if (HelpBuh.USE_TEST_DB) {
            HelpA.showNotification("NOTE, TEST DATABASE IN USE");
            return;
        }
        //
        if (HelpBuh.USE_TEST_SCRIPTS) {
            HelpA.showNotification("ATTENTION, ONLY TEST SCRIPTS IN USE - SO THE MAIN DB IS IN USE");
            return;
        }
    }
    
    @Override
    protected void startUp() {
        //
        notifyAboutTestEnvironment();
        //
        if (HelpA.checkInternetConnection(this, 2) == false) {
            //
            HelpA.showNotification(LANG.INTERNET_CONNECTION_MISSING);
            //
            System.exit(0);
            //
        }
        //
        loadCheckBoxSaveLoginState();
        fillJTableHeader();
        refresh();
        HelpBuh.checkUpdates(LAFakturering.jLabel17_new__version);
        //
        if (HelpBuh.OBLIGATORY_UPDATE__INSTALL_NEW_REQUIRED) {
            //[#OBLIGATORY-UPDATE#]
            boolean b = GP_BUH.confirmWarning(LANG.MSG_29);
            //
            if (b) {
                HelpA.navigate_to_webbpage(GP_BUH.LA_WEB_ADDR);
            }
            //
            System.exit(0);
        }
        //
    }
    
    private void fillJTableHeader() {
        //
        JTable table = this.bim.jTable_shared_users;
        //
        String[] headers_b = {
            TABLE_SHARED_USERS__USER,
            TABLE_SHARED_USERS__DATE_CREATED
        };
        //
        table.setModel(new DefaultTableModel(null, headers_b));
        //
    }
    
    private void fillSharedUsersTable() {
        //
        JTable table = bim.jTable_shared_users;
        //
        String req = bim.getSELECT(DB.BUH_LICENS__KUND_ID, GP_BUH.KUND_ID);
        //
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__SHARED_USERS, req);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(responce);
            //
            for (HashMap<String, String> map : invoices) {
                addRowJTableSharedUsers(map, table);
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addRowJTableSharedUsers(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_LICENS__USER),
            map.get(DB.BUH_LICENS__DATE_CREATED).substring(0, 10)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }
    
    protected void processDeleteGuest() {
        //
        String guestUser = HelpA.getValueSelectedRow(bim.jTable_shared_users, TABLE_SHARED_USERS__USER);
        //
        HttpResponce res = HelpBuh.deleteGuestAccount(guestUser);
        //
        refresh();
        //
        res.defineStatus();
    }
    
    protected void processShareAccount() {
        //
        if (fieldsValidated_tableInvert_4(false)) {
            //
            HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_4, DB.START_COLUMN);
            //
            String userEmailToShareWith = map.get(DB.BUH_LICENS__USER);
            //
            HttpResponce responce = HelpBuh.shareAccount(userEmailToShareWith);
            //
            responce.defineStatus();
            //
            refresh();
        }
        //
    }
    
    protected void processForgotPass() {
        //
        if (fieldsValidated_tableInvert_3(false)) {
            //
            HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_3, DB.START_COLUMN);
            //
            String userEmail = map.get(DB.BUH_LICENS__USER);
            //
            HttpResponce responce = HelpBuh.restorePwd(userEmail);
            //
            responce.defineStatus();
            //
        }
        //
    }
    
    protected void processUserRegistration() {
        //
        if (fieldsValidated_tableInvert_2(false)) {
            //
            HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN);
            //
            String userEmail = map.get(DB.BUH_LICENS__USER);
            String ftgName = map.get(DB.BUH_KUND__NAMN);
            String orgnr = map.get(DB.BUH_KUND__ORGNR);
            //
            HttpResponce responce = HelpBuh.createAccountPHP_main(userEmail, ftgName, orgnr);
            //
            responce.defineStatus();
            //
        }
        //
    }
    
    private void setInloggningsLabel(String text) {
        bim.jLabel_inloggning.setText(text);
//        BlinkThread bt = new BlinkThread(bim.jLabel_inloggning, false);
//        bt.setBlinkText();
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
    
    private JTable getJTable() {
        return bim.jTable_shared_users;
    }
    
    protected void refresh() {
        //
        showTableInvert();
        showTableInvert_2();
        showTableInvert_3();
        showTableInvert_4();
        //
        if (GP_BUH.loggedIn() && GP_BUH.isGuestUser() == false) {
            HelpA.clearAllRowsJTable(getJTable());
            fillSharedUsersTable();
        }
        //
        if (GP_BUH.isGuestUser()) {
            bim.jButton_delete_account_sharing.setEnabled(false);
        }
        //
    }
    
    protected void loggaIn() {
        //
        if (fieldsValidated(false) == false) {
            return;
        }
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        GP_BUH.USER = GP_BUH._get(map, DB.BUH_LICENS__USER);
        GP_BUH.PASS = GP_BUH._get(map, DB.BUH_LICENS__PASS);
        //
        if (login()) {
            //
            bim.enableTabs(true);
            refresh();
            getFtgName();
            setInloggningsLabel(LANG.getInloggningsMsg(GP_BUH.CUSTOMER_COMPANY_NAME));
            //
            bim.openTabByName(LAFakturering.TAB_INVOICES_OVERVIEW);
            bim.allInvoicesTabClicked();
            //
            if (HelpBuh.COMPANY_MIXCONT) {
                //[#EUR-SEK#]
                LAFakturering.jTextField_eur_sek__kurs.setText("" + HelpBuh.getEurSekKurs());
                LAFakturering.jTextField_eur_sek__kurs.setEnabled(false);
                LAFakturering.jPanel_eur_sek__kurs.setVisible(true);
            }else{
                LAFakturering.jPanel_eur_sek__kurs.setVisible(false);
            }
            //
        } else {
            HelpA.showNotification(LANG.VALIDATION_MSG_1);
//            System.exit(0);
        }
        //
    }
    
    private void getFtgName() {
        //
        String req = bim.getSELECT(DB.BUH_KUND__ID, GP_BUH.KUND_ID);
        //         
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FTG_NAME, req);
            //
            HashMap<String, String> map = JSon.JSONToHashMap(responce, false);
            //
            GP_BUH.CUSTOMER_COMPANY_NAME = map.get(DB.BUH_KUND__NAMN);
            //
//            System.out.println("CUSTOMER_COMPANY_NAME: " + GP_BUH.CUSTOMER_COMPANY_NAME);
            //
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean login() {
        //[#SEQURITY#][#&LOGIN&#]
        HashMap<String, String> map = new HashMap();
        //
        // The below parameters are for "buh_visitors" [2020-10-19]
        map.put(DB.BUH_LICENS__MAC_ADDR, HelpA.getMacAddress());
        map.put(DB.BUH_LICENS__OS, HelpA.getOperatingSystem());
        map.put(DB.BUH_LICENS__LANG, HelpA.getUserLanguge());
        map.put(DB.BUH_LICENS__PC_USER_NAME, HelpA.getUserName());
        map.put(DB.BUH_LICENS__JAVA, HelpA.getJavaVersionAndBitAndVendor_b());
        //
        map.put(DB.BUH_VISITORS__PROGRAM_VER, "" + GP_BUH.VERSION_INTEGER); // added on[2021-03-08]
        //
        //[2020-12-23] This is the "login marker" so i can distinguish between
        // a common request and "login". OBS! "login_attempt_marker" shall only 
        // be used from HERE
        map.put("login_attempt_marker", "true");
        //
        String responce;
        //
        try {
            //
            // The name of "PHP_FUNC_DEFINE_KUNDID__LOGIN" is a bit misleading.. The main thing is "login"
            responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DEFINE_KUNDID__LOGIN, JSon.hashMapToJSON(map));
            //
            return validateResponce(responce);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
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
            HttpResponce resp = new HttpResponce(responce, null);
            resp.defineStatus();
            //
        }
        //
        return false;
    }
    
    private void makeTransparent(Table tableInvert, JPanel parentContainer) {
        //
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                //
//                tableInvert.setBackground(new Color(0, 0, 0, 10));
//                tableInvert.revalidate();
//                tableInvert.repaint();
//                //
//                parentContainer.setBackground(new Color(0, 0, 0, 10));
//                parentContainer.revalidate();
//                parentContainer.repaint();
//                //
//                // Not working - border still visible:
////                tableInvert.setBorder(BorderFactory.createEmptyBorder());
////                parentContainer.setBorder(BorderFactory.createEmptyBorder());
//                //
//            }
//        });
        //
    }

    /**
     * LOG IN
     */
    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "login");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this, Color.BLACK);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_inloggning);
        //
        makeTransparent(TABLE_INVERT, bim.jPanel_inloggning);
        //
    }

    /**
     * CREATE ACCOUNT
     */
    public void showTableInvert_2() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "register_new");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this, Color.BLACK);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_register_new, TABLE_INVERT_2);
        //
        makeTransparent(TABLE_INVERT_2, bim.jPanel_register_new);
        //
    }

    /**
     * RESTORE ACCOUNT
     */
    public void showTableInvert_3() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "restore");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this, Color.BLACK);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_restore_password, TABLE_INVERT_3);
        //
        makeTransparent(TABLE_INVERT_3, bim.jPanel_restore_password);
        //
    }
    
    public void showTableInvert_4() {
        //
        // Yes, it's correct that "getConfigTableInvert_3()" is used
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_4(), false, "restore");
        TABLE_INVERT_4 = null;
        TABLE_INVERT_4 = tableBuilder.buildTable_B(this, Color.BLACK);
        setMargin(TABLE_INVERT_4, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_share_account, TABLE_INVERT_4);
        //
        makeTransparent(TABLE_INVERT_4, bim.jPanel_share_account);
        //
    }
    
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        String user_ = IO.loadLastEntered(DB.BUH_LICENS__USER, "");
        RowDataInvert user = new RowDataInvertB(user_, DB.BUH_LICENS__USER, LOGIN, "", true, true, true);
        //
        String pass_ = IO.loadLastEntered(DB.BUH_LICENS__PASS, "");
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, pass_, DB.BUH_LICENS__PASS, "LÖSENORD", "", true, true, true);
        //
        if (GP_BUH.loggedIn()) {
            bim.jButton_logg_in.setEnabled(false);
        }
        //
        RowDataInvert[] rows = {
            user,
            pass
        };
        //
        return rows;
    }
    
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert user = new RowDataInvertB("", DB.BUH_LICENS__USER, LOGIN, "", true, true, true);
        //
        RowDataInvert ftg_name = new RowDataInvertB("", DB.BUH_KUND__NAMN, "FÖRETAGSNAMN", "", true, true, true);
        //
        RowDataInvert org_nr = new RowDataInvertB("", DB.BUH_KUND__ORGNR, "ORGNR", "", true, true, true);
        org_nr.setToolTipFixed(LANG.TOOL_TIP_2);
        //
        if (GP_BUH.loggedIn()) {
            user.setDisabled();
            ftg_name.setDisabled();
            org_nr.setDisabled();
            bim.jButton_register_new_user.setEnabled(false);
        } else {
            bim.jButton_register_new_user.setEnabled(true);
        }
        //
        RowDataInvert[] rows = {
            user,
            ftg_name,
            org_nr
        };
        //
        return rows;
    }
    
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        RowDataInvert user = new RowDataInvertB("", DB.BUH_LICENS__USER, LOGIN, "", true, true, true);
        //
        if (GP_BUH.loggedIn()) {
            user.setDisabled();
            bim.jButton_forgot_password.setEnabled(false);
        } else {
            bim.jButton_forgot_password.setEnabled(true);
        }
        //
        RowDataInvert[] rows = {
            user
        };
        //
        return rows;
    }
    
    public RowDataInvert[] getConfigTableInvert_4() {
        //
        RowDataInvert user = new RowDataInvertB("", DB.BUH_LICENS__USER, LOGIN, "", true, true, true);
        //
        if (GP_BUH.loggedIn() == false || GP_BUH.isGuestUser()) {
            user.setDisabled();
            bim.jButton_share_account.setEnabled(false);
        } else {
            bim.jButton_share_account.setEnabled(true);
        }
//        user.setToolTipFixed("E-post");
        //
        RowDataInvert[] rows = {
            user
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
    
    private boolean fieldsValidated_tableInvert_2(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }
    
    private boolean fieldsValidated_tableInvert_3(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }
    
    private boolean fieldsValidated_tableInvert_4(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT_4, DB.START_COLUMN, getConfigTableInvert_4())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_4, DB.START_COLUMN, getConfigTableInvert_4())) {
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
            //
            Validator.validateEmail(jli);
            //
        } else if (col_name.equals(DB.BUH_LICENS__PASS) && ti.equals(TABLE_INVERT)) {
            if (Validator.validateMaxInputLength(jli, 50)) { // The length 50 has nothing to do with db storage as the password sent is only compared on PHP side
                save(DB.BUH_LICENS__PASS, jli.getValue());
            }
            //
            //
        } else if (col_name.equals(DB.BUH_LICENS__USER) && ti.equals(TABLE_INVERT_2)) {
            Validator.validateEmail(jli);
        } else if (col_name.equals(DB.BUH_KUND__NAMN) && ti.equals(TABLE_INVERT_2)) {
            Validator.validateMaxInputLength(jli, 50);
        } else if (col_name.equals(DB.BUH_KUND__ORGNR) && ti.equals(TABLE_INVERT_2)) {
            Validator.validateOrgnr(jli);
            orgnr_additional(jli, ti);
        } //
        //
        else if (col_name.equals(DB.BUH_LICENS__USER) && ti.equals(TABLE_INVERT_3)) {
            Validator.validateEmail(jli);
        } //
        else if (col_name.equals(DB.BUH_LICENS__USER) && ti.equals(TABLE_INVERT_4)) {
            Validator.validateEmail(jli);
        }
    }

    /**
     * Saving when the checkBox state changed to "1/selected"
     */
    protected void saveUserAndPass() {
        save(DB.BUH_LICENS__USER, getValueTableInvert(DB.BUH_LICENS__USER));
        save(DB.BUH_LICENS__PASS, getValueTableInvert(DB.BUH_LICENS__PASS));
    }
    
    protected void deleteUserAndPass() {
        IO.delete(DB.BUH_LICENS__USER);
        IO.delete(DB.BUH_LICENS__PASS);
    }
    
    private void save(String param, String value) {
        if (saveInloggning()) {
            IO.writeToFile(param, value);
        }
    }
    
}
