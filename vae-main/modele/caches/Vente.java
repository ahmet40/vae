package modele.caches;

public class Vente {

    private int idVe;
    private long prixBase;
    private long prixMin;
    private String debutVe;
    private String finVe;
    private int idOb;
    private byte idSt;

    public Vente(int idVe, long prixBase, long prixMin, String debutVe, String finVe, int idOb, byte idSt){
        this.idVe = idVe;
        this.prixBase = prixBase;
        this.prixMin = prixMin;
        this.debutVe = debutVe;
        this.finVe = finVe;
        this.idOb = idOb;
        this.idSt = idSt;
    }

    public int getIdVe() {
        return this.idVe;
    }

    public long getPrixBase() {
        return this.prixBase;
    }

    public long getPrixMin() {
        return this.prixMin;
    }

    public String getDebutVe() {
        return this.debutVe;
    }

    public String getFinVe() {
        return this.finVe;
    }

    public int getIdOb() {
        return this.idOb;
    }

    public byte getIdSt() {
        return this.idSt;
    }

    public void setDebutVe(String debutVe) {
        this.debutVe = debutVe;
    }

    public void setFinVe(String finVe) {
        this.finVe = finVe;
    }

    
}
