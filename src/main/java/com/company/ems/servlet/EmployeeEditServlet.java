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

@WebServlet("/api/employees/edit")
public class EmployeeEditServlet extends HttpServlet {

    private final Gson gson = new Gson();

    // GET — fetch one employee for pre-filling edit form
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.setStatus(401);
            resp.getWriter().print("{\"error\":\"Unauthorized\"}");
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id    = req.getParameter("id");
        Employee emp = new EmployeeDAO().getEmployeeById(id);

        if (emp == null) {
            resp.setStatus(404);
            resp.getWriter().print("{\"error\":\"Employee not found\"}");
        } else {
            resp.getWriter().print(gson.toJson(emp));
        }
    }

    // POST — update employee
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
        boolean ok = new EmployeeDAO().updateEmployee(emp);

        if (ok) {
            result.put("success", true);
            result.put("message", "Employee updated successfully.");
        } else {
            resp.setStatus(500);
            result.put("success", false);
            result.put("message", "Failed to update employee.");
        }

        out.print(gson.toJson(result));
    }
}