package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ComboObjets implements EventHandler<ActionEvent>{
    private TextField textField4;
    private Label label;

    public ComboObjets(TextField textField4, Label label) {
        this.textField4 = textField4;
        this.label = label;
    }

    @Override
    public void handle(ActionEvent arg0) {
        @SuppressWarnings("unchecked")
        ComboBox<String> comboBox = (ComboBox<String>) arg0.getSource();
        if (comboBox.getValue().equals("Autre")) {
            System.out.println(comboBox.getValue());
            this.textField4.setVisible(true);
            this.label.setVisible(true);
            this.textField4.clear();
        }else{
            this.textField4.setVisible(false);
            this.label.setVisible(false);
        }
    }

}
