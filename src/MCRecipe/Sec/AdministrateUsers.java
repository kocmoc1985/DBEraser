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
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.GP;
import forall.HelpA;
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

/**
 *
 * @author KOCMOC
 */
public class AdministrateUsers extends javax.swing.JFrame implements MouseListener {

    private final MC_RECIPE mc_recipe;
    private final SqlBasicLocal sql;
    private final SqlBasicLocal sql_additional;
//    private Table TABLE_INVERT;
    private BasicTab basicTab;
    private TableBuilderInvert TABLE_BUILDER_INVERT;
    public final static String TABLE_NAME = "MCRecipeUsers";

    /**
     * Creates new form AdministrateUsers
     */
    public AdministrateUsers(MC_RECIPE mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) throws HeadlessException {
        initComponents();
        this.mc_recipe = mc_recipe;
        this.sql = sql;
        this.sql_additional = sql_additional;
        initOther();
        go();
    }

    private void initOther() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Administrate Users");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_RECIPE).getImage());
        jTable1.addMouseListener(this);
    }

    private void go() {
        initBasicTab();
        basicTab.initializeSaveIndicators();
        showTable();
        //
        HelpA.markFirstRowJtable(jTable1);
        clikedJtable1();
    }

    private void showTable() {
        //
        String q = "select * from " + TABLE_NAME;
        //
        try {
            ResultSet rs = sql.execute(q,mc_recipe);
            HelpA.build_table_common(rs, jTable1,q);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.hideColumnByName(jTable1, "dateCreated");
        HelpA.hideColumnByName(jTable1, "dateChanged");
    }

    private void initBasicTab() {
        //
        basicTab = new BasicTab(sql, sql_additional, mc_recipe) {
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                RowDataInvert id = new RowDataInvert(TABLE_NAME, "id", false, "id", "ID", "", true, true, false);
                //
                RowDataInvert user = new RowDataInvert(TABLE_NAME, "id", false, "userName", "USER", "", true, true, false);
                //
                RowDataInvert pass = new RowDataInvert(TABLE_NAME, "id", false, "pass", "PASS", "", true, true, false);
                //
                RowDataInvert role = new RowDataInvert(TABLE_NAME, "id", false, "role", "ROLE", "", true, true, false);
                //
                RowDataInvert dateCreated = new RowDataInvert(TABLE_NAME, "id", false, "dateCreated", "CREATED ON", "", true, true, false);
                //
                RowDataInvert dateChanged = new RowDataInvert(TABLE_NAME, "id", false, "dateChanged", "UPDATED ON", "", true, true, false);
                //
                id.setUneditable();
                dateCreated.setUneditable();
                dateChanged.setUneditable();
                //
                RowDataInvert[] rows = {id, user, pass, role, dateCreated, dateChanged};
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "vendors_2");
                //
                TABLE_INVERT = null;
                //
                String id = HelpA.getValueSelectedRow(jTable1, "id");
                //
                try {
                    String q = "select * from " + TABLE_NAME
                            + " where id = " + id;
                    //
                    OUT.showMessage(q);
                    TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
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
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonRecipeDetailedAddNewRecipe = new javax.swing.JButton();
        jButton_Save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        jButton2.setToolTipText("Mark to delete, delete is done after save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jButtonRecipeDetailedAddNewRecipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        jButtonRecipeDetailedAddNewRecipe.setToolTipText("Add new user");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(641, 641, 641)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveActionPerformed
        TableInvert ti = (TableInvert) basicTab.TABLE_INVERT;
        ti.handleAutomaticFieldUpdate("dateChanged", HelpA.updatedOn());
        basicTab.saveChangesTableInvert();
        showTable();
    }//GEN-LAST:event_jButton_SaveActionPerformed

    private void jButtonRecipeDetailedAddNewRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed
       addUser();
    }//GEN-LAST:event_jButtonRecipeDetailedAddNewRecipeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteUser();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void deleteUser(){
        //
        if(HelpA.confirm() == false){
            return;
        }
        //
        String id = HelpA.getValueSelectedRow(jTable1, "id");
        String q = "delete from " + TABLE_NAME
                + " where id = " + id;
        //
        try {
            sql.execute(q,mc_recipe);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTable();
        HelpA.markFirstRowJtable(jTable1);
        clikedJtable1();
    }
    
    private void addUser(){
        //
        String q = "insert into " + TABLE_NAME
                + " values('new','new','','" + HelpA.updatedOn() + "','" + HelpA.updatedOn() + "')";
        //
        try {
            sql.execute(q,mc_recipe);
        } catch (SQLException ex) {
            Logger.getLogger(AdministrateUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showTable();
        HelpA.markLastRowJtable(jTable1);
        clikedJtable1();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton jButton2;
    protected javax.swing.JButton jButtonRecipeDetailedAddNewRecipe;
    protected javax.swing.JButton jButton_Save;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
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
    
    private void clikedJtable1(){
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
