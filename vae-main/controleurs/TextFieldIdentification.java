package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TextFieldIdentification implements EventHandler<KeyEvent>{

    private Button button;

    public TextFieldIdentification(Button button){
        this.button = button;
    }

    @Override
    public void handle(KeyEvent event) {
        
        if(event.getCode().equals(KeyCode.ENTER)){
            this.button.fire();
        }

    }

}