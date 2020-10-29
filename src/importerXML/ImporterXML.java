/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package importerXML;

import com.jezhumble.javasysmon.JavaSysMon;
import exporterXML.ExporterXML;
import forall.GP;
import forall.HelpA_;
import forall.Sql_A;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author KOCMOC
 */
public class ImporterXML extends javax.swing.JFrame implements Runnable {

    private JavaSysMon monitor = new JavaSysMon();
    private Document DOCUMENT;
    private Element ROOT;
    private Sql_A sql = new Sql_A();
    public static Properties properties = HelpA_.choose_properties(".");
    private String[] TABLES_TO_IMPORT;

    /**
     * Creates new form ImporterXML
     */
    public ImporterXML() {
        initComponents();
        this.setTitle("XML-to-DB-Importer (" + monitor.currentPid() + ")");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        sql_connect();
        define_tables_to_import();
    }

    private void define_tables_to_import() {
        String tables = properties.getProperty("tables_to_import", "");
        //
        show_info("Tables to import: " + tables);
        //
        TABLES_TO_IMPORT = tables.split(",");
    }

    private void sql_connect() {
        String dbtype = properties.getProperty("db_type");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String db_name = properties.getProperty("db_name");
        String user = properties.getProperty("user");
        String pass = properties.getProperty("pass");
        //!!This is very important, otherwise "meta.getTableName(1)" won't work
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = "false";
        try {
            sql.connect(host, port, db_name, user, pass);
            show_info("connected to " + host + " / " + db_name);
        } catch (SQLException ex) {
            show_info("connection failed: " + ex);
            Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void show_info(String msg) {
        textArea1.append(HelpA_.get_proper_date_time_same_format_on_all_computers() + "    " + msg + "\n");
    }

    private void DEFINE_DOCUMENT_AND_ROOT_READ(String path) {
        try {
            DOCUMENT = create_document_from_existing_doc(path);
        } catch (IOException ex) {
            Logger.getLogger(ImporterXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(ImporterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        ROOT = DOCUMENT.getRootElement();
    }

    private Document create_document_from_existing_doc(String path) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(path);
        return document;
    }

    /**
     *
     * @param table_name
     * @throws JDOMException
     * @throws IOException
     */
    private void read(String table_name) throws JDOMException, IOException {
        //
        DEFINE_DOCUMENT_AND_ROOT_READ("export_" + table_name + ".xml");
        //
        Element table = ROOT.getChild(table_name);
        //
        List column_list = table.getChildren();
        //
        int ROWS = 0;
        boolean calculated = false;
        //================
        //Calculating nr of rows
        for (Object c : column_list) {
            if (calculated) {
                break;
            }
            Element column = (Element) c;
            List rows = column.getChildren();

            for (Object row : rows) {
                ROWS++;
            }
            calculated = true;
        }
        //================
        //================
        System.out.println("rows = " + ROWS);
        //================
        //================
        InsertEntry xml_to_table = new InsertEntry(table_name);
        //
        int COUNTER = 0;
        do {
            //
            System.out.println("========================================");
            //
            for (Object c : column_list) {
                Element column = (Element) c;
//                System.out.println("col = " + column.getText());

                List rows = column.getChildren();
                String var_type = column.getAttributeValue("type");

                for (Object row : rows) {
                    Element column_row = (Element) row;
                    String value = column_row.getValue();
                    xml_to_table.add(new TableRecord(value, var_type));
                    column.removeContent(column_row); // it works -> tested
                    break;
                }
            }
            xml_to_table.entry_complete();
            String q = xml_to_table.build_insert_query();
            //
            //
            record_to_sql(q); //---------------------------->OBS!
            //
            //
            COUNTER++;
        } while (COUNTER < ROWS);
        //

    }

    private void record_to_sql(String q) {
        try {
            sql.execute(q);
        } catch (SQLException ex) {
            if (ex.toString().contains("INSERT statement conflicted with")) {
                show_info("Foreign key constraint error");
            } else {
                Logger.getLogger(ImporterXML.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        textArea1 = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Thread thisThr = new Thread(this);
        thisThr.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    @Override
    public void run() {
        for (String table_name : TABLES_TO_IMPORT) {
            try {
                read(table_name);
                show_info(table_name + " ready");
            } catch (JDOMException ex) {
                Logger.getLogger(ImporterXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImporterXML.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            java.util.logging.Logger.getLogger(ImporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        HelpA_.err_output_to_file();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImporterXML().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public static java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
