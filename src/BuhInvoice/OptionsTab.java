/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.BlinkThread;
import BuhInvoice.sec.EmailSendingStatus;
import BuhInvoice.sec.HttpResponce;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.SMTP;
import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import forall.TextFieldCheck;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

/**
 *
 * @author KOCMOC
 */
public class OptionsTab extends Basic_Buh {

    private final static String SMTP_HOST = "host";
    private final static String SMTP_PORT = "port";
    private final static String SMTP_KRYPT = "krypt";
    private final static String SMTP_YOUR_EMAIL = "user";
    private final static String SMTP_PASS = "pass";
    private final static String SMTP_FROM_NAME = "name";

    public OptionsTab(BUH_INVOICE_MAIN bim) {
        super(bim);
        GP_BUH.setPageBackground(bim.jPanel20, GP_BUH.BASIC_BACKGROUND_IMG__PATH);
    }

    protected void refresh() {
        showTableInvert();
    }

    @Override
    protected void startUp() {
        refresh();
        initOther();
    }

    private void initOther() {
        //
        if (GP_BUH.isGuestUser()) {
            bim.jButton_erase_account_btn.setEnabled(false);
        }
        //
        showReminderOption();
        showRutOption();
        //
    }

    protected void deleteCustomerDataPermanent() {
        //
        if (GP_BUH.confirmWarning(LANG.MSG_4_3) == false) {
            return;
        }
        //
        JPasswordField jpf = HelpA.chooseFromPasswordField(LANG.MSG_4_4, true, 30);
        String pwd = new String(jpf.getPassword());
        //
        if (pwd.isEmpty()) {
            return;
        }
        //
        HttpResponce responce = HelpBuh.deleteCustomer_b(pwd);
        //
        responce.defineStatus();
        //
        System.exit(0);
        //
    }

    protected void deleteSmtpSettings() {
        IO.deleteSMTP();
        refresh();
    }

    protected void testSmtpSettings() {
        //
        SMTP smtp = IO.loadSMTP();
        //
        if (smtp != null && smtp.allFilled()) {
            //    
            TextFieldCheck tfc = new TextFieldCheck("", Validator.EMAIL, 25);
            boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, LANG.MSG_7_2);
            String toEmail = tfc.getText_();
            //
            if (yesNo && toEmail != null && tfc.getValidated()) {
                //
                EmailSendingStatus ess = HelpBuh.sendEmailWithAttachment_SMTP(smtp, toEmail, "SMTP Test", "This is a test email", "");
                //
                if (ess.emailSendingSuccessful()) {
                    HelpA.showNotification(LANG.MSG_15_4);
                } else {
                    HelpA.showNotification(LANG.MSG_15_5);
                }
            }
            //
        } else {
            HelpA.showNotification(LANG.MSG_15_3);
        }
        //
    }

    protected void saveSmtpSettings() {
        //
        if (fieldsValidated(false) == false) {
            return;
        }
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
//        SMTP smtp = new SMTP("send.one.com", "andrei.brassas@mixcont.com", "Kocmoc4765", "587", "BuhInvoice");
        SMTP smtp = new SMTP(map.get(SMTP_HOST), map.get(SMTP_YOUR_EMAIL), map.get(SMTP_PASS), map.get(SMTP_PORT), map.get(SMTP_FROM_NAME));
        //
        if (IO.saveSMTP(smtp)) {
            HelpA.showNotification(LANG.MSG_15);
        } else {
            HelpA.showNotification(LANG.MSG_15_2);
        }
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
    public void showTableInvert() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "e_mail_options");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this, Color.BLACK);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_email_client_options);
        //
    }

    /**
     * For the SMTP Settings
     *
     * @return
     */
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        SMTP smtp = IO.loadSMTP();
        //
        if (smtp != null && smtp.allFilled()) {
            return getConfigTableInvert_settings_exist(smtp);
        } else {
            return getConfigTableInvert_settings_dont_exist();
        }
        //
    }

    public RowDataInvert[] getConfigTableInvert_settings_exist(SMTP smtp) {
        //
        String host_ = smtp.getHost();
        RowDataInvert host = new RowDataInvertB(host_, SMTP_HOST, "UTGÅENDE SERVERNAMN", "", true, true, true);
        //
        String port_ = smtp.getPort();
        RowDataInvert port = new RowDataInvertB(port_, SMTP_PORT, "PORT", "", true, true, true);
        //
        String krypt_ = SMTP.getCrypt();
        RowDataInvert krypt = new RowDataInvertB(krypt_, SMTP_KRYPT, "KRYPTERING", "", true, true, true);
        krypt.setDisabled();
        krypt.setDontAquireTableInvertToHashMap();
        //
        String user_ = smtp.getU();
        RowDataInvert user = new RowDataInvertB(user_, SMTP_YOUR_EMAIL, "DIN E-POSTADRESS", "", true, true, true);
        //
        String pass_ = smtp.getP();
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, pass_, SMTP_PASS, "DIN LÖSENORD", "", true, true, true);
        //
        String namn_ = smtp.getFrom_name();
        RowDataInvert namn = new RowDataInvertB(namn_, SMTP_FROM_NAME, "NAMN", "", true, true, false);
        //
        RowDataInvert[] rows = {
            host,
            port,
            krypt,
            user,
            pass,
            namn
        };
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert_settings_dont_exist() {
        //
        RowDataInvert host = new RowDataInvertB("", SMTP_HOST, "UTGÅENDE SERVERNAMN", "", true, true, true);
        //
        RowDataInvert port = new RowDataInvertB("", SMTP_PORT, "PORT", "", true, true, true);
        //
        String krypt_ = SMTP.getCrypt();
        RowDataInvert krypt = new RowDataInvertB(krypt_, SMTP_KRYPT, "KRYPTERING", "", true, true, true);
        krypt.setDisabled();
        krypt.setDontAquireTableInvertToHashMap();
        //
        RowDataInvert user = new RowDataInvertB("", SMTP_YOUR_EMAIL, "DIN E-POSTADRESS", "", true, true, true);
        //
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, "", SMTP_PASS, "DIN LÖSENORD", "", true, true, true);
        //
        RowDataInvert namn = new RowDataInvertB("", SMTP_FROM_NAME, "NAMN", "", true, true, false);
        //
        RowDataInvert[] rows = {
            host,
            port,
            krypt,
            user,
            pass,
            namn
        };
        //
        return rows;
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
        if (col_name.equals(SMTP_PORT) && ti.equals(TABLE_INVERT)) {
            Validator.validateDigitalInput(jli);
        } else if (col_name.equals(SMTP_YOUR_EMAIL) && ti.equals(TABLE_INVERT)) {
            Validator.validateEmail(jli);
        }
        //
    }

    private void showRutOption() {
        //
        String remindMsg = LANG.RUT_MSG_MAIN__AUTO("?", "?", "?", "?");
        //
        JTextArea jtxt = bim.jTextArea_rut_message;
        //
        jtxt.setText(remindMsg);
        //
    }

    protected void saveReminderMessage() {
        //
        JTextArea jtxt = bim.jTextArea_reminder_message;
        //
        if (IO.saveReminderMsg(jtxt.getText())) {
            BlinkThread bt = new BlinkThread(jtxt, false);
        } else {
            BlinkThread bt = new BlinkThread(jtxt, true);
        }
        //
        showReminderOption();
        showRutOption();
        //
    }
    
    protected void saveRutMessage() {
        //
        JTextArea jtxt = bim.jTextArea_rut_message;
        //
        if (IO.saveRutMsg(jtxt.getText())) {
            BlinkThread bt = new BlinkThread(jtxt, false);
        } else {
            BlinkThread bt = new BlinkThread(jtxt, true);
        }
        //
        showRutOption();
        //
    }

    private void showReminderOption() {
        //
        String remindMsg = LANG.PAMMINELSE_MSG_MAIN__AUTO("?");
        //
        JTextArea jtxt = bim.jTextArea_reminder_message;
        //
        jtxt.setText(remindMsg);
        //
    }


    protected void restoreReminderMessage() {
        //
        IO.deleteReminderMsg();
        //
        showReminderOption();
        showRutOption();
        //
    }

    protected void restoreRutMessage() {
        //
        IO.deleteRutMsg();
        //
        showRutOption();
        //
    }
}
