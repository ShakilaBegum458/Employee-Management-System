package com.company.ems.dao;

import com.company.ems.model.Admin;
import com.company.ems.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    public Admin validateLogin(String username, String password) {

        System.out.println("Validating: " +
                username + " / " + password);

        String sql = "SELECT id, username, password " +
                     "FROM admin " +
                     "WHERE username = ? AND password = ?";

        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("DB connected!");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Admin found in DB!");
                Admin admin = new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                rs.close();
                ps.close();
                conn.close();
                return admin;
            } else {
                System.out.println("No admin found in DB!");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}