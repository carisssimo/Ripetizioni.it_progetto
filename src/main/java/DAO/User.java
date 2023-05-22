package DAO;

public class User {
    private int userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;

    public User(int userID, String name, String surname, String email, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String name, String surname, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(){
        this.name = null;
        this.surname =null;
        this.email = null;
        this.password = null;
        this.role = null;
    }

    public int getUserID(){
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString(){
               return "ID utente: " + userID + ",\n" +
                 "Nome: " + name + ", \n" +
                "Cognome: " + surname + ", \n" +
                "Email: " + email + ", \n" +
                "Password: " + password + ", \n" +
                "Ruolo: " + role + ";\n" ;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
