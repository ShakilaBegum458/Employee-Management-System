package com.company.ems.model;

public class Employee {

    private String employee_id;
    private String first_name;
    private String last_name;
    private String dob;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private String date_of_joining;
    private double salary;
    private String address;

    public Employee() {}

    public Employee(
            String employee_id,
            String first_name,
            String last_name,
            String dob,
            String email,
            String phone,
            String department,
            String designation,
            String date_of_joining,
            double salary,
            String address) {

        this.employee_id     = employee_id;
        this.first_name      = first_name;
        this.last_name       = last_name;
        this.dob             = dob;
        this.email           = email;
        this.phone           = phone;
        this.department      = department;
        this.designation     = designation;
        this.date_of_joining = date_of_joining;
        this.salary          = salary;
        this.address         = address;
    }

    public String getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDate_of_joining() {
        return date_of_joining;
    }
    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}