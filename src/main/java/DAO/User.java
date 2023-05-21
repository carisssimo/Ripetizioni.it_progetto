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
}
