/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import Interfaces.SqlBasic;
//import com.microsoft.sqlserver.jdbc.SQLServerException;
import eraser.Eraser;
import exporterMills.Exporter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OBS! OBS! Use this with caution this requires "NetProcMonitor.jar" library
 *
 * @author Administrator
 */
public class Sql_C implements SqlBasic {

    private Connection connection;
    private Statement statement;
    private Statement statement_2;
    private PreparedStatement p_statement;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public boolean isConnected() {
        boolean closed;
        try {
            closed = statement.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(Sql_C.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        if (closed) {
            return false;
        } else {
            return true;
        }
    }

    public void connect(String host, String port, String databaseName, String userName, String password, boolean simpleStatement) throws SQLException, ClassNotFoundException {
        if (GP.SQL_LIBRARY_JTDS) {
            connect_tds(host, port, databaseName, userName, password, simpleStatement);
        } else {
            connect_mssql(host, port, databaseName, userName, password, simpleStatement);
        }
    }

    private void connect_tds(String host, String port, String databaseName, String userName, String password, boolean simpleStatement) throws SQLException, ClassNotFoundException {
        boolean connectionOk = true;

        //Class.forName("com.mysql.jdbc.Driver");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        //
        String port_ = "";
        if (port.isEmpty() == false) {
            port_ = ":" + port;
        }
        //
        GP.CONNECTION_STRING = "jdbc:jtds:sqlserver://" + host + port_ + ";"
                + "databaseName=" + databaseName + ";user=" + userName + ";password=" + password;//+ ";namedPipe=true" -> requires "domain=" paramter!
        //
        if (GP.JTDS_USE_NAMED_PIPES) {
            GP.CONNECTION_STRING += ";namedPipe=true;domain:" + GP.JTDS_DOMAIN_WORKGROUP;

        }
        //
        if (GP.JTDS_INSTANCE_PARAMETER.isEmpty() == false) {
            GP.CONNECTION_STRING += ";instance=" + GP.JTDS_INSTANCE_PARAMETER;
        }
        //
        logg_connection_string(GP.CONNECTION_STRING);
        //
        DriverManager.setLoginTimeout(GP.MSSQL_LOGIN_TIME_OUT);
        //
        connection = DriverManager.getConnection(GP.CONNECTION_STRING);
        //
        if (connectionOk) {
            if (simpleStatement == true) {
                statement = connection.createStatement();
                statement_2 = connection.createStatement();
            } else {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement_2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
        }
        //

        if (statement == null) {
            SimpleLoggerLight.logg("sql_conn.log", "Connection to: " + host + " / dbname: " + databaseName + " failed");
        }
    }

    private void logg_connection_string(String url) {
        if (GP.LOGG_CONNECTION_STRING) {
            SimpleLoggerLight.logg("connection_string.log", url);
        }
    }

    public void connect_mssql(String host, String port, String databaseName, String userName, String password, boolean simpleStatement) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String port_ = "";
        //
        if (port.isEmpty() == false) {
            port_ = ":" + port;
        }
        //
        GP.CONNECTION_STRING = "jdbc:sqlserver://" + host + port_ + ";"
                + "databaseName=" + databaseName + ";user=" + userName + ";password=" + password;

        //
        logg_connection_string(GP.CONNECTION_STRING);
        //
        //For Trelleborgs connection it seems to be important!!
        DriverManager.setLoginTimeout(GP.MSSQL_LOGIN_TIME_OUT);
        //
        connection = DriverManager.getConnection(GP.CONNECTION_STRING);
        //
        if (simpleStatement == true) {
            statement = connection.createStatement();
            statement_2 = connection.createStatement();
        } else {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement_2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
    }

//    
    public void connectMySql(String host, String port, String databaseName, String userName, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://195.178.232.239:3306/m09k2847","m09k2847","636363");
            //
            GP.CONNECTION_STRING = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            //
            logg_connection_string(GP.CONNECTION_STRING);
            //
            connection = DriverManager.getConnection(GP.CONNECTION_STRING, userName, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: " + e1);
        }
    }

    /**
     * For connecting with ODBC. Fits for ACCESS databases also!!
     *
     * @param user
     * @param pass
     * @param odbc
     * @throws SQLException
     */
    public void connectODBC(String user, String pass, String odbc, boolean simpleStatement) throws ClassNotFoundException, SQLException {
        //
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        //
        GP.CONNECTION_STRING = "jdbc:odbc:" + odbc;
        //
        logg_connection_string(GP.CONNECTION_STRING);
        //
        if (simpleStatement == true) {
            statement = connection.createStatement();
        } else {
            connection = DriverManager.getConnection(GP.CONNECTION_STRING, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        //
    }
    
    public void connectMDB(String user, String pass, String pathToMdbFile, boolean simpleStatement) throws SQLException, ClassNotFoundException {
        //
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        GP.CONNECTION_STRING = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=" + pathToMdbFile;
        //
        logg_connection_string(GP.CONNECTION_STRING);
        //
        connection = DriverManager.getConnection(GP.CONNECTION_STRING, user, pass);
        //
        if (simpleStatement == true) {
            statement = connection.createStatement();
            statement_2 = connection.createStatement();
        } else {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement_2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
    }

    public void prepareStatement(String q) {
        try {
            p_statement = connection.prepareStatement(q);
        } catch (SQLException ex) {
            Logger.getLogger(Sql_C.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PreparedStatement getPreparedStatement() {
        return p_statement;
    }

    public int executeUpdatePreparedStatement() throws SQLException {
        return p_statement.executeUpdate();
    }

    public void loggSqlExceptionWithQuerry(String logFile, SQLException ex, String query) {
        if (ex.toString().contains("String or binary data would be truncated")) {
            SimpleLoggerLight.logg(logFile, "!IMPORTANT! Exeption: " + ex.toString() + "\nQuery: " + query);
        } else {
            SimpleLoggerLight.logg(logFile, "Exeption: " + ex.toString() + "\nQuery: " + query);
        }
    }

    @Override
    public ResultSet execute(String sql) throws SQLException {
        if (statement.execute((String) sql)) {
            return statement.getResultSet();
        }
        return null;
    }

    public ResultSet execute_2(String sql) throws SQLException {
        if (statement_2.execute(sql)) {
            return statement_2.getResultSet();
        }
        return null;
    }

    @Override
    public int update(String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}
