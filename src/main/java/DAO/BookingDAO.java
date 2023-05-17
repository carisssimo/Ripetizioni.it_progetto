package DAO;

import java.sql.*;
import java.util.ArrayList;

public class BookingDAO implements DAO<Booking>{
    @Override
    public ArrayList<Booking> getAll() {
        Connection conn1 = null;
        ArrayList<Booking> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONE ");
            while (rs.next()) {
                Booking b = new Booking(rs.getString("DATA_ORA"), rs.getString("STATO"), rs.getInt("ID_CORSO"), rs.getInt("ID_DOCENTE"), rs.getInt("ID_UTENTE"));
                System.out.println(b);
                out.add(b);
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
    public void add(Booking b) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO PRENOTAZIONE (DATA_ORA, STATO, ID_CORSO, ID_DOCENTE, ID_UTENTE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, b.getDateTime());
            statement.setString(2, b.getState());
            statement.setInt(3, b.getObjectId());
            statement.setInt(4, b.getTeacherId());
            statement.setInt(5, b.getUserId());

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
    public void update(Booking elem) {

    }

    public void delete(int id)
    {

        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM PRENOTAZIONE WHERE id=?";
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
    public Booking get(int id) {
        return null;
    }


}
