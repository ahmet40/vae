package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import modele.mysql.SQLUser;
import src.Main;

public class Inscription implements EventHandler<ActionEvent>{

    private src.Inscription inscription;

    public Inscription(src.Inscription inscription){
        this.inscription = inscription;
    }

    @Override
    public void handle(ActionEvent arg0) {

        Main main = Main.getInstance();
        String pseudo = this.inscription.getPseudo().getText();
        String mail = this.inscription.getEmail().getText();
        String password = this.inscription.getPassword().getText();
        if(password.equals("") || password.equals(" ")) password = this.inscription.getPasswordP().getText();

        Boolean isRegistred = false;
        if(this.inscription.isMailCorrect()){
            isRegistred = SQLUser.userInscription(pseudo, mail, password);
        }

        if(isRegistred != null && isRegistred){
            String admOrUser = SQLUser.admOrUser(main, pseudo, password);
            main.getNavigationBar().userConnected(pseudo, "Utilisateur");
            if(MesEncheresControleur.connecte) main.getMesEncheres().set();
            main.mettreAuCentre(( 
                (NousContacter.connecte && admOrUser.equals("Utilisateur")) ? main.getNousContacter().top() 
                : (MesEncheresControleur.connecte && admOrUser.equals("Utilisateur")) ? main.getMesEncheres() 
                :
                main.getAccueil()) );            
            main.userIsConnected(pseudo, password);
        }else{
            Border border = new Border(
            new BorderStroke(
                    Color.RED, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    new BorderWidths(1)
                ));
            this.inscription.getPseudo().setBorder(border);
            this.inscription.getEmail().setBorder(border);
            this.inscription.getPassword().setBorder(border);
            this.inscription.getPasswordP().setBorder(border);
            if(isRegistred == null){
                this.inscription.getPseudoErreur().setText("- Pseudo, email ou mot de passe incorrecte.");
            }else{
                this.inscription.getPseudoErreur().setText( ((this.inscription.isMailCorrect()) ? " - Ce compte existe déjà." : " - Votre email est incorrect.") );
            }
        }

    }
    
}
