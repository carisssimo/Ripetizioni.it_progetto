package DAO;

import java.util.ArrayList;

public interface AvailabilityDAO {
    int updateUser(Availability a, int userId);
    int updateBookingState(Availability a, String state);
    Availability getByTeacherSubjectUser(int teacherId, int subjectId, String dayTime);
    ArrayList<Availability> getByTeacherId(int teacherId);
    ArrayList<Availability> getBySubjectId(int subjectId);
    ArrayList<Availability> getByUserId(int userId);
    ArrayList<Availability> getByDayTime(String dayTime);
    ArrayList<Availability> getByBookingState(String bookingState);
    ArrayList<Availability> getAllAvailabilityAvailable();
    Availability getAvailabilityByID(int av_id);
    public ArrayList<Availability> getUserBooking(int userID);
}
