package DAO;


import java.sql.*;
import java.util.ArrayList;

public class SubjectTeacherDAO implements DAO<SubjectTeacher>{
    @Override
    public ArrayList<SubjectTeacher> getAll() {
        Connection conn1 = null;
        ArrayList<SubjectTeacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM CORSO_DOCENTE");
            while (rs.next()) {
                SubjectTeacher ass = new SubjectTeacher(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"));
                System.out.println(ass);
                out.add(ass);
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
    public void add(SubjectTeacher ass) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO CORSO_DOCENTE (ID_DOCENTE, ID_CORSO) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, ass.getTeacherID());
            statement.setInt(2, ass.getSubjectID());

            int rowsInserted = statement.executeUpdate();

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
    }

    public void delete(int id) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM DISPONIBILITÀ WHERE id=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, Integer.toString(id));
            int rowsInserted = statement.executeUpdate();


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
    }


    @Override
    public void update(int id, SubjectTeacher ass){

    }

}

