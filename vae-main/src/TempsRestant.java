package src;

public class TempsRestant {
 
    private int id;
    private String date;

    public TempsRestant(int id, String date){
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }


}