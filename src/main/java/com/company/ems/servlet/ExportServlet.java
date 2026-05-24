package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.company.ems.model.Employee;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/export/csv")
public class ExportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null ||
                session.getAttribute("admin") == null) {
            resp.setStatus(401);
            return;
        }

        resp.setContentType("text/csv");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Disposition",
                "attachment; filename=\"employees.csv\"");

        List<Employee> employees =
                new EmployeeDAO().getAllEmployees();

        PrintWriter writer = resp.getWriter();

        writer.println(
            "Employee ID,First Name,Last Name," +
            "Date of Birth,Email,Phone," +
            "Department,Designation," +
            "Date of Joining,Salary,Address"
        );

        for (Employee e : employees) {
            writer.println(
                csvField(e.getEmployee_id()) + "," +
                csvField(e.getFirst_name())  + "," +
                csvField(e.getLast_name())   + "," +
                csvField(e.getDob())         + "," +
                csvField(e.getEmail())       + "," +
                csvField(e.getPhone())       + "," +
                csvField(e.getDepartment())  + "," +
                csvField(e.getDesignation()) + "," +
                csvField(e.getDate_of_joining()) + "," +
                e.getSalary()               + "," +
                csvField(e.getAddress())
            );
        }

        writer.flush();
    }

    private String csvField(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}