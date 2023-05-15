package DAO;

public class Booking {
    private int bookingId;
    private String date;
    private String time;
    private boolean confirmed;
    private int objectId;
    private int teacherId;
    private int userId;

    public Booking(int bookingId, String date, String time, boolean confirmed, int objectId, int teacherId, int userId) {
        this.bookingId = bookingId;
        this.date = date;
        this.time = time;
        this.confirmed = confirmed;
        this.objectId = objectId;
        this.teacherId = teacherId;
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isConfirmed() {
        return confirmed;
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
        return "ID_prenotazione: " + bookingId + ", \n" +
                "Data: " + date + ", \n" +
                "Orario: " + time + ", \n" +
                "Confermata: " + isConfirmed() + ", \n" +
                "ID_corso: " + objectId + ", \n" +
                "ID_docente: " + teacherId + ", \n" +
                "ID_utente: " + userId + ", \n";
    }
}
