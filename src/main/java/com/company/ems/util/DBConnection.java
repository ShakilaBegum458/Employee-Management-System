package com.company.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        System.getenv("DB_URL") != null ?
        System.getenv("DB_URL") :
        "jdbc:mysql://localhost:3306/ems_db" +
        "?useSSL=false" +
        "&serverTimezone=UTC" +
        "&allowPublicKeyRetrieval=true";

    private static final String USER =
        System.getenv("DB_USER") != null ?
        System.getenv("DB_USER") : "root";

    private static final String PASS =
        System.getenv("DB_PASSWORD") != null ?
        System.getenv("DB_PASSWORD") :
        "your mysql password";

    public static Connection getConnection()
            throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                URL, USER, PASS);
    }
}
