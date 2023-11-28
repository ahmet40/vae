package src;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
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
import modele.caches.Encherir;
import modele.caches.Objet;
import modele.caches.Photo;
import modele.caches.Vente;
import modele.mysql.SQLPhoto;
import modele.mysql.SQLVente;

import java.io.File;
import java.util.List;

import controleurs.EnchereControler;
import controleurs.MouseEventEncherir;

public class MesEncheres extends VBox {

    private TitledPane mesEncherePasse;
    private TitledPane mesEnchereEnCours;
    private TitledPane enchereEncherie;

    private ScrollPane lastEncheres;
    private ScrollPane progressEnchere;
    private ScrollPane encherieEnchere;

    public MesEncheres(){

        Button newEnchere = new Button("Ajouter une nouvelle enchère");
        newEnchere.setOnMouseClicked(new EnchereControler(Main.getInstance()));

        this.lastEncheres = new ScrollPane();
        this.progressEnchere = new ScrollPane();
        this.encherieEnchere = new ScrollPane();

        this.mesEncherePasse = new TitledPane("Mes enchères finis", this.lastEncheres);        
        this.mesEnchereEnCours = new TitledPane("Mes enchères en cours", this.progressEnchere);
        this.enchereEncherie = new TitledPane("Enchères sur lesquels j'ai enchéri", this.encherieEnchere);

        this.mesEncherePasse.setExpanded(false);
        this.mesEnchereEnCours.setExpanded(false);
        this.enchereEncherie.setExpanded(false);

        Accordion accordion = new Accordion();

        accordion.getPanes().addAll(this.mesEncherePasse, this.mesEnchereEnCours, this.enchereEncherie);

        accordion.expandedPaneProperty().addListener((obs, oldExpanded, newExpanded) -> {
            if (newExpanded == null) {
                accordion.getPanes().forEach(pane -> pane.setExpanded(false));
            } else {
                accordion.getPanes().forEach(pane -> {
                    if (pane == newExpanded) {
                        pane.setExpanded(true);
                    } else {
                        pane.setExpanded(false);
                    }
                });
            }
        });

        HBox hBox = new HBox(newEnchere);

        hBox.setAlignment(Pos.BOTTOM_RIGHT);

        super.getChildren().addAll(accordion, hBox);

    }

    public void set(){
        System.gc();
        Main main = Main.getInstance();
        VBox passe = new VBox(this.annonceBox(main.getVentePasse(main.getPersonneConnecte().getIdUt())));
        VBox enCours = new VBox(this.annonceBox(main.getVenteEnCours(main.getPersonneConnecte().getIdUt())));
        VBox encheri = new VBox(this.annonceBox(main.getVenteEncheri(main.getPersonneConnecte().getIdUt())));
        this.lastEncheres.setContent(passe);
        this.progressEnchere.setContent(enCours);
        this.encherieEnchere.setContent(encheri);
    }

    private HBox annonceBox(List<Vente> liste){

        HBox annonceBox = new HBox(50);

        annonceBox.setPadding(new Insets(100,0,0,0));
        annonceBox.setAlignment(Pos.CENTER);

        int index = 0;
        for(int i = 0; i < liste.size(); ++i){
            annonceBox.getChildren().add(this.annonce(annonceBox, liste.get(i)));
            if(index == 3) index = -1;
            ++index;
        }

        return annonceBox;

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
        Label tempsRestant = new Label("Temps restant : " + ((SQLVente.getTempsRestantVente(vente) == null) ? "Enchère fini" : SQLVente.getTempsRestantVente(vente)) );
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
        Button supprimer = new Button("Supprimer");
        supprimer.setText("Supprimer");
        supprimer.setTextFill(Color.RED);
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

        supprimer.setBorder(new Border(
            new BorderStroke(
                Color.web("006b7b"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2)
            )
        ));
        supprimer.setBackground(new Background(
            new BackgroundFill(
                Color.web("006b7b"),
                new CornerRadii(10),
                Insets.EMPTY
            )));
        supprimer.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        supprimer.setCursor(Cursor.HAND);

        MouseEventEncherir mouseEventEncherirSupprimer = new MouseEventEncherir(supprimer, vente, objet, encherir, photos);
        supprimer.setOnMouseClicked(mouseEventEncherirSupprimer);
        supprimer.setOnMouseEntered(mouseEventEncherirSupprimer);
        supprimer.setOnMouseExited(mouseEventEncherirSupprimer);

        MouseEventEncherir mouseEventEncherir = new MouseEventEncherir(button, vente, objet, encherir, photos);
        button.setOnMouseClicked(mouseEventEncherir);
        button.setOnMouseEntered(mouseEventEncherir);
        button.setOnMouseExited(mouseEventEncherir);
        hBox2.getChildren().add(button);
        hBox2.setAlignment(Pos.CENTER);
        HBox hBox3 = new HBox(supprimer);
        hBox3.setAlignment(Pos.CENTER);
        annonce.getChildren().addAll(tempsRestant,hBox2, hBox3);

        annonce.setPrefSize(250, 350);

        return annonce;
    }
    
}
