package controleurs;

import biblio.BiblioString;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import modele.caches.Aide;
import modele.mysql.SQLAide;
import src.Main;
import src.NousContacter;

public class Soumettre implements EventHandler<ActionEvent> {
    
    private boolean mail;
    private NousContacter nousContacter;
    private Label labelNbCarac;

    public Soumettre(Boolean mail, NousContacter nousContacter, Label labelNbCarac) {
        this.mail = mail;
        this.nousContacter = nousContacter;
        this.labelNbCarac = labelNbCarac;
    }

    @Override
    public void handle(ActionEvent arg0) {
        
        this.mail = this.nousContacter.getMail();
        if (this.mail 
        && !this.nousContacter.getTextFieldNom().getText().isEmpty() 
        && !this.nousContacter.getTextFieldPrenom().getText().isEmpty()
        && !this.nousContacter.getTextFieldMail().getText().isEmpty()
        && !this.nousContacter.getTextMessage().getText().isEmpty()) {
            if (!this.nousContacter.getComboBox().getValue().equals(BiblioString.autreString)) {
                Aide aide = new Aide(
                    SQLAide.maxAideId(nousContacter.getMain()),
                    nousContacter.getTextFieldNom().getText(), 
                    nousContacter.getTextFieldPrenom().getText(), 
                    nousContacter.getTextFieldMail().getText(), 
                    nousContacter.getComboBox().getValue(), 
                    nousContacter.getTextMessage().getText()
                    );
                SQLAide.insererAide(nousContacter.getMain(), aide);
                Main main = Main.getInstance();
                main.getPageLesRapports().majLesRapports(main);
                this.nousContacter.popupSoumettre().showAndWait();
                this.nousContacter.getTextFieldNom().clear();
                this.nousContacter.getTextFieldPrenom().clear();
                this.nousContacter.getTextFieldMail().clear();
                this.nousContacter.getTextMessage().clear();
                this.nousContacter.getTextFieldObjet().clear();
                this.labelNbCarac.setText(BiblioString.nbCaracRestant + this.nousContacter.getNbCaracMax());
                }
                else{
                    if (nousContacter.getTextFieldObjet().getText().isEmpty()){
                        this.nousContacter.popupNonSoumettre().showAndWait();
                    }
                    else{
                        Aide aide = new Aide(
                            SQLAide.maxAideId(nousContacter.getMain()),
                            nousContacter.getTextFieldNom().getText(), 
                            nousContacter.getTextFieldPrenom().getText(), 
                            nousContacter.getTextFieldMail().getText(), 
                            nousContacter.getComboBox().getValue() + " - " + nousContacter.getTextFieldObjet().getText(), 
                            nousContacter.getTextMessage().getText()
                            );
                        SQLAide.insererAide(nousContacter.getMain(), aide);
                        Main main = Main.getInstance();
                        main.getPageLesRapports().majLesRapports(main);
                        this.nousContacter.popupSoumettre().showAndWait();
                        this.nousContacter.getTextFieldNom().clear();
                        this.nousContacter.getTextFieldPrenom().clear();
                        this.nousContacter.getTextFieldMail().clear();
                        this.nousContacter.getTextMessage().clear();
                        this.nousContacter.getTextFieldObjet().clear();
                        this.labelNbCarac.setText(BiblioString.nbCaracRestant + this.nousContacter.getNbCaracMax());
                    }
                }
            }
            else{
                this.nousContacter.popupNonSoumettre().showAndWait();
                }
            }
        }
