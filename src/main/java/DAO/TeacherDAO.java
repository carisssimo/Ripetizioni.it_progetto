package DAO;

import java.util.ArrayList;

public interface TeacherDAO {
    int updateName(Teacher t, String name);
    int updateSurname(Teacher t, String Surname);
    int updateEmail(Teacher t, String email);

    Teacher getByEmail(String email);
    public Teacher getById(int id);

}
