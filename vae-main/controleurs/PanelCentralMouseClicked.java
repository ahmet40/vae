package controleurs;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PanelCentralMouseClicked implements EventHandler<MouseEvent> {

    private Button button;
    private ImageView flecheBas;

    public PanelCentralMouseClicked(Button button){
        this.button = button;
        this.flecheBas = new ImageView(new Image(new File("./images/flecheBas.png").toURI().toString()));
        this.flecheBas.setFitHeight(15);
        this.flecheBas.setFitWidth(15);
    }

    @Override
    public void handle(MouseEvent event) {
        this.button.setGraphic(this.flecheBas);
    }
    


}
