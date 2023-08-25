package DAO;

import java.sql.*;
import java.util.ArrayList;

public class TeacherDaoImpl implements DAO<Teacher>,TeacherDAO {
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
                Teacher t  = new Teacher(rs.getInt("ID_DOCENTE"),rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
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
    public int add(Teacher elem) {
        return 0;
    }

    @Override
    public int addAvailabilitiesToo(Teacher t,int daySize,int slotSize){
        Connection conn1 = null;
        int rowsInserted = 0;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            String query = "INSERT INTO DOCENTE(NOME, COGNOME, EMAIL) VALUES (?, ?, ?)";
            PreparedStatement statement = conn1.prepareStatement(query);

            statement.setString(1, t.getName());
            statement.setString(2, t.getSurname());
            statement.setString(3, t.getEmail());

            rowsInserted = statement.executeUpdate();

            /*ciclo per l'inserimento a tutte le ore e tutti i giorni'*/
            Teacher newTeacher=this.getByEmail(t.getEmail());
            for(int i=1;i<daySize+1;i++) {
                for(int j=1;j<slotSize+1;j++){
                    String queryAvailabilities="INSERT INTO DISPONIBILITA(ID_DOCENTE,ID_GIORNO,ID_SLOT) VALUES (?,?,?)";
                    PreparedStatement statement2 = conn1.prepareStatement(queryAvailabilities);
                    statement2.setInt(1,newTeacher.getTeacherId());
                    statement2.setInt(2,i);
                    statement2.setInt(3,j);
                    int rowsInserted2 = statement2.executeUpdate();
                }

            }


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
    public int delete(int id) {
        Connection con = null;
        int rowsDeleted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database in delete Teacher");
            }

            String query = "DELETE FROM DOCENTE WHERE ID_DOCENTE=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, Integer.toString(id));

            rowsDeleted = statement.executeUpdate();

            /*TODO:eliminazione dalla tabella corso docente*/
            String queryTeacherSubject ="DELETE FROM CORSO_DOCENTE WHERE ID_DOCENTE=?";
            PreparedStatement statement2=con.prepareStatement(queryTeacherSubject);
            statement2.setInt(1,id);
            int rowsDeleted2=statement2.executeUpdate();

            /*TODO:eliminazione di tutte le availabilities a null e modifica di quelle attive in stato docente eliminato*/
            String queryAvailabilitie="UPDATE FROM DISPONIBILITA SET PRENOTAZIONE=? WHERE ID_DOCENTE=?";
            PreparedStatement statement3=con.prepareStatement(queryAvailabilitie);
            statement3.setString(1, "Docente Cancellato");
            statement3.setInt(2,id);
            int rowDeleted3=statement3.executeUpdate();

            String queryAvailabilityAvailable = "DELETE FROM DISPONIBILITA WHERE ID_DOCENTE=? AND PRENOTAZIONE IS NULL";
            PreparedStatement statement4 = con.prepareStatement(queryAvailabilityAvailable);
            statement4.setString(1, Integer.toString(id));
            int rowsInserted = statement4.executeUpdate();

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

        return rowsDeleted;
    }

    @Override
    public int updateName(Teacher t, String name){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE UTENTE SET NOME = ? WHERE ID_UTENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, name);
            statement.setInt(2, t.getTeacherId());

            rowsUpdated = statement.executeUpdate();

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

        return rowsUpdated;
    }

    @Override
    public int updateSurname(Teacher t, String surname){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE UTENTE SET COGNOME = ? WHERE ID_UTENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, surname);
            statement.setInt(2, t.getTeacherId());

            rowsUpdated = statement.executeUpdate();

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

        return rowsUpdated;
    }

    @Override
    public int updateEmail(Teacher t, String email){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE UTENTE SET COGNOME = ? WHERE ID_UTENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, email);
            statement.setInt(2, t.getTeacherId());

            rowsUpdated = statement.executeUpdate();

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

        return rowsUpdated;
    }

    @Override
    public Teacher getByEmail(String email){
        Connection con = null;
        Teacher t = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database getByEmail");
            }

            String sql = "SELECT * FROM DOCENTE WHERE EMAIL = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet rs = statement.executeQuery();
            rs.next();
            t = new Teacher(rs.getInt("ID_DOCENTE"),rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
            System.out.println(t);

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
        return t;
    }

    public Teacher getById(int id){
        Connection con = null;
        Teacher t = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database getByEmail");
            }

            String sql = "SELECT * FROM DOCENTE WHERE ID_DOCENTE = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            t = new Teacher(rs.getInt("ID_DOCENTE"),rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
            System.out.println(t);

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
        return t;
    }
}
