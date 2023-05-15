package DAO;

public class Corso {
    private int ID_corso;
    private String nome_corso;
    private String descrizione;

    public Corso(int ID_corso, String nome_corso, String descrizione) {
        this.ID_corso = ID_corso;
        this.nome_corso = nome_corso;
        this.descrizione = descrizione;
    }

    public int getID_corso() {
        return ID_corso;
    }

    public String getNome_corso() {
        return nome_corso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String toString(){
        return "ID corso: " + ID_corso + ", \n" +
                "Nome corso: " + nome_corso + ", \n" +
                "Descrizione: " + descrizione + "\n";
    }
}
