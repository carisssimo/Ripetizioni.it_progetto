package DAO;

import java.sql.*;
import java.util.ArrayList;

public interface AvailabilityDAO {
    ArrayList<Availability> getAll() ;
    //int add(Availability a);
    //int update(Availability a, String ... args) ;
    //int delete(int id) ;

    Availability get(String ... args);
    //ArrayList<Availability> getByParameters(String... args);
}
