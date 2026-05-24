package com.company.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/ems_db" +
        "?useSSL=false&serverTimezone=UTC" +
        "&allowPublicKeyRetrieval=true";

    private static final String USER = "root";
    private static final String PASS = "Shakila@2005"; 

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                URL, USER, PASS);
        System.out.println("DB Connection successful!");
        return conn;
    }
}