package controleurs;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import src.Annonce;
import src.PageLesUtilisateurs;

public class Recherche implements EventHandler<KeyEvent> {
    private PageLesUtilisateurs pageLesUtilisateurs;
    private Annonce annonces;
    public Recherche(PageLesUtilisateurs pageLesUtilisateurs) {
        this.pageLesUtilisateurs = pageLesUtilisateurs;
        this.annonces = null;
    }
    public Recherche(Annonce annonces) {
        this.pageLesUtilisateurs = null;
        this.annonces = annonces;
    }
    @Override
    public void handle(KeyEvent arg0) {
        TextField textField = (TextField) arg0.getSource();
        Platform.runLater(() -> {
            if(annonces == null){
                this.pageLesUtilisateurs.majLesUtilisateurs(textField.getText());
            }else{
                this.annonces.majLesVentes(textField.getText());
            }
        });
    }
}
