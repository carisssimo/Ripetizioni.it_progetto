package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface UserDAO {

      ArrayList<User> getAll();
      int add(User u);
      int delete(int id);

}
