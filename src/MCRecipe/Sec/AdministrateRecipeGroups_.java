/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.Ingredients;
import MCRecipe.MC_RECIPE;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import MyObjectTableInvert.TableInvert;
import forall.GP;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class AdministrateRecipeGroups_ extends javax.swing.JFrame implements MouseListener {

    protected final String TITLE;
    public final MC_RECIPE mc_recipe;
    public final SqlBasicLocal sql;
    public final SqlBasicLocal sql_additional;
//    private Table TABLE_INVERT;
    public BasicTab basicTab;
    public TableBuilderInvert_ TABLE_BUILDER_INVERT;
    public String TABLE_NAME = "Recipe_Group";
    public String TABLE_ID = "Recipe_Group_ID";
    
    /**
     * Creates new form AdministrateUsers
     */
    public AdministrateRecipeGroups_(String title,MC_RECIPE mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        initComponents();
        this.TITLE = title;
        this.mc_recipe = mc_recipe;
        this.sql = sql;
        this.sql_additional = sql_additional;
        initOther();

        go();
    }
    

    private void initOther() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(TITLE);
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_RECIPE).getImage());
        jTable1.addMouseListener(this);
        jTable1.setAutoCreateRowSorter(true);
        this.setLocation(HelpA_.position_window_in_center_of_the_screen(this));
        setTableTitle();
    }
    
    public void setTableTitle(){
        jLabel1.setText("RECIPE GROUPS");
    }

    private void go() {
        initBasicTab();
        basicTab.initializeSaveIndicators();
        showTable();
        //
        HelpA_.markFirstRowJtable(jTable1);
        clikedJtable1();
    }

    public void showTable() {
        //
        String q = "select * from " + TABLE_NAME;
        //
        try {
            ResultSet rs = sql.execute(q, mc_recipe);
            HelpA_.build_table_common(rs, jTable1, q);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateRecipeGroups_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        actionsAfterShowTable();
    }
    
    public void actionsAfterShowTable(){
        HelpA_.hideColumnByName(jTable1, "dateCreated");
        HelpA_.hideColumnByName(jTable1, "dateChanged");
    }

    public void initBasicTab() {
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                RowDataInvert id = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Recipe_Group_ID", "ID", "", true, true, false);
                //
                RowDataInvert mainGroup = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Main_Group", "MAIN GROUP", "", true, true, false);
                //
                RowDataInvert mainInfo = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "main_Info", "MAIN INFO", "", true, true, false);
                //
                RowDataInvert detailedGroup = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Detailed_Group", "DETAILED GROUP", "", true, true, false);
                //
                RowDataInvert detailedInfo = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Detailed_Info", "DETAILED INFO", "", true, true, false);
                //
                RowDataInvert noteMain = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "Note_Main", "NOTE MAIN", "", true, true, false);
                //
                RowDataInvert updatedOn = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "UpdatedOn", "UPDATED ON", "", true, true, false);
                //
                RowDataInvert updatedBy = new RowDataInvert(TABLE_NAME, TABLE_ID, false, "UpdatedBy", "UPDATED BY", "", true, true, false);
                //
                id.setUneditable();
                updatedOn.setUneditable();
                updatedBy.setUneditable();
                //
                RowDataInvert[] rows = {id, mainGroup, mainInfo, detailedGroup, detailedInfo, noteMain, updatedOn, updatedBy};
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "vendors_2");
                //
                TABLE_INVERT = null;
                //
                String id = HelpA_.getValueSelectedRow(jTable1, TABLE_ID);
                //
                try {
                    String q = "select * from " + TABLE_NAME
                            + " where " + TABLE_ID + "= " + id;
                    //
                    OUT.showMessage(q);
                    TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q,this);
                } catch (SQLException ex) {
                    Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
                    TABLE_BUILDER_INVERT.showMessage(ex.toString());
                }
                //
                showTableInvert(jPanel1, TABLE_INVERT);
            }

            @Override
            public void initializeSaveIndicators() {
                SaveIndicator saveIndicator1 = new SaveIndicator(jButton_Save, this, 1);
            }

            @Override
            public void fillNotes() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean getUnsaved(int nr) {
                if (nr == 1) {
                    //
                    if (TABLE_INVERT == null) {
                        return false;
                    } else if (unsavedEntriesExist(TABLE_INVERT)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                        return true;
                    }
                    //
                }
                return false;
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTable();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonRecipeDetailedAddNewRecipe = new javax.swing.JButton();
        jButton_Save = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.png"))); // NOI18N
        jButton2.setToolTipText("Delete entry");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeDetailedAddNewRecipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        jButtonRecipeDetailedAddNewRecipe.setToolTipText("Add new group");
        jButtonRecipeDetailedAddNewRecipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeDetailedAddNewRecipeActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonRecipeDetailedAddNewRecipe);

        jButton_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        jButton_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SaveActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_Save);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveActionPerformed
        TableInvert ti = (TableInvert) basicTab.TABLE_INVERT;
        ti.handleAutomaticFieldUpdate("dateChanged", HelpA_.updatedOn());
        basicTab.saveChangesTableInvert();
        showTable();
    }//GEN-LAST:event_jButton_SaveActionPerformed

    private void jButtonRecipeDetailedAddNewRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed
        addEntry();
    }//GEN-LAST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteUser();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void deleteUser() {
        //
        if (HelpA_.confirm() == false) {
            return;
        }
        //
        String id = HelpA_.getValueSelectedRow(jTable1, TABLE_ID);
        //
        String q = "delete from " + TABLE_NAME
                + " where " + TABLE_ID + " = " + id;
        //
        try {
            sql.execute(q, mc_recipe);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateRecipeGroups_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTable();
        HelpA_.markFirstRowJtable(jTable1);
        clikedJtable1();
    }
    
    public String addEntryQuery(){
        return "insert into " + TABLE_NAME
                + " values('NEW','','','','','" + HelpA_.updatedOn() + "','" + HelpA_.updatedBy()+ "')";
    }

    private void addEntry() {
        //
        String q = addEntryQuery();
        //
        try {
            sql.execute(q, mc_recipe);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateRecipeGroups_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTable();
        HelpA_.markLastRowJtable(jTable1);
        clikedJtable1();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton jButton2;
    protected javax.swing.JButton jButtonRecipeDetailedAddNewRecipe;
    protected javax.swing.JButton jButton_Save;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getSource() == jTable1 && (me.getClickCount() == 1)) {
            clikedJtable1();
        }
    }

    private void clikedJtable1() {
        basicTab.showTableInvert();
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
}
