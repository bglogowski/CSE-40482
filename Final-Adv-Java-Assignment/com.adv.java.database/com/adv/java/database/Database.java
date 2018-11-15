package com.adv.java.database;

import java.sql.SQLException;


public interface Database {
    
    static Database newInstance() throws SQLException {
        System.out.println("Called: com.adv.java.database.Database.newInstance()");
        return new com.adv.java.database.dbs.SqlLite();
    }

    boolean create(String uuid, String title, String itemLink) throws SQLException;

    String read(String uuid) throws SQLException;
    
    boolean update(String uuid, String title, String itemLink);
    
    boolean delete(String uuid);


}
