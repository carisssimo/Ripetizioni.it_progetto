package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface TeacherDAO {
    ArrayList<Teacher> getAll();
    int add(Teacher t);
    int delete(int id) ;

}

