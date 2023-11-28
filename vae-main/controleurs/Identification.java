package controleurs;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import src.Connexion;
import src.Inscription;
import src.Main;

public class Identification implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

        Object source = event.getSource();

        if(source instanceof Button){

            String nomButton = ((Button) source).getText();

            Main main = Main.getInstance();

            if(nomButton.equals("Connexion") || nomButton.equals("Se connecter")){
                Connexion connexion = main.getConnexion();
                if(main.getPanelCentral().getCenter().equals(connexion)) return;
                main.mettreAuCentre(connexion);
                connexion.clearField();
                Platform.runLater(() -> connexion.focusRequest());
            }else if(nomButton.equals("Inscription") || nomButton.equals("S'inscrire")){
                Inscription inscription = main.getInscription();
                if(main.getPanelCentral().getCenter().equals(inscription)) return;
                inscription.clearField();
                main.mettreAuCentre(inscription);
                Platform.runLater(() -> inscription.focusRequest());
            }

        }
        
    }
    


}
