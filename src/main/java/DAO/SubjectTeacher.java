package DAO;

public class SubjectTeacher  {
    private int subjectTeacherID;
    private int teacherID;
    private int subjectID;

    public SubjectTeacher(int teacherID, int subjectID) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public SubjectTeacher(int subjectTeacherID, int teacherID, int subjectID) {
        this.subjectTeacherID = subjectTeacherID;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public int getSubjectTeacherID() {
        return subjectTeacherID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public String toString(){
        return "ID associazione corso-docente: " + subjectTeacherID + ",\n" +
                "ID docente: " + teacherID + ", \n" +
                "ID corso: " + subjectID + "; \n";
    }

}
