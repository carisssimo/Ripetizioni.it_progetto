package DAO;

import java.util.ArrayList;

public interface SubjectTeacherDAO {
    SubjectTeacher getByTeacherId(int teacherId);
    SubjectTeacher getBySubjectId(int subjectId);

    ArrayList<SubjectTeacher> getAllTeachersBySubjectId(int subjectId);
    ArrayList<SubjectTeacher> getAllSubjectsByTeacherId(int teacherId);

}
