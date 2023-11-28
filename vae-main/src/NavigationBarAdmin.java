package src;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import controleurs.Accueil;
import controleurs.Annonces;
import controleurs.BoutonArchive;
import controleurs.ContexteMenu;
import controleurs.LesRapports;
import controleurs.LesUtilisateurs;
import controleurs.MenuItemAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import modele.caches.Objet;

public class NavigationBarAdmin extends HBox {

    private controleurs.NavigationBar navigationBar;
    private LesUtilisateurs controleurLesUtilisateurs;

    public NavigationBarAdmin(Main main){
        super(50);
        super.setPadding(new Insets(20));
        super.setAlignment(Pos.CENTER_LEFT);

        super.getStylesheets().add(new File("./css/NavigationBar.css").toURI().toString());

        this.navigationBar = new controleurs.NavigationBar();
        this.controleurLesUtilisateurs = new LesUtilisateurs(main);

        super.getChildren().add(this.nomEntreprise(main));
        super.getChildren().add(this.lesAnnonces());
        super.getChildren().add(this.lesUtilisateurs());
        super.getChildren().add(this.lesRapports());
        super.getChildren().add(this.boutonArchive());

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

        annonce.setOnMouseClicked(new Annonces());

        annonce.setOnMouseEntered(this.navigationBar);
        annonce.setOnMouseExited(this.navigationBar);
        
        annonce.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        annonce.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        annonce.setTextFill(Color.web("014751"));
        annonce.setCursor(Cursor.HAND);

        return annonce;

    }
    private Label lesUtilisateurs(){

        Label utilisateurs = new Label("Les utilisateurs");

        utilisateurs.setOnMouseEntered(this.navigationBar);
        utilisateurs.setOnMouseExited(this.navigationBar);
        utilisateurs.setOnMouseClicked(this.controleurLesUtilisateurs);
        
        utilisateurs.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        utilisateurs.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        utilisateurs.setTextFill(Color.web("014751"));
        utilisateurs.setCursor(Cursor.HAND);

        return utilisateurs;

    }
    private Label lesRapports(){

        Label rapport = new Label("Les rapports");

        rapport.setOnMouseClicked(new LesRapports(Main.getInstance()));

        rapport.setOnMouseEntered(this.navigationBar);
        rapport.setOnMouseExited(this.navigationBar);
        
        rapport.setBorder(new Border(new BorderStroke(
            Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1.2,0)
        )));
        rapport.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        rapport.setTextFill(Color.web("014751"));
        rapport.setCursor(Cursor.HAND);

        return rapport;

    }

    public void userConnected(String pseudo, String role){

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

    public void removeLastChildren(){
        super.getChildren().remove(super.getChildren().size()-1);
    }

    public Button boutonArchive(){
            
        Button archive = new Button("Archiver");

        archive.setBackground(new Background(
                new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),
                    null)
            ));
        archive.setBorder(new Border(
                new BorderStroke(
                    Color.web("014751"), 
                    BorderStrokeStyle.SOLID, 
                    new CornerRadii(10), 
                    new BorderWidths(2))   
            ));
            archive.setOnAction(new BoutonArchive(this));
            return archive;
    
    }

    public void archive(List<Objet> dataList){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("archive2ans.pdf"));

            document.open();

            for (Objet data : dataList) {
                Paragraph paragraph = new Paragraph(data.toString());
                document.add(paragraph);
            }

            document.close();

            System.out.println("Le fichier PDF a été créé avec succès !");
        } catch (Exception e) {
            Main.getInstance().afficherErreur(e.getMessage());
        }

    }



    public Alert popUpValidationArchivage(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Archivage sur 2 ans");
        alert.setHeaderText("Effectuer l'archivage ");
        String message = "Voulez-vous effectuer l'archivage sur 2 ans ?";
        alert.setContentText(message);
        return alert;
    }

}
