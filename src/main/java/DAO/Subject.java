package DAO;

public class Subject {
    private int subjectID;
    private String subjectName;
    private String description;

    public Subject(int subjectID, String subjectName, String description) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.description = description;
    }

    public Subject(String subjectName, String description) {
        this.subjectName = subjectName;
        this.description = description;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return "ID corso: " + subjectID + ",\n" +
                "Nome corso: " + subjectName + ", \n" +
                "Descrizione: " + description + ";\n";
    }
}
