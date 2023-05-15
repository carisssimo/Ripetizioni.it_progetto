package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface DAO<T> {
    static final String url1 = "jdbc:mysql://localhost:3306/test_ripetizioni?useSSL=false";
    static final String user = "root";
    static final String password = "root";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    ArrayList<T> getAll();

    void add(T elem);

    void update(T elem);

    void delete(int id);




}
