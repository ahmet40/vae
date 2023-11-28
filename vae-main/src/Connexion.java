package src;

import java.io.File;

import controleurs.Connection;
import controleurs.HideShowPassword;
import controleurs.Identification;
import controleurs.TextFieldIdentification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Connexion extends VBox {

    private Label pseudoErreur;

    private TextField pseudo;
    private TextField password;
    private PasswordField passwordP;
    private ImageView hideShowImageView;
    
    public Connexion(){

        super(50);

        super.getChildren().add(this.dejaClient());
        super.getChildren().add(this.pseudoBox());
        super.getChildren().add(this.motDePasseBox());
        super.getChildren().add(this.seConnecter());
        super.getChildren().add(this.nouveauClientBox());

        super.setMaxSize(500, 600);

        super.setPadding(new Insets(20,20,0,20));

        super.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));

        super.setAlignment(Pos.TOP_CENTER);

    }

    private Label dejaClient(){

        Label dejaClient = new Label("Déjà client ?");
        
        dejaClient.setFont(Font.font("Arial", FontWeight.NORMAL, 35));
        dejaClient.setTextFill(Color.web("014751"));

        return dejaClient;

    }

    private VBox pseudoBox(){

        VBox pseudoBox = new VBox(this.pseudoLabel(),this.pseudoField());

        return pseudoBox;

    }

    private HBox pseudoLabel(){

        HBox hBox = new HBox();

        Label pseudo = new Label("Pseudo");
        this.pseudoErreur = new Label("");
        
        pseudo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        pseudo.setTextFill(Color.web("014751"));
        this.pseudoErreur.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        this.pseudoErreur.setTextFill(Color.RED);

        hBox.getChildren().addAll(pseudo, this.pseudoErreur);

        return hBox;

    }

    private TextField pseudoField(){

        this.pseudo = new TextField();

        this.pseudo.setPrefHeight(40);

        return this.pseudo;
        
    }

    private VBox motDePasseBox(){

        VBox motDePasseBox = new VBox(this.motDePasseLabel(),this.motDePasseField());

        return motDePasseBox;

    }

    private Label motDePasseLabel(){

        Label motDePasse = new Label("Mot de passe");

        motDePasse.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        motDePasse.setTextFill(Color.web("014751"));

        return motDePasse;

    }

    private HBox motDePasseField(){

        HBox hBox = new HBox();
        this.password = new TextField();
        this.password.setMinWidth(415);
        this.passwordP = new PasswordField();
        this.passwordP.setMinWidth(415);

        this.password.setPrefHeight(40);
        this.passwordP.setPrefHeight(40);

        this.password.setVisible(false);
        this.passwordP.setVisible(true);
        
        this.password.setManaged(false);
        this.passwordP.setManaged(true);

        Button button = new Button();
        button.setBackground(null);
        button.setBorder(null);
        this.hideShowImageView = new ImageView( new Image( new File("./images/oeilOuvert.png").toURI().toString() ) );
        button.setOnMouseClicked(new HideShowPassword(this));
        this.hideShowImageView.setFitHeight(40);
        this.hideShowImageView.setFitWidth(40);
        button.setGraphic(this.hideShowImageView);

        StackPane stackPane = new StackPane(this.password,this.passwordP);

        hBox.getChildren().addAll(stackPane,button);

        return hBox;
        
    }

    private Button seConnecter(){

        Button seConnecter = new Button("Se connecter");

        seConnecter.setOnAction(new Connection(this));

        TextFieldIdentification textFieldIdentification = new TextFieldIdentification(seConnecter);

        this.password.setOnKeyPressed(textFieldIdentification);
        this.passwordP.setOnKeyPressed(textFieldIdentification);

        seConnecter.setBackground(null);
        seConnecter.setBorder(new Border(
            new BorderStroke(
                    Color.web("014751"), 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    new BorderWidths(0,0,1.2,0)
                ))
            );
        seConnecter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        seConnecter.setTextFill(Color.web("014751"));
        seConnecter.setCursor(Cursor.HAND);

        return seConnecter;

    }

    private VBox nouveauClientBox(){

        VBox nouveauClientBox = new VBox(10, this.nouveauClient(),this.sInscrire());
        
        nouveauClientBox.setAlignment(Pos.CENTER);

        nouveauClientBox.setBackground(new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                null)
        ));
        nouveauClientBox.setPrefHeight(100);

        return nouveauClientBox;

    }

    private Label nouveauClient(){

        Label nouveauClient = new Label("Nouveau client ?");

        nouveauClient.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        nouveauClient.setTextFill(Color.web("014751"));

        return nouveauClient;

    }

    private Button sInscrire(){

        Button sInscrire = new Button("S'inscrire");

        sInscrire.setOnMouseClicked(new Identification());

        sInscrire.setBackground(null);
        sInscrire.setBorder(new Border(
            new BorderStroke(
                    Color.web("014751"), 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    new BorderWidths(0,0,1.2,0)
                ))
            );
        sInscrire.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        sInscrire.setTextFill(Color.web("014751"));
        sInscrire.setCursor(Cursor.HAND);

        return sInscrire;

    }

    public void focusRequest(){
        this.pseudo.requestFocus();
    }

    public void clearField(){
        this.hideShowImageView.setImage(new Image( new File("./images/oeilOuvert.png").toURI().toString() ) );
        this.pseudo.clear();
        this.password.clear();
        this.passwordP.clear();
        this.password.setVisible(false);
        this.password.setManaged(false);
        this.passwordP.setVisible(true);
        this.passwordP.setManaged(true);
        this.pseudo.setBorder(null);
        this.password.setBorder(null);
        this.passwordP.setBorder(null);
        this.pseudoErreur.setText(null);
    }

    public Label getPseudoErreur() {
        return this.pseudoErreur;
    }

    public TextField getPseudo() {
        return this.pseudo;
    }

    public TextField getPassword() {
        return this.password;
    }

    public PasswordField getPasswordP() {
        return this.passwordP;
    }

    public ImageView getHideShowImageView() {
        return this.hideShowImageView;
    }
    
}
