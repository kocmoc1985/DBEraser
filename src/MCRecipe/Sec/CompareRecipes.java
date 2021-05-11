/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.MC_RECIPE;
import MCRecipe.RecipeDetailed_;
import MCRecipe.SQL_A_;
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
        String q = SQL_A_.compareRecipeDropTable(user);
        //
        try {
            sql.execute(q, mc_recipe);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CompareRecipes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean addToCompare(String recipeCode, String release, String user) {
        //
        String q = SQL_A_.compareRecipesAddToCompare(PROC.PROC_01, recipeCode, release, user);
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

    private void fillTable(String orderByCriteria) {
        //
        JTable table = jTable1;
        //
        String q1 = SQL_A_.compareRecipesShow(HelpA.updatedBy(), orderByCriteria);
        //
        try {
            //
            ResultSet rs = sql.execute(q1, mc_recipe);
            //
            HelpA.build_table_common_with_rounding(rs, q1, table, "%2.2f",
                    new String[]{RecipeDetailed_.t4_id, RecipeDetailed_.t4_loadingSeq, RecipeDetailed_.t4_material, RecipeDetailed_.t4_release},
                    new String[]{RecipeDetailed_.t4_density},
                    new String[]{});
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumns(table);
        //
    }

    /**
     * 2021-05-10 [#COMPARE-RECIPES-2021#]
     *
     * @param toShowNr
     */
    public void fillTable_b(String toShowNr,String markUpColName) {
        //
        JTable table = jTable1;
        //
        String q1 = SQL_A_.compareRecipesShow__b(PROC.PROC_88, toShowNr, HelpA.updatedBy() + "_C");
        //
        try {
            //
            ResultSet rs = HelpA.runProcedureResultSetReturn(sql.getConnection(), q1);
            //
            table = HelpA.build_table_common_return(rs, table);
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        HelpA.tableRowSetBold__first_and_last_rows(table);
        markUpRecipesB(table, markUpColName, true);
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

    private void undoAllMarkUps(JTable table) {
        unpaintAllRows_a(table);
    }

    private void markUpRecipesB(JTable table, String colName, boolean switchGetColor) {
        paint_selected_rows_b(buildMarkUpListB(table, colName, switchGetColor), table);
    }

    private LinkedList<Object> buildMarkUpListB(JTable table, String colName, boolean switchGetColor) {
        //
        LinkedList<Color> colorList = new LinkedList<>();
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
            //
            String rName = HelpA.getValueGivenRow(table, i, colName);
            //
            if (colMap.containsKey(rName) == false) {
                //
                Color color = Color.RED;
                //
                if (switchGetColor == false) {
                    color = colorList.pollFirst();
                } else {
                    color = switchGetColor();
                }
                // 
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

    private static final LinkedList<Color> colorListSwitch = new LinkedList<>();

    static {
        colorListSwitch.add(new Color(253, 193, 213)); // light pink 
        colorListSwitch.add(new Color(202, 255, 209)); // light green
    }
    private static int colorSwitch = 1;

    private static Color switchGetColor() {
        //
        colorSwitch++;
        //
        if (colorSwitch % 2 == 0) {
            return colorListSwitch.get(0);
        } else {
            return colorListSwitch.get(1);
        }
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

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

        jButtonCompareRecipesOrderByDynamic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButtonCompareRecipesOrderByDynamic.setToolTipText("Order by chosen parameters");
        jButtonCompareRecipesOrderByDynamic.setPreferredSize(new java.awt.Dimension(60, 50));
        jButtonCompareRecipesOrderByDynamic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesOrderByDynamicActionPerformed(evt);
            }
        });

        jPanel2.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jButtonCompareRecipesUndoMarkUp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCompareRecipesUndoMarkUp.setText("A");
        jButtonCompareRecipesUndoMarkUp.setToolTipText("Mark up recipes when mixed up");
        jButtonCompareRecipesUndoMarkUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesUndoMarkUpActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesUndoMarkUp);

        jButtonCompareRecipesMarkUp2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCompareRecipesMarkUp2.setText("B");
        jButtonCompareRecipesMarkUp2.setToolTipText("Mark up type 2");
        jButtonCompareRecipesMarkUp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesMarkUp2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesMarkUp2);

        jButtonCompareRecipesMarkUp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCompareRecipesMarkUp.setText("C");
        jButtonCompareRecipesMarkUp.setToolTipText("Mark up type 1");
        jButtonCompareRecipesMarkUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesMarkUpActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesMarkUp);

        jButtonCompareRecipesPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButtonCompareRecipesPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareRecipesPrintActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompareRecipesPrint);

        jButton1.setText("1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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
                        .addComponent(jButtonCompareRecipesOrderByDynamic, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonCompareRecipesOrderByDynamic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxCompareRecipesOrderBy1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxCompareRecipesOrderby2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        fillTable("");
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
        fillTable("");
        markUpRecipesB(jTable1, "RecipeName", false);
    }//GEN-LAST:event_jButtonCompareRecipesMarkUp2ActionPerformed

    private void jButtonCompareRecipesUndoMarkUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareRecipesUndoMarkUpActionPerformed
        undoAllMarkUps(jTable1);
    }//GEN-LAST:event_jButtonCompareRecipesUndoMarkUpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fillTable_b("1","Material");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fillTable_b("2","Phase");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        fillTable_b("3",null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        fillTable_b("4",null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        fillTable_b("5",null);
    }//GEN-LAST:event_jButton5ActionPerformed

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
                        if (po.getColor().getRGB() == light_yellow.getRGB() && isSelected) { // light yellow == new Color(253, 255, 178)
                            c.setForeground(Color.BLUE);
                        }
                        //
                        if (!isSelected) {
                            c.setForeground(Color.BLACK);
                        }
                        //
                        if (po.getColor().getRGB() == light_yellow.getRGB() && !isSelected) {
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
