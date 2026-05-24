package com.company.ems.servlet;

import com.company.ems.dao.EmployeeDAO;
import com.company.ems.model.Employee;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/export/pdf")
public class PdfExportServlet extends HttpServlet {

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

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition",
                "attachment; filename=\"employees.pdf\"");

        List<Employee> employees =
                new EmployeeDAO().getAllEmployees();

        try {
            Document document = new Document(
                    PageSize.A4.rotate());
            PdfWriter.getInstance(document,
                    resp.getOutputStream());
            document.open();

            Font titleFont = new Font(
                Font.FontFamily.HELVETICA, 16,
                Font.BOLD, BaseColor.WHITE);

            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);
            PdfPCell titleCell = new PdfPCell(
                new Phrase("Employee Management System",
                           titleFont));
            titleCell.setBackgroundColor(
                new BaseColor(15, 23, 42));
            titleCell.setPadding(12);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(
                Element.ALIGN_CENTER);
            titleTable.addCell(titleCell);
            document.add(titleTable);

            document.add(new Paragraph(" "));

            Font subFont = new Font(
                Font.FontFamily.HELVETICA, 10,
                Font.NORMAL,
                new BaseColor(100, 116, 139));
            Paragraph sub = new Paragraph(
                "Total Employees: " + employees.size(),
                subFont);
            sub.setAlignment(Element.ALIGN_RIGHT);
            document.add(sub);
            document.add(new Paragraph(" "));

         
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{
                2f, 2f, 2f, 2f,
                3f, 2f, 2.5f, 2.5f, 2f
            });

 
            String[] headers = {
                "Emp ID", "First Name", "Last Name",
                "DOB", "Email", "Phone",
                "Department", "Designation", "Salary"
            };

            Font headerFont = new Font(
                Font.FontFamily.HELVETICA, 9,
                Font.BOLD, BaseColor.WHITE);

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(
                    new Phrase(h, headerFont));
                cell.setBackgroundColor(
                    new BaseColor(30, 41, 59));
                cell.setPadding(6);
                cell.setHorizontalAlignment(
                    Element.ALIGN_CENTER);
                table.addCell(cell);
            }

   
            Font dataFont = new Font(
                Font.FontFamily.HELVETICA, 8,
                Font.NORMAL,
                new BaseColor(30, 41, 59));

            boolean alternate = false;
            for (Employee e : employees) {
                BaseColor rowColor = alternate
                    ? new BaseColor(240, 247, 255)
                    : BaseColor.WHITE;
                alternate = !alternate;

                String[] values = {
                    e.getEmployee_id(),
                    e.getFirst_name(),
                    e.getLast_name(),
                    e.getDob(),
                    e.getEmail(),
                    e.getPhone(),
                    e.getDepartment(),
                    e.getDesignation(),
                    String.valueOf(e.getSalary())
                };

                for (String v : values) {
                    PdfPCell cell = new PdfPCell(
                        new Phrase(
                            v != null ? v : "",
                            dataFont));
                    cell.setBackgroundColor(rowColor);
                    cell.setPadding(5);
                    table.addCell(cell);
                }
            }

            document.add(table);

            document.add(new Paragraph(" "));
            Font footerFont = new Font(
                Font.FontFamily.HELVETICA, 8,
                Font.ITALIC,
                new BaseColor(100, 116, 139));
            Paragraph footer = new Paragraph(
                "Generated by EMS — Employee " +
                "Management System",
                footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}