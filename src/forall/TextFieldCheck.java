/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import ClientRecorder.Sql;
import MCRecipe.SQL_A;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class TextFieldCheck extends JTextField implements KeyListener {

    public SqlBasicLocal sql;
    private String entryExistQuery;
    private String regex;
    private final Font FONT_1 = new Font("Arial", Font.BOLD, 16);
    public static int OK_RESULT = 0;
    public static int WRONG_FORMAT_RESULT = 1;
    public static int ALREADY_EXIST_RESULT = 2;
    public int RESULT;

    public TextFieldCheck(SqlBasicLocal sql, String q, String regex, int columns) {
        this.sql = sql;
        this.entryExistQuery = q;
        this.regex = regex;
        setColumns(columns);
        initOther();
    }

    public int getRESULT() {
        return RESULT;
    }

    private void initOther() {
        this.addKeyListener(this);
        this.setFont(FONT_1);
    }

    @Override
    public String getText() {
        if (RESULT == OK_RESULT) {
            return super.getText();
        } else if (RESULT == ALREADY_EXIST_RESULT) {
            JOptionPane.showMessageDialog(null, "Exist already");
            return null;
        } else if (RESULT == WRONG_FORMAT_RESULT) {
            JOptionPane.showMessageDialog(null, "Wrong format");
            return null;
        }
        //
        return null;

    }

    public String getText_() {
        return super.getText();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //
        String str = getText_();
        //
        if (str.matches(regex)) {
            //
            prepareQuery(entryExistQuery, str, null);
            //
            if (isPresent()) {
                setForeground(Color.ORANGE);
                RESULT = ALREADY_EXIST_RESULT;
            } else {
                setForeground(Color.GREEN);
                RESULT = OK_RESULT;
            }
            //
        } else {
            setForeground(Color.RED);
            RESULT = WRONG_FORMAT_RESULT;
        }
    }

    public void prepareQuery(String q, String val_1, String val_2) {
        //
        if (sql == null) {
            return;
        }
        //
        try {
            sql.prepareStatement(q);
            sql.getPreparedStatement().setString(1, val_1);
        } catch (SQLException ex) {
            Logger.getLogger(TextFieldCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isPresent() {
        if (sql == null) {
            return false;
        } else {
            return entryExistsSql(sql);
        }
    }
    
    public boolean entryExistsSql(SqlBasicLocal sql) {
        try {
            //
            ResultSet rs = sql.getPreparedStatement().executeQuery();
            //
            if (rs.next()) {
                return true;
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        return false;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    
}
