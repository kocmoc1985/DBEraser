/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.MC_RECIPE;
import MCRecipe.RecipeDetailed;
import MCRecipe.SQL_A;
import Reporting.JTableBasicRepport;
import forall.GP;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author KOCMOC
 */
public class CompareRecipes extends javax.swing.JFrame {
    
    private final MC_RECIPE mc_recipe;
    private final SqlBasicLocal sql;
    private final SqlBasicLocal sql_additional;
    private boolean oneTimeFlag = true;

    /**
     * Creates new form CompareRecipes
     */
    public CompareRecipes(MC_RECIPE mc_recipe, SqlBasicLocal sql, SqlBasicLocal sql_additional) {
        initComponents();
        this.mc_recipe = mc_recipe;
        this.sql = sql;
        this.sql_additional = sql_additional;
        initOther();
    }
    
    private void initOther() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_RECIPE).getImage());
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Compare Recipes");
    }
    
    public void tableCommonRepport(JTable table, boolean landscape) {
        new JTableBasicRepport(table, landscape);
    }
    
    public boolean dropCompareTable(String user) {
        String q = SQL_A.compareRecipeDropTable(user);
        //
        try {
            sql.execute(q,mc_recipe);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CompareRecipes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean addToCompare(String recipeCode, String release, String user) {
        //
        String q = SQL_A.compareRecipesAddToCompare(PROC.PROC_01,recipeCode, release, user);
        //
        mc_recipe.showMessage(q);
        //
        if (HelpA.runProcedureIntegerReturn_A_2(sql, q) == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void fillComboBoxes() {
        //
        ArrayList<String> colNames = HelpA.getVisibleColumnsNames(jTable1);
        //
        Object[] colNamesArr = colNames.toArray();
        //
        HelpA.fillComboBox(jComboBoxCompareRecipesOrderBy1, colNamesArr, "");
        HelpA.fillComboBox(jComboBoxCompareRecipesOrderby2, colNamesArr, "");
        //
    }
    
    public void orderByDynamic() {
        //
        String orderParam1 = (String) jComboBoxCompareRecipesOrderBy1.getSelectedItem();
        String orderParam2 = (String) jComboBoxCompareRecipesOrderby2.getSelectedItem();
        //
        String orderByString = "";
        //
        if (orderParam1.isEmpty() || (orderParam1.isEmpty() && orderParam2.isEmpty())) {
            return;
        }
        //
        if (orderParam1.isEmpty() == false && orderParam2.isEmpty() == false) {
            //
            orderByString = "order by " + orderParam1 + "," + orderParam2;
            //
        } else if (orderParam2.isEmpty()) {
            //
            orderByString = "order by " + orderParam1;
            //
        }
        //
        fillTable(orderByString);
        //
    }
    
    public void fillAndShow() {
        //
        unpaintAllRows_a(jTable1);
        //
        fillTable("");
        //
        this.setVisible(true);
        //
        if (oneTimeFlag) {
            fillComboBoxes();
            oneTimeFlag = false;
        }
        //
    }
    
    public void fillTable(String orderByCriteria) {
        //
        JTable table = jTable1;
        //
        String q1 = SQL_A.compareRecipesShow(HelpA.updatedBy(), orderByCriteria);
        //
        try {
            //
            ResultSet rs = sql.execute(q1,mc_recipe);
            //
            HelpA.build_table_common_with_rounding(rs,q1, table, "%2.2f",
                    new String[]{RecipeDetailed.t4_id, RecipeDetailed.t4_loadingSeq,RecipeDetailed.t4_material,RecipeDetailed.t4_release},
                    new String[]{RecipeDetailed.t4_density},
                    new String[]{});
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumns(table);
        //
    }
    
    private void hideColumns(JTable table) {
        HelpA.hideColumnByName(table, "Recipe_Recipe_ID");
        HelpA.hideColumnByName(table, "RecipeID");
//        HelpA.hideColumnByName(table, "RecipeName");
//        HelpA.hideColumnByName(table, "Release");
        HelpA.hideColumnByName(table, "weighingID");
        HelpA.hideColumnByName(table, "SiloId");
        HelpA.hideColumnByName(table, "BalanceID");
        HelpA.hideColumnByName(table, "PriceData");
    }
    
    private void undoAllMarkUps(JTable table){
        unpaintAllRows_a(table);
    }
    
    private void markUpRecipesB(JTable table) {
        paint_selected_rows_b(buildMarkUpListB(table), table);
    }
    
    private LinkedList<Object> buildMarkUpListB(JTable table) {
        //
        LinkedList<Color> colorList = new LinkedList<Color>();
        colorList.add(Color.lightGray);
        colorList.add(new Color(253, 255, 178)); //light yellow
        colorList.add(new Color(253, 193, 213)); // light pink 
        colorList.add(new Color(205, 240, 253)); // light blue
        colorList.add(new Color(202, 255, 209)); // light green
        colorList.add(new Color(255, 173, 250)); // more pink
        //
        HashMap<String, Color> colMap = new HashMap<String, Color>();
        //
        LinkedList listToReturn = new LinkedList();
        //
        for (int i = 0; i < table.getRowCount(); i++) {
            String rName = HelpA.getValueGivenRow(table, i, "RecipeName");
            //
            if (colMap.containsKey(rName) == false) {
                Color color = colorList.pollFirst();
                colMap.put(rName, color);
                listToReturn.add(new PaintObject(i, color));
            } else {
                listToReturn.add(new PaintObject(i, colMap.get(rName)));
            }
            //
        }
        //
        return listToReturn;
    }
    
    private void markUpRecipes(JTable table) {
        paint_selected_rows_a(buildMarkUpList(table), table, Color.lightGray);
    }
    
    private LinkedList<Integer> buildMarkUpList(JTable table) {
        //
        String actRecipeName = "";
        //
        LinkedList<Integer> list = new LinkedList<Integer>();
        //
        for (int i = 0; i < table.getRowCount(); i++) {
            String rName = HelpA.getValueGivenRow(table, i, "RecipeName");
            //
            if (actRecipeName.equals(rName) == false) {
                list.add(i);
                actRecipeName = rName;
            }
            //
        }
        
        return list;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBoxCompareRecipesOrderBy1 = new javax.swing.JComboBox();
        jComboBoxCompareRecipesOrderby2 = new javax.swing.JComboBox();
        jButtonCompareRecipesOrderByDynamic = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonCompareRecipesUndoMarkUp = new javax.swing.JButton();
        jButtonCompareRecipesMarkUp2 = new javax.swing.JButton();
        jButtonCompareRecipesMarkUp = new javax.swing.JButton();
        jButtonCompareRecipesPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

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
        jScrollPane6.setViewportView(jTable1);

        jComboBoxCompareRecipesOrderBy1.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxCompareRecipesOrderby2.setModel(new javax.swing.DefaultComboBoxModel());

        jButtonCompareRecipesOrderByDynamic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ok.png"))); // NOI18N
        jButtonCompareRecipesOrderByDynamic.setToolTipText("Order by chosen parameters");
        jButtonCompareRecipesOrderByDynamic.setPreferredSize(new java.awt.Dimension(60, 50));
        jButtonCompareRecipesOrderByDynamic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesOrderByDynamicActionPerformed(evt);
            }
        });

        jPanel2.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jButtonCompareRecipesUndoMarkUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo2.png"))); // NOI18N
        jButtonCompareRecipesUndoMarkUp.setToolTipText("Mark up recipes when mixed up");
        jButtonCompareRecipesUndoMarkUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesUndoMarkUpActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesUndoMarkUp);

        jButtonCompareRecipesMarkUp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mark_1.png"))); // NOI18N
        jButtonCompareRecipesMarkUp2.setToolTipText("Mark up type 2");
        jButtonCompareRecipesMarkUp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesMarkUp2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesMarkUp2);

        jButtonCompareRecipesMarkUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mark_2.png"))); // NOI18N
        jButtonCompareRecipesMarkUp.setToolTipText("Mark up type 1");
        jButtonCompareRecipesMarkUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesMarkUpActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesMarkUp);

        jButtonCompareRecipesPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonCompareRecipesPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesPrintActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesPrint);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBoxCompareRecipesOrderBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxCompareRecipesOrderby2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCompareRecipesOrderByDynamic, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 502, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonCompareRecipesOrderByDynamic, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxCompareRecipesOrderBy1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxCompareRecipesOrderby2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel1);

        getContentPane().add(jScrollPane5);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCompareRecipesMarkUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesMarkUpActionPerformed
        //
        markUpRecipes(jTable1);
        //
    }//GEN-LAST:event_jButtonCompareRecipesMarkUpActionPerformed
    
    private void jButtonCompareRecipesPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesPrintActionPerformed
        tableCommonRepport(jTable1, true);
    }//GEN-LAST:event_jButtonCompareRecipesPrintActionPerformed
    
    private void jButtonCompareRecipesOrderByDynamicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesOrderByDynamicActionPerformed
        unpaintAllRows_a(jTable1);
        orderByDynamic();
    }//GEN-LAST:event_jButtonCompareRecipesOrderByDynamicActionPerformed
    
    private void jButtonCompareRecipesMarkUp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesMarkUp2ActionPerformed
        markUpRecipesB(jTable1);
    }//GEN-LAST:event_jButtonCompareRecipesMarkUp2ActionPerformed

    private void jButtonCompareRecipesUndoMarkUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesUndoMarkUpActionPerformed
        undoAllMarkUps(jTable1);
    }//GEN-LAST:event_jButtonCompareRecipesUndoMarkUpActionPerformed
    
    public void paint_selected_rows_a(final LinkedList<Integer> rowsToPaint, final JTable jTable, final Color color) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(null);
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                for (Integer r : rowsToPaint) {
                    if (row == r) {
                        c.setBackground(color);
                        
                        return c;
                    }
                }
                
                return this;
            }
        });
        //
        jTable.repaint();
    }
    
    public void unpaintAllRows_a(JTable table) {
        paint_selected_rows_a(new LinkedList<Integer>(), table, null);
    }
    
    public void paint_selected_rows_b(final LinkedList<Object> rowsToPaint, final JTable jTable) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(null);
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //
                for (Object obj : rowsToPaint) {
                    PaintObject po = (PaintObject) obj;
                    if (row == po.getRow()) {
                        c.setBackground(po.getColor());
                        //
                        Color light_yellow = new Color(253, 255, 178);
                        //
                        if(po.getColor().getRGB() == light_yellow.getRGB() && isSelected){ // light yellow == new Color(253, 255, 178)
                            c.setForeground(Color.BLUE);
                        }
                        //
                        if(!isSelected){
                            c.setForeground(Color.BLACK);
                        }
                        //
                        if(po.getColor().getRGB() == light_yellow.getRGB() && !isSelected){
                            c.setForeground(Color.BLACK);
                        }
                        //
                        return c;
                    }
                }
                
                return this;
            }
        });
        //
        jTable.repaint();
    }
    
    
    class PaintObject {
        
        private final int row;
        private final Color color;
        
        public PaintObject(int row, Color color) {
            this.row = row;
            this.color = color;
        }
        
        public int getRow() {
            return row;
        }
        
        public Color getColor() {
            return color;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompareRecipesMarkUp;
    private javax.swing.JButton jButtonCompareRecipesMarkUp2;
    private javax.swing.JButton jButtonCompareRecipesOrderByDynamic;
    protected javax.swing.JButton jButtonCompareRecipesPrint;
    private javax.swing.JButton jButtonCompareRecipesUndoMarkUp;
    private javax.swing.JComboBox jComboBoxCompareRecipesOrderBy1;
    private javax.swing.JComboBox jComboBoxCompareRecipesOrderby2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
