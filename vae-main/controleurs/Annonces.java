package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.Main;

public class Annonces implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        Main main = Main.getInstance();
        main.mettreAuCentre(main.getAnnonce());
        main.getAnnonce().requestFocuse();
    }
    
}
