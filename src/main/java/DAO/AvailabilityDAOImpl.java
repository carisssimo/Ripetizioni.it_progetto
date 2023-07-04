package DAO;

import java.sql.*;
import java.util.ArrayList;

public class AvailabilityDAOImpl implements DAO<Availability>,AvailabilityDAO {
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

            ResultSet rs = st.executeQuery("SELECT * FROM DISPONIBILITA");
            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));

                out.add(a);
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
    public int add(Availability a) {
        Connection con = null;
        int rowsInserted = 0;
        System.out.println("add new availability");
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            if (a.getUserId() > 0) {
                String query = "INSERT INTO DISPONIBILITA (ID_DOCENTE, ID_CORSO, ID_UTENTE, GIORNO_ORA, PRENOTAZIONE) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = con.prepareStatement(query);
                System.out.println("ADDING QUERY AVAILABILITY");
                statement.setInt(1, a.getTeacherId());
                statement.setInt(2, a.getSubjectId());
                statement.setInt(3, a.getUserId());
                statement.setString(4, a.getDayTime());
                statement.setString(5, a.getBooking());

                rowsInserted = statement.executeUpdate();
            } else { //aggiunta di una riga in disponibilità fattibile solo da admin (utente può solo fare update se prenota)
                String query = "INSERT INTO DISPONIBILITA (ID_DOCENTE, ID_CORSO, GIORNO_ORA) VALUES (?, ?, ?)";
                PreparedStatement statement = con.prepareStatement(query);
                System.out.println("ADDING QUERY AVAILABILITY");
                statement.setInt(1, a.getTeacherId());
                statement.setInt(2, a.getSubjectId());
                statement.setString(3, a.getDayTime());

                rowsInserted = statement.executeUpdate();
            }

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

    @Override
    public int delete(int id) {
        Connection con = null;
        int rowsDeleted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database inside delete AvailabilityDAOIMPL");
            }

            String query = "DELETE FROM DISPONIBILITA WHERE ID_DISPONIBILITA=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, id);

            rowsDeleted = statement.executeUpdate();


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

        return rowsDeleted;
    }

    public int updateUser(Availability a, int userId) {
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE DISPONIBILITA SET ID_UTENTE = ? WHERE ID_DISPONIBILITA = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setInt(2, a.getAvailabilityID());

            rowsUpdated = statement.executeUpdate();

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

        return rowsUpdated;
    }

    public int updateBookingState(Availability a, String state) {
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE DISPONIBILITA SET PRENOTAZIONE = ? WHERE ID_DISPONIBILITA = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, state);
            statement.setInt(2, a.getAvailabilityID());

            rowsUpdated = statement.executeUpdate();

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

        return rowsUpdated;
    }

    public Availability getByTeacherSubjectUser(int teacherId, int subjectId, String dayTime) {
        Connection con = null;
        Availability av = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database inside getByTeacherSubjectUser");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE ID_DOCENTE = ? AND ID_CORSO = ? AND GIORNO_ORA = ?";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, teacherId);
            statement.setInt(2, subjectId);
            statement.setString(3, dayTime);

            ResultSet rs = statement.executeQuery();
            rs.next();

            av = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
            System.out.println(av);

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

        return av;
    }

    public ArrayList<Availability> getByTeacherId(int teacherId) {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE ID_DOCENTE = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, teacherId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                out.add(a);
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

    public ArrayList<Availability> getBySubjectId(int subjectId) {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE ID_CORSO = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, subjectId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));

                out.add(a);
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

    public ArrayList<Availability> getByUserId(int userId) {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE ID_UTENTE = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                out.add(a);
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

    public ArrayList<Availability> getByDayTime(String dayTime) {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE GIORNO_ORA = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setString(1, dayTime);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                System.out.println(a);
                out.add(a);
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

    public ArrayList<Availability> getByBookingState(String bookingState) {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE PRENOTAZIONE = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setString(1, bookingState);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                System.out.println(a);
                out.add(a);
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
    public ArrayList<Availability> getAllAvailabilityAvailable() {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM DISPONIBILITA WHERE PRENOTAZIONE IS NULL");
            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                /*System.out.println(a);*/
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
    public int getIDAvailability(Availability a1)
    {
        int id=0;
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "SELECT ID_DISPONIBILITA FROM DISPONIBILITA WHERE ID_DOCENTE = ? AND ID_CORSO = ? AND GIORNO_ORA=?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, a1.getTeacherId());
            statement.setInt(2, a1.getSubjectId());
            statement.setString(3, a1.getDayTime());

            ResultSet rs = statement.executeQuery();



            rs.next();
            id=rs.getInt("ID_DISPONIBILITA");


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
        return id;
    }

    //
    public int updateAvailability(Availability a1) { //prenotazione di una ripetizione
        System.out.println("update Availability--------");
        int result=0;
        if (a1.getBooking() == "disdetta") {
            Availability a2=new Availability(a1.getTeacherId(),a1.getSubjectId(),a1.getDayTime());
            add(a2); //aggiungo senza id e con stato null(nuova availability)
        }
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("AvailabilityDao inside updateAvailability Connected to the database test");
            }
            String sql = "UPDATE DISPONIBILITA SET PRENOTAZIONE = ? , ID_UTENTE = ? WHERE ID_DISPONIBILITA = ?";
            /*int id_av = getIDAvailability(a1);*/
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setString(1, a1.getBooking());
            statement.setInt(2, a1.getUserId());
            statement.setInt(3, a1.getAvailabilityID());
            result=statement.executeUpdate();

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
        return result;
    }

    public Availability getAvailabilityByID(int av_id)
    {

        Availability a= null;
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("AvailabilityDao inside getAvailability_by_ID Connected to the database test");
            }

            String sql = "SELECT * FROM DISPONIBILITA WHERE ID_DISPONIBILITA = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, av_id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            a = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"),rs.getInt("ID_CORSO"),rs.getInt("ID_UTENTE"),rs.getString("GIORNO_ORA"),rs.getString("PRENOTAZIONE"));

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
        return a;
    }
    public ArrayList<Availability> getUserBooking(int userID)
    {
        Connection conn1 = null;
        ArrayList<Availability> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }
            String sql ="SELECT * FROM DISPONIBILITA WHERE ID_UTENTE=?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setInt(1, userID);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Availability a = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                /*System.out.println(a);*/
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

    public ArrayList<Availability> getUserBookingActive(int userID) {
        {
            Connection conn1 = null;
            ArrayList<Availability> out = new ArrayList<>();
            try {
                conn1 = DriverManager.getConnection(url1, user, password);
                if (conn1 != null) {
                    System.out.println("UserDAO Connected to the database test");
                }
                String sql ="SELECT * FROM DISPONIBILITA WHERE ID_UTENTE=? AND PRENOTAZIONE=?";
                PreparedStatement st = conn1.prepareStatement(sql);
                st.setInt(1, userID);
                st.setString(2, "attiva");

                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Availability a = new Availability(rs.getInt("ID_DISPONIBILITA"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"), rs.getInt("ID_UTENTE"), rs.getString("GIORNO_ORA"), rs.getString("PRENOTAZIONE"));
                    /*System.out.println(a);*/
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
    }
}
