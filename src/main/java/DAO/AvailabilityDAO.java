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
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getBoolean("VISIBILITA"), rs.getString("PRENOTAZIONE"));
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
    public void add(Availability a) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            if(a.getUserId() > 0) {
                String query = "INSERT INTO DISPONIBILITA (ID_DOCENTE, ID_CORSO, ID_UTENTE, GIORNO_ORA, VISIBILITA, PRENOTAZIONE) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = con.prepareStatement(query);

                statement.setInt(1, a.getTeacherId());
                statement.setInt(2, a.getSubjectId());
                statement.setInt(3, a.getUserId());
                statement.setString(4, a.getDayTime());
                statement.setBoolean(5, a.isVisibility());
                statement.setString(6, a.getBooking());

                int rowsInserted = statement.executeUpdate();
            }
            else{ //aggiunta di una riga in disponibilità fattibile solo da admin (utente può solo fare update se prenota)
                String query = "INSERT INTO DISPONIBILITA (ID_DOCENTE, ID_CORSO, GIORNO_ORA, VISIBILITA) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = con.prepareStatement(query);

                statement.setInt(1, a.getTeacherId());
                statement.setInt(2, a.getSubjectId());
                statement.setString(3, a.getDayTime());
                statement.setBoolean(4, a.isVisibility());

                int rowsInserted = statement.executeUpdate();
            }

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
    public void update(int id, Availability a) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE DISPONIBILITA SET ID_DOCENTE = ?, ID_CORSO = ?, ID_UTENTE = ?, GIORNO_ORA = ?, VISIBILITA = ?, PRENOTAZIONE = ? WHERE ID_DISPONIBILITA = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, a.getTeacherId());
            statement.setInt(2, a.getSubjectId());
            statement.setInt(3, a.getUserId());
            statement.setString(4, a.getDayTime());
            statement.setBoolean(5, a.isVisibility());
            statement.setString(6, a.getBooking());
            statement.setInt(7, id);

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
    public void delete(int id) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM DISPONIBILITA WHERE DISPONIBILITA.ID_DISPONIBILITA=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, id);
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

    Availability get(){

    }

}
