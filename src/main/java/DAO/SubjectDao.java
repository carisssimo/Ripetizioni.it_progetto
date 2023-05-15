package DAO;

import java.sql.*;
import java.util.ArrayList;

public class SubjectDao implements DAO<Subject>{
    @Override
    public ArrayList<Subject> getAll() {

        Connection conn1 = null;
        ArrayList<Subject> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM CORSO ");
            while (rs.next()) {
                Subject s  = new Subject(rs.getInt("ID_CORSO"), rs.getString("NOME_CORSO"), rs.getString("DESCRIZIONE"));
                System.out.println(s);
                out.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return out;

    }

    @Override
    public void update(Subject elem) {

    }

    @Override
    public void add(Subject elem) {

    }
}
