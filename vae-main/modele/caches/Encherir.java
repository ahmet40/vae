package modele.caches;

public class Encherir {
    
    private int idUt;
    private int idVe;
    private String dateHeure;
    private long montant;

    public Encherir(int idUt, int idVe, String dateHeure, long montant){
        this.idUt = idUt;
        this.idVe = idVe;
        this.dateHeure = dateHeure;
        this.montant = montant;
    }

    public int getIdUt() {
        return this.idUt;
    }

    public int getIdVe() {
        return this.idVe;
    }

    public String getDateHeure() {
        return this.dateHeure;
    }

    public long getMontant() {
        return this.montant;
    }

}
