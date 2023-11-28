package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.Main;


public class LesUtilisateurs implements EventHandler<MouseEvent>{

    private Main main;

    public LesUtilisateurs(Main main){
        this.main = main;
    }
    @Override
    public void handle(MouseEvent event) {
        this.main.mettreAuCentre(this.main.getPageLesUtilisateurs());
        this.main.getPageLesUtilisateurs().requestFocuse();
    }
}
