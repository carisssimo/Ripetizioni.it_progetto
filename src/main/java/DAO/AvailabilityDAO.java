package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface AvailabilityDAO {
    ArrayList<Availability> getAll() ;
    int add(Availability a);

    int delete(int id) ;


}
