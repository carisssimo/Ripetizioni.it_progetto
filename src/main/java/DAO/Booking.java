package DAO;

public class Booking {
    private String dateTime;
    private String state;
    private int objectId;
    private int teacherId;
    private int userId;

    public Booking(String dateTime, String state, int objectId, int teacherId, int userId) {
        this.dateTime = dateTime;
        this.state = state;
        this.objectId = objectId;
        this.teacherId = teacherId;
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getState() {
        return state;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getUserId() {
        return userId;
    }

    public String toString(){
                return "Data e giorno: " + dateTime + ", \n" +
                "Stato: " + state + ", \n" +
                "ID_corso: " + objectId + ", \n" +
                "ID_docente: " + teacherId + ", \n" +
                "ID_utente: " + userId + ", \n";
    }
}
