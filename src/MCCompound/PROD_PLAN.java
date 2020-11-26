/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import MyObjectTable.ShowMessage;
import DatabaseBrowser.FQ;
import MCRecipe.MC_RECIPE;
import MCRecipe.Sec.PROC;
import com.jezhumble.javasysmon.JavaSysMon;
import forall.ErrorOutputListener;
import forall.GP;
import forall.HelpA_;
import forall.Sql_B;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import supplementary.HelpM;

/**
 *
 * @author KOCMOC
 */
public class PROD_PLAN extends javax.swing.JFrame implements MouseListener, ShowMessage, KeyListener {

    private Sql_B sql;
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";
    private final String ODBC = "odbc";
    private final String PROPERTIES_PATH = MC_RECIPE.PROPERTIES_PATH;
    private final Properties PROPS = HelpA_.properties_load_properties(PROPERTIES_PATH, false);
    private final JavaSysMon monitor = new JavaSysMon();
    private ClientCompound clientCompound;
    private ShowProgress showProgress;
    private final boolean TEST_MODE = Boolean.parseBoolean(PROPS.getProperty("prod_plan_test_mode", "false"));
    private final String npms_host_ip = PROPS.getProperty("npms_host", "localhost");
    private ProdPlan prodPlan;
    private ErrorOutputListener errorOutputListener; // OBS! is in use!

    /**
     * Creates new form PROD_PLAN
     */
    public PROD_PLAN() {
        initComponents();
        init();
        go();
    }

    private void init() {
        int pid = monitor.currentPid();
        this.setTitle("MCProdPlan (" + pid + ")");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_PROD_PLAN).getImage());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //
        jTable1.setAutoCreateRowSorter(true);
        jTable2.setAutoCreateRowSorter(true);
        jTabbedPane1.addMouseListener(this);
        jTable1.addMouseListener(this);
        jTable1_2.addMouseListener(this);
        jTable1.addKeyListener(this);
        jTable1_2.addKeyListener(this);
        //
        errorOutputListener = new ErrorOutputListener(HelpA_.LAST_ERR_OUT_PUT_FILE_PATH, jTabbedPane1, jTextAreaErrOutPut, jPanel2);
        //
    }

    @Override
    public void showMessage(String str) {
        String msg = "[" + HelpA_.get_proper_date_time_same_format_on_all_computers() + "] " + "  " + str + "\n";
        System.out.print(msg);
        jTextArea1Console.append(msg);
    }
    
    public void setDisposeOnClose(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public boolean getTestMode() {
        return TEST_MODE;
    }

    private void connectToNpms() {
        //
        showProgress = new ShowProgress();
        //
        if (clientCompound != null && clientCompound.isConnected()) {
            System.out.println("RETURN: ALREADY CONNECTED TO NPMS");
            return;
        }
        //
        if (clientCompound == null || clientCompound.isConnected() == false) {
            clientCompound = new ClientCompound(this, npms_host_ip);
        }
        //
        ClientProtocolCompound cpc = clientCompound.getProtocol();
        cpc.setProdPlan(this);
        cpc.setShowProgress(showProgress);
        //
        if (clientCompound.isConnected() == false) {
            showProgress.enableHideOnClose();
            showProgress.showMessageAppend("Connection to remote replication server on " + npms_host_ip + " failed. Requested procedure can not be done");
        }
        //
    }

    private void copy_dbf_and_run_procedure() {
        //
        connectToNpms();
        //
        // [DESCRIPTION OF COPY MECHANISM:]
        // A "QEW_COMPOUND_COPY_DBF_OK" command is sent to the NPMS server which is intended to make copy.
        // Attention that at the NPMS side a propertie file is needed which is placed in "properties/npms_prod_plan.properties".
        // After the copying is done it sends the same CMD as feedback
        if (getTestMode() == false) {
            clientCompound.sendDoCopyDbfFiles();
        }
        //
    }

    public boolean sql_replication_procedure(String operationName, ShowProgress sp) {
        //
        try {
            sp.showMessageAppend("Replication procedure started, duration about 3 min");
            //
            sp.goToEnd();
            //
            int RETUR = HelpA_.runProcedureIntegerReturn_A(sql.getConnection(), SQL_A.delete_create_all_recipe(PROC.PROC_P_02));
            //
//            System.out.println("RETUR: " + RETUR);
            //
            if (RETUR == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            sp.showMessageAppend("Replication procedure ended with failure: " + "\n" + ex);
            return false;
        }
    }

    public void setLastReplicationTime(String time) {
        this.jLabel_lastReplicationTime.setText("Last replication made on: " + time);
    }

    public void buildProdPlanTable_forward() {
        prodPlan.buildProdPlanTable();
    }
    
    public void rebuildAll_forward(){
        prodPlan.rebuildAll();
    }
    
    public void redrawTablesInvert_forward(){
        prodPlan.redrawTablesInvert();
    }

    public void buildCSVTable_2() {
        try {
            String q = SQL_B.buildRecipeCsv();
            ResultSet rs = sql.execute(q, this);
            HelpA_.build_table_common(rs, jTable2, q);
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel_Table1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel_Table_1_2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1_2 = new javax.swing.JTable();
        jPanel_Table_Invert_1 = new javax.swing.JPanel();
        jPanel_Table_Invert_1_2 = new javax.swing.JPanel();
        jButton2Save = new javax.swing.JButton();
        jButtonToCsvTable = new javax.swing.JButton();
        jButton_ShowAdditional = new javax.swing.JButton();
        jButton3AddToTempTable = new javax.swing.JButton();
        jButtonDeleteRecordTempTable = new javax.swing.JButton();
        jButtonChangePosition = new javax.swing.JButton();
        jButtonDeleteAllRecordsTempTable = new javax.swing.JButton();
        jButtonPrintTable1 = new javax.swing.JButton();
        jButtonPrintTable2 = new javax.swing.JButton();
        jButtonPrintTable3 = new javax.swing.JButton();
        jButtonPrintTable4 = new javax.swing.JButton();
        jLabel_lastReplicationTime = new javax.swing.JLabel();
        jButtonCopyProcedureStart = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4Export = new javax.swing.JButton();
        jButtonClearAll = new javax.swing.JButton();
        jButtonClearColumn = new javax.swing.JButton();
        jComboBox3SelectColumn = new javax.swing.JComboBox();
        jButton4Export1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton5AdjustOptions = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1Console = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaErrOutPut = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Table1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Table1.setOpaque(false);
        jPanel_Table1.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(jTable1);

        jPanel_Table1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel_Table_1_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Table_1_2.setOpaque(false);
        jPanel_Table_1_2.setLayout(new java.awt.BorderLayout());

        jTable1_2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable1_2);

        jPanel_Table_1_2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel_Table_Invert_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Table_Invert_1.setOpaque(false);
        jPanel_Table_Invert_1.setLayout(new java.awt.BorderLayout());

        jPanel_Table_Invert_1_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Table_Invert_1_2.setOpaque(false);
        jPanel_Table_Invert_1_2.setLayout(new java.awt.BorderLayout());

        jButton2Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        jButton2Save.setToolTipText("Save changes");
        jButton2Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2SaveActionPerformed(evt);
            }
        });

        jButtonToCsvTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mark_1.png"))); // NOI18N
        jButtonToCsvTable.setToolTipText("Import to .csv table");
        jButtonToCsvTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonToCsvTableActionPerformed(evt);
            }
        });

        jButton_ShowAdditional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info.png"))); // NOI18N
        jButton_ShowAdditional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ShowAdditionalActionPerformed(evt);
            }
        });

        jButton3AddToTempTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        jButton3AddToTempTable.setToolTipText("Add");
        jButton3AddToTempTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3AddToTempTableActionPerformed(evt);
            }
        });

        jButtonDeleteRecordTempTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
        jButtonDeleteRecordTempTable.setToolTipText("Delete one entry");
        jButtonDeleteRecordTempTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteRecordTempTableActionPerformed(evt);
            }
        });

        jButtonChangePosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/swap.png"))); // NOI18N
        jButtonChangePosition.setToolTipText("Change postion");
        jButtonChangePosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangePositionActionPerformed(evt);
            }
        });

        jButtonDeleteAllRecordsTempTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear3.png"))); // NOI18N
        jButtonDeleteAllRecordsTempTable.setToolTipText("Delete all entries");
        jButtonDeleteAllRecordsTempTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteAllRecordsTempTableActionPerformed(evt);
            }
        });

        jButtonPrintTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonPrintTable1.setToolTipText("");
        jButtonPrintTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintTable1ActionPerformed(evt);
            }
        });

        jButtonPrintTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonPrintTable2.setToolTipText("Print");
        jButtonPrintTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintTable2ActionPerformed(evt);
            }
        });

        jButtonPrintTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonPrintTable3.setToolTipText("Print");
        jButtonPrintTable3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintTable3ActionPerformed(evt);
            }
        });

        jButtonPrintTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonPrintTable4.setToolTipText("Print");
        jButtonPrintTable4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintTable4ActionPerformed(evt);
            }
        });

        jLabel_lastReplicationTime.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_lastReplicationTime.setText("...");

        jButtonCopyProcedureStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/download.png"))); // NOI18N
        jButtonCopyProcedureStart.setToolTipText("Start replication");
        jButtonCopyProcedureStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopyProcedureStartActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        jButton1.setToolTipText("Refresh all tables");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonDeleteRecordTempTable, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeleteAllRecordsTempTable, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonChangePosition, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonToCsvTable, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jButtonPrintTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_Table1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_ShowAdditional, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_lastReplicationTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCopyProcedureStart, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jButton3AddToTempTable, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPrintTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_Table_1_2, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Table_Invert_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_Table_Invert_1_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 277, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonPrintTable4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2Save, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonPrintTable3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel_lastReplicationTime)
                    .addComponent(jButton_ShowAdditional, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButton3AddToTempTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButtonPrintTable1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButtonPrintTable3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButtonCopyProcedureStart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Table1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_Table_Invert_1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonDeleteRecordTempTable, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDeleteAllRecordsTempTable, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonChangePosition, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2Save, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPrintTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPrintTable4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonToCsvTable, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_Table_1_2, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addComponent(jPanel_Table_Invert_1_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ProdPlan", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButton4Export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        jButton4Export.setText("Desktop");
        jButton4Export.setToolTipText("Export to desktop");
        jButton4Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ExportActionPerformed(evt);
            }
        });

        jButtonClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear3.png"))); // NOI18N
        jButtonClearAll.setToolTipText("Clear all columns");
        jButtonClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearAllActionPerformed(evt);
            }
        });

        jButtonClearColumn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
        jButtonClearColumn.setToolTipText("Clear column");
        jButtonClearColumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearColumnActionPerformed(evt);
            }
        });

        jComboBox3SelectColumn.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"}));
        jComboBox3SelectColumn.setToolTipText("Column to clear");

        jButton4Export1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        jButton4Export1.setText("Remote");
        jButton4Export1.setToolTipText("Export to remote drive");
        jButton4Export1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4Export1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton4Export)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4Export1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClearAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClearColumn)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3SelectColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 604, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClearColumn)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4Export)
                        .addComponent(jButtonClearAll)
                        .addComponent(jComboBox3SelectColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4Export1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("CSV Table", jPanel4);

        jButton5AdjustOptions.setText("Adjust Options");
        jButton5AdjustOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5AdjustOptionsActionPerformed(evt);
            }
        });

        jTextArea1Console.setColumns(20);
        jTextArea1Console.setRows(5);
        jScrollPane3.setViewportView(jTextArea1Console);

        jTextAreaErrOutPut.setColumns(20);
        jTextAreaErrOutPut.setRows(5);
        jScrollPane5.setViewportView(jTextAreaErrOutPut);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                    .addComponent(jButton5AdjustOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5AdjustOptions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Log", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2SaveActionPerformed
        prodPlan.saveChangesTableInvert(prodPlan.TABLE_INVERT_2);
        prodPlan.buildProdPlanTableTemp();
    }//GEN-LAST:event_jButton2SaveActionPerformed

    private void jButtonToCsvTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonToCsvTableActionPerformed
        //
        clearAllColumns(false);
        //
        prodPlan.add_to_csv_table();
        //
    }//GEN-LAST:event_jButtonToCsvTableActionPerformed
    //

    private void jButton4ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ExportActionPerformed
        csvToDesktop();
    }//GEN-LAST:event_jButton4ExportActionPerformed
    private String csv;

    private void csvToDesktop() {
        try {
            csv = csvTableToCsvFile();
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        String path = define_path();
        //
        try {
            HelpA_.writeToFile(path, csv);
            JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
        } catch (IOException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private String define_path() {
        String default_path = HelpA_.get_desktop_path() + "\\csv_table.csv";
        String path = PROPS.getProperty("exported_csv_file_path", "");
        if (path.isEmpty()) {
            showMessage("csv output path: " + default_path);
            return default_path;
        } else {
            showMessage("csv output path: " + path);
            return path;
        }
    }

    private void jButton5AdjustOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5AdjustOptionsActionPerformed
        try {
            HelpA_.run_application_exe_or_jar("propertiesreader.jar", ".");
        } catch (IOException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5AdjustOptionsActionPerformed

    private void jButtonClearColumnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearColumnActionPerformed
        clearColumn();
    }//GEN-LAST:event_jButtonClearColumnActionPerformed

    private void jButtonClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearAllActionPerformed
        clearAllColumns(true);
    }//GEN-LAST:event_jButtonClearAllActionPerformed

    private void clearAllColumns(boolean confirmDialogEnabled) {
        //
        boolean x;
        //
        if (confirmDialogEnabled) {
            x = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
        } else {
            x = true;
        }
        //
        if (x) {
            for (int i = 5; i <= 20; i++) {
                try {
                    clear_column(i);
                } catch (SQLException ex) {
                    showMessage("failed to delete for column: " + i);
                    Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //
            buildCSVTable_2();
        }
    }

    private void clearColumn() {
        int column = Integer.parseInt((String) jComboBox3SelectColumn.getSelectedItem());
        //
        try {
            clear_column(column);
        } catch (SQLException ex) {
            showMessage("failed to delete for column: " + column);
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        buildCSVTable_2();
    }

    private void clear_column(int column) throws SQLException {
        //
        String procedure = SQL_A.generate_Empty_CSVColumn(PROC.PROC_P_04, column);
        //
        sql.execute(procedure, this);
    }

    private void jButton_ShowAdditionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ShowAdditionalActionPerformed
        try {
            prodPlan.showAdditional();
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_ShowAdditionalActionPerformed

    private void jButton4Export1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4Export1ActionPerformed
        csvToRemoteDrive();
    }//GEN-LAST:event_jButton4Export1ActionPerformed

    private void jButton3AddToTempTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3AddToTempTableActionPerformed
        prodPlan.insertIntoTempTable();
//        String x = null;
//        System.out.println("" + x.length());
    }//GEN-LAST:event_jButton3AddToTempTableActionPerformed

    private void jButtonDeleteRecordTempTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteRecordTempTableActionPerformed
        prodPlan.deleteEntryTempTable();
    }//GEN-LAST:event_jButtonDeleteRecordTempTableActionPerformed

    private void jButtonChangePositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangePositionActionPerformed
        prodPlan.changeSequencePosition();
    }//GEN-LAST:event_jButtonChangePositionActionPerformed

    private void jButtonDeleteAllRecordsTempTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteAllRecordsTempTableActionPerformed
        prodPlan.deleteAllRecordsTempTable();
    }//GEN-LAST:event_jButtonDeleteAllRecordsTempTableActionPerformed

    private void jButtonPrintTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintTable1ActionPerformed
        prodPlan.table1Repport();
    }//GEN-LAST:event_jButtonPrintTable1ActionPerformed

    private void jButtonPrintTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintTable2ActionPerformed
        prodPlan.table2Repport();
    }//GEN-LAST:event_jButtonPrintTable2ActionPerformed

    private void jButtonPrintTable3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintTable3ActionPerformed
        prodPlan.table3Repport();
    }//GEN-LAST:event_jButtonPrintTable3ActionPerformed

    private void jButtonPrintTable4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintTable4ActionPerformed
        prodPlan.table4Repport();
    }//GEN-LAST:event_jButtonPrintTable4ActionPerformed

    private void jButtonCopyProcedureStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopyProcedureStartActionPerformed
        //
        if(HelpM.confirm() == false){
            return;
        }
        //
        Thread x = new Thread(new CopyFilesAndRunProcedureThread());
        x.start();
    }//GEN-LAST:event_jButtonCopyProcedureStartActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        prodPlan.rebuildAll();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void csvToRemoteDrive() {
        try {
            csv = csvTableToCsvFile();
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        connectToNpms();
        //
        if (clientCompound.isConnected()) {
            clientCompound.sendCSV(csv);
        }
        //
    }

    private String csvTableToCsvFile() throws SQLException {
        String CSV_STRING = "";
        ResultSet rs = sql.execute(SQL_B.getRecipeCsv(), this);
        //
        rs.beforeFirst();
        //
        while (rs.next()) {
            for (int i = 2; i < 21; i++) {
                String actual_field = rs.getString(i);
                //
                if (actual_field == null) {
                    actual_field = "";
                }
                //
                CSV_STRING += actual_field + ";";
            }
            //
            CSV_STRING += "\r\n";
            //
        }
        return CSV_STRING.trim();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final boolean runInNetbeans = HelpA_.runningInNetBeans("ProdPlan.jar");
        //
//        if (runInNetbeans == false) {
//            HelpA_.err_output_to_file();
//        }
        //
        HelpA_.err_output_to_file();
        //
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
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PROD_PLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PROD_PLAN().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    protected javax.swing.JButton jButton2Save;
    private javax.swing.JButton jButton3AddToTempTable;
    private javax.swing.JButton jButton4Export;
    private javax.swing.JButton jButton4Export1;
    private javax.swing.JButton jButton5AdjustOptions;
    private javax.swing.JButton jButtonChangePosition;
    private javax.swing.JButton jButtonClearAll;
    private javax.swing.JButton jButtonClearColumn;
    private javax.swing.JButton jButtonCopyProcedureStart;
    private javax.swing.JButton jButtonDeleteAllRecordsTempTable;
    private javax.swing.JButton jButtonDeleteRecordTempTable;
    private javax.swing.JButton jButtonPrintTable1;
    private javax.swing.JButton jButtonPrintTable2;
    private javax.swing.JButton jButtonPrintTable3;
    private javax.swing.JButton jButtonPrintTable4;
    private javax.swing.JButton jButtonToCsvTable;
    private javax.swing.JButton jButton_ShowAdditional;
    protected javax.swing.JComboBox jComboBox3SelectColumn;
    private javax.swing.JLabel jLabel_lastReplicationTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_Table1;
    private javax.swing.JPanel jPanel_Table_1_2;
    protected javax.swing.JPanel jPanel_Table_Invert_1;
    protected javax.swing.JPanel jPanel_Table_Invert_1_2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable1;
    protected javax.swing.JTable jTable1_2;
    protected javax.swing.JTable jTable2;
    public static javax.swing.JTextArea jTextArea1Console;
    private javax.swing.JTextArea jTextAreaErrOutPut;
    // End of variables declaration//GEN-END:variables

    private void go() {
        Thread x = new Thread(new SqlConnectTableBuildThread());
        x.start();
    }

    class SqlConnectTableBuildThread implements Runnable {

        @Override
        public void run() {
//            copy_dbf_and_run_procedure();
            sqlConnect();
            initOther();
            connectToNpms();
        }
    }

    class CopyFilesAndRunProcedureThread implements Runnable {

        @Override
        public void run() {
            copy_dbf_and_run_procedure();
        }
    }

    private void initOther() {
        //
        int retur = -1;
        //
        try {
            retur = HelpA_.runProcedureIntegerReturn_A(sql.getConnection(), "Insert_Production_plan_csv_22");
            System.out.println("PROCEDURE RETURN: " + retur);
        } catch (SQLException ex) {
            Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (retur != 0) {
            HelpA_.showNotification("Replicating of production plan from .csv file failed");
        }
        //
        clearAllColumns(false);
        //
        prodPlan = new ProdPlan(sql, sql, this);
        //
    }

    private void sqlConnect() {
        //==========
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = PROPS.getProperty("mssql_create_statement_simple", "false");
        GP.MSSQL_LOGIN_TIME_OUT = Integer.parseInt(PROPS.getProperty("login_time_out", "60"));
        GP.SQL_LIBRARY_JTDS = Boolean.parseBoolean(PROPS.getProperty("use_jtds_library", "true"));
        GP.JTDS_USE_NAMED_PIPES = Boolean.parseBoolean(PROPS.getProperty("use_named_pipes", "false"));
        GP.JTDS_DOMAIN_WORKGROUP = PROPS.getProperty("domain_or_workgroup", "workgroup");
        GP.JTDS_INSTANCE_PARAMETER = PROPS.getProperty("jtds_instance", "");
        //==========
        showMessage("mssql_create_statement_simple: " + GP.MSSQL_CREATE_STATEMENT_SIMPLE);
        showMessage("login_time_out: " + GP.MSSQL_LOGIN_TIME_OUT);
        showMessage("use_jtds_library: " + GP.SQL_LIBRARY_JTDS);
        //==========
        String odbc = PROPS.getProperty("odbc", "");
        String odbc_user = PROPS.getProperty("odbc_user", "");
        String odbc_name = PROPS.getProperty("odbc_name", "");
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
        //
        sql = new Sql_B(Boolean.parseBoolean(GP.MSSQL_CREATE_STATEMENT_SIMPLE), GP.MSSQL_LOGIN_TIME_OUT, true);
        //
        //
        try {
            if (dbtype.equals(MS_SQL)) {
                //
                if (GP.SQL_LIBRARY_JTDS) {
                    sql.connect_tds(host, port, db_name, user, pass,
                            GP.JTDS_USE_NAMED_PIPES, GP.JTDS_DOMAIN_WORKGROUP,
                            GP.JTDS_INSTANCE_PARAMETER);
                } else {
                    sql.connect_jdbc(host, port, db_name, user, pass);
                }
                //
            } else if (dbtype.equals(MY_SQL)) {
                sql.connectMySql(host, port, db_name, user, pass);
            } else if (dbtype.equals(ODBC)) {
                sql.connect_odbc(odbc_user, odbc_name, odbc);
            }
            //
            showMessage("Connection to " + host + " / " + db_name + " established");
            //
            //
            //
            //
        } catch (Exception ex) {
            showMessage("Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connection to SQL failed!");
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //
        //
        if (me.getSource() == jTabbedPane1) {
            String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            if (title.equals("ProdPlan")) {
                prodPlan.showTableInvert();
                prodPlan.showTableInvert2();
            } else if (title.equals("CSV Table")) {
                buildCSVTable_2();
            }
        }
        //
        //
        if (me.getSource() == jTable1 && (me.getClickCount() == 1)) {
            prodPlan.showTableInvert();
        } else if (me.getSource() == jTable1_2 && (me.getClickCount() == 1)) {
            prodPlan.showTableInvert2();
        }
        //
        //
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //
        boolean cond_1 = (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP);
        //
        if (ke.getSource() == jTable1 && cond_1) {
            prodPlan.showTableInvert();
        } else if (ke.getSource() == jTable1_2 && cond_1) {
            prodPlan.showTableInvert2();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //
        boolean cond_1 = ke.getKeyCode() == KeyEvent.VK_ENTER;
        //
        if (ke.getSource() == jTable1 && cond_1) {
            prodPlan.insertIntoTempTable();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //
    }
}
