package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.Main;

public class LesRapports implements EventHandler<MouseEvent>{

    private Main main;

    public LesRapports(Main main){
        this.main = main;
    }
    
    @Override
    public void handle(MouseEvent arg0) {
        this.main.mettreAuCentre(this.main.getPageLesRapports());
    }
}
