package src;

import java.io.File;

import controleurs.Accueil;
import controleurs.Annonces;
import controleurs.ContexteMenu;
import controleurs.Identification;
import controleurs.MenuItemAction;
import controleurs.MesEncheresControleur;
import controleurs.NousContacter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NavigationBar extends HBox {

    private Identification identification;
    private controleurs.NavigationBar navigationBar;

    public NavigationBar(){

        super(50);
        super.setPadding(new Insets(20));
        super.setAlignment(Pos.CENTER_LEFT);

        super.getStylesheets().add(new File("./css/NavigationBar.css").toURI().toString());
        
        this.identification = new Identification();
        this.navigationBar = new controleurs.NavigationBar();

        HBox gestionDeCompte = this.gestionDeCompte();

        super.getChildren().add(this.nomEntreprise(Main.getInstance()));
        super.getChildren().add(this.lesAnnonces());
        super.getChildren().add(this.mesEncheres());
        super.getChildren().add(this.mesMessages());
        super.getChildren().add(this.nousContacter());
        super.getChildren().add(gestionDeCompte);

    }

    private Label nomEntreprise(Main main){

        Label nomEntreprise = new Label("VAE");

        nomEntreprise.setOnMouseClicked(new Accueil());

        nomEntreprise.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        nomEntreprise.setTextFill(Color.web("014751"));
        nomEntreprise.setCursor(Cursor.HAND);

        nomEntreprise.getStyleClass().add("label-entreprise");
        
        return nomEntreprise;

    }

    private Label lesAnnonces(){

        Label annonce = new Label("Les annonces");

        annonce.setOnMouseEntered(this.navigationBar);
        annonce.setOnMouseExited(this.navigationBar);

        annonce.setOnMouseClicked(new Annonces());
        
        annonce.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        annonce.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        annonce.setTextFill(Color.web("014751"));
        annonce.setCursor(Cursor.HAND);

        return annonce;

    }

    private Label mesEncheres(){

        Label mesEncheres = new Label("Mes enchères");

        mesEncheres.setOnMouseClicked(new MesEncheresControleur());

        mesEncheres.setOnMouseEntered(this.navigationBar);
        mesEncheres.setOnMouseExited(this.navigationBar);
        
        mesEncheres.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        mesEncheres.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        mesEncheres.setTextFill(Color.web("014751"));
        mesEncheres.setCursor(Cursor.HAND);

        return mesEncheres;

    }

    private Label mesMessages(){

        Label mesMessages = new Label("Mes messages");

        mesMessages.setOnMouseEntered(this.navigationBar);
        mesMessages.setOnMouseExited(this.navigationBar);
        
        mesMessages.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        mesMessages.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        mesMessages.setTextFill(Color.web("014751"));
        mesMessages.setCursor(Cursor.HAND);

        return mesMessages;

    }

    private Label nousContacter(){

        Label nousContacter = new Label("Nous contacter");
        
        nousContacter.setOnMouseEntered(this.navigationBar);
        nousContacter.setOnMouseExited(this.navigationBar);
        nousContacter.setOnMouseClicked(new NousContacter());
        
        nousContacter.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        nousContacter.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        nousContacter.setTextFill(Color.web("014751"));
        nousContacter.setCursor(Cursor.HAND);

        return nousContacter;

    }

    private HBox gestionDeCompte(){

        HBox gestionDeCompte = new HBox(10);

        gestionDeCompte.setAlignment(Pos.CENTER_RIGHT);

        gestionDeCompte.getChildren().add(this.meConnecter());
        gestionDeCompte.getChildren().add(this.mInscrire());

        super.setHgrow(gestionDeCompte, Priority.ALWAYS);

        return gestionDeCompte;

    }

    private Button meConnecter(){

        Button meConnecter = new Button("Connexion");

        meConnecter.setOnMouseEntered(this.navigationBar);
        meConnecter.setOnMouseExited(this.navigationBar);
        meConnecter.setOnMouseClicked(this.identification);

        meConnecter.setBackground(new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                null)
        ));
        meConnecter.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        meConnecter.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        meConnecter.setPrefHeight(50);
        meConnecter.setPrefWidth(120);
        meConnecter.setTextFill(Color.web("014751"));
        meConnecter.setCursor(Cursor.HAND);

        return meConnecter;

    }

    private Button mInscrire(){

        Button mInscrire = new Button("Inscription");

        mInscrire.setOnMouseEntered(this.navigationBar);
        mInscrire.setOnMouseExited(this.navigationBar);
        mInscrire.setOnMouseClicked(this.identification);

        mInscrire.setBackground(new Background(
            new BackgroundFill(
                Color.web("014751"),
                new CornerRadii(10),
                null)
        ));
        mInscrire.setBorder(new Border(
            new BorderStroke(
                Color.web("014751"), 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(10), 
                new BorderWidths(2))   
        ));
        mInscrire.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        mInscrire.setPrefHeight(50);
        mInscrire.setPrefWidth(120);
        mInscrire.setTextFill(Color.WHITE);
        mInscrire.setCursor(Cursor.HAND);

        return mInscrire;

    }

    public void userConnected(String pseudo, String role){

        super.getChildren().remove(super.getChildren().size()-1);
        super.getChildren().add(this.setUserConnection(role + " " + pseudo));

    }

    private HBox setUserConnection(String pseudo){

        HBox hBox = new HBox();

        Button button = new Button(pseudo);
        ImageView imageView = new ImageView(new Image(new File("./images/flecheBas.png").toURI().toString()));
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        button.setGraphic(imageView);

        button.setOnMouseClicked(new ContexteMenu(createContextMenu(button), button));

        button.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        button.setTextFill(Color.web("014751"));
        button.setBackground(null);
        button.setBorder(null);

        hBox.getChildren().add(button);

        hBox.setAlignment(Pos.CENTER_RIGHT);

        super.setHgrow(hBox, Priority.ALWAYS);

        return hBox;

    }

    public void removeUserConnexion(){
        super.getChildren().remove(super.getChildren().size()-1);
        super.getChildren().add(this.gestionDeCompte());
    }

    private ContextMenu createContextMenu(Button button) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("custom-context-menu");
        String taille = (button.getText().length()*11)+"px";
        contextMenu.setStyle("-fx-min-width: "+taille+"; -fx-max-width: "+taille+";");
        MenuItem profil = new MenuItem("Profil");
        profil.setStyle("-fx-text-fill: white;");
        MenuItem deconnexion = new MenuItem("Déconnexion");
        deconnexion.setStyle("-fx-text-fill: white;");
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setStyle("-fx-text-fill: white;");
        MenuItemAction menuItemAction = new MenuItemAction(button);
        profil.setOnAction(menuItemAction);
        deconnexion.setOnAction(menuItemAction);
        quitter.setOnAction(menuItemAction);
        contextMenu.getItems().addAll(profil,deconnexion,quitter);
        return contextMenu;
    }
    
}
