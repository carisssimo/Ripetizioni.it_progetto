package DAO;

public class Prenotazione {
    private int ID_prenotazione;
    private String data;
    private String orario;
    private boolean confermata;
    private int ID_corso;
    private int ID_docente;
    private int ID_utente;

    public Prenotazione(int ID_prenotazione, String data, String orario, boolean confermata, int ID_corso, int ID_docente, int ID_utente) {
        this.ID_prenotazione = ID_prenotazione;
        this.data = data;
        this.orario = orario;
        this.confermata = confermata;
        this.ID_corso = ID_corso;
        this.ID_docente = ID_docente;
        this.ID_utente = ID_utente;
    }

    public int getID_prenotazione() {
        return ID_prenotazione;
    }

    public String getData() {
        return data;
    }

    public String getOrario() {
        return orario;
    }

    public boolean isConfermata() {
        return confermata;
    }

    public int getID_corso() {
        return ID_corso;
    }

    public int getID_docente() {
        return ID_docente;
    }

    public int getID_utente() {
        return ID_utente;
    }


    public String toString(){
        return "ID_prenotazione: " + ID_prenotazione + ", \n" +
                "Data: " + data + ", \n" +
                "Orario: " + orario + ", \n" +
                "Confermata: " + isConfermata() + ", \n" +
                "ID_corso: " + ID_corso + ", \n" +
                "ID_docente: " + ID_docente + ", \n" +
                "ID_utente: " + ID_utente + ", \n";
    }
}
