package DAO;

public class Teacher {
    private int teacherId;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Teacher(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString(){
        return "Nome: " + name + ", \n" +
                "Cognome: " + surname + ", \n" +
                "Email: " + email + ", \n" +
                "Password: " + password + "; \n";
    }
}
