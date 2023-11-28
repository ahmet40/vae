package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class TextArea implements EventHandler<KeyEvent>{
    private int nbMax;
    private Label label;

    public TextArea(int nb, Label label) {
        this.nbMax = nb;
        this.label = label;
    }

    @Override
    public void handle(KeyEvent arg0) {
        javafx.scene.control.TextArea textArea = (javafx.scene.control.TextArea) arg0.getSource();
        this.label.setText("Nombre de caractères restant : " + (nbMax - textArea.getLength()));
    }

}
