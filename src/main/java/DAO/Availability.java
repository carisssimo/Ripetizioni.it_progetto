package DAO;

public class Availability {
    private int availabilityId;
    private int teacherId;
    private int subjectId;
    private String day;
    private String startTime;
    private String endTime;

    public Availability(int ID_disponibilita, int ID_docente, int ID_corso, String giorno, String ora_inizio, String ora_fine) {
        this.availabilityId = ID_disponibilita;
        this.teacherId = ID_docente;
        this.subjectId = ID_corso;
        this.day = giorno;
        this.startTime = ora_inizio;
        this.endTime = ora_fine;
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
