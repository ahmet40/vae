package controleurs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modele.mysql.SQLCategorie;
import modele.mysql.SQLObjet;
import modele.mysql.SQLPhoto;
import modele.mysql.SQLVente;
import modele.caches.Objet;
import modele.caches.Vente;
import src.Enchere;
import src.Main;

public class AjoutVente implements EventHandler<ActionEvent> {
    private List<String> lesPhotos;
    private TextField nom;
    private Enchere enchere;
    private TextArea desc;
    private TextField prixMin;
    private TextField prixBase;
    private ComboBox<String> comboBox;

    public AjoutVente(Enchere enchere, ComboBox<String> comboBox,List<String> lesPhotos,TextField nom,TextArea desc,TextField prixMin,TextField prixBase ){
        this.lesPhotos=lesPhotos;
        this.nom=nom;
        this.prixBase=prixBase;
        this.prixMin=prixMin;
        this.desc=desc;
        this.comboBox=comboBox;
        this.enchere = enchere;
    }

    @Override
    public void handle(ActionEvent event) {
        String debutVe = this.enchere.getDateDebut().toString();
        String finVe = this.enchere.getDateFin().toString();
        
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LocalDateTime debutTime = LocalDateTime.parse(debutVe, inputFormatter);
        LocalDateTime finTime = LocalDateTime.parse(finVe, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S", Locale.FRENCH);
        String debut = debutTime.format(outputFormatter);
        String fin = finTime.format(outputFormatter);
        try{
            float p=Float.parseFloat(this.prixBase.getText());
            float b=Float.parseFloat(this.prixMin.getText());
            if (!this.nom.getText().isEmpty() && !this.desc.getText().isEmpty()&& !this.prixMin.getText().isEmpty() && !this.prixBase.getText().isEmpty() ) {
                Main main = Main.getInstance();
                Optional<ButtonType> optional = main.getEnchere().popUpInsertionVente("Êtes-vous sûr de publier cette enchère ?").showAndWait();
                if(optional.get().equals(ButtonType.YES)){
                    Objet objetMisEnVente = new Objet(SQLObjet.intMaxOb(Main.getInstance()),this.nom.getText(),this.desc.getText(),SQLCategorie.rechercheParNom(Main.getInstance(),this.comboBox.getValue().toString()),Main.getInstance().getPersonneConnecte().getIdUt());
                    Vente vendreObjet= new Vente(SQLVente.maxVenteId(Main.getInstance()), (long) p, (long)b, debut, fin, objetMisEnVente.getIdOb(), (byte) 1);
                    SQLObjet.insererObjet(Main.getInstance(), objetMisEnVente);  
                    SQLVente.mettreEnVente(Main.getInstance(), vendreObjet);
                    for (String photo : this.lesPhotos) {
                        SQLPhoto.enregistrerImage(photo, objetMisEnVente);
                    }
                    main.getEnchere().reset();
                    main.mettreAuCentre( (main.getPersonneConnecte() != null && main.getPersonneConnecte().getRole().equals("Administrateur") 
                    ? main.getAccueilAdmin() : main.getAccueil()) );
                    main.getAccueil().majAffichage();
                    main.getAccueilAdmin().majAffichage();
                    main.getAnnonce().majAffichage();
                }
            }
            else{
                this.enchere.popupNonSoumettre("L'enchère ne s'est pas créée. \n\nMerci de remplir tous les champs.").showAndWait();
            }
        }catch(NumberFormatException ignored){
            this.enchere.popupNonSoumettre("Veuillez saisir des entiers pour les prix !").showAndWait();
        }
    }

}
