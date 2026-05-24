package com.company.ems.dao;

import com.company.ems.model.Employee;
import com.company.ems.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployee_id(rs.getString("employee_id"));
        e.setFirst_name(rs.getString("first_name"));
        e.setLast_name(rs.getString("last_name"));
        e.setDob(rs.getString("dob"));
        e.setEmail(rs.getString("email"));
        e.setPhone(rs.getString("phone"));
        e.setDepartment(rs.getString("department"));
        e.setDesignation(rs.getString("designation"));
        e.setDate_of_joining(rs.getString("date_of_joining"));
        e.setSalary(rs.getDouble("salary"));
        e.setAddress(rs.getString("address"));
        return e;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY employee_id";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employee getEmployeeById(String id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = mapRow(rs);
                rs.close();
                ps.close();
                conn.close();
                return emp;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> searchEmployees(String query) {
        List<Employee> list = new ArrayList<>();
        String q = "%" + query + "%";
        String sql = "SELECT * FROM employees WHERE "
                + "employee_id LIKE ? OR "
                + "first_name LIKE ? OR "
                + "last_name LIKE ? OR "
                + "dob LIKE ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);
            ps.setString(4, q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addEmployee(Employee e) {
        String sql = "INSERT INTO employees "
                + "(employee_id, first_name, last_name, dob, "
                + "email, phone, department, designation, "
                + "date_of_joining, salary, address) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,  e.getEmployee_id());
            ps.setString(2,  e.getFirst_name());
            ps.setString(3,  e.getLast_name());
            ps.setString(4,  e.getDob());
            ps.setString(5,  e.getEmail());
            ps.setString(6,  e.getPhone());
            ps.setString(7,  e.getDepartment());
            ps.setString(8,  e.getDesignation());
            ps.setString(9,  e.getDate_of_joining());
            ps.setDouble(10, e.getSalary());
            ps.setString(11, e.getAddress());
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(Employee e) {
        String sql = "UPDATE employees SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "dob = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "department = ?, "
                + "designation = ?, "
                + "date_of_joining = ?, "
                + "salary = ?, "
                + "address = ? "
                + "WHERE employee_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,  e.getFirst_name());
            ps.setString(2,  e.getLast_name());
            ps.setString(3,  e.getDob());
            ps.setString(4,  e.getEmail());
            ps.setString(5,  e.getPhone());
            ps.setString(6,  e.getDepartment());
            ps.setString(7,  e.getDesignation());
            ps.setString(8,  e.getDate_of_joining());
            ps.setDouble(9,  e.getSalary());
            ps.setString(10, e.getAddress());
            ps.setString(11, e.getEmployee_id());
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(String id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllEmployees() {
        String sql = "DELETE FROM employees";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean employeeIdExists(String id) {
        String sql = "SELECT 1 FROM employees WHERE employee_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            ps.close();
            conn.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}