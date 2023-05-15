package DAO;

public class Subject {
    private int subjectId;
    private String subjectName;
    private String description;

    public Subject(int subjectId, String subjectName, String description) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return "ID corso: " + subjectId + ", \n" +
                "Nome corso: " + subjectName + ", \n" +
                "Descrizione: " + description + "\n";
    }
}
