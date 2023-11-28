package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.Annonce;

public class AfficherPlusResultat implements EventHandler<MouseEvent> {

    private Annonce annonce;

    public AfficherPlusResultat(Annonce annonce){
        this.annonce = annonce;
    }

    @Override
    public void handle(MouseEvent event) {
        this.annonce.afficherPlusDeResultat();
    }
    
}
