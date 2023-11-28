package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controleurs.MouseEventEncherir;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;
import modele.caches.Encherir;
import modele.caches.Objet;
import modele.caches.Photo;
import modele.caches.Vente;
import modele.mysql.SQLPhoto;
import modele.mysql.SQLVente;

public class AccueilAdmin extends VBox {

    private List<Button> button;

    public AccueilAdmin(){

        this.button = new ArrayList<>();

        super.getChildren().add(this.hBox());
        super.getChildren().add(this.annonceBox());

    }

    private HBox hBox(){
        byte taille = 50;

        HBox encadrementBox = new HBox();
        HBox box = new HBox(80);

        ImageView annonce = this.imageView("./images/annonce.png", taille);
        ImageView user = this.imageView("./images/user.png", taille);
        ImageView rapport = this.imageView("./images/rapport.png", taille);

        box.getChildren().addAll(
            this.box(annonce,new Label("1 - Gérer les annonces")), 
            this.box(user,new Label("2 - Gérer les utilisateurs")), 
            this.box(rapport,new Label("3 - Analyser les rapports")));

        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20, 10, 20, 10));

        box.setBorder(new Border(
            new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2)
            ) 
        ));

        encadrementBox.getChildren().add(box);
        encadrementBox.setAlignment(Pos.TOP_CENTER);
        encadrementBox.setPadding(new Insets(0, 150, 0, 150));

        return encadrementBox;
    }

    private HBox annonceBox(){

        HBox annonceBox = new HBox(50);

        annonceBox.setPadding(new Insets(100,0,0,0));
        annonceBox.setAlignment(Pos.CENTER);

        List<TempsRestant> tempsRestant = SQLVente.ventesBientotExpiree(Main.getInstance().getVentes());

        for(int i = 0; i < tempsRestant.size(); ++i){
            annonceBox.getChildren().add(this.annonce(annonceBox, Main.getInstance().getVente(tempsRestant.get(i).getId())));
            if(i == 3) break;
        }
        if(annonceBox.getChildren().size() == 0) this.plusDenchere(annonceBox);

        return annonceBox;

    }

    private ImageView imageView(String path, byte taille){
        ImageView imageView = new ImageView(new Image(new File(path).toURI().toString()));
        imageView.setFitHeight(taille);
        imageView.setFitWidth(taille);
        return imageView;
    }

    private HBox box(ImageView imageView, Label label){
        HBox box = new HBox(10, imageView, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox annonce(HBox annonceBox, Vente vente){
        VBox annonce = new VBox(20);

        annonce.setPadding(new Insets(10));
        annonce.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                Insets.EMPTY
            )
        ));

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
        Label tempsRestant = new Label("Temps restant : " + SQLVente.getTempsRestantVente(vente));
        tempsRestant.setTextFill(Color.WHITE);

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
        button.setTextFill(Color.WHITE);
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
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        button.setCursor(Cursor.HAND);
        MouseEventEncherir mouseEventEncherir = new MouseEventEncherir(button, vente, objet, encherir, photos);
        button.setOnMouseClicked(mouseEventEncherir);
        button.setOnMouseEntered(mouseEventEncherir);
        button.setOnMouseExited(mouseEventEncherir);
        hBox2.getChildren().add(button);
        this.button.add(button);
        hBox2.setAlignment(Pos.CENTER);
        annonce.getChildren().addAll(tempsRestant,hBox2);

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            Encherir enchere = Main.getInstance().getMaxMontant(vente.getIdVe());
            if(enchere == null) montant.setText("Prix actuelle : " + vente.getPrixBase() + "€");
            else montant.setText("Prix actuelle : " + enchere.getMontant() + "€");
            tempsRestant.setText("Temps restant : " + SQLVente.getTempsRestantVente(vente));
            if (tempsRestant.getText().equals("00:00:00")) {
                annonceBox.getChildren().remove(annonce);
                if(annonceBox.getChildren().size() == 0) this.plusDenchere(annonceBox);
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        annonce.setPrefSize(250, 350);

        return annonce;
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

    public void majAffichage(){
        System.gc();
        super.getChildren().set(1, this.annonceBox());
    }

    private void plusDenchere(HBox annonceBox){
        Label label = new Label("Il n'y a plus d'enchères en cours.");
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label);
        vBox.setAlignment(Pos.CENTER);
        annonceBox.getChildren().addAll(vBox);
    }

}
