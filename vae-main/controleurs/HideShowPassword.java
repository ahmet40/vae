package controleurs;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import src.Connexion;

public class HideShowPassword implements EventHandler<MouseEvent> {

    private Connexion connexion;
    private src.Inscription inscription;

    public HideShowPassword(Connexion connexion){
        this.connexion = connexion;
        this.inscription = null;
    }

    public HideShowPassword(src.Inscription inscription){
        this.connexion = null;
        this.inscription = inscription;
    }

    @Override
    public void handle(MouseEvent event) {

        if(this.connexion != null){
            if(this.connexion.getPasswordP().isVisible()){
                this.connexion.getPassword().setVisible(true);
                this.connexion.getPassword().setManaged(true);
                this.connexion.getPasswordP().setVisible(false);
                this.connexion.getPasswordP().setManaged(false);
                this.connexion.getPassword().setText(this.connexion.getPasswordP().getText());
                this.connexion.getHideShowImageView().setImage(new Image(new File("./images/oeilFerme.png").toURI().toString()));
            }else{
                this.connexion.getPasswordP().setText(this.connexion.getPassword().getText());
                this.connexion.getPassword().setVisible(false);
                this.connexion.getPassword().setManaged(false);
                this.connexion.getPasswordP().setVisible(true);
                this.connexion.getPasswordP().setManaged(true);
                this.connexion.getHideShowImageView().setImage(new Image(new File("./images/oeilOuvert.png").toURI().toString()));
            }
        }else if(this.inscription != null){
            if(this.inscription.getPasswordP().isVisible()){
                this.inscription.getPassword().setVisible(true);
                this.inscription.getPassword().setManaged(true);
                this.inscription.getPasswordP().setVisible(false);
                this.inscription.getPasswordP().setManaged(false);
                this.inscription.getPassword().setText(this.inscription.getPasswordP().getText());
                this.inscription.getHideShowImageView().setImage(new Image(new File("./images/oeilFerme.png").toURI().toString()));
            }else{
                this.inscription.getPasswordP().setText(this.inscription.getPassword().getText());
                this.inscription.getPassword().setVisible(false);
                this.inscription.getPassword().setManaged(false);
                this.inscription.getPasswordP().setVisible(true);
                this.inscription.getPasswordP().setManaged(true);
                this.inscription.getHideShowImageView().setImage(new Image(new File("./images/oeilOuvert.png").toURI().toString()));
            }
        }

    }
    
}
