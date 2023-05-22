package DAO;

import java.util.ArrayList;

public interface SubjectDAO {
    ArrayList<Subject> getAll();
    int add(Subject u);
    int delete(int id);
}
