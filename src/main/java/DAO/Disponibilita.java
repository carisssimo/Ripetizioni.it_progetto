package DAO;

public class Disponibilita {
    private int ID_disponibilita;
    private int ID_docente;
    private int ID_corso;
    private String giorno;
    private String ora_inizio;
    private String ora_fine;

    public Disponibilita(int ID_disponibilita, int ID_docente, int ID_corso, String giorno, String ora_inizio, String ora_fine) {
        this.ID_disponibilita = ID_disponibilita;
        this.ID_docente = ID_docente;
        this.ID_corso = ID_corso;
        this.giorno = giorno;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
    }

    public int getID_disponibilita() {
        return ID_disponibilita;
    }

    public int getID_docente() {
        return ID_docente;
    }

    public int getID_corso() {
        return ID_corso;
    }

    public String getGiorno() {
        return giorno;
    }

    public String getOra_inizio() {
        return ora_inizio;
    }

    public String getOra_fine() {
        return ora_fine;
    }

    public String toString(){
        return "ID disponibilita: " + ID_disponibilita + ", \n" +
                "ID docente: " + ID_docente + ", \n" +
                "ID corso: " + ID_corso + ", \n" +
                "Giorno: " + giorno + ", \n" +
                "Ora inizio: " + ora_inizio + ", \n" +
                "Ora fine: " + ora_fine + ", \n";
    }

}
