package DAO;

public class User {
    private int ID_utente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;
    private String tipo;

    public User(int ID_utente, String nome, String cognome, String email, String password, String ruolo, String tipo) {
        this.ID_utente = ID_utente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.tipo = tipo;
    }

    public int getID_utente() {
        return ID_utente;
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

    public String getRuolo() {
        return ruolo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString(){
        return "ID utente: " + ID_utente + ",\n" +
                "Nome: " + nome + ", \n" +
                "Cognome: " + cognome + ", \n" +
                "Email: " + email + ", \n" +
                "Password: " + password + ", \n" +
                "Ruolo " + ruolo + ", \n" +
                "Tipo: " + tipo + "\n";
    }
}
