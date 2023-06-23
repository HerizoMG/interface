package com.example.affectation.Util;

import java.sql.*;


public class Database {
    private final String url = "jdbc:postgresql://localhost:5432/projetAffectation";
    private final String user = "postgres";
    private final String password = "root";
    protected static Connection conn;

    public Database()
    {
        try{
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("succceed");
        } catch (SQLException e) {
            System.out.println("Connection failed :"+ e.getMessage());
        }
    }
}
