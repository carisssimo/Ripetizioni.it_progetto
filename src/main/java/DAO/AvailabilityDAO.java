package DAO;

import java.sql.*;
import java.util.ArrayList;

public class AvailabilityDAO implements DAO<Availability>{
    @Override
    public ArrayList<Availability> getAll() {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM DISPONIBILITA ");
            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getString("GIORNO_ORA"));
                System.out.println(a);
                out.add(a);
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
    public int add(Availability a) {
        Connection con = null;
        int rowsInserted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO DISPONIBILITA (ID_DOCENTE, ID_CORSO, GIORNO_ORA) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, a.getTeacherId());
            statement.setInt(2, a.getSubjectId());
            statement.setString(3, a.getDayTime());

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
    public void update(Availability elem) {

    }
    public int delete(int id)
    {

        Connection con = null;
        int rowsInserted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM DISPONIBILITÀ WHERE id=?";
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
    public Availability get(int id) {
        return null;
    }


}
