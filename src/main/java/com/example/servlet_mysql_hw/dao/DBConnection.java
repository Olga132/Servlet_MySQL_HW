package com.example.servlet_mysql_hw.dao;

import java.sql.*;
import java.util.logging.Logger;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/monetary_transactions";
    private static String userName = "root";
    private static String password = "j3tZa8N+";

    static Logger logger = Logger.getLogger(DBConnection.class.getName());


    public static Connection openDBConnection() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, userName, password);
            if (conn != null) logger.info("Connection to DB Successful!");
            return conn;
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
