package DAO;

public class Day{
   private  int id;
   private  String description;

   public Day(int id, String description){
       this.id=id;
       this.description=description;
   }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
