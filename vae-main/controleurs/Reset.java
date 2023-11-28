package controleurs;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import src.NousContacter;

public class Reset implements EventHandler<ActionEvent> {

    private NousContacter nousContacter;
    private Label labelNbCarac;

    public Reset(NousContacter nousContacter, Label labelNbCarac) {
        this.nousContacter = nousContacter;
        this.labelNbCarac = labelNbCarac;
    }

    @Override
    public void handle(ActionEvent arg0) {
        if (this.nousContacter.getTextFieldObjet().isVisible()) {
            if (!this.nousContacter.getTextFieldNom().getText().isEmpty()
            || !this.nousContacter.getTextFieldPrenom().getText().isEmpty()
            || !this.nousContacter.getTextFieldMail().getText().isEmpty()
            || !this.nousContacter.getTextMessage().getText().isEmpty()
            || !this.nousContacter.getTextFieldObjet().getText().isEmpty()) {
                Optional<ButtonType> reponse = this.nousContacter.popupConfirmationReset().showAndWait();
                if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                    this.nousContacter.getTextFieldNom().clear();
                    this.nousContacter.getTextFieldPrenom().clear();
                    this.nousContacter.getTextFieldMail().clear();
                    this.nousContacter.getTextMessage().clear();
                    this.nousContacter.getTextFieldObjet().clear();
                    this.labelNbCarac.setText("Nombre de caractères restant : " + this.nousContacter.getNbCaracMax());
                }
            }
        }
        else {
            if (!this.nousContacter.getTextFieldNom().getText().isEmpty()
            || !this.nousContacter.getTextFieldPrenom().getText().isEmpty()
            || !this.nousContacter.getTextFieldMail().getText().isEmpty()
            || !this.nousContacter.getTextMessage().getText().isEmpty()) {
                Optional<ButtonType> reponse = this.nousContacter.popupConfirmationReset().showAndWait();
                if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                    this.nousContacter.getTextFieldNom().clear();
                    this.nousContacter.getTextFieldPrenom().clear();
                    this.nousContacter.getTextFieldMail().clear();
                    this.nousContacter.getTextMessage().clear();
                    this.labelNbCarac.setText("Nombre de caractères restant : " + this.nousContacter.getNbCaracMax());
                }
            }
        }
    }
}
