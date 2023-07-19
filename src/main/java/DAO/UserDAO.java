package DAO;

public interface UserDAO {
    User getByEmail(String Email);
    public User getById(int id);
}
