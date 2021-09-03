/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.LAFakturering;
import BuhInvoice.Basic_Buh;
import BuhInvoice.CustomersA_;
import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import static BuhInvoice.HelpBuh.executePHP;
import BuhInvoice.JSon;
import BuhInvoice.Validator;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class RutRot extends Basic_Buh {

    private Table TABLE_INVERT_2;
    private final RutRotFrame rutRotFrame;
    public static final String COL__FORNAMN = "FÖRNAMN";
    public static final String COL__EFTERNAMN = "EFTERNAMN";
    public static final String COL__AVDRAG = "AVDRAG";
    public static final String COL__PNR = "PERSONNUMMER";
    public static final String COL__AVDRAGS_TAK = "AVDRAGSTAK";
    public static final String COL__FATSTIGHETS_BETECKNING = "FASTIGHETSBETECKNING";
    private final String PNR;
    private final String FORNAMN;
    private final String EFTERNAMN;

    public RutRot(LAFakturering bim, RutRotFrame rutRotFrame,String pnr,String fornamn,String efternamn) {
        super(bim);
        this.bim.setRutRot(this);
        this.rutRotFrame = rutRotFrame;
        this.PNR = pnr;
        this.FORNAMN = fornamn;
        this.EFTERNAMN = efternamn;
    }

    public JPanel getTableInvertPanel() {
        return rutRotFrame.jPanel_table_invert;
    }

    public JPanel getTableInvertPanel_2() {
        return rutRotFrame.jPanel_fastighets_beteckning;
    }

    public HashMap<String, String> getValuesTableInvert() {
        return tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
    }

    public String getFastighetsBeteckning() {
        return getValueTableInvert(DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING, TABLE_INVERT_2);
    }

    public void setFastighetsBeteckning(String value) {
        setValueTableInvert(DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING, TABLE_INVERT_2, value);
    }

    public void sendRutDataToDB(String fakturaId) {
        //[#RUTROT#]
        // OBS! OBS! "DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING" IS TAKEN HERE:
        HashMap<String, String> map_rut = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN);
        //
        map_rut.put(DB.BUH_FAKTURA_RUT__KUNDID, "777"); // OBS! 777 is "fake" -> kundId is defined on the serverSide
        map_rut.put(DB.BUH_FAKTURA_RUT__FAKTURAID, fakturaId); // 339 -> Trell faktura: 10114
        map_rut.put(DB.BUH_FAKTURA_RUT__SKATTEREDUKTION, "" + rutRotFrame.AVDRAG_TOTAL);
        map_rut.put(DB.BUH_FAKTURA_RUT__DATE_CREATED, GP_BUH.getDateCreated_special());
        //
        String json = JSon.hashMapToJSON(map_rut);
        //
        String rutId = null;
        //
        try {
            //
            rutId = executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_FAKTURA_RUT_ENTRY_TO_DB, json);
            //
            System.out.println("rutId of inserted entry: " + rutId);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (rutId == null || rutId.isEmpty()) {
            return;
        }
        //
        //
        //
        JTable table_person = rutRotFrame.jTable3_persons;
        //
        for (int row = 0; row < table_person.getRowCount(); row++) {
            //
            HashMap<String, String> map_rut_person = new HashMap<>();
            //
            String avdrag = HelpA.getValueGivenRow(table_person, row, COL__AVDRAG);
            String namn = HelpA.getValueGivenRow(table_person, row, COL__FORNAMN);
            String efternamn = HelpA.getValueGivenRow(table_person, row, COL__EFTERNAMN);
            String pnr = HelpA.getValueGivenRow(table_person, row, COL__PNR);
            //
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__RUTID, rutId);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__FAKTURAID, fakturaId);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__KUNDID, "777");
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__SKATTEREDUKTION, avdrag);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__FORNAMN, namn);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN, efternamn);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__PNR, pnr);
            //
            String json_ = JSon.hashMapToJSON(map_rut_person);
            //
            try {
                //
                executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_FAKTURA_RUT_PERSON_ENTRY_TO_DB, json_);
                //
            } catch (Exception ex) {
                Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //
            //
            //
        }
        //
    }

    public HashMap<String, String> getFakturaPreview_rut() {
        //
        HashMap<String, String> map_rut = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN);
        //
        map_rut.put(DB.BUH_FAKTURA_RUT__SKATTEREDUKTION, "" + rutRotFrame.AVDRAG_TOTAL);
        //
        return map_rut;
        //
    }

    public ArrayList<HashMap<String, String>> getFakturaPreview_rut_pers() {
        //
        JTable table_person = rutRotFrame.jTable3_persons;
        //
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        //
        for (int row = 0; row < table_person.getRowCount(); row++) {
            //
            HashMap<String, String> map_rut_person = new HashMap<>();
            //
            String avdrag = HelpA.getValueGivenRow(table_person, row, COL__AVDRAG);
            String namn = HelpA.getValueGivenRow(table_person, row, COL__FORNAMN);
            String efternamn = HelpA.getValueGivenRow(table_person, row, COL__EFTERNAMN);
            String pnr = HelpA.getValueGivenRow(table_person, row, COL__PNR);
            //
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__SKATTEREDUKTION, avdrag);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__FORNAMN, namn);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN, efternamn);
            map_rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__PNR, pnr);
            //
            list.add(map_rut_person);
            //
        }
        //
        return list;
        //
    }

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "rut_person");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(getTableInvertPanel());
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert fornamn = new RowDataInvertB(FORNAMN, DB.BUH_FAKTURA_RUT_PERSON__FORNAMN, COL__FORNAMN, "", true, true, true);
        //
        RowDataInvert efternamn = new RowDataInvertB(EFTERNAMN, DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN, COL__EFTERNAMN, "", true, true, true);
        //
        RowDataInvert pnr = new RowDataInvertB(PNR, DB.BUH_FAKTURA_RUT_PERSON__PNR, COL__PNR + " (YYMMDD-XXXX)", "", true, true, true);
        //
        RowDataInvert avdrags_tak = new RowDataInvertB("" + rutRotFrame.defineAvdragsTak(), DB.BUH_FAKTURA_RUT_PERSON__AVDRAGSTAK_VALUE_NOT_AQUIRE, COL__AVDRAGS_TAK, "", false, true, true);
        avdrags_tak.setDontAquireTableInvertToHashMap();
        //
        RowDataInvert[] rows = {
            fornamn,
            efternamn,
            pnr,
            avdrags_tak
        };
        //
        return rows;
    }

    public void showTableInvert_2() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "rut");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(getTableInvertPanel_2(), TABLE_INVERT_2);
        //
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert fastighets_beteckning = new RowDataInvertB("", DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING, COL__FATSTIGHETS_BETECKNING, "", true, true, true);
        //
        RowDataInvert[] rows = {
            fastighets_beteckning
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
        if (col_name.equals(DB.BUH_FAKTURA_RUT_PERSON__PNR)) {
            //
            if (Validator.validateMaxInputLength(jli, 11)) {
                Validator.validatePnr(jli);
                pnr_additional(jli, ti);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_RUT_PERSON__FORNAMN)
                || col_name.equals(DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN)
                || col_name.equals(DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING)) {
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        }
    }

    @Override
    protected void startUp() {
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())
                || containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())
                || containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
    }

}
