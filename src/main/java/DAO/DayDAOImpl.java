package DAO;

import java.sql.*;
import java.util.ArrayList;

public class DayDAOImpl implements DAO {
    @Override
    public ArrayList getAll() {

            Connection conn1 = null;
            ArrayList<Day> out = new ArrayList<>();
            try {
                conn1 = DriverManager.getConnection(url1, user, password);
                if (conn1 != null) {
                    System.out.println("UserDAO Connected to the database test");
                }

                Statement st = conn1.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM GIORNO");
                while (rs.next()) {
                    Day d = new Day(rs.getInt("ID"),rs.getString("DESCRIZIONE"));

                    out.add(d);
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
    public int add(Object elem) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
