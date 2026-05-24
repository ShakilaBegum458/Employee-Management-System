package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.company.ems.model.Employee;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/employees")
public class EmployeeListServlet extends HttpServlet {

    private final Gson gson = new Gson();

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

        int page = 1, size = 10;
        try { page = Integer.parseInt(req.getParameter("page")); }
        catch (Exception ignored) {}
        try { size = Integer.parseInt(req.getParameter("size")); }
        catch (Exception ignored) {}

        List<Employee> all  = new EmployeeDAO().getAllEmployees();
        int total   = all.size();
        int fromIdx = Math.min((page - 1) * size, total);
        int toIdx   = Math.min(fromIdx + size, total);

        Map<String, Object> response = new HashMap<>();
        response.put("data",       all.subList(fromIdx, toIdx));
        response.put("total",      total);
        response.put("page",       page);
        response.put("size",       size);
        response.put("totalPages", (int) Math.ceil((double) total / size));

        resp.getWriter().print(gson.toJson(response));
    }
}