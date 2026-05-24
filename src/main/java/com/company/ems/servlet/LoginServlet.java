package com.company.ems.servlet;

import com.company.ems.dao.AdminDAO;
import com.company.ems.model.Admin;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = resp.getWriter();

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String body = sb.toString();
        System.out.println("Login body: " + body);

        Map<String, Object> result = new HashMap<>();

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> bodyMap =
                    gson.fromJson(body, Map.class);

            String username = bodyMap.get("username");
            String password = bodyMap.get("password");

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            if (username == null || username.trim().isEmpty()
                    || password == null
                    || password.trim().isEmpty()) {

                resp.setStatus(400);
                result.put("success", false);
                result.put("message",
                        "Username and password required.");

            } else {

                AdminDAO dao   = new AdminDAO();
                Admin    admin = dao.validateLogin(
                        username.trim(), password.trim());

                System.out.println("Admin found: " + admin);

                if (admin != null) {
                    HttpSession session =
                            req.getSession(true);
                    session.setAttribute("admin",
                            admin.getUsername());
                    session.setMaxInactiveInterval(1800);

                    result.put("success", true);
                    result.put("message", "Login successful.");
                } else {
                    resp.setStatus(401);
                    result.put("success", false);
                    result.put("message",
                            "Invalid username or password.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            result.put("success", false);
            result.put("message", "Server error: " +
                    e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }
}