/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private Pattern pattern;
    private final Font FONT_1 = new Font("Arial", Font.BOLD, 22);
    public static int OK_RESULT = 0;
    public static int WRONG_FORMAT_RESULT = 1;
    public static int ALREADY_EXIST_RESULT = 2;
    public int RESULT;
    private boolean validated = false;
    //
    private String ERR_MSG_1 = "Exist already";
    private String ERR_MSG_2 = "Wrong format";

    public TextFieldCheck(SqlBasicLocal sql, String q, String regex, int columns) {
        this.sql = sql;
        this.entryExistQuery = q;
        this.regex = regex;
        setColumns(columns);
        initOther();
    }

    public TextFieldCheck(String initialValue, Pattern regexPattern, int columns) {
        this.pattern = regexPattern;
        setColumns(columns);
        initOther();
        if (initialValue != null && initialValue.isEmpty() == false) {
            validated = true;
        }
        this.setText(initialValue);
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
            JOptionPane.showMessageDialog(null, ERR_MSG_1);
            return null;
        } else if (RESULT == WRONG_FORMAT_RESULT) {
            JOptionPane.showMessageDialog(null, ERR_MSG_2);
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
        if (validate_(str)) {
            //
            prepareQuery(entryExistQuery, str, null);
            //
            setToolTipText(null);
            setBackground(Color.white);
            //
            if (isPresent()) {
                setToolTipText(ERR_MSG_1);
                setForeground(Color.BLACK);
                setBackground(Color.ORANGE);
                RESULT = ALREADY_EXIST_RESULT;
            } else {
                setToolTipText(null);
                validated = true;
                setForeground(Color.BLACK);
                setBackground(Color.green);
                RESULT = OK_RESULT;
            }
            //
        } else {
            setToolTipText(ERR_MSG_2);
            validated = false;
            setBackground(Color.white);
            setForeground(Color.RED);
            RESULT = WRONG_FORMAT_RESULT;
        }
    }

    public boolean getValidated() {
        return validated;
    }

    private boolean validate_(String strToValidate) {
        if (pattern != null) {
            Matcher matcher = pattern.matcher(strToValidate);
            return matcher.matches();
        } else {
            return strToValidate.matches(regex);
        }
    }

    private void prepareQuery(String q, String val_1, String val_2) {
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
            Logger.getLogger(HelpA_.class.getName()).log(Level.SEVERE, null, ex);
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
