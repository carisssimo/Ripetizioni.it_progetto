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
    public int updateSubjectByID(SubjectTeacher ass, int subjectID){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE CORSO_DOCENTE SET ID_CORSO = ? WHERE ID_CORSO_DOCENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, subjectID);
            statement.setInt(2, ass.getSubjectTeacherID());

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
    public int updateTeacherByID(SubjectTeacher ass, int teacherID){
        Connection con = null;
        int rowsUpdated = 0;
        try {
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database");
            }

            String query = "UPDATE CORSO_DOCENTE SET ID_ = ? WHERE ID_CORSO_DOCENTE = ?";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, teacherID);
            statement.setInt(2, ass.getSubjectTeacherID());

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
    public ArrayList<SubjectTeacher> getAllTeachersBySubjectId(int subjectId) {
        Connection conn1 = null;
        ArrayList<SubjectTeacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DOCENTE WHERE ID_CORSO = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, subjectId);

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
    }

    @Override
    public ArrayList<SubjectTeacher> getAllSubjectsByTeacherId(int teacherId) {
        Connection conn1 = null;
        ArrayList<SubjectTeacher> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("UserDAO Connected to the database test");
            }

            String sql = "SELECT * FROM DOCENTE WHERE ID_DOCENTE = ?";
            PreparedStatement statement = conn1.prepareStatement(sql);
            statement.setInt(1, teacherId);

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
    }
}

