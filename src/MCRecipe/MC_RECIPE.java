/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import LabDev.LabDevelopment;
import MCCompound.PROD_PLAN;
import MCRecipe.Sec.CustomPanelCp;
import MCRecipe.Sec.CustomPanelIF;
import MCRecipe.Sec.CustomPanelQew;
import MCRecipe.Sec.CompareRecipes;
import MCRecipe.Lang.INGR;
import MCRecipe.Lang.LAB_DEV;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.RECIPE_OVERVIEW_;
import MCRecipe.Lang.R_DETAILED;
import MCRecipe.Lang.SEQUENCE;
import MCRecipe.Lang.TOOLTIP;
import MCRecipe.Lang.VENDOR_A_B;
import MCRecipe.Sec.AdminTools;
import MCRecipe.Sec.AdministrateIngredGroups;
import MCRecipe.Sec.AdministrateMixerInfoBasic;
import MCRecipe.Sec.AdministrateRecipeGroups_;
import MCRecipe.Sec.AdministrateUsers;
import MCRecipe.Sec.BOX_PARAMS;
import MCRecipe.Sec.JComboBox_ING_A;
import forall.GradientJPanel;
import MyObjectTable.ShowMessage;
import com.jezhumble.javasysmon.JavaSysMon;
import forall.GP;
import forall.HelpA_;
import forall.JComboBoxA;
import forall.JComboBoxM;
import forall.JComboBoxValueChangedListener;
import MCRecipe.Sec.JComboBox_RI_A;
import MCRecipe.Sec.JComboBox_RI_C;
import MCRecipe.Sec.JComboBox_TP_A;
import forall.ErrorOutputListener;
import forall.SqlBasicLocal;
import forall.Sql_B;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.BadLocationException;
import mySwing.JTableM;
import supplementary.HelpM2;

/**
 *
 * @author KOCMOC
 */
public class MC_RECIPE extends javax.swing.JFrame implements MouseListener, ItemListener, KeyListener, ShowMessage, JComboBoxValueChangedListener {

    public SqlBasicLocal sql;
    public SqlBasicLocal sql_additional;
    public static final String TABLE_4_RECIPE_ADMIN_PROPS_PATH = "properties/table4formats.properties";
    public static final String PROPERTIES_PATH = "properties/mccompound.properties";
    private final String IO_PROPERTIES_PATH = "io.properties";
    private final Properties PROPS = HelpA_.properties_load_properties(PROPERTIES_PATH, false);
    public RecipeInitialIF recipeInitial;
    public RecipeDetailed_ recipeDetailed;
    private Ingredients ingredients;
    private Vendors vendors;
    private VendorsB vendorsB;
    private Sequence sequence;
    private RecipeAdditional recipeAdditional;
    private TestParameters_ testParameters;
    private LabDevelopment labDevelopment;
    private final JavaSysMon monitor = new JavaSysMon();
    protected JTextArea textAreaIngredComments = new JTextArea();
    protected JTextArea textAreaRecipeInitialNotes = new JTextArea();
    public JComboBox jComboBoxVenorsVendors = new JComboBox();
    public JComboBox jComboBoxVenorsTradnames = new JComboBox();
    private CompareRecipes compareRecipes;
    private AdministrateUsers administrateUsers;
    public AdministrateRecipeGroups_ administrateRecipeGroups;
    public AdministrateIngredGroups administrateIngredGroups;
    public AdministrateMixerInfoBasic administrateMixerInfoBasic;
    private AdminTools adminTools;
    private final static String ADMIN_RULE_ENTRANCE_ENABLED = "'rule_free_entrance'";
    private final static String ADMIN_USERS_PWD = "recipe123";
    //
    public static String USER_ROLE = "";
    public final static String ROLE_UNDEFINED = "undef";
    public final static String ROLE_COMMON_USER = "user";
    public final static String ROLE_ADVANCED_USER = "useradvanced";
    public final static String ROLE_ADMIN = "admin";
    public final static String ROLE_DEVELOPER = "developer";
    //
    public static final HashSet<String> USER_ROLES_ADMIN_DEVELOPER_ACCESS = new HashSet<String>();

    //
    static {
        USER_ROLES_ADMIN_DEVELOPER_ACCESS.add(ROLE_DEVELOPER);
        USER_ROLES_ADMIN_DEVELOPER_ACCESS.add(ROLE_ADMIN);
    }
    //
    public CustomPanelIF customPanelRecipeInitial;
    //
    private final ErrorOutputListener errorOutputListener;
    //
    private PROD_PLAN prod_plan;

    /**
     * Creates new form MC_RECIPE
     */
    public MC_RECIPE() {
        //
        initCompany();
        //
        defineLanguage();
        //
        initComponents();
        //
        //
        sql = sqlConnect(); //  ***
        sql_additional = sqlConnect(); //  ***
        //
//         HelpA_.addMouseListenerToAllComponentsOfComponent(this.getContentPane());
        //
        this.setTitle(buildTitle(null));
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_RECIPE).getImage());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //
        companyRelated();
        //
        initOther();
        //
        lang_other();
        //
        loadUserName(); //  ***
        //
        if (verifyUser() == false) {
            userNotValidActions();
        } else {
            HelpA_.setUser(MC_RECIPE.jTextFieldHomeUserName.getText());
            System.out.println("Username set");
        }
        //
        HelpA_.getVersion("MCRecipe.jar", "MCRecipe: V.", jLabelHomeVersion);
        //
        errorOutputListener = new ErrorOutputListener(HelpA_.LAST_ERR_OUT_PUT_FILE_PATH, jTabbedPane1, jTextArea1, jPanel52);
        //
    }

    private void initCompany() {
        //
        GP.COMPANY_NAME = PROPS.getProperty("company", "undef");
        //
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_UNDEFINED) || GP.COMPANY_NAME.isEmpty()) {
            HelpM2.showInformationMessage("Company name missing or undefined, program will close");
            System.exit(0);
        } else {
            if (GP.COMPANIES.contains(GP.COMPANY_NAME) == false) {
                HelpM2.showInformationMessage("Company name : *" + GP.COMPANY_NAME + "* does not exist, program will close");
                System.exit(0);
            } else {
                showMessage("COMPANY SETTING = " + GP.COMPANY_NAME); // Everything ok  
            }
        }
        //
    }

    private String buildTitle(String userRole) {
        //
        int pid = monitor.currentPid();
        //
        if (userRole == null || userRole.isEmpty()) {
            return "MCRecipe (" + pid + ")";
        } else {
            return "MCRecipe (" + pid + ")" + " *" + USER_ROLE;
        }
    }

    private void companyRelated() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_COMPOUNDS)) {
            this.customPanelRecipeInitial = new CustomPanelCp();
            initCustomPanel((Component) customPanelRecipeInitial);
//            HelpA_.hideTabByName(jTabbedPane1, LNG.RECIPE_ADD_TAB());
        } else {
            this.customPanelRecipeInitial = new CustomPanelQew();
            initCustomPanel((Component) customPanelRecipeInitial);
        }
    }

    private void defineLanguage() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_COMPOUNDS)) {
            GP.IS_DATE_FORMAT_DE = true;
            LNG.LANG_ENG = false;
        } else {
            LNG.LANG_ENG = true;
        }
    }

    private void lang_other() {
        if (LNG.LANG_ENG == false) {
            LNG.GO(jTabbedPane1, jTabbedPane3_Lab_Dev);
            RECIPE_OVERVIEW_.RECIPE_OVERVIEW_TABS(jTabbedPane2);
        }
    }

    private void initCustomPanel(Component c) {
        Dimension d = jPanel17.getSize();
        this.jPanel17.add(c);
        this.jPanel17.setLayout(new GridLayout(1, 1));
        this.jPanel17.setPreferredSize(d);
    }

    private void loadUserName() {
        Properties p = HelpA_.properties_load_properties(IO_PROPERTIES_PATH, false);
        String userName = p.getProperty("username", "NA");
        String pass = p.getProperty("pass", "");
        jTextFieldHomeUserName.setText(userName);
        jPasswordFieldHomePass.setText(pass);
    }

    private String getUserName() {
        return jTextFieldHomeUserName.getText();
    }

    private String getPass() {
        return jPasswordFieldHomePass.getText();
    }

    private void updateUserName() {
        //
        String userName = jTextFieldHomeUserName.getText();
        String pass = jPasswordFieldHomePass.getText();
        Properties p = new Properties();
        //
        p.put("username", userName);
        p.put("pass", pass);
        //
        HelpA_.properties_save_properties(p, IO_PROPERTIES_PATH, "");
//        HelpA_.showNotification("User name and password saved");
//        jTextFieldHomeUserName.setBorder(BorderFactory.createLineBorder(Color.green));
    }

    private boolean verifyUser() {
        //
        String sqlTableName = AdministrateUsers.USER_ADM_TBL_NAME;
        String userName = getUserName();
        //
        boolean freeEntranceEnabled = freeEntranceEnabled();
        //
//        USER_ROLE = HelpA_.getSingleParamSql(sql, sqlTableName, "userName", userName, "role", false);
//        System.out.println("USER ROLE: " + USER_ROLE);
        //
        if (freeEntranceEnabled) {
            jTabbedPane1.setEnabled(true);
            USER_ROLE = ROLE_UNDEFINED;
            return true;
        }
        //
        String where = "username ='" + userName + "' and pass ='" + getPass() + "'";
        int userConfirmed = HelpA_.getRowCount(sql, sqlTableName, where);
        boolean userConfirmed_ = userConfirmed >= 1;
        //
        if (userConfirmed_ == false && freeEntranceEnabled == false) {//&& freeEntranceEnabled() == false
            USER_ROLE = ROLE_UNDEFINED;
            setTitle(buildTitle(null));
            HelpA_.showNotification("User not valid");
            jTabbedPane1.setEnabled(false);
            return false;
        } else {
            USER_ROLE = HelpA_.getSingleParamSql(sql, sqlTableName, "userName", userName, "role", false);
            setTitle(buildTitle(USER_ROLE));
            jTabbedPane1.setEnabled(true);
            return true;
        }
        //
    }

    private boolean freeEntranceEnabled() {
        String where = "username =" + ADMIN_RULE_ENTRANCE_ENABLED + " and role ='enabled'";
        int freeEntrance = HelpA_.getRowCount(sql, AdministrateUsers.USER_ADM_TBL_NAME, where);
        boolean freeEntrance_ = freeEntrance >= 1;
        return freeEntrance_;
    }

    public void addMouseListenerToAllComponentsOfComponent(JComponent c, MouseListener ml) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            component.addMouseListener(this);
            if (component instanceof JComponent) {
                addMouseListenerToAllComponentsOfComponent((JComponent) component, ml);
            }
        }
    }

    @Override
    public void showMessage(String str) {
        System.out.println("" + str);
        if (jTextArea1_Logg != null) {
            jTextArea1_Logg.append(HelpA_.get_proper_date_time_same_format_on_all_computers() + "  " + str + "\n");
        }

    }

    private void initOther() {
        //
        this.jTabbedPane1.addMouseListener(this);
        //
        this.jTable1.setName("table_1_recipe");
        this.jTable2.setName("table_2_recipe");
        this.jTable3.setName("table_3_recipe");
        this.jTable4RecipeDetailed.setName("table_4_recipe");
        this.jTable_Ingred_Table1.setName("table_1_ingred");
        this.jTable_Ingred_table2.setName("table_2_ingred");
        this.jTable_Ingred_Table3.setName("table_3_ingred");
        //
        this.jTable1.addMouseListener(this);
        this.jTable2.addMouseListener(this);
        this.jTable3.addMouseListener(this);
//        this.jTable_lab_dev_1.addMouseListener(this);
//        this.jTable_lab_dev_2.addMouseListener(this);
        this.jTable4RecipeDetailed.addMouseListener(this);
        this.jTable_Ingred_Table1.addMouseListener(this);
        this.jTable_Ingred_Table3.addMouseListener(this);
        this.jTableSequnece1.addMouseListener(this);
        this.jTable_1_test_parameters.addMouseListener(this);
//        this.jTable_lab_dev__material_info.addMouseListener(this);
        //
        this.jTable1.addKeyListener(this);
        this.jTable_Ingred_Table1.addKeyListener(this);
        this.jEditorPane_Ingred.addKeyListener(this);
        this.jEditorPaneRecipeInitialNotes.addKeyListener(this);
        this.textAreaIngredComments.addKeyListener(this);
        this.textAreaRecipeInitialNotes.addKeyListener(this);
        this.jTable_1_test_parameters.addKeyListener(this);
        this.jTextArea_notes__lab_dev_tab.addKeyListener(this);
        //
        this.jTable1.setAutoCreateRowSorter(true);
        this.jTable2.setAutoCreateRowSorter(true);
        this.jTable3.setAutoCreateRowSorter(true);
        this.jTable4RecipeDetailed.setAutoCreateRowSorter(true);
        HelpA_.disableColumnDragging(jTable4RecipeDetailed);
        //
        this.textAreaIngredComments.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        this.textAreaRecipeInitialNotes.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        //
        JTableM jtm = (JTableM) jTable4RecipeDetailed;
        jtm.addSyncTable(jTableRecipeDetailedTable4HelpTable);
        //
//        this.jTable4RecipeDetailed.getColumnModel().addColumnModelListener(this);
//        this.jTable_Ingred_Table1.getColumnModel().addColumnModelListener(this);
    }
    public ArrayList<JComboBoxM> recipeInitialGroupA_INGRED;

    public void recipeInitial_GroupA_INGRED_Boxes_to_list() {
        //
        recipeInitialGroupA_INGRED = new ArrayList<JComboBoxM>();
        //
        Component[] components = jPanel2.getComponents();
        //
        for (Component c : components) {
            if (c instanceof JComboBoxM) {
                recipeInitialGroupA_INGRED.add((JComboBoxM) c);
            }
        }
        //
    }
    public ArrayList<JComboBox> recipeInitialGroupA;

    public void recipeInitial_GroupA_Boxes_to_list() {
        //
        recipeInitialGroupA = new ArrayList<JComboBox>();
        //
        Component[] components = jPanel2.getComponents();
        //
        for (Component c : components) {
            if (c instanceof JComboBox_RI_A) {
                recipeInitialGroupA.add((JComboBox) c);
            }
        }
        //
    }
    public ArrayList<JComboBox_RI_C> recipeInitialGroupC;

    public void recipeInitial_GroupC_Boxes_to_list() {
        //
        recipeInitialGroupC = new ArrayList<JComboBox_RI_C>();
        //
        JComponent jc = (JComponent) jPanel17.getComponent(0);
        //
        Component[] components = jc.getComponents();
        //
        //
        for (Component c : components) {
            if (c instanceof JComboBox_RI_C) {
                recipeInitialGroupC.add((JComboBox_RI_C) c);
            }
        }
        //
    }

    public ArrayList<JComboBox> testParametersGroupA;

    public void testParameters_GroupA_Boxes_to_list() {
        //
        testParametersGroupA = new ArrayList<JComboBox>();
        //
        Component[] components = jPanel49.getComponents();
        //
        for (Component c : components) {
            if (c instanceof JComboBox_TP_A) {
                testParametersGroupA.add((JComboBox) c);
            }
        }
        //
    }

    public void addJComboListenersTestParameters() {
        //
        for (JComboBox box : testParametersGroupA) {
            HelpA_.addMouseListenerJComboBox(box, this);
        }
        //
    }

    public void addJComboListenersRecipeInitial() {
        //
        for (JComboBox box : recipeInitialGroupA) {
            HelpA_.addMouseListenerJComboBox(box, this);
        }
        //
        for (JComboBox box : recipeInitialGroupC) {
            HelpA_.addMouseListenerJComboBox(box, this);
        }
        //
        //
        HelpA_.addMouseListenerJComboBox(jComboBox_Ingred_1, this);
        HelpA_.addMouseListenerJComboBox(jComboBox_Ingred_2, this);
        //
        JComboBoxM box_ingred_1 = (JComboBoxM) jComboBox_Ingred_1;
        box_ingred_1.addValueChangedListener(this);
        //
        JComboBoxM box_ingred_2 = (JComboBoxM) jComboBox_Ingred_2;
        box_ingred_2.addValueChangedListener(this);
    }
    public ArrayList<JComboBox> ingredientsGroupList;

    public void ingredients_GroupA_Boxes_to_list() {
        //
        ingredientsGroupList = new ArrayList<JComboBox>();
        //
        Component[] components = jPanel_Ingred_boxes.getComponents();
        //
        for (Component c : components) {
            if (c instanceof JComboBox_ING_A) {
                ingredientsGroupList.add((JComboBox) c);
            }
        }
        //
    }

    public void addJComboListenersIngredients() {
        //
        for (JComboBox box : ingredientsGroupList) {
            HelpA_.addMouseListenerJComboBox(box, this);
        }
        //
    }

    public void addJComboListenersSequence() {
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceRecipe, this);
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceRelease, this);
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceMixerCode, this);
        //
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceReleaseCopy, this);
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceRecipeCopy, this);
        HelpA_.addMouseListenerJComboBox(jComboBoxSequenceMixerCodeCopy, this);
    }

    public void addListenersVendor() {
        jComboBoxVendorChooseIngred.addItemListener(this);
        jComboBoxVenorsTradnames.addItemListener(this);
        jComboBoxVenorsVendors.addItemListener(this);

    }

    public void addListenersRecipeAdditional() {
        jComboBoxRecipeAdditionalOrders.addItemListener(this);
    }

    private SqlBasicLocal sqlConnect() {
        //==========
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = PROPS.getProperty("mssql_create_statement_simple", "false");
        GP.MSSQL_LOGIN_TIME_OUT = Integer.parseInt(PROPS.getProperty("login_time_out", "60"));
        GP.SQL_LIBRARY_JTDS = Boolean.parseBoolean(PROPS.getProperty("use_jtds_library", "true"));
        GP.JTDS_USE_NAMED_PIPES = Boolean.parseBoolean(PROPS.getProperty("use_named_pipes", "false"));
        GP.JTDS_DOMAIN_WORKGROUP = PROPS.getProperty("domain_or_workgroup", "workgroup");
        GP.JTDS_INSTANCE_PARAMETER = PROPS.getProperty("jtds_instance", "");
        //==========
        Sql_B sql_ = new Sql_B(Boolean.parseBoolean(GP.MSSQL_CREATE_STATEMENT_SIMPLE), GP.MSSQL_LOGIN_TIME_OUT, true);
        //==========
        showMessage("mssql_create_statement_simple: " + GP.MSSQL_CREATE_STATEMENT_SIMPLE);
        showMessage("login_time_out: " + GP.MSSQL_LOGIN_TIME_OUT);
        showMessage("use_jtds_library: " + GP.SQL_LIBRARY_JTDS);
        //==========
//        String odbc = PROPS.getProperty("odbc", "");
//        String odbc_user = PROPS.getProperty("odbc_user", "");
//        String odbc_name = PROPS.getProperty("odbc_name", "");
        //==========================================
        String dbtype = PROPS.getProperty("db_type", "");
        String host = PROPS.getProperty("host", "");
        String port = PROPS.getProperty("port", "");
        String db_name = PROPS.getProperty("db_name", "");
        String user = PROPS.getProperty("user", "");
        String pass = PROPS.getProperty("pass", "");
        //
        showMessage("sql_type: " + dbtype);
        //
        showMessage("Connecting to: " + host);
        //
        try {
            sql_.connect_tds(host, port, db_name, user, pass, GP.JTDS_USE_NAMED_PIPES, GP.JTDS_DOMAIN_WORKGROUP, GP.JTDS_INSTANCE_PARAMETER);
            //
            showMessage("Connection to " + host + " / " + db_name + " established");
            //
        } catch (Exception ex) {
            showMessage("Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(MC_RECIPE.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connection to SQL failed!");
            System.exit(0);
        }
        //
        return sql_;
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
        jPanelHome = new GradientJPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        jTextFieldHomeUserName = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        jPasswordFieldHomePass = new javax.swing.JPasswordField();
        jLabelHomeVersion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jButton_Home_Login = new javax.swing.JButton();
        jScrollPaneRecipeInitial = new javax.swing.JScrollPane();
        jPanel_RecipeInitial = new JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1_Recipe_Origin = new JComboBox_RI_A(BOX_PARAMS.RECIPE_ORIGIN);
        jComboBox2_Detailed_Group = new JComboBox_RI_A(BOX_PARAMS.DETAILED_GROUP);
        jComboBox3_Recipe_Stage = new JComboBox_RI_A(BOX_PARAMS.RECIPE_STAGE);
        jComboBox4_Mixer_Code = new JComboBox_RI_A(BOX_PARAMS.MIXER_CODE);
        jComboBox5_Recipe_Version = new JComboBox_RI_A(BOX_PARAMS.RECIPE_VERSION);
        jComboBox6_Status = new JComboBox_RI_A(BOX_PARAMS.STATUS);
        jComboBox7_RecipeAdditional = new JComboBox_RI_A(BOX_PARAMS.RECIPE_ADDITIONAL);
        jComboBox8_Class = new JComboBox_RI_A(BOX_PARAMS.CLASS);
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox_Ingred_1 = new JComboBoxM();
        jLabel55 = new javax.swing.JLabel();
        jComboBox_Ingred_2 = new JComboBoxM();
        jCheckBoxRecipeInitialSearchByIngredients = new javax.swing.JCheckBox();
        jComboBox_Description1 = new JComboBox_RI_A(BOX_PARAMS.DESCR);
        jPanel43 = new javax.swing.JPanel();
        jButtonRecipeInitialGo = new javax.swing.JButton();
        jButtonRecipeInitialResetComboBoxes = new javax.swing.JButton();
        jCheckBoxRecipeInitialOR = new javax.swing.JCheckBox();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTableIngredientInfoTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jButtonRecipeInitialRemoweAllFromCompare = new javax.swing.JButton();
        jButtonRecipeInitialShowCompare = new javax.swing.JButton();
        jButtonRecipeInitialAddToCompare = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        jButtonRecipeInitialPrintTable1 = new javax.swing.JButton();
        jLabelRecordsTable1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jButtonRecipeInitialGo2 = new javax.swing.JButton();
        jButtonRecipeInitialResetComboBoxes3 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPaneRecipeInitialNotes = new javax.swing.JScrollPane();
        jEditorPaneRecipeInitialNotes = new javax.swing.JEditorPane();
        jScrollPaneRecipeDetailed = new javax.swing.JScrollPane();
        jPanel_RecipeDetailed = new javax.swing.JPanel();
        jPanel1_recipe_detailed = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable4RecipeDetailed = new JTableM("r_detailed_t4",true);
        jButton7 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton11 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableRecipeDetailedTable4HelpTable = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        jButton_recipe_detailed_delete_recipe = new javax.swing.JButton();
        jButtonRecipeInitialUnblock1 = new javax.swing.JButton();
        jButtonRecipeDetailedAddRecipeFromScratch = new javax.swing.JButton();
        jButtonRecipeDetailedAddNewRecipe = new javax.swing.JButton();
        jButtonRecipeInitialUnblock2 = new javax.swing.JButton();
        jButtonRecipeDetailedPrint = new javax.swing.JButton();
        jButton_Recipe_Detailed_Save_Invert = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jButtonRecipeDetailedAddNote = new javax.swing.JButton();
        jButtonRDetailedChangeNoteName = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jButton_r_detailed_prev = new javax.swing.JButton();
        jButton_r_detailed_next = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        jButtonRecipeDetailedUndoDelete = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButtonRecipeDetailedSwap = new javax.swing.JButton();
        jButtonRecipeDetailedResetChanges = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jButtonTable4Repport = new javax.swing.JButton();
        jButtonRecipeDetailedSaveTable4 = new javax.swing.JButton();
        jLabelLockedUnlocked = new javax.swing.JLabel();
        jScrollPane_LabDevelopment = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane3_Lab_Dev = new javax.swing.JTabbedPane();
        jPanel61 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jPanel_lab_dev__find_order = new javax.swing.JPanel();
        jButton__lab_dev_find_order_tab__filter = new javax.swing.JButton();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable_lab_dev__find_order = new javax.swing.JTable();
        jTextField__lab_dev__find_order = new javax.swing.JTextField();
        jButton__lab_dev_find_order_tab__set_order = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jPanel_lab_development = new javax.swing.JPanel();
        jButton_lab_dev_save_btn_1 = new javax.swing.JButton();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable_lab_dev_1 = new javax.swing.JTable();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable_lab_dev_2 = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        jButton_lab_dev_add_1 = new javax.swing.JButton();
        jButton_lab_dev_delete_1 = new javax.swing.JButton();
        jPanel56 = new javax.swing.JPanel();
        jButton_lab_dev_add_2 = new javax.swing.JButton();
        jButton_lab_dev_delete_2 = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jPanel_lab_dev_material_info = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTable_lab_dev__material_info = new javax.swing.JTable();
        jButton_lab_dev__material_info_save = new javax.swing.JButton();
        jPanel62 = new javax.swing.JPanel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable_test_definitions = new javax.swing.JTable();
        jButton_lab_dev_test_definition_goto = new javax.swing.JButton();
        jPanel63 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jPanel66 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jPanel65 = new javax.swing.JPanel();
        jButton_lab_dev_tab__save_config_btn = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jPanel_lab_development_2 = new javax.swing.JPanel();
        jPanel_lab_development_3 = new javax.swing.JPanel();
        jPanel_lab_development_4 = new javax.swing.JPanel();
        jPanel_lab_development_5 = new javax.swing.JPanel();
        jButton_lab_dev_save_btn_2 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea_notes__lab_dev_tab = new javax.swing.JTextArea();
        jButton_lab_dev_tab__save_notes = new javax.swing.JButton();
        jPanel_lab_dev_header = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jScrollPaneTestParameters = new javax.swing.JScrollPane();
        jPanel_Test_Parameters = new javax.swing.JPanel();
        jPanel_Test_Params_Inv_Table_1 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTable_1_test_parameters = new JTableM("test_param_jtable", true);
        jPanel49 = new javax.swing.JPanel();
        jComboBoxTestParams_Order = new JComboBox_TP_A(BOX_PARAMS.TP_ORDER);
        jComboBoxTestPararams_Recipe = new JComboBox_TP_A(BOX_PARAMS.TP_RECIPE);
        jPanel50 = new javax.swing.JPanel();
        jButtonTestParametersGo = new javax.swing.JButton();
        jButtonTestParametersClear = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jButtonTestParametersPrint_InvertTable = new javax.swing.JButton();
        jButtonTestParametersSave_InvertTable = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jScrollPaneIngredients = new javax.swing.JScrollPane();
        jPanel_Ingredients = new javax.swing.JPanel();
        jPanel_Ingred_boxes = new javax.swing.JPanel();
        jCombo_Ingred_Name = new JComboBox_ING_A(BOX_PARAMS.INGRED_NAME);
        jCombo_Ingred_Group = new JComboBox_ING_A(BOX_PARAMS.INGRED_GROUP);
        jCombo_Ingred_Descr = new JComboBox_ING_A(BOX_PARAMS.INGRED_DESCR);
        jCombo_Ingred_Class = new JComboBox_ING_A(BOX_PARAMS.CLASS);
        jCombo_Ingred_Status = new JComboBox_ING_A(BOX_PARAMS.INGRED_STATUS);
        jCombo_Ingred_Form = new JComboBox_ING_A(BOX_PARAMS.INGRED_FORM);
        jCombo_Ingred_Cas_Number = new JComboBox_ING_A(BOX_PARAMS.CAS_NUMBER);
        jCombo_Ingred_TradeName = new JComboBox_ING_A(BOX_PARAMS.TRADE_NAME);
        jCombo_Ingred_VendorName = new JComboBox_ING_A(BOX_PARAMS.VENDOR_NAME);
        jCombo_Ingred_Perc_Rubber = new JComboBox_ING_A(BOX_PARAMS.PERC_RUBBER);
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jButtonIngredGo = new javax.swing.JButton();
        jButtonIngredClearBoxes = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel_Ingred_Table1_Cont = new javax.swing.JPanel();
        jScrollPane_Ingred_Table1 = new javax.swing.JScrollPane();
        jTable_Ingred_Table1 =  new JTableM("ingred_t1",true);
        jPanel_Ingred_table2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Ingred_table2 = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jButtonIngredExpressInfoPrint = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButtonIngredientsPasteIngred2 = new javax.swing.JButton();
        jButtonIngredientsPasteIngred1 = new javax.swing.JButton();
        jScrollPaneIngredientsB = new javax.swing.JScrollPane();
        jPanelIngredientsB = new javax.swing.JPanel();
        jPanel_Ingred_Invert_Table = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jButton_Ingred_delete_Ingred = new javax.swing.JButton();
        jButton_Ingred_Add_Ingredient_From_Scratch = new javax.swing.JButton();
        jButton_Ingred_Add_Ingredient = new javax.swing.JButton();
        jButtonIngredPrint = new javax.swing.JButton();
        jButton_Ingredients_Save_Invert = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel_Ingred_table3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_Ingred_Table3 = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        jButton_Ingred_Del_Table_3 = new javax.swing.JButton();
        jButton_Ingred_Add_Table_3 = new javax.swing.JButton();
        jButton_Ingredients_Save_Table3 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jPanel_Ingred_table4 = new javax.swing.JPanel();
        jScrollPane_Ingred_Comments = new javax.swing.JScrollPane();
        jEditorPane_Ingred = new javax.swing.JEditorPane();
        jButton_Ingredients_Save_Comments = new javax.swing.JButton();
        jScrollPaneVendors = new javax.swing.JScrollPane();
        jPanelVendors = new JPanel();
        jPanelVendorsInvertTableIngreds = new javax.swing.JPanel();
        jPanelVendorsInvertTableWarehouse = new javax.swing.JPanel();
        jPanelInvertTable3 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jButtonVendorsSaveTable2 = new javax.swing.JButton();
        jPanelInvertTable4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanelInvertTable4_2 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jLabel38 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jButtonVendorsPrintTable4 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jComboBoxVendorChooseIngred = new JComboBoxA();
        jLabel36 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jButton_vendors_prev_ingred = new javax.swing.JButton();
        jButton_vendors_next_ingred = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jButtonVendorsAddTradeNameToTable3 = new javax.swing.JButton();
        jButtonVendorsDeleteVendorFromTable3 = new javax.swing.JButton();
        jButtonVendorsPrintTable3 = new javax.swing.JButton();
        jButtonVendorsSaveTable3 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jButtonVendorsAddToTable4_2 = new javax.swing.JButton();
        jButtonVendorsDeleteFromTable4_2 = new javax.swing.JButton();
        jButtonVendorsTable4_2Print = new javax.swing.JButton();
        jButtonVendorsSaveTable4_2 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jButtonVendorsPrintTable1 = new javax.swing.JButton();
        jButtonVendorsSaveTable1 = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jScrollPaneVenodrsB = new javax.swing.JScrollPane();
        jPanelVendorsB = new javax.swing.JPanel();
        jPanelInvertTable4_B = new javax.swing.JPanel();
        jPanelInvertTable3_2_B = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jPanelVendorsBSeparator1 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jButtonVendorsAddToTable3_2 = new javax.swing.JButton();
        jButtonVendorsDeleteFromTable3_2 = new javax.swing.JButton();
        jButtonVendorsSaveTable3_2 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jButtonVendorsAddToTable4 = new javax.swing.JButton();
        jButtonVendorsDeleteFromTable4 = new javax.swing.JButton();
        jButtonVendorsSaveTable4 = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jScrollPaneSequence = new javax.swing.JScrollPane();
        jPanel10Sequence = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableSequnece1 = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableSequnce2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jComboBoxSequenceRecipe = new JComboBoxA();
        jComboBoxSequenceRelease = new JComboBoxA();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxSequenceMixerCode = new JComboBoxA();
        jLabel30 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jButtonSequenceSearch = new javax.swing.JButton();
        jButtonBoxesClear = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jButton_sequence_prev_recipe = new javax.swing.JButton();
        jButton_sequence_next_recipe = new javax.swing.JButton();
        jPanelSequence3 = new javax.swing.JPanel();
        jTextFieldStepNrSequence = new javax.swing.JTextField();
        jComboBoxCommandNameSequence = new JComboBoxA();
        jTextFieldCommandParamSequence = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButtonSequenceAddStep = new javax.swing.JButton();
        jButtonSequenceAddLastStep = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jTextFieldInfoSequence = new javax.swing.JTextField();
        jTextFieldUpdateOnSequence = new javax.swing.JTextField();
        jTextFieldUpdatedBy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jComboBoxSequenceRecipeCopy = new JComboBoxA();
        jComboBoxSequenceReleaseCopy = new JComboBoxA();
        jComboBoxSequenceMixerCodeCopy = new JComboBoxA();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jButtonSequenceViewBeforeCopy = new javax.swing.JButton();
        jButtonSequenceCopy = new javax.swing.JButton();
        jButtonBoxesClearCopy = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jScrollPaneRecipeAdditional = new javax.swing.JScrollPane();
        jPanelRecipeAdditional = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable_1_RecipeAdd = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable_2_RecipeAdd = new javax.swing.JTable();
        jComboBoxRecipeAdditionalOrders = new JComboBoxA();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jButtonRecipeAdditionalPrint1 = new javax.swing.JButton();
        jButtonRecipeAdditionalPrint2 = new javax.swing.JButton();
        jPanel_Log = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1_Logg = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jPanel52 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jPanelHome.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mixcont_logo.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forall/icon3.png"))); // NOI18N
        jButton1.setToolTipText("Admin rights required");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(102, 102, 102));
        jLabel69.setText(LNG.USER_NAME());

        jTextFieldHomeUserName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        jButton8.setToolTipText("Predefined password required");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(102, 102, 102));
        jLabel72.setText(LNG.PASSWORD());

        jPasswordFieldHomePass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabelHomeVersion.setText("Version");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText(LNG.USERS());

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("MCPROD PLAN");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(204, 204, 204));
        jLabel46.setText("MCRecipe");

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/groups.png"))); // NOI18N
        jButton19.setToolTipText("Admin rights required");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(102, 102, 102));
        jLabel54.setText(LNG.ADMIN_TOOLS());

        jButton_Home_Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_Home_Login.setToolTipText("Login");
        jButton_Home_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Home_LoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelHomeLayout = new javax.swing.GroupLayout(jPanelHome);
        jPanelHome.setLayout(jPanelHomeLayout);
        jPanelHomeLayout.setHorizontalGroup(
            jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHomeLayout.createSequentialGroup()
                .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel47))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel54))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHomeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_Home_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldHomeUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPasswordFieldHomePass, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(839, Short.MAX_VALUE))))
            .addGroup(jPanelHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHomeLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelHomeVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelHomeLayout.setVerticalGroup(
            jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHomeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel46)))
                .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel69)
                        .addGap(13, 13, 13)
                        .addComponent(jTextFieldHomeUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel72)
                            .addComponent(jLabel54))
                        .addGap(13, 13, 13)
                        .addGroup(jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelHomeLayout.createSequentialGroup()
                                .addComponent(jPasswordFieldHomePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_Home_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelHomeLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 894, Short.MAX_VALUE)
                .addComponent(jLabelHomeVersion)
                .addGap(34, 34, 34))
        );

        jTabbedPane1.addTab("*HOME", jPanelHome);

        jScrollPaneRecipeInitial.setAutoscrolls(true);
        jScrollPaneRecipeInitial.setPreferredSize(new java.awt.Dimension(1380, 930));

        jPanel_RecipeInitial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_RecipeInitial.setAutoscrolls(true);
        jPanel_RecipeInitial.setPreferredSize(new java.awt.Dimension(1380, 928));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1_Recipe_Origin.setEditable(false);
        jComboBox1_Recipe_Origin.setModel(new javax.swing.DefaultComboBoxModel());
        jComboBox1_Recipe_Origin.setToolTipText("");
        jPanel2.add(jComboBox1_Recipe_Origin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 157, -1));

        jComboBox2_Detailed_Group.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox2_Detailed_Group, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 157, -1));

        jComboBox3_Recipe_Stage.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox3_Recipe_Stage, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 157, -1));

        jComboBox4_Mixer_Code.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox4_Mixer_Code, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 157, -1));

        jComboBox5_Recipe_Version.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox5_Recipe_Version, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 157, -1));

        jComboBox6_Status.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox6_Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 157, -1));

        jComboBox7_RecipeAdditional.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox7_RecipeAdditional, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 157, -1));

        jComboBox8_Class.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox8_Class, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 157, -1));

        jLabel12.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_ORIGIN());
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 150, -1));

        jLabel13.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_STAGE());
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 150, -1));

        jLabel14.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_VERSION());
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        jLabel15.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_ADDITIONAL());
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel16.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__POLYMER_GROUP());
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel17.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__MIXER());
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));

        jLabel18.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__STATUS());
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, -1));

        jLabel19.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__INGREDIENT_2());
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, -1, -1));

        jComboBox_Ingred_1.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox_Ingred_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 40, 140, -1));

        jLabel55.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__CLASS());
        jPanel2.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, -1, -1));

        jComboBox_Ingred_2.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox_Ingred_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 90, 140, -1));

        jCheckBoxRecipeInitialSearchByIngredients.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__INGREDIENTS_CHKBOX());
        jCheckBoxRecipeInitialSearchByIngredients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRecipeInitialSearchByIngredientsActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBoxRecipeInitialSearchByIngredients, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, 130, -1));

        jComboBox_Description1.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel2.add(jComboBox_Description1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 40, 140, -1));

        jPanel43.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeInitialGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonRecipeInitialGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialGoActionPerformed(evt);
            }
        });
        jPanel43.add(jButtonRecipeInitialGo);

        jButtonRecipeInitialResetComboBoxes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonRecipeInitialResetComboBoxes.setToolTipText(TOOLTIP.RESET_BOXES_BTN_TOOLTIP());
        jButtonRecipeInitialResetComboBoxes.setPreferredSize(new java.awt.Dimension(65, 43));
        jButtonRecipeInitialResetComboBoxes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialResetComboBoxesActionPerformed(evt);
            }
        });
        jPanel43.add(jButtonRecipeInitialResetComboBoxes);

        jPanel2.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 70, 100, 50));

        jCheckBoxRecipeInitialOR.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__OR_CHKBOX());
        jPanel2.add(jCheckBoxRecipeInitialOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 80, 130, -1));

        jLabel86.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_DESCRIPTION());
        jPanel2.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

        jLabel87.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__INGREDIENT_1());
        jPanel2.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, -1, -1));

        jPanel46.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 40, 130, 50));

        jScrollPane17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jScrollPane17.setViewportView(jTableIngredientInfoTable);

        jPanel2.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 40, 150, 50));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_SET());

        jPanel41.setPreferredSize(new java.awt.Dimension(180, 50));
        jPanel41.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeInitialRemoweAllFromCompare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonRecipeInitialRemoweAllFromCompare.setToolTipText(TOOLTIP.REMOVE_ALL_COMPARE_TOOLTIP());
        jButtonRecipeInitialRemoweAllFromCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialRemoweAllFromCompareActionPerformed(evt);
            }
        });
        jPanel41.add(jButtonRecipeInitialRemoweAllFromCompare);

        jButtonRecipeInitialShowCompare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonRecipeInitialShowCompare.setToolTipText(TOOLTIP.SHOW_COMPARED_RECIPES());
        jButtonRecipeInitialShowCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialShowCompareActionPerformed(evt);
            }
        });
        jPanel41.add(jButtonRecipeInitialShowCompare);

        jButtonRecipeInitialAddToCompare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonRecipeInitialAddToCompare.setToolTipText(TOOLTIP.ADD_RECIPE_TO_COMPARE());
        jButtonRecipeInitialAddToCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialAddToCompareActionPerformed(evt);
            }
        });
        jPanel41.add(jButtonRecipeInitialAddToCompare);

        jPanel42.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel42.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeInitialPrintTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonRecipeInitialPrintTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialPrintTable1ActionPerformed(evt);
            }
        });
        jPanel42.add(jButtonRecipeInitialPrintTable1);

        jLabelRecordsTable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelRecordsTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel44))
                    .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jLabelRecordsTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable2);

        jPanel5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__RECIPE_INFO());

        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel17.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(102, 102, 102));
        jLabel67.setText(MCRecipe.Lang.RECIPE_OVERVIEW_.RECIPE_OVERVIEW__SORT_BY_NOTE_VALUE());

        jPanel44.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel44.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeInitialGo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonRecipeInitialGo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialGo2ActionPerformed(evt);
            }
        });
        jPanel44.add(jButtonRecipeInitialGo2);

        jButtonRecipeInitialResetComboBoxes3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonRecipeInitialResetComboBoxes3.setToolTipText(TOOLTIP.RESET_BOXES_BTN_TOOLTIP());
        jButtonRecipeInitialResetComboBoxes3.setPreferredSize(new java.awt.Dimension(65, 43));
        jButtonRecipeInitialResetComboBoxes3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialResetComboBoxes3ActionPerformed(evt);
            }
        });
        jPanel44.add(jButtonRecipeInitialResetComboBoxes3);

        jPanel47.setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable3);

        jPanel6.add(jScrollPane4);

        jTabbedPane2.addTab("NOTES", jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel7.setLayout(null);

        jScrollPaneRecipeInitialNotes.setViewportView(jEditorPaneRecipeInitialNotes);

        jPanel7.add(jScrollPaneRecipeInitialNotes);
        jScrollPaneRecipeInitialNotes.setBounds(2, 2, 108, 237);

        jTabbedPane2.addTab("NOTES DETAILS", jPanel7);

        jPanel47.add(jTabbedPane2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel45)
                                .addComponent(jLabel67))
                            .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_RecipeInitialLayout = new javax.swing.GroupLayout(jPanel_RecipeInitial);
        jPanel_RecipeInitial.setLayout(jPanel_RecipeInitialLayout);
        jPanel_RecipeInitialLayout.setHorizontalGroup(
            jPanel_RecipeInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RecipeInitialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_RecipeInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE))
                .addContainerGap(399, Short.MAX_VALUE))
        );
        jPanel_RecipeInitialLayout.setVerticalGroup(
            jPanel_RecipeInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RecipeInitialLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(541, Short.MAX_VALUE))
        );

        jScrollPaneRecipeInitial.setViewportView(jPanel_RecipeInitial);

        jTabbedPane1.addTab("RECIPE OVERVIEW", jScrollPaneRecipeInitial);

        jScrollPaneRecipeDetailed.setPreferredSize(new java.awt.Dimension(1450, 931));

        jPanel_RecipeDetailed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_RecipeDetailed.setAutoscrolls(true);
        jPanel_RecipeDetailed.setMaximumSize(new java.awt.Dimension(1450, 32767));

        jPanel1_recipe_detailed.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1_recipe_detailed.setLayout(new java.awt.GridLayout(1, 0));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel14.setLayout(null);

        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jScrollPane8.setViewportView(jTable4RecipeDetailed);

        jPanel18.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton7.setPreferredSize(new java.awt.Dimension(60, 50));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calc.png"))); // NOI18N
        jButton11.setToolTipText("Recalculate");
        jButton11.setPreferredSize(new java.awt.Dimension(60, 50));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText(R_DETAILED.RECIPE_TABLE());

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(102, 102, 102));
        jLabel49.setText(R_DETAILED.NOTES_TABLE());

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText(R_DETAILED.INFO_TABLE());

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText(R_DETAILED.RECIPE_MANAGER_TABLE());

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText(R_DETAILED.NOTE_DETAILS_TABLE());

        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0));

        jTableRecipeDetailedTable4HelpTable.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jTableRecipeDetailedTable4HelpTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTableRecipeDetailedTable4HelpTable);
        //jTableRecipeDetailedTable4HelpTable.setTableHeader(null);

        jPanel19.add(jScrollPane5);

        jPanel35.setLayout(new java.awt.GridLayout(1, 0));

        jButton_recipe_detailed_delete_recipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_recipe_detailed_delete_recipe.setToolTipText(TOOLTIP.DELETE_RECIPE_BTN_TOOLTIP());
        jButton_recipe_detailed_delete_recipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_recipe_detailed_delete_recipeActionPerformed(evt);
            }
        });
        jPanel35.add(jButton_recipe_detailed_delete_recipe);

        jButtonRecipeInitialUnblock1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/unlocked.png"))); // NOI18N
        jButtonRecipeInitialUnblock1.setToolTipText("Unblock recipe");
        jButtonRecipeInitialUnblock1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialUnblock1ActionPerformed(evt);
            }
        });
        jPanel35.add(jButtonRecipeInitialUnblock1);

        jButtonRecipeDetailedAddRecipeFromScratch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add_b.png"))); // NOI18N
        jButtonRecipeDetailedAddRecipeFromScratch.setToolTipText("Create new recipe from scratch");
        jButtonRecipeDetailedAddRecipeFromScratch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedAddRecipeFromScratchActionPerformed(evt);
            }
        });
        jPanel35.add(jButtonRecipeDetailedAddRecipeFromScratch);

        jButtonRecipeDetailedAddNewRecipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonRecipeDetailedAddNewRecipe.setToolTipText("Cretates a new recipe from the actual one");
        jButtonRecipeDetailedAddNewRecipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedAddNewRecipeActionPerformed(evt);
            }
        });
        jPanel35.add(jButtonRecipeDetailedAddNewRecipe);

        jButtonRecipeInitialUnblock2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/visible.png"))); // NOI18N
        jButtonRecipeInitialUnblock2.setToolTipText("Hide/Unhide some fields");
        jButtonRecipeInitialUnblock2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialUnblock2ActionPerformed(evt);
            }
        });
        jPanel35.add(jButtonRecipeInitialUnblock2);

        jButtonRecipeDetailedPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonRecipeDetailedPrint.setToolTipText("");
        jButtonRecipeDetailedPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedPrintActionPerformed(evt);
            }
        });
        jPanel35.add(jButtonRecipeDetailedPrint);

        jButton_Recipe_Detailed_Save_Invert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_Recipe_Detailed_Save_Invert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Recipe_Detailed_Save_InvertActionPerformed(evt);
            }
        });
        jPanel35.add(jButton_Recipe_Detailed_Save_Invert);

        jPanel36.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel36.setLayout(new java.awt.GridLayout(1, 0));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton6.setToolTipText("Add info");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton3.setToolTipText("Change Note Value");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton4);

        jPanel37.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel37.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeDetailedAddNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonRecipeDetailedAddNote.setToolTipText("Add note");
        jButtonRecipeDetailedAddNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedAddNoteActionPerformed(evt);
            }
        });
        jPanel37.add(jButtonRecipeDetailedAddNote);

        jButtonRDetailedChangeNoteName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButtonRDetailedChangeNoteName.setToolTipText("Change Note Name");
        jButtonRDetailedChangeNoteName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRDetailedChangeNoteNameActionPerformed(evt);
            }
        });
        jPanel37.add(jButtonRDetailedChangeNoteName);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel37.add(jButton5);

        jPanel38.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel38.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButton_r_detailed_prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/prev.png"))); // NOI18N
        jButton_r_detailed_prev.setToolTipText("previous recipe");
        jButton_r_detailed_prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_r_detailed_prevActionPerformed(evt);
            }
        });
        jPanel38.add(jButton_r_detailed_prev);

        jButton_r_detailed_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        jButton_r_detailed_next.setToolTipText("next recipe");
        jButton_r_detailed_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_r_detailed_nextActionPerformed(evt);
            }
        });
        jPanel38.add(jButton_r_detailed_next);

        jPanel39.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel39.setLayout(new java.awt.GridLayout(1, 0, 1, 0));

        jButton20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton20.setText("0.0");
        jButton20.setToolTipText("Edit ammount of digits after comma. Restart of program is required!");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel39.add(jButton20);

        jButtonRecipeDetailedUndoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/undo_b.png"))); // NOI18N
        jButtonRecipeDetailedUndoDelete.setToolTipText("Undo delete");
        jButtonRecipeDetailedUndoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedUndoDeleteActionPerformed(evt);
            }
        });
        jPanel39.add(jButtonRecipeDetailedUndoDelete);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton2.setToolTipText("Mark to delete, delete is done after save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel39.add(jButton2);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton10.setToolTipText("Add Ingredient");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel39.add(jButton10);

        jButtonRecipeDetailedSwap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/swap.png"))); // NOI18N
        jButtonRecipeDetailedSwap.setToolTipText("Swap PHR's and Weights");
        jButtonRecipeDetailedSwap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedSwapActionPerformed(evt);
            }
        });
        jPanel39.add(jButtonRecipeDetailedSwap);

        jButtonRecipeDetailedResetChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/undo_a.png"))); // NOI18N
        jButtonRecipeDetailedResetChanges.setToolTipText("Reset changes");
        jButtonRecipeDetailedResetChanges.setPreferredSize(new java.awt.Dimension(60, 50));
        jButtonRecipeDetailedResetChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedResetChangesActionPerformed(evt);
            }
        });
        jPanel39.add(jButtonRecipeDetailedResetChanges);

        jPanel40.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel40.setLayout(new java.awt.GridLayout(1, 0));

        jButtonTable4Repport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonTable4Repport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTable4RepportActionPerformed(evt);
            }
        });
        jPanel40.add(jButtonTable4Repport);

        jButtonRecipeDetailedSaveTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonRecipeDetailedSaveTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedSaveTable4ActionPerformed(evt);
            }
        });
        jPanel40.add(jButtonRecipeDetailedSaveTable4);

        jLabelLockedUnlocked.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/unlocked.png"))); // NOI18N

        javax.swing.GroupLayout jPanel_RecipeDetailedLayout = new javax.swing.GroupLayout(jPanel_RecipeDetailed);
        jPanel_RecipeDetailed.setLayout(jPanel_RecipeDetailedLayout);
        jPanel_RecipeDetailedLayout.setHorizontalGroup(
            jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1_recipe_detailed, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabelLockedUnlocked)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(48, 48, 48)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(42, 42, 42)
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_RecipeDetailedLayout.setVerticalGroup(
            jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelLockedUnlocked)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_RecipeDetailedLayout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel49)
                                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_RecipeDetailedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel51)
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1_recipe_detailed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(71, 71, 71))
        );

        jPanel1_recipe_detailed.getAccessibleContext().setAccessibleName("");
        jPanel1_recipe_detailed.getAccessibleContext().setAccessibleDescription("");

        jScrollPaneRecipeDetailed.setViewportView(jPanel_RecipeDetailed);

        jTabbedPane1.addTab("RECIPE DETAILED", jScrollPaneRecipeDetailed);

        jScrollPane23.setViewportView(jPanel_lab_dev__find_order);

        jButton__lab_dev_find_order_tab__filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/filter.png"))); // NOI18N
        jButton__lab_dev_find_order_tab__filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton__lab_dev_find_order_tab__filterActionPerformed(evt);
            }
        });

        jTable_lab_dev__find_order.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane24.setViewportView(jTable_lab_dev__find_order);

        jTextField__lab_dev__find_order.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton__lab_dev_find_order_tab__set_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton__lab_dev_find_order_tab__set_order.setEnabled(false);
        jButton__lab_dev_find_order_tab__set_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton__lab_dev_find_order_tab__set_orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton__lab_dev_find_order_tab__filter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addComponent(jButton__lab_dev_find_order_tab__set_order, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jTextField__lab_dev__find_order, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton__lab_dev_find_order_tab__filter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField__lab_dev__find_order, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton__lab_dev_find_order_tab__set_order, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane24)
                    .addComponent(jScrollPane23))
                .addContainerGap(292, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("FIND ORDER", jPanel61);

        jPanel_lab_development.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_development.setLayout(new java.awt.GridLayout(1, 0));

        jButton_lab_dev_save_btn_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_lab_dev_save_btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_save_btn_1ActionPerformed(evt);
            }
        });

        jTable_lab_dev_1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane18.setViewportView(jTable_lab_dev_1);

        jTable_lab_dev_2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane20.setViewportView(jTable_lab_dev_2);

        jPanel55.setLayout(new java.awt.GridLayout(1, 0));

        jButton_lab_dev_add_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_lab_dev_add_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_add_1ActionPerformed(evt);
            }
        });
        jPanel55.add(jButton_lab_dev_add_1);

        jButton_lab_dev_delete_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_lab_dev_delete_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_delete_1ActionPerformed(evt);
            }
        });
        jPanel55.add(jButton_lab_dev_delete_1);

        jPanel56.setLayout(new java.awt.GridLayout(1, 0));

        jButton_lab_dev_add_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_lab_dev_add_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_add_2ActionPerformed(evt);
            }
        });
        jPanel56.add(jButton_lab_dev_add_2);

        jButton_lab_dev_delete_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_lab_dev_delete_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_delete_2ActionPerformed(evt);
            }
        });
        jPanel56.add(jButton_lab_dev_delete_2);

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(102, 102, 102));
        jLabel59.setText(LAB_DEV.STATUS_TAB_JTABLE_2());

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText(LAB_DEV.STATUS_TAB_JTABLE_1());

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_lab_development, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_lab_dev_save_btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                    .addComponent(jScrollPane20)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(149, 149, 149))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_lab_dev_save_btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_lab_development, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(156, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("MAIN DATA", jPanel53);

        jPanel_lab_dev_material_info.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_dev_material_info.setLayout(new java.awt.GridLayout(1, 0));

        jTable_lab_dev__material_info.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane22.setViewportView(jTable_lab_dev__material_info);

        jButton_lab_dev__material_info_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_lab_dev__material_info_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev__material_info_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jButton_lab_dev__material_info_save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jPanel_lab_dev_material_info, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_lab_dev__material_info_save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_lab_dev_material_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("MATERIAL INFO", jPanel60);

        jTable_test_definitions.setModel(new javax.swing.table.DefaultTableModel());
        jScrollPane27.setViewportView(jTable_test_definitions);

        jButton_lab_dev_test_definition_goto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton_lab_dev_test_definition_goto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_test_definition_gotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 1119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_lab_dev_test_definition_goto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_lab_dev_test_definition_goto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(292, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("TEST DEFINITIONS", jPanel62);

        jPanel64.setLayout(new java.awt.BorderLayout());

        jPanel66.setPreferredSize(new java.awt.Dimension(430, 500));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0);
        flowLayout2.setAlignOnBaseline(true);
        jPanel66.setLayout(flowLayout2);
        jScrollPane25.setViewportView(jPanel66);

        jPanel65.setPreferredSize(new java.awt.Dimension(430, 500));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        jPanel65.setLayout(flowLayout1);
        jScrollPane26.setViewportView(jPanel65);

        jButton_lab_dev_tab__save_config_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_lab_dev_tab__save_config_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_tab__save_config_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_lab_dev_tab__save_config_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_lab_dev_tab__save_config_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("TEST CONFIG", jPanel63);

        jPanel_lab_development_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_development_2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel_lab_development_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_development_3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel_lab_development_4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_development_4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel_lab_development_5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_lab_development_5.setLayout(new java.awt.GridLayout(1, 0));

        jButton_lab_dev_save_btn_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_lab_dev_save_btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_save_btn_2ActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(102, 102, 102));
        jLabel56.setText(LAB_DEV.TABLE_INVERT_NAME_3());

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText(LAB_DEV.TABLE_INVERT_NAME_4());

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText(LAB_DEV.TABLE_INVERT_NAME_5());

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel_lab_development_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel_lab_development_2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_lab_dev_save_btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel56))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel_lab_development_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel_lab_development_5, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_lab_dev_save_btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_lab_development_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_lab_development_2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel58))
                .addGap(6, 6, 6)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_lab_development_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_lab_development_5, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jTabbedPane3_Lab_Dev.addTab("STATUS", jPanel54);

        jTextArea_notes__lab_dev_tab.setColumns(20);
        jTextArea_notes__lab_dev_tab.setRows(5);
        jScrollPane21.setViewportView(jTextArea_notes__lab_dev_tab);

        jButton_lab_dev_tab__save_notes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_lab_dev_tab__save_notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lab_dev_tab__save_notesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_lab_dev_tab__save_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(379, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_lab_dev_tab__save_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jTabbedPane3_Lab_Dev.addTab("NOTES", jPanel59);

        jPanel_lab_dev_header.setLayout(new java.awt.GridLayout(2, 1));

        jPanel58.setLayout(new java.awt.GridLayout(1, 5, 10, 0));
        jPanel_lab_dev_header.add(jPanel58);

        jPanel57.setLayout(new java.awt.GridLayout(1, 5, 10, 0));
        jPanel_lab_dev_header.add(jPanel57);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3_Lab_Dev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_lab_dev_header, javax.swing.GroupLayout.PREFERRED_SIZE, 1399, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(342, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel_lab_dev_header, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3_Lab_Dev, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(602, Short.MAX_VALUE))
        );

        jScrollPane_LabDevelopment.setViewportView(jPanel1);

        jTabbedPane1.addTab("LAB DEVELOPMENT", jScrollPane_LabDevelopment);

        jPanel_Test_Parameters.setPreferredSize(new java.awt.Dimension(1250, 929));

        jPanel_Test_Params_Inv_Table_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Test_Params_Inv_Table_1.setLayout(new java.awt.BorderLayout());

        jTable_1_test_parameters.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane19.setViewportView(jTable_1_test_parameters);

        jComboBoxTestParams_Order.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxTestPararams_Recipe.setModel(new javax.swing.DefaultComboBoxModel());

        jPanel50.setLayout(new java.awt.GridLayout(1, 0));

        jButtonTestParametersGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonTestParametersGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestParametersGoActionPerformed(evt);
            }
        });
        jPanel50.add(jButtonTestParametersGo);

        jButtonTestParametersClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonTestParametersClear.setToolTipText(TOOLTIP.RESET_BOXES_BTN_TOOLTIP());
        jButtonTestParametersClear.setPreferredSize(new java.awt.Dimension(65, 43));
        jButtonTestParametersClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestParametersClearActionPerformed(evt);
            }
        });
        jPanel50.add(jButtonTestParametersClear);

        jLabel73.setText(MCRecipe.Lang.T_INV.LANG("ORDER"));

        jLabel88.setText(MCRecipe.Lang.T_INV.LANG("RECIPE"));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxTestParams_Order, 0, 135, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxTestPararams_Recipe, 0, 135, Short.MAX_VALUE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel73)
                            .addComponent(jLabel88))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxTestParams_Order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTestPararams_Recipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel51.setLayout(new java.awt.GridLayout(1, 0));

        jButtonTestParametersPrint_InvertTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonTestParametersPrint_InvertTable.setToolTipText("print");
        jButtonTestParametersPrint_InvertTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestParametersPrint_InvertTableActionPerformed(evt);
            }
        });
        jPanel51.add(jButtonTestParametersPrint_InvertTable);

        jButtonTestParametersSave_InvertTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonTestParametersSave_InvertTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestParametersSave_InvertTableActionPerformed(evt);
            }
        });
        jPanel51.add(jButtonTestParametersSave_InvertTable);

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(102, 102, 102));
        jLabel89.setText(MCRecipe.Lang.TEST_PARAMS.TABLE_INVERT_NAME());

        javax.swing.GroupLayout jPanel_Test_ParametersLayout = new javax.swing.GroupLayout(jPanel_Test_Parameters);
        jPanel_Test_Parameters.setLayout(jPanel_Test_ParametersLayout);
        jPanel_Test_ParametersLayout.setHorizontalGroup(
            jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Test_ParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel_Test_Params_Inv_Table_1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_Test_ParametersLayout.createSequentialGroup()
                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_Test_ParametersLayout.createSequentialGroup()
                        .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(759, Short.MAX_VALUE))
                    .addGroup(jPanel_Test_ParametersLayout.createSequentialGroup()
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 1210, Short.MAX_VALUE)
                        .addGap(31, 31, 31))))
        );
        jPanel_Test_ParametersLayout.setVerticalGroup(
            jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Test_ParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_Test_ParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_Test_Params_Inv_Table_1, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                    .addComponent(jScrollPane19))
                .addContainerGap(632, Short.MAX_VALUE))
        );

        jScrollPaneTestParameters.setViewportView(jPanel_Test_Parameters);

        jTabbedPane1.addTab("TEST PARAMETERS", jScrollPaneTestParameters);

        jPanel_Ingredients.setPreferredSize(new java.awt.Dimension(1637, 929));

        jPanel_Ingred_boxes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jCombo_Ingred_Name.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Group.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Descr.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Class.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Status.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Form.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Cas_Number.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_TradeName.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_VendorName.setModel(new javax.swing.DefaultComboBoxModel());

        jCombo_Ingred_Perc_Rubber.setModel(new javax.swing.DefaultComboBoxModel());

        jLabel20.setText(INGR.NAME());

        jLabel21.setText(INGR.DESCR());

        jLabel22.setText(INGR.STATUS());

        jLabel23.setText(INGR.CAS_NR());

        jLabel24.setText(INGR.VENDOR());

        jLabel25.setText(INGR.GROUP());

        jLabel26.setText(INGR.CLASS());

        jLabel27.setText(INGR.FORM());

        jLabel28.setText(INGR.TRADE_NAME());

        jLabel29.setText(INGR.PERC_RUBBER());

        jPanel33.setLayout(new java.awt.GridLayout(1, 0));

        jButtonIngredGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonIngredGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredGoActionPerformed(evt);
            }
        });
        jPanel33.add(jButtonIngredGo);

        jButtonIngredClearBoxes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonIngredClearBoxes.setToolTipText(TOOLTIP.RESET_BOXES_BTN_TOOLTIP());
        jButtonIngredClearBoxes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredClearBoxesActionPerformed(evt);
            }
        });
        jPanel33.add(jButtonIngredClearBoxes);

        javax.swing.GroupLayout jPanel_Ingred_boxesLayout = new javax.swing.GroupLayout(jPanel_Ingred_boxes);
        jPanel_Ingred_boxes.setLayout(jPanel_Ingred_boxesLayout);
        jPanel_Ingred_boxesLayout.setHorizontalGroup(
            jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jCombo_Ingred_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCombo_Ingred_Descr, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jCombo_Ingred_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jCombo_Ingred_Cas_Number, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jCombo_Ingred_VendorName, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jCombo_Ingred_Group, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCombo_Ingred_Class, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCombo_Ingred_Form, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)))
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCombo_Ingred_TradeName, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jCombo_Ingred_Perc_Rubber, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_Ingred_boxesLayout.setVerticalGroup(
            jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(8, 8, 8)
                        .addComponent(jCombo_Ingred_Class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCombo_Ingred_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo_Ingred_Descr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo_Ingred_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo_Ingred_Cas_Number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo_Ingred_VendorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(8, 8, 8)
                        .addComponent(jCombo_Ingred_Group, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(8, 8, 8)
                        .addComponent(jCombo_Ingred_Form, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_Ingred_boxesLayout.createSequentialGroup()
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_Ingred_boxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCombo_Ingred_TradeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo_Ingred_Perc_Rubber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel_Ingred_Table1_Cont.setLayout(new java.awt.BorderLayout());

        jTable_Ingred_Table1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane_Ingred_Table1.setViewportView(jTable_Ingred_Table1);

        jPanel_Ingred_Table1_Cont.add(jScrollPane_Ingred_Table1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Ingred_Table1_Cont, javax.swing.GroupLayout.PREFERRED_SIZE, 1431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Ingred_Table1_Cont, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Ingred_table2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel_Ingred_table2.setLayout(new java.awt.BorderLayout());

        jTable_Ingred_table2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_Ingred_table2);

        jPanel_Ingred_table2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText(INGR.EXPRESS_INFO_TBL());

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText(INGR.WAREHOUSE_TBL());

        jButtonIngredExpressInfoPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonIngredExpressInfoPrint.setToolTipText("");
        jButtonIngredExpressInfoPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredExpressInfoPrintActionPerformed(evt);
            }
        });

        jPanel45.setLayout(new java.awt.GridLayout(1, 0));

        jButton12.setText(INGR.ADD_INGREDIENT_TO_R_DETAILED());
        jButton12.setToolTipText(INGR.ADD_INGREDIENT_TO_R_DETAILED_TOOLTIP());
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton12);

        jButtonIngredientsPasteIngred2.setText(INGR.PASTE_INGRED_2_BTN());
        jButtonIngredientsPasteIngred2.setToolTipText(INGR.PASTE_INGRED_2_BTN_TOOLTIP());
        jButtonIngredientsPasteIngred2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredientsPasteIngred2ActionPerformed(evt);
            }
        });
        jPanel45.add(jButtonIngredientsPasteIngred2);

        jButtonIngredientsPasteIngred1.setText(INGR.PASTE_INGRED_1_BTN());
        jButtonIngredientsPasteIngred1.setToolTipText(INGR.PASTE_INGRED_1_BTN_TOOLTIP());
        jButtonIngredientsPasteIngred1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredientsPasteIngred1ActionPerformed(evt);
            }
        });
        jPanel45.add(jButtonIngredientsPasteIngred1);

        javax.swing.GroupLayout jPanel_IngredientsLayout = new javax.swing.GroupLayout(jPanel_Ingredients);
        jPanel_Ingredients.setLayout(jPanel_IngredientsLayout);
        jPanel_IngredientsLayout.setHorizontalGroup(
            jPanel_IngredientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_IngredientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_IngredientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_IngredientsLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_IngredientsLayout.createSequentialGroup()
                        .addGroup(jPanel_IngredientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel_Ingred_table2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_Ingred_boxes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel_IngredientsLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonIngredExpressInfoPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(177, 177, 177))))
        );
        jPanel_IngredientsLayout.setVerticalGroup(
            jPanel_IngredientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_IngredientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Ingred_boxes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_IngredientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(jButtonIngredExpressInfoPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Ingred_table2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPaneIngredients.setViewportView(jPanel_Ingredients);

        jTabbedPane1.addTab("INGREDIENTS", jScrollPaneIngredients);

        jPanelIngredientsB.setPreferredSize(new java.awt.Dimension(1637, 1450));

        jPanel_Ingred_Invert_Table.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Ingred_Invert_Table.setLayout(new java.awt.GridLayout(1, 0));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText(INGR.INGRED_DETAILED_TBL());

        jPanel32.setLayout(new java.awt.GridLayout(1, 0));

        jButton_Ingred_delete_Ingred.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_Ingred_delete_Ingred.setToolTipText(TOOLTIP.DELETE_INGRED());
        jButton_Ingred_delete_Ingred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingred_delete_IngredActionPerformed(evt);
            }
        });
        jPanel32.add(jButton_Ingred_delete_Ingred);

        jButton_Ingred_Add_Ingredient_From_Scratch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add_b.png"))); // NOI18N
        jButton_Ingred_Add_Ingredient_From_Scratch.setToolTipText(TOOLTIP.ADD_INGR_FROM_SCRATCH());
        jButton_Ingred_Add_Ingredient_From_Scratch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingred_Add_Ingredient_From_ScratchActionPerformed(evt);
            }
        });
        jPanel32.add(jButton_Ingred_Add_Ingredient_From_Scratch);

        jButton_Ingred_Add_Ingredient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_Ingred_Add_Ingredient.setToolTipText(TOOLTIP.ADD_INGR());
        jButton_Ingred_Add_Ingredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingred_Add_IngredientActionPerformed(evt);
            }
        });
        jPanel32.add(jButton_Ingred_Add_Ingredient);

        jButtonIngredPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonIngredPrint.setToolTipText("");
        jButtonIngredPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngredPrintActionPerformed(evt);
            }
        });
        jPanel32.add(jButtonIngredPrint);

        jButton_Ingredients_Save_Invert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_Ingredients_Save_Invert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingredients_Save_InvertActionPerformed(evt);
            }
        });
        jPanel32.add(jButton_Ingredients_Save_Invert);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 102));
        jLabel42.setText(INGR.NOTES_TBL());

        jPanel_Ingred_table3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel_Ingred_table3.setLayout(new java.awt.BorderLayout());

        jTable_Ingred_Table3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable_Ingred_Table3);

        jPanel_Ingred_table3.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel34.setLayout(new java.awt.GridLayout(1, 0));

        jButton_Ingred_Del_Table_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_Ingred_Del_Table_3.setToolTipText(TOOLTIP.DELETE_NOTE());
        jButton_Ingred_Del_Table_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingred_Del_Table_3ActionPerformed(evt);
            }
        });
        jPanel34.add(jButton_Ingred_Del_Table_3);

        jButton_Ingred_Add_Table_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_Ingred_Add_Table_3.setToolTipText(TOOLTIP.ADD_NOTE());
        jButton_Ingred_Add_Table_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingred_Add_Table_3ActionPerformed(evt);
            }
        });
        jPanel34.add(jButton_Ingred_Add_Table_3);

        jButton_Ingredients_Save_Table3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_Ingredients_Save_Table3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingredients_Save_Table3ActionPerformed(evt);
            }
        });
        jPanel34.add(jButton_Ingredients_Save_Table3);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText(INGR.NOTES_DETAILED_TBL());

        jPanel_Ingred_table4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel_Ingred_table4.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane_Ingred_Comments.setViewportView(jEditorPane_Ingred);

        jPanel_Ingred_table4.add(jScrollPane_Ingred_Comments);

        jButton_Ingredients_Save_Comments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton_Ingredients_Save_Comments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Ingredients_Save_CommentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelIngredientsBLayout = new javax.swing.GroupLayout(jPanelIngredientsB);
        jPanelIngredientsB.setLayout(jPanelIngredientsBLayout);
        jPanelIngredientsBLayout.setHorizontalGroup(
            jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_Ingred_Invert_Table, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_Ingred_table3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Ingredients_Save_Comments, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_Ingred_table4, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelIngredientsBLayout.setVerticalGroup(
            jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jButton_Ingredients_Save_Comments, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelIngredientsBLayout.createSequentialGroup()
                        .addGroup(jPanelIngredientsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel_Ingred_table4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_Ingred_table3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                        .addGap(0, 948, Short.MAX_VALUE))
                    .addComponent(jPanel_Ingred_Invert_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 1385, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPaneIngredientsB.setViewportView(jPanelIngredientsB);

        jTabbedPane1.addTab("INGREDIENTS B", jScrollPaneIngredientsB);

        jPanelVendors.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelVendorsInvertTableIngreds.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelVendorsInvertTableIngreds.setLayout(new java.awt.BorderLayout());
        jPanelVendors.add(jPanelVendorsInvertTableIngreds, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 109, 482, 1130));

        jPanelVendorsInvertTableWarehouse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelVendorsInvertTableWarehouse.setLayout(new java.awt.BorderLayout());
        jPanelVendors.add(jPanelVendorsInvertTableWarehouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 109, 1100, 200));

        jPanelInvertTable3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelInvertTable3.setLayout(new java.awt.BorderLayout());

        jScrollPane11.setBorder(null);
        jPanelInvertTable3.add(jScrollPane11, java.awt.BorderLayout.CENTER);

        jPanelVendors.add(jPanelInvertTable3, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 367, 1100, 190));

        jButtonVendorsSaveTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable2ActionPerformed(evt);
            }
        });
        jPanelVendors.add(jButtonVendorsSaveTable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1550, 60, 50, 45));

        jPanelInvertTable4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelInvertTable4.setLayout(new java.awt.BorderLayout());
        jPanelVendors.add(jPanelInvertTable4, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 617, 577, 620));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("TABLE 4_2");
        jPanelVendors.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 590, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText(MCRecipe.Lang.VENDOR_A_B.PURCHASE_TABLE());
        jPanelVendors.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText(MCRecipe.Lang.VENDOR_A_B.VENDOR_TABLE());
        jPanelVendors.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 590, -1, -1));

        jPanelInvertTable4_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelInvertTable4_2.setLayout(new java.awt.BorderLayout());
        jPanelInvertTable4_2.add(jScrollPane13, java.awt.BorderLayout.CENTER);

        jPanelVendors.add(jPanelInvertTable4_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1096, 617, 510, 390));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText(MCRecipe.Lang.VENDOR_A_B.PERSONAL_TABLE());
        jPanelVendors.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 590, -1, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 102, 102));
        jLabel53.setText(MCRecipe.Lang.VENDOR_A_B.INGREDIENT_TABLE());
        jPanelVendors.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 80, -1, -1));

        jButtonVendorsPrintTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonVendorsPrintTable4.setToolTipText("");
        jButtonVendorsPrintTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsPrintTable4ActionPerformed(evt);
            }
        });
        jPanelVendors.add(jButtonVendorsPrintTable4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1027, 565, 50, 45));

        jComboBoxVendorChooseIngred.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxVendorChooseIngred.setModel(new javax.swing.DefaultComboBoxModel());

        jLabel36.setText(MCRecipe.Lang.VENDOR_A_B.VENDOR_A__CHOOSE_INGREDIENT_TO_START());

        jPanel25.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel25.setLayout(new java.awt.GridLayout(1, 0));

        jButton_vendors_prev_ingred.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/prev.png"))); // NOI18N
        jButton_vendors_prev_ingred.setToolTipText("previous ingredient");
        jButton_vendors_prev_ingred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_vendors_prev_ingredActionPerformed(evt);
            }
        });
        jPanel25.add(jButton_vendors_prev_ingred);

        jButton_vendors_next_ingred.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        jButton_vendors_next_ingred.setToolTipText("next ingredient");
        jButton_vendors_next_ingred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_vendors_next_ingredActionPerformed(evt);
            }
        });
        jPanel25.add(jButton_vendors_next_ingred);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jComboBoxVendorChooseIngred, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(7, 7, 7)
                        .addComponent(jComboBoxVendorChooseIngred, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        jPanelVendors.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 70));

        jButtonVendorsAddTradeNameToTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonVendorsAddTradeNameToTable3.setToolTipText("Add tradename");
        jButtonVendorsAddTradeNameToTable3.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsAddTradeNameToTable3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsAddTradeNameToTable3ActionPerformed(evt);
            }
        });

        jButtonVendorsDeleteVendorFromTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonVendorsDeleteVendorFromTable3.setToolTipText("Delete vendor");
        jButtonVendorsDeleteVendorFromTable3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsDeleteVendorFromTable3ActionPerformed(evt);
            }
        });

        jButtonVendorsPrintTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonVendorsPrintTable3.setToolTipText("");
        jButtonVendorsPrintTable3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsPrintTable3ActionPerformed(evt);
            }
        });

        jButtonVendorsSaveTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jButtonVendorsAddTradeNameToTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButtonVendorsDeleteVendorFromTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButtonVendorsPrintTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButtonVendorsSaveTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonVendorsAddTradeNameToTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButtonVendorsDeleteVendorFromTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButtonVendorsPrintTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButtonVendorsSaveTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanelVendors.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 315, 200, 45));

        jPanel28.setLayout(new java.awt.GridLayout(1, 0));

        jButtonVendorsAddToTable4_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonVendorsAddToTable4_2.setToolTipText("Add contact");
        jButtonVendorsAddToTable4_2.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsAddToTable4_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsAddToTable4_2ActionPerformed(evt);
            }
        });
        jPanel28.add(jButtonVendorsAddToTable4_2);

        jButtonVendorsDeleteFromTable4_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonVendorsDeleteFromTable4_2.setToolTipText("Delete contact");
        jButtonVendorsDeleteFromTable4_2.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsDeleteFromTable4_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsDeleteFromTable4_2ActionPerformed(evt);
            }
        });
        jPanel28.add(jButtonVendorsDeleteFromTable4_2);

        jButtonVendorsTable4_2Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonVendorsTable4_2Print.setToolTipText("");
        jButtonVendorsTable4_2Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsTable4_2PrintActionPerformed(evt);
            }
        });
        jPanel28.add(jButtonVendorsTable4_2Print);

        jButtonVendorsSaveTable4_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable4_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable4_2ActionPerformed(evt);
            }
        });
        jPanel28.add(jButtonVendorsSaveTable4_2);

        jPanelVendors.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 565, 200, 45));

        jPanel29.setLayout(new java.awt.GridLayout(1, 0));

        jButtonVendorsPrintTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonVendorsPrintTable1.setToolTipText("print");
        jButtonVendorsPrintTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsPrintTable1ActionPerformed(evt);
            }
        });
        jPanel29.add(jButtonVendorsPrintTable1);

        jButtonVendorsSaveTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable1ActionPerformed(evt);
            }
        });
        jPanel29.add(jButtonVendorsSaveTable1);

        jPanelVendors.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(393, 57, 100, 45));

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(102, 102, 102));
        jLabel79.setText(MCRecipe.Lang.VENDOR_A_B.WAREHOUSE_TABLE());
        jPanelVendors.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 80, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(102, 102, 102));
        jLabel80.setText("TABLE 2");
        jPanelVendors.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, -1, -1));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(102, 102, 102));
        jLabel81.setText("TABLE 3");
        jPanelVendors.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 340, -1, -1));

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(102, 102, 102));
        jLabel82.setText("TABLE 4");
        jPanelVendors.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 590, -1, -1));

        jScrollPaneVendors.setViewportView(jPanelVendors);

        jTabbedPane1.addTab("VENDORS", jScrollPaneVendors);

        jPanelInvertTable4_B.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelInvertTable4_B.setLayout(new java.awt.BorderLayout());

        jPanelInvertTable3_2_B.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelInvertTable3_2_B.setLayout(new java.awt.BorderLayout());

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(102, 102, 102));
        jLabel37.setText(VENDOR_A_B.TRADE_NAME());

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(102, 102, 102));
        jLabel68.setText(VENDOR_A_B.VENDOR_TABLE());

        javax.swing.GroupLayout jPanelVendorsBSeparator1Layout = new javax.swing.GroupLayout(jPanelVendorsBSeparator1);
        jPanelVendorsBSeparator1.setLayout(jPanelVendorsBSeparator1Layout);
        jPanelVendorsBSeparator1Layout.setHorizontalGroup(
            jPanelVendorsBSeparator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        jPanelVendorsBSeparator1Layout.setVerticalGroup(
            jPanelVendorsBSeparator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );

        jPanel30.setPreferredSize(new java.awt.Dimension(160, 39));
        jPanel30.setLayout(new java.awt.GridLayout(1, 0));

        jButtonVendorsAddToTable3_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonVendorsAddToTable3_2.setToolTipText("Add new tradename");
        jButtonVendorsAddToTable3_2.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsAddToTable3_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsAddToTable3_2ActionPerformed(evt);
            }
        });
        jPanel30.add(jButtonVendorsAddToTable3_2);

        jButtonVendorsDeleteFromTable3_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonVendorsDeleteFromTable3_2.setToolTipText("Delete tradename");
        jButtonVendorsDeleteFromTable3_2.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsDeleteFromTable3_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsDeleteFromTable3_2ActionPerformed(evt);
            }
        });
        jPanel30.add(jButtonVendorsDeleteFromTable3_2);

        jButtonVendorsSaveTable3_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable3_2.setToolTipText("");
        jButtonVendorsSaveTable3_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable3_2ActionPerformed(evt);
            }
        });
        jPanel30.add(jButtonVendorsSaveTable3_2);

        jPanel31.setLayout(new java.awt.GridLayout(1, 0));

        jButtonVendorsAddToTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonVendorsAddToTable4.setToolTipText("Add new vendor");
        jButtonVendorsAddToTable4.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsAddToTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsAddToTable4ActionPerformed(evt);
            }
        });
        jPanel31.add(jButtonVendorsAddToTable4);

        jButtonVendorsDeleteFromTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonVendorsDeleteFromTable4.setToolTipText("Delete contact");
        jButtonVendorsDeleteFromTable4.setPreferredSize(new java.awt.Dimension(63, 39));
        jButtonVendorsDeleteFromTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsDeleteFromTable4ActionPerformed(evt);
            }
        });
        jPanel31.add(jButtonVendorsDeleteFromTable4);

        jButtonVendorsSaveTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButtonVendorsSaveTable4.setToolTipText("");
        jButtonVendorsSaveTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendorsSaveTable4ActionPerformed(evt);
            }
        });
        jPanel31.add(jButtonVendorsSaveTable4);

        jLabel83.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(102, 102, 102));
        jLabel83.setText("TABLE 4");

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(102, 102, 102));
        jLabel84.setText("TABLE 3_2");

        javax.swing.GroupLayout jPanelVendorsBLayout = new javax.swing.GroupLayout(jPanelVendorsB);
        jPanelVendorsB.setLayout(jPanelVendorsBLayout);
        jPanelVendorsBLayout.setHorizontalGroup(
            jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVendorsBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanelVendorsBLayout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelInvertTable4_B, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelVendorsBLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelInvertTable3_2_B, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanelVendorsBSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(148, 148, 148))
        );
        jPanelVendorsBLayout.setVerticalGroup(
            jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVendorsBLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(jLabel84))
                    .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel68)
                        .addComponent(jLabel83)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelVendorsBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelVendorsBSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelInvertTable3_2_B, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelInvertTable4_B, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jScrollPaneVenodrsB.setViewportView(jPanelVendorsB);

        jTabbedPane1.addTab("VENDORS B", jScrollPaneVenodrsB);

        jScrollPane9.setViewportView(jTableSequnece1);

        jScrollPane10.setViewportView(jTableSequnce2);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, SEQUENCE.SEARCH_TITLE_BORDER(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanel10.setForeground(new java.awt.Color(153, 153, 153));

        jComboBoxSequenceRecipe.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxSequenceRelease.setEditable(false);
        jComboBoxSequenceRelease.setModel(new javax.swing.DefaultComboBoxModel());

        jLabel10.setText(SEQUENCE.RECIPE());

        jLabel11.setText(SEQUENCE.RELEASE());

        jComboBoxSequenceMixerCode.setModel(new javax.swing.DefaultComboBoxModel());

        jLabel30.setText(SEQUENCE.MIXER_CODE());

        jPanel24.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel24.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSequenceSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonSequenceSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenceSearchActionPerformed(evt);
            }
        });
        jPanel24.add(jButtonSequenceSearch);

        jButtonBoxesClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonBoxesClear.setToolTipText("Clear fields");
        jButtonBoxesClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBoxesClearActionPerformed(evt);
            }
        });
        jPanel24.add(jButtonBoxesClear);

        jPanel48.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel48.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButton_sequence_prev_recipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/prev.png"))); // NOI18N
        jButton_sequence_prev_recipe.setToolTipText("previous recipe");
        jButton_sequence_prev_recipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sequence_prev_recipeActionPerformed(evt);
            }
        });
        jPanel48.add(jButton_sequence_prev_recipe);

        jButton_sequence_next_recipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        jButton_sequence_next_recipe.setToolTipText("next recipe");
        jButton_sequence_next_recipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sequence_next_recipeActionPerformed(evt);
            }
        });
        jPanel48.add(jButton_sequence_next_recipe);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxSequenceRecipe, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxSequenceRelease, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jComboBoxSequenceMixerCode, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxSequenceRecipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxSequenceRelease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxSequenceMixerCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
        );

        jPanelSequence3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, SEQUENCE.MIX_SEQ_STEP_EDITOR_TITLE_BORDER(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanelSequence3.setForeground(new java.awt.Color(153, 153, 153));

        jTextFieldStepNrSequence.setToolTipText("Step number");

        jComboBoxCommandNameSequence.setModel(new javax.swing.DefaultComboBoxModel());

        jTextFieldCommandParamSequence.setToolTipText("Command parameter");

        jLabel7.setText(SEQUENCE.STEP());

        jLabel8.setText(SEQUENCE.COMMAND());

        jLabel9.setText(SEQUENCE.PARAMETER());

        jPanel22.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel22.setLayout(new java.awt.GridLayout(1, 0));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton13.setToolTipText("Update Step");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel22.add(jButton13);

        jButtonSequenceAddStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButtonSequenceAddStep.setToolTipText("Insert new step");
        jButtonSequenceAddStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenceAddStepActionPerformed(evt);
            }
        });
        jPanel22.add(jButtonSequenceAddStep);

        jButtonSequenceAddLastStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add_b.png"))); // NOI18N
        jButtonSequenceAddLastStep.setToolTipText("Insert new step at the end position");
        jButtonSequenceAddLastStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenceAddLastStepActionPerformed(evt);
            }
        });
        jPanel22.add(jButtonSequenceAddLastStep);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton14.setToolTipText("Delete Step");
        jButton14.setPreferredSize(new java.awt.Dimension(65, 46));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel22.add(jButton14);

        javax.swing.GroupLayout jPanelSequence3Layout = new javax.swing.GroupLayout(jPanelSequence3);
        jPanelSequence3.setLayout(jPanelSequence3Layout);
        jPanelSequence3Layout.setHorizontalGroup(
            jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSequence3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldStepNrSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCommandNameSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCommandParamSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSequence3Layout.setVerticalGroup(
            jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSequence3Layout.createSequentialGroup()
                .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSequence3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSequence3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldStepNrSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxCommandNameSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCommandParamSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSequence3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, SEQUENCE.RECIPE_SEQUENCE_INFO(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanel15.setForeground(new java.awt.Color(153, 153, 153));

        jTextFieldInfoSequence.setToolTipText("Info");

        jTextFieldUpdateOnSequence.setToolTipText("UpdateOn");

        jTextFieldUpdatedBy.setToolTipText("UpdatedBy");

        jLabel4.setText(SEQUENCE.INFO());

        jLabel5.setText(SEQUENCE.DATE_CHANGED());

        jLabel6.setText(SEQUENCE.USER());

        jPanel11.setPreferredSize(new java.awt.Dimension(180, 50));
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton15.setToolTipText("Start creating new sequence");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton15);

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton16.setToolTipText("Update INFO");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton16);

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton17.setToolTipText("Delete all sequence steps");
        jButton17.setPreferredSize(new java.awt.Dimension(65, 46));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton17);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldInfoSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldUpdateOnSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(7, 7, 7)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldUpdatedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInfoSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldUpdatedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldUpdateOnSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, SEQUENCE.COPY_SEQUENCE_TITLE_BORDER(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanel16.setForeground(new java.awt.Color(153, 153, 153));

        jComboBoxSequenceRecipeCopy.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxSequenceReleaseCopy.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxSequenceMixerCodeCopy.setModel(new javax.swing.DefaultComboBoxModel());

        jLabel31.setText(SEQUENCE.RECIPE());

        jLabel32.setText(SEQUENCE.RELEASE());

        jLabel33.setText(SEQUENCE.MIXER_CODE());

        jPanel23.setPreferredSize(new java.awt.Dimension(180, 50));
        jPanel23.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSequenceViewBeforeCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N
        jButtonSequenceViewBeforeCopy.setToolTipText("Show sequence to be copied");
        jButtonSequenceViewBeforeCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenceViewBeforeCopyActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonSequenceViewBeforeCopy);

        jButtonSequenceCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copy.png"))); // NOI18N
        jButtonSequenceCopy.setToolTipText("Copy Sequence");
        jButtonSequenceCopy.setPreferredSize(new java.awt.Dimension(67, 47));
        jButtonSequenceCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSequenceCopyActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonSequenceCopy);

        jButtonBoxesClearCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButtonBoxesClearCopy.setToolTipText("Clear fields");
        jButtonBoxesClearCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBoxesClearCopyActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonBoxesClearCopy);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jComboBoxSequenceRecipeCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel32))
                    .addComponent(jComboBoxSequenceReleaseCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel33))
                    .addComponent(jComboBoxSequenceMixerCodeCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addGap(4, 4, 4)
                            .addComponent(jComboBoxSequenceRecipeCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel32)
                            .addGap(4, 4, 4)
                            .addComponent(jComboBoxSequenceReleaseCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel33)
                            .addGap(4, 4, 4)
                            .addComponent(jComboBoxSequenceMixerCodeCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1))
        );

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(102, 102, 102));
        jLabel74.setText(SEQUENCE.MIXING_SEQUENCE_TBL());

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(102, 102, 102));
        jLabel75.setText(SEQUENCE.RECIPE_FORMULATION_TBL());

        javax.swing.GroupLayout jPanel10SequenceLayout = new javax.swing.GroupLayout(jPanel10Sequence);
        jPanel10Sequence.setLayout(jPanel10SequenceLayout);
        jPanel10SequenceLayout.setHorizontalGroup(
            jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanelSequence3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel75)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel10SequenceLayout.setVerticalGroup(
            jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10SequenceLayout.createSequentialGroup()
                        .addComponent(jPanelSequence3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel10SequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jScrollPaneSequence.setViewportView(jPanel10Sequence);

        jTabbedPane1.addTab("SEQUENCE", jScrollPaneSequence);

        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jTable_1_RecipeAdd.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(jTable_1_RecipeAdd);

        jPanel20.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jTable_2_RecipeAdd.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(jTable_2_RecipeAdd);

        jPanel21.add(jScrollPane14, java.awt.BorderLayout.CENTER);

        jComboBoxRecipeAdditionalOrders.setModel(new javax.swing.DefaultComboBoxModel());
        jComboBoxRecipeAdditionalOrders.setToolTipText("Select Order");

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(102, 102, 102));
        jLabel70.setText("TEST PROCEDURES");

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(102, 102, 102));
        jLabel71.setText("TEST RESULTS");

        jButtonRecipeAdditionalPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonRecipeAdditionalPrint1.setToolTipText("");
        jButtonRecipeAdditionalPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeAdditionalPrint1ActionPerformed(evt);
            }
        });

        jButtonRecipeAdditionalPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonRecipeAdditionalPrint2.setToolTipText("");
        jButtonRecipeAdditionalPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeAdditionalPrint2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRecipeAdditionalLayout = new javax.swing.GroupLayout(jPanelRecipeAdditional);
        jPanelRecipeAdditional.setLayout(jPanelRecipeAdditionalLayout);
        jPanelRecipeAdditionalLayout.setHorizontalGroup(
            jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecipeAdditionalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRecipeAdditionalLayout.createSequentialGroup()
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRecipeAdditionalPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRecipeAdditionalLayout.createSequentialGroup()
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxRecipeAdditionalOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonRecipeAdditionalPrint2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(521, 521, 521))
        );
        jPanelRecipeAdditionalLayout.setVerticalGroup(
            jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecipeAdditionalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70)
                    .addComponent(jButtonRecipeAdditionalPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelRecipeAdditionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel71)
                        .addComponent(jComboBoxRecipeAdditionalOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonRecipeAdditionalPrint2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPaneRecipeAdditional.setViewportView(jPanelRecipeAdditional);

        jTabbedPane1.addTab("RECIPE ADD.", jScrollPaneRecipeAdditional);

        jTextArea1_Logg.setColumns(20);
        jTextArea1_Logg.setRows(5);
        jScrollPane6.setViewportView(jTextArea1_Logg);

        jScrollPane15.setViewportView(jTextPane1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane16.setViewportView(jTextArea1);

        jButton9.setText("Show all");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(102, 102, 102));
        jLabel76.setText("OUTPUT");

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(102, 102, 102));
        jLabel77.setText("ERROR OUTPUT");

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(102, 102, 102));
        jLabel78.setText("CONSOLE OUTPUT");

        jButton18.setText("Clean");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton21.setText("Latest");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jPanel52.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1026, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_LogLayout = new javax.swing.GroupLayout(jPanel_Log);
        jPanel_Log.setLayout(jPanel_LogLayout);
        jPanel_LogLayout.setHorizontalGroup(
            jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_LogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane16)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE)
                    .addComponent(jScrollPane15)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_LogLayout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_LogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_LogLayout.createSequentialGroup()
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel76)))
                .addGap(387, 387, 387))
        );
        jPanel_LogLayout.setVerticalGroup(
            jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_LogLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jButton18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_LogLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_LogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton9)
                                .addComponent(jButton21))
                            .addComponent(jLabel77)))
                    .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("LOG", jPanel_Log);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int col = HelpA_.getColByName(jTable2, "Recipe_Prop_Free_Info_ID");
        recipeInitial.delete_record_table_2_or_3(jTable2, col, "Recipe_Prop_Free_Info", "Recipe_Prop_Free_Info_ID");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int col = HelpA_.getColByName(jTable3, "Recipe_Prop_Free_Text_ID");
        recipeInitial.delete_record_table_2_or_3(jTable3, col, "Recipe_Prop_Free_Text", "Recipe_Prop_Free_Text_ID");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonRecipeDetailedSaveTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedSaveTable4ActionPerformed
        recipeDetailed.apply_changes_table4_real(true);
    }//GEN-LAST:event_jButtonRecipeDetailedSaveTable4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        recipeDetailed.apply_changes_table4_tempory();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        recipeDetailed.add_ingredient_table_4(null);
        //
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButtonRecipeDetailedUndoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedUndoDeleteActionPerformed
        recipeDetailed.undo_mark_to_delete_ingredient_table_4();
    }//GEN-LAST:event_jButtonRecipeDetailedUndoDeleteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        recipeDetailed.mark_to_delete_ingredient_table_4();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            recipeDetailed.save_changes_notes(jScrollPaneRecipeInitialNotes, jEditorPaneRecipeInitialNotes,
                    jTable3, textAreaRecipeInitialNotes, "Recipe_Prop_Free_Text_ID");
            //
        } catch (BadLocationException ex) {
            Logger.getLogger(MC_RECIPE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButtonRecipeDetailedAddNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedAddNoteActionPerformed
//        recipeDetailed.addRow(jTable2, recipeDetailed.addedRows_table_2);
        recipeDetailed.table3_add_note();
    }//GEN-LAST:event_jButtonRecipeDetailedAddNoteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        recipeDetailed.save_changes_table_2(jTable2);
        recipeDetailed.table2_change_note_value(jTable2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_Recipe_Detailed_Save_InvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Recipe_Detailed_Save_InvertActionPerformed
        recipeDetailed.saveTableInvert();
    }//GEN-LAST:event_jButton_Recipe_Detailed_Save_InvertActionPerformed

    private void jButton_Ingredients_Save_InvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingredients_Save_InvertActionPerformed
        ingredients.saveTableInvert();
    }//GEN-LAST:event_jButton_Ingredients_Save_InvertActionPerformed

    private void jButton_Ingredients_Save_Table3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingredients_Save_Table3ActionPerformed
        ingredients.save_changes_table_3();
    }//GEN-LAST:event_jButton_Ingredients_Save_Table3ActionPerformed

    private void jButton_Ingred_Del_Table_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingred_Del_Table_3ActionPerformed
        ingredients.delete_from_table_3();
    }//GEN-LAST:event_jButton_Ingred_Del_Table_3ActionPerformed

    private void jButton_Ingred_Add_Table_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingred_Add_Table_3ActionPerformed
//        ingredients.addRow(jTable_Ingred_Table3, ingredients.addedRows_table_3);
        ingredients.addToTable3();
    }//GEN-LAST:event_jButton_Ingred_Add_Table_3ActionPerformed

    private void jButton_Ingredients_Save_CommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingredients_Save_CommentsActionPerformed
        try {
            ingredients.save_changes_notes(jScrollPane_Ingred_Comments, jEditorPane_Ingred, jTable_Ingred_Table3, textAreaIngredComments, "IngredientCode_ID");
        } catch (BadLocationException ex) {
            Logger.getLogger(MC_RECIPE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_Ingredients_Save_CommentsActionPerformed

    private void jButtonSequenceSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenceSearchActionPerformed
        if (sequence.checkHeadParameters() == false) {
            JOptionPane.showMessageDialog(null, "Recipe, Release, Mixer code must be filled in!");
            return;
        }
        //
        sequence.clearRecipeSequenceInfo();
        //
        sequence.fill_table_1(jComboBoxSequenceRecipe, jComboBoxSequenceRelease, jComboBoxSequenceMixerCode);
        sequence.fill_table_2(jComboBoxSequenceRecipe, jComboBoxSequenceRelease, jComboBoxSequenceMixerCode, true);
        //
        HelpA_.markFirstRowJtable(jTableSequnece1);
        clickedTable1Sequence();
    }//GEN-LAST:event_jButtonSequenceSearchActionPerformed

    private void jButtonSequenceAddStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenceAddStepActionPerformed
        sequence.insert_new_step(false, false);
    }//GEN-LAST:event_jButtonSequenceAddStepActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        sequence.update_step();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        sequence.delete_step();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        sequence.createNewSequence();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        sequence.updateOther();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        if (HelpA_.confirm("Mixing sequence will be deleted including all mixing steps!")) {
            sequence.deleteAllSequenceSteps();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButtonRecipeInitialResetComboBoxesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialResetComboBoxesActionPerformed
        recipeInitial.clearBoxes();
    }//GEN-LAST:event_jButtonRecipeInitialResetComboBoxesActionPerformed

    private void jButtonRecipeInitialGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialGoActionPerformed
        recipeInitial.fill_table_1(null, null, null, evt);
        HelpA_.markGivenRow(jTable1, 0);
        clickedOnTable1RecipeInitial();
    }//GEN-LAST:event_jButtonRecipeInitialGoActionPerformed

    private void jButtonIngredClearBoxesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredClearBoxesActionPerformed
        ingredients.clearBoxes();
    }//GEN-LAST:event_jButtonIngredClearBoxesActionPerformed

    private void jButtonIngredGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredGoActionPerformed
        ingredients.fill_table_1(null);
        ingredients.simulateClickOnTable1();
    }//GEN-LAST:event_jButtonIngredGoActionPerformed

    private void jButtonSequenceCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenceCopyActionPerformed
        sequence.copySequence();
    }//GEN-LAST:event_jButtonSequenceCopyActionPerformed

    private void jButtonSequenceViewBeforeCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenceViewBeforeCopyActionPerformed
        sequence.fill_table_1(jComboBoxSequenceRecipeCopy, jComboBoxSequenceReleaseCopy, jComboBoxSequenceMixerCodeCopy);
        sequence.fill_table_2(jComboBoxSequenceRecipeCopy, jComboBoxSequenceReleaseCopy, jComboBoxSequenceMixerCodeCopy, false);
        //
        HelpA_.markFirstRowJtable(jTableSequnece1);
        clickedTable1Sequence();
    }//GEN-LAST:event_jButtonSequenceViewBeforeCopyActionPerformed

    private void jButtonBoxesClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBoxesClearActionPerformed
        sequence.clearComboBoxes();
    }//GEN-LAST:event_jButtonBoxesClearActionPerformed

    private void jButtonBoxesClearCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBoxesClearCopyActionPerformed
        sequence.clearComboBoxesCopy();
    }//GEN-LAST:event_jButtonBoxesClearCopyActionPerformed

    private void jButtonVendorsSaveTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable1ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT);
    }//GEN-LAST:event_jButtonVendorsSaveTable1ActionPerformed

    private void jButtonVendorsSaveTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable2ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT_2);
    }//GEN-LAST:event_jButtonVendorsSaveTable2ActionPerformed

    private void jButtonVendorsSaveTable3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable3ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT_3);
    }//GEN-LAST:event_jButtonVendorsSaveTable3ActionPerformed

    private void jButtonVendorsSaveTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable4ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT_4);
    }//GEN-LAST:event_jButtonVendorsSaveTable4ActionPerformed

    private void jButtonVendorsAddTradeNameToTable3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsAddTradeNameToTable3ActionPerformed
//        vendors.addToTable3();
        vendors.addToTable3C();
    }//GEN-LAST:event_jButtonVendorsAddTradeNameToTable3ActionPerformed

    private void jButtonVendorsDeleteVendorFromTable3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsDeleteVendorFromTable3ActionPerformed
//        vendors.deleteVendorFromTable3();
        vendors.deleteTradeNameFromTable3B();
    }//GEN-LAST:event_jButtonVendorsDeleteVendorFromTable3ActionPerformed

    private void jButtonVendorsSaveTable3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable3_2ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT_3_2);
    }//GEN-LAST:event_jButtonVendorsSaveTable3_2ActionPerformed

    private void jButtonVendorsSaveTable4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsSaveTable4_2ActionPerformed
        vendors.saveChangesTableInvert(vendors.TABLE_INVERT_4_2);
    }//GEN-LAST:event_jButtonVendorsSaveTable4_2ActionPerformed

    private void jButtonRecipeDetailedResetChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedResetChangesActionPerformed
        recipeDetailed.fill_table_4(true);
    }//GEN-LAST:event_jButtonRecipeDetailedResetChangesActionPerformed

    private void jButtonVendorsAddToTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsAddToTable4ActionPerformed
        vendorsB.addToTable4();
    }//GEN-LAST:event_jButtonVendorsAddToTable4ActionPerformed

    private void jButtonRecipeDetailedSwapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedSwapActionPerformed
        //
        recipeDetailed.phrsAndWeights();
        //

    }//GEN-LAST:event_jButtonRecipeDetailedSwapActionPerformed

    private void jButtonVendorsDeleteFromTable3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsDeleteFromTable3_2ActionPerformed
        vendorsB.deleteFromTable3_2();
    }//GEN-LAST:event_jButtonVendorsDeleteFromTable3_2ActionPerformed

    private void jButtonVendorsAddToTable4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsAddToTable4_2ActionPerformed
        vendors.addToTable4_2();
    }//GEN-LAST:event_jButtonVendorsAddToTable4_2ActionPerformed

    private void jButtonVendorsDeleteFromTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsDeleteFromTable4ActionPerformed
        vendorsB.deleteFromTable4();
    }//GEN-LAST:event_jButtonVendorsDeleteFromTable4ActionPerformed

    private void jButtonRecipeDetailedAddNewRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed
        recipeDetailed.addNewRecipe(false);
    }//GEN-LAST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed

    private void jButton_Ingred_Add_IngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingred_Add_IngredientActionPerformed
        ingredients.addIngredient(false);
    }//GEN-LAST:event_jButton_Ingred_Add_IngredientActionPerformed

    private void jButtonVendorsAddToTable3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsAddToTable3_2ActionPerformed
        vendorsB.addToTable3_2();
    }//GEN-LAST:event_jButtonVendorsAddToTable3_2ActionPerformed

    private void jButtonVendorsDeleteFromTable4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsDeleteFromTable4_2ActionPerformed
        vendors.deleteFromTable4_2();
    }//GEN-LAST:event_jButtonVendorsDeleteFromTable4_2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //
        // This snippet is for copying
//        if (USER_ROLES_ADMIN_DEVELOPER_ACCESS.contains(USER_ROLE)) {
//            
//        }else {
//            HelpA_.showAccessDeniedUserRole(USER_ROLE);
//        }
        //
        if (USER_ROLES_ADMIN_DEVELOPER_ACCESS.contains(USER_ROLE)) {
            if (prod_plan == null) {
                prod_plan = new PROD_PLAN();
                prod_plan.setVisible(true);
                prod_plan.setDisposeOnClose();
            } else {
                prod_plan.setVisible(true);
                prod_plan.redrawTablesInvert_forward();
            }
        } else {
            HelpA_.showAccessDeniedUserRole(USER_ROLE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    private void jButtonRecipeInitialResetComboBoxes3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialResetComboBoxes3ActionPerformed
        recipeInitial.clearBoxesB();
        recipeInitial.fill_table_1(null, null, null, null);
        HelpA_.markGivenRow(jTable1, 0);
        clickedOnTable1RecipeInitial();
    }//GEN-LAST:event_jButtonRecipeInitialResetComboBoxes3ActionPerformed

    private void jButtonIngredPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredPrintActionPerformed
        ingredients.table1Repport();
    }//GEN-LAST:event_jButtonIngredPrintActionPerformed

    private void jButtonVendorsPrintTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsPrintTable1ActionPerformed
        vendors.table1Repport();
    }//GEN-LAST:event_jButtonVendorsPrintTable1ActionPerformed

    private void jButtonRecipeDetailedPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedPrintActionPerformed
        recipeDetailed.table1Repport();
    }//GEN-LAST:event_jButtonRecipeDetailedPrintActionPerformed

    private void jButtonRecipeInitialPrintTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialPrintTable1ActionPerformed
        recipeInitial.table1Repport();
    }//GEN-LAST:event_jButtonRecipeInitialPrintTable1ActionPerformed

    private void jButtonIngredExpressInfoPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredExpressInfoPrintActionPerformed
        ingredients.table2Repport();
    }//GEN-LAST:event_jButtonIngredExpressInfoPrintActionPerformed

    private void jButtonRecipeInitialGo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialGo2ActionPerformed
        recipeInitial.fill_table_1(null, null, null, evt);
        HelpA_.markGivenRow(jTable1, 0);
        clickedOnTable1RecipeInitial();
    }//GEN-LAST:event_jButtonRecipeInitialGo2ActionPerformed

    private void jButtonVendorsPrintTable3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsPrintTable3ActionPerformed
        vendors.table3Repport();
    }//GEN-LAST:event_jButtonVendorsPrintTable3ActionPerformed

    private void jButtonVendorsPrintTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsPrintTable4ActionPerformed
        vendors.table4Repport();
    }//GEN-LAST:event_jButtonVendorsPrintTable4ActionPerformed

    private void jButtonVendorsTable4_2PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendorsTable4_2PrintActionPerformed
        vendors.table4_2Repport();
    }//GEN-LAST:event_jButtonVendorsTable4_2PrintActionPerformed

    private void jButtonRecipeAdditionalPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeAdditionalPrint1ActionPerformed
        if (recipeAdditional != null) {
            recipeAdditional.table1Repport();
        }
    }//GEN-LAST:event_jButtonRecipeAdditionalPrint1ActionPerformed

    private void jButtonRecipeAdditionalPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeAdditionalPrint2ActionPerformed
        if (recipeAdditional != null) {
            recipeAdditional.table2Repport();
        }
    }//GEN-LAST:event_jButtonRecipeAdditionalPrint2ActionPerformed

    private void jCheckBoxRecipeInitialSearchByIngredientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRecipeInitialSearchByIngredientsActionPerformed
//        recipeInitial.allowIngredSearch(jCheckBoxRecipeInitialSearchByIngredients);
    }//GEN-LAST:event_jCheckBoxRecipeInitialSearchByIngredientsActionPerformed

    private void jButtonRecipeDetailedAddRecipeFromScratchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedAddRecipeFromScratchActionPerformed
        recipeDetailed.addNewRecipe(true);
    }//GEN-LAST:event_jButtonRecipeDetailedAddRecipeFromScratchActionPerformed

    private void jButton_Ingred_Add_Ingredient_From_ScratchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingred_Add_Ingredient_From_ScratchActionPerformed
        ingredients.addIngredient(true);
    }//GEN-LAST:event_jButton_Ingred_Add_Ingredient_From_ScratchActionPerformed

    private void jButton_vendors_next_ingredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_vendors_next_ingredActionPerformed
        vendors.nextIngredient();
        vendors.vendorsColumnClickedSimulation();
    }//GEN-LAST:event_jButton_vendors_next_ingredActionPerformed

    private void jButton_vendors_prev_ingredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_vendors_prev_ingredActionPerformed
        vendors.prevIngredient();
        vendors.vendorsColumnClickedSimulation();
    }//GEN-LAST:event_jButton_vendors_prev_ingredActionPerformed

    private void jButtonSequenceAddLastStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSequenceAddLastStepActionPerformed
        HelpA_.markLastRowJtable(jTableSequnece1);
        clickedTable1Sequence();
        sequence.insert_new_step(false, true);
    }//GEN-LAST:event_jButtonSequenceAddLastStepActionPerformed

    private void jButtonRecipeInitialAddToCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialAddToCompareActionPerformed
        //
        if (compareRecipes == null) {
            compareRecipes = new CompareRecipes(this, sql, sql_additional);
            recipeInitial.setCompareRecipes(compareRecipes);
            recipeInitial.dropCompareTable();
        }
        //
        recipeInitial.addToCompare();
    }//GEN-LAST:event_jButtonRecipeInitialAddToCompareActionPerformed

    private void jButtonRecipeInitialShowCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialShowCompareActionPerformed
        recipeInitial.showComparedRecipes();
    }//GEN-LAST:event_jButtonRecipeInitialShowCompareActionPerformed

    private void jButtonRecipeInitialRemoweAllFromCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialRemoweAllFromCompareActionPerformed
        recipeInitial.remoweAllFromCompare();
    }//GEN-LAST:event_jButtonRecipeInitialRemoweAllFromCompareActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        //
        if (verifyPasswordUserAdmin() == false) {
            HelpA_.showNotification("Invalid password");
            return;
        }
        //
        if (administrateUsers == null) {
            String title = "Administrate users";
            administrateUsers = new AdministrateUsers(title, this, sql, sql_additional);
            administrateUsers.setVisible(true);
        } else {
            administrateUsers.setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private boolean verifyPasswordUserAdmin() {
        //
        JPasswordField jpf = HelpA_.chooseFromPasswordField("Enter password", true);
        String pwd = new String(jpf.getPassword());
        //
        if (pwd == null || pwd.isEmpty()) {
            return false;
        }
        //
        if (pwd.equals(ADMIN_USERS_PWD)) {
            return true;
        }
        //
        return false;
    }

    private void userNotValidActions() {
        jTextFieldHomeUserName.setText("");
        jPasswordFieldHomePass.setText("");
        updateUserName();
    }

    private void jButton_Home_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Home_LoginActionPerformed
        if (verifyUser()) {
            HelpA_.showNotification("Login valid");
            updateUserName();
        } else {
            userNotValidActions();
        }
    }//GEN-LAST:event_jButton_Home_LoginActionPerformed


    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        errorOutputListener.showAll();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButtonIngredientsPasteIngred1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredientsPasteIngred1ActionPerformed
        ingredients.setIngredComboBoxRecipeInitial(jComboBox_Ingred_1);
    }//GEN-LAST:event_jButtonIngredientsPasteIngred1ActionPerformed

    private void jButtonIngredientsPasteIngred2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngredientsPasteIngred2ActionPerformed
        ingredients.setIngredComboBoxRecipeInitial(jComboBox_Ingred_2);
    }//GEN-LAST:event_jButtonIngredientsPasteIngred2ActionPerformed

    private void jButtonRecipeInitialUnblock1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialUnblock1ActionPerformed
        recipeDetailed.unblockRecipe(jTable1);
    }//GEN-LAST:event_jButtonRecipeInitialUnblock1ActionPerformed

    private void jButton_recipe_detailed_delete_recipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_recipe_detailed_delete_recipeActionPerformed
        recipeInitial.delete_record_table_1(jTable1);
    }//GEN-LAST:event_jButton_recipe_detailed_delete_recipeActionPerformed

    private void jButtonRDetailedChangeNoteNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRDetailedChangeNoteNameActionPerformed
        recipeDetailed.table3_change_note_name(jTable3);
    }//GEN-LAST:event_jButtonRDetailedChangeNoteNameActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        recipeDetailed.table2_add_new_entry(jTable2);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        ingredients.addIngredientToTable4RecipeDetailed();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        jTextArea1_Logg.setText("");
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton18ActionPerformed
    public static boolean SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT = false;

    private void jButtonRecipeInitialUnblock2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialUnblock2ActionPerformed
        if (SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT == false) {
            SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT = true;
        } else {
            SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT = false;
        }
        //
        recipeDetailed.showTableInvert();
    }//GEN-LAST:event_jButtonRecipeInitialUnblock2ActionPerformed

    private void jButtonTable4RepportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTable4RepportActionPerformed
        recipeDetailed.table4Repport();
    }//GEN-LAST:event_jButtonTable4RepportActionPerformed

    private void jButton_r_detailed_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_r_detailed_nextActionPerformed
        recipeDetailed.nextRecipe();
    }//GEN-LAST:event_jButton_r_detailed_nextActionPerformed

    private void jButton_r_detailed_prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_r_detailed_prevActionPerformed
        recipeDetailed.prevRecipe();
    }//GEN-LAST:event_jButton_r_detailed_prevActionPerformed

    private void jButton_sequence_prev_recipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sequence_prev_recipeActionPerformed
        if (recipeDetailed != null) {
            recipeDetailed.prevRecipe();
            sequence.setSelectedItems();
            jButtonSequenceSearchActionPerformed(null);
        }
    }//GEN-LAST:event_jButton_sequence_prev_recipeActionPerformed

    private void jButton_sequence_next_recipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sequence_next_recipeActionPerformed
        if (recipeDetailed != null) {
            recipeDetailed.nextRecipe();
            sequence.setSelectedItems();
            jButtonSequenceSearchActionPerformed(null);
        }
    }//GEN-LAST:event_jButton_sequence_next_recipeActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if (adminTools == null) {
            final MC_RECIPE mc_recipe = this;
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (USER_ROLES_ADMIN_DEVELOPER_ACCESS.contains(USER_ROLE)) {
                        adminTools = new AdminTools(mc_recipe);
                        adminTools.setVisible(true);
                    } else {
                        HelpA_.showAccessDeniedUserRole(USER_ROLE);
                    }

                }
            });
        } else {
            adminTools.setVisible(true);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton_Ingred_delete_IngredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Ingred_delete_IngredActionPerformed
        //
        ingredients.deleteIngredient();
        //
    }//GEN-LAST:event_jButton_Ingred_delete_IngredActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        try {
            //The settings are set via the "PropertiesReader.jar" - file: "table4formats.properties"
            //Pay also attention to the "pr_skip.list" which contains the main property file "mccompound.properties",
            //this is done to only show the "table4formats.properties" file
            HelpA_.run_application_exe_or_jar("PropertiesReader.jar", "properties");
        } catch (IOException ex) {
            Logger.getLogger(MC_RECIPE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButtonTestParametersGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestParametersGoActionPerformed
        testParameters.searchButtonClicked();
    }//GEN-LAST:event_jButtonTestParametersGoActionPerformed

    private void jButtonTestParametersClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestParametersClearActionPerformed
        testParameters.clearBoxes();
    }//GEN-LAST:event_jButtonTestParametersClearActionPerformed

    private void jButtonTestParametersPrint_InvertTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestParametersPrint_InvertTableActionPerformed
        testParameters.tableInvertRepport();
    }//GEN-LAST:event_jButtonTestParametersPrint_InvertTableActionPerformed

    private void jButtonTestParametersSave_InvertTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestParametersSave_InvertTableActionPerformed
        testParameters.saveChangesTableInvert();
    }//GEN-LAST:event_jButtonTestParametersSave_InvertTableActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        errorOutputListener.showLatest();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton_lab_dev_save_btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_save_btn_1ActionPerformed
        labDevelopment.saveTableInvert__tab_main_data();
    }//GEN-LAST:event_jButton_lab_dev_save_btn_1ActionPerformed

    private void jButton_lab_dev_save_btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_save_btn_2ActionPerformed
        labDevelopment.labDevStatusTab.saveTableInvert_2_3_4_5__tab_status();
    }//GEN-LAST:event_jButton_lab_dev_save_btn_2ActionPerformed

    private void jButton_lab_dev_delete_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_delete_1ActionPerformed
        labDevelopment.deleteJTableNote(jTable_lab_dev_1, LabDevelopment.TABLE_NOTES_1);
    }//GEN-LAST:event_jButton_lab_dev_delete_1ActionPerformed

    private void jButton_lab_dev_delete_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_delete_2ActionPerformed
        labDevelopment.deleteJTableNote(jTable_lab_dev_2, LabDevelopment.TABLE_NOTES_2);
    }//GEN-LAST:event_jButton_lab_dev_delete_2ActionPerformed

    private void jButton_lab_dev_add_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_add_1ActionPerformed
        labDevelopment.addJTableNote(jTable_lab_dev_1, LabDevelopment.TABLE_NOTES_1);
    }//GEN-LAST:event_jButton_lab_dev_add_1ActionPerformed

    private void jButton_lab_dev_add_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_add_2ActionPerformed
        labDevelopment.addJTableNote(jTable_lab_dev_2, LabDevelopment.TABLE_NOTES_2);
    }//GEN-LAST:event_jButton_lab_dev_add_2ActionPerformed

    private void jButton_lab_dev_tab__save_notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_tab__save_notesActionPerformed
        labDevelopment.saveNotesJTexArea();
    }//GEN-LAST:event_jButton_lab_dev_tab__save_notesActionPerformed

    private void jButton_lab_dev__material_info_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev__material_info_saveActionPerformed
        labDevelopment.saveTableInvert_6();
    }//GEN-LAST:event_jButton_lab_dev__material_info_saveActionPerformed

    private void jButton__lab_dev_find_order_tab__filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton__lab_dev_find_order_tab__filterActionPerformed
        labDevelopment.lab_dev_tab_tab_find_order__test_btn_clicked();
    }//GEN-LAST:event_jButton__lab_dev_find_order_tab__filterActionPerformed

    private void jButton__lab_dev_find_order_tab__set_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton__lab_dev_find_order_tab__set_orderActionPerformed
        labDevelopment.lab_dev_tab_tab_find_order__set_order_clicked();
    }//GEN-LAST:event_jButton__lab_dev_find_order_tab__set_orderActionPerformed

    private void jButton_lab_dev_tab__save_config_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_tab__save_config_btnActionPerformed
        labDevelopment.labDevTestConfigTab.saveTableInvert();
    }//GEN-LAST:event_jButton_lab_dev_tab__save_config_btnActionPerformed

    private void jButton_lab_dev_test_definition_gotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lab_dev_test_definition_gotoActionPerformed
        labDevelopment.labDevTestDefinitionTab.lab_dev_test_definitions_table_clicked();
    }//GEN-LAST:event_jButton_lab_dev_test_definition_gotoActionPerformed

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
                    //
                    UIDefaults defaults = UIManager.getLookAndFeelDefaults();
                    defaults.put("Table.gridColor", new Color(198, 195, 198));
                    defaults.put("Table.disabled", false);
                    defaults.put("Table.showGrid", true);
                    defaults.put("Table.intercellSpacing", new Dimension(1, 1));
                    //
                    UIManager.put("ScrollBar.minimumThumbSize", new DimensionUIResource(35, 35));
                    //
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //
        boolean runInNetbeans = HelpA_.runningInNetBeans();
        //
        if (runInNetbeans == false) {
            HelpA_.err_output_to_file();
        }
        //
//        if (runInNetbeans == false) {
//            HelpA_.err_output_to_file();
//        }
        //
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                new MC_RECIPE().setVisible(true);
                //
                if (runInNetbeans == false) {
                    HelpA_.console_output_to_jtextpane(jTextPane1); // must be placed here!
                }
                //
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    protected javax.swing.JButton jButton10;
    protected javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    public javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    protected javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    protected javax.swing.JButton jButton3;
    protected javax.swing.JButton jButton4;
    protected javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    protected javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonBoxesClear;
    private javax.swing.JButton jButtonBoxesClearCopy;
    private javax.swing.JButton jButtonIngredClearBoxes;
    private javax.swing.JButton jButtonIngredExpressInfoPrint;
    private javax.swing.JButton jButtonIngredGo;
    private javax.swing.JButton jButtonIngredPrint;
    private javax.swing.JButton jButtonIngredientsPasteIngred1;
    private javax.swing.JButton jButtonIngredientsPasteIngred2;
    public javax.swing.JButton jButtonRDetailedChangeNoteName;
    private javax.swing.JButton jButtonRecipeAdditionalPrint1;
    private javax.swing.JButton jButtonRecipeAdditionalPrint2;
    protected javax.swing.JButton jButtonRecipeDetailedAddNewRecipe;
    public javax.swing.JButton jButtonRecipeDetailedAddNote;
    protected javax.swing.JButton jButtonRecipeDetailedAddRecipeFromScratch;
    private javax.swing.JButton jButtonRecipeDetailedPrint;
    protected javax.swing.JButton jButtonRecipeDetailedResetChanges;
    protected javax.swing.JButton jButtonRecipeDetailedSaveTable4;
    private javax.swing.JButton jButtonRecipeDetailedSwap;
    protected javax.swing.JButton jButtonRecipeDetailedUndoDelete;
    private javax.swing.JButton jButtonRecipeInitialAddToCompare;
    public javax.swing.JButton jButtonRecipeInitialGo;
    public javax.swing.JButton jButtonRecipeInitialGo2;
    protected javax.swing.JButton jButtonRecipeInitialPrintTable1;
    private javax.swing.JButton jButtonRecipeInitialRemoweAllFromCompare;
    public javax.swing.JButton jButtonRecipeInitialResetComboBoxes;
    public javax.swing.JButton jButtonRecipeInitialResetComboBoxes3;
    private javax.swing.JButton jButtonRecipeInitialShowCompare;
    private javax.swing.JButton jButtonRecipeInitialUnblock1;
    private javax.swing.JButton jButtonRecipeInitialUnblock2;
    public javax.swing.JButton jButtonSequenceAddLastStep;
    public javax.swing.JButton jButtonSequenceAddStep;
    public javax.swing.JButton jButtonSequenceCopy;
    public javax.swing.JButton jButtonSequenceSearch;
    private javax.swing.JButton jButtonSequenceViewBeforeCopy;
    private javax.swing.JButton jButtonTable4Repport;
    public javax.swing.JButton jButtonTestParametersClear;
    public javax.swing.JButton jButtonTestParametersGo;
    private javax.swing.JButton jButtonTestParametersPrint_InvertTable;
    public javax.swing.JButton jButtonTestParametersSave_InvertTable;
    public javax.swing.JButton jButtonVendorsAddToTable3_2;
    public javax.swing.JButton jButtonVendorsAddToTable4;
    public javax.swing.JButton jButtonVendorsAddToTable4_2;
    private javax.swing.JButton jButtonVendorsAddTradeNameToTable3;
    private javax.swing.JButton jButtonVendorsDeleteFromTable3_2;
    private javax.swing.JButton jButtonVendorsDeleteFromTable4;
    private javax.swing.JButton jButtonVendorsDeleteFromTable4_2;
    private javax.swing.JButton jButtonVendorsDeleteVendorFromTable3;
    private javax.swing.JButton jButtonVendorsPrintTable1;
    private javax.swing.JButton jButtonVendorsPrintTable3;
    private javax.swing.JButton jButtonVendorsPrintTable4;
    public javax.swing.JButton jButtonVendorsSaveTable1;
    public javax.swing.JButton jButtonVendorsSaveTable2;
    public javax.swing.JButton jButtonVendorsSaveTable3;
    public javax.swing.JButton jButtonVendorsSaveTable3_2;
    public javax.swing.JButton jButtonVendorsSaveTable4;
    public javax.swing.JButton jButtonVendorsSaveTable4_2;
    private javax.swing.JButton jButtonVendorsTable4_2Print;
    protected javax.swing.JButton jButton_Home_Login;
    private javax.swing.JButton jButton_Ingred_Add_Ingredient;
    private javax.swing.JButton jButton_Ingred_Add_Ingredient_From_Scratch;
    private javax.swing.JButton jButton_Ingred_Add_Table_3;
    private javax.swing.JButton jButton_Ingred_Del_Table_3;
    private javax.swing.JButton jButton_Ingred_delete_Ingred;
    protected javax.swing.JButton jButton_Ingredients_Save_Comments;
    protected javax.swing.JButton jButton_Ingredients_Save_Invert;
    protected javax.swing.JButton jButton_Ingredients_Save_Table3;
    protected javax.swing.JButton jButton_Recipe_Detailed_Save_Invert;
    private javax.swing.JButton jButton__lab_dev_find_order_tab__filter;
    public javax.swing.JButton jButton__lab_dev_find_order_tab__set_order;
    public javax.swing.JButton jButton_lab_dev__material_info_save;
    private javax.swing.JButton jButton_lab_dev_add_1;
    private javax.swing.JButton jButton_lab_dev_add_2;
    private javax.swing.JButton jButton_lab_dev_delete_1;
    private javax.swing.JButton jButton_lab_dev_delete_2;
    public javax.swing.JButton jButton_lab_dev_save_btn_1;
    public javax.swing.JButton jButton_lab_dev_save_btn_2;
    public javax.swing.JButton jButton_lab_dev_tab__save_config_btn;
    public javax.swing.JButton jButton_lab_dev_tab__save_notes;
    public javax.swing.JButton jButton_lab_dev_test_definition_goto;
    private javax.swing.JButton jButton_r_detailed_next;
    private javax.swing.JButton jButton_r_detailed_prev;
    private javax.swing.JButton jButton_recipe_detailed_delete_recipe;
    private javax.swing.JButton jButton_sequence_next_recipe;
    private javax.swing.JButton jButton_sequence_prev_recipe;
    private javax.swing.JButton jButton_vendors_next_ingred;
    private javax.swing.JButton jButton_vendors_prev_ingred;
    public static javax.swing.JCheckBox jCheckBoxRecipeInitialOR;
    public static javax.swing.JCheckBox jCheckBoxRecipeInitialSearchByIngredients;
    protected javax.swing.JComboBox jComboBox1;
    public javax.swing.JComboBox jComboBox1_Recipe_Origin;
    protected javax.swing.JComboBox jComboBox2_Detailed_Group;
    protected javax.swing.JComboBox jComboBox3_Recipe_Stage;
    protected javax.swing.JComboBox jComboBox4_Mixer_Code;
    protected javax.swing.JComboBox jComboBox5_Recipe_Version;
    protected javax.swing.JComboBox jComboBox6_Status;
    protected javax.swing.JComboBox jComboBox7_RecipeAdditional;
    protected javax.swing.JComboBox jComboBox8_Class;
    public javax.swing.JComboBox jComboBoxCommandNameSequence;
    public javax.swing.JComboBox jComboBoxRecipeAdditionalOrders;
    public javax.swing.JComboBox jComboBoxSequenceMixerCode;
    public javax.swing.JComboBox jComboBoxSequenceMixerCodeCopy;
    public javax.swing.JComboBox jComboBoxSequenceRecipe;
    public javax.swing.JComboBox jComboBoxSequenceRecipeCopy;
    public javax.swing.JComboBox jComboBoxSequenceRelease;
    public javax.swing.JComboBox jComboBoxSequenceReleaseCopy;
    public javax.swing.JComboBox jComboBoxTestParams_Order;
    public javax.swing.JComboBox jComboBoxTestPararams_Recipe;
    public javax.swing.JComboBox jComboBoxVendorChooseIngred;
    public javax.swing.JComboBox jComboBox_Description1;
    public javax.swing.JComboBox jComboBox_Ingred_1;
    public javax.swing.JComboBox jComboBox_Ingred_2;
    public javax.swing.JComboBox jCombo_Ingred_Cas_Number;
    public javax.swing.JComboBox jCombo_Ingred_Class;
    public javax.swing.JComboBox jCombo_Ingred_Descr;
    public javax.swing.JComboBox jCombo_Ingred_Form;
    public javax.swing.JComboBox jCombo_Ingred_Group;
    public javax.swing.JComboBox jCombo_Ingred_Name;
    public javax.swing.JComboBox jCombo_Ingred_Perc_Rubber;
    public javax.swing.JComboBox jCombo_Ingred_Status;
    public javax.swing.JComboBox jCombo_Ingred_TradeName;
    public javax.swing.JComboBox jCombo_Ingred_VendorName;
    public javax.swing.JEditorPane jEditorPaneRecipeInitialNotes;
    protected javax.swing.JEditorPane jEditorPane_Ingred;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel80;
    public javax.swing.JLabel jLabel81;
    public javax.swing.JLabel jLabel82;
    public javax.swing.JLabel jLabel83;
    public javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelHomeVersion;
    public javax.swing.JLabel jLabelLockedUnlocked;
    public javax.swing.JLabel jLabelRecordsTable1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel10Sequence;
    private javax.swing.JPanel jPanel11;
    protected javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    public javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    protected javax.swing.JPanel jPanel1_recipe_detailed;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    public javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    public javax.swing.JPanel jPanel64;
    public javax.swing.JPanel jPanel65;
    public javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelHome;
    private javax.swing.JPanel jPanelIngredientsB;
    public javax.swing.JPanel jPanelInvertTable3;
    public javax.swing.JPanel jPanelInvertTable3_2_B;
    public javax.swing.JPanel jPanelInvertTable4;
    public javax.swing.JPanel jPanelInvertTable4_2;
    public javax.swing.JPanel jPanelInvertTable4_B;
    private javax.swing.JPanel jPanelRecipeAdditional;
    public javax.swing.JPanel jPanelSequence3;
    private javax.swing.JPanel jPanelVendors;
    private javax.swing.JPanel jPanelVendorsB;
    private javax.swing.JPanel jPanelVendorsBSeparator1;
    public javax.swing.JPanel jPanelVendorsInvertTableIngreds;
    public javax.swing.JPanel jPanelVendorsInvertTableWarehouse;
    protected javax.swing.JPanel jPanel_Ingred_Invert_Table;
    private javax.swing.JPanel jPanel_Ingred_Table1_Cont;
    private javax.swing.JPanel jPanel_Ingred_boxes;
    protected javax.swing.JPanel jPanel_Ingred_table2;
    protected javax.swing.JPanel jPanel_Ingred_table3;
    protected javax.swing.JPanel jPanel_Ingred_table4;
    private javax.swing.JPanel jPanel_Ingredients;
    private javax.swing.JPanel jPanel_Log;
    private javax.swing.JPanel jPanel_RecipeDetailed;
    public javax.swing.JPanel jPanel_RecipeInitial;
    private javax.swing.JPanel jPanel_Test_Parameters;
    public javax.swing.JPanel jPanel_Test_Params_Inv_Table_1;
    public javax.swing.JPanel jPanel_lab_dev__find_order;
    public javax.swing.JPanel jPanel_lab_dev_header;
    public javax.swing.JPanel jPanel_lab_dev_material_info;
    public javax.swing.JPanel jPanel_lab_development;
    public javax.swing.JPanel jPanel_lab_development_2;
    public javax.swing.JPanel jPanel_lab_development_3;
    public javax.swing.JPanel jPanel_lab_development_4;
    public javax.swing.JPanel jPanel_lab_development_5;
    public static javax.swing.JTextField jPasswordFieldHomePass;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    public javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    public javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    protected javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneIngredients;
    private javax.swing.JScrollPane jScrollPaneIngredientsB;
    private javax.swing.JScrollPane jScrollPaneRecipeAdditional;
    private javax.swing.JScrollPane jScrollPaneRecipeDetailed;
    private javax.swing.JScrollPane jScrollPaneRecipeInitial;
    public javax.swing.JScrollPane jScrollPaneRecipeInitialNotes;
    private javax.swing.JScrollPane jScrollPaneSequence;
    private javax.swing.JScrollPane jScrollPaneTestParameters;
    private javax.swing.JScrollPane jScrollPaneVendors;
    private javax.swing.JScrollPane jScrollPaneVenodrsB;
    protected javax.swing.JScrollPane jScrollPane_Ingred_Comments;
    private javax.swing.JScrollPane jScrollPane_Ingred_Table1;
    private javax.swing.JScrollPane jScrollPane_LabDevelopment;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JTabbedPane jTabbedPane3_Lab_Dev;
    protected javax.swing.JTable jTable1;
    protected javax.swing.JTable jTable2;
    protected javax.swing.JTable jTable3;
    protected javax.swing.JTable jTable4RecipeDetailed;
    public javax.swing.JTable jTableIngredientInfoTable;
    public javax.swing.JTable jTableRecipeDetailedTable4HelpTable;
    public javax.swing.JTable jTableSequnce2;
    public javax.swing.JTable jTableSequnece1;
    public javax.swing.JTable jTable_1_RecipeAdd;
    public javax.swing.JTable jTable_1_test_parameters;
    public javax.swing.JTable jTable_2_RecipeAdd;
    protected javax.swing.JTable jTable_Ingred_Table1;
    protected javax.swing.JTable jTable_Ingred_Table3;
    protected javax.swing.JTable jTable_Ingred_table2;
    public javax.swing.JTable jTable_lab_dev_1;
    public javax.swing.JTable jTable_lab_dev_2;
    public javax.swing.JTable jTable_lab_dev__find_order;
    public javax.swing.JTable jTable_lab_dev__material_info;
    public javax.swing.JTable jTable_test_definitions;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextArea jTextArea1_Logg;
    public javax.swing.JTextArea jTextArea_notes__lab_dev_tab;
    public javax.swing.JTextField jTextFieldCommandParamSequence;
    public static javax.swing.JTextField jTextFieldHomeUserName;
    public javax.swing.JTextField jTextFieldInfoSequence;
    public javax.swing.JTextField jTextFieldStepNrSequence;
    public javax.swing.JTextField jTextFieldUpdateOnSequence;
    public javax.swing.JTextField jTextFieldUpdatedBy;
    public javax.swing.JTextField jTextField__lab_dev__find_order;
    public static javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
        //
//        System.out.println("" + me.getSource());
        //
    }

    public void recipeDetailedTabbClicked() {
        //
        if (recipeDetailed == null && recipeInitial != null) {
            recipeDetailed = new RecipeDetailed_(this, sql, sql_additional, new ChangeSaver(sql, this), recipeInitial);
        }
        //
        if (recipeDetailed != null) {
            jPanel12.add(jScrollPane3);// Moving TABLE2
            jPanel13.add(jScrollPane4);//Moving TABLE3
            jPanel14.add(jScrollPaneRecipeInitialNotes);//Moving TEXT_AREA
            jScrollPaneRecipeInitialNotes.setSize(jPanel14.getSize());
            //
            recipeDetailed.showTableInvert();
            //
            recipeDetailed.checkIfDisabled();
            //
            recipeDetailed.fill_table_4(true);
            //
            recipeDetailed.reset();
            //
            recipeDetailed.autoSelectRowTable4();
            //
            recipeDetailed.showLockedUnlocked();
        }
        //
    }

    private RecipeInitialIF defineRecipeInitial() {
//        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_COMPOUNDS)) {
//            return new RecipeInitialCp(this, sql);
//        } else {
//            return new RecipeInitialQew(this, sql);
//        }
//
        return new RecipeInitial(this, sql);
    }

    public void recipeInitialTabClicked() {
        //
        if (recipeInitial == null) {
//            recipeInitial = new RecipeInitial(this, sql);
            recipeInitial = defineRecipeInitial();
        }
        //
        jPanel5.add(jScrollPane3);// Moving TABLE2
        jPanel6.add(jScrollPane4);//Moving TABLE3
        jPanel7.add(jScrollPaneRecipeInitialNotes);//Moving TEXT_AREA
        //
        jScrollPaneRecipeInitialNotes.setSize(jPanel7.getSize());
        //
        HelpA_.resetTableHeaderPainting(jTable2, HelpA_.getColByName(jTable2, "Note_Value"));
    }

    

    public static String ACTUAL_TAB_NAME = "";

    private void mousePressedOnTab(MouseEvent me) {
        //
        if (me.getSource() == jTabbedPane1) {
            //
            String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            ACTUAL_TAB_NAME = title;
            //
            if (title.equals(LNG.RECIPE_INITIAL_TAB())) {
                //
                recipeInitialTabClicked();
                //
            } else if (title.equals(LNG.RECIPE_DETAILED_TAB())) {
                // 
                recipeDetailedTabbClicked();
                //
            } else if (title.equals(LNG.INGREDIENTS_TAB())) {
                //
                if (ingredients == null) {
                    ingredients = new Ingredients(sql, sql_additional, this);
                } else if (ingredients != null && ingredients.table1Build) {
                    clickedTable1Ingredients();
                }
                //
                if (vendors == null) {
                    vendors = new Vendors(sql, sql_additional, this);
                }
                //
            } else if (title.equals(LNG.INGREDIENTS_TAB_B())) {
                //
                if (ingredients != null && ingredients.table1Build) {
                    clickedTable1Ingredients();
                }
                //
            } else if (title.equals(LNG.SEQUENCE_TAB())) {
                //
                if (sequence == null) {
                    sequence = new Sequence(sql, sql_additional, this);
                }
                //
                if (recipeDetailed != null) {
                    sequence.setSelectedItems();
                    jButtonSequenceSearchActionPerformed(null);
                }
                //
            } else if (title.equals(LNG.VENDORS_TAB())) {
                //
                if (vendors == null) {
                    vendors = new Vendors(sql, sql_additional, this);
                }
                //
                if (jComboBoxVendorChooseIngred.getSelectedItem() != null) {
                    vendorsShowTablesOnItemStateChanged();
                    vendors.vendorsColumnClickedSimulation();
                    vendors.reset();
                }
                //
            } else if (title.equals(LNG.VENDORS_TAB_B())) {
                //
                if (vendors != null && vendorsB == null) {
                    vendorsB = new VendorsB(sql, sql_additional, this, vendors);
                }
                //
                if (vendors != null && vendorsB != null) {
                    vendors_tab_b_clicked();
                }
                //
            } else if (title.equals(LNG.RECIPE_ADD_TAB())) {
                //
                if (recipeAdditional != null && recipeInitial != null) {
                    recipeAdditional.recipeAdditionalTabClicked();
                }
                //
                if (recipeAdditional == null && recipeInitial != null) {
                    recipeAdditional = new RecipeAdditional(sql, sql_additional, this);
                }

            } else if (title.equals(LNG.TEST_PARAMS_TAB())) {
                //
                if (testParameters == null) {
                    testParameters = new TestParameters_(sql, sql_additional, this);
                }
                //
                test_parameters_tab_clicked();
                //
            } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB())) {
                //
                if (labDevelopment == null) {
                    labDevelopment = new LabDevelopment(sql, sql_additional, this, new ChangeSaver(sql, this));
                }
                //
                labDevelopment.lab_dev_tab__clicked(null,true);
                //
            }
        }
    }

    

    public void test_parameters_tab_clicked() {
        testParameters.fillTable1();
        testParameters.showTableInvert();
    }

    public void vendors_tab_b_clicked() {
        vendorsB.showTableInvertIn(jPanelInvertTable4_B, vendors.TABLE_INVERT_4);
        vendorsB.showTableInvertIn(jPanelInvertTable3_2_B, vendors.TABLE_INVERT_3_2);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //
        mousePressedOnTab(me);
        //
        boolean cond_1 = HelpA_.getCurrentTabName(jTabbedPane1).equals(LNG.RECIPE_DETAILED_TAB);
        //
        //
        if (me.getSource() == jTable3 && (me.getClickCount() == 1)) {
            //
            recipeInitial.fillNotes();
            //
        } else if (me.getSource() == jTable3 && (me.getClickCount() == 2)) {
            //
            recipeDetailed.table3_change_note_name(jTable3);
            //
        } else if (me.getSource() == jTable2 && (me.getClickCount() == 2)) {
            //
            recipeDetailed.table2_change_note_value(jTable2);
            //
        } else if (me.getSource() == jTable1 && (me.getClickCount() == 1)) {
            //
            clickedOnTable1RecipeInitial();
            //
        } else if (me.getSource() == jTable4RecipeDetailed && (me.getClickCount() == 2)) {
            //
            recipeDetailed.edit_ingredients_parameters_table_4(jTable4RecipeDetailed.getSelectedRow(), jTable4RecipeDetailed.getSelectedColumn());
            //
        } else if (me.getSource() == jTable4RecipeDetailed && (me.getClickCount() == 1)) {
            //
            recipeDetailed.jTable4RecipeDetailedClicked();
            //
        } else if (me.getSource() == jTable_Ingred_Table1 && (me.getClickCount() == 1)) {
            //
            clickedTable1Ingredients();
            //
        } else if (me.getSource() == jTable_Ingred_Table3 && (me.getClickCount() == 1)) {
            //
            ingredients.fillNotes();
            //
        } else if (me.getSource() == jTable_Ingred_Table3 && (me.getClickCount() == 2)) {
            //
//            ingredients.edit_table_3(jTable_Ingred_Table3.getSelectedRow(), jTable_Ingred_Table3.getSelectedColumn());
            //
            ingredients.addPotentiallyUnsavedEntries(jTable_Ingred_Table3.getSelectedRow(),
                    ingredients.unsavedRows_table_3, ingredients.addedRows_table_3, jTable_Ingred_Table3);
            //
        } else if (me.getSource() == jTableSequnece1 && (me.getClickCount() == 1)) {
            //
            clickedTable1Sequence();
            //
        } else if (me.getSource() == jTable_1_test_parameters && (me.getClickCount() == 1)) {
            //
            clickedTable1TestParameters();
            //
            //
            //
        } else if (me.getSource() == jTable_lab_dev_1 && (me.getClickCount() == 2)) {
            labDevelopment.changeJTableNoteValue(jTable_lab_dev_1, LabDevelopment.TABLE_NOTES_1, "Name", "ID");
        } else if (me.getSource() == jTable_lab_dev_2 && (me.getClickCount() == 2)) {
            labDevelopment.changeJTableNoteValue(jTable_lab_dev_2, LabDevelopment.TABLE_NOTES_2, "Name", "ID");
        } else if(me.getSource() == jTable_lab_dev__material_info && (me.getClickCount() == 1)){
            labDevelopment.materialInfoJTableClicked();
        }
    }

    public void clickedTable1TestParameters() {
        testParameters.showTableInvert();
    }

    public void clickedTable1Sequence() {
        //
        sequence.table1Click(jTableSequnece1);
        //
    }

    public void clickedTable1Ingredients() {
        //
        if (Ingredients.INGRED_NAME != null) {
            ingredients.fill_table_1(Ingredients.INGRED_NAME);
            Ingredients.INGRED_NAME = null;
            HelpA_.markGivenRow(jTable_Ingred_Table1, 0);
        }
        //
        ingredients.showTableInvert();
        //
        ingredients.fill_tables_2_and_3();
        //
        HelpA_.markGivenRow(jTable_Ingred_Table3, 0);
        ingredients.fillNotes();
        //
        vendors.setSelectedItem(ingredients.getIngredCode());
    }

    public void clickedOnTable1RecipeInitial() {
        //
        recipeInitial.fill_table_2_and_3();
        //
        HelpA_.markGivenRow(jTable3, 0);
        recipeInitial.fillNotes();
        //
        //
        if (testParameters != null) {
            testParameters.clearBoxes();
        }
        //
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //
        if (me.getSource() instanceof JButton) {
            JButton button = (JButton) me.getSource();
            if (button.getParent() instanceof JComboBox) {
                //
                JComboBox parent = (JComboBox) button.getParent();
                //Sequence
                if (parent.equals(jComboBoxSequenceRecipe)) {
                    sequence.fillComboRecipe();
                } else if (parent.equals(jComboBoxSequenceRelease)) {
                    sequence.fillComboRelease();
                } else if (parent.equals(jComboBoxSequenceMixerCode)) {
                    sequence.fillComboMixer();
                } else if (parent.equals(jComboBoxSequenceRecipeCopy)) {
                    sequence.fillComboRecipeCopy();
                } else if (parent.equals(jComboBoxSequenceReleaseCopy)) {
                    sequence.fillComboReleaseCopy();
                } else if (parent.equals(jComboBoxSequenceMixerCodeCopy)) {
                    sequence.fillComboMixerCopy();
                } //
                //RecipeInitial GROUP A
                else if (parent instanceof JComboBox_RI_A) {
                    //
                    JComboBox_RI_A box = (JComboBox_RI_A) parent;
                    //
                    if (box.isMULTI_PARAM()) {
                        recipeInitial.fillComboBoxMultiple(box, box.getPARAMETER(), box.getPARAMETER_2());
                    } else {
                        recipeInitial.fillComboBox(box, box.getPARAMETER());
                    }
                    //
                    //
                } //RecipeInitial GROUP B
                else if (parent.equals(jComboBox_Ingred_1)) {
                    //
                    recipeInitial.fillComboBoxIngredients_with_wait((JComboBoxM) jComboBox_Ingred_1, "IngredName1");
                    //
                } else if (parent.equals(jComboBox_Ingred_2)) {
                    //
                    recipeInitial.fillComboBoxIngredients_with_wait((JComboBoxM) jComboBox_Ingred_2, "IngredName2");
                    //
                } //   RecipeInitial GROUP C
                else if (parent instanceof JComboBox_RI_C) {
                    //
                    JComboBox_RI_C box = (JComboBox_RI_C) parent;
                    //
                    recipeInitial.fillComboBoxB(box, box.getPARAMETER());
                    //
                } //Ingredients
                //
                else if (parent instanceof JComboBox_ING_A) {
                    //
                    JComboBox_ING_A box = (JComboBox_ING_A) parent;
                    //
                    ingredients.fillComboBox(box, box.getPARAMETER());
                    //
                } //
                else if (parent instanceof JComboBox_TP_A) {
                    //
                    JComboBox_TP_A box = (JComboBox_TP_A) parent;
                    //
                    testParameters.fillComboBox(box, box.getPARAMETER());
                    //
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
//        if (me.getSource() instanceof JButton) {
//            JButton button = (JButton) me.getSource();
//            if (button.getParent() instanceof JComboBox) {
//                //
//                JComboBox parent = (JComboBox) button.getParent();
//                //
//                parent.setBorder(HelpA_.initialComboBoxBorder);
//            }
//        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        Object source = ie.getSource();
        if (source instanceof JComboBox) {
            if (source == jComboBoxSequenceRecipe && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                sequence.fillComboRecipe();
                //
            } else if (source == jComboBoxSequenceRelease && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                sequence.fillComboRelease();
                //
            } else if (source == jComboBoxVendorChooseIngred && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                vendorsShowTablesOnItemStateChanged();
                //
            } else if (source == jComboBoxVenorsTradnames && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                vendors.comboTradeNamesItemStateChanged();
                //
            } else if (source == jComboBoxVenorsVendors && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                vendors.comboVendorsItemStateChanged();
                //
            } else if (source == jComboBoxRecipeAdditionalOrders && ie.getStateChange() == ItemEvent.SELECTED) {
                //
                recipeAdditional.fillTable2();
                //
            }
        }
    }

    public void vendorsShowTablesOnItemStateChanged() {
        //
        vendors.clear_tables_3_2__4__4_2();
        //
        vendors.showTableInvert();
        vendors.showTableInvert2();
        vendors.showTableInvert3();
        vendors.showTableInvert4(null);
        vendors.showTableInvert4_2(null);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == textAreaRecipeInitialNotes || ke.getSource() == jEditorPaneRecipeInitialNotes) {
            recipeDetailed.notesUnsaved = true;
        } else if (ke.getSource() == jEditorPane_Ingred || ke.getSource() == textAreaIngredComments) {
            ingredients.notesUnsaved = true;
        } else if (ke.getSource() == jTextArea_notes__lab_dev_tab) {
            labDevelopment.notesUnsaved = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //
        boolean cond_1 = (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP);
        //
        if (ke.getSource() == jTable1 && cond_1) {
            clickedOnTable1RecipeInitial();
        } else if (ke.getSource() == jTable_Ingred_Table1 && cond_1) {
            clickedTable1Ingredients();
        }
    }

//    public void columnMarginChanged(ChangeEvent ce) {
//        //
//        DefaultTableColumnModel dtcm = (DefaultTableColumnModel) ce.getSource();
//        JTable parentTable = null;
//        //
//        Object[] signers = dtcm.getColumnModelListeners();
//        //
//        for (Object object : signers) {
//            if (object instanceof JTable && parentTable == null) {
//                parentTable = (JTable) object;
//            }
//        }
//        //
//        //
//        if (parentTable == jTable4RecipeDetailed) {
//            //
//            HelpA_.synchColumnWidths(jTable4RecipeDetailed, jTableRecipeDetailedTable4HelpTable);
//            //
//            if (recipeDetailed != null) {// asking for null very important for proper saving of widths
//                HelpA_.R_DETAILED_TABLE_4_LIST = HelpA_.saveColumnWidths(HelpA_.R_DETAILED_TABLE_4_LIST, 75, jTable4RecipeDetailed, HelpA_.R_DETAILED_TABLE_4_OBJ);
//            }
//            //
//        } else if (parentTable == jTable_Ingred_Table1) {
//            //
//            if (ingredients != null) {// asking for null very important for proper saving of widths
//                HelpA_.INGRED_TABLE_1_LIST = HelpA_.saveColumnWidths(HelpA_.INGRED_TABLE_1_LIST, 75, jTable_Ingred_Table1, HelpA_.INGRED_TABLE_1_OBJ);
//            }
//            //
//        }
//        //
//    }
    @Override
    public void comboBoxvalueChanged(String value, JComboBoxM boxX) {
        if (boxX.equals(jComboBox_Ingred_1) || boxX.equals(jComboBox_Ingred_2)) {
            recipeInitial.showIngredInfoOnValueChange(value);
        }
    }
}
