package controleurs;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.Connexion;
import src.Main;

public class MesEncheresControleur implements EventHandler<MouseEvent> {

    public static boolean connecte = false;

    @Override
    public void handle(MouseEvent event) {

        Main main = Main.getInstance();

        if(!main.isConnected()) {
            Connexion connection = main.getConnexion();
            connection.clearField();
            main.mettreAuCentre(connection);
            connecte = true;
            Platform.runLater(() -> connection.focusRequest());
        }
        else {
            main.getMesEncheres().set();
            main.mettreAuCentre(main.getMesEncheres());
        }
        
    }
    
}
