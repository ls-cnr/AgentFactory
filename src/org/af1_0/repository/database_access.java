/*
 * AbstractAccess.java
 *
 * Created on 24 settembre 2002, 11.53
 */

package org.af1_0.repository;

import java.sql.*;

/**
 *
 * @author  Luca
 */
public class database_access {
    protected static Connection con=null;
    protected static Statement stmt=null;
    protected String error;
    
    /** Creates a new instance of AbstractAccess */
    public database_access() {
        try {
            error = null;
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            error = new String("Can't connect to driver Jdbc-Odbc"+e.getMessage());
            System.out.println("[tool.db.ComponentAccess]"+"Can't connect to driver Jdbc-Odbc");
        }
    }
    
    public String getError() {
        return error;
    }
    
    public Statement openConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:odbc:agent_factory_repository","luca", "passi");
        stmt = con.createStatement();
        return stmt;
    }
    
    public void closeConnection() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            error = new String("Can't close db connection");
        }
    }
}
