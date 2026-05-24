package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/employees/search")
public class EmployeeSearchServlet extends HttpServlet {

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

        String q = req.getParameter("q");
        if (q == null) q = "";

        List<?> results = new EmployeeDAO().searchEmployees(q.trim());
        resp.getWriter().print(gson.toJson(results));
    }
}