package DAO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements DAO<User>,UserDAO{
    @Override
    public ArrayList<User> getAll() {
        Connection conn1 = null;
        ArrayList<User> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE ");
            while (rs.next()) {
                User u = new User(rs.getInt("ID_UTENTE"),rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getString("RUOLO"));
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
    public int add(User u){
        Connection con = null;
        int rowsInserted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO UTENTE (NOME, COGNOME, EMAIL, PASSWORD, RUOLO) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPassword());
            statement.setString(5, u.getRole());

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
    public int delete(int id){
        Connection con = null;
        int rowsDeleted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM UTENTE WHERE ID_UTENTE=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, id);

            rowsDeleted = statement.executeUpdate();
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
    public User getByEmail(String email) {
        Connection con = null;
        User u= new User();
        System.out.println(email);
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String query = "SELECT* FROM UTENTE WHERE EMAIL=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1,email);

            ResultSet rs = statement.executeQuery();
            rs.next();

            u.setUserID(rs.getInt("ID_UTENTE"));
            u.setName(rs.getString("NOME"));
            u.setSurname(rs.getString("COGNOME"));
            u.setEmail(rs.getString("EMAIL"));
            u.setPassword( rs.getString("PASSWORD"));
            u.setRole(rs.getString("RUOLO"));

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

        return u;
    }

    @Override
    public User getById(int id) {
        Connection con = null;
        User u= new User();
       /* System.out.println(email);*/
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String query = "SELECT* FROM UTENTE WHERE ID_UTENTE=?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, String.valueOf(id));

            ResultSet rs = statement.executeQuery();
            rs.next();

            u.setUserID(rs.getInt("ID_UTENTE"));
            u.setName(rs.getString("NOME"));
            u.setSurname(rs.getString("COGNOME"));
            u.setEmail(rs.getString("EMAIL"));
            u.setPassword( rs.getString("PASSWORD"));
            u.setRole(rs.getString("RUOLO"));

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

        return u;
    }
}


