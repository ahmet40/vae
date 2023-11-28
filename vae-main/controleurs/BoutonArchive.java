package controleurs;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modele.mysql.SQLArchivage;
import src.NavigationBarAdmin;

public class BoutonArchive implements EventHandler<ActionEvent>  {
    private NavigationBarAdmin navigationBarAdmin;
    public BoutonArchive(NavigationBarAdmin navigationBarAdmin) {
        this.navigationBarAdmin = navigationBarAdmin;
    }
    @Override
    public void handle(ActionEvent arg) {
        Optional<ButtonType> reponse = this.navigationBarAdmin.popUpValidationArchivage().showAndWait();        
        if(reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            this.navigationBarAdmin.archive(SQLArchivage.archivageSur2ans());
        }
    }    
}

