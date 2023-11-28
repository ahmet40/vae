package controleurs;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modele.caches.Aide;
import modele.mysql.SQLAide;
import src.PageLesRapports;

public class RapportSupprimer implements EventHandler<ActionEvent>{
    private PageLesRapports pageLesRapports;
    private Aide aide;

    public RapportSupprimer(PageLesRapports pageLesRapports, Aide aide){
        this.pageLesRapports = pageLesRapports;
        this.aide = aide;
    }
    
    @Override
    public void handle(ActionEvent event){
        Optional<ButtonType> reponse = this.pageLesRapports.popUpValidationSuppression().showAndWait();
        
        if(reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            SQLAide.supprimerAide(pageLesRapports.getMain(), aide);
            this.pageLesRapports.majLesRapports(pageLesRapports.getMain());
        }
    }

}

