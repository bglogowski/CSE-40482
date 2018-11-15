package com.adv.java.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlLite {
    
    private static final String database = "jdbc:sqlite:test.db";
    
    public static void main(String[] args) throws SQLException {
        createNewDatabase();
        

    }

    
    private static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(database);
        return conn;
    }

    private static void createNewDatabase() throws SQLException {

        Connection conn = connect();
        if (conn != null) {

            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Loaded Database driver: " + meta.getDriverName());

            conn.close();

        }

    }
    
    private static void createNewTable(String tableName, String schema) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + schema + ");";

        Connection conn = connect();
        if (conn != null) {

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();

        }

    }



}
