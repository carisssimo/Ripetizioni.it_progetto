package DAO;

public class Availability {
    private int availabilityId;
    private int teacherId;
    private int subjectId;
    private String day;
    private String startTime;
    private String endTime;

    public Availability(int availabilityId, int teacherId, int subjectId, String day, String startTime, String endTime) {
        this.availabilityId = availabilityId;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getAvailabilityId() {
        return availabilityId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String toString(){
        return "ID disponibilita: " + availabilityId + ", \n" +
                "ID docente: " + teacherId + ", \n" +
                "ID corso: " + subjectId + ", \n" +
                "Giorno: " + day + ", \n" +
                "Ora inizio: " + startTime + ", \n" +
                "Ora fine: " + endTime + ", \n";
    }

}
