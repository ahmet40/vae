package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import src.Main;
import src.Profil;

public class ChangementProfil implements EventHandler<MouseEvent> {

    private Profil profil;

    public ChangementProfil(Profil profil){
        this.profil = profil;
    }

    @Override
    public void handle(MouseEvent event) {
        Button button = (Button) event.getSource();
        String text = button.getText();
        Main main = Main.getInstance();
        if(text.equals("Sauvegarder")){
            if(main.pseudoValide(this.profil.getPseudo().getText()) && main.emailValide(this.profil.getEmail().getText())){

            }
        }else{

        }
    }
    
}
