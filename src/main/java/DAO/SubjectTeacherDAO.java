package DAO;

import java.util.ArrayList;

public interface SubjectTeacherDAO {
    int updateSubjectByID(SubjectTeacher ass, int subjectID);

    int updateTeacherByID(SubjectTeacher ass, int teacherID);

    ArrayList<SubjectTeacher> getAllTeachersBySubjectId(int subjectId);

    ArrayList<SubjectTeacher> getAllSubjectsByTeacherId(int teacherId);
}
