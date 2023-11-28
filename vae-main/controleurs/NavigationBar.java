package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class NavigationBar implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

        Object source = event.getSource();

        if(source instanceof Label){

            Label src = (Label) source;

            src.setBorder(new Border(
                new BorderStroke(
                        (((Color) src.getBorder().getStrokes().get(0).getLeftStroke()).toString().equals("0x00000000")) ? Color.web("014751") : Color.TRANSPARENT, 
                        BorderStrokeStyle.SOLID, 
                        CornerRadii.EMPTY, 
                        new BorderWidths(0,0,1.2,0)
                    ))
                );

        }else if(source instanceof Button){

            Button src = (Button) source;

            Color color = (Color) src.getBackground().getFills().get(0).getFill();

            if(color.toString().equals("0x014751ff") || color.toString().equals("0xffffffff")){
                src.setBackground(new Background(
                    new BackgroundFill(
                        Color.web("006b7b"),
                        new CornerRadii(10),
                        null)
                ));
                src.setBorder(new Border(
                    new BorderStroke(
                        Color.web("006b7b"), 
                        BorderStrokeStyle.SOLID, 
                        new CornerRadii(10), 
                        new BorderWidths(2))   
                ));
                src.setTextFill(Color.WHITE);
            }else{
                if(src.getText().equals("Connexion") || src.getText().equals("Ajouter image") || src.getText().equals("Exemple") || src.getText().equals("Reset")){
                    src.setBackground(new Background(
                        new BackgroundFill(
                            Color.WHITE,
                            new CornerRadii(10),
                            null)
                    ));
                    src.setBorder(new Border(
                        new BorderStroke(
                            Color.web("014751"), 
                            BorderStrokeStyle.SOLID, 
                            new CornerRadii(10), 
                            new BorderWidths(2))   
                    ));
                    src.setTextFill(Color.web("014751"));
                }else{
                    src.setBackground(new Background(
                        new BackgroundFill(
                            Color.web("014751"),
                            new CornerRadii(10),
                            null)
                        ));
                    src.setBorder(new Border(
                        new BorderStroke(
                            Color.web("014751"), 
                            BorderStrokeStyle.SOLID, 
                            new CornerRadii(10), 
                            new BorderWidths(2))   
                    ));
                }
            }

        }

    }
    


}
