package src;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import biblio.BiblioString;
import controleurs.AjouteEnchere;
import controleurs.ControleurTfPrix;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import modele.caches.Encherir;
import modele.caches.Objet;
import modele.caches.Photo;
import modele.caches.Vente;
import modele.mysql.SQLPhoto;
import modele.mysql.SQLVente;

public class EncherirObjet {

    public HBox laPage(Vente vente, Objet objet, Encherir enchere, List<Photo> photos) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox vBox2 = new VBox(30);

        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();

        Label labelNomObj = new Label(objet.getNomOb());
        Label labelDesc = new Label(EncherirObjet.addNewLines(objet.getDescriptionOb(), 50));
        Label labelPrix = new Label();
        Label labelDate = new Label("L'enchère se termine dans " + SQLVente.getTempsRestantVente(vente) + "!!!");
        Label labelEuro = new Label(BiblioString.euroString);
        Label labelTexte = new Label("Le montant de l'enchère s'élève à ");

        Label statutDeVente = new Label();
        Label gagnant = new Label();

        TextField textFieldPrix = new TextField();

        Button bouton = new Button("Encherir");
        bouton.setOnAction(new AjouteEnchere(vente, textFieldPrix, this));

        textFieldPrix.setTextFormatter(new TextFormatter<>(createFilter(6))); // Limite à 5 chiffres

        labelNomObj.setFont(Font.font(BiblioString.policeArial, FontWeight.NORMAL, 20));
        labelDesc.setFont(Font.font(BiblioString.policeArial, FontWeight.NORMAL, 10));

        labelDate.setText("L'enchère se termine dans " + SQLVente.getTempsRestantVente(vente) + "!!!");
        if (Main.getInstance().getMaxMontant(vente.getIdVe()) != null) {
            labelPrix.setText(Main.getInstance().getMaxMontant(vente.getIdVe()).getMontant() + "");
        }
        else {
            labelPrix.setText(vente.getPrixBase() + "");
        }
        if (SQLVente.getTempsRestantVente(vente) == null) {
            textFieldPrix.setVisible(false);
            bouton.setVisible(false);
            labelDate.setVisible(false);
            statutDeVente.setText("L'enchère n'est plus en cours");
            gagnant.setText("Le gagnant de l'enchère est : " + Main.getInstance().getUtilisateure(Main.getInstance().getMaxMontant(vente.getIdVe()).getIdUt()).getPseudoUt());
        }
        else {
            statutDeVente.setText("L'enchère est en cours");
            gagnant.setText(null);
        }

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            labelDate.setText("L'enchère se termine dans " + SQLVente.getTempsRestantVente(vente) + "!!!");
            if (Main.getInstance().getMaxMontant(vente.getIdVe()) != null) {
                labelPrix.setText(Main.getInstance().getMaxMontant(vente.getIdVe()).getMontant() + "");
            }
            else {
                labelPrix.setText(vente.getPrixBase() + "");
            }
            if (SQLVente.getTempsRestantVente(vente) == null) {
                textFieldPrix.setVisible(false);
                bouton.setVisible(false);
                labelDate.setVisible(false);
                statutDeVente.setText("L'enchère n'est plus en cours");
                gagnant.setText("Le gagnant de l'enchère est : " + Main.getInstance().getUtilisateure(Main.getInstance().getMaxMontant(vente.getIdVe()).getIdUt()).getPseudoUt());
            }
            else {
                statutDeVente.setText("L'enchère est en cours");
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        if (photos.isEmpty()) {
            ImageView imageView = new ImageView(new Image("./images/apareilPhot.jpg"));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            hBox2.getChildren().add(imageView);
        }
        else {
            for (Photo photo : photos) {
                ImageView imageView = new ImageView(SQLPhoto.avoirImage(photo));
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                if (hBox2.getChildren().size() == 2) {
                    hBox3.getChildren().add(imageView);
                }
                else {
                    hBox2.getChildren().add(imageView);
                }
            }
        }

        textFieldPrix.setOnKeyTyped(new ControleurTfPrix(labelPrix));

        hBox4.getChildren().addAll(labelTexte, labelPrix, labelEuro);

        vBox2.getChildren().addAll(hBox4, labelDate, bouton, textFieldPrix, statutDeVente, gagnant);
        vBox2.setStyle("-fx-border-color: #"+ BiblioString.couleurString +"; -fx-border-width: 0 0 0 2px;");
        vBox2.setPadding(new Insets(0, 0, 0, 20));

        vBox.setSpacing(30);
        vBox.getChildren().addAll(labelNomObj, hBox2, hBox3, labelDesc);
        if (hBox2.getChildren().size() == 2 || hBox3.getChildren().size() == 2) {
            vBox.setPadding(new Insets(0, 20, 0, 250));
        }
        else {
            vBox.setPadding(new Insets(0, 20, 0, 400));
        }

        hBox2.setSpacing(10);
        hBox3.setSpacing(10);

        hBox.getChildren().addAll(vBox, vBox2);

        hBox.setPadding(new Insets(20));

        return hBox;
    }

    private UnaryOperator<TextFormatter.Change> createFilter(int limit) {
        Pattern pattern = Pattern.compile("\\d{0," + limit + "}");
        return change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
    }

    public static String addNewLines(String input, int lineLength) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (char c : input.toCharArray()) {
            sb.append(c);
            count++;

            if (count == lineLength) {
                sb.append("\n");
                count = 0;
            }
        }

        return sb.toString();
    }

    public Alert popUpConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Veuillez confirmer");
        alert.setContentText("Voulez-vous vraiment enchérir sur cette vente ?");
        return alert;
    }

}
