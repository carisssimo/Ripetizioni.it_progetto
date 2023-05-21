package DAO;

public class Teacher {
    private int teacherId;
    private String name;
    private String surname;
    private String email;

    public Teacher(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Teacher(int teacherId, String name, String surname, String email) {
        this.teacherId = teacherId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getTeacherId() {
        return teacherId;
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

    @Override
    public String toString(){
        return "ID docente: " + teacherId + ",\n" +
                "Nome: " + name + ", \n" +
                "Cognome: " + surname + ", \n" +
                "Email: " + email + "; \n";
    }
}
