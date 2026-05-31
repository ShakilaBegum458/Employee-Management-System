# Employee Management System
A full-stack Java web application for
managing employee records.

## Tech Stack
- Frontend: HTML, CSS, JavaScript
- Backend: Java Servlets (Apache Tomcat 9)
- Database: MySQL 8.0
- JSON Library: Gson 2.10

## Features
- Splash screen with animation
- Admin login system
- Add / Edit / Delete employees
- Search employees by ID, Name, DOB
- Export to CSV and PDF
- Dark mode toggle
- Responsive design for Mobile and Desktop
- Pagination

## Project Structure
EMS/
├── src/main/java/com/company/ems/
│   ├── util/DBConnection.java
│   ├── model/Admin.java
│   ├── model/Employee.java
│   ├── dao/AdminDAO.java
│   ├── dao/EmployeeDAO.java
│   └── servlet/
│       ├── LoginServlet.java
│       ├── LogoutServlet.java
│       ├── EmployeeListServlet.java
│       ├── EmployeeAddServlet.java
│       ├── EmployeeEditServlet.java
│       ├── EmployeeDeleteServlet.java
│       ├── EmployeeSearchServlet.java
│       ├── ExportServlet.java
│       └── PdfExportServlet.java
├── src/main/webapp/
│   ├── index.html
│   ├── dashboard.html
│   ├── add-employee.html
│   ├── edit-employee.html
│   ├── css/style.css
│   └── js/
│       ├── api.js
│       ├── login.js
│       ├── employee.js
│       ├── add-employee.js
│       ├── edit-employee.js
│       ├── form-validation.js
│       └── darkmode.js
└── database/
    └── ems_schema.sql

## Setup Instructions
# Step 1 - Database
Run this in MySQL Workbench:
ems_db

# Step 2 - Configure Database
Open DBConnection.java:
- URL: jdbc:mysql://localhost:3306/ems_db
- USER: root
- PASS: my_sql password

# Step 3 - Add JAR Files
Place in WEB-INF/lib/:
- gson-2.10.jar
- mysql-connector-j.jar
- itextpdf-5.5.13.jar

# Step 4 - Deploy
1. Import in Eclipse
2. Add Tomcat 9 server
3. Run on Server
4. Open https://ems-app-z19k.onrender.com

# Login Credentials
- Username: user
- Password: shaki@123

# API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/login | Admin login |
| GET | /api/logout | Logout |
| GET | /api/employees | List employees |
| POST | /api/employees/add | Add employee |
| GET | /api/employees/edit | Get employee |
| POST | /api/employees/edit | Update employee |
| POST | /api/employees/delete | Delete employee |
| GET | /api/employees/search | Search |
| GET | /api/export/csv | Export CSV |
| GET | /api/export/pdf | Export PDF |

# Author
Shakila Begum
