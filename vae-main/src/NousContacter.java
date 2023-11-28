package src;

import java.util.Arrays;
import java.util.List;

import controleurs.GenererExemple;
import controleurs.Soumettre;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NousContacter{
    
    private int nbCaracMax;
    private ComboBox<String> comboBoxObjet;
    private List<String> lesObjetsListe;
    private boolean mailCorrect;
    private TextField textFieldNom; 
    private TextField textFieldPrenom; 
    private TextField textFieldEmail; 
    private TextField textFieldObjet; 
    private TextArea textAreaMessage;
    private Main main;
    private controleurs.NavigationBar navigationBar;

    public NousContacter(Main main) {
        this.main = main;
        this.nbCaracMax = 500;
        this.lesObjetsListe = Arrays.asList("Signalement", "Aide", "Test", "Autre");
        this.mailCorrect = false;
        this.textFieldNom = creeTextFieldSansChiffres();
        this.textFieldPrenom = creeTextFieldSansChiffres();
        this.textFieldEmail = new TextField();
        this.textFieldObjet = new TextField();
        this.textAreaMessage = new TextArea();
        this.navigationBar = new controleurs.NavigationBar();
    };

    public BorderPane top() {
        this.comboBoxObjet = new ComboBox<>();

        BorderPane borderPane = new BorderPane();

        Label label = new Label("Centre d'aide");
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
        label.setPadding(new Insets(0, 0, 10, 0));

        borderPane.setTop(label);
        borderPane.setCenter(this.formulaire());
        borderPane.setPadding(new Insets(15));
        BorderPane.setAlignment(label, Pos.CENTER);

        return borderPane;
    }

    public HBox formulaire() {
        HBox hBox = new HBox();
        GridPane gridPane = new GridPane();
        
        Label labelNom = new Label("Nom");
        Label labelEmail = new Label("Votre email");
        Label labelPrenom = new Label("Prénom");
        Label labelObjetParDefaut = new Label("Objet de la demande");
        Label labelObjetPersonnalise = new Label("Votre Objet");
        Label labelMessage = new Label("Votre message");
        Label labelNbCaracRestant = new Label("Nombre de caractères restant : " + nbCaracMax);

        this.textFieldNom.setPromptText("Dupond");
        this.textFieldPrenom.setPromptText("Jean");        
        this.textFieldEmail.setPromptText("lesupervendeur45@gmail.com");
        this.textFieldEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0){
                textFieldEmail.setStyle("");
            }
            else {
                if (!newValue.contains("@")) {
                    textFieldEmail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                    this.mailCorrect = false;
                }
                else {
                    if (newValue.contains(" ")){
                        textFieldEmail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                        this.mailCorrect = false;
                    }
                    else if (newValue.contains(".com")){
                        textFieldEmail.setStyle(""); // Rétablissez le style par défaut si "@" et ".com" est présent
                        this.mailCorrect = true;
                    }
                    else if (newValue.contains(".fr")){
                        textFieldEmail.setStyle(""); // Rétablissez le style par défaut si bleu si "@" et ".fr" est présent
                        this.mailCorrect = true;
                    }
                    else {
                        textFieldEmail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                        this.mailCorrect = false;
                    }
                }
            }
        });
        this.textAreaMessage.setPrefWidth(200);
        this.textAreaMessage.setWrapText(true);

        Tooltip infoBulleEnvoyer = new Tooltip("Bouton permettant d'envoyer votre demande");
        Tooltip infoBulleExample = new Tooltip("Bouton permettant de préremplir le message de votre demande");
        Tooltip infoBulleReset = new Tooltip("Bouton permettant d'effacer tout les champs");

        Button soummetreButton = new Button("Envoyer");
        Button genererExemple = new Button("Exemple");
        Button reset = new Button("Reset");

        soummetreButton.setOnMouseEntered(this.navigationBar);
        soummetreButton.setOnMouseExited(this.navigationBar);

        reset.setOnMouseEntered(this.navigationBar);
        reset.setOnMouseExited(this.navigationBar);

        genererExemple.setOnMouseEntered(this.navigationBar);
        genererExemple.setOnMouseExited(this.navigationBar);

        genererExemple.setBackground(new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                null)
        ));
        genererExemple.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        genererExemple.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        genererExemple.setPrefHeight(50);
        genererExemple.setPrefWidth(120);
        genererExemple.setTextFill(Color.web("014751"));
        genererExemple.setCursor(Cursor.HAND);


        soummetreButton.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                null)
        ));
        soummetreButton.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        soummetreButton.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        soummetreButton.setPrefHeight(50);
        soummetreButton.setPrefWidth(120);
        soummetreButton.setTextFill(Color.WHITE);
        soummetreButton.setCursor(Cursor.HAND);


        reset.setBackground(new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                null)
        ));
        reset.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        reset.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        reset.setPrefHeight(50);
        reset.setPrefWidth(120);
        reset.setTextFill(Color.web("014751"));
        reset.setCursor(Cursor.HAND);

        soummetreButton.setOnAction(new Soumettre(this.mailCorrect, this, labelNbCaracRestant));
        genererExemple.setOnAction(new GenererExemple(textAreaMessage, labelNbCaracRestant));
        reset.setOnAction(new controleurs.Reset(this, labelNbCaracRestant));

        soummetreButton.setTooltip(infoBulleEnvoyer);
        genererExemple.setTooltip(infoBulleExample);
        reset.setTooltip(infoBulleReset);
        
        GridPane.setHalignment(genererExemple, HPos.LEFT);
        GridPane.setHalignment(reset, HPos.RIGHT);
        GridPane.setHalignment(soummetreButton, HPos.CENTER);

        textFieldObjet.setVisible(false);
        labelObjetPersonnalise.setVisible(false);

        this.comboBoxObjet.getItems().addAll(this.lesObjetsListe);
        this.comboBoxObjet.setOnAction(new controleurs.ComboObjets(textFieldObjet, labelObjetPersonnalise));
        this.comboBoxObjet.setValue(this.lesObjetsListe.get(0));

        textAreaMessage.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > nbCaracMax) {
                // Si le texte dépasse la limite maximale, le raccourcir
                textAreaMessage.setText(newValue.substring(0, nbCaracMax));
            }
        });

        textAreaMessage.setOnKeyTyped(new controleurs.TextArea(nbCaracMax, labelNbCaracRestant));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setPadding(new Insets(10));

        gridPane.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));

        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(gridPane);

        HBox hBox2 = new HBox(30);
        hBox2.getChildren().addAll(genererExemple,soummetreButton, reset);

        gridPane.add(labelNom, 0, 0, 1, 1);
        gridPane.add(labelPrenom, 1, 0, 1, 1);
        gridPane.add(textFieldNom, 0, 1, 1, 1);
        gridPane.add(textFieldPrenom, 1, 1, 1, 1);
        gridPane.add(labelEmail, 0, 2, 1, 1);
        gridPane.add(textFieldEmail, 0, 3, 2, 1);
        gridPane.add(labelObjetParDefaut, 0, 4, 1, 1);
        gridPane.add(this.comboBoxObjet, 0, 5, 1, 1);
        gridPane.add(textFieldObjet, 1, 5, 1, 1);
        gridPane.add(labelObjetPersonnalise, 1, 4, 1, 1);
        gridPane.add(textAreaMessage, 0, 7, 2, 1);
        gridPane.add(labelMessage, 0, 6, 1, 1);
        gridPane.add(labelNbCaracRestant, 0, 8, 1, 1);
        gridPane.add(hBox2, 0, 10, 2, 1);

        return hBox;
    }

    private TextField creeTextFieldSansChiffres() {
        TextField textField = new TextField();

        // Définir le TextFormatter avec une expression régulière pour interdire les chiffres
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String nouveauTexte = change.getControlNewText();
            if (nouveauTexte.matches("[a-zA-Z]*")) {
                return change;
            }
            return null;
        });        

        // Appliquer le TextFormatter au TextField
        textField.setTextFormatter(textFormatter);

        return textField;
    }

    public boolean getMail() {
        return this.mailCorrect;
    }

    public Main getMain() {
        return this.main;
    }

    public TextField getTextFieldNom() {
        return this.textFieldNom;
    }

    public TextField getTextFieldPrenom() {
        return this.textFieldPrenom;
    }

    public TextField getTextFieldMail() {
        return this.textFieldEmail;
    }

    public TextField getTextFieldObjet() {
        return this.textFieldObjet;
    }

    public ComboBox<String> getComboBox() {
        return this.comboBoxObjet;
    }

    public TextArea getTextMessage() {
        return this.textAreaMessage;
    }

    public int getNbCaracMax() {
        return this.nbCaracMax;
    }

    public void setNb(int valeur){
        this.nbCaracMax = valeur;
    }

    public Alert popupSoumettre(){
        String messageAlert = "Le message a été envoyé. \n\nMerci de nous avoir contacté.";
        String title = "Envoyé";
        Alert alert = new Alert(Alert.AlertType.INFORMATION,messageAlert);
        alert.setTitle(title);
        return alert;
    }

    public Alert popupNonSoumettre(){
        String messageAlert = "Le message n'a pas été envoyé. \n\nMerci de remplir tous les champs.";
        String title = "Erreur";
        Alert alert = new Alert(Alert.AlertType.WARNING,messageAlert);
        alert.setTitle(title);
        return alert;
    }

    public Alert popupConfirmationReset() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment réinitialiser tout le formulaire ?", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setPrefSize(400, 200);
        alert.setTitle("Réinisialiser Formulaire");
        return alert;
    }

}
