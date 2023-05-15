package DAO;

import java.sql.*;
import java.util.ArrayList;

public class UserDao implements DAO<User>{

    @Override
    public ArrayList<User> getAll() {
        Connection conn1 = null;
        ArrayList<User> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE ");
            while (rs.next()) {
                User u = new User(rs.getInt("ID_UTENTE"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getString("RUOLO"), rs.getString("TIPO"));
                System.out.println(u);
                out.add(u);
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
    public void add(User u){
        Connection conn1 = null;
        try{

            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO `UTENTE` (`ID_UTENTE`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `RUOLO`, `TIPO`) VALUES ('7', 'bubi', 'bubi', 'bubi@hotmail.com', 'Bubi', 'Utente', 'Studente');");
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
    }

    @Override
    public void update(User ut){

    }
}
