package DAO;

public class Availability {
    private int availabilityID;
    private int teacherId;
    private int subjectId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    private String dayTime;

    public void setBooking(String booking) {
        this.booking = booking;
    }

    private String booking;

    public Availability(int teacherId, int subjectId, String dayTime)
    {
        this.teacherId = teacherId;
        this.subjectId=subjectId;
        this.dayTime=dayTime;
    }
    public Availability(int teacherId, int subjectId, int userId, String dayTime, String booking) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.dayTime = dayTime;
        this.booking = booking;
    }

    public Availability(int availabilityID, int teacherId, int subjectId, int userId, String dayTime, String booking) {
        this.availabilityID = availabilityID;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.dayTime = dayTime;
        this.booking = booking;
    }

    public int getAvailabilityID() {
        return availabilityID;
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

    public String getBooking() {
        return booking;
    }

    public String toString(){
               return "ID disponibilità: " + availabilityID + ",\n" +
                       "ID docente: " + teacherId + ", \n" +
                       "ID corso: " + subjectId + ", \n" +
                       "ID utente: " + userId + ", \n" +
                       "Giorno e ora: " + dayTime + ", \n" +
                       "Stato prenotazione: " + booking + ";\n";
    }

}
