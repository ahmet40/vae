package modele.caches;

public class Categorie {

    private int idCat;
    private String nomCat;
    
    public Categorie(int idCat, String nomCat){
        this.idCat = idCat;
        this.nomCat = nomCat;
    }
    public int getIdCat(){
        return this.idCat;
    }
    public String getNomCat(){
        return this.nomCat;
    }
    @Override
    public String toString(){
        return this.nomCat;
    }


    
}
