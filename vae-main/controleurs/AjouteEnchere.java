package controleurs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import modele.caches.Encherir;
import modele.caches.Utilisateur;
import modele.caches.Vente;
import modele.mysql.SQLEncherir;
import src.EncherirObjet;
import src.Main;

public class AjouteEnchere implements EventHandler<ActionEvent> {

    private Vente vente;
    private TextField textField;
    private EncherirObjet encherirObjet;

    public AjouteEnchere(Vente vente, TextField textField, EncherirObjet encherirObjet) {
        this.vente = vente;
        this.textField = textField;
        this.encherirObjet = encherirObjet;
    }

    @Override
    public void handle(ActionEvent event) {

        try {
            long montant = Long.parseLong(textField.getText());
            Encherir maxMontant = Main.getInstance().getMaxMontant(this.vente.getIdVe());
            long montantCheck = this.vente.getPrixBase();
            if(maxMontant != null) montantCheck = maxMontant.getMontant();
            if(montant < montantCheck || montant < this.vente.getPrixMin()) return;
            Optional<ButtonType> reponse = this.encherirObjet.popUpConfirmation().showAndWait();

            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                LocalDateTime maintenant = LocalDateTime.now();
                String formateur = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formateur);
                String dateFormatee = maintenant.format(dateTimeFormatter);
                Main main = Main.getInstance();
                Utilisateur u = main.getPersonneConnecte();
                SQLEncherir.insererEnrechissement(main, 
                new Encherir(u.getIdUt()
                , this.vente.getIdVe()
                , dateFormatee
                , montant)
                );
                this.textField.clear();
            }
        } catch (NumberFormatException e) {
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }

}
