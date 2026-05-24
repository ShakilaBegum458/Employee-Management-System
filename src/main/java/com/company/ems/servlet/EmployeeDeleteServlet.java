package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/employees/delete")
public class EmployeeDeleteServlet extends HttpServlet {

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

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) sb.append(line);

        @SuppressWarnings("unchecked")
        Map<String, Object> body = gson.fromJson(sb.toString(), Map.class);

        Map<String, Object> result = new HashMap<>();
        EmployeeDAO dao = new EmployeeDAO();

        if (Boolean.TRUE.equals(body.get("deleteAll"))) {
            boolean ok = dao.deleteAllEmployees();
            result.put("success", ok);
            result.put("message", ok
                    ? "All employees deleted."
                    : "Failed to delete employees.");
        } else {
            String id = (String) body.get("id");
            if (id == null || id.isBlank()) {
                resp.setStatus(400);
                result.put("success", false);
                result.put("message", "Employee ID is required.");
            } else {
                boolean ok = dao.deleteEmployee(id.trim());
                result.put("success", ok);
                result.put("message", ok
                        ? "Employee deleted."
                        : "Employee not found.");
                if (!ok) resp.setStatus(404);
            }
        }

        resp.getWriter().print(gson.toJson(result));
    }
}