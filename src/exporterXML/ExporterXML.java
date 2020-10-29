/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exporterXML;

import com.jezhumble.javasysmon.JavaSysMon;
import static exporterXML.ExporterXML.write_xml_doc_to_file;
import forall.GP;
import forall.HelpA_;
import forall.Sql_A;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author KOCMOC
 */
public class ExporterXML extends javax.swing.JFrame implements Runnable {

    private JavaSysMon monitor = new JavaSysMon();
    private Sql_A sql = new Sql_A();
    private final static Properties properties = HelpA_.choose_properties(".");
    public final static String XML_EXPORTER_MILLS__TABLES_TO_EXPORT = properties.getProperty("tables_to_export", "");
    private ArrayList<Table> TABLES_LIST = new ArrayList<Table>();
    private Document DOCUMENT;
    private Element ROOT;

    /**
     * Creates new form ExporterXML
     */
    public ExporterXML() {
        initComponents();
        this.setTitle("DB-to-XML-Exporter (" + monitor.currentPid() + ")");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        define_tables_list();
        sql_connect();
    }

    private void define_tables_list() {
        String tables = XML_EXPORTER_MILLS__TABLES_TO_EXPORT;
        show_info("Tables to export: " + tables);
        String[] arr = tables.split(",");
        if (arr.length != 0) {
            for (String table_name : arr) {
                String sql_query = properties.getProperty(table_name, "");
                TABLES_LIST.add(new Table(table_name, sql_query));
            }
        }
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

    private void show_info(String msg) {
        textArea1.append(HelpA_.get_proper_date_time_same_format_on_all_computers() + "    " + msg + "\n");
    }

    /**
     * This one defines a Document for writing
     */
    private void DEFINE_DOCUMENT_AND_ROOT_WRITE() {
        DOCUMENT = create_new_document();
        ROOT = DOCUMENT.getRootElement();
    }

    private Document create_new_document() {
        Document doc = new Document(new Element("root"));
        return doc;
    }

    private void sql_data_to_xml(String table__, String sql_query) throws ClassNotFoundException, SQLException, JDOMException, IOException {
        File file = new File("export_" + table__ + ".xml");
        if (file.exists()) {
            file.delete();
        }
        //
        DEFINE_DOCUMENT_AND_ROOT_WRITE();//------------------------------------------>>>>>OBS!
        //
        //======================================================================
        ResultSet rs = sql.execute_2(sql_query);
        //
        ResultSetMetaData meta = rs.getMetaData();
        //=======================================================================
        //
        String table_name = meta.getTableName(1);
        Element table = new Element(table_name);
        ROOT.addContent(table);
        table.addContent("\n");
        //
        //
        //This "for" loop lists through all of the columns of given table
        for (int i = 1; i < meta.getColumnCount() + 1; i++) {
            //This one "rs.first()" is obligatory
            rs.first();
            //
            String act_column = meta.getColumnName(i);
            String type = meta.getColumnTypeName(i);
            //
            Element COLUMN = new Element("column");
            //
            COLUMN.setAttribute("name", act_column);
            COLUMN.setAttribute("type", type);
            //
            COLUMN.setText(act_column);
            COLUMN.addContent("\n");
            //
            table.addContent(COLUMN);
            table.addContent("\n");
            //
            int index = 1;
            //
            //This while loop lists through all of the values of actual column
            while (rs.next()) {
                //
                String row_value = rs.getString(i);
                //
                Element ROW = new Element("row");
                //
                if (row_value == null) { //This is obligatory!
                    row_value = "0";
                }
                //
                ROW.setText(row_value.trim());
                ROW.setAttribute("n", "" + index);
                //
                COLUMN.addContent(ROW);
                COLUMN.addContent("\n");
                //
                index++;
            }
        }
        //
        write_xml_doc_to_file(DOCUMENT, file.getPath());
        //
        show_info("Export table " + table__ + " ready");
    }

    /**
     * This method is for saving changes made to the xml.doc
     *
     * @param doc
     * @param path
     */
    public static void write_xml_doc_to_file(Document doc, String path) {
        try {
            new XMLOutputter().output(doc, System.out);
            FileWriter fstream = new FileWriter(path, true);
            BufferedWriter out = new BufferedWriter(fstream);
            new XMLOutputter().output(doc, out);
        } catch (Exception ex) {
            Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
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

        textArea1 = new java.awt.TextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("....");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
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
        for (Table table : TABLES_LIST) {
            try {
                sql_data_to_xml(table.getTableName(), table.getSqlQuery());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDOMException ex) {
                Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ExporterXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        textArea1.append("\n");
        show_info("Export Ready");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        HelpA_.err_output_to_file();
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
            java.util.logging.Logger.getLogger(ExporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExporterXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExporterXML().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
