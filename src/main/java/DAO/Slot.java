package DAO;

public class Slot {
    private int id;
    private String description;

    public Slot(int id,String description){
        this.id=id;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
