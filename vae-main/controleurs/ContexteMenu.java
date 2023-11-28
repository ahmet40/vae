package controleurs;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import src.Main;

public class ContexteMenu implements EventHandler<MouseEvent> {

    private ContextMenu contexteMenu;
    private ImageView flecheHaut;
    private ImageView flecheBas;
    private Button button;
    private PanelCentralMouseClicked panelCentralMouseClicked;

    public ContexteMenu(ContextMenu contextMenu, Button button){
        this.contexteMenu = contextMenu;
        this.flecheHaut = new ImageView(new Image(new File("./images/flecheHaut.png").toURI().toString()));
        this.flecheHaut.setFitHeight(15);
        this.flecheHaut.setFitWidth(15);
        this.flecheBas = new ImageView(new Image(new File("./images/flecheBas.png").toURI().toString()));
        this.flecheBas.setFitHeight(15);
        this.flecheBas.setFitWidth(15);
        this.button = button;
        this.panelCentralMouseClicked = new PanelCentralMouseClicked(button);
    }

    @Override
    public void handle(MouseEvent event) {
        Bounds bounds = this.button.localToScreen(this.button.getBoundsInLocal());
        if (!this.contexteMenu.isShowing()) {
            this.contexteMenu.show(this.button, bounds.getMinX(), bounds.getMaxY());
            Main.getInstance().getPanelCentral().setOnMouseClicked(this.panelCentralMouseClicked);
            this.button.setGraphic(this.flecheHaut);
        } else {
            this.contexteMenu.hide();
            Main.getInstance().getPanelCentral().setOnMouseClicked(null);
            this.button.setGraphic(this.flecheBas);
        }
    }
    
}
