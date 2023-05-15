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
                Teacher t  = new Teacher(rs.getInt("ID_DOCENTE"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getInt("ID_CORSO"));
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
    public void add(Teacher t){
        Connection conn1 = null;
        try{

            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO `DOCENTE` (`ID_DOCENTE`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `ID_CORSO`) VALUES ('7', 'bubi', 'bubi', 'bubi@hotmail.com', 'Bubi', 'Utente', 'Studente');");
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
    public void update(Teacher t){

    }
}
