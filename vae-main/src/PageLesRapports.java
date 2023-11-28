package src;

import javafx.scene.layout.VBox;

import java.util.List;

import controleurs.RapportSupprimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.caches.Aide;
import modele.mysql.SQLAide;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;

public class PageLesRapports extends VBox{
    private Main main;
    private List<Aide> lesRapports;
    public PageLesRapports(Main main){
        super(50);

        this.main = main;
        this.lesRapports = SQLAide.chargerAllAide(main);

        super.setMaxSize(1100, 600);

        super.setPadding(new Insets(20,20,0,20));

        super.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));

        super.setAlignment(Pos.TOP_CENTER);
        super.getChildren().add(this.titre());
        super.getChildren().add(this.slider());
    }
    public Main getMain() {
        return main;
    }

    public Label titre (){
        Label titre = new Label("Carnet de bord");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titre.setTextFill(Color.web("014751"));
        return titre;
    }
    public ScrollPane slider(){
        ScrollPane leSlide = new ScrollPane();
        leSlide.setContent(this.lesBoxRapport());
        leSlide.setPadding(new Insets(20));
        return leSlide;
    }
    public VBox lesBoxRapport(){
        VBox box = new VBox(50);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER_LEFT);
        for(Aide a : this.lesRapports){

            Label nom = new Label("Nom : " + a.getNom());
            Label prenom = new Label("Prenom : " + a.getPrenom());
            Label email = new Label("Email : " + a.getEmail());
            Label objet = new Label("Objet : " + a.getObjet());
            Label message = new Label("Message : " + a.getMessage());
            
            nom.setWrapText(true);
            prenom.setWrapText(true);
            email.setWrapText(true);
            objet.setWrapText(true);
            message.setWrapText(true);

            Button repondre = new Button("Répondre");

            Button supprimer = new Button("Supprimer");
            supprimer.setOnAction(new RapportSupprimer(this, a));

            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(nom, prenom, email, objet, message, new HBox(20,repondre,supprimer));
            vBox.setPadding(new Insets(20, 20, 20, 20));
            vBox.setBorder(new Border(
                new BorderStroke(
                    Color.web("014751"), 
                    BorderStrokeStyle.SOLID, 
                    new CornerRadii(10), 
                    new BorderWidths(2))   
            ));
            vBox.setPrefWidth(900);
            box.getChildren().add(vBox);
        }
        return box;
    }

    public void supprimerUnRapport(Aide aide){
        this.lesRapports.remove(aide);
    }
    public void majLesRapports(Main main){
        super.getChildren().remove(1);
        this.lesRapports = SQLAide.chargerAllAide(main);
        super.getChildren().add(this.slider());
    }

    public Alert popUpValidationSuppression(){
        Alert alert = new Alert(AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le rapport");
        alert.setContentText("Voulez-vous vraiment supprimer ce rapport ?");
        return alert;
    }
}
