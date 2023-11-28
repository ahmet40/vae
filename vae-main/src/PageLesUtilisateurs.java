package src;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modele.caches.Utilisateur;
import modele.mysql.SQLUser;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

import controleurs.Recherche;
import controleurs.UtilisateurSupprimer;

public class PageLesUtilisateurs  extends VBox{
    private Main main;
    private TextField barreRecherche;
    private List<Utilisateur> utilisateurs;
    public PageLesUtilisateurs(Main main){
        super(50);

        this.main = main;


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
        super.getChildren().add(this.top());
        // super.getChildren().add(this.slider());
        super.getChildren().add(this.pasDeRecherche());
    }
    public TextField getRecherche(){
        return this.barreRecherche;
    }
    public HBox top(){
        ImageView loupe = new ImageView(new Image("images/rechercher.png"));
        loupe.setFitHeight(30);
        loupe.setFitWidth(30);

        HBox top = new HBox(50);
        top.setAlignment(Pos.CENTER);
        top.getChildren().add(loupe);
        top.getChildren().add(this.barreRecherche());
        return top;


    }
    public TextField barreRecherche(){
        this.barreRecherche = new TextField();
        this.barreRecherche.setPromptText("Rechercher un utilisateur");
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

        this.barreRecherche.setOnKeyTyped(new Recherche(this));

        return this.barreRecherche;
    }
    public VBox pasDeRecherche(){
        VBox pasDeRecherche = new VBox(50);
        pasDeRecherche.setAlignment(Pos.CENTER);

        ImageView emojiSourire = new ImageView(new Image("images/emojiSourire.png"));
        emojiSourire.setFitHeight(100);
        emojiSourire.setFitWidth(100);

        Label pasDeRechercheLabel = new Label("Rechercher un utilisateur");
        pasDeRechercheLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        pasDeRecherche.getChildren().addAll(emojiSourire, pasDeRechercheLabel);
        return pasDeRecherche;

    }
    public VBox aucunResultat(){
        VBox aucunRes = new VBox(50);
        aucunRes.setAlignment(Pos.CENTER);

        ImageView emojiInquiet = new ImageView(new Image("images/emojiInquiet.png"));
        emojiInquiet.setFitHeight(100);
        emojiInquiet.setFitWidth(100);

        Label aucunResLabel = new Label("Il n'y a aucun résultat");
        aucunResLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        aucunRes.getChildren().addAll(emojiInquiet, aucunResLabel);
        return aucunRes;

    }
    public ScrollPane slider(){
        ScrollPane leSlide = new ScrollPane();
        leSlide.setContent(this.listeUtilisateurs());
        leSlide.setPadding(new Insets(20));
        return leSlide;
    }
    public VBox listeUtilisateurs(){
        VBox liste = new VBox(50);
        for(int i = 0; i <this.utilisateurs.size(); i++){
            HBox boxUtilisateur = new HBox(20);
            Utilisateur user = this.utilisateurs.get(i);

            Label titreId = new Label("Identifiant : ");
            Label titrePseudo = new Label("Pseudo : ");
            Label titreMail = new Label("Mail : ");

            titreId.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            titrePseudo.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            titreMail.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

            Text id = new Text(String.valueOf(user.getIdUt()));
            Text pseudo = new Text(user.getPseudoUt());
            Text mail = new Text(user.getEmailUt());

            id.setStyle("-fx-font-size: 15px;");
            pseudo.setStyle("-fx-font-size: 15px;");
            mail.setStyle("-fx-font-size: 15px;");

            ImageView actif = null;
            if(user.getActiveUt() == 'O'){
                actif = new ImageView(new Image("images/actif.png"));
            }else{
                actif = new ImageView(new Image("images/inactif.png"));
            }
            actif.setFitHeight(10);
            actif.setFitWidth(10);

            Button supprimer = new Button();
            ImageView imageSupprimer = new ImageView(new Image("images/supprimer.png"));
            imageSupprimer.setFitHeight(20);
            imageSupprimer.setFitWidth(20);

            supprimer.setGraphic(imageSupprimer);
            supprimer.setBackground(null);
            supprimer.setEffect(null);
            supprimer.setOnAction(new UtilisateurSupprimer(this.main,  this, user));

            Tooltip tooltip = new Tooltip("Supprimer l'utilisateur");

            supprimer.setOnMouseEntered(event -> {
                tooltip.show(supprimer, event.getScreenX(), event.getScreenY() + 20);
            });

            supprimer.setOnMouseExited(event -> {
                tooltip.hide();
            });

            boxUtilisateur.getChildren().addAll(titreId,id, titrePseudo,pseudo, titreMail,mail, actif, supprimer);
            liste.getChildren().add(boxUtilisateur);
        }
        return liste;
    }
    public Alert popUpValidationSuppression(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'utilisateur");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        return alert;
    }
    public void majLesUtilisateurs(String recherche){
        this.utilisateurs = SQLUser.rechercheParText(main, recherche);

        super.getChildren().remove(1);
        if(this.barreRecherche.getText().length() == 0){
            super.getChildren().add(this.pasDeRecherche());
        }
        else{
            if(this.utilisateurs.size() == 0){
                super.getChildren().add(this.aucunResultat());
            }else{
                super.getChildren().add(this.slider());
            }
        }
    }
    
    public void supprimeUtilisateur(Utilisateur utilisateur){
        this.utilisateurs.remove(utilisateur);
    }

    public void requestFocuse(){
        Platform.runLater(() -> this.barreRecherche.requestFocus());
    }
}
