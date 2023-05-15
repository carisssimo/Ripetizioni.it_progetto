package DAO;

public class Teacher {
    private int teacherId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int subjectId;

    public Teacher(String name, String surname, String email, String password, int subjectId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.subjectId = subjectId;
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

    public int getSubjectId() {
        return subjectId;
    }

    @Override
    public String toString(){
        return "Nome: " + name + ", \n" +
                "Cognome: " + surname + ", \n" +
                "Email: " + email + ", \n" +
                "Password: " + password + ", \n"+
                "ID corso: " + subjectId + ", \n";
    }
}
