package controleurs;

import biblio.BiblioString;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GenererExemple implements EventHandler<ActionEvent> {
    private TextArea textArea;
    private Label label;

    public GenererExemple(TextArea textArea, Label label){
        this.textArea = textArea;
        this.label = label;

    }

    @Override
    public void handle(ActionEvent arg0) {

        int nbMax = 431;
        this.label.setText(BiblioString.nbCaracRestant+ nbMax);
        this.textArea.setText(BiblioString.genererExempleText);
    }


}
