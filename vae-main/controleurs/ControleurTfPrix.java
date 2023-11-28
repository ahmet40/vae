package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControleurTfPrix implements EventHandler<KeyEvent> {

    private Label labelPrix;

    public ControleurTfPrix(Label labelPrix) {
        this.labelPrix = labelPrix;
    }

    @Override
    public void handle(KeyEvent event) {

        TextField textField = (TextField) event.getSource();
        try{
            if (Integer.parseInt(textField.getText()) >= Integer.parseInt(this.labelPrix.getText())) {
                textField.setStyle("-fx-border-color: blue;");
            }
            else {
                textField.setStyle("-fx-border-color: red;");
            }
        }catch(NumberFormatException ignored){
            textField.setStyle("-fx-border-color: red;");
        }
    }
}
