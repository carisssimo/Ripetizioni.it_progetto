package DAO;

public class Availability {
    private int teacherId;
    private int subjectId;
    private String dayTime;

    public Availability(int teacherId, int subjectId, String dayTime) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.dayTime = dayTime;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getDayTime() {
        return dayTime;
    }

    public String toString(){
               return "ID docente: " + teacherId + ", \n" +
                "ID corso: " + subjectId + ", \n" +
                "Giorno e ora: " + dayTime + ", \n" ;
    }

}
