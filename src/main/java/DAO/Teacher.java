package DAO;

public class Teacher {
    private int ID_docente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private int ID_corso;

    public Teacher(int ID_docente, String nome, String cognome, String email, String password, int ID_corso) {
        this.ID_docente = ID_docente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ID_corso = ID_corso;
    }

    public int getID_docente() {
        return ID_docente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getID_corso() {
        return ID_corso;
    }

    @Override
    public String toString(){
        return "ID docente: " + ID_docente + ", \n" +
                "Nome: " + nome + ", \n" +
                "Cognome: " + cognome + ", \n" +
                "Email: " + email + ", \n" +
                "Password: " + password + ", \n"+
                "ID corso: " +  ID_corso + ", \n";
    }
}
