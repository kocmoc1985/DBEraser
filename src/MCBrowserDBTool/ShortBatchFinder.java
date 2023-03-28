/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import static MCBrowserDBTool.LostPointsFinder.ids_to_remove;
import static MCBrowserDBTool.LostPointsFinder.output;
import forall.HelpA;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class ShortBatchFinder extends LostPointsFinder {

    private int length_less_then = 200;

    public ShortBatchFinder() {
        this.setTitle("ShortBatchFinder");
    }

    @Override
    public void mc_batchinfo_loop() {
        //
        output("Started");
        //
//        String q = String.format("SELECT * FROM mc_batchinfo where PROD_LENGTH < '%s'", length_less_then);
        //
        String q = String.format("SELECT * FROM mc_batchinfo where PROD_DATE > '%s' AND PROD_DATE < '%s' AND PROD_LENGTH < '%s' ORDER BY PROD_DATE DESC", date_more_then, date_less_then, length_less_then);
        //
        try {
            //
            ResultSet rs = sql.execute(q);
            //
            while (rs.next()) {
                //
                int id = rs.getInt("ID");
                //
                int length = rs.getInt("PROD_LENGTH");
                String recipe = rs.getString("RECIPEID");
                String orderName = rs.getString(ORDER_NAME_COLUMN);
                String batch = rs.getString("BATCHNO");
                String date = rs.getString("PROD_DATE");
                //
                String str = recipe + " / " + orderName + " / " + batch + " date: " + date + " length: " + length + " id: " + id;
                LostPointsFinder.output(str);
                LostPointsFinder.ids_to_remove.add(id);
                //
            }
            //
            prepare_delete_sql();
            //
            output("\n\nEnded");
            //
        } catch (SQLException ex) {
            Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void prepare_delete_sql() {
        //
        HashSet<Integer> set = ids_to_remove;
        String output = "";
        //
        for (Iterator<Integer> iterator = set.iterator(); iterator.hasNext();) {
            Integer id = iterator.next();
            output += id + ",";
        }
        //
        if (output.isEmpty()) {
            return;
        }
        //
        output = output.substring(0, output.length() - 1);
        //
        output("\n\n delete from mc_batchinfo where ID in (" + output + ")");
        //
        output("\n\n delete from mc_trend where IDBATCHINFO in (" + output + ")");
    }

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
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        if (HelpA.runningInNetBeans() == false) {
            HelpA.err_output_to_file();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShortBatchFinder().setVisible(true);
            }
        });
    }

}
