package DAO;


import java.sql.*;
import java.util.ArrayList;

public class SubjectTeacherDAOImpl implements DAO<SubjectTeacher>,SubjectTeacherDAO{
    @Override
    public ArrayList<SubjectTeacher> getAll() {
        Connection conn1 = null;
        ArrayList<SubjectTeacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM CORSO_DOCENTE");
            while (rs.next()) {
                SubjectTeacher ass = new SubjectTeacher(rs.getInt("ID_CORSO_DOCENTE"),rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"));
                System.out.println(ass);
                out.add(ass);
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
    public int add(SubjectTeacher ass) {
        Connection con = null;
        int rowsInserted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "INSERT INTO CORSO_DOCENTE (ID_DOCENTE, ID_CORSO) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, ass.getTeacherID());
            statement.setInt(2, ass.getSubjectID());

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

    /*@Override
    public int update(SubjectTeacher ass, String ... args){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE CORSO_DOCENTE SET ID_DOCENTE = ?, ID_CORSO = ? WHERE ID_CORSO_DOCENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, Integer.parseInt(args[0]));
            statement.setInt(2, Integer.parseInt(args[1]));
            statement.setInt(3, ass.getSubjectTeacherID());

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
    }*/

    @Override
    public int delete(int id) {
        Connection con = null;
        int rowsDeleted = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "DELETE FROM CORSO_DOCENTE WHERE ID_CORSO_DOCENTE = ?";
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
    public SubjectTeacher getByTeacherId(int teacherId) {
        return null;
    }

    @Override
    public SubjectTeacher getBySubjectId(int subjectId) {
        return null;
    }

    @Override
    public ArrayList<SubjectTeacher> getAllTeachersBySubjectId(int subjectId) {
        return null;
    }

    @Override
    public ArrayList<SubjectTeacher> getAllSubjectsByTeacherId(int teacherId) {
        return null;
    }

    /*@Override
    public SubjectTeacher get(String ... args){
        Connection con = null;
        SubjectTeacher ass = null;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String sql = "SELECT * FROM CORSO_DOCENTE WHERE ID_DOCENTE = ?, ID_CORSO = ?";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(args[0]));
            statement.setInt(2, Integer.parseInt(args[1]));

            ResultSet rs = statement.executeQuery();

            ass = new SubjectTeacher(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"));
            System.out.println(ass);

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
        return ass;
    }*/

    /*@Override
    public ArrayList<SubjectTeacher> getByParameters(String ... args){
        Connection conn1 = null;
        ArrayList<SubjectTeacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DOCENTE WHERE ID_DOCENTE = ?, ID_CORSO = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(args[0]));
            statement.setInt(2, Integer.parseInt(args[1]));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                SubjectTeacher t = new SubjectTeacher(rs.getInt("ID_DOCENTE"), rs.getInt("ID_CORSO"));
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
    }*/
}

