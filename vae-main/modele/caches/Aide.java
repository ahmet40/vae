package modele.caches;

public class Aide {

   private int id;
    private String nom;
    private String prenom;
    private String email;
    private String objet;
    private String message;

    public Aide(int id,String nom, String prenom, String email, String objet, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.objet = objet;
        this.message = message;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public String getObjet() {
        return this.objet;
    }

    public String getMessage() {
        return this.message;
    }
    public int getId() {
        return this.id;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n" + " nom : " + getNom() + "\n" + " prenom : " + getPrenom() + "\n" + " Email : " + getEmail()
                + "\n" + " objet : " + this.getObjet() + "\n" + " Message : " + getMessage() ;
    }


}
