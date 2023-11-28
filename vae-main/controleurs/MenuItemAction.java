package controleurs;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.Main;
import src.Profil;

public class MenuItemAction implements EventHandler<ActionEvent> {

    private Button button;
    private ImageView flecheBas;

    public MenuItemAction(Button button){
        this.button = button;
        this.flecheBas = new ImageView(new Image(new File("./images/flecheBas.png").toURI().toString()));
        this.flecheBas.setFitHeight(15);
        this.flecheBas.setFitWidth(15);
    }

    @Override
    public void handle(ActionEvent event) {
        this.button.setGraphic(this.flecheBas);

        if(event.getSource() instanceof MenuItem){
            MenuItem menuItem = (MenuItem) event.getSource();
            String text = menuItem.getText();
            if(text.equals("Déconnexion")){
                Main main = Main.getInstance();
                main.afficherPopUpDeconnexion();
                main.getAccueil().isAdmin();
                main.getAccueilAdmin().isAdmin();
                main.getAnnonce().isAdmin();
            }else if(text.equals("Quitter")){
                Main.getInstance().afficherPopUpQuitter();
            }else if(text.equals("Profil")){
                Main.getInstance().mettreAuCentre(new Profil());
            }
        }
    }
    
}
