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

            /*User u = new User(rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getString("RUOLO"));
           */
           u.setName(rs.getString("NOME"));
           u.setSurname(rs.getString("COGNOME"));
           u.setEmail(rs.getString("EMAIL"));
           u.setPassword( rs.getString("PASSWORD"));
           u.setRole(rs.getString("RUOLO"));

            System.out.println(u);





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
        System.out.println(u);
        return u;
    }

    /*@Override
    public User get(String ... args) {
        Connection con = null;
        User u = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String sql = "SELECT * FROM UTENTE WHERE EMAIL = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, args[0]);

            ResultSet rs = statement.executeQuery();

            u = new User(rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getString("RUOLO"));
            System.out.println(u);

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
        return u;
    }*/

    /*@Override
    public ArrayList<User> getByParameters(String ... args){
        Connection conn1 = null;
        ArrayList<User> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM UTENTE WHERE NOME = ?, COGNOME = ?, EMAIL = ?, PASSWORD = ?, RUOLO =?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            statement.setString(3, args[2]);
            statement.setString(4, args[3]);
            statement.setString(5, args[4]);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getString("RUOLO"));
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
    }*/
}


