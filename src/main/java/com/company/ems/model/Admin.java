package com.company.ems.model;

public class Admin {

    private int    id;
    private String username;
    private String password;

    public Admin() {}

    public Admin(int id, String username, String password) {
        this.id       = id;
        this.username = username;
        this.password = password;
    }

    public int    getId()               { return id; }
    public void   setId(int v)          { id = v; }

    public String getUsername()         { return username; }
    public void   setUsername(String v) { username = v; }

    public String getPassword()         { return password; }
    public void   setPassword(String v) { password = v; }
}