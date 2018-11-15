
package com.adv.java.database.dbs;

import com.adv.java.database.Database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlLite implements Database {

    private static final String database = "jdbc:sqlite:nypl.db";
    private static final String itemSchema = "id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT NOT NULL, title TEXT, itemLink TEXT";

    public SqlLite() throws SQLException {

        // Make sure the database and schema exist
        createNewDatabase();
        createNewTable("items", itemSchema);

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

    private static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(database);
        return conn;
    }

    @Override
    public boolean create(String uuid, String title, String itemLink) throws SQLException {
        System.out.println("Called: com.adv.java.database.dbs.SqlLite.create(String, String, String)");

        boolean returnValue = false;

        String sql = "INSERT INTO items(uuid, title, itemLink) VALUES(?, ?, ?)";
        PreparedStatement ps = null;

        Connection conn = connect();
        if (conn != null) {
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, uuid);
                ps.setString(2, title);
                ps.setString(3, itemLink);
                ps.executeUpdate();
                returnValue = true;
                ps.close();
            } catch (Exception e) {
                returnValue = false;
            }
        }
        conn.close();

        return returnValue;
    }

    @Override
    public String read(String uuid) throws SQLException {
        System.out.println("Called: com.adv.java.database.dbs.SqlLite.read(String)");

        String returnString = null;

        String sql = "SELECT * FROM items WHERE uuid = \"" + uuid + "\"";

        Connection conn = connect();
        if (conn != null) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Create a String to demonstrate the READ succeeded
                returnString = rs.getString("title") + " (" + rs.getString("itemLink") + ")";
            }
            rs.close();
            stmt.close();
        }
        conn.close();

        return returnString;
    }

    @Override
    public boolean update(String uuid, String title, String itemLink) {
        System.out.println("Called: com.adv.java.database.dbs.SqlLite.update(String, String, String)");

        // Not implemented for this assignment
        return false;
    }

    @Override
    public boolean delete(String uuid) {
        System.out.println("Called: com.adv.java.database.dbs.SqlLite.delete(String)");

        // Not implemented for this assignment
        return false;
    }

}
