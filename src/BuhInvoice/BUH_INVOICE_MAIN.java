/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.JTextAreaJLink;
import BuhInvoice.sec.LANG;
import forall.HelpA;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class BUH_INVOICE_MAIN extends javax.swing.JFrame implements MouseListener, KeyListener {

    private InvoiceA_Insert invoiceA_insert;
    private InvoiceA_Update invoiceA_update;
    private CustomersA_ customersA;
    private ForetagA foretagA;
    private ArticlesA articlesA;
    protected InvoiceB invoiceB;

    private String ACTUAL_TAB_NAME;
    private String PREVIOUS_TAB_NAME;
    public final static String TAB_INVOICES_OVERVIEW = "ALLA FAKTUROR";
    private final static String TAB_FAKTURA = "FAKTURA";
    public final static String TAB_KUDNER = "KUNDER";
    private final static String TAB_ARTIKLAR = "ARTIKLAR";
    private final static String TAB_FTG_SETUP = "FÖRETAGS INSTÄLLNINGAR";
    //
    private ArrayList<HashMap<String, String>> ARTICLES_ACTUAL_INVOICE;
    //
    public String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR;
    private final ArrayList<JToggleButton> toggleBtnList = new ArrayList<>();

    /**
     * Creates new form BUH_INVOICE_MAIN
     */
    public BUH_INVOICE_MAIN() {
        initComponents();
        initOhter();
    }

    protected void RESET_SEARCH_FILTER() {
        PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR;
    }

    protected boolean isInitialFilter() {
        if (PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR)) {
            return true;
        } else {
            return false;
        }
    }

    private void initOhter() {
        //
        this.jTabbedPane1.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addKeyListener(this);
        //
        this.jTable_InvoiceA_Insert_articles.addMouseListener(this);
        this.jTable_kunder.addMouseListener(this);
        this.jTable_kund_adresses.addMouseListener(this);
        this.jTable_ArticlesA_articles.addMouseListener(this);
        //
        this.jTextArea_faktura_komment.addKeyListener(this);
        //
        invoiceB = new InvoiceB(this);
        HelpA.markFirstRowJtable(jTable_invoiceB_alla_fakturor);
        jtable_InvoiceB_all_invoices_clicked();
        //
        invoiceA_update = new InvoiceA_Update(this);
        //
        setUneditableAllJTables();
        //
        this.setTitle("Fakturering");
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //
        initToggleBtnList();
        //
    }

    private void initToggleBtnList() {
        toggleBtnList.add(jToggleButton_not_send_filter);
        toggleBtnList.add(jToggleButton_obetald_filter);
        toggleBtnList.add(jToggleButton_delvis_betald_filter);
        toggleBtnList.add(jToggleButton_makulerad_filter);
        toggleBtnList.add(jToggleButton_act_month_filter);
        toggleBtnList.add(jToggleButton_forfallen_filter);
    }

    protected void untoggleAll() {
        for (JToggleButton btn : toggleBtnList) {
            btn.setSelected(false);
        }
    }

    private void untoggleAllExcept(JToggleButton exceptBtn) {
        for (JToggleButton btn : toggleBtnList) {
            if (btn.equals(exceptBtn) == false) {
                btn.setSelected(false);
            }
        }
    }

    private void setUneditableAllJTables() {
        HelpA.setUneditableJTable(jTable_invoiceB_alla_fakturor);
        HelpA.setUneditableJTable(jTable_invoiceB_faktura_artiklar);
        HelpA.setUneditableJTable(jTable_InvoiceA_Insert_articles);
        HelpA.setUneditableJTable(jTable_ArticlesA_articles);
        HelpA.setUneditableJTable(jTable_ftg);
        HelpA.setUneditableJTable(jTable_ftg_addr);
        HelpA.setUneditableJTable(jTable_kund_adresses);
        HelpA.setUneditableJTable(jTable_kunder);
    }

    protected void hideShowButtonsDependingOnConditions() {
        //
        if (jTable_invoiceB_alla_fakturor.getRowCount() == 0) {
            jButton_edit_faktura.setEnabled(false);
            jButton_copy_faktura.setEnabled(false);
            jButton_kredit_faktura.setEnabled(false);
            jButton_delete_faktura.setEnabled(false);
            jButton_print_faktura.setEnabled(false);
            jButton_inbetalning.setEnabled(false);
            jButton_show_actions.setEnabled(false);
            jButton_send_reminder.setEnabled(false);
            return;
        }
        //
        if (isKreditFaktura()) {
            jButton_kredit_faktura.setEnabled(false);
            jButton_copy_faktura.setEnabled(false);
            jButton_inbetalning.setEnabled(false);
            jButton_send_reminder.setEnabled(false);
        } else if (isKontantFaktura()) {
            jButton_kredit_faktura.setEnabled(false);
            jButton_send_reminder.setEnabled(false);
            setEnabledCommonSet();
        } else {
            jButton_send_reminder.setEnabled(true);
            jButton_kredit_faktura.setEnabled(true);
            setEnabledCommonSet();
        }
        //
    }

    private void setEnabledCommonSet() {
        jButton_copy_faktura.setEnabled(true);
        jButton_inbetalning.setEnabled(true);
        jButton_print_faktura.setEnabled(true);
        jButton_delete_faktura.setEnabled(true);
        jButton_show_actions.setEnabled(true);
        jButton_edit_faktura.setEnabled(true);
    }

    protected void displayArticlesCount(){
        invoiceB.displayArticlesCount();
    }
    
    protected void setArticlesActualInvoice(ArrayList<HashMap<String, String>> list) {
        this.ARTICLES_ACTUAL_INVOICE = list;
    }

    protected ArrayList<HashMap<String, String>> getArticlesActualInvoice() {
        return this.ARTICLES_ACTUAL_INVOICE;
    }

    /**
     * OBS! Note if "kundId" does not exist it will not be possible to insert a
     * invoice because of the "foreign key constraints".
     *
     * It will maybe make sense to verify if the "kundId" exist in cloud
     *
     * @return
     */
    protected String getKundId() {
        return "1";
    }

    protected boolean noCustomersPresent() {
        return invoiceB.noCustomersPresent();
    }

    protected String getFakturaId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
    }

    protected void deleteFaktura(String fakturaId) {
        invoiceB.deleteFakturaPrimary(fakturaId);
    }

    protected String getFakturaNr() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURANR);
    }

    protected String getFakturaKund() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KUND);
    }

    protected String getFakturaKundId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KUND_ID);
    }

    protected double getFakturaTotal() {
        return Double.parseDouble(HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
    }

    protected String getFakturaArtikelId() {
        return HelpA.getValueSelectedRow(jTable_InvoiceA_Insert_articles, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
    }

    protected String getFakturaType() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
    }

     protected boolean fakturaBetald() {
        //
        JTable table = jTable_invoiceB_alla_fakturor;
        //
        if(table.getRowCount() == 0){
            return false;
        }
        //
        String betald = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__BETALD);
        //
        return !betald.equals(DB.STATIC__NO) && !betald.equals(DB.STATIC_BET_STATUS_KREDIT);
        //
    }
    
    protected boolean isMakulerad() {
        //
        String makulerad = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD);
        //
        if (makulerad.equals(DB.STATIC__YES)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isKreditFaktura() {
        //
        String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
        //
        if (fakturaTyp == null) {
            return false;
        }
        //
        if (fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isKontantFaktura() {
        //
        String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
        //
        if (fakturaTyp == null) {
            return false;
        }
        //
        if (fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * OBS! Remember the "komment" field is used internally for different
     * purposes. One of usages is to save the "krediterad" invoice number
     * [2020-09-15]
     *
     * @return
     */
    protected String getKomment_$() {
        String val = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KOMMENT_$);
        return GP_BUH.getValNoNull(val);
    }

    protected String getSELECT_fakturaId() {
        return getSELECT(DB.BUH_FAKTURA__ID__, getFakturaId());
    }

    protected String getSELECT_kundId() {
        return getSELECT(DB.BUH_FAKTURA__KUNDID__, getKundId());
    }

    protected String getSELECT_kundId__doubleWhere(String secondWhereValue) {
        return getSELECT_doubleWhere(DB.BUH_FAKTURA__KUNDID__, getKundId(), DB.BUH_FAKTURA_KUND__ID, secondWhereValue);
    }

    protected String getSELECT_fakturaKundId(String fakturaKundId) {
        return getSELECT(DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
    }

    protected String getSELECT(String whereColName, String whereValue) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getSELECT_doubleWhere(String whereColName, String whereValue, String whereColName_b, String whereValue_b) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        map.put("where_b", whereColName_b);
        map.put(whereColName_b, whereValue_b);
        //
        return JSon.hashMapToJSON(map);
    }

    /**
     * OBS! this function returns only basic parameters for "update". So you
     * will have to add the update values after calling this function.
     *
     * @param whereColName
     * @param whereValue
     * @param tableName
     * @return
     */
    protected HashMap<String, String> getUPDATE(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }

    protected static HashMap<String, String> getUPDATE_static(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }

    protected HashMap<String, String> getDELETE(String whereColName, String whereValue, String tableName) {
        return getUPDATE(whereColName, whereValue, tableName);
    }

    protected String getExist(String columnName, String value, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("value", value);
        map.put("table", tableName);
        map.put("kundId", getKundId());
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getLatest(String columnName, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("table", tableName);
        map.put("kundId", getKundId());
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getForeignKeyBindings(JTable table, String idColName, String whereParam, String phpFunc, String getFromMapParam) {
        //
        String returnStr = "";
        //
        String id = HelpA.getValueSelectedRow(table, idColName);
        //
        String json = getSELECT(whereParam, id);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpFunc, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String rst = map.get(getFromMapParam);
                //
                returnStr += rst + ",";
                //
            }
            //
            if (returnStr.isEmpty()) {
                return returnStr;
            } else {
                return JSon.delete_last_letter_in_string(returnStr, ",");
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public void setValueAllInvoicesJTable(String colName, String value) {
        HelpA.setValueCurrentRow(jTable_invoiceB_alla_fakturor, colName, value);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_invoiceB_alla_fakturor = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_invoiceB_faktura_artiklar = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton_edit_faktura = new javax.swing.JButton();
        jButton_create_new_faktura = new javax.swing.JButton();
        jButton_kontant_faktura = new javax.swing.JButton();
        jButton_kredit_faktura = new javax.swing.JButton();
        jButton_copy_faktura = new javax.swing.JButton();
        jButton_delete_faktura = new javax.swing.JButton();
        jButton_print_faktura = new javax.swing.JButton();
        jLabel_btn_separator_1 = new javax.swing.JLabel();
        jButton_send_reminder = new javax.swing.JButton();
        jButton_inbetalning = new javax.swing.JButton();
        jButton_show_actions = new javax.swing.JButton();
        jLabel_all_invoices_list = new javax.swing.JLabel();
        jLabel_all_invoices_list1 = new javax.swing.JLabel();
        jTextArea_faktura_komment = new JTextAreaJLink();
        jButton4_save_faktura_komment = new javax.swing.JButton();
        jButton4_delete_faktura_komment1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jToggleButton_obetald_filter = new javax.swing.JToggleButton();
        jToggleButton_forfallen_filter = new javax.swing.JToggleButton();
        jToggleButton_delvis_betald_filter = new javax.swing.JToggleButton();
        jToggleButton_not_send_filter = new javax.swing.JToggleButton();
        jToggleButton_makulerad_filter = new javax.swing.JToggleButton();
        jToggleButton_act_month_filter = new javax.swing.JToggleButton();
        jComboBox_faktura_kunder_filter = new javax.swing.JComboBox<>();
        jButton_search_by_kund = new javax.swing.JButton();
        jScrollPane1_faktura = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2_faktura_main = new javax.swing.JPanel();
        jPanel_articles = new JPanel();
        jPanel3_faktura_sec = new javax.swing.JPanel();
        jLabel_Faktura_Insert_or_Update = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_InvoiceA_Insert_articles = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton_confirm_insert_update = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton_add_article = new javax.swing.JButton();
        jButton_update_articles_row = new javax.swing.JButton();
        jButton_delete_articles_row = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_total_exkl_moms = new javax.swing.JTextField();
        jTextField_moms = new javax.swing.JTextField();
        jTextField_rabatt_total = new javax.swing.JTextField();
        jTextField_total_inkl_moms = new javax.swing.JTextField();
        jLabel_ammount_of_articles = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel4_Customers = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_kunder = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_kund_adresses = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton_update_kund = new javax.swing.JButton();
        jButton_delete_customer = new javax.swing.JButton();
        jLabel_Kund_Insert_or_Update = new javax.swing.JLabel();
        jPanel4_Articles = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_ArticlesA_articles = new javax.swing.JTable();
        jLabel_Artikel_Insert_or_Update = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton_update_article = new javax.swing.JButton();
        jButton_delete_article = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable_ftg = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable_ftg_addr = new javax.swing.JTable();
        jButton_update_kund_data = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1290, 500));

        jPanel3.setPreferredSize(new java.awt.Dimension(1288, 1000));

        jTable_invoiceB_alla_fakturor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable_invoiceB_alla_fakturor);

        jTable_invoiceB_faktura_artiklar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable_invoiceB_faktura_artiklar);

        jPanel4.setLayout(new java.awt.GridLayout(1, 8));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh.png"))); // NOI18N
        jButton15.setToolTipText("Uppdatera listan");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15);

        jButton_edit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton_edit_faktura.setToolTipText("Bearbeta faktura");
        jButton_edit_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_edit_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_edit_faktura);

        jButton_create_new_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton_create_new_faktura.setToolTipText("Skapa ny faktura");
        jButton_create_new_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_create_new_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_create_new_faktura);

        jButton_kontant_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/kontantfaktura.png"))); // NOI18N
        jButton_kontant_faktura.setToolTipText("Skapa ny kontantfaktura");
        jButton_kontant_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kontant_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_kontant_faktura);

        jButton_kredit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_b.png"))); // NOI18N
        jButton_kredit_faktura.setToolTipText("Skapa kreditfaktura - välj först en faktura ur listan och tryck sedan på knappen");
        jButton_kredit_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kredit_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_kredit_faktura);

        jButton_copy_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copy.png"))); // NOI18N
        jButton_copy_faktura.setToolTipText("Kopiera faktura");
        jButton_copy_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_copy_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_copy_faktura);

        jButton_delete_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_faktura.setToolTipText("Radera faktura");
        jButton_delete_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_delete_faktura);

        jButton_print_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton_print_faktura.setToolTipText("Skriv ut faktura");
        jButton_print_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_print_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_print_faktura);
        jPanel4.add(jLabel_btn_separator_1);

        jButton_send_reminder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bell.png"))); // NOI18N
        jButton_send_reminder.setToolTipText("Skicka påminnelse");
        jButton_send_reminder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_reminderActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_send_reminder);

        jButton_inbetalning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payed.png"))); // NOI18N
        jButton_inbetalning.setToolTipText("Registrera inbetalning");
        jButton_inbetalning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_inbetalningActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_inbetalning);

        jButton_show_actions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/event.png"))); // NOI18N
        jButton_show_actions.setToolTipText("Se historia över skickade e-post och påminnelser");
        jButton_show_actions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_show_actionsActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_show_actions);

        jLabel_all_invoices_list.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list.setText("FAKTUROR");

        jLabel_all_invoices_list1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list1.setText("FAKTURA ARTIKLAR");

        jTextArea_faktura_komment.setColumns(20);
        jTextArea_faktura_komment.setForeground(new java.awt.Color(102, 102, 102));
        jTextArea_faktura_komment.setLineWrap(true);
        jTextArea_faktura_komment.setRows(2);
        jTextArea_faktura_komment.setToolTipText("Skriv faktura kommentar här");
        jTextArea_faktura_komment.setWrapStyleWord(true);

        jButton4_save_faktura_komment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_sm.png"))); // NOI18N
        jButton4_save_faktura_komment.setToolTipText("Uppdatera faktura kommentar");
        jButton4_save_faktura_komment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_save_faktura_kommentActionPerformed(evt);
            }
        });

        jButton4_delete_faktura_komment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_sm.png"))); // NOI18N
        jButton4_delete_faktura_komment1.setToolTipText("Radera faktura kommentar");
        jButton4_delete_faktura_komment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_delete_faktura_komment1ActionPerformed(evt);
            }
        });

        jPanel18.setLayout(new java.awt.GridLayout(1, 4));

        jToggleButton_obetald_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_obetald_filter.setText("OBETALDA");
        jToggleButton_obetald_filter.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jToggleButton_obetald_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_obetald_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_obetald_filter);

        jToggleButton_forfallen_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_forfallen_filter.setText("FÖRFALLNA");
        jToggleButton_forfallen_filter.setToolTipText("Visar förfallna, ej betalda, ej makulerade fakturor av typen NORMAL");
        jToggleButton_forfallen_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_forfallen_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_forfallen_filter);

        jToggleButton_delvis_betald_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_delvis_betald_filter.setText("DELVIS BETALD");
        jToggleButton_delvis_betald_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_delvis_betald_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_delvis_betald_filter);

        jToggleButton_not_send_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_not_send_filter.setText("EJ SKICKADE");
        jToggleButton_not_send_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_not_send_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_not_send_filter);

        jToggleButton_makulerad_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_makulerad_filter.setText("MAKULERADE");
        jToggleButton_makulerad_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_makulerad_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_makulerad_filter);

        jToggleButton_act_month_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_act_month_filter.setText("AKT. MÅNAD");
        jToggleButton_act_month_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_act_month_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_act_month_filter);

        jComboBox_faktura_kunder_filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        jButton_search_by_kund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jButton_search_by_kund.setToolTipText("Sök");
        jButton_search_by_kund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_search_by_kundActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(1043, 1043, 1043)
                            .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2)
                                    .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jComboBox_faktura_kunder_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton_search_by_kund, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_faktura_kunder_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_search_by_kund, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );

        jScrollPane3.setViewportView(jPanel3);

        jTabbedPane1.addTab("ALLA FAKTUROR", jScrollPane3);

        jPanel1.setPreferredSize(new java.awt.Dimension(1286, 1000));

        jPanel2_faktura_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2_faktura_main.setLayout(new java.awt.BorderLayout());

        jPanel_articles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_articles.setLayout(new java.awt.BorderLayout());

        jPanel3_faktura_sec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3_faktura_sec.setLayout(new java.awt.GridLayout(1, 0));

        jLabel_Faktura_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_Faktura_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jTable_InvoiceA_Insert_articles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable_InvoiceA_Insert_articles);

        jPanel2.add(jScrollPane2);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton11.setToolTipText("Skapa ny faktura");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton11);

        jButton_confirm_insert_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_confirm_insert_update.setToolTipText("Spara faktura");
        jButton_confirm_insert_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirm_insert_updateActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_confirm_insert_update);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton19.setToolTipText("Bearbeta artikel");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton19);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton20.setToolTipText("Bekräfta ändringar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton20);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3));

        jButton_add_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_add_article.setToolTipText("Lägg till artikel");
        jButton_add_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_add_articleActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_add_article);

        jButton_update_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_articles_row.setToolTipText("Uppdatera artikel");
        jButton_update_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_update_articles_row);

        jButton_delete_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_articles_row.setToolTipText("Radera artikel");
        jButton_delete_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_delete_articles_row);

        jPanel15.setLayout(new java.awt.GridLayout(2, 4));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Exkl. moms");
        jPanel15.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Moms");
        jPanel15.add(jLabel3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(103, 103, 103));
        jLabel1.setText("Rabatt ");
        jPanel15.add(jLabel1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Total");
        jPanel15.add(jLabel4);

        jTextField_total_exkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_exkl_moms.setText("0");
        jTextField_total_exkl_moms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_total_exkl_momsActionPerformed(evt);
            }
        });
        jPanel15.add(jTextField_total_exkl_moms);

        jTextField_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms.setText("0");
        jPanel15.add(jTextField_moms);

        jTextField_rabatt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_rabatt_total.setText("0");
        jPanel15.add(jTextField_rabatt_total);

        jTextField_total_inkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_inkl_moms.setText("0");
        jPanel15.add(jTextField_total_inkl_moms);

        jLabel_ammount_of_articles.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_ammount_of_articles.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 475, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_ammount_of_articles, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ammount_of_articles))
                .addGap(1520, 1520, 1520))
        );

        jScrollPane1_faktura.setViewportView(jPanel1);

        jTabbedPane1.addTab("FAKTURA", jScrollPane1_faktura);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable_kunder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable_kunder);

        jTable_kund_adresses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable_kund_adresses);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(555, 416));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton13.setToolTipText("Skapa ny");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton13);

        jButton_update_kund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund.setToolTipText("Uppdatera faktura kund data");
        jButton_update_kund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kundActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_update_kund);

        jButton_delete_customer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_customer.setToolTipText("Radera kund");
        jButton_delete_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_customerActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_delete_customer);

        jLabel_Kund_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Kund_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel4_CustomersLayout = new javax.swing.GroupLayout(jPanel4_Customers);
        jPanel4_Customers.setLayout(jPanel4_CustomersLayout);
        jPanel4_CustomersLayout.setHorizontalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                        .addComponent(jScrollPane8))
                    .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        jPanel4_CustomersLayout.setVerticalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(142, 142, 142))
        );

        jScrollPane9.setViewportView(jPanel4_Customers);

        jTabbedPane1.addTab("KUNDER", jScrollPane9);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jTable_ArticlesA_articles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable_ArticlesA_articles);

        jLabel_Artikel_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Artikel_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel14.setLayout(new java.awt.GridLayout(1, 4));

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton16.setToolTipText("Skapa ny artikel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton16);

        jButton_update_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_article.setToolTipText("Uppdatera artikel data");
        jButton_update_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_update_article);

        jButton_delete_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_article.setToolTipText("Radera artikel");
        jButton_delete_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_delete_article);

        javax.swing.GroupLayout jPanel4_ArticlesLayout = new javax.swing.GroupLayout(jPanel4_Articles);
        jPanel4_Articles.setLayout(jPanel4_ArticlesLayout);
        jPanel4_ArticlesLayout.setHorizontalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        jPanel4_ArticlesLayout.setVerticalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("ARTIKLAR", jPanel4_Articles);

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jTable_ftg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(jTable_ftg);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jTable_ftg_addr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(jTable_ftg_addr);

        jButton_update_kund_data.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund_data.setToolTipText("Uppdatera kund data");
        jButton_update_kund_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kund_dataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        jPanel16.getAccessibleContext().setAccessibleDescription("");

        jScrollPane10.setViewportView(jPanel8);

        jTabbedPane1.addTab("FÖRETAGS INSTÄLLNINGAR", jScrollPane10);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton_delete_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_fakturaActionPerformed
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_1) == false) {
            return;
        }
        //
        deleteFaktura(getFakturaId());
        //
    }//GEN-LAST:event_jButton_delete_fakturaActionPerformed


    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        customersA.createNewFakturaKund();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton_update_kundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kundActionPerformed
        if (customersA.getCurrentOperationInsert()) {
            if (customersA.fieldsValidated(true)) {
                customersA.insert();
            }
        } else {
            if (customersA.fieldsValidated(false)) {
                customersA.update();
            }
        }
    }//GEN-LAST:event_jButton_update_kundActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //"Refresh btn"
        invoiceB.refresh(null);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton_delete_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_customerActionPerformed
        //
        if (HelpA.rowSelected(jTable_kunder) == false) {
            return;
        }
        //
        customersA.delete();
        invoiceB.refresh(null);
    }//GEN-LAST:event_jButton_delete_customerActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        articlesA.createNew();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton_delete_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articleActionPerformed
        articlesA.delete();
    }//GEN-LAST:event_jButton_delete_articleActionPerformed

    private void jButton_update_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articleActionPerformed
        if (articlesA.getCurrentOperationInsert()) {
            if (articlesA.fieldsValidated(true)) {
                articlesA.insert();
            }
        } else {
            if (articlesA.fieldsValidated(false)) {
                articlesA.update();
            }
        }

    }//GEN-LAST:event_jButton_update_articleActionPerformed

    private void jButton_edit_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_edit_fakturaActionPerformed
        editFakturaBtnKlicked();
    }//GEN-LAST:event_jButton_edit_fakturaActionPerformed

    public void editFakturaBtnKlicked() {
        HelpA.openTabByName(jTabbedPane1, TAB_FAKTURA);
        fakturaTabClicked();
    }

    private void jButton_print_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_print_fakturaActionPerformed
        String fakturatype = getFakturaType();
//        System.out.println("AA " + fakturaTyp);
        invoiceB.htmlFakturaOrReminder(fakturatype, false);
    }//GEN-LAST:event_jButton_print_fakturaActionPerformed

    private void jButton_delete_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        if (InvoiceA_Insert.CURRENT_OPERATION_INSERT) {
            //
            invoiceA_insert.deleteArtikel();
            //
            invoiceA_insert.showTableInvert_3(); // some kind of redraw, OBS! needed -> see: "Invoice_.disableMomsJComboIf()"
            //
        } else {
            //
            invoiceA_update.deleteFakturaArtikel();
            invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
            invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
            invoiceA_update.insertOrUpdate(); // Update entire faktura after delete
            invoiceB.refresh_c();
            //
            invoiceA_update.showTableInvert_2(); // some kind of redraw, OBS! needed
            invoiceA_update.showTableInvert_3(); // some kind of redraw, OBS! needed
            //
        }
        //
        displayArticlesCount();
        //
    }//GEN-LAST:event_jButton_delete_articles_rowActionPerformed

    private void jButton_update_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            GP_BUH.confirmWarning(LANG.MSG_5);
            return;
        }
        //
        if (invoiceA_update.fieldsValidatedArticle()) {
            //
            invoiceA_update.updateArticle();
            //
            invoiceA_update.insertOrUpdate(); // update entire faktura on updated article
            //
            int selected_row = jTable_InvoiceA_Insert_articles.getSelectedRow();
            //
            invoiceB.refresh_c();
            //
            HelpA.markGivenRow(jTable_InvoiceA_Insert_articles, selected_row);
            jTable_InvoiceA_Insert_articles_clicked();
            //
        }
        //
    }//GEN-LAST:event_jButton_update_articles_rowActionPerformed

    private void jButton_add_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_add_articleActionPerformed
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
                    if (invoiceA_insert.fieldsValidatedArticle()) {
                        invoiceA_insert.addArticle();
                    }
                } else {
                    if (invoiceA_update.fieldsValidatedArticle()) {
                        invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
                        invoiceA_update.addArticle();
                        invoiceB.refresh_c(); // correct
                    }
                }
                //
                displayArticlesCount();
                //
            }
        });
        //
    }//GEN-LAST:event_jButton_add_articleActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        invoiceA_insert.submitEditedArticle();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        InvoiceA_Insert.EDIT__ARTICLE_UPPON_INSERT__SWITCH = true;
        invoiceA_insert.showTableInvert_2();
        invoiceA_insert.refreshTableInvert(invoiceA_insert.TABLE_INVERT_2);
        //
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton_confirm_insert_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirm_insert_updateActionPerformed
        // "FAKTURA HTTP" BTN
        if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
            if (invoiceA_insert.fieldsValidated(true)) {
                invoiceA_insert.insertOrUpdate();
                invoiceA_insert.resetSavedMoms_jCombo();
                invoiceB.refresh(null);
                HelpA.openTabByName(jTabbedPane1, BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
            }
            //
            boolean isKontantFaktura = isKontantFaktura();
            //
            if (isKontantFaktura) {
                makeInbetalning(isKontantFaktura, true);
            }
            //
        } else {
            if (invoiceA_update.fieldsValidated(true)) {
                invoiceA_update.insertOrUpdate();
                invoiceA_update.resetSavedMoms_jCombo();
                invoiceB.refresh_c();
                HelpA.openTabByName(jTabbedPane1, BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
            }
        }
        //

        //
    }//GEN-LAST:event_jButton_confirm_insert_updateActionPerformed

    private void createNewFaktura(boolean isKontantfaktura) {
        //
        if (invoiceA_insert == null) {
            invoiceA_insert = new InvoiceA_Insert(this);
        }
        //
        if (noCustomersPresent()) {
            HelpA.showNotification_separate_thread(LANG.MSG_11);
        }
        //
        invoiceA_insert.resetSavedMoms_jCombo();
        //
        invoiceA_insert.createNew(isKontantfaktura);
        //
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_5_1) == false) {
            return;
        }
        //
        createNewFaktura(false);
        //
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField_total_exkl_momsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_total_exkl_momsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_total_exkl_momsActionPerformed

    private void jButton_update_kund_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kund_dataActionPerformed
        if (foretagA.fieldsValidated(false)) {
            foretagA.update();
        }
    }//GEN-LAST:event_jButton_update_kund_dataActionPerformed

    private void jButton_inbetalningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inbetalningActionPerformed
        makeInbetalning(isKontantFaktura(), false);
    }//GEN-LAST:event_jButton_inbetalningActionPerformed

    private void makeInbetalning(boolean isKontantFaktura, boolean directlyAfterCreation) {
        //
        EditPanel_Inbet epb = new EditPanel_Inbet(this, isKontantFaktura, getFakturaId(), getFakturaNr(), getFakturaKund());
        //
        if (directlyAfterCreation) {
            epb.setHeaderAfterCreationOfKontantFaktura();
        }
        //
        GP_BUH.centerAndBringToFront(epb);
        //
    }

    private void jButton_show_actionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_show_actionsActionPerformed
        EditPanel_Send eps = new EditPanel_Send(this, isKontantFaktura(), getFakturaId(), getFakturaNr(), getFakturaKund());
        GP_BUH.centerAndBringToFront(eps);
    }//GEN-LAST:event_jButton_show_actionsActionPerformed

    private void jButton_create_new_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_create_new_fakturaActionPerformed
        HelpA.openTabByName(jTabbedPane1, TAB_FAKTURA);
        createNewFaktura(false);
    }//GEN-LAST:event_jButton_create_new_fakturaActionPerformed

    private void jButton4_save_faktura_kommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_save_faktura_kommentActionPerformed
        invoiceB.updateKomment(false);
    }//GEN-LAST:event_jButton4_save_faktura_kommentActionPerformed

    private void jButton_copy_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_copy_fakturaActionPerformed
        copyFaktura_createKreditFaktura(false);
    }//GEN-LAST:event_jButton_copy_fakturaActionPerformed

    private void copyFaktura_createKreditFaktura(boolean isKreditFaktura) {
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        String msg;
        //
        if (isKreditFaktura) {
            msg = LANG.FAKTURA_KREDIT_MSG(getFakturaNr());
        } else {
            msg = LANG.FAKTURA_COPY_MSG_A(getFakturaNr());
        }
        //
        if (GP_BUH.confirm(msg) == false) {
            return;
        }
        //
        invoiceB.copy(isKreditFaktura);
        //
    }

    private void jButton4_delete_faktura_komment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_delete_faktura_komment1ActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_3) == false) {
            return;
        }
        //
        invoiceB.deleteComment();
    }//GEN-LAST:event_jButton4_delete_faktura_komment1ActionPerformed

    private void jButton_kredit_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kredit_fakturaActionPerformed
        copyFaktura_createKreditFaktura(true); // true = "kreditfaktura"
    }//GEN-LAST:event_jButton_kredit_fakturaActionPerformed

    private void jButton_kontant_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kontant_fakturaActionPerformed
        HelpA.openTabByName(jTabbedPane1, TAB_FAKTURA);
        createNewFaktura(true);
    }//GEN-LAST:event_jButton_kontant_fakturaActionPerformed

    private void jButton_send_reminderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_reminderActionPerformed
        //
        if(fakturaBetald()){
            HelpA.showNotification_separate_thread(LANG.MSG_12);
        }
        //
        invoiceB.htmlFakturaOrReminder(null, true);
    }//GEN-LAST:event_jButton_send_reminderActionPerformed


    private void jToggleButton_obetald_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_obetald_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__OBETALD, evt);
    }//GEN-LAST:event_jToggleButton_obetald_filterActionPerformed

    private void jToggleButton_not_send_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_not_send_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__EJ_SKICKAD, evt);
    }//GEN-LAST:event_jToggleButton_not_send_filterActionPerformed

    private void jToggleButton_delvis_betald_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_delvis_betald_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__DELVIS_BETALD, evt);
    }//GEN-LAST:event_jToggleButton_delvis_betald_filterActionPerformed

    private void jToggleButton_makulerad_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_makulerad_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD, evt);
    }//GEN-LAST:event_jToggleButton_makulerad_filterActionPerformed

    private void jButton_search_by_kundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_search_by_kundActionPerformed
        PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND;
        String fakturaKundId = HelpA.getComboBoxSelectedValue(jComboBox_faktura_kunder_filter, 2);
        untoggleAll();
        invoiceB.refresh(fakturaKundId);
    }//GEN-LAST:event_jButton_search_by_kundActionPerformed

    private void jToggleButton_act_month_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_act_month_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH, evt);
    }//GEN-LAST:event_jToggleButton_act_month_filterActionPerformed

    private void jToggleButton_forfallen_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_forfallen_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FORFALLEN, evt);
    }//GEN-LAST:event_jToggleButton_forfallen_filterActionPerformed

    private void toggleFilterBtnPressed(String phpFunc, ActionEvent evt) {
        //
        JToggleButton jtb = (JToggleButton) evt.getSource();
        //
        if (jtb.isSelected() == true) {
            PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = phpFunc;
            invoiceB.refresh(null);
            untoggleAllExcept((JToggleButton) evt.getSource());
        }else{
            jtb.setSelected(false);
            invoiceB.refresh(null);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BUH_INVOICE_MAIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    protected javax.swing.JButton jButton4_delete_faktura_komment1;
    protected javax.swing.JButton jButton4_save_faktura_komment;
    protected javax.swing.JButton jButton_add_article;
    protected javax.swing.JButton jButton_confirm_insert_update;
    private javax.swing.JButton jButton_copy_faktura;
    private javax.swing.JButton jButton_create_new_faktura;
    protected javax.swing.JButton jButton_delete_article;
    protected javax.swing.JButton jButton_delete_articles_row;
    protected javax.swing.JButton jButton_delete_customer;
    private javax.swing.JButton jButton_delete_faktura;
    private javax.swing.JButton jButton_edit_faktura;
    private javax.swing.JButton jButton_inbetalning;
    private javax.swing.JButton jButton_kontant_faktura;
    private javax.swing.JButton jButton_kredit_faktura;
    protected javax.swing.JButton jButton_print_faktura;
    protected javax.swing.JButton jButton_search_by_kund;
    private javax.swing.JButton jButton_send_reminder;
    private javax.swing.JButton jButton_show_actions;
    protected javax.swing.JButton jButton_update_article;
    protected javax.swing.JButton jButton_update_articles_row;
    protected javax.swing.JButton jButton_update_kund;
    protected javax.swing.JButton jButton_update_kund_data;
    protected javax.swing.JComboBox<String> jComboBox_faktura_kunder_filter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    protected javax.swing.JLabel jLabel_Artikel_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Faktura_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Kund_Insert_or_Update;
    protected javax.swing.JLabel jLabel_all_invoices_list;
    protected javax.swing.JLabel jLabel_all_invoices_list1;
    protected javax.swing.JLabel jLabel_ammount_of_articles;
    private javax.swing.JLabel jLabel_btn_separator_1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    protected javax.swing.JPanel jPanel11;
    protected javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    protected javax.swing.JPanel jPanel16;
    protected javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel2_faktura_main;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel3_faktura_sec;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel4_Articles;
    protected javax.swing.JPanel jPanel4_Customers;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    protected javax.swing.JPanel jPanel_articles;
    private javax.swing.JScrollPane jScrollPane10;
    protected javax.swing.JScrollPane jScrollPane11;
    protected javax.swing.JScrollPane jScrollPane12;
    protected javax.swing.JScrollPane jScrollPane1_faktura;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    protected javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable_ArticlesA_articles;
    protected javax.swing.JTable jTable_InvoiceA_Insert_articles;
    protected javax.swing.JTable jTable_ftg;
    protected javax.swing.JTable jTable_ftg_addr;
    protected javax.swing.JTable jTable_invoiceB_alla_fakturor;
    protected javax.swing.JTable jTable_invoiceB_faktura_artiklar;
    protected javax.swing.JTable jTable_kund_adresses;
    protected javax.swing.JTable jTable_kunder;
    protected javax.swing.JTextArea jTextArea_faktura_komment;
    protected static javax.swing.JTextField jTextField_moms;
    protected static javax.swing.JTextField jTextField_rabatt_total;
    protected static javax.swing.JTextField jTextField_total_exkl_moms;
    protected static javax.swing.JTextField jTextField_total_inkl_moms;
    private javax.swing.JToggleButton jToggleButton_act_month_filter;
    private javax.swing.JToggleButton jToggleButton_delvis_betald_filter;
    private javax.swing.JToggleButton jToggleButton_forfallen_filter;
    private javax.swing.JToggleButton jToggleButton_makulerad_filter;
    private javax.swing.JToggleButton jToggleButton_not_send_filter;
    private javax.swing.JToggleButton jToggleButton_obetald_filter;
    // End of variables declaration//GEN-END:variables

    private void mousePressedOnTab(MouseEvent me) {
        //
        if (me.getSource() == jTabbedPane1) {
            //
            String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            ACTUAL_TAB_NAME = title;
            //
            boolean sameTabClicked = ACTUAL_TAB_NAME.equals(PREVIOUS_TAB_NAME);
            //
            if (ACTUAL_TAB_NAME.equals(TAB_INVOICES_OVERVIEW)) {
                //
                invoiceB.refresh_c();
                jtable_InvoiceB_all_invoices_clicked();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FAKTURA) && sameTabClicked == false) {
                //
                fakturaTabClicked();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_KUDNER)) {
                //
                if (customersA == null) {
                    customersA = new CustomersA_(this);
                } else {
                    jTableCustomersA_kunder_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_ARTIKLAR)) {
                //
                if (articlesA == null) {
                    articlesA = new ArticlesA(this);
                } else {
                    jTableArticles_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FTG_SETUP)) {
                //
                if (foretagA == null) {
                    foretagA = new ForetagA(this);
                } else {
                    jTableForetagA_ftg_table_clicked();
                }
                //
            }
            //
            PREVIOUS_TAB_NAME = ACTUAL_TAB_NAME;
            //
        }
        //
    }

    private void fakturaTabClicked() {
        //
        DefaultTableModel dtm = (DefaultTableModel) jTable_invoiceB_faktura_artiklar.getModel();
        jTable_InvoiceA_Insert_articles.setModel(dtm);
        invoiceB.hideColumnsArticlesTable(jTable_InvoiceA_Insert_articles); //***
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                if (getFakturaNr() == null || getFakturaNr().isEmpty()) {
                    //It's for the cases when the faktura list is empty
                    createNewFaktura(false);
                    return;
                }
                //
                HelpA.markFirstRowJtable(jTable_InvoiceA_Insert_articles);
                jTable_InvoiceA_Insert_articles_clicked();
                //
                invoiceA_update.showTableInvert();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT);
                //
                invoiceA_update.showTableInvert_3();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_3);
                //
                invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
                //
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        mousePressedOnTab(e);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && (e.getClickCount() == 1)) {
            //
            Thread x = new Thread(() -> {
                jtable_InvoiceB_all_invoices_clicked();
            });
            //
            x.start();
            //
        } else if (e.getSource() == jTable_InvoiceA_Insert_articles && (e.getClickCount() == 1)) {
            //
            jTable_InvoiceA_Insert_articles_clicked();
            //
        } else if (e.getSource() == jTable_kunder && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_kunder_clicked();
            //
        } else if (e.getSource() == jTable_kund_adresses && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_adress_clicked();
            //
        } else if (e.getSource() == jTable_ArticlesA_articles && (e.getClickCount() == 1)) {
            //
            jTableArticles_clicked();
            //
        }
    }

    protected void jTableArticles_clicked() {
        if (articlesA.getTableArticles().getRowCount() == 0) {
            articlesA.showTableInvert();
            articlesA.refreshTableInvert();
        } else {
            articlesA.showTableInvert_2();
            articlesA.refreshTableInvert(articlesA.TABLE_INVERT_2);
        }

    }

    protected void jTableCustomersA_adress_clicked() {
        customersA.showTableInvert_3();
        customersA.refreshTableInvert(customersA.TABLE_INVERT_3);
    }

    protected void jTableForetagA_adress_clicked() {
        foretagA.showTableInvert_3();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_3);
    }

    protected void jTableCustomersA_kunder_clicked() {
        //
        if (customersA.getTableMain().getRowCount() == 0) {
            // This makes that when there are no custmers it's opened directly not for "update" but for "insert" [2020-09-29]
            customersA.showTableInvert();
            customersA.refreshTableInvert();
            customersA.showTableInvert_4();
            customersA.refreshTableInvert(customersA.TABLE_INVERT_4);
        } else {
            customersA.showTableInvert_2();
            customersA.refreshTableInvert(customersA.TABLE_INVERT_2);
            //
            customersA.fillAddressTable();
            HelpA.markFirstRowJtable(jTable_kund_adresses);
            jTableCustomersA_adress_clicked();
        }
        //
    }

    protected void jTableForetagA_ftg_table_clicked() {
        //
        foretagA.showTableInvert_2();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_2);
        foretagA.fillAddressTable();
        //
        HelpA.markFirstRowJtable(jTable_ftg_addr);
        jTableForetagA_adress_clicked();
        //
    }

    private void jTable_InvoiceA_Insert_articles_clicked() {
        //
        if (Invoice_.CURRENT_OPERATION_INSERT == false) {
            invoiceA_update.showTableInvert_2();
        }
        //
    }

    protected void jtable_InvoiceB_all_invoices_clicked() {
        //
        String fakturaId = getFakturaId();
        //
        invoiceB.all_invoices_table_clicked(fakturaId);
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
        boolean cond_1 = (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && cond_1) {
            //
            Thread x = new Thread(() -> {
                jtable_InvoiceB_all_invoices_clicked();
            });
            //
            x.start();
            //
        } else if (e.getSource() == jTextArea_faktura_komment) {
            //
            JTextAreaJLink txt = (JTextAreaJLink) jTextArea_faktura_komment;
            //
            Validator.validateMaxInputLength(txt, 100);
            //
            System.out.println("" + txt.getText().length());
            //
        }
        //
    }

}
