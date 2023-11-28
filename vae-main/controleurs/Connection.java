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
import src.Connexion;
import src.Main;

public class Connection implements EventHandler<ActionEvent>{

    private Connexion connexion;
    
    public Connection(Connexion connexion){
        this.connexion = connexion;
    }

    @Override
    public void handle(ActionEvent event) {

        Main main = Main.getInstance();
        String pseudo = this.connexion.getPseudo().getText();
        String password = this.connexion.getPassword().getText();

        if(password.equals("")) password = this.connexion.getPasswordP().getText();

        boolean isConnected = SQLUser.userConnection(pseudo, password);

        if(isConnected){
            String admOrUser = SQLUser.admOrUser(main, pseudo, password);
            main.getNavigationBar().userConnected(pseudo, main.getPersonneConnecte().getRole());
            if(MesEncheresControleur.connecte) main.getMesEncheres().set();
            main.mettreAuCentre(( 
                (NousContacter.connecte && admOrUser.equals("Utilisateur")) ? main.getNousContacter().top() 
                : (MesEncheresControleur.connecte && admOrUser.equals("Utilisateur")) ? main.getMesEncheres() 
                : main.getAccueil()) );
            main.userIsConnected(pseudo, password);
            if (admOrUser.equals("Administrateur")){
                main.getNavigationBarAdmin().userConnected(pseudo,"Administrateur");
                main.pageAdmin();
            }
            main.getAccueil().isAdmin();
            main.getAccueilAdmin().isAdmin();
            main.getAnnonce().isAdmin();
        }else{
            Border border = new Border(
            new BorderStroke(
                    Color.RED, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    new BorderWidths(1)
                ));
            this.connexion.getPseudo().setBorder(border);
            this.connexion.getPassword().setBorder(border);
            this.connexion.getPasswordP().setBorder(border);
            this.connexion.getPseudoErreur().setText(" - Pseudo ou mot de passe incorrecte.");
        }
        
    }
    
}
