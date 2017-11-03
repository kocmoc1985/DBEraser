/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.MC_RECIPE;
import forall.HelpA;

/**
 *
 * @author KOCMOC
 */
public class AdminTools extends javax.swing.JFrame {

    private MC_RECIPE mcRecipe;

    /**
     * Creates new form AdminTools
     */
    public AdminTools(MC_RECIPE mcRecipe) {
        initComponents();
        this.mcRecipe = mcRecipe;
        this.setTitle("Administration");
        this.setSize(400,300);
        this.setLocation(HelpA.position_window_in_center_of_the_screen(this));
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
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        jPanel1.setLayout(new java.awt.GridLayout(2, 2));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 102, 102));
        jButton3.setText("RECIPE GROUPS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("INGRED GROUPS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setText("MIXER INFO BASIC");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (mcRecipe.administrateRecipeGroups == null) {
            mcRecipe.administrateRecipeGroups = new AdministrateRecipeGroups(mcRecipe, mcRecipe.sql, mcRecipe.sql_additional);
            mcRecipe.administrateRecipeGroups.setVisible(true);
        } else {
            mcRecipe.administrateRecipeGroups.setVisible(true);
        }
        //
        setVisible(false);
        //
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (mcRecipe.administrateIngredGroups == null) {
            mcRecipe.administrateIngredGroups = new AdministrateIngredGroups(mcRecipe, mcRecipe.sql, mcRecipe.sql_additional);
            mcRecipe.administrateIngredGroups.setVisible(true);
        } else {
            mcRecipe.administrateIngredGroups.setVisible(true);
        }
        //
        setVisible(false);
        //
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (mcRecipe.administrateMixerInfoBasic == null) {
            mcRecipe.administrateMixerInfoBasic = new AdministrateMixerInfoBasic(mcRecipe, mcRecipe.sql, mcRecipe.sql_additional);
            mcRecipe.administrateMixerInfoBasic.setVisible(true);
        } else {
            mcRecipe.administrateMixerInfoBasic.setVisible(true);
        }
        //
        setVisible(false);
        //
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
