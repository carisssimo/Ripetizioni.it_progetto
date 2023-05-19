package DAO;

public class SubjectTeacher {
    private int teacherID;
    private int subjectID;

    public SubjectTeacher(int teacherID, int subjectID) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public String toString(){
        return "ID docente: " + teacherID + ", \n" +
                "ID corso: " + subjectID + "; \n";
    }

}
