/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCREMOTE
 */
public class ArticlesA extends Basic_Buh {

    public ArticlesA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                showTableInvert();
            }
        });
        //
    }

    public void insertArtikel() {
        //
        HashMap<String, String> map = tableInvertToHashMap_updated_values_only(TABLE_INVERT, 1, getConfigTableInvert());
        //
        map.put(DB.BUH_FAKTURA_ARTIKEL___KUND_ID, getKundId());
        //
        String json = JSon.hashMapToJSON(map);
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_3);
            return;
        }
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_ARTIKEL_TO_DB, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel6);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___NAMN, "ARTIKEL NAMN", "", true, true, true);
        //
        RowDataInvert pris = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___PRIS, "PRIS", "", true, true, false);
        //
        RowDataInvert inkopspris = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, "INKÖPS PRIS", "", true, true, false);
        //
        RowDataInvert lager = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___LAGER, "LAGER", "", true, true, false);
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT, "KOMMENT", "", true, true, false);
        //
        //
        RowDataInvert[] rows = {
            namn,
            pris,
            inkopspris,
            lager,
            komment
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
        if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___PRIS)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___LAGER)) {
            //
            Validator.validateDigitalInput(jli);
            //
        }else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___NAMN)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.TABLE__BUH_FAKTURA_ARTIKEL);
            //
        }
        //
    }
}
