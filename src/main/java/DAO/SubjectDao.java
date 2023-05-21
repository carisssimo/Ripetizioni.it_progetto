package DAO;

import java.sql.*;
import java.util.ArrayList;

public class SubjectDao implements DAO<Subject> {
    @Override
    public ArrayList<Subject> getAll() {
        Connection conn1 = null;
        ArrayList<Subject> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM CORSO ");
            while (rs.next()) {
                Subject s = new Subject( rs.getString("NOME_CORSO"), rs.getString("DESCRIZIONE"));
                System.out.println(s);
                out.add(s);
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
    public int add(Subject s) {
        Connection con = null;
        int rowsInserted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO CORSO (NOME_CORSO, DESCRIZIONE) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, s.getSubjectName());
            statement.setString(2, s.getDescription());

            rowsInserted = statement.executeUpdate();

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
    public int update(Subject s, String ... args) {
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE CORSO SET NOME_CORSO = ?, DESCRIZIONE = ? WHERE ID_CORSO = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            statement.setInt(3, s.getSubjectID());

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
    public int delete(int id) {
        Connection con = null;
        int rowsDeleted=0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM CORSO WHERE ID_DOCENTE = ?";
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
    public Subject get(String ... args){
        Connection con = null;
        Subject s = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String sql = "SELECT * FROM CORSO WHERE NOME_CORSO = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, args[0]);

            ResultSet rs = statement.executeQuery();

            s = new Subject(rs.getString("NOME_CORSO"), rs.getString("DESCRIZIONE"));
            System.out.println(s);

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
        return s;
    }

    public ArrayList<Subject> getByParameters(String ... args){
        Connection conn1 = null;
        ArrayList<Subject> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DOCENTE WHERE NOME = ?, COGNOME = ?, EMAIL = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            statement.setString(3, args[2]);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Subject s = new Subject(rs.getString("NOME_CORSO"), rs.getString("DESCRIZIONE"));
                System.out.println(s);
                out.add(s);
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
