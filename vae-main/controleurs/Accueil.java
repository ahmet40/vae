package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import src.Main;

public class Accueil implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

        Object source = event.getSource();

        if(source instanceof Label){

            String nomButton = ((Label) source).getText();

            if(nomButton.equals("VAE")){
                Main main = Main.getInstance();
                if(main.getPersonneConnecte() != null && main.getPersonneConnecte().getRole().equals("Administrateur")){
                    main.mettreAuCentre(main.getAccueilAdmin());
                }else{
                    main.mettreAuCentre(main.getAccueil());
                }
            }

        }
        
    }

}
