package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface DAO<T> {
    static final String url1 = "jdbc:mysql://localhost:8889/test_ripetizioni";
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


    int add(T elem);

    void update(int id, T elem);

    int delete(int id);

    T get(String ... strings);

}
