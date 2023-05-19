package DAO;

public class Subject {
    private String subjectName;
    private String description;

    public Subject( String subjectName, String description) {
        this.subjectName = subjectName;
        this.description = description;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return "Nome corso: " + subjectName + ", \n" +
                "Descrizione: " + description + ";\n";
    }
}
