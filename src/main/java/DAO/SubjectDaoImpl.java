package DAO;

import java.sql.*;
import java.util.ArrayList;

public class SubjectDaoImpl implements DAO<Subject>,SubjectDAO {
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
                Subject s = new Subject(rs.getInt("ID_CORSO"), rs.getString("NOME_CORSO"), rs.getString("DESCRIZIONE"));
                System.out.println(s);
                out.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
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
    public int delete(int id)
    {

        Connection con = null;
        int rowsInserted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM CORSO WHERE ID_CORSO=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, Integer.toString(id));
            rowsInserted = statement.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return rowsInserted;
    }




    @Override
    public int add(Subject s) {
        Connection con = null;
        int rowsInserted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }
            String query = "INSERT INTO CORSO (NOME_CORSO, DESCRIZIONE) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, s.getSubjectName());
            statement.setString(2, s.getDescription());
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return rowsInserted;
    }
}