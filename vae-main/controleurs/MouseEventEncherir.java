package controleurs;

import java.util.List;

import biblio.BiblioString;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import modele.caches.Encherir;
import modele.caches.Objet;
import modele.caches.Photo;
import modele.caches.Vente;
import src.Connexion;
import src.Main;

public class MouseEventEncherir implements EventHandler<MouseEvent> {

    private Button button;
    private Vente vente;
    private Objet objet;
    private Encherir enchere;
    private List<Photo> photo;

    public MouseEventEncherir(Button button, Vente vente, Objet objet, Encherir encherir, List<Photo> photo){
        this.button = button;
        this.vente = vente;
        this.objet = objet;
        this.enchere = encherir;
        this.photo = photo;
    }

    @Override
    public void handle(MouseEvent event) {
     
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){

            this.button.setBackground(new Background(
                    new BackgroundFill(
                        Color.web(BiblioString.couleurString,0.3),
                        new CornerRadii(20),
                        Insets.EMPTY
                    )));
        }else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
            this.button.setBackground(new Background(
                    new BackgroundFill(
                        Color.web(BiblioString.couleurString),
                        new CornerRadii(20),
                        Insets.EMPTY
                    )));
        }else if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if(!button.getText().equals("Supprimer")){
                Main main = Main.getInstance();
                if(!main.isConnected()) {
                    Connexion connection = main.getConnexion();
                    main.mettreAuCentre(connection);
                    Platform.runLater(() -> connection.focusRequest());
                }
                else {
                    main.mettreAuCentre(main.getEncherirObjet().laPage(this.vente, this.objet, this.enchere, this.photo));
                }
            }else{
                Main.getInstance().popUpSurDuChoix(this.vente,this.objet);
            }
        }

    }

}
