package src;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controleurs.AfficherPlusResultat;
import controleurs.MouseEventEncherir;
import controleurs.Recherche;
import javafx.application.Platform;
import javafx.fxml.LoadException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.caches.Vente;
import modele.mysql.SQLPhoto;
import modele.caches.Objet;
import modele.caches.Encherir;
import modele.caches.Photo;

public class Annonce extends VBox {

    private int page;
    private VBox vBox;
    private TextField barreRecherche;
    private List<Button> button;
    
    public Annonce(){

        this.page = 1;

        ScrollPane scrollPane = new ScrollPane();

        this.button = new ArrayList<>();

        this.barreRecherche = this.barreRecherche();
        scrollPane.setContent(this.vBox());

        HBox hBox = new HBox(this.barreRecherche);
        hBox.setAlignment(Pos.CENTER);
        super.getChildren().addAll(hBox, scrollPane);

    }

    public VBox vBox(){
        if(this.vBox == null) {
            this.vBox = new VBox(20);
            this.vBox.setPadding(new Insets(60));
        }
        List<Vente> ventes = Main.getInstance().getVentes();
        HBox hBox = new HBox(20);
        int check = 0;
        boolean afficherBouton = true;
        VBox vBox;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime finVe;
        LocalDateTime currentDateTime = LocalDateTime.now();
        for(int i = 50*(this.page-1); i < 50*this.page; ++i){
            if(i >= ventes.size()) {
                afficherBouton = false;
                break;
            }
            finVe = LocalDateTime.parse(ventes.get(i).getFinVe(), formatter);
            if(finVe.isBefore(currentDateTime)) continue;
            vBox = this.annonce(ventes.get(i));
            if(vBox != null) hBox.getChildren().add(vBox);
            if(check == 3) {
                this.vBox.getChildren().add(hBox);
                hBox = new HBox(20);
                check = -1;
            }
            ++check;
        }
        if(afficherBouton){
            Button button = new Button("Afficher plus de résultat");
            button.setOnMouseClicked(new AfficherPlusResultat(this));
            this.vBox.getChildren().add(button);
        }
        return this.vBox;
    }
    
    private VBox annonce(Vente vente){
        VBox annonce = new VBox(20);

        annonce.setPadding(new Insets(10));
        annonce.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                Insets.EMPTY
            )
        ));

        Main main = Main.getInstance();

        List<Photo> photos = Main.getInstance().getPhotos(vente.getIdOb());
        Objet objet = Main.getInstance().getObjet(vente.getIdOb());

        HBox hBox = new HBox(20);
        Label nomOb = new Label(objet.getNomOb());
        nomOb.setTextFill(Color.WHITE);
        nomOb.setWrapText(true);
        Label descriptionOb = new Label(objet.getDescriptionOb());
        descriptionOb.setTextFill(Color.WHITE);
        descriptionOb.setWrapText(true);
        Encherir encherir = Main.getInstance().getMaxMontant(vente.getIdVe());
        Label montant = new Label("Prix actuelle : " + vente.getPrixBase() + "€");
        montant.setTextFill(Color.WHITE);
        if(encherir != null) montant.setText("Prix actuelle : " + encherir.getMontant() + "€");

        if(!photos.isEmpty()){
            ImageView imageView = new ImageView(SQLPhoto.avoirImage(photos.get(0)));
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            hBox.getChildren().add(imageView);
            annonce.getChildren().add(hBox);
        }else{
            ImageView imageView = new ImageView(new Image(new File("./images/apareilPhot.jpg").toURI().toString()));
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            hBox.getChildren().add(imageView);
            annonce.getChildren().add(hBox);
        }

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(nomOb,montant);

        hBox.getChildren().add(vBox);
        annonce.getChildren().add(descriptionOb);

        HBox hBox2 = new HBox(20);
        Button button = new Button("Enchérir");
        button.setBorder(new Border(
            new BorderStroke(
                Color.web("006b7b"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2)
            )
        ));
        button.setBackground(new Background(
            new BackgroundFill(
                Color.web("006b7b"),
                new CornerRadii(10),
                Insets.EMPTY
            )));
        if(main.getPersonneConnecte() != null && main.getPersonneConnecte().getRole().equals("Administrateur")){
            button.setText("Supprimer");
            button.setTextFill(Color.RED);
        }else button.setTextFill(Color.WHITE);
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        button.setCursor(Cursor.HAND);
        MouseEventEncherir mouseEventEncherir = new MouseEventEncherir(button, vente, objet, encherir, photos);
        button.setOnMouseClicked(mouseEventEncherir);
        button.setOnMouseEntered(mouseEventEncherir);
        button.setOnMouseExited(mouseEventEncherir);
        hBox2.getChildren().add(button);
        this.button.add(button);
        hBox2.setAlignment(Pos.CENTER);
        Label label = new Label("Auteur de la vente : " + main.getUtilisateur(vente.getIdOb()).getPseudoUt());
        label.setWrapText(true);
        label.setTextFill(Color.WHITE);
        annonce.getChildren().addAll(label, hBox2);

        annonce.setPrefSize(250, 350);

        return annonce;
    }

    public void afficherPlusDeResultat(){
        ++this.page;
        this.vBox.getChildren().remove(this.vBox.getChildren().size()-1);
        this.vBox();
    }

    public TextField barreRecherche(){
        this.barreRecherche = new TextField();
        this.barreRecherche.setPromptText("Rechercher une vente");
        this.barreRecherche.setMaxWidth(300);
        this.barreRecherche.setMinWidth(300);
        this.barreRecherche.setMaxHeight(30);
        this.barreRecherche.setMinHeight(30);
        this.barreRecherche.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        this.barreRecherche.setMinSize(500, 50);
        this.barreRecherche.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        this.barreRecherche.setBackground(
            new Background(
                new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),
                    Insets.EMPTY
                )
            )
        );

        this.barreRecherche.setOnKeyTyped(new Recherche(this));

        return this.barreRecherche;
    }

    public void majLesVentes(String recherche){
        System.gc();
        ScrollPane scrollPane = (ScrollPane) super.getChildren().get(super.getChildren().size()-1);
        if(recherche == null || recherche.equals(" ") || recherche.equals("")){
            scrollPane.setContent(this.vBox());
        }else{
            scrollPane.setContent(this.vBoxRecherche(recherche));
        }
    }
    public void majAffichage(){
        System.gc();
        ScrollPane scrollPane = (ScrollPane) super.getChildren().get(super.getChildren().size()-1);
        scrollPane.setContent(this.vBoxRecherche(null));
    }

    public List<Vente> recherche(String recherche){
        List<Vente> lesVentes = new ArrayList<>();
        List<String> nomVentes = Main.getInstance().getNomVente();
        for (String nomVente : nomVentes) {
            if(nomVente != null && (recherche == null || (recherche.length() > 2 && (nomVente.toLowerCase().contains(recherche.toLowerCase())))
            || nomVente.toLowerCase().startsWith(recherche.toLowerCase()))  )
                lesVentes.add(Main.getInstance().getVente(nomVente));
        }
        return lesVentes;
    }

    public void isAdmin(){
        Main main = Main.getInstance();
        for (Button button : this.button) {
            if(main.getPersonneConnecte() != null && main.getPersonneConnecte().getRole().equals("Administrateur")){
                button.setText("Supprimer");
                button.setTextFill(Color.RED);
            }else{
                button.setText("Enchérir");
                button.setBorder(new Border(
                    new BorderStroke(
                        Color.web("006b7b"),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(10),
                        new BorderWidths(2)
                    )
                ));
                button.setBackground(new Background(
                    new BackgroundFill(
                        Color.web("006b7b"),
                        new CornerRadii(10),
                        Insets.EMPTY
                    )));
                button.setTextFill(Color.WHITE);
                button.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                button.setCursor(Cursor.HAND);
            }
        }
    }

    public VBox vBoxRecherche(String recherche){
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(60));
        List<Vente> lesVentes = this.recherche(recherche);
        if(!lesVentes.isEmpty()){
            Platform.runLater(() -> {
                HBox hBox = new HBox(20);
                int index = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime finVe;
                LocalDateTime currentDateTime = LocalDateTime.now();
                for (Vente vente : lesVentes) {
                    finVe = LocalDateTime.parse(vente.getFinVe(), formatter);
                    if(finVe.isBefore(currentDateTime)) continue;
                    if(index == 4){
                        vBox.getChildren().add(hBox);
                        hBox = new HBox(20);
                        index = 0;
                    }
                    hBox.getChildren().add(this.annonce(vente));
                    ++index;
                }
                vBox.getChildren().add(hBox);
            });
        }
        else if(lesVentes.isEmpty()){
            vBox.getChildren().add(this.aucunResultat());
            vBox.setAlignment(Pos.CENTER);
        }
        return vBox;
    }

    public VBox aucunResultat(){
        VBox aucunRes = new VBox(50);
        aucunRes.setAlignment(Pos.CENTER);
        aucunRes.setPadding(new Insets(0,0,0,450));

        ImageView emojiInquiet = new ImageView(new Image("images/emojiInquiet.png"));
        emojiInquiet.setFitHeight(100);
        emojiInquiet.setFitWidth(100);

        Label aucunResLabel = new Label("Il n'y a aucun résultat");
        aucunResLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        aucunRes.getChildren().addAll(emojiInquiet, aucunResLabel);
        return aucunRes;

    }

    public void requestFocuse(){
        Platform.runLater(() -> this.barreRecherche.requestFocus());
    }

}
