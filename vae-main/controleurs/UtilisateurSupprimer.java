package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modele.caches.Utilisateur;
import modele.caches.Vente;
import modele.mysql.SQLUser;
import modele.mysql.SQLVente;
import modele.caches.Objet;
import modele.mysql.SQLObjet;
import src.Main;
import src.PageLesUtilisateurs;
import java.util.List;
import java.util.Optional;

public class UtilisateurSupprimer implements EventHandler<ActionEvent>{
    private Main main;
    private PageLesUtilisateurs pageLesUtilisateurs;
    private Utilisateur utilisateur;
    public UtilisateurSupprimer(Main main, PageLesUtilisateurs pageLesUtilisateurs, Utilisateur utilisateur){
        this.main = main;
        this.pageLesUtilisateurs = pageLesUtilisateurs;
        this.utilisateur = utilisateur;
    }
    @Override
    public void handle(ActionEvent arg0) {
        Optional<ButtonType> reponse = this.pageLesUtilisateurs.popUpValidationSuppression().showAndWait();
        
        if(reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            this.pageLesUtilisateurs.supprimeUtilisateur(this.utilisateur);
            SQLUser.deleteUserEncherir(main, this.utilisateur);

            List<Objet> lesObjetsUtilisateurs = SQLObjet.rechercheParUtilisateur(main, utilisateur);
            for(int i = 0; i < lesObjetsUtilisateurs.size(); i++){
                List<Vente> lesVentes = SQLVente.rechercheVenteParObjet(main, lesObjetsUtilisateurs.get(i));
                System.out.println("Les Ventes : " + lesVentes.size());
                for(int j = 0; j < lesVentes.size(); j++){
                    SQLVente.supprimerEnchere(main, lesVentes.get(j));
                    SQLVente.supprimerVente(main, lesVentes.get(j));
                }
                SQLObjet.supprimerObjet(main, lesObjetsUtilisateurs.get(i));
            }
            SQLUser.deleteUser(main, this.utilisateur);

            this.pageLesUtilisateurs.majLesUtilisateurs(this.pageLesUtilisateurs.getRecherche().getText());
        }
    }
}