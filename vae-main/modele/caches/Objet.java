package modele.caches;

import java.util.ArrayList;
import java.util.List;

public class Objet {
    
    private int idOb;
    private String nomOb;
    private String descriptionOb;
    private List<Photo> photos;
    private Categorie categorie;
    private int idUt;
    public Objet(int idOb, String nomOb, String descriptionOb, Categorie categorie, int idUt){
        this.idOb = idOb;
        this.nomOb = nomOb;
        this.descriptionOb = descriptionOb;
        this.photos = new ArrayList<>();
        this.categorie = categorie;
        this.idUt = idUt;
    }
    public int getIdOb(){
        return this.idOb;
    }
    public String getNomOb(){
        return this.nomOb;
    }
    public String getDescriptionOb(){
        return this.descriptionOb;
    }
    public List<Photo> getPhotos(){
        return this.photos;
    }
    public Categorie getCategorie(){
        return this.categorie;
    }
    public int getIdUt() {
        return this.idUt;
    }
    @Override
    public String toString(){
        return "Id : "+this.idOb+", Nom : "+this.nomOb+ ", Categorie : "+this.categorie.toString()+", Id utilisateur : "+this.idUt;
    }



}
