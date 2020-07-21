/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import MyObjectTable.OutPut;
import MCCompound.SQL_B;
import MyObjectTable.Common_Table_Config;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author mcab
 */
public class Run {
    
    public static Table table;

    public static void main(String[] args) {
        JFrame table_container_frame = new JFrame("test");
        table_container_frame.setLayout(new BorderLayout());
        table_container_frame.setSize(new Dimension(600, 400));
        //
        ShowMessage sm = new OutPut();
        //
        final TableBuilderBasic tableBuilder = new TableBuilderBasic(sm, Common_Table_Config.getHeaders_test(),1,null);
        //
        try {
            table = tableBuilder.buildTable(SQL_B.test_1_querry());
        } catch (SQLException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            sm.showMessage(ex.toString());
        }
        //
        //
        JButton button = new JButton("Test");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                table.deleteRowGraphic(table.getSelectedRow());
//                try {
//                    tableBuilder.getSql().execute(SQL_DELETE.test_1_delete_querry(table.getSelectedDatabaseId()));
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
        //
        
        table_container_frame.add(table.getTable());
        table_container_frame.setVisible(true);
        //
        //
        //
        JFrame test_frame = new JFrame("Control_test");
        test_frame.setLayout(new BorderLayout());
        test_frame.setSize(new Dimension(100, 100));
        test_frame.add(button);
        test_frame.setVisible(true);
    }
}
