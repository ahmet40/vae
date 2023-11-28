package src;

import java.beans.EventHandler;
import java.io.File;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import controleurs.AjoutVente;
import controleurs.ChargerFichier;
import controleurs.ComboObjets;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import modele.caches.Categorie;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Alert;

@SuppressWarnings("all")
public class Enchere {
   private int nombreImage;
   private List<String> lesPhotos;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private ComboBox<String> comboBox;
    private List<String> lesObjetsListe;
    private DatePicker dateDebut;
    private DatePicker dateFin;
    private TextField textField1Titre;
    private TextField textField4Desc;
    private TextField textField5PrixMin;
    private TextField textField6PrixBase;
    private TextField textField7;
    private TextField textField8;
    private TextArea textArea;
    private Button ajoutImage;
    private Button supprimerImage;

    private double longueurImage;
    private double hauteurImage ;
    private java.util.Date date1Debut;
    private java.util.Date date2Fin;

    private Button publierVente;
    
    public Enchere() {
        this.nombreImage = 0;
        this.longueurImage = 250;
        this.hauteurImage = 175;
        this.date1Debut = Date.from(Instant.now());
        this.date2Fin = Date.from(Instant.now());
        this.lesPhotos = new ArrayList<>();
    };

    public BorderPane top() {
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(gauche());
        borderPane.setRight(droite());
        return borderPane;
    }

    public BorderPane gauche() {
        this.lesObjetsListe = new ArrayList<>();
        for (Categorie categorie : Main.getInstance().getCategories()) {
            this.lesObjetsListe.add(categorie.getNomCat());
        }
        this.comboBox = new ComboBox<>();
        this.comboBox.setValue(this.lesObjetsListe.get(0));
        this.comboBox.getItems().addAll(this.lesObjetsListe);

        BorderPane borderPane = new BorderPane();

        Label label = new Label("Crée une enchère");

        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));

        label.setPadding(new Insets(0, 0, 10, 0));
        borderPane.setTop(label);
        borderPane.setLeft(this.formulaire());
        BorderPane.setAlignment(label, Pos.CENTER);
        borderPane.setPadding(new Insets(15));
        return borderPane;
    }

    public GridPane droite() {
        GridPane grid = new GridPane();
        
        String cheminImage = "images/apareilPhot.jpg";
        this.longueurImage = 250;
        this.hauteurImage = 175;
    
        VBox vbox1 = new VBox();
        this.imageView1 = new ImageView(new Image(new File(cheminImage).toURI().toString(), this.longueurImage, this.hauteurImage, true, true));
        vbox1.getChildren().add(imageView1);
        vbox1.setPadding(new Insets(10));
    
        VBox vbox2 = new VBox();
        this.imageView2 = new ImageView(new Image(new File(cheminImage).toURI().toString(), this.longueurImage, this.hauteurImage, true, true));
        vbox2.getChildren().add(imageView2);
        vbox2.setPadding(new Insets(10));
    
        VBox vbox3 = new VBox();
        this.imageView3 = new ImageView(new Image(new File(cheminImage).toURI().toString(), this.longueurImage, this.hauteurImage, true, true));
        vbox3.getChildren().add(imageView3);
        vbox3.setPadding(new Insets(10));
    
        VBox vbox4 = new VBox();
        this.imageView4 = new ImageView(new Image(new File(cheminImage).toURI().toString(), this.longueurImage, this.hauteurImage, true, true));
        vbox4.getChildren().add(imageView4);
        vbox4.setPadding(new Insets(10));

        this.ajoutImage=new Button("Ajouter image");
        ajoutImage.setOnMouseClicked(new ChargerFichier(this));
        this.supprimerImage=new Button("Supprimer image");
        supprimerImage.setOnMouseClicked(e -> supprimerImage());

        controleurs.NavigationBar navigationBar = new controleurs.NavigationBar();

        this.ajoutImage.setOnMouseEntered(navigationBar);
        this.ajoutImage.setOnMouseExited(navigationBar);

        this.supprimerImage.setOnMouseEntered(navigationBar);
        this.supprimerImage.setOnMouseExited(navigationBar);

        this.publierVente=new Button("Publier la vente");
        publierVente.setOnAction(new AjoutVente(this,this.comboBox, this.lesPhotos, this.textField1Titre, this.textArea, textField5PrixMin, textField6PrixBase));

        VBox buttonContainer = new VBox(10); // Create a VBox to hold the buttons
        buttonContainer.setPadding(new Insets(10, 0, 0, 0)); // Add padding above the buttons
        buttonContainer.getChildren().addAll(ajoutImage, supprimerImage,publierVente);

        buttonContainer.setAlignment(Pos.CENTER);

        this.ajoutImage.setBackground(new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                null)
        ));
        this.ajoutImage.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));

        this.supprimerImage.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                null)
        ));
        this.supprimerImage.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        this.supprimerImage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        this.supprimerImage.setPrefHeight(50);
        this.supprimerImage.setPrefWidth(220);
        this.supprimerImage.setTextFill(Color.WHITE);
        this.supprimerImage.setCursor(Cursor.HAND);


        this.ajoutImage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        this.ajoutImage.setPrefHeight(50);
        this.ajoutImage.setPrefWidth(220);
        this.ajoutImage.setTextFill(Color.web("014751"));
        this.ajoutImage.setCursor(Cursor.HAND);


        publierVente.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                null)
        ));
        publierVente.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        publierVente.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        publierVente.setPrefHeight(70);
        publierVente.setPrefWidth(350);
        publierVente.setTextFill(Color.GOLD);
        publierVente.setCursor(Cursor.HAND);



        //ajoutImage.setPadding(new Insets(0, 0, 0, 10));
        grid.add(vbox1, 0, 0);
        grid.add(vbox2, 1, 0);
        grid.add(vbox3, 0, 1);
        grid.add(vbox4, 1, 1);
        grid.add(buttonContainer,0,2,2,1);
        // grid.add(supprimerImage,0,3,2,1);
        grid.setPadding(new Insets(50, 210, 0, 0));
        GridPane.setHalignment(buttonContainer, HPos.CENTER);
        // GridPane.setHalignment(supprimerImage, HPos.CENTER);


        return grid;
    }
    
    public void reset(){
        this.lesPhotos.clear();
        this.nombreImage = 0;
    }

    public void ouvrirGestionnaireFichiers() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("images"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null && selectedFile.length() < 100000) {
            URI fileUri = selectedFile.toURI();
            String filePath = fileUri.getPath();
            changerImage(filePath);
        }
        else {
            this.popUpFichierTropFrand().show();
        }
    }


    public void changerImage(String chemin){
        Image nouvelleImage = new Image(new File(chemin).toURI().toString(), this.longueurImage, this.hauteurImage, true, true);
        if (nombreImage < 4){
            this.lesPhotos.add(chemin);
            if (nombreImage == 0) {
                imageView1.setImage(nouvelleImage);
                nombreImage += 1;
            } else if (nombreImage == 1) {
                imageView2.setImage(nouvelleImage);
                nombreImage += 1;
            } else if (nombreImage == 2) {
                imageView3.setImage(nouvelleImage);
                nombreImage += 1;
            } else if (nombreImage == 3) {
                imageView4.setImage(nouvelleImage);
                nombreImage += 1;
            }
        }
        
    }

    public void supprimerImage(){
        String chemin = "images/apareilPhot.jpg";
        Image nouvelleImage = new Image(new File(chemin).toURI().toString(), this.longueurImage, this.hauteurImage, true, true);
        if (nombreImage>0){
            this.lesPhotos.remove(this.lesPhotos.size()-1);
            if (nombreImage == 1) {
                imageView1.setImage(nouvelleImage);
                nombreImage -= 1;
            } else if (nombreImage == 2) {
                imageView2.setImage(nouvelleImage);
                nombreImage -= 1;
            } else if (nombreImage == 3) {
                imageView3.setImage(nouvelleImage);
                nombreImage -= 1;
            } else if (nombreImage == 4) {
                imageView4.setImage(nouvelleImage);
                nombreImage -= 1;
            }
        }

    }

    public HBox formulaire() {

        HBox hBox = new HBox();
        GridPane gridPane = new GridPane();

        Label label1 = new Label("Titre");
        Label label2 = new Label("Date de début");
        Label label3 = new Label("Date de fin");
        Label label4 = new Label("Catégorie");
        Label label5 = new Label("Votre Objet");
        Label label6 = new Label("Description");
        Label label7 = new Label("Prix min.");
        Label label8 = new Label("Prix de base");

        this.dateDebut = new DatePicker(LocalDate.now());
        this.dateFin = new DatePicker(LocalDate.now());
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, this.date1Debut.getHours());
        Spinner<Integer> minuteeSpinner = new Spinner<>(0, 59, this.date1Debut.getMinutes());

        javafx.event.EventHandler event3 = new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                LocalDate selectedDate=dateDebut.getValue();
                int selectedHour = hourSpinner.getValue();
                int selectedMinute = minuteeSpinner.getValue();

                LocalTime selectedTime = LocalTime.of(selectedHour,selectedMinute);
                //LocalDateTime dateTime = LocalDateTime.of(selectedDate, selectedTime);

                //System.out.println("Date et heure sélectionnées : " + dateTime);
                date1Debut.setTime(new java.util.Date(selectedDate.getYear()-1900, selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth(), selectedTime.getHour(), selectedTime.getMinute()).getTime()); 
            }
        };

        javafx.event.EventHandler event4 = new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                LocalDate selectedDate=dateDebut.getValue();
                int selectedHour = hourSpinner.getValue();
                int selectedMinute = minuteeSpinner.getValue();

                LocalTime selectedTime = LocalTime.of(selectedHour,selectedMinute);
                //LocalDateTime dateTime = LocalDateTime.of(selectedDate, selectedTime);

                //System.out.println("Date et heure sélectionnées : " + dateTime);
                date1Debut.setTime(new java.util.Date(selectedDate.getYear()-1900, selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth(), selectedTime.getHour(), selectedTime.getMinute()).getTime()); 
            }
        };

        this.dateDebut.setOnAction(event3);
        hourSpinner.setOnMouseClicked(event4);
        minuteeSpinner.setOnMouseClicked(event4);

        Spinner<Integer> hourSpinnerFin = new Spinner<>(0, 23, this.date2Fin.getHours());
        Spinner<Integer> minuteeSpinnerFin = new Spinner<>(0, 59, this.date2Fin.getMinutes());

        javafx.event.EventHandler<ActionEvent> event = new javafx.event.EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg0) {
                LocalDate selectedDate=dateFin.getValue();
                int selectedHour = hourSpinnerFin.getValue();
                int selectedMinute = minuteeSpinnerFin.getValue();

                LocalTime selectedTime = LocalTime.of(selectedHour,selectedMinute);
                date2Fin.setTime(new java.util.Date(selectedDate.getYear()-1900, selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth(), selectedTime.getHour(), selectedTime.getMinute()).getTime()); 
            };
        };
        javafx.event.EventHandler<MouseEvent> eventMouse = new javafx.event.EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                LocalDate selectedDate=dateFin.getValue();
                int selectedHour = hourSpinnerFin.getValue();
                int selectedMinute = minuteeSpinnerFin.getValue();

                LocalTime selectedTime = LocalTime.of(selectedHour,selectedMinute);
                date2Fin.setTime(new java.util.Date(selectedDate.getYear()-1900, selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth(), selectedTime.getHour(), selectedTime.getMinute()).getTime()); 
            };
        };

        this.dateFin.setOnAction(event);
        hourSpinnerFin.setOnMouseClicked(eventMouse);
        minuteeSpinnerFin.setOnMouseClicked(eventMouse);


        LocalDate dateLimite = LocalDate.now();
        this.dateDebut.setDayCellFactory(getDatePickerCellFactory(dateLimite));

        LocalDate dateLimite2 = LocalDate.now().plusDays(1);
        this.dateFin.setDayCellFactory(getDatePickerCellFactory(dateLimite2));


        this.textField1Titre = new TextField();
        textField1Titre.setPromptText("PS5");



        this.textField4Desc = new TextField();
        textField4Desc.setPromptText("Belle voiture");

        this.textField5PrixMin = new TextField("0");
        textField5PrixMin.setPromptText("5,50");

        this.textField6PrixBase = new TextField("0");
        textField6PrixBase.setPromptText("6,50");

        this.textArea = new TextArea();
        this.textArea.setWrapText(true);

        int nbCaracMax = 750;

        Label labelNbCaracRestant = new Label("Nombre de caractères restant : " + nbCaracMax);

        this.textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > nbCaracMax) {
                // Si le texte dépasse la limite maximale, le raccourcir
                this.textArea.setText(newValue.substring(0, nbCaracMax));
            }
        });

        this.textArea.setOnKeyTyped(new controleurs.TextArea(nbCaracMax, labelNbCaracRestant));

        this.textField7 = new TextField();
        textField7.setPromptText("10");
        this.textField8 = new TextField();
        textField8.setPromptText("Belle voiture");

        Label heureDebut = new Label("Heure de début");
        Label minuteDebut = new Label("Minute de début");
        Label heureFin = new Label("Heure de fin");
        Label minuteFin = new Label("Minute de fin");

        gridPane.add(label1, 0, 0, 1, 1);
        gridPane.add(this.textField1Titre, 0, 1, 1, 1);

        gridPane.add(label4, 1, 0, 1, 1);
        gridPane.add(this.comboBox, 1, 1, 1, 1);

        gridPane.add(label2, 0, 6, 1, 1);
        gridPane.add(this.dateDebut, 0, 7, 1, 1);

        gridPane.add(label3, 1, 6, 1, 1);
        gridPane.add(this.dateFin, 1, 7, 1, 1);



        gridPane.add(heureDebut, 0, 2, 1, 1);
        gridPane.add(hourSpinner, 0, 3, 1, 1);

        gridPane.add(minuteDebut, 1, 2, 1, 1);
        gridPane.add(minuteeSpinner, 1, 3, 1, 1);


        
        gridPane.add(heureFin, 0, 4, 1, 1);
        gridPane.add(hourSpinnerFin, 0, 5, 1, 1);

        gridPane.add(minuteFin, 1, 4, 1, 1);
        gridPane.add(minuteeSpinnerFin, 1, 5, 1, 1);






        gridPane.add(label6, 0, 8, 1, 1);
        gridPane.add(this.textArea, 0, 9, 2, 1);
        gridPane.add(labelNbCaracRestant, 0, 10, 1, 1);



        gridPane.add(label7, 0, 11, 1, 1);
        gridPane.add(this.textField5PrixMin, 0, 12, 1, 1);







        gridPane.add(label8, 1, 11, 1, 1);
        gridPane.add(this.textField6PrixBase, 1, 12, 1, 1);


        this.comboBox.setOnAction(new ComboObjets(textField4Desc, label5));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setPadding(new Insets(10));

        gridPane.setBorder(new Border(new BorderStroke(Color.web("014751"), BorderStrokeStyle.SOLID,
                new CornerRadii(10), new BorderWidths(2))));

        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(gridPane);
        return hBox;

    }


    private Callback<DatePicker, DateCell> getDatePickerCellFactory(LocalDate dateLimite) {
        return new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Désactiver les dates antérieures à la date limite inférieure
                        if (item.isBefore(dateLimite)) {
                            setDisable(true);
                            setStyle("-fx-text-fill: gray");
                        }
                    }
                };
            }
        };
    }

    public java.util.Date getDateDebut() {
        return this.date1Debut;
    }

    public java.util.Date getDateFin() {
        return this.date2Fin;
    }

    public Alert popupNonSoumettre(String message){
        String messageAlert = message;
        String title = "Erreur";
        Alert alert = new Alert(Alert.AlertType.WARNING,messageAlert);
        alert.setTitle(title);
        return alert;
    }

    public Alert popUpInsertionVente(String message){
        String messageAlert = message;
        String title = "Confirmation";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,messageAlert,ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        return alert;
    }

    public Alert popUpFichierTropFrand() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur Fichier");
        alert.setHeaderText("Erreur Fichier");
        alert.setContentText("Le fichier sélectionné est trop lourd" + "\n" + "il ne doit pas dépassé 100 ko");
        return alert;
    }

}
