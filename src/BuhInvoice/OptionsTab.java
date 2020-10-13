/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.IO;
import BuhInvoice.sec.SMTP;
import MyObjectTable.OutPut;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert_;

/**
 *
 * @author KOCMOC
 */
public class OptionsTab extends Basic_Buh {

    public OptionsTab(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    protected void refresh() {
        showTableInvert();
    }

    @Override
    protected void startUp() {
        refresh();
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "e_mail_options");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_email_client_options);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
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
        RowDataInvert host = new RowDataInvertB(host_, "host", "UTGÅENDE SERVERNAMN", "", true, true, true);
        //
        String user_ = smtp.getU();
        RowDataInvert user = new RowDataInvertB(user_, "user", "DIN E-POSTADRESS", "", true, true, true);
        //
        RowDataInvert[] rows = {
            host,
            user
        };
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert_settings_dont_exist() {
        RowDataInvert[] rows = {};
        //
        return rows;
    }
}
