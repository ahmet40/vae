package src;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.caches.Utilisateur;

public class Profil extends VBox {

    private TextField pseudo;
    private TextField email;
    private TextField password;
    private PasswordField passwordP;
    private boolean mailCorrect;

    public Profil(){

        super(10);

        this.mailCorrect = false;

        super.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label changerNom = new Label("Changer de nom : ");
        Label changerMail = new Label("Changer de mail : ");
        Label changerMdp = new Label("Changer de mot de passe : ");

        Utilisateur utilisateur = Main.getInstance().getPersonneConnecte();

        TextField changerDeNom = new TextField(utilisateur.getPseudoUt());
        TextField changerDeMail = new TextField(utilisateur.getEmailUt());
        changerDeMail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0){
                changerDeMail.setStyle("");
            }
            else{
                if (!newValue.contains("@")) {
                    changerDeMail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                } else {
                    if (newValue.contains(" ")){
                        changerDeMail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                        this.mailCorrect = false;
                    

                    }else if (newValue.contains(".com")){
                        changerDeMail.setStyle(""); // Rétablissez le style par défaut si "@" et ".com" est présent
                        this.mailCorrect = true;
                        System.out.println(this.mailCorrect);

                        
                    }else if (newValue.contains(".fr")){
                        changerDeMail.setStyle(""); // Rétablissez le style par défaut si bleu si "@" et ".fr" est présent
                        this.mailCorrect = true;
                        System.out.println(this.mailCorrect);

                    }else {
                        changerDeMail.setStyle("-fx-border-color: red;"); // Mettez en surbrillance le TextField avec une bordure rouge si "@" est manquant
                        this.mailCorrect = false;
                    }
                }
            }
        });
        PasswordField changerDeMdp = new PasswordField();
        changerDeMdp.setText(utilisateur.getMdpUt());

        gridPane.add(changerNom, 0, 0);
        gridPane.add(changerDeNom, 1, 0);

        gridPane.add(changerMail, 0, 1);
        gridPane.add(changerDeMail, 1, 1);

        gridPane.add(changerMdp, 0, 2);
        gridPane.add(changerDeMdp, 1, 2);

        gridPane.setAlignment(Pos.CENTER);

        Button save = new Button("Sauvegarder");
        Button del = new Button("Supprimer le compte");

        super.getChildren().addAll(gridPane, save, del);

    }

    public TextField getPseudo() {
        return this.pseudo;
    }

    public TextField getEmail() {
        return this.email;
    }

    public TextField getPassword() {
        return this.password;
    }

    public PasswordField getPasswordP() {
        return this.passwordP;
    }

}
