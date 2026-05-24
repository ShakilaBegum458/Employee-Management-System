package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.company.ems.model.Employee;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/employees/add")
public class EmployeeAddServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.setStatus(401);
            resp.getWriter().print("{\"error\":\"Unauthorized\"}");
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) sb.append(line);
        Employee emp = gson.fromJson(sb.toString(), Employee.class);

        Map<String, Object> result = new HashMap<>();
        EmployeeDAO dao = new EmployeeDAO();

        if (emp.getEmployee_id() == null || emp.getEmployee_id().isBlank()) {
            resp.setStatus(400);
            result.put("success", false);
            result.put("message", "Employee ID is required.");
        } else if (dao.employeeIdExists(emp.getEmployee_id().trim())) {
            resp.setStatus(409);
            result.put("success", false);
            result.put("message", "Employee ID already exists.");
        } else {
            emp.setEmployee_id(emp.getEmployee_id().trim());
            boolean ok = dao.addEmployee(emp);
            if (ok) {
                result.put("success", true);
                result.put("message", "Employee added successfully.");
            } else {
                resp.setStatus(500);
                result.put("success", false);
                result.put("message", "Failed to add employee.");
            }
        }

        out.print(gson.toJson(result));
    }
}