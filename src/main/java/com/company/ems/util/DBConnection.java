package com.company.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://mysql-xxx.aivencloud.com:3306/defaultdb" +
        "?useSSL=true" +
        "&serverTimezone=UTC" +
        "&allowPublicKeyRetrieval=true";

    private static final String USER = "avnadmin";
    private static final String PASS = "AVNS_20M5K8GLGCWtrj46qiR";

    public static Connection getConnection()
            throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                URL, USER, PASS);
    }
}
