package modele.caches;

public class Photo {
    
    private int idPh;
    private String titrePh;
    private byte[] imgPh;
    private int idOb;

    public Photo(int idPh, String titrePh, byte[] imgPh, int idOb){
        this.idPh = idOb;
        this.titrePh = titrePh;
        this.imgPh = imgPh;
        this.idOb = idOb;
    }

    public int getIdPh() {
        return this.idPh;
    }

    public String getTitrePh() {
        return this.titrePh;
    }

    public byte[] getImgPh() {
        return this.imgPh;
    }

    public int getIdOb() {
        return this.idOb;
    }

}
