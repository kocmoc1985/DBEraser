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

/**
 *
 * @author KOCMOC
 */
public class Home extends Basic_Buh {

    public Home(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        refresh();
    }

    protected void refresh() {
        showTableInvert();
    }

    protected void loggaIn() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        System.out.println("");
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
        RowDataInvert user = new RowDataInvertB("", DB.BUH_LICENS__USER, "ANVÄNDARNAMN", "", true, true, true);
        //
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, "", DB.BUH_LICENS__PASS, "LÖSENORD", "", true, true, true);
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

}
