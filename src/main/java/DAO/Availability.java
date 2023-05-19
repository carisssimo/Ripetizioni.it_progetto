package DAO;

public class Availability {
    private int teacherId;
    private int subjectId;
    private int userId;
    private String dayTime;
    private boolean visibility;
    private String booking;

    public Availability(int teacherId, int subjectId, int userId, String dayTime, boolean visibility, String booking) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.dayTime = dayTime;
        this.visibility = visibility;
        this.booking = booking;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDayTime() {
        return dayTime;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public String getBooking() {
        return booking;
    }

    public String toString(){
               return "ID docente: " + teacherId + ", \n" +
                       "ID corso: " + subjectId + ", \n" +
                       "ID utente: " + userId + ", \n" +
                       "Giorno e ora: " + dayTime + ", \n" +
                       "Visibilità: " + visibility + ", \n" +
                       "Stato prenotazione: " + booking + ";\n";
    }

}
