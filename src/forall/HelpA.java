/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import MCCompound.PROD_PLAN;
import MCRecipe.Lang.ERRORS;
import MCRecipe.Sec.ComboBoxTitle;
import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_A;
import MyObjectTableInvert.RowDataInvert;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.michaelbaranov.microba.calendar.DatePicker;
import images.IconUrls;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author Administrator
 */
public class HelpA {

    private static HashMap<String, String> properties_to_use_map = new HashMap();
    private static int nr_properties;
    public static String LAST_ERR_OUT_PUT_FILE_PATH;
    private static Border PREV_BORDER;

    public static void addMouseListenerToAllComponentsOfComponent(Container c) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    String str = "SOURCE ELEM: " + me.getSource();
                    System.out.println(str);
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        PREV_BORDER = jc.getBorder();
                        jc.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    }
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        jc.setBorder(PREV_BORDER);
                    }
                }
            });
            if (component instanceof JComponent) {
                addMouseListenerToAllComponentsOfComponent((JComponent) component);
            }
        }
    }

    public static boolean getVersion(String path, String prefix, JLabel label) {
        File f = new File(path);
        //
        if (f.exists() == false) {
            return false;
        }
        //
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // this works to!
        long now = Long.parseLong("" + f.lastModified());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        String version = formatter.format(calendar.getTime());
        //
        label.setText(prefix + version);
        //
        return true;
    }

    public static void read_err_outputfile_and_show(JTextArea jta, long offset) {
        //
        if (offset == -1) {
            return;
        }
        //
        ArrayList<String> list = read_Txt_To_ArrayList(LAST_ERR_OUT_PUT_FILE_PATH, offset);
        //
        jta.setText("");
        //
        for (String string : list) {
            jta.append(string + "\n");
        }
        //
    }

    public static void read_err_outputfile_and_show(JTextArea jta) {
        //
        ArrayList<String> list = read_Txt_To_ArrayList(LAST_ERR_OUT_PUT_FILE_PATH);
        //
        jta.setText("");
        //
        for (String string : list) {
            jta.append(string + "\n");
        }
        //
    }

    public static ArrayList<String> read_Txt_To_ArrayList(String filename, long offset) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            raf.seek(offset);
            String rs = raf.readLine();
            while (rs != null) {
                list.add(rs);
                rs = raf.readLine();
            }
            //
        } catch (IOException e) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, e);
        }
        //
        return list;
    }

    public static void console_output_to_jtextpane(JTextPane jTextPane) {
        try {
            PrintStream out = new PrintStream(new MyOutputStream(jTextPane), false, "UTF-8");
//            System.setErr(out);
            System.setOut(out);
        } catch (IOException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Used to know if you are running from NetBeans, this to know if to use
     * err_output_to_file()
     *
     * @param path
     * @return
     */
    public static boolean runningInNetBeans(String pathAndFileName) {
        File f = new File(pathAndFileName);
        if (f.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public static void err_output_to_file() {
        //Write error stream to a file
        create_dir_if_missing("err_output");
        try {
            String err_file = "err_" + get_date_time() + ".txt";
            String output_path = "err_output/" + err_file;
            LAST_ERR_OUT_PUT_FILE_PATH = output_path;
            PrintStream out = new PrintStream(new FileOutputStream(output_path));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void create_dir_if_missing(String path_and_folder_name) {
        File f = new File(path_and_folder_name);
        if (f.exists() == false) {
            f.mkdir();
        }
    }

//    public static final boolean TRACKING_ON = true;
    public static void setTrackingToolTip(JComponent jc, String text) {
        //
        if (jc == null) {
            return;
        }
        //
        boolean role_developer = MC_RECIPE_.USER_ROLE.equals(MC_RECIPE_.ROLE_DEVELOPER);
        //
        if (runningInNetBeans("MCRecipe.jar") || HelpA.updatedBy().equals("SB") || role_developer) { // 
            //
            if (jc instanceof JComboBoxA) {
                jc.setToolTipText(text);
            } else if (jc instanceof JComboBox) {//Combo from InvertTable
                JComboBox box = (JComboBox) jc;
                box.setRenderer(new MyComboBoxRenderer(text));
            } else {
                jc.setToolTipText(text);
            }
            //
        }
    }

    static class MyComboBoxRenderer extends BasicComboBoxRenderer {

        private String toolTipText;

        public MyComboBoxRenderer(String toolTipText) {
            this.toolTipText = toolTipText;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {
                    list.setToolTipText(toolTipText);
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public static RowDataInvert[] removeGivenEntriesFromArray(RowDataInvert[] arr, String[] columnsToRemove) {
        //
        ArrayList<RowDataInvert> list = new ArrayList<RowDataInvert>();
        //
        list.addAll(Arrays.asList(arr));
        //
        RowDataInvert[] toReturn = new RowDataInvert[list.size() - columnsToRemove.length];
        //
        int flag = 0;
        //
        for (RowDataInvert rdi : list) {
            if (a01(columnsToRemove, rdi.getFieldNickName()) == false) {
                toReturn[flag] = rdi;
                flag++;
            }
        }
        //
        return toReturn;
    }

    private static boolean a01(String[] columnsToRemove, String currColName) {
        for (String colName : columnsToRemove) {
            if (currColName.equals(colName)) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentTabName(JTabbedPane jtp) {
        int i = jtp.getSelectedIndex();
        return jtp.getTitleAt(i);
    }

    /**
     * Not working
     *
     * @param jtp
     * @param tabName
     */
    public static void getTabByName(JTabbedPane jtp, String tabName) {
        jtp.setBorder(BorderFactory.createLineBorder(Color.red, 5));
//        for (int i = 0; i < jtp.getTabCount(); i++) {
//            String title = jtp.getTitleAt(i);
//            if (title.equals(tabName)) {
//                  jtp.setSelectedIndex(i);
//                jtp.setBackgroundAt(i, Color.red);
//            }
//        }
    }

    public static void openTabByName(JTabbedPane jtp, String tabName) {
        for (int i = 0; i < jtp.getTabCount(); i++) {
            String title = jtp.getTitleAt(i);
            //
            if (title.equals(tabName)) {
                jtp.setSelectedIndex(i);
            }
            //
        }
    }

    public static void hideTabByName(JTabbedPane jtp, String tabName) {
        for (int i = 0; i < jtp.getTabCount(); i++) {
            String title = jtp.getTitleAt(i);
            //
            if (title.equals(tabName)) {
                jtp.removeTabAt(i);
            }
            //
        }
    }

    public static void changeTabName(JTabbedPane jtp, String tabNameOld, String tabNameNew) {
        for (int i = 0; i < jtp.getTabCount(); i++) {
            String title = jtp.getTitleAt(i);
            //
            if (title.equals(tabNameOld)) {
                jtp.setTitleAt(i, tabNameNew);
            }
            //
        }
    }

    public static boolean getSelectedCheckBox(JCheckBox box) {
        return box.isSelected();
    }

    public static boolean columnExistsSqlTable(SqlBasicLocal sql, String colName, String tableName) {
        String q = "select top 1 " + colName + " from " + tableName;
        //
        try {
            sql.execute(q);
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    public static boolean entryExistsSql(SqlBasicLocal sql, String q) {
        try {
            ResultSet rs = sql.execute(q);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        return false;
    }

    public static String getSingleParamSql(SqlBasicLocal sql, String tableName,
            String columnWhere, String valueWhere, String columnGet, boolean number) {
        //
        String q = "SELECT * from " + tableName
                + " where " + columnWhere + "=" + SQL_A.quotes(valueWhere, number);
        //
        try {
            ResultSet rs = sql.execute(q);
            if (rs.next()) {
                return rs.getString(columnGet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getLastIncrementedId(SqlBasicLocal sql, String tableName) {
        //
        String q = "SELECT IDENT_CURRENT('" + tableName + "')";
        //
        try {
            ResultSet rs = sql.execute(q);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int getRowCount(SqlBasicLocal sql, String tableName) {
        String q = "SELECT COUNT(*) FROM " + tableName;
        int ammount = -1;
        //
        try {
            ResultSet rs = sql.execute(q);

            if (rs.next()) {
                ammount = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        //
        return ammount;
    }

    public static int getRowCount(SqlBasicLocal sql, String tableName, String whereCondition) {
        //
        String q = "SELECT COUNT(*) FROM " + tableName
                + " where " + whereCondition;
        //
        int ammount = -1;
        //
        try {
            ResultSet rs = sql.execute(q);

            if (rs.next()) {
                ammount = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        //
        return ammount;
    }

    public static String get_date_time() {
        DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String updatedOnLocal() {
        return get_proper_date_adjusted_format(3);
    }

    public static String updatedOn() {
        return get_proper_date_adjusted_format(3);
    }

//    public static String updatedOn() {
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//        return formatter.format(calendar.getTime());
//    }
    private static String ACTUAL_USER = "UNDEF";

    public static void setUser(String user) {
        ACTUAL_USER = user;
    }

    public static String updatedBy() {
        return ACTUAL_USER;
    }

    public static String define_date_format(String date) {
        if (date != null) {
            for (String parse : formats) {
                SimpleDateFormat sdf = new SimpleDateFormat(parse);
                try {
                    sdf.parse(date);
                    return parse;
                } catch (ParseException e) {
                    //Do nothing
                }
            }
        }
        return null;
    }
    /**
     * It's best not to change anything here
     */
    private static final String[] formats = {
        "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
        "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
        "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
        "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
        "yyyy:MM:dd HH:mm:ss",
        "yyyy-MM-dd", "yyyy:MM:dd",
        //        "yyyyMMdd", dont use this because 352980126 is considered as this time format
        "dd/MM/yy", "dd/MM/yyyy", "dd-MM-yy", "dd-MM-yyyy",
        "dd:MM:yy", "dd:MM:yyyy"};

    public static long dateToMillisConverter3(String date, String date_format) {
        DateFormat formatter = new SimpleDateFormat(date_format);
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static long millis_to_days_converter(long millis) {
        return millis / 86400000;
    }

    public static String get_proper_date_adjusted_format(long millis, int style) {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        DateFormat f1 = DateFormat.getDateInstance(style);
        cal.setTimeInMillis(millis);
        Date d = cal.getTime();
        return f1.format(d);
    }

    public static String millisToDefaultDate(long millis) {
        return get_proper_date_adjusted_format(millis, 2);
    }

    public static String millisToDateConverter(String millis, String dateFormat) {
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS"); //this works!
        //note if to write hh instead of HH it will show like 03:15:16 and not 15:15:16
        DateFormat formatter = new SimpleDateFormat(dateFormat); // this works to!
        long now = Long.parseLong(millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return formatter.format(calendar.getTime());
    }

    public static String datePickerGetDate(DatePicker dp, String dateFormat) {
        //
        if (dp.getDate() == null) {
            return "";
        }
        //
        return millisToDateConverter("" + dp.getDate().getTime(), dateFormat);
    }

    public static Properties choose_properties(String path) {
        String dialog = choose_properties_dialog_string(path);
        String property_path;
        Properties properties;
        int val = 0;
        if (nr_properties == 1) {
            property_path = (String) properties_to_use_map.get("" + 1);
            properties = HelpA.properties_load_properties(path + "/" + property_path, false);
        } else {
            try {
                val = Integer.parseInt(JOptionPane.showInputDialog(dialog));
            } catch (Exception ex) {
                System.exit(0);
            }

            property_path = (String) properties_to_use_map.get("" + val);
            properties = HelpA.properties_load_properties(path + "/" + property_path, false);
        }
        if (properties == null) {
            JOptionPane.showMessageDialog(null, "properties not found or error occured: " + property_path + " / program will close");
            System.exit(0);
            return null;
        }
        return properties;
    }

    private static String choose_properties_dialog_string(String path) {
        File[] f = new File(path).listFiles();
        String dialog = "";
        int i = 1;
        for (File file : f) {
            String file_name = file.getName();
            if (file_name.contains("properties") && file_name.contains("other") == false
                    && file_name.contains("properties") && file_name.contains("update_map") == false) {
                dialog += i + ". " + file_name + "\n";
                properties_to_use_map.put("" + i, file_name);
                i++;
                nr_properties++;
            }
        }
        return dialog.isEmpty() ? "0" : dialog;
    }

    public static int runProcedureIntegerReturn_A(Connection sqlConnection, String procedure) throws SQLException {
        CallableStatement proc = sqlConnection.prepareCall("{ ? = call " + procedure + " }");
        proc.registerOutParameter(1, Types.INTEGER);
        proc.execute();
        //
        int ret;
        //
        try {
            ret = proc.getInt(1);
        } catch (Throwable ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        }
        //
        return ret;
    }

    public static int runProcedureIntegerReturn_A_2(SqlBasicLocal sql, String procedure) {
        CallableStatement proc;
        //
        try {
            proc = sql.getConnection().prepareCall("{ ? = call " + procedure + " }");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        //
        int ret;
        //
        try {
            ret = proc.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        }
        //
        return ret;
    }

    public static void setColumnWidthByName(String colName, JTable table, double percent) {
        int table_width = table.getWidth();
        double width = table_width * percent;
        table.getColumn(colName).setPreferredWidth((int) width);
    }

    public static void setColumnWidthByIndex(int colIndex, JTable table, double percent) {
        int table_width = table.getWidth();
        double width = table_width * percent;
        table.getColumnModel().getColumn(colIndex).setPreferredWidth((int) width);
    }

    /**
     *
     * @param colIndex - starts from 0
     * @param table
     * @param width
     */
    public static void setColumnWidthByIndex(int colIndex, JTable table, int width) {
        table.getColumnModel().getColumn(colIndex).setPreferredWidth(width);
    }

    public static int getColumnWidthByIndex(int colIndex, JTable table) {
        return table.getColumnModel().getColumn(colIndex).getWidth();
    }

    private static void objectToFile(String path, Object obj) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Object fileToObject(String path) throws IOException, ClassNotFoundException {
        FileInputStream fas = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fas);
        Object obj = ois.readObject();
        return obj;
    }

    public static synchronized void build_table_common_with_rounding_properties(ResultSet rs, String q, Properties props, JTable jTable, String defaultFormat, String[] skipColumnsNames, String[] sortAsInt) {
        //
        if (rs == null) {
            return;
        }
        //
        HelpA.setTrackingToolTip(jTable, q);
        //
        try {
            //
            String[] headers = getHeaders(rs);
            //
            Object[][] content = getContentRounding_properties(rs, props, defaultFormat, headers, skipColumnsNames, sortAsInt);
            //
            jTable.setModel(new DefaultTableModelM(content, headers, sortAsInt, jTable));
            jTable.setAutoCreateRowSorter(true);
            //
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized void build_table_common_with_rounding(ResultSet rs, String q, JTable jTable, String roundingFormat, String[] skipColumnsNames, String[] exceptionColumns, String[] sortAsInt) {
        //
        if (rs == null) {
            return;
        }
        //
        HelpA.setTrackingToolTip(jTable, q);
        //
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContentRounding(rs, roundingFormat, headers, skipColumnsNames, exceptionColumns, sortAsInt);
            jTable.setModel(new DefaultTableModelM(content, headers, sortAsInt, jTable));
            jTable.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized void build_table_common(ResultSet rs, JTable jTable, String q, int indexFirst, int indexLast) {
        //
        if (rs == null) {
            return;
        }
        //
        HelpA.setTrackingToolTip(jTable, q);
        //
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs, indexFirst, indexLast);
            jTable.setModel(new DefaultTableModel(content, headers));
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
    }

    public static synchronized void build_table_common(ResultSet rs, JTable jTable, String q) {
        //
        if (rs == null) {
            return;
        }
        //
        HelpA.setTrackingToolTip(jTable, q);
        //
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs);
            jTable.setModel(new DefaultTableModel(content, headers));
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized JTable build_table_common_return(ResultSet rs, JTable jTable) {
        //
        if (rs == null) {
            return null;
        }
        //
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs);
            jTable.setModel(new DefaultTableModel(content, headers));
            return jTable;
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static synchronized String[] getHeaders(ResultSet rs) throws SQLException {
        ResultSetMetaData meta; // Returns the number of columns
        String[] headers; // skapar en ny array att lagra titlar i
        meta = rs.getMetaData(); // Den parameter som skickas in "ResultSet rs" innehåller Sträng vid initialisering
        headers = new String[meta.getColumnCount()]; // ger arrayen "headers" initialisering och anger antalet positioner
        for (int i = 0; i < headers.length; i++) {
            headers[i] = meta.getColumnLabel(i + 1);
        }
        //
        return headers;
    }

    public static synchronized Object[][] getContent(ResultSet rs, int indexFirst, int indexLast) throws SQLException {
        ResultSetMetaData rsmt;
        Object[][] content;
        int rows, columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        rs.last(); // flyttar pekaren till sista positon
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".
        rows = (indexLast - indexFirst) + 1;
        content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        // initialisering i den första demensionen är "rows" i den andra "columns"
        //
        int row_ = 0;
        for (int row = indexFirst; row <= indexLast; row++) {
            rs.absolute(row + 1); // Flytta till rätt rad i resultatmängden
            for (int col = 0; col < columns; col++) {
                Object obj = rs.getString(col + 1);
                content[row_][col] = obj;
            }
            row_++;
        }
        //
        return content;
    }

    public static synchronized Object[][] getContent(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmt;
        Object[][] content;
        int rows, columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        rs.last(); // flyttar pekaren till sista positon
        rows = rs.getRow(); // retrieves the current antalet rows och lagrar det i variabeln "rows"
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".
        content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        // initialisering i den första demensionen är "rows" i den andra "columns"
        //
        for (int row = 0; row < rows; row++) {
            rs.absolute(row + 1); // Flytta till rätt rad i resultatmängden
            for (int col = 0; col < columns; col++) {
                Object obj = rs.getString(col + 1);
                content[row][col] = obj;
            }
        }
        //
        return content;
    }

    private static synchronized Object[][] getContentRounding(ResultSet rs, String format, String[] headers, String[] skipColumnsNames, String[] exceptionColumns, String[] sortAsInt) throws SQLException {
        ResultSetMetaData rsmt;
        Object[][] content;
        int rows, columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        rs.last(); // flyttar pekaren till sista positon
        rows = rs.getRow(); // retrieves the current antalet rows och lagrar det i variabeln "rows"
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".
        content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        //
        // initialisering i den första demensionen är "rows" i den andra "columns"
        //
        for (int row = 0; row < rows; row++) {
            rs.absolute(row + 1); // Flytta till rätt rad i resultatmängden
            for (int col = 0; col < columns; col++) {
                //
                Object obj = rs.getString(col + 1);
                //
                if (exceptionColumn(col, headers, exceptionColumns)) {
                    content[row][col] = roundDouble(obj, "%2.3f");
                } else if (skipRounding(col, headers, skipColumnsNames) == false) {
                    content[row][col] = roundDouble(obj, format);//-----------------------OBS ROUNDING IS DONE HERE
                } else if (sortAsInteger(col, headers, sortAsInt)) {
                    content[row][col] = Integer.parseInt(obj.toString());
                } else {
                    content[row][col] = obj;
                }
                //
            }
        }
        //
        return content;
    }

    private static synchronized Object[][] getContentRounding_properties(ResultSet rs, Properties pFomats, String defaultFormat, String[] headers, String[] skipColumnsNames, String[] sortAsInt) throws SQLException {
        ResultSetMetaData rsmt;
        Object[][] content;
        int rows, columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        rs.last(); // flyttar pekaren till sista positon
        rows = rs.getRow(); // retrieves the current antalet rows och lagrar det i variabeln "rows"
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".
        content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        //
        // initialisering i den första demensionen är "rows" i den andra "columns"
        //
        for (int row = 0; row < rows; row++) {
            rs.absolute(row + 1); // Flytta till rätt rad i resultatmängden
            for (int col = 0; col < columns; col++) {
                //
                Object obj = rs.getString(col + 1);
                //
                if (skipRounding(col, headers, skipColumnsNames) == false) {
                    //
                    String roundFormat = defineRoundingByColName(col, headers, defaultFormat, pFomats);
                    //
                    content[row][col] = roundDouble(obj, roundFormat);//-----------------------OBS ROUNDING IS DONE HERE
                    //
                } else if (sortAsInteger(col, headers, sortAsInt)) {
                    content[row][col] = Integer.parseInt(obj.toString());
                } else {
                    content[row][col] = obj;
                }
                //
            }
        }
        //
        return content;
    }

    private static String defineRoundingByColName(int col, String[] headers, String defaultFormat, Properties p) {
        //
        String colName = headers[col];
        String format = "%2.";
        String x = p.getProperty(colName, defaultFormat);
        //
        try {
            Integer.parseInt(x);
            return format += x + "f";
        } catch (Exception ex) {
            return x;
        }
        //
    }

    /**
     *
     * @tags float, align, right
     * @param table
     */
    public static void rightAlignValuesJTable(JTable table) {
        //
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        //
        for (int x = 0; x < table.getColumnCount(); x++) {
            table.getColumnModel().getColumn(x).setCellRenderer(rightRenderer);
        }
        //
    }

    /**
     * @tags float, align, left
     * @param table
     * @param colName
     */
    public static void leftAlignValueByColName(JTable table, String colName) {
        //
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        //
        int colNr = getColByName(table, colName);
        //
        if (colNr != -1) {
            table.getColumnModel().getColumn(colNr).setCellRenderer(leftRenderer);
        }
    }

    public static void rightAlignValueByColName(JTable table, String colName) {
        //
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        //
        int colNr = getColByName(table, colName);
        //
        if (colNr != -1) {
            table.getColumnModel().getColumn(colNr).setCellRenderer(rightRenderer);
        }
    }

    /**
     *
     * @param table
     * @param colName
     * @param rightOrLeft 2=left, 4=right
     */
    public static void alignValueByColName(JTable table, String colName, int rightOrLeft) {
        //
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(rightOrLeft);
        //
        int colNr = getColByName(table, colName);
        //
        if (colNr != -1) {
            table.getColumnModel().getColumn(colNr).setCellRenderer(renderer);
        }
    }

    private static boolean sortAsInteger(int colNr, String[] headers, String[] colNames) {
        for (String colName : colNames) {
            if (headers[colNr].equals(colName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean skipRounding(int colNr, String[] headers, String[] skipColumnsNames) {
        for (String colName : skipColumnsNames) {
            if (headers[colNr].equals(colName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean exceptionColumn(int colNr, String[] headers, String[] exceptionColumns) {
        for (String colName : exceptionColumns) {
            if (headers[colNr].equals(colName)) {
                return true;
            }
        }
        return false;
    }

    private static synchronized Object roundDouble(Object obj, String format) {
        if (isDouble(obj)) {
            String val = (String) obj;
            double ret = Double.parseDouble(val);
//            return "" + Double.parseDouble(roundDouble(ret, format));
            return "" + roundDouble(ret, format);
        } else {
            return obj;
        }
    }

    /**
     *
     * @param number
     * @param format - is passed like "#.###"
     * @return
     */
    private static synchronized String roundDouble(double number, String format) {
        return String.format(format, number).replace(",", ".");
    }

    private static synchronized boolean isDouble(Object obj) {
        if (obj instanceof String) {
            String val = (String) obj;
            //
            //
            try {
                Double.parseDouble(val);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    private static boolean isInteger(Object obj) {
        if (obj instanceof String) {
            String val = (String) obj;
            try {
                Integer.parseInt(val);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    public static boolean isIngred(String strToCheck) {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)) {
            if (strToCheck.length() > 5) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    public static String jTableToHTML(JTable table, JEditorPane pane, String[] CSSRules) {
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        pane.setEditorKit(kit);
        //
        StyleSheet styleSheet = kit.getStyleSheet();
//        styleSheet.addRule("table, th, td {border: 1px solid black}");
        //
        for (int i = 0; i < CSSRules.length; i++) {
            styleSheet.addRule(CSSRules[i]);
        }
        //
        //
        ArrayList<String> colNames = getVisibleColumnsNames(table);
        //
        //
        String html = "";
        //
        //
        html += "<table>";
        //
        //<TABLE HEADER>
        html += "<tr>";
        //
        for (int i = 0; i < colNames.size(); i++) {
            html += "<th>" + colNames.get(i) + "</th>";
        }
        //
        html += "</tr>";
        //</TABLE HEADER>
        //
        //<TABLE BODY>
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            ArrayList rowValues = getLineValuesVisibleColsOnly(table, x);
            //
            html += "<tr>";
            //
            for (int i = 0; i < rowValues.size(); i++) {
                html += "<td>" + rowValues.get(i) + "</td>";
            }
            //
            html += "</tr>";
            //
        }
        //</TABLE BODY>
        //
        html += "</table>";
        //
        //
        Document doc = kit.createDefaultDocument();
        pane.setDocument(doc);
        //
        pane.setText(html);
        //
        return html;
    }

    public static String jTableToCSV(JTable table, boolean writeToFile) {
        //
        String csv = "";
        //
        for (Object colName : getVisibleColumnsNames(table)) {
            csv += colName + ";";
        }
        //
        csv += "\n";
        //
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            for (Object rowValue : getLineValuesVisibleColsOnly(table, x)) {
                csv += rowValue + ";";
            }
            csv += "\n";
        }
        //
        String path = HelpA.get_desktop_path() + "\\"
                + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".csv";
        //
        if (writeToFile) {
            try {
                HelpA.writeToFile(path, csv);
//                JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
                run_application_with_associated_application(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        return csv;
    }

    public static String jTableToCSV(JTable table, boolean writeToFile, String[] columnsToInclude) {
        //
        String csv = "";
        //
        for (Object colName : getVisibleColumnsNames_B(table, columnsToInclude)) {
            csv += colName + ";";
        }
        //
        csv += "\n";
        //
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            for (Object rowValue : getLineValuesVisibleColsOnly_B(table, x, columnsToInclude)) {
                csv += rowValue + ";";
            }
            csv += "\n";
        }
        //
        String path = HelpA.get_desktop_path() + "\\"
                + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".csv";
        //
        if (writeToFile) {
            try {
                HelpA.writeToFile(path, csv);
//                JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
                run_application_with_associated_application(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        return csv;
    }

    private static boolean exists_(String col, String[] columns) {
        for (String colName : columns) {
            if (colName.equals(col)) {
                return true;
            }
        }
        return false;
    }

    public static void disableColumnDragging(JTable table) {
        table.getTableHeader().setReorderingAllowed(false);
    }

    public static ArrayList getLineValuesVisibleColsOnly_B(JTable table, int rowNr, String[] columnsToInclude) {
        ArrayList rowValues = new ArrayList();
        for (int x = 0; x < table.getColumnCount(); x++) {
            if (columnIsVisible(table, x)) {
                String value = "" + table.getValueAt(rowNr, x);
                if (exists_(getColumnNameByIndex(table, x), columnsToInclude)) {
                    rowValues.add(value);
                }
            }
        }

        return rowValues;
    }

    /**
     * OBS! JTable row index start with 0
     *
     * @param table
     * @param rowNr
     * @return
     */
    public static ArrayList getLineValuesVisibleColsOnly(JTable table, int rowNr) {
        ArrayList rowValues = new ArrayList();
        for (int x = 0; x < table.getColumnCount(); x++) {
            if (columnIsVisible(table, x)) {
                String value = "" + table.getValueAt(rowNr, x);
                rowValues.add(value);
            }
        }
        return rowValues;
    }

    public static ArrayList getVisibleColumnsNames_B(JTable table, String[] columnsToInclude) {
        ArrayList columnNames = new ArrayList();
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnIsVisible(table, i) && exists_(getColumnNameByIndex(table, i), columnsToInclude)) {
                columnNames.add(getColumnNameByIndex(table, i));
            }
        }

//        ArrayList visibleColumnsIndexes = getVisibleColumnsIndexes(table);
//
//        for (Object index : visibleColumnsIndexes) {
//            Integer ind = (Integer) index;
//            if (exists_(ind, columnsToInclude)) {
//                columnNames.add(getColumnNameByIndex(table, ind));
//            }
//        }
        return columnNames;
    }

    public static ArrayList getVisibleColumnsNames(JTable table) {
        ArrayList columnNames = new ArrayList();
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnIsVisible(table, i)) {
                columnNames.add(getColumnNameByIndex(table, i));
            }
        }
        return columnNames;
    }

    public static ArrayList getVisibleColumnsIndexes(JTable table) {
        ArrayList indexes = new ArrayList();
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnIsVisible(table, i)) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public static int getVisibleColumnsCount(JTable table) {
        int count = 0;
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (columnIsVisible(table, i)) {
                count++;
            }
        }
        return count++;
    }

    public static boolean columnIsVisible(JTable table, int column) {
        int width = table.getColumnModel().getColumn(column).getWidth();
        return width == 0 ? false : true;
    }

    public static String getColumnNameByIndex(JTable table, int column) {
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(column);
        return (String) tc.getHeaderValue();
    }

    /**
     * OBS! Even if you change the header title of the column the "Real Name"
     * will be the same!!!!!
     *
     * @param table
     * @param column
     * @param newTitle
     */
    public static void changeTableHeaderTitleOfOneColumn(JTable table, int column, String newTitle) {
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(column);
        tc.setHeaderValue(newTitle);
        th.repaint();
    }

    /**
     * OBS! Even if you change the header title of the column the "Real Name"
     * will be the same!!!!!
     *
     * @param table
     * @param oldName
     * @param newTitle
     */
    public static void changeTableHeaderTitleOfOneColumn(JTable table, String oldName, String newTitle) {
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(getColByName(table, oldName));
        tc.setHeaderValue(newTitle);
        th.repaint();
    }
    private static HashMap<String, String> HEADER_MAP = new HashMap<String, String>();

    public static void changeTableHeaderTitleOfOneColumn_to_hashmap(JTable table, String oldName, String newTitle) {
        //
        HEADER_MAP.put(newTitle, oldName);
        //
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(getColByName(table, oldName));
        tc.setHeaderValue(newTitle);
        th.repaint();
    }

    public static int getColByName_hashmap(JTable table, String name) {
        for (int i = 0; i < table.getColumnCount(); ++i) {
            if (HEADER_MAP.containsKey(name)) {
                if (table.getColumnName(i).equals(HEADER_MAP.get(name))) {
                    return i;
                }
            } else {
                if (table.getColumnName(i).equals(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //
    public static void paintTableHeaderBorderOneColumn(JTable table, int column, final Color borederColor) {
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(column);
        //
        //
        TableCellRenderer renderer = new TableCellRenderer() {
            JLabel label = new JLabel();

            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                label.setOpaque(true);
                label.setText("" + value);
                label.setBorder(BorderFactory.createLineBorder(borederColor));
                return label;
            }
        };
        //
        tc.setHeaderRenderer(renderer);
        th.repaint();
    }

    public static void resetTableHeaderPainting(JTable table, int column) {
        //
        if (column == -1) {
            return;
        }
        //
        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(column);
        //
        tc.setHeaderRenderer(null);
        th.repaint();
    }

    public static void clearAllRowsJTable(JTable table) {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        //
        int rowCount = dm.getRowCount();
        //
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    public static void removeRowJTable(JTable table, int rowToRemove) {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        dm.removeRow(rowToRemove);
    }

    public static void addRowJTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{});
    }

    public static int getRowByValue(JTable table, String col_name, String row_value) {
        for (int i = 0; i < table.getColumnCount(); ++i) {
            if (table.getColumnName(i).equals(col_name)) {
                for (int y = 0; y < table.getRowCount(); ++y) {
                    String curr_row_value = "" + table.getValueAt(y, i);
                    //
                    if (curr_row_value == null) {
                        continue;
                    }
                    //
                    if (curr_row_value.equals(row_value)) {
                        return y;
                    }
                }
            }
        }
        return -1;
    }

    public static void setValueGivenRow(JTable table, int row, String colName, Object value) {
        table.setValueAt(value, row, getColByName(table, colName));
    }

    public static boolean getIfAnyRowChosen(JTable table) {
        if (table.getSelectedRow() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static String getValueGivenRow(JTable table, int row, String colName) {
        return "" + table.getValueAt(row, getColByName(table, colName));
    }

    public static String getValueSelectedRow(JTable table, String colName) {
        //
        int selected_row = table.getSelectedRow();
        //
        try {
            //
            String value = "" + table.getValueAt(selected_row, getColByName(table, colName));
            //
            if (value.trim().equals("null")) {
                return "";
            } else {
                return value;
            }
            //
        } catch (Exception ex) {
            return null;
        }
    }

    public static int getColByName(JTable table, String name) {
        for (int i = 0; i < table.getColumnCount(); ++i) {
            if (table.getColumnName(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean hideColumnByName(JTable table, String name) {
        for (int i = 0; i < table.getColumnCount(); ++i) {
            if (table.getColumnName(i).equals(name)) {
                table.getColumnModel().getColumn(i).setMinWidth(0);
                table.getColumnModel().getColumn(i).setMaxWidth(0);
                table.getColumnModel().getColumn(i).setWidth(0);
                return true;
            }
        }
        return false;
    }

    public static int moveRowToEnd(JTable table, int currRow) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.moveRow(currRow, currRow, table.getRowCount() - 1);
        return table.getRowCount() - 1;
    }

    public static void moveRowTo(JTable table, int rowToMove, int rowToMoveTo) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.moveRow(rowToMove, rowToMove, rowToMoveTo);
    }

    public static void moveRowTo(JTable table, String colName, String rowValue, int rowToMoveTo) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int rowToMove = getRowByValue(table, colName, rowValue);
        dtm.moveRow(rowToMove, rowToMove, rowToMoveTo);
    }

    public static void selectNextRow(JTable table) {
        try {
            table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
        } catch (Exception ex) {
        }
    }

    public static void selectPrevRow(JTable table) {
        try {
            table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
        } catch (Exception ex) {
        }
    }

    public static void setSelectedRow(JTable table, int rowNr) {
        table.setRowSelectionInterval(rowNr, rowNr);
    }

    public static void markFirstRowJtable(JTable table) {
        markGivenRow(table, 0);
    }

    public static void markLastRowJtable(JTable table) {
        markGivenRow(table, table.getRowCount() - 1);
    }

    public static void markGivenRow(JTable table, int row) {
        try {
            table.changeSelection(row, 0, false, false);
        } catch (Exception ex) {
        }
    }

    public static int getNextRow(JTable table, int previousRow) {
        int nextRow = previousRow++;
        if (nextRow < table.getRowCount()) {
            return nextRow;
        } else {
            return 0;
        }
    }

    public static boolean isEmtyJTable(JTable table) {
        if (table.getRowCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void stopEditJTable(JTable table) {
        table.editCellAt(0, 0);
    }

    public static void showActionDeniedUserRole(String userRole) {
        JOptionPane.showMessageDialog(null, "Action not allowed with user role: *" + userRole + "*", "Not allowed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showAccessDeniedUserRole(String userRole) {
        JOptionPane.showMessageDialog(null, "Acces not allowed with user role: *" + userRole + "'", "Not allowed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showNotification(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public static void showNotification_Data_Truncation_Error() {
        JOptionPane.showMessageDialog(null, ERRORS.SQL_ERROR_DATA_TRUNCATION());
    }

    public static void showNotification_SQL_Error() {
        JOptionPane.showMessageDialog(null, ERRORS.SQL_ERROR());
    }

    public static boolean confirm() {
        return JOptionPane.showConfirmDialog(null, "Confirm action?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean checkIfNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
//            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            showNotification("Not a number, please try again");
            return false;
        }
    }

    public static boolean checkIfDate(String value_yyyy_MM_dd) {
        if (value_yyyy_MM_dd.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return true;
        }
        return false;
    }

    private static void requestFocus(final JComponent component) {
        Thread x = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //
                component.requestFocus();
            }
        };

        x.start();
    }

    public static JPasswordField chooseFromPasswordField(String msg, boolean hideChars) {
        //
        JPasswordField jpf = new JPasswordField();
        //
        requestFocus(jpf);
        //
        if (hideChars == false) {
            jpf.setEchoChar((char) 0);
        }
        //
        boolean x = JOptionPane.showConfirmDialog(null, jpf, msg, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
        return jpf;
    }

    public static boolean chooseFromJTextField(JTextField jtf, String msg) {
        requestFocus(jtf);
        return JOptionPane.showConfirmDialog(null, jtf, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFromJTextFieldWithCheck(TextFieldCheck tfc, String msg) {
        requestFocus(tfc);
        return JOptionPane.showConfirmDialog(null, tfc, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFromComboBoxDialog(JComboBox box, String msg) {
        return JOptionPane.showConfirmDialog(null, box, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFrom2ComboBoxDialogs(JComboBox box, JComboBox box2, String msg) {
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(box);
        container.add(box2);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFromComboBoxDialogBoxAndTextfield(JComboBox box, JTextField field, String msg) {
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(box);
        container.add(field);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean chooseFrom2Textfields(JTextField field1, JTextField field2, String label1, String label2, String msg) {
        JPanel container = new JPanel(new GridLayout(4, 1));
        container.setPreferredSize(new Dimension(200, 100));
        JLabel lbl1 = new JLabel(label1);
        JLabel lbl2 = new JLabel(label2);
        container.add(lbl1);
        container.add(field1);
        container.add(lbl2);
        container.add(field2);
        return JOptionPane.showConfirmDialog(null, container, msg, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void addMouseListenerJComboBox(JComponent c, MouseListener ml) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            try {
//                addMouseListenerJComboBox((JComponent) component, ml);
                component.addMouseListener(ml);
            } catch (Exception ex) {
            }
        }
    }
    public static Border initialComboBoxBorder;
    private static final HashMap<String, String> fakeValuesMap = new HashMap<String, String>();

    static {
        //STATUS -> RECIPE_DETAILED -> TABLE_INVERT
        fakeValuesMap.put("S", "ACTIVE");
        fakeValuesMap.put("I", "UNLOCKED");
        fakeValuesMap.put("O", "OLD");
        //CLASS -> RECIPE_DETAILED -> TABLE_INVERT
        fakeValuesMap.put("P", "PRODUCTION");
        fakeValuesMap.put("C", "CALCULATION");
        fakeValuesMap.put("R", "DEVELOPMENT");
    }

    public static JComboBox fillComboBox(SqlBasicLocal sql, JComboBox jbox, String query,
            Object initialValue, boolean showMultipleValues, boolean fakeValue) {
        //
        setCursorWait(true);
        //
        if (jbox instanceof JComboBoxA == false) {
            return fillComboBox_old(sql, jbox, query, initialValue, showMultipleValues, fakeValue);
        }
        //
        //
        ArrayList<Object> list = new ArrayList<Object>();
        //
        boolean cond_1 = initialValue != null && (initialValue instanceof Boolean == false)
                && showMultipleValues == false && fakeValue == false;
        //
        if (cond_1) {
            list.add(initialValue);
        }
        //
        if (fakeValue) {
            list.add(" ");
        }
        //
        try {
            //
            ResultSet rs = sql.execute(query);
            //
            while (rs.next()) {
                //
                String val;
                //
                try {
                    val = rs.getString(1);
                } catch (Exception ex) {
                    break;
                }
                //
                if (val != null && val.isEmpty() == false) {
                    if (showMultipleValues) {
                        //
                        list.add(new ComboBoxObjectB(getValueResultSet(rs, 1), getValueResultSet(rs, 2), getValueResultSet(rs, 3)));
                        //
                    } else if (fakeValue) {
                        //
                        String value = getValueResultSet(rs, 1);
                        String fakeVal = fakeValuesMap.get(value);
                        if (fakeVal != null) {
                            list.add(new ComboBoxObjectC(value, fakeVal, ""));
                        }
                        //
                    } else {
                        //
                        list.add(new ComboBoxObject(getValueResultSet(rs, 1), getValueResultSet(rs, 2), getValueResultSet(rs, 3)));
                        //
                    }
                }
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (jbox instanceof JComboBoxA) {
            JComboBoxA boxA = (JComboBoxA) jbox;
            boxA.AUTOFILL_ADD(list);
            //
            try {
                jbox.setSelectedIndex(0);
            } catch (Exception ex) {
            }
            //

        }
        //
        //
        if (initialComboBoxBorder == null) {
            initialComboBoxBorder = jbox.getBorder();
        }
        //
        tryMatch(jbox, (String) initialValue, showMultipleValues, fakeValue);
        //
        setTrackingToolTip(jbox, query);
        //
        setCursorWait(false);
        //
        return jbox;
    }

    public static void setCursorWait(boolean yesno) {
        Cursor defCursor = Cursor.getDefaultCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        Frame.getFrames()[0].setCursor(yesno ? waitCursor : defCursor);
    }
    private static HashMap<JComboBox, AutoCompleteSupport> autoSupportList = new HashMap<JComboBox, AutoCompleteSupport>();

    /**
     * @deprecated @param sql
     * @param jbox
     * @param query
     * @param initialValue
     * @param showMultipleValues
     * @param fakeValue
     * @return
     */
    public static JComboBox fillComboBox_old(SqlBasicLocal sql, JComboBox jbox, String query,
            Object initialValue, boolean showMultipleValues, boolean fakeValue) {
        //
        setCursorWait(true);
        //
        ArrayList<Object> list = new ArrayList<Object>();
        //
        boolean cond_1 = initialValue != null && (initialValue instanceof Boolean == false)
                && showMultipleValues == false && fakeValue == false;
        //
        if (cond_1) {
            list.add(initialValue);
        }
        //
        if (fakeValue) {
            list.add(" ");
        }
        //
        try {
            ResultSet rs = sql.execute(query);
            while (rs.next()) {
                String val = rs.getString(1);
                if (val != null && val.isEmpty() == false) {
                    if (showMultipleValues) {
                        //
                        list.add(new ComboBoxObjectB(getValueResultSet(rs, 1), getValueResultSet(rs, 2), getValueResultSet(rs, 3)));
                        //
                    } else if (fakeValue) {
                        //
                        String value = getValueResultSet(rs, 1);
                        String fakeVal = fakeValuesMap.get(value);
                        if (fakeVal != null) {
                            list.add(new ComboBoxObjectC(value, fakeVal, ""));
                        }
                        //
                    } else {
                        //
                        list.add(new ComboBoxObject(getValueResultSet(rs, 1), getValueResultSet(rs, 2), getValueResultSet(rs, 3)));
                        //
                    }
                }
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        Object[] arr = list.toArray();
        //
        if (arr.length == 0) {
            arr = new Object[1];
            arr[0] = "";
        }
        //
        AutoCompleteSupport support;
        //
        //#AutoComplete, Auto complete# glazedlists_java15-1.9.1.jar is needed
        if (autoSupportList.containsKey(jbox) == false) {
            support = AutoCompleteSupport.install(
                    jbox, GlazedLists.eventListOf(arr));
            //
            autoSupportList.put(jbox, support);
            //
            jbox.setSelectedIndex(0);
            //
        } else {
            //
            support = autoSupportList.get(jbox);
            //
            if (support.isInstalled()) {
                support.uninstall();
                support = AutoCompleteSupport.install(
                        jbox, GlazedLists.eventListOf(arr));
                //
                autoSupportList.remove(jbox);
                //
                autoSupportList.put(jbox, support);
                //
            }
        }
        //
        if (initialComboBoxBorder == null) {
            initialComboBoxBorder = jbox.getBorder();
        }
        //
        tryMatch(jbox, (String) initialValue, showMultipleValues, fakeValue);
        //
        setTrackingToolTip(jbox, query);
        //
        setCursorWait(false);
        //
        return jbox;
    }

    /**
     * For MultipleValues only with initial value present
     *
     * @param box
     * @param initialValue
     * @param showMultipleValues
     */
    public static void tryMatch(JComboBox box, String initialValue, boolean showMultipleValues, boolean fakeValue) {
        //
        if (showMultipleValues == false || initialValue == null || initialValue.isEmpty()) {
            if (fakeValue && initialValue != null) {
                initialValue = fakeValuesMap.get(initialValue);
            } else {
                return;
            }
        }
        //
        for (int i = 0; i < box.getItemCount(); i++) {
            //
            String str = box.getItemAt(i).toString();
            //
            if (str == null || initialValue == null) {
                box.setSelectedIndex(0);
                continue;
            }
            //
            if (str.regionMatches(0, initialValue, 0, initialValue.length())) {
                box.setSelectedIndex(i);
            }
        }
    }

    public static JComboBox fillComboBox_no_autofill(SqlBasicLocal sql, JComboBox jbox, String query, Object initialValue) {
        //
        ArrayList<Object> list = new ArrayList<Object>();
        //
        if (initialValue != null) {
            list.add(initialValue);
        }
        //
        try {
            ResultSet rs = sql.execute(query);
            while (rs.next()) {
                String val = rs.getString(1);
                if (val != null && val.isEmpty() == false) {
                    list.add(new ComboBoxObject(getValueResultSet(rs, 1), getValueResultSet(rs, 2), getValueResultSet(rs, 3)));
                }
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        Object[] arr = list.toArray();
        //
        jbox.removeAllItems();
        //
        jbox.setModel(new DefaultComboBoxModel(arr));
        //
        return jbox;
    }

    public static String getValueResultSet(ResultSet rs, int index) {
        try {
            return rs.getString(index).trim();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getComboBoxSelectedValue(JComboBox box) {
        Object val = box.getSelectedItem();
        //
        if (val == null) {
            return "NULL";
        }
        //
        if (val instanceof String) {
            String v = (String) val;
            if (v.isEmpty()) {
                return "NULL";
            } else {
                return v.toString();
            }
        }
        //
        if (val instanceof ComboBoxTitle) {
            return "NULL";
        }
        //
        if (val instanceof HelpA.ComboBoxObject) {
            HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) val;
//            return cbo.getParam_1();
            return cbo.getParamAuto(); // Introduced [2020-07-13]
        }
        //
        return null;
    }

    /**
     * Returns nulls instead of 'NULLS'
     *
     * @param box
     * @return
     */
    public static String getComboBoxSelectedValueB(JComboBox box) {
        Object val = box.getSelectedItem();
        //
        if (val == null) {
            return null;
        }
        //
        if (val instanceof String) {
            String v = (String) val;
            if (v.isEmpty()) {
                return null;
            } else {
                return v.toString();
            }
        }
        //
        if (val instanceof ComboBoxTitle) {
            return null;
        }
        //
        if (val instanceof HelpA.ComboBoxObject) {
            HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) val;
            return cbo.getParam_1(); // The "'" shall be remowed in feature!!!!!
        }
        //
        return null;
    }

    public static ComboBoxObject getSelectedComboBoxObject(JComboBox box) {
        //
        ComboBoxObject cbo;
        //
        if (box.getSelectedItem() instanceof ComboBoxObject == false) {
            cbo = (ComboBoxObject) box.getItemAt(box.getSelectedIndex() + 1);
        } else {
            cbo = (ComboBoxObject) box.getSelectedItem();
        }
        //
        return cbo;
    }

    public static class ComboBoxObject {

        String param_1;
        String param_2;
        String param_3;
        int paramToReturn = 1;

        public ComboBoxObject(String param_1, String param_2, String param_3) {
            this.param_1 = param_1;
            this.param_2 = param_2;
            this.param_3 = param_3;
        }

        public void setParamToReturn(int paramToReturn) {
            this.paramToReturn = paramToReturn;
        }

        public String getParamAuto() {
            HashMap<Integer, String> paramMap = new HashMap<>();
            paramMap.put(1, param_1);
            paramMap.put(2, param_2);
            paramMap.put(3, param_3);
            return paramMap.get(paramToReturn);
        }

        @Override
        public String toString() {
            return param_1;
        }

        public String getParam_1() {
            return param_1;
        }

        public String getParam_2() {
            return param_2;
        }

        public String getParam_3() {
            return param_3;
        }
    }

    public static class ComboBoxObjectB extends ComboBoxObject {

        public ComboBoxObjectB(String param_1, String param_2, String param_3) {
            super(param_1, param_2, param_3);
        }

        @Override
        public String toString() {
            return param_1 + "   " + param_2;
        }
    }

    public static class ComboBoxObjectC extends ComboBoxObject {

        public ComboBoxObjectC(String param_1, String param_2, String param_3) {
            super(param_1, param_2, param_3);
        }

        @Override
        public String toString() {
            return param_2;
        }
    }

    public static JComboBox fillComboBox(JComboBox jbox, Object[] values, Object initialValue) {
        //
        ArrayList<Object> list = new ArrayList<Object>();
        //
        if (initialValue != null) {
            list.add(initialValue);
        }
        //
        list.addAll(Arrays.asList(values));
        //
        Object[] arr = list.toArray();
        //
        //#AutoComplete, Auto complete# glazedlists_java15-1.9.1.jar is needed
        AutoCompleteSupport support = AutoCompleteSupport.install(
                jbox, GlazedLists.eventListOf(arr));
        //
        jbox.setSelectedIndex(0);
        //
        return jbox;
    }

    public static String[] extract_comma_separated_values(String str) {
        str = str.trim();
        String[] arr = str.split(",");
        return arr;
    }

    /**
     * [2020-07-10] the format is following: "Skruv;1,Spik;2,Hammare;3"
     *
     * @return
     */
    public static ComboBoxObject[] extract_comma_separated_objects(String str, int paramToReturn) {
        str = str.trim();
        String[] arr = str.split(",");
        ComboBoxObject[] cbo_arr = new ComboBoxObject[arr.length];
        int i = 0;
        for (String stringObj : arr) {
            String[] arr_obj = stringObj.split(";");
            ComboBoxObject cbo = new ComboBoxObject(arr_obj[0], arr_obj[1], "");
            cbo.setParamToReturn(paramToReturn);
            cbo_arr[i] = cbo;
            i++;
        }
        return cbo_arr;
    }

    public static void main(String[] args) {
        String str = "Skruv;1,Spik;2,Hammare;3";
        ComboBoxObject[] cbo_arr = extract_comma_separated_objects(str,2);
        for (ComboBoxObject cbo : cbo_arr) {
            System.out.println("ID: " + cbo.getParam_2());
        }
    }

    public static void run_application_with_associated_application(File file) throws IOException {
        Desktop.getDesktop().open(file);
    }

    public static void run_application_exe_or_jar(String application_to_run_name, String path) throws IOException {
        String[] commands = new String[3];
        if (application_to_run_name.contains(".jar")) {
            commands[0] = "java";
            commands[1] = "-jar";
            commands[2] = application_to_run_name; //OBS! pay attention here
        } else {
            commands[0] = path + "/" + application_to_run_name; // and here!
            commands[1] = "";
            commands[2] = "";
        }
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.directory(new File(path));
        builder.start();
    }

    public static void run_program_with_catching_output(JTextArea textarea, String cmd1, String cmd2, String cmd3) {
        textarea.setText("");
        String[] commands2 = {cmd1, cmd2, cmd3};
        try {
            run_program_with_catching_output_overall(textarea, commands2);
        } catch (IOException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void run_program_with_catching_output_overall(JTextArea textarea, String[] commands) throws IOException {
        String line;
        OutputStream stdin;
        InputStream stderr;
        InputStream stdout;

        // launch EXE and grab stdin/stdout and stderr
        Process process = Runtime.getRuntime().exec(commands);
        stdin = process.getOutputStream();
        stderr = process.getErrorStream();
        stdout = process.getInputStream();

        // "write" the parms into stdin
        line = "param1" + "\n";
        stdin.write(line.getBytes());
        stdin.flush();

        line = "param2" + "\n";
        stdin.write(line.getBytes());
        stdin.flush();

        line = "param3" + "\n";
        stdin.write(line.getBytes());
        stdin.flush();

        stdin.close();

        // clean up if any output in stdout
        BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
        while ((line = brCleanUp.readLine()) != null) {
            textarea.append("" + line + "\n");
        }
        brCleanUp.close();

        // clean up if any output in stderr
        brCleanUp = new BufferedReader(new InputStreamReader(stderr));
        while ((line = brCleanUp.readLine()) != null) {
            textarea.append("" + line);
            System.out.println("[Stderr] " + line);
        }
        brCleanUp.close();
    }

    public static void run_with_cmd(String cmd_application, String arg) {
        String[] commands = {"cmd", "/c", "start", "\"" + cmd_application + "\"", cmd_application, arg};
        ProcessBuilder builder = new ProcessBuilder(commands);
        try {
            builder.start();
        } catch (IOException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Properties properties_load_properties(String path_andOr_fileName, boolean list_properties) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(path_andOr_fileName));
            if (list_properties == true) {
                p.list(System.out);
            }
        } catch (IOException ex) {
            System.out.println("" + ex);
        }
        return p;
    }

    public static void properties_save_properties(Properties props_to_store, String fileName, String title_inside_file) {
        try {
            props_to_store.store(new FileOutputStream(fileName), title_inside_file);
        } catch (IOException ex) {
            System.out.println("" + ex);
        }
    }

    public static String addDay(String date) {
        String[] arr = date.split("-");
        int day = Integer.parseInt(arr[2]);
        day++;
        return "" + arr[0] + "-" + arr[1] + "-" + day;
    }

    public static void copy_file(String file_to_copy, String name_of_duplicate) throws FileNotFoundException, IOException {
        File inputFile = new File(file_to_copy);
        File outputFile = new File(name_of_duplicate);

        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }

        in.close();
        out.close();
    }

    public static int get_column_count(ResultSet rs) throws SQLException {
        ResultSetMetaData meta;
        String[] headers;
        meta = rs.getMetaData();
        headers = new String[meta.getColumnCount()];
        return headers.length;
    }

    public static void looseFocus() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }
        // This is done to loose focus ========      
        robot.keyPress(KeyEvent.VK_TAB);
        wait_(10);
        robot.keyRelease(KeyEvent.VK_TAB);
        wait_(10);

        robot.keyPress(KeyEvent.VK_ENTER);
        wait_(10);
        robot.keyRelease(KeyEvent.VK_ENTER);
        wait_(50);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        wait_(10);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        //======================================
    }

    private static void wait_(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * VERY
     * IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static byte[] filetoByteArray(String path) throws FileNotFoundException, IOException {
        byte[] content;
        FileInputStream p = new FileInputStream(path);
        content = new byte[p.available()];
        p.read(content);
        p.close();
        return content;
    }

    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!! Verified! Works good with
     * 'filetoByteArray(String path)'
     *
     * @param path
     * @param arr
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void byteArrayToFile(String path, byte[] arr) throws FileNotFoundException, IOException {
        File f2 = new File(path);
        OutputStream out;
        out = new FileOutputStream(f2);
        out.write(arr);
    }

    public static void copyFile(String file_to_copy, String duplicate_file_name) throws IOException {
        byte[] b_arr = filetoByteArray(file_to_copy);
        byteArrayToFile(duplicate_file_name, b_arr);
        System.out.println("copy files done");
    }

    public static String get_proper_date_time_same_format_on_all_computers() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String get_proper_date_time_same_format_on_all_computers_err_output() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    /**
     * Use 3
     *
     * @param style
     * @return
     */
    public static String get_proper_date_adjusted_format(int style) {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        DateFormat f1 = DateFormat.getDateInstance(style);
        Date d = cal.getTime();
        return f1.format(d);
    }

    public static String extractValueFromHtmlString(String str) {
        if (str.contains("<") == false) {
            return str;
        }
        String arr[] = str.split(">");
        String arr_2[] = arr[2].split("<");
        return arr_2[0];
    }

    public static void writeToFile(String fileName, String textToWrite) throws IOException {
        FileWriter fstream = new FileWriter(fileName, false);
        BufferedWriter out = new BufferedWriter(fstream);

        out.write(textToWrite);
        out.newLine();
        out.flush();
        out.close();
    }

    public static ArrayList<String> read_Txt_To_ArrayList(String filename) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String rs = br.readLine();
            while (rs != null) {
                list.add(rs);
                rs = br.readLine();
            }

        } catch (IOException e) {
//            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, e);
        }
        //
        return list;
    }

    public static void open_dir(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String get_desktop_path() {
        return System.getProperty("user.home") + "\\" + "Desktop";
    }

    public static Point position_window_in_center_of_the_screen(JFrame window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((d.width - window.getSize().width) / 2, (d.height - window.getSize().height) / 2);
    }

    public static void goToEndPosition(JTextArea txtArea) {
        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }

    public static void setJLabelIcon(JLabel label, URL url) {
        label.setIcon(new ImageIcon(url)); // NOI18N
    }

    public static void setButtonIconPreAdd(JButton btn) {
        btn.setIcon(new ImageIcon(IconUrls.PRE_ADD)); // NOI18N
    }

    public static void setButtonIconCompleteAdd(JButton btn) {
        btn.setIcon(new ImageIcon(IconUrls.ADD_COMPLETE)); // NOI18N
    }

    public static void nimbusLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HelpA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelpA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelpA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelpA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
