package DAO;

import java.sql.*;
import java.util.ArrayList;

public class TeacherDao implements DAO<Teacher> {

    @Override
    public ArrayList<Teacher> getAll() {
        Connection conn1 = null;
        ArrayList<Teacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTE ");
            while (rs.next()) {
                Teacher t  = new Teacher(rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getInt("ID_CORSO"));
                System.out.println(t);
                out.add(t);
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
    public int add(Teacher t){
        Connection conn1 = null;
        int rowsInserted=0;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            String query = "INSERT INTO DOCENTE(NOME, COGNOME, EMAIL, PASSWORD, ID_CORSO) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn1.prepareStatement(query);

            statement.setString(1, t.getName());
            statement.setString(2, t.getSurname());
            statement.setString(3, t.getEmail());
            statement.setString(4, t.getPassword());
            statement.setInt(5, t.getSubjectId());

            rowsInserted = statement.executeUpdate();
        }
        catch (SQLException e){
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
        return rowsInserted;
    }

    @Override
    public void update(Teacher t){

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

            String query = "DELETE FROM DOCENTE WHERE ID_DOCENTE=?";
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
    public Teacher get(int id) {
        return null;
    }


}
