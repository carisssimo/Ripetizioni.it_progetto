package DAO;

public class Availability {
    private int availabilityID;
    private int teacherId;
    private int subjectId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    private int dayId;

    private int slotId;

    public void setBooking(String booking) {
        this.booking = booking;
    }

    private String booking;

    public Availability(int teacherId, int day, int slot)
    {
        this.teacherId = teacherId;
        this.dayId =day;
        this.slotId =slot;
    }
    public Availability(int teacherId, int subjectId, int userId, int day,int slot, String booking) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.dayId = day;
        this.slotId =slot;
        this.booking = booking;
    }

    public Availability(int availabilityID, int teacherId, int subjectId, int userId, int day,int slot, String booking) {
        this.availabilityID = availabilityID;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.dayId = day;
        this.slotId =slot;
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

    public int getDayId() {
        return dayId;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getBooking() {
        return booking;
    }

    public String toString(){
               return "ID disponibilità: " + availabilityID + ",\n" +
                       "ID docente: " + teacherId + ", \n" +
                       "ID corso: " + subjectId + ", \n" +
                       "ID utente: " + userId + ", \n" +
                       "Giorno: " + dayId + ", \n" +
                       "Orario: " + slotId + ", \n" +
                       "Stato prenotazione: " + booking + ";\n";
    }

}
