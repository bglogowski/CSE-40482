import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransactionRollbackException;
import java.sql.Statement;

public class Lesson7Database {

    public static void main(String[] args) throws IOException, SQLException {
        
        String url = "jdbc:derby://localhost:1527/COREJAVA;create=true";

        System.out.println("Database Connection info: " + url);
        
        // Connect to the database
        Connection conn = null;
        try {
            System.out.print("Connecting to the database: ");
            conn = DriverManager.getConnection(url);
            System.out.println("Done.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a query statement
        Statement stmt = conn.createStatement();

        // Read the text file (this assumes it's in the CWD)
        System.out.print("Populating the database: ");
        Files.lines(Paths.get("Lessons.sql")).forEach(t -> {
            try {
                // Put the data into the database
                if (t.toUpperCase().startsWith("INSERT")) {
                    // If the query is an INSERT, use the executeUpdate method
                    stmt.executeUpdate(t.replaceAll("Lessons", "Lessons VALUES").replaceAll(";$", ""));
                } else {
                    try {
                        // For all other queries, use the execute method
                        stmt.execute(t.replaceAll(";$", ""));
                    } catch (SQLTransactionRollbackException | SQLSyntaxErrorException x) {
                        // Do nothing, these are expected and can be ignored.
                    }
                }
            } catch (SQLException e) {
                // These are not expected and should not be ignored.
                e.printStackTrace();
            }
        });    
        System.out.println("Done.");

        // Read the data back from the database
        ResultSet results = null;
        try {
            results = stmt.executeQuery("SELECT * FROM Lessons");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Get the metadata from the result
        ResultSetMetaData rsmd = null;
        try {
            rsmd = results.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Integer numberCols = 0;
        try {
            numberCols = rsmd.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Query of Lessons table results:");
        
        // Print the column names
        for (int i = 1; i <= numberCols; i++) {
            if (i < numberCols)
                try {
                    System.out.print(rsmd.getColumnLabel(i) + "\t");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            else
                try {
                    System.out.print(rsmd.getColumnLabel(i) + "\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        // Print some dashes
        System.out.println(new String(new char[40]).replace("\0", "-"));

        // Print the data
        try {
            while (results.next())
                System.out.println(results.getInt(1) + "\t\t" + results.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close open file handles
        try {
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
